// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane;

import model.AliasedSchemaElement;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import view.explorerPane.editPane.ExplorerEditPane;
import view.sharedComponents.LinkedTreeNode;

/** Class for representing schema tree nodes */
public class SchemaTreeNode extends LinkedTreeNode
{
	// Link constants
	private static final Integer EDIT_SOURCES = 0;
	private static final Integer EDIT_SCHEMA = 1;
	private static final Integer EDIT_PARENTS = 2;
	private static final Integer ADD_ENTITY = 3;
	private static final Integer ADD_RELATIONSHIP = 4;
	private static final Integer ADD_ATTRIBUTE = 5;
	private static final Integer ADD_DOMAIN = 6;
	private static final Integer EDIT_ENTITY = 7;
	private static final Integer EDIT_RELATIONSHIP = 8;
	private static final Integer EDIT_ATTRIBUTE = 9;
	private static final Integer EDIT_DOMAIN = 10;
	private static final Integer EDIT_DOMAIN_VALUES = 11;
	
	/** Stores the schema associated with this schema node */
	private Schema schema = null;
	
	/** Stores a link to the explorer pane */
	private ExplorerPane explorerPane = null;
	
	/** Constructs a schema tree node */
	SchemaTreeNode(Schema schema, Object object, ExplorerPane explorerPane)
	{
		// Store the schema node object
		super(object);
		this.schema = schema;
		this.explorerPane = explorerPane;
		
		// Handles links on schema nodes
		if(object.equals(schema.getId()))
			link = schema.getLocked() ? EDIT_SOURCES : EDIT_SCHEMA;
		
		// Handles links on schema object nodes
		if(object instanceof AliasedSchemaElement && !schema.getLocked())
		{
			SchemaElement schemaElement = ((AliasedSchemaElement)object).getElement();
			boolean base = schema.getId().equals(schemaElement.getBase());
			if(schemaElement instanceof Entity) link = base ? EDIT_ENTITY : ADD_ATTRIBUTE;
			if(schemaElement instanceof Relationship && base) link = EDIT_RELATIONSHIP;
			if(schemaElement instanceof Attribute && base) link = EDIT_ATTRIBUTE;
			if(schemaElement instanceof Domain) link = base ? EDIT_DOMAIN : EDIT_DOMAIN_VALUES;
		}
			
		// Handles links on string nodes
		if(object instanceof String && !schema.getLocked())
		{
			String text = (String)object;
			if(text.equals("Entities")) link = ADD_ENTITY;
			if(text.equals("Relationships")) link = ADD_RELATIONSHIP;
			if(text.equals("Domains")) link = ADD_DOMAIN;
			if(text.equals("Parents")) link = EDIT_PARENTS;
		}
	}
	
	/** Returns the link string associated with the schema tree node */
	public String getLinkString()
	{
		if(link==EDIT_SOURCES) return "Edit Sources/Delete";
		else if(link==EDIT_SCHEMA) return "Edit/Delete";
		else if(link==EDIT_PARENTS) return "Edit";
		else if(link==ADD_ENTITY) return "Add Entity";
		else if(link==ADD_RELATIONSHIP) return "Add Relationship";
		else if(link==ADD_ATTRIBUTE) return "Add Attribute";
		else if(link==ADD_DOMAIN) return "Add Domain";
		else if(link==EDIT_ENTITY) return "Edit/Delete";
		else if(link==EDIT_RELATIONSHIP) return "Edit/Delete";
		else if(link==EDIT_ATTRIBUTE) return "Edit/Delete";
		else if(link==EDIT_DOMAIN) return "Edit/Delete";
		else if(link==EDIT_DOMAIN_VALUES) return "Edit Domain Values";
		return "";
	}
	
	/** Performs the link action */
	public void executeLinkAction()
	{
		if(link.equals(SchemaTreeNode.EDIT_SOURCES))
			explorerPane.editInfo(ExplorerEditPane.EDIT_SOURCES,schema,null); 
		else if(link.equals(SchemaTreeNode.EDIT_SCHEMA))
			explorerPane.editInfo(ExplorerEditPane.EDIT_SCHEMA,schema,null);
		else if(link.equals(SchemaTreeNode.EDIT_PARENTS))
			explorerPane.editInfo(ExplorerEditPane.EDIT_PARENTS,schema,null);
		else if(link.equals(SchemaTreeNode.ADD_ENTITY))
			explorerPane.editInfo(ExplorerEditPane.EDIT_ENTITY,schema,null);
		else if(link.equals(SchemaTreeNode.ADD_RELATIONSHIP))
			explorerPane.editInfo(ExplorerEditPane.EDIT_RELATIONSHIP,schema,null);
		else if(link.equals(SchemaTreeNode.ADD_ATTRIBUTE))
			explorerPane.editInfo(ExplorerEditPane.EDIT_ATTRIBUTE,schema,(Entity)((AliasedSchemaElement)userObject).getElement());
		else if(link.equals(SchemaTreeNode.ADD_DOMAIN))
			explorerPane.editInfo(ExplorerEditPane.EDIT_DOMAIN,schema,null);
		else if(link.equals(SchemaTreeNode.EDIT_ENTITY))
			explorerPane.editInfo(ExplorerEditPane.EDIT_ENTITY,schema,(Entity)((AliasedSchemaElement)userObject).getElement());
		else if(link.equals(SchemaTreeNode.EDIT_RELATIONSHIP))
			explorerPane.editInfo(ExplorerEditPane.EDIT_RELATIONSHIP,schema,(Relationship)((AliasedSchemaElement)userObject).getElement());
		else if(link.equals(SchemaTreeNode.EDIT_ATTRIBUTE))
			explorerPane.editInfo(ExplorerEditPane.EDIT_ATTRIBUTE,schema,(Attribute)((AliasedSchemaElement)userObject).getElement());
		else if(link.equals(SchemaTreeNode.EDIT_DOMAIN))
			explorerPane.editInfo(ExplorerEditPane.EDIT_DOMAIN,schema,(Domain)((AliasedSchemaElement)userObject).getElement());
		else if(link.equals(SchemaTreeNode.EDIT_DOMAIN_VALUES))
			explorerPane.editInfo(ExplorerEditPane.EDIT_DOMAIN_VALUES,schema,(Domain)((AliasedSchemaElement)userObject).getElement());
	}
}