package org.mitre.openii.views.manager;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

public class ManagerContentProvider implements ITreeContentProvider
{
	// Stores the headers
	private String schemaHeader = "Schemas";
	private String groupHeader = "Groups";
	private String mappingHeader = "Mappings";
	
	/** Returns the children elements for the specified element */
	public Object[] getChildren(Object element)
	{
		// Handles data categories
		if(element instanceof String)
		{
			if(element.equals("")) return new String[] {schemaHeader,groupHeader,mappingHeader};
			if(element.equals(schemaHeader)) return WidgetUtilities.sortList(OpenIIManager.getSchemas()).toArray();
		    if(element.equals(groupHeader)) return WidgetUtilities.sortList(OpenIIManager.getSubgroups(null)).toArray();
		    if(element.equals(mappingHeader)) return WidgetUtilities.sortList(OpenIIManager.getMappings()).toArray();
		}
		    
		// Handles schema elements
		if(element instanceof Schema)
		{
			Schema schema = (Schema)element;
			ArrayList<Object> elements = new ArrayList<Object>();
			for(DataSource dataSource : OpenIIManager.getDataSources(schema.getId()))
				elements.add(dataSource);
			return WidgetUtilities.sortList(elements).toArray();		
		}
		
		// Handles group elements
		if(element instanceof Group)
		{
			Group group = (Group)element;
			ArrayList<SchemaInGroup> schemas = new ArrayList<SchemaInGroup>();
			for(Integer schemaID : OpenIIManager.getGroupSchemas(group.getId()))
				schemas.add(new SchemaInGroup(group.getId(),OpenIIManager.getSchema(schemaID)));

			// Put together the list of elements listed under group
			ArrayList<Object> elements = new ArrayList<Object>();
			elements.addAll(WidgetUtilities.sortList(schemas));
			elements.addAll(WidgetUtilities.sortList(OpenIIManager.getSubgroups(group.getId())));
			return elements.toArray();
		}
			
		// Handles mapping elements
		if(element instanceof Mapping)
		{
			Mapping mapping = (Mapping)element;
			ArrayList<Object> elements = new ArrayList<Object>();
			for(Integer schemaID : mapping.getSchemaIDs())
				elements.add(new SchemaInMapping(mapping.getId(),OpenIIManager.getSchema(schemaID)));
			return WidgetUtilities.sortList(elements).toArray();
		}
		
		return new String[] {};
	}

	/** Return the parent element for the specified element */
	public Object getParent(Object element)
		{ return null; }

	/** Indicates if the specified element has any children */
	public boolean hasChildren(Object element)
		{ return getChildren(element).length > 0; }

	/** Returns the list of elements to display for the specified element */
	public Object[] getElements(Object element)
		{ return getChildren(element); }

	// Unused functions
	public void dispose() {}
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {}
}
