// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.matcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.matchers.MatchScore;
import org.mitre.harmony.matchers.MatchScores;
import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.mergers.MatchMerger;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.filters.FilterManager;
import org.mitre.harmony.model.filters.Focus;
import org.mitre.harmony.model.mapping.MappingCellManager;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 * Displays the dialog which displays the matcher processing
 * @author CWOLF
 */
class MatcherDialog extends JDialog implements ActionListener, Runnable
{
	/** Stores the match voters being used */
	private ArrayList<MatchVoter> voters;

	/** Stores the match mergers being used */
	private MatchMerger merger;

	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Stores the matcher thread */
	private Thread thread = null;
	
	/** Used to halt the running of the matchers */
	private boolean stop = false;
	
	/** Generates the main pane for the matcher dialog */
	private JPanel getMainPane()
	{
		// Creates progress bar to show that processing is occurring
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);

		// Creates the progress bar pane
		JPanel progressPane = new JPanel();
		progressPane.setBorder(new EmptyBorder(0, 0, 5, 0));
		progressPane.setLayout(new BorderLayout());
		progressPane.add(new JLabel("Generating Matches"), BorderLayout.NORTH);
		progressPane.add(progressBar, BorderLayout.CENTER);

		// Create button pane
		JPanel buttonPane = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);

		// Place progress bar in center of project pane
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(10, 20, 10, 20));
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		pane.add(progressPane);
		pane.add(buttonPane);
		return pane;
	}

	/** Construct the matcher dialog */
	MatcherDialog(MatchMerger merger, ArrayList<MatchVoter> voters, HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		this.merger = merger;
		this.voters = voters;
		this.harmonyModel = harmonyModel;
		
		// Set up matcher dialog layout and contents
		setTitle("Schema Matcher");
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setContentPane(getMainPane());
		setLocationRelativeTo(harmonyModel.getBaseFrame());
		pack();

		// Initialize the execution of the matcher
		thread = new Thread(this);
		thread.start();

		// Display the window
		setVisible(true);
	}

	/** Returns the generated matcher name */
	private String getMatcherName()
	{
		// Handles the case where all voters were used
		if(voters.size() == MatcherManager.getVoters().size())
			return merger.getName() + "(All Voters)";
		
		// Handles the case where a single voter was used
		if(voters.size() == 1) return voters.get(0).getName();

		// Handles the case where a subset of voters was used
		String matcherName = merger.getName() + "(";
		for (MatchVoter voter : voters) 
		{
			if (matcherName.length() + voter.getName().length() > 45)
				{ matcherName += "..., "; break; }
			matcherName += voter.getName() + ", ";
		}
		matcherName = matcherName.substring(0, matcherName.length() - 2) + ")";
		return matcherName;
	}
	
	/** Returns the list of filtered schema info for the specified side */
	private ArrayList<FilteredSchemaInfo> getFilteredSchemaInfoList(Integer side)
	{
		ArrayList<FilteredSchemaInfo> filteredSchemaInfoList = new ArrayList<FilteredSchemaInfo>();
		FilterManager filters = harmonyModel.getFilters();
		
		// Create filtered schema info for each schema in focus
		for(Integer schemaID : harmonyModel.getMappingManager().getSchemaIDs(side))
			if(filters.inFocus(side, schemaID))
			{
				HierarchicalSchemaInfo schemaInfo = harmonyModel.getSchemaManager().getSchemaInfo(schemaID);
				FilteredSchemaInfo filteredSchemaInfo = new FilteredSchemaInfo(schemaInfo);

				// Set the filter roots
				Focus focus = filters.getFocus(side,schemaID);
				if(focus!=null && focus.getFocusedPaths().size()>0)
					filteredSchemaInfo.setFilteredRoots(focus.getFocusedIDs());
					
				// Filter by minimum and maximum depth
				filteredSchemaInfo.setMinDepth(filters.getMinDepth(side));
				filteredSchemaInfo.setMaxDepth(filters.getMaxDepth(side));
						
				// Set the hidden elements
				ArrayList<Integer> hiddenElements = new ArrayList<Integer>();
				hiddenElements.addAll(harmonyModel.getPreferences().getFinishedElements(schemaID));
				if(focus!=null) hiddenElements.addAll(focus.getHiddenElements());
				filteredSchemaInfo.setHiddenElements(hiddenElements);

				// Add the filtered schema info
				filteredSchemaInfoList.add(filteredSchemaInfo);
			}
		
		return filteredSchemaInfoList;
	}

	/** Runs the matchers on the specified schemas */
	private void runMatch(FilteredSchemaInfo leftSchemaInfo, FilteredSchemaInfo rightSchemaInfo, String matcherName)
	{
		// Generate the match scores for the left and right roots
		MatchScores matchScores = MatcherManager.getScores(leftSchemaInfo, rightSchemaInfo, voters, merger);

		// Store all generated match scores
		MappingCellManager manager = harmonyModel.getMappingCellManager();
		for (MatchScore matchScore : matchScores.getScores())
		{
			// Don't proceed if process has been stopped
			if(stop)
				return;
			
			// Don't store mapping cells which were already validated
			Integer mappingCellID = manager.getMappingCellID(matchScore.getElement1(), matchScore.getElement2());
			if(mappingCellID!=null && manager.getMappingCell(mappingCellID).getValidated()) continue;
			
			// Get the mapping cell properties
			Integer id = manager.getMappingCellID(matchScore.getElement1(), matchScore.getElement2());
			Integer mappingID = harmonyModel.getMappingManager().getMapping().getId();
			Integer input = matchScore.getElement1();
			Integer output = matchScore.getElement2();
			Double score = matchScore.getScore();
			Date date = Calendar.getInstance().getTime();
			
			// Set the mapping cell
			MappingCell mappingCell = MappingCell.createProposedMappingCell(id, mappingID, input, output, score, matcherName, date, null);
			manager.setMappingCell(mappingCell);
		}
	}
	
	/** Runs the matcher dialog processing as a separate thread */
	public void run()
	{
		// Generate the matcher name
		String matcherName = getMatcherName();

		// Store the left and right schema info
		ArrayList<FilteredSchemaInfo> leftSchemaInfoList = getFilteredSchemaInfoList(MappingSchema.LEFT);
		ArrayList<FilteredSchemaInfo> rightSchemaInfoList = getFilteredSchemaInfoList(MappingSchema.RIGHT);

		// Matches all left schemas to all right schemas
		RunMatchers: for(FilteredSchemaInfo leftSchemaInfo : leftSchemaInfoList)
			for(FilteredSchemaInfo rightSchemaInfo : rightSchemaInfoList)
			{
				if(stop) break RunMatchers;
				try { runMatch(leftSchemaInfo,rightSchemaInfo, matcherName); }
				catch (Exception e) { System.out.println("(E) MatcherDialog.run - " + e.getMessage()); }
			}
		
		// Once matching is completed, shut down dialog box
		if(!stop) dispose();
	}

	/** Cancels the running of the matchers */
	public void actionPerformed(ActionEvent e)
	{
		stop=true;
		try { Thread.sleep(500); } catch(Exception e2) {}
		if(thread.isAlive()) thread.interrupt();
		dispose();
	}
}