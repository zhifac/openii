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
import org.mitre.harmony.model.MappingCellManager;
import org.mitre.harmony.model.SchemaManager;
import org.mitre.harmony.model.filters.Filters;
import org.mitre.harmony.model.filters.Focus;
import org.mitre.harmony.model.preferences.Preferences;
import org.mitre.harmony.model.selectedInfo.SelectedInfo;
import org.mitre.harmony.view.harmonyPane.HarmonyFrame;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.graph.FilteredGraph;

/**
 * Displays the dialog which displays the matcher processing
 * @author CWOLF
 */
class MatcherDialog extends JDialog implements ActionListener,Runnable
{	
	private ArrayList<MatchVoter> voters;								// List of match voters to use
	private MatchMerger merger;											// Match merger being used
	private JLabel progressLabel = new JLabel("Generating Matches");	// Labels the progress bar
	private JButton cancelButton = new JButton("Cancel");				// Cancel button
	
	/** Generates the main pane for the matcher dialog */
	private JPanel mainPane()
	{
		// Creates progress bar to show that processing is occurring
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		
		// Creates the progress bar pane
		JPanel progressPane = new JPanel();
		progressPane.setBorder(new EmptyBorder(0,0,5,0));
		progressPane.setLayout(new BorderLayout());
		progressPane.add(progressLabel,BorderLayout.NORTH);
		progressPane.add(progressBar,BorderLayout.CENTER);
		
		// Create button pane
		JPanel buttonPane = new JPanel();
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
		// Place progress bar in center of project pane
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(10,20,10,20));
		pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
		pane.add(progressPane);
		pane.add(buttonPane);
		return pane;
	}
	
	/** Construct the matcher dialog */
	MatcherDialog(MatchMerger merger, ArrayList<MatchVoter> voters)
	{
		super(HarmonyFrame.harmonyFrame.getFrame());
		this.merger = merger;
		this.voters = voters;
	
		// Set up matcher dialog layout and contents
		setTitle("Schema Matcher");
		setModal(true);
    	setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setContentPane(mainPane());
		setLocationRelativeTo(HarmonyFrame.harmonyFrame);
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
		String matcherName;
		if(voters.size()>1)
		{
			matcherName = merger.getName() + "(";
			for(MatchVoter voter : voters)
				matcherName += voter + ", ";
			matcherName.replace(", $",")");
		}
		else matcherName = voters.get(0).getName();
		
		// Determine what left roots to match on
		ArrayList<FilteredGraph> leftGraphs = new ArrayList<FilteredGraph>();
		if(Filters.getFocus(HarmonyConsts.LEFT)!=null)
		{
			Focus focus = Filters.getFocus(HarmonyConsts.LEFT);
			FilteredGraph graph = new FilteredGraph(SchemaManager.getGraph(focus.getSchemaID()));
			graph.setFilteredRoot(focus.getElementID());
			leftGraphs.add(graph);
		}
		else for(Integer schemaID : SelectedInfo.getSchemas(HarmonyConsts.LEFT))
			leftGraphs.add(new FilteredGraph(SchemaManager.getGraph(schemaID)));

		// Set the min and max depths for each graph on the left
		for(FilteredGraph graph : leftGraphs)
		{
			graph.setMinDepth(Filters.getMinDepth(HarmonyConsts.LEFT));
			graph.setMaxDepth(Filters.getMaxDepth(HarmonyConsts.LEFT));
			graph.setHiddenElements(Preferences.getFinishedElements(graph.getSchema().getId()));
		}
			
		// Determine what right roots to match on
		ArrayList<FilteredGraph> rightGraphs = new ArrayList<FilteredGraph>();
		if(Filters.getFocus(HarmonyConsts.RIGHT)!=null)
		{
			Focus focus = Filters.getFocus(HarmonyConsts.RIGHT);
			FilteredGraph graph = new FilteredGraph(SchemaManager.getGraph(focus.getSchemaID()));
			graph.setFilteredRoot(focus.getElementID());
			rightGraphs.add(graph);
		}
		else for(Integer schemaID : SelectedInfo.getSchemas(HarmonyConsts.RIGHT))
			rightGraphs.add(new FilteredGraph(SchemaManager.getGraph(schemaID)));

		// Set the min and max depths for each graph on the right
		for(FilteredGraph graph : rightGraphs)
		{
			graph.setMinDepth(Filters.getMinDepth(HarmonyConsts.RIGHT));
			graph.setMaxDepth(Filters.getMaxDepth(HarmonyConsts.RIGHT));
			graph.setHiddenElements(Preferences.getFinishedElements(graph.getSchema().getId()));
		}
		
		// Perform matches on the specified left and right element
		for(FilteredGraph leftGraph : leftGraphs)
			for(FilteredGraph rightGraph : rightGraphs)
				if(!leftGraph.getSchema().getId().equals(rightGraph.getSchema().getId()))
				{
					// Generate the match scores for the left and right roots
					MatchScores matchScores = MatcherManager.getScores(leftGraph, rightGraph, voters, merger);
	
					// Store all generated match scores
					for(MatchScore matchScore : matchScores.getScores())
					{
						// Get mapping cell ID
						Integer mappingCellID = MappingCellManager.getMappingCellID(matchScore.getElement1(), matchScore.getElement2());
						if(mappingCellID==null) mappingCellID = MappingCellManager.createMappingCell(matchScore.getElement1(), matchScore.getElement2());
	
						// Modify mapping cell with new score
						MappingCell mappingCell = MappingCellManager.getMappingCell(mappingCellID);
						if(!mappingCell.getValidated())
							MappingCellManager.modifyMappingCell(mappingCellID, matchScore.getScore(), matcherName, false);
					}
				}
			
		// Once matching is completed, shut down dialog box
		dispose();
	}
	
	/**
	 * Handles the pressing of the cancel button
	 */
	public void actionPerformed(ActionEvent e)
	{
		// TODO: Implement ability to stop matchers
	}
}