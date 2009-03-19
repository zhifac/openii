// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.mappings;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.AbstractButtonPane;

/**
 * Class used for the saving the current mapping
 * @author CWOLF
 */
public class SaveMappingDialog extends JDialog implements ListSelectionListener
{	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Stores the mapping pane */
	private MappingPane mappingPane = null;
	
	/** Stores the info pane */
	private InfoPane infoPane = null;

	/** Private class for defining the button pane */
	private class ButtonPane extends AbstractButtonPane
	{
		/** Constructs the button pane */
		public ButtonPane()
			{ super("Save", "Cancel"); }

		/** Handles selection of okay button */
		protected void button1Action()
		{
			if(infoPane.validateInfo())
			{
				harmonyModel.getMappingManager().saveMapping(mappingPane.getMapping());
				dispose();
			}
		}

		/** Handles selection of cancel button */
		protected void button2Action()
			{ dispose(); }
	}
	
	/** Generates the main pane of the dialog for saving mappings */
	private JPanel getMainPane()
	{
		// Initialize the mapping pane
		mappingPane = new MappingPane(true,harmonyModel);
		mappingPane.addListSelectionListener(this);

		//Initialize the info pane
		infoPane = new InfoPane(true, harmonyModel);
		infoPane.setInfo(mappingPane.getMapping());
		
		// Creates the main pane
		JPanel mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(0,0,4,0));
		mainPane.setLayout(new BorderLayout());
		mainPane.add(mappingPane,BorderLayout.WEST);
		mainPane.add(infoPane,BorderLayout.CENTER);
	    
		// Place list of roots in center of project pane
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(15,15,10,15));
		pane.setLayout(new BorderLayout());
		pane.add(mainPane,BorderLayout.CENTER);
		pane.add(new ButtonPane(),BorderLayout.SOUTH);
		return pane;
	}

	/** Constructs the dialog for saving mappings */
	public SaveMappingDialog(HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		this.harmonyModel = harmonyModel;
		setTitle("Save Mapping As");
		setModal(true);
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(getMainPane());
		pack();
		setLocationRelativeTo(harmonyModel.getBaseFrame());
		setVisible(true);
	}

	/** Handles a change to the selected mapping list */
	public void valueChanged(ListSelectionEvent e)
		{ infoPane.setInfo(mappingPane.getMapping()); }
}