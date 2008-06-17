// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.schemaPane;

import java.awt.BasicStroke;
import java.awt.Image;

import org.mitre.schemastore.model.SchemaElement;

import model.AliasedSchemaElement;
import model.Schemas;
import model.SelectedObjects;
import model.search.SearchManager;
import model.server.ImageManager;

import prefuse.Constants;
import prefuse.render.LabelRenderer;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

/** Class for implementing the schema label renderer */
public class SchemaLabelRenderer extends LabelRenderer
{
	/** Returns the text to display for the schema label */
	protected String getText(VisualItem item)
	{
		String text = "";

		// Generate the text for this schema element
		Object object = ((NodeItem)item).get("SchemaObject");
		if(object instanceof Integer) text += Schemas.getSchema((Integer)object).getName();
		else text += ((AliasedSchemaElement)object).getName();

		// Display comparison information if needed
		Integer comparisonSchemaID = SelectedObjects.getSelectedComparisonSchema();
		if(comparisonSchemaID != null)
		{
			if(object instanceof Integer)
				text += " (vs "+Schemas.getSchema(comparisonSchemaID).getName()+")";
			else
			{
				AliasedSchemaElement aliasedElement = (AliasedSchemaElement)object;
				String comparisonName = Schemas.getSchemaElementName(comparisonSchemaID, aliasedElement.getId());
				if(!aliasedElement.getName().equals(comparisonName))
					text += " (" + comparisonName + ")";
			}
		}
		
		return text;
	}

	/** Returns the stroke used in the schema label border */
	protected BasicStroke getStroke(VisualItem item)
	{
		Object object = ((NodeItem)item).get("SchemaObject");
		boolean selectedItem = false;
		if(object instanceof AliasedSchemaElement)
		{
			AliasedSchemaElement aliasedElement = (AliasedSchemaElement)object;
			SchemaElement element = aliasedElement.getElement();
			SchemaElement alias = aliasedElement.getAlias();
			if(SearchManager.containsObject(element) || (alias!=null && SearchManager.containsObject(alias)))
				selectedItem = true;
		}
		return new BasicStroke(selectedItem ? 3 : 1);
	}

	/** Returns the image used in the schema label */
	protected Image getImage(VisualItem item)
	{
		Object object = ((NodeItem)item).get("SchemaObject");
		if(object instanceof Integer) return ImageManager.getImage("Schema");
		return null;
	}

	/** Constructs the schema label renderer */
	SchemaLabelRenderer()
	{
		super("SchemaObject");
		setVerticalImageAlignment(Constants.CENTER);
		setHorizontalPadding(4);
		setVerticalPadding(4);
	}
}
