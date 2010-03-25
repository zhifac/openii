// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.porters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.harmony.model.project.ProjectMapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.mappingExporters.MappingExporter;

/**
 * Dialog for exporting the selected mapping
 * @author CWOLF
 */
public class MappingExporterDialog
{	
	/** Private class for creating a mapping file filter */
	private static class MappingFileFilter extends FileFilter
	{
		/** Stores the mapping exporter */
		private MappingExporter exporter = null;
		
		/** Constructs the mapping file filter */
		private MappingFileFilter(MappingExporter exporter)
			{ this.exporter = exporter; }

		/** Indicates if the file should be accepted */
		public boolean accept(File file)
		{
			if(file.isDirectory()) return true;
			if(file.toString().endsWith(exporter.getFileType())) return true;
			return false;
		}

		/** Provides the exporter file description */
		public String getDescription()
			{ return exporter.getName() + "(" + exporter.getFileType() + ")"; }
	}
	
	/** Allows user to export a mapping */
	public static void exportMapping(HarmonyModel harmonyModel, ProjectMapping mapping)
	{
		// Initialize the file chooser
		JFileChooser chooser = new JFileChooser(harmonyModel.getPreferences().getExportDir());
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setAcceptAllFileFilterUsed(false);

		// Set up file filters for the various mapping exporters
		ArrayList<MappingExporter> exporters =  SchemaStoreManager.getPorters(PorterManager.MAPPING_EXPORTERS);
		for(MappingExporter exporter : exporters)
			chooser.addChoosableFileFilter(new MappingFileFilter(exporter));
		chooser.setFileFilter(chooser.getChoosableFileFilters()[0]);

		// Display the dialog for selecting which exporter to use
		if(chooser.showDialog(harmonyModel.getBaseFrame(),"Export")==JFileChooser.APPROVE_OPTION)
		{
			// Retrieve the selected file and exporter from the file chooser
			File file = chooser.getSelectedFile();
			harmonyModel.getPreferences().setExportDir(file.getParentFile());
			MappingExporter exporter = ((MappingFileFilter)chooser.getFileFilter()).exporter;

			// Ensure that file has the proper ending
			if(!file.getName().endsWith(exporter.getFileType()))
				file = new File(file.getPath()+exporter.getFileType());
			
			// Check to see if file already exists and checks to make sure it can be overwritten
			if(file!=null && file.exists())
    		{
	    		int option = JOptionPane.showConfirmDialog(harmonyModel.getBaseFrame(),
	        			file + " exists.  Overwrite?",
						"Overwrite File", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
	    		if(option==1) return;
    		}

			// Export the mapping to the specified file
			Project project = harmonyModel.getProjectManager().getProject();
			try { exporter.exportMapping(project, mapping, mapping.getMappingCells(), file); }
			catch(IOException e)
			{
				JOptionPane.showMessageDialog(harmonyModel.getBaseFrame(),
						"File " + file.toString() + " failed to be exported.\n"+e.getMessage(),
						"Export Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
