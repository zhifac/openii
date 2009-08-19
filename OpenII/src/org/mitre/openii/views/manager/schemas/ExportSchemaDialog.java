package org.mitre.openii.views.manager.schemas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.graph.Graph;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.schemaExporters.SchemaExporter;

/** Constructs the Export Schema Dialog class */
public class ExportSchemaDialog
{
	/** Function for exporting the specified schema */
	static public void export(Shell shell, Schema schema)
	{
		// Create the dialog
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setText("Export Schema");
		dialog.setFileName(schema.getName());
		dialog.setFilterPath(OpenIIManager.getActiveDir());
		
		// Get the list of exporters available for use
		PorterManager manager = new PorterManager(RepositoryManager.getClient());
		ArrayList<SchemaExporter> exporters = manager.getSchemaExporters();
		
		// Set up the filter names and extensions
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> extensions = new ArrayList<String>();
		for(SchemaExporter exporter : exporters)
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
				// Set the active directory
				OpenIIManager.setActiveDir(dialog.getFilterPath());
				
				// Generate the export text
	        	SchemaExporter exporter = exporters.get(dialog.getFilterIndex());
	        	Graph graph = OpenIIManager.getGraph(schema.getId());
	        	StringBuffer buffer = exporter.exportSchema(schema.getId(), graph.getElements(null));

	        	// Export the schema to file
				BufferedWriter out = new BufferedWriter(new FileWriter(new File(filename)));
				out.write(buffer.toString());
				out.close();
			}
			catch(Exception e)
			{
				MessageBox message = new MessageBox(shell,SWT.ERROR);
				message.setText("Schema Export Error");
				message.setMessage("Failed to export schema. " + e.getMessage());
				message.open();
			}
        }
	}
}