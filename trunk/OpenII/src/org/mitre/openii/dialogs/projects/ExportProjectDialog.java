package org.mitre.openii.dialogs.projects;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.porters.ExporterDialog;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.porters.PorterManager.PorterType;
import org.mitre.schemastore.porters.projectExporters.ProjectExporter;

/** Constructs the Export Project Dialog class */
public class ExportProjectDialog
{
	/** Function for exporting the specified project */
	static public void export(Shell shell, Project project)
	{
		// Create and launch the dialog
		ExporterDialog dialog = new ExporterDialog(shell,project.getName(),PorterType.PROJECT_EXPORTERS);
        String filename = dialog.open();
        
		// Launch the dialog to retrieve the specified file to save to
        if(filename != null)
        {        	
			try {
				// Retrieve the project mappings
				HashMap<Mapping,ArrayList<MappingCell>> mappings = new HashMap<Mapping,ArrayList<MappingCell>>();
				for(Mapping mapping : OpenIIManager.getMappings(project.getId()))
					mappings.put(mapping, OpenIIManager.getMappingCells(mapping.getId()));
				
				// Export the project
				ProjectExporter exporter = dialog.getExporter();				
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