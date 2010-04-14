// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.schema;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.porters.ExportSchemaDialog;
import org.mitre.harmony.view.dialogs.porters.ImportSchemaDialog;
import org.mitre.harmony.view.dialogs.widgets.AbstractButtonPane;
import org.mitre.schemastore.model.Schema;

/**
 * Displays the schema dialog
 * @author CWOLF
 */
public class SchemaDialog extends JDialog implements ListSelectionListener
{
	/** Comparator used to alphabetize schemas */
	private class SchemaComparator implements Comparator<Object>
	{
		public int compare(Object schema1, Object schema2)
			{ return schema1.toString().compareTo(schema2.toString()); }	
	}
	
	/** Private class for defining the button pane */
	private class ButtonPane extends AbstractButtonPane
	{
		/** Constructs the button pane */
		private ButtonPane()
		{
			super(new String[]{"Import", "Export", "Delete", "Close"},1,4);
			setEnabled("Export", false);
			setEnabled("Delete", false);
		}

		/** Handles selection of button */
		protected void buttonPressed(String label)
		{		
			// Handles the import of a schema
			if(label.equals("Import"))
			{
				ImportSchemaDialog dialog = new ImportSchemaDialog(SchemaDialog.this, harmonyModel);
				while(dialog.isDisplayable()) try { Thread.sleep(500); } catch(Exception e2) {}
				if(dialog.isSuccessful()) updateSchemaList();
			}
			
			// Handles the export of a schema
			else if(label.equals("Export"))
				new ExportSchemaDialog(getSchema()).export(harmonyModel);
			
			// Handles the deletion of a schema
			else if(label.equals("Delete"))
			{
				Schema schema = getSchema();
				int reply = JOptionPane.showConfirmDialog(SchemaDialog.this,"This action cannot be reversed!  Are you certain that you would like to delete \"" + schema.getName() + "\"","Delete Schema",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				if(reply==0)
					if(harmonyModel.getSchemaManager().deleteSchema(schema.getId()))
						updateSchemaList();
			}
				
			// Close down the search pane if "Close" selected
			else if(label.equals("Close")) dispose();
		}
	}

	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Stores locked schemas (can't be deleted) */
	private ArrayList<Integer> lockedSchemas;
	
	/** Stores the schema list */
	private JList schemaList = null;
	
	/** Stores the button pane */
	private ButtonPane buttonPane = null;
	
	/** Updates the schema list */
	private void updateSchemaList()
	{
		Vector<Schema> schemas = new Vector<Schema>(harmonyModel.getSchemaManager().getSchemas());
		Collections.sort(schemas, new SchemaComparator());		
		schemaList.setListData(schemas);
	}
	
	/** Generate schema list */
	private JScrollPane getSchemaList()
	{
		// Initialize the schema list
		schemaList = new JList();
		updateSchemaList();
		schemaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		schemaList.addListSelectionListener(this);
		
		// Create a scroll pane to hold the project list
		JScrollPane schemaScrollPane = new JScrollPane(schemaList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		schemaScrollPane.setPreferredSize(new Dimension(130,200));
		return schemaScrollPane;
	}
	
	/** Initializes the schema dialog */
	public SchemaDialog(HarmonyModel harmonyModel, ArrayList<Integer> lockedSchemas)
	{
		super(harmonyModel.getBaseFrame());
		this.harmonyModel = harmonyModel;
		this.lockedSchemas = lockedSchemas;
		
		// Constructs the content pane 
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(10,10,0,10));
		pane.setLayout(new BorderLayout());
		pane.add(getSchemaList(), BorderLayout.CENTER);
		pane.add(buttonPane = new ButtonPane(), BorderLayout.SOUTH);
		
		// Set up loader dialog layout and contents
		setTitle("Schema Manager");
		setModal(true);
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(pane);
		pack();
		setLocationRelativeTo(harmonyModel.getBaseFrame());
		setVisible(true);
   	}

	/** Retrieves the selected schema */
	private Schema getSchema()
		{ return (Schema)schemaList.getSelectedValue(); }
	
	/** Handles changes to the selected schema */
	public void valueChanged(ListSelectionEvent e)
	{
		// Retrieve the schema and information on if it can be deleted
		Schema schema = getSchema();
		boolean deletable = schema!=null && harmonyModel.getSchemaManager().getDeletableSchemas().contains(schema.getId());
		deletable = deletable && !harmonyModel.getProjectManager().getSchemaIDs().contains(schema.getId());
		deletable = deletable && !lockedSchemas.contains(schema.getId());
		
		// Update the ability to export and delete the schema
		buttonPane.setEnabled("Export",schema!=null);
		buttonPane.setEnabled("Delete",deletable);
	}
}
