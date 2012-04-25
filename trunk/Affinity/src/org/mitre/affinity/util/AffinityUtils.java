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

package org.mitre.affinity.util;


import org.eclipse.swt.graphics.Point;
import org.mitre.affinity.model.Position;
import org.mitre.affinity.model.clusters.DistanceGrid;
import org.mitre.affinity.model.clusters.ClusterObjectPairValues.Transformation;

public class AffinityUtils {
	
	private AffinityUtils() {}
	
	/**
	 * Computes the Euclidean distance between two positions.
	 * 
	 * @param p1 - the first position
	 * @param p2 - the second position
	 * @return - the distance between p1 and p2
	 */
	public static double computeDistance(Position p1, Position p2) {
		double sum = 0;
		for(int dim=0; dim<p1.getNumDimensions(); dim++) {
			double diff = p2.getDimensionValue(dim) - p1.getDimensionValue(dim);
			sum += diff*diff;
		}
		return Math.sqrt(sum);
	}
	
	/**
	 * Computes the Euclidean distance between two points
	 * 
	 * @param p1 - the first point
	 * @param p2 - the second point
	 * @return - the distance between p1 and p2
	 */
	public static double computeDistance(Point p1, Point p2) {
		return Math.sqrt(((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y)));
	}
	
	
	/**
	 * Computes the Euclidean distance between two points
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double computeDistance(float x1, float y1, float x2, float y2) {
		return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
	}
	
	/**
	 * Computes the angle between two points in degrees.
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double computeAngle(Point p1, Point p2) {
		return Math.atan((double)(p2.y - p1.y)/(p2.x - p1.x)) * (180/Math.PI);
	}
	
	/**
	 * Computes the angle between two points in degrees.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double computeAngle(int x1, int y1, int x2, int y2) {
		return Math.atan((double)(y2 - y1)/(x2 - x1)) * (180/Math.PI);
	}
	
	/**
	 * Computes an angle given a change in x and a change in y between two points
	 * 
	 * @param xChange
	 * @param yChange
	 * @return
	 */
	public static double computeAngle(double xChange, double yChange) {
		return Math.atan(yChange/xChange) * (180/Math.PI);		
	}
	
	/**
	 * @param angle in degrees
	 * @return cos of the angle
	 */
	public static double cos(double angle) {
		if(angle % 90 == 0) {
			int i = (int)(angle/90) % 4;
			switch(i) {
			case 0: return 1;
			case 1: case 3: return 0; 
			case 2: return -1;			
			}
		}		
		return Math.cos(angle * Math.PI/180D);
	}
	
	/**
	 * @param angle in degrees
	 * @return sin of the angle
	 */
	public static double sin(double angle) {
		if(angle % 90 == 0) {
			int i = (int)(angle/90) % 4;
			switch(i) {
			case 0: case 2: return 0;
			case 1: return 1;
			case 3: return -1;
			}
		}		
		return Math.sin(angle * Math.PI/180D);
	}
	
	/**
	 * Given a list of schemas and a distance grid, creates the cluster steps using hierarchical clustering.
	 * 
	 * @param schemas
	 * @param dg
	 * @return
	 */
	/*public static ClustersContainer createClusters(List<SchemaDocument> schemas, DistanceGrid dg) {	
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();		 
		for(Schema s : schemas) {
			schemaIDs.add(s.getId());			
		}
		
		HierarchicalClusterer hc = new HierarchicalClusterer();
		return hc.generateClusters(schemaIDs, dg);
	}*/
	
	/**
	 * Given a set of schemas and a set of cluster steps generated using hierarchical clustering,
	 * returns a set of cluster steps where duplicate clusters (i.e., clusters contained in prior steps)
	 * have been removed.  (This essentially means we just leave the last cluster group in each cluster step).
	 * 
	 * @param schemaIds - The ids of each schema
	 * @param cc - The clusters container
	 * @return
	 */
	/*public static ClustersContainer removeDuplicateClusterGroups(List<Integer> schemaIds, ClustersContainer cc) {
		ClustersContainer newCC = new ClustersContainer(schemaIds, cc.getDistanceGrid());
		
		int currStep = 0;
		for(ClusterStep cs : cc) {			
			if(currStep > 0) { 
				ClusterStep newStep = new ClusterStep();
				newStep.addGroup(cs.getClusterGroups().get(cs.getSize() - 1));
				newCC.addClusterStep(newStep);
			}
			currStep++;
		}
		
		return newCC;
	}*/
	
	/**
	 * Given a set of schemas and a set of cluster steps generated using hierarchical clustering,
	 * returns a set of cluster steps where duplicate clusters (i.e., clusters contained in prior steps)
	 * have been removed.
	 * 
	 * @param schemaIds
	 * @param cc
	 * @return
	 */
	/*public static ClustersContainer normalizeClusterSteps(List schemaIds, ClustersContainer cc) {
		ClustersContainer newCC = new ClustersContainer(schemaIds, cc.getDistanceGrid());
		
		//newCC.addClusterStep(cc.getClusterStep(0));
		int currClusterSize = 2;
		int currStep = 0;
		ClusterStep newStep = new ClusterStep();
		
		for(int step=0; step<cc.getNumClusterSteps(); step++) {
			ClusterStep cs = cc.getClusterStep(step);
			if(step == 0) {
				//We're at the first step
				//newCC.addClusterStep(cs);
				currStep++;
			}	
			else if(step == cc.getNumClusterSteps() - 1) {
				//We're at the last step
				newCC.addClusterStep(newStep);
				newStep = new ClusterStep();
				newStep.addGroup(cs.getClusterGroups().get(0));
				newCC.addClusterStep(newStep);
			} 
			else {				
				for(ClusterGroup cg : cs) {
					if(cg.getNumSchemas() == currClusterSize) {
						boolean containsClusterGroup = false;
						for(ClusterGroup newCG : newStep) {
							if(newCG.equals(cg)) {
								containsClusterGroup = true;
								break;
							}
						}
						if(!containsClusterGroup)
							newStep.addGroup(cg);
					}
					else if(cg.getNumSchemas() > currClusterSize) {
						newCC.addClusterStep(newStep);
						currStep++;
						currClusterSize = cg.getNumSchemas();
						newStep = new ClusterStep();
						newStep.addGroup(cg);
					}
				}
			}
		}
		
		return newCC;
	}*/
	
