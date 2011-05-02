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
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.Thesaurus;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/**
 * Handles the saving of a thesaurus to the schema store web service
 * @author CWOLF
 */
public class SaveThesaurus
{				
	/** Generates a list of terms aligned with repository IDs */
	static private HashMap<Integer,Term> getAlignedTerms(DataManager manager, Thesaurus oldThesaurus, Thesaurus newThesaurus)
	{
		// Gather up references to old terms
		HashMap<Integer,Term> oldTerms = new HashMap<Integer,Term>();
		for(Term term : oldThesaurus.getTerms())
			oldTerms.put(term.getId(), term);
		
		// Align terms while adjusting term name changes
		HashMap<Integer,Term> alignedTerms = new HashMap<Integer,Term>();
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
			alignedTerms.put(term.getId(),term);
		}
		return alignedTerms;
	}
	
	/** Adds new terms to the vocabulary */
	static private void addNewTerms(DataManager manager, SchemaInfo schemaInfo, HashMap<Integer,Term> terms) throws Exception
	{
		// Identify all terms the need to be created
		ArrayList<Term> newTerms = new ArrayList<Term>();
		for(Term term : terms.values())
		{
			if(term.getId()!=null)
			{
				// Swap out term names in the vocabulary schema if needed
				SchemaElement oldElement = schemaInfo.getElement(term.getId());
				String description = term.getDescription()==null ? "" : term.getDescription();
				if(!term.getName().equals(oldElement.getName()) || !description.equals(oldElement.getDescription()))
				{
					SchemaElement element = manager.getSchemaElementCache().getSchemaElement(term.getId());
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
	static private void adjustSynonyms(DataManager manager, List<Term> oldTerms, HashMap<Integer,Term> newTerms) throws Exception
	{
		/** Cycle through all terms to examine associated elements */
		for(Term oldTerm : oldTerms)
		{
			// Get the associated new term
			Term newTerm = newTerms.get(oldTerm.getId());
			
			// Generate a hash map of all old associated elements
			HashMap<Integer,AssociatedElement> oldElements = new HashMap<Integer,AssociatedElement>();
			for(AssociatedElement oldElement : oldTerm.getElements())
				oldElements.put(oldElement.getElementID(), oldElement);

			// Generate a hash map of all new associated elements
			HashMap<Integer,AssociatedElement> newElements = new HashMap<Integer,AssociatedElement>();
			for(AssociatedElement newElement : newTerm.getElements())
				newElements.put(newElement.getElementID(), newElement);
			
			// Add new associated elements
//			for(AssociatedElement newElement : newElements.values())
//			{
//				if(newElement!=null)
//				{
//					
//				}
//				else
//			}
//			
//			// Delete old associated elements
//			for(AssociatedElement oldElement : oldElements.values())
//				if(!newElements.containsKey(oldElement.getElementID()))
//					if(!manager.getSchemaElementCache().deleteSchemaElement(oldElement.getElementID()))
//						throw new Exception("Failed to delete thesaurus synonym");
		}
	}
	
	/** Removes old terms from the thesaurus */
	static private void removeOldTerms(DataManager manager, List<Term> oldTerms, HashMap<Integer,Term> newTerms) throws Exception
	{
		for(Term term : oldTerms)
			if(!newTerms.containsKey(term.getId()))
				if(!manager.getSchemaElementCache().deleteSchemaElement(term.getId()))
					throw new Exception("Failed to delete thesaurus term");
	}
	
	/** Saves the specified thesaurus into the web services */
	static boolean saveThesaurus(DataManager manager, Thesaurus thesaurus) throws RemoteException
	{		
		try {
			// Retrieve the referenced thesaurus
			Schema schema = manager.getSchemaCache().getSchema(thesaurus.getId());
			if(schema==null) throw new Exception("Thesaurus does not exist");
			ArrayList<SchemaElement> elements = manager.getSchemaElementCache().getSchemaElements(thesaurus.getId());
			SchemaInfo schemaInfo = new SchemaInfo(schema, new ArrayList<Integer>(), elements);
			
			// Align the terms between the old and new thesaurus
			Thesaurus oldThesaurus = GetThesaurus.getThesaurus(manager,thesaurus.getId());
			HashMap<Integer,Term> alignedTerms = getAlignedTerms(manager,oldThesaurus,thesaurus);
			
			// Swap out terms from the thesaurus
			addNewTerms(manager, schemaInfo, alignedTerms);
			adjustSynonyms(manager, Arrays.asList(oldThesaurus.getTerms()), alignedTerms);
			removeOldTerms(manager, Arrays.asList(oldThesaurus.getTerms()), alignedTerms);
		}
		catch(Exception e) { System.out.println("(E) SaveThesaurus:saveThesaurus "+e.getMessage()); return false; }
		return true;
	}
}