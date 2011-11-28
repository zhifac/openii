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

package org.mitre.affinity.model.clusters.schema_clusters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mitre.affinity.algorithms.distance_functions.schemas.SchemaTermVector;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.model.schemas.ISchemaManager;

/**
 * Computes statistics for terms in a cluster.
 * 
 * @author CBONACETO
 *
 */
public class ClusterTermVector {
	
	/** All clusters */
	private ClustersContainer<Integer> clusters;
	
	/** The cluster of interest */
	private ClusterGroup<Integer> cluster;	
	
	/** Maps schemaID to that schema's term vector for each schema in the cluster */
	//private Map<Integer, SchemaTermVector> schemaTermVectors;
	
	/** The schema manager */
	private ISchemaManager schemaManager;
	
	/** Maps each term in the cluster to the term's TF-IDF statistics */
	private Map<String, ClusterTermStatistics> clusterTermStats;
	
	/** The sum of the number of occurrences of all terms in all schemas in the cluster */
	private int totalTermOccurencesInCluster;
	
	public static final Comparator<ClusterTermStatistics> compareTermsByNumContainingSchemas = new Comparator<ClusterTermStatistics>() {
		public int compare(ClusterTermStatistics arg0, ClusterTermStatistics arg1) {		
			return arg1.numSchemasContainingTerm - arg0.numSchemasContainingTerm;
		}		
	};
	
	public static final Comparator<ClusterTermStatistics> compareTermsByNumContainingSchemasInCluster = new Comparator<ClusterTermStatistics>() {
		public int compare(ClusterTermStatistics arg0, ClusterTermStatistics arg1) {						 
			return arg1.numSchemasInClusterContainingTerm - arg0.numSchemasInClusterContainingTerm;
		}		
	};
	
	public static final Comparator<ClusterTermStatistics> compareTermsByClusterTFIDFScore = new Comparator<ClusterTermStatistics>() {
		public int compare(ClusterTermStatistics arg0, ClusterTermStatistics arg1) {				
			return (int)Math.signum(arg1.clusterTFIDFScore - arg0.clusterTFIDFScore);
		}		
	};
	
	public ClusterTermVector(ClustersContainer<Integer> clusters, ClusterGroup<Integer> cluster, ISchemaManager schemaManager) {
		this.clusters = clusters;
		this.cluster = cluster;
		this.schemaManager = schemaManager;	
		recomputeStatistics();
	}
	
	public void recomputeStatistics()
	{
		this.clusterTermStats = new HashMap<String, ClusterTermStatistics>();
		this.totalTermOccurencesInCluster = 0;
		
		//Compute statistics for each term in the cluster
		for(Integer schemaID : cluster) {
			SchemaTermVector termVector = schemaManager.getSchemaTermVector(schemaID);
			// True TF/IDF --> this.totalTermOccurencesInCluster += termVector.getWordCount();
			this.totalTermOccurencesInCluster += termVector.getTermCount();
			for(String term : termVector.getTerms()) {
				ClusterTermStatistics stats = clusterTermStats.get(term);
				if(stats == null)
				{
					stats = new ClusterTermStatistics(term, cluster.getNumClusterObjects());
					clusterTermStats.put(term, stats);
				}
				stats.numSchemasInClusterContainingTerm++;
				// True TF/IDF --> stats.numTermOccurencesInCluster += termVector.getWordCount(term);
				stats.numTermOccurencesInCluster++;
			}
		}
		
		//Computer cluster-wide TF-IDF score for each term		
		Collection<Integer> schemas = clusters.getObjectIDs();
		int numSchemas = schemas.size();
		
		for(Map.Entry<String, ClusterTermStatistics> termClusterStatsEntry : clusterTermStats.entrySet()) {			
			ClusterTermStatistics termClusterStats = termClusterStatsEntry.getValue();
			//System.out.println("term: " + termClusterStats.getTerm() + ", numTermOccurencesInCluster: " + termClusterStats.numTermOccurencesInCluster + ", totalTermOccurences: " + totalTermOccurences);
			
			//This is the old formula that computed the TF-IDF score relative to the cluster
			/*termClusterStats.clusterTFIDFScore = ((float)termClusterStats.numTermOccurencesInCluster/totalTermOccurencesInCluster) * 
				Math.log((double)cluster.getNumSchemas()/termClusterStats.numSchemasContainingTerm);*/
			
			//This is the new formula that computes the TF-IDF score relative to all schemas in all clusters
			termClusterStats.numSchemasContainingTerm = schemaManager.getNumSchemasContainingTerm(termClusterStats.term, schemas);
			double tf = ((double)termClusterStats.numSchemasInClusterContainingTerm)/(termClusterStats.numSchemasInCluster);
			double idf = ((double)numSchemas)/(termClusterStats.numSchemasContainingTerm);
			termClusterStats.clusterTFIDFScore = tf * Math.log10(idf);
		}	
	}	
	
	/**
	 * @param term
	 * @return total number of schemas containing the term
	 */
	public int getNumSchemasContainingTerm(String term) {
		ClusterTermStatistics termClusterStats = this.clusterTermStats.get(term);
		if(termClusterStats != null) 
			return termClusterStats.numSchemasContainingTerm;
		return 0;
	}
	
