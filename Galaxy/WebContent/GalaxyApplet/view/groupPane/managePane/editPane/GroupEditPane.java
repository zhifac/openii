// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane.editPane;

import model.UniversalObjects;
import view.sharedComponents.EditPane;
import view.sharedComponents.EditPaneInterface;
import view.sharedComponents.EditPaneParent;

/** Class for editing information about a group object */
public class GroupEditPane extends EditPane
{
	// Constants indicating various edit actions
	static public final int EDIT_STRUCTURE = 0;
	static public final int MANAGE_SCHEMAS = 1;
	static public final int MANAGE_GROUPS = 2;
	
	/** Constructs the Edit Pane */
	public GroupEditPane(EditPaneParent parent)
		{ super(parent); }
	
	/** Displays the specified object in the edit pane */
	public void editInfo(Integer action, Integer reference)
	{
		// Determine the title of the edit pane
		String titleText = "";
		if(action==EDIT_STRUCTURE) titleText="Edit Group Structure";
		if(action==MANAGE_SCHEMAS) titleText="Manage \""+UniversalObjects.getName(reference)+"\" Schemas";
		if(action==MANAGE_GROUPS) titleText="Manage \""+UniversalObjects.getName(reference)+"\" Groups";
	
		// Configure the button settings
		saveButton.setEnabled(true);
		cancelButton.setText("Cancel");
		
		// Determine the pane to use for the edit pane
		EditPaneInterface pane = null;
		switch(action)
		{
			case EDIT_STRUCTURE: pane = new EditGroupStructuresPane(this); break;
			case MANAGE_SCHEMAS: pane = new EditGroupSchemasPane(reference); break;
			case MANAGE_GROUPS: pane = new EditSchemaGroupsPane(reference); break;
		}

		// Configure the edit pane
		editInfo(titleText,pane,false,false);
	}
}
