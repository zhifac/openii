// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.harmony.view.dialogs.exporters.AbstractExportDialog;
import org.mitre.harmony.view.dialogs.importers.AbstractImportDialog;
import org.mitre.schemastore.porters.Exporter;
import org.mitre.schemastore.porters.PorterType;

/** Dialog for selecting remote porters to use for via web service */
public class RemotePorterDialog extends JDialog
{
	/** Stores the porter dialog */
	private Object porterDialog = null;
	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel = null;
	
	/** Stores the selected porter */
	private JComboBox porterList = null;
	
	/** Returns the porter type associated with the dialog */
	private PorterType getPorterType()
	{
		if(porterDialog instanceof AbstractImportDialog)
			return ((AbstractImportDialog)porterDialog).getPorterType();
		if(porterDialog instanceof AbstractExportDialog)
			return ((AbstractExportDialog)porterDialog).getPorterType();
		return null;
	}
	
	/** Returns the dialog title */
	private String getDialogTitle()
	{
		PorterType porterType = getPorterType();
		if(porterType==PorterType.SCHEMA_IMPORTERS) return "Import Schema";
		if(porterType==PorterType.PROJECT_IMPORTERS) return "Import Project";
		if(porterType==PorterType.MAPPING_IMPORTERS) return "Import Mapping";
		if(porterType==PorterType.SCHEMA_EXPORTERS) return "Export Schema";
		if(porterType==PorterType.PROJECT_EXPORTERS) return "Export Project";
		if(porterType==PorterType.MAPPING_EXPORTERS) return "Export Mapping";
		return null;
	}
	
	/** Initializes the web service porter dialog */
	public RemotePorterDialog(Object porterDialog, HarmonyModel harmonyModel, Window parent)
	{
		super(parent);
		this.porterDialog = porterDialog;
		this.harmonyModel = harmonyModel;
		
		// Initialize the porter list
		porterList = new JComboBox(new Vector<Object>(SchemaStoreManager.getPorters(getPorterType())));
		porterList.setBackground(Color.white);
		porterList.setFocusable(false);
		porterList.setSelectedIndex(0);			
		
		// Create the info pane
		JPanel infoPane = new JPanel();
		infoPane.setBorder(new CompoundBorder(new EmptyBorder(5,5,0,5),new CompoundBorder(new LineBorder(Color.gray),new EmptyBorder(8,8,8,8))));
		infoPane.setLayout(new GridLayout(2,1));
		infoPane.add(new JLabel("Select an " + (porterDialog instanceof AbstractImportDialog ? "exporter" : "importer") + ":"));
		infoPane.add(porterList);
		
		// Generate the main dialog pane
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createLineBorder(Color.black));
		pane.setLayout(new BorderLayout());
		pane.add(infoPane,BorderLayout.CENTER);
		pane.add(new ButtonPane(),BorderLayout.SOUTH);
		
		// Initialize the dialog parameters
		setTitle(getDialogTitle());
		setModal(true);
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(pane);
		setSize(200,150);
		setResizable(false);
		setLocationRelativeTo(parent);
		pack();
		setVisible(true);
	}
	
	/** Private class for defining the button pane */
	private class ButtonPane extends AbstractButtonPane
	{
		/** Constructs the button pane */
		private ButtonPane()
			{ super(new String[]{"OK","Cancel"},1,2); }

		/** Handles selection of button */
		protected void buttonPressed(String label)
		{
			if(label.equals("OK"))
			{
				// Handles importers
				if(porterDialog instanceof AbstractImportDialog)
				{
					
				}
				
				// Handles exporters
				else if(porterDialog instanceof AbstractExportDialog)
					try
					{
						// Export the file
						Exporter exporter = (Exporter)porterList.getSelectedItem();
						ArrayList<Object> data = ((AbstractExportDialog)porterDialog).getData(harmonyModel);
						String filename = SchemaStoreManager.exportData(getPorterType(), exporter.getName(), data);

						// Trigger javascript to allow the user to save the file
						if(filename!=null)
							harmonyModel.getApplet().getAppletContext().showDocument(new URL("javascript:exportFile(\""+filename+"\")"));
					}
					catch(MalformedURLException me) {}
			}
			dispose();
		}
	}
}