// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.exporters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.Exporter;
import org.mitre.schemastore.porters.PorterType;
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
	public PorterType getPorterType() { return PorterType.SCHEMA_EXPORTERS; }
	
	/** Retrieves the schema elements */
	private ArrayList<SchemaElement> getSchemaElements(HarmonyModel harmonyModel)
		{ return harmonyModel.getSchemaManager().getSchemaElements(schema.getId(), null); }

	/** Handles the export through a web service */
	public ArrayList<Object> getData(HarmonyModel harmonyModel)
	{
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(schema); data.add(getSchemaElements(harmonyModel));
		return data;
	}	
	
	/** Handles the export to the specified file */
	protected void export(HarmonyModel harmonyModel, Exporter exporter, File file) throws IOException
		{ ((SchemaExporter)exporter).exportSchema(schema, getSchemaElements(harmonyModel), file); }	
}