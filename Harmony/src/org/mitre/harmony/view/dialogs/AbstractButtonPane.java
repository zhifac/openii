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
	/** Constructs the abstract button pane */
	public AbstractButtonPane(String[] labels, int rows, int cols)
	{
		// Construct pane containing buttons
		JPanel buttonsPane = new JPanel();
		buttonsPane.setLayout(new GridLayout(rows,cols));
		
		// Initialize the various buttons
		for(String label : labels)
		{
			// Generate the button
			JButton button = new JButton(label);
			button.addActionListener(this);
			
			// Place the button in a pane
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new EmptyBorder(3,3,3,3));
			buttonPane.setLayout(new BorderLayout());
			buttonPane.add(button);
			
			// Place the button pane in the buttons pane
			buttonsPane.add(buttonPane);
		}

		// Construct the button pane
		setLayout(new FlowLayout());
		add(buttonsPane);
	}
	
	/** Reacts to the buttons being pressed */
	public void actionPerformed(ActionEvent e)
		{ buttonPressed(((JButton)e.getSource()).getText()); }
	
	/** Abstract function for handling the pressing of the first button */
	abstract protected void buttonPressed(String label);
}
