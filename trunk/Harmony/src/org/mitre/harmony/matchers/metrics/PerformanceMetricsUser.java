package org.mitre.harmony.matchers.metrics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*; 

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.mergers.*;
import org.mitre.harmony.matchers.voters.*;
import org.mitre.harmony.model.HarmonyModel;

import java.io.File;

public class PerformanceMetricsUser extends JFrame
{
	/**
	 * 	Creates user interface for performance calculations
	 * @author STANDON
	 */
	//instance variables used for creation of UI
	private JTextField txtf_pathName = null;
	private JPanel titlePanel, optionsPanel, groundTruthsOptionsPanel, voterOptionsPanel, checkPanel, mergerOptionsPanel, selectionPanel;
	
	//create listeners
	private ButtonHandler buttonListener = new ButtonHandler();
	private ItemHandler itemListener = new ItemHandler();
	
	//variables needed for calculation of precision and recall
	private File groundTruthXMLFile = null;
	private ArrayList<MatchVoter> availableVoters = null;
	private ArrayList<MatchVoter> selectedVoters = new ArrayList<MatchVoter>();
	private ArrayList<MatchMerger> availableMergers = null;
	private MatchMerger selectedMerger = null;

	//ArrayList storing values of precision and recall for each threshold value
	//private ArrayList<HashMap<Double,Double>> precisionRecall = new ArrayList<HashMap<Double,Double>>();
	
	private HarmonyModel harmonyModel;
	
	//types of fonts used
	private Font font1 = new Font(Font.DIALOG_INPUT ,Font.BOLD,25);
	private Font font2 = new Font(Font.SANS_SERIF, Font.BOLD, 15);
	
	//icon used
	final static ImageIcon mitreIcon = new ImageIcon("C://commonground//Harmony//org//mitre//wc3//w909//workbench//matchers//metrics//MITREicon.gif");
 	
	//default constructor - sets up GUI
	public PerformanceMetricsUser()
	{
		//create the frame
		super("Calculate Performance Metrics");
		harmonyModel = new HarmonyModel(this);
		
		//get content pane for the JFrame container
		Container c = getContentPane();
		 
		//set layout manager for the container
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		
		//get the list of all available voters
		availableVoters = MatcherManager.getVoters();
		 
		//get the list of all available mergers
		availableMergers = MatcherManager.getMergers();
		 
		//create and add title panel on the top
		c.add(createTitlePanel());
		
		//create and add options panel below it
		c.add(createOptionsPanel());
		 
		 //create and add selection panel below it
		 c.add(createCalculationPanel());
	}//end of constructor
	
	//creates panel for title
	private JPanel createTitlePanel()
	{
		titlePanel = new JPanel(new BorderLayout(20, 0));
		
		//create labels
		JLabel lbl_icon = new JLabel(mitreIcon, JLabel.LEADING);
		JLabel lbl_title = new JLabel("Calculation of Performance Metrics", JLabel.CENTER);
		lbl_title.setFont(font1);
		
		//add labels to the panel
		titlePanel.add(lbl_icon, BorderLayout.WEST);
		titlePanel.add(lbl_title, BorderLayout.CENTER);
		
		return titlePanel;
	}
	
	//creates panel for displaying and letting the user select ground truths file, voters and mergers
	private JPanel createOptionsPanel()
	{
		optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		
		optionsPanel.add(createGroundTruthsOptionsPanel());
		optionsPanel.add(createVoterOptionsPanel());
		optionsPanel.add(createMergerOptionsPanel());
		
		return optionsPanel;
	}

	//creates panel for displaying and letting the user select ground truths file
	private JPanel createGroundTruthsOptionsPanel()
	{
		groundTruthsOptionsPanel = new JPanel();
			
		//create and add ground truths heading and border
		groundTruthsOptionsPanel.setBorder(createBorder("Select Ground Truths file: "));
		
		//create a panel for text field and scroll bar
		JPanel txtfPanel = new JPanel();
		txtfPanel.setLayout(new BoxLayout(txtfPanel, BoxLayout.Y_AXIS));
		
		//create and add text field for entering ground truths file path name
		txtf_pathName = new JTextField(50);
		txtf_pathName.setHorizontalAlignment(JTextField.LEFT );
		txtf_pathName.setEditable(false);
		txtf_pathName.setFont(font2);
		txtf_pathName.setBackground(Color.WHITE);
		txtfPanel.add(txtf_pathName);
		
		//create and add scroll bar
		JScrollBar txtf_scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
		BoundedRangeModel brm = txtf_pathName.getHorizontalVisibility();
		txtf_scrollBar.setModel(brm);
		txtfPanel.add(txtf_scrollBar);
		
		//add this panel to groundTruthsOptionsPanel
		groundTruthsOptionsPanel.add(txtfPanel);
		
		//create and add button for browsing to search for ground truths file
		JButton btn_browse = new JButton("Browse");
		groundTruthsOptionsPanel.add(btn_browse);
		
		//create an instance of inner class ButtonHandler that is a listener and processes the action event
		//Register listener with the browse button
		btn_browse.addActionListener(buttonListener);
		
		return groundTruthsOptionsPanel;
	}
	
