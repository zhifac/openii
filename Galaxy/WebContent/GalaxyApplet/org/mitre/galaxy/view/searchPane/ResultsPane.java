package org.mitre.galaxy.view.searchPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.mitre.galaxy.model.listeners.SearchListener;
import org.mitre.galaxy.model.search.SearchManager;
import org.mitre.galaxy.model.search.SearchResult;


/** Class for displaying the search results */
class ResultsPane extends JPanel implements SearchListener
{
	/** Stores the selected schema */
	private Integer schemaID = null;
	
	// Stores objects used by the search results pane
	private JScrollPane scrollpane = new JScrollPane();
	private JPanel searchResults = new JPanel();
	
	/** Constructs the search results pane */
	ResultsPane()
	{
		// Initialize the search results
		searchResults.setBackground(Color.white);
		searchResults.setLayout(new BoxLayout(searchResults,BoxLayout.Y_AXIS));
		
		// Initialize the search results pane
		JPanel searchResultsPane = new JPanel();
		searchResultsPane.setBackground(Color.white);
		searchResultsPane.setLayout(new BorderLayout());
		searchResultsPane.add(searchResults,BorderLayout.NORTH);
		
		// Create the scroll pane for showing the search results
		scrollpane.setViewportView(searchResultsPane);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		// Constructs the search results pane
		setLayout(new BorderLayout());
		add(scrollpane,BorderLayout.CENTER);
		
		// Add listeners to monitor events which affect the search results pane
		SearchManager.addSearchListener(this);
	}
	
	/** Sets the selected schema */
	public void setSchema(Integer schemaID)
	{
		this.schemaID = schemaID;
		for(Component component : searchResults.getComponents())
		{
			ResultPane resultPane = (ResultPane)component;
			resultPane.highlightResult(resultPane.getSchema().getId().equals(schemaID));	
		}		
	}	
	
	/** Handles changes to the selected schema */
	public void searchResultsChanged()
	{
		// Clear out old search results
		searchResults.removeAll();
		for(SearchResult searchResult : SearchManager.getSearchResults())
		{
			ResultPane resultPane = new ResultPane(searchResult);
			resultPane.highlightResult(resultPane.getSchema().getId().equals(schemaID));
			searchResults.add(resultPane);
		}
		searchResults.revalidate();
		searchResults.repaint();
		scrollpane.revalidate();
		scrollpane.repaint();
	}
}
