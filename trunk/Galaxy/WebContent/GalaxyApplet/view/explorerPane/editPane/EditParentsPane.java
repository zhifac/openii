// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane.editPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.Schema;

import view.sharedComponents.EditPaneInterface;

import model.Schemas;
import model.server.ServletConnection;

/** Class for editing parent schema information for a schema object */
class EditParentsPane extends EditPaneInterface implements ActionListener
{
	/** Private class for constructing a schema check box */
	private class SchemaCheckBox extends JCheckBox
	{
		/** Stores the schema associated with this check box */
		private Schema schema;
		
		/** Schema Check Box constructor */
		SchemaCheckBox(Schema schema)
		{
			super(schema.toString());
			this.schema = schema;
			setBorder(new EmptyBorder(2,3,2,2));
			setFocusable(false);
			setOpaque(false);
		}
		
		/** Gets the schema associated with this check box */
		Schema getSchema()
			{ return schema; }
	}
	
	/** Stores the schema to which parent schemas are being edited */
	private Schema schema;
	
	/** Keeps track of the currently selected parent schemas */
	private HashSet<Integer> parentSchemaIDs = new HashSet<Integer>();
	
	// Stores various pane objects
	private JPanel parentsPane = new JPanel();
	
	/** Updates the selected schemas */
	private void updateSelectedSchemas()
	{
		// Get ancestor schemas
		HashSet<Integer> ancestors = new HashSet<Integer>();
		for(Integer parentSchemaID : parentSchemaIDs)
			ancestors.addAll(ServletConnection.getAncestorSchemas(parentSchemaID));
		
		// Eliminate any parent schemas which are also an ancestor
		for(Integer parentSchemaID : new HashSet<Integer>(parentSchemaIDs))
			if(ancestors.contains(parentSchemaID))
				parentSchemaIDs.remove(parentSchemaID);
		
		// Mark the selected and ancestor schemas
		for(int i=0; i<parentsPane.getComponentCount(); i++)
		{
			SchemaCheckBox schemaCheckBox = (SchemaCheckBox)parentsPane.getComponent(i);
			Schema possSchema = schemaCheckBox.getSchema();
			schemaCheckBox.setSelected(parentSchemaIDs.contains(possSchema.getId()) || ancestors.contains(possSchema.getId()));
			schemaCheckBox.setEnabled(!ancestors.contains(possSchema.getId()));
		}
	}
	
	/** Constructs the Edit Parents Pane */
	EditParentsPane(Schema schema)
	{
		this.schema = schema;

		// Get the list of associated schemas
		ArrayList<Schema> associatedSchemas = new ArrayList<Schema>();
		for(Integer associatedSchemaID : Schemas.getAssociatedSchemas(schema.getId()))
			associatedSchemas.add(Schemas.getSchema(associatedSchemaID));
		Schemas.sort(associatedSchemas);
		
		// Generate the parents pane
		parentsPane.setBackground(Color.white);
		parentsPane.setLayout(new BoxLayout(parentsPane,BoxLayout.Y_AXIS));
		for(Schema parentSchema : associatedSchemas)
			if(parentSchema.getLocked())
			{
				SchemaCheckBox schemaCheckBox = new SchemaCheckBox(parentSchema);
				schemaCheckBox.addActionListener(this);
				parentsPane.add(schemaCheckBox);
			}
		
		// Generate the parents scroll pane
		JScrollPane parentsSPane = new JScrollPane();
		parentsSPane.setBackground(Color.white);
		parentsSPane.setPreferredSize(new Dimension(100,125));
		parentsSPane.setViewportView(parentsPane);
		parentsSPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		parentsSPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Generate the Edit Parents Pane
		setBorder(new EmptyBorder(2,0,0,0));
		setLayout(new BorderLayout());
		setOpaque(false);
		add(parentsSPane,BorderLayout.CENTER);
		
		// Updates the selected schemas
		parentSchemaIDs.addAll(Schemas.getParentSchemas(schema.getId()));
		updateSelectedSchemas();
	}

	/** Handles the selection of check boxes */
	public void actionPerformed(ActionEvent e)
	{
		SchemaCheckBox schemaCheckBox = (SchemaCheckBox)e.getSource();
		if(schemaCheckBox.isSelected())
			parentSchemaIDs.add(schemaCheckBox.getSchema().getId());
		else parentSchemaIDs.remove(schemaCheckBox.getSchema().getId());
		updateSelectedSchemas();
	}
	
	/** Handles the validating of the attribute */
	public boolean validatePane()
	{
		if(parentSchemaIDs.size()==0) return false;
		return true;
	}
	
	/** Handles the saving of modified parent schemas */
	public void save()
		{ Schemas.setParentSchemas(schema,new ArrayList<Integer>(parentSchemaIDs)); }
}
