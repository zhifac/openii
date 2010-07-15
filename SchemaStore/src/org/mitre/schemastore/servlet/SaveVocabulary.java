// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.mitre.schemastore.data.DataManager;
import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.Vocabulary;
import org.mitre.schemastore.model.mappingInfo.MappingInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/**
 * Handles the saving of a vocabulary to the schema store web service
 * @author CWOLF
 */
public class SaveVocabulary
{			
	/** Retrieves the specified schema */
	static private SchemaInfo getSchema(DataManager manager, Integer schemaID)
	{
		Schema schema = manager.getSchemaCache().getSchema(schemaID);
		ArrayList<Integer> parentIDs = manager.getSchemaRelationshipCache().getParents(schemaID);
		ArrayList<SchemaElement> elements = manager.getSchemaElementCache().getSchemaElements(schemaID);
		return new SchemaInfo(schema, parentIDs, elements);
	}
	
	/** Generates a list of terms aligned with repository IDs */
	static private ArrayList<Term> getAlignedTerms(DataManager manager, Vocabulary oldVocabulary, Vocabulary newVocabulary)
	{
		// Gather up references to old terms
		HashMap<Integer,Term> oldTerms = new HashMap<Integer,Term>();
		for(Term term : oldVocabulary.getTerms())
			oldTerms.put(term.getId(), term);
		
		// Align terms while adjusting term name changes
		ArrayList<Term> alignedTerms = new ArrayList<Term>();
		for(Term term : newVocabulary.getTerms())
		{
			// Set the aligned term
			Term oldTerm = oldTerms.get(term.getId());
			if(oldTerm==null) term.setId(null);
			alignedTerms.add(term);
		}
		return alignedTerms;
	}
	
	/** Generates a list of mappings based on the vocabulary */
	static private HashMap<Integer,MappingInfo> getMappings(DataManager manager, Project project, Integer vocabularyID, List<Term> terms) throws Exception
	{
		// Retrieve the project schemas for validation
		HashMap<Integer,SchemaInfo> schemas = new HashMap<Integer,SchemaInfo>();
		for(Integer schemaID : project.getSchemaIDs())
			schemas.put(schemaID, getSchema(manager,schemaID));
		
		// Generate the mappings
		HashMap<Integer,MappingInfo> mappings = new HashMap<Integer,MappingInfo>();
		Date date = Calendar.getInstance().getTime();
		for(Term term : terms)
			for(AssociatedElement element : term.getElements())
			{
				// Verify that the schema and element exist
				SchemaInfo schema = schemas.get(element.getSchemaID());
				if(schema==null || !schema.containsElement(element.getElementID()))
					throw new Exception("Invalid schemas or elements referenced in vocabulary!");
				
				// Get the mapping for which a cell needs to be added
				MappingInfo mappingInfo = mappings.get(element.getSchemaID());
				if(mappingInfo==null)
				{
					Mapping mapping = new Mapping(null,project.getId(),element.getSchemaID(),vocabularyID);
					mappingInfo = new MappingInfo(mapping,new ArrayList<MappingCell>());
					mappings.put(element.getSchemaID(),mappingInfo);
				}
				
				// Adds a mapping cell 
				MappingCell mappingCell = MappingCell.createIdentityMappingCell(null, mappingInfo.getId(), element.getElementID(), term.getId(), "AUTO", date, null);
				mappingInfo.getMappingCells().set(mappingCell);
			}
		return mappings;
	}
	
	/** Adds new terms to the vocabulary */
	static private void addNewTerms(DataManager manager, Integer vocabularyID, List<Term> terms) throws Exception
	{
		// Retrieve the vocabulary schema
		SchemaInfo schema = getSchema(manager, vocabularyID);
		
		// Identify all terms the need to be created
		ArrayList<Term> newTerms = new ArrayList<Term>();
		for(Term term : terms)
		{
			if(term.getId()!=null)
			{
				// Swap out term names in the vocabulary schema if needed
				SchemaElement oldElement = schema.getElement(term.getId());
				String description = term.getDescription()==null ? "" : term.getDescription();
				if(!term.getName().equals(oldElement.getName()) || !description.equals(oldElement.getDescription()))
				{
					SchemaElement element = manager.getSchemaElementCache().getSchemaElement(term.getId());
					element.setName(term.getName());
					element.setDescription(term.getDescription());
					manager.getSchemaElementCache().updateSchemaElement(element);
				}
			}
			else newTerms.add(term);
		}		
		
		// Create the new terms
		Integer counter = manager.getUniversalIDs(newTerms.size());
		ArrayList<SchemaElement> newEntities = new ArrayList<SchemaElement>();
		for(Term term : newTerms)
		{
			Integer id = counter++;
			term.setId(id);
			newEntities.add(new Entity(id,term.getName(),"",vocabularyID));
		}
		
		// Add the new terms to the vocabulary schema
		if(!manager.getSchemaElementCache().addSchemaElements(newEntities))
			throw new Exception("Failed to update vocabulary schema");
	}
	
