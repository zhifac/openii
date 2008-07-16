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
import java.util.logging.Logger;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.SchemaElementList;
import org.mitre.schemastore.model.Subtype;

import com.thoughtworks.xstream.XStream;

public class SchemaUtility {

	private static final Logger logger = Logger.getLogger("org.openii.schemr.SchemaUtility");
	static {
//	    Handler fh = new FileHandler("%t/wombat.log");
//	    Logger.getLogger("").addHandler(fh);
	}
	
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
	
	public static final String ENTITY = "entity";
	public static final String ATTRIBUTE = "attribute";
	public static final String DOMAIN = "domain";
	public static final String DOMAIN_VALUE = "domain_value";
	public static final String RELATIONSHIP = "relationship";
	public static final String CONTAINMENT = "containment";
	public static final String SUBTYPE = "subtype";
	public static final String ALIAS = "alias";
	public static final String SCHEMA = "schema";
	
	public static String getType(SchemaElement schemaElement) {
		if (schemaElement instanceof Entity) return ENTITY;
		else if (schemaElement instanceof Attribute) return ATTRIBUTE;
		else if (schemaElement instanceof Domain) return DOMAIN;
		else if (schemaElement instanceof DomainValue) return DOMAIN_VALUE;
		else if (schemaElement instanceof Relationship) return RELATIONSHIP;
		else if (schemaElement instanceof Containment) return CONTAINMENT;
		else if (schemaElement instanceof Subtype) return SUBTYPE;
		else if (schemaElement instanceof Alias) return ALIAS;
		else throw new IllegalArgumentException("Unexpected SchemaElement type");
	}

}
