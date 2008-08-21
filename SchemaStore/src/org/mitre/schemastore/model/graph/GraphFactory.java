// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;

/**
 *  Class used to generate a hierarchical graph
 */
public class GraphFactory
{
	/** Generates a hierarchical graph */
	static public HierarchicalGraph generateGraph(Integer schemaID, ArrayList<SchemaElement> elements)
	{
		// Build the graph
		Graph graph = new Graph(schemaID, elements);

		// Determine the makeup of the schema
		Integer totalCount=0, domainCount=0, containmentCount=0;
		for(SchemaElement element : graph.getElements(null))
		{
			if(element instanceof Domain || element instanceof DomainValue) domainCount++;
			if(element instanceof Containment) containmentCount++;
			totalCount++;
		}
		
		// Identify which hierarchical graph to use
		if(domainCount==totalCount) return new DomainGraph(graph);
		if(containmentCount>0) return new ContainmentGraph(graph);
		return new RelationalGraph(graph);
	}
}