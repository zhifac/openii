// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mitre.flexidata.ygg.view.GenericView;
import org.mitre.flexidata.ygg.view.ViewFactory;
import org.mitre.flexidata.ygg.view.menu.Menu;
import org.mitre.flexidata.ygg.view.schema.porters.ImporterView;

/** Class for running the Ygg Schema Manager */
public class Ygg extends JFrame
{
	/** Stores a reference to the view pane */
	static private JPanel viewPane = null;
	
	/** Stores a self reference to the application */
	static public JFrame ygg = null;
	
	/** Displays the Ygg Schema Manager */
	public Ygg()
	{
		// Creates a self reference to the application
		ygg = this;
		
		// Create the view pane in Ygg
		viewPane = new JPanel();
		viewPane.setLayout(new BorderLayout());
		viewPane.add(ViewFactory.generateView(new ImporterView()),BorderLayout.CENTER);
		
		// Create the main pane in Ygg
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.add(new Menu(),BorderLayout.WEST);
		pane.add(viewPane,BorderLayout.CENTER);
		
		// Configure the Ygg frame
		setTitle("Ygg");		
        setSize(800, 600);
		setContentPane(pane);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
	}
	
	/** Displays the specified view */
	public static void setView(GenericView view)
	{
		viewPane.removeAll();
		viewPane.add(ViewFactory.generateView(view),BorderLayout.CENTER);
		viewPane.revalidate();
	}

	/** Runs the Ygg Schema Manager */
	public static void main (String[] args)
		{ new Ygg(); }
}
