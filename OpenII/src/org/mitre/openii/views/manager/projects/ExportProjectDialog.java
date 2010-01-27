package org.mitre.openii.views.manager.projects;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.projectExporters.ProjectExporter;

/** Constructs the Export Project Dialog class */
public class ExportProjectDialog
{
	/** Function for exporting the specified project */
	static public void export(Shell shell, Project project)
	{
		// Create the dialog
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setText("Export Project");
		dialog.setFileName(project.getName());
		dialog.setFilterPath(OpenIIManager.getActiveDir());
		
		// Get the list of exporters available for use
		PorterManager manager = new PorterManager(RepositoryManager.getClient());
		ArrayList<ProjectExporter> exporters = manager.getPorters(PorterManager.PROJECT_EXPORTERS);
		
		// Set up the filter names and extensions
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> extensions = new ArrayList<String>();
		for(ProjectExporter exporter : exporters)
		{
			names.add(exporter.getName() + " (" + exporter.getFileType() + ")");
			extensions.add("*"+exporter.getFileType());
		}
		dialog.setFilterNames(names.toArray(new String[0]));
		dialog.setFilterExtensions(extensions.toArray(new String[0]));
        
		// Launch the dialog to retrieve the specified file to save to
        String filename = dialog.open();
        if(filename != null)
        {        	
			try {
				// Retrieve the project mappings
				HashMap<Mapping,ArrayList<MappingCell>> mappings = new HashMap<Mapping,ArrayList<MappingCell>>();
				for(Mapping mapping : OpenIIManager.getMappings(project.getId()))
					mappings.put(mapping, OpenIIManager.getMappingCells(mapping.getId()));
				
				// Export the project
				OpenIIManager.setActiveDir(dialog.getFilterPath());
				ProjectExporter exporter = exporters.get(dialog.getFilterIndex());				
	        	exporter.exportProject(project, mappings, new File(filename));
			}
			catch(Exception e)
			{
				MessageBox message = new MessageBox(shell,SWT.ERROR);
				message.setText("Project Export Error");
				message.setMessage("Failed to export project. " + e.getMessage());
				message.open();
			}
        }
	}
}