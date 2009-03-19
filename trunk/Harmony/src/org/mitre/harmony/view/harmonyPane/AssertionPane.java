// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.harmonyPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JCheckBox;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.filters.FilterManager;

/**
 * Pane to display currently set assertions
 * @author CWOLF
 */
public class AssertionPane extends JPanel implements ActionListener
{
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Initializes the assertion pane */
	public AssertionPane(HarmonyModel harmonyModel)
	{
		this.harmonyModel = harmonyModel;
		
		// Create check boxes for each type of assertion
		JCheckBox user = new JCheckBox("User",harmonyModel.getFilters().getAssertions()[FilterManager.USER]);
		JCheckBox system = new JCheckBox("System",harmonyModel.getFilters().getAssertions()[FilterManager.SYSTEM]);
		JCheckBox best = new JCheckBox("Best",harmonyModel.getFilters().getAssertions()[FilterManager.BEST]);
	
		// Add listeners to monitor the assertion check boxes
		user.addActionListener(this);
		system.addActionListener(this);
		best.addActionListener(this);
	
		// Place assertion check boxes in assertion pane
		setLayout(new GridLayout(3,1));
		add(user); add(system); add(best);
	}
	
	/** Monitors for assertion check boxes to be checked/unchecked */
	public void actionPerformed(ActionEvent e)
	{
		// Determine which assertions have been checked/unchecked
		boolean assertions[] = new boolean[getComponentCount()];
		for(int i=0; i<getComponentCount(); i++)
			assertions[i]=((JCheckBox)getComponent(i)).isSelected();

		// Store new assertion settings
		harmonyModel.getFilters().setAssertions(assertions);
	}
}