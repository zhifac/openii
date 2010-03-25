// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.rmap.model;

import java.util.*;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.schemaInfo.model.*;
import org.mitre.schemastore.model.schemaInfo.*;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

public class LogicalRelation {

	static private Integer nextID = 1;
	static public Integer getNextID() { return nextID++; }

	// needs to be copied
	private HierarchicalSchemaInfo _mappingSchemaInfo;
	private HashMap<Integer,ArrayList<Integer>> _entityIndicesByRel;
	private ArrayList<Entity> _entitySet;	 			 // set of paths followed in the chase (schemaStore schemaInfo ID space)
	private ArrayList<Entity> _mappingSchemaEntitySet; // entity set  for copy in mappingSchemaInfo
	private HashMap<Integer,Integer> _IDmappings_LR_to_SS;
	private HashMap<Integer,ArrayList<Integer>> _IDmappings_SS_to_LR;
	public HashMap<Integer,Integer> getIDmappings_LR_to_SS(){ return _IDmappings_LR_to_SS; }
	public HashMap<Integer,ArrayList<Integer>> getIDmappings_SS_to_LR(){ return _IDmappings_SS_to_LR; }
	
	/**
	 * translate:  enumerate all possible translation of input array of *SchemaStore IDs* 
	 * @param inputs
	 * @return
	 */
	public ArrayList<Integer[]> translate_SS_to_LR (Integer[] inputs){
		ArrayList<Integer[]> retVal = null;
		Integer product = 1;
		for (int i : inputs){
			if (_IDmappings_SS_to_LR.get(i) == null)
				product = 0;
			else
				product *=  _IDmappings_SS_to_LR.get(i).size();
			
		}
		if (product > 0){
			retVal = new ArrayList<Integer[]>();
			Integer[] output = new Integer[inputs.length];
			for (int i=0; i<inputs.length; i++)
				output[i] = _IDmappings_SS_to_LR.get(inputs[i]).get(0); 
			retVal.add(output);
			if (product > 1)
				retVal.add(output);
		}
		
		return retVal;
	}
		
	public int getPositionMapingSchemaEntitySet(Integer passedID){
		for (int i = 0; i < _mappingSchemaEntitySet.size() ; i++)
			if (_mappingSchemaEntitySet.get(i).getId().equals(passedID))
				return i;
		return -1;
	}
	

	/**
	 * copy(): creates a deep copy of the LogicalRelation
	 * @return the copy
	 */
	public LogicalRelation copy(){

		LogicalRelation copy = new LogicalRelation();
		HashMap<Integer,Integer> copyIDmappings_LR_to_SS = new HashMap<Integer,Integer>();
		for (Integer key : this._IDmappings_LR_to_SS.keySet())
			copyIDmappings_LR_to_SS.put(key, this._IDmappings_LR_to_SS.get(key));

		HashMap<Integer,ArrayList<Integer>> copyEntityIndicesByRel = new HashMap<Integer,ArrayList<Integer>>();
		for (Integer key : this._entityIndicesByRel.keySet()){
			ArrayList<Integer> idxSet = this._entityIndicesByRel.get(key);
			ArrayList<Integer> idxSetCopy = new ArrayList<Integer>();
			for (Integer id : idxSet) idxSetCopy.add(id);
			copyEntityIndicesByRel.put(key,idxSetCopy);
		}
		
		HashMap<Integer,ArrayList<Integer>> copyIDmappings_SS_to_LR = new HashMap<Integer,ArrayList<Integer>>();
		for (Integer key : this._IDmappings_SS_to_LR.keySet()){
			ArrayList<Integer> idSet = this._IDmappings_SS_to_LR.get(key);
			ArrayList<Integer> idSetCopy = new ArrayList<Integer>();
			for (Integer id : idSet) idSetCopy.add(id);
			copyIDmappings_SS_to_LR.put(key,idSetCopy);
		}

		ArrayList<Entity> copyEntitySet = new ArrayList<Entity>();
		for (Entity entity : this._entitySet)
			copyEntitySet.add(entity.copy());

		ArrayList<Entity> copyMappingSchemaEntitySet = new ArrayList<Entity>();
		for (Entity entity : this._mappingSchemaEntitySet)
			copyMappingSchemaEntitySet.add(entity.copy());

		copy = new LogicalRelation();
		copy._mappingSchemaInfo = this._mappingSchemaInfo;
		copy._mappingSchemaEntitySet = copyMappingSchemaEntitySet;
		copy._entitySet = copyEntitySet;
		copy._IDmappings_LR_to_SS = copyIDmappings_LR_to_SS;
		copy._IDmappings_SS_to_LR = copyIDmappings_SS_to_LR;
		copy._entityIndicesByRel = copyEntityIndicesByRel;
		return copy;
	} // end method copy

