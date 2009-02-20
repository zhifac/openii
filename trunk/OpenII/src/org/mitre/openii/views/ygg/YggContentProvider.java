package org.mitre.openii.views.ygg;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

public class YggContentProvider implements ITreeContentProvider
{
	// Stores the headers
	private String schemaHeader = "Schemas";
	private String mappingHeader = "Mappings";
	
	/** Returns the children elements for the specified element */
	public Object[] getChildren(Object element)
	{
		if(element.equals(""))
			return new String[] {schemaHeader,mappingHeader};
	    if(element.equals(schemaHeader))
	    	return OpenIIManager.getSchemas().toArray(new Schema[0]);
	    if(element.equals(mappingHeader))
	    	return OpenIIManager.getMappings().toArray(new Mapping[0]);
	    return new String[] {};
	}

	/** Return the parent element for the specified element */
	public Object getParent(Object element)
	{
	    if(element instanceof Schema) return schemaHeader;
	    if(element instanceof Mapping) return mappingHeader;
	    return null;
	}

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
