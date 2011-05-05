// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class storing thesaurus terms
 * @author CWOLF
 */
public class ThesaurusTerms implements Serializable
{	
	/** Stores the thesaurus id */
	private Integer thesaurusID;
	
	/** Stores the list of terms included in the  thesaurus */
	private Term[] terms = new Term[0];
	
	/** Constructs the default list of thesaurus terms */ public ThesaurusTerms() {}
	
	/** Constructs the thesaurus terms */
	public ThesaurusTerms(Integer thesaurusID, Term[] terms)
		{ this.thesaurusID = thesaurusID; this.terms = terms; }
	
	/** Copies the thesaurus terms */
	public ThesaurusTerms copy()
	{
		ArrayList<Term> copiedTerms = new ArrayList<Term>();
		for(Term term : terms) copiedTerms.add(term.copy());
		return new ThesaurusTerms(getThesaurusId(),copiedTerms.toArray(new Term[0]));
	}
	
	// Handles all of the thesaurus getters
	public Integer getThesaurusId() { return thesaurusID; }
	public Term[] getTerms() { return terms; }

	// Handles all of the thesaurus setters
	public void setThesaurusId(Integer thesaurusID) { this.thesaurusID = thesaurusID; }
	public void setTerms(Term[] terms) { this.terms = terms; }
	
	/** Adds a term to the thesaurus */
	public void addTerm(Term newTerm)
	{
		ArrayList<Term> terms = new ArrayList<Term>(Arrays.asList(this.terms));
		terms.add(newTerm);
		this.terms = terms.toArray(new Term[0]);
	}
	
	/** Removes the term from the thesaurus */
	public void removeTerm(Term oldTerm)
	{
		ArrayList<Term> terms = new ArrayList<Term>(Arrays.asList(this.terms));
		terms.remove(oldTerm);
		this.terms = terms.toArray(new Term[0]);		
	}
}