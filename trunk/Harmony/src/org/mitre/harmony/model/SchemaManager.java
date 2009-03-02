package org.mitre.harmony.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.harmony.model.preferences.Preferences;
import org.mitre.schemastore.importers.Importer;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 * Class for managing the schemas in the schema repository
 * @author CWOLF
 */
public class SchemaManager
{
	/** Caches schema information */
	static protected HashMap<Integer,Schema> schemas = null;

	/** Caches schema element information */
	static protected HashMap<Integer,SchemaElement> schemaElements = new HashMap<Integer,SchemaElement>();
	
	/** Caches graphs associated with schemas */
	static protected HashMap<Integer,HierarchicalGraph> schemaGraphs = new HashMap<Integer,HierarchicalGraph>();
	
	//------------------
	// Schema Functions
	//------------------
	
	/** Initializes the schema list */
	static public void initSchemas()
	{
		schemas = new HashMap<Integer,Schema>();
		try {
			for(Schema schema : SchemaStoreManager.getSchemas())
				schemas.put(schema.getId(),schema);
		}
		catch(RemoteException e)
			{ System.out.println("(E) SchemaManager.initSchemas - " + e.getMessage()); }
	}
	
	/** Returns a list of all schemas */
	static public ArrayList<Schema> getSchemas()
	{
		if(schemas==null) initSchemas();
		return new ArrayList<Schema>(schemas.values());
	}
	
	/** Returns the list of all schema IDs */
	static public ArrayList<Integer> getSchemaIDs()
	{
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		for(Schema schema : getSchemas())
			schemaIDs.add(schema.getId());
		return schemaIDs;
	}
	
	/** Returns the deletable schemas */
	static public ArrayList<Integer> getDeletableSchemas()
	{
		try {
			return SchemaStoreManager.getDeletableSchemas();
		} catch(Exception e) { System.out.println("(E) SchemaManager.getDeletableSchemas - " + e.getMessage()); }
		return new ArrayList<Integer>();
	}
	
	/** Returns the specified schema */
	static public Schema getSchema(Integer schemaID)
	{
		if(schemas==null) initSchemas();
		if(!schemas.containsKey(schemaID))
		{
			try { schemas.put(schemaID, SchemaStoreManager.getSchema(schemaID)); }
			catch(RemoteException e) { System.out.println("(E) SchemaManager.getSchema - " + e.getMessage()); }
		}
		return schemas.get(schemaID);
	}

	/** Deletes the specified schema */
	static public boolean deleteSchema(Integer schemaID)
	{
		try {
			boolean success = SchemaStoreManager.deleteSchema(schemaID);
			if(success) { schemas.remove(schemaID); return true; }
		} catch(Exception e) { System.out.println("(E) SchemaManager.deleteSchema - " + e.getMessage()); }
		return false;
	}
	
	//--------------------
	// Importer Functions
	//--------------------
	
	/** Returns the list of available importers */
	static public ArrayList<Importer> getImporters()
	{
		ArrayList<Importer> importerList = new ArrayList<Importer>();
		for(Importer importer : SchemaStoreManager.getImporters())
			if(importer.getURIType()==Importer.FILE || importer.getURIType()==Importer.ARCHIVE)
				importerList.add(importer);
		return importerList;
	}
	
	//--------------------------
	// Schema Element Functions
	//--------------------------
	
	/** Returns the graph for the specified schema */
	static public HierarchicalGraph getGraph(Integer schemaID)
	{
		// Fill cache if needed
		if(!schemaGraphs.containsKey(schemaID))
		{
			try {
				HierarchicalGraph graph = SchemaStoreManager.getGraph(schemaID);
				graph.setModel(Preferences.getSchemaGraphModel(schemaID));
				for(SchemaElement schemaElement : graph.getElements(null))
					schemaElements.put(schemaElement.getId(), schemaElement);
				schemaGraphs.put(schemaID, graph);
			}
			catch(RemoteException e) { schemaGraphs.put(schemaID,null); }
		}
		return schemaGraphs.get(schemaID);
	}
	
	/** Retrieves the schema elements for the specified schema and type from the web service */
	static public ArrayList<SchemaElement> getSchemaElements(Integer schemaID, Class type)
		{ return getGraph(schemaID).getElements(type); }	
	
	/** Gets the specified schema element */
	static public SchemaElement getSchemaElement(Integer schemaElementID)
	{
		if(!schemaElements.containsKey(schemaElementID))
		{
			try { schemaElements.put(schemaElementID, SchemaStoreManager.getSchemaElement(schemaElementID)); }
			catch(RemoteException e) {}
		}
		return schemaElements.get(schemaElementID);
	}

	/** Returns the list of descendant elements for the specified schema and element */
	static public ArrayList<SchemaElement> getDescendantElements(Integer schemaID, Integer elementID)
		{ return getGraph(schemaID).getDescendantElements(elementID); }

	/** Returns the various depths of the specified schema element */
	static public ArrayList<Integer> getDepths(Integer schemaID, Integer elementID)
		{ return getGraph(schemaID).getDepths(elementID); }
}