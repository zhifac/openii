package org.mitre.schemastore.client;

import java.io.File;

import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.mappingImporters.M3MappingImporter;

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

//			ProjectSchema schemas[] = null;
//			for(Project project : client.getProjects())
//			{
//				System.out.println(project.getId());
//				for(Mapping mapping : client.getMappings(project.getId()))
//				{
//					System.out.println(project.getId() + " - " + mapping.getId() + " - " + mapping.getSourceId() + " - " + mapping.getTargetId());
//					schemas = project.getSchemas();
//				}
//			}
		
			Project project = client.getProject(151990);
			
			PorterManager manager = new PorterManager(client);
//			M3MappingExporter exporter = (M3MappingExporter)manager.getPorter(M3MappingExporter.class);
//			exporter.exportMapping(client.getMapping(1027), client.getMappingCells(1027), new File("Test.m3m"));
			M3MappingImporter importer = (M3MappingImporter)manager.getPorter(M3MappingImporter.class);
			importer.initialize(new File("Test.m3m").toURI());
			importer.importMapping(project, 12, 13);
		}
		catch(Exception e) { e.printStackTrace(); }
	}
}
