// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.shared;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

/** Class for displaying a colored button */
public class ColoredButton extends JButton implements MouseListener
{
	// Defines the various colors used within the button
	private final Color baseColor = new Color(0xffa500);
	private final Color pressedColor = new Color(0xffb510);
	private final Color highlightColor = new Color(0xffc125);
	private final Color shadowColor = new Color(0xcd8500);
	
	/** Modify the ButtonUI to not change the background color when the button is pressed */
	private class YggButtonUI extends BasicButtonUI
	{
		protected void paintButtonPressed(Graphics g, AbstractButton button)
		{
			g.setColor(pressedColor);
			g.fillRect(getX()-getWidth()/2-2,getY()-getHeight()/2-2,getWidth()+5,getHeight()+5);
		}
	}
	
	/** Set the button border */
	private void setBorder(boolean selected)
		{ setBorder(new CompoundBorder(new BevelBorder(selected ? BevelBorder.LOWERED : BevelBorder.RAISED,highlightColor,shadowColor), new EmptyBorder(2,2,2,2)));	}
	
	/** Constructs the button */
	public ColoredButton(String text)
	{
		super(text);
		setUI(new YggButtonUI());
		setBackground(baseColor);
		setBorder(false);
		setFocusable(false);
		addMouseListener(this);
	}

	// Indent the borders when the button is pressed
	public void mousePressed(MouseEvent arg0) { setBorder(true); }
	public void mouseReleased(MouseEvent arg0) { setBorder(false); }
	
	// Unused listener events
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
}
