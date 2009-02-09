package org.mitre.harmony.view.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/** Class used to construct the acstract button pane */
abstract public class AbstractButtonPane extends JPanel implements ActionListener
{
	// Stores the buttons displayed in this pane
	private JButton button1 = null;
	private JButton button2 = null;
	
	/** Constructs the abstract button pane */
	public AbstractButtonPane(String button1Label, String button2Label)
	{
		// Initialize buttons
		button1 = new JButton(button1Label);
		button2 = new JButton(button2Label);

		// Create okay button pane
		JPanel okayButtonPane = new JPanel();
		okayButtonPane.setBorder(new EmptyBorder(3,3,3,3));
		okayButtonPane.setLayout(new BorderLayout());
		okayButtonPane.add(button1);
		
		// Create cancel button pane
		JPanel cancelButtonPane = new JPanel();
		cancelButtonPane.setBorder(new EmptyBorder(3,3,3,3));
		cancelButtonPane.setLayout(new BorderLayout());
		cancelButtonPane.add(button2);
		
		// Construct pane containing buttons
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new GridLayout(1,2));
		buttonPane.add(okayButtonPane);
		buttonPane.add(cancelButtonPane);

		// Construct the buttons pane
		setLayout(new FlowLayout());
		add(buttonPane);
		
		// Activate buttons such that they will trigger actions
		button1.addActionListener(this);
		button2.addActionListener(this);
	}
	
	/** Reacts to the buttons being pressed */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(button1)) button1Action();
		else button2Action();
	}
	
	/** Abstract function for handling the pressing of the first button */
	abstract protected void button1Action();

	/** Abstract function for handling the pressing of the second button */
	abstract protected void button2Action();
}
