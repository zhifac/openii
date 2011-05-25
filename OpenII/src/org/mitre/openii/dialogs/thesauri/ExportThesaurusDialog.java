package org.mitre.openii.dialogs.thesauri;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.porters.ExporterDialog;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.porters.PorterType;
import org.mitre.schemastore.porters.schemaExporters.SchemaExporter;

/** Constructs the Export Schema Dialog class */
public class ExportThesaurusDialog
{
	/** Function for exporting the specified schema */
	static public void export(Shell shell, Schema schema)
	{
		// Create and launch the dialog
		ExporterDialog dialog = new ExporterDialog(shell,schema.getName(),PorterType.THESAURUS_EXPORTERS);
        String filename = dialog.open();
        
		// Export the file as specified
        if(filename != null)
        {        	
			try {
				// Generate the export text
	        	SchemaExporter exporter = dialog.getExporter();
	        	SchemaInfo schemaInfo = OpenIIManager.getSchemaInfo(schema.getId());
	        	exporter.exportSchema(schema, schemaInfo.getElements(null), new File(filename));
			}
			catch(Exception e)
			{
				MessageBox message = new MessageBox(shell,SWT.ERROR);
				message.setText("Thesaurus Export Error");
				message.setMessage("Failed to export thesaurus. " + e.getMessage());
				message.open();
			}
        }
	}
}