package org.mitre.openii.widgets.schemaList;

import java.util.HashMap;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;

/** Class for defining how schema models should be displayed in schema list */
public class ModelLabelProvider extends CellLabelProvider implements ILabelProvider
{
	/** Stores the models associated with various schemas */
	private HashMap<Integer,SchemaModel> models = null;
	
	/** Constructs the model label provider */
	ModelLabelProvider(HashMap<Integer,SchemaModel> models)
		{ this.models = models; }
	
	/** Returns the image associated with the specified element */
	public Image getImage(Object element)
		{ return null; }

	/** Returns the name of the model associated with the specified element */
	public String getText(Object element)
	{
		Integer schemaID = ((Schema)element).getId();
		SchemaModel model = models.get(schemaID);
		return model==null ? "<default>" : model.getName();
	}

	/** Indicates that the label is not influenced by an element property */
	public boolean isLabelProperty(Object element, String property) { return false; }

	/** Display the image and text for the cell element */
	public void update(ViewerCell cell)
	{
		Object element = cell.getElement();
		cell.setImage(getImage(element));
		cell.setText(getText(element));
	}
	
	// Unused functions
	public void addListener(ILabelProviderListener listener) {}
	public void dispose() {}
	public void removeListener(ILabelProviderListener listener) {}
}
