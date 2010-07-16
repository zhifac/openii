// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;

/**
 * Class for storing a vocabulary term
 * @author CWOLF
 */
public class Term implements Serializable
{
	/** Stores the term ID */
	private Integer id;
	
	/** Stores the term name */
	private String name;
	
	/** Stores the term description */
	private String description;
	
	/** Stores the list of associated elements */
	private AssociatedElement[] elements;

	/** Constructs the default term */ public Term() {}
	
	/** Constructs the term */
	public Term(Integer id, String name, String description, AssociatedElement[] elements)
		{ this.id = id; this.name = name; this.description = description; this.elements = elements; }

	/** Copies the term */
	public Term copy()
		{ return new Term(id, name, description, elements.clone()); }
	
	// Handles all of the term getters
	public Integer getId() { return id; }
	public String getName() { return name; }
	public String getDescription() { return description; }
	public AssociatedElement[] getElements() { return elements; }

	// Handles all of the term setters
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setDescription(String description) { this.description = description; }
	public void setElements(AssociatedElement[] elements) { this.elements = elements; }
	
	/** Gets the associated element for the specified schema */
	public AssociatedElement getAssociatedElement(int schemaID)
	{
		for(AssociatedElement element : elements)
			if(element.getSchemaID().equals(schemaID)) return element;
		return null;
	}
}
