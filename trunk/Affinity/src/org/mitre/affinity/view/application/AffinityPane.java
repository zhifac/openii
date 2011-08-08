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

package org.mitre.affinity.view.application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingConstants;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Sash;
import org.mitre.affinity.AffinityConstants;
import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.clusters.ClustersContainer;
import org.mitre.affinity.clusters.DistanceGrid;
import org.mitre.affinity.clusters.SchemaPairValue;
import org.mitre.affinity.clusters.SchemaPairValues;
import org.mitre.affinity.clusters.clusterers.Clusterer;
import org.mitre.affinity.clusters.clusterers.HierarchicalClusterer;
import org.mitre.affinity.clusters.distanceFunctions.DistanceFunction;
import org.mitre.affinity.clusters.distanceFunctions.JaccardDistanceFunction;
import org.mitre.affinity.dimensionality_reducers.IMultiDimScaler;
import org.mitre.affinity.dimensionality_reducers.PrefuseForceDirectedMDS;
import org.mitre.affinity.model.AffinityModel;
import org.mitre.affinity.model.Position;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.SchemaDendrogram;
import org.mitre.affinity.util.SWTUtils;
import org.mitre.affinity.util.SWTUtils.TextSize;
import org.mitre.affinity.view.craigrogram.ClusterRadiusDlg;
import org.mitre.affinity.view.craigrogram.ISchemaGUI;
import org.mitre.affinity.view.craigrogram.OvalSchemaGUI;
import org.mitre.affinity.view.craigrogram.SWTClusterDistanceSliderPane;
import org.mitre.affinity.view.craigrogram.SchemaCluster2DView;
import org.mitre.affinity.view.craigrogram.SchemaCluster2DViewPane;
import org.mitre.affinity.view.craigrogram.SchemaCluster2DViewPane.SliderType;
import org.mitre.affinity.view.dendrogram.DendrogramCanvas;
import org.mitre.affinity.view.event.AffinityException;
import org.mitre.affinity.view.event.SelectionChangedEvent;
import org.mitre.affinity.view.event.SelectionChangedListener;
import org.mitre.affinity.view.event.SelectionClickedListener;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;


/**
 * A pane with a linked "Craigrogram" and Dendrogram, and a cluster distance slider.
 * 
 * @author CBONACETO
 *
 */
public class AffinityPane extends Composite implements SelectionChangedListener { 
	/** Pane with the cluster distance slider */
	private SWTClusterDistanceSliderPane slider;
	
	/** Pane with the "Craigrogram" */	
	private SchemaCluster2DViewPane craigrogram;
	
	/** Pane with the dendrogram */	
	private DendrogramCanvas dendrogram;	
	
	/** Pane above Dendrogram that allows user to search for a schema **/
	private Composite searchToolBar;
	
	/** Mapping of schema id to a cached schema */
	private Map<Integer, Schema> schemasMap; 
	
	/** Schema ids of the schemas to be clustered */
	private ArrayList<Integer> schemaIDs;
	
	/** Percent of the screen the Craigrogram is using.  Starts out at 50. */
	private int craigrogramPercent = 50;
	
	/** Whether or not the affinity pane was created successfully */
	private boolean affinityPaneCreated;	
	
	private boolean rescale = true;
	
	/** Contains the caught exception when there is an error creating the Craigrogram or dendrogram */
	private Exception exception;
	
	private List<Schema> schemasForSavingStats;
	private AffinityModel affinityModelForSavingStats;

	
	public AffinityPane(final Composite parent, int style, AffinityModel affinityModel, ILoadProgressIndicator progressIndicator) {
		this(parent, style, affinityModel, null, progressIndicator);
	}
	
