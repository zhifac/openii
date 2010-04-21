package org.mitre.harmony.view.dialogs.matcher.wizard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

/** Class for managing the wizard model */
public class WizardModel
{
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
    private HashMap<Object,WizardPanel> panelHashmap;

    /** Hashmap for referencing the properties */
    private HashMap<String,Object> propertyHashmap;
    
    /** Manages property listeners */
    private PropertyChangeSupport propertyChangeSupport;
    
    /** Constructs the wizard model */
    public WizardModel()
    {    
        panelHashmap = new HashMap<Object,WizardPanel>();
        propertyHashmap = new HashMap<String,Object>();
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
    
    /** Returns the currently displayed panel */
    WizardPanel getCurrentPanel()
    	{ return currentPanel; }
    
    /** Registers the specified panel */
    void registerPanel(Object id, WizardPanel descriptor)
     	{ panelHashmap.put(id, descriptor); }

    /** Gets the specified panel */
    WizardPanel getPanel(Object id)
    	{ return (WizardPanel)panelHashmap.get(id); }
    
    /** Sets the current panel */
    void setCurrentPanel(Object id)
    {
    	// Retrieve the panel to be displayed
    	WizardPanel nextPanel = (WizardPanel)panelHashmap.get(id);

        // Change panel and inform property listeners
        WizardPanel oldPanel = currentPanel;
        currentPanel = nextPanel;
        if(oldPanel != currentPanel) firePropertyChange(CURRENT_PANEL_DESCRIPTOR_PROPERTY, oldPanel, currentPanel);
    }
    
    /** Retrieves the specified property */
    Object getProperty(String property)
    	{ return propertyHashmap.get(property); }

    /** Sets the specified property to the specified value */
    void setProperty(String property, Object newValue)
    {    
        Object oldValue = getProperty(property);        
        if(!newValue.equals(oldValue))
        {
            propertyHashmap.put(property, newValue);
            firePropertyChange(property, oldValue, newValue);
        }
    }
    
    /** Handles changes to the properties */
    public void addPropertyChangeListener(PropertyChangeListener p)
    	{ propertyChangeSupport.addPropertyChangeListener(p); }
    public void removePropertyChangeListener(PropertyChangeListener p)
    	{ propertyChangeSupport.removePropertyChangeListener(p); }
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue)
    	{ propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue); }   
}