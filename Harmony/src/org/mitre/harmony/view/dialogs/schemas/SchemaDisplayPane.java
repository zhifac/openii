package org.mitre.harmony.view.dialogs.schemas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.model.SchemaManager;
import org.mitre.harmony.model.selectedInfo.SelectedInfo;
import org.mitre.harmony.view.dialogs.UnderlinedLabel;
import org.mitre.schemastore.model.Schema;

/** Class for handling the display of schemas */
public class SchemaDisplayPane extends JPanel
{	
	/** List of schemas with display options */
	private JPanel schemaList = null;
	
	/** Constructs the schema display pane */
	public SchemaDisplayPane()
	{
		// Create the header
		JLabel col1Label = new UnderlinedLabel("Left");
		JLabel col2Label = new UnderlinedLabel("Right");
		JLabel col3Label = new UnderlinedLabel("Schema");
		JPanel headerPane = new SchemaDisplayRow(new JLabel(""), col1Label, col2Label, col3Label);
		
		// Create the schema item list
		schemaList = new JPanel();
		schemaList.setBackground(Color.white);
		schemaList.setLayout(new BoxLayout(schemaList,BoxLayout.Y_AXIS));
		
		// Create the schema scroll pane
		JScrollPane schemaScrollPane = new JScrollPane(schemaList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		schemaScrollPane.setBorder(null);
		schemaScrollPane.setPreferredSize(new Dimension(250, 200));
		
		// Create the schema pane
		setBackground(Color.white);
		setBorder(new CompoundBorder(new LineBorder(Color.gray),new EmptyBorder(1,2,1,2)));
		setLayout(new BorderLayout());
		add(headerPane,BorderLayout.NORTH);
		add(schemaScrollPane,BorderLayout.CENTER);
	}
	
	/** Adds a schema to the pane */
	public void selectSchema(Integer schemaID)
	{
		Schema schema = SchemaManager.getSchema(schemaID);
		int loc = 0;
		for(loc=0; loc<schemaList.getComponentCount(); loc++)
		{
			SchemaDisplayItem item = (SchemaDisplayItem)schemaList.getComponent(loc);
			if(item.getSchema().getName().toLowerCase().compareTo(schema.getName().toLowerCase())>0)
				break;
		}
		schemaList.add(new SchemaDisplayItem(schema),loc);
		revalidate(); repaint();
	}

	/** Removes a schema from the pane */
	public void unselectSchema(Integer schemaID)
	{
		for(int i=0; i<schemaList.getComponentCount(); i++)
		{
			SchemaDisplayItem item = (SchemaDisplayItem)schemaList.getComponent(i);
			if(item.getSchema().getId().equals(schemaID))
				{ schemaList.remove(i); break; }
		}
		revalidate(); repaint();
	}
	
	/** Saves the schema display settings */
	void save()
	{
		// Gather up the left and right schemas
		ArrayList<Integer> leftSchemas = new ArrayList<Integer>();
		ArrayList<Integer> rightSchemas = new ArrayList<Integer>();
		for(int loc=0; loc<schemaList.getComponentCount(); loc++)
		{
			SchemaDisplayItem item = (SchemaDisplayItem)schemaList.getComponent(loc);
			if(item.getSide()==SchemaDisplayItem.LEFT)
				leftSchemas.add(item.getSchema().getId());
			if(item.getSide()==SchemaDisplayItem.RIGHT)
				rightSchemas.add(item.getSchema().getId());
		}

		// Update the selected schemas
		SelectedInfo.setSelectedSchemas(leftSchemas, rightSchemas);
	}
}