	/**
	 * Creates an AffinityPane using the given schemas and the default distance algorithm, dimensionality reduction algorithm,
	 * and clustering algorithm
	 */
	public AffinityPane(final Composite parent, int style, AffinityModel affinityModel, ArrayList<Integer> schemaIDs, ILoadProgressIndicator progressIndicator) {
		this(parent, style, affinityModel, schemaIDs, new JaccardDistanceFunction(),  new PrefuseForceDirectedMDS(), new HierarchicalClusterer(), progressIndicator);
	}	
	
	
	/**
	 * Creates an AffinityPane using the given schemas, dimensionality reduction algorithm,
	 * and clustering algorithm
	 */
	public AffinityPane(final Composite parent, int style, AffinityModel affinityModel, ArrayList<Integer> schemaIDs, 
			DistanceFunction distanceFunction, IMultiDimScaler mds, Clusterer clusterer, ILoadProgressIndicator progressIndicator) {
		super(parent, style);
		this.affinityPaneCreated = false;
		try {		
			//Get the schemas
			if(schemaIDs == null){ 
				this.schemaIDs = affinityModel.getSchemaManager().getSchemaIDs();
			}else{ 
				this.schemaIDs = schemaIDs;
			}
			
			//check to see how many schemas are being visualized
			if(this.schemaIDs == null || this.schemaIDs.size() == 0){
				throw new AffinityException("List of schema IDs was empty");
			}else{ 
				//1 or more schemas, so go ahead and run Affinity normally
				List<Schema> schemas = new ArrayList<Schema>();
				int i = 1;
				int numSchemas = this.schemaIDs.size();			
				int percentProgressPerSchema = 90/numSchemas;
				for(Integer schemaID : this.schemaIDs) {			
					Schema schema = affinityModel.getSchemaManager().getSchema(schemaID);
					if(schema == null) {
						System.err.println("Warning: Could not find schema " + schemaID + " in the repository");
						if(progressIndicator != null)
							progressIndicator.setErrorText("Error: schema " + schemaID + " not found");
					}
					else {
						schemas.add(schema);
					}
					if(progressIndicator != null) {
						progressIndicator.setProgressText("Loading Schemas (" + i + "/" + numSchemas + ")");
						progressIndicator.setPercentComplete(i * percentProgressPerSchema);
					}
					i++;
				}
				
				//Run the clusterer to generate clusters and the distance grid
				if(progressIndicator != null) progressIndicator.setProgressText("Computing clusters");
				affinityModel.generateClusters(schemaIDs, distanceFunction, clusterer);
				if(progressIndicator != null) progressIndicator.setPercentComplete(95);

				//Run the MDS algorithm to generate 2D points
				if(progressIndicator != null) progressIndicator.setProgressText("Computing layout");

				if(mds == null || mds instanceof PrefuseForceDirectedMDS) {
					if(mds == null)
						mds = new PrefuseForceDirectedMDS();
					//usePrefuse = true;
					
						if(rescale){
							affinityModel.getClusters().getDistanceGrid().rescale(0, 1000);			
						}
												
						((PrefuseForceDirectedMDS)mds).setSchemaIDs(this.schemaIDs);
				}		
				PositionGrid pg = mds.scaleDimensions(affinityModel.getClusters().getDistanceGrid(), true, false, 2, false);
				
				
				if(progressIndicator != null) progressIndicator.setPercentComplete(100);

				//Create the Affinity GUI			
				if(progressIndicator != null) progressIndicator.setProgressText("Opening Affinity");
				this.createAffinityPane(schemas, affinityModel.getClusters(), pg, affinityModel);
				affinityPaneCreated = true;	
				
			}

		} catch(Exception ex) {
			//There was an error creating the affinity pane
			affinityPaneCreated = false;			
			ex.printStackTrace();			
			this.exception = ex;
			if(ex.getMessage() != null && progressIndicator != null) 
				progressIndicator.setErrorText(ex.getMessage());
			if(progressIndicator != null) 
				progressIndicator.setProgressText("Could not launch Affinity due to errors!");
		}
	}	

	/**
	 * Creates an AffinityPane using the given schemas and clusters
	 */
/*	public AffinityPane(final Composite parent, int style, AffinityModel affinityModel, ArrayList<Integer> schemaIDs,
			ClustersContainer cc, IMultiDimScaler mds, ILoadProgressIndicator progressIndicator) {
		super(parent, style);
		this.affinityPaneCreated = false;
		try {	
			affinityModel.setClusters(cc);

			//Get the schemas
			this.schemaIDs = schemaIDs;			
			if(this.schemaIDs == null || this.schemaIDs.size() == 0)
				throw new AffinityException("List of schema IDs was empty");
			int i = 1;
			int numSchemas = this.schemaIDs.size();			
			int percentProgressPerSchema = 90/numSchemas;
			List<Schema> schemas = new ArrayList<Schema>();
			for(Integer schemaID : this.schemaIDs) {			 
				Schema schema = affinityModel.getSchemaManager().getSchema(schemaID);
				if(schema == null) {
					System.err.println("Warning: Could not find schema " + schemaID + " in the repository");
					if(progressIndicator != null) 
						progressIndicator.setErrorText("Error: schema " + schemaID + " not found");
				}
				else 
					schemas.add(schema);	
				if(progressIndicator != null) {
					progressIndicator.setProgressText("Loading Schemas (" + i + "/" + numSchemas + ")");
					progressIndicator.setPercentComplete(i * percentProgressPerSchema);
				}
				i++;
			}

			//Run the MDS algorithm to generate 2D points
			if(progressIndicator != null) progressIndicator.setProgressText("Computing layout");
			//boolean usePrefuse = false;
			if(mds == null || mds instanceof PrefuseForceDirectedMDS) {
				if(mds == null)
					mds = new PrefuseForceDirectedMDS();
				//usePrefuse = true;
				cc.getDistanceGrid().rescale(0, 1000);
				//this.distGridStats = cc.getDistanceGrid();
				mds = new PrefuseForceDirectedMDS();
				((PrefuseForceDirectedMDS)mds).setSchemaIDs(this.schemaIDs);
			}
			PositionGrid pg = mds.scaleDimensions(cc.getDistanceGrid(), true, false, 2, false);
			//this.posGridStats = pg;
			if(progressIndicator != null)  progressIndicator.setPercentComplete(100);

			//If we used prefuse, we need to recreate the distance grid
			//TODO: fix this
			//if(usePrefuse)
			//dg = distanceFunction.generateDistanceGrid(schemaIDs);

			//Create the Affinity GUI
			if(progressIndicator != null)  progressIndicator.setProgressText("Opening Affinity");				
			this.createAffinityPane(schemas, cc, pg, affinityModel);
			affinityPaneCreated = true;
		} catch(Exception ex) {
			//There was an error creating the affinity pane
			affinityPaneCreated = false;
			ex.printStackTrace();			
			this.exception = ex;
			if(ex.getMessage() != null && progressIndicator != null) 
				progressIndicator.setErrorText(ex.getMessage());
			if(progressIndicator != null) progressIndicator.setProgressText("Could not launch Affinity due to errors!");
		}
	}		
*/
	
