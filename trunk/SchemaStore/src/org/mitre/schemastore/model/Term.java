// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

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
	private AssociatedElement[] elements = new AssociatedElement[0];

	/** Constructs the default term */ public Term() {}
	
	/** Constructs the term */
	public Term(Integer id, String name, String description)
		{ this.id = id; this.name = name; this.description = description; }
	
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
	
	/** Adds an associated element to the term */
	public void addAssociatedElement(AssociatedElement newElement)
	{
		ArrayList<AssociatedElement> elements = new ArrayList<AssociatedElement>(Arrays.asList(this.elements));
		for(AssociatedElement element : elements)
			if(element.getSchemaID().equals(newElement.getSchemaID()))
				elements.remove(element);
		elements.add(newElement);	
		this.elements = elements.toArray(new AssociatedElement[0]);
	}
	
	/** Removes the associated element from the term */
	public void removeAssociatedElement(AssociatedElement oldElement)
	{
		ArrayList<AssociatedElement> elements = new ArrayList<AssociatedElement>(Arrays.asList(this.elements));
		for(AssociatedElement element : elements)
			if(element.getSchemaID().equals(oldElement.getSchemaID()) && element.getElementID().equals(oldElement.getElementID()))
				elements.remove(element);
		this.elements = elements.toArray(new AssociatedElement[0]);		
	}
}
