package org.mitre.openii.widgets.schemaTree;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/** Handles the sorting of the Schema Tree */
public class SchemaElementSorter extends ViewerSorter
{
	/** Stores a reference to the schema */
	private SchemaTree schemaView = null;
	
	/** Constructs the content sorter */
	public SchemaElementSorter(SchemaTree schemaView)
		{ this.schemaView = schemaView; }
	
	/** Compares elements in the schema tree */
	public int compare(Viewer viewer, Object object1, Object object2)
	{
		if(object1 instanceof SchemaElement && object2 instanceof SchemaElement)				
		{
			Integer element1ID = ((SchemaElement)object1).getId();
			Integer element2ID = ((SchemaElement)object2).getId();
			if(schemaView.isAlphabetized())
			{
				HierarchicalSchemaInfo schema = schemaView.getSchema();
				String name1 = schema.getDisplayName(element1ID);
				String name2 = schema.getDisplayName(element2ID);
				return name1.compareToIgnoreCase(name2);
			}	
			return element1ID.compareTo(element2ID);
		}
		return super.compare(viewer, object1, object2);
	}
}