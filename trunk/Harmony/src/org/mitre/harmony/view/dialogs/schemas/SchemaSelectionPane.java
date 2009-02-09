package org.mitre.harmony.view.dialogs.schemas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.mitre.harmony.model.MappingManager;
import org.mitre.harmony.model.SchemaManager;
import org.mitre.harmony.view.dialogs.Link;
import org.mitre.harmony.view.dialogs.TitledPane;
import org.mitre.harmony.view.dialogs.importer.ImporterDialog;
import org.mitre.schemastore.model.Schema;

/** Class for allowing the selection of schemas */
public class SchemaSelectionPane extends JPanel
{
	/** Set of check boxes containing the schema selection */
	JPanel schemaList = null;

	/** Comparator used to alphabetize schemas */
	private class SchemaComparator implements Comparator<Schema>
	{
		public int compare(Schema schema1, Schema schema2)
			{ return schema1.getName().toLowerCase().compareTo(schema2.getName().toLowerCase()); }	
	}

	/** Create the schema list */
	public void generateSchemaList()
	{
		// Get the list available schemas
		ArrayList<Schema> schemas = SchemaManager.getSchemas();
		Collections.sort(schemas, new SchemaComparator());

		// Get the list of deletable schemas
		ArrayList<Integer> deletableSchemas = SchemaManager.getDeletableSchemas();
		deletableSchemas.removeAll(MappingManager.getSchemas());
		
		// Generate the list of schemas
		schemaList.removeAll();
		for(Schema schema : schemas)
		{
			boolean deletable = deletableSchemas.contains(schema.getId());
			schemaList.add(new SchemaSelectionItem(schema,deletable));
		}
		revalidate(); repaint();
	}
	
	/** Constructs the Schema List pane */
	public SchemaSelectionPane()
	{			
		// Create the schema list
		schemaList = new JPanel();
		schemaList.setBackground(Color.white);
		schemaList.setLayout(new BoxLayout(schemaList,BoxLayout.Y_AXIS));
		generateSchemaList();
			
		// Create a scroll pane to hold the list of schemas
		JScrollPane schemaScrollPane = new JScrollPane(schemaList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		schemaScrollPane.setPreferredSize(new Dimension(250, 200));

		// Creates the schema selection pane
		Link link = new Link("Import",new ImportSchema());
		setLayout(new BorderLayout());
		add(new TitledPane("Schemas ",link,schemaScrollPane),BorderLayout.CENTER);
	}
	
	/** Set the specified schema selection */
	public void schemaSelected(Integer schemaID, boolean selected)
	{
		for(int i=0; i<schemaList.getComponentCount(); i++)
		{
			SchemaSelectionItem item = (SchemaSelectionItem)schemaList.getComponent(i);
			if(item.getSchema().getId().equals(schemaID))
				{ item.setSelected(selected); break; }
		}
	}
	
	/** Class for handling the import of a schema */
	private class ImportSchema implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// Identify the dialog of which this pane is part of
			Component parent = getParent();
			while(!(parent instanceof JDialog))
				parent = parent.getParent();
				
			// Launch the importer dialog
			ImporterDialog dialog = new ImporterDialog((JDialog)parent);
			while(dialog.isDisplayable()) try { Thread.sleep(500); } catch(Exception e2) {}

			// Update the list of schemas if a new schema was imported
			Integer schemaID = dialog.getSchemaID();
			if(schemaID!=null)
			{
				SchemaManager.initSchemas();
				generateSchemaList();
			}
		}
	}
}