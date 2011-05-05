// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.data.DataManager;
import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.VocabularyTerms;

/**
 * Handles the retrieval of vocabulary terms from the schema store web service
 * @author CWOLF
 */
public class GetVocabularyTerms
{		
	/** Retrieves the schema elements for the specified schema ID */
	static private HashMap<Integer,SchemaElement> getSchemaElements(DataManager manager, Integer schemaID)
	{
		HashMap<Integer,SchemaElement> elements = new HashMap<Integer,SchemaElement>();
		for(SchemaElement element : manager.getSchemaElementCache().getSchemaElements(schemaID))
			elements.put(element.getId(), element);
		return elements;
	}
	
	/** Gets the specified vocabulary terms from the web services */
	static VocabularyTerms getVocabularyTerms(DataManager manager, Integer projectID) throws RemoteException
	{		
		// Get the vocabulary schema
		Integer vocabularyID = manager.getProjectCache().getVocabularyID(projectID);
		if(vocabularyID==null) return null;
		HashMap<Integer,SchemaElement> vocabularyElements = getSchemaElements(manager, vocabularyID);		
		
		// Generate the vocabulary terms
		HashMap<Integer,Term> terms = new HashMap<Integer,Term>();
		for(SchemaElement element : vocabularyElements.values())
			terms.put(element.getId(), new Term(element.getId(),element.getName(),element.getDescription()));			
		
		// Get the associated elements for the vocabulary terms
		ArrayList<Mapping> mappings = manager.getProjectCache().getVocabularyMappings(projectID);
		for(Mapping mapping : mappings)
		{
			// Get the ID of the schema involved in the vocabulary
			Integer schemaID = mapping.getSourceId();
			
			// Add the mapped elements to the vocabulary term list
			HashMap<Integer,SchemaElement> schemaElements = getSchemaElements(manager,schemaID);
			for(MappingCell mappingCell : manager.getProjectCache().getMappingCells(mapping.getId()))
			{
				// Get the term
				Integer termID = mappingCell.getOutput();
				Term term = terms.get(termID);
				
				// Add the associated element
				SchemaElement element = schemaElements.get(mappingCell.getInputs()[0].getElementID());
				AssociatedElement associatedElement = new AssociatedElement(schemaID, element.getId(), element.getName(), element.getDescription());
				term.addAssociatedElement(associatedElement);
			}
		}
		
		// Generate the vocabulary
		return new VocabularyTerms(projectID, terms.values().toArray(new Term[0]));
	}
}