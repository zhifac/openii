package org.mitre.harmony.view.dialogs.matcher.wizard;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

/** Class defining the wizard */
public class Wizard extends WindowAdapter implements PropertyChangeListener
{
	// Define the wizard return codes
	public static final int FINISH_RETURN_CODE = 0;
    public static final int CANCEL_RETURN_CODE = 1;
    public static final int ERROR_RETURN_CODE = 2;
        
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
    
    // References to the various wizard buttons
    private JButton backButton;
    private JButton nextButton;
    private JButton cancelButton;
    
    /** Stores the wizard return code */
    private int returnCode;

    /** Generates a button with the specified action command */
    private JButton generateButton(String command)
    {
        JButton button = new JButton();
    	button.setActionCommand(command);
    	button.addActionListener(wizardController);
    	return button;
    }
    
    /** Constructs the wizard */
    public Wizard(Frame owner)
    {
    	// Initialize the wizard model and controller
        wizardController = new WizardController(this);       
    	wizardModel = new WizardModel();
        wizardModel.addPropertyChangeListener(this);       
        
        // Generate the pane for displaying the wizard panel
        cardPanel = new JPanel();
        cardPanel.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));       
        cardPanel.setLayout(new CardLayout());
        
        // Generate the button box
        Box buttonBox = new Box(BoxLayout.X_AXIS);
        buttonBox.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));       
        buttonBox.add(backButton = generateButton(BACK_BUTTON_ACTION_COMMAND));
        buttonBox.add(Box.createHorizontalStrut(10));
        buttonBox.add(nextButton = generateButton(NEXT_BUTTON_ACTION_COMMAND));
        buttonBox.add(Box.createHorizontalStrut(30));
        buttonBox.add(cancelButton = generateButton(CANCEL_BUTTON_ACTION_COMMAND));
        
        // Generate the button pane
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(new JSeparator(), BorderLayout.NORTH);
        buttonPanel.add(buttonBox, java.awt.BorderLayout.EAST);

        // Generate the wizard dialog
        wizardDialog = new JDialog(owner);         
        wizardDialog.getContentPane().setLayout(new BorderLayout());
        wizardDialog.getContentPane().add(cardPanel, java.awt.BorderLayout.CENTER);
        wizardDialog.getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);
        wizardDialog.addWindowListener(this);
    }

    /** Shows the wizard dialog */
    public int showDialog()
    {    
        wizardDialog.setModal(true);
        wizardDialog.pack();
        wizardDialog.setLocationRelativeTo(getDialog().getParent());
        wizardDialog.setVisible(true);
        return returnCode;
    }

    /** Returns the wizard dialog */
    public JDialog getDialog()
    	{ return wizardDialog; }
    
    /** Returns the wizard model */
    public WizardModel getModel()
    	{ return wizardModel; }
    
    /** Registers the specified panel */
    public void registerWizardPanel(String id, WizardPanel panel)
    {
        cardPanel.add(panel.getPanel(), id);
        panel.setWizard(this);
        wizardModel.registerPanel(id, panel);        
    }  
    
    /** Retrieves the specified panel */
    public WizardPanel getPanel(Object id)
    	{ return wizardModel.getPanel(id); }
    
    /** Sets the currently displayed panel */
    public void setCurrentPanel(String id)
    {
    	// Don't proceed if a bad id was given
    	if (id == null)
            close(ERROR_RETURN_CODE);
        
    	// Informs old panel that it is about to be hidden
        WizardPanel oldPanel = wizardModel.getCurrentPanel();
        if (oldPanel != null) oldPanel.aboutToHidePanel();
        
        // Informs the new panel that it is about to be displayed
        wizardModel.setCurrentPanel(id);
        wizardModel.getCurrentPanel().aboutToDisplayPanel();
        
        // Informs the new panel that it is being displayed
        ((CardLayout)cardPanel.getLayout()).show(cardPanel, id.toString());
        wizardModel.getCurrentPanel().displayingPanel();                
    }

    /** Handles a change to a wizard property */
    public void propertyChange(PropertyChangeEvent evt)
    {    
        if (evt.getPropertyName().equals(WizardModel.CURRENT_PANEL_DESCRIPTOR_PROPERTY))
            wizardController.resetButtonsToPanelRules(); 
        else if (evt.getPropertyName().equals(WizardModel.NEXT_FINISH_BUTTON_TEXT_PROPERTY))
            nextButton.setText(evt.getNewValue().toString());
        else if (evt.getPropertyName().equals(WizardModel.BACK_BUTTON_TEXT_PROPERTY))            
            backButton.setText(evt.getNewValue().toString());
        else if (evt.getPropertyName().equals(WizardModel.CANCEL_BUTTON_TEXT_PROPERTY))      
            cancelButton.setText(evt.getNewValue().toString());
        else if (evt.getPropertyName().equals(WizardModel.NEXT_FINISH_BUTTON_ENABLED_PROPERTY))
            nextButton.setEnabled(((Boolean)evt.getNewValue()).booleanValue());
        else if (evt.getPropertyName().equals(WizardModel.BACK_BUTTON_ENABLED_PROPERTY))
            backButton.setEnabled(((Boolean)evt.getNewValue()).booleanValue());
        else if (evt.getPropertyName().equals(WizardModel.CANCEL_BUTTON_ENABLED_PROPERTY))            
            cancelButton.setEnabled(((Boolean)evt.getNewValue()).booleanValue());
    }
    
    /** Returns the return code associated with the wizard */
    public int getReturnCode()
    	{ return returnCode; }

    /** Sets the back button as enabled/disabled */
    public void setBackButtonEnabled(boolean newValue)
    	{ wizardModel.setProperty(WizardModel.BACK_BUTTON_ENABLED_PROPERTY,newValue); }

    /** Sets the next button as enabled/disabled */
    public void setNextFinishButtonEnabled(boolean newValue)
    	{ wizardModel.setProperty(WizardModel.NEXT_FINISH_BUTTON_ENABLED_PROPERTY,newValue); }
    
    /* Sets the cancel button as enabled/disabled */
    public void setCancelButtonEnabled(boolean newValue)
    	{ wizardModel.setProperty(WizardModel.CANCEL_BUTTON_ENABLED_PROPERTY,newValue); }
    
    /** Handles the closing of the wizard */
    void close(int code)
    {
        wizardModel.getCurrentPanel().aboutToHidePanel();
        returnCode = code;
        wizardDialog.dispose();
    }
}