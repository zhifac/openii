// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Interface for representing the graph model
 */
public interface GraphModel
{
	/** Returns the name of the graph model */
	public String getName();
	
	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getRootElements(HierarchicalGraph graph);
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(HierarchicalGraph graph, Integer elementID);
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(HierarchicalGraph graph, Integer elementID);
	
	/** Returns the domain of the specified element in this graph */
	public Domain getDomainForElement(HierarchicalGraph graph, Integer elementID);

	/** Returns the elements referenced by the specified domain */
	public ArrayList<SchemaElement> getElementsForDomain(HierarchicalGraph graph, Integer domainID);
}