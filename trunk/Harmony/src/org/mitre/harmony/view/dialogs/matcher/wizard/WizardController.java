package org.mitre.harmony.view.dialogs.matcher.wizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Controls the buttons in the wizard */
public class WizardController implements ActionListener
{    
	/** Stores reference to the wizard */
    private Wizard wizard;
    
    /** Constructs the Wizard controller */
    public WizardController(Wizard wizard)
    	{ this.wizard = wizard; }

    /** Handles an action for any button pressed */
    public void actionPerformed(ActionEvent evt)
    {    
        if (evt.getActionCommand().equals(Wizard.CANCEL_BUTTON_ACTION_COMMAND))
            cancelButtonPressed();
        else if (evt.getActionCommand().equals(Wizard.BACK_BUTTON_ACTION_COMMAND))
            backButtonPressed();
        else if (evt.getActionCommand().equals(Wizard.NEXT_BUTTON_ACTION_COMMAND))
            nextButtonPressed();   
    }
    
    /** Handles the pressing of the cancel button */
    private void cancelButtonPressed()
    	{ wizard.close(Wizard.CANCEL_RETURN_CODE); }

    /** Handles the pressing of the next button */
    private void nextButtonPressed()
    {
        // Identify the next panel
        WizardPanel descriptor = wizard.getModel().getCurrentPanel();
        String nextPanelDescriptor = descriptor.getNextPanelDescriptor();

        // Goes to the next panel or shut down the wizard
        if (nextPanelDescriptor == null)
            wizard.close(Wizard.FINISH_RETURN_CODE);
        else wizard.setCurrentPanel(nextPanelDescriptor);
    }

    /** Handles the pressing of the back button */
    private void backButtonPressed()
    {
        WizardPanel panel = wizard.getModel().getCurrentPanel();
        String backPanelDescriptor = panel.getBackPanelDescriptor();        
        wizard.setCurrentPanel(backPanelDescriptor);        
    }

    /** Resets the buttons to the rules of the currently displayed panel */
    void resetButtonsToPanelRules()
    {
    	// Get the currently displayed panel
    	WizardModel model = wizard.getModel();
        WizardPanel panel = model.getCurrentPanel();
        
        // Set the cancel button
        model.setProperty(WizardModel.CANCEL_BUTTON_TEXT_PROPERTY,"Cancel");
        
        // Set the back button
        model.setProperty(WizardModel.BACK_BUTTON_TEXT_PROPERTY,"Back");
        String backPanelDescriptor = panel.getBackPanelDescriptor();
        model.setProperty(WizardModel.BACK_BUTTON_ENABLED_PROPERTY,backPanelDescriptor!=null);

        // Sets the next/finish button
        String nextPanelDescriptor = panel.getNextPanelDescriptor();
        model.setProperty(WizardModel.NEXT_FINISH_BUTTON_ENABLED_PROPERTY,nextPanelDescriptor!=null);
        model.setProperty(WizardModel.NEXT_FINISH_BUTTON_TEXT_PROPERTY,nextPanelDescriptor==null ? "Finish" : "Next");
    }
}
