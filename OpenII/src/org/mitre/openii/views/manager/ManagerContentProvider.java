package org.mitre.openii.views.manager;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Tag;

public class ManagerContentProvider implements ITreeContentProvider
{
	// Stores the headers
	
	/** Returns the children elements for the specified element */
	public Object[] getChildren(Object element)
	{
		// Handles data categories
		if(element instanceof String)
		{
			if(element.equals("")) return new String[] {ManagerView.SCHEMAS_HEADER,ManagerView.PROJECTS_HEADER};
			if(element.equals(ManagerView.SCHEMAS_HEADER))
			{
				ArrayList<Object> tags = new ArrayList<Object>();
				tags.add(ManagerView.ALL_SCHEMAS_HEADER);
				tags.addAll(WidgetUtilities.sortList(OpenIIManager.getSubcategories(null)));
				return tags.toArray();
			}
			if(element.equals(ManagerView.ALL_SCHEMAS_HEADER)) return WidgetUtilities.sortList(OpenIIManager.getSchemas()).toArray();
		    if(element.equals(ManagerView.PROJECTS_HEADER)) return WidgetUtilities.sortList(OpenIIManager.getProjects()).toArray();
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
		
		// Handles tag elements
		if(element instanceof Tag)
		{
			Tag tag = (Tag)element;
			ArrayList<SchemaInTag> schemas = new ArrayList<SchemaInTag>();
			for(Integer schemaID : OpenIIManager.getTagSchemas(tag.getId()))
				schemas.add(new SchemaInTag(tag.getId(),OpenIIManager.getSchema(schemaID)));

			// Put together the list of elements listed under tag
			ArrayList<Object> elements = new ArrayList<Object>();
			elements.addAll(WidgetUtilities.sortList(schemas));
			elements.addAll(WidgetUtilities.sortList(OpenIIManager.getSubcategories(tag.getId())));
			return elements.toArray();
		}
			
		// Handles project elements
		if(element instanceof Project)
		{
			Project project = (Project)element;
			ArrayList<Integer> schemaIDs = new ArrayList<Integer>(Arrays.asList(project.getSchemaIDs()));
			
			// Display mappings
			ArrayList<Mapping> mappings = new ArrayList<Mapping>();
			for(Mapping mapping : OpenIIManager.getMappings(project.getId()))
			{
				mappings.add(mapping);
				schemaIDs.remove(mapping.getSourceId());
				schemaIDs.remove(mapping.getTargetId());
			}
			
			// Display schemas which are not part of a mapping
			ArrayList<SchemaInProject> schemas = new ArrayList<SchemaInProject>();
			for(Integer schemaID : schemaIDs)
				schemas.add(new SchemaInProject(project.getId(),OpenIIManager.getSchema(schemaID),true));

			// Return the sorted list of project elements
			ArrayList<Object> elements = new ArrayList<Object>();
			elements.addAll(WidgetUtilities.sortList(mappings));
			elements.addAll(WidgetUtilities.sortList(schemas));
			return elements.toArray();
		}
		
		// Handles mapping elements
		if(element instanceof Mapping)
		{
			Mapping mapping = (Mapping)element;
			ArrayList<SchemaInProject> schemas = new ArrayList<SchemaInProject>();
			schemas.add(new SchemaInProject(mapping.getProjectId(),OpenIIManager.getSchema(mapping.getSourceId()),false));
			schemas.add(new SchemaInProject(mapping.getProjectId(),OpenIIManager.getSchema(mapping.getTargetId()),false));
			return schemas.toArray();
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