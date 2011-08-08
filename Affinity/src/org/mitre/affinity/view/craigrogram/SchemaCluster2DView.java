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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tracker;

import org.mitre.affinity.AffinityConstants;
import org.mitre.affinity.AffinityConstants.CursorType;
import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.clusters.ClusterStep;
import org.mitre.affinity.clusters.ClustersContainer;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.util.AffinityUtils;
import org.mitre.affinity.view.ISchemaCluster2DView.Mode;
import org.mitre.affinity.view.event.SelectionChangedEvent;
import org.mitre.affinity.view.event.SelectionChangedListener;
import org.mitre.affinity.view.event.SelectionClickedEvent;
import org.mitre.affinity.view.event.SelectionClickedListener;

public class SchemaCluster2DView extends Schema2DView {
	
	private static final int leftButtonCtrlStateMask = SWT.BUTTON1 | SWT.CONTROL;
	private static final int rightButtonCtrlStateMask = SWT.BUTTON3 | SWT.CONTROL;
	
	/** Whether or not debug mode is enabled. When enabled, debug info is displayed on standard out. */
	public boolean debug = false;
	
	/** Listeners that have registered to received SelectionChangedEvents from this component */
	private List<SelectionChangedListener> selectionChangedListeners;
	
	/** Listeners that have registered to received SelectionClickedEvents from this component */
	private List<SelectionClickedListener> selectionClickedListeners;
	
	/** The current interaction mode (panning or selecting multiple items with a tracker) */
	private Mode mode;
	
	/** The clusters  */
	protected ClustersContainer clusters;		
	protected List<List<ClusterLocation>> clusterLocations;
	
	/** Maps cluster group to its associated cluster location */	
	protected Map<ClusterGroup, ClusterLocation> clusterLocationsMap;
	
	/**  Maps schema id to its associated schema location */
	protected Map<Integer, SchemaLocation> schemaLocationsMap;
	
	/** The position grid that contains the x,y position of each schema */
	protected PositionGrid positionGrid;
	
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
	private Color lineColor = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
	
	/** The highlight color */
	private Color highlightColor = Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
	
	/** The currently selected schema(s) */	
	private Set<ISchemaGUI> selectedSchemas;
	private Set<Integer> selectedSchemaIDs;
	
	/** The currently selected cluster */
	private ClusterLocation selectedCluster = null;
	
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
	
	/** Whether or not the schema skip logic is enabled when outlining clusters.
	 * This logic prevents schemas from being visited that would cause a line to cross. */
	private boolean useSchemaSkipLogic = true;
	
	/** Whether or not schemas are scaled when zooming */
	private boolean zoomSchemas = false;

