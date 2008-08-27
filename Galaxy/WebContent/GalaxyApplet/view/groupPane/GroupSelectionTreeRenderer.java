// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane;

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

import model.Groups;
import model.server.ImageManager;

/** Class for rendering a check box tree */
public class GroupSelectionTreeRenderer extends JPanel implements TreeCellRenderer
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
    		pane.add(new JLabel(new ImageIcon(ImageManager.getImage("Groups"))));

    	// Constructs the group pane
    	else
    	{
    		// Retrieve info on if the group is selected or inferred
    		boolean groupSelected = ((GroupSelectionTree)tree).isPathSelected(tree.getPathForRow(row));
     		boolean groupInferred = ((GroupSelectionTree)tree).isPathInferred(tree.getPathForRow(row));

     		// Construct the check box
     		JCheckBox checkbox = new JCheckBox();
     		checkbox.setSelected(groupSelected||groupInferred);
    		checkbox.setBorder(new EmptyBorder(1,0,1,0));
    		checkbox.setOpaque(false);
    		checkbox.setEnabled(!groupInferred);
       		pane.add(checkbox);
     	}
    	
    	// Get the label text
    	Object userObject = ((DefaultMutableTreeNode)object).getUserObject();
 		String text = userObject.toString();
		if(userObject instanceof Integer)
			text = Groups.getGroup((Integer)userObject).getName();

	   	// Construct the label
		JLabel label = new JLabel(" " + text);
		label.setFont(defaultFont);
		pane.add(label);
    	
		// Return the pane
    	return pane;
    }
}