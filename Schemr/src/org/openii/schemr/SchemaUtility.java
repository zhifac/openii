package org.openii.schemr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Properties;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.SchemaElementList;

import com.thoughtworks.xstream.XStream;

public class SchemaUtility {

	
	public static String DEFAULT_ROOT = null;
	
	static {
		File localPropsFile = new File("local.properties");
		if (localPropsFile.exists()) {
			Properties p = new Properties();
			try {
				p.load(new FileInputStream(localPropsFile));
				SchemaUtility.DEFAULT_ROOT = p.getProperty("local_working_dir");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				System.out.println("DEFAULT_ROOT = "+DEFAULT_ROOT);
			}
		}
	}

	static SchemaStoreClient CLIENT = new SchemaStoreClient("http://localhost:8080/SchemaStore/services/SchemaStore");		

	static XStream XSTREAM = new XStream();

	public static ArrayList<Schema> getAllSchemas(SchemaStoreClient client) {
		ArrayList<Schema> schemas = null;
		try {
			schemas = client.getSchemas();
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
		
		return schemas;
	}

	public static SchemaElementList getSchemaElementList(SchemaStoreClient client, Integer schemaID) {

		try {
			return new SchemaElementList(client.getSchemaElements(schemaID).toArray(new SchemaElement[0]));
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	public static void serializeSchema(Schema schema, SchemaElementList elementList, OutputStream out) {		
		XSTREAM.toXML(new SchemaXml(schema, elementList), out);
	}

	public static SchemaXml deserializeSchema(InputStream is) {
		return (SchemaXml) XSTREAM.fromXML(is);
	}

}
