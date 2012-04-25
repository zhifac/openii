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
import java.util.Map;

import javax.swing.SwingConstants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.mitre.affinity.AffinityConstants;
import org.mitre.affinity.controller.IAffinityController;
import org.mitre.affinity.model.IClusterObjectManager;
import org.mitre.affinity.model.Position;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.view.craigrogram.Cluster2DViewPane.SliderType;
import org.mitre.affinity.view.craigrogram.OvalClusterObjectGUI;
import org.mitre.affinity.view.craigrogram.SWTClusterDistanceSliderPane;
import org.mitre.affinity.view.craigrogram.Cluster2DView;
import org.mitre.affinity.view.craigrogram.Cluster2DViewPane;
import org.mitre.affinity.view.dendrogram.DendrogramCanvas;
import org.mitre.affinity.view.dendrogram.DendrogramClusterObjectGUI;
import org.mitre.affinity.view.event.ClusterDistanceChangeEvent;
import org.mitre.affinity.view.event.SelectionChangedEvent;
import org.mitre.affinity.view.event.SelectionChangedListener;
import org.mitre.affinity.view.event.SelectionClickedListener;

/**
 * A pane with a linked "Craigrogram" and Dendrogram and a cluster distance slider.
 * 
 * @author CBONACETO
 * 
 * @param <K>
 * @param <V>
 */
public class AffinityPane_Old<K extends Comparable<K>, V> extends Composite implements SelectionChangedListener<K> {
	
	/** Pane with the cluster distance slider */
	private SWTClusterDistanceSliderPane<K, V> slider;
	
	/** Pane with the "Craigrogram" (clusters view) */	
	private Cluster2DViewPane<K, V> craigrogram;
	
	/** Pane with the dendrogram */	
	private DendrogramCanvas<K, V> dendrogram;	
	
	/** Pane above Dendrogram that allows user to search for a cluster object **/
	private SearchToolBar<K, V> searchToolBar;
	
	/** The controller */
	private IAffinityController<K, V> controller;
	
	/** The cluster object manager */
	protected IClusterObjectManager<K, V> clusterObjectManager;
	
	/** Mapping of cluster object id to a cached cluster object */
	private Map<K, V> clusterObjectsMap; 
	
	/** Cluster object IDs of the cluster objects to be clustered */
	private Collection<K> objectIDs;
	
	/** Percent of the screen the Craigrogram is using.  Starts out at 50. */
	private int craigrogramPercent = 50;
				
	/**
	 * 
	 * @param parent
	 * @param style
	 * @param affinityModel
	 * @param progressIndicator
	 */
	/*public AffinityPane(final Composite parent, int style, AffinityModel<K, V> affinityModel, 
			ILoadProgressIndicator progressIndicator) {
		this(parent, style, affinityModel, null, progressIndicator);
	}*/
	
	/**
	 * Creates an AffinityPane using the given schemas and the default distance algorithm, dimensionality reduction algorithm,
	 * and clustering algorithm
	 */
	/*public AffinityPane(final Composite parent, int style, AffinityModel<K, V> affinityModel,
			ArrayList<K> objectIDs, ILoadProgressIndicator progressIndicator) {		
		this(parent, style, affinityModel, schemaIDs, new JaccardDistanceFunction(),  
				new PrefuseForceDirectedMDS<K>(), new HierarchicalClusterer<K>(), progressIndicator);
	}*/	
	
	/**
	 * Create a blank AffinityPane.
	 * 
	 * @param parent
	 * @param style
	 * @param searchHint
	 * @param showingSchemas
	 */
	public AffinityPane_Old(final Composite parent, int style, IClusterObjectManager<K, V> clusterObjectManager, String searchHint) {
		super(parent, style);
		this.clusterObjectManager = clusterObjectManager;
		createAffinityPane(searchHint);
	}	
	
