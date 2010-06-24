package org.mitre.harmony.view.dialogs.matcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.matcher.wizard.WizardPanel;

public class SelectMatchOptionsPane extends WizardPanel implements ActionListener {
	// Defines the identifier for the match voter pane
	static public final String IDENTIFIER = "SELECT_MATCH_OPTIONS_PANE";

	/** Constructs the type pane */
    public SelectMatchOptionsPane(HarmonyModel harmonyModel) {
		// Initialize the layout of the type pane
		JPanel pane = getPanel();
		pane.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10),new CompoundBorder(new LineBorder(Color.lightGray),new EmptyBorder(5,5,5,5))));
		pane.setLayout(new BorderLayout());
    }

    /** Describes the next pane to display */
	public String getBackPanelDescriptor() {
		return SelectMatchTypePane.IDENTIFIER;
	}

    /** Describes the previous pane that was displayed */
	public String getNextPanelDescriptor() {
		return MatchingStatusPane.IDENTIFIER;
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
