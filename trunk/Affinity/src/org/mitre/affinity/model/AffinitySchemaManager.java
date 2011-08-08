/*
 *  Copyright 2010 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mitre.affinity.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.mitre.affinity.clusters.distanceFunctions.SchemaTermVector;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
/**
 * Class for managing the schemas in the schema repository
 * @author CWOLF
 */
public class AffinitySchemaManager implements ISchemaManager
{	
	/** Caches schema information */
	protected HashMap<Integer, Schema> schemas = null;

	/** Caches schema element information */
	protected HashMap<Integer, SchemaElement> schemaElements = new HashMap<Integer,SchemaElement>();
	
	/** Caches schema info objects associated with schemas */
	protected HashMap<Integer, HierarchicalSchemaInfo> schemaInfos = new HashMap<Integer, HierarchicalSchemaInfo>();
	
	/** Caches term vectors associated with schemas */
	protected Map<Integer, SchemaTermVector> schemaTermVectors = new HashMap<Integer, SchemaTermVector>();
	
	//protected int totalTermOccurences;
	
	//** Constructs the Schema Manager */
	public AffinitySchemaManager()
		{ //System.out.println("constructed schema manager");
		}
	
	//------------------
	// Schema Functions
	//------------------
	
	/** Initializes the schema list */
	public void initSchemas()
	{
		schemas = new HashMap<Integer,Schema>();
		try {
			for(Schema schema : AffinitySchemaStoreManager.getSchemas())
				schemas.put(schema.getId(),schema);
		}
		catch(RemoteException e)
			{ System.out.println("(E) SchemaManager.initSchemas - " + e.getMessage()); }
	}
	
	/** Returns a list of all schemas */
	public ArrayList<Schema> getSchemas()
	{
		if(schemas==null) initSchemas();
		return new ArrayList<Schema>(schemas.values());
	}
	
	/** Returns the list of all schema IDs */
	public ArrayList<Integer> getSchemaIDs()
	{
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		for(Schema schema : getSchemas())
			schemaIDs.add(schema.getId());
		return schemaIDs;
	}
	
	/** Returns the deletable schemas */
	public ArrayList<Integer> getDeletableSchemas()
	{
		try {
			return AffinitySchemaStoreManager.getDeletableSchemas();
		} catch(Exception e) { System.out.println("(E) SchemaManager.getDeletableSchemas - " + e.getMessage()); }
		return new ArrayList<Integer>();
	}
	
	/** Returns the specified schema */
	public Schema getSchema(Integer schemaID)
	{
		if(schemas==null) initSchemas();
		if(!schemas.containsKey(schemaID))
		{
			try { schemas.put(schemaID, AffinitySchemaStoreManager.getSchema(schemaID)); }
			catch(RemoteException e) { System.out.println("(E) SchemaManager.getSchema - " + e.getMessage()); }
		}
		return schemas.get(schemaID);
	}

	/** Deletes the specified schema */
	public boolean deleteSchema(Integer schemaID)
	{
		try {
			boolean success = AffinitySchemaStoreManager.deleteSchema(schemaID);
			if(success) { schemas.remove(schemaID); return true; }
		} catch(Exception e) { System.out.println("(E) SchemaManager.deleteSchema - " + e.getMessage()); }
		return false;
	}
	
	/** Returns the term vector for the specified schema */
	public SchemaTermVector getSchemaTermVector(Integer schemaID)
	{
		// Fill cache if needed
		if(!schemaTermVectors.containsKey(schemaID)) {			
			schemaTermVectors.put(schemaID, new SchemaTermVector(getSchemaInfo(schemaID)));
		}
		
		return schemaTermVectors.get(schemaID);
	}
	
	/**
	 * Return the total number of term occurrences for the given collection of schemas.
	 * 
	 * @param schemaIDs
	 * @return
	 */
	public int getNumSchemasContainingTerm(String term, Collection<Integer> schemaIDs) {
		int numSchemas = 0;		
		for(Integer schemaID : schemaIDs) {
			SchemaTermVector termVector = getSchemaTermVector(schemaID);
			if(termVector != null && termVector.containsTerm(term))
				numSchemas++;
		}
		return numSchemas;
	}	
	
	
	//--------------------------
	// Schema Element Functions
	//--------------------------
	
	/** Returns the schemaInfo object for the specified schema */
	public HierarchicalSchemaInfo getSchemaInfo(Integer schemaID) 
	{
		// Fill cache if needed
		if(!schemaInfos.containsKey(schemaID))
		{
			try {
				HierarchicalSchemaInfo graph = AffinitySchemaStoreManager.getGraph(schemaID);
				//graph.setModel(harmonyModel.getPreferences().getGraphModel(schemaID));
				for(SchemaElement schemaElement : graph.getElements(null))
					schemaElements.put(schemaElement.getId(), schemaElement);
				schemaInfos.put(schemaID, graph);
			}
			catch(RemoteException e) { schemaInfos.put(schemaID,null); }
		}
		return schemaInfos.get(schemaID);
	}
	
	/** Retrieves the schema elements for the specified schema and type from the web service */
	public ArrayList<SchemaElement> getSchemaElements(Integer schemaID, Class<?> type)
		{ return getSchemaInfo(schemaID).getElements(type); }	
	
	/** Gets the specified schema element */
	public SchemaElement getSchemaElement(Integer schemaElementID)
	{
		if(!schemaElements.containsKey(schemaElementID))
		{
			try { schemaElements.put(schemaElementID, AffinitySchemaStoreManager.getSchemaElement(schemaElementID)); }
			catch(RemoteException e) {}
		}
		return schemaElements.get(schemaElementID);
	}

	/** Returns the list of descendant elements for the specified schema and element */
	public ArrayList<SchemaElement> getDescendantElements(Integer schemaID, Integer elementID)
		{ return getSchemaInfo(schemaID).getDescendantElements(elementID); }

	/** Returns the various depths of the specified schema element */
	public ArrayList<Integer> getDepths(Integer schemaID, Integer elementID)
		{ return getSchemaInfo(schemaID).getDepths(elementID); }
	
	//-------------------
	// Mapping Functions
	//-------------------
	
	/** Returns a list of all available mappings */
	public ArrayList<Mapping> getAvailableMappings()
		{ return new ArrayList<Mapping>(); }
}