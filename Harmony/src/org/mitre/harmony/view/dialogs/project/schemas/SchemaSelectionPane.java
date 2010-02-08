package org.mitre.harmony.view.dialogs.project.schemas;

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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.Harmony;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.porters.SchemaImporterDialog;
import org.mitre.schemastore.model.Schema;

/** Class for allowing the selection of schemas */
class SchemaSelectionPane extends JPanel implements ActionListener
{
	/** Comparator used to alphabetize schemas */
	private class SchemaComparator implements Comparator<Schema>
	{
		public int compare(Schema schema1, Schema schema2)
			{ return schema1.getName().toLowerCase().compareTo(schema2.getName().toLowerCase()); }	
	}
	
	/** Stores the harmony model */
	private HarmonyModel harmonyModel;
	
	/** Set of check boxes containing the schema selection */
	private JPanel schemaList = null;

	/** Create the schema list */
	private void generateSchemaList()
	{
		// Get the list available schemas
		ArrayList<Schema> schemas = harmonyModel.getSchemaManager().getSchemas();
		Collections.sort(schemas, new SchemaComparator());

		// Get the list of deletable schemas
		ArrayList<Integer> deletableSchemas = harmonyModel.getSchemaManager().getDeletableSchemas();
		deletableSchemas.removeAll(harmonyModel.getProjectManager().getSchemaIDs());
		
		// Generate the list of schemas
		schemaList.removeAll();
		for(Schema schema : schemas)
		{
			boolean deletable = deletableSchemas.contains(schema.getId());
			schemaList.add(new SchemaSelectionItem(schema,deletable,harmonyModel));
		}
		revalidate(); repaint();
	}
	
	/** Constructs the Schema List pane */
	SchemaSelectionPane(HarmonyModel harmonyModel)
	{			
		this.harmonyModel = harmonyModel;
		
		// Create the schema list
		schemaList = new JPanel();
		schemaList.setOpaque(false);
		schemaList.setLayout(new BoxLayout(schemaList,BoxLayout.Y_AXIS));
		generateSchemaList();

		// Force schema list to not spread out
		JPanel schemaListPane = new JPanel();
		schemaListPane.setBackground(Color.white);
		schemaListPane.setLayout(new BorderLayout());
		schemaListPane.add(schemaList,BorderLayout.NORTH);
		
		// Create a scroll pane to hold the list of schemas
		JScrollPane schemaScrollPane = new JScrollPane(schemaListPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		schemaScrollPane.setPreferredSize(new Dimension(250, 200));

		// Creates the schema selection pane
		setBorder(new EmptyBorder(0,0,0,5));
		setLayout(new BorderLayout());
		add(schemaScrollPane,BorderLayout.CENTER);
		if(harmonyModel.getBaseFrame() instanceof Harmony) 
		{
			// Create the import schema button
			JButton button = new JButton("Import Schema");
			button.setFocusable(false);
			button.addActionListener(this);

			// Create a button pane
			JPanel pane = new JPanel();
			pane.setLayout(new BorderLayout());
			pane.setBorder(new EmptyBorder(5,0,0,0));
			pane.add(button,BorderLayout.CENTER);
			add(pane,BorderLayout.SOUTH);
		}
	}
	
	/** Deletes the specified schema */
	void deleteSchemaItem(SchemaSelectionItem item)
		{ generateSchemaList(); revalidate(); repaint(); }
	
	/** Returns the list of selected schemas */
	ArrayList<Schema> getSelectedSchemas()
	{
		ArrayList<Schema> schemas = new ArrayList<Schema>();
		for(Component component : schemaList.getComponents())
		{
			SchemaSelectionItem item = (SchemaSelectionItem)component;
			if(item.isSelected()) schemas.add(item.getSchema());
		}
		return schemas;
	}
	
	/** Set the specified schema selection */
	void schemaSelected(Integer schemaID, boolean selected)
	{
		for(int i=0; i<schemaList.getComponentCount(); i++)
		{
			SchemaSelectionItem item = (SchemaSelectionItem)schemaList.getComponent(i);
			if(item.getSchema().getId().equals(schemaID))
				{ item.setSelected(selected); break; }
		}
	}	
	
	/** Handles the import of a schema */
	public void actionPerformed(ActionEvent e)
	{
		// Identify the dialog of which this pane is part of
		Component parent = getParent();
		while(!(parent instanceof JDialog))
			parent = parent.getParent();
			
		// Launch the importer dialog
		SchemaImporterDialog dialog = new SchemaImporterDialog((JDialog)parent, harmonyModel);
		while(dialog.isDisplayable()) try { Thread.sleep(500); } catch(Exception e2) {}

		// Update the list of schemas if a new schema was imported
		if(dialog.isSuccessful())
		{
			harmonyModel.getSchemaManager().initSchemas();
			generateSchemaList();
		}
	}
}