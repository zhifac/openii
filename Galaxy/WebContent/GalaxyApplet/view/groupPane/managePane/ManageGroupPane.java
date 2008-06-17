// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import model.Groups;
import model.Schemas;
import model.listeners.GroupsListener;
import model.listeners.SchemasListener;

import view.groupPane.managePane.editPane.GroupEditPane;
import view.sharedComponents.EditPaneParent;

/** Class for displaying the Group Pane of Galaxy */
public class ManageGroupPane extends JPanel implements EditPaneParent, ActionListener, GroupsListener, SchemasListener
{
	// Objects used by the explorer pane
	private JRadioButton groupMode = new JRadioButton("Manage by Group");
	private JRadioButton schemaMode = new JRadioButton("Manage by Schema");	
	private JScrollPane groupPane = new JScrollPane();
	private GroupEditPane editPane = new GroupEditPane(this);

	/** Returns the group mode pane */
	private JPanel getGroupModePane()
	{
		// Set up the "group" group mode
		groupMode.setOpaque(false);
		groupMode.setFocusable(false);
		groupMode.addActionListener(this);
		groupMode.setSelected(true);
		
		// Set up the "schema" group mode
		schemaMode.setOpaque(false);
		schemaMode.setFocusable(false);
		schemaMode.addActionListener(this);

		// Set up the group mode button group
		ButtonGroup selectionMode = new ButtonGroup();
		selectionMode.add(groupMode);
		selectionMode.add(schemaMode);
		
		// Generate the group mode pane
		JPanel pane = new JPanel();
		pane.setOpaque(false);
		pane.setLayout(new BorderLayout());
		pane.add(groupMode,BorderLayout.WEST);
		pane.add(schemaMode,BorderLayout.EAST);
		return pane;
	}
	
	/** Constructs the Group Pane */
	public ManageGroupPane()
	{
		// Initializes the edit pane initially as hidden
		editPane.setVisible(false);
		
		// Create the group pane
		groupPane.setViewportView(new GroupTree(this));
		groupPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		groupPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		// Create the main pane
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());
		mainPane.add(groupPane,BorderLayout.CENTER);
		mainPane.add(editPane,BorderLayout.SOUTH);
		
		// Constructs the Group Pane
		setBackground(Color.white);
		setBorder(new EmptyBorder(5,5,5,5));
		setLayout(new BorderLayout());
		add(new JLabel("Group Management"),BorderLayout.NORTH);
		add(mainPane,BorderLayout.CENTER);
		add(getGroupModePane(),BorderLayout.SOUTH);
		
		// Adds listeners to monitor various changes to the model
		Schemas.addSchemasListener(this);
		Groups.addGroupsListener(this);
	}

	/** Edit info about a specified schema object */
	public void editInfo(Integer action, Integer object)
	{
		editPane.setVisible(true);
		editPane.editInfo(action,object);
		validate();
	}
	
	/** Hides the info editor */
	public void doneEditingInfo()
		{ editPane.setVisible(false); validate(); }
	
	/** Handles the selection of group or schema mode */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==groupMode)
			groupPane.setViewportView(new GroupTree(this));
		else if(e.getSource()==schemaMode)
			groupPane.setViewportView(new SchemaTree(this));
	}

	/** Handles the addition of a schema */
	public void schemaAdded(Schema schema)
		{ ((SchemasListener)groupPane.getViewport().getView()).schemaAdded(schema); }

	/** Handles the updating of schema information */
	public void schemaUpdated(Schema schema)
		{ repaint(); }
	
	/** Handles the removal of a schema */
	public void schemaRemoved(Schema schema)
		{ ((SchemasListener)groupPane.getViewport().getView()).schemaRemoved(schema); }

	/** Handles the addition of a group */
	public void groupAdded(Integer groupID)
		{ ((GroupsListener)groupPane.getViewport().getView()).groupAdded(groupID); }
	
	/** Handles the update of a group */
	public void groupUpdated(Integer groupID)
		{ repaint(); }
	
	/** Handles the removal of a group */
	public void groupRemoved(Integer groupID)
		{ ((GroupsListener)groupPane.getViewport().getView()).groupRemoved(groupID); }

	/** Handles the addition of a schema group */
	public void schemaGroupAdded(Integer schemaID, Integer groupID)
		{ ((GroupsListener)groupPane.getViewport().getView()).schemaGroupAdded(schemaID,groupID); }

	/** Handles the removal of a schema group */
	public void schemaGroupRemoved(Integer schemaID, Integer groupID)
		{ ((GroupsListener)groupPane.getViewport().getView()).schemaGroupRemoved(schemaID,groupID); }
	
	// Unused listener events
	public void schemaLocked(Schema schema) {}
	public void schemaElementAdded(SchemaElement schemaElement) {}
	public void schemaElementRemoved(SchemaElement schemaElement) {}
	public void schemaParentsUpdated(Schema schema) {}
}
