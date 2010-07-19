package org.mitre.openii.editors.mappings.quickAlign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.mitre.harmony.matchers.MatchScore;
import org.mitre.harmony.matchers.MatchScores;
import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.mergers.VoteMerger;
import org.mitre.harmony.matchers.voters.MappingMatcher;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/** Constructs the Quick Alignment Mapping Editor */
public class QuickAlignEditor extends OpenIIEditor implements SelectionListener
{
	/** Stores a list of all match panes */
	private ArrayList<MatchPane> matchPanes = new ArrayList<MatchPane>();
	
	/** Class for displaying a source element and its possible matches */
	private class MatchPane extends Composite implements ISelectionChangedListener
	{
		/** Stores the source ID */
		private Integer sourceID;
		
		/** Stores the target info */
		private HierarchicalSchemaInfo targetInfo = null;
		
		// Stores various components used in the match pane */
		private ComboViewer viewer = null;
		private Label pathLabel = null;

		/** Updates the path label */
		public void updatePathLabel()
		{
			String path = "";
			SchemaElement element = getElement();
			if(element!=null)
			{
				ArrayList<SchemaElement> pathElements = targetInfo.getPaths(element.getId()).get(0);
				for(SchemaElement pathElement : pathElements.subList(0, pathElements.size()-1))
					path += targetInfo.getDisplayName(pathElement.getId()) + " -> ";
				if(path.length()>4) path = path.substring(0, path.length()-4);
			}
			pathLabel.setText(path);
		}
		
		/** Constructs the match pane */
		private MatchPane(Composite parent, Integer sourceID, HierarchicalSchemaInfo targetInfo)
		{
			// Constructs the pane
			super(parent, SWT.NONE);
			this.sourceID = sourceID;
			this.targetInfo = targetInfo;
			GridLayout layout = new GridLayout(1,false);
			layout.marginHeight = 2;
			layout.marginWidth = 0;
			setLayout(layout);
			setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			// Create the selection pane
			viewer = new ComboViewer(this,SWT.NONE);
			viewer.getCombo().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			viewer.addSelectionChangedListener(this);

			// Create the path label
			pathLabel = new Label(this, SWT.NONE);
			pathLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		
		/** Adds an element to the selection pane */
		private void add(Object object)
			{ viewer.add(object); }

		/** Selects an object in the selection pane */
		private void select(Integer loc)
			{ viewer.getCombo().select(loc); updatePathLabel(); }

		/** Returns the source ID */
		private Integer getSourceID()
			{ return sourceID; }
		
		/** Get the selected element */
		private SchemaElement getElement()
		{
			Object selection = ((StructuredSelection)viewer.getSelection()).getFirstElement();
			return selection instanceof SchemaElement ? (SchemaElement)selection : null;
		}
		
		/** Update the path label */
		public void selectionChanged(SelectionChangedEvent e)
			{ updatePathLabel(); }
	}
	
	/** Retrieves the user matches */
	private HashMap<Integer,Integer> getUserMatches()
	{
		HashMap<Integer,Integer> matches = new HashMap<Integer,Integer>();
		for(MappingCell mappingCell : OpenIIManager.getMappingCells(getElementID()))
			if(mappingCell.isValidated())
				matches.put(mappingCell.getInput()[0], mappingCell.getOutput());
		return matches;
	}
	
	/** Sorts the list of matches */
	private ArrayList<MatchScore> sortMatches(ArrayList<MatchScore> matches)
	{
		/** Compares two matches to one another */
		class ScoreComparator implements Comparator<MatchScore>
			{ public int compare(MatchScore score1, MatchScore score2)
				{ return score1.getScore().compareTo(score2.getScore()); } }

		// Sort the list of matches
		Collections.sort(matches, new ScoreComparator());

		// Return the list of matches (trimmed to the top 10)
		if(matches.size()>10) matches = new ArrayList<MatchScore>(matches.subList(0,10));
		return matches;
	}
	
	/** Retrieves the suggested matches */
	private HashMap<Integer,ArrayList<MatchScore>> getSuggestedMatches(HierarchicalSchemaInfo sourceInfo, HierarchicalSchemaInfo targetInfo)
	{		
		// Retrieve the source and target schemas
		FilteredSchemaInfo sourceFilter = new FilteredSchemaInfo(sourceInfo);
		FilteredSchemaInfo targetFilter = new FilteredSchemaInfo(targetInfo);
		
		// Run matchers on the unassigned mapping cells
		ArrayList<MatchVoter> voters = MatcherManager.getDefaultMatchers();
		voters.add(new MappingMatcher(RepositoryManager.getClient()));
		MatchScores matchScores = MatcherManager.getScores(sourceFilter, targetFilter, voters, new VoteMerger());

		// Generate list of potential matches for each source element
		HashMap<Integer,ArrayList<MatchScore>> matches = new HashMap<Integer,ArrayList<MatchScore>>();
		for(MatchScore score : matchScores.getScores())
		{
			ArrayList<MatchScore> matchList = matches.get(score.getSourceID());
			if(matchList==null)
				matches.put(score.getSourceID(), matchList=new ArrayList<MatchScore>());
			matchList.add(score);
		}
		
		// Sort matches and trim to top 10
		for(Integer sourceID : matches.keySet())
			matches.put(sourceID, sortMatches(matches.get(sourceID)));
		return matches;
	}
	
