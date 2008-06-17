// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane.editPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import model.Groups;

import view.groupPane.groupSelectionTree.SelectedGroupsWithInferredParents;
import view.sharedComponents.EditPaneInterface;

/** Class for editing groups associated with the specified schema */
class EditSchemaGroupsPane extends EditPaneInterface
{	
	/** Stores the schema for which groups are being assigned */
	private Integer schemaID;
	
	/** Stores the group selection tree */
	private view.groupPane.groupSelectionTree.GroupSelectionTree groupSelectionTree;
	
	/** Constructs the Edit Schema Groups Pane */
	EditSchemaGroupsPane(Integer schemaID)
	{
		this.schemaID = schemaID;

		// Initialize the group selection tree
		SelectedGroupsWithInferredParents selectedGroups = new SelectedGroupsWithInferredParents(Groups.getSchemaGroups(schemaID));
		groupSelectionTree = new view.groupPane.groupSelectionTree.GroupSelectionTree(selectedGroups);
		
		// Generate the parents scroll pane
		JScrollPane parentsSPane = new JScrollPane();
		parentsSPane.setBackground(Color.white);
		parentsSPane.setPreferredSize(new Dimension(100,125));
		parentsSPane.setViewportView(groupSelectionTree);
		parentsSPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		parentsSPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Generate the Edit Parents Pane
		setBorder(new EmptyBorder(2,0,0,0));
		setLayout(new BorderLayout());
		setOpaque(false);
		add(parentsSPane,BorderLayout.CENTER);
	}
	
	/** Handles the validating of the schema groups */
	public boolean validatePane()
		{ return true; }
	
	/** Handles the saving of the schema groups */
	public void save()
		{ Groups.setSchemaGroups(schemaID,groupSelectionTree.getSelectedGroups()); }
}
