package org.mitre.schemastore.client;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

public class SchemaStoreTest
{
	static int size(String value)
		{ return value==null ? 0 : value.length(); }
	
	static private String getKey(SchemaElement element)
		{ return element.getName() + " " + element.getDescription(); }
	
	static public void main(String args[])
	{
		// Display the schemas found within the repository
		try {
//			Repository repository = new Repository(Repository.DERBY,new URI("."),"schemastore","postgres","postgres");
			Repository repository = new Repository(Repository.POSTGRES,new URI("platform-2"),"schemastore","postgres","postgres");
//			Repository repository = new Repository(Repository.SERVER,new URI("http://ygg:8080/SchemaStore/services/SchemaStore"),"","","");
			SchemaStoreClient client = new SchemaStoreClient(repository);

			for(Integer schemaID : client.getTagSchemas(1250123))
			{				
				SchemaInfo schema = client.getSchemaInfo(schemaID);
				
				// Identify all large sets of duplicates
				HashMap<String,Integer> matches = new HashMap<String,Integer>();
				for(SchemaElement element : schema.getElements(Attribute.class))
				{
					String key = getKey(element);
					Integer count = matches.get(key);
					matches.put(key, count==null ? 1 : count+1);
				}
				
				// Pull out duplicated attributes
				HashSet<Integer> domainIDs = new HashSet<Integer>();
				for(SchemaElement element : schema.getElements(Attribute.class))
				{
					String key = getKey(element);
					if(matches.get(key)>10)
					{
						if(((Attribute)element).getDomainID()>0)
							domainIDs.add(((Attribute)element).getDomainID());
						schema.deleteElement(element.getId());
					}
				}
				
				// Pull out unused domains
				for(Integer domainID : domainIDs)
				{
					for(DomainValue domainValue : schema.getDomainValuesForDomain(domainID))
						schema.deleteElement(domainValue.getId());
					schema.deleteElement(domainID);
				}
				
				// Store the newly generated schema
				schema.getSchema().setName(schema.getSchema().getName()+"(C)");
				client.importSchema(schema.getSchema(), schema.getElements(null));
				
				System.out.println(schema.getSchema().getName() + " done!");
			}
		}
		catch(Exception e) { e.printStackTrace(); }
	}
}
