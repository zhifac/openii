// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/** Class for displaying the view pane */
public class ViewFactory extends JPanel
{
	/** Class for the view header */
	static private class Header extends JPanel
	{
		/** Constructs the header */
		private Header(String title)
		{
			// Initialize the title
			JLabel label = new JLabel(title);
			label.setFont(Consts.TITLE_FONT);
			
			// Generate the header pane
			setBorder(new EmptyBorder(0,0,10,0));
			setOpaque(false);
			setLayout(new BorderLayout());
			add(label,BorderLayout.CENTER);			
		}
		
		/** Handles the painting of the header */
		public void paint(Graphics g)
		{
			// Paints the menu pane
			super.paint(g);
			
			// Paints a line under the header pane
			g.setColor(Consts.ORANGE);
			g.fillRect(0, getHeight()-6, getWidth(), 2);
			g.fillRect(0, getHeight()-2, getWidth(), 1);
		}
	}

	/** Class for the view footer */
	static private class Footer extends JPanel
	{
		/** Constructs the footer */
		private Footer(ArrayList<JButton> buttons)
		{
			// Generate the buttons pane
			JPanel buttonsPane = new JPanel();
			buttonsPane.setOpaque(false);
			buttonsPane.setLayout(new GridLayout(1,3));
			for(JButton button : buttons)
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setOpaque(false);
				buttonPane.setBorder(new EmptyBorder(0,5,0,5));
				buttonPane.setPreferredSize(new Dimension(100,25));
				buttonPane.setLayout(new BorderLayout());
				buttonPane.add(button,BorderLayout.CENTER);
				buttonsPane.add(buttonPane);
			}
				
			// Generate the footer pane
			setBorder(new EmptyBorder(15,0,5,0));
			setOpaque(false);
			setLayout(new BorderLayout());
			add(buttonsPane,BorderLayout.EAST);
		}
		
		/** Handles the painting of the header */
		public void paint(Graphics g)
		{
			// Paints the menu pane
			super.paint(g);
			
			// Paints a line under the header pane
			g.setColor(Consts.ORANGE);
			g.fillRect(0, 4, getWidth(), 2);
			g.fillRect(0, 1, getWidth(), 1);		
		}
	}
	
	/** Generates the specified view */
	static public JPanel generateView(GenericView view)
	{
		// Generate the view pane
		JPanel viewPane = new JPanel();
		viewPane.setOpaque(false);
		viewPane.setLayout(new BorderLayout());
		viewPane.add(view);
		
		// Generate the pane
		JPanel pane = new JPanel();
		pane.setBackground(Consts.WHITE);
		pane.setBorder(new EmptyBorder(10,10,10,10));
		pane.setLayout(new BorderLayout());
		pane.add(new Header(view.getTitle()),BorderLayout.NORTH);
		pane.add(viewPane,BorderLayout.CENTER);
		pane.add(new Footer(view.getButtons()),BorderLayout.SOUTH);
		return pane;
	}
}