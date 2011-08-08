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

package org.mitre.affinity.clusters.distanceFunctions;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Hashtable;

import org.mitre.affinity.clusters.DistanceGrid;
import org.mitre.affinity.clusters.distanceFunctions.SchemaTermVector;
import org.mitre.affinity.model.ISchemaManager;
import org.mitre.affinity.model.AffinitySchemaManager;

/**
 * Calculates the cosine of the angle between the term vectors.
 * Returns 1-actual cosine score, so as to produce a distance and
 * not a similarity score.
 * @author MDMORSE
 * created Feb 27, 2009
 */
public class CosineDistanceFunction implements DistanceFunction{
	
	/** 
	 * This is a helper that evaluates the Integer weight of a word (key) in
	 * a document (WordVector).  There are 2 choices, 1st, use the actual
	 * Integer weight, 1, 2, 3, etc. in the cosine computation.  
	 * 2nd, use only a binary 1 or 0.  
	 * 
	 * Set WEIGHT to 0 for binary 0 or 1, set to 1 for integer weight  
	 */
	private static int WEIGHT = 1;
	
	/**
	 * Constructor.  Not too interesting
	 */
	public CosineDistanceFunction(){
	}
	
	/**
	 * Return the name of the distance function.
	 */
	public String getName(){
		return "Cosine";
	}
	
	/** Generates a distance grid for the given schemas */
	public DistanceGrid generateDistanceGrid(ArrayList<Integer> schemaIDs, ISchemaManager schemaManager){
		DistanceGrid dm = new DistanceGrid();
		Vector<SchemaTermVector> wordVectors = new Vector<SchemaTermVector>();		
		for(int j =0; j < schemaIDs.size(); j++){
			wordVectors.add(new SchemaTermVector(schemaManager.getSchemaInfo(schemaIDs.get(j))));
		}
		for(int j=0; j < schemaIDs.size(); j++){
			for(int k = j; k < schemaIDs.size(); k++){
				//if(schemaIDs.get(j) == 2329 && schemaIDs.get(k) == 3426)
				//	System.out.println("hi");
				dm.set(schemaIDs.get(j), schemaIDs.get(k), 1-cosineDoc(wordVectors.get(j),wordVectors.get(k)));
			}
		}
		return dm;
	}
	
	/**
	 * Evaluates the cosine score of two term vectors.
	 * @param R1 - term vector 1
	 * @param R2 - term vector 2
	 * @return the cosine score.
	 */
	   public static double cosineDoc(SchemaTermVector R1, SchemaTermVector R2){
		      Hashtable<String, Integer> totals = new Hashtable<String,Integer>();
		      //what we have are 2 hashtables with the document numbers.
		      //we will create a combined vector of all hashed values.
		      for(String term : R1.getTerms())
		      {
		        totals.put(term, weight(term,R1));
		      }
		      for(String term : R2.getTerms())
		      {
		        if(!totals.containsKey(term)){
		            totals.put(term, weight(term,R2));
		         } else{
		         	totals.remove(term);
		         	totals.put(term, combine(weight(term,R1),weight(term,R2)));
		         }
		      }

		      Vector<String> words = new Vector<String>();
		      for (java.util.Enumeration<String> g = totals.keys(); g.hasMoreElements() ;) {
		        String key = (String) g.nextElement();
		        words.add(key);
		      }
		      //cos theta = v1 dot v2 / |v1||v2|
		      Vector<Integer> v1 = new Vector<Integer>();
		      Vector<Integer> v2 = new Vector<Integer>();
		      int j;
		      for(j = 0; j < words.size(); j++){
		        if(R1.containsTerm(words.elementAt(j))){
		        	v1.add(weight(words.elementAt(j),R1));
		        }
		        else{
		        	v1.add(new Integer(0));
		        }
		        if(R2.containsTerm(words.elementAt(j))){
		        	v2.add(weight(words.elementAt(j),R2));
		        }
		        else{
		        	v2.add(new Integer(0));
		        }
		      }
		      double v1dotv2=0;
		      double v1size=0;
		      double v2size=0;
		      for(j=0; j < v1.size(); j++){
		        v1dotv2+=v1.elementAt(j).doubleValue()*v2.elementAt(j).doubleValue();
		        v1size+=v1.elementAt(j).doubleValue()*v1.elementAt(j).doubleValue();
		        v2size+=v2.elementAt(j).doubleValue()*v2.elementAt(j).doubleValue();
		      }
		      v1size = java.lang.Math.sqrt(v1size);
		      v2size = java.lang.Math.sqrt(v2size);
		      return v1dotv2/v1size/v2size;

		   }
	   
	   /**
	    * This is a helper that evaluates the Integer weight of a word (key) in
	    * a document (WordVector).  There are 2 choices, 1st, to return the actual
	    * Integer weight, 1, 2, 3, etc.  2nd, to return a binary 1 or 0.  
	    * 
	    * Choice is controlled by WEIGHT class variable at top of file.
	    * 
	    * @param term - word for whose weight we are interested
	    * @param R - the WordVector
	    * @return an Integer weight
	    */
	   private static Integer weight(String term, SchemaTermVector R){
		   /* word weight*/
		   if(WEIGHT !=0){
			   return R.getWordCount(term);
		   }
		   else{   /* the following 3 lines for binary 0 or 1 */
			   if(R.containsTerm(term)){
				   return new Integer(1);
			   } else{ return new Integer(0); }
		   }
	   }
		/**
		 * either do a logical or on 2 binary scores or add integer scores together.
		 * Controlled by WEIGHT class variable.   
		 * @param score1
		 * @param score2
		 * @return
		 */
	   private static int combine(int score1, int score2){
		   if(WEIGHT != 0){
			   return score1+score2;
		   } else{ return Math.min(score1, score2); }
	   }
	   
	 //helper, inefficient.
	   private static void prettyPrint(ArrayList<Integer> schemaIDs,DistanceGrid dg, AffinitySchemaManager schemaManager){
		   for(Integer schemaid: schemaIDs){
			   System.out.print(schemaManager.getSchema(schemaid).getName()+"\t ");
		   }
		   System.out.print("\n");
		   for(Integer i:schemaIDs){
			   for(Integer j: schemaIDs){
				   System.out.print(dg.get(i,j)+"\t");
			   }
			   System.out.print("\n");
		   }
	   }
	   
	   /**
	    * test for Cosine method.
	    * @param args
	    */
	   public static void main(String[] args){
		   CosineDistanceFunction jdf = new CosineDistanceFunction();
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
		   schemaIDs.add(2700);
		   schemaIDs.add(1925);
		   schemaIDs.add(2763);
		   schemaIDs.add(1510);
		   schemaIDs.add(2971);
		   schemaIDs.add(2159);
		   //ArrayList<Schema> schemas = AffinitySchemaManager.getSchemas();
		   //for(Schema s: schemas){
			//   System.out.println(s.getName()+" "+s.getId());
		   //}
		   AffinitySchemaManager schemaManager = new AffinitySchemaManager();
		   DistanceGrid dg = jdf.generateDistanceGrid(schemaIDs, schemaManager);
		   prettyPrint(schemaIDs, dg, schemaManager);
		   //System.out.println(dg.toString());
	   }
}