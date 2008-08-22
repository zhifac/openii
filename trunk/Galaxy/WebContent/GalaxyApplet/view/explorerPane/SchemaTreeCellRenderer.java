// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

import view.sharedComponents.LinkedTreeNodeRenderer;

import model.AliasedSchemaElement;
import model.Schemas;
import model.SelectedObjects;

/** Class for rendering schema components */
class SchemaTreeCellRenderer extends LinkedTreeNodeRenderer
{
	/** Returns if this schema element has any new children for specified schema */
	private boolean hasNewChildren(SchemaElement schemaElement, Integer schemaID)
	{
		HierarchicalGraph graph = Schemas.getGraph(schemaID);
		
		// Checks for new children in entities
		if(schemaElement instanceof Entity)
			for(Attribute attribute : graph.getAttributes(((Entity)schemaElement).getId()))
				if(schemaID.equals(attribute.getBase()) || hasNewChildren(attribute,schemaID))
					return true;
		
		// Checks for new children in attributes
		if(schemaElement instanceof Domain)
			for(DomainValue domainValue : graph.getDomainValuesForDomain(((Domain)schemaElement).getId()))
				if(schemaID.equals(domainValue.getBase()))
					return true;
		
		// Indicates that no new children were found
		return false;
	}
	
	/** Gets the label for the specified node */
	public JLabel getLabel(Object userObject)
	{
		// Determine how schema component should be rendered
		boolean isNewElement = false;
		boolean hasNewChildren = false;
		if(userObject instanceof AliasedSchemaElement)
		{
			SchemaElement schemaElement = ((AliasedSchemaElement)userObject).getElement();
			isNewElement = SelectedObjects.getSelectedSchema().equals(schemaElement.getBase());
			hasNewChildren = !isNewElement && hasNewChildren(schemaElement, SelectedObjects.getSelectedSchema());
		}
		
		// Determine the name that needs to be displayed
		String name = "";
		if(userObject instanceof Integer) name = Schemas.getSchema((Integer)userObject).getName();
		else if(userObject instanceof AliasedSchemaElement) name = ((AliasedSchemaElement)userObject).getName();
		else name = userObject.toString();
		
		// Constructs the label
		JLabel label = new JLabel(name+(hasNewChildren?"*":""));
		label.setBorder(new EmptyBorder(0,3,0,3));
		label.setFont(isNewElement ? newFont : defaultFont);
		return label;
	}
	
	/** Get the icon to use for this schema component */
	public String getIconName(Object userObject)
	{
		String iconName = "Schema";
		if(userObject instanceof AliasedSchemaElement)
		{
			if(((AliasedSchemaElement)userObject).getElement() instanceof Attribute)
				iconName = "Attribute";
			else iconName = "SchemaElement";
		}
		else if(userObject instanceof String)
		{
			String text = (String)userObject;
			if(text.equals("Entities") || text.equals("Relationships") || text.equals("Domains"))
				iconName = "SchemaElements";
			else if(text.equals("Parents"))
				iconName = "Parents";
			else if(text.equals("Children"))
				iconName = "Children";
		}
		return iconName;
	}
	
	/** Gets the background color for the specified node */
	public Color getBackgroundColor(Object userObject)
	{
		if(SelectedObjects.getSelectedComparisonSchema()!=null)
		{
			if(userObject.equals(SelectedObjects.getSelectedSchema()))
				return Color.yellow;
			else if(userObject.equals(SelectedObjects.getSelectedComparisonSchema()))
				return Color.orange;
		}
		return Color.white;
	}
}