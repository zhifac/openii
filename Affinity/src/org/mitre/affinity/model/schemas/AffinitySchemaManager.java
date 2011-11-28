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

package org.mitre.affinity.model.schemas;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mitre.affinity.algorithms.distance_functions.schemas.SchemaTermVector;
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
	
	private boolean usingSchemaStoreManager = true;
	
	//** Constructs the Schema Manager */
	public AffinitySchemaManager() {
	}
	
	//------------------
	// Schema Functions
	//------------------
	
	/** Initializes the schema list */
	@Override
	public void initSchemas() {
		schemas = new HashMap<Integer,Schema>();
		if(usingSchemaStoreManager) {
			try {
				for(Schema schema : AffinitySchemaStoreManager.getSchemas()) {
					schemas.put(schema.getId(), schema);
				}
				usingSchemaStoreManager = true;
			}
			catch(RemoteException e)
			{ System.out.println("(E) SchemaManager.initSchemas - " + e.getMessage()); }
		}
	}	
	
	@Override
	public void setSchemas(List<Schema> schemas) {
		if(schemas == null) {
			throw new IllegalArgumentException("Schemas cannot be null");
		}
		usingSchemaStoreManager = false;
		this.schemas = new HashMap<Integer, Schema>(schemas.size());
		for(Schema schema : schemas) {
			this.schemas.put(schema.getId(), schema);
		}
	}

	/** Returns a list of all schemas */
	@Override
	public ArrayList<Schema> getSchemas() {
		if(schemas==null) initSchemas();
		return new ArrayList<Schema>(schemas.values());
	}	
	
	@Override
	public ArrayList<Schema> getClusterObjects() {
		return getSchemas();
	}	

	/** Returns the list of all schema IDs */
	@Override
	public ArrayList<Integer> getSchemaIDs() {
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		for(Schema schema : getSchemas()) {
			schemaIDs.add(schema.getId());
		}
		return schemaIDs;
	}
	
	@Override
	public ArrayList<Integer> getClusterObjectIDs() {
		return getSchemaIDs();
	}
	
	
	/** Returns the deletable schemas */
	@Override
	public ArrayList<Integer> getDeletableSchemas() {
		if(usingSchemaStoreManager) {
			try {
				return AffinitySchemaStoreManager.getDeletableSchemas();
			} catch(Exception e) { System.out.println("(E) SchemaManager.getDeletableSchemas - " + e.getMessage()); }
			return new ArrayList<Integer>();
		}
		else {
			return getSchemaIDs();
		}
	}
	
	@Override
	public ArrayList<Integer> getDeletableClusterObjects() {
		return getDeletableSchemas();
	}
	
	/** Returns the specified schema */
	@Override
	public Schema getSchema(Integer schemaID) {
		if(schemas==null) initSchemas();
		if(!schemas.containsKey(schemaID) && usingSchemaStoreManager) {
			try { schemas.put(schemaID, AffinitySchemaStoreManager.getSchema(schemaID)); }
			catch(RemoteException e) { System.out.println("(E) SchemaManager.getSchema - " + e.getMessage()); }
		}
		return schemas.get(schemaID);
	}
	
	@Override
	public Schema getClusterObject(Integer objectID) {
		return getSchema(objectID);
	}
	
	@Override
	public String getClusterObjectName(Integer objectID) {
		Schema schema = getSchema(objectID);
		if(schema != null) {
			return schema.getName();
		}
		return null;
	}
	
	@Override
	public Integer findClusterObject(String identifier) {
		if(schemas==null) initSchemas();
		if(schemas != null && !schemas.isEmpty()) {
			for(Schema schema : schemas.values()) {
				if(schema.getName() != null && schema.getName().equalsIgnoreCase(identifier)) {
					return schema.getId();
				}
			}
		}
		return null;
	}

	/** Deletes the specified schema */
	@Override
	public boolean deleteSchema(Integer schemaID) {
		if(usingSchemaStoreManager) {
			try {
				boolean success = AffinitySchemaStoreManager.deleteSchema(schemaID);
				if(success) { schemas.remove(schemaID); return true; }
			} catch(Exception e) { System.out.println("(E) SchemaManager.deleteSchema - " + e.getMessage()); }
			return false;
		}
		else {
			return (schemas.remove(schemaID) != null);
		}
	}
	
	@Override
	public boolean deleteClusterObject(Integer objectID) {
		return deleteSchema(objectID);
	}
	
	/** Returns the term vector for the specified schema */
	@Override
	public SchemaTermVector getSchemaTermVector(Integer schemaID) {
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
	@Override
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
	@Override
	public HierarchicalSchemaInfo getSchemaInfo(Integer schemaID) {
		// Fill cache if needed
		if(!schemaInfos.containsKey(schemaID)) {
			try {
				HierarchicalSchemaInfo graph = AffinitySchemaStoreManager.getGraph(schemaID);
				for(SchemaElement schemaElement : graph.getElements(null)) {
					schemaElements.put(schemaElement.getId(), schemaElement);
				}
				schemaInfos.put(schemaID, graph);
			}
			catch(RemoteException e) { schemaInfos.put(schemaID,null); }
		}
		return schemaInfos.get(schemaID);
	}
	
	/** Retrieves the schema elements for the specified schema and type from the web service */
	@Override
	public ArrayList<SchemaElement> getSchemaElements(Integer schemaID, Class<?> type) { 
		return getSchemaInfo(schemaID).getElements(type); 
	}	
	
	/** Gets the specified schema element */
	@Override
	public SchemaElement getSchemaElement(Integer schemaElementID) {
		if(!schemaElements.containsKey(schemaElementID)) {
			try { schemaElements.put(schemaElementID, AffinitySchemaStoreManager.getSchemaElement(schemaElementID)); }
			catch(RemoteException e) {}
		}
		return schemaElements.get(schemaElementID);
	}

	/** Returns the list of descendant elements for the specified schema and element */
	@Override
	public ArrayList<SchemaElement> getDescendantElements(Integer schemaID, Integer elementID) { 
		return getSchemaInfo(schemaID).getDescendantElements(elementID); 
	}

	/** Returns the various depths of the specified schema element */
	@Override
	public ArrayList<Integer> getDepths(Integer schemaID, Integer elementID) { 
		return getSchemaInfo(schemaID).getDepths(elementID); 
	}
	
	//-------------------
	// Mapping Functions
	//-------------------
	
	/** Returns a list of all available mappings */
	@Override
	public ArrayList<Mapping> getAvailableMappings() { 
		return new ArrayList<Mapping>(); 
	}	
}