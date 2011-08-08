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

package org.mitre.affinity.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.clusters.ClusterStep;
import org.mitre.affinity.clusters.ClustersContainer;
import org.mitre.affinity.clusters.DistanceGrid;
import org.mitre.affinity.clusters.clusterers.HierarchicalClusterer;
import org.mitre.affinity.util.AffinityUtils;
import org.mitre.schemastore.model.Schema;

import edu.iu.iv.visualization.dendrogram.model.BasicDendrogramNode;
import edu.iu.iv.visualization.dendrogram.model.Dendrogram;
import edu.iu.iv.visualization.dendrogram.model.DendrogramNode;

public class SchemaDendrogram extends Dendrogram {
	
	public SchemaDendrogram(SchemaDendrogramNode root) {
		super(root);
	}
	
	/**
	 * @param schemas: List of schemas
	 * @param dg: Distance grid with distance between each pair of schemas
	 * @return: A dendrogram
	 * @throws IllegalArgumentException
	 */
	public static SchemaDendrogram createDendrogram(List<Schema> schemas, DistanceGrid dg) throws IllegalArgumentException {	
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();		 
		for(Schema s : schemas) {
			schemaIDs.add(s.getId());			
		}
		
		HierarchicalClusterer hc = new HierarchicalClusterer();
		ClustersContainer cc = hc.generateClusters(schemaIDs, dg);
		
		//DEBUG CODE
		/*
		System.out.println("Schemas:");
		for(SchemaDocument s : schemas) {
			System.out.println(s.getId() + ": " + s.getName());
		}
		System.out.println("Distance Grid:");
		for(Map.Entry<SchemaIDPair, Double> dgEntry : dg) {
			System.out.println(dgEntry.getKey().getSchema1ID() + "," + dgEntry.getKey().getSchema2ID() + ": " + dgEntry.getValue());
		}
		
		System.out.println("Clusters: ");
		printClusters(cc);	
		*/	
		//END DEBUG CODE
		
		//ClustersContainer newCC = AffinityUtils.normalizeClusterSteps(schemaIDs, cc);
		ClustersContainer newCC = AffinityUtils.removeDuplicateClusterGroups(schemaIDs, cc);
		//System.out.println("Normalized Clusters: ");
		//printClusters(newCC);
		
		return createDendrogram(schemas, newCC);
	}
	
	/**
	 * @param schemaIDs: List of schema IDs
	 * @param cc:  Cluster container
	 * @return A dendrogram
	 */
	public static SchemaDendrogram createDendrogram(List<Schema> schemas, ClustersContainer cc) {	
		Map<Integer, SchemaDendrogramNode> nodes = new HashMap<Integer, SchemaDendrogramNode>(); 
		int i = 1;
		for(Schema s : schemas) {
			Integer sId = s.getId();
			int pos = i;
			//DEBUG CODE
			if(sId == 4)
				pos = 2;
			else if(sId == 2) 
				pos = 3;
			else if(sId == 3)
				pos = 4;
			//System.out.println("i=" + i + ", pos=" + pos);
			//END DEBUG CODE
			//nodes.put(sId, new SchemaDendrogramNode(pos, s.getName()));
			nodes.put(sId, new SchemaDendrogramNode(pos, s.getId().toString()));
			i++;
		}		
		
		//Convert ClusterContainer to a Dendrogram 
		//Note: First cluster step (0) is the bottom of the Dendrogram
		//DendrogramNode[][] steps = new DendrogramNode[cc.getNumClusterSteps()][];
		SchemaDendrogramNode[][] steps = new SchemaDendrogramNode[cc.getNumClusterSteps()][];	
		SchemaDendrogramNode rootNode = null;
		int step = 0;
		for(ClusterStep cs : cc) {
			steps[step] = new SchemaDendrogramNode[cs.getClusterGroups().size()];
			int cgIndex = 0;
			for(ClusterGroup cg : cs.getClusterGroups()) {
				if(step == 0) {
					if(cg.getSchemaIDs() == null || cg.getSchemaIDs().size() != 1) {
						throw new IllegalArgumentException
							("Error rendering dendrogram: Each cluster group at step 0 must contain exactly 1 schema.");
					}
					steps[step][cgIndex] = nodes.get((cg.getSchemaIDs().get(0)));
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
					if(cg.getSchemaIDs() == null || cg.getSchemaIDs().size() != 2) {
						throw new IllegalArgumentException
							("Error rendering dendrogram: Each cluster group at step 1 must contain exactly 2 schemas.");
					}										 
					steps[step][cgIndex] = new SchemaDendrogramNode(cg.getDistance().floatValue(),
							nodes.get(cg.getSchemaIDs().get(0)), 
							nodes.get(cg.getSchemaIDs().get(1)));
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
					SchemaDendrogramNode node = new SchemaDendrogramNode(cg.getDistance().floatValue(), createLabel(cg));
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
							SchemaDendrogramNode currNode = steps[searchStep][searchNode];
							//if(currNode == null) {
							//	System.out.println("current node is null: step=" + step + ", searchStep=" + searchStep + ", searchNode=" + searchNode);
							//}
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
		return new SchemaDendrogram(rootNode);		
	}
	
	private static String createLabel(ClusterGroup cg) {
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for(Integer sId : cg) {
			sb.append(sId);
			if(index != cg.getSchemaIDs().size() - 1) {
				sb.append(", ");
			}
			index++;
		}
		return sb.toString();
	}
	
	public static void printClusters(ClustersContainer cc) {
		int step = 0;
		for(ClusterStep cs : cc) {
			System.out.print("Step " + step + ": ");
			for(ClusterGroup cg : cs) {
				System.out.print("{");
				for(Integer sId : cg) {
					System.out.print(sId + " ");
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
}
