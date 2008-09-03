package org.mitre.schemastore.client;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

public class SchemaStoreTest
{
	static public void main(String args[])
	{
		SchemaStoreClient client = new SchemaStoreClient("http://brainsrv2:8090/SchemaStore/services/SchemaStore");

		// Display remaining schemas
//		try {
//			for(Schema schema : client.getSchemas())
//				System.out.println(schema.getId() + " " + schema.getName());
//			for(SchemaElement schemaElement : client.getSchemaElements(0))
//				System.out.println(schemaElement.getId() + " " + schemaElement.getName());
//		} catch(Exception e) { System.out.println(e.getMessage());}

		//test getSynonyms
		try {
			for (String synset: client.getSynonyms(52527))
				{
				System.out.println(synset);
			}
		} catch(Exception e) { System.out.println(e.getMessage());}

//		//test addWordsAndSynonymsForAllSchemas
//		try {
//			client.addWordsAndSynonymsForAllSchemas()
//			}
//		} catch(Exception e) { System.out.println(e.getMessage());}
//		
			for(Schema schema : client.getSchemas())
				System.out.println(schema.getId() + " " + schema.getName());
			
			HierarchicalGraph graph = client.getSchemaElementGraph(107691);
			System.out.println(graph.toString());
		}
		catch(Exception e) { e.printStackTrace(); }
	}
}
