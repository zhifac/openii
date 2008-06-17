// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane.editPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.Schema;

import view.sharedComponents.EditPaneInterface;

import model.Groups;
import model.Schemas;

/** Class for editing schemas associated with the specified group */
class EditGroupSchemasPane extends EditPaneInterface
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
	
	/** Stores the group to which schemas are being edited */
	private Integer groupID;

	// Stores various pane objects
	JPanel schemasPane = new JPanel();
	
	/** Constructs the Edit Group Schemas Pane */
	EditGroupSchemasPane(Integer groupID)
	{
		// Get group information
		this.groupID = groupID;
		ArrayList<Integer> groupSchemas = Groups.getGroupSchemas(groupID);
		
		// Generate the schemas pane
		schemasPane.setBackground(Color.white);
		schemasPane.setLayout(new BoxLayout(schemasPane,BoxLayout.Y_AXIS));
		for(Schema schema : Schemas.sort(Schemas.getSchemas()))
		{
			SchemaCheckBox schemaCheckBox = new SchemaCheckBox(schema);
			if(groupSchemas.contains(schema.getId())) schemaCheckBox.setSelected(true);
			schemasPane.add(schemaCheckBox);
		}
		
		// Generate the schema scroll pane
		JScrollPane schemasSPane = new JScrollPane();
		schemasSPane.setBackground(Color.white);
		schemasSPane.setPreferredSize(new Dimension(100,125));
		schemasSPane.setViewportView(schemasPane);
		schemasSPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		schemasSPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Generate the main pane
		setBorder(new EmptyBorder(2,0,0,0));
		setLayout(new BorderLayout());
		setOpaque(false);
		add(schemasSPane,BorderLayout.CENTER);
	}

	/** Handles the validating of the group schemas */
	public boolean validatePane()
		{ return true; }
	
	/** Handles the saving of modified group schemas */
	public void save()
	{
		ArrayList<Integer> groupSchemas = new ArrayList<Integer>();
		for(int i=0; i<schemasPane.getComponentCount(); i++)
		{
			SchemaCheckBox schemaCheckBox = (SchemaCheckBox)schemasPane.getComponent(i);
			if(schemaCheckBox.isSelected()) groupSchemas.add(schemaCheckBox.getSchema().getId());
		}
		Groups.setGroupSchemas(groupID,groupSchemas);
	}
}
