// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.mitre.harmony.model.MappingCellManager;
import org.mitre.harmony.model.selectedInfo.SelectedInfo;
import org.mitre.harmony.view.harmonyPane.HarmonyFrame;
import org.mitre.schemastore.model.MappingCell;

/**
 * Displays the dialog which allows links to be accepted/rejected
 * @author CWOLF
 */
public class LinkDialog extends JDialog implements ActionListener, MouseListener, MouseMotionListener
{
	static private final String MULTIPLE_VALUES = "<Multiple Values>";
	
	private List<Integer> links;	// Links which may be accepted/rejected

	/**
	 * Displays the pane displaying current link information
	 * @author CWOLF
	 */
	private class LinkInfoPane extends JPanel
	{
		/**
		 * Initializes the height of the pane
		 */
		private LinkInfoPane()
		{
			int height = getFontMetrics(getFont()).getHeight();
			setPreferredSize(new Dimension(200,height*2+15));
		}
		
		/**
		 * Draws the pane displaying link information
		 */
		public void paint(Graphics g)
		{
			// Calculate the confidence range of the selected links
			Double minConf = 1.0;
			Double maxConf = -1.0;
			for(Integer link : links)
			{
				Double conf = MappingCellManager.getMappingCell(link).getScore();
				if(conf < minConf) minConf = conf;
				if(conf > maxConf) maxConf = conf;
			}
			minConf = Math.round(minConf*100.0)/100.0;
			maxConf = Math.round(maxConf*100.0)/100.0;
			
			// Retrieves the height of the font
			int height = getFontMetrics(getFont()).getHeight();
			
			// Label computer selection line
			g.setColor(Color.black);
			g.drawString("Not a Match",5,height);
			int width = (int)g.getFontMetrics().getStringBounds("Is a Match",g).getWidth();
			g.drawString("Is a Match",195-width,height);
		
			// Draw computer selection line
			Graphics2D g2d = (Graphics2D)g;
			g2d.setPaint(Color.RED);
			g2d.fillRect(5,height+3,64,3);
			g2d.setPaint(new GradientPaint(69,height+4,Color.red,132,height+4,Color.yellow));
			g2d.fillRect(69,height+3,63,3);
			g2d.setPaint(new GradientPaint(132,height+4,Color.yellow,195,height+4,Color.green));
			g2d.fillRect(132,height+3,63,3);
			
			// Point at selected point on computer selection line
			g.setColor(Color.black);
			int minXLoc = (int)(minConf*95)+100;
			int maxXLoc = (int)(maxConf*95)+100;
			int midXLoc = (minXLoc+maxXLoc)/2;
			int yLoc = height+5;
			if(minXLoc==maxXLoc)
			{
				int x[] = {midXLoc,midXLoc-5,midXLoc+4};
				int y[] = {yLoc,yLoc+5,yLoc+5};
				g.fillPolygon(x,y,3);
			}
			else
			{
				g.drawLine(minXLoc,yLoc+1,minXLoc,yLoc+5);
				g.drawLine(maxXLoc,yLoc+1,maxXLoc,yLoc+5);
				g.drawLine(minXLoc,yLoc+5,maxXLoc,yLoc+5);
			}
			g.drawLine(midXLoc,yLoc+5,midXLoc,height*2+5);
			g.drawLine(midXLoc,height*2+5,100,height*2+5);
			
			// Get label to display in link dialog box
			String label;
			label = "Confidence ("+minConf+(!minConf.equals(maxConf)?"-"+maxConf:"")+")";

			// Display label with arrow going to confidence location
			width = (int)g.getFontMetrics().getStringBounds(label,g).getWidth();
			g.setColor(getBackground());
			g.fillRect(100-width/2,height+15,width,height);
			g.setColor(Color.black);
			g.drawString(label,100-width/2,height*2+10);
		}
	}

	private JTextField nameField; 			// Field for displaying the link's name
	private JTextField dateField; 			// Field for displaying the link's creation date
	private JTextField authorField; 		// Field for displaying the link's author
	private JTextField transformField; // Field for displaying the link's transformation
	private JTextArea notesField; 			// Field for displaying the link's notes
	
	private JCheckBox acceptCheckbox;		// Checkbox indicating to accept the link
	private JCheckBox rejectCheckbox;		// Checkbox indicating to reject the link
	
	private JButton okayButton; // Button used for approving changes to the link's settings
	private JButton cancelButton; // Button used for cancelling changes to the link's settings
	
	/**
	 * @return Indicates if all of the links are accepted
	 */
	private boolean linksAccepted()
	{
		for(Integer link : links)
		{
			MappingCell mappingCell = MappingCellManager.getMappingCell(link);
			if(!mappingCell.getValidated()) return false;
			if(mappingCell.getScore()!=1.0) return false;
		}
		return true;
	}