	public SchemaCluster2DView(Composite parent, int style, 
			List<ISchemaGUI> schemata, ClustersContainer clusters) {
		super(parent, style);
		super.panCursor = AffinityConstants.getAffinityCursor(AffinityConstants.CursorType.PANNING_CURSOR);
		this.setMode(Mode.PAN_AND_SELECT);
		this.setSchemata(schemata);
		this.selectedSchemas = new TreeSet<ISchemaGUI>();
		this.selectedSchemaIDs = new TreeSet<Integer>();
		this.setClusters(clusters);	
		//this.affinityEventListeners = new LinkedList<AffinityListener>();
		this.selectionChangedListeners = new LinkedList<SelectionChangedListener>();
		this.selectionClickedListeners = new LinkedList<SelectionClickedListener>();		
		
		//Add mouse listener to show schema name when the mouse is over a schema
		this.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent event) {
				if(!schemaNamesVisible) {					
					//First we need to transpose the mouse coordinates based on the current zoom/pan settings
					Point translatedPoint = canvasPointToTranslatedPoint(event.x, event.y);
					

					for(SchemaLocation schema : schemaLocationsMap.values()){
						if(schema.bounds.contains(translatedPoint.x, translatedPoint.y))						
							schema.schema.setMouseOver(true);
						else 
							schema.schema.setMouseOver(false);
					}
				}
			}			
		});
		
		SchemaCluster2DViewEventListener eventListener = new SchemaCluster2DViewEventListener();
		addMouseListener(eventListener);
		addMouseMoveListener(eventListener);
	}
	
	public void addSelectionChangedListener(SelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}
	
	public void removeSelectionChangedListener(SelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}
	
	public void addSelectionClickedListener(SelectionClickedListener listener) {
		selectionClickedListeners.add(listener);
	}
	
	public void removeSelectionClickedListener(SelectionClickedListener listener) {
		selectionClickedListeners.remove(listener);
	}	
	
	protected void fireSelectionChangedEvent() {
		if(!selectionChangedListeners.isEmpty()) {
			SelectionChangedEvent event = new SelectionChangedEvent(this);
			if(!selectedSchemaIDs.isEmpty())
				event.selectedSchemas = selectedSchemaIDs;
			if(selectedCluster != null) {
				event.selectedClusters = new LinkedList<ClusterGroup>();
				event.selectedClusters.add(selectedCluster.cg);
			}
			for(SelectionChangedListener listener : selectionChangedListeners) 
				listener.selectionChanged(event);
		}
	}
	
	protected void fireSelectionClickedEvent(int button, int stateMask, int clickCount, int x, int y) {
		if(!selectionClickedListeners.isEmpty()) {			 
			SelectionClickedEvent event = new SelectionClickedEvent(this, button, 
					(stateMask == leftButtonCtrlStateMask) ? true : false, clickCount, x, y);			
			if(!selectedSchemaIDs.isEmpty())
				event.selectedSchemas = selectedSchemaIDs;
			if(selectedCluster != null) {
				event.selectedClusters = new LinkedList<ClusterGroup>();
				event.selectedClusters.add(selectedCluster.cg);
			}
			for(SelectionClickedListener listener : selectionClickedListeners) 
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
			case SELECT_MULTIPLE_SCHEMAS:
				super.normalCursor = AffinityConstants.getAffinityCursor(CursorType.SELECT_MULTIPLE_SCHEMAS_CURSOR);
				setCursor(super.normalCursor);
				super.setPanningEnabled(false);
				unselectAllClusters();
				redraw();
				break;
			}
		}
	}
	
	/**
	 * Select a single schema.  Any previously selected schemas are unselected.
	 * 
	 * @param schemaID
	 */
	public void setSelectedSchema(Integer schemaID) {
		//Unselect any previously selected schemas 
		unselectAllSchemas();		
		if(schemaID != null) {
			SchemaLocation s =  schemaLocationsMap.get(schemaID);
			if(s != null) {
				s.schema.setSelected(true);
				selectedSchemas.add(s.schema);
				selectedSchemaIDs.add(s.schemaID);
			}
		}
	}
	
	/**
	 * Select a group of schemas.  Any previously selected schemas are unselected.
	 * 
	 * @param schemaIDs
	 */
	public void setSelectedSchemas(Collection<Integer> schemaIDs) {
		//Unselect any previously selected schemas
		unselectAllSchemas();		
		if(schemaIDs != null) {
			for(Integer schemaID : schemaIDs) {
				SchemaLocation s =  schemaLocationsMap.get(schemaID);
				if(s != null) {
					s.schema.setSelected(true);
					selectedSchemas.add(s.schema);
					selectedSchemaIDs.add(s.schemaID);
				}
			}
		}
	}
	
	/**
	 *  Select a single cluster. Any previously selected clusters are unselected.
	 * 
	 * @param cluster
	 */
	public void setSelectedCluster(ClusterGroup cluster) {
		unselectAllClusters();
		if(cluster != null) {
			ClusterLocation clusterLocation = this.clusterLocationsMap.get(cluster);
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
	public void setSelectedCluster(ClusterLocation cluster) {
		unselectAllClusters();
		if(cluster != null) {
			this.selectedCluster = cluster;
			cluster.setHighlighted(true);
		}
	}
	
	/**
	 * Unselect all schemas 
	 */
	public void unselectAllSchemas() {
		for(ISchemaGUI s : selectedSchemas) 
			s.setSelected(false);
		selectedSchemas.clear();
		selectedSchemaIDs.clear();
	}
	
	/**
	 * Unselect all clusters
	 */
	public void unselectAllClusters() {
		if(this.selectedCluster != null) {
			this.selectedCluster.setHighlighted(false);
			this.selectedCluster = null;
		}
	}
	
	/**
	 * Unselect all schemas and clusters
	 */
	public void unselectAllSchemasAndClusters() {
		unselectAllSchemas();
		unselectAllClusters();
	}
	
	/**
	 * Gets the first cluster that contain the point (x,y)
	 * 
	 * @param x
	 * @param y
	 * @return - A cluster that contains the point (x,y)
	 */
	public ClusterLocation getFirstClusterContainsPoint(int x, int y) {
		GC gc = new GC(this);		
		for(List<ClusterLocation> clusters : this.clusterLocations) {
			for(ClusterLocation cluster : clusters) {
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
	public List<ClusterLocation> getAllClustersThatContainPoint(int x, int y) {
		GC gc = new GC(this);
		List<ClusterLocation> clusterLocations = new ArrayList<ClusterLocation>();
		for(List<ClusterLocation> clusters : this.clusterLocations) {
			for(ClusterLocation cluster : clusters) {
				if(cluster.contour != null && cluster.contour.contains(x, y, gc, false))
					clusterLocations.add(cluster);
			}
		}
		gc.dispose();
		return clusterLocations;
	}
	
	public boolean isZoomSchemas() {
		return zoomSchemas;
	}

	public void setZoomSchemas(boolean zoomSchemas) {
		this.zoomSchemas = zoomSchemas;
	}

	public boolean isUseConcavitySkipLocic() {
		return useConcavitySkipLocic;
	}

	public void setUseConcavitySkipLocic(boolean useConcavitySkipLocic) {
		this.useConcavitySkipLocic = useConcavitySkipLocic;
		this.clusterPathsNeedRefresh = true;
	}

	public boolean isUseSchemaSkipLogic() {
		return useSchemaSkipLogic;
	}

	public void setUseSchemaSkipLogic(boolean useSchemaSkipLogic) {
		this.useSchemaSkipLogic = useSchemaSkipLogic;
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

	/* (non-Javadoc)
	 * @see org.mitre.cg.viz.gui.swt.Schema2DPlot#setSchemata(java.util.List)
	 */
	@Override
	public void setSchemata(List<ISchemaGUI> schemata) {		
		super.setSchemata(schemata);
		//this.schemaGUIsMap = new HashMap<Integer, ISchemaGUI>();
		this.schemaLocationsMap = new HashMap<Integer, SchemaLocation>();
		for(ISchemaGUI schema : schemata) {
			//this.schemaGUIsMap.put(schema.getSchemaID(), schema);
			schemaLocationsMap.put(schema.getSchemaID(), new SchemaLocation(schema));
		}
		this.clusterPathsNeedRefresh = true;
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

	public ClustersContainer getClusters() {
		return clusters;
	}

	public void setClusters(ClustersContainer clusters) {
		this.clusters = clusters;		 
		this.minClusterStep = 0;
		this.maxClusterStep = clusters.getNumClusterSteps() - 1;
		this.modelChanged();
	}
	
	public int getMaxClusterNestingLevel() {
		return maxClusterNestingLevel;
	}

	public void schemaLocationsChanged() {		
		this.clusterPathsNeedRefresh = true;
	}
	
	/**
	 * This method is called to notify the class the clusters or schemas have changed
	 */
	public void modelChanged() {
		this.processClusters();
		this.clusterPathsNeedRefresh = true;
	}
	
	private void processClusters() {
		//Sort the clusters based on the position of the schema furthest to the left in each cluster		
		//this.sortedClusters = new ArrayList<List<List<SchemaLocation>>>();		
		for(ClusterStep cs : this.clusters) {			
			List<List<SchemaLocation>> clusters = new ArrayList<List<SchemaLocation>>();			
			for(ClusterGroup cg : cs.getClusterGroups()) {
				List<SchemaLocation> schemaLocations = new ArrayList<SchemaLocation>();
				for(Integer schemaID : cg) {					
					//ISchemaGUI schemaGUI = this.schemaGUIsMap.get(schemaID);
					//SchemaLocation schemaLocation = new SchemaLocation(schemaGUI); 
					//schemaLocations.add(schemaLocation);
					schemaLocations.add(schemaLocationsMap.get(schemaID));
				}
				Collections.sort(schemaLocations);				
				clusters.add(schemaLocations);
			}
			//this.sortedClusters.add(clusters);
		}
		
		//Initialize the cluster locations
		this.clusterLocations = new ArrayList<List<ClusterLocation>>();
		this.clusterLocationsMap = new HashMap<ClusterGroup, ClusterLocation>();
		int step = 0;	
		int clusterID = 0;
		for(ClusterStep cs : this.clusters) {		
			List<ClusterLocation> clusters = new ArrayList<ClusterLocation>();			
			for(ClusterGroup cg : cs.getClusterGroups()) {
				ClusterLocation clusterLocation = new ClusterLocation(clusterID, cg);				
				clusterLocation.clusterDistance = cg.getDistance();
				clusterID++;				
				if(step == 0) {
					for(Integer schemaID : cg) {
						//ISchemaGUI schemaGUI = this.schemaGUIsMap.get(schemaID);
						//clusterLocation.addLeafSchemaLocation(new SchemaLocation(schemaGUI));
						clusterLocation.addLeafSchemaLocation(schemaLocationsMap.get(schemaID));
					}
				}				
				else {		
					//Find the cluster groups in a previous step that are contained in this cluster group 
					//(i.e., nested clusters). A cluster group at a previous step is contained cluster group 
					//iff it is a subset of this cluster group.					
					boolean allNestedClustersFound = false;
					int searchStep = step - 1;
					while(!allNestedClustersFound && searchStep >= 0) {
						//For each cluster at the previous step, determine if its schemata are contained
						//by this cluster group						
						for(ClusterLocation currCluster : this.clusterLocations.get(searchStep)) {							
							if(cg.contains(currCluster.allSchemas) && !clusterLocation.containsCluster(currCluster)) {	
								//System.out.println("Adding cluster " + currCluster + " to cluster " + clusterLocation);
								
								//The cluster is contained within this cluster group
								clusterLocation.addNestedCluster(currCluster);
								if(clusterLocation.getTotalNumSchemas() == cg.getNumSchemas()) {								
									allNestedClustersFound = true;
									break;
								}
							}
						}
						searchStep--;
					}
					if(!allNestedClustersFound) {
						//System.out.println("At step " + step + ", looking for leaf schemas");
						//Add any remaining schemas that were not part of a nested cluster
						for(Integer schemaID : cg) {
							if(!clusterLocation.containsSchema(schemaID)) {
								//ISchemaGUI schemaGUI = this.schemaGUIsMap.get(schemaID);
								//clusterLocation.addLeafSchemaLocation(new SchemaLocation(schemaGUI));
								clusterLocation.addLeafSchemaLocation(schemaLocationsMap.get(schemaID));
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
			for(List<ClusterLocation> clusterLocations : this.clusterLocations) {
				System.out.print(step + ": ");
				for(ClusterLocation clusterLocation : clusterLocations) {
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
		if(this.showClusters) {		
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

			//for(int currStep=minClusterStep; currStep<=maxClusterStep; currStep++) {
			for(int currStep=minStep; currStep<=maxStep; currStep++) {
				for(ClusterLocation cluster : this.clusterLocations.get(currStep)) {					
					/*boolean displayCluster = true;
					if(this.filterOnClusterDistance && 
							(cluster.clusterDistance < this.minClusterDistance || cluster.clusterDistance > this.maxClusterDistance)) {
						displayCluster = false;
					}*/					
					if(clusterPathsNeedRefresh && cluster.getTotalNumSchemas() > 1) {
						//Recompute the contour path for the cluster						
						Path path = new Path(gc.getDevice());
						int radius = this.startRadius + this.radiusIncrement * (cluster.getMaxNestingLevel());
						cluster.contourRadius = radius;
						
						//gc.setLineWidth((this.clusterLocations.size()-currStep+1)/5);
						//gc.setAlpha(alpha);					
						if(cluster.getTotalNumSchemas() == 1) {
							//Circle a single schema					
							if(debug) 
								System.out.println("Circling Single Schema");
							SchemaLocation schema = cluster.allSchemaLocations.first();
							path.addArc(schema.location.x - radius, schema.location.y - radius, radius*2, radius*2, 
									0, 360);
						}
						else {						
							//Circle a cluster
							if(debug) {
								System.out.println("------------------------------");
								System.out.println("Circling Cluster:" + cluster);						
								System.out.println("Sorted schemas: " + cluster.allSchemaLocations);
								
								System.out.println("Max nesting level: " + cluster.getMaxNestingLevel());
							}
							
							double angle = 0;
							double prevAngle = 0;
							double firstAngle = 0;
							Iterator<SchemaLocation> schemaIter = cluster.allSchemaLocations.iterator();						 
							SchemaLocation firstSchema = null;
							SchemaLocation currSchema = schemaIter.next();						 
							SchemaLocation prevSchema = null;
							SchemaLocation nextSchema = null;
							SchemaLocation nextNextSchema = null;

							//Draw first part of cluster enclosing curve, moving from left to right
							int numSchemas = cluster.getTotalNumSchemas();
							boolean containsNestedClusters = true;
							if(cluster.getNumNestedClusters() == 0) 
								containsNestedClusters = false;

							for(int currSchemaIndex = 0; currSchemaIndex < numSchemas; currSchemaIndex++) {
								if(currSchemaIndex < (numSchemas - 1)) {
									if(currSchemaIndex == 0) {
										nextSchema = schemaIter.next();
									}
									else { 
										nextSchema = nextNextSchema;
									}								

									//System.out.println("Prev Schema: " + prevSchema + ", Curr Schema: " + currSchema + ", Next Schema: " + nextSchema + ", Next Next Schema: " + nextNextSchema);

									if(schemaIter.hasNext()) {	
										nextNextSchema = schemaIter.next();									
										SchemaLocation currSchemaTest = nextSchema;	
										//boolean nextSchemaFound = !containsNestedClusters;
										boolean nextSchemaFound = false;
										while(!nextSchemaFound) { // && schemaIter.hasNext()) {
											nextSchemaFound = true;
											if(this.useConcavitySkipLocic) {
												//We don't draw a path to this schema it doing so would create a concavity (eventually we'll specifiy a max concavity)
												//if(currSchemaTest.location.y < currSchema.location.y) { //|| currSchemaTest.location.y < nextNextSchema.location.y) {
												//TestZoomPan all schemas after this schema to see if they would create a concavity
												Iterator<SchemaLocation> concaveTestIter = cluster.allSchemaLocations.iterator();
												while(concaveTestIter.hasNext()) {
													SchemaLocation concaveSchemaTest = concaveTestIter.next();														 
													if(concaveSchemaTest.location.x >= currSchemaTest.location.x && concaveSchemaTest != currSchemaTest) {
														int y =  (int)(this.computeSlope(currSchema, concaveSchemaTest) * (currSchemaTest.location.x - currSchema.location.x)) + currSchema.location.y;								
														if(currSchemaTest.location.y < y) {
															if(debug)																	
																System.out.println("skipping schema " + currSchemaTest.schemaID + " because it would create a concavity due to schema " + concaveSchemaTest.schemaID);
															nextSchemaFound = false;
															break;
														}
													}
												}																					
											}

											if(this.useSchemaSkipLogic && nextSchemaFound && containsNestedClusters) {																					
												//Old Logic: We don't draw a path to this schema if there is a schema in a different cluster
												//containing a point with a lower x position than this schema and a higher y
												//position than this schema.													
												//New Logic: We don't draw a path to this schema if doing so would cross the contour
												//path of a nested cluster contained within this cluster.																	
												//Point currPoint = new Point(currSchema.location.x + radius, currSchema.location.y + radius);
												//TODO: Double check that we can just use nestedClusters and not allNestedClusters
												//for(ClusterLocation clusterTest : cluster.allNestedClusters) {											
												for(ClusterLocation clusterTest : cluster.nestedClusters) {
													//if(clusterTest.getTotalNumSchemas() > 2 &&
													//	clusterTest.getMinXCoord() < currSchemaTest.location.x &&
													//clusterTest.getMaxYCoord() > currSchemaTest.location.y &&
													//!currSchemaTest.containedInCluster(clusterTest.clusterID)) {
													//TODO: Double check the getMinXCoord test logic
													//TODO: Double check radius subtraction logic
													int xRadiusCorrection = 0;
													//if(currSchema.location.y > currSchemaTest.location.y)
													if(currSchema.location.y < currSchemaTest.location.y)
														xRadiusCorrection = -1 * radius;
													else 
														xRadiusCorrection = radius;		
													if(clusterTest.getTotalNumSchemas() > 1 && 
															//clusterTest.getMinXCoord() < currSchemaTest.location.x &&
															clusterTest.getMinXCoord() > currSchemaTest.location.x &&
															!currSchemaTest.containedInCluster(clusterTest.clusterID) &&
															clusterTest.intersectedByLine(currSchema.location.x + xRadiusCorrection, currSchema.location.y + radius, 
																	currSchemaTest.location.x + xRadiusCorrection, currSchemaTest.location.y + radius, 
																	clusterTest.contourRadius, gc, debug)) {
														if(debug) {
															System.out.println("skipping schema " + currSchemaTest.schemaID + " at " +
																	"(" + currSchemaTest.location.x + "," + currSchemaTest.location.y + ")" +
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
														nextSchemaFound = false;
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

											if(!nextSchemaFound) {	
												if(!schemaIter.hasNext()) {
													nextSchemaFound = true;
													nextSchema = nextNextSchema;
													currSchemaIndex++;
												}
												else {
													nextSchema = nextNextSchema;
													nextNextSchema = schemaIter.next();
													currSchemaIndex++;												
													currSchemaTest = nextSchema;
												}

												if(debug)
													System.out.println("currSchemaTest: " + currSchemaTest + ", nextSchema: " + nextSchema + ", nextNextSchema: " + nextNextSchema);
											}
											else {											
												nextSchema = currSchemaTest;
												if(debug)
													System.out.println("found next schema: " + nextSchema.schemaID + ", nextNextSchema: " + nextNextSchema);											
											}
										}									
									} //if(schemaIter.hasNext())		


									if(debug)
										System.out.println("Prev Schema: " + prevSchema + ", Curr Schema: " + currSchema + ", Next Schema: " + nextSchema + ", Next Next Schema: " + nextNextSchema);

									//Compute angle between this schema and the next schema
									int xChange = nextSchema.location.x - currSchema.location.x;
									int yChange = nextSchema.location.y - currSchema.location.y;
									prevAngle = angle;
									angle = AffinityUtils.computeAngle(xChange, yChange) * -1;	

									//Connect this schema to the next schema
									if(prevSchema == null) {
										//Draw convex arc connecting the first schema to the next schema
										if(debug) 
											System.out.println("Drawing arc at schema " + currSchema.schemaID + " to schema " + nextSchema.schemaID + ", angle = " + angle + ", prevAngle = " + prevAngle);

										firstAngle = angle;		
										firstSchema = nextSchema;
										
										path.addArc(currSchema.location.x - radius, currSchema.location.y - radius, 
												radius*2, radius*2, (int)angle + 180 + 85, 90 - 85);											
									}
									else {
										if(debug)
											System.out.println("Drawing arc at schema " + currSchema.schemaID + " to schema " + nextSchema.schemaID + ", angle = " + angle + ", prevAngle = " + prevAngle);											

										//TODO: Account for case when two schemas have the same x location (vertical line, need to test using x coords)											
										int y =  (int)(this.computeSlope(prevSchema, nextSchema) * (currSchema.location.x - prevSchema.location.x)) + prevSchema.location.y;
										if(debug)
											System.out.println("Schema " + prevSchema.schemaID + " location: (" + prevSchema.location.x + "," + prevSchema.location.y + "), " + 
													"Schema " + nextSchema.schemaID + " location: (" + nextSchema.location.x + "," + nextSchema.location.y + "), " +
													"Schema " + currSchema.schemaID + " location: (" + currSchema.location.x + "," + currSchema.location.y + "), " +
													"y = " + y);

										if(currSchema.location.y >= y) {
											//Draw convex arc
											int startAngle = 0;
											int arcAngle = 0;											
											if(currSchema.location.y == nextSchema.location.y) {
												//Orientation: \--
												startAngle =  -1 * (int)(90 + Math.abs(prevAngle));
												arcAngle = (int)Math.abs(prevAngle);
											}
											else if(currSchema.location.y > nextSchema.location.y && currSchema.location.y > prevSchema.location.y) {
												//Orientation: \-/
												startAngle = -1 * (int)(90 + Math.abs(prevAngle));											
												arcAngle =(int)(Math.abs(angle) + Math.abs(prevAngle));
											}
											else if(currSchema.location.y < nextSchema.location.y && currSchema.location.y > prevSchema.location.y){											
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

											path.addArc(currSchema.location.x - radius, currSchema.location.y - radius, 
													radius*2, radius*2, startAngle, arcAngle);
										}
										else {
											//Draw concave arc	
											//Compute the angle of a line drawn from this schema that bisects a line drawn
											//between the previous and next schema.
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
											if(currSchema.location.y < nextSchema.location.y && currSchema.location.y < prevSchema.location.y) {
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
											else if(currSchema.location.y > nextSchema.location.y && currSchema.location.y < prevSchema.location.y) {
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
											path.addArc(currSchema.location.x + (int)(AffinityUtils.cos(ang) * (radius+drawRadius)) - drawRadius, 
													currSchema.location.y - (int)(AffinityUtils.sin(ang) * (radius+drawRadius+radiusIncrement*.5)) - drawRadius, 
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
									prevSchema = currSchema;
								}							
								else {
									//We're at the last schema
									prevAngle = angle;								
								}	
								if(debug)
									System.out.println();
								//prevSchema = currSchema;
								currSchema = nextSchema;
							}

							//Draw next part of cluster enclosing border, moving from right to left		
							schemaIter = cluster.allSchemaLocations.descendingIterator();								
							currSchema = schemaIter.next();
							if(debug)
								System.out.println("Drawing upper curve");
							//prevSchema = null;
							boolean atFirstSchema = true;
							for(int currSchemaIndex = numSchemas - 1; currSchemaIndex >= 0; currSchemaIndex--) {									
								//if(schemaIter.hasNext()) {
								if(currSchemaIndex > 0) {									
									if(currSchemaIndex == numSchemas - 1) {
										nextSchema = schemaIter.next();
										//System.out.println("next schema: " + nextSchema);
									}
									else { 
										nextSchema = nextNextSchema;
									}	

									if(debug)
										System.out.println("Prev Schema: " + prevSchema + ", Curr Schema: " + currSchema + ", Next Schema: " + nextSchema + ", Next Next Schema: " + nextNextSchema);								

									if(schemaIter.hasNext()) {	
										nextNextSchema = schemaIter.next();									
										SchemaLocation currSchemaTest = nextSchema;									
										//SchemaLocation prevSchemaTest = currSchema;
										boolean nextSchemaFound = false;
										while(!nextSchemaFound) { // && schemaIter.hasNext()) {
											nextSchemaFound = true;
											if(this.useConcavitySkipLocic) {
												//We don't draw a path to this schema it doing so would create a concavity (eventually we'll specifiy a max concavity)												
												//if(currSchemaTest.location.y > currSchema.location.y && currSchemaTest.schemaID != 1) {// && nextSchema.location.y < currSchemaTest.location.y) {// && currSchemaTest.location.y > nextNextSchema.location.y) {
												//Test all schemas after this schema to see if they would create a concavity
												Iterator<SchemaLocation> concaveTestIter = cluster.allSchemaLocations.descendingIterator();
												while(concaveTestIter.hasNext()) {
													SchemaLocation concaveSchemaTest = concaveTestIter.next();
													//if(concaveSchemaTest.location.x < currSchemaTest.location.x) {
													if(concaveSchemaTest.location.x <= currSchemaTest.location.x && concaveSchemaTest != currSchemaTest) {
														int y =  (int)(this.computeSlope(currSchema, concaveSchemaTest) * (currSchemaTest.location.x - currSchema.location.x)) + currSchema.location.y;
														if(debug)															
															System.out.println("currSchema: " + currSchema + ", currSchemaTest: " + currSchemaTest + ", concaveSchemaTest: " + concaveSchemaTest + " (" +concaveSchemaTest.location.x + "," + concaveSchemaTest.location.y + "), y=" + y);
														if(currSchemaTest.location.y > y) {
															if(debug)																	
																System.out.println("skipping schema " + currSchemaTest.schemaID + " because it would create a concavity due to schema " + concaveSchemaTest.schemaID);
															nextSchemaFound = false;
															break;
														}
													}
												}
											}

											if(this.useSchemaSkipLogic && nextSchemaFound && containsNestedClusters) {
												//Old Logic: We don't draw a path to this schema if there is a schema in a different cluster
												//containing a point with a lower x position than this schema and a higher y
												//position than this schema.													
												//New Logic: We don't draw a path to this schema if doing so would cross the contour
												//path of a nested cluster contained within this cluster.
												//TODO: Double check radius subtraction logic												
												//Point currPoint = new Point(currSchema.location.x - radius, currSchema.location.y - radius);
												//TODO: Double check that we can just use nestedClusters and not allNestedClusters
												//for(ClusterLocation clusterTest : cluster.allNestedClusters) {
												for(ClusterLocation clusterTest : cluster.nestedClusters) {
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
													//if(currSchemaTest.schemaID != 1 &&
													//	clusterTest.getTotalNumSchemas() > 3 &&
													//	clusterTest.getMaxXCoord() > currSchemaTest.location.x &&
													//	clusterTest.getMinYCoord() < currSchemaTest.location.y &&
													//	!currSchemaTest.containedInCluster(clusterTest.clusterID)) {
													//TODO: Double check the getMaxXCoord() logic
													//TODO: Double check radius subtraction logic	
													//boolean showIntersectingLine = false;
													//if(debug && currSchema.schemaID == 3753) showIntersectingLine = true;
													int xRadiusCorrection = 0;
													if(currSchema.location.y < currSchemaTest.location.y)
														xRadiusCorrection = -1 * radius;
													else 
														xRadiusCorrection = radius;															
													if(clusterTest.getTotalNumSchemas() > 1 && 
															//clusterTest.getMaxXCoord() > currSchemaTest.location.x &&
															clusterTest.getMaxXCoord() > currSchemaTest.location.x &&
															!currSchemaTest.containedInCluster(clusterTest.clusterID) &&
															clusterTest.intersectedByLine(currSchema.location.x + xRadiusCorrection, currSchema.location.y - radius, 
																	currSchemaTest.location.x + xRadiusCorrection, currSchemaTest.location.y - radius,
																	clusterTest.contourRadius, gc, debug)) {
														if(debug) {
															System.out.println("skipping schema " + currSchemaTest.schemaID + " at " +
																	"(" + currSchemaTest.location.x + "," + currSchemaTest.location.y + ")" +
																	" because nested cluster " + clusterTest + " contains a point with an x coord of " +
																	clusterTest.getMaxXCoord() + " and a point with a y coord of " + 
																	clusterTest.getMinYCoord());																
														}
														nextSchemaFound = false;
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

											if(!nextSchemaFound) {	
												if(!schemaIter.hasNext()) {
													nextSchemaFound = true;
													nextSchema = nextNextSchema;
													currSchemaIndex--;
												}
												else {
													nextSchema = nextNextSchema;
													nextNextSchema = schemaIter.next();
													currSchemaIndex--;
													//prevSchemaTest = currSchemaTest;
													//currSchemaTest = nextNextSchema;
													currSchemaTest = nextSchema;
												}

												if(debug)
													System.out.println("currSchemaTest: " + currSchemaTest + ", nextSchema: " + nextSchema + ", nextNextSchema: " + nextNextSchema);
											}
											else {											
												nextSchema = currSchemaTest;		
												if(debug)
													System.out.println("found next schema: " + nextSchema.schemaID + ", nextNextSchema: " + nextNextSchema);											
											}
										}									
									} //if(schemaIter.hasNext())										
									if(debug)
										System.out.println("Prev Schema: " + prevSchema + ", Curr Schema: " + currSchema + ", Next Schema: " + nextSchema + ", Next Next Schema: " + nextNextSchema);

									//Compute angle between this schema and the next schema
									int xChange = nextSchema.location.x - currSchema.location.x;
									int yChange = nextSchema.location.y - currSchema.location.y;	
									prevAngle = angle;
									angle = Math.atan((double)yChange/xChange) * (180/Math.PI) * -1;									

									//Connect this schema to the next schema																
									if(atFirstSchema) {
										//Draw convex arc connecting the first schema to the next schema
										if(debug) {
											System.out.println("At first schema (" + currSchema.schemaID + ")");
											System.out.println("Drawing arc at schema " + currSchema.schemaID + " to schema " + nextSchema.schemaID + ", angle = " + angle + ", prevAngle = " + prevAngle);
										}

										int arcStartAngle = 0;
										int arcAngle = 0;
										if(prevSchema == nextSchema) {
											if(currSchema.location.y > nextSchema.location.y) 
												arcStartAngle = -1 * (int)(90 + Math.abs(angle));
											else
												arcStartAngle = -1 * (int)(90 - Math.abs(angle));
											arcAngle = 180;
										}
										else {
											if(currSchema.location.y < nextSchema.location.y && currSchema.location.y < prevSchema.location.y) {
												//Orientation: /-\
												arcStartAngle = -1 * (int)(90 - Math.abs(prevAngle));
												arcAngle = (int)(180 + Math.abs(angle) - Math.abs(prevAngle));
											}
											else if(currSchema.location.y > nextSchema.location.y && currSchema.location.y > prevSchema.location.y) { 												
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
										path.addArc(currSchema.location.x - radius, currSchema.location.y - radius, 
												radius*2, radius*2, arcStartAngle, arcAngle);

										//DEBUG CODE										
										/*gc.drawOval(currSchema.location.x - radius, currSchema.location.y - radius, 
												radius*2, radius*2);*/

										atFirstSchema = false;										
									}
									else {
										//prevAngle = angle;
										if(debug)
											System.out.println("Drawing arc at schema " + currSchema.schemaID + " to schema " + nextSchema.schemaID + ", angle = " + angle + ", prevAngle = " + prevAngle);											

										//TODO: Account for case when two schemas have the same x location (vertical line, need to test using x coords)											
										int y =  (int)(this.computeSlope(prevSchema, nextSchema) * (currSchema.location.x - prevSchema.location.x)) + prevSchema.location.y;
										if(debug) {
											System.out.println("Schema " + prevSchema.schemaID + " location: (" + prevSchema.location.x + "," + prevSchema.location.y + "), " + 
													"Schema " + nextSchema.schemaID + " location: (" + nextSchema.location.x + "," + nextSchema.location.y + "), " +
													"Schema " + currSchema.schemaID + " location: (" + currSchema.location.x + "," + currSchema.location.y + "), " +
													"y = " + y);
										}
										//if(prevAngle < 0) {										
										if(currSchema.location.y <= y) {
											//Draw convex arc											
											int startAngle = 0;
											int arcAngle = 0;											
											if(currSchema.location.y == prevSchema.location.y) {
												//Orientation: --\
												startAngle = 90;
												arcAngle = (int)Math.abs(angle);
											}
											else if(currSchema.location.y < nextSchema.location.y && currSchema.location.y < prevSchema.location.y) {
												//Orientation: /-\
												startAngle = (int)(90 - Math.abs(prevAngle));
												arcAngle = (int)(Math.abs(angle) + Math.abs(prevAngle));
											}
											else if(currSchema.location.y < nextSchema.location.y && currSchema.location.y > prevSchema.location.y){											
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

											path.addArc(currSchema.location.x - radius, currSchema.location.y - radius, 
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
											if(currSchema.location.y > nextSchema.location.y && currSchema.location.y > prevSchema.location.y) {
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

												path.addArc(currSchema.location.x - (int)(AffinityUtils.cos(ang) * (radius+drawRadius)) - drawRadius, 
														currSchema.location.y - (int)(AffinityUtils.sin(ang) * (radius+drawRadius)) - drawRadius, 
														drawRadius*2, drawRadius*2, arcStartAngle, arcAngle);
											}
											else if(currSchema.location.y > nextSchema.location.y && currSchema.location.y < prevSchema.location.y) {
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

												path.addArc(currSchema.location.x + (int)(AffinityUtils.cos(ang) * (radius*2)) - radius, 
														currSchema.location.y - (int)(AffinityUtils.sin(ang) * (radius*2)) - radius, 
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
												path.addArc(currSchema.location.x + (int)(AffinityUtils.cos(ang) * (radius*2)) - radius, 
														currSchema.location.y - (int)(AffinityUtils.sin(ang) * (radius*2)) - radius, 
														radius*2, radius*2, arcStartAngle, arcAngle);										
											}										
										}
									}																												
								}									
								else {										
									//We're at the last schema, which will connect to the previous schema	
									int xChange = currSchema.location.x - prevSchema.location.x;
									int yChange = currSchema.location.y - prevSchema.location.y;
									angle = Math.atan((double)yChange/xChange) * (180/Math.PI) * -1;																

									//path.addArc(currSchema.location.x - radius, currSchema.location.y - radius, 
									//	radius*2, radius*2, (int)angle + 90, 90);									

									/*path.addArc(currSchema.location.x - radius, currSchema.location.y - radius, 
										radius*2, radius*2, (int)(90 + Math.abs(angle)), 
										(int)(180 - Math.abs(angle) - Math.abs(firstAngle)));*/

									//TODO: Double check that this is correct for all cases
									nextSchema = firstSchema;
									prevAngle = angle;
									angle = firstAngle;

									if(debug) {
										System.out.println("At last schema: " + currSchema.schemaID + ", prevSchema: " + prevSchema.schemaID + ", nextSchema: " + nextSchema.schemaID);
										System.out.println("angle: " + angle + ", prevAngle: " + prevAngle);
									}
									
									if(currSchema.location.x == prevSchema.location.x && currSchema.location.x == nextSchema.location.x) {
										//Orientation: |
										//             |
										//             |
										path.close();
									}			
									else {
										int arcStartAngle = 0;
										int arcAngle = 0;
										if(prevSchema == nextSchema) {
											if(currSchema.location.y > nextSchema.location.y) { 
												arcStartAngle = (int)(90 + Math.abs(angle));
											}
											else {
												arcStartAngle = (int)(90 - Math.abs(angle));
											}
											arcAngle = 180;
										}
										else {																	
											if(currSchema.location.y < nextSchema.location.y && currSchema.location.y < prevSchema.location.y) {
												//Orientation: /-\																								
												arcStartAngle = (int)(90 - Math.abs(prevAngle));
												arcAngle = (int)(180 - Math.abs(angle) + Math.abs(prevAngle));
											}
											else if(currSchema.location.y > nextSchema.location.y && currSchema.location.y > prevSchema.location.y) { 												
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
										path.addArc(currSchema.location.x - radius, currSchema.location.y - radius, 
												radius*2, radius*2, arcStartAngle, arcAngle);
									}	
								}
								if(debug)
									System.out.println();
								prevSchema = currSchema;
								currSchema = nextSchema;
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
					if(displayCluster && cluster.getTotalNumSchemas() > 1) {						
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
		this.clusterPathsNeedRefresh = false;

		//Draw the schemas
		if(this.schemaLocationsMap != null) {	
			boolean zoomSchemas = this.zoomSchemas;
			boolean zoomFont = this.zoomFont;
			if(renderingOverview) {
				zoomSchemas = true;
				zoomFont = true;
			}
			
			//if(zoomSchemas) {
			//Draw schemas
			if(!zoomSchemas)
				gc.setTransform(null);
			for(SchemaLocation s : this.schemaLocationsMap.values()) {			
				ISchemaGUI schema = s.schema;
				boolean showLabel = schema.isShowLabel();
				if(!zoomFont)
					//Schema name will be drawn un-zoomed below
					schema.setShowLabel(false);
				if(zoomSchemas) {
					//Draw schema zoomed
					schema.render(gc, this);
					if(!renderingOverview)
						s.bounds = schema.getBounds();
				}
				else {
					//Draw schema un-zoomed
					Point origLocation = schema.getLocation();
					Point translatedLocation = originalPointToTranslatedPoint(s.location.x, s.location.y);
					//boolean showLabel = schema.isShowLabel();
					schema.setLocation(translatedLocation);					
					schema.setShowLabel(false);
					schema.render(gc, this);						
					schema.setLocation(origLocation);	
					if(!renderingOverview) {
						Rectangle bounds = schema.getBounds();
						//s.bounds.x = bounds.x; s.bounds.y = bounds.y;					
						s.bounds.width = (int)(bounds.width/zoom);
						s.bounds.height =(int)(bounds.height/zoom);
						s.bounds.x = origLocation.x - s.bounds.width/2; 
						s.bounds.y = origLocation.y - s.bounds.height/2;
					}
				}				
				schema.setShowLabel(showLabel);					
			}
			//}
			
			if(!zoomFont) {
				//Draw schema names un-zoomed
				gc.setTransform(null);
				//Transform panTransform = createPanTransform();
				//gc.setTransform(panTransform);
				for(SchemaLocation s : this.schemaLocationsMap.values()) {
					ISchemaGUI schema = s.schema;
					Point translatedLocation = originalPointToTranslatedPoint(s.location.x, s.location.y);
					/*if(!zoomSchemas) {
						//Draw schema un-zoomed
						Point origLocation = schema.getLocation();
						boolean showLabel = schema.isShowLabel();
						schema.setLocation(translatedLocation);					
						schema.setShowLabel(false);
						schema.render(gc, this);
						schema.setShowLabel(showLabel);
						schema.setLocation(origLocation);						
						//s.bounds.x = bounds.x; s.bounds.y = bounds.y;
						s.bounds.x = origLocation.x; s.bounds.y = origLocation.y;
						s.bounds.width = (int)(schema.getBounds().width/zoom);
						s.bounds.height =(int)(schema.getBounds().height/zoom);
					}*/
					if(schema.isShowLabel() && !zoomFont) {
						//Draw schema name un-zoomed
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
						if(s.labelBounds == null)
							s.labelBounds = new Rectangle(translatedLocation.x - Math.round(labelSize.x/2.f),translatedLocation.y - Math.round(labelSize.y/2.f),
									labelSize.x, labelSize.y);
						else {
							s.labelBounds.x = translatedLocation.x - Math.round(labelSize.x/2.f);
							s.labelBounds.y = translatedLocation.y - Math.round(labelSize.y/2.f);
							s.labelBounds.width = labelSize.x;
							s.labelBounds.height = labelSize.y;
						}
						//System.out.println("Schema: " + s.getLabel() + ", orig pos: " + s.getLocation() + ", new pos: " + originalPointToTranslatedPoint(s.getLocation().x, s.getLocation().y));						
						gc.drawString(schema.getLabel(), s.labelBounds.x, s.labelBounds.y, true);						
					}
					else if(!renderingOverview) {
						s.labelBounds = null;
					}
				}
				//panTransform.dispose();
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
	
	private double computeSlope(SchemaLocation s1, SchemaLocation s2) {		
		if(s1.location.x == s2.location.x)
			return 0;		
		return (double)((s2.location.y - s1.location.y))/(s2.location.x - s1.location.x);
	}
	
	public static class ClusterLocation {
		public Path contour;
		
		public int contourRadius;
		
		public ClusterGroup cg;
		
		public Integer clusterID;
		
		public double clusterDistance;
		
		public boolean highlighted = false;
		
		/**
		 * Clusters contained within this cluster location
		 */
		public List<ClusterLocation> nestedClusters;				
		public List<ClusterLocation> allNestedClusters;
		private int maxNestingLevel = 0;
		
		private SchemaLocation schemaAtMinX;
		private SchemaLocation schemaAtMaxX;
		private SchemaLocation schemaAtMinY;
		private SchemaLocation schemaAtMaxY;
		
		/**
		 * Schemas contained within this cluster but not in a nested cluster 
		 */
		public NavigableSet<SchemaLocation> leafSchemas;		
		
		/**
		 * Contains both leaf schemas and schemas contained within the nested clusters
		 */
		public NavigableSet<Integer> allSchemas;			
		public NavigableSet<SchemaLocation> allSchemaLocations;		
		
		public ClusterLocation(Integer clusterID, ClusterGroup cg) {
			this.clusterID = clusterID;
			this.cg = cg;
		}
		
		public void addNestedCluster(ClusterLocation nestedCluster) {
			if(this.nestedClusters == null)
				this.nestedClusters = new ArrayList<ClusterLocation>();			
			this.nestedClusters.add(nestedCluster);			
			
			if(this.allNestedClusters == null)
				this.allNestedClusters = new ArrayList<ClusterLocation>();
			
			if(nestedCluster.allNestedClusters != null && !nestedCluster.allNestedClusters.isEmpty()) {
				for(ClusterLocation cluster : nestedCluster.allNestedClusters) {
					if(cluster.getTotalNumSchemas() > 1)
						this.allNestedClusters.add(cluster);
				}
			}
			this.allNestedClusters.add(nestedCluster);	
			
			this.addNestedSchemaLocations(nestedCluster);
			//if(this.maxNestingLevel == 0)
			//	this.maxNestingLevel = 1;
			if(nestedCluster.maxNestingLevel >= this.maxNestingLevel) {			
				this.maxNestingLevel = nestedCluster.maxNestingLevel + 1;
			}
		}
		
		private void addNestedSchemaLocations(ClusterLocation nestedCluster) {
			if(this.allSchemas == null) {
				this.allSchemas = new TreeSet<Integer>();
				this.allSchemaLocations = new TreeSet<SchemaLocation>();
			}
			
			if(nestedCluster.allSchemas != null) {				
				for(SchemaLocation schema : nestedCluster.allSchemaLocations) {
					this.allSchemas.add(schema.schemaID);
					this.allSchemaLocations.add(schema);
					
					if(this.schemaAtMinX == null || schema.location.x < this.schemaAtMinX.location.x)
						this.schemaAtMinX = schema;
					if(this.schemaAtMaxX == null || schema.location.x > this.schemaAtMaxX.location.x)
						this.schemaAtMaxX = schema;
					if(this.schemaAtMinY == null ||schema.location.y < this.schemaAtMinY.location.y)
						this.schemaAtMinY = schema;
					if(this.schemaAtMaxY == null ||schema.location.y > this.schemaAtMaxY.location.y)
						this.schemaAtMaxY = schema;
					
					schema.addContainingCluster(this.clusterID);
				}
			}
		}
		
		public void addLeafSchemaLocation(SchemaLocation schema) {
			if(this.leafSchemas == null) {
				this.leafSchemas = new TreeSet<SchemaLocation>();
			}
			this.leafSchemas.add(schema);
			if(this.allSchemas == null) {
				this.allSchemas = new TreeSet<Integer>();
				this.allSchemaLocations = new TreeSet<SchemaLocation>();
			}
			this.allSchemas.add(schema.schemaID);
			this.allSchemaLocations.add(schema);
			
			if(this.maxNestingLevel == 0)
				this.maxNestingLevel = 1;
			
			if(this.schemaAtMinX == null || schema.location.x < this.schemaAtMinX.location.x)
				this.schemaAtMinX = schema;
			if(this.schemaAtMaxX == null || schema.location.x > this.schemaAtMaxX.location.x)
				this.schemaAtMaxX = schema;
			if(this.schemaAtMinY == null ||schema.location.y < this.schemaAtMinY.location.y)
				this.schemaAtMinY = schema;
			if(this.schemaAtMaxY == null ||schema.location.y > this.schemaAtMaxY.location.y)
				this.schemaAtMaxY = schema;
			
			schema.addContainingCluster(this.clusterID);
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
		}

		public boolean containsCluster(ClusterLocation clusterLocation) {
			if(this.allSchemas == null || clusterLocation.allSchemas == null)
				return false;			
			return this.allSchemas.containsAll(clusterLocation.allSchemas);			
		}
		
		public boolean containsSchema(Integer schema) {
			if(this.allSchemas != null)
				return this.allSchemas.contains(schema);
			return false;
		}		

		public double getClusterDistance() {
			return clusterDistance;
		}

		public void setClusterDistance(double clusterDistance) {
			this.clusterDistance = clusterDistance;
		}

		public int getTotalNumSchemas() {
			return this.allSchemas.size();
		}
		
		public int getNumNestedClusters() {
			if(this.nestedClusters == null)
				return 0;
			return this.nestedClusters.size();
		}
		
		/**
		 * @return the maximum depth of a nested cluster within this cluster
		 */
		public int getMaxNestingLevel() {
			return this.maxNestingLevel-2;
		}
		
		/**
		 * @return the minimum x coordinate for all schema locations in this cluster
		 */
		public int getMinXCoord() {
			//return minXCoord;
			return this.schemaAtMinX.location.x;
		}	
		
		/**
		 * @return the maximum x coordinate for all schema locations in this cluster
		 */
		public int getMaxXCoord() {
			//return maxXCoord;
			return this.schemaAtMaxX.location.x;
		}
		
		/**
		 * @return the minimum y coordinate for all schema locations in this cluster
		 */
		public int getMinYCoord() {
			//return minYCoord;
			return this.schemaAtMinY.location.x;
		}
		
		/**
		 * @return the maximum y coordinate for all schema locations in this cluster
		 */
		public int getMaxYCoord() {
			//return maxYCoord;
			return this.schemaAtMaxY.location.x;
		}		

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			//sb.append("{");
			if(this.nestedClusters != null && !this.nestedClusters.isEmpty()) {
				sb.append("{");
				for(ClusterLocation cluster : this.nestedClusters) {					
					sb.append(cluster.toString());
					sb.append(", ");
				}
				sb.delete(sb.length()-2,sb.length());
				sb.append("}");
			}
			if(this.leafSchemas != null) {
				for(SchemaLocation schema : this.leafSchemas) {
					sb.append(schema.schemaID + ", ");
				}
				sb.delete(sb.length()-2,sb.length());
			}
			
			//sb.append("}");
			return sb.toString();
		}
	}

	public static class SchemaLocation implements Comparable<SchemaLocation> {
		public ISchemaGUI schema;
		public Integer schemaID;
		public Point location;
		public Rectangle bounds = new Rectangle(0, 0, 0, 0);
		public Rectangle labelBounds;
		
		/**
		 * Set of clusters this schema is contained in
		 */
		public Set<Integer> clusters;
		
		public SchemaLocation(ISchemaGUI schema) {
			this.schema = schema;
			this.schemaID = schema.getSchemaID();			
			this.location = schema.getLocation();
			this.clusters = new TreeSet<Integer>();
		}

		public int compareTo(SchemaLocation schemaLocation) {
			if(this.location.x == schemaLocation.location.x)
				return (this.location.y - schemaLocation.location.y);			
			return (this.location.x - schemaLocation.location.x);
		}

		@Override
		public String toString() {
			return schemaID.toString();
		}
		
		/**
		 * @param clusterID - a cluster id
		 * @return whether or not this schema is contained in the cluster with the given id
		 */
		public boolean containedInCluster(Integer clusterID) {
			return this.clusters.contains(clusterID);
		}
		
		public void addContainingCluster(Integer clusterID) {
			this.clusters.add(clusterID);
		}
		
		public void removeContainingCluster(Integer clusterID) {
			this.clusters.remove(clusterID);
		}		
	}
	
	/**
	 * A mouse listener to determine if a schema or cluster was double-clicked.
	 *
	 */
	private class SchemaCluster2DViewEventListener extends MouseAdapter implements MouseMoveListener {
		Point startPoint;
				
		@Override
		public void mouseDown(MouseEvent e) {		
			if(e.button == 1 && mode == Mode.SELECT_MULTIPLE_SCHEMAS) {				
				startPoint = new Point(e.x, e.y);					
			}
		}

		public void mouseMove(MouseEvent e) {			
			if(startPoint != null && (Math.abs(startPoint.x - e.x) > 2 || Math.abs(startPoint.y - e.y) > 2)) {
				//System.out.println("rendering");
				//Create a tracker for selecting items when the mouse is pressed and moved more than 
				//2 pixels in select mode
				startPoint = null;
				unselectAllClusters();
				//#redraw();
				//gc = new GC(SchemaCluster2DView.this);
				//gc.setTransform(SchemaCluster2DView.this.createZoomPanTransform());
				final Tracker tracker = new Tracker(SchemaCluster2DView.this, SWT.RESIZE);
				tracker.setStippled(true);				
				tracker.setRectangles(new Rectangle [] {new Rectangle(e.x, e.y, 1, 1)});
				tracker.addControlListener(new ControlAdapter() {
					public void controlResized(ControlEvent e) {
						Rectangle rect = tracker.getRectangles()[0];
						//Determine if the tracker rectangle interesects any schemas
						for(SchemaLocation schemaLocation : schemaLocationsMap.values()) {
							ISchemaGUI schema = schemaLocation.schema;
							boolean selected = false;
							Rectangle translatedBounds = originalBoundsToTranslatedBounds(schemaLocation.bounds);							
							if(rect.intersects(translatedBounds))								
								selected = true;
							if(selected != schema.isSelected()) {	
								if(selected) {
									selectedSchemas.add(schemaLocation.schema);
									selectedSchemaIDs.add(schema.getSchemaID());
								}
								else {
									selectedSchemas.remove(schemaLocation.schema);
									selectedSchemaIDs.remove(schema.getSchemaID());
								}
								schema.setSelected(selected);								
								//drawSchema(schema, gc);
								if(schemaLocation.labelBounds != null) {
									if(schemaLocation.labelBounds.x < translatedBounds.x)
										translatedBounds.x = schemaLocation.labelBounds.x;									
									if(schemaLocation.labelBounds.y < translatedBounds.y)
										translatedBounds.x = schemaLocation.labelBounds.x;
									if(schemaLocation.labelBounds.width > translatedBounds.width)
										translatedBounds.width = schemaLocation.labelBounds.width;
									if(schemaLocation.labelBounds.height > translatedBounds.height)
										translatedBounds.height = schemaLocation.labelBounds.height;
								}								
								redraw(translatedBounds.x - 10, translatedBounds.y - 10, translatedBounds.width + 20, translatedBounds.height + 20, true);
							}
						}						
						//redraw();
					}					
				});
				tracker.open();
				//Notify any SelectionChangedListeners that the selection of schemas changed
				fireSelectionChangedEvent();
			}			
		}

		@Override		
		public void mouseUp(MouseEvent e) {
			//System.out.println(e.stateMask + ", " + leftButtonCtrlStateMask);
			
			startPoint = null;			

			if(e.count > 2 || SchemaCluster2DView.this.mouseMoved) return;

			//First we need to transpose the mouse coordinates based on the current zoom/pan settings
			Point translatedPoint = canvasPointToTranslatedPoint(e.x, e.y);

			ISchemaGUI clickedSchema = null;
			ClusterLocation clickedCluster = null;

			//Determine if a schema was clicked			
			for(SchemaLocation schema : schemaLocationsMap.values()) {				
				if(schema.bounds.contains(translatedPoint.x, translatedPoint.y)) {
					clickedSchema = schema.schema;	
					break;
				}
			}
			
			if(e.button == 3 || e.stateMask == leftButtonCtrlStateMask) {
				if(selectedSchemas.size() > 1 && (clickedSchema == null || e.button == 3)) {//!(clickedSchema == null && e.stateMask == leftButtonCtrlStateMask)) {					
					//If the right mouse button is clicked anywhere (except on a schema)
					//when multiple schemas are selected, fire a selection clicked event and return					
					fireSelectionClickedEvent(e.button, e.stateMask, e.count, e.x, e.y);					
					return;				
				}				

				if(selectedSchemas.size() <= 1) {
					//#if(mode != Mode.SELECT) {
					//Enable right click on single cluster or schema when the cursor is over that schema or cluster
					GC gc = new GC(SchemaCluster2DView.this);
					if((selectedSchemas.size() == 1 && selectedSchemas.iterator().next().containsPoint(translatedPoint.x, translatedPoint.y)) ||
							(selectedCluster != null && selectedCluster.containsPoint(gc, translatedPoint.x, translatedPoint.y))) {						
						gc.dispose();
						fireSelectionClickedEvent(e.button, e.stateMask, e.count, e.x, e.y);						
						return;
					}
					gc.dispose();				
				}
			}			
			
			//#if(mode != Mode.SELECT) return;

			if(clickedSchema != null && e.button != 3) {
				//A single schema was selected

				//Unselect any previously selected schemas or clusters
				if(e.stateMask != leftButtonCtrlStateMask && e.stateMask != rightButtonCtrlStateMask) {																		
					unselectAllSchemasAndClusters();						
				}
				else if(selectedCluster != null) {
					unselectAllClusters();
				}						

				if(e.stateMask == leftButtonCtrlStateMask && clickedSchema.isSelected()) {
					//Mark the schema as unselected	
					selectedSchemas.remove(clickedSchema);
					selectedSchemaIDs.remove(clickedSchema.getSchemaID());
					clickedSchema.setSelected(false);
				}
				else {						
					//Mark the schema as selected						
					selectedSchemas.add(clickedSchema);
					selectedSchemaIDs.add(clickedSchema.getSchemaID());
					clickedSchema.setSelected(true);
				}

				//TODO: Just redraw the schema
				//GC gc = new GC(Schema2DPlotWithClusters.this);
				//schema.render(gc, Schema2DPlotWithClusters.this);
				//gc.dispose();
				redraw();
				update();

				//Notify any SelectionChangedListeners that a single schema is currently selected
				fireSelectionChangedEvent();						

				//Notify any SelectionClickedListeners that a single schema was double clicked or right-clicked				
				if(e.count == 2 || e.button == 3)// || e.stateMask == leftButtonCtrlStateMask)					
					fireSelectionClickedEvent(e.button, e.stateMask, e.count, e.x, e.y);
			}
			else if(mode != Mode.SELECT_MULTIPLE_SCHEMAS && clickedSchema == null && e.stateMask != leftButtonCtrlStateMask && e.stateMask != rightButtonCtrlStateMask) {
				//If no schema was clicked, determine if a cluster was clicked			
				clickedCluster = getFirstClusterContainsPoint(translatedPoint.x, translatedPoint.y);
				if(clickedCluster != null) {
					if(!clickedCluster.isHighlighted()) {
						//A single cluster was selected						

						//Unselect any previously selected schemas or clusters
						unselectAllSchemasAndClusters();						
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
					//No schema or cluster was clicked, so unhighlight any previously highlighted schemas or clusters					
					if(!selectedSchemas.isEmpty() || selectedCluster != null) {					 
						unselectAllSchemasAndClusters();
						fireSelectionChangedEvent();
						redraw();
						update();
					}					
				}
			}
			else if(e.button != 3 && e.stateMask != leftButtonCtrlStateMask) {					
				//No schema or cluster was clicked, so unhighlight any previously highlighted schemas or clusters					
				if(!selectedSchemas.isEmpty() || selectedCluster != null) {					 
					unselectAllSchemasAndClusters();
					fireSelectionChangedEvent();
					redraw();
					update();
				}					
			}
		}
	}
}