	/**
	 * @param term
	 * @return the number of schemas in the cluster containing the term
	 */
	public int getNumSchemasInClusterContainingTerm(String term) {
		ClusterTermStatistics termClusterStats = this.clusterTermStats.get(term);
		if(termClusterStats != null) 
			return termClusterStats.numSchemasInClusterContainingTerm;
		return 0;
	}
	
	/**
	 * @return the total number of schemas in the cluster
	 */
	public int getNumSchemas() {
		return cluster.getNumClusterObjects();
	}	
	
	public double getTermClusterTFIDFScore(String term) {
		ClusterTermStatistics termClusterStats = this.clusterTermStats.get(term);
		if(termClusterStats != null) 
			return termClusterStats.clusterTFIDFScore;
		return 0D;
	}
	
	public double getTermSchemaTFIDFScore(String term, Integer schemaID) {
		SchemaTermVector termVector = schemaManager.getSchemaTermVector(schemaID);
		ClusterTermStatistics termClusterStats = this.clusterTermStats.get(term);
		//TF-IDF = term frequency * inverse document frequency
		if(termVector != null && termClusterStats != null) 
			return termVector.getTermFrequency(term) * 
				Math.log((double)cluster.getNumClusterObjects()/termClusterStats.numSchemasInClusterContainingTerm);
		return 0D;
	}
	
	public int getTermNumOccurencesInSchema(String term, Integer schemaID) {
		SchemaTermVector termVector = schemaManager.getSchemaTermVector(schemaID);
		if(termVector != null) 
			return termVector.getWordCount(term);
		return 0;
	}
	
	public float getTermFrequencyInSchema(String term, Integer schemaID) {
		SchemaTermVector termVector = schemaManager.getSchemaTermVector(schemaID);
		if(termVector != null) 
			return termVector.getTermFrequency(term);
		return 0;
	}
	
	public int getTermNumOccurencesInCluster(String term) {
		ClusterTermStatistics termClusterStats = this.clusterTermStats.get(term);
		if(termClusterStats != null) 
			return termClusterStats.numTermOccurencesInCluster;
		return 0;
	}
	
	public float getTermFrequencyInCluster(String term) {
		ClusterTermStatistics termClusterStats = this.clusterTermStats.get(term);
		if(termClusterStats != null) 
			return (float)termClusterStats.numTermOccurencesInCluster/totalTermOccurencesInCluster;
		return 0F;
	}	
	
	public List<ClusterTermStatistics> getTermsSortedByNumContainingSchemas() {
		List<ClusterTermStatistics> termsSortedByNumContainingSchemas = new ArrayList<ClusterTermStatistics>();
		
		for(ClusterTermStatistics termClusterStats : clusterTermStats.values()) {
			termsSortedByNumContainingSchemas.add(termClusterStats);
		}		
		Collections.sort(termsSortedByNumContainingSchemas, compareTermsByNumContainingSchemas);
		
		return termsSortedByNumContainingSchemas;
	}
	
	public List<ClusterTermStatistics> getTermsSortedByNumContainingSchemasInCluster() {
		List<ClusterTermStatistics> termsSortedByNumContainingSchemas = new ArrayList<ClusterTermStatistics>();
		
		for(ClusterTermStatistics termClusterStats : clusterTermStats.values()) {
			termsSortedByNumContainingSchemas.add(termClusterStats);
		}		
		Collections.sort(termsSortedByNumContainingSchemas, compareTermsByNumContainingSchemasInCluster);
		
		return termsSortedByNumContainingSchemas;
	}
	
	public List<ClusterTermStatistics> getTermsSortedByClusterTFIDFScore() {
		List<ClusterTermStatistics> termsSortedByClusterTFIDFScore = new ArrayList<ClusterTermStatistics>();
		
		for(ClusterTermStatistics termClusterStats : clusterTermStats.values()) {
			termsSortedByClusterTFIDFScore.add(termClusterStats);
		}		
		Collections.sort(termsSortedByClusterTFIDFScore, compareTermsByClusterTFIDFScore);
		
		return termsSortedByClusterTFIDFScore;
	}
	
	public static class ClusterTermStatistics {
		private final String term; //The term
		
		private final int numSchemasInCluster;
		
		private int numSchemasContainingTerm; //Total number of schemas across all clusters containing the term

		private int numSchemasInClusterContainingTerm; //Total number of schemas in the cluster containing the term

		private int numTermOccurencesInCluster; //Total number of times the term appears in the cluster		

		private double clusterTFIDFScore; //The TF-IDF score for the term across all schemas in the cluster

		public ClusterTermStatistics(String term, int numSchemasInCluster) {
			this.term = term;
			this.numSchemasInCluster = numSchemasInCluster;
			this.numSchemasContainingTerm = 0;
			this.numSchemasInClusterContainingTerm = 0;
			this.numTermOccurencesInCluster = 0;
			this.clusterTFIDFScore = 0;
		}

		public String getTerm() {
			return term;
		}

		public int getNumSchemasContainingTerm() {
			return numSchemasContainingTerm;
		}		
		
		public int getNumSchemasInClusterContainingTerm() {
			return numSchemasInClusterContainingTerm;
		}

		public int getNumTermOccurencesInCluster() {
			return numTermOccurencesInCluster;
		}

		public double getClusterTFIDFScore() {
			return clusterTFIDFScore;
		}
	}
}
