// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.search;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

/** Class for storing a search result match */
public class Match
{
	/** Stores the matched element */
	private Object element;

	/** Stores the type of match */
	private Integer type;
		
	/** Constructs the match */
	public Match(Object element, Integer type)
		{ this.element = element; this.type = type; }

	/** Returns the match element ID */
	public Integer getElementID()
		{ return element instanceof Schema ? ((Schema)element).getId() : ((SchemaElement)element).getId(); }
	
	/** Returns the match element */
	public Object getElement()
		{ return element; }
	
	/** Returns the match type */
	public Integer getType()
		{ return type; }
}