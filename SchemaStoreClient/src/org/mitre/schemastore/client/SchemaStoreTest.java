package org.mitre.schemastore.client;

import org.mitre.schemastore.graph.HierarchicalGraph;
import org.mitre.schemastore.model.Schema;

public class SchemaStoreTest
{
	static public void main(String args[])
	{
		SchemaStoreClient client = new SchemaStoreClient("http://brainsrv2:8090/SchemaStore/services/SchemaStore");

		// Display remaining schemas
		try {
			for(Schema schema : client.getSchemas())
				System.out.println(schema.getId() + " " + schema.getName());
			
			HierarchicalGraph graph = client.getSchemaElementGraph(107691);
			System.out.println(graph.toString());
		}
		catch(Exception e) { e.printStackTrace(); }
	}
}
