// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.mitre.schemastore.data.DataManager;
import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.Vocabulary;

/**
 * Handles the retrieval of a vocabulary from the schema store web service
 * @author CWOLF
 */
public class GetVocabulary
{		
	/** Retrieves the schema elements for the specified schema ID */
	static private HashMap<Integer,SchemaElement> getSchemaElements(DataManager manager, Integer schemaID)
	{
		HashMap<Integer,SchemaElement> elements = new HashMap<Integer,SchemaElement>();
		for(SchemaElement element : manager.getSchemaElementCache().getSchemaElements(schemaID))
			elements.put(element.getId(), element);
		return elements;
	}
	
	/** Gets the specified vocabulary from the web services */
	static Vocabulary getVocabulary(DataManager manager, Integer projectID) throws RemoteException
	{		
		// Get the vocabulary schema
		Integer vocabularyID = manager.getProjectCache().getVocabularyID(projectID);
		if(vocabularyID==null) return null;
		HashMap<Integer,SchemaElement> vocabularyElements = getSchemaElements(manager, vocabularyID);		
		
		// Get the vocabulary mappings
		ArrayList<Mapping> mappings = manager.getProjectCache().getVocabularyMappings(projectID);
		
		// Generate the vocabulary terms
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		HashMap<Integer,Term> terms = new HashMap<Integer,Term>();
		for(Mapping mapping : mappings)
		{
			// Get the ID of the schema involved in the vocabulary
			Integer schemaID = mapping.getSourceId();
			schemaIDs.add(schemaID);
			
			// Add the mapped elements to the vocabulary term list
			HashMap<Integer,SchemaElement> schemaElements = getSchemaElements(manager,schemaID);
			for(MappingCell mappingCell : manager.getProjectCache().getMappingCells(mapping.getId()))
			{
				// Get the term
				Integer termID = mappingCell.getOutput();
				Term term = terms.get(termID);
				if(term==null) term = new Term(termID,vocabularyElements.get(termID).getName(),new AssociatedElement[0]);
				
				// Add the associated element
				SchemaElement element = schemaElements.get(mappingCell.getInput()[0]);
				AssociatedElement associatedElement = new AssociatedElement(schemaID, element.getId(), element.getName());
				List<AssociatedElement> associatedElements = Arrays.asList(term.getElements());
				associatedElements.add(associatedElement);
			}
		}
		
		// Generate the vocabulary
		return new Vocabulary(projectID, schemaIDs.toArray(new Integer[0]), terms.values().toArray(new Term[0]));
	}
}