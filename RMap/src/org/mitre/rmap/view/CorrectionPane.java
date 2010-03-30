package org.mitre.rmap.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import org.mitre.harmony.model.selectedInfo.SelectedInfoListener;

public class CorrectionPane extends JPanel implements SelectedInfoListener {

	/** Stores the Harmony model */
	private RMapHarmonyModel harmonyModel;
	
	/** Stores the box that contains the changes to be made */
	private JTextPane corrections = new JTextPane();

	public void setCorrections(JTextPane corrections) {
		this.corrections = corrections;
	}

	public JTextPane getCorrections() {
		return corrections;
	}

	/** Initializes the corrections pane */
	public CorrectionPane(RMapHarmonyModel harmonyModel) {
		this.harmonyModel = harmonyModel;
		setLayout(new BorderLayout());
		harmonyModel.getSelectedInfo().addListener(this);
	}

	public void displayedElementModified(Integer role) {
		// TODO Auto-generated method stub
	}

	public void selectedElementsModified(Integer role) {
		// TODO Auto-generated method stub
	}

	public void selectedMappingCellsModified() {
		// TODO Auto-generated method stub
	}
}
