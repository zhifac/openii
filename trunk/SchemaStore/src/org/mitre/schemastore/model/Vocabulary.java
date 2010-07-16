// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Class for storing a vocabulary
 * @author CWOLF
 */
public class Vocabulary implements Serializable
{
	/** Stores the project associated with this vocabulary */
	private Integer projectID;
	
	/** Stores the list of terms which make up this vocabulary */
	private Term[] terms;
	
	/** Constructs the default vocabulary */ public Vocabulary() {}
	
	/** Constructs the vocabulary */
	public Vocabulary(Integer projectID, Term[] terms)
		{ this.projectID = projectID; this.terms = terms; }
	
	/** Copies the vocabulary */
	public Vocabulary copy()
	{
		ArrayList<Term> copiedTerms = new ArrayList<Term>();
		if(terms!=null) for(Term term : terms) copiedTerms.add(term.copy());
		return new Vocabulary(getProjectID(),copiedTerms.toArray(new Term[0]));
	}
	
	// Handles all of the vocabulary getters
	public Integer getProjectID() { return projectID; }
	public Term[] getTerms() { return terms; }

	// Handles all of the vocabulary setters
	public void setProjectID(Integer projectID) { this.projectID = projectID; }
	public void setTerms(Term[] terms) { this.terms = terms; }
	
	/** Adds a term to the vocabulary */
	public void addTerm(Term newTerm)
	{
		ArrayList<Term> terms = new ArrayList<Term>(Arrays.asList(this.terms));
		terms.add(newTerm);
		this.terms = terms.toArray(new Term[0]);
	}
	
	/** Removes the term from the vocabulary */
	public void removeTerm(Term oldTerm)
	{
		ArrayList<Term> terms = new ArrayList<Term>(Arrays.asList(this.terms));
		terms.remove(oldTerm);
		this.terms = terms.toArray(new Term[0]);		
	}
	
	/** Returns the schemas used in this vocabulary */
	public Integer[] getSchemaIDs()
	{
		HashSet<Integer> schemaIDs = new HashSet<Integer>();
		for(Term term : terms)
			for(AssociatedElement element : term.getElements())
				schemaIDs.add(element.getSchemaID());
		return schemaIDs.toArray(new Integer[0]);
	}
}