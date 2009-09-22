package org.mitre.harmony.view.dialogs.matcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.matchers.TypeMappings;
import org.mitre.harmony.matchers.mergers.MatchMerger;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.matcher.wizard.WizardPanel;

/** Constructs the match pane for the matcher wizard */
public class MatchPane extends WizardPanel implements MatchListener
{    
	// Defines the identifier for the match voter pane
	static public final String IDENTIFIER = "MATCH_PANEL";
	
    /** Stores the Harmony model for reference */
    private HarmonyModel harmonyModel;
    
    /** Stores the match merger to be used */
    private MatchMerger merger;
    
    /** Stores the currently running matching process */
    private MatchThread matchThread = null;
    
    // Stores references to components in the Match Pane
	private JProgressBar voterProgressBar = new JProgressBar();
    private JLabel voterProgressBarLabel = new JLabel();
	private JProgressBar overallProgressBar = new JProgressBar();
   
    /** Constructs a progress pane */
    public JPanel generateProgressBarPane(JLabel label, JProgressBar progressBar)
    {
		// Creates progress bar to show that processing is occurring
        progressBar.setStringPainted(true);
    	
    	// Set the font of the progress bar description
        label.setFont(new java.awt.Font("MS Sans Serif", Font.BOLD, 11));
        label.setBorder(new EmptyBorder(0,0,3,0));
        
        // Generate the progress bar pane
    	JPanel progressBarPane = new JPanel();
    	progressBarPane.setBorder(new EmptyBorder(15,0,0,0));
    	progressBarPane.setLayout(new GridLayout(2,1));
    	progressBarPane.add(label);
    	progressBarPane.add(progressBar);
    	
    	// Push progress bar to top of pane
    	JPanel pane = new JPanel();
    	pane.setLayout(new BorderLayout());
    	pane.add(progressBarPane,BorderLayout.NORTH);
    	return pane;
    }
    
	/** Constructs the match pane */
    public MatchPane(HarmonyModel harmonyModel, MatchMerger merger)
    {   
    	this.harmonyModel = harmonyModel;
        this.merger = merger;

		// Creates the progress pane
		JPanel progressPane = new JPanel();
		progressPane.setBorder(new CompoundBorder(new EmptyBorder(10,0,5,0),new CompoundBorder(new LineBorder(Color.gray), new EmptyBorder(5,10,5,10))));
		progressPane.setLayout(new BoxLayout(progressPane,BoxLayout.Y_AXIS));
		progressPane.add(generateProgressBarPane(voterProgressBarLabel, voterProgressBar));
		progressPane.add(generateProgressBarPane(new JLabel("Overall Progress"), overallProgressBar));

		// Create the match pane
		JPanel pane = getPanel();
		pane.setLayout(new BorderLayout());
		pane.add(new JLabel("Progress of schema matching..."),BorderLayout.NORTH);
		pane.add(progressPane,BorderLayout.CENTER);
    }

    /** Describes the next pane to display */
    public String getNextPanelDescriptor()
    	{ return null; }
    
    /** Describes the previous pane that was displayed */
    public String getBackPanelDescriptor()
    	{ return TypePane.IDENTIFIER; }
    
    /** Initializes the panel before being displayed */
    public void aboutToDisplayPanel()
    {
    	getWizard().setBackButtonEnabled(false);
    	getWizard().setNextFinishButtonEnabled(false);
    }
	
    /** Generates the schema matches when the panel is being displayed */
    public void displayingPanel()
    {
    	// Retrieve the voter information
    	MatchVoterPane matchVoterPane = (MatchVoterPane)getWizard().getPanel(MatchVoterPane.IDENTIFIER);
    	ArrayList<MatchVoter> voters = matchVoterPane.getSelectedMatchVoters();
    	
    	// Retrieve the type information
    	TypePane typePane = (TypePane)getWizard().getPanel(TypePane.IDENTIFIER);
    	TypeMappings typeMappings = typePane.getTypeMappings();
    	
    	// Starts the match thread
    	matchThread = new MatchThread(harmonyModel,voters,merger,typeMappings);
        matchThread.addListener(this);
    	matchThread.start();
    }

    /** Handles the hiding of the match pane */
    public void aboutToHidePanel()
    	{ matchThread.stopThread(); }

    /** Updates the progress of the voters */
	public void updateVoterProgress(Double percentComplete, String status)
	{
		voterProgressBar.setValue(percentComplete.intValue());
		voterProgressBarLabel.setText(status);
	}
    
    /** Updates the progress of the schemas */
	public void updateOverallProgress(Double percentComplete)
		{ overallProgressBar.setValue(percentComplete.intValue()); }
  
	/** Handles the completion of the matching */
	public void matchCompleted()
		{ getWizard().getDialog().dispose(); }
}