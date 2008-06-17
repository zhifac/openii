// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane.editPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import view.sharedComponents.EditPaneInterface;

/** Class for editing the group hierarchical structure */
public class EditGroupStructuresPane extends EditPaneInterface
{
	/** Stores the group edit pane */
	GroupEditPane editPane;
	
	/** Stores the group tree */
	private GroupTree groupTree = new GroupTree(this);
	
	/** Constructs the Edit Parents Pane */
	EditGroupStructuresPane(GroupEditPane editPane)
	{
		this.editPane = editPane;
		editPane.saveButton.setEnabled(false);
		editPane.cancelButton.setText("Close");

		// Generate the parents scroll pane
		JScrollPane parentsSPane = new JScrollPane();
		parentsSPane.setBackground(Color.white);
		parentsSPane.setPreferredSize(new Dimension(100,125));
		parentsSPane.setViewportView(groupTree);
		parentsSPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		parentsSPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Generate the Edit Parents Pane
		setBorder(new EmptyBorder(2,0,0,0));
		setLayout(new BorderLayout());
		setOpaque(false);
		add(parentsSPane,BorderLayout.CENTER);
	}

	/** Returns the group tree associated with the group structure pane */
	public GroupTree getGroupTree()
		{ return groupTree; }
	
	/** Displays the secondary pane */
	public void displaySecondaryPane(Integer groupID)
	{
		editPane.displaySecondaryPane(new EditGroupStructurePane(groupID),false);
		editPane.saveButton.setEnabled(true);
		editPane.saveButton.setText("Save");
		editPane.cancelButton.setText("Cancel");
	}
	
	/** Retrieves information from the secondary pane */
	public void saveSecondaryInfo(EditPaneInterface secondaryPane, boolean deleted)
		{ ((EditGroupStructurePane)secondaryPane).save(); }
	
	/** Closes the secondary pane */
	public void closeSecondaryPane()
	{
		editPane.saveButton.setEnabled(false);
		editPane.cancelButton.setText("Close");		
	}
}