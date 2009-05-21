// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Interface for representing the graph model
 */
abstract public class GraphModel
{
	/** Returns the name of the graph model */
	abstract public String getName();
	
	/** Returns the root elements in this graph */
	abstract public ArrayList<SchemaElement> getRootElements(HierarchicalGraph graph);
	
	/** Returns the parent elements of the specified element in this graph */
	abstract public ArrayList<SchemaElement> getParentElements(HierarchicalGraph graph, Integer elementID);
	
	/** Returns the children elements of the specified element in this graph */
	abstract public ArrayList<SchemaElement> getChildElements(HierarchicalGraph graph, Integer elementID);
	
	/** Returns the domain of the specified element in this graph */
	abstract public Domain getDomainForElement(HierarchicalGraph graph, Integer elementID);

	/** Returns the type name associated with the specified element (or NULL if element has no name) */
	abstract public String getType(HierarchicalGraph graph, Integer elementID);
	
	/** Returns the elements referenced by the specified domain */
	abstract public ArrayList<SchemaElement> getElementsForDomain(HierarchicalGraph graph, Integer domainID);
	
	/** Returns the name of the graph model */
	public String toString() { return getName(); }
}