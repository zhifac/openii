// � The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.mitre.harmony.model.MappingCellManager;

/**
 * Displays the dialog showing mapping cell confidence
 * @author CWOLF
 */
public class ConfidenceDialog extends JDialog
{	
	/** Stores the confidence dialog label */
	private JLabel label = new JLabel();

	/** Initializes the mapping cell dialog */
	public ConfidenceDialog()
	{		
		// Generate the pane
		JPanel pane = new JPanel();
		pane.setBorder(new LineBorder(Color.darkGray));
		pane.setBackground(UIManager.getColor("ToolTip.background"));
		pane.setSize(new Dimension(200, getFontMetrics(label.getFont()).getHeight() * 2));
		pane.add(label);
		
		// Initialize the dialog parameters
		setModal(false);
		setResizable(false);
		setUndecorated(true);
		setContentPane(pane);
		pack();
	}

	/** Sets the confidence dialog mapping cell */
	public void setMappingCell(Integer mappingCellID)
	{
		// Retrieve mapping cell information
		Double score = MappingCellManager.getMappingCell(mappingCellID).getScore();
		String notes = MappingCellManager.getMappingCellNotes(mappingCellID);

		// Generate the text to display in the tool tip
		StringBuffer display = new StringBuffer("<html>");
		display.append("Confidence: " + score + "<br>");
		display.append(notes.length()==0 ? "" : "Notes: " + notes);
		display.append("</html>");
		label.setText(display.toString());
		pack();
	}
}
