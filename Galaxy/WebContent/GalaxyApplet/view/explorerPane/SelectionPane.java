// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import model.Schemas;
import model.SelectedObjects;
import model.listeners.SchemasListener;
import model.listeners.SelectedObjectsListener;

/** Class for displaying the selection pane for the explorer pane */
class SelectionPane extends JPanel implements ActionListener, MouseListener, MouseMotionListener, SchemasListener, SelectedObjectsListener
{
	// Objects used by the selection pane
	private JLabel link = new JLabel();
	private JComboBox schemaList = new JComboBox();

	/** Selection pane renderer */
	private class SelectionPaneRenderer extends DefaultListCellRenderer
	{
		/** Defines how items in the selection list should be rendered */
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			// Find the selection name
			Schema schema = Schemas.getSchema((Integer)value);
			if(schema==null) return new JPanel();
			
			// Generate a selection list item component
			JPanel pane = new JPanel();
			pane.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
			pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
			pane.add(new JLabel(schema.getName()));
			
			// Display "editable" for all listed schemas that are not yet locked
			if(!schema.getLocked())
			{
				JLabel editableLabel = new JLabel(" (Editable)");
				editableLabel.setForeground(new Color(150,0,0));
				pane.add(editableLabel);
			}
			
			// Return the list item component
			return pane;
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
		// Initialize the schema link
		link.setForeground(Color.blue);
		link.setFont(new Font(null,Font.BOLD,10));
		link.addMouseListener(this);
		link.addMouseMotionListener(this);
		
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
		labelPane.add(link,BorderLayout.EAST);
		
		// Creates the selection options pane
		setBackground(Color.white);
		setBorder(new EmptyBorder(0,0,5,0));
		setLayout(new BorderLayout());
		add(labelPane,BorderLayout.NORTH);
		add(schemaList,BorderLayout.CENTER);

		// Add a listener to monitor for changes in the selected schema
		Schemas.addSchemasListener(this);
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
	
	/** Handles the addition of a schema */
	public void schemaAdded(Schema schema)
	{
		DefaultComboBoxModel model = (DefaultComboBoxModel)schemaList.getModel();
		for(int i=0; i<model.getSize(); i++)
		{
			String name = Schemas.getSchema((Integer)model.getElementAt(i)).getName();
			if(name.compareTo(schema.getName())>0)
				{ model.insertElementAt(schema.getId(), i); break; }
		}
	}

	/** Handles the updating of a schema */
	public void schemaUpdated(Schema schema)
		{ repaint(); }

	/** Handles the locking of a schema */
	public void schemaLocked(Schema schema)
		{ link.setText("Extend"); validate(); revalidate(); repaint(); }
	
	/** Handles the removal of a schema */
	public void schemaRemoved(Schema schema)
	{		
		// Removes the schema from the list
		schemaList.removeItem(schema.getId());
		if(schemaList.getSelectedItem()==null && schemaList.getItemCount()>0)
			SelectedObjects.setSelectedSchema((Integer)schemaList.getItemAt(0));
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
			
			// Update the link text
			boolean committed = Schemas.getSchema(selectedSchemaID).getLocked();
			link.setText(committed ? "Extend" : "Commit");
			validate(); revalidate(); repaint();
		}
	}

	/** Handles changes to the selected groups */
	public void selectedGroupsChanged()
		{ updateSchemaList(); }
	
	/** Handles the selection of a schema nodes */
	public void mouseClicked(MouseEvent e)
	{
		Schema schema = Schemas.getSchema(SelectedObjects.getSelectedSchema());
		if(schema.getLocked()) Schemas.extendSchema(SelectedObjects.getSelectedSchema());
		else Schemas.lockSchema(schema.getId());
	}
	
	/** Displays hand when over link */
	public void mouseMoved(MouseEvent e)
		{ setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
	
	/** Displays arrow when not over link */
	public void mouseExited(MouseEvent e)
		{ setCursor(Cursor.getDefaultCursor()); }
	
	// Unused listener events
	public void mouseEntered(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void schemaParentsUpdated(Schema schema) {}
	public void selectedComparisonSchemaChanged() {}
	public void schemaElementAdded(SchemaElement schemaElement) {}
	public void schemaElementRemoved(SchemaElement schemaElement) {}
}