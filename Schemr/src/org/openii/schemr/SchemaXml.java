package org.openii.schemr;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElementList;

public class SchemaXml {

	private Schema schema = null;

	private SchemaElementList schemaElementList = null;
	
	public SchemaXml(Schema schema, SchemaElementList sel) {
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
