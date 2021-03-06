// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.schemastore.data.DataManager;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Vocabulary;
import org.mitre.schemastore.model.mappingInfo.MappingInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.model.terms.AssociatedElement;
import org.mitre.schemastore.model.terms.Term;
import org.mitre.schemastore.model.terms.VocabularyTerms;

/**
 * Handles the saving of a vocabulary's terms to the schema store web service
 * @author CWOLF
 */
public class SaveVocabularyTerms
{			
	/** Retrieves the specified schema */
	static private SchemaInfo getSchema(DataManager manager, Integer schemaID)
	{
		Schema schema = manager.getSchemaCache().getSchema(schemaID);
		ArrayList<Integer> parentIDs = manager.getSchemaRelationshipCache().getParents(schemaID);
		ArrayList<SchemaElement> elements = manager.getSchemaElementCache().getSchemaElements(schemaID);
		return new SchemaInfo(schema, parentIDs, elements);
	}
	
	/** Validate terms against the schemas they are referencing */
	static private boolean validateTerms(HashMap<Integer,SchemaInfo> schemas, Term[] terms)
	{
		for(Term term : terms)
			for(AssociatedElement element : term.getElements())
			{
				// Verify that the schema and element exist
				SchemaInfo schema = schemas.get(element.getSchemaID());
				if(schema==null || !schema.containsElement(element.getElementID()))
					return false;
			}
		return true;
	}
	
	/** Generates a list of terms aligned with repository IDs */
	static private void alignTerms(DataManager manager, VocabularyTerms oldVocabulary, VocabularyTerms newVocabulary)
	{
		// Gather up references to old terms
		HashMap<Integer,Term> oldTerms = new HashMap<Integer,Term>();
		for(Term term : oldVocabulary.getTerms())
			oldTerms.put(term.getId(), term);
		
		// Scrub the temporary IDs off terms which don't yet have universal IDs 
		for(Term term : newVocabulary.getTerms())
		{
			// Set the aligned term
			Term oldTerm = oldTerms.get(term.getId());
			if(oldTerm==null) term.setId(null);
		}
	}
	
	/** Generates a list of mappings based on the vocabulary */
	static private HashMap<Integer,MappingInfo> getMappings(DataManager manager, Project project, Integer vocabularyID, Term[] terms) throws Exception
	{
		// Generate the mappings
		HashMap<Integer,MappingInfo> mappings = new HashMap<Integer,MappingInfo>();
		Date date = Calendar.getInstance().getTime();
		for(Term term : terms)
			for(AssociatedElement element : term.getElements())
			{
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
	static private void addNewTerms(DataManager manager, Integer vocabularyID, Term[] terms) throws Exception
	{
		// Retrieve the vocabulary schema
		SchemaInfo schema = getSchema(manager, vocabularyID);
		
		// Identify all terms the need to be created and updated
		ArrayList<Term> newTerms = new ArrayList<Term>();
		ArrayList<SchemaElement> updatedElements = new ArrayList<SchemaElement>();
		for(Term term : terms)
		{
			if(term.getId()!=null)
			{
				// Swap out term names in the vocabulary schema if needed
				SchemaElement oldElement = schema.getElement(term.getId());
				String description = term.getDescription()==null ? "" : term.getDescription();
				if(!term.getName().equals(oldElement.getName()) || !description.equals(oldElement.getDescription()))
				{
					SchemaElement updatedElement = oldElement.copy();
					updatedElement.setName(term.getName());
					updatedElement.setDescription(term.getDescription());
					updatedElements.add(updatedElement);
				}
			}
			else newTerms.add(term);
		}		
		
		// Create the new terms
		Integer counter = manager.getUniversalIDs(newTerms.size());
		ArrayList<SchemaElement> newElements = new ArrayList<SchemaElement>();
		for(Term term : newTerms)
		{
			Integer id = counter++;
			term.setId(id);
			newElements.add(new Entity(id,term.getName(),"",vocabularyID));
		}
		
		// Add the new and updated terms to the vocabulary schema
		boolean success = manager.getSchemaElementCache().addSchemaElements(newElements);
		if(success) success = manager.getSchemaElementCache().updateSchemaElements(updatedElements);
		if(!success) throw new Exception("Failed to update vocabulary schema");
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
	static private boolean removeOldTerms(DataManager manager, Term[] oldTerms, Term[] newTerms)
	{
		// Generate a hash of all new term references
		HashSet<Integer> termIDs = new HashSet<Integer>();
		for(Term term : newTerms) termIDs.add(term.getId());
		
		// Identify all old terms that are no longer used
		boolean success = true;
		for(Term term : oldTerms)
			if(!termIDs.contains(term.getId()))
				success &= manager.getSchemaElementCache().deleteSchemaElement(term.getId());
		return success;
	}
	
	/** Saves the specified vocabulary into the web services */
	static VocabularyTerms saveVocabularyTerms(DataManager manager, VocabularyTerms terms) throws RemoteException
	{		
		try {
			// Get the referenced project
			Integer projectID = terms.getProjectID();
			Project project = manager.getProjectCache().getProject(projectID);
			if(project==null) throw new Exception("Vocabulary contains invalid project");
	
			// Retrieve the referenced project schemas
			HashMap<Integer,SchemaInfo> schemas = new HashMap<Integer,SchemaInfo>();
			for(Integer schemaID : project.getSchemaIDs())
				schemas.put(schemaID, getSchema(manager,schemaID));

			// Validate the terms to make sure they all reference valid elements
			if(!validateTerms(schemas,terms.getTerms()))
				throw new Exception("Invalid schemas or elements referenced in vocabulary!");
			
			// First get the old vocabulary (or create if necessary)
			Integer vocabularyID = manager.getProjectCache().getVocabularyID(terms.getProjectID());
			if(vocabularyID==null)
			{
				Vocabulary vocabulary = new Vocabulary(null,"Vocabulary for " + project.getName());
				vocabularyID = manager.getSchemaCache().addSchema(vocabulary);
				if(vocabularyID==null || !manager.getProjectCache().setVocabularyID(projectID,vocabularyID))
					throw new Exception("Failed to create vocabulary schema");
			}
			VocabularyTerms oldTerms = GetVocabularyTerms.getVocabularyTerms(manager,projectID);
	
			// Align the terms between the old and new vocabulary
			alignTerms(manager,oldTerms,terms);
			
			// Swap out terms from the vocabulary
			addNewTerms(manager, vocabularyID, terms.getTerms());
			updateMappings(manager, projectID, vocabularyID, getMappings(manager, project, vocabularyID, terms.getTerms()));
			removeOldTerms(manager, oldTerms.getTerms(), terms.getTerms());
		}
		catch(Exception e) { System.out.println("(E) SaveVocabularyTerms:saveVocabularyTerms: "+e.getMessage()); return null; }
		return terms;
	}
}