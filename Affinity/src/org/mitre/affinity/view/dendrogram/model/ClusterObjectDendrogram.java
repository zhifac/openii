/*
 *  Copyright 2010 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mitre.affinity.view.dendrogram.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import org.mitre.affinity.algorithms.clusterers.HierarchicalClusterer;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.ClusterStep;
import org.mitre.affinity.model.clusters.ClustersContainer;
//import org.mitre.affinity.model.clusters.DistanceGrid;

import edu.iu.iv.visualization.dendrogram.model.BasicDendrogramNode;
import edu.iu.iv.visualization.dendrogram.model.Dendrogram;
import edu.iu.iv.visualization.dendrogram.model.DendrogramNode;

/**
 * @author CBONACETO
 *
 */
public class ClusterObjectDendrogram<K extends Comparable<K>, V> extends Dendrogram {
	
	/**
	 * @param root
	 */
	public ClusterObjectDendrogram(ClusterObjectDendrogramNode<K> root) {
		super(root);
	}
	
	/**
	 * @param clusterObjects List of cluster objects
	 * @param objectIDs List of cluster object IDs
	 * @param cc the clusters
	 */
	//public ClusterObjectDendrogram(List<V> clusterObjects, Collection<K> objectIDs, DistanceGrid<K> dg, ClustersContainer<K> cc){
	public ClusterObjectDendrogram(List<V> clusterObjects, Collection<K> objectIDs, ClustersContainer<K> cc) {
		super(new DendrogramCreator<K, V>().createDendrogram(clusterObjects, objectIDs, cc));
	}	
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ClusterObjectDendrogramNode<K>[] getLeaves() {
		DendrogramNode[] leaves = super.getLeaves();
		if(leaves != null) {
			ClusterObjectDendrogramNode<K>[] clusterObjectLeaves = new ClusterObjectDendrogramNode[leaves.length];
			for(int i = 0; i<leaves.length; i++) {
				clusterObjectLeaves[i] = (ClusterObjectDendrogramNode)leaves[i];
			}
			return clusterObjectLeaves;
		}
		return null;		
	}