	/**
	 * @return Indicates if all of the links are rejected
	 */
	private boolean linksRejected()
	{
		for(Integer link : links)
		{
			MappingCell mappingCell = MappingCellManager.getMappingCell(link);
			if(!mappingCell.getValidated()) return false;
			if(mappingCell.getScore()!=-1.0) return false;
		}
		return true;
	}
	
	/**
	 * @param label - Label for label pane
	 * @return A generated label pane
	 */
	private JPanel getLabelPane(String label)
	{
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.add(new JLabel(label),BorderLayout.EAST);
		return pane;
	}
	
	/**
	 * @return The generated annotations pane
	 */
	private JPanel getAnnotationsPane()
	{
		// Initial annotations
		String name = MappingCellManager.getMappingCellName(links.get(0));
		String creationDate = MappingCellManager.getMappingCellCreationDate(links.get(0));
		String author = MappingCellManager.getMappingCellAuthor(links.get(0));
		String transform = MappingCellManager.getMappingCellTransform(links.get(0));
		String notes = MappingCellManager.getMappingCellNotes(links.get(0));
		
		// Check to see if the annotations for all links match
		for(Integer link : links)
		{
			if(!name.equals(MappingCellManager.getMappingCellName(link))) name = MULTIPLE_VALUES;
			if(!creationDate.equals(MappingCellManager.getMappingCellCreationDate(link))) creationDate = MULTIPLE_VALUES;
			if(!author.equals(MappingCellManager.getMappingCellAuthor(link))) author = MULTIPLE_VALUES;
			if(!transform.equals(MappingCellManager.getMappingCellTransform(link))) transform = MULTIPLE_VALUES;
			if(!notes.equals(MappingCellManager.getMappingCellNotes(link))) notes = MULTIPLE_VALUES;			
		}
		
		// Initialize annotation fields
		nameField = new JTextField(name); nameField.setMargin(new Insets(1,1,1,1));
		dateField = new JTextField(creationDate); dateField.setMargin(new Insets(1,1,1,1));
		authorField = new JTextField(author); authorField.setMargin(new Insets(1,1,1,1));
		transformField = new JTextField(transform); transformField.setMargin(new Insets(1,1,1,1));
		notesField = new JTextArea(notes); transformField.setMargin(new Insets(1,1,1,1));
		notesField.setRows(3);
		notesField.setBorder(nameField.getBorder());
		
		// Build a pane with all of the annotation labels
		JPanel labelPane = new JPanel();
		labelPane.setLayout(new GridLayout(4,1));
		labelPane.add(getLabelPane("Name: "));
		labelPane.add(getLabelPane("Date: "));
		labelPane.add(getLabelPane("Author: "));
		labelPane.add(getLabelPane("Transform: "));
		
		// Build a pane with all of the annotation field boxes
		JPanel fieldPane = new JPanel();
		fieldPane.setLayout(new GridLayout(4,1));
		fieldPane.add(nameField);
		fieldPane.add(dateField);
		fieldPane.add(authorField);
		fieldPane.add(transformField);
		
		// Build a pane for the annotations
		JPanel annotationsPane = new JPanel();
		annotationsPane.setBorder(new EmptyBorder(0,3,3,3));
		annotationsPane.setLayout(new BorderLayout());
		annotationsPane.add(labelPane,BorderLayout.WEST);
		annotationsPane.add(fieldPane,BorderLayout.CENTER);
		
		// Build a pane with the notes annotation
		JPanel notesPane = new JPanel();
		notesPane.setBorder(new EmptyBorder(3,3,3,3));
		notesPane.setLayout(new BorderLayout());
		notesPane.add(new JLabel("Notes:"),BorderLayout.NORTH);
		notesPane.add(notesField,BorderLayout.CENTER);
		
		// Merge together all annotation
		JPanel pane = new JPanel();
		pane.setBorder(new TitledBorder("Annotations"));
		pane.setLayout(new BorderLayout());
		pane.add(annotationsPane,BorderLayout.NORTH);
		pane.add(notesPane,BorderLayout.CENTER);
		return pane;
	}
	
