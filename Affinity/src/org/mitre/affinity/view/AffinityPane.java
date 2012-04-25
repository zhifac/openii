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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.mitre.affinity.AffinityConstants;
import org.mitre.affinity.model.IClusterObjectManager;
import org.mitre.affinity.model.Position;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.view.craigrogram.Cluster2DViewPane.SliderType;
import org.mitre.affinity.view.craigrogram.OvalClusterObjectGUI;
import org.mitre.affinity.view.craigrogram.Cluster2DView;
import org.mitre.affinity.view.craigrogram.Cluster2DViewPane;
import org.mitre.affinity.view.dendrogram.DendrogramCanvas;
import org.mitre.affinity.view.dendrogram.DendrogramClusterObjectGUI;
import org.mitre.affinity.view.event.SelectionChangedEvent;

/**
 * A pane with a linked "Craigrogram" and Dendrogram and a cluster distance slider.
 * 
 * @author CBONACETO
 * 
 * @param <K>
 * @param <V>
 */
public class AffinityPane<K extends Comparable<K>, V> extends AbstractAffinityPane<K, V> {	
	
	/** Pane with the "Craigrogram" (left cluster view) */	
	protected Cluster2DViewPane<K, V> craigrogram;
	
	/** Pane with the Dendrogram (right cluster view) */	
	protected DendrogramCanvas<K, V> dendrogram;		
	
	/**
	 * Create a blank AffinityPane.
	 * 
	 * @param parent
	 * @param style
	 * @param searchHint
	 * @param showingSchemas
	 */
	public AffinityPane(final Composite parent, int style, IClusterObjectManager<K, V> clusterObjectManager, String searchHint) {
		super(parent, style, clusterObjectManager, searchHint);
	}		
	
	/**
	 * Creates an AffinityPane using the given cluster objects, clusters, and position grid.
	 *
	 * @param parent
	 * @param style
	 * @param clusterObjectManager
	 * @param objectIDs
	 * @param clusterObjects
	 * @param clusters
	 * @param pg
	 * @param searchHint
	 */
	public AffinityPane(final Composite parent, int style, IClusterObjectManager<K, V> clusterObjectManager,
			ArrayList<K> objectIDs, List<V> clusterObjects, ClustersContainer<K> clusters, 
			PositionGrid<K> pg, String searchHint) {
		super(parent, style, clusterObjectManager, objectIDs, clusterObjects, clusters, pg, searchHint);
	}		
	
	@Override
	protected void setClusterObjectsAndClustersForClusterViews(
			ArrayList<K> objectIDs, Collection<V> clusterObjects,
			ClustersContainer<K> clusters, PositionGrid<K> pg) {
		final List<IClusterObjectGUI<K, V>> craigrogramClusterObjectGUIs = new ArrayList<IClusterObjectGUI<K, V>>();
		final List<DendrogramClusterObjectGUI<K, V>> dendrogramClusterObjectGUIs = new ArrayList<DendrogramClusterObjectGUI<K, V>>();
		clusterObjectsMap = new HashMap<K, V>();
		if(clusterObjects != null && !clusterObjects.isEmpty() && objectIDs != null && !objectIDs.isEmpty()) {
			int i = 0;
			for(V clusterObject : clusterObjects) {
				K objectID = objectIDs.get(i);
				String objectName = clusterObjectManager.getClusterObjectName(objectID);
				clusterObjectsMap.put(objectID, clusterObject);
				Position pos = pg.getPosition(objectID);
				if(pos == null) {
					System.err.println("Error: position for cluster object " + objectName + " is null");
				}
				OvalClusterObjectGUI<K, V> clusterObjectGUI = new OvalClusterObjectGUI<K, V>(getShell(), objectID, 
						AffinityConstants.CLUSTER_OBJECT_DIAMETER, AffinityConstants.CLUSTER_OBJECT_DIAMETER);			
				clusterObjectGUI.setLocation((int)pos.getDimensionValue(0), (int)pos.getDimensionValue(1));
				clusterObjectGUI.setLabel(objectName);
				clusterObjectGUI.setLabelColor(AffinityConstants.COLOR_CLUSTER_OBJECT_LABEL); //black
				clusterObjectGUI.setForeground(AffinityConstants.COLOR_CLUSTER_OBJECT_LINE); //light gray
				clusterObjectGUI.setBackground(AffinityConstants.COLOR_CLUSTER_OBJECT_FILL); //light gray
				clusterObjectGUI.setSelectedLineColor(AffinityConstants.COLOR_CLUSTER_OBJECT_HIGHLIGHT_LINE); //medium gray
				clusterObjectGUI.setShowLabel(true);
				clusterObjectGUI.setToolTipText(objectName);		
				craigrogramClusterObjectGUIs.add(clusterObjectGUI);

				DendrogramClusterObjectGUI<K, V> dendrogramClusterObjectGUI = new DendrogramClusterObjectGUI<K, V>(objectID, clusterObject);
				dendrogramClusterObjectGUI.setLabel(objectName);
				dendrogramClusterObjectGUIs.add(dendrogramClusterObjectGUI);
				i++;
			}
		}
		dendrogram.setClusterObjectsAndClusters(dendrogramClusterObjectGUIs, clusters);
		dendrogram.redraw();
		craigrogram.setClusterObjectsAndClusters(craigrogramClusterObjectGUIs, clusters, pg);	
	}

