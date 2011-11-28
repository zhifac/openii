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

package org.mitre.affinity.view.venn_diagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.mitre.affinity.model.schemas.ISchemaManager;
import org.mitre.affinity.view.venn_diagram.model.CachedFilteredSchemaInfo;
import org.mitre.harmony.matchers.MatchGenerator;
import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.mergers.VoteMerger;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

public class VennDiagramUtils {
	private VennDiagramUtils() {}
	
	public static final Comparator<SchemaElement> LexicalSchemaElementComparator = new Comparator<SchemaElement>() {
		public int compare(SchemaElement element1, SchemaElement element2) { 
			return element1.getName().compareToIgnoreCase(element2.getName()); 
		}			
	};		
	
	/** Creates a default match score computer, which uses all of Harmony's matchers */
	public static MatchGenerator createDefaultMatchScoreComputer()
		{ return new MatchGenerator(MatcherManager.getDefaultMatchers(), new VoteMerger()); }	
	
	/** Creates a cached filtered schema info from the given schema that contains no elements whose name is null or an empty string */
	public static CachedFilteredSchemaInfo createCachedFilteredSchemaInfo(Integer schemaID, ISchemaManager schemaManager) {
		CachedFilteredSchemaInfo schemaInfo = new CachedFilteredSchemaInfo(schemaManager.getSchemaInfo(schemaID));		
		deleteUnnamedElements(schemaInfo);
		return schemaInfo;
	}
	
	/** Creates a filtered schema info from the given schema that contains no elements whose name is null or an empty string */
	public static FilteredSchemaInfo createFilteredSchemaInfo(Integer schemaID, ISchemaManager schemaManager) {
		FilteredSchemaInfo schemaInfo = new FilteredSchemaInfo(schemaManager.getSchemaInfo(schemaID));		
		deleteUnnamedElements(schemaInfo);
		return schemaInfo;
	}
	
	public static void deleteUnnamedElements(SchemaInfo schemaInfo) {
		for(SchemaElement element : schemaInfo.getElements(null)) {
			if(element.getName() == null || element.getName().isEmpty())
				schemaInfo.deleteElement(element.getId());
		}
	}
	
	/** Sorts the schema elements in the given schema info object alphabetically and returns them */
	public static ArrayList<SchemaElement> sortFilteredElements(FilteredSchemaInfo schemaInfo) {
		ArrayList<SchemaElement> elements = schemaInfo.getFilteredElements();	
		Collections.sort(elements, LexicalSchemaElementComparator);
		return elements;
	}
	
	/** Sorts the given schema elements alphabetically */
	public static void sortElements(List<SchemaElement> schemaElements) {
		Collections.sort(schemaElements, LexicalSchemaElementComparator);
	}
}
