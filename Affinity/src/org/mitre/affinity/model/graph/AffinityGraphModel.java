package org.mitre.affinity.model.graph;

import java.util.Collection;
import java.util.HashSet;

import org.mitre.affinity.algorithms.IProgressMonitor;
import org.mitre.affinity.algorithms.clusterers.Clusterer;
import org.mitre.affinity.algorithms.dimensionality_reducers.IMultiDimScaler;
import org.mitre.affinity.algorithms.distance_functions.DistanceFunction;
import org.mitre.affinity.model.AffinityModel;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.ClusterStep;
import org.mitre.affinity.model.clusters.ClustersContainer;

import communityFinder.Edge;
import communityFinder.Node;

/**
 * The objects being clustered are edges (links). 
 * 
 * @author CBONACETO
 *
 */
public class AffinityGraphModel extends AffinityModel<String, GraphObject> {	

	protected final GraphClusterObjectManager clusterObjectManager;
	
	protected Double bestCommunityClusterDistance;
	
	public AffinityGraphModel(GraphClusterObjectManager clusterObjectManager) {
		this.clusterObjectManager = clusterObjectManager;
	}
	
	@Override
	public GraphClusterObjectManager getClusterObjectManager() {
		return clusterObjectManager;
	}
	
	@Override
	public void setClusters(ClustersContainer<String> clusters) {
		super.setClusters(clusters);
		bestCommunityClusterDistance = null;
	}

	@Override
	public void generateClustersAndPositionGrid(Collection<String> objectIDs,
			DistanceFunction<String, GraphObject> distanceFunction,
			Clusterer<String> clusterer, IMultiDimScaler<String> mds,
			IProgressMonitor progressMonitor) {
		super.generateClustersAndPositionGrid(objectIDs, distanceFunction, clusterer,
				mds, progressMonitor);
		bestCommunityClusterDistance = null;
	}

	@Override
	public void generateClusters(Collection<String> objectIDs,
			DistanceFunction<String, GraphObject> distanceFunction,
			Clusterer<String> clusterer, IProgressMonitor progressMonitor) {
		super.generateClusters(objectIDs, distanceFunction, clusterer, progressMonitor);
		bestCommunityClusterDistance = null;
	}

	public double getBestCommunityClusterDistance() {
		if(bestCommunityClusterDistance == null) {
			//TODO: Check that this is correct
			if(clusters != null && clusters.getNumClusterSteps() > 0) {
				ClusterStep<String> bestClusterStep = null;
				double maxPartitionDensity = Double.MIN_VALUE;
				//double clusterDistance = 1;
				//int i = 0;
				for(ClusterStep<String> clusterStep : clusters) {
					//System.out.println("At cluster step " + i + ", cluster groups: " + clusterStep.getSize());
					//Compute the partition density of this cluster step using the 
					//equation from Ahn et. al.
					double density = computePartitionDensity(clusterStep);
					if(density > maxPartitionDensity) {
						maxPartitionDensity = density;
						bestClusterStep = clusterStep;
					}
				}
				//Return the cluster distance for the cluster step with the 
				//maximum partition density
				if(bestClusterStep != null) {
					double maxDistance = Double.MIN_VALUE;
					for(ClusterGroup<String> cg : bestClusterStep) {
						if(cg.getDistance() > maxDistance) {
							maxDistance = cg.getDistance();
						}
					}
					bestCommunityClusterDistance = maxDistance;
					//return maxDistance;
				}
			}
		}
		if(bestCommunityClusterDistance == null) {
			return 0;
		}
		else {
			return bestCommunityClusterDistance;	
		}		
	}
	
	/**
	 * Compute the partition density of a cluster step using the equation from Ahn et. al.
	 * From Karl Branting's LinkClusterer.
	 * 
	 * @param clusterStep the cluster step
	 * @return the partition density of the cluster step
	 */
	protected double computePartitionDensity(ClusterStep<String> clusterStep) {
		double result = 0;
		int totalLinks = 0;

		for(ClusterGroup<String> c : clusterStep.getClusterGroups()) {
			int n = nodeCount(c);
			int m = linkCount(c);
			totalLinks += m;

			double denominator = (double)((n-2) * (n-1));
			double numerator = m * (m-(n-1));

			if(denominator != 0.0) {
				result += numerator / denominator;
			}
			//DEBUG CODE
			/*System.out.print("\nn " + n + " m " + m + " totalLinks " + 
					totalLinks + " numerator " + numerator + " denominator " + 
					denominator + " partitionDensity " + (result* 2.0 / totalLinks) + "\n");*/
			//END DEBUG CODE
		}
		
		if(totalLinks==0) {
			return 0.0;
		} else {
			return result * 2.0 / totalLinks;
		}
	}
	
	protected int nodeCount(ClusterGroup<String> c) {
		return getNodes(c).size();
	}
	
	protected HashSet<Node> getNodes(ClusterGroup<String> c) {
		HashSet<Node> nodeSet = new HashSet<Node>();
		getNodes(c, nodeSet);
		return nodeSet;
	}

	protected void getNodes(ClusterGroup<String> c, HashSet<Node> nodeSet) {
		//TODO: Check that this is correct
		if(c.getObjectIDs() != null) {
			for(String edgeID : c.getObjectIDs()) {
				Edge e = clusterObjectManager.getEdge(edgeID);
				if(e != null) {
					nodeSet.add(e.getDestNode());
					nodeSet.add((Node)e.getSourceNode());
				}
			}
		}
		/*//if(c instanceof Cluster.LeafNode) {
		if(c.getNumClusterObjects() == 1) {
			//Cluster.LeafNode<Edge> ln = (Cluster.LeafNode<Edge>)c;
			//Edge e = ln.getContent();
			Edge e = clusterObjectManager.getClusterObject(c.getObjectIDs().get(0));
			nodeSet.add(e.getDestNode());
			nodeSet.add((Node) e.getSourceNode());
		} else {
			Cluster.InternalNode<Edge> in = (Cluster.InternalNode<Edge>)c;
			getNodes(in.getChild1(), nodeSet);
			getNodes(in.getChild2(), nodeSet);
		}*/
	}

	protected int linkCount(ClusterGroup<String> c) {
		//TODO: Check that this is correct
		return c.getNumClusterObjects();
		/*if(c instanceof Cluster.InternalNode) {
			Cluster.InternalNode<Edge> in = (Cluster.InternalNode<Edge>) c;
			return linkCount(in.getChild1()) + linkCount(in.getChild2());
		} else {
			return 1;
		}*/
	}
}