// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.porters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.Exporter;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.schemaExporters.SchemaExporter;

/**
 * Dialog for exporting the current schema
 * @author CWOLF
 */
public class ExportSchemaDialog extends AbstractExportDialog
{
	/** Stores the schema being exported */
	private Schema schema;
	
	/** Constructs the Schema Export dialog */
	public ExportSchemaDialog(Schema schema)
		{ this.schema = schema; }
	
	/** Declares the export type */
	protected int getExporterType() { return PorterManager.SCHEMA_EXPORTERS; }
	
	/** Handles the export to the specified file */
	protected void export(HarmonyModel harmonyModel, Exporter exporter, File file) throws IOException
	{
		ArrayList<SchemaElement> schemaElements = harmonyModel.getSchemaManager().getSchemaElements(schema.getId(), null);
		((SchemaExporter)exporter).exportSchema(schema, schemaElements, file);
	}
}