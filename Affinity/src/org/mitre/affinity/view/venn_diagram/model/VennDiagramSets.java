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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mitre.harmony.matchers.MatchGenerator;
import org.mitre.harmony.matchers.MatchScores;
import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.mergers.VoteMerger;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;


public class VennDiagramSets
{	
	// Stores references to the schemas being compared
	private SchemaInfo schema1;
	private SchemaInfo schema2;
	
	// Stores the minimum and maximum score thresholds
	private double minThreshold = 0.5d;
	private double maxThreshold = 1.d;
	
	/** Contains all elements in schema 1 */
	private ArrayList<SchemaElement> schema1AllElements; 
	
	/** Contains all elements in schema 2 */
	private ArrayList<SchemaElement> schema2AllElements;
	
	/** Stores the match scores */
	private MatchScores matchScores;	
	
	/** Schema elements unique to schema 1 given the current matchScoreThreshold 
	 * (i.e., do not match any elements in schema 2 given the matchScoreThreshold)*/
	private Set<SchemaElement> schema1UniqueElements;
	
	/** Schema elements unique to schema 2 given the current matchScoreThreshold
	 * (i.e., do not match any elements in schema 1 given the matchScoreThreshold) */
	private Set<SchemaElement> schema2UniqueElements;
	
	/** Contains schema elements that match from schemas 1 and 2 given the current matchScoreThreshold */
	private List<MatchedSchemaElements> intersectElements;
	
	private int numIntersectElements;
	
	//1 if element level matching, 2 if entity level matching
	private int entityOrElement;

	/** Constructs the Venn Diagram sets */
	public VennDiagramSets(FilteredSchemaInfo schema1, FilteredSchemaInfo schema2, double minThreshold, double maxThreshold, MatchGenerator matchScoreComputer)
	{
		// Store schema and threshold information
		this.schema1 = schema1;
		this.schema2 = schema2;
		this.minThreshold = minThreshold;
		this.maxThreshold = maxThreshold;

		// Store element information
		this.schema1AllElements = schema1.getFilteredElements();
		this.schema2AllElements = schema2.getFilteredElements();

		// Generate the match scores
		this.matchScores = new MatchGenerator(MatcherManager.getDefaultMatchers(), new VoteMerger()).getScores(schema1, schema2);

		// Recomputes the sets based on the generated match scores
		recomputeSets();
	}
	
	public SchemaInfo getSchemaInfo1(){
		return schema1;
	}
	
	public SchemaInfo getSchemaInfo2(){
		return schema2;
	}
	
	public int getEntityOrElementVal(){
		return entityOrElement;
	}
	

	/** Computes set memberships for schema1UniqueElements, schema2UniqueElements, and
	 * intersectElements given the current matchScoreThreshold  and the scoreGrid */
	private void recomputeSets() {
		schema1UniqueElements = new LinkedHashSet<SchemaElement>();
		schema2UniqueElements = new LinkedHashSet<SchemaElement>();
		intersectElements = new LinkedList<MatchedSchemaElements>();
		numIntersectElements = 0;
		
		//Initialize schema2UniqueElements with all elements in schema2.
		//They will be removed as they are matched against elements in schema1.
		for(SchemaElement element : schema2AllElements) {
			schema2UniqueElements.add(element);
		}
		
		for(SchemaElement element1 : schema1AllElements) {
			//MatchedSchemaElements matchedElements = null;
			boolean matchFound = false;
			for(SchemaElement element2 : schema2AllElements) {
				//Determine if the current element in schema 1 matches any elements in schema 2
				Double score = matchScores.getScore(element1, element2);
				//if(score == null) score = 0.d;
				//System.out.println("score: " + score);
				if(score != null && score >= minThreshold && score <= maxThreshold) {
					//A matching element was found
					//System.out.println("Matching elements found: " + element1 + ", " + element2 + ", " + score);
					//if(matchedElements == null) matchedElements = new MatchedSchemaElements();
					matchFound = true;
					MatchedSchemaElements matchedElements = new MatchedSchemaElements();
					matchedElements.addMatchedElement(schema1.getSchema().getId(), element1);
					//System.out.println("schema1 ID: " + schema1.getId() + " element 1: " + element1.getName());
					
					matchedElements.addMatchedElement(schema2.getSchema().getId(), element2);
					matchedElements.matchScore = score;
					intersectElements.add(matchedElements);
					
					//Remove the matched element from schema2UniqueElements
					schema2UniqueElements.remove(element2);
				}
			}
			
			if(matchFound) 
				numIntersectElements++;
			else
				schema1UniqueElements.add(element1);			
			/*if(matchedElements != null) 
				intersectElements.add(matchedElements);
			else
				schema1UniqueElements.add(element1);*/
		}		
		
		//VennDiagramUtils.sortElements(intersectElements);
	}

