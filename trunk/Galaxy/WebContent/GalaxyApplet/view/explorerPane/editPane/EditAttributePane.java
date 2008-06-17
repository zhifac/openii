// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane.editPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import view.sharedComponents.EditPaneInterface;

import model.Schemas;

/** Class for editing a schema object attribute */
class EditAttributePane extends EditPaneInterface
{		
	/** Stores the schema associated with the attribute */
	private Schema schema;

	/** Stores the attribute id if one exists */
	private Integer attributeID;
	
	/** Stores the entity id associated with this attribute */
	private Integer entityID;
	
	// Stores various pane objects
	private JTextField name = new JTextField();
	private JTextArea description = new JTextArea();
	private DomainComboBox domain = null;
	private Integer min = null;
	private Integer max = null;

	/** Get the attribute */
	Attribute getAttribute()
		{ return new Attribute(attributeID,name.getText(),description.getText(),entityID,domain.getID(),min,max,schema.getId()); }
	
	/** Constructs the Attribute Pane */
	EditAttributePane(Schema schema, SchemaElement schemaElement)
	{
		this.schema = schema;
		if(schemaElement instanceof Entity) { entityID = ((Entity)schemaElement).getId(); attributeID = null; }
		else { entityID = ((Attribute)schemaElement).getEntityID(); attributeID = ((Attribute)schemaElement).getId(); }
		domain = new DomainComboBox(schema);
		
		// Populate the various elements
		if(schemaElement instanceof Attribute)
		{
			Attribute attribute = (Attribute)schemaElement;
			name.setText(attribute.getName());
			description.setText(attribute.getDescription());
			domain.setID(attribute.getDomainID());
			min = attribute.getMin();
			max = attribute.getMax();
		}
		
		// Constructs the name pane
		NamePane namePane = new NamePane(name);
		namePane.setBorder(new EmptyBorder(2,10,2,2));
		
		// Constructs the description pane
		TextPane descriptionPane = new TextPane("Details",description);
		descriptionPane.setBorder(new EmptyBorder(2,4,2,0));
		
		// Construct the Edit Attribute pane
		setLayout(new BorderLayout());
		setOpaque(false);
		add(namePane,BorderLayout.NORTH);
		add(descriptionPane,BorderLayout.CENTER);
		add(new DomainPane(domain),BorderLayout.SOUTH);
	}
	
	/** Handles the validating of the attribute */
	public boolean validatePane()
	{
		// Checks to make sure that name has been entered
		name.setBackground(Color.white);
		if(name.getText().length()==0)
			{ name.setBackground(Color.yellow); return false; }
	
		return true;
	}
	
	/** Handles the saving of the attribute */
	public void save()
	{
		// Retrieve the attribute
		Attribute attribute = getAttribute();
		if(attributeID==null || !Schemas.getSchemaElement(attributeID).getName().equals(attribute.getName()))
			attribute.setName(getUniqueName(attribute.getName(),new ArrayList<Object>(Schemas.getAttributesFromEntity(schema.getId(), entityID))));
			
		// Handle the addition or editing of an attribute
		if(attributeID==null) Schemas.addSchemaElement(attribute);
		else Schemas.updateSchemaElement(attribute);
	}
	
	/** Handles the deletion of a attribute */
	public boolean delete()
		{ return Schemas.deleteSchemaElement(getAttribute()); }
}