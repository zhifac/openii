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

package org.mitre.affinity.view.venn_diagram.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.mitre.harmony.matchers.MatchGenerator;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;

/**
 * A matrix of Venn Diagram sets.
 * 
 * @author CBONACETO
 *
 */
public class VennDiagramSetsMatrix implements Iterable<ArrayList<VennDiagramSets>> {
	/** Matrix of VennDiagramSets */
	private ArrayList<ArrayList<VennDiagramSets>> matrix;
	
	/** The minimum score threshold used to determine matching elements */
	private double minMatchScoreThreshold = 0.5d;
	
	/** The maximum score threshold used to determine matching elements */
	private double maxMatchScoreThreshold = 1.d;
	
	private int numSchemas;
	
	public VennDiagramSetsMatrix(List<FilteredSchemaInfo> schemaInfos, double minMatchScoreThreshold, double maxMatchScoreThreshold, MatchGenerator matchScoreComputer) {	
		numSchemas = schemaInfos.size();
		//int numCols = numSchemas;
		int rowIndex = 0;
		this.matrix = new ArrayList<ArrayList<VennDiagramSets>>(numSchemas);
		this.minMatchScoreThreshold = minMatchScoreThreshold;
		this.maxMatchScoreThreshold = maxMatchScoreThreshold;
		for(FilteredSchemaInfo schema1 : schemaInfos) {
			//ArrayList<VennDiagramSets> row = new ArrayList<VennDiagramSets>(numCols);
			ArrayList<VennDiagramSets> row = new ArrayList<VennDiagramSets>(numSchemas);
			int colIndex = 0;
			for(FilteredSchemaInfo schema2 : schemaInfos) {
				if(colIndex >= rowIndex) {		
					VennDiagramSets vd = new VennDiagramSets(schema1, schema2, minMatchScoreThreshold,
							maxMatchScoreThreshold, matchScoreComputer);
					row.add(vd);					
				}
				else {
					row.add(matrix.get(colIndex).get(rowIndex));
				}
				colIndex++;
			}
			matrix.add(row);
			rowIndex++;
			//numCols--;
		}
	}

	/** constructor without min and max defined uses 1.0 for max and greatest stdDev for min **/
	public VennDiagramSetsMatrix(List<FilteredSchemaInfo> schemaInfos, MatchGenerator matchScoreComputer) {	
		numSchemas = schemaInfos.size();
		//int numCols = numSchemas;
		int rowIndex = 0;
		this.matrix = new ArrayList<ArrayList<VennDiagramSets>>(numSchemas);
		
		this.minMatchScoreThreshold = 0.6;
		this.maxMatchScoreThreshold = 1.0;
		
		for(FilteredSchemaInfo schema1 : schemaInfos) {
			//ArrayList<VennDiagramSets> row = new ArrayList<VennDiagramSets>(numCols);
			ArrayList<VennDiagramSets> row = new ArrayList<VennDiagramSets>(numSchemas);
			int colIndex = 0;
			for(FilteredSchemaInfo schema2 : schemaInfos) {
				if(colIndex >= rowIndex) {		
					VennDiagramSets vd = new VennDiagramSets(schema1, schema2, minMatchScoreThreshold,
							maxMatchScoreThreshold, matchScoreComputer);
					row.add(vd);					
				}
				else {
					row.add(matrix.get(colIndex).get(rowIndex));
				}
				colIndex++;
			}
			matrix.add(row);
			rowIndex++;
			//numCols--;
		}

		this.minMatchScoreThreshold = greatestStdDv();
		//System.out.println("Min threshold/greatest stdev should be: " + minMatchScoreThreshold);
		this.setMatchScoreThresholdRange(greatestStdDv(), 1.0);
	
	}	
	
	/** constructor that includes  main schema name, used for K-Nearest **/
	public VennDiagramSetsMatrix(String mainSchemaName, List<FilteredSchemaInfo> schemaInfos, MatchGenerator matchScoreComputer) {	
		numSchemas = schemaInfos.size();
		this.matrix = new ArrayList<ArrayList<VennDiagramSets>>();
		
		this.minMatchScoreThreshold = 0.6;
		this.maxMatchScoreThreshold = 1.0;
		
		FilteredSchemaInfo mainSchemaInfo = null;
		for(FilteredSchemaInfo schema1 : schemaInfos) {
			if(schema1.getSchema().getName().equals(mainSchemaName)){
				mainSchemaInfo = schema1;
				schemaInfos.remove(schema1);
				break;
			}
		}
		
			ArrayList<VennDiagramSets> row = new ArrayList<VennDiagramSets>(numSchemas);
			for(FilteredSchemaInfo schema2 : schemaInfos) {
				//System.out.println("adding VD for " + schema2.getSchema().getName());
				VennDiagramSets vd = new VennDiagramSets(mainSchemaInfo, schema2, minMatchScoreThreshold,
							maxMatchScoreThreshold, matchScoreComputer);
				row.add(vd);					
			}

			matrix.add(row);

		this.minMatchScoreThreshold = greatestStdDv();
		this.setMatchScoreThresholdRange(greatestStdDv(), 1.0);
	
	}
	
	
	public int getNumSchemas() {
		return numSchemas;
	}
	