	/**
	 * @return a sample distance grid 
	 */
	public static DistanceGrid<Integer> createSampleDistancesGrid() {
		/* The sample grid to create:
		      d1	  d2	  d3	  d4	  d5	  d6	  d7	  d8	  d9
		d1	1.000								
		d2	0.408	1.000							
		d3	0.548	0.894	1.000						
		d4	0.772	0.378	0.338	1.000					
		d5	0.289	0.354	0.316	0.535	1.000				
		d6	0.408	0.500	0.447	0.378	0.354	1.000			
		d7	0.000	0.000	0.000	0.218	0.000	0.577	1.000		
		d8	0.000	0.000	0.000	0.000	0.000	0.500	0.577	1.000	
		d9	0.183	0.224	0.200	0.169	0.316	0.671	0.516	0.894	1.000
		*/
		
		DistanceGrid<Integer> dg = new DistanceGrid<Integer>();
		
		dg.set(9, 1, 0.183);
		dg.set(9, 2, 0.224);
		dg.set(9, 3, 0.200);
		dg.set(9, 4, 0.169);
		dg.set(9, 5, 0.316);
		dg.set(9, 6, 0.671);
		dg.set(9, 7, 0.516);
		dg.set(9, 8, 0.894);
		//dg.setDistance(9, 9, 1.000);
		
		dg.set(8, 1, 0.000);
		dg.set(8, 2, 0.000);
		dg.set(8, 3, 0.000);
		dg.set(8, 4, 0.000);
		dg.set(8, 5, 0.000);
		dg.set(8, 6, 0.500);
		dg.set(8, 7, 0.577);
		//dg.setDistance(8, 8, 1.000);
		
		dg.set(7, 1, 0.000);
		dg.set(7, 2, 0.000);
		dg.set(7, 3, 0.000);
		dg.set(7, 4, 0.218);
		dg.set(7, 5, 0.000);
		dg.set(7, 6, 0.577);
		//dg.setDistance(7, 7, 1.000);
		
		dg.set(6, 1, 0.408);
		dg.set(6, 2, 0.500);
		dg.set(6, 3, 0.447);
		dg.set(6, 4, 0.378);
		dg.set(6, 5, 0.354);
		//dg.setDistance(6, 6, 1.000);
		
		dg.set(5, 1, 0.289);
		dg.set(5, 2, 0.354);
		dg.set(5, 3, 0.316);
		dg.set(5, 4, 0.535);
		//dg.setDistance(5, 5, 1.000);
		
		dg.set(4, 1, 0.772);
		dg.set(4, 2, 0.378);
		dg.set(4, 3, 0.338);
		//dg.setDistance(4, 4, 1.000);
		
		dg.set(3, 1, 0.548);
		dg.set(3, 2, 0.894);
		//dg.setDistance(3, 3, 1.000);
		
		dg.set(2, 1, 0.408);
		//dg.setDistance(2, 2, 1.000);
		
		//dg.setDistance(1, 1, 1.0);
		
		//These are cosine distances (0 = no similarity, 1 = completely the same), so rescale
		//as Euclidean distances using formula sqrt(2-2*val)
		class EuclideanTransform implements Transformation<Double>
			{ public Double transform(Double value) { return Math.sqrt(2-(2*value)); } }
		dg.transform(new EuclideanTransform());
		
		return dg;
	}
	
	public static DistanceGrid<Integer> createSampleDistancesGrid2() {
		/* The sample grid to create:
		      d1	  d2	  d3	  d4	  d5	  
		d1	0.000								
		d2	1.000	0.000							
		d3	2.000	1.000	0.000						
		d4	3.000	2.000	1.000	0.000					
		d5	4.000	3.000	2.000	1.000	0.000		
		*/
		
		DistanceGrid<Integer> dg = new DistanceGrid<Integer>();
		
		dg.set(5, 1, 4.000);
		dg.set(5, 2, 3.000);
		dg.set(5, 3, 2.000);
		dg.set(5, 4, 1.000);
		//dg.setDistance(5, 5, 0.000);
		
		dg.set(4, 1, 3.000);
		dg.set(4, 2, 2.000);
		dg.set(4, 3, 1.000);
		//dg.setDistance(4, 4, 0.000);
		
		dg.set(3, 1, 2.000);
		dg.set(3, 2, 1.000);
		//dg.setDistance(3, 3, 0.000);
		
		dg.set(2, 1, 1.000);
		//dg.setDistance(2, 2, 0.000);
		
		//dg.setDistance(1, 1, 0.000);
		
		return dg;
	}
}