// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.extensionsPane;

import java.util.Collection;
import java.util.HashMap;

import org.mitre.schemastore.model.DataSource;

import model.DataSources;
import model.Schemas;
import prefuse.data.Graph;
import prefuse.data.Node;

/** Model for storing schema extensions */
public class ExtensionGraph extends Graph
{
	/** References to the various schema nodes */
	private HashMap<Integer,Node> nodes = new HashMap<Integer,Node>();
	
	/** Constructs the Extension Tree */
	ExtensionGraph(Collection<Integer> schemaIDs)
	{
		// Defines the columns in the node table
		super(true);
		getNodeTable().addColumn("NodeObject",Object.class);
		
		// Constructs the schema nodes
		for(Integer schemaID : schemaIDs)
		{
			Node node = addNode();
			node.set("NodeObject",Schemas.getSchema(schemaID));
			nodes.put(schemaID,node);
		}

		// Connect the schemas together
		for(Integer schemaID : schemaIDs)
		{
			Node parent = nodes.get(schemaID);
			for(Integer childID : Schemas.getChildSchemas(schemaID))
				addEdge(parent,nodes.get(childID));
		}
		
		// Connect the data sources to the schemas
		for(DataSource dataSource : DataSources.getDataSources(null))
			for(Integer schemaID : schemaIDs)
				if(schemaID.equals(dataSource.getSchemaID()))
				{
					Node node = addNode();
					node.set("NodeObject",dataSource);
					Node parent = nodes.get(schemaID);
					addEdge(parent,node);
				}
	}
}
