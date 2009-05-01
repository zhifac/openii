// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.schemaPane.navigatorPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.mitre.galaxy.model.Schemas;
import org.mitre.galaxy.view.schemaPane.SchemaPane;
import org.mitre.schemastore.model.Schema;

/** Class for displaying the navigator pane of Galaxy */
public class NavigatorPane extends JPanel implements ActionListener
{	
	/** Stores the schema pane associated with this navigation pane */
	private SchemaPane schemaPane = null;
	
	/** Stores the navigator display */
	private NavigatorDisplay display = null;
	
	/** Stores the list of comparable schemas */
	private JComboBox schemaList = new JComboBox();
	
	/** Navigator pane renderer */
	private class NavigatorPaneRenderer extends DefaultListCellRenderer
	{
		/** Defines how items in the selection list should be rendered */
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			Schema schema = Schemas.getSchema((Integer)value);
			if(schema==null) return new JLabel("<None>");
			else return new JLabel(schema.getName());
		}
	}
	
	/** Constructs the display pane */
	private JPanel getDisplayPane()
	{
		// Set up the display
		display = new NavigatorDisplay(schemaPane);
		display.setBackground(new Color((float)1.0,(float)1.0,(float)0.85));
		
		// Set up the display pane
		JPanel displayPane = new JPanel();
		displayPane.setBorder(new LineBorder(Color.lightGray,4));
		displayPane.setLayout(new BorderLayout());
		displayPane.add(display);
		return displayPane;
	}
	
	/** Constructs the comparison pane */
	private JPanel getComparisonPane()
	{
		// Creates the comparison label pane
		JPanel labelPane = new JPanel();
		labelPane.setOpaque(false);
		labelPane.setLayout(new BorderLayout());
		labelPane.add(new JLabel("Compare To:"),BorderLayout.WEST);
		
		// Creates the list of schemas which can be compared against
		schemaList.setBackground(Color.white);
		schemaList.setFocusable(false);
		schemaList.setRenderer(new NavigatorPaneRenderer());
		schemaList.addActionListener(this);
		
		// Set up the comparison pane
		JPanel comparisonPane = new JPanel();
		comparisonPane.setOpaque(false);
		comparisonPane.setLayout(new BorderLayout());
		comparisonPane.add(labelPane,BorderLayout.NORTH);
		comparisonPane.add(schemaList,BorderLayout.CENTER);
		return comparisonPane;
	}
	
	/** Constructs the navigator pane of Galaxy */
	public NavigatorPane(SchemaPane schemaPane)
	{
		this.schemaPane = schemaPane;
		setBounds(5,5,150,175);
		setLayout(new BorderLayout());
		setOpaque(false);
		add(getDisplayPane(),BorderLayout.CENTER);
		add(getComparisonPane(),BorderLayout.SOUTH);
	}
	
	/** Resets the navigator pane */
	public void update()
	{
		// Reset the display
		display.reset();
		
		// Reset the comparison pane
		Vector<Integer> schemas = new Vector<Integer>(Schemas.getAssociatedSchemas(schemaPane.getSchemaID()));
		schemas.remove(schemaPane.getSchemaID());
		schemas.add(0,null);
		schemaList.removeActionListener(this);
		schemaList.setModel(new DefaultComboBoxModel(schemas));
		schemaList.setSelectedItem(schemaPane.getComparisonSchemaID());
		schemaList.addActionListener(this);
	}

	/** Handles the selection of a comparison schema */
	public void actionPerformed(ActionEvent e)
		{ schemaPane.fireComparisonSchemaChangedEvent((Integer)schemaList.getSelectedItem()); }
}