	/**
	 * Creates an AffinityPane using the given model, dimensionality reduction algorithm,
	 * and clustering algorithm
	 */
	/*public AffinityPane(final Composite parent, int style, AffinityModel<K, V> affinityModel, ArrayList<K> objectIDs, 
			DistanceFunction<K, V> distanceFunction, IMultiDimScaler<K> mds, Clusterer<K> clusterer, 
			ILoadProgressIndicator progressIndicator, String searchHint, boolean showingSchemas) {
		super(parent, style);
		this.showingSchemas = showingSchemas;
		affinityPaneCreated = false;
		try {		
			//Get the cluster objects
			if(objectIDs == null) { 
				this.objectIDs = affinityModel.getClusterObjectManager().getClusterObjectIDs();
			} else { 
				this.objectIDs = objectIDs;
			}
			
			//Check to see how many cluster objects are being visualized
			if(this.objectIDs == null || this.objectIDs.size() == 0) {
				throw new AffinityException("List of cluster object IDs was empty");
			} else { 
				//1 or more cluster objects, so run Affinity normally
				List<V> clusterObjects = new ArrayList<V>();
				int i = 1;
				int numClusterObjects = this.objectIDs.size();			
				int percentProgressPerClusterObject = 90/numClusterObjects;
				for(K objectID : objectIDs) {			
					V clusterObject = affinityModel.getClusterObjectManager().getClusterObject(objectID);
					if(clusterObject == null) {
						System.err.println("Warning: Cluster object " + objectID + " not found");
						if(progressIndicator != null)
							progressIndicator.setErrorText("Error: cluster object " + objectID + " not found");
					}
					else {
						clusterObjects.add(clusterObject);
					}
					if(progressIndicator != null) {
						progressIndicator.setProgressText("Loading Cluster Objects (" + i + "/" + numClusterObjects + ")");
						progressIndicator.setPercentComplete(i * percentProgressPerClusterObject);
					}
					i++;
				}
				
				//Run the clusterer to generate clusters and the distance grid
				if(progressIndicator != null) {
					progressIndicator.setProgressText("Computing clusters");
				}
				affinityModel.generateClusters(objectIDs, distanceFunction, clusterer);
				if(progressIndicator != null) {
					progressIndicator.setPercentComplete(95);
				}

				//Run the MDS algorithm to generate 2D points
				if(progressIndicator != null) {
					progressIndicator.setProgressText("Computing layout");
				}

				if(mds == null || mds instanceof PrefuseForceDirectedMDS) {
					if(mds == null)
						mds = new PrefuseForceDirectedMDS<K>();					
						if(rescale){
							affinityModel.getClusters().getDistanceGrid().rescale(0, 1000);			
						}												
						mds.setObjectIDs(this.objectIDs);
				}		
				PositionGrid<K> pg = mds.scaleDimensions(affinityModel.getClusters().getDistanceGrid(), true, false, 2, false);				
				
				if(progressIndicator != null) {
					progressIndicator.setPercentComplete(100);
				}

				//Create the Affinity GUI			
				if(progressIndicator != null) {
					progressIndicator.setProgressText("Opening Affinity");
				}
				createAffinityPane(objectIDs, clusterObjects, affinityModel.getClusters(), pg, 
						affinityModel, searchHint);
				affinityPaneCreated = true;					
			}
		} catch(Exception ex) {
			//There was an error creating the affinity pane
			affinityPaneCreated = false;			
			ex.printStackTrace();			
			this.exception = ex;
			if(ex.getMessage() != null && progressIndicator != null) 
				progressIndicator.setErrorText(ex.getMessage());
			if(progressIndicator != null) {
				progressIndicator.setProgressText("Could not launch Affinity due to errors!");
			}
		}
	}*/
	
	/**
	 * Creates an AffinityPane using the given cluster objects, clusters, and position grid.
	 */
	public AffinityPane_Old(final Composite parent, int style, IClusterObjectManager<K, V> clusterObjectManager,
			ArrayList<K> objectIDs, List<V> clusterObjects, ClustersContainer<K> clusters, 
			PositionGrid<K> pg, String searchHint) {
		super(parent, style);
		this.clusterObjectManager = clusterObjectManager;
		this.objectIDs = objectIDs;
		createAffinityPane(searchHint);	
		setClusterObjectsAndClusters(objectIDs, clusterObjects, clusters, pg);
	}
	
	public void setController(IAffinityController<K, V> controller) {
		this.controller = controller;
		searchToolBar.setController(controller);
	}

	/**
	 * @param objectIDs
	 * @param clusterObjects
	 * @param clusters
	 * @param pg
	 */
	public void setClusterObjectsAndClusters(ArrayList<K> objectIDs, Collection<V> clusterObjects, 
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
		slider.setClusters(clusters);
		dendrogram.setClusterObjectsAndClusters(dendrogramClusterObjectGUIs, clusters);
		dendrogram.redraw();
		craigrogram.setClusterObjectsAndClusters(craigrogramClusterObjectGUIs, clusters, pg);		
		//craigrogram.fitInWindow();
		//craigrogram.redraw();
	}	
	
