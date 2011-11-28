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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mitre.affinity.algorithms.distance_functions.schemas.SchemaTermVector;
import org.mitre.affinity.model.IClusterObjectManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

public interface ISchemaManager extends IClusterObjectManager<Integer, Schema> {
	
	/** Initializes the schema list */
	public void initSchemas();
	
	/** Set the schemas instead of fetching them from the schema store manager */
	public void setSchemas(List<Schema> schemas);
	
	/** Returns a list of all schemas */
	public ArrayList<Schema> getSchemas();
	
	/** Returns the list of all schema IDs */
	public ArrayList<Integer> getSchemaIDs();
	
	/** Returns the deletable schemas */
	public ArrayList<Integer> getDeletableSchemas();	
	
	/** Returns the specified schema */
	public Schema getSchema(Integer schemaID);	

	/** Deletes the specified schema */
	public boolean deleteSchema(Integer schemaID);		
	
	/** Returns the term vector for the specified schema */
	public SchemaTermVector getSchemaTermVector(Integer schemaID);	
	
	/** Return the total number of term occurrences for the given collection of schemas. */
	public int getNumSchemasContainingTerm(String term, Collection<Integer> schemaIDs);
	
	//--------------------------
	// Schema Element Functions
	//--------------------------
	
	/** Returns the SchemaInfo object for the specified schema */
	public HierarchicalSchemaInfo getSchemaInfo(Integer schemaID);
	
	/** Retrieves the schema elements for the specified schema and type from the web service */
	public ArrayList<SchemaElement> getSchemaElements(Integer schemaID, Class<?> type);
	
	/** Gets the specified schema element */
	public SchemaElement getSchemaElement(Integer schemaElementID);	
	
	/** Returns the list of descendant elements for the specified schema and element */
	public  ArrayList<SchemaElement> getDescendantElements(Integer schemaID, Integer elementID);

	/** Returns the various depths of the specified schema element */
	public ArrayList<Integer> getDepths(Integer schemaID, Integer elementID);
	
	//-------------------
	// Mapping Functions
	//-------------------
	
	/** Returns a list of all available mappings */
	public ArrayList<Mapping> getAvailableMappings();	
}