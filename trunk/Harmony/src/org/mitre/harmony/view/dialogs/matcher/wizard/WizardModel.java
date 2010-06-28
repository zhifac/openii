package org.mitre.harmony.view.dialogs.matcher.wizard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;

/** Class for managing the wizard model */
public class WizardModel {
	// Defines the various properties that can be triggered
    public static final String CURRENT_PANEL_DESCRIPTOR_PROPERTY = "currentPanelDescriptorProperty";
    public static final String BACK_BUTTON_TEXT_PROPERTY = "backButtonTextProperty";
    public static final String BACK_BUTTON_ENABLED_PROPERTY = "backButtonEnabledProperty";
    public static final String NEXT_BUTTON_TEXT_PROPERTY = "nextButtonTextProperty";
    public static final String NEXT_BUTTON_ENABLED_PROPERTY = "nextButtonEnabledProperty";
    public static final String CANCEL_BUTTON_TEXT_PROPERTY = "cancelButtonTextProperty";
    public static final String CANCEL_BUTTON_ENABLED_PROPERTY = "cancelButtonEnabledProperty";

    /** Stores the currently displayed wizard panel */
    private WizardPanel currentPanel;

    /** Hashmap for referencing the wizard panels */
    private ArrayList<WizardPanel> panels;

    /** Hashmap for referencing the properties */
    private HashMap<String,Object> properties;

    /** Manages property listeners */
    private PropertyChangeSupport propertyChangeSupport;

    /** Constructs the wizard model */
    public WizardModel() {    
        panels = new ArrayList<WizardPanel>();
        properties = new HashMap<String,Object>();
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /** Returns the currently displayed panel */
    public WizardPanel getCurrentPanel() {
    	return currentPanel;
    }

    /** Registers the specified panel */
    public void registerPanel(WizardPanel descriptor, Integer id) {
    	panels.add(id, descriptor);
    }

    /** Returns the back panel in our ordered hashmap */
    public WizardPanel getBackPanel(Integer id) {
    	try {
    		return panels.get(id - 1);
    	} catch (IndexOutOfBoundsException e) {
    		return null;
    	}
    }

    /** Returns the next panel in our ordered hashmap */
    public WizardPanel getNextPanel(Integer id) {
    	try {
    		return panels.get(id + 1);
    	} catch (IndexOutOfBoundsException e) {
    		return null;
    	}
    }

    /** Gets the specified panel */
    public WizardPanel getPanel(Integer id) {
    	return panels.get(id);
    }

    /** Sets the current panel */
    public void setCurrentPanel(Integer id) {
    	// Retrieve the panel to be displayed
    	WizardPanel nextPanel = panels.get(id);

        // Change panel and inform property listeners
        WizardPanel oldPanel = currentPanel;
        currentPanel = nextPanel;
        if (oldPanel != currentPanel) {
        	firePropertyChange(CURRENT_PANEL_DESCRIPTOR_PROPERTY, oldPanel, currentPanel);
        }
    }

    /** Retrieves the specified property */
    public Object getProperty(String property) {
    	return properties.get(property);
    }

    /** Sets the specified property to the specified value */
    public void setProperty(String property, Object newValue) {    
        Object oldValue = getProperty(property);
        if (!newValue.equals(oldValue)) {
            properties.put(property, newValue);
            firePropertyChange(property, oldValue, newValue);
        }
    }

    /** Handles changes to the properties */
    public void addPropertyChangeListener(PropertyChangeListener p) {
    	propertyChangeSupport.addPropertyChangeListener(p);
    }

    public void removePropertyChangeListener(PropertyChangeListener p) {
    	propertyChangeSupport.removePropertyChangeListener(p);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    	propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }   
}