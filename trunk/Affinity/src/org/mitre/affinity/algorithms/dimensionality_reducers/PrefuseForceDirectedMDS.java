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

package org.mitre.affinity.algorithms.dimensionality_reducers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.mitre.affinity.algorithms.IProgressMonitor;
import org.mitre.affinity.model.Position;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.clusters.ClusterObjectPairValue;
import org.mitre.affinity.model.clusters.DistanceGrid;
import org.mitre.affinity.util.AffinityUtils;

import prefuse.util.force_craig.AbstractForce;
import prefuse.util.force_craig.DragForce;
import prefuse.util.force_craig.ForceItem;
import prefuse.util.force_craig.ForceSimulator;
import prefuse.util.force_craig.NBodyForce;
import prefuse.util.force_craig.SpringForce;

//import prefuse.util.force.NBodyForce;


/**
 * An MDS implementation that uses the Prefuse force simulator to create a simulation
 * where each particle (schema) is tensioned by a series of springs whose resting length
 * is the distance between two schemas.  The simulator uses a simulated annealing schedule
 * over which it attempts to bring this system to an equilibrium state.
 * 
 * @author CBONACETO
 *
 */
public class PrefuseForceDirectedMDS<K extends Comparable<K>> implements IMultiDimScaler<K> {
	
	/**
	 * The number of simulator iterations to run in bringing the system to equilibrium
	 * (about 1000 iterations seems to be enough) 
	 */
	private int numIterations = 1000; //previously 2500
	
	/**
	 * List of cluster object IDs
	 */
	private Collection<K> objectIDs;
	
	public int getNumIterations() {
		return numIterations;
	}

	public void setNumIterations(int numIterations) {
		this.numIterations = numIterations;
	}	

	public Collection<K> getObjectIDs() {
		return objectIDs;
	}

