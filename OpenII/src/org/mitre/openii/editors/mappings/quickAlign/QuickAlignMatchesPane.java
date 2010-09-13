package org.mitre.openii.editors.mappings.quickAlign;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.mitre.harmony.matchers.MatchGenerator;
import org.mitre.harmony.matchers.MatchScore;
import org.mitre.harmony.matchers.MatchScores;
import org.mitre.harmony.matchers.matchers.Matcher;
import org.mitre.harmony.matchers.mergers.VoteMerger;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.mappingInfo.MappingCellHash;
import org.mitre.schemastore.model.mappingInfo.MappingInfo;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;

/** Constructs the Quick Alignment Matches Pane */
public class QuickAlignMatchesPane extends Composite
{
	/** Stores reference to the Filter pane */
	private QuickAlignFilterPane filterPane = null;
	
	/** Stores the mapping */
	private MappingInfo mappingInfo = null;
	
	/** Stores a list of all match panes */
	private ArrayList<QuickAlignMatchPane> matchPanes = new ArrayList<QuickAlignMatchPane>();
	
	/** Stores the info pane */
	private QuickAlignInfoPane infoPane = null;
	
	/** Generates a listing of matches hashed by input element */
	private HashMap<Integer,ArrayList<QuickAlignMatch>> getMatches()
	{
		// Retrieve the matches
		HashMap<Integer,ArrayList<QuickAlignMatch>> matches = new HashMap<Integer,ArrayList<QuickAlignMatch>>();
		for(MappingCell mappingCell : mappingInfo.getMappingCells().get())
			for(Integer inputID : mappingCell.getElementInputIDs())
			{
				ArrayList<QuickAlignMatch> elementMatches = matches.get(inputID);
				if(elementMatches==null) matches.put(inputID, elementMatches=new ArrayList<QuickAlignMatch>());
				elementMatches.add(new QuickAlignMatch(mappingCell,filterPane.getTargetSchema()));
			}
		
		// Compares two matches to one another
		class ScoreComparator implements Comparator<QuickAlignMatch>
			{ public int compare(QuickAlignMatch match1, QuickAlignMatch match2)
				{ return match2.getMappingCell().getScore().compareTo(match1.getMappingCell().getScore()); } }

		// Sort the list of matches and trim to the top 10
		for(Integer key : matches.keySet())
		{
			ArrayList<QuickAlignMatch> mappingCells = matches.get(key);
			Collections.sort(mappingCells, new ScoreComparator());
			if(mappingCells.size()>10) mappingCells = new ArrayList<QuickAlignMatch>(mappingCells.subList(0,10));
			matches.put(key, mappingCells);
		}
		
		return matches;
	}
	
