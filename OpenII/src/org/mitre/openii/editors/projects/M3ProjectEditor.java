package org.mitre.openii.editors.projects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.ExpandBarWidgets;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/** Constructs the Mapping View */
public class M3ProjectEditor extends OpenIIEditor
{	
	/** Generates the schema pane */
	private void generateSchemasPane(ExpandBar bar)
	{		
		// Generate the schemas table
		String[] fields = new String[]{"ID","Name","Model"};
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		for(ProjectSchema schema : OpenIIManager.getProject(elementID).getSchemas())
		{
			Integer id = schema.getId();
			String name = OpenIIManager.getSchema(id).getName();
			String model = schema.getModel();
			rows.add(new Object[]{id,name,model});
		}
		ExpandBarWidgets.createTablePane(bar, "Schemas", fields, rows);
	}
	
	/** Generates the mappings panes */
	private void generateMappingsPanes(ExpandBar bar)
	{
		// Retrieve information about all schemas in the mapping
		HashMap<Integer,SchemaInfo> schemaInfoList = new HashMap<Integer,SchemaInfo>();
		for(Integer schemaID : OpenIIManager.getProject(elementID).getSchemaIDs())
			schemaInfoList.put(schemaID, OpenIIManager.getSchemaInfo(schemaID));
		
		// Cycle through all mappings
		for(Mapping mapping : OpenIIManager.getMappings(elementID))
		{
			// Retrieve the source and target schemas
			SchemaInfo sourceSchema = schemaInfoList.get(mapping.getSourceId());
			SchemaInfo targetSchema = schemaInfoList.get(mapping.getTargetId());
			
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
				for(Integer input : mappingCell.getInput())
					inputs += sourceSchema.getElement(input).getName() + " (" + input  + "), ";
				if(inputs.length()>2) inputs = inputs.substring(0,inputs.length()-2);
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
	}
	
	/** Displays the Mapping View */
	public void createPartControl(Composite parent)
	{
		// Retrieve the project and project schemas
		Project project = OpenIIManager.getProject(elementID);
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>(Arrays.asList(project.getSchemaIDs()));
		Collections.sort(schemaIDs);
			
		// Create the expand bar
		ExpandBar bar = new ExpandBar(parent, SWT.V_SCROLL);
		bar.setSpacing(8);

		// Creates the properties pane
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("Name:"+project.getName()+" ("+project.getId()+")");
		attributes.add("Author:"+project.getAuthor());
		attributes.add("Description:"+project.getDescription());
		ExpandBarWidgets.createPropertiesPane(bar, "Project Properties", attributes);
		
		// Generate panes for showing the mapping schemas and mapping cells
		generateSchemasPane(bar);
		generateMappingsPanes(bar);
	}
}