// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.sharedComponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.server.ImageManager;

/** Abstract class for rendering linked tree nodes */
abstract public class LinkedTreeNodeRenderer extends DefaultTreeCellRenderer
{
	// Defines the fonts
	static public Font defaultFont = new Font(null,Font.PLAIN,12);
	static public Font italicFont = new Font(null,Font.ITALIC,11);
	static public Font newFont = new Font(null,Font.BOLD,12);
	static public Font linkFont = new Font(null,Font.BOLD,10);
	
	/** Handles the rendering of a linked node */
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		// Fetch the user object and link
		LinkedTreeNode node = (LinkedTreeNode)value;
		Object userObject = node.getUserObject();

		// Constructs the icon
		String iconName = getIconName(userObject);
		Icon icon = iconName==null ? null : new ImageIcon(ImageManager.getImage(iconName));
		
		// Constructs the link if exists
		JLabel link = null;
		if(node.containsLink())
		{
			link = new JLabel("("+node.getLinkString()+")");
			link.setForeground(Color.blue);
			link.setFont(linkFont);
		}
			
		// Construct the rendered linked node
		JPanel pane = new JPanel();
		pane.setBackground(getBackgroundColor(userObject));
		pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
		pane.add(new JLabel(icon));
		pane.add(getLabel(userObject));
		if(link!=null) pane.add(link);
		return pane;
	}
	
	/** Gets the label for the specified node */
	abstract public JLabel getLabel(Object userObject);
	
	/** Gets the icon for the specified node */
	abstract public String getIconName(Object userObject);
	
	/** Gets the background color for the specified node */
	abstract public Color getBackgroundColor(Object userObject);
}
