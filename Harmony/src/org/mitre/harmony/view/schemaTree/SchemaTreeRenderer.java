// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.schemaTree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.mitre.harmony.model.SchemaManager;
import org.mitre.harmony.model.filters.Filters;
import org.mitre.harmony.model.preferences.Preferences;
import org.mitre.harmony.model.selectedInfo.SelectedInfo;
import org.mitre.harmony.view.dialogs.Link;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 * Renders schema tree node
 * @author CWOLF
 */
class SchemaTreeRenderer extends DefaultTreeCellRenderer
{
	// Fonts used for displaying schema tree node text
	static private Font ital = new Font("Dialog", Font.ITALIC,12);
	static private Font bold = new Font("Dialog", Font.BOLD, 12);
	
    // Icons used in displaying schema tree nodes
	static private Icon schemaIcon = getIcon("Schema");
	static private Icon schemaElementIcon = getIcon("SchemaElement");
	static private Icon finishedSchemaElementIcon = getIcon("FinishedSchemaElement");
	static private Icon attributeIcon = getIcon("Attribute");
	static private Icon finishedAttributeIcon = getIcon("FinishedAttribute");
	
	/** Defines highlight color used in displaying highlighted schema tree nodes */
	static private Color highlightColor = new Color(0xFFFF66);

	/** Retrieve the specified icon */
	static private Icon getIcon(String name)
	{
		URL url = SchemaTreeRenderer.class.getResource("/org/mitre/harmony/view/graphics/"+name+".jpg");
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
	}
	
	/** Returns the rendered schema tree nodes */
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean highlighted, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		// Keeps track of various display states of node
		boolean isSelected = false;
		boolean isFocused = true;
		boolean isFinished = false;
		
		// Retrieves the object to be rendered
		Object obj = ((DefaultMutableTreeNode)value).getUserObject();
		
		// Handle root nodes, the most simple case
		if (row == 0)
		{
			JPanel schemaLabelPane = new JPanel();
			schemaLabelPane.setBackground(Color.white);
			schemaLabelPane.add(new JLabel("Schemas"));
			schemaLabelPane.add(new Link("Edit Schemas",null));
			return schemaLabelPane;
		}
		
		// Handles rendering of schema nodes
		else if (obj instanceof Integer) 
		{
			// Gets schema node and associated schema tree
			Integer elementID = (Integer)obj;
			SchemaElement element = SchemaManager.getSchemaElement(elementID);
			SchemaTree schemaTree = (SchemaTree)tree;
			
			// Get the schema element domain
			Integer schemaID = SchemaTree.getSchema((DefaultMutableTreeNode)value);
			HierarchicalGraph graph = SchemaManager.getGraph(schemaID);
			Domain domain = graph.getDomainForElement(element.getId());

			// Determine the display state of the node
			isFocused = Filters.visibleNode(schemaTree.getRole(),(DefaultMutableTreeNode)value);
			isSelected = SelectedInfo.isElementSelected(elementID,schemaTree.getRole());
			isFinished = Preferences.isFinished(schemaID,elementID);

			// Set the text and icon
			String text = "<html>" + graph.getDisplayName(element.getId());
			if(Preferences.getShowSchemaTypes() && domain!=null)
				text += " <font color='#888888'>(" + domain.getName() + ")</font></html>";
			setText(text);
			if(domain!=null) setIcon(isFinished ? finishedAttributeIcon : attributeIcon);
			else setIcon(isFinished ? finishedSchemaElementIcon : schemaElementIcon);
		}
		
		// Handles any other rows that need rendering
		else
		{
			// Determine if the node is in focus
			SchemaTree schemaTree = (SchemaTree)tree;
			isFocused = Filters.getFocus(schemaTree.getRole())==null;
			
			// Set the text and icon
			setText("  "  + obj);
			setIcon(schemaIcon);
		}

		// Adjust the settings based on the state of the node
		selected = isSelected;
		setFont(isFocused ? bold : ital);
		setBackgroundNonSelectionColor(highlighted ? highlightColor : Color.white);
		
		// Returns the rendered node
		return this;
	}
}