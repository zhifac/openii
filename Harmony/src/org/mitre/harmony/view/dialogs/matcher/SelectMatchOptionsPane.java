package org.mitre.harmony.view.dialogs.matcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.view.dialogs.matcher.wizard.WizardPanel;

public class SelectMatchOptionsPane extends WizardPanel implements ActionListener {
	/** Constructs the type pane */
    public SelectMatchOptionsPane(Integer panelId) {
    	this.panelId = panelId;

    	// Initialize the layout of the type pane
		JPanel pane = getPanel();
		pane.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10),new CompoundBorder(new LineBorder(Color.lightGray),new EmptyBorder(5,5,5,5))));
		pane.setLayout(new BorderLayout());
    }

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
