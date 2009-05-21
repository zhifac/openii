// (c) The MITRE Corporation 2006
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

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.filters.Focus;
import org.mitre.harmony.model.search.SearchResult;
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
	static private Icon schemaIcon = getIcon("Schema.jpg");
	static private Icon schemaElementIcon = getIcon("SchemaElement.jpg");
	static private Icon finishedSchemaElementIcon = getIcon("FinishedSchemaElement.jpg");
	static private Icon attributeIcon = getIcon("Attribute.jpg");
	static private Icon finishedAttributeIcon = getIcon("FinishedAttribute.jpg");
	static private Icon hiddenRootIcon = getIcon("HiddenRoot.gif");
	static private Icon hiddenElementIcon = getIcon("HiddenElement.gif");
	
	/** Defines highlight color used in displaying highlighted schema tree nodes */
	static private Color nameHighlightColor = new Color(0xFFFF66);
	static private Color descHighlightColor = new Color(0xFFFFb3);

	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Retrieve the specified icon */
	static private Icon getIcon(String name)
	{
		URL url = SchemaTreeRenderer.class.getResource("/org/mitre/harmony/view/graphics/"+name);
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
	}
	
	/** Constructs the Schema Tree renderer */
	public SchemaTreeRenderer(HarmonyModel harmonyModel)
		{ this.harmonyModel = harmonyModel; }
	
	/** Returns the rendered schema tree nodes */
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean highlighted, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		// Keeps track of various display states of node
		boolean isSelected = false;
		boolean isFocused = true;
		boolean isFinished = false;
		
		// Keeps track if match for search
		SearchResult result = null;
		
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
			SchemaElement element = harmonyModel.getSchemaManager().getSchemaElement(elementID);
			SchemaTree schemaTree = (SchemaTree)tree;
			
			// Get the schema element domain
			Integer schemaID = SchemaTree.getSchema((DefaultMutableTreeNode)value);
			HierarchicalGraph graph = harmonyModel.getSchemaManager().getGraph(schemaID);
			Domain domain = graph.getDomainForElement(element.getId());

			// Sets the tool tip
			String tooltip = element.getDescription() + ((domain != null) ? " (Domain: " + domain.getName() + ")" : "");
			setToolTipText(tooltip);

			// Determine the display state of the node
			isFocused = harmonyModel.getFilters().isVisibleNode(schemaTree.getSide(),(DefaultMutableTreeNode)value);
			isSelected = harmonyModel.getSelectedInfo().isElementSelected(elementID,schemaTree.getSide());
			isFinished = harmonyModel.getPreferences().isFinished(schemaID,elementID);
			
			// Determine if the element is marked as hidden
			Focus focus = harmonyModel.getFilters().getFocus(schemaTree.getSide(), schemaID);
			boolean isHiddenRoot = focus!=null && focus.getHiddenIDs().contains(elementID);
			boolean isHiddenElement = focus!=null && focus.getHiddenElements().contains(elementID);
			
			// Set the text
			String name = graph.getDisplayName(element.getId());
			String text = "<html>" + name.replace("<","&lt;").replace(">","&gt;");
			if(harmonyModel.getPreferences().getShowSchemaTypes() && graph.getModel().getType(graph,element.getId()) != null)
				text += " <font color='#888888'>(" + graph.getModel().getType(graph,element.getId()) + ")</font>";
			text += "</html>";

			
			setText(text);
			
			// Set the icon
			if(isHiddenRoot) setIcon(hiddenRootIcon);
			else if(isHiddenElement) setIcon(hiddenElementIcon);
			else if(domain!=null) setIcon(isFinished ? finishedAttributeIcon : attributeIcon);
			else setIcon(isFinished ? finishedSchemaElementIcon : schemaElementIcon);

			// Retrieves if element is match for current search
			result = harmonyModel.getSearchManager().getResult(elementID, schemaTree.getSide());
		}
		
		// Handles any other rows that need rendering
		else
		{
			// Determine if the node is in focus
			SchemaTree schemaTree = (SchemaTree)tree;
			isFocused = harmonyModel.getFilters().getFocusCount(schemaTree.getSide())==0;
			
			// Set the text and icon
			setText("  "  + obj);
			setIcon(schemaIcon);
		}

		// Adjust the settings based on the state of the node
		selected = isSelected;
		setFont(isFocused ? bold : ital);
		setBackgroundNonSelectionColor(result==null ? Color.white : result.nameMatched() ? nameHighlightColor : descHighlightColor);
		
		// Returns the rendered node
		return this;
	}
}