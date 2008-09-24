package org.mitre.schemastore.client;

import org.mitre.schemastore.model.Schema;

public class SchemaStoreTest
{
	static public void main(String args[])
	{
		SchemaStoreClient client = new SchemaStoreClient("http://localhost:8080/SchemaStore2/services/SchemaStore");

		// Display remaining schemas
		try {
			for(Schema schema : client.getSchemas())
				System.out.println(schema.getId() + ": " + schema.getName());
		}
		catch(Exception e) { e.printStackTrace(); }
	}
}
