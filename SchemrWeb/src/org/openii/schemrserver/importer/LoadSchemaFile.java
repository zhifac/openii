package org.openii.schemrserver.importer;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;

import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.schemaImporters.CSVImporter;
import org.mitre.schemastore.porters.schemaImporters.DDLImporter;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;
import org.mitre.schemastore.porters.schemaImporters.XSDImporter;


import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;

public class LoadSchemaFile{
	
	protected static SchemaStoreClient client = null;
	public  static  SchemaStoreClient setClient(){
		try {
			System.out.println("Getting new Client");
			if (client ==null) client = new SchemaStoreClient();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
	}

	public static void loadSchema(File file, String name, String author, String description) throws URISyntaxException, ImporterException {
		String url = "file:///" + file.getAbsolutePath().replace("\\", "/").replace(" ", "%20");
		SchemaImporter importer = null;
		// pick a loader
		if (file.getName().endsWith("xsd")) {
			importer = new XSDImporter();
		} else if(file.getName().endsWith("csv")){
			importer = new CSVImporter();
		}
		else if (file.getName().endsWith("sql") || file.getName().endsWith("ddl")) {
			importer = new DDLImporter();
		}
		importer.importSchema(name, author, description, new URI(url));
		
	}
}
