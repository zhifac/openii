package org.openii.schemr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;

public class SchemaUtility {
	
	public static String DEFAULT_ROOT = System.getProperty("user.home");
	public static String SCHEMA_STORE_URL = "http://brainsrv2.mitre.org:8090/SchemaStore/services/SchemaStore";
	private static SchemaStoreClient CLIENT = null;

	private static String CONFIG_FILE_PATH = "local.properties";
	static {
		File localPropsFile = new File(CONFIG_FILE_PATH);
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(localPropsFile));
			String dir = p.getProperty("local_working_dir");
			if (dir != null) {
				DEFAULT_ROOT = dir;
			}
			String server = p.getProperty("schema_store_server");
			if (server != null) {
				SCHEMA_STORE_URL = server;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Configuration file not found, using defaults: "+CONFIG_FILE_PATH);
		} catch (IOException e) {
			System.out.println("Configuration file access error, using defaults: "+CONFIG_FILE_PATH);
		} finally {
			System.out.println("DEFAULT_ROOT = "+DEFAULT_ROOT);
			System.out.println("SCHEMA STORE SERVER initialized to "+SCHEMA_STORE_URL);
		}
	}

	public static SchemaStoreClient getCLIENT() {
		if (CLIENT == null) {
			CLIENT = new SchemaStoreClient(SCHEMA_STORE_URL);
		}
		return CLIENT;
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
