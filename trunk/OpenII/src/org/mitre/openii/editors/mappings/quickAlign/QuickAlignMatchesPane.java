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
	private HashMap<Integer,ArrayList<MappingCell>> getMatches()
	{
		// Retrieve the matches
		HashMap<Integer,ArrayList<MappingCell>> matches = new HashMap<Integer,ArrayList<MappingCell>>();
		for(MappingCell mappingCell : mappingInfo.getMappingCells().get())
			for(Integer inputID : mappingCell.getElementInputIDs())
			{
				ArrayList<MappingCell> mappingCells = matches.get(inputID);
				if(mappingCells==null) matches.put(inputID, mappingCells=new ArrayList<MappingCell>());
				mappingCells.add(mappingCell);
			}
		
		// Compares two matches to one another
		class ScoreComparator implements Comparator<MappingCell>
			{ public int compare(MappingCell mappingCell1, MappingCell mappingCell2)
				{ return mappingCell2.getScore().compareTo(mappingCell1.getScore()); } }

		// Sort the list of matches and trim to the top 10
		for(Integer key : matches.keySet())
		{
			ArrayList<MappingCell> mappingCells = matches.get(key);
			Collections.sort(mappingCells, new ScoreComparator());
			if(mappingCells.size()>10) mappingCells = new ArrayList<MappingCell>(mappingCells.subList(0,10));
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
		
		// Retrieve the mapping information
		HashMap<Integer,ArrayList<MappingCell>> matches = getMatches();
		
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
	
	/** Handles the recalculation of matches */
	void recalculateMatches()
	{
		// Retrieve the source and target schemas
		FilteredSchemaInfo sourceFilter = filterPane.getSourceSchema();
		FilteredSchemaInfo targetFilter = filterPane.getTargetSchema();
		
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
		HashMap<Integer,ArrayList<MappingCell>> matches = getMatches();
		for(QuickAlignMatchPane matchPane : matchPanes)
		{
			ArrayList<MappingCell> elementMatches = matches.get(matchPane.getSourceElement());
			matchPane.updateMatches(elementMatches, filterPane.getTargetSchema());
		}
	}
	
	/** Sets the selected match pane */
	void setSelectedPane(QuickAlignMatchPane pane)
		{ if(infoPane!=null) infoPane.setSelectedPane(pane); }
}