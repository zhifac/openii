// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.searchPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.galaxy.model.search.SearchManager;


/** Class for handling advanced searches */
class AdvancedSearchPane extends JPanel implements ActionListener, MouseListener
{
	/** Text field used for entering keywords */
	private JTextField schemaField = new JTextField();
	private JTextField entityField = new JTextField();
	private JTextField domainField = new JTextField();
	private JTextField relationshipField = new JTextField();
	
	/** Constructs a text label */
	private JLabel getLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		return label;
	}
	
	/** Constructs the advance search pane */
	AdvancedSearchPane()
	{			
		// Initialize the toggle field
		JLabel toggle = new JLabel();
		toggle.setForeground(Color.blue);
		toggle.setFont(new Font(null,Font.BOLD,10));		
		toggle.setText("(Basic)");
		toggle.addMouseListener(this);
		
		// Initializes the search button
		JButton searchButton = new JButton("Search");
		searchButton.setBorder(new CompoundBorder(new LineBorder(Color.black),new EmptyBorder(0,2,0,2)));
		searchButton.setFocusable(false);
		searchButton.addActionListener(this);
		
		// Constructs the title pane
		JPanel titlePane = new JPanel();
		titlePane.setOpaque(false);
		titlePane.setLayout(new BoxLayout(titlePane,BoxLayout.X_AXIS));
		titlePane.add(new JLabel("Advanced Search "));
		titlePane.add(toggle);
		
		// Constructs the labels pane
		JPanel labelsPane = new JPanel();
		labelsPane.setBorder(new EmptyBorder(0,0,0,3));
		labelsPane.setLayout(new GridLayout(5,1));
		labelsPane.setOpaque(false);
		labelsPane.add(getLabel("Schema:"));
		labelsPane.add(getLabel("Entity:"));
		labelsPane.add(getLabel("Domain:"));
		labelsPane.add(getLabel("Relationship:"));
		
		// Constructs the fields pane
		JPanel fieldsPane = new JPanel();
		fieldsPane.setLayout(new GridLayout(5,1));
		fieldsPane.setOpaque(false);
		fieldsPane.add(schemaField);
		fieldsPane.add(entityField);
		fieldsPane.add(domainField);
		fieldsPane.add(relationshipField);
		
		// Constructs the keyword pane
		JPanel keywordPane = new JPanel();
		keywordPane.setLayout(new BorderLayout());
		keywordPane.setOpaque(false);
		keywordPane.add(labelsPane,BorderLayout.WEST);
		keywordPane.add(fieldsPane,BorderLayout.CENTER);
		
		// Constructs the search pane
		JPanel searchPane = new JPanel();
		searchPane.setLayout(new FlowLayout());
		searchPane.setOpaque(false);
		searchPane.add(searchButton);
		
		// Constructs the main pane
		JPanel pane = new JPanel();
		pane.setBorder(new CompoundBorder(new LineBorder(Color.gray),new EmptyBorder(4,4,0,4)));
		pane.setLayout(new BorderLayout());
		pane.setOpaque(false);
		pane.add(keywordPane,BorderLayout.CENTER);
		pane.add(searchPane,BorderLayout.SOUTH);
		
		// Creates the pane for displaying the selected schema
		setBorder(new EmptyBorder(0,0,5,0));
		setLayout(new BorderLayout());
		setOpaque(false);
		add(titlePane,BorderLayout.NORTH);
		add(pane,BorderLayout.CENTER);
	}

	/** Adds keywords for the specified field (while appending the prefix) */
	private StringBuffer getKeywords(JTextField field, String prefix)
	{
		StringBuffer keywordString = new StringBuffer();
		String keywords[] = field.getText().split(" ");
		for(String keyword : keywords)
			if(keyword.length()>0)
				keywordString.append(prefix+":"+keyword+" ");
		return keywordString;
	}
	
	/** Handles the submission of search keywords */
	public void actionPerformed(ActionEvent e)
	{
		StringBuffer searchString = new StringBuffer();
		searchString.append(getKeywords(schemaField,"schema"));
		searchString.append(getKeywords(entityField,"entity"));
		searchString.append(getKeywords(domainField,"domain"));
		searchString.append(getKeywords(relationshipField,"relationship"));
		SearchManager.searchFor(searchString.toString());
	}
	
	/** Toggles the keyword pane between basic and advanced search */
	public void mouseClicked(MouseEvent e)
		{ SearchPane.searchPane.changeToBasicMode(); }

	/** Switches mouse to hand icon */
	public void mouseEntered(MouseEvent e)
		{ setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
	
	/** Switches mouse back to normal icon */
	public void mouseExited(MouseEvent e)
		{ setCursor(Cursor.getDefaultCursor()); }
	
	// Unused mouse listener events
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