	public Cluster2DView<K, V> getCraigrogram() {
		return craigrogram.getClusterObject2DPlot();
	}
	
	public Cluster2DViewPane<K, V> getCraigrogramPane() {
		return craigrogram;
	}

	public DendrogramCanvas<K, V> getDendrogram() {
		return dendrogram;
	}		

	/** Create the Craigrogram */
	@Override
	protected Cluster2DViewPane<K, V> createLeftClusterView(Composite parent, Color background) {
		craigrogram = new Cluster2DViewPane<K, V>(parent, SWT.BORDER, true, SliderType.NONE, 0, 1000);
		craigrogram.setLockAspectRatio(true);			
		craigrogram.getClusterObject2DPlot().setShowClusters(true);
		craigrogram.getClusterObject2DPlot().setStartRadius(AffinityConstants.CLUSTER_OBJECT_DIAMETER/2 + 
				AffinityConstants.CLUSTER_RADIUS_INCREMENT);		
		craigrogram.getClusterObject2DPlot().setRadiusIncrement(AffinityConstants.CLUSTER_RADIUS_INCREMENT);
		craigrogram.getClusterObject2DPlot().setBackground(background);
		craigrogram.getClusterObject2DPlot().setFillClusters(true);
		return craigrogram;
	}

	/** Create the Dendrogram */
	@Override
	protected DendrogramCanvas<K, V> createRightClusterView(Composite parent, Color background) {
		dendrogram = new DendrogramCanvas<K, V>(parent, SWT.DOUBLE_BUFFERED);		
		dendrogram.setFont(AffinityConstants.getFont(AffinityConstants.NORMAL_FONT));
		dendrogram.setSelectedFont(AffinityConstants.getFont(AffinityConstants.NORMAL_BOLD_FONT));
		dendrogram.setBackground(background);
		return dendrogram;
	}		
	
	@Override
	public void selectionChanged(SelectionChangedEvent<K> event) {
		if(event.getSource() != craigrogram.getClusterObject2DPlot()) {
			//Update selection in the craigrogram
			leftClusterView.setSelectedClusterObjects(event.selectedClusterObjects);
			if(event.selectedClusters != null && event.selectedClusters.size() == 1) {
				ClusterGroup<K> cluster = event.selectedClusters.iterator().next();
				leftClusterView.setSelectedCluster(cluster);
			}
			else {
				leftClusterView.setSelectedCluster(null);
			}
			leftClusterView.redraw();
		} 		
		if(event.getSource() != dendrogram) {
			//Update selection in the dendrogram
			rightClusterView.setSelectedClusterObjects(event.selectedClusterObjects);
			if(event.selectedClusters != null && event.selectedClusters.size() == 1) {
				ClusterGroup<K> cluster = event.selectedClusters.iterator().next();
				rightClusterView.setSelectedCluster(cluster);
			}
			else {
				rightClusterView.setSelectedCluster(null);
			}
			rightClusterView.redraw();
		}
	}
}