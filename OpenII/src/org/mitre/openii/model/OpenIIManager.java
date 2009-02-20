package org.mitre.openii.model;

import java.io.File;
import java.util.ArrayList;

import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.graph.Graph;

/**
 * Generates the schema store connection for use by all components
 * @author CWOLF
 */
public class OpenIIManager
{
	/** Stores the client used for accessing the database */
	static private SchemaStoreClient client = null;
	static
	{
		try {
			File file = new File(OpenIIActivator.getBundleFile(),"SchemaStore.jar");
			if(!file.exists()) file = new File(OpenIIActivator.getBundleFile(),"lib/SchemaStore.jar");
			client = new SchemaStoreClient(file.getAbsolutePath());
		}
		catch(Exception e) { System.out.println("(E) SchemaStoreConnection - " + e.getMessage()); }
	}
	
	/** Returns the schema store connection */
	public static SchemaStoreClient getConnection()
		{ return client; }
	
	/** Returns the list of schemas */
	public static ArrayList<Schema> getSchemas()
		{ try { return client.getSchemas(); } catch(Exception e) { return new ArrayList<Schema>(); } }

	/** Returns the graph for the specified schema */
	public static Graph getGraph(Integer schemaID)
		{ try { return client.getGraph(schemaID); } catch(Exception e) { return null; } }
	
	/** Returns the list of mappings */
	public static ArrayList<Mapping> getMappings()
		{ try { return client.getMappings(); } catch(Exception e) { return new ArrayList<Mapping>(); } }
}