package org.mitre.schemastore.client;

import org.mitre.schemastore.model.Schema;

public class SchemaStoreTest
{
	static public void main(String args[])
	{
		// Display the schemas found within the repository
		try {
			SchemaStoreClient client = new SchemaStoreClient("http://brainsrv2:8090/SchemaStore/services/SchemaStore");
			for(Schema schema : client.getSchemas())
				System.out.println(schema.getId() + ": " + schema.getName());
		}
		catch(Exception e) { e.printStackTrace(); }
	}
}