	public void setObjectIDs(Collection<K> objectIDs) {
		this.objectIDs = objectIDs;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PositionGrid<K> scaleDimensions(DistanceGrid<K> dg, boolean isSymmetric,
			boolean isMetric, int numDimensions, boolean metric, IProgressMonitor progressMonitor) {		
		
//		System.out.println("Distance grid: " + dg);
//		System.out.println("Size of dg is: " + dg.getValues().size());
				
		if(objectIDs == null) 
			throw new IllegalArgumentException("Error in PrefuseForceDirectedMDS: Must first set objectIDs before running scaleDimensions.");		
		
		//Deterministic randomness
		AbstractForce.rand = new Random(234L);
		
		ForceSimulator fs = runForceSimulator(objectIDs, dg, this.numIterations, progressMonitor);
		
		PositionGrid<K> pg = new PositionGrid<K>(numDimensions);
		
		Iterator iter = fs.getItems();
		while(iter.hasNext()) {
			ForceItemForClusterObject<K> fi = (ForceItemForClusterObject)iter.next();
			if(numDimensions == 2)
				pg.setPosition(fi.objectID, new Position(fi.location[0], fi.location[1]));
			else {
				pg.setPosition(fi.objectID, new Position(fi.location));				
			}

		}
		return pg;
	}
	
	protected ForceSimulator runForceSimulator(Collection<K> objectIDs, DistanceGrid<K> dg, int numIterations,
			IProgressMonitor progressMonitor) {
		ForceSimulator fs = new ForceSimulator();
		fs.addForce(new NBodyForce());
		fs.addForce(new SpringForce());
		fs.addForce(new DragForce());
		
		if(progressMonitor != null) {
			progressMonitor.setProgress(0);
			progressMonitor.setNote("Initializing Forces");
		}
		
		//Create a ForceItem for each schema
		Map<K, ForceItemForClusterObject<K>> forceItems = new HashMap<K, ForceItemForClusterObject<K>>(); //maps object ID to ForceItem for that object
		for(K objectID : objectIDs) {
			ForceItemForClusterObject<K> fi = new ForceItemForClusterObject<K>(objectID);
			forceItems.put(objectID, fi);
			fs.addItem(fi);
		}
				
		//System.out.println("About to add a spring for each distance grid entry");
		//Add a spring for each distance grid entry where the spring length is the distance
		for(ClusterObjectPairValue<K, Double> value : dg.getValues()) {
			//System.out.println("Pair is: " + value.getSchema1ID() + " " + value.getSchema2ID());
			ForceItem fi1 = forceItems.get(value.getObject1ID());
			ForceItem fi2 = forceItems.get(value.getObject2ID());	
			fs.addSpring(fi1, fi2, value.getValue().floatValue());
		}
		//System.out.println("finished adding a spring for each distance grid entry");
		long timestep = 1000L;		
		float progressPerIteration = 100f/numIterations;
		for (int i = 0; i < numIterations; i++)  {
			// use an annealing schedule to set time step
			timestep *= (1.0 - i/(double)numIterations);
			long step = timestep+50;
			// run simulator
			fs.runSimulator(step);
			if(progressMonitor != null) {
				progressMonitor.setProgress((int)(progressPerIteration * i));
				progressMonitor.setNote("Simulating Force Interactions");
			}
		}
		
		if(progressMonitor != null) {
			progressMonitor.setProgress(100);
		}
		
		return fs;
	}	
	
	/**
	 * Creates a prefuse graph from the given distance grid
	 * 
	 * @param dg
	 */
	/*
	public static void createGraphFromDistanceGrid(DistanceGrid dg, boolean isSymmetric) {
		ForceSimulator fs = new ForceSimulator();
		fs.addForce(new NBodyForce());
		fs.addForce(new SpringForce());
		fs.addForce(new DragForce());
		
		ForceItem fi1 = new ForceItem();
		fi1.mass = 1.0f;		
		fi1.location[0] = fi1.location[1] = 5;		
		fi1.force[0] = fi1.force[1] = 0;
		fi1.velocity[0] = fi1.velocity[1] = 0;		
		ForceItem fi2 = new ForceItem();		
		fi2.mass = 1.0f;		
		fi2.location[0] = fi2.location[1] = 20;
		fi2.force[0] = fi2.force[1] = 0;
		fi2.velocity[0] = fi2.velocity[1] = 0;
		
		ForceItem fi3 = new ForceItem();		
		fi3.mass = 1.0f;		
		fi3.location[0] = fi3.location[1] = 20;
		fi3.force[0] = fi3.force[1] = 0;
		fi3.velocity[0] = fi3.velocity[1] = 0;
		
		fs.addItem(fi1);
		fs.addItem(fi2);
		fs.addItem(fi3);
		
		//Add a spring for each distance grid entry where the distance is the spring length
		fs.addSpring(fi1, fi2, 10);
		fs.addSpring(fi1, fi3, 30);
		fs.addSpring(fi2, fi3, 30);
		
		long timestep = 1000L;
		int num_iterations = 1000;
		for ( int i = 0; i < num_iterations; i++ ) {
			// use an annealing schedule to set time step
			timestep *= (1.0 - i/(double)num_iterations);
			long step = timestep+50;
			// run simulator
			fs.runSimulator(step);           
		}
		
		//Display force item positions
		Iterator itemIter = fs.getItems();
		int i = 1;
		while(itemIter.hasNext()) {
			ForceItem fi = (ForceItem)itemIter.next();
			System.out.println("Location " + i + ": " + fi.location[0] + ", " + fi.location[1]);
			i++;
		}
		
		int row = 1, col;
		Iterator iter1 = fs.getItems();
		while(iter1.hasNext()) {
			fi1 = (ForceItem)iter1.next();
			col = 1;
			Iterator iter2 = fs.getItems();			
			while(iter2.hasNext()) {				
				fi2 = (ForceItem)iter2.next();
				if(row != col) {
					System.out.println("Distance between " + row + " and " + col + ": " + computeDistance(fi1, fi2));
				}
				col++;
				if(isSymmetric && col >= row)
					break;
			}
			row++;
		}
	}
	 */
	
	/**
	 * Computes the Euclidean distance between two ForceItems.
	 * 
	 * @param f1
	 * @param f2
	 * @return
	 */
	public static double computeDistance(ForceItem f1, ForceItem f2) {
		//System.out.println("computing distance between (" + f1.location[0] + "," + f1.location[1] + ") and (" + f2.location[0] + "," + f2.location[1] + ")");
		return AffinityUtils.computeDistance(f1.location[0], f1.location[1], f2.location[0], f2.location[1]);
	}
	
	public static class ForceItemForClusterObject<K> extends ForceItem {
		final K objectID;
		
		public ForceItemForClusterObject(K objectID) {
			super();
			this.objectID = objectID;
		}
	}	
	
	public static void main(String[] args) {	
		DistanceGrid<Integer> dg = AffinityUtils.createSampleDistancesGrid();		
		dg.rescale(0, 1000);
		
		ArrayList<Integer> schemas = new ArrayList<Integer>();
		for(int i=1; i<=9; i++) {			
			schemas.add(i);
		}				
		
		PrefuseForceDirectedMDS<Integer> mds = new PrefuseForceDirectedMDS<Integer>();
		mds.setObjectIDs(schemas);
		PositionGrid<Integer> pg = mds.scaleDimensions(dg, true, false, 2, false, null);	
		
		new MDSTester(schemas, dg, pg);
	}
}