	public void setSearchHint(String searchHint) {
		searchToolBar.setSearchHint(searchHint);
	}
	
	public void addSelectionChangedListener(SelectionChangedListener<K> listener) {
		craigrogram.getClusterObject2DPlot().addSelectionChangedListener(listener);
		dendrogram.addSelectionChangedListener(listener);
	}
	
	public void removeSelectionChangedListener(SelectionChangedListener<K> listener) {
		craigrogram.getClusterObject2DPlot().removeSelectionChangedListener(listener);
		dendrogram.removeSelectionChangedListener(listener);
	}
	
	public void addSelectionClickedListener(SelectionClickedListener<K> listener) {
		craigrogram.getClusterObject2DPlot().addSelectionClickedListener(listener);
		dendrogram.addSelectionClickedListener(listener);
	}
	
	public void removeSelectionClickedListener(SelectionClickedListener<K> listener) {
		craigrogram.getClusterObject2DPlot().removeSelectionClickedListener(listener);
		dendrogram.removeSelectionClickedListener(listener);
	}	
	
	public Collection<K> getObjectIDs() {
		return objectIDs;
	}
	
	public V getClusterObject(K objectID) {
		return clusterObjectsMap.get(objectID);
	}
	
	public Map<K, V> getClusterObjects() {
		return clusterObjectsMap;
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
	
	public void addClusterDistanceTickLocation(double distance, boolean highlighted) {
		if(slider != null) {
			slider.getClusterDistanceSliderPane().addClusterDistanceTickLocation(distance, highlighted);
		}
	}
	
	public void clearAdditionalClusterDistanceTickLocations() {
		if(slider != null) {
			slider.getClusterDistanceSliderPane().clearAdditionalClusterDistanceTickLocations();
		}
	}
	
	public void setClusterDistances(double minDistance, double maxDistance) {
		slider.getClusterDistanceSliderPane().setClusterDistances(minDistance, maxDistance);
		ClusterDistanceChangeEvent ev = new ClusterDistanceChangeEvent(slider, minDistance, maxDistance);
		dendrogram.clusterDistanceChanged(ev);
		craigrogram.clusterDistanceChanged(ev);		
	}
	
	//SelectionChangedListner method to highlight a cluster object/cluster in the dendrogram when highlighted
	//in the craigrogram and vice-versa.
	public void selectionChanged(SelectionChangedEvent<K> event) {	
		if(event.getSource() == this.craigrogram.getClusterObject2DPlot()) {
			//Selection changed in the craigrogram, so update selection in the dendrogram
			dendrogram.setSelectedClusterObjects(event.selectedClusterObjects);
			if(event.selectedClusters != null && event.selectedClusters.size() == 1) {
				ClusterGroup<K> cluster = event.selectedClusters.iterator().next();
				dendrogram.setSelectedCluster(cluster);
			}
			else {
				dendrogram.setSelectedCluster(null);
			}
			dendrogram.redraw();
		} else if(event.getSource() == this.dendrogram){
			//Selection changed in the dendrogram, so update selection in the craigrogram
			craigrogram.setSelectedClusterObjects(event.selectedClusterObjects);
			if(event.selectedClusters != null && event.selectedClusters.size() == 1) {
				ClusterGroup<K> cluster = event.selectedClusters.iterator().next();
				craigrogram.setSelectedCluster(cluster);
			}
			else {
				craigrogram.setSelectedCluster(null);
			}
			craigrogram.redraw();
		}	
	}
	
	protected void createAffinityPane(String searchHint) {
		//setLayout(new FillLayout());		
		/*final Decorations decoration = new Decorations(this, SWT.NONE);
		GridLayout gl= new GridLayout(1, true);
		gl.marginHeight = 1;	
		gl.marginTop = 1;
		gl.marginBottom = 1;
		decoration.setLayout(gl);*/
		
		//Create the menu
		/*int clusterObjectDiameter = 10;
		int radiusIncrement = 5;
		createMenu(decoration, this, radiusIncrement);*/
		
		GridLayout gl= new GridLayout(1, true);
		gl.marginHeight = 1;	
		gl.marginTop = 1;
		gl.marginBottom = 1;
		setLayout(gl);
		
		//Create pane with the cluster distance slider
		Composite sliderPane = new Composite(this, SWT.NONE);
		sliderPane.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		sliderPane.setLayout(new FillLayout());
		this.slider = new SWTClusterDistanceSliderPane<K, V>(sliderPane, SWT.NONE, null, SwingConstants.HORIZONTAL);		
		
		// Create sash that the Craigrogram and Dendrogram will be attached to		
		final Composite pane = new Composite(this, SWT.NONE);
		pane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		pane.setLayout(new FormLayout());
		final Sash sash = new Sash(pane, SWT.VERTICAL);
		this.craigrogramPercent = 50;
		Color white = getDisplay().getSystemColor(SWT.COLOR_WHITE);
		sash.setBackground(white);		
		final FormData sashData = new FormData ();
		sashData.left = new FormAttachment (craigrogramPercent, 0);
		sashData.top = new FormAttachment (0, 0);
		sashData.bottom = new FormAttachment (100, 0);
		sash.setLayoutData(sashData);		
		
		//Create the Craigrogram pane		
		/*Color black = display.getSystemColor(SWT.COLOR_BLACK);
		Color mediumGray = new Color(display, 100, 100, 100);
		Color lightGray = new Color(display, 150, 150, 150);*/
		
		/*if(showingSchemas) {
			this.schemaIDs = (ArrayList<Integer>)objectIDs;
			this.schemasForSavingStats = (List<Schema>)clusterObjects;
			this.affinityModelForSavingStats = (AffinitySchemaModel)affinityModel;
		}*/
		
		/*final List<IClusterObjectGUI<K, V>> craigrogramClusterObjectGUIs = new ArrayList<IClusterObjectGUI<K, V>>();
		final List<DendrogramClusterObjectGUI<K, V>> dendrogramClusterObjectGUIs = new ArrayList<DendrogramClusterObjectGUI<K, V>>();
		clusterObjectsMap = new HashMap<K, V>();
		if(clusterObjects != null && !clusterObjects.isEmpty()) {
			int i = 0;
			for(V clusterObject : clusterObjects) {
				K objectID = objectIDs.get(i);
				String objectName = affinityModel.getClusterObjectManager().getClusterObjectName(objectID);
				clusterObjectsMap.put(objectID, clusterObject);
				Position pos = pg.getPosition(objectID);
				if(pos == null) {
					System.err.println("Error: position for cluster object " + objectName + " is null");
				}
				OvalClusterObjectGUI<K, V> clusterObjectGUI = new OvalClusterObjectGUI<K, V>(getShell(), objectID, 
						clusterObjectDiameter, clusterObjectDiameter);			
				clusterObjectGUI.setLocation((int)pos.getDimensionValue(0), (int)pos.getDimensionValue(1));
				clusterObjectGUI.setLabel(objectName);
				clusterObjectGUI.setLabelColor(black);
				clusterObjectGUI.setForeground(lightGray);
				clusterObjectGUI.setBackground(lightGray);
				clusterObjectGUI.setSelectedLineColor(mediumGray);
				clusterObjectGUI.setShowLabel(true);
				clusterObjectGUI.setToolTipText(objectName);		
				craigrogramClusterObjectGUIs.add(clusterObjectGUI);

				DendrogramClusterObjectGUI<K, V> dendrogramClusterObjectGUI = new DendrogramClusterObjectGUI<K, V>(objectID, clusterObject);
				dendrogramClusterObjectGUI.setLabel(objectName);
				dendrogramClusterObjectGUIs.add(dendrogramClusterObjectGUI);
				i++;
			}
		}				
		this.craigrogram = new Cluster2DViewPane<K, V>(pane, SWT.BORDER, true, SliderType.NONE, 0, 1000,
				craigrogramClusterObjectGUIs, cc, pg);*/	
		craigrogram = new Cluster2DViewPane<K, V>(pane, SWT.BORDER, true, SliderType.NONE, 0, 1000);
		craigrogram.setLockAspectRatio(true);			
		craigrogram.getClusterObject2DPlot().setShowClusters(true);
		craigrogram.getClusterObject2DPlot().setStartRadius(AffinityConstants.CLUSTER_OBJECT_DIAMETER/2 + 
				AffinityConstants.CLUSTER_RADIUS_INCREMENT);		
		craigrogram.getClusterObject2DPlot().setRadiusIncrement(AffinityConstants.CLUSTER_RADIUS_INCREMENT);
		craigrogram.getClusterObject2DPlot().setBackground(sash.getBackground());
		craigrogram.getClusterObject2DPlot().setFillClusters(true);
		FormData pane1Data = new FormData ();
		pane1Data.left = new FormAttachment (0, 0);
		pane1Data.right = new FormAttachment (sash, 0);
		pane1Data.top = new FormAttachment (0, 0);
		pane1Data.bottom = new FormAttachment (100, 0);
		craigrogram.setLayoutData(pane1Data);
					
		//Create search + dendrogram combo pane
		final Composite searchPlusDendrogramPane = new Composite(pane, SWT.BORDER);	
		FormLayout fl1 = new FormLayout();
		searchPlusDendrogramPane.setLayout(fl1);
		this.searchToolBar = new SearchToolBar<K, V>(searchPlusDendrogramPane,  SWT.NONE, controller, searchHint);
		//this.searchToolBar = new SearchToolBar<K, V>(searchPlusDendrogramPane,  SWT.NONE, this, 
		//		clusterObjectManager, searchHint);
		
		//Create the Dendrogram pane				
		final Composite dendrogramPane = new Composite(searchPlusDendrogramPane, SWT.BORDER);
		dendrogramPane.setLayout(new FillLayout());
		FormData fd1 = new FormData();
		fd1.top = new FormAttachment(searchToolBar, 0);
		fd1.left = new FormAttachment (sash, 0);
		fd1.right = new FormAttachment (100, 0);
		fd1.bottom = new FormAttachment (100, 0);		
		dendrogramPane.setLayoutData(fd1);		
		dendrogramPane.setBackground(white);
		/*this.dendrogram = new DendrogramCanvas<K, V>(dendrogramPane, SWT.DOUBLE_BUFFERED, cc,  
		dendrogramClusterObjectGUIs);*/
		dendrogram = new DendrogramCanvas<K, V>(dendrogramPane, SWT.DOUBLE_BUFFERED);		
		dendrogram.setFont(AffinityConstants.getFont(AffinityConstants.NORMAL_FONT));
		dendrogram.setSelectedFont(AffinityConstants.getFont(AffinityConstants.NORMAL_BOLD_FONT));
		dendrogram.setBackground(dendrogramPane.getBackground());
		
		//right side of sash 
		FormData pane2Data = new FormData ();
		pane2Data.left = new FormAttachment (sash, 0);
		pane2Data.right = new FormAttachment (100, 0);
		pane2Data.top = new FormAttachment (0, 0);
		pane2Data.bottom = new FormAttachment (100, 0);		
		searchPlusDendrogramPane.setLayoutData(pane2Data);
				
		slider.getClusterDistanceSliderPane().addClusterDistanceChangeListener(dendrogram);
		slider.getClusterDistanceSliderPane().addClusterDistanceChangeListener(craigrogram);
		
		// Add the listener to allow resizing of the craigrogram and dendrogram	
		sash.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				int limit = 20;
				Rectangle sashRect = sash.getBounds ();
				Rectangle shellRect = getClientArea ();
				int right = shellRect.width - sashRect.width - limit;
				e.x = Math.max (Math.min (e.x, right), limit);
				if (e.x != sashRect.x)  {
					sashData.left = new FormAttachment (0, e.x);
					sash.getParent().layout();
				}
				
				//TODO: Resizing introduces error in the position grid, think about maintaining the original
				//aspect ratio or recomputing the position grid
				craigrogram.resize();
				//System.out.println("Correlation: " + MDSTester.compute2DFitCorrelation(schemaIDs, cc.getDistanceGrid(), craigrogram.getPositionGrid()));
			}
		});	
		
		//Add the resize listeners
		addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent event) {}			
			public void controlResized(ControlEvent event) {
				int craigrogramWidth = craigrogram.getSize().x;
				if(craigrogramWidth > 0)
					craigrogramPercent = (int)((float)craigrogramWidth/(craigrogramWidth + dendrogram.getSize().x) * 100);
				sashData.left = new FormAttachment (craigrogramPercent, 0);				
				layout();				
				craigrogram.resize();
			}
		});
		
		//Add listener to highlight cluster/schema in dendrogram when highlighted in craigrogram and vice-versa
		addSelectionChangedListener(this);
	}	

	public void resize() {
		craigrogram.resize();
	}
	
	/*protected void createMenu(final Decorations decoration, final Composite parent, int radiusIncrement) {		
		//Create menus
		Menu menuBar = new Menu(decoration, SWT.BAR);
		decoration.setMenuBar(menuBar);	
		
		//Create the File menu
		MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		fileItem.setText("&File");		
		Menu fileMenu = new Menu(decoration, SWT.DROP_DOWN);
		fileItem.setMenu(fileMenu);		
		
		if(showingSchemas) {
			MenuItem saveStatsItem = new MenuItem(fileMenu, SWT.NONE);
			saveStatsItem.setText("Save Statistics");
			saveStatsItem.addSelectionListener(new SelectionListener(){
				public void widgetDefaultSelected(SelectionEvent arg0) {}
				public void widgetSelected(SelectionEvent arg0) {
					//create a dialogue to save file
					FileDialog fileDlg = new FileDialog(parent.getShell(), SWT.SAVE);
					fileDlg.setText("Save Stats");
					fileDlg.setFilterPath("C:/");
					fileDlg.setFilterExtensions(new String[]{"*.xls"});
					fileDlg.setFileName("AffinityStatistics.xls");
					fileDlg.setOverwrite(true);
					String selectedFile = fileDlg.open();
					writeToSpreadsheet(selectedFile);
				}			
			});
		}
		
		MenuItem exitMenuItem = new MenuItem(fileMenu, SWT.NONE);		
		exitMenuItem.setText("Exit");
		exitMenuItem.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {}
			public void widgetSelected(SelectionEvent event) {			
				parent.getShell().dispose();
			}
		});
		
		// Create the View menu
		MenuItem viewItem = new MenuItem(menuBar, SWT.CASCADE);
		viewItem.setText("View");		
		Menu viewMenu = new Menu(decoration, SWT.DROP_DOWN);
		viewItem.setMenu(viewMenu);
		
		//Create the Craigrogram options menu
		MenuItem craigrogramMenuItem = new MenuItem(viewMenu, SWT.CASCADE);		
		craigrogramMenuItem.setText("Craig-Ro-Gram Options");		
		Menu craigrogramMenu = new Menu(decoration, SWT.DROP_DOWN);
		craigrogramMenuItem.setMenu(craigrogramMenu);
		
		//Create text size sub-menu
		MenuItem textSizeMenuItem = new MenuItem(craigrogramMenu, SWT.CASCADE);		
		textSizeMenuItem.setText("Text Size");	
		final Menu textSizeMenu = new Menu(decoration, SWT.DROP_DOWN);		
		textSizeMenuItem.setMenu(textSizeMenu);		
		final MenuItem smallTextItem = new MenuItem(textSizeMenu, SWT.RADIO);
		smallTextItem.setText("Small");
		final MenuItem normalTextItem = new MenuItem(textSizeMenu, SWT.RADIO);
		normalTextItem.setText("Normal");
		normalTextItem.setSelection(true);
		final MenuItem largeTextItem = new MenuItem(textSizeMenu, SWT.RADIO);
		largeTextItem.setText("Large");
		final MenuItem hideTextItem = new MenuItem(textSizeMenu, SWT.RADIO);
		hideTextItem.setText("Hide");
		SelectionAdapter textSizeListener = new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent e) {
				MenuItem item = (MenuItem)e.widget;
				if(item.getText().startsWith("Small")){ 
					craigrogram.getClusterObject2DPlot().setClusterObjectNamesVisible(true);
					craigrogram.setTextSize(TextSize.Small);
				}else if(item.getText().startsWith("Normal")){
					craigrogram.getClusterObject2DPlot().setClusterObjectNamesVisible(true);
					craigrogram.setTextSize(TextSize.Normal);
				}else if(item.getText().startsWith("Large")){
					craigrogram.getClusterObject2DPlot().setClusterObjectNamesVisible(true);
					craigrogram.setTextSize(TextSize.Large);
				}else{
					//hide text
					craigrogram.getClusterObject2DPlot().setClusterObjectNamesVisible(false);
				}
			}
		};
		smallTextItem.addSelectionListener(textSizeListener);
		normalTextItem.addSelectionListener(textSizeListener);
		largeTextItem.addSelectionListener(textSizeListener);
		hideTextItem.addSelectionListener(textSizeListener);
		textSizeMenu.setDefaultItem(normalTextItem);
		textSizeMenu.addMenuListener(new MenuAdapter() {
			public void menuShown(MenuEvent e) {
				smallTextItem.setSelection(false); 
				normalTextItem.setSelection(false); 
				largeTextItem.setSelection(false);			
				hideTextItem.setSelection(false); 
				if(craigrogram.getClusterObject2DPlot().isClusterObjectNamesVisible()){
					switch(craigrogram.getTextSize()) {
						case Small: smallTextItem.setSelection(true); break;
						case Normal: normalTextItem.setSelection(true); break;
						case Large: largeTextItem.setSelection(true); break;
					}
				}else{
					hideTextItem.setSelection(true); 
				}
			}
		});		
		
		//Create zoom sub-menu
		MenuItem zoomMenuItem = new MenuItem(craigrogramMenu, SWT.CASCADE);		
		zoomMenuItem.setText("Zoom");	
		final Menu zoomMenu = new Menu(decoration, SWT.DROP_DOWN);		
		zoomMenuItem.setMenu(zoomMenu);	
		final MenuItem[] zoomLevelItems = new MenuItem[AffinityConstants.ZOOM_LEVELS.length];		
		SelectionAdapter zoomLevelListener = new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent e) {				
				MenuItem item = (MenuItem)e.widget;
				int level = (Integer)item.getData();
				if(level == -1) {
					//Fit in window was selected
					craigrogram.fitInWindow();
				}
				else {
					//Set zoom level to selected level
					craigrogram.setZoomPercent(AffinityConstants.ZOOM_LEVELS[level]);
				}
			}
		};
		int i = 0;
		for(int zoomLevel : AffinityConstants.ZOOM_LEVELS) {
			MenuItem zoomLevelItem = new MenuItem(zoomMenu, SWT.RADIO);
			zoomLevelItem.setText(Integer.toString(zoomLevel) + "%");
			zoomLevelItem.setData(i);
			zoomLevelItem.addSelectionListener(zoomLevelListener);
			zoomLevelItems[i] = zoomLevelItem;
			i++;
		}
		final MenuItem fitInWindowItem = new MenuItem(zoomMenu, SWT.NONE);
		fitInWindowItem.setText("Fit In Window");
		fitInWindowItem.setData(-1);
		fitInWindowItem.addSelectionListener(zoomLevelListener);
		zoomMenu.addMenuListener(new MenuAdapter() {
			public void menuShown(MenuEvent e) {
				float zoomLevel = craigrogram.getZoomPercent();
				for(int i=0; i<zoomLevelItems.length; i++) {
					if(AffinityConstants.ZOOM_LEVELS[i] == zoomLevel)
						zoomLevelItems[i].setSelection(true);
					else 
						zoomLevelItems[i].setSelection(false);
				}	
			}
		});
		
		//Create show tool bar check box
		final MenuItem showToolBarItem = new MenuItem(craigrogramMenu, SWT.CHECK);
		showToolBarItem.setText("Show Tool Bar");
		showToolBarItem.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent event) {			
				boolean selected = ((MenuItem)event.getSource()).getSelection();				
				craigrogram.setShowToolBar(selected);
				craigrogram.redraw();
			}
		});		
		showToolBarItem.setSelection(true);		
		
		//Create show overview check box
		final MenuItem showOverviewItem = new MenuItem(craigrogramMenu, SWT.CHECK);
		showOverviewItem.setText("Show Overview");
		showOverviewItem.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent event) {			
				boolean selected = ((MenuItem)event.getSource()).getSelection();				
				craigrogram.getClusterObject2DPlot().setShowOverview(selected);
				craigrogram.redraw();
			}
		});		
		showOverviewItem.setSelection(true);	
		
		//Create show cluster object names check box
		final MenuItem showTitlesItem = new MenuItem(craigrogramMenu, SWT.CHECK);
		showTitlesItem.setText("Show Object Names");
		showTitlesItem.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent event) {			
				boolean selected = ((MenuItem)event.getSource()).getSelection();				
				craigrogram.getClusterObject2DPlot().setClusterObjectNamesVisible(selected);
			
				List<IClusterObjectGUI<K, V>> clusterObjects = craigrogram.getClusterObject2DPlot().getClusterObjects();
				for(IClusterObjectGUI<K, V> c : clusterObjects) {
					c.setShowLabel(selected);			
				}		
				craigrogram.redraw();
			}
		});		
		showTitlesItem.setSelection(true);	
			
		//Create show clusters check box
		final MenuItem showClustersItem = new MenuItem(craigrogramMenu, SWT.CHECK);
		showClustersItem.setText("Show Clusters");
		showClustersItem.addSelectionListener(new SelectionAdapter() {		
			public void widgetSelected(SelectionEvent event) {			
				boolean selected = ((MenuItem)event.getSource()).getSelection();							
				craigrogram.getClusterObject2DPlot().setShowClusters(selected);
				craigrogram.redraw();
			}
		});		
		showClustersItem.setSelection(true);
		
		//Create fill clusters check box
		final MenuItem fillClustersItem = new MenuItem(craigrogramMenu, SWT.CHECK);
		fillClustersItem.setText("Fill Clusters");
		fillClustersItem.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent event) {			
				boolean selected = ((MenuItem)event.getSource()).getSelection();				
				craigrogram.getClusterObject2DPlot().setFillClusters(selected);
				craigrogram.redraw();
			}
		});		
		fillClustersItem.setSelection(true);		
		
		//Create follow concave paths check box
		final MenuItem followConcavePathsItem = new MenuItem(craigrogramMenu, SWT.CHECK);
		followConcavePathsItem.setText("Follow Concave Paths");
		followConcavePathsItem.addSelectionListener(new SelectionAdapter() {		
			public void widgetSelected(SelectionEvent event) {			
				boolean selected = ((MenuItem)event.getSource()).getSelection();				
				craigrogram.getClusterObject2DPlot().setUseConcavitySkipLocic(!selected);
				craigrogram.getClusterObject2DPlot().setUseClusterObjectSkipLogic(!selected);
				craigrogram.redraw();
			}
		});		
		followConcavePathsItem.setSelection(false);	
		
		final ClusterRadiusDlg radiusDlg = new ClusterRadiusDlg(parent.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		radiusDlg.setRadius(radiusIncrement);
		MenuItem clusterSizeItem = new MenuItem(craigrogramMenu, SWT.NONE);
		clusterSizeItem.setText("Set Cluster Spacing");
		clusterSizeItem.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent event) {
				if(!radiusDlg.isVisible()) {					
					ClusterRadiusDlg.DialogModel model = (ClusterRadiusDlg.DialogModel)radiusDlg.setVisible(true);
					if(model.buttonPushed == ClusterRadiusDlg.DialogModel.ButtonType.OK) {
						craigrogram.getClusterObject2DPlot().setRadiusIncrement(model.radius);						
					}					
					craigrogram.getClusterObject2DPlot().redraw();
				}				
			}
		});
		
		craigrogramMenu.addMenuListener(new MenuAdapter() {
			public void menuShown(MenuEvent e) {
				//lockAspectRatioItem.setSelection(craigrogram.isLockAspectRatio());
				showToolBarItem.setSelection(craigrogram.isShowToolBar());
				showOverviewItem.setSelection(craigrogram.getClusterObject2DPlot().isShowOverview());
				//showTitlesItem.setSelection(true);
				showClustersItem.setSelection(craigrogram.getClusterObject2DPlot().isShowClusters());
				fillClustersItem.setSelection(craigrogram.getClusterObject2DPlot().isFillClusters());
				followConcavePathsItem.setSelection(!craigrogram.getClusterObject2DPlot().isUseConcavitySkipLocic());
			}			
		});
		
		//Create the dendrogram options menu
		MenuItem dendrogramMenuItem = new MenuItem(viewMenu, SWT.CASCADE);		
		dendrogramMenuItem.setText("Dendrogram Options");		
		Menu dendrogramMenu = new Menu(decoration, SWT.DROP_DOWN);
		dendrogramMenuItem.setMenu(dendrogramMenu);
		
		MenuItem showAltColorsItem = new MenuItem(dendrogramMenu, SWT.CHECK);
		showAltColorsItem.setText("Show Alternating Colors");
		showAltColorsItem.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent event) {			
				boolean selected = ((MenuItem)event.getSource()).getSelection();				
				dendrogram.setShowClusters(selected);
				dendrogram.redraw();
			}
		});		
		showAltColorsItem.setSelection(true);
		
		MenuItem shadeBackgroundsItem = new MenuItem(dendrogramMenu, SWT.CHECK);
		shadeBackgroundsItem.setText("Shade Backgrounds");
		shadeBackgroundsItem.addSelectionListener(new SelectionAdapter() {		
			public void widgetSelected(SelectionEvent event) {			
				boolean selected = ((MenuItem)event.getSource()).getSelection();				
				dendrogram.setFillClusters(selected);
				dendrogram.redraw();
			}
		});		
		shadeBackgroundsItem.setSelection(true);	
	}*/	
}