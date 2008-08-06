// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import view.explorerPane.editPane.ExplorerEditPane;
import view.sharedComponents.EditPaneParent;

import model.Schemas;
import model.SelectedObjects;
import model.listeners.SchemasListener;
import model.listeners.SelectedObjectsListener;

import org.mitre.schemastore.graph.*;

/** Class for displaying the Explorer Pane of Galaxy */
public class ExplorerPane extends JPanel implements EditPaneParent, SchemasListener, SelectedObjectsListener
{
	/** Keeps track of the current schema tree */
	private SchemaTree schemaTree = null;
	
	// Objects used by the explorer pane
	private JScrollPane schemaPane = new JScrollPane();
	private ViewPane viewPane = new ViewPane();
	private ExplorerEditPane editPane = new ExplorerEditPane(this);
	
	/** Resets the view */
	private void resetView()
	{
		schemaPane.setViewportView(schemaTree = new SchemaTree(SelectedObjects.getSelectedSchema(),this));
		doneEditingInfo();
	}	
	
	/** Constructs the Explorer Pane */
	public ExplorerPane()
	{
		// Create the selection pane
		schemaPane.setViewportView(null);
		schemaPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		schemaPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		// Create the info pane
		JPanel infoPane = new JPanel();
		infoPane.setLayout(new BoxLayout(infoPane,BoxLayout.Y_AXIS));
		infoPane.add(viewPane);
		infoPane.add(editPane);
		
		// Creates the main pane
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());
		mainPane.add(schemaPane,BorderLayout.CENTER);
		mainPane.add(infoPane,BorderLayout.SOUTH);
		
		// Constructs the Explorer Pane
		setBackground(Color.white);
		setBorder(new EmptyBorder(5,5,5,5));
		setLayout(new BorderLayout());
		add(new SelectionPane(),BorderLayout.NORTH);
		add(mainPane,BorderLayout.CENTER);
		add(new DataSourcePane(),BorderLayout.SOUTH);

		// Add in event listeners
		SelectedObjects.addSelectedObjectsListener(this);
		Schemas.addSchemasListener(this);

		// Resets the view
		resetView();
	}
	
	/** Clears an schema object info being displayed */
	public void clearInfo()
		{ viewPane.clearInfo(); }
	
	/** Display info on the specified schema element */
	public void displayInfo(GraphBuilder graph, SchemaElement schemaElement, Integer schemaID)
		{ viewPane.displayInfo(graph, schemaElement,schemaID); }
	
	/** Edit info about a specified schema object */
	public void editInfo(Integer action, Schema schema, SchemaElement schemaElement)
	{
		editPane.setVisible(true); viewPane.setVisible(false);
		editPane.editInfo(action,schema,schemaElement);
		validate();
	}
	
	/** Hides the info editor */
	public void doneEditingInfo()
		{ editPane.setVisible(false); viewPane.setVisible(true); clearInfo(); validate(); }

	/** Handles changes to the selected comparison schema */
	public void selectedComparisonSchemaChanged()
		{ if(schemaTree!=null) schemaTree.selectedComparisonSchemaChanged(); }
	
	// Handles changes to the displayed schema
	public void selectedSchemaChanged() { resetView(); }
	public void schemaLocked(Schema schema) { resetView(); }
	public void schemaParentsUpdated(Schema schema) { resetView(); }
	public void schemaUpdated(Schema schema) { schemaTree.schemaUpdated(schema); }
	public void schemaElementAdded(SchemaElement schemaElement) { schemaTree.schemaElementAdded(schemaElement); }
	public void schemaElementRemoved(SchemaElement schemaElement) { schemaTree.schemaElementRemoved(schemaElement); }
	
	// Unused listener events
	public void schemaAdded(Schema schema) {}
	public void schemaRemoved(Schema schema) {}
	public void selectedGroupsChanged() {}
}
