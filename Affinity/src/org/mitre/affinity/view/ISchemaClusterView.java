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

package org.mitre.affinity.view;

import java.util.Collection;

import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.util.SWTUtils.TextSize;
import org.mitre.affinity.view.event.SelectionChangedListener;
import org.mitre.affinity.view.event.SelectionClickedListener;

public interface ISchemaClusterView {
	//public void addAffinityListener(AffinityListener listener);	
	//public void removeAffinityListener(AffinityListener listener);
	public void addSelectionChangedListener(SelectionChangedListener listener);	
	public void removeSelectionChangedListener(SelectionChangedListener listener);	
	public void addSelectionClickedListener(SelectionClickedListener listener);	
	public void removeSelectionClickedListener(SelectionClickedListener listener);
	
	public void setTextSize(TextSize textSize);
	public TextSize getTextSize();	
	
	public void setShowClusters(boolean showClusters);
	public void setFillClusters(boolean fillClusters);
	
	public void redraw();
	
	/**
	 * Select a single schema.  Any previously selected schemas or clusters are unselected.
	 * 
	 * @param schemaID
	 */
	public void setSelectedSchema(Integer schemaID);
	
	/**
	 * Select a group of schemas.  Any previously selected schemas or clusters are unselected.
	 * 
	 * @param schemaIDs
	 */
	public void setSelectedSchemas(Collection<Integer> schemaIDs);
	
	/**
	 * Unselect all schemas and clusters
	 */
	public void unselectAllSchemasAndClusters();
	
	/**
	 *  Select a single cluster. Any previously selected schemas or clusters are unselected.
	 * 
	 * @param cluster
	 */
	public void setSelectedCluster(ClusterGroup cluster);
}
