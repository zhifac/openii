// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.searchPane;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/** Class for displaying the Search Pane of Galaxy */
public class SearchPane extends JPanel
{	
	// Stores a reference to the Search pane
	static SearchPane searchPane;
	
	// Stores components used by the search pane
	JPanel searchPanel = new JPanel();
	
	/** Constructs the Search Pane */
	public SearchPane()
	{		
		// Stores the reference to this search pane
		searchPane = this;

		// Constructs the search panel
		searchPanel.setLayout(new BorderLayout());
		searchPanel.setOpaque(false);
		searchPanel.add(new BasicSearchPane(),BorderLayout.CENTER);
		
		// Constructs the search pane
		setBackground(Color.white);
		setBorder(new EmptyBorder(5,5,5,5));
		setLayout(new BorderLayout());
		add(searchPanel,BorderLayout.NORTH);
		add(new ResultsPane(),BorderLayout.CENTER);
	}
	
	/** Change the search pane to basic mode */
	void changeToBasicMode()
	{
		searchPanel.removeAll();
		searchPanel.add(new BasicSearchPane(),BorderLayout.CENTER);
		validate();
	}

	/** Change the search pane to advanced mode */
	void changeToAdvancedMode()
	{
		searchPanel.removeAll();
		searchPanel.add(new AdvancedSearchPane(),BorderLayout.CENTER);
		validate();
	}
}
