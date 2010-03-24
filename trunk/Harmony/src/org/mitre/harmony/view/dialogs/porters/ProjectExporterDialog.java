// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.porters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.harmony.model.project.ProjectMapping;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.projectExporters.ProjectExporter;

/**
 * Dialog for exporting the current project
 * @author CWOLF
 */
public class ProjectExporterDialog
{	
	/** Private class for creating a project file filter */
	private static class ProjectFileFilter extends FileFilter
	{
		/** Stores the project exporter */
		private ProjectExporter exporter = null;
		
		/** Constructs the project file filter */
		private ProjectFileFilter(ProjectExporter exporter)
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
	
	/** Allows user to export a project */
	public static void exportProject(HarmonyModel harmonyModel)
	{
		// Initialize the file chooser
		JFileChooser chooser = new JFileChooser(harmonyModel.getPreferences().getExportDir());
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setAcceptAllFileFilterUsed(false);

		// Set up file filters for the various project exporters
		ArrayList<ProjectExporter> exporters = SchemaStoreManager.getPorters(PorterManager.PROJECT_EXPORTERS);
		for(ProjectExporter exporter : exporters)
			chooser.addChoosableFileFilter(new ProjectFileFilter(exporter));

		// Display the dialog for selecting which exporter to use
		if(chooser.showDialog(harmonyModel.getBaseFrame(),"Export")==JFileChooser.APPROVE_OPTION)
		{
			// Retrieve the selected file and exporter from the file chooser
			File file = chooser.getSelectedFile();
			harmonyModel.getPreferences().setExportDir(file.getParentFile());
			ProjectExporter exporter = ((ProjectFileFilter)chooser.getFileFilter()).exporter;

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
			
			// Gather up the project to export
			Project project = harmonyModel.getProjectManager().getProject();
			HashMap<Mapping,ArrayList<MappingCell>> mappings = new HashMap<Mapping,ArrayList<MappingCell>>();
			for(ProjectMapping mapping : harmonyModel.getMappingManager().getMappings())
				mappings.put(mapping, mapping.getMappingCells());

			// Export the project to the specified file
			try { exporter.exportProject(project,mappings, file); }
			catch(IOException e)
			{
				JOptionPane.showMessageDialog(harmonyModel.getBaseFrame(),
						"File " + file.toString() + " failed to be exported.\n"+e.getMessage(),
						"Export Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
