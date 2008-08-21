// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane.editPane;

import model.Schemas;
import model.server.ServletConnection;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import view.sharedComponents.EditPane;
import view.sharedComponents.EditPaneInterface;
import view.sharedComponents.EditPaneParent;

/** Class for editing information about a schema object */
public class ExplorerEditPane extends EditPane
{
	// Constants indicating various edit actions
	static public final int EDIT_SOURCES = 0;
	static public final int EDIT_SCHEMA = 1;
	static public final int EDIT_PARENTS = 2;
	static public final int EDIT_ENTITY = 3;
	static public final int EDIT_RELATIONSHIP = 4;
	static public final int EDIT_ATTRIBUTE = 5;
	static public final int EDIT_DOMAIN = 6;
	static public final int EDIT_DOMAIN_VALUES = 7;	
	
	/** Constructs the Edit Pane */
	public ExplorerEditPane(EditPaneParent parent)
		{ super(parent); }
	
	/** Displays the specified schema object in the edit pane */
	public void editInfo(Integer action, Schema schema, SchemaElement schemaElement)
	{
		// Determine if info is being added or edited
		boolean addInfo = (schemaElement==null && action!=EDIT_SCHEMA && action!=EDIT_SOURCES) || (action==EDIT_ATTRIBUTE && schemaElement instanceof Entity);
		
		// Determine the title of the edit pane
		String titleText = "";
		if(action==EDIT_PARENTS) titleText="Edit Schema Parents";
		else
		{
			titleText = addInfo ? "Add " : "Edit ";
			if(action==EDIT_SOURCES) titleText += "Sources";
			if(action==EDIT_SCHEMA) titleText += "Schema";
			if(action==EDIT_ENTITY) titleText += "Entity";
			if(action==EDIT_RELATIONSHIP) titleText += "Relationship";
			if(action==EDIT_ATTRIBUTE) titleText += "Attribute";
			if(action==EDIT_DOMAIN) titleText += "Domain";
			if(action==EDIT_DOMAIN_VALUES) titleText += "Domain Values";
		}
		
		// Determine the pane to use for the edit pane
		EditPaneInterface pane = null;
		switch(action)
		{
			case EDIT_SOURCES: pane = new EditSourcesPane(schema,this); break;
			case EDIT_SCHEMA: pane = new EditSchemaPane(schema); break;
			case EDIT_PARENTS: pane = new EditParentsPane(schema); break;
			case EDIT_ENTITY: pane = new EditEntityPane(schema,(Entity)schemaElement,this); break;
			case EDIT_RELATIONSHIP: pane = new EditRelationshipPane(schema,(Relationship)schemaElement); break;
			case EDIT_ATTRIBUTE: pane = new EditAttributePane(schema,schemaElement); break;
			case EDIT_DOMAIN: pane = new EditDomainPane(schema,(Domain)schemaElement,this); break;
			case EDIT_DOMAIN_VALUES: pane = new EditDomainValuesPane(schema,(Domain)schemaElement); break;
		}
		
		// Determine delete check box configuration
		boolean deleteVisible = !addInfo && action!=EDIT_DOMAIN_VALUES;
		boolean deleteEnabled = true;
		if(action==EDIT_SOURCES && ServletConnection.getChildSchemas(schema.getId()).size()>0) deleteEnabled = false;
		if(action==EDIT_DOMAIN && !addInfo)
		{
			int associatedAttributes = Schemas.getGraph(schema.getId()).getAttributes(((Domain)schemaElement).getId()).size();
			if(associatedAttributes>0) deleteEnabled = false;
		}

		// Configure the edit pane
		editInfo(titleText,pane,deleteVisible,deleteEnabled);
	}
}
