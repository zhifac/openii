package org.mitre.openii.editors.mappings.quickAlign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		for(ArrayList<MappingCell> mappingCells : matches.values())
		{
			Collections.sort(mappingCells, new ScoreComparator());
			if(mappingCells.size()>10) mappingCells = new ArrayList<MappingCell>(mappingCells.subList(0,10));
		}
		
		return matches;
	}

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
		HashMap<Integer,ArrayList<MappingCell>> matches = getMatches();
		
		// Display the source elements which need to be aligned
		ArrayList<SchemaElement> sourceElements = sourceInfo.getHierarchicalElements();
		for(SchemaElement sourceElement : sourceElements)
		{
			// Create the match pane
			QuickAlignMatchPane matchPane = new QuickAlignMatchPane(matchersPane,sourceElement);
			matchPane.updateMatches(matches.get(sourceElement.getId()), targetInfo);
			matchPanes.add(matchPane);
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