	/**
	 * Creates an AffinityPane using the given schemas, clusters, position grid, and distance grid
	 */
	public AffinityPane(final Composite parent, int style, List<Schema> schemas, ClustersContainer cc, PositionGrid pg) {
		super(parent, style);
		this.affinityPaneCreated = false;
		//this.posGridStats = pg;
		//this.distGridStats = cc.getDistanceGrid();
		
		try {	
			this.schemaIDs = new ArrayList<Integer>();			
			for(Schema s : schemas)
				schemaIDs.add(s.getId());	
			if(this.schemaIDs.size() == 0)
				throw new AffinityException("List of schema IDs was empty");	
			this.createAffinityPane(schemas, cc, pg, null);
			affinityPaneCreated = true;
		} catch(Exception ex) {
			//There was an error creating the affinity pane
			affinityPaneCreated = false;
			ex.printStackTrace();	
			this.exception = ex;
		}
	}

	
	public boolean isAffinityPaneCreated() {
		return affinityPaneCreated;
	}

	public Exception getException() {
		return exception;
	}

	/*
	public void addAffinityEventListener(AffinityListener listener) {
		craigrogram.getSchema2DPlot().addAffinityListener(listener);
		dendrogram.addAffinityEventListener(listener);
	}
	
	public void removeAffinityEventListener(AffinityListener listener) {
		craigrogram.getSchema2DPlot().removeAffinityListener(listener);
		dendrogram.removeAffinityEventListener(listener);
	}
	*/
	
	public void addSelectionChangedListener(SelectionChangedListener listener) {
		craigrogram.getSchema2DPlot().addSelectionChangedListener(listener);
		dendrogram.addSelectionChangedListener(listener);
	}
	
	public void removeSelectionChangedListener(SelectionChangedListener listener) {
		craigrogram.getSchema2DPlot().removeSelectionChangedListener(listener);
		dendrogram.removeSelectionChangedListener(listener);
	}
	
	public void addSelectionClickedListener(SelectionClickedListener listener) {
		craigrogram.getSchema2DPlot().addSelectionClickedListener(listener);
		dendrogram.addSelectionClickedListener(listener);
	}
	
	public void removeSelectionClickedListener(SelectionClickedListener listener) {
		craigrogram.getSchema2DPlot().removeSelectionClickedListener(listener);
		dendrogram.removeSelectionClickedListener(listener);
	}	
	
	public ArrayList<Integer> getSchemaIDs() {
		return schemaIDs;
	}

	public SchemaCluster2DView getCraigrogram() {
		return craigrogram.getSchema2DPlot();
	}
	
	public SchemaCluster2DViewPane getCraigrogramPane() {
		return craigrogram;
	}

	public DendrogramCanvas getDendrogram() {
		return dendrogram;
	}
	
	//SelectionChangedListner  method to highlight a schema/cluster in the dendrogram when highlighted
	//in the craigrogram and vice-versa.
	public void selectionChanged(SelectionChangedEvent event) {		
		//System.out.println("selection changed");
		if(event.eventSource == this.craigrogram.getSchema2DPlot()) {
			//Selection changed in the craigrogram, so update selection in the dendrogram
			dendrogram.setSelectedSchemas(event.selectedSchemas);
			if(event.selectedClusters != null && event.selectedClusters.size() == 1) {
				ClusterGroup cluster = event.selectedClusters.iterator().next();
				dendrogram.setSelectedCluster(cluster);
			}
			else
				dendrogram.setSelectedCluster(null);			
			dendrogram.redraw();
		}else if(event.eventSource == this.dendrogram){
			//Selection changed in the dendrogram, so update selection in the craigrogram
			craigrogram.setSelectedSchemas(event.selectedSchemas);
			if(event.selectedClusters != null && event.selectedClusters.size() == 1) {
				ClusterGroup cluster = event.selectedClusters.iterator().next();
				craigrogram.setSelectedCluster(cluster);
			}
			else
				craigrogram.setSelectedCluster(null);
			craigrogram.redraw();
		}	
	}


