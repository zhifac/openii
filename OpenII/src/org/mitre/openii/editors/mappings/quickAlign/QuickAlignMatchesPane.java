package org.mitre.openii.editors.mappings.quickAlign;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.mappingInfo.MappingInfo;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/** Constructs the Quick Alignment Matches Pane */
public class QuickAlignMatchesPane extends Composite
{
	/** Stores the mapping */
	private MappingInfo mappingInfo = null;
	
	/** Stores a list of all match panes */
	private ArrayList<QuickAlignMatchPane> matchPanes = new ArrayList<QuickAlignMatchPane>();
	
	/** Stores the info pane */
	private QuickAlignInfoPane infoPane = null;
	
	/** Retrieves the user matches */
	private HashMap<Integer,Integer> getUserMatches()
	{
		HashMap<Integer,Integer> matches = new HashMap<Integer,Integer>();
		for(MappingCell mappingCell : OpenIIManager.getMappingCells(mappingInfo.getMapping().getId()))
			if(mappingCell.isValidated())
				matches.put(mappingCell.getElementInputIDs()[0], mappingCell.getOutput());
		return matches;
	}
	
//	/** Sorts the list of matches */
//	private ArrayList<MatchScore> sortMatches(ArrayList<MatchScore> matches)
//	{
//		/** Compares two matches to one another */
//		class ScoreComparator implements Comparator<MatchScore>
//			{ public int compare(MatchScore score1, MatchScore score2)
//				{ return score1.getScore().compareTo(score2.getScore()); } }
//
//		// Sort the list of matches
//		Collections.sort(matches, new ScoreComparator());
//
//		// Return the list of matches (trimmed to the top 10)
//		if(matches.size()>10) matches = new ArrayList<MatchScore>(matches.subList(0,10));
//		return matches;
//	}
//	
//	/** Retrieves the suggested matches */
//	private HashMap<Integer,ArrayList<MatchScore>> getSuggestedMatches(HierarchicalSchemaInfo sourceInfo, HierarchicalSchemaInfo targetInfo)
//	{		
//		// Retrieve the source and target schemas
//		FilteredSchemaInfo sourceFilter = new FilteredSchemaInfo(sourceInfo);
//		FilteredSchemaInfo targetFilter = new FilteredSchemaInfo(targetInfo);
//		
//		// Run matchers on the unassigned mapping cells
//		ArrayList<Matcher> matchers = MatcherManager.getDefaultMatchers();
//		matchers.add(new MappingMatcher());
//		MatchScores matchScores = new MatchGenerator(matchers, new VoteMerger()).getScores(sourceFilter, targetFilter);
//
//		// Generate list of potential matches for each source element
//		HashMap<Integer,ArrayList<MatchScore>> matches = new HashMap<Integer,ArrayList<MatchScore>>();
//		for(MatchScore score : matchScores.getScores())
//		{
//			ArrayList<MatchScore> matchList = matches.get(score.getSourceID());
//			if(matchList==null)
//				matches.put(score.getSourceID(), matchList=new ArrayList<MatchScore>());
//			matchList.add(score);
//		}
//		
//		// Sort matches and trim to top 10
//		for(Integer sourceID : matches.keySet())
//			matches.put(sourceID, sortMatches(matches.get(sourceID)));
//		return matches;
//	}

	/** Resize the match labels (to align with one another) */
	private void alignMatchLabels(HierarchicalSchemaInfo sourceInfo, ArrayList<SchemaElement> sourceElements)
	{
		// Find the maximum length of any source element
		int maxLength = 0;
		for(SchemaElement sourceElement : sourceElements)
		{
			int length = sourceInfo.getDisplayName(sourceElement.getId()).length();
			if(length>maxLength) maxLength = length;
		}

		// Calculate out the size that the match label must be to fit the longest source element
		GC gc = new GC(this);
		int width = (maxLength+2) * gc.getFontMetrics().getAverageCharWidth();
	    gc.dispose();

	    // Resize the match labels to all be aligned 
	    for(QuickAlignMatchPane matchPane : matchPanes)
			matchPane.setLabelWidth(width);
	}
	
