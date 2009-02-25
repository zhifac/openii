package org.mitre.openii.views.ygg;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

public class YggContentProvider implements ITreeContentProvider
{
	// Stores the headers
	private String schemaHeader = "Schemas";
	private String groupHeader = "Groups";
	private String mappingHeader = "Mappings";
	
	/** Returns the children elements for the specified element */
	public Object[] getChildren(Object element)
	{
		// Handles headers
		if(element instanceof String)
		{
			if(element.equals(""))
				return new String[] {schemaHeader,groupHeader,mappingHeader};
		    if(element.equals(schemaHeader))
		    	return OpenIIManager.getSchemas().toArray(new Schema[0]);
		    if(element.equals(groupHeader))
		    	return OpenIIManager.getSubgroups(null).toArray(new Group[0]);
		    if(element.equals(mappingHeader))
		    	return OpenIIManager.getMappings().toArray(new Mapping[0]);
		}
		    
		// Handles group elements
		if(element instanceof Group)
		{
			Group group = (Group)element;
			ArrayList<Object> elements = new ArrayList<Object>();
			for(Integer schemaID : OpenIIManager.getGroupSchemas(group.getId()))
				elements.add(new GroupSchema(group.getId(),OpenIIManager.getSchema(schemaID)));
			for(Group subgroup : OpenIIManager.getSubgroups(group.getId()))
				elements.add(subgroup);
			return elements.toArray(new Object[0]);
		}
			
		// Handles mapping elements
		if(element instanceof Mapping)
		{
			Mapping mapping = (Mapping)element;
			ArrayList<Object> elements = new ArrayList<Object>();
			for(Integer schemaID : mapping.getSchemas())
				elements.add(new MappingSchema(mapping.getId(),OpenIIManager.getSchema(schemaID)));
			return elements.toArray(new Object[0]);
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
