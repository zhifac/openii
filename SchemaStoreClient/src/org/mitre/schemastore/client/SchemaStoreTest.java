package org.mitre.schemastore.client;

import org.mitre.schemastore.model.Schema;

public class SchemaStoreTest
{
	static public void main(String args[])
	{
		SchemaStoreClient client = new SchemaStoreClient("http://brainsrv2:8090/SchemaStore/services/SchemaStore");

		// Display remaining schemas
		try {
			for(Schema schema : client.getSchemas())
				System.out.println(schema.getId() + ": " + schema.getName());

//			Graph graph = client.getGraph(3032);
//			for(SchemaElement element : graph.getElements(null))
//				System.out.println(element.getName());
		}
		catch(Exception e) { e.printStackTrace(); }
	}
}
