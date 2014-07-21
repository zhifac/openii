package org.mitre.openii.widgets.schemaTree;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/** Provides Content to the Schema Tree */
public class SchemaElementContentProvider implements ITreeContentProvider {
	/** Stores a reference to the schema view */
	private SchemaTree schemaView = null;

	/** Constructs the content provider */
	public SchemaElementContentProvider(SchemaTree schemaView) {
		this.schemaView = schemaView;
	}
	SchemaTree getSchemaView() {
		return schemaView;
	}
	/** Returns the children elements for the specified element */
	public Object[] getChildren(Object element) {
		HierarchicalSchemaInfo schema = schemaView.getSchema();
		if (element instanceof String)
			return new Object[] { schema.getSchema() };
		ArrayList<SchemaElementWrapper> wrappers = new ArrayList<SchemaElementWrapper>();
		ArrayList<SchemaElement> elements;
		if (element instanceof Schema) {
			elements = schema.getRootElements();
			for (SchemaElement current : elements) {
				wrappers.add(new SchemaElementWrapper(current));
			}
		} else {
			SchemaElementWrapper wrapper = (SchemaElementWrapper) element;
			if (!(wrapper.getPathIds().contains(schema.getType(schema, wrapper
					.getSchemaElement().getId())))) {
				SchemaElement type = schema.getType(schema, wrapper
						.getSchemaElement().getId());
				if (type instanceof Domain) {
					type = null;
				}
				if (type != null) {
					wrapper.insertTypeId(type.getId());
				}
				elements = schema.getChildElements(wrapper.getSchemaElement()
						.getId(), wrapper.getPathIds());

				for (SchemaElement current : elements) {
					SchemaElementWrapper w = wrapper
							.createChildWrapper(current);
					wrappers.add(w);
					
				}
			}
		}

		return wrappers.toArray();
	}

	/** Return the parent element for the specified element */
	public Object getParent(Object element) {
		return null;
	}

	/** Indicates if the specified element has any children */
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	/** Returns the list of elements to display for the specified element */
	public Object[] getElements(Object element) {
		return getChildren(element);
	}

	// Unused functions
	public void dispose() {

	}

	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
	}

	public void updateChildCount(Object arg0, int arg1) {
	}

	public void updateElement(Object arg0, int arg1) {
	}
}