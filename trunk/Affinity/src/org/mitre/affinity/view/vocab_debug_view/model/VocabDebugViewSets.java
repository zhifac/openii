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

package org.mitre.affinity.view.vocab_debug_view.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets;
import org.mitre.harmony.matchers.MatchGenerator;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;


//public class VocabDebugViewSets implements Iterable<ArrayList<VocabDebugViewSets>> {
public class VocabDebugViewSets {
	
	/** All schemas to use in the set */
	private List<FilteredSchemaInfo> allSchemas;
	private ArrayList<VennDiagramSets> allSets;
	
	private FilteredSchemaInfo schemaToTempUseAsCore;
	
	/** The minimum score threshold used to determine matching elements */
	private double minMatchScoreThreshold;
	
	/** The maximum score threshold used to determine matching elements */
	private double maxMatchScoreThreshold;
	

	
	
	
	public VocabDebugViewSets(List<FilteredSchemaInfo> schemaInfos,	double minMatchScore, 
			double maxMatchScore, MatchGenerator matchScoreComputer) {	

		int numSchemas = schemaInfos.size();
		allSchemas = schemaInfos;
		allSets = new ArrayList<VennDiagramSets>(numSchemas);
		minMatchScoreThreshold = minMatchScore;
		maxMatchScoreThreshold = maxMatchScore;
		
		schemaToTempUseAsCore = schemaInfos.get(0);
			
		
		for(int i = 1; i < numSchemas; i++){
			FilteredSchemaInfo schema2 = schemaInfos.get(i); 
			//matching each schema back to the temp core vocab
			//match schema i to schema 0
			allSets.add(new VennDiagramSets(schemaToTempUseAsCore, schema2, minMatchScoreThreshold, maxMatchScoreThreshold, matchScoreComputer));
		}
	}

	public int getNumSchemas() {
		return allSchemas.size();
	}
	
	public ArrayList<VennDiagramSets> getAllSets() {
		return allSets;
	}
	
	public VennDiagramSets getListEntry(int index) {
		return (VennDiagramSets)allSets.get(index);
	}

	public double getMinMatchScoreThreshold() {
		return minMatchScoreThreshold;
	}

	public double getMaxMatchScoreThreshold() {
		return maxMatchScoreThreshold;
	}

	
	public Iterator<VennDiagramSets> iterator() {
		return allSets.iterator();
	}
}
