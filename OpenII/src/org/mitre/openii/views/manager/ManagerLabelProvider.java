package org.mitre.openii.views.manager;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Tag;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

public class ManagerLabelProvider implements ILabelProvider
{
	/** Returns the image associated with the specified element */
	public Image getImage(Object element)
	{
		String imageName = "";
		if(element instanceof Schema || element instanceof SchemaInTag || element instanceof SchemaInMapping) imageName = "Schema.gif";
		else if(element instanceof Mapping) imageName = "Mapping.gif";
		else if(element instanceof Tag) imageName = "Tag.gif";
		else if(element instanceof DataSource) imageName = "DataSource.gif";
		else if(element.equals("Schemas")) imageName = "Schemas.gif";
		else if(element.equals("Tags")) imageName = "Tags.gif";
		else if(element.equals("Mappings")) imageName = "Mappings.gif";
		return OpenIIActivator.getImage(imageName);
	}

	/** Returns the name associated with the specified element */
	public String getText(Object element)
		{ return element.toString(); }

	/** Indicates that the label is not influenced by an element property */
	public boolean isLabelProperty(Object element, String property) { return false; }
	
	// Unused functions
	public void addListener(ILabelProviderListener listener) {}
	public void dispose() {}
	public void removeListener(ILabelProviderListener listener) {}
}
