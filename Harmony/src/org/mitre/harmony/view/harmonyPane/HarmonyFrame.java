// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.harmonyPane;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.model.MappingCellManager;
import org.mitre.harmony.model.MappingManager;
import org.mitre.harmony.model.ProjectManager;
import org.mitre.harmony.model.selectedInfo.SelectedInfo;
import org.mitre.harmony.view.mappingPane.MappingPane;

/**
 * Displays main Harmony window
 * @author CWOLF
 */
public class HarmonyFrame extends JInternalFrame implements WindowListener
{	
	/** Stores the project manager */
	private ProjectManager projectManager = null;

	/** Stores the frame to which this internal frame is associated */
	private Frame frame = null;
	
	/** Stores the main harmony frame for reference */
	static public HarmonyFrame harmonyFrame;

	/** Subclass used to delete links */
	private class DeleteLink extends AbstractAction
	{
		/** Deletes selected link from mapping */
		public void actionPerformed(ActionEvent e)
		{
			for(Integer link : SelectedInfo.getMappingCells())
				MappingCellManager.modifyMappingCell(link,-1.0,System.getProperty("user.name"),true);
			SelectedInfo.setMappingCells(new ArrayList<Integer>(),false);
		}
	};
	
	/** Subclass used to accept links */
	private class AcceptLink extends AbstractAction
	{
		/** Accept selected link */
		public void actionPerformed(ActionEvent arg0)
		{
			for(Integer link : SelectedInfo.getMappingCells())
				MappingCellManager.modifyMappingCell(link,1.0,System.getProperty("user.name"),true);
			SelectedInfo.setMappingCells(new ArrayList<Integer>(),false);
		}
	};
	
	/** Generates the main pane */
	private JPanel getMainPane()
	{
    	// Initialize the various panes shown in the main Harmony pane
    	TitledPane mappingPane = new TitledPane(null,new MappingPane());
    	TitledPane confidencePane = new TitledPane("Confidence",new ConfidencePane());
    	TitledPane assertionPane = new TitledPane("Show Links",new AssertionPane());
 
    	// Layout side pane of Harmony
    	JPanel sidePane = new JPanel();
    	sidePane.setLayout(new BorderLayout());
    	sidePane.add(confidencePane,BorderLayout.CENTER);
    	sidePane.add(assertionPane,BorderLayout.SOUTH);
   	
    	// Generate the main pane of Harmony
    	JPanel mainPane = new JPanel();
    	mainPane.setLayout(new BorderLayout());
    	mainPane.add(mappingPane,BorderLayout.CENTER);
    	mainPane.add(sidePane,BorderLayout.EAST);

		// Register keyboard actions for deleting links
		KeyStroke deleteKey = KeyStroke.getKeyStroke((char) KeyEvent.VK_DELETE);
		mainPane.getInputMap(JPanel.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(deleteKey, "deleteLink");
		KeyStroke backspaceKey = KeyStroke.getKeyStroke((char) KeyEvent.VK_BACK_SPACE);
		mainPane.getInputMap(JPanel.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(backspaceKey, "deleteLink");
		mainPane.getActionMap().put("deleteLink", new DeleteLink());
		
		// Register keyboard actions for accepting links
		KeyStroke acceptKey = KeyStroke.getKeyStroke((char) KeyEvent.VK_SPACE);
		mainPane.getInputMap(JPanel.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(acceptKey, "acceptLink");
		mainPane.getActionMap().put("acceptLink", new AcceptLink());
		
		return mainPane;
	}
	
	/** Constructs the Harmony pane */
    public HarmonyFrame(Frame frame)
    {
    	super();
    	this.frame = frame;
    	
    	// Start up the project manager
    	if(projectManager==null) projectManager = new ProjectManager();
    	
    	// Place title on application
		String mappingName = MappingManager.getMapping().getName();
		setTitle("Harmony Schema Matcher" + (mappingName!=null ? " - " + MappingManager.getMapping().getName() : ""));
    	harmonyFrame = this;
    	
    	// Set dialog pane settings
		((javax.swing.plaf.basic.BasicInternalFrameUI)harmonyFrame.getUI()).setNorthPane(null);
		harmonyFrame.setBorder(new EmptyBorder(0,0,0,0));
		try { harmonyFrame.setMaximum(true); } catch(Exception e) {}
    	setJMenuBar(new HarmonyMenuBar());
		setContentPane(getMainPane());
 	   	setVisible(true);
 	   	
 	   	// Add a listener to monitor for the closing of the parent frame
 	   	frame.addWindowListener(this);
    }

    /** Returns the frame in which this internal frame is placed */
    public Frame getFrame()
    	{ return frame; }
    
    /** Safely closes Harmony without losing user data */
	public void exitApp()
	{
		ProjectManager.save();
		getFrame().dispose();
	}

	/** Forces graceful closing of Harmony */
	public void windowClosing(WindowEvent event)
		{ exitApp(); }

	// Unused event listeners
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}