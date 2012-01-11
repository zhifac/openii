package org.mitre.affinity.model.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.mitre.affinity.model.IClusterObjectManager;

import communityFinder.Edge;
import communityFinder.Node;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * @author CBONACETO
 *
 */
public class GraphClusterObjectManager implements IClusterObjectManager<String, GraphObject> {
	
	public static enum ClusterObjectType {Links, Nodes, LinkCommunityNodes};
	
	/** The graph */
	protected UndirectedSparseGraph<Node, Edge> graph;
	
	/** The nodes in the graph (mapped by node value) */
	protected HashMap<String, GraphObject> nodes;
	
	/** The edges in the graph (mapped by edge name) */
	protected HashMap<String, GraphObject> edges;
	
	/** The cluster object type to use (default is Links) */
	protected ClusterObjectType clusterObjectType = ClusterObjectType.Links;
	
	public GraphClusterObjectManager() {}
	
	public GraphClusterObjectManager(UndirectedSparseGraph<Node, Edge> graph) {
		setGraph(graph);
	}
	
	public ClusterObjectType getClusterObjectType() {
		return clusterObjectType;
	}

	public void setClusterObjectType(ClusterObjectType clusterObjectType) {
		this.clusterObjectType = clusterObjectType;
	}

	public UndirectedSparseGraph<Node, Edge> getGraph() {
		return graph;
	}

	public void setGraph(UndirectedSparseGraph<Node, Edge> graph) {
		this.graph = graph;
		edges = new HashMap<String, GraphObject>();
		nodes = new HashMap<String, GraphObject>();
		if(graph != null) {
			if(graph.getEdges() != null) {
				for(Edge edge : graph.getEdges()) {
					edges.put(edge.getEdgeName(), new GraphObject(edge));
				}
			}
			if(graph.getVertices() != null) {
				for(Node node : graph.getVertices()) {
					nodes.put(node.getValue(), new GraphObject(node));
				}
			}
		}
	}

	@Override
	public Collection<GraphObject> getClusterObjects() {
		switch(clusterObjectType) {
		case Links:
			if(edges != null) {
				return edges.values();
			}
			return null;
		case Nodes:
			if(nodes != null) {
				return nodes.values();
			}
			return null;
		}
		return null;
		/*if(graph != null) {
			return graph.getEdges();
		}
		return null;*/
	}

	/* (non-Javadoc)
	 * @see org.mitre.affinity.model.IClusterObjectManager#getClusterObjectIDs()
	 */
	@Override
	public ArrayList<String> getClusterObjectIDs() {
		switch(clusterObjectType) {
		case Links:
			if(edges != null) {
				return new ArrayList<String>(edges.keySet());
			}
			return null;
		case Nodes:
			if(nodes != null) {
				return new ArrayList<String>(nodes.keySet());
			}
			return null;
		}
		return null;
	}

	@Override
	public Collection<String> getDeletableClusterObjects() {
		switch(clusterObjectType) {
		case Links:
			if(edges != null) {
				return edges.keySet();
			}
			return null;
		case Nodes:
			if(nodes != null) {
				return nodes.keySet();
			}
			return null;
		}
		return null;
	}
	
	public Collection<Edge> getEdges() {
		if(graph != null) {
			return graph.getEdges();
		}
		return null;
	}
	
	public Edge getEdge(String edgeID) {
		if(edges != null) {
			GraphObject edge = edges.get(edgeID);
			if(edge != null) {
				return edge.getEdge();
			} 
		}
		return null;
	}
	
	public Node getNode(String nodeID) {
		if(nodes != null) {
			GraphObject node = nodes.get(nodeID);
			if(node != null) {
				return node.getNode();
			}
		}
		return null;
	}
	
	public Collection<Node> getNodes() {
		if(graph != null) {
			return graph.getVertices();
		}
		return null;
	}

	@Override
	public GraphObject getClusterObject(String objectID) {
		switch(clusterObjectType) {
		case Links:
			if(edges != null) {
				return edges.get(objectID);
			}
			return null;
		case Nodes:
			if(nodes != null) {
				return nodes.get(objectID);
			}
			return null;
		}
		return null;		
	}

	@Override
	public String getClusterObjectName(String objectID) {
		switch(clusterObjectType) {
		case Links:
			if(edges != null) {
				GraphObject edge = edges.get(objectID);
				if(edge != null) {
					return edge.getName();
				}
			}
			return null;
		case Nodes:
			if(nodes != null) {
				GraphObject node = nodes.get(objectID);
				if(node != null) {
					return node.getName();
				}
			}
			return null;
		}
		return null;
	}

	@Override
	public String findClusterObject(String identifier) {		
		HashMap<String, GraphObject> clusterObjects = null;
		if(clusterObjectType == ClusterObjectType.Links) {
			clusterObjects = edges;
		} else if(clusterObjectType == ClusterObjectType.Nodes) {
			clusterObjects = nodes;
		}
		if(clusterObjects != null) {
			GraphObject object = clusterObjects.get(identifier);
			if(object == null) {
				for(GraphObject o : clusterObjects.values()) {					
					if(o.getObjectId() != null && o.getObjectId().equalsIgnoreCase(identifier)) {
						object = o;
						break;
					}
				}
			}
			if(object != null) {
				return object.getObjectId();
			}
		}
		return null;
	}

	@Override
	public boolean deleteClusterObject(String objectID) {
		// TODO Auto-generated method stub
		return false;
	}
}