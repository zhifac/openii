package org.mitre.openii.widgets.schemaTree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/** Provides Content to the Schema Tree */
public class SchemaElementContentProvider implements ITreeContentProvider
{
	/** Stores a reference to the schema view */
	private SchemaTree schemaView = null;
	
	/** Constructs the content provider */
	public SchemaElementContentProvider(SchemaTree schemaView)
		{ this.schemaView = schemaView; }
	
	/** Returns the children elements for the specified element */
	public Object[] getChildren(Object element)
	{
		HierarchicalSchemaInfo schema = schemaView.getSchema();
		if(element instanceof String) return new Object[]{schema.getSchema()};
		if(element instanceof Schema) return schema.getRootElements().toArray();
		return schema.getChildElements(((SchemaElement)element).getId()).toArray();
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
	public void updateChildCount(Object arg0, int arg1) {}
	public void updateElement(Object arg0, int arg1) {}
}