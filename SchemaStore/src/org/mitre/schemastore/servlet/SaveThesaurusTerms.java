// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.mitre.schemastore.data.DataManager;
import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Synonym;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.ThesaurusTerms;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/**
 * Handles the saving of a thesaurus to the schema store web service
 * @author CWOLF
 */
public class SaveThesaurusTerms
{				
	/** Generates a list of terms aligned with repository IDs */
	static private ArrayList<Term> getAlignedTerms(DataManager manager, ThesaurusTerms oldThesaurus, ThesaurusTerms newThesaurus)
	{
		// Gather up references to old terms
		HashMap<Integer,Term> oldTerms = new HashMap<Integer,Term>();
		for(Term term : oldThesaurus.getTerms())
			oldTerms.put(term.getId(), term);
		
		// Align terms while adjusting term name changes
		ArrayList<Term> alignedTerms = new ArrayList<Term>();
		for(Term term : newThesaurus.getTerms())
		{
			// Set the aligned term
			Term oldTerm = oldTerms.get(term.getId());
			if(oldTerm==null) term.setId(null);
			for(AssociatedElement element : term.getElements())
			{
				boolean elementExists = false;
				if(oldTerm!=null)
					for(AssociatedElement oldElement : oldTerm.getElements())
						if(oldElement.getElementID().equals(element.getElementID()))
							elementExists = true;
				if(!elementExists) element.setElementID(null);
			}
			alignedTerms.add(term);
		}
		return alignedTerms;
	}
	
