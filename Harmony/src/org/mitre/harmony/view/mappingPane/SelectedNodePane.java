// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.mappingPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.selectedInfo.SelectedInfoListener;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 * Pane used to display source or target node info in the NodeInfoPane
 * @author CWOLF
 */
class SelectedNodePane extends JPanel implements SelectedInfoListener
{
	/** Defines the color for the blue background */
	static private final Color BLUE = new Color(100,100,255);
	
	/** Tracks the side associated with this pane */
	private Integer side = null;
	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Stores the title component associated with this pane */
	private JLabel titlePane = new JLabel();
	
	/** Stores the text component associated with this pane */
	private JTextPane textPane = new JTextPane();
	
	/** Scrub the text to work within html */
	private String scrub(String text)
		{ return text.replace("<","&lt;").replace(">","&gt;"); }
	
	/** Constructs the Selected Node pane */
	SelectedNodePane(Integer side, HarmonyModel harmonyModel)
	{
		this.side = side;
		this.harmonyModel = harmonyModel;
		
		// Initialize the title pane
		titlePane = new JLabel();
		titlePane.setBorder(new EmptyBorder(2,4,2,4));
		titlePane.setForeground(Color.white);
		titlePane.setOpaque(false);
		
		// Initialize the text pane
		textPane.setBackground(Color.white);
		textPane.setContentType("text/html");
	
		// Create a scroll pane in which to place text pane
		JScrollPane scrollPane = new JScrollPane(textPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		// Initialize the selected node pane
		setBackground(Color.white);
		setBorder(new EmptyBorder(2,2,2,2));
		setLayout(new BorderLayout());
		setOpaque(false);
		setVisible(false);
		add(titlePane,BorderLayout.NORTH);
		add(scrollPane,BorderLayout.CENTER);
		
		// Add listeners to monitor for changes to the selected elements
		harmonyModel.getSelectedInfo().addListener(this);
	}

	/** Generates the text to display in the text pane */
	private String getText(SchemaElement element, String displayName)
	{
 		StringBuffer text = new StringBuffer();		
		
		// Display schema element name and description
		text.append("<b>Description</b>: " + scrub(element.getDescription()) + "<br>");

		// Collect domain information
		Domain domain = null;
		HashSet<DomainValue> domainValues = new HashSet<DomainValue>();
		for(Integer schemaID : harmonyModel.getSelectedInfo().getSchemas(side))
		{
			HierarchicalGraph graph = harmonyModel.getSchemaManager().getGraph(schemaID);
			if(domain==null) domain = graph.getDomainForElement(element.getId());
			domainValues.addAll(graph.getDomainValuesForElement(element.getId()));
		}
		
		// Display domain if found
		if(domain!=null)
		{
			text.append("<table cellpadding=0 cellspacing=0><tr><td valign=top nowrap><b>Domain</b>:&nbsp;</td><td>" + domain.getName());
			if(domainValues.size()>0)
			{
				text.append(" (");
				for(DomainValue domainValue : domainValues)
					text.append(scrub(domainValue.getName()) + ", ");
				text.delete(text.length()-2, text.length());
				text.append(")");
			}
			text.append("</td></tr></table>");
		}
		
		return text.toString();
	}
	
	/** Activates visibility of this pane */
	public void displayedElementModified(Integer role)
	{
		// Only proceed if referring modified displayed element is on same side as selected node pane
		if(!role.equals(this.side)) return;
		
		// Make the pane visible as needed
		Integer elementID = harmonyModel.getSelectedInfo().getDisplayedElement(role);
		if(elementID==null) { setVisible(false); return; }
		SchemaElement element = harmonyModel.getSchemaManager().getSchemaElement(elementID);
		setVisible(true);

		// Gather up names to display
		HashSet<String> names = new HashSet<String>();
		for(Integer schemaID : harmonyModel.getSelectedInfo().getSchemas(side))
		{
			HierarchicalGraph graph = harmonyModel.getSchemaManager().getGraph(schemaID);
			if(graph.containsElement(element.getId()))
				names.add(graph.getDisplayName(element.getId()));
		}
		
		// Generate the name to display
		String displayName = "";
		for(String name : names) displayName += name + ", ";
		if(displayName.length()>2) displayName = displayName.substring(0, displayName.length()-2);
		
		// Set the title and text panes
		titlePane.setText(scrub(displayName));
		textPane.setText("<html>"+getText(element,displayName)+"</html>");
	}
	
	// Unused event listeners
	public void selectedElementsModified(Integer role) {}
	public void selectedMappingCellsModified() {}
	public void selectedSchemasModified() {}
	
	/** Paints the border around the selected node pane */
	public void paint(Graphics g)
	{
		// Paint the border around the selected node pane
		g.setColor(BLUE);
		g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);		
		g.setColor(Color.black);
		g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
		g.setColor(BLUE);
		g.fillRect(0, 20, getWidth()-1, getHeight()-1);
		g.setColor(Color.black);
		g.drawLine(0, 20, 0, getHeight()-1);
		g.drawLine(getWidth()-1, 20, getWidth()-1, getHeight()-1);
		g.drawLine(0, getHeight()-1, getWidth()-1, getHeight()-1);

		// Display the internal components
		super.paint(g);
	}
}