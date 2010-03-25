package org.mitre.harmony.view.dialogs.matcher.wizard;

import javax.swing.JPanel;

/** Abstract class for storing a panel for the wizard */
abstract public class WizardPanel
{    
	/** Stores the wizard associated with this panel */
	private Wizard wizard;
	
	/** Stores the panel */
    private JPanel panel = new JPanel();
    
    /** Returns the wizard panel */
    public final JPanel getPanel()
    	{ return panel; }
    
    /** Sets the wizard associated with this pane */
    final void setWizard(Wizard wizard)
    	{ this.wizard = wizard; }
    
    /** Returns the wizard associated with this pane */
    public final Wizard getWizard()
    	{ return wizard; }   
    
    // Defines the previous and next panels to be displayed
    abstract public String getBackPanelDescriptor();
    abstract public String getNextPanelDescriptor();
    
    // Allows panel to perform actions before, during, and after being displayed
    public void aboutToDisplayPanel() {}
    public void displayingPanel() {}
    public void aboutToHidePanel() {}
}
