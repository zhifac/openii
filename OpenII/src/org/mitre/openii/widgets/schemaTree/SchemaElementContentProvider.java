package org.mitre.openii.widgets.schemaTree;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

public class SchemaElementContentProvider implements ITreeContentProvider
{
	/** Holds a reference to the schema view */
	private SchemaTree schemaView = null;
	
	/** Constructs the content provider */
	public SchemaElementContentProvider(SchemaTree schemaView)
		{ this.schemaView = schemaView; }
	
	/** Returns the children elements for the specified element */
	public Object[] getChildren(Object element)
	{
		// Gets the children elements
		List<? extends Object> children = null;
		HierarchicalSchemaInfo schema = schemaView.getSchema();
		if(element instanceof String) children = Arrays.asList(new Object[]{schema.getSchema()});
		else if(element instanceof Schema) children = schema.getRootElements();
		else children = schema.getChildElements(((SchemaElement)element).getId());

		// Alphabetize if needed
		if(schemaView.isAlphabetized())
		{
			class ElementComparator implements Comparator<Object>
				{ public int compare(Object object1, Object object2)
					{ return object1.toString().compareToIgnoreCase(object2.toString()); } }
			Collections.sort(children, new ElementComparator());
		}
		
		return children.toArray();
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