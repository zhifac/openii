package org.openii.schemrserver.indexer;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElementList;

public class SchemrSchema {

	private Schema schema = null;

	private SchemaElementList schemaElementList = null;
	
	public SchemrSchema(Schema schema, SchemaElementList sel) {
		this.schema = schema;
		this.schemaElementList = sel;
	}

	public Schema getSchema() {
		return schema;
	}
	
	public SchemaElementList getSchemaElementList() {
		return schemaElementList;
	}
	
	
}
