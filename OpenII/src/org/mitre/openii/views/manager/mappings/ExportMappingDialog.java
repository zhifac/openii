package org.mitre.openii.views.manager.mappings;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.PorterManager.PorterType;
import org.mitre.schemastore.porters.mappingExporters.MappingExporter;

/** Constructs the Export Mapping Dialog class */
public class ExportMappingDialog
{
	/** Function for exporting the specified mapping */
	static public void export(Shell shell, Mapping mapping)
	{
		// Retrieve the schemas associated with the mapping
		Schema source = OpenIIManager.getSchema(mapping.getSourceId());
		Schema target = OpenIIManager.getSchema(mapping.getTargetId());
		
		// Create the dialog
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setText("Export Mapping");
		dialog.setFileName(source.getName() + " to " + target.getName());
		dialog.setFilterPath(OpenIIManager.getActiveDir());
		
		// Get the list of exporters available for use
		PorterManager manager = new PorterManager(RepositoryManager.getClient());
		ArrayList<MappingExporter> exporters =  manager.getPorters(PorterType.MAPPING_EXPORTERS);
		
		// Set up the filter names and extensions
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> extensions = new ArrayList<String>();
		for(MappingExporter exporter : exporters)
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
				OpenIIManager.setActiveDir(dialog.getFilterPath());
				MappingExporter exporter = exporters.get(dialog.getFilterIndex());
				Project project = OpenIIManager.getProject(mapping.getProjectId());
	        	exporter.exportMapping(project, mapping, OpenIIManager.getMappingCells(mapping.getId()), new File(filename));
			}
			catch(Exception e)
			{
				MessageBox message = new MessageBox(shell,SWT.ERROR);
				message.setText("Mapping Export Error");
				message.setMessage("Failed to export mapping. " + e.getMessage());
				message.open();
			}
        }
	}
}