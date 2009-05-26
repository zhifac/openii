package org.mitre.openii.editors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.jar.Attributes;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.ExpandBarWidgets;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

/** Constructs the Schema Graph */
public class MappingView extends OpenIIEditor implements ISelectionChangedListener
{	
	/** Stores the schemas associated with the mapping */
	private ArrayList<Schema> schemas = new ArrayList<Schema>();
	
	// Stores the various view fields
	ComboViewer schema1Selector = null;
	ComboViewer schema2Selector = null;
	ExpandItem mappingCellExpandItem = null;
	Composite mappingCellPane = null;
	
	/** Generates the schema pane */
	private void generateSchemasPane(ExpandBar bar)
	{		
		// Generate the schemas table
		String[] fields = new String[]{"ID","Name","Author","Source","Type","Locked","Description"};
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		for(Schema schema : schemas)
		{
			Integer id = schema.getId();
			String name = schema.getName();
			String author = schema.getAuthor();
			String source = schema.getSource();
			String type = schema.getType();
			Boolean locked = schema.getLocked();
			String description = schema.getDescription();
			rows.add(new Object[]{id,name,author,source,type,locked,description});
		}
		ExpandBarWidgets.createTablePane(bar, "Schemas", fields, rows);
	}

	/** Generates a schema selector */
	private ComboViewer generateSchemaSelector(Composite parent, String label)
	{
		// Construct the composite pane
		Composite pane = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2,false);
		layout.marginHeight = 1; layout.marginWidth = 4;
		pane.setLayout(layout);
		
		// Create the combo label
		BasicWidgets.createLabel(pane, label);
		
