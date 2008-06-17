// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.extensionsPane;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Schema;

import model.DataSources;
import model.Schemas;
import prefuse.data.Edge;
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
			node.set("NodeObject",schemaID);
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
		{
			for(Integer schemaID : schemaIDs)
				if(schemaID.equals(dataSource.getSchemaID()))
				{
					Node node = addNode();
					node.set("NodeObject",dataSource.getId());
					Node parent = nodes.get(schemaID);
					addEdge(parent,node);
				}
		}
	}
	
	/** Handles the addition of a schema */
	public void schemaAdded(Schema schema)
	{
		// Create node for specified schema
		Node node = addNode();
		node.set("NodeObject", schema.getId());
		nodes.put(schema.getId(), node);

		// Connect to parent schemas
		for(Integer parentSchemaID : Schemas.getParentSchemas(schema.getId()))
			addEdge(nodes.get(parentSchemaID),node);
	}

	/** Handles the removal of a schema */
	public void schemaRemoved(Schema schema)
	{
		removeNode(nodes.get(schema.getId()));
		nodes.remove(schema.getId());
	}
	
	/** Handles the update of schema parents */
	public void schemaParentsUpdated(Schema schema)
	{
		// Remove connections to old parent schemas
		Node node = nodes.get(schema.getId());
		Iterator edges = node.inEdges();
		while(edges.hasNext())
			removeEdge((Edge)edges.next());
		
		// Connect to parent schemas
		for(Integer parentSchemaID : Schemas.getParentSchemas(schema.getId()))
			addEdge(nodes.get(parentSchemaID),node);
	}
	
	/** Handles the addition of a data source */
	public void dataSourceAdded(DataSource dataSource)
	{
		Node node = addNode();
		node.set("NodeObject",dataSource.getId());
		Node parent = nodes.get(dataSource.getSchemaID());
		addEdge(parent,node);
	}

	/** Handles the removal of a data source */
	public void dataSourceRemoved(DataSource dataSource)
	{
		Node node = nodes.get(dataSource.getSchemaID());
		for(int i=0; i<node.getChildCount(); i++)
			if(node.getChild(i).get("NodeObject").equals(dataSource.getId()))
				removeNode(node.getChild(i));
	}
}