	public ArrayList<Entity> getEntitySet(){ return _entitySet; }
	public ArrayList<Entity> getMappingSchemaEntitySet(){ return _mappingSchemaEntitySet; }
	public HashMap<Integer,ArrayList<Integer>> getEntityIndicesByRel(){ return _entityIndicesByRel;}
	public Integer getSchemaId() {return _mappingSchemaInfo.getSchema().getId();}
	public HierarchicalSchemaInfo getMappingSchemaInfo(){ return _mappingSchemaInfo; }

	public LogicalRelation(){
		_entitySet = new ArrayList<Entity>();
		_mappingSchemaEntitySet = new ArrayList<Entity>();
		_entityIndicesByRel = new HashMap<Integer,ArrayList<Integer>>();
	}

	public ArrayList<Integer> getEntitySetIds(){
		ArrayList<Integer> retVal = new ArrayList<Integer>();
		for (Entity entity : _entitySet)
			retVal.add(entity.getId());
		return retVal;
	}


	/**
	 * generateLogicalRelationSchemaInfo:  generates the schemaInfo for the 
	 * LogicalRelation by creating a "deep copy" of a given schemaInfo 
	 * in from the repository and modifying it to reflect the 
	 * results of the chase.
	 *
	 * @param schemaStoreSchemaInfo schemaInfo from repository
	 */
	private void generateLogicalRelationSchemaInfo(SchemaInfo schemaStoreSchemaInfo){

		_IDmappings_LR_to_SS = new HashMap<Integer,Integer>();
		_IDmappings_SS_to_LR = new HashMap<Integer, ArrayList<Integer>>();

		Integer[] newPathEntityIDbyPosition = new Integer[_entitySet.size()];
		HashMap<Integer,Integer> domainSS_to_domainLR = new HashMap<Integer, Integer>();
		ArrayList<SchemaElement> returnSchemaElements = new ArrayList<SchemaElement>();

		// use the relational schemaInfo model to build a Relational schemaInfo for SchemaStore schemaInfo
		// that LogicalRelation schemaInfo is based on
		SchemaModel relationalModel = null, rmapModel = null;
		for (SchemaModel gm : HierarchicalSchemaInfo.getSchemaModels()){
			if (gm.getName().equals("Relational")) relationalModel = gm;
			if (gm.getName().equals("RMap - 1 to N")) rmapModel = gm;
		}

		if (rmapModel == null || relationalModel == null)
			System.err.println("[E] LogicalRelation:generateLogicalRelationSchemaInfo -- relationalModel or rmapModel is null");

		HierarchicalSchemaInfo relationalSchemaInfo = new HierarchicalSchemaInfo(schemaStoreSchemaInfo,relationalModel);

		// create new Schema
		Schema newSchema = new Schema(getNextID(),schemaStoreSchemaInfo.getSchema().getName(),
				schemaStoreSchemaInfo.getSchema().getAuthor(),
				schemaStoreSchemaInfo.getSchema().getSource(),
				schemaStoreSchemaInfo.getSchema().getType(),
				schemaStoreSchemaInfo.getSchema().getDescription(),false);

		for (Integer currIndex=0; currIndex < _entitySet.size(); currIndex++){
			Entity pathEntity = _entitySet.get(currIndex);
			Entity pathEntityCopy = pathEntity.copy();
			pathEntityCopy.setId(getNextID());
			_IDmappings_LR_to_SS.put(pathEntityCopy.getId(), pathEntity.getId());

			if (_IDmappings_SS_to_LR.get(pathEntity.getId()) == null)
				_IDmappings_SS_to_LR.put(pathEntity.getId(),new ArrayList<Integer>());
			_IDmappings_SS_to_LR.get(pathEntity.getId()).add(pathEntityCopy.getId());

			returnSchemaElements.add(pathEntityCopy);
			newPathEntityIDbyPosition[currIndex] = pathEntityCopy.getId();
			_mappingSchemaEntitySet.add(pathEntityCopy);

			// create duplicate of all attributes (for path entity), and their domains (if necessary)
			for (Attribute attr : relationalSchemaInfo.getAttributes(pathEntity.getId()))
			{
				// create copy of attribute
				Attribute attrCopy = attr.copy();
				attrCopy.setId(getNextID());
				attrCopy.setEntityID(pathEntityCopy.getId());

				_IDmappings_LR_to_SS.put(attrCopy.getId(),attr.getId());
				if (_IDmappings_SS_to_LR.get(attr.getId()) == null)
					_IDmappings_SS_to_LR.put(attr.getId(),new ArrayList<Integer>());
				_IDmappings_SS_to_LR.get(attr.getId()).add(attrCopy.getId());

				returnSchemaElements.add(attrCopy);

				// check to see if domain has been added
				if (domainSS_to_domainLR.get(attr.getDomainID()) == null){
					Domain attrDomain = relationalSchemaInfo.getDomainForElement(attr.getId());
					Domain attrDomainCopy = attrDomain.copy();
					attrDomainCopy.setId(getNextID());
					domainSS_to_domainLR.put(attrDomain.getId(), attrDomainCopy.getId());
					_IDmappings_LR_to_SS.put(attrDomainCopy.getId(), attrDomain.getId());

					if (_IDmappings_SS_to_LR.get(attrDomain.getId()) == null)
						_IDmappings_SS_to_LR.put(attrDomain.getId(),new ArrayList<Integer>());
					_IDmappings_SS_to_LR.get(attrDomain.getId()).add(attrDomainCopy.getId());

					returnSchemaElements.add(attrDomainCopy);
					for (DomainValue dv : relationalSchemaInfo.getDomainValuesForDomain(attrDomain.getId())){
						DomainValue dvCopy = dv.copy();
						dvCopy.setId(getNextID());
						dvCopy.setDomainID(attrDomainCopy.getId());
						_IDmappings_LR_to_SS.put(dvCopy.getId(),dv.getId());
						returnSchemaElements.add(dvCopy);
					}
				}
				attrCopy.setDomainID(domainSS_to_domainLR.get(attr.getDomainID()));
			}

			// add relationships
			for (SchemaElement se : schemaStoreSchemaInfo.getElements(Relationship.class)){
				
				if (_entityIndicesByRel.containsKey(se.getId())){
					
					Relationship relation = (Relationship)se;
					Integer leftIndex = _entityIndicesByRel.get(relation.getId()).get(0);
					Integer rightIndex = _entityIndicesByRel.get(relation.getId()).get(1);
					// if currentIndex is idTriple rightPathIndex, then copy relationship
					if (relation.getRightID().equals(_entitySet.get(currIndex).getId()) && rightIndex == currIndex){
	
						// create copy of this relationship
						Relationship rel = (Relationship)relationalSchemaInfo.getElement(relation.getId());
						Relationship relCopy = rel.copy();
						relCopy.setId(getNextID());
						relCopy.setLeftID(newPathEntityIDbyPosition[leftIndex]);
						relCopy.setRightID(newPathEntityIDbyPosition[rightIndex]);
	
						_IDmappings_LR_to_SS.put(relCopy.getId(),rel.getId());
						if (_IDmappings_SS_to_LR.get(rel.getId()) == null)
							_IDmappings_SS_to_LR.put(rel.getId(),new ArrayList<Integer>());
						_IDmappings_SS_to_LR.get(rel.getId()).add(relCopy.getId());
						returnSchemaElements.add(relCopy);
					}
				}
			}
		} 
		// create the SchemaInfo
		this._mappingSchemaInfo = new HierarchicalSchemaInfo(new SchemaInfo(newSchema, new ArrayList<Integer>(),returnSchemaElements),rmapModel);
		
	}