	/** Updates the vocabulary mappings */
	static private void updateMappings(DataManager manager, Integer projectID, Integer vocabularyID, HashMap<Integer,MappingInfo> mappings) throws Exception
	{
		// Populate vocabulary mappings
		ArrayList<Mapping> oldMappings = manager.getProjectCache().getVocabularyMappings(projectID);
		for(MappingInfo mapping : mappings.values())
		{
			// Gets the mapping ID (as referenced in the old vocabulary mappings)
			Integer mappingID = null;
			for(Mapping oldMapping : oldMappings)
				if(oldMapping.getSourceId().equals(mapping.getSourceID()) && oldMapping.getTargetId().equals(mapping.getTargetID()))
					{ mappingID = oldMapping.getId(); oldMappings.remove(oldMapping); break; }

			// If no mapping exists, create new mapping
			if(mappingID==null)
				mappingID = manager.getProjectCache().addMapping(mapping.getMapping());
			if(mappingID==null) throw new Exception("Failed to create a vocabulary mapping for " + manager.getSchemaCache().getSchema(mapping.getSourceID()));
			
			// Save mapping cells to the mapping
			boolean success = SaveMappingCells.saveMappingCells(manager, mappingID, mapping.getMappingCells().get());
			if(!success) throw new Exception("Failed to save mapping cells to the vocabulary mapping for " + manager.getSchemaCache().getSchema(mapping.getSourceID()));
		}
		
		// Delete any eliminated vocabulary mappings
		for(Mapping oldMapping : oldMappings)
			if(!manager.getProjectCache().deleteMapping(oldMapping.getId()))
				throw new Exception("Failed to delete the vocabulary mapping for " + manager.getSchemaCache().getSchema(oldMapping.getSourceId()));
	}
	
	/** Removes old terms from the vocabulary */
	static private boolean removeOldTerms(DataManager manager, List<Term> oldTerms, List<Term> newTerms)
	{
		// Generate a hash of all new term references
		HashSet<Integer> termIDs = new HashSet<Integer>();
		for(Term term : newTerms) termIDs.add(term.getId());
		
		// Identify all old terms that are no longer used
		boolean success = true;
		for(Term term : oldTerms)
			success &= manager.getSchemaElementCache().deleteSchemaElement(term.getId());
		return success;
	}
	
	/** Saves the specified vocabulary into the web services */
	static boolean saveVocabulary(DataManager manager, Vocabulary vocabulary) throws RemoteException
	{		
		try {
			// Get the referenced project
			Integer projectID = vocabulary.getProjectID();
			Project project = manager.getProjectCache().getProject(projectID);
			if(project==null) throw new Exception("Vocabulary contains invalid project");
		
			// First get the old vocabulary (or create if necessary)
			Integer vocabularyID = manager.getProjectCache().getVocabularyID(vocabulary.getProjectID());
			if(vocabularyID==null)
			{
				Schema schema = new Schema(null,"Vocabulary for " + project.getName(),"AUTO",null,null,null,false);
				vocabularyID = manager.getSchemaCache().addSchema(schema);
				if(vocabularyID==null || !manager.getProjectCache().setVocabularyID(projectID,vocabularyID))
					throw new Exception("Failed to create vocabulary schema");
			}
			Vocabulary oldVocabulary = GetVocabulary.getVocabulary(manager,projectID);
	
			// Align the terms between the old and new vocabulary
			ArrayList<Term> alignedTerms = getAlignedTerms(manager,oldVocabulary,vocabulary);
			HashMap<Integer,MappingInfo> mappings = getMappings(manager, project, vocabularyID, alignedTerms);

			// Swap out terms from the vocabulary
			addNewTerms(manager, vocabularyID, alignedTerms);
			updateMappings(manager, projectID, vocabularyID, mappings);
			removeOldTerms(manager, Arrays.asList(oldVocabulary.getTerms()), alignedTerms);
		}
		catch(Exception e) { System.out.println("(E) VocabularyCache:setVocabulary: "+e.getMessage()); return false; }
		return true;
	}
}