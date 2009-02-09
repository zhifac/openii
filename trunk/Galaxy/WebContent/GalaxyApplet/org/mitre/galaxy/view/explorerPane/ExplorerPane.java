// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.explorerPane;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.mitre.galaxy.model.SelectedObjects;
import org.mitre.galaxy.model.listeners.SelectedObjectsListener;
import org.mitre.schemastore.model.SchemaElement;


/** Class for displaying the Explorer Pane of Galaxy */
public class ExplorerPane extends JPanel implements SelectedObjectsListener
{
	/** Keeps track of the current schema tree */
	private SchemaTree schemaTree = null;
	
	// Objects used by the explorer pane
	private JScrollPane schemaPane = new JScrollPane();
	private ViewPane viewPane = new ViewPane();
	
	/** Resets the view */
	private void resetView()
	{
		Integer selectedObject = SelectedObjects.getSelectedSchema();
		schemaTree = selectedObject==null ? null : new SchemaTree(selectedObject,this);
		schemaPane.setViewportView(schemaTree);
	}	
	
	/** Constructs the Explorer Pane */
	public ExplorerPane()
	{
		// Create the selection pane
		schemaPane.setViewportView(null);
		schemaPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		schemaPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		// Creates the main pane
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());
		mainPane.add(schemaPane,BorderLayout.CENTER);
		mainPane.add(viewPane,BorderLayout.SOUTH);
		
		// Constructs the Explorer Pane
		setBackground(Color.white);
		setBorder(new EmptyBorder(5,5,5,5));
		setLayout(new BorderLayout());
		add(new SelectionPane(),BorderLayout.NORTH);
		add(mainPane,BorderLayout.CENTER);
		add(new DataSourcePane(),BorderLayout.SOUTH);

		// Add in event listeners
		SelectedObjects.addSelectedObjectsListener(this);

		// Resets the view
		resetView();
	}
	
	/** Clears an schema object info being displayed */
	public void clearInfo()
		{ viewPane.clearInfo(); }
	
	/** Display info on the specified schema element */
	public void displayInfo(SchemaElement schemaElement, Integer schemaID)
		{ viewPane.displayInfo(schemaElement,schemaID); }

	/** Handles changes to the selected schema */
	public void selectedSchemaChanged() { resetView(); }
	
	/** Handles changes to the selected comparison schema */
	public void selectedComparisonSchemaChanged()
		{ if(schemaTree!=null) schemaTree.selectedComparisonSchemaChanged(); }
	
	// Unused listener events
	public void selectedGroupsChanged() {}
}
