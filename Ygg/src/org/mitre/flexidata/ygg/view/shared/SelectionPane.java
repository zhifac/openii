// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.shared;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.mitre.flexidata.ygg.view.Consts;

/** Class for displaying the selection pane */
public class SelectionPane<E> extends JPanel
{
	// Stores the selection list
	private JComboBox selectionList = null;
	
	/** Constructs the selection pane */
	public SelectionPane(String label, ArrayList<E> list)
	{
		// Initializes the label
		JLabel selectionLabel = new JLabel(label+": ");
		selectionLabel.setVerticalAlignment(SwingConstants.CENTER);
		selectionLabel.setFont(Consts.MENU_OPTION_FONT);
		
		// Generate the selection list
		selectionList = new JComboBox(new Vector<E>(list));
		selectionList.setBackground(Color.white);
		selectionList.setFocusable(false);
		
		// Generate the importer list pane
		JPanel exporterListPane = new JPanel();
		exporterListPane.setOpaque(false);
		exporterListPane.setLayout(new BoxLayout(exporterListPane,BoxLayout.X_AXIS));
		exporterListPane.add(selectionLabel);
		exporterListPane.add(selectionList);
		
		// Generate the selection pane
		setBorder(new EmptyBorder(8,5,0,0));
		setOpaque(false);
		setLayout(new BorderLayout());
		add(exporterListPane,BorderLayout.WEST);
	}

	/** Returns the selection */
	@SuppressWarnings("unchecked")
	public E getSelection()
		{ return (E)selectionList.getSelectedItem(); }
	
	/** Adds an action listener */
	public void addActionListener(ActionListener listener)
		{ selectionList.addActionListener(listener); }
	
	/** Removes an action listener */
	public void removeActionListener(ActionListener listener)
		{ selectionList.removeActionListener(listener); }
}