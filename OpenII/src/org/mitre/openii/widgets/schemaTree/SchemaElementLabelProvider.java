package org.mitre.openii.widgets.schemaTree;

import java.util.HashMap;

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
	
	/** Stores a reference to the schema view */
	private SchemaTree schemaView = null;
	
	/** Stores a cache for schema names */
	private HashMap<Integer,String> schemaCache = new HashMap<Integer,String>();
	
	/** Constructs the content provider */
	public SchemaElementLabelProvider(SchemaTree schemaView)
		{ this.schemaView = schemaView; }

	/** Returns the image associated with the specified element */
	public static Image getImage(Object element)
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
	public String getText(Object object)
	{
		String text = "";
		if(object instanceof SchemaElement)
			text = schemaView.getSchema().getDisplayName(((SchemaElement)object).getId());
		else text = object.toString();
		return text;
	}

	/** Returns the specified element's associated info */
	public String getAssociatedInfo(Object object)
	{
		String text = "";
		if(object instanceof SchemaElement)
		{
			// Get the schema element
			HierarchicalSchemaInfo schema = schemaView.getSchema();
			SchemaElement element = (SchemaElement)object;
			
			// Displays the type of each element if requested
			if(schemaView.showTypes())
			{
				SchemaElement type = schema.getType(schema, element.getId());
				if(type!=null) text += " (" + type.getName() + ")";
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
		// Display the cell image
		Object element = cell.getElement();
		cell.setImage(getImage(element));
		
		// Display the cell text
		String text = getText(element);
		String associatedInfo = getAssociatedInfo(element);
		cell.setText(text + associatedInfo);

		// Determine the highlight color
		Color highlightColor = WHITE;
		if(element instanceof SchemaElement)
		{
			SchemaElement schemaElement = (SchemaElement)element;
			SchemaSearchResult searchResult = schemaView.getSearchResult(schemaElement.getId());
			if(searchResult!=null)
				{ highlightColor = searchResult.nameMatched() ? YELLOW : searchResult.descriptionMatched() ? ORANGE : WHITE; }
		}
		
		// Assign the background color
		StyleRange styleRanges[] = new StyleRange[2];
		styleRanges[0] = new StyleRange(0,text.length(),BLACK,highlightColor);
		styleRanges[1] = new StyleRange(text.length(),associatedInfo.length(),GRAY,WHITE);
		cell.setStyleRanges(styleRanges);
		
		super.update(cell);
	}
}