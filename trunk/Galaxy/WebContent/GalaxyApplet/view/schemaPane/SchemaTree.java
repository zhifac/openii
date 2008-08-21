// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.schemaPane;

import java.util.ArrayList;
import java.util.HashSet;

import model.AliasedSchemaElement;
import model.Schemas;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

import prefuse.data.Node;
import prefuse.data.Tree;

/** Model for storing schema extensions */
public class SchemaTree extends Tree
{	
	/** Constructs the Schema Tree */
	SchemaTree()
	{
		getNodeTable().addColumn("SchemaObject",Object.class);
		addRoot();		
	}
	
	/** Builds the schema tree */
	void buildTree(Integer schemaID, Integer comparisonID)
	{		
		// Constructs the base object
		Node schemaNode = getRoot();
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int i=0; i<schemaNode.getChildCount(); i++) nodes.add(schemaNode.getChild(i));
		for(Node node : nodes) removeChild(node);
		try { Thread.sleep(50); } catch(Exception e) {}
		schemaNode.set("SchemaObject", schemaID);
		
		// Constructs the entity objects
		HierarchicalGraph graph = Schemas.getGraph(schemaID);
		HierarchicalGraph cGraph = Schemas.getGraph(comparisonID);
		HashSet<SchemaElement> entities = new HashSet<SchemaElement>(graph.getElements(Entity.class));
		if(comparisonID!=null) entities.addAll(cGraph.getElements(Entity.class));
		for(SchemaElement entity : entities)
		{
			Node entityNode = addChild(schemaNode);
			entityNode.set("SchemaObject",new AliasedSchemaElement(schemaID,entity.getId()));
			
			// Constructs the attribute objects
			HashSet<Attribute> attributes = new HashSet<Attribute>(graph.getAttributes(entity.getId()));
			if(comparisonID!=null) attributes.addAll(cGraph.getAttributes(entity.getId()));			
			for(SchemaElement attribute : attributes)
			{
				Node attributeNode = addChild(entityNode);
				attributeNode.set("SchemaObject",new AliasedSchemaElement(schemaID,attribute.getId()));
				
				// Constructs the domain values objects
				HashSet<DomainValue> domainValues = new HashSet<DomainValue>(graph.getDomainValues(((Attribute)attribute).getDomainID()));
				if(comparisonID!=null) domainValues.addAll(cGraph.getDomainValues(((Attribute)attribute).getDomainID()));
				for(SchemaElement domainValue : domainValues)
				{
					Node domainNode = addChild(attributeNode);
					domainNode.set("SchemaObject",new AliasedSchemaElement(schemaID,domainValue.getId()));
				}
			}
		}
	}
}