	/**
	 * @return The generated confidence pane
	 */
	private JPanel getConfidencePane()
	{
		// Initialize checkboxes
		acceptCheckbox = new JCheckBox("Accept");
		rejectCheckbox = new JCheckBox("Reject");

		// Add listeners to the checkboxes
		acceptCheckbox.addActionListener(this);
		rejectCheckbox.addActionListener(this);
		
		// Mark checkboxes as needed
		acceptCheckbox.setSelected(linksAccepted());
		rejectCheckbox.setSelected(linksRejected());
		
		// Set up accept and reject panes
		JPanel acceptPane = new JPanel();
		acceptPane.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		acceptPane.add(acceptCheckbox);
		JPanel rejectPane = new JPanel();
		rejectPane.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		rejectPane.add(rejectCheckbox);
		
		// Set up checkbox item pane
		JPanel checkboxPane = new JPanel();
		checkboxPane.setLayout(new GridLayout(1,2));
		checkboxPane.add(acceptPane);
		checkboxPane.add(rejectPane);
		
		// Set up link info pane
		JPanel linkInfoPane = new JPanel();
		linkInfoPane.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		linkInfoPane.add(new LinkInfoPane());
		
		// Set up confidence pane
		JPanel pane = new JPanel();
		pane.setBorder(new TitledBorder("Confidence"));
		pane.setLayout(new BorderLayout());
		pane.add(linkInfoPane,BorderLayout.CENTER);
		pane.add(checkboxPane,BorderLayout.SOUTH);
		return pane;
	}
	
	/**
	 * @return The generated buttons pane
	 */
	private JPanel getButtonsPane()
	{
		// Initialize buttons
		okayButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		
		// Listen for actions on the buttons
		okayButton.addActionListener(this);
		cancelButton.addActionListener(this);

		// Set up the pane displaying all button information
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(4,4,4,4));
		pane.setLayout(new GridLayout(1,2));
		pane.add(okayButton);
		pane.add(cancelButton);
		return pane;
	}
	
	/**
	 * Initializes the link dialog
	 * @param link Link associated with the link dialog
	 */
	public LinkDialog(List<Integer> links)
	{
		super(HarmonyFrame.harmonyFrame.getFrame());
		
		// Initialize the selected links
		this.links = links;
		
		// Set up the main dialog pane
		JPanel pane = new JPanel();
		pane.setBorder(new LineBorder(Color.black));
		pane.setLayout(new BorderLayout());
		pane.add(getAnnotationsPane(),BorderLayout.NORTH);
		pane.add(getConfidencePane(),BorderLayout.CENTER);
		pane.add(getButtonsPane(),BorderLayout.SOUTH);
		
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

	/**
	 * Handles the pressing of dialog buttons
	 */
	public void actionPerformed(ActionEvent e)
	{
		// If "Accept" checkbox chosen, unselect "Reject"
		if(e.getSource()==acceptCheckbox)
		{
			if(acceptCheckbox.isSelected()) rejectCheckbox.setSelected(false);
			else if(linksAccepted() || linksRejected()) acceptCheckbox.setSelected(true);
		}
		
		// If "Reject" checkbox chosen, unselect "Accept"
		if(e.getSource()==rejectCheckbox)
		{
			if(rejectCheckbox.isSelected()) acceptCheckbox.setSelected(false);
			else if(linksAccepted() || linksRejected()) rejectCheckbox.setSelected(true);
		}
		
		// If "Okay" button pressed, update link information in model
		if(e.getSource()==okayButton)
		{
			// Store if links are currently rejected
			boolean originallyRejected = linksRejected();
			
			// Cycle through all links to update annotations
			for(Integer link : links)
			{
				// Store all modifications to annotations
				if(!nameField.getText().equals(MULTIPLE_VALUES))
					MappingCellManager.setMappingCellName(link,nameField.getText());
				if(!dateField.getText().equals(MULTIPLE_VALUES))
					MappingCellManager.setMappingCellCreationDate(link,dateField.getText());
				if(!authorField.getText().equals(MULTIPLE_VALUES))
					MappingCellManager.setMappingCellAuthor(link,authorField.getText());
				if(!transformField.getText().equals(MULTIPLE_VALUES))
					MappingCellManager.setMappingCellTransform(link,transformField.getText());
				if(!notesField.getText().equals(MULTIPLE_VALUES))
					MappingCellManager.setMappingCellNotes(link,notesField.getText());

				// Accept or reject links as needed
				if(acceptCheckbox.isSelected() || rejectCheckbox.isSelected())
					MappingCellManager.modifyMappingCell(link,acceptCheckbox.isSelected()?1.0:-1.0,System.getProperty("user.name"),true);
			}
				
			// If links rejected, unselect links after modifying annotations
			if(rejectCheckbox.isSelected() && !originallyRejected)
				SelectedInfo.setMappingCells(SelectedInfo.getMappingCells(),true);
			
			// Close link dialog
			dispose();
		}
		
		// If "Cancel" button pressed, just close dialog window
		if(e.getSource()==cancelButton)
			dispose();
	}
	
	//-----------------------------------------------------------------
	// Purpose: Handles the movement of the dialog box by mouse actions
	//-----------------------------------------------------------------
	private Point mousePosition = null;
	public void mousePressed(MouseEvent e) { mousePosition=e.getPoint(); }
	public void mouseDragged(MouseEvent e)
	{
		if(mousePosition!=null) {
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
