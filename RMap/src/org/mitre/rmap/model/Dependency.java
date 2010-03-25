// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.rmap.model;

import java.util.*;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

public class Dependency {

	// store source logical relation
	private LogicalRelation _sourceLogRel;

	// store target logical relation
	private LogicalRelation _targetLogRel;

	// store set of constraints between source and target from mapping function
	private ArrayList<MappingCell> _coveredCorrespondences;

	// constant ID assigned to the base type 
	private static final Integer INTEGER_DOMAIN_ID = -1;
	public void updateCorrespondences(ArrayList<MappingCell> newCellList){
		_coveredCorrespondences = new ArrayList<MappingCell>();
		for (MappingCell corr : newCellList) {
			this._coveredCorrespondences.add(corr);
		}
	}

	public Dependency(LogicalRelation source, LogicalRelation target, ArrayList<MappingCell> corrs){
		_sourceLogRel = source.copy(); _targetLogRel = target.copy();
		_coveredCorrespondences = corrs;
	}

	public ArrayList<MappingCell> getCoveredCorrespondences() { return _coveredCorrespondences;}
	public LogicalRelation getSourceLogRel(){ return _sourceLogRel; }
	public LogicalRelation getTargetLogRel(){ return _targetLogRel; }


	// generate all of the information necessary to represent the dependency
	// as a mapping
	public Object[] generateMapping(Project project) {
		Object[] retVal = new Object[4];

		// generate return Mapping
		Schema sourceSchema = this._sourceLogRel.getMappingSchemaInfo().getSchema();
		Schema targetSchema = this._targetLogRel.getMappingSchemaInfo().getSchema();
		ProjectSchema sourceProjectSchema = new ProjectSchema(sourceSchema.getId(), sourceSchema.getName(), "RMap");
		ProjectSchema targetProjectSchema = new ProjectSchema(targetSchema.getId(), targetSchema.getName(), "RMap");
		Mapping mapping = new Mapping(LogicalRelation.getNextID(), project.getId(), sourceProjectSchema.getId(), targetProjectSchema.getId());

		// generate "translated" mapping cell for each covered correspondence
		ArrayList<MappingCell> mappingCells= new ArrayList<MappingCell>();
		for (MappingCell corr : _coveredCorrespondences) {
			// create copy of mapping cell for correspondence
			MappingCell cell = corr.copy();
			cell.setId(LogicalRelation.getNextID());
			cell.setMappingId(mapping.getId());

			try { 
				Integer sourceCount = this._sourceLogRel.getIDmappings_SS_to_LR().get(this._sourceLogRel.getIDmappings_LR_to_SS().get(cell.getInput()[0])).size();
				Integer targetCount = this._targetLogRel.getIDmappings_SS_to_LR().get(this._targetLogRel.getIDmappings_LR_to_SS().get(cell.getOutput())).size();
				// TODO: Need to modify here to add "color" back to lines
				if (sourceCount > 1 || targetCount > 1) {
					cell.setScore(0.1);
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			mappingCells.add(cell);
		}

		this._coveredCorrespondences = mappingCells;
		retVal[0] = mapping;
		retVal[1] = mappingCells;
		retVal[2] = _sourceLogRel.getMappingSchemaInfo();
		retVal[3] = _targetLogRel.getMappingSchemaInfo();
		return retVal;
	} // end method

	// generateDependencies(): For each pair of source and target logical relations,
	// 	generate a dependency for each possible coverage of covered correspondences 
	static public ArrayList<Dependency> generateDependencies(ArrayList<LogicalRelation> sourceLogRels, ArrayList<LogicalRelation> targetLogRels, ArrayList<MappingCell> correspondences) {
		ArrayList<Dependency> retVal = new ArrayList<Dependency>();
		for (LogicalRelation sourceLogRel : sourceLogRels ){
			for (LogicalRelation targetLogRel : targetLogRels){
				ArrayList<MappingCell> coveredCorrs = new ArrayList<MappingCell>();
				for (MappingCell corr : correspondences){
					if (sourceLogRel.translate_SS_to_LR(corr.getInput()) != null && targetLogRel.getIDmappings_SS_to_LR().get(corr.getOutput()) != null) {						
						MappingCell corrCopy = corr.copy();
						corrCopy.setInput(sourceLogRel.translate_SS_to_LR(corr.getInput()).get(0));
						corrCopy.setOutput(targetLogRel.getIDmappings_SS_to_LR().get(corr.getOutput()).get(0));
						coveredCorrs.add(corrCopy);
					}
				}
			
				if (coveredCorrs.size() > 0) {
					retVal.add(new Dependency(sourceLogRel, targetLogRel, coveredCorrs));
				}
			}
		}

		return retVal;
	}

	public static ArrayList<Dependency> generate(Integer mappingID, SchemaStoreClient client){
		// create logical relations for source / target schemas
		ArrayList<Dependency> retVal = new ArrayList<Dependency>();
		ArrayList<LogicalRelation> allSourceLogRels = new ArrayList<LogicalRelation>();
		ArrayList<LogicalRelation> allTargetLogRels = new ArrayList<LogicalRelation>();
		
		// find source / target graphs mentioned in the selected mapping
		try {
			// get the SchemaInfo objects for the source and target
			SchemaInfo sourceGraph = client.getSchemaInfo(client.getMapping(mappingID).getSourceId());
			SchemaInfo targetGraph = client.getSchemaInfo(client.getMapping(mappingID).getTargetId());

			// render the source and target
			renderRelational(sourceGraph);
			renderRelational(targetGraph);
			
			// add them to the list of relationals
			allSourceLogRels.addAll(LogicalRelation.createLogicalRelations(sourceGraph));
			allTargetLogRels.addAll(LogicalRelation.createLogicalRelations(targetGraph));

			// get correspondences
			ArrayList<MappingCell> schemaStoreCorrespondences = client.getMappingCells(mappingID);

			// create dependencies -- for each dependency, generate mapping graphs for each logical relation
			retVal = Dependency.generateDependencies(allSourceLogRels, allTargetLogRels, schemaStoreCorrespondences);
		} catch (Exception e){
			e.printStackTrace();
		}
		return retVal;
	} // end method generate()

	
	/**
	 * renderRelational: 
	 * @param input
	 * @return
	 */
	private static void renderRelational(SchemaInfo inGraph){
		Integer maxID = 0;
		for (SchemaElement se : inGraph.getElements(null)) {
			if (maxID < se.getId()) { maxID = se.getId(); }
		}
		maxID++;
		
		for (SchemaElement se : inGraph.getElements(Relationship.class)) {
			Relationship rel = (Relationship) se;
			
			
			// make sure LEFT FK --> RIGHT
			if (rel.getRightMax() == null || rel.getRightMax() != 1){
				Integer temp = rel.getRightID();
				rel.setRightID(rel.getLeftID());
				rel.setLeftID(temp);
				
				temp = rel.getRightMin();
				rel.setRightMin(rel.getLeftMin());
				rel.setLeftMin(temp);
				
				temp = rel.getRightMax();
				rel.setRightMax(rel.getLeftMax());
				rel.setLeftMax(temp);
			} 
		} 
		
		// if graph contains containments -- add ID column to entity
		if (inGraph.getElements(Containment.class).size() > 0) {
			if (inGraph.getElement(Dependency.INTEGER_DOMAIN_ID) == null) {
				if (!inGraph.addElement(new Domain(-1,"Integer","The Integer domain",inGraph.getSchema().getId()))) {
					System.err.println("RMap:renderRelational -- failed to add Integer domain");
				}
			}
			for (SchemaElement se : inGraph.getElements(Entity.class)) {
				Entity entity = (Entity)se;
				Attribute entityKey = new Attribute(maxID++,"ID","",entity.getId(),Dependency.INTEGER_DOMAIN_ID,1,1,true,inGraph.getSchema().getId());
				if (!inGraph.addElement(entityKey)) {
					System.err.println("RMap:renderRelational -- failed to add entityKey for entity " + entity.getName());
				}
			}
		}
		
		// replace each containment
		for (SchemaElement se : inGraph.getElements(Containment.class)) {
			Containment cont = (Containment) se;
			SchemaElement child = inGraph.getElement(cont.getChildID());
			
			// CASE: schema --> (entity | domain) ==> delete
			if (cont.getParentID() == null){
				if (!inGraph.deleteElement(cont.getId()))
					System.err.println("RMap:renderRelational -- failed to delete schema-level containement");
			}
			// CASE: entity --> entity ==> replace with relationship 
			else if (child instanceof Entity){
				Relationship rel = new Relationship(cont.getId(),cont.getName(),cont.getChildID(),cont.getMin(),cont.getMax(),cont.getParentID(),1,1,inGraph.getSchema().getId());
				if (!inGraph.deleteElement(cont.getId()))
					System.err.println("RMap:renderRelational -- failed to delete containement " + cont.getName());
				if (inGraph.addElement(rel) == false)
					System.err.println("RMap:renderRelational -- failed to add relationship " + rel.getName());	
			}
			
			// CASE: entity --> domain ==>
			//		a) max > 1 --> replace with table
			//		b) max == 1 --> replace with attribute
			
			else if (child instanceof Domain) {
				if (cont.getMax() != 1) {
					String name = cont.getName() +"." + inGraph.getElement(cont.getParentID()).getName();
					Entity domEntity = new Entity(maxID++,name,"desc",inGraph.getSchema().getId());
					Relationship rel = new Relationship(maxID++,name,domEntity.getId(),cont.getMin(),cont.getMax(),cont.getParentID(),1,1,inGraph.getSchema().getId());
					Attribute domAttr = new Attribute(cont.getId(),cont.getName(),cont.getDescription(),domEntity.getId(),child.getId(),0,1,false,inGraph.getSchema().getId());
					Attribute domEntityKey = new Attribute(maxID++,"ID","",domEntity.getId(),Dependency.INTEGER_DOMAIN_ID,1,1,true,inGraph.getSchema().getId());
					//Attribute domEntityFK = new Attribute(maxID++,name,"",domEntity);//(maxID++,name,"",domEntity.getId(),);

					if (!inGraph.deleteElement(cont.getId()))
						System.err.println("RMap:renderRelational -- failed to delete containement");
					if (!inGraph.addElement(domEntity))
						System.err.println("RMap:renderRelational -- failed to add domEntity");
					if (!inGraph.addElement(rel))
						System.err.println("RMap:renderRelational -- failed to add relationship");
					if (!inGraph.addElement(domAttr))
						System.err.println("RMap:renderRelational -- failed to add domAttr");
					if (!inGraph.addElement(domEntityKey))
						System.err.println("RMap:renderRelational -- failed to add domEntityKey");
				} else {
					Attribute attr = new Attribute(cont.getId(),cont.getName(),cont.getDescription(),cont.getParentID(),cont.getChildID(),cont.getMin(),cont.getMax(),false,inGraph.getSchema().getId());

					if (!inGraph.deleteElement(cont.getId()))
						System.err.println("RMap:renderRelational -- failed to delete containement");
					if (!inGraph.addElement(attr))
						System.err.println("RMap:renderRelational -- failed to add attribute " + attr.getName());
				}
			}
		}
	}
		
} // end class
