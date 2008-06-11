package org.mitre.schemastore.client;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

public class SchemaStoreTest
{
	static public void main(String args[])
	{
		SchemaStoreClient client = new SchemaStoreClient("http://localhost:8080/SchemaStore/services/SchemaStore");

		// Display remaining schemas
		try {
			for(Schema schema : client.getSchemas())
				System.out.println(schema.getId() + " " + schema.getName());
			for(SchemaElement schemaElement : client.getSchemaElements(0))
				System.out.println(schemaElement.getId() + " " + schemaElement.getName());
		} catch(Exception e) {}
	}
}
