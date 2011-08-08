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

import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 * An extension of a FilteredGraph that simply caches the list of filtered elements until the 
 * user explicitly requests that it be recreated by calling filteredElementsChanged().  
 * 
 * This implementation is typically used with the VennDiagramMatrix class to enable only one 
 * list of elements to be stored for each schema to reduce memory usage.
 * 
 * @author CBONACETO
 *
 */
public class CachedFilteredSchemaInfo extends FilteredSchemaInfo {
	private static final long serialVersionUID = 1L;
	
	protected ArrayList<SchemaElement> cachedFilteredElements = null;

	public CachedFilteredSchemaInfo(HierarchicalSchemaInfo graph) {
		super(graph);
	}
	
	public void filteredElementsChanged() {
		this.cachedFilteredElements = null;
	}
	
	/** Returns the list of all filtered elements in this graph.
	 * This implementation always returns the cached elements, and only
	 * recreates the list if it is null */
	public ArrayList<SchemaElement> getFilteredElements() {
		if(this.cachedFilteredElements == null)
			this.cachedFilteredElements = super.getFilteredElements();
		return this.cachedFilteredElements;
	}

}