	//public HashMap<Double, Double> getJaccards(double minVal){
	public double getJaccards(double minVal){
		//0 = 0.0, 1= 0.1, 2 = 0.2, ..., 10 = 1.0
		//HashMap<Double, Double> minValtoJaccards = new HashMap<Double, Double>();
		
		//System.out.println(this.schema1.getName() + " to " + this.schema2.getName());
	//	if(this.schema1.getName()!= this.schema2.getName()){
			//double i = 0;
		//	while(i<1.0){
				//this.minMatchScoreThreshold = i;
				this.minThreshold = minVal;
				//System.out.println("threshold: " + this.minMatchScoreThreshold + " " + this.maxMatchScoreThreshold);
				this.recomputeSets();
				double intersect = this.getNumIntersectElements();
				double unique1 = this.schema1UniqueElements.size();
				double unique2 = this.schema2UniqueElements.size();
				//System.out.println("size of intersection: " + intersect);
				//System.out.println("unique in schema 1: " + unique1);
				//System.out.println("unique in schema 2: " + unique2);
				double jaccards = intersect/(unique1+unique2+intersect);
				//System.out.println("intersection/union: " + jaccards);
				//minValtoJaccards.put(i, jaccards);
				//i = i + 0.05;
			//}
				return jaccards;
	//	}

		//return minValtoJaccards;

	}
	
	public double getMinMatchScoreThreshold() {
		return minThreshold;
	}

	public double getMaxMatchScoreThreshold() {
		return maxThreshold;
	}
	

	
	public void setMatchScoreThresholdRange(double minMatchScoreThreshold, double maxMatchScoreThreshold) {
		if(this.minThreshold != minMatchScoreThreshold || 
				this.maxThreshold != maxMatchScoreThreshold) {
			this.minThreshold = minMatchScoreThreshold;
			this.maxThreshold = maxMatchScoreThreshold;
			recomputeSets();
		}
	}

	public Schema getSchema1() {
		return schema1.getSchema();
	}

	public Schema getSchema2() {
		return schema2.getSchema();
	}	
	
	public ArrayList<SchemaElement> getSchema1AllElements() {
		return schema1AllElements;
	}

	public ArrayList<SchemaElement> getSchema2AllElements() {
		return schema2AllElements;
	}

	public Set<SchemaElement> getSchema1UniqueElements() {
		return schema1UniqueElements;
	}
	
	public Set<SchemaElement> getSchema2UniqueElements() {
		return schema2UniqueElements;
	}

	public List<MatchedSchemaElements> getIntersectElements() {
		return intersectElements;
	}
	
	public int getNumIntersectElements() {
		return numIntersectElements;
		//return intersectElements.size();
	}

	public static class MatchedSchemaElements implements Iterable<Map.Entry<Integer, SchemaElement>> { //implements Iterable<SchemaMemberElement> {
		//private List<SchemaMemberElement> matchedElements;
		private Map<Integer, SchemaElement> matchedElements;
		private double matchScore;
		
		public MatchedSchemaElements() {
			//this.matchedElements = new LinkedList<SchemaMemberElement>();
			this.matchedElements = new HashMap<Integer, SchemaElement>();
		}
		
		public void addMatchedElement(Integer schemaID, SchemaElement schemaElement) {
			//matchedElements.add(new SchemaMemberElement(schemaID, schemaElement));
			matchedElements.put(schemaID, schemaElement);
		}		
		
		public void addMatchedElement(SchemaMemberElement matchedElement) {
			//matchedElements.add(matchedElement);
			matchedElements.put(matchedElement.schemaID, matchedElement.schemaElement);
		}

		public SchemaElement getMatchedElement(Integer schemaID) {
			return matchedElements.get(schemaID);
		}
		
		/*public List<SchemaMemberElement> getMatchedElements() {
			return matchedElements;
		}*/
		
		public int getNumMatchedElements() {
			return matchedElements.size();
		}
		
		/*@Override
		public Iterator<SchemaMemberElement> iterator() {
			return matchedElements.iterator();
		}*/
		
		public Iterator<Map.Entry<Integer, SchemaElement>> iterator() {
			return matchedElements.entrySet().iterator();
		}

		public double getMatchScore() {
			return matchScore;
		}

		public void setMatchScore(double matchScore) {
			this.matchScore = matchScore;
		}

		@Override
		public String toString() {
			return new String("Matched elements: " + matchedElements);
		}
	}
	
	public static class SchemaMemberElement {
		public final Integer schemaID;
		public final SchemaElement schemaElement;
	
		public SchemaMemberElement(Integer schemaID, SchemaElement schemaElement) {
			this.schemaID = schemaID;
			this.schemaElement = schemaElement;
		}
		
		public Integer getSchemaID() {
			return schemaID;
		}
		
		public SchemaElement getSchemaElement() {
			return schemaElement;
		}

		@Override
		public String toString() {
			return new String("(Schema: " + schemaID + ", Element: " + schemaElement + ")");
		}
	}
}