	public void printClusters(ClustersContainer<K> cc) {
		int step = 0;
		for(ClusterStep<K> cs : cc) {
			System.out.print("Step " + step + ": ");
			for(ClusterGroup<K> cg : cs) {
				System.out.print("{");
				for(K objectID : cg) {
					System.out.print(objectID + " ");
				}
				System.out.print("} ");
			}
			System.out.println();
			step++;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		char newline = '\n';
		
		java.util.Queue<DendrogramNode> nodeQueue = new LinkedList<DendrogramNode>();
		nodeQueue.add(this.root);
		while(!nodeQueue.isEmpty()) {
			DendrogramNode currNode = nodeQueue.poll();
			if(currNode != null) {
				sb.append("Node " + currNode.getLabel() + ": ");
				sb.append("Value: " + currNode.getValue() + ", ");
				if(currNode.getLeftChild() != null) {
					sb.append("Left Child: " + currNode.getLeftChild().getLabel());
					nodeQueue.add(currNode.getLeftChild());				
				}
				else {
					sb.append("Left Child: null");
				}
				if(currNode.getRightChild() != null) {
					sb.append(", Right Child: " + currNode.getRightChild().getLabel() + newline);
					nodeQueue.add(currNode.getRightChild());
				}
				else {
					sb.append(", Right Child: null" + newline);
				}
			}
			else {
				sb.append("Node: null" + newline);
			}
		}
		sb.append("Leaf Nodes: ");
		DendrogramNode[] leaves = this.getLeaves();
		if(leaves != null) {
			for(int i=0; i<leaves.length; i++) {
				sb.append(leaves[i].getLabel());
				if(i != leaves.length - 1) {
					sb.append(", ");
				}
			}
		}
		
		return sb.toString();
	}	

	public static Dendrogram createSampleDendrogram() {
		DendrogramNode[] d = new DendrogramNode[7];
        
        d[0] = new BasicDendrogramNode(1,"e1");
        d[1] = new BasicDendrogramNode(2,"e2");
        d[2] = new BasicDendrogramNode(3,"e3");
        d[3] = new BasicDendrogramNode(4,"e4");
        d[4] = new BasicDendrogramNode(5,"e5");
        d[5] = new BasicDendrogramNode(6,"e6");
        d[6] = new BasicDendrogramNode(7,"e7");
        
        DendrogramNode[] l1 = new DendrogramNode[4];
        
        l1[0] = new BasicDendrogramNode((float) 0.2,d[0],d[1]);
        l1[1] = new BasicDendrogramNode((float) 0.5,d[2],d[3]);
        l1[2] = new BasicDendrogramNode((float) 0.3,d[4],d[5]);
        l1[3] = new BasicDendrogramNode((float) 0.8,d[6],null);
                
        DendrogramNode[] l2 = new DendrogramNode[2];
        
        l2[0] = new BasicDendrogramNode((float) .4,l1[0],l1[1]);
        l2[1] = new BasicDendrogramNode((float) .2,l1[2],l1[3]);
        
        BasicDendrogramNode root = new BasicDendrogramNode((float) 1,null,l2[0],l2[1]);
        
        return new Dendrogram(root);
	}	
	
	protected static class DendrogramCreator<K extends Comparable<K>, V> {
		/*protected ClusterObjectDendrogramNode<K> createDendrogram(List<V> clusterObjects, Collection<K> objectIDs, DistanceGrid<K> dg) {
			HierarchicalClusterer<K> hc = new HierarchicalClusterer<K>();
			ClustersContainer<K> cc = hc.generateClusters(objectIDs, dg);
			ClustersContainer<K> newCC = cc.removeDuplicateClusterGroups(objectIDs, cc);
			return createDendrogram(clusterObjects, objectIDs, newCC);
		}*/
		
		/**
		 * @param clusterObjects
		 * @param objectIDs
		 * @param cc
		 * @return the root node
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		protected ClusterObjectDendrogramNode<K> createDendrogram(List<V> clusterObjects, Collection<K> objectIDs, ClustersContainer<K> cc) {	
			Map<K, ClusterObjectDendrogramNode<K>> nodes = new HashMap<K, ClusterObjectDendrogramNode<K>>(); 
			int i = 1;
			for(K objectID : objectIDs) {
				int pos = i;
				//DEBUG CODE
				/*if(sId == 4)
				pos = 2;
			else if(sId == 2) 
				pos = 3;
			else if(sId == 3)
				pos = 4;
			System.out.println("i=" + i + ", pos=" + pos);*/
				//END DEBUG CODE
				nodes.put(objectID, new ClusterObjectDendrogramNode<K>(pos, objectID.toString(), objectID));
				i++;
			}		

			//Convert ClusterContainer to a Dendrogram 
			//Note: First cluster step (0) is the bottom of the Dendrogram
			ClusterObjectDendrogramNode<K>[][] steps = new ClusterObjectDendrogramNode[cc.getNumClusterSteps()][];	
			ClusterObjectDendrogramNode<K> rootNode = null;
			int step = 0;
			for(ClusterStep<K> cs : cc) {
				steps[step] = new ClusterObjectDendrogramNode[cs.getClusterGroups().size()];
				int cgIndex = 0;
				for(ClusterGroup<K> cg : cs.getClusterGroups()) {
					if(step == 0) {
						if(cg.getObjectIDs() == null || cg.getObjectIDs().size() != 1) {
							throw new IllegalArgumentException
							("Error rendering dendrogram: Each cluster group at step 0 must contain exactly 1 cluster object.");
						}
						steps[step][cgIndex] = nodes.get((cg.getObjectIDs().get(0)));
						steps[step][cgIndex].setChildren(cg);

						if(cc.getNumClusterSteps()-1 == 0) {
							//There is only one schema, so this is the root node
							if(cs.getClusterGroups().size() != 1) {
								throw new IllegalArgumentException
								("Error rendering dendrogram: There can be only one cluster group in the last step.");
							}
							rootNode = steps[step][cgIndex];
						}
					}
					else if (step == 1) {
						if(cg.getObjectIDs() == null || cg.getObjectIDs().size() != 2) {
							throw new IllegalArgumentException
							("Error rendering dendrogram: Each cluster group at step 1 must contain exactly 2 schemas.");
						}										 
						steps[step][cgIndex] = new ClusterObjectDendrogramNode(cg.getDistance().floatValue(),
								nodes.get(cg.getObjectIDs().get(0)), 
								nodes.get(cg.getObjectIDs().get(1)));
						steps[step][cgIndex].setChildren(cg);
						steps[step][cgIndex].setLabel(createLabel(cg));

						if(cc.getNumClusterSteps()-1 == 1) {
							//This is the last cluster step, so this is the root node
							if(cs.getClusterGroups().size() != 1) {
								throw new IllegalArgumentException
								("Error rendering dendrogram: There can be only one cluster group in the last step.");
							}
							rootNode = steps[step][cgIndex];
						}
					}
					else {					
						//Find the two cluster groups in a previous step that are connected to this cluster group.
						//A cluster group at a previous step is connected to this cluster group iff it is a subset
						//of this cluster group.
						ClusterObjectDendrogramNode<K> node = new ClusterObjectDendrogramNode<K>(cg.getDistance().floatValue(), createLabel(cg), null);
						node.setChildren(cg);
						steps[step][cgIndex] = node;					

						if(step == cc.getNumClusterSteps()-1) {
							//This is the last cluster step, so this is the root node
							if(cs.getClusterGroups().size() != 1) {
								throw new IllegalArgumentException
								("Error rendering dendrogram: There can be only one cluster group in the last step.");
							}
							rootNode = node;
						}					

						boolean childrenFound = false;
						int searchStep = step - 1;
						while(!childrenFound && searchStep >= 0) {
							//For each node at the previous step, determine if its children are contained by this node
							for(int searchNode = 0; searchNode < steps[searchStep].length; searchNode++) {
								ClusterObjectDendrogramNode<K> currNode = steps[searchStep][searchNode];
								if(currNode.getParent() == null && cg.contains(currNode.getChildren())) {
									//The node's children are contained by this node, so connect the nodes
									if(node.getLeftChild() == null) {
										node.setLeftChild(currNode);
									}
									else {
										node.setRightChild(currNode);
										childrenFound = true;
										break;
									}
								}
							}
							searchStep--;
						}
						if(!childrenFound) {
							throw new IllegalArgumentException
							("Error rendering dendrogram: Could not connect cluster group at step" + step + " to children");
						}					
					}
					cgIndex++;
				}
				step++;
			}		
			//return new SchemaDendrogram(rootNode);
			return rootNode;
		}

		protected String createLabel(ClusterGroup<K> cg) {
			StringBuilder sb = new StringBuilder();
			int index = 0;
			for(K objectID : cg) {
				sb.append(objectID);
				if(index != cg.getObjectIDs().size() - 1) {
					sb.append(", ");
				}
				index++;
			}
			return sb.toString();
		}
	}
}