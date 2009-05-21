package org.mitre.openii.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.dialogs.DialogComponents;
import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.model.graph.Graph;

/** Constructs the Schema Graph */
public class SchemaView extends OpenIIEditor
{
	/** Returns the list of schema elements for the specified element type */
	private ArrayList<SchemaElement> getElements(Graph graph, Class<?> type)
	{
		// Class for sorting schema elements
		class ElementComparator implements Comparator<SchemaElement>
		{
			public int compare(SchemaElement element1, SchemaElement element2)
				{ return element1.getId().compareTo(element2.getId()); }			
		}
		
		// Returns the list of schema elements
		ArrayList<SchemaElement> elements = graph.getElements(type);
		Collections.sort(elements,new ElementComparator());
		return elements;
	}
	
	/** Creates a property field */
	private void createField(Composite parent, String name, String value)
	{
		Text field = DialogComponents.createTextField(parent,name);		
		field.setText(value);
		field.setEditable(false);
		field.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
	}
	
	/** Generate the properties pane */
	private void generatePropertiesPane(ExpandBar bar)
	{
		// Construct the pane for showing the info for the selected importer
		Composite pane = new Composite(bar, SWT.NONE);
		pane.setLayout(new GridLayout(2,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		pane.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		
		// Generate the property fields to be displayed by the info pane
		Schema schema = OpenIIManager.getSchema(elementID);
		createField(pane,"Name",schema.getName());
		createField(pane,"Author",schema.getAuthor());
		createField(pane,"Source",schema.getSource());
		createField(pane,"Type",schema.getType());
		createField(pane,"Description",schema.getDescription());
		
		// Create the Expand Item containing the properties
		createExpandItem(bar,"Schema Properties",pane);
	}
	
	/** Generates a table */
	private Table createTable(Composite parent, String fields[])
	{
		// Generate the table
		Table table = new Table(parent, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(data);
		
		// Specify the field names
		for(String field : fields)
			new TableColumn(table, SWT.NONE).setText(field);
		
		// Return the table
		return table;
	}
	
	/** Generates a table item */
	private void createTableItem(Table table, Object values[])
	{
		TableItem item = new TableItem(table, SWT.NONE);
		for(int i=0; i<values.length; i++)
			item.setText(i,values[i]==null ? "" : values[i].toString());
	}
	
	/** Generates an expand item */
	private void createExpandItem(ExpandBar bar, String label, Composite item)
	{
		// Determine which height to use for sizing the expand item
		Integer height = item.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		if(height>250) height = 250;
		
		// Generate the expand item
		ExpandItem expandItem = new ExpandItem(bar, SWT.NONE);
		expandItem.setText(label);
		expandItem.setHeight(height);
		expandItem.setControl(item);
		expandItem.setImage(OpenIIActivator.getImage("SchemaElement.jpg"));
		expandItem.setExpanded(true);
	}
	
	/** Generate the schema element panes */
	private void generateElementPanes(ExpandBar bar)
	{
		// Get the schema graph
		Graph graph = OpenIIManager.getGraph(elementID);

		// Generate the entities table
		Table table = createTable(bar,new String[]{"ID","Name","Description"});
		for(SchemaElement element : getElements(graph,Entity.class))
		{
			Entity entity = (Entity)element;
			Integer id = entity.getId();
			String name = entity.getName();
			String description = entity.getDescription();
			createTableItem(table,new Object[]{id,name,description});
		}
		for(int i=0; i<table.getColumnCount(); i++) table.getColumn(i).pack();
		createExpandItem(bar,"Entities",table);

		// Generate the attributes table
		table = createTable(bar,new String[]{"ID","Name","Entity","Domain","Min","Max","IsKey","Description"});
		for(SchemaElement element : getElements(graph,Attribute.class))
		{
			Attribute attribute = (Attribute)element;
			Integer id = attribute.getId();
			String name = attribute.getName();
			String entity = graph.getElement(attribute.getEntityID()).getName() + " (" + attribute.getEntityID() + ")";
			String domain = graph.getElement(attribute.getDomainID()).getName() + " (" + attribute.getDomainID() + ")";
			Integer min = attribute.getMin();
			Integer max = attribute.getMax();
			Boolean isKey = attribute.isKey();
			String description = attribute.getDescription();
			createTableItem(table,new Object[]{id,name,entity,domain,min,max,isKey,description});
		}
		for(int i=0; i<table.getColumnCount(); i++) table.getColumn(i).pack();
		createExpandItem(bar,"Attributes",table);

		// Generate the domains table
		table = createTable(bar,new String[]{"ID","Name","Description"});
		for(SchemaElement element : getElements(graph,Domain.class))
		{
			Domain domain = (Domain)element;
			Integer id = domain.getId();
			String name = domain.getName();
			String description = domain.getDescription();
			createTableItem(table,new Object[]{id,name,description});
		}
		for(int i=0; i<table.getColumnCount(); i++) table.getColumn(i).pack();
		createExpandItem(bar,"Domains",table);		

		// Generate the domain values table
		table = createTable(bar,new String[]{"ID","Name","Domain","Description"});
		for(SchemaElement element : getElements(graph,DomainValue.class))
		{
			DomainValue domainValue = (DomainValue)element;
			Integer id = domainValue.getId();
			String name = domainValue.getName();
			String domain = graph.getElement(domainValue.getDomainID()).getName() + " (" + domainValue.getDomainID() + ")";
			String description = domainValue.getDescription();
			createTableItem(table,new Object[]{id,name,domain,description});
		}
		for(int i=0; i<table.getColumnCount(); i++) table.getColumn(i).pack();
		createExpandItem(bar,"Domain Values",table);		

		// Generate the relationships table
		table = createTable(bar,new String[]{"ID","Name","Left Element","Left Min","Left Max","Right Element","Right Min","Right Max","Description"});
		for(SchemaElement element : getElements(graph,Relationship.class))
		{
			Relationship relationship = (Relationship)element;
			Integer id = relationship.getId();
			String name = relationship.getName();
			String left = graph.getElement(relationship.getLeftID()).getName() + " (" + relationship.getLeftID() + ")";
			Integer leftMin = relationship.getLeftMin();
			Integer leftMax = relationship.getLeftMax();
			String right = graph.getElement(relationship.getRightID()).getName() + " (" + relationship.getRightID() + ")";
			Integer rightMin = relationship.getRightMin();
			Integer rightMax = relationship.getRightMax();
			String description = relationship.getDescription();
			createTableItem(table,new Object[]{id,name,left,leftMin,leftMax,right,rightMin,rightMax,description});
		}
		for(int i=0; i<table.getColumnCount(); i++) table.getColumn(i).pack();
		createExpandItem(bar,"Relationships",table);		

		// Generate the containments table
		table = createTable(bar,new String[]{"ID","Name","Parent Element","Child Element","Min","Max","Description"});
		for(SchemaElement element : getElements(graph,Containment.class))
		{
			Containment containment = (Containment)element;
			Integer id = containment.getId();
			String name = containment.getName();
			String parentElement = containment.getParentID()==null ? "" : graph.getElement(containment.getParentID()).getName() + " (" + containment.getParentID() + ")";
			String childElement = graph.getElement(containment.getChildID()).getName() + " (" + containment.getParentID() + ")";
			Integer min = containment.getMin();
			Integer max = containment.getMax();
			String description = containment.getDescription();
			createTableItem(table,new Object[]{id,name,parentElement,childElement,min,max,description});
		}
		for(int i=0; i<table.getColumnCount(); i++) table.getColumn(i).pack();
		createExpandItem(bar,"Containments",table);		

		// Generate the subtypes table
		table = createTable(bar,new String[]{"ID","Name","Parent Element","Child Element","Description"});
		for(SchemaElement element : getElements(graph,Subtype.class))
		{
			Subtype subtype = (Subtype)element;
			Integer id = subtype.getId();
			String name = subtype.getName();
			String parentElement = graph.getElement(subtype.getParentID()).getName() + " (" + subtype.getParentID() + ")";
			String childElement = graph.getElement(subtype.getChildID()).getName() + " (" + subtype.getParentID() + ")";
			String description = subtype.getDescription();
			createTableItem(table,new Object[]{id,name,parentElement,childElement,description});
		}
		for(int i=0; i<table.getColumnCount(); i++) table.getColumn(i).pack();
		createExpandItem(bar,"Subtypes",table);		

		// Generate the aliases table
		table = createTable(bar,new String[]{"ID","Name","Aliased Element"});
		for(SchemaElement element : getElements(graph,Alias.class))
		{
			Alias alias = (Alias)element;
			Integer id = alias.getId();
			String name = alias.getName();
			String aliasedElement = graph.getElement(alias.getElementID()).getName() + " (" + alias.getElementID() + ")";
			createTableItem(table,new Object[]{id,name,aliasedElement});
		}
		for(int i=0; i<table.getColumnCount(); i++) table.getColumn(i).pack();
		createExpandItem(bar,"Aliases",table);
	}
	
	/** Displays the Schema Graph */
	public void createPartControl(Composite parent)
	{		
		// Create the expand bar
		ExpandBar bar = new ExpandBar(parent, SWT.V_SCROLL);
		bar.setSpacing(8);

		// Generate the various panes in the expand bar
		generatePropertiesPane(bar);
		generateElementPanes(bar);
	}
}