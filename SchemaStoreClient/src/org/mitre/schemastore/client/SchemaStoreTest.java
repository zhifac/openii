package org.mitre.schemastore.client;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import java.util.ArrayList;

public class SchemaStoreTest
{
	static public void main(String args[])
	{
		SchemaStoreClient client = new SchemaStoreClient("http://localhost:8080/SchemaStore/services/SchemaStore");

		// Display remaining schemas
		try {
		  Schema s = client.getSchema(new Integer(10));
		  System.out.println(s.toString());
		  
		  ArrayList<Schema> schemas = client.getSchemas();
          System.out.println("getSchemas() did not choke; # schemas: "+schemas.size());
		  for(Schema schema : schemas)
              System.out.println(schema.getId() + " " + schema.getName());
			for(SchemaElement schemaElement : client.getSchemaElements(0))
				System.out.println(schemaElement.getId() + " " + schemaElement.getName());
		} catch(Exception e) {
		  e.printStackTrace();
		}
	}
}
