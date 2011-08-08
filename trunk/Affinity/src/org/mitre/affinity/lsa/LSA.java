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

package org.mitre.affinity.lsa;

import java.util.ArrayList;

import org.mitre.affinity.clusters.distanceFunctions.SchemaTermVector;
import org.mitre.affinity.jama.Matrix;
import org.mitre.affinity.jama.SingularValueDecomposition;
import org.mitre.affinity.model.AffinitySchemaManager;

/**
 * Implementation of the Latentent Semantic Analysis method.
 * @author MDMORSE
 * Added March 4, 2009
 */
public class LSA{
	
	/** M is the matrix representation of data set */
	private Matrix M;
	/** The Singular Value Decomposition */
	private SingularValueDecomposition svd;
	
	/**
	 * Constructor, not too interesting.
	 */
	public LSA(){
		
	}
	
	/**
	 * Method that takes as input the term vectors for the dataset and produces
	 * the SVD.
	 * @param tvs - arraylist of term vectors.
	 */
	public void computeLSA(ArrayList<SchemaTermVector> tvs){
		//get list of terms
		ArrayList<String> terms = SchemaTermVector.getCommonWords(tvs);
		
		//convert term vectors into matrix representation needed by jama.
		double[][] mArray= new double[tvs.size()][terms.size()];
		int i=0, j=0;
		for(SchemaTermVector tv: tvs)
		{
			for(String term : terms)
				mArray[i][j++] = tv.getWordCount(term);
			i++; j=0;
		}
		
		//create the SVD.
		M= new Matrix(mArray);
		M=M.transpose();
		svd = M.svd();
	}
	
	/**
	 * Get the 1st principle component.
	 * @return double[]
	 */
	public double[] getV1(){
		Matrix m = svd.getV();
		//Matrix q = svd.getU();
		//System.out.println(q.getRowDimension()+", "+q.getColumnDimension());
		int size = m.getColumnDimension();
		double[] v1 = new double[size];
		for(int j=0; j < size; j++){
			v1[j]=m.get(j,0);
		}
		return v1;
	}
	
	/**
	 * Get the 2nd principle component.
	 * @return double[]
	 */
	public double[] getV2(){
		Matrix m = svd.getV();
		int size = m.getColumnDimension();
		double[] v2 = new double[size];
		for(int j=0; j < size; j++){
			v2[j]=m.get(j,1);
		}
		return v2;
	}
	
	//helper to print out the SVD.
	private void prettyPrint(ArrayList<Integer> schemaIDS, AffinitySchemaManager schemaManager){
		//for(Integer schemaid: schemaIDS){
			//   System.out.print(AffinitySchemaManager.getSchema(schemaid).getName()+"\t ");
		   //}
		   //System.out.print("\n");
		   double[] v1 = getV1();
		   double[] v2 = getV2();
		   for(int j=0; j < schemaIDS.size();j++){
			   System.out.print(schemaManager.getSchema(schemaIDS.get(j)).getName());
			   System.out.print("\t"+v1[j]+"\t"+v2[j]+"\n");
		   }
		   //System.out.println(getV1());
		   //System.out.println(getV2());
	}
	
	/**
	 * TestZoomPan the LSA
	 * @param args
	 */
	public static void main(String[] args){
		   LSA jdf = new LSA();
		   ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		   schemaIDs.add(2857);
		   schemaIDs.add(1001);
		   schemaIDs.add(2016);
		   schemaIDs.add(3087);
		   schemaIDs.add(1412);
		   schemaIDs.add(2329);
		   schemaIDs.add(2061);
		   schemaIDs.add(2641);
		   schemaIDs.add(1126);
		   schemaIDs.add(2106);
		   schemaIDs.add(3188);
		   schemaIDs.add(3426);
		   schemaIDs.add(3321);
		   schemaIDs.add(1745);
		   //schemaIDs.add(2700);
		   schemaIDs.add(1925);
		   schemaIDs.add(2763);
		   schemaIDs.add(1510);
		   schemaIDs.add(2971);
		   schemaIDs.add(2159);
		   
		   AffinitySchemaManager schemaManager = new AffinitySchemaManager();
		   ArrayList<SchemaTermVector> wordVectors = new ArrayList<SchemaTermVector>();		
			for(int j =0; j < schemaIDs.size(); j++){
				wordVectors.add(new SchemaTermVector(schemaManager.getSchemaInfo(schemaIDs.get(j))));
			}
		   
		   //ArrayList<Schema> schemas = AffinitySchemaManager.getSchemas();
		   //for(Schema s: schemas){
			//   System.out.println(s.getName()+" "+s.getId());
		   //}
		   jdf.computeLSA(wordVectors);
		   jdf.prettyPrint(schemaIDs, schemaManager);
		   //System.out.println(dg.toString());
	   }
}