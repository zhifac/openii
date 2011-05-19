// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;

/**
 * Class for storing an annotation
 * @author CWOLF
 */
public class Annotation implements Serializable
{
	/** Stores the schema element */
	private Integer elementID;

	/** Stores the element value */
	private String value;
	
	/** Constructs the default annotation */ public Annotation() {}	
	
	/** Constructs the annotation */
	public Annotation(Integer elementID, String value)
		{ this.elementID = elementID; this.value = value; }

	/** Copies the annotation */
	public Annotation copy()
		{ return new Annotation(this.elementID, this.value); }
	
	// Handles all of the annotation getters
	public Integer getElementID() { return elementID; }
	public String getValue() { return value; }
	
	// Handles all of the annotation setters
	public void setElementID(Integer elementID) { this.elementID = elementID; }
	public void setValue(String value) { this.value = value; }
}