	/**
	 * createLogicalRelations: create the set of logical relations for a given
	 * schema by applying the relational chase to each entity in given schema
	 *
	 * @param schemaStoreInfo schemaInfo for schema
	 * @return list of logical relations
	 */
	public static ArrayList<LogicalRelation> createLogicalRelations(SchemaInfo schemaStoreInfo){

		// perform the relational chase
		ArrayList<LogicalRelation> logicalRelations = chase(schemaStoreInfo);

		// replace schemaInfo in each logical relation with
		for (LogicalRelation logRel : logicalRelations)
			logRel.generateLogicalRelationSchemaInfo(schemaStoreInfo);

		return logicalRelations;
	}


	/**
	 * chase: Perform the relational chase by following foreign key relationships
	 * @param inputSchemaInfo input schemaInfo to perform relational chase over
	 * @return list of LogicalRelations (one per entity in input schemaInfo
	 */
	private static ArrayList<LogicalRelation> chase(SchemaInfo inputSchemaInfo){

		ArrayList<LogicalRelation> logicalRelations = new ArrayList<LogicalRelation>();
		// get entity set from SchemaStore schemaInfo
		ArrayList<Entity> entitySet = new ArrayList<Entity>();
		for (SchemaElement se : inputSchemaInfo.getElements(Entity.class))
			entitySet.add((Entity)se);
		
		// create logical relation for each entity
		for (Entity entity : entitySet){

			LogicalRelation currLogRel = new LogicalRelation();
			HashSet<Integer> seenEdges = new HashSet<Integer>();
			ArrayList<Entity> queue = new ArrayList<Entity>();
			queue.add(entity);
			currLogRel.getEntitySet().add(entity);
			Integer leftPathIndex =  null;
			Integer rightPathIndex = currLogRel.getEntitySet().lastIndexOf(entity);
	
			while (queue.size() > 0){
				Entity leftPath = queue.remove(0);

				ArrayList<Relationship> relEdges = inputSchemaInfo.getRelationships(leftPath.getId());
				for (Relationship rel : relEdges){
					if ( rel.getLeftID().equals(leftPath.getId())&& !seenEdges.contains(rel.getId())){
						
						// update attribute sets; pathIndex is index of path in LogicalRelation's pathSet
						leftPathIndex = currLogRel.getEntitySet().lastIndexOf(leftPath);
						rightPathIndex = currLogRel.getEntitySet().size();
						ArrayList<Integer> indices = new ArrayList<Integer>();
						indices.add(leftPathIndex); indices.add(rightPathIndex);
						currLogRel._entityIndicesByRel.put(rel.getId(),indices);
						
						currLogRel.getEntitySet().add((Entity)inputSchemaInfo.getElement(rel.getRightID()));
						queue.add((Entity)inputSchemaInfo.getElement(rel.getRightID()));
						seenEdges.add(rel.getId());
					}
				}
			}
			logicalRelations.add(currLogRel);
		}
		return logicalRelations;
	}

} // end class LogicalRelation