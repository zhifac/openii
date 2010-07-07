package org.mitre.harmony.view.dialogs.matcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.view.dialogs.matcher.wizard.Wizard;
import org.mitre.harmony.view.dialogs.matcher.wizard.WizardPanel;

public class SelectMatchOptionsPanel extends WizardPanel implements ActionListener {
	/** Constructs the type pane */
    public SelectMatchOptionsPanel(Wizard wizard, String id) {
    	super.setWizard(wizard);
    	super.id = id;

    	// create a bunch of cards for each option panel we have based on the selected options
    	// check each voter that has been selected to see if it has options then create a card for it
    	// then insert hooks into the wizard to make it go to those new cards

    	// Initialize the layout of the options pane
		JPanel pane = getPanel();
		pane.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10),new CompoundBorder(new LineBorder(Color.lightGray),new EmptyBorder(5,5,5,5))));
		pane.setLayout(new BorderLayout());
		pane.add(new JEditorPane("text/plain", this.toString()), BorderLayout.CENTER);
    }

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void aboutToDisplayPanel() {
		// TODO Auto-generated method stub
		
	}

	public void aboutToHidePanel() {
		// TODO Auto-generated method stub
		
	}

	public void displayingPanel() {
		// TODO Auto-generated method stub
		
	}
}
