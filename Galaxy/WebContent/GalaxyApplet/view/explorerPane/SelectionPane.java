// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.Schema;

import model.Schemas;
import model.SelectedObjects;
import model.listeners.SelectedObjectsListener;

/** Class for displaying the selection pane for the explorer pane */
class SelectionPane extends JPanel implements ActionListener, SelectedObjectsListener
{
	// Objects used by the selection pane
	private JComboBox schemaList = new JComboBox();

	/** Selection pane renderer */
	private class SelectionPaneRenderer extends DefaultListCellRenderer
	{
		/** Defines how items in the selection list should be rendered */
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			Schema schema = Schemas.getSchema((Integer)value);
			if(schema==null) return new JPanel();
			else return new JLabel(schema.getName());
		}
	}
	
	/** Display schemas */
	private void updateSchemaList()
	{
		// Gather the schemas to display
		ArrayList<Schema> schemas = new ArrayList<Schema>();
		for(Schema schema : Schemas.getSchemas())
			if(SelectedObjects.inSelectedGroups(schema.getId())) schemas.add(schema);
		Schemas.sort(schemas);
		
		// Set the selection model
		Vector<Integer> selectionIDs = new Vector<Integer>();
		for(Schema schema : schemas) selectionIDs.add(schema.getId());
		schemaList.setModel(new DefaultComboBoxModel(selectionIDs));
		
		// Set the selected schema
		Integer selectedSchemaID = SelectedObjects.getSelectedSchema();
		schemaList.setSelectedItem(selectedSchemaID);
		if((selectedSchemaID==null || !selectedSchemaID.equals(schemaList.getSelectedItem())) && schemaList.getItemCount()>0)
			SelectedObjects.setSelectedSchema((Integer)schemaList.getItemAt(0));
	}
	
	/** Constructs the Selection Pane */
	SelectionPane()
	{
		// Set up the selection options
		schemaList.setBackground(Color.white);
		schemaList.setFocusable(false);
		schemaList.setRenderer(new SelectionPaneRenderer());
		schemaList.addActionListener(this);
		
		// Creates the selection label pane
		JPanel labelPane = new JPanel();
		labelPane.setOpaque(false);
		labelPane.setLayout(new BorderLayout());
		labelPane.add(new JLabel("Current Schema:"),BorderLayout.WEST);
		
		// Creates the selection options pane
		setBackground(Color.white);
		setBorder(new EmptyBorder(0,0,5,0));
		setLayout(new BorderLayout());
		add(labelPane,BorderLayout.NORTH);
		add(schemaList,BorderLayout.CENTER);

		// Add a listener to monitor for changes in the selected schema
		SelectedObjects.addSelectedObjectsListener(this);

		// Initializes the schemas
		updateSchemaList();
	}
	
	/** Handles the selection of a schema */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==schemaList)
			SelectedObjects.setSelectedSchema((Integer)schemaList.getSelectedItem());
	}
	
	/** Handles the selection of a schema external to the selection pane */
	public void selectedSchemaChanged()
	{
		// Switch to the newly selected schema (if needed)
		Integer selectedSchemaID = SelectedObjects.getSelectedSchema();
		if(selectedSchemaID!=null)
		{
			// Set the selected schema
			schemaList.removeActionListener(this);
			schemaList.getModel().setSelectedItem(selectedSchemaID);
			schemaList.addActionListener(this);
		}
	}

	/** Handles changes to the selected groups */
	public void selectedGroupsChanged()
		{ updateSchemaList(); }
	
	// Unused listener events
	public void selectedComparisonSchemaChanged() {}
}