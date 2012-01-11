package org.mitre.affinity.algorithms.distance_functions.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.mitre.affinity.algorithms.IProgressMonitor;
import org.mitre.affinity.algorithms.distance_functions.CommonDistanceMetrics;
import org.mitre.affinity.algorithms.distance_functions.DistanceFunction;
import org.mitre.affinity.model.IClusterObjectManager;
import org.mitre.affinity.model.clusters.DistanceGrid;
import org.mitre.affinity.model.graph.GraphObject;
import org.mitre.affinity.model.graph.GraphClusterObjectManager;

import communityFinder.Edge;
import communityFinder.Node;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * A distance function that computes the distances between nodes in a graph based on
 * the number of neighbors nodes share.
 * 
 * @author CBONACETO
 *
 */
public class NodeDistanceFunction implements DistanceFunction<String, GraphObject> {
	
	protected int separationDistance = 1;
	
	public NodeDistanceFunction() {}
	
	public NodeDistanceFunction(int separationDistance) {
		setSeparationDistance(separationDistance);
	}

	@Override
	public String getName() {
		return "Node Neighbors Distance Function";
	}

	public int getSeparationDistance() {
		return separationDistance;
	}

	public void setSeparationDistance(int separationDistance) {
		if(separationDistance <= 0) {
			throw new IllegalArgumentException("SeparationDistance must be >= 0");
		}
		this.separationDistance = separationDistance;
	}

	/* (non-Javadoc)
	 * @see org.mitre.affinity.algorithms.distance_functions.DistanceFunction#generateDistanceGrid(java.util.Collection, org.mitre.affinity.model.IClusterObjectManager, org.mitre.affinity.algorithms.IProgressMonitor)
	 */
	@Override
	public DistanceGrid<String> generateDistanceGrid(
			Collection<String> objectIDs,
			IClusterObjectManager<String, GraphObject> clusterObjectManager,
			IProgressMonitor progressMonitor) {
		if(!(clusterObjectManager instanceof GraphClusterObjectManager)) {
			throw new IllegalArgumentException("Cluster object manager must be a LinkCommunityClusterObjectManager");
		}
		UndirectedSparseGraph<Node, Edge> graph = ((GraphClusterObjectManager)clusterObjectManager).getGraph();
		if(graph != null && graph.getVertexCount() > 0) {
			DistanceGrid<String> dg = new DistanceGrid<String>();
			ArrayList<Node> nodes = new ArrayList<Node>(graph.getVertices());
			for(int i=0; i<nodes.size(); i++) {
				Node n1 = nodes.get(i);
				Set<Node> neighborsN1 = getNeighbors(n1, graph);
				//System.out.println("Neigbors of " + n1.getValue() + ": " + neighborsN1);
				for(int j=i+1; j<nodes.size(); j++) {
					//Compute the Jaccard distance between the sets of neighbors for nodes n1 and n2
					Node n2 = nodes.get(j);
					Set<Node> neighborsN2 = getNeighbors(n2, graph);
					//TODO: Memory storage optimization - only store distance if < 1, have dg return 1 if distance is null
					//System.out.println("Neighbors of " + n1.getValue() + ": " + neighborsN1);
					dg.set(n1.getValue(), n2.getValue(), 
							CommonDistanceMetrics.computeJaccardDistance(neighborsN1, neighborsN2));
					//System.out.println("Neigbors of " + n2.getValue() + ": " + neighborsN2);
					//System.out.println("Distance from " + n1.getValue() + "->" + n2.getValue() + ": " + dg.get(n1.getValue(), n2.getValue()));
				}
			}
			return dg;
		}
		return null;
	}
	
	/**
	 * @param node
	 * @param graph
	 * @return
	 */
	protected Set<Node> getNeighbors(Node node, UndirectedSparseGraph<Node, Edge> graph) {
		Set<Node> neighbors = new HashSet<Node>(); 
		neighbors.add(node); //Now include the node itself as one of its neighbors
		Collection<Node> neighborsToAdd = graph.getNeighbors(node);		
		if(neighborsToAdd != null && !neighborsToAdd.isEmpty()) {
			neighbors.addAll(neighborsToAdd);
			if(separationDistance > 1) {
				Queue<NodeAndSeparationDistance> nodesToExpand = new LinkedList<NodeAndSeparationDistance>();
				for(Node n : neighborsToAdd) {
					nodesToExpand.add(new NodeAndSeparationDistance(n, 1));
				}
				while(!nodesToExpand.isEmpty()) {
					NodeAndSeparationDistance nextNodeToExpand = nodesToExpand.poll();
					neighborsToAdd = graph.getNeighbors(nextNodeToExpand.node);
					if(neighborsToAdd != null && !neighborsToAdd.isEmpty()) {
						/*neighbors.addAll(neighborsToAdd);
						if(nextNodeToExpand.separationDistance < separationDistance - 1) {
							int distance = nextNodeToExpand.separationDistance + 1;
							for(Node n : neighborsToAdd) {
								nodesToExpand.add(new NodeAndSeparationDistance(n, distance));
							}
						}*/
						int distance = nextNodeToExpand.separationDistance + 1;
						for(Node neighborToAdd : neighborsToAdd) {
							if(neighborToAdd != node) {
								neighbors.add(neighborToAdd);
								if(nextNodeToExpand.separationDistance < separationDistance - 1) {
									nodesToExpand.add(new NodeAndSeparationDistance(neighborToAdd, distance));
								}
							}
						}
						
					}
				}		
			}
		}
		return neighbors;
	}	

	protected static class NodeAndSeparationDistance {
		public Node node;
		
		public int separationDistance;
		
		public NodeAndSeparationDistance(Node node, int separationDistance) {
			this.node = node;
			this.separationDistance = separationDistance;
		}
	}
}