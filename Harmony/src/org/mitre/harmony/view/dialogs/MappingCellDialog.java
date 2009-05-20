// (c) The MITRE Corporation 2006
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
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.mapping.MappingCellManager;
import org.mitre.schemastore.model.MappingCell;

/**
 * Displays the dialog which allows mapping cells to be accepted/rejected
 * @author CWOLF
 */
public class MappingCellDialog extends JDialog implements ActionListener, MouseListener, MouseMotionListener
{
	/** String for indicating that multiple values exist for the particular field */
	static private final String MULTIPLE_VALUES = "<Multiple Values>";
	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Stores the list of mapping cells to which this dialog pertains */
	private List<MappingCell> mappingCells;
	
	// Components used within the Mapping Cell dialog
	private JTextField dateField;
	private JTextField authorField;
	private JTextField transformField;
	private JTextArea notesField;
	private JCheckBox acceptCheckbox;
	private JCheckBox rejectCheckbox;
	
	/** Displays the pane displaying current mapping cell information */
	private class MappingCellInfoPane extends JPanel
	{
		/** Initializes the height of the pane */
		private MappingCellInfoPane()
		{
			int height = getFontMetrics(getFont()).getHeight();
			setPreferredSize(new Dimension(200,height*2+15));
		}
		
		/** Draws the pane displaying mapping cell information */
		public void paint(Graphics g)
		{
			// Calculate the confidence range of the selected mapping cells
			Double minConf = 1.0;
			Double maxConf = -1.0;
			for(MappingCell mappingCell : mappingCells)
			{
				Double conf = mappingCell.getScore();
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

	/** Private class for defining the button pane */
	private class ButtonPane extends AbstractButtonPane
	{
		/** Constructs the button pane */
		public ButtonPane()
			{ super("OK", "Cancel"); }

		/** Handles selection of okay button */
		protected void button1Action()
		{
			// Store if links are currently rejected
			boolean originallyRejected = mappingCellsRejected();
			
			// Cycle through all links to update annotations
			MappingCellManager manager = harmonyModel.getMappingCellManager();
			for(MappingCell mappingCell : mappingCells)
			{
				// Store all modifications to annotations
				if(!authorField.getText().equals(MULTIPLE_VALUES))
					mappingCell.setAuthor(authorField.getText());
				if(!transformField.getText().equals(MULTIPLE_VALUES))
					mappingCell.setTransform(transformField.getText());
				if(!notesField.getText().equals(MULTIPLE_VALUES))
					mappingCell.setNotes(notesField.getText());

				// Accept or reject links as needed
				if(acceptCheckbox.isSelected() || rejectCheckbox.isSelected())
				{
					mappingCell.setScore(acceptCheckbox.isSelected()?1.0:-1.0);
					mappingCell.setAuthor(System.getProperty("user.name"));
					mappingCell.setValidated(true);
				}

				manager.setMappingCell(mappingCell);
			}
				
			// If links rejected, unselect links after modifying annotations
			if(rejectCheckbox.isSelected() && !originallyRejected)
				harmonyModel.getSelectedInfo().setMappingCells(harmonyModel.getSelectedInfo().getSelectedMappingCells(),true);
			
			// Close link dialog
			dispose();
		}

		/** Handles selection of cancel button */
		protected void button2Action()
			{ dispose(); }
	}
	
	/** Indicates if all of the mapping cells are accepted */
	private boolean mappingCellsAccepted()
	{
		for(MappingCell mappingCell : mappingCells)
			if(!mappingCell.getValidated() || mappingCell.getScore()!=1.0) return false;
		return true;
	}

	/** Indicates if all of the mapping cells are rejected */
	private boolean mappingCellsRejected()
	{
		for(MappingCell mappingCell : mappingCells)
			if(!mappingCell.getValidated()|| mappingCell.getScore()!=-1.0) return false;
		return true;
	}
	
	/** Generates a label pane */
	private JPanel getLabelPane(String label)
	{
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.add(new JLabel(label),BorderLayout.EAST);
		return pane;
	}
	
	/** Returns the generated annotations pane */
	private JPanel getAnnotationsPane()
	{
		// Initial annotations
		String date = mappingCells.get(0).getDate();
		String author = mappingCells.get(0).getAuthor();
		String transform = mappingCells.get(0).getTransform();
		String notes = mappingCells.get(0).getNotes();
		
		// Check to see if the annotations for all mapping cells match
		for(MappingCell mappingCell : mappingCells)
		{
			if(!date.equals(mappingCell.getDate())) date = MULTIPLE_VALUES;
			if(!author.equals(mappingCell.getAuthor())) author = MULTIPLE_VALUES;
			if(!transform.equals(mappingCell.getTransform())) transform = MULTIPLE_VALUES;
			if(!notes.equals(mappingCell.getNotes())) notes = MULTIPLE_VALUES;			
		}
		
		// Initialize annotation fields
		dateField = new JTextField(date); dateField.setMargin(new Insets(1,1,1,1));
		authorField = new JTextField(author); authorField.setMargin(new Insets(1,1,1,1));
		transformField = new JTextField(transform); transformField.setMargin(new Insets(1,1,1,1));
		notesField = new JTextArea(notes); transformField.setMargin(new Insets(1,1,1,1));

		// Adjust the look of the annotation fields
		dateField.setEnabled(false);
		dateField.setBorder(BorderFactory.createEtchedBorder(Color.white,Color.gray));
		dateField.setDisabledTextColor(Color.gray);
		notesField.setRows(5);
		notesField.setLineWrap(true);
		notesField.setWrapStyleWord(true);
		
		// Build a pane with all of the annotation labels
		JPanel labelPane = new JPanel();
		labelPane.setLayout(new GridLayout(3,1));
		labelPane.add(getLabelPane("Date: "));
		labelPane.add(getLabelPane("Author: "));
		labelPane.add(getLabelPane("Transform: "));
		
		// Build a pane with all of the annotation field boxes
		JPanel fieldPane = new JPanel();
		fieldPane.setLayout(new GridLayout(3,1));
		fieldPane.add(dateField);
		fieldPane.add(authorField);
		fieldPane.add(transformField);
		
		// Build a pane for the annotations
		JPanel annotationsPane = new JPanel();
		annotationsPane.setBorder(BorderFactory.createEmptyBorder(0,3,3,3));
		annotationsPane.setLayout(new BorderLayout());
		annotationsPane.add(labelPane,BorderLayout.WEST);
		annotationsPane.add(fieldPane,BorderLayout.CENTER);

		// Build a pane with the notes annotation
		JPanel notesPane = new JPanel();
		notesPane.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
		notesPane.setLayout(new BorderLayout());
		notesPane.add(new JLabel("Notes:"),BorderLayout.NORTH);
		notesPane.add(new JScrollPane(notesField,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),BorderLayout.CENTER);
		
		// Merge together all annotation
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createTitledBorder("Annotations"));
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
		acceptCheckbox.setSelected(mappingCellsAccepted());
		rejectCheckbox.setSelected(mappingCellsRejected());
		
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
		linkInfoPane.add(new MappingCellInfoPane());
		
		// Set up confidence pane
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createTitledBorder("Confidence"));
		pane.setLayout(new BorderLayout());
		pane.add(linkInfoPane,BorderLayout.CENTER);
		pane.add(checkboxPane,BorderLayout.SOUTH);
		return pane;
	}
	
	/** Initializes the mapping cell dialog */
	public MappingCellDialog(List<MappingCell> mappingCells, HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		
		// Initialize the selected links
		this.mappingCells = mappingCells;
		this.harmonyModel = harmonyModel;
		
		// Set up the main dialog pane
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createLineBorder(Color.black));
		pane.setLayout(new BorderLayout());
		pane.add(getAnnotationsPane(),BorderLayout.NORTH);
		pane.add(getConfidencePane(),BorderLayout.CENTER);
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

	/**
	 * Handles the pressing of dialog buttons
	 */
	public void actionPerformed(ActionEvent e)
	{
		// If "Accept" checkbox chosen, unselect "Reject"
		if(e.getSource()==acceptCheckbox)
		{
			if(acceptCheckbox.isSelected()) rejectCheckbox.setSelected(false);
			else if(mappingCellsAccepted() || mappingCellsRejected()) acceptCheckbox.setSelected(true);
		}
		
		// If "Reject" checkbox chosen, unselect "Accept"
		if(e.getSource()==rejectCheckbox)
		{
			if(rejectCheckbox.isSelected()) acceptCheckbox.setSelected(false);
			else if(mappingCellsAccepted() || mappingCellsRejected()) rejectCheckbox.setSelected(true);
		}
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
