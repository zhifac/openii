package org.mitre.schemastore.client;

import org.mitre.schemastore.model.Schema;

public class SchemaStoreTest
{
	static public void main(String args[])
	{
		// Display the schemas found within the repository
		try {
			SchemaStoreClient client = new SchemaStoreClient("C:\\chris\\projects\\Flexidata\\SchemaStore\\SchemaStore.jar");
			
			for(Schema schema : client.getSchemas())
				System.out.println(schema.getId() + ": " + schema.getName());

			for(Integer schemaID : client.getDeletableSchemas())
				System.out.println(schemaID);

//			for(SchemaElement element : client.getGraph(52525).getElements(null))
//				if(element.getDescription()!=null && !element.getDescription().equals(""))
//					System.out.println(element.getId() + ": " + element.getName());
			
//			FilteredGraph graph = new FilteredGraph(new HierarchicalGraph(client.getGraph(177492)));
//			graph.setFilteredRoot(177716);
//			graph.setMinDepth(3);
//			graph.setMaxDepth(4);
//			for(SchemaElement element : graph.getFilteredElements())
//				System.out.println(element.getName());
		}
		catch(Exception e) { e.printStackTrace(); }
	}
}
