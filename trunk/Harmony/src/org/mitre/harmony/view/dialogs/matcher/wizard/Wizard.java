package org.mitre.harmony.view.dialogs.matcher.wizard;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.matchers.MatchTypeMappings;
import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.MatcherOption;
import org.mitre.harmony.matchers.mergers.MatchMerger;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.matcher.SelectMatchersPanel;
import org.mitre.harmony.view.dialogs.matcher.SelectMatchTypePanel;
import org.mitre.harmony.view.dialogs.matcher.SelectMatchOptionsPanel;
import org.mitre.harmony.view.dialogs.matcher.MatchingStatusPanel;

/** Class defining the wizard */
public class Wizard extends WindowAdapter implements PropertyChangeListener {
	// Define the wizard return codes
	public static final int FINISH_RETURN_CODE = 0;
    public static final int CANCEL_RETURN_CODE = 1;
    public static final int ERROR_RETURN_CODE = 2;

    // Define the order of the wizard panels
    public static final int SELECT_MATCHERS_PANEL = 0;
    public static final int SELECT_MATCH_TYPE_PANEL = 1;
    public static final int SELECT_MATCH_OPTIONS_PANEL = 2;
    public static final int MATCHING_STATUS_PANEL = 3;

    // Define the wizard button command strings
    public static final String NEXT_BUTTON_ACTION_COMMAND = "NextButtonActionCommand";
    public static final String BACK_BUTTON_ACTION_COMMAND = "BackButtonActionCommand";
    public static final String CANCEL_BUTTON_ACTION_COMMAND = "CancelButtonActionCommand";

    // Stores the main components of the wizard
    private WizardModel wizardModel;
    private WizardController wizardController;
    private JDialog wizardDialog;

    /** Store reference to the panel for showing the wizard panel */
    private JPanel cardPanel;

    // references the harmony model
    private HarmonyModel harmonyModel;

    // References to the various wizard buttons
    private JButton backButton = new JButton();
    private JButton nextButton = new JButton();
    private JButton cancelButton = new JButton();

    // references controlling the panels
    private WizardPanel currentPanel = null;
    private HashMap<String, WizardPanel> panels = new HashMap<String, WizardPanel>();

    // stores the list of matchers that the user has selected
    private ArrayList<MatchVoter> selectedMatchers = null;

    // stores the list of types that the user has selected
    private MatchTypeMappings selectedMatchTypeMappings = null;

    // stores the list of matcher options that the user has selected
    private HashMap<String, ArrayList<MatcherOption>> selectedMatcherOptions = new HashMap<String, ArrayList<MatcherOption>>();

    // stores the "advanced" mode flag
    private boolean advancedMode = true;

