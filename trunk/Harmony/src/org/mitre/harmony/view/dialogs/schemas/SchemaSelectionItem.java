package org.mitre.harmony.view.dialogs.schemas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.mitre.harmony.Harmony;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.Link;
import org.mitre.schemastore.model.Schema;

/** Schema selection item class */
class SchemaSelectionItem extends JPanel implements ActionListener
{
	/** Stores the schema associated with this check box */
	private Schema schema;
	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Stores the check box associated with the schema */
	private JCheckBox checkbox;
	
	/** Stores the delete link associated with the schema */
	private Link deleteLink = null;
	
	/** Constructs the schema check box */
	SchemaSelectionItem(Schema schema, boolean deletable, HarmonyModel harmonyModel)
	{
		this.schema = schema;
		this.harmonyModel = harmonyModel;
			
		// Initialize the check box
		checkbox = new JCheckBox(schema.getName());
		checkbox.setOpaque(false);
		checkbox.setFocusable(false);
		checkbox.addActionListener(this);
		
		// Initialize the delete link
		if(deletable && harmonyModel.getBaseFrame() instanceof Harmony) deleteLink = new Link("Delete",this);
		
		// Constructs the check box pane
		JPanel checkboxPane = new JPanel();
		checkboxPane.setOpaque(false);
		checkboxPane.setLayout(new BoxLayout(checkboxPane,BoxLayout.X_AXIS));
		checkboxPane.add(checkbox);
		if(deleteLink!=null) checkboxPane.add(deleteLink);
		
		// Constructs the schema check box
		setOpaque(false);
		setLayout(new BorderLayout());
		add(checkboxPane,BorderLayout.WEST);
	}
	
	/** Returns the schema associated with this check box */
	Schema getSchema()
		{ return schema; }
	
	/** Sets the selection of the schema check box */
	void setSelected(boolean selected)
		{ checkbox.setSelected(selected); }
	
	/** Handles actions occurring to the schema selection item */
	public void actionPerformed(ActionEvent e)
	{
		// Handles changes to the check box
		if(e.getSource()==checkbox)
		{
			// Retrieve the schema dialog
			Component component = getParent();
			while(!(component instanceof SchemaDialog))
				component = component.getParent();
			SchemaDialog dialog = (SchemaDialog)component;
			
			// Inform the schema dialog that a schema has been selected/unselected
			if(checkbox.isSelected())
				dialog.selectSchema(schema.getId());
			else dialog.unselectSchema(schema.getId());
		}
		
		// Handles selection of the delete link
		else if(e.getSource()==deleteLink)
		{
			// Verify that the schema should be deleted
			int reply = JOptionPane.showConfirmDialog(null,"This action cannot be reversed!  Are you certain that you would like to delete \"" + schema.getName() + "\"","Delete Schema",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
			if(reply==0)
			{
				// Get the schema selection pane
				Component component = getParent();
				while(!(component instanceof SchemaSelectionPane))
					component = component.getParent();
				SchemaSelectionPane pane = (SchemaSelectionPane)component;
				
				// Delete the schema
				if(harmonyModel.getSchemaManager().deleteSchema(schema.getId()))
				{
					pane.schemaList.remove(this);
					pane.generateSchemaList();
					pane.revalidate(); pane.repaint();
				}
			}
		}
	}
}