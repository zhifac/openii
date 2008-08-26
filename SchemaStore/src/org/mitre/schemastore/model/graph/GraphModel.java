// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Abstract class for representing the graph model
 */
abstract public class GraphModel
{
	/** Stores a reference to the graph being modeled */
	protected HierarchicalGraph graph;
	
	/** Constructs the graph model */
	public GraphModel(HierarchicalGraph graph)
		{ this.graph = graph; }
	
	/** Returns the root elements in this graph */
	abstract public ArrayList<SchemaElement> getRootElements();
	
	/** Returns the parent elements of the specified element in this graph */
	abstract public ArrayList<SchemaElement> getParentElements(Integer elementID);
	
	/** Returns the children elements of the specified element in this graph */
	abstract public ArrayList<SchemaElement> getChildElements(Integer elementID);
	
	/** Returns the domain of the specified element in this graph */
	abstract public Domain getDomainForElement(Integer elementID);

	/** Returns the elements referenced by the specified domain */
	abstract public ArrayList<SchemaElement> getElementsForDomain(Integer domainID);
}