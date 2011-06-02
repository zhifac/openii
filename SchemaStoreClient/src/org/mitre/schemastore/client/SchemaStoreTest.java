package org.mitre.schemastore.client;

import java.io.File;
import java.util.ArrayList;

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

			ArrayList<Annotation> annotations = new ArrayList<Annotation>();
			annotations.add(new Annotation(14, 20, "test", "test3"));
			annotations.add(new Annotation(15, 20, "test2", "test4"));
			annotations.add(new Annotation(16, 20, "test3", "test5"));
			client.setAnnotations(annotations);
			
			System.out.println("A: " + client.getAnnotation(12, null, "test"));
			System.out.println("B: " + client.getAnnotation(13, null, "test"));
			System.out.println("C: " + client.getAnnotation(13, 20, "test"));
			for(Annotation annotation : client.getAnnotations(20, "test"))
				System.out.println(annotation.getValue());
			
		} catch(Exception e) { e.printStackTrace(); }
	}
}
