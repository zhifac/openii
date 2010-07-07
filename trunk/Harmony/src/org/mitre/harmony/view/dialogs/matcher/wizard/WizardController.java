package org.mitre.harmony.view.dialogs.matcher.wizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Controls the buttons in the wizard */
public class WizardController implements ActionListener {    
	/** Stores reference to the wizard */
    private Wizard wizard;

    /** Constructs the Wizard controller */
    public WizardController(Wizard wizard) {
    	this.wizard = wizard;
    }

    /** Handles an action for any button pressed */
    public void actionPerformed(ActionEvent evt) {    
        if (evt.getActionCommand().equals(Wizard.CANCEL_BUTTON_ACTION_COMMAND)) {
            cancelButtonPressed();
        } else if (evt.getActionCommand().equals(Wizard.BACK_BUTTON_ACTION_COMMAND)) {
            backButtonPressed();
        } else if (evt.getActionCommand().equals(Wizard.NEXT_BUTTON_ACTION_COMMAND)) {
            nextButtonPressed();   
        }
    }

    /** Handles the pressing of the cancel button */
    private void cancelButtonPressed() {
    	wizard.close(Wizard.CANCEL_RETURN_CODE);
    }

    /** Handles the pressing of the next button */
    private void nextButtonPressed() {
    	wizard.setCurrentPanel(wizard.getNextPanel().toString());
    }

    /** Handles the pressing of the back button */
    private void backButtonPressed() {
    	wizard.setCurrentPanel(wizard.getBackPanel().toString());
    }
}