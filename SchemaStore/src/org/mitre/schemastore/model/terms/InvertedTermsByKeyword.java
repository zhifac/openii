// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.terms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class storing an inverted list of terms
 * @author CWOLF
 */
public class InvertedTermsByKeyword implements Serializable
{	
	/** Stores the inverted list of terms by keyword */
	private HashMap<String,Terms> invertedList = new HashMap<String,Terms>();

	/** Constructs the terms */
	public InvertedTermsByKeyword() {}	
	
	/** Constructs the terms */
	public InvertedTermsByKeyword(Terms terms)
		{ for(Term term : terms.getTerms()) addTerm(term); }
	
	/** Adds a term to the inverted list */
	public void addTerm(Term term)
	{
		for(AssociatedElement element : term.getElements())
		{
			Terms terms = invertedList.get(element.getName());
			if(terms==null) invertedList.put(element.getName(), terms=new Terms());
			terms.addTerm(term);
		}
	}
	
	/** Add a list of terms to the inverted list */
	public void addTerms(Terms terms)
		{ for(Term term : terms.getTerms()) addTerm(term); }
	
	/** Updates a term in the inverted list */
	public void updateTerm(Term oldTerm, Term newTerm)
		{ removeTerm(oldTerm); addTerm(newTerm); }
	
	/** Removes a term from the inverted list */
	public void removeTerm(Term term)
	{
		for(AssociatedElement element : term.getElements())
		{
			Terms terms = invertedList.get(element.getName());
			if(terms!=null) terms.removeTerm(term);
		}
	}
	
	/** Returns the list of synonyms for the specified word */
	public String[] getSynonyms(String keyword)
	{
		ArrayList<String> synonyms = new ArrayList<String>();
		Terms terms = invertedList.get(keyword);
		if(terms!=null)
			for(Term term : terms.getTerms())
			{
				synonyms.add(term.getName());
				for(AssociatedElement element : term.getElements())
					synonyms.add(element.getName());
			}
		return synonyms.toArray(new String[0]);
	}
}