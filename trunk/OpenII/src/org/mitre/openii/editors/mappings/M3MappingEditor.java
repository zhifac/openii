package org.mitre.openii.editors.mappings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.ExpandBarWidgets;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingCellInput;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/** Constructs the Mapping View */
public class M3MappingEditor extends OpenIIEditor
{	
	/** Generates the mappings panes */
	private void generateMappingsPanes(ExpandBar bar)
	{
		Mapping mapping = OpenIIManager.getMapping(getElementID());
		
		// Retrieve the source and target schemas
		SchemaInfo sourceSchema = OpenIIManager.getSchemaInfo(mapping.getSourceId());
		SchemaInfo targetSchema = OpenIIManager.getSchemaInfo(mapping.getTargetId());
		
		// Sort the list of mapping cells
		ArrayList<MappingCell> mappingCells = OpenIIManager.getMappingCells(mapping.getId());
		class MappingCellComparator implements Comparator<MappingCell>
		{
			public int compare(MappingCell mappingCell1, MappingCell mappingCell2)
				{ return mappingCell2.getScore().compareTo(mappingCell1.getScore()); }				
		}
		Collections.sort(mappingCells, new MappingCellComparator());

		// Generate the schemas table
		String[] fields = new String[]{"ID","Function/Score","Inputs","Output","Author","Modified","Notes"};
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		for(MappingCell mappingCell : mappingCells)
		{
			// Gather mapping cell input and output information
			String inputs = "";
			for(MappingCellInput input : mappingCell.getInputs())
			{
				if(input.isConstant()) inputs += input.getConstant();
				else inputs += sourceSchema.getElement(input.getElementID()).getName() + " (" + input.getElementID() + "), ";
			}
			String output = targetSchema.getElement(mappingCell.getOutput()).getName() + " (" + mappingCell.getOutput() + ")";
			
			// Gather general mapping cell information
			Integer id = mappingCell.getId();
			String author = mappingCell.getAuthor();
			Date modifiedDate = mappingCell.getModificationDate();
			String notes = mappingCell.getNotes();
			
			// Gets the function/score from the mapping cell
			String functionScore = mappingCell.getScore().toString();
			if(mappingCell.getFunctionID()!=null)
				functionScore = OpenIIManager.getFunction(mappingCell.getFunctionID()).getName();
			
			// Display mapping cell
			rows.add(new Object[]{id,functionScore,inputs,output,author,modifiedDate,notes});
		}

		// Generates the mapping cell pane
		String sourceName = sourceSchema.getSchema().getName();
		String targetName = targetSchema.getSchema().getName();		
		ExpandBarWidgets.createTablePane(bar, "Mapping Cells - '"+sourceName+"' to '"+targetName+"'", fields, rows);
	}
	
	/** Displays the Mapping View */
	public void createPartControl(Composite parent)
	{
		// Create the expand bar
		ExpandBar bar = new ExpandBar(parent, SWT.V_SCROLL);
		bar.setSpacing(8);

		// Generate panes for showing the mapping schemas and mapping cells
		generateMappingsPanes(bar);
	}
}