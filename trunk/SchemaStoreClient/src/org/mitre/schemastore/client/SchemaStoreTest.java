package org.mitre.schemastore.client;

import java.io.File;

import org.mitre.schemastore.model.Annotation;

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

			client.setAnnotation(12, null, "test", "test");
			client.setAnnotation(13, 20, "test", "test2");
			client.setAnnotation(14, 20, "test", "test3");

			for(Annotation annotation : client.getAnnotations(20, "test"))
				System.out.println(annotation.getValue());
			
		} catch(Exception e) { e.printStackTrace(); }
	}
}