	//creates panel for displaying and letting the user select voters
	private JPanel createVoterOptionsPanel()
	{
		JPanel voterOptionsPanel = new JPanel();
		voterOptionsPanel.setLayout(new BorderLayout());
		
		//create and add voter heading and border
		voterOptionsPanel.setBorder(createBorder("Select Voter algorithms: "));
		
		//create a panel for check boxes
		checkPanel = new JPanel(new GridLayout(0, 1));

		//Create the check boxes
		for(MatchVoter v : availableVoters)
		{
			JCheckBox box = new JCheckBox(v.getName(), false);
			box.setFont(font2);
			//create an instance of inner class ItemHandler that listens to and processes the item event 
			//received from check boxes.  Register this listener with each check box
			box.addItemListener(itemListener);
			checkPanel.add(box);
		}

        //add this panel to the voterOptionsPanel
        voterOptionsPanel.add(checkPanel, BorderLayout.CENTER);
        
		return voterOptionsPanel;
	}
	
	//creates panel for displaying and letting the user select merger
	private JPanel createMergerOptionsPanel()
	{
		mergerOptionsPanel = new JPanel(new GridLayout(0, 1));
				
		//create and add merger heading and border
		mergerOptionsPanel.setBorder(createBorder("Select Merger algorithm: "));
		
		//create a group for the radio buttons
		ButtonGroup group = new ButtonGroup();
		
		//Create the radio buttons
		for(MatchMerger m : availableMergers)
		{
			JRadioButton radioButton = new JRadioButton(m.getName(), false);
			radioButton.setFont(font2);
			//create an instance of inner class ItemHandler that listens to and processes the item event 
			//received from radio buttons.  Register this listener with each radio button
			radioButton.addItemListener(itemListener);
			group.add(radioButton);
			mergerOptionsPanel.add(radioButton);
		}
  		return mergerOptionsPanel;
	}
	
