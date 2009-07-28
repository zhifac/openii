package org.mitre.openii.editors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.ExpandBarWidgets;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.graph.Graph;

/** Constructs the Schema Graph */
public class MappingView extends OpenIIEditor
{	
	/** Generates the schema pane */
	private void generateSchemasPane(ExpandBar bar)
	{		
		// Generate the schemas table
		String[] fields = new String[]{"ID","Name","Model","Side"};
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		for(MappingSchema schema : OpenIIManager.getMapping(elementID).getSchemas())
		{
			Integer id = schema.getId();
			String name = OpenIIManager.getSchema(id).getName();
			String model = schema.getModel();
			Integer side = schema.getSide();
			String sideString = side==null?"None":side.equals(MappingSchema.LEFT)?"Left":side.equals(MappingSchema.RIGHT)?"Right":"None";
			rows.add(new Object[]{id,name,model,sideString});
		}
		ExpandBarWidgets.createTablePane(bar, "Schemas", fields, rows);
	}

	/** Generates a mapping cell pane */
	private void generateMappingCellPane(ExpandBar bar, Graph leftGraph, Graph rightGraph, ArrayList<MappingCell> mappingCells)
	{		
		// Sort the list of mapping cells
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
			for(Integer input : mappingCell.getInput())
				inputs += leftGraph.getElement(input).getName() + " (" + input  + "), ";
			if(inputs.length()>2) inputs = inputs.substring(0,inputs.length()-2);
			String output = rightGraph.getElement(mappingCell.getOutput()).getName() + " (" + mappingCell.getOutput() + ")";
			
			// Gather general mapping cell information
			Integer id = mappingCell.getId();
			String author = mappingCell.getAuthor();
			Date modifiedDate = mappingCell.getModificationDate();
			String notes = mappingCell.getNotes();
			
			// Gets the function/score based on if mapping cell is validated
			String functionScore = mappingCell.getScore().toString();
			if(mappingCell.getValidated())
			{
				functionScore = mappingCell.getFunctionClass();
				if(functionScore!=null) functionScore = functionScore.replaceAll(".*\\.", "");
			}
			
			// Display mapping cell
			rows.add(new Object[]{id,functionScore,inputs,output,author,modifiedDate,notes});
		}

		// Generates the mapping cell pane
		String leftSchema = leftGraph.getSchema().getName();
		String rightSchema = rightGraph.getSchema().getName();		
		ExpandBarWidgets.createTablePane(bar, "Mapping Cells - '"+leftSchema+"' to '"+rightSchema+"'", fields, rows);
	}
	
	/** Generates the mapping cell panes */
	private void generateMappingCellPanes(ExpandBar bar)
	{
		// Retrieve the mapping schemas and mapping cells
		MappingSchema schemas[] = OpenIIManager.getMapping(elementID).getSchemas();
		ArrayList<MappingCell> allMappingCells = OpenIIManager.getMappingCells(elementID);

		// Retrieve the various schema graphs
		ArrayList<Graph> graphs = new ArrayList<Graph>();
		for(MappingSchema schema : schemas)
			graphs.add(OpenIIManager.getGraph(schema.getId()));
		
		// Generate a mapping cell pane for each pair of schemas containing mapping cells
		for(Graph leftGraph : graphs)
			for(Graph rightGraph : graphs)
			{
				// Don't proceed if the left and right schemas are the same
				if(leftGraph.getSchema().equals(rightGraph.getSchema())) continue;
				
				// Gather up the list of mappings cells for the schema pair
				ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
				MappingCellLoop: for(MappingCell mappingCell : allMappingCells)
				{
					for(Integer input : mappingCell.getInput())
						if(!leftGraph.containsElement(input)) continue MappingCellLoop;
					if(!rightGraph.containsElement(mappingCell.getOutput())) continue;
					mappingCells.add(mappingCell);
				}
				
				// Display the mapping cells for the schema pair
				if(mappingCells.size()>0)
					generateMappingCellPane(bar, leftGraph, rightGraph, mappingCells);
			}
	}
	
	/** Displays the Schema Graph */
	public void createPartControl(Composite parent)
	{
		// Retrieve the mapping and mapping schemas
		Mapping mapping = OpenIIManager.getMapping(elementID);
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>(Arrays.asList(mapping.getSchemaIDs()));
		Collections.sort(schemaIDs);
			
		// Create the expand bar
		ExpandBar bar = new ExpandBar(parent, SWT.V_SCROLL);
		bar.setSpacing(8);

		// Creates the properties pane
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("Name:"+mapping.getName());
		attributes.add("Author:"+mapping.getAuthor());
		attributes.add("Description:"+mapping.getDescription());
		ExpandBarWidgets.createPropertiesPane(bar, "Mapping Properties", attributes);
		
		// Generate panes for showing the mapping schemas and mapping cells
		generateSchemasPane(bar);
		generateMappingCellPanes(bar);
	}
}