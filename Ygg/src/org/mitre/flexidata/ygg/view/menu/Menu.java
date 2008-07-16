// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.menu;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.mitre.flexidata.ygg.model.SchemaManager;
import org.mitre.flexidata.ygg.view.Consts;
import org.mitre.flexidata.ygg.view.images.ImageManager;

/** Class for displaying the menu pane */
public class Menu extends JPanel
{
	/** Stores the various menu modes */
	private final Integer SCHEMA_MODE = 0;
	private final Integer MAPPING_MODE = 1;
	private final Integer GROUP_MODE = 2;
	
	// Stores references to various panel objects
	private JLabel menuHeader = new JLabel();
	private JPanel menuView = new JPanel();
	private JPanel menuOptionsPane = new JPanel();
	
	/** Class for storing a menu option */
	private class MenuOption extends JPanel implements MouseListener, MouseMotionListener
	{
		/** Stores the menu option */
		private Integer mode;
		
		/** Stores the option label */
		private JLabel optionLabel;
		
		/** Constructs the menu option */
		private MenuOption(Integer mode)
		{
			this.mode = mode;
			
			// Generate the option label
			optionLabel = new JLabel(" " + getModeLabel(mode) + " Manager");
			optionLabel.setFont(Consts.MENU_OPTION_FONT);
			
			// Initialize the menu option pane
			setBorder(new EmptyBorder(5,0,5,0));
			setOpaque(false);
			setLayout(new BorderLayout());
			add(new JLabel(new ImageIcon(ImageManager.getImage("Leaf.jpg"))),BorderLayout.WEST);
			add(optionLabel);

			// Monitor menu option mouse events
			optionLabel.addMouseListener(this);
			optionLabel.addMouseMotionListener(this);
		}

		// Display a hand cursor when the mouse is hovering above option
		public void mouseEntered(MouseEvent arg0) { setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
		public void mouseExited(MouseEvent arg0) { setCursor(Cursor.getDefaultCursor()); }
		
		/** Switch menu modes */
		public void mouseClicked(MouseEvent arg0) { switchMode(mode); }		
		
		// Unused listener events
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
		public void mouseDragged(MouseEvent arg0) {}
		public void mouseMoved(MouseEvent arg0) {}
	}
	
	/** Returns the label associated with the specified mode */
	private String getModeLabel(Integer mode)
		{ return mode==SCHEMA_MODE ? "Schema" : mode==MAPPING_MODE ? "Mapping" : "Group"; }
	
	/** Switch to the specified mode */
	private void switchMode(Integer mode)
	{
		// Highlight the currently selected menu option
		for(int i=0; i<menuOptionsPane.getComponentCount(); i++)
		{
			MenuOption option = (MenuOption)menuOptionsPane.getComponent(i);
			option.optionLabel.setForeground(mode==option.mode ? Consts.TAN : Consts.BLACK);
		}

		// Remove the old menu
		if(menuView.getComponentCount()>0)
		{
			JPanel oldMenu = (JPanel)menuView.getComponent(0);
			if(oldMenu instanceof SchemaMenu) SchemaManager.removeSchemasListener((SchemaMenu)oldMenu);
			menuView.removeAll();
		}
			
		// Generate the new menu to be displayed
		JPanel newMenu;
		if(mode==SCHEMA_MODE) { newMenu = new SchemaMenu();  SchemaManager.addSchemaListener((SchemaMenu)newMenu); }
		else if(mode==MAPPING_MODE) newMenu = new MappingMenu();
		else newMenu = new GroupMenu();
		
		// Update the menu header and view
		menuHeader.setText(getModeLabel(mode) + " Management");
		menuView.add(newMenu);
	}
	
	/** Constructs the menu pane */
	public Menu()
	{
		// Generate the title pane
		JPanel titlePane = new JPanel();
		titlePane.setBorder(new EmptyBorder(10,10,10,10));
		titlePane.setOpaque(false);
		titlePane.add(new JLabel(new ImageIcon(ImageManager.getImage("Ygg.jpg"))));
		
		// Initialize the menu header and view
		menuHeader.setFont(Consts.MENU_FONT);
		menuHeader.setForeground(Consts.ORANGE);
		menuHeader.setHorizontalAlignment(SwingConstants.CENTER);
		menuHeader.setBorder(new EmptyBorder(0,0,8,0));
		menuView.setLayout(new BorderLayout());
		menuView.setOpaque(false);
		
		// Generate the selected menu pane
		JPanel menuPane = new JPanel();
		menuPane.setBorder(new EmptyBorder(0,10,0,10));
		menuPane.setOpaque(false);
		menuPane.setLayout(new BorderLayout());
		menuPane.add(menuHeader, BorderLayout.NORTH);
		menuPane.add(menuView, BorderLayout.CENTER);

		// Generate the menu options pane
		menuOptionsPane.setBackground(Consts.WHITE);
		menuOptionsPane.setBorder(new EmptyBorder(20,10,10,10));
		menuOptionsPane.setLayout(new BoxLayout(menuOptionsPane,BoxLayout.Y_AXIS));
		menuOptionsPane.add(new MenuOption(SCHEMA_MODE));
		menuOptionsPane.add(new MenuOption(MAPPING_MODE));
		menuOptionsPane.add(new MenuOption(GROUP_MODE));
		
		// Layout the Navigator Pane
		setPreferredSize(new Dimension(200,0));
		setBackground(Consts.BLUE);
		setLayout(new BorderLayout());
		add(titlePane,BorderLayout.NORTH);
		add(menuPane,BorderLayout.CENTER);
		add(menuOptionsPane,BorderLayout.SOUTH);
		
		// Switch to "Schema" mode
		switchMode(SCHEMA_MODE);
	}
	
	/** Handles the painting of the menu pane */
	public void paint(Graphics g)
	{
		// Paints the menu pane
		super.paint(g);
		
		// Adds in the curved menu bottom above the options pane
		g.setColor(Consts.WHITE);
		g.fillRect(menuOptionsPane.getWidth()-10,menuOptionsPane.getY()-10,10,10);
		g.setColor(Consts.BLUE);
		g.fillRect(menuOptionsPane.getX(), menuOptionsPane.getY(), menuOptionsPane.getWidth()-20, 10);
		g.fillArc(menuOptionsPane.getWidth()-40,menuOptionsPane.getY()-30,40,40,0,-90);
	}
}