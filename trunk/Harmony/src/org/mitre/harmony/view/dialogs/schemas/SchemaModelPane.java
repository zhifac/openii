package org.mitre.harmony.view.dialogs.schemas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.UnderlinedLabel;
import org.mitre.schemastore.model.Schema;

/** Class for handling the setting of the schema model */
public class SchemaModelPane extends JPanel
{	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** List of schemas with model settings */
	private JPanel schemaList = null;
	
	/** Constructs the schema model pane */
	public SchemaModelPane(HarmonyModel harmonyModel)
	{
		this.harmonyModel = harmonyModel;
		
		// Create the header
		JLabel col1Label = new UnderlinedLabel("Graph Model");
		JLabel col2Label = new UnderlinedLabel("Schema");
		JPanel headerPane = new SchemaModelRow(col1Label, col2Label);

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
		setBorder(new CompoundBorder(new LineBorder(Color.gray),new EmptyBorder(1,4,1,4)));
		setLayout(new BorderLayout());
		add(headerPane,BorderLayout.NORTH);
		add(schemaScrollPane,BorderLayout.CENTER);
	}
	
	/** Adds a schema to the pane */
	public void selectSchema(Integer schemaID)
	{
		Schema schema = harmonyModel.getSchemaManager().getSchema(schemaID);
		int loc = 0;
		for(loc=0; loc<schemaList.getComponentCount(); loc++)
		{
			SchemaModelItem item = (SchemaModelItem)schemaList.getComponent(loc);
			if(item.getSchema().getName().toLowerCase().compareTo(schema.getName().toLowerCase())>0)
				break;
		}
		schemaList.add(new SchemaModelItem(schema, harmonyModel),loc);
		revalidate(); repaint();
	}

	/** Removes a schema from the pane */
	public void unselectSchema(Integer schemaID)
	{
		for(int i=0; i<schemaList.getComponentCount(); i++)
		{
			SchemaModelItem item = (SchemaModelItem)schemaList.getComponent(i);
			if(item.getSchema().getId().equals(schemaID))
				{ schemaList.remove(i); break; }
		}
		revalidate(); repaint();
	}
	
	/** Saves the schema graph model settings */
	void save()
	{
		for(int loc=0; loc<schemaList.getComponentCount(); loc++)
		{
			SchemaModelItem item = (SchemaModelItem)schemaList.getComponent(loc);
			harmonyModel.getPreferences().setSchemaGraphModel(item.getSchema().getId(), item.getModel());
		}
	}
}