	private void createAffinityPane(final List<Schema> schemas, final ClustersContainer cc, final PositionGrid pg, AffinityModel affinityModel) {		
		setLayout(new FillLayout());
		Display display = getDisplay();
		
		final Decorations decoration = new Decorations(this, SWT.NONE);
		GridLayout gl= new GridLayout(1, true);
		gl.marginHeight = 1;	
		gl.marginTop = 1;
		gl.marginBottom = 1;
		decoration.setLayout(gl);
		
		int schemaDiameter = 10;
		int radiusIncrement = 5;

		this.createMenu(schemas, decoration, this, radiusIncrement, affinityModel);


		
		//Create pane with the cluster distance slider
		Composite sliderPane = new Composite(decoration, SWT.NONE);
		sliderPane.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		sliderPane.setLayout(new FillLayout());
		this.slider = new SWTClusterDistanceSliderPane(sliderPane, SWT.NONE, cc, SwingConstants.HORIZONTAL);
		
		
		// Create sash that the Craigrogram and Dendrogram will be attached to		
		final Composite pane = new Composite(decoration, SWT.NONE);
		pane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		pane.setLayout(new FormLayout());
		final Sash sash = new Sash(pane, SWT.VERTICAL);
		this.craigrogramPercent = 50;
		sash.setBackground(display.getSystemColor(SWT.COLOR_WHITE));		
		final FormData sashData = new FormData ();
		sashData.left = new FormAttachment (craigrogramPercent, 0);
		sashData.top = new FormAttachment (0, 0);
		sashData.bottom = new FormAttachment (100, 0);
		sash.setLayoutData(sashData);		
		
		//int width = parent.getSize().x;
		//int height = parent.getSize().y;
		//int schemaDiameter = 10;
		//int radiusIncrement = 5;
		
		//Create the menu
		//affinityMenu = this.createMenu(schemas, decoration, this, radiusIncrement, affinityModel);	
		
		//FillLayout fillLayout = new FillLayout();
		//fillLayout.type = SWT.HORIZONTAL;
		//affinityMenu.setLayout(fillLayout);
		//affinityMenu.setLayoutData(new RowData(105, 30));

		
		//decoration.getMenuBar().setVisible(true);
		
		//Create the Craigrogram pane		
		Color black = display.getSystemColor(SWT.COLOR_BLACK);
		//Color white = parent.getDisplay().getSystemColor(SWT.COLOR_WHITE);
		Color mediumGray = new Color(display, 100, 100, 100);
		Color lightGray = new Color(display, 150, 150, 150);		
		final List<ISchemaGUI> schemaGUIs = new ArrayList<ISchemaGUI>();	
		this.schemasMap = new HashMap<Integer, Schema>();
		//System.out.println("Schema locations");
		
		for(Schema s : schemas) {
			schemasMap.put(s.getId(), s);
			Position pos = pg.getPosition(s.getId());
			//System.out.println(s.getId() + s.getName() + ", " + (int)pos.getDimensionValue(0) + ", " + (int)pos.getDimensionValue(1));
			if(pos == null)
				System.err.println("Error: position for schema " + s.getName() + " is null");		
			OvalSchemaGUI schemaGUI = new OvalSchemaGUI(getShell(), s.getId(), schemaDiameter, schemaDiameter);			
			schemaGUI.setLocation((int)pos.getDimensionValue(0), (int)pos.getDimensionValue(1));
			schemaGUI.setLabel(s.getName());
			//schemaGUI.setLabel(s.getId().toString());
			schemaGUI.setLabelColor(black);
			schemaGUI.setForeground(lightGray);
			schemaGUI.setBackground(lightGray);
			schemaGUI.setSelectedLineColor(mediumGray);
			schemaGUI.setShowLabel(true);
			schemaGUI.setToolTipText(s.getName());		
			schemaGUIs.add(schemaGUI);			
		}		
				
		this.craigrogram = new SchemaCluster2DViewPane(pane, SWT.BORDER, schemaGUIs, cc, pg, true, SliderType.NONE, 0, 1000);				
		craigrogram.setLockAspectRatio(true);
		//craigrogram.setBackground(white);	
		
		//only in here for testing
		//only in here for testing
		
		craigrogram.getSchema2DPlot().setShowClusters(true);
		craigrogram.getSchema2DPlot().setStartRadius(schemaDiameter/2 + radiusIncrement);		
		craigrogram.getSchema2DPlot().setRadiusIncrement(radiusIncrement);
		craigrogram.getSchema2DPlot().setBackground(sash.getBackground());
		craigrogram.getSchema2DPlot().setFillClusters(true);
		FormData pane1Data = new FormData ();
		pane1Data.left = new FormAttachment (0, 0);
		pane1Data.right = new FormAttachment (sash, 0);
		pane1Data.top = new FormAttachment (0, 0);
		pane1Data.bottom = new FormAttachment (100, 0);
		craigrogram.setLayoutData(pane1Data);		
		
					
		//create search/dendro combo pane
		final Composite searchPlusDendrogramPane = new Composite(pane, SWT.BORDER);	
		FormLayout fl1 = new FormLayout();
		searchPlusDendrogramPane.setLayout(fl1);
		this.searchToolBar = new SearchToolBar(searchPlusDendrogramPane,  SWT.NONE, this, schemas);

		
		//Create the Dendrogram pane				
		final Composite dendrogramPane = new Composite(searchPlusDendrogramPane, SWT.BORDER);
		dendrogramPane.setLayout(new FillLayout());
		FormData fd1 = new FormData();
		fd1.top = new FormAttachment(searchToolBar, 0);
		fd1.left = new FormAttachment (sash, 0);
		fd1.right = new FormAttachment (100, 0);
		fd1.bottom = new FormAttachment (100, 0);		
		dendrogramPane.setLayoutData(fd1);
		
		dendrogramPane.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		
		//dendrogram takes the leaves in order, uses IVC code to get these leaves - this would be easy to replace if need be		
		SchemaDendrogram dendroStruct = SchemaDendrogram.createDendrogram(schemas, cc.getDistanceGrid());
		this.dendrogram = new DendrogramCanvas(dendrogramPane, SWT.DOUBLE_BUFFERED, cc, schemas, dendroStruct.getLeaves());
		dendrogram.setFont(SWTUtils.getFont(SWTUtils.NORMAL_FONT));
		dendrogram.setSelectedFont(SWTUtils.getFont(SWTUtils.NORMAL_BOLD_FONT));
		dendrogram.setBackground(dendrogramPane.getBackground());

		
		//right side of sash 
		FormData pane2Data = new FormData ();
		pane2Data.left = new FormAttachment (sash, 0);
		pane2Data.right = new FormAttachment (100, 0);
		pane2Data.top = new FormAttachment (0, 0);
		pane2Data.bottom = new FormAttachment (100, 0);		
		searchPlusDendrogramPane.setLayoutData(pane2Data);
		
		//this.craigrogram.getClusterDistanceSlider().addClusterDistanceChangeListener(this.dendrogram);	
		//this.craigrogram.getClusterDistanceSlider().addClusterDistanceChangeListener(this.craigrogram);
		

		
		this.slider.getClusterDistanceSliderPane().addClusterDistanceChangeListener(this.dendrogram);
		this.slider.getClusterDistanceSliderPane().addClusterDistanceChangeListener(this.craigrogram);
		
		// Add the listener to allow resizing of the craigrogram and dendrogram	
		sash.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				//int limit = 0;
				int limit = 20;
				//if(craigrogram.getPane().getSize().x < dendrogram.getSize().x)					
					//limit = craigrogram.getClusterDistanceSlider().getWidth() + 15;				
				
				//System.out.println("limit: " + limit);	
				Rectangle sashRect = sash.getBounds ();
				Rectangle shellRect = getClientArea ();
				int right = shellRect.width - sashRect.width - limit;
				e.x = Math.max (Math.min (e.x, right), limit);
				if (e.x != sashRect.x)  {
					sashData.left = new FormAttachment (0, e.x);
					sash.getParent().layout();
					//sash.update();
				}
				
				//TODO: Resizing introduces error in the position grid, think about maintaining the original
				//aspect ratio or recomputing the position grid
				craigrogram.resize();
				//System.out.println("Correlation: " + MDSTester.compute2DFitCorrelation(schemaIDs, cc.getDistanceGrid(), craigrogram.getPositionGrid()));
			}
		});	
		
		//Add the resize listeners
		//decoration.addControlListener(new ControlListener() {
		decoration.addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent event) {}
			
			public void controlResized(ControlEvent event) {
				int craigrogramWidth = craigrogram.getSize().x;
				if(craigrogramWidth > 0)
					craigrogramPercent = (int)((float)craigrogramWidth/(craigrogramWidth + dendrogram.getSize().x) * 100);
				sashData.left = new FormAttachment (craigrogramPercent, 0);				
				decoration.layout();				
				craigrogram.resize();
			}
		});
		
		//Add listener to highlight cluster/schema in dendrogram when highlighted in craigrogram and vice-versa
		//addAffinityEventListener(this);
		addSelectionChangedListener(this);
		//shell.pack();
	}
	
	public Schema getSchema(Integer schemaID) {
		return this.schemasMap.get(schemaID);
	}
	
	public Map<Integer, Schema> getSchemas() {
		return schemasMap;
	}

	public void resize() {
		craigrogram.resize();
	}
	
	/*
	private Shell createDialogShell(Display display, String title) {
		Shell dlgShell = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dlgShell.setText(title);
		dlgShell.setLayout(new FillLayout());
		return dlgShell;
	}*/
	
	private HashMap<Integer, HashMap<Integer, Integer>> calculateGroundTruthOverlap(final List<Schema> schemas, AffinityModel affinityModel){
		HashMap<Integer,HashMap<Integer, Integer>> groundTruthOverlap = new HashMap<Integer, HashMap<Integer, Integer>>();
		//note: may need way to determine if they're from gold standard repository

		//key is a schemaID, ArryaList of Integers is Integers for all elements in that schema
		HashMap<Integer, ArrayList<String>> schemaElementParsedInts = new HashMap<Integer, ArrayList<String>>();
		
		//first parse each schema and create the ArrayList containing all element numbers
		//System.out.println("creating schema -> element nums in that schema");
		for(int i=0; i<schemas.size(); i++){
			Integer schemaIID = schemas.get(i).getId();
			//System.out.println("creating schema: " + schemaIID);
			SchemaInfo schemaIInfo = affinityModel.getSchemaManager().getSchemaInfo(schemaIID);
			ArrayList<SchemaElement> elementsI = schemaIInfo.getElements(null);
			ArrayList<String> schemasIntegers = new ArrayList<String>();

           for(int j=0; j<elementsI.size(); j++){
        	   //System.out.println("Element name " + elementsI.get(j));
        	   String elementName = elementsI.get(j).getName();
        	   int beginNum = elementName.indexOf("(");
        	   int endNum = elementName.indexOf(")");
        	   String num = new String();
        	   for(int k=beginNum+1; k<endNum; k++){
        		   //System.out.println(elementName.charAt(k));
        		   num += elementName.charAt(k);
        	   }
        	   if(num != null && !num.equals("")){
        		   //going to put the element in 
        		   schemasIntegers.add(num);
        		   //System.out.println("putting in num " + num);
        	   }
           }
           schemaElementParsedInts.put(schemaIID, schemasIntegers);
		}
		
		
		//check to see how many in A match something in B (note we're only creating half of the matrix)
		//enter that into the groundtruth Overlap hashmap
		//System.out.println("about to calculate overlaps");
		for(int i=0; i<schemas.size(); i++){
			Integer schemaIID = schemas.get(i).getId();
			ArrayList<String> schemaInums = schemaElementParsedInts.get(schemaIID);
			HashMap<Integer, Integer> schemaIDtoOverlap = new HashMap<Integer, Integer>();
			
			for(int j=i+1; j<schemas.size(); j++){
				Integer schemaJID = schemas.get(j).getId();
				ArrayList<String> schemaJnums = schemaElementParsedInts.get(schemaJID);
				int overlap = 0;
				for(int k=0; k<schemaJnums.size(); k++){
					if(schemaInums.contains(schemaJnums.get(k))){
						overlap++;
					}
				}
				schemaIDtoOverlap.put(schemaJID, overlap);
				//System.out.println("for " + schemaIID + "-" + schemaJID + " overlap of " + overlap);
			}
			groundTruthOverlap.put(schemaIID, schemaIDtoOverlap);
		}
		return groundTruthOverlap;
	}
	
	
	
	
