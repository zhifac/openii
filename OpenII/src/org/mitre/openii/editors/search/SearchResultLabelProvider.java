package org.mitre.openii.editors.search;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
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
	private static final Color BLACK = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK); 
	private static final Color YELLOW = Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW); 
	
	/** Holds a reference to the schema */
	private SchemaInfo schema = null;

	/** Holds the keyword pattern */
	private Pattern pattern = null;
	
	/** Constructs the content provider */
	public SearchResultLabelProvider(SchemaInfo schema, String keyword)
	{
		this.schema = schema;
		
		// Store the pattern to search for
		keyword = "\\b" + keyword.replaceAll("\\*",".*") + "\\b";
		pattern = Pattern.compile("(?i)" + keyword);
	}

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

		// Highlight keyword references in the text
		ArrayList<StyleRange> styleRanges = new ArrayList<StyleRange>();
		Matcher matcher = pattern.matcher(getText(element));
		while(matcher.find())
			styleRanges.add(new StyleRange(matcher.start(),matcher.end()-matcher.start(),BLACK,YELLOW));			
		cell.setStyleRanges(styleRanges.toArray(new StyleRange[0]));
		
		super.update(cell);
	}
}