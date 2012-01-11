package org.mitre.affinity.model.graph;

import org.mitre.affinity.model.Position;

import communityFinder.Edge;
import communityFinder.Node;

/**
 * Wraps a graph node or edge.
 * 
 * @author CBONACETO
 *
 */
public class GraphObject {
	
	protected final Node node;
	
	protected final Edge edge;
	
	protected Position location;
	
	public GraphObject(Node node) {
		this.node = node;
		this.edge = null;
	}
	
	public GraphObject(Edge edge) {
		this.edge = edge;
		this.node = null;
	}

	public Node getNode() {
		return node;
	}

	public Edge getEdge() {
		return edge;
	}
	
	public Position getLocation() {
		return location;
	}

	public void setLocation(Position location) {
		this.location = location;
	}

	public String getName() {
		if(node != null) {
			return node.getValue();
		} else {
			return edge.getEdgeName();
		}
	}
	
	public String getObjectId() {
		return getName();
	}

	public Object getClusterObject() {
		if(node != null) {
			return node;
		} else {
			return edge;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof GraphObject) {
			if(node != null) {
				return node == ((GraphObject)obj).getNode();
			} else {
				return edge == ((GraphObject)obj).getEdge();
			}	
		} else if(obj instanceof Node) {
			return node == (Node)obj;
		} else if(obj instanceof Edge) {
			return edge == (Edge)obj;
		}		
		return super.equals(obj);
	}
}