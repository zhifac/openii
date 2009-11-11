// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.tagPane;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import org.mitre.galaxy.model.Tags;
import org.mitre.galaxy.model.server.ImageManager;

/** Class for rendering a check box tree */
public class TagSelectionTreeRenderer extends JPanel implements TreeCellRenderer
{
	// Defines the fonts
	static public Font defaultFont = new Font(null,Font.PLAIN,12);
	
    /** Render the check box tree node */
    public Component getTreeCellRendererComponent(JTree tree, Object object, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
    	// Initialize the pane
    	JPanel pane = new JPanel();
    	pane.setOpaque(false);
		pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
    	
    	// Constructs the header
    	if(row==0)
    		pane.add(new JLabel(new ImageIcon(ImageManager.getImage("Tags"))));

    	// Constructs the tag pane
    	else
    	{
    		// Retrieve info on if the tag is selected or inferred
    		boolean tagSelected = ((TagSelectionTree)tree).isPathSelected(tree.getPathForRow(row));
     		boolean tagInferred = ((TagSelectionTree)tree).isPathInferred(tree.getPathForRow(row));

     		// Construct the check box
     		JCheckBox checkbox = new JCheckBox();
     		checkbox.setSelected(tagSelected||tagInferred);
    		checkbox.setBorder(new EmptyBorder(1,0,1,0));
    		checkbox.setOpaque(false);
    		checkbox.setEnabled(!tagInferred);
       		pane.add(checkbox);
     	}
    	
    	// Get the label text
    	Object userObject = ((DefaultMutableTreeNode)object).getUserObject();
 		String text = userObject.toString();
		if(userObject instanceof Integer)
			text = Tags.getTag((Integer)userObject).getName();

	   	// Construct the label
		JLabel label = new JLabel(" " + text);
		label.setFont(defaultFont);
		pane.add(label);
    	
		// Return the pane
    	return pane;
    }
}