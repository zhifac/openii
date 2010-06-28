package org.mitre.harmony.view.dialogs.matcher.wizard;

import javax.swing.JPanel;

/** Abstract class for storing a panel for the wizard */
abstract public class WizardPanel {
	/** Stores the wizard associated with this panel */
	private Wizard wizard;

	/** Stores the panel */
    private JPanel panel = new JPanel();

    /** Returns the wizard panel */
    final public JPanel getPanel() {
    	return panel;
    }

    /** Sets the wizard associated with this pane */
    final void setWizard(Wizard wizard) {
    	this.wizard = wizard;
    }

    /** Returns the wizard associated with this pane */
    final public Wizard getWizard() {
    	return wizard;
    }

    /** Returns the positions around this pane */
	protected Integer panelId = null;

	public Integer getPanelId() {
		return panelId;
	}

	public Integer getBackPanelId() {
		try {
			return getWizard().getModel().getBackPanel(panelId).getPanelId();
		} catch (NullPointerException e) {
			return null;
		}
	}

	public Integer getNextPanelId() {
		try {
			return getWizard().getModel().getNextPanel(panelId).getPanelId();
		} catch (NullPointerException e) {
			return null;
		}
	}

    // Allows panel to perform actions before, during, and after being displayed
    public void aboutToDisplayPanel() {}
    public void displayingPanel() {}
    public void aboutToHidePanel() {}
}
