// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.schemaPane;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;

import model.AliasedSchemaElement;
import model.Schemas;

import org.mitre.schemastore.graph.*;
import org.mitre.schemastore.client.*;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;

import prefuse.data.Node;
import prefuse.data.Tree;

/** Model for storing schema extensions */
public class SchemaTree extends Tree
{	
	SchemaStoreClient client = new SchemaStoreClient("http://localhost:8080/SchemaStore/services/SchemaStore");
	GraphBuilder graph;
	Integer schemaID;
	
	/** Constructs the Schema Tree */
	SchemaTree()
	{
		getNodeTable().addColumn("SchemaObject",Object.class);
		addRoot();		
	}
	
	void buildTree(Integer schemaID, Integer comparisonID)
	{		
		// Constructs the base object
		 
		// TODO: figure out what this code does?
		 Node schemaNode = getRoot();
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int i=0; i<schemaNode.getChildCount(); i++) nodes.add(schemaNode.getChild(i));
		for(Node node : nodes) removeChild(node);
		try { Thread.sleep(50); } catch(Exception e) {}
		
		
		try {
			graph = new GraphBuilder(client.getSchemaElements(schemaID),schemaID);
			schemaNode.set("SchemaObject", graph.getSchemaElement(schemaID));
		} catch (RemoteException e) {
			System.out.println("[E] SchemaTree:buildTree: Problem getting schemaElements for schemaID " + schemaID);
			e.printStackTrace();
		}
	//	recursiveBuildTree(schemaNode);
	}
	void recursiveBuildTree(Node rootNode){
		SchemaElement root = (SchemaElement)rootNode.get("SchemaObject");
		for (SchemaElement child : graph.getChildren(root.getId()) ){
			Node childNode = addChild(rootNode);
			childNode.set("SchemaObject", child);
			recursiveBuildTree(childNode);
		}
	}
		
	/**	
		//HashSet<SchemaElement> entities = new HashSet<SchemaElement>(Schemas.getSchemaElements(schemaID,Entity.class));
		
		//if(comparisonID!=null) entities.addAll(Schemas.getSchemaElements(comparisonID,Entity.class));
		
		
		
		for(SchemaElement entity : entities)
		{
			Node entityNode = addChild(schemaNode);
			entityNode.set("SchemaObject",new AliasedSchemaElement(schemaID,entity.getId()));
			
			// Constructs the attribute objects
			HashSet<Attribute> attributes = new HashSet<Attribute>(Schemas.getAttributesFromEntity(schemaID,entity.getId()));
			if(comparisonID!=null) attributes.addAll(Schemas.getAttributesFromEntity(comparisonID,entity.getId()));			
			for(SchemaElement attribute : attributes)
			{
				Node attributeNode = addChild(entityNode);
				
				attributeNode.set("SchemaObject",new AliasedSchemaElement(schemaID,attribute.getId()));
				
				// Constructs the domain values objects
				HashSet<DomainValue> domainValues = new HashSet<DomainValue>(Schemas.getDomainValues(schemaID,((Attribute)attribute).getDomainID()));
				if(comparisonID!=null) domainValues.addAll(Schemas.getDomainValues(comparisonID,((Attribute)attribute).getDomainID()));
				
				for(SchemaElement domainValue : domainValues)
				{
					Node domainNode = addChild(attributeNode);
					domainNode.set("SchemaObject",new AliasedSchemaElement(schemaID,domainValue.getId()));
				}
			}
		}
		
	}
	
*/
}