    /** Constructs the wizard */
    public Wizard(HarmonyModel harmonyModel) {
    	this.harmonyModel = harmonyModel;

    	// Initialize the wizard model and controller
        wizardController = new WizardController(this);
    	wizardModel = new WizardModel();
        wizardModel.addPropertyChangeListener(this);

        // Generate the pane for displaying the wizard panel
        cardPanel = new JPanel();
        cardPanel.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));       
        cardPanel.setLayout(new CardLayout());

        // Generate the button box
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridLayout(1,3));
        buttonPane.add(generateButton(backButton, BACK_BUTTON_ACTION_COMMAND));
        buttonPane.add(generateButton(nextButton, NEXT_BUTTON_ACTION_COMMAND));
        buttonPane.add(generateButton(cancelButton, CANCEL_BUTTON_ACTION_COMMAND));

        // Generate the button pane
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(new JSeparator(), BorderLayout.NORTH);
        buttonPanel.add(buttonPane, java.awt.BorderLayout.SOUTH);

        // Generate the wizard dialog
        wizardDialog = new JDialog(harmonyModel.getBaseFrame());
        wizardDialog.getContentPane().setLayout(new BorderLayout());
        wizardDialog.getContentPane().add(cardPanel, java.awt.BorderLayout.CENTER);
        wizardDialog.getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);
        wizardDialog.addWindowListener(this);
    }

    /** Generates a button with the specified action command */
    private JPanel generateButton(JButton button, String command) {
    	// Generate the button
     	button.setActionCommand(command);
    	button.addActionListener(wizardController);
    	button.setFocusable(false);

    	// Generate the button pane
    	JPanel pane = new JPanel();
    	pane.setBorder(new EmptyBorder(5,5,5,5));
    	pane.setLayout(new BorderLayout());
    	pane.add(button,BorderLayout.CENTER);
    	return pane; 
    }

    /** Resets the buttons to the rules of the currently displayed panel */
    private void resetButtonsToPanelRules() {
        // set the cancel button
        wizardModel.setProperty(WizardModel.CANCEL_BUTTON_TEXT_PROPERTY, "Cancel");

        // set the back button
        // the back button is enabled as long as there is a panel to go back to
        WizardPanel backPanel = getBackPanel();
        wizardModel.setProperty(WizardModel.BACK_BUTTON_TEXT_PROPERTY, "Back");
        wizardModel.setProperty(WizardModel.BACK_BUTTON_ENABLED_PROPERTY, (backPanel != null));

        // sets the next/finish button
        // if we are on the last panel (the matching status panel) disable the "next" and "back" button
        // if we are on the second to last panel, turn the "next" button into a "finish" button
        WizardPanel nextPanel = getNextPanel();
        boolean currentIsMatchPane = getCurrentPanel() instanceof MatchingStatusPanel;
        boolean nextIsMatchPane = (nextPanel != null && nextPanel instanceof MatchingStatusPanel);
        wizardModel.setProperty(WizardModel.NEXT_BUTTON_ENABLED_PROPERTY, !currentIsMatchPane);
        wizardModel.setProperty(WizardModel.NEXT_BUTTON_TEXT_PROPERTY, (currentIsMatchPane || nextIsMatchPane ? "Run" : "Next"));
    }

    /** Handles a change to a wizard property */
    public void propertyChange(PropertyChangeEvent evt) {
    	// change the buttons on the panel to match the rules of the current panel
        if (evt.getPropertyName().equals(WizardModel.CURRENT_PANEL_DESCRIPTOR_PROPERTY))
        	{ resetButtonsToPanelRules(); }

        // set the text on the panel buttons to match the rules of the current panel
        if (evt.getPropertyName().equals(WizardModel.NEXT_BUTTON_TEXT_PROPERTY))
        	{ nextButton.setText(evt.getNewValue().toString()); }
        if (evt.getPropertyName().equals(WizardModel.BACK_BUTTON_TEXT_PROPERTY))
        	{ backButton.setText(evt.getNewValue().toString()); }
        if (evt.getPropertyName().equals(WizardModel.CANCEL_BUTTON_TEXT_PROPERTY))
        	{ cancelButton.setText(evt.getNewValue().toString()); }

        // set the enabled state of the panel buttons to match the rules of the current panel
        if (evt.getPropertyName().equals(WizardModel.NEXT_BUTTON_ENABLED_PROPERTY))
        	{ nextButton.setEnabled(((Boolean)evt.getNewValue()).booleanValue()); }
        if (evt.getPropertyName().equals(WizardModel.BACK_BUTTON_ENABLED_PROPERTY))
        	{ backButton.setEnabled(((Boolean)evt.getNewValue()).booleanValue()); }
        if (evt.getPropertyName().equals(WizardModel.CANCEL_BUTTON_ENABLED_PROPERTY))
        	{ cancelButton.setEnabled(((Boolean)evt.getNewValue()).booleanValue()); }
    }

    /** Returns the wizard dialog */
    public JDialog getDialog() {
    	return wizardDialog;
    }

    /** Returns the wizard model */
    public WizardModel getModel() {
    	return wizardModel;
    }

    /** Sets the back button as enabled/disabled */
    public void setBackButtonEnabled(boolean newValue) {
    	wizardModel.setProperty(WizardModel.BACK_BUTTON_ENABLED_PROPERTY, newValue);
    }

    /** Sets the next button as enabled/disabled */
    public void setNextButtonEnabled(boolean newValue) {
    	wizardModel.setProperty(WizardModel.NEXT_BUTTON_ENABLED_PROPERTY, newValue);
    }

    /** Handles the closing of the wizard */
    public void close(int code) {
        getCurrentPanel().aboutToHidePanel();
        wizardDialog.dispose();
    }

    /** Shows the wizard dialog */
    public void showDialog(ArrayList<MatchVoter> matchers, MatchMerger merger, boolean custom) {
    	// create our list of panels that could possibly exist
		addPanel(new SelectMatchersPanel(this, matchers));
		addPanel(new SelectMatchTypePanel(this, harmonyModel));
    	addPanel(new MatchingStatusPanel(this, harmonyModel, merger));

    	// this is all panels that contain options
    	for (MatchVoter matcher : MatcherManager.getVisibleMatchers()) {
    		if (matcher.isConfigurable()) {
    			addPanel(new SelectMatchOptionsPanel(this, matcher.getClass().getName()));
    		}
    	}

    	if (custom) {
    		// load the matcher choosing panel
    		setCurrentPanel(SelectMatchersPanel.class.getName());
    	} else {
    		// just run the matching status panel
    		setCurrentPanel(MatchingStatusPanel.class.getName());
    	}

        wizardDialog.setModal(true);
        wizardDialog.pack();
        wizardDialog.setLocationRelativeTo(getDialog().getParent());
        wizardDialog.setVisible(true);
    }

    /** BELOW ARE METHODS TO DETERMINE WHICH PANEL TO SHOW */
    private String getMatchOptionsPanelId(MatchVoter matcher) {
    	StringBuffer string = new StringBuffer();
    	string.append(SelectMatchOptionsPanel.class.getName());
    	string.append(" ");
    	string.append(matcher.getClass().getName());
    	return string.toString();
    }

    public void addPanel(WizardPanel panel) {
        // if we don't have this panel in our list of panels already, put it there
        if (!panels.containsKey(panel.toString())) {
        	cardPanel.add(panel.getPanel(), panel.toString());
        	panels.put(panel.toString(), panel);
        }
    }

    public WizardPanel getCurrentPanel() {
    	return currentPanel;
    }

    public void setCurrentPanel(String id) {
    	WizardPanel panel = panels.get(id);

    	// Informs old panel that it is about to be hidden
        WizardPanel oldPanel = currentPanel;
        if (oldPanel != null) { oldPanel.aboutToHidePanel(); }

        // Informs the new panel that it is about to be displayed
        currentPanel = panel;
        if (oldPanel != currentPanel) { wizardModel.firePropertyChange(WizardModel.CURRENT_PANEL_DESCRIPTOR_PROPERTY, oldPanel, currentPanel); }
        currentPanel.aboutToDisplayPanel();

        // Informs the new panel that it is being displayed
        ((CardLayout)cardPanel.getLayout()).show(cardPanel, currentPanel.toString());
        currentPanel.displayingPanel();
    }

    public WizardPanel getNextPanel() {
    	if (currentPanel == null) { return null; }

    	// Panel 1: SelectMatchersPanel
    	if (currentPanel instanceof SelectMatchersPanel) {
    		// if we are in advanced mode, go to the match type panel, otherwise go to the last panel
    		if (getAdvancedMode())  { return panels.get(SelectMatchTypePanel.class.getName()); }
    		if (!getAdvancedMode()) { return panels.get(MatchingStatusPanel.class.getName()); }
    	}

    	// Panel 2: SelectMatchTypePanel
    	if (currentPanel instanceof SelectMatchTypePanel) {
    		// find the first matcher selected that has options and return that
    		for (MatchVoter matcher : getSelectedMatchers()) {
    			// only look at matchers that have options
    			if (matcher.isConfigurable()) {
    				return panels.get(getMatchOptionsPanelId(matcher));
    			}
        	}

    		// if we are here, it means that no selected matchers had options, so we go to the status panel
    		return panels.get(MatchingStatusPanel.class.getName());
    	}

    	// Panel 3: SelectMatchOptionsPanel
    	if (currentPanel instanceof SelectMatchOptionsPanel) {
    		// figure out which panel we are currently looking at, then choose the next one
    		String lastPanelId = null;
    		for (MatchVoter matcher : getSelectedMatchers()) {
    			// only look at matchers that have options
    			if (matcher.isConfigurable()) {
					// see if the last matcher option panel is the panel currently being shown
					// if it is, then we want to show this matcher's options panel
    				if (lastPanelId != null && lastPanelId.equals(currentPanel.toString())) {
    					return panels.get(getMatchOptionsPanelId(matcher));
    				}
    				lastPanelId = getMatchOptionsPanelId(matcher);
    			}
        	}

    		// if we are here, it means that no selected matchers had options, so we go to the status panel
    		return panels.get(MatchingStatusPanel.class.getName());
    	}

    	// Panel 4: MatchingStatusPanel
    	if (currentPanel instanceof MatchingStatusPanel) {
    		// the back/next buttons are disabled on the matching status panel
    		return null;
    	}

    	return null;
    }
    
    public WizardPanel getBackPanel() {
    	if (currentPanel == null) { return null; }

    	// Panel 1: SelectMatchersPanel
    	if (currentPanel instanceof SelectMatchersPanel) {
    		// there are no more panels before this one
    		return null;
    	}

    	// Panel 2: SelectMatchTypePanel
    	if (currentPanel instanceof SelectMatchTypePanel) {
    		// we would only be at the select match type panel if we were in "advanced" mode
    		return panels.get(SelectMatchersPanel.class.getName());
    	}

    	// Panel 3: SelectMatchOptionsPanel
    	if (currentPanel instanceof SelectMatchOptionsPanel) {
    		// figure out which panel we are currently looking at, then choose the previous one
    		String lastPanelId = null;
    		for (MatchVoter matcher : getSelectedMatchers()) {
    			// only look at matchers that have options
    			if (matcher.isConfigurable()) {
					// see if this matcher option panel is the panel currently being shown
					// if it is, then we want to show the last matcher's options panel
    				if (lastPanelId != null && currentPanel.toString().equals(getMatchOptionsPanelId(matcher))) {
    					return panels.get(lastPanelId);
    				}
    				lastPanelId = getMatchOptionsPanelId(matcher);
    			}
        	}

    		// if we are here, it means that no selected matchers had options, so we go to the match type panel
    		return panels.get(SelectMatchTypePanel.class.getName());
    	}

    	// Panel 4: MatchingStatusPanel
    	if (currentPanel instanceof MatchingStatusPanel) {
    		// the back/next buttons are disabled on the matching status panel
    		return null;
    	}

    	return null;
    }

    /** BELOW ARE METHODS TO GET/SET DETAILS ABOUT THE MATCHER OPTIONS */

    /** Returns the list of matchers that were selected by the user.
        If the user did not select any matchers, return the default list. */
    public ArrayList<MatchVoter> getSelectedMatchers() {
    	if (selectedMatchers == null) {
    		selectedMatchers = MatcherManager.getDefaultMatchers();
    	}
    	return selectedMatchers;
    }
    
    public void setSelectedMatchers(ArrayList<MatchVoter> selectedMatchers) {
    	this.selectedMatchers = selectedMatchers; 
    }

    /** Return what matcher type mappings have been chosen.
     *  If no types have been selected, the default is just "null" */
    public MatchTypeMappings getSelectedMatchTypeMappings() {
    	return selectedMatchTypeMappings;
    }

    public void setSelectedMatchTypeMappings(MatchTypeMappings selectedMatchTypeMappings) {
    	this.selectedMatchTypeMappings = selectedMatchTypeMappings;
    }

    /** Return any options selected for the specified matcher class.
        If no options have been selected, it returns the default option set for that matcher. */
    public HashMap<String, ArrayList<MatcherOption>> getSelectedMatcherOptions() {
    	return selectedMatcherOptions;
    }

    public ArrayList<MatcherOption> getSelectedMatcherOptions(String id) {
    	if (!selectedMatcherOptions.containsKey(id)) {
    		selectedMatcherOptions.put(id, MatcherManager.getMatcherOptions(id));
    	}
    	return selectedMatcherOptions.get(id);
    }

    public void setSelectedMatcherOptions(String id, ArrayList<MatcherOption> selectedMatcherOptions) {
    	this.selectedMatcherOptions.put(id, selectedMatcherOptions);
    }

    /** Return whether we are running in "advanced" mode */
    public void setAdvancedMode(boolean advancedMode) {
    	this.advancedMode = advancedMode;
    }
    
    public boolean getAdvancedMode() {
    	return advancedMode;
    }
}