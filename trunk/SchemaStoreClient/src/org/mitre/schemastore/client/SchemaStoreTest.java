package org.mitre.schemastore.client;

import java.io.File;
import java.util.ArrayList;

import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Synonym;

public class SchemaStoreTest
{
	static public void main(String args[])
	{
		// Display the schemas found within the repository
		try {
			Repository repository = new Repository(Repository.DERBY,new File(".").toURI(),"testStore","postgres","postgres");
//			Repository repository = new Repository(Repository.POSTGRES,new URI("platform-2"),"schemastore","postgres","postgres");
//			Repository repository = new Repository(Repository.SERVICE,new URI("http://ygg:8080/D3-develop/services/SchemaStore"),"","","");
			SchemaStoreClient client = new SchemaStoreClient(repository);

			Schema schema = new Schema(0,"test","cwolf","test","test","test",false);
			ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
			elements.add(new Entity(1,"term1","",0));
			elements.add(new Synonym(2,"synonym1","",1,0));
			client.importSchema(schema, elements);
		} catch(Exception e) { e.printStackTrace(); }
	}
}
