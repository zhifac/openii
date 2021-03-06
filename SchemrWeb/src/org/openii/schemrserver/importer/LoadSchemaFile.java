package org.openii.schemrserver.importer;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.schemaImporters.DDLImporter;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;
import org.mitre.schemastore.porters.schemaImporters.XSDImporter;
import org.openii.schemrserver.SchemaUtility;

public class LoadSchemaFile{
	
	protected static SchemaStoreClient client = null;
	public  static  void setClient(){
		try {
			System.out.println("Getting new Client");
			if (client ==null) client = SchemaUtility.getCLIENT();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return;
	}

	public static void loadSchema(File file, String name, String author, String description) throws URISyntaxException, ImporterException {
		String url = "file:///" + file.getAbsolutePath().replace("\\", "/").replace(" ", "%20");
		SchemaImporter importer = null;
		// pick a loader
		if (file.getName().endsWith("xsd")) {
			importer = new XSDImporter();
		} else if (file.getName().endsWith("sql") || file.getName().endsWith("ddl")) {
			importer = new DDLImporter();
		}
		importer.setClient(SchemaUtility.getCLIENT());
		importer.importSchema(name, author, description, new URI(url));
		
	}
}
