// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for storing a vocabulary
 * @author CWOLF
 */
public class Vocabulary implements Serializable
{
	/** Stores the project associated with this vocabulary */
	private Integer projectID;
	
	/** Stores the list of schemas which make up this vocabulary */
	private Integer[] schemaIDs;
	
	/** Stores the list of terms which make up this vocabulary */
	private Term[] terms;
	
	/** Constructs the default vocabulary */ public Vocabulary() {}
	
	/** Constructs the vocabulary */
	public Vocabulary(Integer projectID, Integer[] schemaIDs, Term[] terms)
		{ this.projectID = projectID; this.schemaIDs = schemaIDs; this.terms = terms; }
	
	/** Copies the vocabulary */
	public Vocabulary copy()
	{
		ArrayList<Term> copiedTerms = new ArrayList<Term>();
		for(Term term : terms) copiedTerms.add(term.copy());
		return new Vocabulary(getProjectID(),getSchemaIDs().clone(),copiedTerms.toArray(new Term[0]));
	}
	
	// Handles all of the vocabulary getters
	public Integer getProjectID() { return projectID; }
	public Integer[] getSchemaIDs() { return schemaIDs; }
	public Term[] getTerms() { return terms; }

	// Handles all of the vocabulary setters
	public void setProjectID(Integer projectID) { this.projectID = projectID; }
	public void setSchemaIDs(Integer[] schemaIDs) { this.schemaIDs = schemaIDs; }
	public void setTerms(Term[] terms) { this.terms = terms; }
}