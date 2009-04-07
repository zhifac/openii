// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.matcher;

import java.util.ArrayList;
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
import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.filters.FilterManager;
import org.mitre.harmony.model.filters.Focus;
import org.mitre.harmony.model.mapping.MappingCellManager;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.graph.FilteredGraph;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 * Displays the dialog which displays the matcher processing
 * 
 * @author CWOLF
 */
class MatcherDialog extends JDialog implements ActionListener, Runnable
{
	private ArrayList<MatchVoter> voters; // List of match voters to use
	private MatchMerger merger; // Match merger being used
	private JLabel progressLabel = new JLabel("Generating Matches"); // Labels the progress bar
	private JButton cancelButton = new JButton("Cancel"); // Cancel button
	private String matcherName;

	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Generates the main pane for the matcher dialog */
	private JPanel mainPane()
	{
		// Creates progress bar to show that processing is occurring
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);

		// Creates the progress bar pane
		JPanel progressPane = new JPanel();
		progressPane.setBorder(new EmptyBorder(0, 0, 5, 0));
		progressPane.setLayout(new BorderLayout());
		progressPane.add(progressLabel, BorderLayout.NORTH);
		progressPane.add(progressBar, BorderLayout.CENTER);

		// Create button pane
		JPanel buttonPane = new JPanel();
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
		setContentPane(mainPane());
		setLocationRelativeTo(harmonyModel.getBaseFrame());
		pack();

		// Initialize the execution of the matcher
		new Thread(this).start();

		// Display the window
		setVisible(true);
	}

	/** Runs the matcher dialog processing as a separate thread */
	public void run()
	{
		// Generate the matcher name
		if (voters.size() == MatcherManager.getVoters().size()) matcherName = merger.getName() + "(All Voters)";
		else if (voters.size() == 1) matcherName = voters.get(0).getName();
		else
		{
			matcherName = merger.getName() + "(";
			for (MatchVoter voter : voters) 
			{
				if (matcherName.length() + voter.getName().length() > 45)
					{ matcherName += "..., "; break; }
				matcherName += voter.getName() + ", ";
			}
			matcherName = matcherName.substring(0, matcherName.length() - 2) + ")";
		}

		// Store the left and right graphs
		ArrayList<FilteredGraph> leftGraphs = new ArrayList<FilteredGraph>();
		ArrayList<FilteredGraph> rightGraphs = new ArrayList<FilteredGraph>();		
		
		// Generate graphs for the left and right sides
		Integer sides[] = { HarmonyConsts.LEFT, HarmonyConsts.RIGHT };
		for(Integer side : sides)
		{
			FilterManager filters = harmonyModel.getFilters();
			ArrayList<FilteredGraph> filteredGraphs = side==HarmonyConsts.LEFT ? leftGraphs : rightGraphs;
			
			// Create filtered graphs for each schema in focus
			for(Integer schemaID : harmonyModel.getSelectedInfo().getSchemas(side))
				if(filters.inFocus(side, schemaID))
				{
					HierarchicalGraph graph = harmonyModel.getSchemaManager().getGraph(schemaID);
					FilteredGraph filteredGraph = new FilteredGraph(graph);
	
					// Set the filter roots
					Focus focus = filters.getFocus(side,schemaID);
					if(focus!=null && focus.getFocusedIDs().size()>0)
						filteredGraph.setFilteredRoots(focus.getFocusedIDs());
					
					// Filter by minimum and maximum depth
					filteredGraph.setMinDepth(filters.getMinDepth(side));
					filteredGraph.setMaxDepth(filters.getMaxDepth(side));
							
					// Set the hidden elements
					ArrayList<Integer> hiddenElements = new ArrayList<Integer>();
					hiddenElements.addAll(harmonyModel.getPreferences().getFinishedElements(schemaID));
					if(focus!=null) hiddenElements.addAll(focus.getHiddenElements());
					filteredGraph.setHiddenElements(hiddenElements);
	
					// Add the filtered graph
					filteredGraphs.add(filteredGraph);
				}
		}

		// Matches all left graphs to all right graphs
		for(FilteredGraph leftGraph : leftGraphs)
			for(FilteredGraph rightGraph : rightGraphs)
				try {
					runMatch(leftGraph,rightGraph);
				} catch (Exception e) { System.out.println("(E) MatcherDialog.run - " + e.getMessage()); }

		// Once matching is completed, shut down dialog box
		dispose();
	}

	private void runMatch(FilteredGraph leftGraph, FilteredGraph rightGraph)
	{
		// Generate the match scores for the left and right roots
		MatchScores matchScores = MatcherManager.getScores(leftGraph, rightGraph, voters, merger);

		// Store all generated match scores
		MappingCellManager manager = harmonyModel.getMappingCellManager();
		for (MatchScore matchScore : matchScores.getScores())
		{
			MappingCell mappingCell = new MappingCell();
			mappingCell.setId(manager.getMappingCellID(matchScore.getElement1(), matchScore.getElement2()));
			mappingCell.setElement1(matchScore.getElement1());
			mappingCell.setElement2(matchScore.getElement2());
			mappingCell.setScore(matchScore.getScore());
			mappingCell.setAuthor(matcherName);
			mappingCell.setValidated(false);
			manager.setMappingCell(mappingCell);
		}
	}

	/**
	 * Handles the pressing of the cancel button
	 */
	public void actionPerformed(ActionEvent e) {
	// TODO: Implement ability to stop matchers
	}
}