// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.shared;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.flexidata.ygg.view.Consts;
import org.mitre.flexidata.ygg.view.images.ImageManager;

/** Class for displaying the description pane */
public class DescriptionPane extends JPanel
{
	// Stores objects used by the description pane
	private JPanel warningPane = new JPanel();
	private JTextArea description = new JTextArea();
	
	/** Constructs the description pane */
	public DescriptionPane(String text)
	{
		// Initialize the warning pane
		warningPane.setBackground(Consts.YELLOW);
		warningPane.setLayout(new BorderLayout());
		
		// Initialize the description
		description.setBorder(new EmptyBorder(5,5,5,5));
		description.setFont(new Font("SansSerif",Font.PLAIN,13));
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setText(text);
		
		// Generate the description pane
		setOpaque(false);
		setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10), new LineBorder(Consts.BLACK, 2)));
		setLayout(new BorderLayout());
		add(warningPane, BorderLayout.WEST);
		add(description, BorderLayout.CENTER);
	}
	
	/** Sets the description */
	public void setText(String text, boolean warning)
	{
		// Adjust the warning pane
		if(warning) warningPane.add(new JLabel(new ImageIcon(ImageManager.getImage("Warning.jpg"))));
		else warningPane.removeAll();
		warningPane.revalidate();

		// Adjust the displayed description
		description.setBackground(warning ? Consts.YELLOW : Consts.WHITE);
		description.setText(text);
	}
}