	/** Resize the match labels (to align with one another) */
	private void alignMatchLabels(ArrayList<SchemaElement> sourceElements)
	{
		// Find the maximum length of any source element
		int maxLength = 0;
		for(SchemaElement sourceElement : sourceElements)
		{
			int length = filterPane.getSourceSchema().getDisplayName(sourceElement.getId()).length();
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
	private void generateMatchesPane(Composite parent)
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
		
		// Retrieve the matches
		HashMap<Integer,ArrayList<QuickAlignMatch>> matches = getMatches();
		
		// Display the source elements which need to be aligned
		ArrayList<SchemaElement> sourceElements = filterPane.getSourceSchema().getHierarchicalElements();
		for(SchemaElement sourceElement : sourceElements)
		{
			// Create the match pane
			QuickAlignMatchPane matchPane = new QuickAlignMatchPane(matchersPane,sourceElement);
			matchPane.updateMatches(matches.get(sourceElement.getId()), filterPane.getTargetSchema());
			matchPanes.add(matchPane);
		}

		// Resize the labels to fit the text
		alignMatchLabels(sourceElements);
		
		// Resize the scroll pane to fit all of the matches
		scrollPane.setContent(matchersPane);
		scrollPane.setMinHeight(computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	}
	
	/** Generates the QuickAlign matches pane */
	QuickAlignMatchesPane(Composite parent, MappingInfo mappingInfo, QuickAlignFilterPane filterPane)
	{
		// Generate the main pane
		super(parent, SWT.NONE);
		setLayout(new GridLayout(1,false));
		setLayoutData(new GridData(GridData.FILL_BOTH));

		// Retrieve the mapping and source/target schemas
		this.filterPane = filterPane;
		this.mappingInfo = mappingInfo;
		
		// Generate the matches pane
		generateMatchesPane(this);
		
		// Generate the info pane
		infoPane = new QuickAlignInfoPane(this, filterPane.getSourceSchema(), filterPane.getTargetSchema());
	}
	
	/** Returns the mapping info */
	MappingInfo getMappingInfo()
		{ return mappingInfo; }
	
	/** Handles the recalculation of matches */
	void recalculateMatches()
	{
		// Retrieve the source and target schemas
		FilteredSchemaInfo sourceFilter = filterPane.getSourceSchema();
		FilteredSchemaInfo targetFilter = filterPane.getTargetSchema();
		
		// Hide elements which already have a selection
		ArrayList<Integer> hiddenElements = new ArrayList<Integer>();
		for(QuickAlignMatchPane matchPane : matchPanes)
			if(matchPane.getTargetMatch()!=null)
				hiddenElements.add(matchPane.getSourceElement().getId());
		sourceFilter.setHiddenElements(hiddenElements);
		
		// Run matchers on the unassigned mapping cells
		ArrayList<Matcher> matchers = filterPane.getMatchers();
		MatchScores matchScores = new MatchGenerator(matchers, new VoteMerger()).getScores(sourceFilter, targetFilter);

		// Remove all potential matches
		MappingCellHash mappingCells = mappingInfo.getMappingCells();
		for(MappingCell mappingCell : mappingCells.get())
			if(!mappingCell.isValidated())
				mappingCells.delete(mappingCell);
		
		// Generate list of potential matches for each source element
		for(MatchScore score : matchScores.getScores())
		{
			Integer mappingID = mappingInfo.getMapping().getId();
			Integer sourceId = score.getSourceID();
			Integer targetId = score.getTargetID();
			Date date = Calendar.getInstance().getTime();
			MappingCell mappingCell = MappingCell.createProposedMappingCell(null, mappingID, sourceId, targetId, score.getScore(), "Quick Align", date, null);
			mappingInfo.getMappingCells().set(mappingCell);
		}
		
		// Update the match panes
		HashMap<Integer,ArrayList<QuickAlignMatch>> matches = getMatches();
		for(QuickAlignMatchPane matchPane : matchPanes)
		{
			ArrayList<QuickAlignMatch> elementMatches = matches.get(matchPane.getSourceElement());
			matchPane.updateMatches(elementMatches, filterPane.getTargetSchema());
		}
	}
	
	/** Handles the saving of matches */
	void saveMatches()
	{
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		for(QuickAlignMatchPane matchPane : matchPanes)
		{
			// Saves the selected match
			QuickAlignMatch match = matchPane.getTargetMatch();
			if(match!=null)
			{
				MappingCell mappingCell = match.getMappingCell();
				if(!mappingCell.isValidated())
				{
					Integer id = mappingCell.getId();
					Integer mappingID = mappingCell.getMappingId();
					Integer input = mappingCell.getElementInputIDs()[0];
					Integer output = mappingCell.getOutput();
					String author = System.getProperty("user.name");
					Date date = Calendar.getInstance().getTime();
					String notes = mappingCell.getNotes();
					mappingCell = MappingCell.createIdentityMappingCell(id, mappingID, input, output, author, date, notes);
				}
				mappingCells.add(mappingCell);
			}

			// If no selected matches, save the potential matches
			else for(QuickAlignMatch potentialMatch : matchPane.getPotentialMatches())
			{
				MappingCell mappingCell = potentialMatch.getMappingCell();
				if(mappingCell.isValidated())
					{ mappingCell.setFunctionID(null); mappingCell.setScore(0.95); }
				mappingCells.add(mappingCell);
			}
		}
		
		// Save the mapping cells
		OpenIIManager.saveMappingCells(mappingInfo.getMapping().getId(), mappingCells);
	}
	
	/** Sets the selected match pane */
	void setSelectedPane(QuickAlignMatchPane pane)
		{ if(infoPane!=null) infoPane.setSelectedPane(pane); }
}