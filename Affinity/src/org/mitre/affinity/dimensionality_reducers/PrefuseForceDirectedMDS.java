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

package org.mitre.affinity.dimensionality_reducers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.mitre.affinity.clusters.DistanceGrid;
import org.mitre.affinity.clusters.SchemaPairValue;
import org.mitre.affinity.model.Position;
import org.mitre.affinity.model.PositionGrid;
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
public class PrefuseForceDirectedMDS implements IMultiDimScaler {
	
	/**
	 * The number of simulator iterations to run in bringing the system to equilibrium
	 * (about 1000 iterations seems to be enough) 
	 */
	private int numIterations = 2500;
	
	/**
	 * List of schema IDs
	 */
	private ArrayList<Integer> schemaIDs;
	
	public int getNumIterations() {
		return numIterations;
	}

	public void setNumIterations(int numIterations) {
		this.numIterations = numIterations;
	}

	public ArrayList<Integer> getSchemaIDs() {
		return schemaIDs;
	}

	public void setSchemaIDs(ArrayList<Integer> schemaIDs) {
		this.schemaIDs = schemaIDs;
	}

	@SuppressWarnings("unchecked")
	public PositionGrid scaleDimensions(DistanceGrid dg, boolean isSymmetric,
			boolean isMetric, int numDimensions, boolean metric) {
		
		
//		System.out.println("Distance grid: " + dg);
//		System.out.println("Size of dg is: " + dg.getValues().size());
		
		
		if(this.schemaIDs == null) 
			throw new IllegalArgumentException("Error in PrefuseForceDirectedMDS: Must first set schemaIDs before running scaleDimensions.");

		
		//Deterministic randomness
		AbstractForce.rand = new Random(234L);
		
		ForceSimulator fs = runForceSimulator(schemaIDs, dg, this.numIterations);
		
		PositionGrid pg = new PositionGrid(numDimensions);			
		
		Iterator iter = fs.getItems();
		while(iter.hasNext()) {
			ForceItemForSchema fi = (ForceItemForSchema)iter.next();
			if(numDimensions == 2)
				pg.setPosition(fi.schemaID, new Position(fi.location[0], fi.location[1]));
			else {
				pg.setPosition(fi.schemaID, new Position(fi.location));				
			}

		}
		return pg;
	}
	
	protected ForceSimulator runForceSimulator(ArrayList<Integer> schemaIDs, DistanceGrid dg, int numIterations) {
		ForceSimulator fs = new ForceSimulator();
//		System.out.println("About to add a new NBodyForce");
		fs.addForce(new NBodyForce());
//		System.out.println("Added new NBody Force");
		fs.addForce(new SpringForce());
		fs.addForce(new DragForce());
		
		//Create a ForceItem for each schema
		Map<Integer, ForceItemForSchema> forceItems = new HashMap<Integer, ForceItemForSchema>(); //maps schema ID to ForceItem for that schema
		for(Integer schemaID : schemaIDs) {
//			System.out.println("adding a foce item for schema: " + schemaID);
			ForceItemForSchema fi = new ForceItemForSchema(schemaID);
//			System.out.println("finished adding a force item for schema: " + schemaID);
			forceItems.put(schemaID, fi);
//			System.out.println("finished putting into forceItems: " + schemaID);
			fs.addItem(fi);
//			System.out.println("finished fs.addItem(fi) for: " + schemaID);
		}
				
//		System.out.println("About to add a spring for each distance grid entry");
		//Add a spring for each distance grid entry where the spring length is the distance
		for(SchemaPairValue<Double> value : dg.getValues())
		{
//			System.out.println("Pair is: " + value.getSchema1ID() + " " + value.getSchema2ID());
			ForceItem fi1 = forceItems.get(value.getSchema1ID());
			ForceItem fi2 = forceItems.get(value.getSchema2ID());	
			fs.addSpring(fi1, fi2, value.getValue().floatValue());
		}
//		System.out.println("finished adding a spring for each distance grid entry");
		long timestep = 1000L;		
		for ( int i = 0; i < numIterations; i++ ) {
			// use an annealing schedule to set time step
			timestep *= (1.0 - i/(double)numIterations);
			long step = timestep+50;
			// run simulator
			fs.runSimulator(step);           
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
	
	public static class ForceItemForSchema extends ForceItem {
		final Integer schemaID;
		
		public ForceItemForSchema(Integer schemaID) {
			super();
			this.schemaID = schemaID;
		}
	}	
	
	public static void main(String[] args) {	
		DistanceGrid dg = AffinityUtils.createSampleDistancesGrid();		
		dg.rescale(0, 1000);
		
		ArrayList<Integer> schemas = new ArrayList<Integer>();
		for(int i=1; i<=9; i++) {			
			schemas.add(i);
		}				
		
		PrefuseForceDirectedMDS mds = new PrefuseForceDirectedMDS();
		mds.setSchemaIDs(schemas);
		PositionGrid pg = mds.scaleDimensions(dg, true, false, 2, false);	
		
		new MDSTester(schemas, dg, pg);
	}
}
