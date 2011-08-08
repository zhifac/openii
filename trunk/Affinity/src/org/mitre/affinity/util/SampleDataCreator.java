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

package org.mitre.affinity.util;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mitre.affinity.clusters.DistanceGrid;
import org.mitre.affinity.clusters.distanceFunctions.DistanceFunction;
import org.mitre.affinity.model.Position;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.SchemaDocument;
//import org.mitre.affinity.model.AffinitySchemaManager;

//import org.mitre.schemastore.porters.schemaImporters.SchemaImporterException;
import org.mitre.schemastore.porters.schemaImporters.XSDImporter;
import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;
//import org.mitre.schemastore.model.SchemaElement;

import au.com.bytecode.opencsv.CSVReader;

public class SampleDataCreator {
	
	/**
	 * Parses a sample position grid contained in a CSV file
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static SchemasAndPositionGrid parsePositionGridCSVFile(String fileName) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(fileName));		 
	    List<String []> data = (List<String []>)reader.readAll();
		
	    List<Schema> schemas = new ArrayList<Schema>();
	    PositionGrid pg = new PositionGrid(2);
	    
	    int currRow = 0;	    
	    for(String[] row : data) {
	    	//System.out.println("Row " + (currRow+1) + ": " + row[0]);
	    	Integer schemaID = Integer.parseInt(row[0]);
	    	String schemaName = row[1];
	    	double x = Double.parseDouble(row[2]);
	    	double y = Double.parseDouble(row[3]);
	    	schemas.add(new SchemaDocument(schemaID, schemaName, "", "", "", schemaName, false));
	    	pg.setPosition(currRow+1, new Position(x, y));

	    	currRow++;
	    }
	    return new SchemasAndPositionGrid(schemas, pg);
	}
	
	public static SchemasAndDistanceGrid loadSchemas(String filePath,
			List<Schema> schemas, DistanceFunction distanceFunction) {		
		
		XSDImporter xsdImporter = new XSDImporter();		
		
		for(Schema schema : schemas) {
			try {
				xsdImporter.importSchema(schema.getName(), schema.getAuthor(), 
						schema.getDescription(), new URI(filePath + "/" + schema.getName() + ".xsd"));
			} catch(Exception ex) {
				System.err.println("Error loading schema " + schema.getName() + ", Details:");
				System.err.println(ex);
			}

			/*
			try {				
				for(SchemaElement schemaElement : xsdImporter.getSchemaElements()) {
					AffinitySchemaManager.addSchema(schema, schemaElement);
				}
			} catch(SchemaImporterException ex) {
				System.err.println("Error getting schema elements: " + ex);
			}*/
		}
		
		return null;
	}
	
	/**
	 * Parses a sample distance grid contained in a CSV file
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static SchemasAndDistanceGrid parseDistanceGridCSVFile(String fileName) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(fileName));
	    List<String []> data = (List<String []>)reader.readAll();
		
	    List<Schema> schemas = new ArrayList<Schema>();
	    DistanceGrid dg = new DistanceGrid();
	    Map<Integer, Integer> idMap = new HashMap<Integer, Integer>();
	    int currRow = 0;
		for(String[] row : data) {
			int currCol = 0;
			for(String cell : row) {
				//System.out.println(cell);
				if(currRow == 0) {
					//Get schema ids in first row and map the column index to the schema id in that column
					//System.out.println(cell);
					idMap.put(currCol, Integer.parseInt(cell));					
				}
				else if(currRow == 1) {
					//Get schema names in second row and create the schemas
					schemas.add(new SchemaDocument(idMap.get(currCol), cell, "", "", "", cell, false));
				}				
				else {
					//Get distance grid value
					//System.out.println(currRow + "," + (currCol+1) + "," + cell);
					//System.out.println("(" + (currRow-2) + ", " + currCol + "), schema 1: " + idMap.get(currRow-2) + ", schema2: " + idMap.get(currCol));
					dg.set(idMap.get(currRow-2), idMap.get(currCol), Double.parseDouble(cell));
				}
				currCol++;
			}
			currRow++;
		}
		
		return new SchemasAndDistanceGrid(schemas, dg);
	}
	
	public static void loadSchemas() {
		
	}
	
	public static class SchemasAndDistanceGrid {
		public List<Schema> schemas;
		public DistanceGrid dg;
		
		public SchemasAndDistanceGrid(List<Schema> schemas, DistanceGrid dg) {
			this.schemas = schemas;
			this.dg = dg;
		}
	}
	
	public static class SchemasAndPositionGrid {
		public List<Schema> schemas;
		public PositionGrid pg;
		
		public SchemasAndPositionGrid(List<Schema> schemas, PositionGrid pg) {
			this.schemas = schemas;
			this.pg = pg;
		}
	}
	
	public static void main(String[] args) {
		// Display the schemas found within the repository
		
		try {
			SchemaStoreClient client = new SchemaStoreClient(
					new Repository(Repository.SERVICE, new URI("src/data/realestate_auto_schemas.jar"), "", "", ""));
			for(Schema schema : client.getSchemas())
				System.out.println(schema.getId() + ": " + schema.getName());
		}
		catch(Exception e) { e.printStackTrace(); }
		
		//List<SchemaDocument> schemas = null;
		//DistanceGrid dg = null;
		/*
		try {
			parseDistanceGridCSVFile("src/data/realestate_auto_result.csv");
		} catch(IOException ex) {
			System.err.println(ex);
		}*/
	}
}
