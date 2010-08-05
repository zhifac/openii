package org.mitre.schemastore.client;

import java.io.File;

import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.Vocabulary;

public class SchemaStoreTest
{
	static public void main(String args[])
	{
		// Display the schemas found within the repository
		try {
			Repository repository = new Repository(Repository.DERBY,new File(".").toURI(),"schemastore","postgres","postgres");
//			Repository repository = new Repository(Repository.POSTGRES,new URI("platform-2"),"schemastore","postgres","postgres");
//			Repository repository = new Repository(Repository.SERVICE,new URI("http://ygg:8080/D3-develop/services/SchemaStore"),"","","");
			SchemaStoreClient client = new SchemaStoreClient(repository);

			for ( Project p : client.getProjects() )
			{
				System.out.println( p.getName() ); 
				Vocabulary v = client.getVocabulary(p.getId());
				if(v==null) v = new Vocabulary(p.getId());
				v.addTerm(new Term(null,"new term", "new term description"));
				client.saveVocabulary(v); 
				System.out.println ( "vocab size: " + v.getTerms().length );
			}
		} catch(Exception e) { e.printStackTrace(); }
	}
}
