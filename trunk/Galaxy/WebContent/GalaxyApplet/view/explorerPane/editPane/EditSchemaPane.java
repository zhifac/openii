// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane.editPane;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.Schema;

import view.sharedComponents.EditPaneInterface;

import model.Schemas;

/** Class for editing a schema relationship */
class EditSchemaPane extends EditPaneInterface
{	
	/** Stores the schema associated with the relationship */
	private Schema schema;
	
	// Stores various pane objects
	private JTextField name = new JTextField();
	private JTextArea description = new JTextArea();
	
	/** Constructs the Schema Pane */
	EditSchemaPane(Schema schema)
	{
		this.schema = schema;

		// Populate the various elements
		name.setText(schema.getName());
		description.setText(schema.getDescription());

		// Constructs the name pane
		NamePane namePane = new NamePane(name);
		namePane.setBorder(new EmptyBorder(2,6,2,2));
		
		// Construct the schema pane
		setLayout(new BorderLayout());
		setOpaque(false);
		add(namePane,BorderLayout.NORTH);
		add(new TextPane("Details",description),BorderLayout.CENTER);
	}
	
	/** Handles the validating of the schema */
	public boolean validatePane()
	{
		// Checks to make sure that name has been entered
		name.setBackground(Color.white);
		if(name.getText().length()==0)
			{ name.setBackground(Color.yellow); return false; }
	
		return true;
	}
	
	/** Handles the saving of the schema */
	public void save()
		{ Schemas.updateSchema(new Schema(schema.getId(),name.getText(),"","","",description.getText(),schema.getLocked())); }
	
	/** Handles the deletion of the schema */
	public boolean delete()
		{ Schemas.deleteSchema(schema); return true; }
}