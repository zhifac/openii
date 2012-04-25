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

package org.mitre.affinity.view.craigrogram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tracker;

import org.mitre.affinity.AffinityConstants;
import org.mitre.affinity.AffinityConstants.CursorType;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.ClusterStep;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.util.AffinityUtils;
import org.mitre.affinity.view.IClusterObjectGUI;
import org.mitre.affinity.view.craigrogram.ICluster2DView.Mode;
import org.mitre.affinity.view.event.SelectionChangedEvent;
import org.mitre.affinity.view.event.SelectionChangedListener;
import org.mitre.affinity.view.event.SelectionClickedEvent;
import org.mitre.affinity.view.event.SelectionClickedListener;

/**
 * @author CBONACETO
 *
 * @param <K>
 * @param <V>
 */
public class Cluster2DView<K extends Comparable<K>, V> extends ClusterObject2DView<K, V> {
	
	private static final int leftButtonCtrlStateMask = SWT.BUTTON1 | SWT.CONTROL;
	private static final int rightButtonCtrlStateMask = SWT.BUTTON3 | SWT.CONTROL;
	
	/** Whether or not debug mode is enabled. When enabled, debug info is displayed on standard out. */
	public boolean debug = false;
	
	/** Listeners that have registered to received SelectionChangedEvents from this component */
	private List<SelectionChangedListener<K>> selectionChangedListeners;
	
	/** Listeners that have registered to received SelectionClickedEvents from this component */
	private List<SelectionClickedListener<K>> selectionClickedListeners;
	
	/** The current interaction mode (panning or selecting multiple items with a tracker) */
	private Mode mode;
	
	/** The clusters  */
	protected ClustersContainer<K> clusters;	
	
	/** The locations of each cluster  */
	protected List<List<ClusterLocation<K, V>>> clusterLocations;
	
	/** Maps cluster group to its associated cluster location */	
	protected Map<ClusterGroup<K>, ClusterLocation<K, V>> clusterLocationsMap;
	
	/**  Maps cluster object id to its associated cluster location */
	protected Map<K, ClusterObjectLocation<K, V>> clusterObjectLocationsMap;	
	
	/** If true, filter on min/max cluster distance, otherwise filter on min/max step */
	private boolean filterOnClusterDistance = true;
	
	/** The minimum cluster step to display when filtering using steps */
	private int minClusterStep;
	
	/** The maximum cluster step to display when filtering using steps */
	private int maxClusterStep;
	
	/** The minimum cluster step to display when filtering using cluster size */
	private double minClusterDistance = 0;
	
	/** The maximum cluster step to display when filtering using cluster size */
	private double maxClusterDistance = 1;
	
	/**  Whether or not clusters are displayed */
	private boolean showClusters;
	
	/**
	 * Used internally to indicate whether or not the contour lines around clusters need to be recomputed.
	 * Cluster contours must be recomputed when clusters have changed or the size of the component has changed
	 */
	private boolean clusterPathsNeedRefresh = false;
	
	private int maxClusterNestingLevel = 0;
	
	/** The line color */
	private Color lineColor = AffinityConstants.COLOR_RED;
	
	/** The highlight color */
	private Color highlightColor = AffinityConstants.COLOR_HIGHLIGHT;
	
	/** The currently selected cluster object(s) */	
	private Set<IClusterObjectGUI<K, V>> selectedClusterObjects;
	private Set<K> selectedClusterObjectIDs;
	
	/** The currently selected cluster */
	private ClusterLocation<K, V> selectedCluster = null;
	
	/** Whether or not clusters are filled with the fill color */
	private boolean fillClusters = false;
	
	/** Transparency value of the cluster fill  */
	private int fillAlpha = 25;
	
	/** The beginning radius to use when outlining clusters	 */
	private int startRadius = 20;	
	
	/** This amount is added to the start radius at each level in the cluster hierarchy such that
	 * the outline of larger clusters is drawn with larger radii to cirlcle inner clusters. */
	private int radiusIncrement = 5;
	
	/** Whether or not concavities are followed when outlining clusters	 */
	private boolean useConcavitySkipLocic = true;
	
	/** Whether or not the cluster object skip logic is enabled when outlining clusters.
	 * This logic prevents cluster objects from being visited that would cause a line to cross. */
	private boolean useClusterObjectSkipLogic = true;
	
	/** Whether or not cluster objects are scaled when zooming */
	private boolean zoomClusterObjects = false;
	
	public Cluster2DView(Composite parent, int style) {
		this(parent, style, null, null);
	}

