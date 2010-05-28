package org.mitre.openii.views.manager.mappings;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.porters.ExporterDialog;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
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
		
		// Create and launch the dialog
		ExporterDialog dialog = new ExporterDialog(shell,source.getName() + " to " + target.getName(),PorterType.MAPPING_EXPORTERS);
        String filename = dialog.open();
        
		// Launch the dialog to retrieve the specified file to save to
        if(filename != null)
        {        	
			try {
				MappingExporter exporter = dialog.getExporter();
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