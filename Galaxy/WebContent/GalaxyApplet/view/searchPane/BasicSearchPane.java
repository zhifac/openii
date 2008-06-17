// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.searchPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.search.SearchManager;

/** Class for handling basic searches */
class BasicSearchPane extends JPanel implements ActionListener, KeyListener, MouseListener
{
	/** Text field used for entering keywords */
	private JTextField keywordField = new JTextField();
	
	/** Constructs the basic search pane */
	BasicSearchPane()
	{			
		// Initialize the toggle field
		JLabel toggle = new JLabel();
		toggle.setForeground(Color.blue);
		toggle.setFont(new Font(null,Font.BOLD,10));		
		toggle.setText("(Advanced)");
		toggle.addMouseListener(this);
		
		// Initialize the keyword field
		keywordField.setBorder(new CompoundBorder(new EmptyBorder(0,0,0,3),new CompoundBorder(new LineBorder(Color.gray),new EmptyBorder(1,1,1,1))));
		keywordField.addKeyListener(this);
		
		// Initializes the search button
		JButton searchButton = new JButton("Search");
		searchButton.setBorder(new CompoundBorder(new LineBorder(Color.black),new EmptyBorder(0,2,0,2)));
		searchButton.setFocusable(false);
		searchButton.addActionListener(this);
		
		// Constructs the title pane
		JPanel titlePane = new JPanel();
		titlePane.setOpaque(false);
		titlePane.setLayout(new BoxLayout(titlePane,BoxLayout.X_AXIS));
		titlePane.add(new JLabel("Basic Search "));
		titlePane.add(toggle);
		
		// Constructs the search pane
		JPanel keywordPane = new JPanel();
		keywordPane.setLayout(new BorderLayout());
		keywordPane.add(keywordField,BorderLayout.CENTER);
		keywordPane.add(searchButton,BorderLayout.EAST);
		
		// Creates the pane for displaying the selected schema
		setBorder(new EmptyBorder(0,0,5,0));
		setLayout(new BorderLayout());
		setOpaque(false);
		add(titlePane,BorderLayout.NORTH);
		add(keywordPane,BorderLayout.CENTER);
	}

	/** Handles the submission of search keywords */
	public void actionPerformed(ActionEvent e)
		{ SearchManager.searchFor(keywordField.getText()); }
	
	/** Handles the submission of search keywords */
	public void keyTyped(KeyEvent e)
		{ if(e.getKeyChar() == KeyEvent.VK_ENTER) SearchManager.searchFor(keywordField.getText());}
	
	/** Toggles the keyword pane between basic and advanced search */
	public void mouseClicked(MouseEvent e)
		{ SearchPane.searchPane.changeToAdvancedMode(); }

	/** Switches mouse to hand icon */
	public void mouseEntered(MouseEvent e)
		{ setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
	
	/** Switches mouse back to normal icon */
	public void mouseExited(MouseEvent e)
		{ setCursor(Cursor.getDefaultCursor()); }
	
	// Unused mouse listener events
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
