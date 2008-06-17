// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import view.groupPane.managePane.ManageGroupPane;

/** Class for displaying the Group Pane of Galaxy */
public class GroupPane extends JPanel implements ActionListener
{
	// Stores various objects used by the Group Pane
	public JPanel groupPane = new JPanel();
	public JButton selectButton = new JButton("Select Groups");
	public JButton manageButton = new JButton("Manage Groups");
	
	/** Handles the selecting of the specified button */
	private void selectButton(JButton button)
	{
		JButton unselectedButton = button==selectButton ? manageButton : selectButton;
		button.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED,Color.lightGray,Color.gray), new EmptyBorder(1,1,1,1)));
		unselectedButton.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,Color.lightGray,Color.gray), new EmptyBorder(1,1,1,1)));
	}
	
	/** Constructs a button pane */
	private JPanel getButtonPane(JButton button)
	{
		// Initialize the button
		button.setFocusable(false);
		button.addActionListener(this);
		
		// Initialize the button pane
		JPanel pane = new JPanel();
		pane.setOpaque(false);
		pane.setBorder(new EmptyBorder(2,4,2,4));
		pane.setLayout(new BorderLayout());
		pane.add(button,BorderLayout.CENTER);
		return pane;
	}
	
	/** Constructs the Group Pane */
	public GroupPane()
	{
		// Constructs the button pane
		JPanel buttonPane = new JPanel();
		buttonPane.setOpaque(false);
		buttonPane.setBorder(new EmptyBorder(3,1,3,1));
		buttonPane.setLayout(new GridLayout(1,2));
		buttonPane.add(getButtonPane(selectButton));
		buttonPane.add(getButtonPane(manageButton));
		selectButton(selectButton);
		
		// Construct the group pane
		groupPane.setOpaque(false);
		groupPane.setLayout(new CardLayout());
		groupPane.setBorder(new EmptyBorder(0,0,0,0));
		groupPane.add(new SelectGroupPane(),"Select");
		groupPane.add(new ManageGroupPane(),"Manage");
		
		// Lays out the group pane
		setBackground(Color.white);
		setBorder(new EmptyBorder(4,4,4,4));
		setLayout(new BorderLayout());
		add(buttonPane,BorderLayout.NORTH);
		add(groupPane,BorderLayout.CENTER);
	}
	
	
	/** Handles changes between "select" and "manage" group panes */
	public void actionPerformed(ActionEvent e)
	{
		// Adjust border when buttons pressed
		boolean select = e.getSource()==selectButton;
		selectButton(select?selectButton:manageButton);
		
		// Changes group view
	    CardLayout view = (CardLayout)(groupPane.getLayout());
	    view.show(groupPane, select?"Select":"Manage");
	}
}
