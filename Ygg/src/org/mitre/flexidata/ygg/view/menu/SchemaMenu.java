// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.menu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.mitre.flexidata.ygg.Ygg;
import org.mitre.flexidata.ygg.model.SchemaListener;
import org.mitre.flexidata.ygg.model.SchemaManager;
import org.mitre.flexidata.ygg.view.schema.porters.ImporterView;
import org.mitre.flexidata.ygg.view.schema.view.SchemaView;
import org.mitre.flexidata.ygg.view.shared.ColoredButton;
import org.mitre.schemastore.model.Schema;

/** Class for displaying the schema menu pane */
public class SchemaMenu extends JPanel implements ActionListener, ListSelectionListener, SchemaListener
{	
	/** Stores the schema list */
	private JList schemaList = null;
	
	/** Stores the "Add Schema" button */
	private ColoredButton addSchemaButton = new ColoredButton("Add Schema");
	
	/** Comparator for sorting schemas */
	private class SchemaComparator implements Comparator<Schema>
	{
		public int compare(Schema schema1, Schema schema2)
			{ return schema1.getName().compareTo(schema2.getName()); }		
	}
	
	/** Constructs the schema menu pane */
	public SchemaMenu()
	{
		// Generate the list of items to be managed
		ArrayList<Schema> schemas = SchemaManager.getSchemas();
		Collections.sort(schemas,new SchemaComparator());
		DefaultListModel model = new DefaultListModel();
		for(Schema schema : schemas) model.addElement(schema);
		schemaList = new JList(model);

		// Generate the scroll pane for showing the items being managed
		JScrollPane schemaListPane = new JScrollPane();
		schemaListPane.setViewportView(schemaList);
		schemaListPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		schemaListPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Generate the button pane
		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new EmptyBorder(5,0,0,0));
		buttonPane.setOpaque(false);
		buttonPane.add(addSchemaButton);
		
		// Generate the schema menu pane
		setOpaque(false);
		setLayout(new BorderLayout());
		add(schemaListPane,BorderLayout.CENTER);
		add(buttonPane,BorderLayout.SOUTH);

		// Add menu listeners
		schemaList.addListSelectionListener(this);
		addSchemaButton.addActionListener(this);
	}
	
	/** Handles changes to the selected schema */
	public void valueChanged(ListSelectionEvent e)
	{
		Schema schema = (Schema)schemaList.getSelectedValue();
		Ygg.setView(new SchemaView(schema));
	}

	/** Handles the pressing of the import button */
	public void actionPerformed(ActionEvent e)
	{
		schemaList.removeListSelectionListener(this);
		schemaList.clearSelection();
		schemaList.addListSelectionListener(this);
		Ygg.setView(new ImporterView());
	}
	
	/** Handles the adding of a schema to the schema menu */
	public void schemaAdded(Schema schema)
	{
		DefaultListModel model = (DefaultListModel)schemaList.getModel();
		SchemaComparator comparator = new SchemaComparator();
		int i;
		for(i=0; i<model.getSize(); i++)
			if(comparator.compare(schema,(Schema)model.getElementAt(i))<0) break;
		model.insertElementAt(schema,i);
		schemaList.setSelectedIndex(i);
	}
	
	/** Handles the removal of a schema from the schema menu */
	public void schemaRemoved(Schema schema)
	{
		DefaultListModel model = (DefaultListModel)schemaList.getModel();
		schemaList.removeListSelectionListener(this);
		model.removeElement(schema);
		schemaList.addListSelectionListener(this);
		Ygg.setView(new ImporterView());
	}
}