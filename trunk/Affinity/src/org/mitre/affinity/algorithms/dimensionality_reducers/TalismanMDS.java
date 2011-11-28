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

import jp.sourceforge.talisman.mds.Item;
import jp.sourceforge.talisman.mds.MdsMethod;
import jp.sourceforge.talisman.mds.Table;

import org.mitre.affinity.model.Position;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.clusters.ClusterObjectPairValue;
import org.mitre.affinity.model.clusters.DistanceGrid;
import org.mitre.affinity.util.AffinityUtils;

/**
 * An implementation of MDS from the Talisman project.  See http://talisman.sourceforge.jp/mds/index.html.
 * Used under the Apache 2.0 license.
 * 
 * @author CBONACETO
 *
 */
public class TalismanMDS implements IMultiDimScaler<Integer> {
	
	protected ArrayList<Integer> objectIDs;

	private TalismanMDS() {}	
	
	/* (non-Javadoc)
	 * @see org.mitre.affinity.dimensionality_reducers.IMultiDimScaler#scaleDimensions(org.mitre.affinity.clusters.DistanceGrid, boolean, boolean, int, boolean)
	 */
	public PositionGrid<Integer> scaleDimensions(DistanceGrid<Integer> dg, boolean isSymmetric, boolean isMetric, 
			int numDimensions, boolean metric) {
		
		//Create the distance table MdsMethod() requires from the distance grid
		Table<Integer> table = new Table<Integer>();
		for(ClusterObjectPairValue<Integer, Double> value : dg.getValues()) {
			table.addValue(value.getObject1ID(), value.getObject2ID(), value.getValue());
		}
		
		//DEBUG CODE
		/*Object[] names = table.getKeys();
        for(int i = 0; i < names.length; i++){
            for(int j = i + 1; j < names.length; j++){
            	if(table.getValue((Integer)names[i], (Integer)names[j]) == null) {
            		System.out.println("Value for entry "+ names[i] + "," + names[j] + " is null");
            	}
            }
        } */      
        //END DEBUG CODE
		
		//Create MDSMethod instance and run calculate
		MdsMethod<Integer> mds = new MdsMethod<Integer>(table);	
		mds.setLimit(numDimensions);
		//System.out.println("rank: " + mds.getRank());
		//mds.calculate();
		
		//Create and return the PositionGrid from MdsMethod
		PositionGrid<Integer> pg = new PositionGrid<Integer>(numDimensions);
		for(Item item : mds) {
			//System.out.println("num dimensions = " + item.getDimension());
			Position p = new Position(numDimensions);
			//int dim = 0;
			//System.out.println(item.getDimension());
			//for(Number n : item) {
			//Iterator<Number> itemIter = item.iterator();
			for(int dim=0; dim<numDimensions; dim++) {
				//System.out.println(dim);
				//System.out.println("val: " + item.get(dim));
				Double val = item.get(dim).doubleValue();
				//System.out.println("val: " + val);
				if(val.equals(Double.NaN)) {
					//System.out.println("Warning, coordinate is NaN");
					p.setDimensionValue(dim, 0D);
				}
				else {
					p.setDimensionValue(dim, val);
				}
				//System.out.println(dim++ + ": " + n);
			}
			//System.out.println("Setting position for schema " + item.getName() + ": " + p);
			pg.setPosition(new Integer(item.getName()), p);
		}
		
		return pg;
	}
	
	public static void main(String[] args) {
		DistanceGrid<Integer> dg = AffinityUtils.createSampleDistancesGrid();		
		dg.rescale(0, 1000);
		
		ArrayList<Integer> schemas = new ArrayList<Integer>();
		for(int i=1; i<=9; i++) {			
			schemas.add(i);
		}				
		
		TalismanMDS mds = new TalismanMDS();		
		PositionGrid<Integer> pg = mds.scaleDimensions(dg, true, false, 2, false);		
		
		new MDSTester(schemas, dg, pg);
	}

	@Override
	public ArrayList<Integer> getObjectIDs() {
		return objectIDs;
	}

	@Override
	public void setObjectIDs(ArrayList<Integer> objectIDs) {
		this.objectIDs = objectIDs;
	}
}