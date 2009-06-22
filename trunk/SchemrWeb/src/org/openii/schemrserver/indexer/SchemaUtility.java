package org.openii.schemrserver.indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.rmi.RemoteException;
import java.util.Properties;

import org.mitre.schemastore.client.Repository;
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
	
//	public static String DEFAULT_ROOT = null;

	public static final String LOCAL_DATA_DIR = "C:/eclipse_workspaces/openii/schemr_data";
	public static final String LOCAL_INDEX_DIR = LOCAL_DATA_DIR + "/lucene_index";
	public static final String DERBY_DIR = LOCAL_DATA_DIR + "/derby_data";
	
	private static SchemaStoreClient CLIENT;		

	public static SchemaStoreClient getCLIENT() {
		if (CLIENT == null) {
			try {
//				CLIENT = new SchemaStoreClient(
//						new Repository(Repository.SERVICE,
//								new URI("http://localhost:8080/SchemaStore/services/SchemaStore"),
//								"", "", ""));
//				CLIENT = new SchemaStoreClient(
//						new Repository(Repository.POSTGRES,
//								new URI("localhost"),
//								"schemastore", "postgres", "postgres"));
				URI db = new URI(DERBY_DIR);
//				System.out.println("Database path: " + System.getProperty("user.dir"));
//				db = db.resolve("file:///C:/eclipse_workspaces/openii/derby_data/");
				CLIENT = new SchemaStoreClient(
					new Repository(Repository.DERBY, db,
							"schemastore", "", ""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return CLIENT;
	}

	static XStream XSTREAM = new XStream();

	public static void serializeSchema(Schema schema, SchemaElementList elementList, OutputStream out) {		
		XSTREAM.toXML(new SchemrSchema(schema, elementList), out);
	}

	public static SchemrSchema deserializeSchema(InputStream is) {
		return (SchemrSchema) XSTREAM.fromXML(is);
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

//	public static final String SCHEMA_STORE_URL = "http://localhost:8080/SchemaStore/services/SchemaStore";
	
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
