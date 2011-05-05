// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.data.DataManager;
import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Synonym;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.ThesaurusTerms;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/**
 * Handles the retrieval of a thesaurus' terms from the schema store web service
 * @author CWOLF
 */
public class GetThesaurusTerms
{	
	/** Gets the specified thesaurus from the web services */
	static ThesaurusTerms getThesaurusTerms(DataManager manager, Integer thesaurusID) throws RemoteException
	{		
		// Get the thesaurus
		Schema thesaurus = manager.getSchemaCache().getSchema(thesaurusID);
		ArrayList<SchemaElement> elements = manager.getSchemaElementCache().getSchemaElements(thesaurusID);
		SchemaInfo schemaInfo = new SchemaInfo(thesaurus, new ArrayList<Integer>(),elements);

		// Generate the thesaurus terms
		HashMap<Integer,Term> terms = new HashMap<Integer,Term>();
		for(SchemaElement element : schemaInfo.getElements(Synonym.class))
		{
			Synonym synonym = (Synonym)element;
			
			// Get the term
			Term term = terms.get(synonym.getElementID());
			if(term==null)
			{
				SchemaElement termElement = schemaInfo.getElement(synonym.getElementID());
				terms.put(termElement.getId(),new Term(termElement.getId(),termElement.getName(),termElement.getDescription()));
			}

			// Add the synonym
			term.addAssociatedElement(new AssociatedElement(thesaurusID,synonym.getId(),synonym.getName(),synonym.getDescription()));
		}
		
		// Generate the thesaurus terms
		return new ThesaurusTerms(thesaurusID, terms.values().toArray(new Term[0]));
	}
}