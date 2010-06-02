package org.mitre.openii.dialogs.schemas;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Tag;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.schemaExporters.M3SchemaExporter;

/** Constructs the Export Schemas Dialog class */
public class ExportSchemasDialog
{
	/** Function for exporting the schema under the specified tag */
	static public void export(Shell shell, Tag tag)
	{
		// Retrieve the folder where to export schemas
		DirectoryDialog dialog = new DirectoryDialog(shell);
		dialog.setFilterPath(OpenIIManager.getActiveDir());
		dialog.setText("Export Schemas");
		String filename = dialog.open();
		OpenIIManager.setActiveDir(dialog.getFilterPath());
		File directory = new File(filename);
		
		// Export the schemas
		for(Integer schemaID : OpenIIManager.getTagSchemas(tag.getId()))
		{
			// Get the schema and schema elements
			Schema schema = OpenIIManager.getSchema(schemaID);
			String schemaName = schema.getName().replace("/", "_");
			ArrayList<SchemaElement> elements = OpenIIManager.getSchemaInfo(schemaID).getElements(null);
			
			// Find a unique file name
			int i=0;
			File file = new File(directory,schemaName+".m3s");
			while(file.exists()) file = new File(directory,schemaName+"("+(++i)+").m3s");

			// Export the schema
			try {
				PorterManager manager = new PorterManager(RepositoryManager.getClient());
				M3SchemaExporter exporter = (M3SchemaExporter)manager.getPorter(M3SchemaExporter.class);
				exporter.exportSchema(schema,elements,file);
			}
			catch(Exception e)
			{
				MessageBox message = new MessageBox(shell,SWT.ERROR);
				message.setText("Schema Export Error");
				message.setMessage("Failed to export schema " + schema.getName() + ". " + e.getMessage());
				message.open();
			}
		}
	}
}