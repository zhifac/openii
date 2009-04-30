package org.mitre.openii.views.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;

public class ManagerContentProvider implements ITreeContentProvider
{
	// Stores the headers
	private String schemaHeader = "Schemas";
	private String groupHeader = "Groups";
	private String mappingHeader = "Mappings";
	
	/** Sorts the provided array by name */
	private <T> Object[] sortList(ArrayList<T> list)
	{
		/** Handles the comparison of list items */
		class ItemComparator implements Comparator<Object>
		{
			public int compare(Object item1, Object item2)
			{
				if(item1.getClass()!=item2.getClass()) return -1;
				return item1.toString().compareTo(item2.toString());
			}
		}
		
		Collections.sort(list, new ItemComparator());
		return list.toArray();
	}
	
	/** Returns the children elements for the specified element */
	public Object[] getChildren(Object element)
	{
		// Handles data categories
		if(element instanceof String)
		{
			if(element.equals("")) return new String[] {schemaHeader,groupHeader,mappingHeader};
			if(element.equals(schemaHeader)) return sortList(OpenIIManager.getSchemas());
		    if(element.equals(groupHeader)) return sortList(OpenIIManager.getSubgroups(null));
		    if(element.equals(mappingHeader)) return sortList(OpenIIManager.getMappings());
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
			return sortList(elements);
		}
			
		// Handles mapping elements
		if(element instanceof Mapping)
		{
			Mapping mapping = (Mapping)element;
			ArrayList<Object> elements = new ArrayList<Object>();
			for(Integer schemaID : mapping.getSchemas())
				elements.add(new MappingSchema(mapping.getId(),OpenIIManager.getSchema(schemaID)));
			return sortList(elements);
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
