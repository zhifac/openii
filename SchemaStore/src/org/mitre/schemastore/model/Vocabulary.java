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
	/** Stores a vocabulary associated element */
	static public class AssociatedElement
	{
		/** Stores the schema ID */
		private Integer schemaID;
		
		/** Stores the schema element */
		private Integer elementID;
	
		/** Stores the element name */
		private String name;
		
		/** Constructs the associated element */
		public AssociatedElement(Integer schemaID, Integer elementID, String name)
			{ this.schemaID = schemaID; this.elementID = elementID; this.name = name; }

		// Handles all of the associated element getters
		public Integer getSchemaID() { return schemaID; }
		public Integer getElementID() { return elementID; }
		public String getName() { return name; }
		
		// Handles all of the associated element setters
		public void setSchemaID(Integer schemaID) { this.schemaID = schemaID; }
		public void setElementID(Integer elementID) { this.elementID = elementID; }
		public void setName(String name) { this.name = name; }
	}
	
	/** Stores a vocabulary term */
	static public class Term
	{
		/** Stores the term ID */
		private Integer id;
		
		/** Stores the term name */
		private String name;
		
		/** Stores the list of associated elements */
		private AssociatedElement[] elements;

		/** Constructs the default term */ public Term() {}
		
		/** Constructs the term */
		public Term(Integer id, String name, AssociatedElement[] elements)
			{ this.id = id; this.name = name; this.elements = elements; }

		/** Copies the term */
		public Term copy()
			{ return new Term(id, name, elements.clone()); }
		
		// Handles all of the term getters
		public Integer getId() { return id; }
		public String getName() { return name; }
		public AssociatedElement[] getElements() { return elements; }

		// Handles all of the term setters
		public void setId(Integer id) { this.id = id; }
		public void setName(String name) { this.name = name; }
		public void setElements(AssociatedElement[] elements) { this.elements = elements; }
	}
	
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