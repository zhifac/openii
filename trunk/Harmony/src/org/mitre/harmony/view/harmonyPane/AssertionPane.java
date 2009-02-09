// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.harmonyPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JCheckBox;

import org.mitre.harmony.model.filters.Filters;

/**
 * Pane to display currently set assertions
 * @author CWOLF
 */
public class AssertionPane extends JPanel implements ActionListener
{
	/**
	 * Initializes the assertion pane
	 */
	public AssertionPane()
	{
		// Create check boxes for each type of assertion
		JCheckBox user = new JCheckBox("User",Filters.getAssertions()[Filters.USER]);
		JCheckBox system = new JCheckBox("System",Filters.getAssertions()[Filters.SYSTEM]);
		JCheckBox best = new JCheckBox("Best",Filters.getAssertions()[Filters.BEST]);
	
		// Add listeners to monitor the assertion check boxes
		user.addActionListener(this);
		system.addActionListener(this);
		best.addActionListener(this);
	
		// Place assertion check boxes in assertion pane
		setLayout(new GridLayout(3,1));
		add(user); add(system); add(best);
	}
	
	/**
	 * Monitors for assertion check boxes to be checked/unchecked
	 */
	public void actionPerformed(ActionEvent e)
	{
		// Determine which assertions have been checked/unchecked
		boolean assertions[] = new boolean[getComponentCount()];
		for(int i=0; i<getComponentCount(); i++)
			assertions[i]=((JCheckBox)getComponent(i)).isSelected();

		// Store new assertion settings
		Filters.setAssertions(assertions);
	}
}