	public ArrayList<ArrayList<VennDiagramSets>> getMatrix() {
		return matrix;
	}
	
	public VennDiagramSets getMatrixEntry(int schema1Index, int schema2Index) {
		return matrix.get(schema1Index).get(schema2Index);
	}

	public double getMinMatchScoreThreshold() {
		return minMatchScoreThreshold;
	}

	public double getMaxMatchScoreThreshold() {
		return maxMatchScoreThreshold;
	}

	/** Sets the min and max thresholds used to compute all sets in the matrix */
	public void setMatchScoreThresholdRange(double minMatchScoreThreshold, double maxMatchScoreThreshold) {
		for(ArrayList<VennDiagramSets> row : matrix) {
			for(VennDiagramSets col : row) {
				col.setMatchScoreThresholdRange(minMatchScoreThreshold, maxMatchScoreThreshold);
			}
		}
	}
	
	
	
	/** Returns the tick mark value that results in the greatest Stdv **/
	public double greatestStdDv() {
		HashMap<Double, ArrayList<Double>> valsToCollectionOfJaccardsAtValue = new HashMap<Double, ArrayList<Double>>();

		ArrayList<Double> values = new ArrayList<Double>();
		for(double i=0.0; i<1.0; i=i+0.05){
			values.add(i);
			ArrayList<Double> jaccardsForI= new ArrayList<Double>();
			valsToCollectionOfJaccardsAtValue.put(i, jaccardsForI);
		}
	
		for(ArrayList<VennDiagramSets> row : matrix) {
			for(VennDiagramSets col : row) {
				if(col.getSchema1().getName() != col.getSchema2().getName()){
					//System.out.println("stdev for: " + col.getSchema1().getName() + " " + col.getSchema2().getName());
					for(double val : values){
						//get jaccards distance for this pair of schemas, for every tick mark on the slider
						double jaccardsForVal = col.getJaccards(val);
						//put the jaccards in the arraylist that corresponds to that tick mark
						//reason for doing this is that we want an arraylist with all jaccards values for a given tick mark
						ArrayList<Double> curAL = valsToCollectionOfJaccardsAtValue.get(val);
						curAL.add(jaccardsForVal);
					}
				}
			}
		}
		
		//go through arrayList associated with each tick mark and find max standard dev
		double maxStdDv = 0.0; //note that if it's 0 then something is probably wrong
		double tickMarkForMax = 0.0;
		
		for(double val: values){
			ArrayList<Double> allValsForTickMark = valsToCollectionOfJaccardsAtValue.get(val);
			//System.out.println("Calling std for: " + val);
			double currentStdDv = getStandardDev(allValsForTickMark);
			//System.out.println("Standard dev for: " + val + " is " + currentStdDv);
			//System.out.println("vals that calc that were: " + allValsForTickMark.toString());
			if(currentStdDv > maxStdDv){
				maxStdDv = currentStdDv;
				tickMarkForMax = val;
			}
		}
		
		return tickMarkForMax;
	}
	
	//takes an array of doubles and returns the standard deviation
	private double getStandardDev(ArrayList<Double> values){
		//get the average

		int numberVals = values.size();
		//System.out.println("Size: " + numberVals);
		
		double sumVals = 0;
		for(int i =0; i<values.size(); i++){
			sumVals += values.get(i);
		}
		//System.out.println("Sum of Vals: " + sumVals);
		
		double average = sumVals/numberVals;
		//System.out.println("Average of Vals: " + average);
		
		//difference of each data point from the mean, squared, all summed
		double sumOfDiffSqrd = 0.0;
		for(Double val: values){
			sumOfDiffSqrd += Math.pow((val - average), 2);
		}
		//System.out.println("Sum of squares: " + sumOfDiffSqrd);
		
		double stdDv = Math.sqrt(sumOfDiffSqrd/numberVals);
		return stdDv;
	}
	
	
	/** Sorts the rows and columns of the matrix using the given schema comparator */
	public void sortMatrixBySchemas(final Comparator<Schema> c) {
		sortMatrixBySets(new Comparator<VennDiagramSets>() {
			public int compare(VennDiagramSets arg0, VennDiagramSets arg1) {
				int result = c.compare(arg0.getSchema1(), arg1.getSchema1());
				if(result == 0)
					return c.compare(arg0.getSchema2(), arg1.getSchema2());
				return result;
			}			
		});
	}	
	
	/** Sorts the rows and columns of the matrix using the given venn diagram sets comparator */
	public void sortMatrixBySets(final Comparator<VennDiagramSets> c) {
		//Sort each row
		for(ArrayList<VennDiagramSets> row : matrix) {
			Collections.sort(row, c);
		}
		
		//Now sort the order of the rows
		Collections.sort(matrix, new Comparator<ArrayList<VennDiagramSets>>() {
			public int compare(ArrayList<VennDiagramSets> o1, ArrayList<VennDiagramSets> o2) {				
				return c.compare(o1.get(0), o2.get(0));
			}			
		});
	}	

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<ArrayList<VennDiagramSets>> iterator() {
		return matrix.iterator();
	}
}
