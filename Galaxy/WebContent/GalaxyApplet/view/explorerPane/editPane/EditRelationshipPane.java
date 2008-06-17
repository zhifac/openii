// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane.editPane;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;

import view.sharedComponents.EditPaneInterface;

import model.Schemas;

/** Class for editing a schema relationship */
class EditRelationshipPane extends EditPaneInterface
{		
	/** Stores the schema associated with the relationship */
	private Schema schema;

	/** Stores the relationship id if one exists */
	private Integer relationshipID;
	
	// Stores various pane objects
	private JTextField name = new JTextField();
	private EntityComboBox leftEntity = null;
	private NumberField leftMin = new NumberField();
	private NumberField leftMax = new NumberField();
	private EntityComboBox rightEntity = null;
	private NumberField rightMin = new NumberField();
	private NumberField rightMax = new NumberField();
	
	/** Get the relationship */
	private Relationship getRelationship()
		{ return new Relationship(relationshipID,name.getText(),leftEntity.getID(),leftMin.getValue(),leftMax.getValue(),rightEntity.getID(),rightMin.getValue(),rightMax.getValue(),schema.getId()); }
	
	/** Constructs the Relationship Pane */
	EditRelationshipPane(Schema schema, Relationship relationship)
	{
		this.schema = schema;
		this.relationshipID = relationship==null ? null :  relationship.getId();
		leftEntity = new EntityComboBox(schema);
		rightEntity = new EntityComboBox(schema);
		
		// Constructs the name pane
		NamePane namePane = new NamePane(name);
		namePane.setBorder(new EmptyBorder(2,32,2,2));
		
		// Constructs the relationship entity panes
		RelationshipEntityPane leftRelationshipEntityPane = new RelationshipEntityPane("Left",leftEntity,leftMin,leftMax);
		leftRelationshipEntityPane.setBorder(new EmptyBorder(10,9,2,2));
		RelationshipEntityPane rightRelationshipEntityPane = new RelationshipEntityPane("Right",rightEntity,rightMin,rightMax);
		rightRelationshipEntityPane.setBorder(new EmptyBorder(10,2,2,2));
		
		// Construct the relationship pane
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setOpaque(false);
		add(namePane);
		add(leftRelationshipEntityPane);
		add(rightRelationshipEntityPane);
		
		// Populate the relationship pane
		if(relationship!=null)
		{
			name.setText(relationship.getName());
			leftEntity.setID(relationship.getLeftID());
			leftMin.setValue(relationship.getLeftMin());
			leftMax.setValue(relationship.getLeftMax());
			rightEntity.setID(relationship.getRightID());
			rightMin.setValue(relationship.getRightMin());
			rightMax.setValue(relationship.getRightMax());			
		}
	}
	
	/** Handles the validating of the relationship */
	public boolean validatePane()
	{
		// Clear out all error markings
		name.setBackground(Color.white);
		leftMin.setBackground(Color.white); leftMax.setBackground(Color.white);
		rightMin.setBackground(Color.white); rightMax.setBackground(Color.white);
		
		// Don't save if relationship name is empty
		if(name.getText().length()==0)
			{ name.setBackground(Color.yellow); return false; }
		
		// Don't save if left min is greater than left max
		if(leftMin.getValue()!=null && leftMax.getValue()!=null && leftMin.getValue()>leftMax.getValue())
			{ leftMin.setBackground(Color.yellow); leftMax.setBackground(Color.yellow); return false; }
		
		// Don't save if right min is greater than right max
		if(rightMin.getValue()!=null && rightMax.getValue()!=null && rightMin.getValue()>rightMax.getValue())
			{ rightMin.setBackground(Color.yellow); rightMax.setBackground(Color.yellow); return false; }

		return true;
	}
	
	/** Handles the saving of the relationship */
	public void save()
	{		
		// Retrieve the relationship
		Relationship relationship = getRelationship();
		if(relationshipID==null || !Schemas.getSchemaElement(relationshipID).getName().equals(relationship.getName()))
			relationship.setName(getUniqueName(relationship.getName(),new ArrayList<Object>(Schemas.getSchemaElements(schema.getId(),Relationship.class))));

		// Handle the adding or editing of a relationship
		if(relationshipID==null) Schemas.addSchemaElement(relationship);
		else Schemas.updateSchemaElement(relationship);
	}
	
	/** Handles the deletion of a relationship */
	public boolean delete()
		{ return Schemas.deleteSchemaElement(getRelationship()); }
}