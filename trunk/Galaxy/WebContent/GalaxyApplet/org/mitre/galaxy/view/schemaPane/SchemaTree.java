// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.schemaPane;

import java.util.ArrayList;
import java.util.HashSet;


import org.mitre.galaxy.model.Schemas;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

import prefuse.data.Node;
import prefuse.data.Tree;

/** Model for storing schema extensions */
public class SchemaTree extends Tree
{	
	/** Hash set used to prohibit cycles in tree */
	private HashSet<Integer> usedElements = new HashSet<Integer>();
	
	/** Constructs the Schema Tree */
	SchemaTree()
	{
		getNodeTable().addColumn("SchemaObject",Object.class);
		addRoot();		
	}
	
	/** Build the specified schema branches */
	private void buildBranches(Node node, HashSet<SchemaElement> elements, HierarchicalSchemaInfo sInfo, HierarchicalSchemaInfo cInfo)
	{
		// Cycle through all elements associated with this branch
		for(SchemaElement element : elements)
		{
			if(usedElements.contains(element.getId())) continue;
			usedElements.add(element.getId());
			
			// Attach the branch to the tree
			Node childNode = addChild(node);
			childNode.set("SchemaObject", new SchemaTreeObject(element.getId(),sInfo,cInfo));

			// Attach domain values to this branch
			HashSet<DomainValue> domainValues = new HashSet<DomainValue>(sInfo.getDomainValuesForElement(element.getId()));
			if(cInfo!=null) domainValues.addAll(cInfo.getDomainValuesForElement(element.getId()));
			for(SchemaElement domainValue : domainValues)
			{
				Node domainNode = addChild(childNode);
				domainNode.set("SchemaObject",new SchemaTreeObject(domainValue.getId(),sInfo,cInfo));
			}			
			
			// Build lower layer of branches
			HashSet<SchemaElement> childElements = new HashSet<SchemaElement>(sInfo.getChildElements(element.getId()));
			if(cInfo!=null) childElements.addAll(cInfo.getChildElements(element.getId()));
			buildBranches(childNode, childElements, sInfo, cInfo);
		}		
	}
	
	/** Builds the schema tree */
	void buildTree(Integer schemaID, Integer comparisonID)
	{
		// Clear out the used element array before building the tree
		usedElements.clear();
		
		// Constructs the base object
		Node schemaNode = getRoot();
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int i=0; i<schemaNode.getChildCount(); i++) nodes.add(schemaNode.getChild(i));
		for(Node node : nodes) removeChild(node);
		try { Thread.sleep(50); } catch(Exception e) {}
		schemaNode.set("SchemaObject", schemaID);

		// Build the first layer of the tree
		HierarchicalSchemaInfo sInfo = Schemas.getSchemaInfo(schemaID);
		HierarchicalSchemaInfo cInfo = comparisonID==null ? null : Schemas.getSchemaInfo(comparisonID);
		HashSet<SchemaElement> elements = new HashSet<SchemaElement>(sInfo.getRootElements());
		if(cInfo!=null) elements.addAll(cInfo.getRootElements());
		buildBranches(schemaNode, elements, sInfo, cInfo);
	}
}