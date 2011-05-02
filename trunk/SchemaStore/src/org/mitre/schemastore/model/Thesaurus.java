// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class storing a thesaurus
 * @author CWOLF
 */
public class Thesaurus implements Serializable
{	
	/** Stores the thesaurus id */
	private Integer id;
	
	/** Stores the list of terms which make up this thesaurus */
	private Term[] terms = new Term[0];
	
	/** Constructs the default thesaurus */ public Thesaurus() {}
	
	/** Constructs the thesaurus */
	public Thesaurus(Integer id, Term[] terms)
		{ this.id = id; this.terms = terms; }
	
	/** Copies the thesaurus */
	public Thesaurus copy()
	{
		ArrayList<Term> copiedTerms = new ArrayList<Term>();
		for(Term term : terms) copiedTerms.add(term.copy());
		return new Thesaurus(getId(),copiedTerms.toArray(new Term[0]));
	}
	
	// Handles all of the thesaurus getters
	public Integer getId() { return id; }
	public Term[] getTerms() { return terms; }

	// Handles all of the thesaurus setters
	public void setId(Integer id) { this.id = id; }
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