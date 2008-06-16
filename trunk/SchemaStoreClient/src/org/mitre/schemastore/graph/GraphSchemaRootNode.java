// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.schemastore.graph;

/**
 * Class GraphSchemaRootNode
 * @author MDMORSE
 */

import java.util.ArrayList;

public class GraphSchemaRootNode extends GraphObject{
	
	//relationships and containments that are my children.
	ArrayList<GraphObject> childcontainments;
	
	public GraphSchemaRootNode(int id, String name, String description, int base){
		super(id, name, description, base);
		childcontainments = new ArrayList<GraphObject>();
	}
	
	public GraphObject getChildContainment(int n){
		return childcontainments.get(n);
	}
	
	public int getNumberOfChildContainments(){
		return childcontainments.size();
	}
	
	public void addChildContainment(GraphObject gE){
		childcontainments.add(gE);
	}
	
}