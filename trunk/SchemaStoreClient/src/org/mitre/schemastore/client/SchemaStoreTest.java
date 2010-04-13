package org.mitre.schemastore.client;

import java.net.URI;

import org.mitre.schemastore.model.Schema;

public class SchemaStoreTest
{
	static int size(String value)
		{ return value==null ? 0 : value.length(); }
	
	static public void main(String args[])
	{
		// Display the schemas found within the repository
		try {
			Repository repository = new Repository(Repository.DERBY,new URI("."),"schemastore","postgres","postgres");
//			Repository repository = new Repository(Repository.POSTGRES,new URI("platform-2"),"schemastore","postgres","postgres");
//			Repository repository = new Repository(Repository.SERVER,new URI("http://ygg:8080/SchemaStore/services/SchemaStore"),"","","");
			SchemaStoreClient client = new SchemaStoreClient(repository);
			for(Schema schema : client.getSchemas())
				System.out.println(schema.getId() + ": " + schema.getName());
		}
		catch(Exception e) { e.printStackTrace(); }
	}
}
