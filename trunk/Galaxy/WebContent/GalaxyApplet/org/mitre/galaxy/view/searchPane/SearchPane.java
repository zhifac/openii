// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.searchPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/** Class for displaying the Search Pane of Galaxy */
public class SearchPane extends JPanel
{	
	// Stores components used by the search pane 
	private JPanel searchPane = new JPanel();
	private ResultsPane resultsPane = new ResultsPane();	
	
	/** List of listeners monitoring the extensions pane */
	private ArrayList<SearchPaneListener> listeners = new ArrayList<SearchPaneListener>();
	
	/** Constructs the Search Pane */
	public SearchPane()
	{		
		// Constructs the search panel
		searchPane.setLayout(new BorderLayout());
		searchPane.setOpaque(false);
		searchPane.add(new BasicSearchPane(),BorderLayout.CENTER);
		
		// Constructs the search pane
		setBackground(Color.white);
		setBorder(new EmptyBorder(5,5,5,5));
		setLayout(new BorderLayout());
		add(searchPane,BorderLayout.NORTH);
		add(resultsPane,BorderLayout.CENTER);
	}
	
	/** Sets the schema associated with search pane */
	public void setSchema(Integer schemaID)
		{ resultsPane.setSchema(schemaID); }
	
	/** Change the search pane to basic mode */
	void changeToBasicMode()
	{
		searchPane.removeAll();
		searchPane.add(new BasicSearchPane(),BorderLayout.CENTER);
		validate();
	}

	/** Change the search pane to advanced mode */
	void changeToAdvancedMode()
	{
		searchPane.removeAll();
		searchPane.add(new AdvancedSearchPane(),BorderLayout.CENTER);
		validate();
	}
	
	/** Adds a listener monitoring the search pane */
	public void addSearchPaneListener(SearchPaneListener listener)
		{ listeners.add(listener); }
	
	/** Removes a listener monitoring the search pane */
	public void removeSearchPaneListener(SearchPaneListener listener)
		{ listeners.remove(listener); }
	
	/** Fires the schema selected event */
	public void fireSchemaSelectedEvent(Integer schemaID)
		{ for(SearchPaneListener listener : listeners) listener.schemaSelected(schemaID); }
}
