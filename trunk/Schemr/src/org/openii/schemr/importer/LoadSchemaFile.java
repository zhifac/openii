package org.openii.schemr.importer;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.mitre.flexidata.ygg.importers.DDLImporter;
import org.mitre.flexidata.ygg.importers.Importer;
import org.mitre.flexidata.ygg.importers.ImporterException;
import org.mitre.flexidata.ygg.importers.XSDImporter;
import org.mitre.schemastore.client.SchemaStoreClient;

public class LoadSchemaFile {

	protected static SchemaStoreClient client = new SchemaStoreClient("http://localhost:8080/SchemaStore/services/SchemaStore");


	public static void loadSchema(File file, String name, String author, String description) throws ImporterException, URISyntaxException {
		String url = "file:///" + file.getAbsolutePath().replace("\\", "/").replace(" ", "%20");

		Importer importer = null;
		// pick a loader
		if (file.getName().endsWith("xsd")) {
			importer = new XSDImporter();
		} else if (file.getName().endsWith("sql")) {
			importer = new DDLImporter();
		}

		importer.importSchema(name, author, description, new URI(url));
		
	}
}
