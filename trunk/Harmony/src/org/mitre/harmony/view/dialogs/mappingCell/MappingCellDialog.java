// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.mappingCell;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.AbstractButtonPane;
import org.mitre.schemastore.model.MappingCell;

/**
 * Displays the dialog which allows mapping cells to be accepted/rejected
 * @author CWOLF
 */
public class MappingCellDialog extends JDialog implements MouseListener, MouseMotionListener
{
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;

	/** Stores the annotation pane */
	private MappingCellAnnotationPane annotationPane;
	
	/** Stores the confidence pane */
	private MappingCellConfidencePane confidencePane;

	/** Stores the list of mapping cells to which this dialog pertains */
	private ArrayList<MappingCell> mappingCells;
	
	/** Private class for defining the button pane */
	private class ButtonPane extends AbstractButtonPane
	{
		/** Constructs the button pane */
		public ButtonPane()
			{ super(new String[]{"OK", "Cancel"},1,2); }

		/** Handles selection of button */
		protected void buttonPressed(String label)
		{
			if(label.equals("OK"))
			{
				// Throw out mapping cells if they have been rejected
				if(confidencePane.isRejected())
					harmonyModel.getMappingManager().deleteMappingCells(mappingCells);
	
				// Validate mapping cells if they have been accepted
				else
				{
					// Adjust the mapping cells to reflect changes
					ArrayList<MappingCell> newMappingCells = annotationPane.getMappingCells();
					harmonyModel.getMappingManager().setMappingCells(newMappingCells);
					
					// If the mapping cells were accepted, validate as needed
					if(confidencePane.isAccepted())
						harmonyModel.getMappingManager().validateMappingCells(newMappingCells);
				}
			}
			
			// Close link dialog
			dispose();
		}
	}
	
	/** Initializes the mapping cell dialog */
	public MappingCellDialog(ArrayList<MappingCell> mappingCells, HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		
		// Initialize the selected links
		this.mappingCells = mappingCells;
		this.harmonyModel = harmonyModel;
		
		// Set up the main dialog pane
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createLineBorder(Color.black));
		pane.setLayout(new BorderLayout());
		pane.add(annotationPane = new MappingCellAnnotationPane(mappingCells),BorderLayout.NORTH);
		pane.add(confidencePane = new MappingCellConfidencePane(mappingCells),BorderLayout.CENTER);
		pane.add(new ButtonPane(),BorderLayout.SOUTH);
		
		// Set up ability to escape out of dialog
		Action escape = new AbstractAction() { public void actionPerformed(ActionEvent arg0) { dispose(); } };
		pane.getInputMap().put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE),"escape");
		pane.getActionMap().put("escape", escape);
		
		// Initialize the dialog parameters
		setModal(true);
		setResizable(false);
		setUndecorated(true);
		setContentPane(pane);
		pack();
		
		// Listen for mouse actions to move the dialog pane
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	//-----------------------------------------------------------------
	// Purpose: Handles the movement of the dialog box by mouse actions
	//-----------------------------------------------------------------
	private Point mousePosition = null;
	public void mousePressed(MouseEvent e) { mousePosition=e.getPoint(); }
	public void mouseDragged(MouseEvent e)
	{
		if(mousePosition!=null)
		{
			int xShift=e.getPoint().x-mousePosition.x;
			int yShift=e.getPoint().y-mousePosition.y;
			setLocation(getLocation().x+xShift,getLocation().y+yShift);
		}
	}
	public void mouseReleased(MouseEvent e) { mousePosition=null; }
	public void mouseClicked(MouseEvent e) {}	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
}
