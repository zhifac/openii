package org.mitre.openii.widgets.schemaTree;

import java.util.HashMap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.search.SchemaSearchResult;

/** Defines the labels shown in the Schema Tree */
public class SchemaElementLabelProvider extends StyledCellLabelProvider
{
	// Defines the various referenced colors
	private static final Color YELLOW = Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW); 
	private static final Color ORANGE = new Color(Display.getCurrent(), 255, 200, 145); 
	private static final Color WHITE = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE); 
	private static final Color GRAY = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY); 
	private static final Color BLACK = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK); 
	private HashMap<ImageDescriptor, Image> imageCache = new HashMap<ImageDescriptor, Image>();
	
	/** Stores a reference to the schema view */
	private SchemaTree schemaView = null;
	
	/** Stores a cache for schema names */
	private HashMap<Integer,String> schemaCache = new HashMap<Integer,String>();
	
	/** Constructs the content provider */
	public SchemaElementLabelProvider(SchemaTree schemaView)
		{ this.schemaView = schemaView; }

	/** Returns the image associated with the specified element */
	private Image getImage(Object element)
	{
		String imageName = "";
		if(element instanceof Schema) imageName = "Schema.gif";
		else if (element instanceof SchemaElementWrapper){
			element = ((SchemaElementWrapper)element).getSchemaElement();
			if(element instanceof DomainValue) imageName = "DomainValue.jpg";
			else if(element instanceof Attribute) imageName = "Attribute.jpg";
			else if(element instanceof Containment) imageName = "Containment.jpg";
			else if(element instanceof Relationship) imageName = "Relationship.jpg";
			else imageName = "SchemaElement.jpg";
		}
		ImageDescriptor descriptor = OpenIIActivator.getImageDescriptorForIcons(imageName);
		Image image = imageCache.get(descriptor);
		if (image == null) {
			image = descriptor.createImage();
			imageCache.put(descriptor, image);
		}
		return image;
	}

	/** Returns the name associated with the specified element */
	public String getText(Object object)
	{
		String text = "";
		if(object instanceof SchemaElementWrapper) {

			text = schemaView.getSchema().getDisplayName(((SchemaElementWrapper)object).getSchemaElement().getId());

		}
		else text = object.toString();
		return text;
	}

	/** Returns the specified element's associated info */
	public String getAssociatedInfo(Object object)
	{
		String text = "";
		if(object instanceof SchemaElementWrapper)
		{
			// Get the schema element
			HierarchicalSchemaInfo schema = schemaView.getSchema();
			SchemaElementWrapper wrap = (SchemaElementWrapper) object;
			SchemaElement element = ((SchemaElementWrapper)object).getSchemaElement();
			Attribute attr = null;
			if (element instanceof Attribute) {
				attr = (Attribute) element;
				if (attr.isKey()) {
					text += " {K}";
				}
			}
			if (schemaView.showCardinality()) {
				if (!(element instanceof Entity)) {
					if (element instanceof Relationship) {
						Relationship rel = (Relationship) element;
						if (wrap.getParentId().equals(rel.getLeftID())) {
						text += " [" + getCardinalityText(rel.getRightMin(), rel.getRightMax()) + "]";
						}
						else {
							text += " [" + getCardinalityText(rel.getLeftMin(), rel.getLeftMax()) + "]";
						}
					}
					if (attr != null){

						text += " [" + getCardinalityText(attr.getMin(), attr.getMax()) + "]";
					}
					if (element instanceof Containment) {
						Containment cont = (Containment) element;
						
						text += " [" + getCardinalityText(cont.getMin(),cont.getMax()) + "]";
					}
				}
			}
			
			// Displays the type of each element if requested
			if(schemaView.showTypes())
			{
				SchemaElement type = schema.getType(schema, element.getId());
				if(type!=null) text += " (" + schema.getDisplayName(type.getId())+ ")";
			}
			

			// Displays the base schemas of each element if requested
			if(schemaView.showBaseSchemas())
			{
				Integer baseID = element.getBase();
				if(baseID!=null && !baseID.equals(schemaView.getSchema().getSchema().getId()))
				{
					String schemaName = schemaCache.get(baseID);
					if(schemaName==null) schemaCache.put(baseID,schemaName = OpenIIManager.getSchema(baseID).getName());
					text += " <- " + schemaName;
				}
			}
		}
		return text;
	}
	private String getCardinalityText(Integer min, Integer max){
		String mn = getMin(min);
		String mx = getMax(max);
		if (mn.equals(mx)) {
			return mn;
		}
		return mn + ".." + mx;
	}
	private String getMin(Integer value) {
		if (value != null){
			return value.toString();
		}
		return "1";
	}
	private String getMax(Integer value) {
		if (value != null) {
			if (value.intValue() == -1){
				return "*";
			}
			return value.toString();
		}
		return "1";
	}
	
	/** Displays the tool tip for the specified element */
	public String getToolTipText(Object element)
	{
		if(element instanceof SchemaElementWrapper)
			return ((SchemaElementWrapper)element).getSchemaElement().getDescription();
		return null;
	}
	
	/** Updates the viewer cell */
	public void update(ViewerCell cell)
	{
		// Display the cell image
		Object element = cell.getElement();
		cell.setImage(getImage(element));
		
		// Display the cell text
		String text = getText(element);
		String associatedInfo = getAssociatedInfo(element);
		cell.setText(text + associatedInfo);

		// Determine the highlight color
		Color highlightColor = WHITE;
		if(element instanceof SchemaElementWrapper)
		{
			SchemaElement schemaElement = ((SchemaElementWrapper)element).getSchemaElement();
			SchemaSearchResult searchResult = schemaView.getSearchResult(schemaElement.getId());
			if(searchResult!=null)
				{ highlightColor = searchResult.nameMatched() ? YELLOW : searchResult.descriptionMatched() ? ORANGE : WHITE; }
		}
		
		// Assign the background color
		StyleRange styleRanges[] = new StyleRange[2];
		styleRanges[0] = new StyleRange(0,text.length(),BLACK,highlightColor);
		styleRanges[1] = new StyleRange(text.length(),associatedInfo.length(),GRAY,WHITE);
		cell.setStyleRanges(styleRanges);
		
		//set data field with ID
		if(element instanceof SchemaElementWrapper)
		{
		    cell.getItem().setData("uid", ((SchemaElementWrapper)element).getSchemaElement().getId());
		    cell.getItem().setData("description", ((SchemaElementWrapper)element).getSchemaElement().getDescription());
		}
		
		
		super.update(cell);
	}
	public void dispose() {
		for (Image image : imageCache.values()) {
			image.dispose();
		}
		imageCache.clear();
		super.dispose();
	}
}