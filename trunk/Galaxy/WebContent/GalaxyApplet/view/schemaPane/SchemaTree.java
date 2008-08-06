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
	GraphBuilder graph;
	Integer schemaID;
	
	/** Constructs the Schema Tree */
	SchemaTree()
	{
		getNodeTable().addColumn("SchemaObject",AliasedSchemaElement.class);
		addRoot();		
	}
	
	void buildTree(Integer schemaID, Integer comparisonID)
	{		
		// Constructs the base object
		 Node schemaNode = getRoot();
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int i=0; i<schemaNode.getChildCount(); i++) nodes.add(schemaNode.getChild(i));
		for(Node node : nodes) removeChild(node);
		try { Thread.sleep(50); } catch(Exception e) {}
		
		
		try {
			graph = new GraphBuilder(Schemas.getSchemaElements(schemaID,null),schemaID);	
			if (graph.getSchemaElement(schemaID) == null){
				System.err.println("DOES NOT CONTAIN SCHEMA ELEMENT " + schemaID);
			}
			graph.getSchemaElement(schemaID).setName(Schemas.getSchema(schemaID).getName());
			schemaNode.set("SchemaObject", new AliasedSchemaElement(graph.getSchemaElement(schemaID)));
		} catch (Exception e) {
			System.out.println("[E] SchemaTree:buildTree: Problem getting schemaElements for schemaID " + schemaID);
			e.printStackTrace();
		}
		recursiveBuildTree(schemaNode);
	}
	void recursiveBuildTree(Node rootNode){
		SchemaElement root = (SchemaElement)rootNode.get("SchemaObject");
		for (SchemaElement child : graph.getChildren(root.getId()) ){
			Node childNode = addChild(rootNode);
			childNode.set("SchemaObject", new AliasedSchemaElement(child));
			recursiveBuildTree(childNode);
		}
	}
}
