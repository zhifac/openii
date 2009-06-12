package org.mitre.schemastore.client;

import org.mitre.schemastore.model.Schema;

public class SchemaStoreTest
{
	static int size(String value)
		{ return value==null ? 0 : value.length(); }
	
	static public void main(String args[])
	{
		// Display the schemas found within the repository
		try {
			SchemaStoreClient client = new SchemaStoreClient("../SchemaStore/SchemaStore.jar");
//			SchemaStoreClient client = new SchemaStoreClient("http://ygg:8080/SchemaStore/services/SchemaStore");
			for(Schema schema : client.getSchemas())
				System.out.println(schema.getId() + ": " + schema.getName());
		}
		catch(Exception e) { e.printStackTrace(); }
	}
}
