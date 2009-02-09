// � The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.exporters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.mitre.harmony.model.ToolManager;
import org.mitre.harmony.model.preferences.Preferences;
import org.mitre.harmony.view.harmonyPane.HarmonyFrame;

/**
 * Manages the Harmony Exporters
 * @author CWOLF
 */
public class ExporterManager
{
	/** Stores a listing of all mapping exporters */
	static private ArrayList<Exporter> exporters = new ArrayList<Exporter>();
	
	/** Initializes the export manager with all defined exporters */
	static
	{
		// Retrieve the exporters
		for(String exporterString : ToolManager.getTools("exporter"))
			try {
				Class exporterClass = Class.forName(exporterString);
				exporters.add((Exporter)exporterClass.newInstance());
			}
		    catch(Exception e) { System.out.println("(E)ExporterManager - Failed to locate exporter class "+exporterString); }
	}
	
	/** Allows user to export a project */
	public static void exportMapping()
	{
		// Ask for location to export project
		JFileChooser chooser = new JFileChooser(Preferences.getExportDir());
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setAcceptAllFileFilterUsed(false);
		for(Exporter exporter : exporters)
			chooser.addChoosableFileFilter(exporter.getFileFilter());
		if(chooser.showDialog(HarmonyFrame.harmonyFrame,"Export")==JFileChooser.APPROVE_OPTION)
		{
			// Retrieve the selected file from the file chooser
			File file = chooser.getSelectedFile();
			Preferences.setExportDir(file.getParentFile());

			// Retrieves the selected converter from the file chooser
			Exporter exporter = null;
			for(int i=0; i<chooser.getChoosableFileFilters().length; i++)
				if(chooser.getFileFilter()==chooser.getChoosableFileFilters()[i])
					exporter = exporters.get(i);

			// Ensure that file has the proper ending
			if(!file.getName().endsWith("."+exporter.getFileType()))
				file = new File(file.getPath()+"."+exporter.getFileType());
			
			// Check to see if file already exists and checks to make sure it can be overwritten
			if(file!=null && file.exists())
    		{
	    		int option = JOptionPane.showConfirmDialog(HarmonyFrame.harmonyFrame,
	        			file + " exists.  Overwrite?",
						"Overwrite File", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
	    		if(option==1) return;
    		}
			
			// Export the project to the specified file
			try { exporter.exportTo(file); }
			catch(IOException e)
			{
				JOptionPane.showMessageDialog(HarmonyFrame.harmonyFrame,
						"File " + file.toString() + " failed to be exported.\n"+e.getMessage(),
						"Export Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
