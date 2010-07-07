package org.mitre.harmony.view.dialogs.matcher.wizard;

import javax.swing.JPanel;

/** Abstract class for storing a panel for the wizard */
abstract public class WizardPanel {
	/** Stores the wizard associated with this panel */
	private Wizard wizard;

	/** Stores the panel */
    private JPanel panel = new JPanel();

    /** Stores any ID that was given by the user */
    protected String id = null;

    /** Returns the wizard panel */
    final public JPanel getPanel() {
    	return panel;
    }

    /** Sets the wizard associated with this pane */
    protected final void setWizard(Wizard wizard) {
    	this.wizard = wizard;
    }

    /** Returns the wizard associated with this pane */
    final public Wizard getWizard() {
    	return wizard;
    }

    // Allows panel to perform actions before, during, and after being displayed
    abstract public void aboutToDisplayPanel();
    abstract public void displayingPanel();
    abstract public void aboutToHidePanel();

    // should contain an identifier for the panel that is unique
    final public String toString() {
    	if (id == null) {
    		return this.getClass().getName();
    	} else {
    		StringBuffer string = new StringBuffer();
    		string.append(this.getClass().getName());
			string.append(" ");
			string.append(id);
    		return string.toString();
    	}
    }
}
