// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

/** Class defining the generic view interface */
abstract public class GenericView extends JPanel
{	
	/** Function to retrieve the view title */
	abstract public String getTitle();
	
	/** Function to retrieve the view buttons */
	abstract public ArrayList<JButton> getButtons();
}