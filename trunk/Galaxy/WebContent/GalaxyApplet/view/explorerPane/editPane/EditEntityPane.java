// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane.editPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;

import view.sharedComponents.EditPaneInterface;
import view.sharedComponents.EditPaneInterface.LinkPane.Link;

import model.Schemas;

/** Class for editing a schema entity */
class EditEntityPane extends EditPaneInterface implements MouseListener, MouseMotionListener
{	
	/** Stores the schema to which the entity is associated */
	private Schema schema;

	/** Stores the entity id if one exists */
	private Integer entityID;
	
	/** Stores the edit pane for switching to secondary pane */
	private ExplorerEditPane editPane;
	
	/** Stores the attributes attached to this entity */
	private ArrayList<Attribute> attributes = new ArrayList<Attribute>();
	
	/** Stores the attributes deleted from this entity */
	private ArrayList<Attribute> deletedAttributes = new ArrayList<Attribute>();

	/** Tracks the current attribute being edited */
	private Attribute currAttribute = null;
	
	// Stores various pane objects
	private JTextField name = new JTextField();
	private JTextArea description = new JTextArea();
	private JPanel infoPane = new JPanel();
	private JLabel addLink = new JLabel("(Add)");

	/** Get the entity */
	private Entity getEntity()
		{ return new Entity(entityID,name.getText(),description.getText(),schema.getId()); }

	/** Returns the attribute list */
	private JPanel getAttributeList()
	{
		ArrayList<LinkPane> attributeList = new ArrayList<LinkPane>();
		for(Attribute attribute : attributes)
		{
			LinkPane pane = new LinkPane(attribute);
			pane.getLink().addMouseListener(this);
			pane.getLink().addMouseMotionListener(this);
			attributeList.add(pane);
		}
		return new LinkListPane("Attributes:",attributeList,addLink);
	}
	
	/** Constructs the Edit Entity pane */
	EditEntityPane(Schema schema, Entity entity, ExplorerEditPane editPane)
	{
		this.schema = schema;
		this.entityID = entity==null ? null : entity.getId();
		this.editPane = editPane;
		
		// Populate the various elements
		if(entity!=null)
		{
			name.setText(entity.getName());
			description.setText(entity.getDescription());
			attributes = Schemas.getAttributesFromEntity(schema.getId(),entity.getId());
		}
		
		// Initialize the add link
		addLink.addMouseListener(this);
		addLink.addMouseMotionListener(this);
		
		// Constructs the name pane
		NamePane namePane = new NamePane(name);
		namePane.setBorder(new EmptyBorder(2,23,2,2));
		
		// Constructs the description pane
		TextPane descriptionPane = new TextPane("Details",description);
		descriptionPane.setBorder(new EmptyBorder(2,17,2,0));
		
		// Construct the info pane
		infoPane.setOpaque(false);
		infoPane.setLayout(new GridLayout(2,1));
		infoPane.add(descriptionPane);
		infoPane.add(getAttributeList());
		
		// Construct the Edit Domain pane
		setLayout(new BorderLayout());
		setOpaque(false);
		add(namePane,BorderLayout.NORTH);
		add(infoPane,BorderLayout.CENTER);
	}
	
	/** Retrieves information from the secondary pane */
	public void saveSecondaryInfo(EditPaneInterface secondaryPane, boolean deleted)
	{
		// Get the attribute
		EditAttributePane attributePane = (EditAttributePane)secondaryPane;
		Attribute attribute = attributePane.getAttribute();
		
		// Delete the old attribute
		if(currAttribute!=null)
			for(int i=0; i<attributes.size(); i++)
				if(attributes.get(i).getName().equals(currAttribute.getName()))
					{ attributes.remove(i); break; }

		// Add new attribute if not being deleted
		if(!deleted && attribute.getName().length()>0)
		{
			attribute.setName(getUniqueName(attribute.getName(), new ArrayList<Object>(attributes)));
			attributes.add(attribute);
		}
		else if(attribute.getId()!=null) deletedAttributes.add(attribute);

		// Redisplay the new list of attributes
		infoPane.remove(1);
		infoPane.add(getAttributeList());
	}
	
	/** Handles the validating of the entity */
	public boolean validatePane()
	{
		// Checks to make sure that name has been entered
		name.setBackground(Color.white);
		if(name.getText().length()==0)
			{ name.setBackground(Color.yellow); return false; }
	
		return true;
	}
	
	/** Handles the saving of the entity */
	public void save()
	{
		// Retrieve the entity
		Entity entity = getEntity();
		if(entityID==null || !Schemas.getSchemaElement(entityID).getName().equals(entity.getName()))
			entity.setName(getUniqueName(entity.getName(),new ArrayList<Object>(Schemas.getSchemaElements(schema.getId(),Entity.class))));			
		
		// Handle the addition of an entity
		if(entityID==null)
		{
			Schemas.addSchemaElement(entity);
			entityID = entity.getId();
			for(Attribute attribute : attributes) attribute.setEntityID(entityID);
		}
			
		// Handles the editing of an entity
		else Schemas.updateSchemaElement(entity);

		// Deletes the attributes that were removed
		for(Attribute attribute : deletedAttributes)
		{
			EditAttributePane secondaryPane = new EditAttributePane(schema,attribute);
			secondaryPane.delete();			
		}

		// Saves the attributes that were created
		for(Attribute attribute : attributes)
		{
			EditAttributePane secondaryPane = new EditAttributePane(schema,attribute);
			secondaryPane.save();		
		}
	}
	
	/** Handles the deletion of a entity */
	public boolean delete()
	{
		Entity entity = getEntity();
		for(Attribute attribute : Schemas.getAttributesFromEntity(schema.getId(),entity.getId()))
			Schemas.deleteSchemaElement(attribute);
		return Schemas.deleteSchemaElement(entity);
	}
	
	/** Switch to a secondary pane for adding an attribute if the link is selected */
	public void mouseClicked(MouseEvent e)
	{
		// Handles the addition of a link
		if(e.getSource()==addLink)
		{
			currAttribute = null;
			EditAttributePane secondaryPane = new EditAttributePane(schema,getEntity());
			editPane.displaySecondaryPane(secondaryPane,false);
		}
		
		// Handles the editing of a link
		else
		{
			currAttribute = (Attribute)((Link)e.getSource()).getObject();
			EditAttributePane secondaryPane = new EditAttributePane(schema,currAttribute);
			editPane.displaySecondaryPane(secondaryPane,true);
		}

		// Set the mouse cursor back to an arrow
		setCursor(Cursor.getDefaultCursor());
	}
	
	/** Displays a hand cursor when the mouse is over the edit link */
	public void mouseEntered(MouseEvent e)
		{ setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
	
	/** Resets the mouse cursor if moved off of the edit link */
	public void mouseExited(MouseEvent e)
		{ setCursor(Cursor.getDefaultCursor()); }

	// Unused listener event
	public void mouseMoved(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
}