package org.mitre.openii.editors.search;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

public class SearchResultLabelProvider extends StyledCellLabelProvider
{
	// Defines the various referenced colors
	private static final Color YELLOW = Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW); 
	private static final Color ORANGE = new Color(Display.getCurrent(), 255, 200, 145); 
	private static final Color WHITE = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE); 
	
	/** Holds a reference to the schema */
	private SchemaInfo schema = null;

	/** Holds a reference to the keyword */
	private String keyword = null;
	
	/** Constructs the content provider */
	public SearchResultLabelProvider(SchemaInfo schema, String keyword)
		{ this.schema = schema; this.keyword = keyword; }

	/** Returns the image associated with the specified element */
	public Image getImage(Object element)
	{
		String imageName = "";
		if(element instanceof Schema) imageName = "Schema.gif";
		else if(element instanceof DomainValue) imageName = "DomainValue.jpg";
		else if(element instanceof Attribute) imageName = "Attribute.jpg";
		else if(element instanceof Containment) imageName = "Containment.jpg";
		else if(element instanceof Relationship) imageName = "Relationship.jpg";
		else if(element instanceof SchemaElement) imageName = "SchemaElement.jpg";
		else imageName = "More.gif";
		return OpenIIActivator.getImage(imageName);
	}

	/** Returns the name associated with the specified element */
	public String getText(Object element)
	{
		if(element instanceof SchemaElement)
		{
			String name = schema.getDisplayName(((SchemaElement)element).getId());
			String description = ((SchemaElement)element).getDescription();
			return name + (description!=null ? " (" + description + ")" : "");
		}
		return element.toString();
	}

	/** Displays the tool tip for the specified element */
	public String getToolTipText(Object element)
	{
		if(element instanceof SchemaElement)
			return ((SchemaElement)element).getDescription();
		return null;
	}
	
	/** Updates the viewer cell */
	public void update(ViewerCell cell)
	{
		// Set up the cell text and image
		Object element = cell.getElement();
		cell.setImage(getImage(element));
		cell.setText(getText(element));

		// Highlight cells which match the search results
//		if(element instanceof SchemaElement)
//		{
//			// Get the search result
//			SchemaElement schemaElement = (SchemaElement)element;
//			SchemaSearchResult searchResult = schemaView.getSearchResult(schemaElement.getId());
//
//			// Assign the background color
//			Color color = WHITE;
//			if(searchResult!=null)
//				{ color = searchResult.nameMatched() ? YELLOW : searchResult.descriptionMatched() ? ORANGE : WHITE; }
//			cell.setBackground(color);
//		}
		
		super.update(cell);
	}
}