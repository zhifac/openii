// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.explorerPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.mitre.galaxy.model.Schemas;
import org.mitre.galaxy.model.SelectedObjects;
import org.mitre.galaxy.model.server.ImageManager;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;


/** Class for rendering schema components */
class SchemaTreeCellRenderer extends DefaultTreeCellRenderer
{
	static public Font defaultFont = new Font(null,Font.PLAIN,12);
	static public Font newFont = new Font(null,Font.BOLD,12);
	
	/** Returns if this schema element has any new children for specified schema */
	private boolean hasNewChildren(SchemaElement schemaElement, Integer schemaID)
	{
		HierarchicalSchemaInfo schemaInfo = Schemas.getSchemaInfo(schemaID);
		
		// Checks for new children in entities
		if(schemaElement instanceof Entity)
			for(Attribute attribute : schemaInfo.getAttributes(((Entity)schemaElement).getId()))
				if(schemaID.equals(attribute.getBase()) || hasNewChildren(attribute,schemaID))
					return true;
		
		// Checks for new children in attributes
		if(schemaElement instanceof Domain)
			for(DomainValue domainValue : schemaInfo.getDomainValuesForDomain(((Domain)schemaElement).getId()))
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
			SchemaElement element = ((AliasedSchemaElement)userObject).getElement();
			if(element instanceof Attribute || element instanceof DomainValue)
				iconName = "DependantElement";
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
	
	/** Handles the rendering of a schema tree cell node */
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		// Fetch the user object
		SchemaTreeNode node = (SchemaTreeNode)value;
		Object userObject = node.getUserObject();

		// Constructs the icon
		String iconName = getIconName(userObject);
		Icon icon = iconName==null ? null : new ImageIcon(ImageManager.getImage(iconName));
			
		// Construct the rendered linked node
		JPanel pane = new JPanel();
		pane.setBackground(getBackgroundColor(userObject));
		pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
		pane.add(new JLabel(icon));
		pane.add(getLabel(userObject));
		return pane;
	}
}