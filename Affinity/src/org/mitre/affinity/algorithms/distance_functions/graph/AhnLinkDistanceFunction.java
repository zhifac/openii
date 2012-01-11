package org.mitre.affinity.algorithms.distance_functions.graph;

import java.util.ArrayList;
import java.util.Collection;
//import java.util.HashSet;

//import org.mitre.affinity.algorithms.distance_functions.CommonDistanceMetrics;
import org.mitre.affinity.algorithms.IProgressMonitor;
import org.mitre.affinity.algorithms.distance_functions.DistanceFunction;
import org.mitre.affinity.model.IClusterObjectManager;
import org.mitre.affinity.model.clusters.DistanceGrid;
import org.mitre.affinity.model.graph.GraphObject;
import org.mitre.affinity.model.graph.GraphClusterObjectManager;

import communityFinder.Edge;
//import communityFinder.Metrics;
import communityFinder.Node;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * @author CBONACETO
 *
 */
public class AhnLinkDistanceFunction implements DistanceFunction<String, GraphObject> {
	
	// for now, we use a static value to permit filtering of high-degree nodes
	//private static int MAX_DEGREE = Integer.MAX_VALUE;

	@Override
	public String getName() {
		return "Link Community Distance Function (Ahn)";
	}

	@Override
	public DistanceGrid<String> generateDistanceGrid(Collection<String> objectIDs,
			IClusterObjectManager<String, GraphObject> clusterObjectManager, IProgressMonitor progressMonitor) {
		
		if(!(clusterObjectManager instanceof GraphClusterObjectManager)) {
			throw new IllegalArgumentException("Error using " + getName() + ": Can only be used with a link community  manager.");
		}

		//Get the graph
		UndirectedSparseGraph<Node, Edge> g = ((GraphClusterObjectManager)clusterObjectManager).getGraph();
		if(g == null) {
			return null;
		}
		
		DistanceGrid<String> dg = new DistanceGrid<String>();
		
		//Compute Jaccard distance between every edge
		ArrayList<Edge> edges = new ArrayList<Edge>(g.getEdges());
		for(int i=0; i<edges.size(); i++) {
			for(int j=i+1; j<edges.size(); j++) {
				Edge edge1 = edges.get(i);
				Edge edge2 = edges.get(j);
				double distance = 1;
				//Keystone is node incident to edge1 and edge2, if no keystone, distance is max distance (1)
				Node keystone = null;
				if(edge1.getSourceNode() == edge2.getSourceNode() || edge1.getSourceNode() == edge2.getDestNode()) {
					keystone = edge1.getSourceNode();
				}
				else if(edge1.getDestNode() == edge2.getSourceNode() || edge1.getDestNode() == edge2.getDestNode()) {
					keystone = edge1.getDestNode();
				}
				if(keystone != null) {
					distance = 1 - jaccard(edge1, edge2, keystone, g);
				}
				//TODO: Memory storage optimization - only store distance if < 1, have dg return 1 if distance is null
				dg.set(edge1.getEdgeName(), edge2.getEdgeName(), distance);
			}
		}		

		// For every node, compute the Jaccard distance between every pair of incident edges		
		/*HashSet<Node> neighborSet1 = new HashSet<Node>();
		for(Node n : g.getVertices()) {
			Collection<Edge> incidentEdges = g.getIncidentEdges(n);
			if (incidentEdges.size()<=MAX_DEGREE){
				// To facilitate enumeration of pairs of edges, store in an array
				Edge [] edgeList = new Edge[incidentEdges.size()];
				int i = 0;
				for(Edge e : incidentEdges) {
					edgeList[i++] = e;
				}
				
				// For each pair of edges incident to node n, find the intersection
				// and union of neighbors of the non-shared nodes
				for(i = 0; i < edgeList.length - 1; i++) {
					Edge edge1 = edgeList[i];
					Node n1 = edge1.getOppositeEnd(n);					
					Collection<Node> neighbors1 = g.getNeighbors(n1);
					
					if (neighbors1 != null) {
						neighborSet1.clear();
						neighborSet1.addAll(neighbors1);
						neighborSet1.add(n1);// Ahn's n+
						for(int j = i + 1; j < edgeList.length; j++) {
							Edge edge2 = edgeList[j];
							Node n2 = edge2.getOppositeEnd(n);
							int intersectionSize = 0;
							int unionSize = neighborSet1.size();
							Collection<Node> neighbors2 = g.getNeighbors(n2);
							
							if(neighbors2 != null) {
								for(Node neighbor : neighbors2) {
									if(neighborSet1.contains(neighbor)) {
										intersectionSize++;
									} else {
										unionSize++;
									}
								}
								// Include n2 itself as well as n2's neighbors in
								// calculating n+(n2) as per Ahn et al.
								if(!neighborSet1.contains(n2)) {
									unionSize++;
								} else {
									intersectionSize++;
								}

								//Compute the Jaccard distance value and store in the distance grid
								//double jaccard = unionSize == 0 ? 0.0 : (double)intersectionSize / (double)unionSize;
								double distance = CommonDistanceMetrics.computeJaccardDistance(unionSize, intersectionSize);
								dg.set(edge1.getEdgeName(), edge2.getEdgeName(), distance);	
								//System.out.println("Setting " + edge1.getEdgeName() + "," + edge2.getEdgeName() + " to: " + distance);
							}
						}
					}
				}
			}
		}*/		
		return dg;
	}
	
	/**
	 * @author Karl Branting
	 * @version 1.0
	 * Ahn's metric, consisting of the jaccard of the inclusive
	 * neighborhoods of n1 and n2
	 * @param edge1 - Edge for which similarity to edge2 is to be determined
	 * @param edge2 - Edge for which similarity to edge1 is to be determined
	 * @param keystone - Node incident to edge1 and edge2
	 */
	public static double jaccard(Edge edge1, Edge edge2, Node keystone,
			UndirectedSparseGraph<Node, Edge> g) {
		Node n1 = edge1.getOppositeEnd(keystone);
		Node n2 = edge2.getOppositeEnd(keystone);
		return jaccard(n1, n2, g);
	}
	/**
	 * @param n1 - an impost Node
	 * @param n2 - an impost Node
	 * @param keystone - Node incident to edge1 and edge2
	 */
	public static double jaccard(Node n1, Node n2, UndirectedSparseGraph<Node, Edge> g) {
		Collection<Node> neighbors1 = g.getNeighbors(n1);
		Collection<Node> neighbors2 = g.getNeighbors(n2);	
		
		int intersectionSize = 0;
		int unionSize = neighbors1.size()+1; // n1's neighbors plus n1 
		for (Node neighbor : neighbors2) {
			if (neighbors1.contains(neighbor)||neighbor.equals(n1)) {
				intersectionSize++;
			} else {
				unionSize++;
			}
		}
		// Include n2 itself as well as n2's neighbors in
		// calculating n+(n2) as per Ahn et al.
		if(neighbors1.contains(n2)) {
			intersectionSize++; 
		} else {
			unionSize++;
		}		
		return unionSize == 0 ? 0.0 : (double) intersectionSize / (double) unionSize;
	}
}