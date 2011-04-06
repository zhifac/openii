package org.mitre.openii.editors.schemas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

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
import org.mitre.schemastore.model.Synonym;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/** Constructs the Schema View */
public class M3SchemaView extends OpenIIEditor
{	
	/** Generate a listing of base schema references */
	private HashMap<Integer,String> baseSchemas = null;
	
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
	
	/** Generates field information */
	private String[] getFields(String... labels)
	{
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("ID");
		fields.add("Name");
		if(baseSchemas!=null) fields.add("Base Schema");
		for(String label : labels) fields.add(label);
		return fields.toArray(new String[0]);
	}

	/** Generate a data row */
	private Object[] getDataRow(SchemaElement element, Object... items)
	{
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(element.getId());
		data.add(element.getName());
		if(baseSchemas!=null)
		{
			String schemaName = baseSchemas.get(element.getBase());
			data.add(schemaName==null ? "" : schemaName + " (" + element.getBase() + ")");
		}
		for(Object item : items) data.add(item);
		return data.toArray();
	}
	
	/** Generate the schema element panes */
	private void generateElementPanes(ExpandBar bar)
	{
		// Get the schema info
		SchemaInfo schemaInfo = OpenIIManager.getSchemaInfo(getElementID());

		// Generate the entities table
		String[] fields = getFields("Description");
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Entity.class))
		{
			Entity entity = (Entity)element;
			String description = entity.getDescription();
			rows.add(getDataRow(entity,description));
		}
		ExpandBarWidgets.createTablePane(bar, "Entities", fields, rows);

		// Generate the attributes table
		fields = getFields("Entity","Domain","Min","Max","IsKey","Description");
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Attribute.class))
		{
			Attribute attribute = (Attribute)element;
			String entity = schemaInfo.getElement(attribute.getEntityID()).getName() + " (" + attribute.getEntityID() + ")";
			String domain = schemaInfo.getElement(attribute.getDomainID()).getName() + " (" + attribute.getDomainID() + ")";
			Integer min = attribute.getMin();
			Integer max = attribute.getMax();
			Boolean isKey = attribute.isKey();
			String description = attribute.getDescription();
			rows.add(getDataRow(attribute,entity,domain,min,max,isKey,description));
		}
		ExpandBarWidgets.createTablePane(bar, "Attributes", fields, rows);

		// Generate the domains table
		fields = getFields("Description");
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Domain.class))
		{
			Domain domain = (Domain)element;
			String description = domain.getDescription();
			rows.add(getDataRow(domain,description));
		}
		ExpandBarWidgets.createTablePane(bar, "Domain", fields, rows);

		// Generate the domain values table
		fields = getFields("Domain","Description");
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,DomainValue.class))
		{
			DomainValue domainValue = (DomainValue)element;
			String domain = schemaInfo.getElement(domainValue.getDomainID()).getName() + " (" + domainValue.getDomainID() + ")";
			String description = domainValue.getDescription();
			rows.add(getDataRow(domainValue,domain,description));
		}
		ExpandBarWidgets.createTablePane(bar, "Domain Values", fields, rows);

		// Generate the relationships table
		fields = getFields("Left Element","Left Min","Left Max","Right Element","Right Min","Right Max","Description");
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Relationship.class))
		{
			Relationship relationship = (Relationship)element;
			String left = schemaInfo.getDisplayName(relationship.getLeftID()) + " (" + relationship.getLeftID() + ")";
			Integer leftMin = relationship.getLeftMin();
			Integer leftMax = relationship.getLeftMax();
			String right = schemaInfo.getDisplayName(relationship.getRightID()) + " (" + relationship.getRightID() + ")";
			Integer rightMin = relationship.getRightMin();
			Integer rightMax = relationship.getRightMax();
			String description = relationship.getDescription();
			rows.add(getDataRow(relationship,left,leftMin,leftMax,right,rightMin,rightMax,description));
		}
		ExpandBarWidgets.createTablePane(bar, "Relationships", fields, rows);

		// Generate the containments table
		fields = getFields("Parent Element","Child Element","Min","Max","Description");
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Containment.class))
		{
			Containment containment = (Containment)element;
			String parentElement = containment.getParentID()==null ? "" : schemaInfo.getDisplayName(containment.getParentID()) + " (" + containment.getParentID() + ")";
			String childElement = schemaInfo.getDisplayName(containment.getChildID()) + " (" + containment.getChildID() + ")";
			Integer min = containment.getMin();
			Integer max = containment.getMax();
			String description = containment.getDescription();
			rows.add(getDataRow(containment,parentElement,childElement,min,max,description));
		}
		ExpandBarWidgets.createTablePane(bar, "Containments", fields, rows);

		// Generate the subtypes table
		fields = getFields("Parent Element","Child Element","Description");
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Subtype.class))
		{
			Subtype subtype = (Subtype)element;
			String parentElement = schemaInfo.getDisplayName(subtype.getParentID()) + " (" + subtype.getParentID() + ")";
			String childElement = schemaInfo.getDisplayName(subtype.getChildID()) + " (" + subtype.getChildID() + ")";
			String description = subtype.getDescription();
			rows.add(getDataRow(subtype,parentElement,childElement,description));
		}
		ExpandBarWidgets.createTablePane(bar, "Subtypes", fields, rows);

		// Generate the synonyms table
		fields = getFields("Associated Element","Description");
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Synonym.class))
		{
			Synonym synonym = (Synonym)element;
			String associatedElement = schemaInfo.getDisplayName(synonym.getElementID()) + " (" + synonym.getElementID() + ")";
			String description = synonym.getDescription();
			rows.add(getDataRow(synonym,associatedElement,description));
		}
		ExpandBarWidgets.createTablePane(bar, "Synonyms", fields, rows);
		
		// Generate the aliases table
		fields = getFields("Aliased Element");
		rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo,Alias.class))
		{
			Alias alias = (Alias)element;
			String aliasedElement = schemaInfo.getDisplayName(alias.getElementID()) + " (" + alias.getElementID() + ")";
			rows.add(getDataRow(alias,aliasedElement));
		}
		ExpandBarWidgets.createTablePane(bar, "Aliases", fields, rows);
	}
	
	/** Displays the Schema View */
	public void createPartControl(Composite parent)
	{		
		Schema schema = OpenIIManager.getSchema(getElementID());

		// Generate references to parent schemas
		ArrayList<Integer> ancestorIDs = OpenIIManager.getAncestorSchemas(schema.getId());
		if(ancestorIDs.size()>0)
		{
			baseSchemas = new HashMap<Integer,String>();
			for(Integer ancestorID : ancestorIDs)
				baseSchemas.put(ancestorID, OpenIIManager.getSchema(ancestorID).getName());
		}
		
		// Create the expand bar
		ExpandBar bar = new ExpandBar(parent, SWT.V_SCROLL);
		bar.setSpacing(8);

		// Creates the properties pane
		ArrayList<String> attributes = new ArrayList<String>();
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