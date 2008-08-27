// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import model.Groups;
import model.SelectedObjects;

/** Class for displaying the Group Pane of Galaxy */
public class GroupPane extends JPanel
{
	/** Private class for storing selected groups (informs model of changes to selected groups) */
	private class SelectGroupSelectedGroups extends SelectedGroupsWithInferredChildren
	{
		/** Updates the selected groups */
		private void updateSelectedGroups()
		{
			ArrayList<Integer> selectedGroupIDs = new ArrayList<Integer>();
			selectedGroupIDs.addAll(selectedGroups);
			selectedGroupIDs.addAll(inferredGroups);
			SelectedObjects.setSelectedGroups(selectedGroupIDs);
		}
		
		/** Constructs the selected groups */
		private SelectGroupSelectedGroups()
			{ super(SelectedObjects.getSelectedGroups()); }

		/** Handles the addition of a selected group */
		public void add(Integer groupID)
			{ super.add(groupID); updateSelectedGroups(); }

		/** Handles the removal of a selected group */
		public void remove(Integer groupID)
			{ super.remove(groupID); updateSelectedGroups(); }
	}
	
	/** Constructs the Group Pane */
	public GroupPane()
	{
		// Identify the selected groups
		GroupSelectionTree selectionTree = new GroupSelectionTree(new SelectGroupSelectedGroups());
		
		// Create the group selection scroll pane
		JScrollPane groupSelectionPane = new JScrollPane();
		groupSelectionPane.setViewportView(selectionTree);
		groupSelectionPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		groupSelectionPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Constructs the group selection pane
		setBackground(Color.white);
		setBorder(new EmptyBorder(5,5,5,5));
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(0,125));
		add(new JLabel("Selected Groups"),BorderLayout.NORTH);
		add(groupSelectionPane,BorderLayout.CENTER);

		// Listen for group events
		Groups.addGroupsListener(selectionTree);
	}
}
