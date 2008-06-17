package view.searchPane;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.listeners.SearchListener;
import model.search.SearchManager;
import model.search.SearchResult;

/** Class for displaying the search results */
class ResultsPane extends JPanel implements SearchListener
{	
	// Stores objects used by the search results pane
	JScrollPane scrollpane = new JScrollPane();
	JPanel searchResults = new JPanel();
	
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
	
	/** Handles changes to the selected schema */
	public void searchResultsChanged()
	{
		// Clear out old search results
		searchResults.removeAll();
		for(SearchResult searchResult : SearchManager.getSearchResults())
			searchResults.add(new ResultPane(searchResult));
		searchResults.revalidate();
		searchResults.repaint();
		scrollpane.revalidate();
		scrollpane.repaint();
	}
}
