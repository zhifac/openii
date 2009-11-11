// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.tagPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.mitre.galaxy.model.SelectedObjects;

/** Class for displaying the Tag Pane of Galaxy */
public class TagPane extends JPanel
{
	/** Private class for storing selected tags (informs model of changes to selected tags) */
	private class SelectTagSelectedTags extends SelectedTags
	{
		/** Updates the selected tags */
		private void updateSelectedTags()
		{
			ArrayList<Integer> selectedTagIDs = new ArrayList<Integer>();
			selectedTagIDs.addAll(selectedTags);
			selectedTagIDs.addAll(inferredTags);
			SelectedObjects.setSelectedTags(selectedTagIDs);
		}
		
		/** Constructs the selected tags */
		private SelectTagSelectedTags()
			{ super(SelectedObjects.getSelectedTags()); }

		/** Handles the addition of a selected tag */
		public void add(Integer tagID)
			{ super.add(tagID); updateSelectedTags(); }

		/** Handles the removal of a selected tag */
		public void remove(Integer tagID)
			{ super.remove(tagID); updateSelectedTags(); }
	}
	
	/** Constructs the Tag Pane */
	public TagPane()
	{
		// Identify the selected tags
		TagSelectionTree selectionTree = new TagSelectionTree(new SelectTagSelectedTags());
		
		// Create the tag selection scroll pane
		JScrollPane tagSelectionPane = new JScrollPane();
		tagSelectionPane.setViewportView(selectionTree);
		tagSelectionPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tagSelectionPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Constructs the tag selection pane
		setBackground(Color.white);
		setBorder(new EmptyBorder(5,5,5,5));
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(0,125));
		add(new JLabel("Selected Tags"),BorderLayout.NORTH);
		add(tagSelectionPane,BorderLayout.CENTER);
	}
}