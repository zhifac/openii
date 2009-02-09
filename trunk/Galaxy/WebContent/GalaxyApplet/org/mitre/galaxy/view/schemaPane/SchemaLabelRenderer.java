// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.schemaPane;

import java.awt.BasicStroke;
import java.awt.Image;

import org.mitre.galaxy.model.Schemas;
import org.mitre.galaxy.model.SelectedObjects;
import org.mitre.galaxy.model.search.SearchManager;
import org.mitre.galaxy.model.server.ImageManager;


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
		// Generate the text for schema node
		Object object = ((NodeItem)item).get("SchemaObject");
		if(object instanceof Integer)
		{
			String text = Schemas.getSchema((Integer)object).getName();
			Integer comparisonSchemaID = SelectedObjects.getSelectedComparisonSchema();
			if(comparisonSchemaID != null)
				text += " (vs "+Schemas.getSchema(comparisonSchemaID).getName()+")";
			return text;
		}

		// Generate the text for schema element nodes
		return ((SchemaTreeObject)object).getName();
	}

	/** Returns the stroke used in the schema label border */
	protected BasicStroke getStroke(VisualItem item)
	{
		Object object = ((NodeItem)item).get("SchemaObject");
		boolean selectedItem = false;
		if(object instanceof SchemaTreeObject)
		{
			for(Integer id : ((SchemaTreeObject)object).getIDs())
				if(SearchManager.containsElement(id))
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