//	public void writeToSpreadsheet(final List<Schema> schemas, String selectedFileName, AffinityModel affinityModel){
	public void writeToSpreadsheet(String selectedFileName){
		final List<Schema> schemas = this.schemasForSavingStats;
		AffinityModel affinityModel = this.affinityModelForSavingStats;
		
		Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Statistics");
        
        HashMap<Integer, HashMap<Integer, Integer>> groundTruthOverlap = calculateGroundTruthOverlap(schemas, affinityModel);

        String[] colNames = {"Schema A", "Schema B", 
        		"Size A", "Size B",
        		"Ground Truth Intersection Size", "Ground Truth Union Size",
        		"Jaccards (Ground Truth)", 
        		"Bag Size A", "Bag Size B", 
        		"Bag Intersection Size", "Bag Union Size" ,
        		"1- Jaccards (Bag Words)", "Jaccards (Bag Words)", 
        		"X coord (for A)", "Y coord (for A)", "X coord (for B)", "Y coord (for B)",
        		"Euclidean Distance Between A and B"};
        Row headingRow = sheet.createRow(0);

        CellStyle headingStyle= wb.createCellStyle();
        Font headingFont = wb.createFont();
        headingFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headingStyle.setFont(headingFont);
        
        for(int i=0; i<colNames.length; i++){
        	Cell cell = headingRow.createCell(i);
        	cell.setCellValue(colNames[i]);
        	cell.setCellStyle(headingStyle);
        }
       
        int nextSchema = 1; //using this to only cover each pair once
        int rowNum = 1;
        Integer schemaAID, schemaBID;
        String schemaAname, schemaBname;
        int aXpos, aYpos, bXpos, bYpos;
        
        JaccardDistanceFunction distanceFunction = new JaccardDistanceFunction();
		DistanceGrid dataDistGrid = distanceFunction.generateDistanceGrid(this.schemaIDs, affinityModel.getSchemaManager());
		SchemaPairValues<Integer> schemaBagIntersectSizes = distanceFunction.getIntersections();
		SchemaPairValues<Integer> schemaBagUnion = distanceFunction.getUnions();
		
		IMultiDimScaler mds = new PrefuseForceDirectedMDS();
		if(this.rescale){
			affinityModel.getClusters().getDistanceGrid().rescale(0.0, 1000.0);		
		}
		((PrefuseForceDirectedMDS)mds).setSchemaIDs(this.schemaIDs);
		PositionGrid dataPosGrid = mds.scaleDimensions(affinityModel.getClusters().getDistanceGrid(), true, false, 2, false);
		//PositionGrid posGrid = craigrogram.getPositionGrid();
        //PositionGrid posGrid = posGridStats;
		//this.distGridStats = affinityModel.getClusters().getDistanceGrid();
		//PositionGrid dataPosGrid = this.posGridStats;
        //DistanceGrid dataDistGrid = this.distGridStats;
        //DistanceGrid dataDistGrid = affinityModel.getDistanceGrid();
        
          for(int i=0; i<schemaIDs.size(); i++){
        	schemaAID = schemaIDs.get(i);
            schemaAname = affinityModel.getSchemaManager().getSchema(schemaAID).getName();
            Position pos = dataPosGrid.getPosition(schemaAID);
            aXpos = (int)pos.getDimensionValue(0);
            aYpos = (int)pos.getDimensionValue(1);
            HashMap<Integer, Integer> groundTruthForI = groundTruthOverlap.get(schemaAID);
            int numElementsA = affinityModel.getSchemaManager().getSchemaInfo(schemaAID).getElements(null).size();
   			numElementsA--;//-1 because taking out the blank "" element that always appears
   			
   			
            for(int j = nextSchema; j<schemaIDs.size(); j++){
        		schemaBID = schemaIDs.get(j);

            	Row dataRow = sheet.createRow(rowNum);

        		Cell nameAcell = dataRow.createCell(0);
        		nameAcell.setCellValue(schemaAID + " (" + schemaAname + ")");

        		Cell nameBcell = dataRow.createCell(1);
                schemaBname = affinityModel.getSchemaManager().getSchema(schemaBID).getName();
        		nameBcell.setCellValue(schemaBID + " (" + schemaBname + ")");

        		//Size A
        		Cell Asize = dataRow.createCell(2);
        		Asize.setCellValue(numElementsA); 
        		
        		//Size B
           		Cell Bsize = dataRow.createCell(3);
        		int numElementsB = affinityModel.getSchemaManager().getSchemaInfo(schemaBID).getElements(null).size();
        		numElementsB--;//-1 because taking out the blank "" element that always appears	        		
        		Bsize.setCellValue(numElementsB); 
        		
        		//Ground Truth Intersection Size 
        		Cell overlapGroundTruth = dataRow.createCell(4);
        		double overlap = groundTruthForI.get(schemaBID);
        		overlapGroundTruth.setCellValue(overlap);
        		
        		
        		//Grount Truth Union Size
        		Cell groundTruthUnion = dataRow.createCell(5);
        		double unionSize = numElementsA + numElementsB - overlap;
        		groundTruthUnion.setCellValue(unionSize);
        		
        		//Jaccards (Ground Truth)        		
        		Cell jaccardsGroundTruth = dataRow.createCell(6);
        		//jaccards ground truth is the size of the intersection over the size of the union
        		double jaccardsGT = overlap/unionSize;
        		//System.out.println(overlap + "/" + unionSize + "=" + jaccardsGT);
    			jaccardsGroundTruth.setCellValue(jaccardsGT);
        		
    			
        		Cell bagSizeA = dataRow.createCell(7);
        		bagSizeA.setCellValue(distanceFunction.getTermCount(schemaAID));
        		
        		Cell bagSizeB = dataRow.createCell(8);
        		bagSizeB.setCellValue(distanceFunction.getTermCount(schemaBID));
        		
        		Cell bagIntersectionSize = dataRow.createCell(9);
        		bagIntersectionSize.setCellValue(schemaBagIntersectSizes.get(schemaAID,schemaBID));

        		Cell bagUnionSize = dataRow.createCell(10);
        		bagUnionSize.setCellValue(schemaBagUnion.get(schemaAID,schemaBID));

        		Cell oneMjaccardAffinity  = dataRow.createCell(11);
                oneMjaccardAffinity.setCellValue(dataDistGrid.get(schemaAID, schemaBID));
        		
           		Cell jaccardAffinity = dataRow.createCell(12);
                jaccardAffinity.setCellValue(1-(dataDistGrid.get(schemaAID, schemaBID)));

           		Cell aX = dataRow.createCell(13);
           		aX.setCellValue(aXpos);
           		
        		Cell aY = dataRow.createCell(14);
        		aY.setCellValue(aYpos);
        		
        		Cell bX = dataRow.createCell(15);
           		bXpos = (int)dataPosGrid.getPosition(schemaBID).getDimensionValue(0);
        		bX.setCellValue(bXpos);

        		Cell bY = dataRow.createCell(16);
                bYpos = (int)dataPosGrid.getPosition(schemaBID).getDimensionValue(1);        		
        		bY.setCellValue((int)bYpos);
        		
        		Cell euclidDist = dataRow.createCell(17);
        		//sqrt of (x-x)^2 + (y-y)^2
        		double xs = Math.pow((aXpos-bXpos), 2);
        		double ys = Math.pow((aYpos-bYpos), 2);
        		double euclid = Math.sqrt(xs+ys);
        		euclidDist.setCellValue(euclid);
        		
        		rowNum++;
        	}
        	nextSchema++;
        }

        //resizing all the columns once all the data is in
        for(int i=0; i<colNames.length; i++){
               	sheet.autoSizeColumn((short)i);
        }
       

      //save statistics to the selected spreadsheet
        FileOutputStream out = null;
		try {
			out = new FileOutputStream(selectedFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			
	    try {
				wb.write(out);
		        out.close();
		} catch (IOException e) {e.printStackTrace();}		
	}
	
	
	

	private void createMenu(final List<Schema> schemas, final Decorations decoration, final Composite parent, int radiusIncrement, final AffinityModel affinityModel) {
	//private ToolBar createMenu(final List<Schema> schemas, Composite fakeMenuPane, final  Decorations decoration, final Composite parent, int radiusIncrement, final AffinityModel affinityModel) {
		this.schemasForSavingStats = schemas;
		this.affinityModelForSavingStats = affinityModel;
		
		//Create menus
		Menu menuBar = new Menu(decoration, SWT.BAR);
		decoration.setMenuBar(menuBar);	
		
		//Create the File menu
		MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		fileItem.setText("File");		
		Menu fileMenu = new Menu(decoration, SWT.DROP_DOWN);
		fileItem.setMenu(fileMenu);
		
		
		MenuItem saveStatsItem = new MenuItem(fileMenu, SWT.NONE);
		saveStatsItem.setText("Save statistics to...");
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
				//writeToSpreadsheet(schemas, selectedFile, affinityModel);
				writeToSpreadsheet(selectedFile);
			}
			
		});
		
		
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
		
		//Create the craigrogram options menu
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
					craigrogram.getSchema2DPlot().setSchemaNamesVisible(true);
					craigrogram.setTextSize(TextSize.Small);
				}else if(item.getText().startsWith("Normal")){
					craigrogram.getSchema2DPlot().setSchemaNamesVisible(true);
					craigrogram.setTextSize(TextSize.Normal);
				}else if(item.getText().startsWith("Large")){
					craigrogram.getSchema2DPlot().setSchemaNamesVisible(true);
					craigrogram.setTextSize(TextSize.Large);
				}else{
					//hide text
					craigrogram.getSchema2DPlot().setSchemaNamesVisible(false);
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
				if(craigrogram.getSchema2DPlot().getSchemaNamesVisible()){
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
		

		
		//Create lock aspect ratio check box
/*		final MenuItem lockAspectRatioItem = new MenuItem(craigrogramMenu, SWT.CHECK);
		lockAspectRatioItem.setText("Lock Aspect Ratio");
		lockAspectRatioItem.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent event) {			
				boolean selected = ((MenuItem)event.getSource()).getSelection();				
				craigrogram.setLockAspectRatio(selected);
				//craigrogram.redraw();
			}
		});		
	*/
		
		
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
				craigrogram.getSchema2DPlot().setShowOverview(selected);
				craigrogram.redraw();
			}
		});		
		showOverviewItem.setSelection(true);	
		
		//Create show schema names check box
		final MenuItem showTitlesItem = new MenuItem(craigrogramMenu, SWT.CHECK);
		showTitlesItem.setText("Show Schema Names");
		showTitlesItem.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent event) {			
				boolean selected = ((MenuItem)event.getSource()).getSelection();				
				craigrogram.getSchema2DPlot().setSchemaNamesVisible(selected);
			
				List<ISchemaGUI> schemas = craigrogram.getSchema2DPlot().getSchemata();
				for(ISchemaGUI s : schemas) {
					s.setShowLabel(selected);			
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
				craigrogram.getSchema2DPlot().setShowClusters(selected);
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
				craigrogram.getSchema2DPlot().setFillClusters(selected);
				craigrogram.redraw();
			}
		});		
		fillClustersItem.setSelection(true);		
		
		//Create follow convace paths check box
		final MenuItem followConcavePathsItem = new MenuItem(craigrogramMenu, SWT.CHECK);
		followConcavePathsItem.setText("Follow Concave Paths");
		followConcavePathsItem.addSelectionListener(new SelectionAdapter() {		
			public void widgetSelected(SelectionEvent event) {			
				boolean selected = ((MenuItem)event.getSource()).getSelection();				
				craigrogram.getSchema2DPlot().setUseConcavitySkipLocic(!selected);
				craigrogram.getSchema2DPlot().setUseSchemaSkipLogic(!selected);
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
						craigrogram.getSchema2DPlot().setRadiusIncrement(model.radius);						
					}					
					craigrogram.getSchema2DPlot().redraw();
				}				
			}
		});
		
		craigrogramMenu.addMenuListener(new MenuAdapter() {
			public void menuShown(MenuEvent e) {
				//lockAspectRatioItem.setSelection(craigrogram.isLockAspectRatio());
				showToolBarItem.setSelection(craigrogram.isShowToolBar());
				showOverviewItem.setSelection(craigrogram.getSchema2DPlot().isShowOverview());
				//showTitlesItem.setSelection(true);
				showClustersItem.setSelection(craigrogram.getSchema2DPlot().isShowClusters());
				fillClustersItem.setSelection(craigrogram.getSchema2DPlot().isFillClusters());
				followConcavePathsItem.setSelection(!craigrogram.getSchema2DPlot().isUseConcavitySkipLocic());
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
				dendrogram.setShadeClusters(selected);
				dendrogram.redraw();
			}
		});		
		shadeBackgroundsItem.setSelection(true);		
		
		//return toolbar;
	}	
}