	/** Adds new terms to the vocabulary */
	static private void addNewTerms(DataManager manager, SchemaInfo schemaInfo, ArrayList<Term> terms) throws Exception
	{
		// Identify all terms the need to be created
		ArrayList<Term> newTerms = new ArrayList<Term>();
		for(Term term : terms)
		{
			if(term.getId()!=null)
			{
				// Swap out term names in the vocabulary schema if needed
				SchemaElement oldElement = schemaInfo.getElement(term.getId());
				String description = term.getDescription()==null ? "" : term.getDescription();
				if(!term.getName().equals(oldElement.getName()) || !description.equals(oldElement.getDescription()))
				{
					SchemaElement element = oldElement.copy();
					element.setName(term.getName());
					element.setDescription(term.getDescription());
					if(!manager.getSchemaElementCache().updateSchemaElement(element))
						throw new Exception("Failed to update thesaurus term");
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
			newEntities.add(new Entity(id,term.getName(),"",schemaInfo.getSchema().getId()));
		}
		
		// Add the new terms to the vocabulary schema
		if(!manager.getSchemaElementCache().addSchemaElements(newEntities))
			throw new Exception("Failed to update thesaurus terms");
	}

	/** Adjusts the synonyms in the thesaurus */
	static private void adjustSynonyms(DataManager manager, SchemaInfo schemaInfo, List<Term> oldTerms, ArrayList<Term> newTerms) throws Exception
	{
		// Create a hash of all old terms
		HashMap<Integer,Term> oldTermHash = new HashMap<Integer,Term>();
		for(Term oldTerm : oldTerms) oldTermHash.put(oldTerm.getId(), oldTerm);
		
		// Store the list of new and updated synonyms
		ArrayList<AssociatedElement> newElements = new ArrayList<AssociatedElement>();
		ArrayList<SchemaElement> updatedSynonyms = new ArrayList<SchemaElement>();
		
		/** Cycle through all terms to examine associated elements */
		for(Term newTerm : newTerms)
		{
			// Get the associated old term
			Term oldTerm = oldTermHash.get(newTerm.getId());
			
			// Identify which synonyms to add and update
			for(AssociatedElement element : newTerm.getElements())
			{
				if(element.getElementID()!=null)
				{
					// Swap out term names in element already exists
					SchemaElement oldElement = schemaInfo.getElement(element.getElementID());
					String description = element.getDescription()==null ? "" : element.getDescription();
					if(!element.getName().equals(oldElement.getName()) || !description.equals(oldElement.getDescription()))
					{
						SchemaElement updatedElement = oldElement.copy();
						updatedElement.setName(element.getName());
						updatedElement.setDescription(element.getDescription());
						updatedSynonyms.add(updatedElement);
					}
				}
				else
				{
					element.setElementID(newTerm.getId());
					newElements.add(element);
				}
			}
			
			// Generate a hash map of all new associated elements
			HashMap<Integer,AssociatedElement> newElementHash = new HashMap<Integer,AssociatedElement>();
			for(AssociatedElement newElement : newTerm.getElements())
				newElementHash.put(newElement.getElementID(), newElement);
			
			// Delete old synonyms
			if(oldTerm!=null)
				for(AssociatedElement oldElement : oldTerm.getElements())
					if(!newElementHash.containsKey(oldElement.getElementID()))
						if(!manager.getSchemaElementCache().deleteSchemaElement(oldElement.getElementID()))
							throw new Exception("Failed to delete thesaurus synonym");
		}

		// Create the new synonyms
		ArrayList<SchemaElement> newSynonyms = new ArrayList<SchemaElement>();
		Integer counter = manager.getUniversalIDs(newElements.size());
		for(AssociatedElement element : newElements)
			newSynonyms.add(new Synonym(counter++,element.getName(),element.getDescription(),element.getElementID(),schemaInfo.getSchema().getId()));
		
		// Add the new and updated synonyms to the thesaurus schema
		boolean success = manager.getSchemaElementCache().addSchemaElements(newSynonyms);
		if(success) success = manager.getSchemaElementCache().updateSchemaElements(updatedSynonyms);
		if(!success) throw new Exception("Failed to update vocabulary schema");
	}
	
	/** Removes old terms from the thesaurus */
	static private void removeOldTerms(DataManager manager, List<Term> oldTerms, ArrayList<Term> newTerms) throws Exception
	{
		// Create a hash of all new terms
		HashMap<Integer,Term> newTermHash = new HashMap<Integer,Term>();
		for(Term newTerm : newTerms) newTermHash.put(newTerm.getId(), newTerm);

		// Remove all terms which are no longer used
		for(Term term : oldTerms)
			if(!newTermHash.containsKey(term.getId()))
				if(!manager.getSchemaElementCache().deleteSchemaElement(term.getId()))
					throw new Exception("Failed to delete thesaurus term");
	}
	
	/** Saves the specified thesaurus terms into the web services */
	static boolean saveThesaurusTerms(DataManager manager, ThesaurusTerms terms) throws RemoteException
	{		
		try {
			// Retrieve the referenced thesaurus
			Schema schema = manager.getSchemaCache().getSchema(terms.getThesaurusId());
			if(schema==null) throw new Exception("Thesaurus does not exist");
			ArrayList<SchemaElement> elements = manager.getSchemaElementCache().getSchemaElements(terms.getThesaurusId());
			SchemaInfo schemaInfo = new SchemaInfo(schema, new ArrayList<Integer>(), elements);
			
			// Add new terms to the thesaurus
			ThesaurusTerms oldTerms = GetThesaurusTerms.getThesaurusTerms(manager,terms.getThesaurusId());
			ArrayList<Term> alignedTerms = getAlignedTerms(manager,oldTerms,terms);

			// Set all new, modified, and removed terms
			addNewTerms(manager, schemaInfo, alignedTerms);
			adjustSynonyms(manager, schemaInfo, Arrays.asList(oldTerms.getTerms()), alignedTerms);
			removeOldTerms(manager, Arrays.asList(oldTerms.getTerms()), alignedTerms);
		}
		catch(Exception e) { System.out.println("(E) SaveThesaurusTerms:saveThesaurusTerms "+e.getMessage()); return false; }
		return true;
	}
}