	/** Generate the matches pane */
	private void generateMatchesPane(Composite parent, HierarchicalSchemaInfo sourceInfo, HierarchicalSchemaInfo targetInfo, MappingInfo mappingInfo)
	{
		// Generate the scroll pane
		ScrolledComposite scrollPane = new ScrolledComposite(parent, SWT.BORDER | SWT.V_SCROLL);
		scrollPane.setExpandHorizontal(true);
		scrollPane.setExpandVertical(true);
		scrollPane.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Constructs the matches pane
		Composite matchersPane = new Composite(scrollPane, SWT.NONE);
		matchersPane.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		GridLayout layout = new GridLayout(1,false);
		layout.marginHeight = 0; layout.marginWidth = 0;
		layout.horizontalSpacing = 1; layout.verticalSpacing = 1;
		matchersPane.setLayout(layout);
		
		// Retrieve the mapping information
		HashMap<Integer,Integer> userMatches = getUserMatches();
//		HashMap<Integer,ArrayList<MatchScore>> suggestedMatches = getSuggestedMatches(sourceInfo,targetInfo);
		
		// Display the source elements which need to be aligned
		ArrayList<SchemaElement> sourceElements = sourceInfo.getHierarchicalElements();
		for(SchemaElement sourceElement : sourceElements)
		{
//			// Display the source element label
//			Label label = BasicWidgets.createLabel(pane, sourceElement.getName());
//			GridData labelGridData = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_BEGINNING);
//			labelGridData.verticalIndent = 5;
//			label.setLayoutData(labelGridData);
			
			// Create the match pane
			QuickAlignMatchPane matchPane = new QuickAlignMatchPane(matchersPane,sourceElement);
			matchPanes.add(matchPane);
			
			// Determine if there is a user match
			Integer userMatch = userMatches.get(sourceElement.getId());
			
			// Populate the selection box
			matchPane.add("");
			if(userMatch!=null) matchPane.add(targetInfo.getElement(userMatch));
//			ArrayList<MatchScore> matches = suggestedMatches.get(sourceElement.getId());
//			if(matches!=null)
//				for(MatchScore match : matches)
//					if(!match.getTargetID().equals(userMatch))
//						matchPane.add(targetInfo.getElement(match.getTargetID()));
		}

		// Resize the labels to fit the text
		alignMatchLabels(sourceInfo,sourceElements);
		
		// Resize the scroll pane to fit all of the matches
		scrollPane.setContent(matchersPane);
		scrollPane.setMinHeight(computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	}
	
	/** Generates the QuickAlign matches pane */
	QuickAlignMatchesPane(Composite parent, MappingInfo mappingInfo)
	{
		// Generate the main pane
		super(parent, SWT.NONE);
		setLayout(new GridLayout(1,false));
		setLayoutData(new GridData(GridData.FILL_BOTH));

		// Retrieve the mapping and source/target schemas
		this.mappingInfo = mappingInfo;
		Mapping mapping = mappingInfo.getMapping();
		Project project = OpenIIManager.getProject(mapping.getProjectId());
		HierarchicalSchemaInfo sourceInfo = new HierarchicalSchemaInfo(OpenIIManager.getSchemaInfo(mapping.getSourceId()),project.getSchemaModel(mapping.getSourceId()));
		HierarchicalSchemaInfo targetInfo = new HierarchicalSchemaInfo(OpenIIManager.getSchemaInfo(mapping.getTargetId()),project.getSchemaModel(mapping.getTargetId()));
		
		// Generate the matches pane
		generateMatchesPane(this, sourceInfo, targetInfo, mappingInfo);
		
		// Generate the info pane
		infoPane = new QuickAlignInfoPane(this, sourceInfo, targetInfo);
	}
	
	/** Sets the selected match pane */
	void setSelectedPane(QuickAlignMatchPane pane)
		{ infoPane.setSelectedPane(pane); }
}