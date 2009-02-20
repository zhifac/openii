package org.mitre.openii.views.ygg.dialogs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.exporters.Exporter;
import org.mitre.schemastore.exporters.ExporterManager;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.graph.Graph;

/** Constructs the Export Schema Dialog class */
public class ExportSchemaDialog
{
	/** Function for exporting the specified schema */
	static public void export(Shell shell, Schema schema)
	{
		// Create the dialog
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setText("Export Schema");
		dialog.setFilterPath("C:/");
		
		// Get the list of exporters available for use
		ExporterManager manager = new ExporterManager(OpenIIManager.getConnection());
		ArrayList<Exporter> exporters = manager.getExporters(null);
		
		// Set up the filter names and extensions
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> extensions = new ArrayList<String>();
		for(Exporter exporter : exporters)
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
	        	// Generate the export text
	        	Exporter exporter = exporters.get(dialog.getFilterIndex());
	        	Graph graph = OpenIIManager.getGraph(schema.getId());
	        	StringBuffer buffer = exporter.exportSchema(schema.getId(), graph.getElements(null));

	        	// Export the schema to file
				BufferedWriter out = new BufferedWriter(new FileWriter(new File(filename)));
				out.write(buffer.toString());
				out.close();
			}
			catch(Exception e2)
			{
				MessageBox message = new MessageBox(shell,SWT.ERROR);
				message.setText("Export Error");
				message.setMessage("Unable to export to specified file!");
				message.open();
			}
        }
	}
}