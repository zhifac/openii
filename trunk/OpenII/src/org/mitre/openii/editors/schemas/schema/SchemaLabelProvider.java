package org.mitre.openii.editors.schemas.schema;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

public class SchemaLabelProvider implements ILabelProvider
{
	/** Holds a reference to the schema */
	private HierarchicalSchemaInfo schema = null;
	
	/** Constructs the content provider */
	public SchemaLabelProvider(HierarchicalSchemaInfo schema)
		{ this.schema = schema; }

	/** Returns the image associated with the specified element */
	public Image getImage(Object element)
	{
		String imageName = "";
		if(element instanceof Schema) imageName = "Schema.gif";
		else if(element instanceof DomainValue) imageName = "DomainValue.jpg";
		else if(element instanceof Attribute) imageName = "Attribute.jpg";
		else if(element instanceof Containment) imageName = "Containment.jpg";
		else if(element instanceof Relationship) imageName = "Relationship.jpg";
		else imageName = "SchemaElement.jpg";
		return OpenIIActivator.getImage(imageName);
	}

	/** Returns the name associated with the specified element */
	public String getText(Object element)
	{
		if(element instanceof SchemaElement)
			return schema.getDisplayName(((SchemaElement)element).getId());
		return element.toString();
	}

	/** Indicates that the label is not influenced by an element property */
	public boolean isLabelProperty(Object element, String property) { return false; }
	
	// Unused functions
	public void addListener(ILabelProviderListener listener) {}
	public void dispose() {}
	public void removeListener(ILabelProviderListener listener) {}
}