	//creates panel for selecting ground truths file, voters and mergers
	private JPanel createCalculationPanel()
	{
		selectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 15)); 
		
		//create and add button for calculation
		JButton btn_calculate = new JButton("CALCULATE PRECISION AND RECALL");
		selectionPanel.add(btn_calculate);

		//create an instance of inner class ButtonHandler that is a listener and processes the action event
		//Register listener with the calculate button
		btn_calculate.addActionListener(buttonListener);
		
		//create and add button for exiting
		JButton btn_exit = new JButton("Exit");
		selectionPanel.add(btn_exit);

		//create an instance of inner class ButtonHandler that is a listener and processes the action event
		//Register listener with the select button
		btn_exit.addActionListener(buttonListener);
		
		return selectionPanel;
	}
	
	//creates borders for options panel
	private Border createBorder(String title)
	{
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		
		return BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel), 
				title, TitledBorder.LEADING , TitledBorder.DEFAULT_POSITION , 
				new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
	}
	
	//inner class that receives notifications from buttons (that are registered with it) when the button is clicked
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String action = e.getActionCommand();

			if(e.getSource() instanceof JButton)
			{
				if("Exit".equals(action))
				{
					System.exit(0);
				}
				else if("Browse".equals(action))
				{
					browse();
				}
				else if("CALCULATE PRECISION AND RECALL".equals(action))
				{
					calculate();
				}
			}
		}
		
		//method to find a file/directory
		private void browse()
		{
			//GUI for choosing files
			JFileChooser fc = new JFileChooser ();
		    fc.setDialogTitle ("Select a XML File to be used as Ground Truths file: ");
		    
		    //Choose only files, not directories
		    fc.setFileSelectionMode ( JFileChooser.FILES_ONLY);
		    
		    //Start in current directory
		    fc.setCurrentDirectory (new File ("."));
		    
		    //Set filter for source files
		    ExtensionFileFilter ef1 = new ExtensionFileFilter("XML", "xml");
		    fc.setFileFilter(ef1);
		    
		    //Now open chooser
		    int result = fc.showOpenDialog(PerformanceMetricsUser.this);
		    
		    if (result == JFileChooser.APPROVE_OPTION)
		    {
		    	groundTruthXMLFile = fc.getSelectedFile();
		    	txtf_pathName.setText(groundTruthXMLFile.getAbsolutePath());
		    }
		}//end of method browse()
		
		//method to calculate precision and recall
		private void calculate()
		{
			if(txtf_pathName.getText().trim().length() <= 0)
			{
				JOptionPane.showMessageDialog(groundTruthsOptionsPanel, "You forgot to enter name of Ground Truths file", 
										"Try Again!", JOptionPane.WARNING_MESSAGE);
			}
			
			if(selectedVoters.isEmpty())
			{
				JOptionPane.showMessageDialog(voterOptionsPanel, "You forgot to select voters", 
						"Try Again!", JOptionPane.WARNING_MESSAGE);
			}
			
			if(selectedMerger == null)
			{
				JOptionPane.showMessageDialog(mergerOptionsPanel, "You forgot to select a merger", 
						"Try Again!", JOptionPane.WARNING_MESSAGE);
			}

			try
			{
				if (groundTruthXMLFile != null && !selectedVoters.isEmpty() && selectedMerger != null)
				{
					System.out.println("Ground Truth File: " + groundTruthXMLFile.getName());
					System.out.println("List of selected Voters: " + selectedVoters.toString());
					System.out.println("Selected Merger: " + selectedMerger.toString());
					
					AlgorithmMappings mappings = new AlgorithmMappings();
					ArrayList<HashMap<Double,Double>> precisionRecall = mappings.getMetrics(groundTruthXMLFile, selectedVoters, selectedMerger, harmonyModel);
				
					System.out.println("Threshold " + "Precision " + "Recall");
					for(double threshold = 0.0; threshold <= 1.01; threshold += 0.01)
						System.out.println(threshold + " " + precisionRecall.get(0).get(threshold) + " " + 
		     									precisionRecall.get(1).get(threshold));
				}
			}
			catch(IllegalFileFormatException e)
			{
				JOptionPane.showMessageDialog(groundTruthsOptionsPanel, e.getMessage(), 
						"Try Again!", JOptionPane.WARNING_MESSAGE);
			}
		}//end of method calculate()

		/**
		//method to set default values in each field (not being used for now)
		private void setDefaultValues()
		{
			groundTruthXMLFile = null;
			selectedVoters.clear();
			selectedMerger = null;
			
			txtf_pathName = null;
			
			for (int i = 0; i < checkPanel.getComponentCount(); i++)
			{
				JCheckBox box = (JCheckBox) checkPanel.getComponent(i);
				if(box instanceof JCheckBox)
					box.setSelected(false);
			}
			
			for (int i = 0; i < mergerOptionsPanel.getComponentCount(); i++)
			{
				JRadioButton button = (JRadioButton) mergerOptionsPanel.getComponent(i);
				if(button instanceof JRadioButton)
					button.setSelected(false);
			}
		}
		*/
		
	}//end of inner class ButtonHandler
	
	//inner class that listens to and receives item events from a check boxes (that are registered with it) when 
	//an item selection event occurs
	private class ItemHandler implements ItemListener
	{
		//invoked when a check box is selected or de-selected
		public void itemStateChanged(ItemEvent e)
		{
			ItemSelectable source = e.getItemSelectable();
						
			if(source instanceof JCheckBox)
			{
				//find the instance of the voter whose corresponding check box was clicked on
				MatchVoter clickedVoter = null;
				for(MatchVoter v : availableVoters)
					if (clickedVoter == null)
						if (v.getName() == ((JCheckBox)source).getText())
							clickedVoter = v;
				
				//populate data depending on selection or de-selection
				if (e.getStateChange() == ItemEvent.DESELECTED)
					selectedVoters.remove(clickedVoter);
				else if (e.getStateChange() == ItemEvent.SELECTED)
					selectedVoters.add(clickedVoter);
			}
				
			if(source instanceof JRadioButton)
			{
				//find the instance of the merger whose corresponding radio button was clicked on
				MatchMerger clickedMerger = null;
				for(MatchMerger m : availableMergers)
					if (clickedMerger == null)
						if (m.getName() == ((JRadioButton)source).getText())
							clickedMerger = m;
				
				//populate data depending on selection or de-selection
				if (e.getStateChange() == ItemEvent.DESELECTED)
					{	}
				else if (e.getStateChange() == ItemEvent.SELECTED)
					selectedMerger = clickedMerger;
			}
		}//end of method itemStateChanged
	}//end of class CheckBoxHandler
	
	//creates and sets up user interface.  For thread safety, this method is invoked from the event-dispatching thread.
	private static void createAndShowGUI() 
	{
		//Create and set up the window
		PerformanceMetricsUser userInterface = new PerformanceMetricsUser();
		userInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		userInterface.setLocation(200, 200);
		JFrame.setDefaultLookAndFeelDecorated(true);
		userInterface.setIconImage(mitreIcon.getImage());
		
		//Display the window
		userInterface.pack();
		userInterface.setVisible(true);
	}//end of createAndShowGUI() method
    
	public static void main(String[] args) 
	{
		//Schedule a job for the event-dispatching thread i.e., creating and showing this application's GUI
	    javax.swing.SwingUtilities.invokeLater(new Runnable()
	    { // Anonymous class
	      public void run()
	      {
	    	  createAndShowGUI();
	      }
	    });
	  }//end of main
}//end of PerformanceMetricsUser class
