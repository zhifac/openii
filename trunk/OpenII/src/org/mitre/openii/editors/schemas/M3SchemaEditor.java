package org.mitre.openii.editors.schemas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.ExpandBarWidgets;
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
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/** Constructs the Schema View */
public class M3SchemaEditor extends OpenIIEditor
{	
	/** Returns the list of schema elements for the specified element type */
	private ArrayList<SchemaElement> getElements(SchemaInfo schemaInfo, Class<?> type)
	{
		// Class for sorting schema elements
		class ElementComparator implements Comparator<SchemaElement>
		{
			public int compare(SchemaElement element1, SchemaElement element2)
				{ return element1.getId().compareTo(element2.getId()); }			
		}
		
		// Returns the list of schema elements
		ArrayList<SchemaElement> elements = schemaInfo.getElements(type);
		Collections.sort(elements,new ElementComparator());
		return elements;
	}
	
	/** Generate the schema element panes */
	private void generateElementPanes(ExpandBar bar)
	{
		// Get the schema info
		SchemaInfo schemaInfo = OpenIIManager.getSchemaInfo(elementID);

		// Generate the entities table
		String[] fields = new String[]{"ID","Name","Description"};
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Entity.class))
		{
			Entity entity = (Entity)element;
			Integer id = entity.getId();
			String name = entity.getName();
			String description = entity.getDescription();
			rows.add(new Object[]{id,name,description});
		}
		ExpandBarWidgets.createTablePane(bar, "Entities", fields, rows);

		// Generate the attributes table
		fields = new String[]{"ID","Name","Entity","Domain","Min","Max","IsKey","Description"};
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Attribute.class))
		{
			Attribute attribute = (Attribute)element;
			Integer id = attribute.getId();
			String name = attribute.getName();
			String entity = schemaInfo.getElement(attribute.getEntityID()).getName() + " (" + attribute.getEntityID() + ")";
			String domain = schemaInfo.getElement(attribute.getDomainID()).getName() + " (" + attribute.getDomainID() + ")";
			Integer min = attribute.getMin();
			Integer max = attribute.getMax();
			Boolean isKey = attribute.isKey();
			String description = attribute.getDescription();
			rows.add(new Object[]{id,name,entity,domain,min,max,isKey,description});
		}
		ExpandBarWidgets.createTablePane(bar, "Attributes", fields, rows);

		// Generate the domains table
		fields = new String[]{"ID","Name","Description"};
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Domain.class))
		{
			Domain domain = (Domain)element;
			Integer id = domain.getId();
			String name = domain.getName();
			String description = domain.getDescription();
			rows.add(new Object[]{id,name,description});
		}
		ExpandBarWidgets.createTablePane(bar, "Domain", fields, rows);

		// Generate the domain values table
		fields = new String[]{"ID","Name","Domain","Description"};
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,DomainValue.class))
		{
			DomainValue domainValue = (DomainValue)element;
			Integer id = domainValue.getId();
			String name = domainValue.getName();
			String domain = schemaInfo.getElement(domainValue.getDomainID()).getName() + " (" + domainValue.getDomainID() + ")";
			String description = domainValue.getDescription();
			rows.add(new Object[]{id,name,domain,description});
		}
		ExpandBarWidgets.createTablePane(bar, "Domain Values", fields, rows);

		// Generate the relationships table
		fields = new String[]{"ID","Name","Left Element","Left Min","Left Max","Right Element","Right Min","Right Max","Description"};
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Relationship.class))
		{
			Relationship relationship = (Relationship)element;
			Integer id = relationship.getId();
			String name = relationship.getName();
			String left = schemaInfo.getElement(relationship.getLeftID()).getName() + " (" + relationship.getLeftID() + ")";
			Integer leftMin = relationship.getLeftMin();
			Integer leftMax = relationship.getLeftMax();
			String right = schemaInfo.getElement(relationship.getRightID()).getName() + " (" + relationship.getRightID() + ")";
			Integer rightMin = relationship.getRightMin();
			Integer rightMax = relationship.getRightMax();
			String description = relationship.getDescription();
			rows.add(new Object[]{id,name,left,leftMin,leftMax,right,rightMin,rightMax,description});
		}
		ExpandBarWidgets.createTablePane(bar, "Relationships", fields, rows);

		// Generate the containments table
		fields = new String[]{"ID","Name","Parent Element","Child Element","Min","Max","Description"};
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Containment.class))
		{
			Containment containment = (Containment)element;
			Integer id = containment.getId();
			String name = containment.getName();
			String parentElement = containment.getParentID()==null ? "" : schemaInfo.getElement(containment.getParentID()).getName() + " (" + containment.getParentID() + ")";
			String childElement = schemaInfo.getElement(containment.getChildID()).getName() + " (" + containment.getChildID() + ")";
			Integer min = containment.getMin();
			Integer max = containment.getMax();
			String description = containment.getDescription();
			rows.add(new Object[]{id,name,parentElement,childElement,min,max,description});
		}
		ExpandBarWidgets.createTablePane(bar, "Containments", fields, rows);

		// Generate the subtypes table
		fields = new String[]{"ID","Name","Parent Element","Child Element","Description"};
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Subtype.class))
		{
			Subtype subtype = (Subtype)element;
			Integer id = subtype.getId();
			String name = subtype.getName();
			String parentElement = schemaInfo.getElement(subtype.getParentID()).getName() + " (" + subtype.getParentID() + ")";
			String childElement = schemaInfo.getElement(subtype.getChildID()).getName() + " (" + subtype.getChildID() + ")";
			String description = subtype.getDescription();
			rows.add(new Object[]{id,name,parentElement,childElement,description});
		}
		ExpandBarWidgets.createTablePane(bar, "Subtypes", fields, rows);

		// Generate the aliases table
		fields = new String[]{"ID","Name","Aliased Element"};
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Alias.class))
		{
			Alias alias = (Alias)element;
			Integer id = alias.getId();
			String name = alias.getName();
			String aliasedElement = schemaInfo.getElement(alias.getElementID()).getName() + " (" + alias.getElementID() + ")";
			rows.add(new Object[]{id,name,aliasedElement});
		}
		ExpandBarWidgets.createTablePane(bar, "Aliases", fields, rows);
	}
	
	/** Displays the Schema View */
	public void createPartControl(Composite parent)
	{		
		// Create the expand bar
		ExpandBar bar = new ExpandBar(parent, SWT.V_SCROLL);
		bar.setSpacing(8);

		// Creates the properties pane
		ArrayList<String> attributes = new ArrayList<String>();
		Schema schema = OpenIIManager.getSchema(elementID);
		attributes.add("Name:"+schema.getName()+" ("+schema.getId()+")");
		attributes.add("Author:"+schema.getAuthor());
		attributes.add("Source:"+schema.getSource());
		attributes.add("Type:"+schema.getType());
		attributes.add("Description:"+schema.getDescription());
		ExpandBarWidgets.createPropertiesPane(bar,"Schema Properties",attributes);
		
		// Generate the element panes in the schema view
		generateElementPanes(bar);
	}
}