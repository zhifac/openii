// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.rmap.view;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.harmony.model.SchemaManager;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.rmap.model.Dependency;


public class RMapSchemaManager extends SchemaManager {

	/** Caches schema information for Logical Relation "schemas" by Dependency */
	private RMapHarmonyModel _harmonyModel;
	public HashMap<Integer,Schema> _sourceSchemaIDForMappingID = null;
	public HashMap<Integer,Schema> _targetSchemaIDForMappingID = null;
	public ArrayList<Mapping> _mappingList = null;
	public HashMap<Integer,ArrayList<MappingCell>> _mappingCellsByMapping = null;
	public HashMap<Dependency, Integer> _mappingID_by_Dependency = null;

	public ArrayList<Mapping> getMappingList() { return _mappingList; }
	public HashMap<Integer, ArrayList<MappingCell>> getMappingCellsByMapping() { return _mappingCellsByMapping; }
	
	public RMapSchemaManager(RMapHarmonyModel harmonyModel){
		super(null);
		_harmonyModel = harmonyModel;
	}
	
	public Mapping getMapping(Integer mappingID){
		Mapping retVal = null;
		for (Mapping mapping : _mappingList)
			if (mapping.getId().equals(mappingID)) retVal = mapping;
		return retVal;
	}
	
	public ArrayList<MappingCell> getMappingCells (Integer mappingID){
		return _mappingCellsByMapping.get(mappingID);
	}
	
	public Boolean setMappingCells(Dependency depend, ArrayList<MappingCell> newMappingCells){
		Integer mappingID = _mappingID_by_Dependency.get(depend);
		if (mappingID != null){
			_mappingCellsByMapping.put(mappingID, newMappingCells);
			return true;
		}
		else 	
			return false;
	}

	
	//------------------
	// Overridden Schema Functions
	//------------------
	
	/** Override -- Initializes the schema list */
	@SuppressWarnings("unchecked")
	public void initSchemas(ArrayList<Dependency> dependList)
	{
		schemas = new HashMap<Integer,Schema>();
		_mappingList = new ArrayList<Mapping>();
		_mappingCellsByMapping = new HashMap<Integer,ArrayList<MappingCell>>();
		
		_sourceSchemaIDForMappingID = new HashMap<Integer,Schema>();
		_targetSchemaIDForMappingID = new HashMap<Integer,Schema>();
		_mappingID_by_Dependency = new HashMap<Dependency,Integer>();
		
		try {
		
			for (Dependency depend : dependList ){
				// generate the mapping, mapping matrix, and logical relation "schema" graphs
				Object[] mapInfo = depend.generateMapping(_harmonyModel.getProjectManager().getProject());
				
				HierarchicalSchemaInfo sourceGraph = (HierarchicalSchemaInfo) mapInfo[2];
				HierarchicalSchemaInfo targetGraph = (HierarchicalSchemaInfo) mapInfo[3];
				Mapping mapping = (Mapping)mapInfo[0]; 
				ArrayList<MappingCell> mappingCells = (ArrayList<MappingCell>) mapInfo[1];
				
				_mappingList.add(mapping);
				_mappingCellsByMapping.put(mapping.getId(),mappingCells);
				
				// store the IDs for the Logical Relation "schemas" for dependency
				_sourceSchemaIDForMappingID.put(mapping.getId(), getSchema(mapping.getSourceId()));
				_targetSchemaIDForMappingID.put(mapping.getId(), getSchema(mapping.getTargetId()));
				_mappingID_by_Dependency.put(depend, mapping.getId());
					
				// add schemas for the logical relations
				schemas.put(sourceGraph.getSchema().getId(), sourceGraph.getSchema());
				schemas.put(targetGraph.getSchema().getId(), targetGraph.getSchema());
								
				// add a graph for each logical relation
				schemaInfoList.put(sourceGraph.getSchema().getId(), sourceGraph);
				schemaInfoList.put(targetGraph.getSchema().getId(), targetGraph);
				
				// add all the schemaElements
				for (SchemaElement se : sourceGraph.getElements(null)) {
					schemaElements.put(se.getId(),se);
				}
				
				for (SchemaElement se : targetGraph.getElements(null)) {
					schemaElements.put(se.getId(),se);
				}
			}
		} catch(Exception e) { 
			System.err.println("[E] RMapSchemaManager.initSchemas - " + e.getMessage()); 
			e.printStackTrace();
		}	
	}
	
	/** Initializes the schema list */
	public void initSchemas(){return;}

	/** @Override -- Returns a list of all schemas */
	public ArrayList<Schema> getSchemas() {
		return new ArrayList<Schema>(schemas.values());
	}
	
	/** @Override -- Returns the deletable schemas */
	public ArrayList<Integer> getDeletableSchemas() {
		return new ArrayList<Integer>();
	}
	
	/** @Override -- Returns the specified schema */
	public Schema getSchema(Integer schemaID) {
		if(schemas==null) initSchemas();
		if(!schemas.containsKey(schemaID)) {
			System.err.println("[E] RMapSchemaManager.getSchema -- trying to use undefined schema " + schemaID);
		}
		return schemas.get(schemaID);
	}

	//--------------------------
	// Schema Element Functions
	//--------------------------
	
	/** @Override - Returns the graph for the specified schema */
	public HierarchicalSchemaInfo getSchemaInfo(Integer schemaID)
	{
		// Fill cache if needed
		if(!schemaInfoList.containsKey(schemaID))
			System.err.println("[E] RMapSchemaManager.getSchemaInfoList -- trying to use undefined schemaInfo " + schemaID);
		
		return schemaInfoList.get(schemaID);
	}
	
	/** @Override - Gets the specified schema element */
	public SchemaElement getSchemaElement(Integer schemaElementID)
	{
		if(!schemaElements.containsKey(schemaElementID))
			System.err.println("[E] RMapSchemaManager.getSchemaElement -- trying to use undefined schemaElement " + schemaElementID);
		return schemaElements.get(schemaElementID);
	}

}
	