	public Cluster2DView(Composite parent, int style, 
			List<IClusterObjectGUI<K, V>> clusterObjects, ClustersContainer<K> clusters) {
		super(parent, style);
		super.panCursor = AffinityConstants.getAffinityCursor(AffinityConstants.CursorType.PANNING_CURSOR);
		this.setMode(Mode.PAN_AND_SELECT);
		setClusterObjects(clusterObjects);
		this.selectedClusterObjects = new TreeSet<IClusterObjectGUI<K, V>>();
		this.selectedClusterObjectIDs = new TreeSet<K>();		
		setClusters(clusters);	
		//this.affinityEventListeners = new LinkedList<AffinityListener>();
		this.selectionChangedListeners = new LinkedList<SelectionChangedListener<K>>();
		this.selectionClickedListeners = new LinkedList<SelectionClickedListener<K>>();		
		
		//Add mouse listener to show the cluster object name when the mouse is over a cluster object
		this.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent event) {
				if(!clusterObjectNamesVisible) {					
					//First we need to transpose the mouse coordinates based on the current zoom/pan settings
					Point translatedPoint = canvasPointToTranslatedPoint(event.x, event.y);

					for(ClusterObjectLocation<K, V> clusterObject : clusterObjectLocationsMap.values()){
						if(clusterObject.bounds.contains(translatedPoint.x, translatedPoint.y))						
							clusterObject.clusterObject.setMouseOver(true);
						else 
							clusterObject.clusterObject.setMouseOver(false);
					}
				}
			}			
		});
		
		Cluster2DViewEventListener eventListener = new Cluster2DViewEventListener();
		addMouseListener(eventListener);
		addMouseMoveListener(eventListener);
	}
	
	public void addSelectionChangedListener(SelectionChangedListener<K> listener) {
		selectionChangedListeners.add(listener);
	}
	
	public void removeSelectionChangedListener(SelectionChangedListener<K> listener) {
		selectionChangedListeners.remove(listener);
	}
	
	public void addSelectionClickedListener(SelectionClickedListener<K> listener) {
		selectionClickedListeners.add(listener);
	}
	
	public void removeSelectionClickedListener(SelectionClickedListener<K> listener) {
		selectionClickedListeners.remove(listener);
	}	
	
	protected void fireSelectionChangedEvent() {
		if(!selectionChangedListeners.isEmpty()) {
			Set<K> selectedClusterIDs = null;
			if(!selectedClusterObjectIDs.isEmpty()) {
				selectedClusterIDs = selectedClusterObjectIDs;
			}
			List<ClusterGroup<K>> selectedClusters = null;
			if(selectedCluster != null) {
				selectedClusters = new LinkedList<ClusterGroup<K>>();
				selectedClusters.add(selectedCluster.cg);
			}
			SelectionChangedEvent<K> event = new SelectionChangedEvent<K>(this, selectedClusterIDs, selectedClusters);
			for(SelectionChangedListener<K> listener : selectionChangedListeners) 
				listener.selectionChanged(event);
		}
	}
	
	protected void fireSelectionClickedEvent(int button, int stateMask, int clickCount, int x, int y) {
		if(!selectionClickedListeners.isEmpty()) {			
			Set<K> selectedClusterIDs = null;
			if(!selectedClusterObjectIDs.isEmpty()) {
				selectedClusterIDs = selectedClusterObjectIDs;
			}
			List<ClusterGroup<K>> selectedClusters = null;
			if(selectedCluster != null) {
				selectedClusters = new LinkedList<ClusterGroup<K>>();
				selectedClusters.add(selectedCluster.cg);
			}
			SelectionClickedEvent<K> event = new SelectionClickedEvent<K>(this, 
					selectedClusterIDs, selectedClusters, button, 
					(stateMask == leftButtonCtrlStateMask) ? true : false, clickCount, x, y);		
			for(SelectionClickedListener<K> listener : selectionClickedListeners) 
				listener.selectionClicked(event);
		}
	}
	
	public void setMode(Mode mode) {
		if(mode != this.mode) {
			this.mode = mode;
			switch(mode) {
			case PAN_AND_SELECT:
				super.normalCursor = (AffinityConstants.getAffinityCursor(CursorType.PAN_CURSOR));
				setCursor(super.normalCursor);
				super.setPanningEnabled(true);
				break;
			case SELECT_MULTIPLE_CLUSTER_OBJECTS:
				super.normalCursor = AffinityConstants.getAffinityCursor(CursorType.SELECT_MULTIPLE_OBJECTS_CURSOR);
				setCursor(super.normalCursor);
				super.setPanningEnabled(false);
				unselectAllClusters();
				redraw();
				break;
			}
		}
	}
	
	/**
	 * @param clusterObjects
	 * @param clusters
	 */
	public void setClusterObjectsAndClusters(List<IClusterObjectGUI<K, V>> clusterObjects, 
			ClustersContainer<K> clusters) {
		setClusterObjects(clusterObjects);
		setClusters(clusters);
		
		redraw();
	}
	
	/* (non-Javadoc)
	 * @see org.mitre.affinity.view.craigrogram.ClusterObject2DView#setClusterObjects(java.util.List)
	 */
	@Override
	public void setClusterObjects(List<IClusterObjectGUI<K, V>> clusterObjects) {
		super.setClusterObjects(clusterObjects);
		this.clusterObjectLocationsMap = new HashMap<K, ClusterObjectLocation<K, V>>();
		if(clusterObjects != null) {
			for(IClusterObjectGUI<K, V> clusterObject : clusterObjects) {
				clusterObjectLocationsMap.put(clusterObject.getObjectID(), 
						new ClusterObjectLocation<K, V>(clusterObject));
			}
		}
		this.clusterPathsNeedRefresh = true;
	}

	/**
	 * @return
	 */
	public ClustersContainer<K> getClusters() {
		return clusters;
	}

	/**
	 * Set the clusters.
	 * 
	 * @param clusters the clusters
	 */
	public void setClusters(ClustersContainer<K> clusters) {
		this.clusters = clusters;		 
		this.minClusterStep = 0;
		if(clusters != null) {
			this.maxClusterStep = clusters.getNumClusterSteps() - 1;
		}
		else {
			this.maxClusterStep = 0;
		}
		modelChanged();
	}
	
	/**
	 * Select a single cluster object.  Any previously selected cluster objects are unselected.
	 * 
	 * @param clusterObjectID
	 */
	public void setSelectedClusterObject(K clusterObjectID) {
		//Unselect any previously selected cluster objects 
		unselectAllClusterObjects();		
		if(clusterObjectID != null) {
			ClusterObjectLocation<K, V> o =  clusterObjectLocationsMap.get(clusterObjectID);
			if(o != null) {
				o.clusterObject.setSelected(true);
				selectedClusterObjects.add(o.clusterObject);
				selectedClusterObjectIDs.add(o.objectID);
			}
		}
	}
	
	/**
	 * Select a group of cluster objects. Any previously selected cluster objects are unselected.
	 * 
	 * @param clusterObjectIDs
	 */
	public void setSelectedClusterObjects(Collection<K> clusterObjectIDs) {
		//Unselect any previously selected cluster objects
		unselectAllClusterObjects();		
		if(clusterObjectIDs != null) {
			for(K objectID : clusterObjectIDs) {
				ClusterObjectLocation<K, V> o =  clusterObjectLocationsMap.get(objectID);
				if(o != null) {
					o.clusterObject.setSelected(true);
					selectedClusterObjects.add(o.clusterObject);
					selectedClusterObjectIDs.add(o.objectID);
				}
			}
		}
	}
	
	/**
	 *  Select a single cluster. Any previously selected clusters are unselected.
	 * 
	 * @param cluster
	 */
	public void setSelectedCluster(ClusterGroup<K> cluster) {
		unselectAllClusters();
		if(cluster != null) {
			ClusterLocation<K, V> clusterLocation = this.clusterLocationsMap.get(cluster);
			if(clusterLocation != null) {
				this.selectedCluster = clusterLocation;
				clusterLocation.setHighlighted(true);
			}
		}		
	}
	
	/**
	 *  Select a single cluster. Any previously selected clusters are unselected.
	 * 
	 * @param cluster
	 */
	public void setSelectedCluster(ClusterLocation<K, V> clusterLocation) {
		unselectAllClusters();
		if(clusterLocation != null) {
			this.selectedCluster = clusterLocation;
			clusterLocation.setHighlighted(true);
		}
	}
	
	/**
	 * Unselect all cluster objects 
	 */
	public void unselectAllClusterObjects() {
		for(IClusterObjectGUI<K, V> o : selectedClusterObjects) { 
			o.setSelected(false);
		}
		selectedClusterObjects.clear();
		selectedClusterObjectIDs.clear();
		if(selectedCluster != null) {
			for(ClusterObjectLocation<K, V> clusterObject : selectedCluster.allClusterObjectLocations) {
				clusterObject.clusterObject.setSelected(false);
			}
		}
	}
	
	/**
	 * Unselect all clusters
	 */
	public void unselectAllClusters() {
		if(selectedCluster != null) {
			selectedCluster.setHighlighted(false);
			selectedCluster = null;
		}
	}
	
	/**
	 * Unselect all cluster objects and clusters
	 */
	public void unselectAllClusterObjectsAndClusters() {
		unselectAllClusterObjects();
		unselectAllClusters();
	}
	
	/**
	 * Gets the first cluster that contain the point (x,y)
	 * 
	 * @param x
	 * @param y
	 * @return - A cluster that contains the point (x,y)
	 */
	public ClusterLocation<K, V> getFirstClusterContainsPoint(int x, int y) {
		GC gc = new GC(this);		
		for(List<ClusterLocation<K, V>> clusters : this.clusterLocations) {
			for(ClusterLocation<K, V> cluster : clusters) {
				if(cluster.contour != null && cluster.contour.contains(x, y, gc, false)) {
					gc.dispose();
					return cluster;
				}
			}
		}
		gc.dispose();
		return null;
	}
	
	/**
	 * Gets all clusters that contain the point (x,y)
	 * 
	 * @param x
	 * @param y
	 * @return - All clusters that contains the point (x,y)
	 */
	public List<ClusterLocation<K, V>> getAllClustersThatContainPoint(int x, int y) {
		GC gc = new GC(this);
		List<ClusterLocation<K, V>> clusterLocations = new ArrayList<ClusterLocation<K, V>>();
		for(List<ClusterLocation<K, V>> clusters : this.clusterLocations) {
			for(ClusterLocation<K, V> cluster : clusters) {
				if(cluster.contour != null && cluster.contour.contains(x, y, gc, false))
					clusterLocations.add(cluster);
			}
		}
		gc.dispose();
		return clusterLocations;
	}

	public boolean isZoomClusterObjects() {
		return zoomClusterObjects;
	}

	public void setZoomClusterObjects(boolean zoomClusterObjects) {
		this.zoomClusterObjects = zoomClusterObjects;
	}

	public boolean isUseConcavitySkipLocic() {
		return useConcavitySkipLocic;
	}

	public void setUseConcavitySkipLocic(boolean useConcavitySkipLocic) {
		this.useConcavitySkipLocic = useConcavitySkipLocic;
		this.clusterPathsNeedRefresh = true;
	}	

	public boolean isUseClusterObjectSkipLogic() {
		return useClusterObjectSkipLogic;
	}

	public void setUseClusterObjectSkipLogic(boolean useClusterObjectSkipLogic) {
		this.useClusterObjectSkipLogic = useClusterObjectSkipLogic;
		this.clusterPathsNeedRefresh = true;
	}

	public int getStartRadius() {
		return startRadius;
	}

	public void setStartRadius(int startRadius) {
		this.startRadius = startRadius;
		this.clusterPathsNeedRefresh = true;
	}	
	
	public void setRadiusIncrement(int radiusIncrement) {
		this.radiusIncrement = radiusIncrement;
		this.clusterPathsNeedRefresh = true;
	}
	
	public int getRadiusIncrement() {
		return radiusIncrement;
	}	

	public boolean isFilterOnClusterDistance() {
		return filterOnClusterDistance;
	}

	public void setFilterOnClusterDistance(boolean filterOnClusterDistance) {
		this.filterOnClusterDistance = filterOnClusterDistance;
		this.clusterPathsNeedRefresh = true;
	}

	public double getMinClusterDistance() {
		return minClusterDistance;
	}

	public void setMinClusterDistance(double minClusterDistance) {
		this.minClusterDistance = minClusterDistance;
	}

	public double getMaxClusterDistance() {
		return maxClusterDistance;
	}

	public void setMaxClusterDistance(double maxClusterDistance) {
		this.maxClusterDistance = maxClusterDistance;
	}
	
	/**
	 * @return the minClusterStep
	 */
	public int getMinClusterStep() {
		return minClusterStep;
	}

	/**
	 * @param minClusterStep the minClusterStep to set
	 */
	public void setMinClusterStep(int minClusterStep) {
		if(minClusterStep < 0)
			throw new IllegalArgumentException("Error: minClusterStep must be greater than 0");
		this.minClusterStep = minClusterStep;
	}

	/**
	 * @return the maxClusterStep
	 */
	public int getMaxClusterStep() {
		return maxClusterStep;
	}

	/**
	 * @param maxClusterStep the maxClusterStep to set
	 */
	public void setMaxClusterStep(int maxClusterStep) {
		if(maxClusterStep < minClusterStep || maxClusterStep >= this.clusters.getNumClusterSteps()) {
			throw new IllegalArgumentException("Error: max cluster step must be between " + minClusterStep +  " and " + (this.clusters.getNumClusterSteps()-1) + ", was: " + maxClusterStep);
		}
		this.maxClusterStep = maxClusterStep;
		//this.clusterPathsNeedRefresh = true;
	}

	/**
	 * @return the showClusters
	 */
	public boolean isShowClusters() {
		return showClusters;
	}

	/**
	 * @param showClusters the showClusters to set
	 */
	public void setShowClusters(boolean showClusters) {
		this.showClusters = showClusters;
		this.clusterPathsNeedRefresh = true;
	}

	public boolean isFillClusters() {
		return fillClusters;
	}

	public void setFillClusters(boolean fillClusters) {
		this.fillClusters = fillClusters;
		//this.clusterPathsNeedRefresh = true;
	}

	public int getFillAlpha() {
		return fillAlpha;
	}

	public void setFillAlpha(int fillAlpha) {
		this.fillAlpha = fillAlpha;
		//this.clusterPathsNeedRefresh = true;
	}

	/**
	 * @return the lineColor
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * @param lineColor the lineColor to set
	 */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public Color getHighlightColor() {
		return highlightColor;
	}

	public void setHighlightColor(Color highlightColor) {
		this.highlightColor = highlightColor;
	}	
	
	public int getMaxClusterNestingLevel() {
		return maxClusterNestingLevel;
	}

	public void clusterObjectLocationsChanged() {		
		this.clusterPathsNeedRefresh = true;
	}
	
	/**
	 * This method is called to notify the class the clusters or cluster objects have changed
	 */
	public void modelChanged() {
		this.processClusters();
		this.clusterPathsNeedRefresh = true;
	}
	
	private void processClusters() {
		this.clusterLocations = new ArrayList<List<ClusterLocation<K, V>>>();
		this.clusterLocationsMap = new HashMap<ClusterGroup<K>, ClusterLocation<K, V>>();
		
		if(clusters == null) {
			return;
		}
		
		//Sort the clusters based on the position of the cluster object farthest to the left in each cluster
		for(ClusterStep<K> cs : this.clusters) {			
			List<List<ClusterObjectLocation<K, V>>> clusters = new ArrayList<List<ClusterObjectLocation<K, V>>>();			
			for(ClusterGroup<K> cg : cs.getClusterGroups()) {
				List<ClusterObjectLocation<K, V>> clusterObjectLocations = new ArrayList<ClusterObjectLocation<K, V>>();
				for(K clusterObjectID : cg) {
					clusterObjectLocations.add(clusterObjectLocationsMap.get(clusterObjectID));
				}
				Collections.sort(clusterObjectLocations);				
				clusters.add(clusterObjectLocations);
			}
		}
		
		//Initialize the cluster locations
		int step = 0;	
		int clusterID = 0;
		for(ClusterStep<K> cs : this.clusters) {		
			List<ClusterLocation<K, V>> clusters = new ArrayList<ClusterLocation<K, V>>();			
			for(ClusterGroup<K> cg : cs.getClusterGroups()) {
				ClusterLocation<K, V> clusterLocation = new ClusterLocation<K, V>(clusterID, cg);				
				clusterLocation.clusterDistance = cg.getDistance();
				clusterID++;				
				if(step == 0) {
					for(K objectID : cg) {
						clusterLocation.addLeafClusterObjectLocation(clusterObjectLocationsMap.get(objectID));
					}
				}				
				else {		
					//Find the cluster groups in a previous step that are contained in this cluster group 
					//(i.e., nested clusters). A cluster group at a previous step is contained cluster group 
					//iff it is a subset of this cluster group.					
					boolean allNestedClustersFound = false;
					int searchStep = step - 1;
					while(!allNestedClustersFound && searchStep >= 0) {
						//For each cluster at the previous step, determine if its cluster objects are contained
						//by this cluster group						
						for(ClusterLocation<K, V> currCluster : this.clusterLocations.get(searchStep)) {							
							if(cg.contains(currCluster.allClusterObjects) && !clusterLocation.containsCluster(currCluster)) {	
								//System.out.println("Adding cluster " + currCluster + " to cluster " + clusterLocation);								
								//The cluster is contained within this cluster group
								clusterLocation.addNestedCluster(currCluster);
								if(clusterLocation.getTotalNumClusterObjects() == cg.getNumClusterObjects()) {								
									allNestedClustersFound = true;
									break;
								}
							}
						}
						searchStep--;
					}
					if(!allNestedClustersFound) {
						//Add any remaining cluster objects that were not part of a nested cluster
						for(K objectID : cg) {
							if(!clusterLocation.containsClusterObject(objectID)) {
								clusterLocation.addLeafClusterObjectLocation(clusterObjectLocationsMap.get(objectID));
							}
						}
					}					
				}
				this.clusterLocationsMap.put(cg, clusterLocation);
				clusters.add(clusterLocation);
				if(clusterLocation.getMaxNestingLevel() > this.maxClusterNestingLevel) 
					this.maxClusterNestingLevel = clusterLocation.getMaxNestingLevel();
			}
			this.clusterLocations.add(clusters);
			step++;
		}
		//DEBUG CODE
		if(debug) {
			System.out.println("Cluster Locations");
			step = 0;
			for(List<ClusterLocation<K, V>> clusterLocations : this.clusterLocations) {
				System.out.print(step + ": ");
				for(ClusterLocation<K, V> clusterLocation : clusterLocations) {
					System.out.print(clusterLocation + ", ");
				}
				System.out.println();
				step++;
			}		
		}
		//END DEBUG CODE
	}
	
	@Override
	public void render(GC gc, Composite parent) {
		//Draw the clusters		
		if(showClusters && clusters != null) {		
			if(debug && this.clusterPathsNeedRefresh) {
				Rectangle bounds = this.getBounds();
				System.out.println("recalculating contours: " + bounds.width + ", " + bounds.height);
			}
			
			gc.setAntialias(SWT.ON);
			gc.setForeground(this.lineColor);
			gc.setLineWidth(1);			
			//int currRadius = this.radiusIncrement;
			//int radius = this.startRadius;

			int minStep = 0;
			int maxStep = clusters.getNumClusterSteps() - 1;			
			if(!this.filterOnClusterDistance) {
				minStep = this.minClusterStep;
				maxStep = this.maxClusterStep;
			}
			
			for(int currStep=minStep; currStep<=maxStep; currStep++) {
				for(ClusterLocation<K, V> cluster : this.clusterLocations.get(currStep)) {					
					/*boolean displayCluster = true;
					if(this.filterOnClusterDistance && 
							(cluster.clusterDistance < this.minClusterDistance || cluster.clusterDistance > this.maxClusterDistance)) {
						displayCluster = false;
					}*/					
					if(clusterPathsNeedRefresh && cluster.getTotalNumClusterObjects() > 1) {
						//Recompute the contour path for the cluster						
						Path path = new Path(gc.getDevice());
						int radius = this.startRadius + this.radiusIncrement * (cluster.getMaxNestingLevel());
						cluster.contourRadius = radius;
						
						//gc.setLineWidth((this.clusterLocations.size()-currStep+1)/5);
						if(cluster.getTotalNumClusterObjects() == 1) {
							//Circle a single cluster object					
							if(debug) {
								System.out.println("Circling Single Cluster Object");
							}
							ClusterObjectLocation<K, V> clusterObject = cluster.allClusterObjectLocations.first();
							path.addArc(clusterObject.location.x - radius, clusterObject.location.y - radius, radius*2, 
									radius*2, 0, 360);
						}
						else {						
							//Circle a cluster
							if(debug) {
								System.out.println("------------------------------");
								System.out.println("Circling Cluster:" + cluster);						
								System.out.println("Sorted cluster objects: " + cluster.allClusterObjectLocations);								
								System.out.println("Max nesting level: " + cluster.getMaxNestingLevel());
							}
							
							double angle = 0;
							double prevAngle = 0;
							double firstAngle = 0;
							Iterator<ClusterObjectLocation<K, V>> clusterObjectIter = cluster.allClusterObjectLocations.iterator();						 
							ClusterObjectLocation<K, V> firstClusterObject = null;
							ClusterObjectLocation<K, V> currClusterObject = clusterObjectIter.next();						 
							ClusterObjectLocation<K, V> prevClusterObject = null;
							ClusterObjectLocation<K, V> nextClusterObject = null;
							ClusterObjectLocation<K, V> nextNextClusterObject = null;

							//Draw first part of cluster enclosing curve, moving from left to right
							int numClusterObjects = cluster.getTotalNumClusterObjects();
							boolean containsNestedClusters = true;
							if(cluster.getNumNestedClusters() == 0) 
								containsNestedClusters = false;

							for(int currClusterObjectIndex = 0; currClusterObjectIndex < numClusterObjects; currClusterObjectIndex++) {
								if(currClusterObjectIndex < (numClusterObjects - 1)) {
									if(currClusterObjectIndex == 0) {
										nextClusterObject = clusterObjectIter.next();
									}
									else { 
										nextClusterObject = nextNextClusterObject;
									}								

									//System.out.println("Prev Cluster Object: " + prevClusterObject + ", Curr ClusterObject: " + currClusterObject + ", Next Cluster Object: " + nextClusterObject + ", Next Next Cluster Object: " + nextNextClusterObject);

									if(clusterObjectIter.hasNext()) {	
										nextNextClusterObject = clusterObjectIter.next();									
										ClusterObjectLocation<K, V> currClusterObjectTest = nextClusterObject;										
										boolean nextClusterObjectFound = false;
										while(!nextClusterObjectFound) { 
											nextClusterObjectFound = true;
											if(this.useConcavitySkipLocic) {
												//We don't draw a path to this cluster object if doing so would create a concavity (eventually we'll specifiy a max concavity)
												//if(currSchemaTest.location.y < currSchema.location.y) { //|| currSchemaTest.location.y < nextNextSchema.location.y) {
												//TestZoomPan all cluster objects after this cluster object to see if they would create a concavity
												Iterator<ClusterObjectLocation<K, V>> concaveTestIter = cluster.allClusterObjectLocations.iterator();
												while(concaveTestIter.hasNext()) {
													ClusterObjectLocation<K, V> concaveClusterObjectTest = concaveTestIter.next();														 
													if(concaveClusterObjectTest.location.x >= currClusterObjectTest.location.x && 
															concaveClusterObjectTest != currClusterObjectTest) {
														int y =  (int)(this.computeSlope(currClusterObject, concaveClusterObjectTest) * 
																(currClusterObjectTest.location.x - currClusterObject.location.x)) + currClusterObject.location.y;								
														if(currClusterObjectTest.location.y < y) {
															if(debug) {																	
																System.out.println("skipping cluster object " + currClusterObjectTest.objectID + 
																		" because it would create a concavity due to cluster object " + concaveClusterObjectTest.objectID);
															}
															nextClusterObjectFound = false;
															break;
														}
													}
												}																					
											}

											if(this.useClusterObjectSkipLogic && nextClusterObjectFound && containsNestedClusters) {																					
												//Old Logic: We don't draw a path to this cluster object if there is a cluster object in a different cluster
												//containing a point with a lower x position than this cluster object and a higher y
												//position than this cluster object.													
												//New Logic: We don't draw a path to this cluster object if doing so would cross the contour
												//path of a nested cluster contained within this cluster.																	
												//Point currPoint = new Point(currSchema.location.x + radius, currSchema.location.y + radius);
												//TODO: Double check that we can just use nestedClusters and not allNestedClusters
												//for(ClusterLocation clusterTest : cluster.allNestedClusters) {											
												for(ClusterLocation<K, V> clusterTest : cluster.nestedClusters) {
													//if(clusterTest.getTotalNumSchemas() > 2 &&
													//	clusterTest.getMinXCoord() < currSchemaTest.location.x &&
													//clusterTest.getMaxYCoord() > currSchemaTest.location.y &&
													//!currSchemaTest.containedInCluster(clusterTest.clusterID)) {
													//TODO: Double check the getMinXCoord test logic
													//TODO: Double check radius subtraction logic
													int xRadiusCorrection = 0;
													//if(currSchema.location.y > currSchemaTest.location.y)
													if(currClusterObject.location.y < currClusterObjectTest.location.y) {
														xRadiusCorrection = -1 * radius;
													} else { 
														xRadiusCorrection = radius;
													}
													if(clusterTest.getTotalNumClusterObjects() > 1 && 
															//clusterTest.getMinXCoord() < currSchemaTest.location.x &&
															clusterTest.getMinXCoord() > currClusterObjectTest.location.x &&
															!currClusterObjectTest.containedInCluster(clusterTest.clusterID) &&
															clusterTest.intersectedByLine(currClusterObject.location.x + xRadiusCorrection, currClusterObject.location.y + radius, 
																	currClusterObjectTest.location.x + xRadiusCorrection, currClusterObjectTest.location.y + radius, 
																	clusterTest.contourRadius, gc, debug)) {
														if(debug) {
															System.out.println("skipping cluster object " + currClusterObjectTest.objectID + " at " +
																	"(" + currClusterObjectTest.location.x + "," + currClusterObjectTest.location.y + ")" +
																	" because nested cluster " + clusterTest + " contains a point with an x coord of " +
																	clusterTest.getMinXCoord() + " and a point with a y coord of " + 
																	clusterTest.getMaxYCoord());
															/*
																if(currStep == maxStep) {
																	Color foreground = gc.getForeground();
																	gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_RED));																	
																	gc.drawLine(currPoint.x, currPoint.y, currSchemaTest.location.x + radius, currSchemaTest.location.y + radius);
																	gc.drawPath(clusterTest.contour);
																	gc.setForeground(foreground);
																}
															 */
														}
														nextClusterObjectFound = false;
														break;
													}											
													else {
														/*
														 * if(debug) {
														System.out.println("not skipping schema " + currSchemaTest.schemaID + " at " +
														"(" + currSchemaTest.location.x + "," + currSchemaTest.location.y + ")" +
														", evaluated nested cluster " + clusterTest + " with min x coord of " +
														clusterTest.getMinXCoord() + " and max y coord of " + 
														clusterTest.getMaxYCoord());
														}
														 */
													}											
												}	
											}

											if(!nextClusterObjectFound) {	
												if(!clusterObjectIter.hasNext()) {
													nextClusterObjectFound = true;
													nextClusterObject = nextNextClusterObject;
													currClusterObjectIndex++;
												}
												else {
													nextClusterObject = nextNextClusterObject;
													nextNextClusterObject = clusterObjectIter.next();
													currClusterObjectIndex++;												
													currClusterObjectTest = nextClusterObject;
												}

												if(debug) {
													System.out.println("currClusterObjectTest: " + currClusterObjectTest + ", nextClusterObject: " + nextClusterObject 
															+ ", nextNextClusterObject: " + nextNextClusterObject);
												}
											} else {											
												nextClusterObject = currClusterObjectTest;
												if(debug) {
													System.out.println("found next cluster object: " + nextClusterObject.objectID + ", nextNextClusterObject: " + nextNextClusterObject);
												}
											}
										}									
									} //if(schemaIter.hasNext())		


									if(debug) {
										System.out.println("Prev cluster object: " + prevClusterObject + ", Curr clusterObject: " + currClusterObject + ", Next cluster object: " + nextClusterObject + ", Next Next cluster object: " + nextNextClusterObject);
									}

									//Compute angle between this cluster object and the next cluster object
									int xChange = nextClusterObject.location.x - currClusterObject.location.x;
									int yChange = nextClusterObject.location.y - currClusterObject.location.y;
									prevAngle = angle;
									angle = AffinityUtils.computeAngle(xChange, yChange) * -1;	

									//Connect this cluster object to the next cluster object
									if(prevClusterObject == null) {
										//Draw convex arc connecting the first cluster object to the next cluster object
										if(debug) 
											System.out.println("Drawing arc at cluster object " + currClusterObject.objectID + " to cluster object " + nextClusterObject.objectID + ", angle = " + angle + ", prevAngle = " + prevAngle);

										firstAngle = angle;		
										firstClusterObject = nextClusterObject;
										
										path.addArc(currClusterObject.location.x - radius, currClusterObject.location.y - radius, 
												radius*2, radius*2, (int)angle + 180 + 85, 90 - 85);											
									}
									else {
										if(debug)
											System.out.println("Drawing arc at cluster object " + currClusterObject.objectID + " to cluster object " + nextClusterObject.objectID + ", angle = " + angle + ", prevAngle = " + prevAngle);											

										//TODO: Account for case when two cluster objects have the same x location (vertical line, need to test using x coords)											
										int y =  (int)(this.computeSlope(prevClusterObject, nextClusterObject) * 
												(currClusterObject.location.x - prevClusterObject.location.x)) + prevClusterObject.location.y;
										if(debug)
											System.out.println("Cluster object " + prevClusterObject.objectID + " location: (" + prevClusterObject.location.x + "," + prevClusterObject.location.y + "), " + 
													"Cluster object " + nextClusterObject.objectID + " location: (" + nextClusterObject.location.x + "," + nextClusterObject.location.y + "), " +
													"Cluster object " + currClusterObject.objectID + " location: (" + currClusterObject.location.x + "," + currClusterObject.location.y + "), " +
													"y = " + y);

										if(currClusterObject.location.y >= y) {
											//Draw convex arc
											int startAngle = 0;
											int arcAngle = 0;											
											if(currClusterObject.location.y == nextClusterObject.location.y) {
												//Orientation: \--
												startAngle =  -1 * (int)(90 + Math.abs(prevAngle));
												arcAngle = (int)Math.abs(prevAngle);
											}
											else if(currClusterObject.location.y > nextClusterObject.location.y && currClusterObject.location.y > prevClusterObject.location.y) {
												//Orientation: \-/
												startAngle = -1 * (int)(90 + Math.abs(prevAngle));											
												arcAngle =(int)(Math.abs(angle) + Math.abs(prevAngle));
											}
											else if(currClusterObject.location.y < nextClusterObject.location.y && currClusterObject.location.y > prevClusterObject.location.y){											
												//Orientation: \   
												//              \
												startAngle = -1 * (int)(90 + Math.abs(prevAngle));
												arcAngle = (int)(Math.abs(prevAngle) - Math.abs(angle));
											}
											else {
												//Orientation: /
												//            /
												startAngle = -1 * (int)(90 - Math.abs(prevAngle));
												arcAngle = -1 * (int)(Math.abs(prevAngle) - Math.abs(angle));											
											}	
											
											if(debug) {
												System.out.println("Drawing convex arc, startAngle = " + startAngle + ", arcAngle = " + arcAngle);
												//gc.drawOval(currSchema.location.x - radius, currSchema.location.y - radius, 
												//		radius*2, radius*2);
											}

											path.addArc(currClusterObject.location.x - radius, currClusterObject.location.y - radius, 
													radius*2, radius*2, startAngle, arcAngle);
										}
										else {
											//Draw concave arc	
											//Compute the angle of a line drawn from this cluster object that bisects a line drawn
											//between the previous and next cluster objects.
											/*
										Point midPoint = new Point((int)((nextSchema.location.x - prevSchema.location.x)/2.0 + prevSchema.location.x),
												(int)((nextSchema.location.y - prevSchema.location.y)/2.0 + prevSchema.location.y));
										double d2 = AffinityUtils.computeDistance(currSchema.location, nextSchema.location);
										double d4 = AffinityUtils.computeDistance(currSchema.location, midPoint);
										//double d3 = AffinityUtils.computeDistance(prevSchema.location, nextSchema.location)/2.0;
										double perpBisectorAng = 0;
										if(nextSchema.location.y > prevSchema.location.y) 
											perpBisectorAng = Math.abs(angle) - Math.abs(AffinityUtils.computeAngle(prevSchema.location, nextSchema.location));
										else
											perpBisectorAng = Math.abs(angle) + Math.abs(AffinityUtils.computeAngle(prevSchema.location, nextSchema.location));
											 */

											//TODO: Fix angle calculation										
											//double ang = -1 *(Math.abs(angle) +  (90 - perpBisectorAng));
											//double ang = -1 * (Math.abs(angle) + 180 - perpBisectorAng - Math.abs((Math.asin(d2 * (AffinityUtils.sin(perpBisectorAng)/d4)) * (180/Math.PI))));
											//double ang = -1 * (Math.abs(angle) + Math.abs((Math.asin(d3 * (AffinityUtils.sin(perpBisectorAng)/d4)) * (180/Math.PI))));
											//double ang = -1 * (Math.abs(angle) + ((180 - Math.abs(angle) - Math.abs(prevAngle))/2.0));

											double ang = 0;
											int arcStartAngle = 0;
											int arcAngle = 0;										
											if(currClusterObject.location.y < nextClusterObject.location.y && currClusterObject.location.y < prevClusterObject.location.y) {
												//Orientation: /-\
												//System.out.println("in /-\\ case");
												//TODO: Fix this
												ang = -1 * ((180 - Math.abs(prevAngle) - Math.abs(angle))/2.0 + Math.abs(angle));
												//arcStartAngle = (90 - (int)Math.abs(Math.abs(angle) - Math.abs(prevAngle)));
												arcStartAngle = (90 + (int)Math.abs(prevAngle));
												//arcAngle = (int)(Math.abs(angle) - Math.abs(prevAngle));
												arcAngle = -1 * (int)(Math.abs(angle) + Math.abs(prevAngle));
												//arcAngle = -5;
											}
											else if(currClusterObject.location.y > nextClusterObject.location.y && currClusterObject.location.y < prevClusterObject.location.y) {
												//Orientation: /
												//            /
												ang = -1 * ((180 - Math.abs(prevAngle) + Math.abs(angle))/2.0 - Math.abs(angle));																								
												arcStartAngle = (int)(90 + Math.abs(prevAngle));											
												arcAngle = -1 * (int)(Math.abs(prevAngle) - Math.abs(angle));
											}
											else {					
												//Orientation: \
												//              \
												ang = -1 * ((180 - Math.abs(angle) + Math.abs(prevAngle))/2.0 + Math.abs(angle));																								
												arcStartAngle = (int)(90 - Math.abs(prevAngle));
												arcAngle = -1 * (int)(Math.abs(angle) - Math.abs(prevAngle));											
											}

											if(debug) {
												System.out.println("Drawing convex arc, ang = " + ang + ", arc start ang = " + arcStartAngle + ", arc angle = " + arcAngle);
												System.out.println("prevAngle = " + prevAngle + ", angle = " + angle);
											}

											//int drawRadius = radius - (this.radiusIncrement * 2); 
											int drawRadius = radius - (this.radiusIncrement * (cluster.getMaxNestingLevel()+1));
											if(drawRadius <= 0)
												drawRadius = 1;
											
											if(debug)
												System.out.println("Drawing concave arc");

											//TODO: The + radiusIncrement*0.5 is a kludge but seems to do a decent job
											path.addArc(currClusterObject.location.x + (int)(AffinityUtils.cos(ang) * (radius+drawRadius)) - drawRadius, 
													currClusterObject.location.y - (int)(AffinityUtils.sin(ang) * (radius+drawRadius+radiusIncrement*.5)) - drawRadius, 
													drawRadius*2, drawRadius*2, arcStartAngle, arcAngle);
											//DEBUG CODE
											/*gc.drawLine(currSchema.location.x, currSchema.location.y, 
												currSchema.location.x + (int)(AffinityUtils.cos(ang) * (radius+drawRadius)),
												currSchema.location.y - (int)(AffinityUtils.sin(ang) * (radius+drawRadius+radiusIncrement*.5)));
										gc.drawOval(currSchema.location.x + (int)(AffinityUtils.cos(ang) * (radius+drawRadius)) - drawRadius,
												currSchema.location.y - (int)(AffinityUtils.sin(ang) * (radius+drawRadius+radiusIncrement*.5)) - drawRadius, 
												drawRadius*2, drawRadius*2);*/
										}										
									}									
									prevClusterObject = currClusterObject;
								}							
								else {
									//We're at the last cluster object
									prevAngle = angle;								
								}	
								if(debug) {
									System.out.println();
								}
								currClusterObject = nextClusterObject;
							}

							//Draw next part of cluster enclosing border, moving from right to left		
							clusterObjectIter = cluster.allClusterObjectLocations.descendingIterator();								
							currClusterObject = clusterObjectIter.next();
							if(debug) {
								System.out.println("Drawing upper curve");
							}
							boolean atFirstClusterObject = true;
							for(int currClusterObjectIndex = numClusterObjects - 1; currClusterObjectIndex >= 0; currClusterObjectIndex--) {
								if(currClusterObjectIndex > 0) {									
									if(currClusterObjectIndex == numClusterObjects - 1) {
										nextClusterObject = clusterObjectIter.next();
									}
									else { 
										nextClusterObject = nextNextClusterObject;
									}	

									if(debug) {
										System.out.println("Prev cluster object: " + prevClusterObject + ", Curr cluster object: " + currClusterObject + ", Next cluster object: " + nextClusterObject + ", Next Next cluster object: " + nextNextClusterObject);
									}

									if(clusterObjectIter.hasNext()) {	
										nextNextClusterObject = clusterObjectIter.next();									
										ClusterObjectLocation<K, V> currClusterObjectTest = nextClusterObject;
										boolean nextClusterObjectFound = false;
										while(!nextClusterObjectFound) { 
											nextClusterObjectFound = true;
											if(useConcavitySkipLocic) {
												//We don't draw a path to this cluster object if doing so would create a concavity (eventually we'll specifiy a max concavity)
												//Test all cluster objects after this cluster object to see if they would create a concavity
												Iterator<ClusterObjectLocation<K, V>> concaveTestIter = cluster.allClusterObjectLocations.descendingIterator();
												while(concaveTestIter.hasNext()) {
													ClusterObjectLocation<K, V> concaveClusterObjectTest = concaveTestIter.next();
													if(concaveClusterObjectTest.location.x <= currClusterObjectTest.location.x && concaveClusterObjectTest != currClusterObjectTest) {
														int y =  (int)(this.computeSlope(currClusterObject, concaveClusterObjectTest) * 
																(currClusterObjectTest.location.x - currClusterObject.location.x)) + currClusterObject.location.y;
														if(debug) {															
															System.out.println("currClusterObject: " + currClusterObject + ", currClusterObjectTest: " + currClusterObjectTest + ", concaveClusterObjectTest: " + concaveClusterObjectTest + " (" +concaveClusterObjectTest.location.x + "," + concaveClusterObjectTest.location.y + "), y=" + y);
														}
														if(currClusterObjectTest.location.y > y) {
															if(debug) {	 																
																System.out.println("skipping cluster object " + currClusterObjectTest.objectID +
																		" because it would create a concavity due to cluster object " + concaveClusterObjectTest.objectID);
															}
															nextClusterObjectFound = false;
															break;
														}
													}
												}
											}

											if(useClusterObjectSkipLogic && nextClusterObjectFound && containsNestedClusters) {
												//Old Logic: We don't draw a path to this cluster object if there is a cluster object in a different cluster
												//containing a point with a lower x position than this cluster object and a higher y
												//position than this cluster object.													
												//New Logic: We don't draw a path to this cluster object if doing so would cross the contour
												//path of a nested cluster contained within this cluster.
												//TODO: Double check radius subtraction logic
												//TODO: Double check that we can just use nestedClusters and not allNestedClusters
												for(ClusterLocation<K, V> clusterTest : cluster.nestedClusters) {
													/*
														if(debug && currStep == maxStep) {
															clusterTest.intersectedByLine(currPoint.x, currPoint.y, 
																	currSchemaTest.location.x - radius, currSchemaTest.location.y - radius,
																	clusterTest.contourRadius, gc, true);
															Color foreground = gc.getForeground();
															gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_RED));																	
															gc.drawLine(currPoint.x, currPoint.y, currSchemaTest.location.x - radius, currSchemaTest.location.y - radius);
															gc.drawPath(clusterTest.contour);
															gc.setForeground(foreground);
														}
													 */
													//TODO: Double check the getMaxXCoord() logic
													//TODO: Double check radius subtraction logic													
													int xRadiusCorrection = 0;
													if(currClusterObject.location.y < currClusterObjectTest.location.y){
														xRadiusCorrection = -1 * radius;
													}
													else { 
														xRadiusCorrection = radius;
													}
													if(clusterTest.getTotalNumClusterObjects() > 1 && 
															//clusterTest.getMaxXCoord() > currClusterObjectTest.location.x &&
															clusterTest.getMaxXCoord() > currClusterObjectTest.location.x &&
															!currClusterObjectTest.containedInCluster(clusterTest.clusterID) &&
															clusterTest.intersectedByLine(currClusterObject.location.x + xRadiusCorrection, currClusterObject.location.y - radius, 
																	currClusterObjectTest.location.x + xRadiusCorrection, currClusterObjectTest.location.y - radius,
																	clusterTest.contourRadius, gc, debug)) {
														if(debug) {
															System.out.println("skipping cluster object " + currClusterObjectTest.objectID + " at " +
																	"(" + currClusterObjectTest.location.x + "," + currClusterObjectTest.location.y + ")" +
																	" because nested cluster " + clusterTest + " contains a point with an x coord of " +
																	clusterTest.getMaxXCoord() + " and a point with a y coord of " + 
																	clusterTest.getMinYCoord());																
														}
														nextClusterObjectFound = false;
														break;
													}											
													else {
														/*
													System.out.println("not skipping schema " + currSchemaTest.schemaID + " at " +
															"(" + currSchemaTest.location.x + "," + currSchemaTest.location.y + ")" +
															", evaluated nested cluster " + clusterTest + " with max x coord of " +
															clusterTest.getMaxXCoord() + " and min y coord of " + 
															clusterTest.getMinYCoord());*/
													}											
												}	
											}

											if(!nextClusterObjectFound) {	
												if(!clusterObjectIter.hasNext()) {
													nextClusterObjectFound = true;
													nextClusterObject = nextNextClusterObject;
													currClusterObjectIndex--;
												}
												else {
													nextClusterObject = nextNextClusterObject;
													nextNextClusterObject = clusterObjectIter.next();
													currClusterObjectIndex--;
													currClusterObjectTest = nextClusterObject;
												}

												if(debug) {
													System.out.println("currClusterObjectTest: " + currClusterObjectTest + ", nextClusterObject: " + nextClusterObject + 
															", nextNextClusterObject: " + nextNextClusterObject);
												}
											}
											else {											
												nextClusterObject = currClusterObjectTest;		
												if(debug) {
													System.out.println("found next cluster object: " + nextClusterObject.objectID +
															", nextNextClusterObject: " + nextNextClusterObject);
												}
											}
										}									
									} //if(clusterObjectIter.hasNext())										
									if(debug) {
										System.out.println("Prev cluster object: " + prevClusterObject + ", Curr cluster object: " + 
												currClusterObject + ", Next cluster object: " + nextClusterObject + ", Next Next cluster object: " + 
												nextNextClusterObject);
									}

									//Compute angle between this cluster object and the next cluster object
									int xChange = nextClusterObject.location.x - currClusterObject.location.x;
									int yChange = nextClusterObject.location.y - currClusterObject.location.y;	
									prevAngle = angle;
									angle = Math.atan((double)yChange/xChange) * (180/Math.PI) * -1;									

									//Connect this cluster object to the next cluster object																
									if(atFirstClusterObject) {
										//Draw convex arc connecting the first cluster object to the next cluster object
										if(debug) {
											System.out.println("At first cluster object (" + currClusterObject.objectID + ")");
											System.out.println("Drawing arc at cluster object " + currClusterObject.objectID + 
													" to cluster object " + nextClusterObject.objectID + ", angle = " + angle + ", prevAngle = " + prevAngle);
										}

										int arcStartAngle = 0;
										int arcAngle = 0;
										if(prevClusterObject == nextClusterObject) {
											if(currClusterObject.location.y > nextClusterObject.location.y) 
												arcStartAngle = -1 * (int)(90 + Math.abs(angle));
											else
												arcStartAngle = -1 * (int)(90 - Math.abs(angle));
											arcAngle = 180;
										}
										else {
											if(currClusterObject.location.y < nextClusterObject.location.y && currClusterObject.location.y < prevClusterObject.location.y) {
												//Orientation: /-\
												arcStartAngle = -1 * (int)(90 - Math.abs(prevAngle));
												arcAngle = (int)(180 + Math.abs(angle) - Math.abs(prevAngle));
											}
											else if(currClusterObject.location.y > nextClusterObject.location.y && currClusterObject.location.y > prevClusterObject.location.y) { 												
												//Orientation: \-/
												arcStartAngle = -1 * (int)(90 + Math.abs(prevAngle));
												arcAngle = (int)(180 - Math.abs(angle) + Math.abs(prevAngle));
											}
											else {
												//Orientation: >
												arcStartAngle = -1 * (int)(90 - Math.abs(prevAngle));
												arcAngle = (int)(180 - Math.abs(angle) - Math.abs(prevAngle));
											}
										}
										path.addArc(currClusterObject.location.x - radius, currClusterObject.location.y - radius, 
												radius*2, radius*2, arcStartAngle, arcAngle);

										//DEBUG CODE										
										/*gc.drawOval(currSchema.location.x - radius, currSchema.location.y - radius, 
												radius*2, radius*2);*/

										atFirstClusterObject = false;										
									}
									else {
										if(debug) {
											System.out.println("Drawing arc at cluster object " + currClusterObject.objectID + 
													" to cluster object " + nextClusterObject.objectID + ", angle = " + angle + ", prevAngle = " + prevAngle);											
										}

										//TODO: Account for case when two cluster objects have the same x location (vertical line, need to test using x coords)											
										int y =  (int)(this.computeSlope(prevClusterObject, nextClusterObject) * (currClusterObject.location.x - prevClusterObject.location.x)) + prevClusterObject.location.y;
										if(debug) {
											System.out.println("Cluster object " + prevClusterObject.objectID + " location: (" + prevClusterObject.location.x + "," + prevClusterObject.location.y + "), " + 
													"Cluster object " + nextClusterObject.objectID + " location: (" + nextClusterObject.location.x + "," + nextClusterObject.location.y + "), " +
													"Cluster object " + currClusterObject.objectID + " location: (" + currClusterObject.location.x + "," + currClusterObject.location.y + "), " +
													"y = " + y);
										}										
										if(currClusterObject.location.y <= y) {
											//Draw convex arc											
											int startAngle = 0;
											int arcAngle = 0;											
											if(currClusterObject.location.y == prevClusterObject.location.y) {
												//Orientation: --\
												startAngle = 90;
												arcAngle = (int)Math.abs(angle);
											}
											else if(currClusterObject.location.y < nextClusterObject.location.y && currClusterObject.location.y < prevClusterObject.location.y) {
												//Orientation: /-\
												startAngle = (int)(90 - Math.abs(prevAngle));
												arcAngle = (int)(Math.abs(angle) + Math.abs(prevAngle));
											}
											else if(currClusterObject.location.y < nextClusterObject.location.y && currClusterObject.location.y > prevClusterObject.location.y){											
												//Orientation:  /
												//             /
												startAngle = (int)(90 + Math.abs(prevAngle));
												arcAngle = -1 * (int)(Math.abs(prevAngle) - Math.abs(angle));											
											}	
											else {
												//Orientation:  \
												//               \										
												startAngle = (int)(90 - Math.abs(prevAngle));
												arcAngle = (int)(Math.abs(prevAngle) - Math.abs(angle));												
											}
											
											if(debug)
												System.out.println("Drawing convex arc: startAngle = " + startAngle + ", arcAngle = " + arcAngle);

											path.addArc(currClusterObject.location.x - radius, currClusterObject.location.y - radius, 
													radius*2, radius*2, startAngle, arcAngle);										
											//DEBUG CODE										
											/*gc.drawOval(currSchema.location.x - radius, currSchema.location.y - radius, 
													radius*2, radius*2);*/
										}
										else {
											//Draw concave arc
											if(debug) 
												System.out.println("Drawing concave arc");
											/*
										double perpBisectorAng = 0;
										if(nextSchema.location.y > prevSchema.location.y)
											perpBisectorAng = Math.abs(angle) - Math.abs(AffinityUtils.computeAngle(prevSchema.location, nextSchema.location));
										else
											perpBisectorAng = Math.abs(angle) + Math.abs(AffinityUtils.computeAngle(prevSchema.location, nextSchema.location));
											 */																						
											//TODO: Fix angle calculation
											//double ang = (Math.abs(angle) + (90 - perpBisectorAng));											
											//double ang = (Math.abs(angle) + ((180 - Math.abs(angle) - Math.abs(prevAngle))/2.0));									

											//int drawRadius = radius - (this.radiusIncrement * 2); 
											int drawRadius = radius - (this.radiusIncrement * (cluster.getMaxNestingLevel()+1));
											if(drawRadius <= 0)
												drawRadius = 1;

											double ang = 0;
											int arcStartAngle = 0;
											int arcAngle = 0;
											if(currClusterObject.location.y > nextClusterObject.location.y && currClusterObject.location.y > prevClusterObject.location.y) {
												//Orientation: \./												
												ang = (Math.abs(angle) + ((180 - Math.abs(prevAngle) - Math.abs(angle))/2.0));
												arcStartAngle = -1 * (int)(90 - Math.abs(prevAngle));											
												arcAngle = -1 * (int)(Math.abs(angle) + Math.abs(prevAngle));

												if(debug) {
													System.out.println("prevAngle = " + prevAngle + ", angle = " + angle);
													System.out.println("Drawing convex arc (case \\./), ang = " + ang + ", arcStartAngle = " + arcStartAngle + ", arcAngle = " + arcAngle);
												}

												//DEBUG CODE
												/*gc.drawLine(currSchema.location.x, currSchema.location.y, currSchema.location.x - (int)(AffinityUtils.cos(ang) * (radius*2)), 
														currSchema.location.y - (int)(AffinityUtils.sin(ang) * (radius*2)));
											gc.drawOval(currSchema.location.x - (int)(AffinityUtils.cos(ang) * (radius+drawRadius)) - drawRadius, 
													currSchema.location.y - (int)(AffinityUtils.sin(ang) * (radius+drawRadius)) - drawRadius, 
													drawRadius*2, drawRadius*2);*/

												path.addArc(currClusterObject.location.x - (int)(AffinityUtils.cos(ang) * (radius+drawRadius)) - drawRadius, 
														currClusterObject.location.y - (int)(AffinityUtils.sin(ang) * (radius+drawRadius)) - drawRadius, 
														drawRadius*2, drawRadius*2, arcStartAngle, arcAngle);
											}
											else if(currClusterObject.location.y > nextClusterObject.location.y && currClusterObject.location.y < prevClusterObject.location.y) {
												//Orientation: \.
												//               \												
												ang = ((180 - Math.abs(angle) + Math.abs(prevAngle))/2.0 - Math.abs(prevAngle));												
												arcStartAngle = (int)(-90 - Math.abs(prevAngle));
												arcAngle = -1 * (int)(Math.abs(angle) - Math.abs(prevAngle));

												if(debug) {
													System.out.println("prevAngle = " + prevAngle + ", angle = " + angle);
													System.out.println("Drawing convex arc (case \\.\\), ang = " + ang + ", arcStartAngle = " + arcStartAngle + ", arcAngle = " + arcAngle);
												}

												//DEBUG CODE
												/*gc.drawLine(currSchema.location.x, currSchema.location.y, currSchema.location.x + (int)(AffinityUtils.cos(ang) * (radius*2)), 
														currSchema.location.y - (int)(AffinityUtils.sin(ang) * (radius*2)));
												gc.drawOval(currSchema.location.x + (int)(AffinityUtils.cos(ang) * (radius*2)) - radius, 
														currSchema.location.y - (int)(AffinityUtils.sin(ang) * (radius*2)) - radius, 
														radius*2, radius*2);*/															

												path.addArc(currClusterObject.location.x + (int)(AffinityUtils.cos(ang) * (radius*2)) - radius, 
														currClusterObject.location.y - (int)(AffinityUtils.sin(ang) * (radius*2)) - radius, 
														radius*2, radius*2, arcStartAngle, arcAngle);
											}
											else {													
												//Orientation: ./
												//            /												
												ang = (Math.abs(prevAngle) + ((Math.abs(prevAngle) + Math.abs(angle))/2.0));
												arcStartAngle = (int)(90 - Math.abs(prevAngle));
												arcAngle = -1 * (int)(Math.abs(prevAngle) - Math.abs(angle));
												if(debug) {
													System.out.println("prevAngle = " + prevAngle + ", angle = " + angle);
													System.out.println("Drawing convex arc (case /./), ang = " + ang + ", arcStartAngle = " + arcStartAngle + ", arcAngle = " + arcAngle);
												}												
												/*
												gc.drawLine(currSchema.location.x, currSchema.location.y, currSchema.location.x + (int)(AffinityUtils.cos(ang) * (radius*2)), 
														currSchema.location.y - (int)(AffinityUtils.sin(ang) * (radius*2)));
												gc.drawOval(currSchema.location.x + (int)(AffinityUtils.cos(ang) * (radius*2)) - radius, 
														currSchema.location.y - (int)(AffinityUtils.sin(ang) * (radius*2)) - radius, 
														radius*2, radius*2);*/	
												path.addArc(currClusterObject.location.x + (int)(AffinityUtils.cos(ang) * (radius*2)) - radius, 
														currClusterObject.location.y - (int)(AffinityUtils.sin(ang) * (radius*2)) - radius, 
														radius*2, radius*2, arcStartAngle, arcAngle);										
											}										
										}
									}																												
								}									
								else {										
									//We're at the last cluster object, which will connect to the previous cluster object	
									int xChange = currClusterObject.location.x - prevClusterObject.location.x;
									int yChange = currClusterObject.location.y - prevClusterObject.location.y;
									angle = Math.atan((double)yChange/xChange) * (180/Math.PI) * -1;																

									//path.addArc(currSchema.location.x - radius, currSchema.location.y - radius, 
									//	radius*2, radius*2, (int)angle + 90, 90);									

									/*path.addArc(currSchema.location.x - radius, currSchema.location.y - radius, 
										radius*2, radius*2, (int)(90 + Math.abs(angle)), 
										(int)(180 - Math.abs(angle) - Math.abs(firstAngle)));*/

									//TODO: Double check that this is correct for all cases
									nextClusterObject = firstClusterObject;
									prevAngle = angle;
									angle = firstAngle;

									if(debug) {
										System.out.println("At last cluster object: " + currClusterObject.objectID + 
												", prevClusterObject: " + prevClusterObject.objectID + ", nextClusterObject: " + nextClusterObject.objectID);
										System.out.println("angle: " + angle + ", prevAngle: " + prevAngle);
									}
									
									if(currClusterObject.location.x == prevClusterObject.location.x && currClusterObject.location.x == nextClusterObject.location.x) {
										//Orientation: |
										//             |
										//             |
										path.close();
									}			
									else {
										int arcStartAngle = 0;
										int arcAngle = 0;
										if(prevClusterObject == nextClusterObject) {
											if(currClusterObject.location.y > nextClusterObject.location.y) { 
												arcStartAngle = (int)(90 + Math.abs(angle));
											}
											else {
												arcStartAngle = (int)(90 - Math.abs(angle));
											}
											arcAngle = 180;
										}
										else {																	
											if(currClusterObject.location.y < nextClusterObject.location.y && currClusterObject.location.y < prevClusterObject.location.y) {
												//Orientation: /-\																								
												arcStartAngle = (int)(90 - Math.abs(prevAngle));
												arcAngle = (int)(180 - Math.abs(angle) + Math.abs(prevAngle));
											}
											else if(currClusterObject.location.y > nextClusterObject.location.y && currClusterObject.location.y > prevClusterObject.location.y) { 												
												//Orientation: \-/										
												arcStartAngle = (int)(90 + Math.abs(prevAngle));											
												arcAngle = (int)(180 + Math.abs(angle) - Math.abs(prevAngle));
											}
											else {
												//Orientation: <										
												arcStartAngle = (int)(90 + Math.abs(prevAngle));											
												arcAngle = (int)(180 - Math.abs(angle) - Math.abs(prevAngle));
											}
										}
										if(debug) {
											System.out.println("arcStartAngle: " + arcStartAngle + ", arcAngle: " + arcAngle);											
										}
										path.addArc(currClusterObject.location.x - radius, currClusterObject.location.y - radius, 
												radius*2, radius*2, arcStartAngle, arcAngle);
									}	
								}
								if(debug) {
									System.out.println();
								}
								prevClusterObject = currClusterObject;
								currClusterObject = nextClusterObject;
							}								
						}	
						//TODO: Determine if path.close() call is necessary here
						//*path.close();
						//Connect path arcs before rendering 
						//(this fixes an swt bug on the Mac where only arcs are drawn)						
						//this.connectPath(cluster.contour, gc);
						cluster.contour = path;
					}// if(clusterPathsNeedRefresh)

					boolean displayCluster = true;
					if(this.filterOnClusterDistance && 
							(cluster.clusterDistance < this.minClusterDistance || cluster.clusterDistance > this.maxClusterDistance)) {
						displayCluster = false;
					}
					if(displayCluster && cluster.getTotalNumClusterObjects() > 1) {						
						if(fillClusters) {
							gc.setBackground(cluster.cg.getDendroColor());	
							gc.setAlpha(fillAlpha);						
							gc.fillPath(cluster.contour);
							gc.setAlpha(255);
						}								
						if(cluster.highlighted) {
							gc.setForeground(highlightColor);
							gc.setLineWidth(2);
						}
						else {
							gc.setForeground(cluster.cg.getDendroColor());
							gc.setLineWidth(1);
						}
						gc.drawPath(cluster.contour);
					}

					//DEBUG CODE	
					/*
						if(cluster.getTotalNumSchemas() == 2) {
							SchemaLocation s1 = cluster.schemaAtMinX;
							SchemaLocation s2 = cluster.schemaAtMaxX;
							//gc.drawLine(s1.location.x, s1.location.y, s2.location.x, s2.location.y);
							Point p1 = new Point(700, 100);
							Point p2 = new Point(735, 700);
							gc.drawLine(p1.x, p1.y, p2.x, p2.y);
							cluster.lineIntersectsCluster(p1, p2, this.radiusIncrement, gc);
							//System.out.println("circling cluster: " + cluster);
							//System.out.println("Line intersects cluster: " + cluster.lineIntersectsCluster(s1.location, s2.location, this.radiusIncrement, gc));
							//System.out.println("Line intersects cluster: " + cluster.lineIntersectsCluster(p1, p2, this.radiusIncrement, gc));
						}
					 */
					//END DEBUG CODE					
				}
			}
		} 
		clusterPathsNeedRefresh = false;

		//Draw the cluster objects
		if(clusterObjectLocationsMap != null && !clusterObjectLocationsMap.isEmpty()) {	
			boolean zoomClusterObjects = this.zoomClusterObjects;
			boolean zoomFont = this.zoomFont;
			if(renderingOverview) {
				zoomClusterObjects = true;
				zoomFont = true;
			}			
			//Draw cluster objects
			if(!zoomClusterObjects) {
				gc.setTransform(null);
			}
			for(ClusterObjectLocation<K, V> c : clusterObjectLocationsMap.values()) {			
				IClusterObjectGUI<K, V> clusterObject = c.clusterObject;
				boolean showLabel = clusterObject.isShowLabel();
				if(!zoomFont) {
					//Cluster object name will be drawn un-zoomed below
					clusterObject.setShowLabel(false);
				}
				if(zoomClusterObjects) {
					//Draw cluster object zoomed
					clusterObject.render(gc, this);
					if(!renderingOverview) {
						c.bounds = clusterObject.getBounds();
					}
				}
				else {
					//Draw cluster object un-zoomed
					Point origLocation = clusterObject.getLocation();
					Point translatedLocation = originalPointToTranslatedPoint(c.location.x, c.location.y);
					clusterObject.setLocation(translatedLocation);					
					clusterObject.setShowLabel(false);
					clusterObject.render(gc, this);						
					clusterObject.setLocation(origLocation);	
					if(!renderingOverview) {
						Rectangle bounds = clusterObject.getBounds();
						c.bounds.width = (int)(bounds.width/zoom);
						c.bounds.height =(int)(bounds.height/zoom);
						c.bounds.x = origLocation.x - c.bounds.width/2; 
						c.bounds.y = origLocation.y - c.bounds.height/2;
					}
				}				
				clusterObject.setShowLabel(showLabel);					
			}
			//}
			
			if(!zoomFont) {
				//Draw cluster object names un-zoomed
				gc.setTransform(null);
				for(ClusterObjectLocation<K, V> c : this.clusterObjectLocationsMap.values()) {
					IClusterObjectGUI<K, V> clusterObject = c.clusterObject;
					Point translatedLocation = originalPointToTranslatedPoint(c.location.x, c.location.y);
					if(clusterObject.isShowLabel() && !zoomFont) {
						//Draw cluster object name un-zoomed
						if(clusterObject.isSelected()) {
							gc.setFont(clusterObject.getSelectedFont());
							gc.setForeground(clusterObject.getLabelColor());
						}
						else {
							gc.setFont(clusterObject.getFont());
							gc.setForeground(clusterObject.getLabelColor());
						}
						Point labelSize = gc.stringExtent(clusterObject.getLabel());	
						if(c.labelBounds == null)
							c.labelBounds = new Rectangle(translatedLocation.x - Math.round(labelSize.x/2.f),translatedLocation.y - Math.round(labelSize.y/2.f),
									labelSize.x, labelSize.y);
						else {
							c.labelBounds.x = translatedLocation.x - Math.round(labelSize.x/2.f);
							c.labelBounds.y = translatedLocation.y - Math.round(labelSize.y/2.f);
							c.labelBounds.width = labelSize.x;
							c.labelBounds.height = labelSize.y;
						}						
						gc.drawString(clusterObject.getLabel(), c.labelBounds.x, c.labelBounds.y, true);						
					}
					else if(!renderingOverview) {
						c.labelBounds = null;
					}
				}
			}		
		}
	}
	
	protected void connectPath(Path path, GC gc) {
		Color col = gc.getForeground();
		gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_DARK_MAGENTA));
		float[] points = path.getPathData().points;
		byte[] types = path.getPathData().types;
		if(points == null || points.length < 2)
			return;
		System.out.println("path start, " + points.length/2 + ", " + types.length);
		int j = -1;
		for (int i = 0; i < points.length; i+=2) {
			String typeStr = "none";
			if(j < types.length - 1) { 
				j++;
				switch(types[j]) {
				case SWT.PATH_LINE_TO: typeStr = "line_to"; break;
				case SWT.PATH_MOVE_TO: typeStr = "move_to"; break;
				case SWT.PATH_QUAD_TO: typeStr = "quad_to"; break;
				case SWT.PATH_CUBIC_TO: typeStr = "cubic_to"; break;
				case SWT.PATH_CLOSE: typeStr = "close"; break;

				}
			}
			gc.drawPoint((int)points[i], (int)points[i+1]);
			System.out.println(i + " " + points[i] + ", " +  points[i+1] + ", " + typeStr);
			
		}
		System.out.println("path end");
		gc.setForeground(col);
	}

	/*
	protected void drawSchema(ISchemaGUI schema, GC gc) {
		//Erase the schema		
		
		boolean showLabel = schema.isShowLabel();
		if(!zoomFont)
			schema.setShowLabel(false);
		schema.render(gc, this);
		schema.setShowLabel(showLabel);
		
		//Draw the schema name un-zoomed if zoomFont is false
		if(!zoomFont) {			
			if(schema.isShowLabel()) {						
				if(schema.isSelected()) {
					gc.setFont(schema.getSelectedFont());
					gc.setForeground(schema.getLabelColor());
					//gc.setForeground(highlightColor);
				}
				else {
					gc.setFont(schema.getFont());
					gc.setForeground(schema.getLabelColor());
				}
				Point labelSize = gc.stringExtent(schema.getLabel());
				//Point translatedLocation = originalPointToTranslatedPoint(schema.getLocation().x, schema.getLocation().y);				
				//gc.drawString(schema.getLabel(), translatedLocation.x - Math.round(labelSize.x/2.f), 
				//		translatedLocation.y - Math.round(labelSize.y/2.f), true);
				gc.drawString(schema.getLabel(), schema.getLocation().x - Math.round(labelSize.x/2.f), 
						schema.getLocation().y - Math.round(labelSize.y/2.f), true);
			}
		}
	}
	*/

	/*
	private void connectSchemas(SchemaLocation currSchema, SchemaLocation prevSchema, SchemaLocation nextSchema) {

	}
	 */
	
	private double computeSlope(ClusterObjectLocation<K, V> o1, ClusterObjectLocation<K, V> o2) {		
		if(o1.location.x == o2.location.x) {
			return 0;		
		}
		return (double)((o2.location.y - o1.location.y))/(o2.location.x - o1.location.x);
	}
	
	public static class ClusterLocation<K extends Comparable<K>, V> {
		
		/** The cluster this cluster location is for */
		public ClusterGroup<K> cg;
		
		public Path contour;
		
		public int contourRadius;		
		
		public Integer clusterID;
		
		public double clusterDistance;
		
		/** Whether the cluster is highlighted */
		public boolean highlighted = false;
		
		/** Clusters contained within this cluster location	 */
		public List<ClusterLocation<K, V>> nestedClusters;				
		public List<ClusterLocation<K, V>> allNestedClusters;
		private int maxNestingLevel = 0;
		
		private ClusterObjectLocation<K, V> clusterObjectAtMinX;
		private ClusterObjectLocation<K, V> clusterObjectAtMaxX;
		private ClusterObjectLocation<K, V> clusterObjectAtMinY;
		private ClusterObjectLocation<K, V> clusterObjectAtMaxY;
		
		/**
		 * Cluster objects contained within this cluster but not in a nested cluster 
		 */
		public NavigableSet<ClusterObjectLocation<K, V>> leafClusterObjects;		
		
		/**
		 * Contains both leaf cluster objects and cluster objects contained within the nested clusters
		 */
		public NavigableSet<K> allClusterObjects;		
		public NavigableSet<ClusterObjectLocation<K, V>> allClusterObjectLocations;		
		
		public ClusterLocation(Integer clusterID, ClusterGroup<K> cg) {
			this.clusterID = clusterID;
			this.cg = cg;
		}
		
		public void addNestedCluster(ClusterLocation<K, V> nestedCluster) {
			if(this.nestedClusters == null) {
				this.nestedClusters = new ArrayList<ClusterLocation<K, V>>();
			}
			this.nestedClusters.add(nestedCluster);			
			
			if(this.allNestedClusters == null) {
				this.allNestedClusters = new ArrayList<ClusterLocation<K, V>>();
			}
			
			if(nestedCluster.allNestedClusters != null && !nestedCluster.allNestedClusters.isEmpty()) {
				for(ClusterLocation<K, V> cluster : nestedCluster.allNestedClusters) {
					if(cluster.getTotalNumClusterObjects() > 1)
						this.allNestedClusters.add(cluster);
				}
			}
			this.allNestedClusters.add(nestedCluster);	
			
			this.addNestedClusterObjectLocations(nestedCluster);
			//if(this.maxNestingLevel == 0)
			//	this.maxNestingLevel = 1;
			if(nestedCluster.maxNestingLevel >= this.maxNestingLevel) {			
				this.maxNestingLevel = nestedCluster.maxNestingLevel + 1;
			}
		}
		
		private void addNestedClusterObjectLocations(ClusterLocation<K, V> nestedCluster) {
			if(this.allClusterObjects == null) {
				this.allClusterObjects = new TreeSet<K>();
				this.allClusterObjectLocations = new TreeSet<ClusterObjectLocation<K, V>>();
			}
			
			if(nestedCluster.allClusterObjects != null) {				
				for(ClusterObjectLocation<K, V> clusterObject : nestedCluster.allClusterObjectLocations) {
					this.allClusterObjects.add(clusterObject.objectID);
					this.allClusterObjectLocations.add(clusterObject);
					
					if(this.clusterObjectAtMinX == null || clusterObject.location.x < this.clusterObjectAtMinX.location.x)
						this.clusterObjectAtMinX = clusterObject;
					if(this.clusterObjectAtMaxX == null || clusterObject.location.x > this.clusterObjectAtMaxX.location.x)
						this.clusterObjectAtMaxX = clusterObject;
					if(this.clusterObjectAtMinY == null ||clusterObject.location.y < this.clusterObjectAtMinY.location.y)
						this.clusterObjectAtMinY = clusterObject;
					if(this.clusterObjectAtMaxY == null ||clusterObject.location.y > this.clusterObjectAtMaxY.location.y)
						this.clusterObjectAtMaxY = clusterObject;
					
					clusterObject.addContainingCluster(this.clusterID);
				}
			}
		}
		
		public void addLeafClusterObjectLocation(ClusterObjectLocation<K, V> clusterObject) {
			if(this.leafClusterObjects == null) {
				this.leafClusterObjects = new TreeSet<ClusterObjectLocation<K, V>>();
			}
			this.leafClusterObjects.add(clusterObject);
			if(this.allClusterObjects == null) {
				this.allClusterObjects = new TreeSet<K>();
				this.allClusterObjectLocations = new TreeSet<ClusterObjectLocation<K, V>>();
			}
			this.allClusterObjects.add(clusterObject.objectID);
			this.allClusterObjectLocations.add(clusterObject);
			
			if(this.maxNestingLevel == 0) {
				this.maxNestingLevel = 1;
			}
			if(this.clusterObjectAtMinX == null || clusterObject.location.x < this.clusterObjectAtMinX.location.x)
				this.clusterObjectAtMinX = clusterObject;
			if(this.clusterObjectAtMaxX == null || clusterObject.location.x > this.clusterObjectAtMaxX.location.x)
				this.clusterObjectAtMaxX = clusterObject;
			if(this.clusterObjectAtMinY == null || clusterObject.location.y < this.clusterObjectAtMinY.location.y)
				this.clusterObjectAtMinY = clusterObject;
			if(this.clusterObjectAtMaxY == null || clusterObject.location.y > this.clusterObjectAtMaxY.location.y)
				this.clusterObjectAtMaxY = clusterObject;
			
			clusterObject.addContainingCluster(this.clusterID);
		}
		
		public boolean intersectedByLine(int x1, int y1, int x2, int y2, int testIncrement, GC gc, boolean debug) {
			return this.intersectedByLine(x1, y1, x2, y2, AffinityUtils.computeAngle(x1, y1, x2, y2), testIncrement, gc, debug);
		}
		
		/**
		 * Tests whether a line that starts at startPoint and ends at endPoint intersects the cluster's
		 * contour path.
		 * 
		 * @param startPoint - the start point of the line
		 * @param endPoint - the end point of the line 
		 * @param angle - the angle between startPoint and endPoint in degrees (3 O'Clock position is 0 degrees)
		 * @param increment
		 * @param gc - a GC
		 * @return - whether or not the line intersects the cluster.
		 */
		public boolean intersectedByLine(int x1, int y1, int x2, int y2, double angle, int increment, GC gc, boolean debug) {
			if(x1 == x2 && y1==y2) {
				if(this.contour != null)
					return this.contour.contains(x1, y1, gc, false);
				return false;
			}
			
			int firstX, firstY;
			int lastX, lastY;
			if(x1 < x2) {
				firstX = x1;				
				firstY = y1;
				lastX = x2;		
				lastY = y2;
			}
			else {
				firstX = x2;
				firstY = y2;
				lastX = x1;
				lastY = y1;
			}
			int currX = firstX;
			int currY = firstY;
			
			double xIncrement = AffinityUtils.cos(angle) * increment;
			double yIncrement = AffinityUtils.sin(angle) * increment;
			Color foreground = gc.getForeground();			
			if(this.contour != null) {				
				if(debug)
					gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLUE));
				int currPoint = 1;
				
				//if(lastY - firstY > 0) {
				if(yIncrement >=0) {
					//y is increasing and (lastY >= firstY), OR y is decreasing and (lastY > firstY)
					//Line slope is positive				
					while(currX <= lastX && currY <= lastY) {
						if(this.contour.contains(currX, currY, gc, false)) {
							if(debug)
								gc.setForeground(foreground);
							return true;						
						}
						//Compute the next point on the line to test testIncrement away from the current point									
						currX = firstX + (int)(xIncrement * currPoint);
						currY = firstY + (int)(yIncrement * currPoint);
						if(debug) {
							gc.drawPoint(currX, currY);
							//System.out.println(currX + ", " + currY + ", " + slope + ", " + lastY);
						}
						currPoint++;			
					}
				}
				else {
					//y is decreasing
					//Line slope is negative or 0										
					while(currX <= lastX && currY >= lastY) {
						if(this.contour.contains(currX, currY, gc, false)) {
							if(debug)
								gc.setForeground(foreground);
							return true;						
						}
						//Compute the next point on the line to test testIncrement away from the current point									
						currX = firstX + (int)(xIncrement * currPoint);
						currY = firstY + (int)(yIncrement * currPoint);	
						if(debug) {
							gc.drawPoint(currX, currY);
							//System.out.println(currX + ", " + currY + ", " + slope + ", " + lastY);
						}
						currPoint++;												
					}
				}
			}			
			if(debug)
				gc.setForeground(foreground);
			return false;
		}		
		
		public boolean containsPoint(GC gc, int x, int y) {
			if(contour != null && contour.contains(x, y, gc, false)) 
				return true;
			return false;
		}
		
		public boolean isHighlighted() {
			return highlighted;
		}

		public void setHighlighted(boolean highlighted) {
			this.highlighted = highlighted;
			//Also highlight/un-highlight cluster objects within the cluster
			if(allClusterObjectLocations != null) {
				for(ClusterObjectLocation<K, V> clusterObject : allClusterObjectLocations) {
					clusterObject.clusterObject.setSelected(highlighted);
				}
			}
		}

		public boolean containsCluster(ClusterLocation<K, V> clusterLocation) {
			if(this.allClusterObjects == null || clusterLocation.allClusterObjects == null) {
				return false;			
			}
			return this.allClusterObjects.containsAll(clusterLocation.allClusterObjects);			
		}
		
		public boolean containsClusterObject(K objectID) {
			if(this.allClusterObjects != null) {
				return this.allClusterObjects.contains(objectID);
			}
			return false;
		}		

		public double getClusterDistance() {
			return clusterDistance;
		}

		public void setClusterDistance(double clusterDistance) {
			this.clusterDistance = clusterDistance;
		}

		public int getTotalNumClusterObjects() {
			return this.allClusterObjects.size();
		}
		
		public int getNumNestedClusters() {
			if(this.nestedClusters == null) {
				return 0;
			}
			return this.nestedClusters.size();
		}
		
		/**
		 * @return the maximum depth of a nested cluster within this cluster
		 */
		public int getMaxNestingLevel() {
			return this.maxNestingLevel-2;
		}
		
		/**
		 * @return the minimum x coordinate for all cluster object locations in this cluster
		 */
		public int getMinXCoord() {
			return clusterObjectAtMinX.location.x;
		}	
		
		/**
		 * @return the maximum x coordinate for all cluster object locations in this cluster
		 */
		public int getMaxXCoord() {
			return clusterObjectAtMaxX.location.x;
		}
		
		/**
		 * @return the minimum y coordinate for all cluster object locations in this cluster
		 */
		public int getMinYCoord() {
			return clusterObjectAtMinY.location.x;
		}
		
		/**
		 * @return the maximum y coordinate for all cluster object locations in this cluster
		 */
		public int getMaxYCoord() {
			return clusterObjectAtMaxY.location.x;
		}		

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			if(this.nestedClusters != null && !this.nestedClusters.isEmpty()) {
				sb.append("{");
				for(ClusterLocation<K, V> cluster : this.nestedClusters) {					
					sb.append(cluster.toString());
					sb.append(", ");
				}
				sb.delete(sb.length()-2,sb.length());
				sb.append("}");
			}
			if(this.leafClusterObjects != null) {
				for(ClusterObjectLocation<K, V> clusterObject : this.leafClusterObjects) {
					sb.append(clusterObject.objectID + ", ");
				}
				sb.delete(sb.length()-2,sb.length());
			}
			return sb.toString();
		}
	}

	public static class ClusterObjectLocation<K extends Comparable<K>, V> implements Comparable<ClusterObjectLocation<K, V>> {
		
		/** The cluster object */
		public IClusterObjectGUI<K, V> clusterObject;
		
		/** The cluster object ID */
		public K objectID;
		
		public Point location;
		
		public Rectangle bounds = new Rectangle(0, 0, 0, 0);
		
		public Rectangle labelBounds;
		
		/** Set of clusters this cluster object is contained in */
		public Set<Integer> clusters;
		
		public ClusterObjectLocation(IClusterObjectGUI<K, V> clusterObject) {
			this.clusterObject = clusterObject;
			this.objectID = clusterObject.getObjectID();
			this.location = clusterObject.getLocation();
			this.clusters = new TreeSet<Integer>();
		}

		public int compareTo(ClusterObjectLocation<K, V> clusterObjectLocation) {
			if(this.location.x == clusterObjectLocation.location.x)
				return (this.location.y - clusterObjectLocation.location.y);			
			return (this.location.x - clusterObjectLocation.location.x);
		}

		@Override
		public String toString() {
			return objectID.toString();
		}
		
		/**
		 * @param clusterID - a cluster id
		 * @return whether or not this cluster object is contained in the cluster with the given id
		 */
		public boolean containedInCluster(Integer clusterID) {
			return clusters.contains(clusterID);
		}
		
		public void addContainingCluster(Integer clusterID) {
			clusters.add(clusterID);
		}
		
		public void removeContainingCluster(Integer clusterID) {
			clusters.remove(clusterID);
		}		
	}
	
	/**
	 * A mouse listener to determine if a cluster object or cluster was double-clicked.
	 *
	 */
	private class Cluster2DViewEventListener extends MouseAdapter implements MouseMoveListener {
		Point startPoint;
				
		@Override
		public void mouseDown(MouseEvent e) {		
			if(e.button == 1 && mode == Mode.SELECT_MULTIPLE_CLUSTER_OBJECTS) {				
				startPoint = new Point(e.x, e.y);					
			}
		}

		public void mouseMove(MouseEvent e) {			
			if(startPoint != null && (Math.abs(startPoint.x - e.x) > 2 || Math.abs(startPoint.y - e.y) > 2)) {
				//Create a tracker for selecting items when the mouse is pressed and moved more than 
				//2 pixels in select mode
				startPoint = null;
				unselectAllClusters();
				//#redraw();
				//gc = new GC(SchemaCluster2DView.this);
				//gc.setTransform(SchemaCluster2DView.this.createZoomPanTransform());
				final Tracker tracker = new Tracker(Cluster2DView.this, SWT.RESIZE);
				tracker.setStippled(true);				
				tracker.setRectangles(new Rectangle [] {new Rectangle(e.x, e.y, 1, 1)});
				tracker.addControlListener(new ControlAdapter() {
					public void controlResized(ControlEvent e) {
						Rectangle rect = tracker.getRectangles()[0];
						//Determine if the tracker rectangle interesects any cluster objects
						for(ClusterObjectLocation<K, V> clusterObjectLocation : clusterObjectLocationsMap.values()) {
							IClusterObjectGUI<K, V> clusterObject = clusterObjectLocation.clusterObject;
							boolean selected = false;
							Rectangle translatedBounds = originalBoundsToTranslatedBounds(clusterObjectLocation.bounds);							
							if(rect.intersects(translatedBounds))								
								selected = true;
							if(selected != clusterObject.isSelected()) {	
								if(selected) {
									selectedClusterObjects.add(clusterObjectLocation.clusterObject);
									selectedClusterObjectIDs.add(clusterObject.getObjectID());
								}
								else {
									selectedClusterObjects.remove(clusterObjectLocation.clusterObject);
									selectedClusterObjectIDs.remove(clusterObject.getObjectID());
								}
								clusterObject.setSelected(selected);
								if(clusterObjectLocation.labelBounds != null) {
									if(clusterObjectLocation.labelBounds.x < translatedBounds.x)
										translatedBounds.x = clusterObjectLocation.labelBounds.x;									
									if(clusterObjectLocation.labelBounds.y < translatedBounds.y)
										translatedBounds.x = clusterObjectLocation.labelBounds.x;
									if(clusterObjectLocation.labelBounds.width > translatedBounds.width)
										translatedBounds.width = clusterObjectLocation.labelBounds.width;
									if(clusterObjectLocation.labelBounds.height > translatedBounds.height)
										translatedBounds.height = clusterObjectLocation.labelBounds.height;
								}								
								redraw(translatedBounds.x - 10, translatedBounds.y - 10, translatedBounds.width + 20, translatedBounds.height + 20, true);
							}
						}						
						//redraw();
					}					
				});
				tracker.open();
				//Notify any SelectionChangedListeners that the selection of cluster objects changed
				fireSelectionChangedEvent();
			}			
		}

		@Override		
		public void mouseUp(MouseEvent e) {
			startPoint = null;			

			if(e.count > 2 || Cluster2DView.this.mouseMoved) return;

			//First we need to transpose the mouse coordinates based on the current zoom/pan settings
			Point translatedPoint = canvasPointToTranslatedPoint(e.x, e.y);

			IClusterObjectGUI<K, V> clickedClusterObject = null;
			ClusterLocation<K, V> clickedCluster = null;

			//Determine if a cluster object was clicked
			for(ClusterObjectLocation<K, V> clusterObject : clusterObjectLocationsMap.values()) {				
				if(clusterObject.bounds.contains(translatedPoint.x, translatedPoint.y)) {
					clickedClusterObject = clusterObject.clusterObject;	
					break;
				}
			}
			
			if(e.button == 3 || e.stateMask == leftButtonCtrlStateMask) {
				if(selectedClusterObjects.size() > 1 && (clickedClusterObject == null || e.button == 3)) {
					//If the right mouse button is clicked anywhere (except on a cluster object)
					//when multiple cluster objects are selected, fire a selection clicked event and return					
					fireSelectionClickedEvent(e.button, e.stateMask, e.count, e.x, e.y);					
					return;				
				}				

				if(selectedClusterObjects.size() <= 1) {
					//#if(mode != Mode.SELECT) {
					//Enable right click on single cluster or cluster object when the cursor is over that cluster object or cluster
					GC gc = new GC(Cluster2DView.this);
					if((selectedClusterObjects.size() == 1 && 
							selectedClusterObjects.iterator().next().containsPoint(translatedPoint.x, translatedPoint.y)) ||
							(selectedCluster != null && selectedCluster.containsPoint(gc, translatedPoint.x, translatedPoint.y))) {						
						gc.dispose();
						fireSelectionClickedEvent(e.button, e.stateMask, e.count, e.x, e.y);						
						return;
					}
					gc.dispose();				
				}
			}			
			
			//#if(mode != Mode.SELECT) return;
			if(clickedClusterObject != null && e.button != 3) {
				//A single cluster object was selected

				//Unselect any previously selected cluster objects or clusters
				if(e.stateMask != leftButtonCtrlStateMask && e.stateMask != rightButtonCtrlStateMask) {																		
					unselectAllClusterObjectsAndClusters();						
				}
				else if(selectedCluster != null) {
					unselectAllClusters();
				}						

				if(e.stateMask == leftButtonCtrlStateMask && clickedClusterObject.isSelected()) {
					//Mark the cluster object as unselected	
					selectedClusterObjects.remove(clickedClusterObject);
					selectedClusterObjectIDs.remove(clickedClusterObject.getObjectID());
					clickedClusterObject.setSelected(false);
				}
				else {						
					//Mark the cluster object as selected						
					selectedClusterObjects.add(clickedClusterObject);
					selectedClusterObjectIDs.add(clickedClusterObject.getObjectID());
					clickedClusterObject.setSelected(true);
				}

				//TODO: Just redraw the cluster object
				//GC gc = new GC(Schema2DPlotWithClusters.this);
				//schema.render(gc, Schema2DPlotWithClusters.this);
				//gc.dispose();
				redraw();
				update();

				//Notify any SelectionChangedListeners that a single cluster object is currently selected
				fireSelectionChangedEvent();						

				//Notify any SelectionClickedListeners that a single cluster object was double clicked or right-clicked				
				if(e.count == 2 || e.button == 3)// || e.stateMask == leftButtonCtrlStateMask)					
					fireSelectionClickedEvent(e.button, e.stateMask, e.count, e.x, e.y);
			}
			else if(mode != Mode.SELECT_MULTIPLE_CLUSTER_OBJECTS && clickedClusterObject == null && e.stateMask != leftButtonCtrlStateMask && e.stateMask != rightButtonCtrlStateMask) {
				//If no cluster object was clicked, determine if a cluster was clicked			
				clickedCluster = getFirstClusterContainsPoint(translatedPoint.x, translatedPoint.y);
				if(clickedCluster != null) {
					if(!clickedCluster.isHighlighted()) {
						//A single cluster was selected						

						//Unselect any previously selected cluster objects or clusters
						unselectAllClusterObjectsAndClusters();						
						selectedCluster = clickedCluster;

						//Mark the cluster as selected						
						clickedCluster.setHighlighted(true);								

						//TODO: just redraw cluster
						//Schema2DPlotWithClusters.this.redraw(x, y, width, height, all);
						//redraw(cluster.contour.getBounds(bounds));					
						redraw();
						update();

						//Notify any SelectionChangedListeners that a single cluster is currently selected
						fireSelectionChangedEvent();
					}		

					//Notify any SelectionClickedListeners that a single cluster was double-clicked or right-clicked					
					if(e.count == 2 || e.button == 3 || e.stateMask == leftButtonCtrlStateMask)
						fireSelectionClickedEvent(e.button, e.stateMask, e.count, e.x, e.y);
				}
				else if(e.button != 3 && e.stateMask != leftButtonCtrlStateMask) {					
					//No cluster object or cluster was clicked, so unhighlight any previously highlighted cluster objects or clusters					
					if(!selectedClusterObjects.isEmpty() || selectedCluster != null) {					 
						unselectAllClusterObjectsAndClusters();
						fireSelectionChangedEvent();
						redraw();
						update();
					}					
				}
			}
			else if(e.button != 3 && e.stateMask != leftButtonCtrlStateMask) {					
				//No cluster object or cluster was clicked, so unhighlight any previously highlighted cluster objects or clusters					
				if(!selectedClusterObjects.isEmpty() || selectedCluster != null) {					 
					unselectAllClusterObjectsAndClusters();
					fireSelectionChangedEvent();
					redraw();
					update();
				}					
			}
		}
	}
}