	/** Generates the filter pane */
	private void generateFilterPane(Composite parent)
	{
		// Constructs the filter pane
		Composite pane = new Composite(parent, SWT.NONE);
		pane.setLayout(new GridLayout(2,false));
		
		// Constructs the selection pane
		Composite selectionPane = new Composite(pane, SWT.NONE);
		selectionPane.setLayout(new GridLayout(1,false));
		BasicWidgets.createTextField(pane, "Source");
		BasicWidgets.createTextField(pane, "Target");		
		
		// Construct the save button
		Button button = BasicWidgets.createButton(pane, "Align", this);
		button.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));		
	}
	
	/** Generates the info pane */
	private void generateInfoPane(Composite parent)
	{
		
	}
	
	/** Generates the alignment pane */
	private void generateAlignmentPane(Composite parent)
	{
		// Constructs the scroll pane
		ScrolledComposite scrollPane = new ScrolledComposite(parent, SWT.BORDER | SWT.V_SCROLL);
		scrollPane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrollPane.setExpandHorizontal(true);
		scrollPane.setExpandVertical(true);
		
		// Retrieve the mapping and source/target schemas
		Mapping mapping = OpenIIManager.getMapping(getElementID());
		Project project = OpenIIManager.getProject(mapping.getProjectId());
		HierarchicalSchemaInfo sourceInfo = new HierarchicalSchemaInfo(OpenIIManager.getSchemaInfo(mapping.getSourceId()),project.getSchemaModel(mapping.getSourceId()));
		HierarchicalSchemaInfo targetInfo = new HierarchicalSchemaInfo(OpenIIManager.getSchemaInfo(mapping.getTargetId()),project.getSchemaModel(mapping.getTargetId()));
		
		// Retrieve the mapping information
		HashMap<Integer,Integer> userMatches = getUserMatches();
		HashMap<Integer,ArrayList<MatchScore>> suggestedMatches = getSuggestedMatches(sourceInfo,targetInfo);

		// Display the alignment pane
		Composite pane = new Composite(scrollPane, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(2,false));
		scrollPane.setContent(pane);
		
		// Display the source elements which need to be aligned
		for(SchemaElement sourceElement : sourceInfo.getHierarchicalElements())
		{
			// Display the source element label
			Label label = BasicWidgets.createLabel(pane, sourceElement.getName());
			GridData labelGridData = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_BEGINNING);
			labelGridData.verticalIndent = 5;
			label.setLayoutData(labelGridData);
			
			// Create the match pane
			MatchPane matchPane = new MatchPane(pane,sourceElement.getId(),targetInfo);
			matchPanes.add(matchPane);
			
			// Determine if there is a user match
			Integer userMatch = userMatches.get(sourceElement.getId());
			
			// Populate the selection box
			matchPane.add("<None>");
			if(userMatch!=null) matchPane.add(targetInfo.getElement(userMatch));
			ArrayList<MatchScore> matches = suggestedMatches.get(sourceElement.getId());
			if(matches!=null)
				for(MatchScore match : matches)
					if(!match.getTargetID().equals(userMatch))
						matchPane.add(targetInfo.getElement(match.getTargetID()));
			
			// Mark the selection
			matchPane.select(userMatch==null ? 0 : 1);
		}
	}
	
	/** Displays the Quick Alignment Editor */
	public void createPartControl(Composite parent)
	{		
		// Constructs the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		generateFilterPane(pane);
		generateAlignmentPane(pane);
		generateInfoPane(pane);
	}

	/** Handles the selection of the save button */
	public void widgetSelected(SelectionEvent e)
	{
		ArrayList<MappingCell> mappingCells = OpenIIManager.getMappingCells(getElementID());

		// Update mapping cells based on changes to the match panes
		for(MatchPane matchPane : matchPanes)
		{
			Integer sourceID = matchPane.getSourceID();
			
			// Remove the mapping cell
			if(matchPane.getElement()==null)
			{
				for(MappingCell mappingCell : new ArrayList<MappingCell>(mappingCells))
					if(mappingCell.getInput()[0].equals(sourceID))
						mappingCells.remove(mappingCell);
			}

			// Adds a mapping cell
			else
			{
				Integer targetID = matchPane.getElement().getId();
				mappingCells.add(MappingCell.createIdentityMappingCell(null, getElementID(), sourceID, targetID, System.getProperty("user.name"), new Date(), ""));
			}
		}		
		
		// Save the mapping cells before closing
		if(OpenIIManager.saveMappingCells(getElementID(), mappingCells))
			closeEditor();
	}
	
	// Unused listener event
	public void widgetDefaultSelected(SelectionEvent e) {}
}