		// Create the combo box
		ComboViewer schemaCombo = new ComboViewer(pane, SWT.NONE);
		for(Schema schema : schemas)
			schemaCombo.add(schema);
		schemaCombo.addSelectionChangedListener(this);
		return schemaCombo;
	}
	
	/** Generates the mapping cells pane */
	private void generateMappingCellPane(ExpandBar bar)
	{
		// Construct the composite pane
		mappingCellPane = new Composite(bar, SWT.NONE);
		mappingCellPane.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		GridLayout layout = new GridLayout(1,false);
		layout.marginHeight = 0; layout.marginWidth = 0;
		mappingCellPane.setLayout(layout);
		mappingCellPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Construct the selection pane
		Composite selectionPane = new Composite(mappingCellPane, SWT.NONE);
		selectionPane.setLayout(new GridLayout(2,true));
		selectionPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		schema1Selector = generateSchemaSelector(selectionPane,"First Schema");
		schema2Selector = generateSchemaSelector(selectionPane,"Second Schema");

		// Initialize the selected schemas
		schema1Selector.getCombo().select(0);
		schema2Selector.getCombo().select(1);
		
		// Create the mapping cells pane
		mappingCellExpandItem = ExpandBarWidgets.createExpandItem(bar, "Mapping Cells", mappingCellPane);
	}
	
	/** Generates the mapping cell table */
	private void generateMappingCellTable()
	{
		// Retrieve the currently selected schemas
		Schema schema1 = (Schema)((StructuredSelection)schema1Selector.getSelection()).getFirstElement();
		Schema schema2 = (Schema)((StructuredSelection)schema2Selector.getSelection()).getFirstElement();
		
		// Display the mapping cells associated with the selected schemas
		if(schema1!=null && schema2!=null)
		{
			// Generate a table to look up elements in schema 1
			HashMap<Integer,SchemaElement> schema1Elements = new HashMap<Integer,SchemaElement>();
			for(SchemaElement element : OpenIIManager.getGraph(schema1.getId()).getElements(null))
				schema1Elements.put(element.getId(),element);

			// Generate a table to look up elements in schema 2
			HashMap<Integer,SchemaElement> schema2Elements = new HashMap<Integer,SchemaElement>();
			for(SchemaElement element : OpenIIManager.getGraph(schema2.getId()).getElements(null))
				schema2Elements.put(element.getId(),element);
			
			// Get list of mapping cells to display
			ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
			for(MappingCell mappingCell : OpenIIManager.getMappingCells(elementID))
			{
				// Determine if mapping cell runs between selected schemas
				Integer element1 = mappingCell.getElement1();
				Integer element2 = mappingCell.getElement2();
				if(!schema1Elements.containsKey(element1) || !schema2Elements.containsKey(element2))
				{
					if(!schema1Elements.containsKey(element2) || !schema2Elements.containsKey(element1)) continue;
					mappingCell.setElement1(element2); mappingCell.setElement2(element1);					
				}
				mappingCells.add(mappingCell);
			}
			
			// Sort the list of mapping cells
			class MappingCellComparator implements Comparator<MappingCell>
			{
				public int compare(MappingCell mappingCell1, MappingCell mappingCell2)
					{ return mappingCell2.getScore().compareTo(mappingCell1.getScore()); }				
			}
			Collections.sort(mappingCells, new MappingCellComparator());			
			
			// Merge together the list of schema elements
			HashMap<Integer,SchemaElement> elements = schema1Elements;
			elements.putAll(schema2Elements);
			
			// Construct the table pane
			String[] fields = new String[]{"ID","Score","Element1","Element2","Author","Modified","Transform","Validated","Notes"};
			ArrayList<Object[]> rows = new ArrayList<Object[]>();
			for(MappingCell mappingCell : mappingCells)
			{
				// Gather mapping cell information
				Integer id = mappingCell.getId();
				Double score = mappingCell.getScore();
				String element1 = elements.get(mappingCell.getElement1()).getName() + " (" + mappingCell.getElement1() + ")";
				String element2 = elements.get(mappingCell.getElement2()).getName() + " (" + mappingCell.getElement2() + ")";
				String author = mappingCell.getAuthor();
				Date modifiedDate = mappingCell.getModificationDate();
				String transform = mappingCell.getTransform();
				Boolean validated = mappingCell.getValidated();
				String notes = mappingCell.getNotes();
				
				// Display mapping cell
				rows.add(new Object[]{id,score,element1,element2,author,modifiedDate,transform,validated,notes});
			}
			
			// Display the mapping cell table
			BasicWidgets.createTable(mappingCellPane, fields, rows);
		}
		
		// Resize the table to fit all mapping cells
		Integer height = mappingCellPane.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		mappingCellExpandItem.setHeight(height);
	}
	
	/** Displays the Schema Graph */
	public void createPartControl(Composite parent)
	{
		// Retrieve the mapping and mapping schemas
		Mapping mapping = OpenIIManager.getMapping(elementID);
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>(Arrays.asList(mapping.getSchemas()));
		Collections.sort(schemaIDs);
		for(Integer schemaID : schemaIDs) schemas.add(OpenIIManager.getSchema(schemaID));
			
		// Create the expand bar
		ExpandBar bar = new ExpandBar(parent, SWT.V_SCROLL);
		bar.setSpacing(8);

		// Creates the properties pane
		Attributes attributes = new Attributes();
		attributes.putValue("Name",mapping.getName());
		attributes.putValue("Author",mapping.getAuthor());
		attributes.putValue("Description",mapping.getDescription());
		ExpandBarWidgets.createPropertiesPane(bar, attributes);
		
		// Generate a pane showing the mapping schemas
		generateSchemasPane(bar);
		
		// Generate a pane showing the mapping cells
		generateMappingCellPane(bar);
		generateMappingCellTable();
	}

	/** Handles the changing of one of the schema selectors */
	public void selectionChanged(SelectionChangedEvent e)
	{
		// Make sure that both sides both display same schema
		Integer schema1Index = schema1Selector.getCombo().getSelectionIndex();
		Integer schema2Index = schema2Selector.getCombo().getSelectionIndex();
		if(schema1Index.equals(schema2Index))
		{
			if(e.getSource().equals(schema1Selector))
				schema2Selector.getCombo().select(schema2Index==0 ? 1 : 0);
			else schema1Selector.getCombo().select(schema1Index==0 ? 1 : 0);
		}		
		
		// Regenerate the mapping cell table
		mappingCellPane.getChildren()[1].dispose();
		generateMappingCellTable();
		mappingCellPane.layout(true,true);
	}
}