// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.harmony.view;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.net.URI;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.harmony.view.dialogs.importers.AbstractImportDialog;
import org.mitre.harmony.view.dialogs.importers.ImportMappingDialog;
import org.mitre.harmony.view.harmonyPane.HarmonyFrame;

/** Class for displaying the Harmony Applet */
public class HarmonyApplet extends Applet
{
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;

	/** Constructs the applet */
	public void init()
	{
		// Generate the Harmony pane
		harmonyModel = new HarmonyModel(this);
		JPanel harmonyPane = new JPanel();
		harmonyPane.setBorder(new CompoundBorder(new EmptyBorder(2,2,2,2),new LineBorder(Color.gray)));
		harmonyPane.setLayout(new BorderLayout());
		harmonyPane.add(new HarmonyFrame(harmonyModel),BorderLayout.CENTER);

		// Display Harmony in the frame
		setLayout(new BorderLayout());
		add(harmonyPane,BorderLayout.CENTER);

		// Connect to the remote schema store client
		SchemaStoreManager.init(this);
	}
	
	/** Handles the importation of a file */
	public void importFile(String filename, String location)
	{
		try {
			JInternalFrame dialog = harmonyModel.getDialogManager().getDialog();
			if(dialog instanceof AbstractImportDialog)
				((AbstractImportDialog)dialog).setURI(new URI(location), filename);
			else if(dialog instanceof ImportMappingDialog)
				((ImportMappingDialog)dialog).setURI(new URI(location), filename);
		}
		catch(Exception e) { System.out.println("(E)HarmonyApplet.importFile: " + e.getMessage()); }
	}
	
	/** Informs the applet that the import dialog has been closed */
	public void closeDialog()
	{
		harmonyModel.getDialogManager().unlockFrame();
	}
}