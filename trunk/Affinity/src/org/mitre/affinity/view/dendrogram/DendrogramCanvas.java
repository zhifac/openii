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

package org.mitre.affinity.view.dendrogram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.clusters.ClusterStep;
import org.mitre.affinity.clusters.ClustersContainer;
import org.mitre.affinity.util.SWTUtils;
import org.mitre.affinity.view.craigrogram.SchemaCluster2DViewToolBar;
import org.mitre.affinity.view.event.ClusterDistanceChangeEvent;
import org.mitre.affinity.view.event.ClusterDistanceChangeListener;
import org.mitre.affinity.view.event.SelectionChangedEvent;
import org.mitre.affinity.view.event.SelectionChangedListener;
import org.mitre.affinity.view.event.SelectionClickedEvent;
import org.mitre.affinity.view.event.SelectionClickedListener;
import org.mitre.schemastore.model.Schema;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;

import edu.iu.iv.visualization.dendrogram.model.DendrogramNode;

/**
 * Renders a dendrogram from a given set of clusters. 
 * 
 * @author BETHYOST
 *
 */
public class DendrogramCanvas extends Canvas implements ClusterDistanceChangeListener {
	private final ClustersContainer cc;
	

	/** Maps schema id to its schema gui */
	private Map<Integer, DendrogramSchemaGUI> schemas;	
	
	private DendrogramNode[] leaves; //this provides the schemas in the order they appear at the bottom of the dendrogram
	private Composite parent;
	
	//schemaNumber followed by X location
	protected Map<Integer, Integer> schemaXLocationsMap;
	protected Map<Integer, Integer> schemaYLocationsMap;
	protected Map<Integer, ClusterGroup> newSchemasToClusterGroups;
	protected Map<Integer, Integer> fromNewSchemasToClustertoSchemaID;
	protected Map<Integer, Integer> firstChild;
	protected Map<Integer, Integer> secondChild;
	
	private int maxSchemaNameLength;
	private boolean currentColorBlue;
	private boolean altTreeColors;
	private boolean shadeBackground;
	
	Color white;
	Color black;
	Color red;
	Color blue;
	Color gray;
	Color lightGray, blue1, blue2, blue3, blue4, blue5;
	Color green;
	private double maxBarDVal;
	private double minBarDVal;
	private int canvasWidth;
	private int canvasHeight;
	
	/** We render the dendrogram on an image, and the image is scrolled */
	private Image dendrogramImage;
	
	/** The highlight color */
	private Color highlightColor;
	
	//private List<AffinityListener> affinityEventListeners;
	
	/** Listeners that have registered to received SelectionChangedEvents from this component */
	private List<SelectionChangedListener> selectionChangedListeners;
	
	/** Listeners that have registered to received SelectionClickedEvents from this component */
	private List<SelectionClickedListener> selectionClickedListeners;
	
	/** The currently selected schemas */	
	//private Schema highlightedSchema = null;
	private Collection<Integer> selectedSchemas;
	
	/** The currently selected cluster */
	private ClusterGroup selectedCluster = null;
	
	/** Cached list of the bounding rectangles for all schema labels to enable mouse hit detection  */
	//private Map<Integer, Rectangle> schemaBounds;
	
	/** Cached list of the bounding rectangles for all clusters to enable mouse hit detection  */
	private Map<ClusterGroup, Rectangle> clusterBounds;
	
	/** Font used to render a schema name when the schema is selected */
	//private Font selectedFont = SWTUtils.getFont(SWTUtils.NORMAL_BOLD_FONT);
	//private Font selectedFont = SWTUtils.getFont(SWTUtils.LARGE_BOLD_FONT);
	private Font selectedFont = new Font(Display.getDefault(), new FontData("Times", 18, SWT.ITALIC));
	
	private Point origin;
	
	//a DendroCanvas expects a ClustersContainer that has duplicate clusters removed
	public DendrogramCanvas(Composite par, int style, ClustersContainer clustc, List<Schema> schemas, DendrogramNode[] leafs) {
		super(par, style | SWT.V_SCROLL);	
		
		//this.affinityEventListeners = new LinkedList<AffinityListener>();
		this.selectedSchemas = new LinkedList<Integer>();		
		this.selectionChangedListeners = new LinkedList<SelectionChangedListener>();
		this.selectionClickedListeners = new LinkedList<SelectionClickedListener>();	
		this.clusterBounds = new HashMap<ClusterGroup, Rectangle>();
		/*this.clusterBounds = new TreeMap<ClusterGroup, Rectangle>(new Comparator<ClusterGroup>() {
			public int compare(ClusterGroup arg0, ClusterGroup arg1) {				
				return (arg0.getNumSchemas() - arg1.getNumSchemas());
			}			
		});*/
		parent = par;
		cc = clustc;
		this.schemas = new HashMap<Integer, DendrogramSchemaGUI>();
		for(Schema schema : schemas) 
			this.schemas.put(schema.getId(), new DendrogramSchemaGUI(schema));
		leaves = leafs;
		
		//starting value is set by default to 1.0 and 0.0
		//maxBarDVal = 0.75;
		maxBarDVal = 1.00;
		minBarDVal = 0.00;
		
		//default is true for showing alt colors and background fill
		altTreeColors = true;
		shadeBackground = true;
		
		//getting the colors
		white = this.getDisplay().getSystemColor(SWT.COLOR_WHITE);
		black = this.getDisplay().getSystemColor(SWT.COLOR_BLACK);		
		blue = this.getDisplay().getSystemColor(SWT.COLOR_BLUE);
		red = this.getDisplay().getSystemColor(SWT.COLOR_RED);		
		gray = this.getDisplay().getSystemColor(SWT.COLOR_GRAY);		
		green = this.getDisplay().getSystemColor(SWT.COLOR_GREEN);		
		lightGray = new Color(this.getDisplay(), 238, 238, 238);
		blue1 = new Color(this.getDisplay(), 239, 243, 255);
		blue2 = new Color(this.getDisplay(), 189, 215, 231);
		blue3 = new Color(this.getDisplay(), 107,174, 214);
		blue4 = new Color(this.getDisplay(), 49, 130, 189);
		blue5 = new Color(this.getDisplay(), 8, 81, 156);
		highlightColor = green;
		
		this.addMouseListener(new DendrogramMouseListener());
		
		this.dendrogramImage = new Image(getDisplay(), 200, 300);
		//final Point origin = new Point (0, 0); //Used in scrolling to know how much to offset by
		this.origin = new Point(0, 0);
		
		//redrawing the dendrogram
		this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				//render(e.gc, parent);
				GC gc = e.gc;
				GC imageGC = new GC(dendrogramImage);
				render(imageGC, parent);
				gc.drawImage (dendrogramImage, origin.x, origin.y);
				Rectangle rect = dendrogramImage.getBounds ();
				Rectangle client = getClientArea ();
				int marginWidth = client.width - rect.width;
				if (marginWidth > 0) {
					gc.fillRectangle (rect.width, 0, marginWidth, client.height);
				}
				int marginHeight = client.height - rect.height;
				if (marginHeight > 0) {
					gc.fillRectangle (0, rect.height, client.width, marginHeight);
				}
				imageGC.dispose();
			}
		});	
		
		//Create vertical scrollbar
		final ScrollBar vBar = getVerticalBar ();
		vBar.addListener(SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				int vSelection = vBar.getSelection();
				int destY = -vSelection - origin.y;
				Rectangle rect = getBounds();
				scroll(0, destY, 0, 0, rect.width, rect.height, false);
				origin.y = -vSelection;
			}
		});
		
		//Resize listener
		addListener(SWT.Resize,  new Listener () {
			public void handleEvent (Event e) {
				Rectangle client = getClientArea ();				
				int distanceBetween = (client.height-5)/leaves.length;
				
				//int fontHeight = getFont().getFontData()[0].getHeight();
				GC imageGC = new GC(dendrogramImage);
				int fontHeight = imageGC.stringExtent("X").y;
				imageGC.dispose();
				
				int minDistanceBetween = fontHeight + 4;
				//int minHeight = 2 + fontHeight * leaves.length + minDistanceBetween * (leaves.length - 1);
				int desiredHeight = client.height;
				if(distanceBetween < minDistanceBetween) {
					desiredHeight = minDistanceBetween * leaves.length + 5;
				}
				
				//The dendrogram is always sized to fit within the canvas's width and to be 
				//high enough to display all leaf node text at the minimum text height
				if(dendrogramImage.getBounds().width != client.width || 
						dendrogramImage.getBounds().height != desiredHeight) {
					dendrogramImage.dispose();
					if(client.width <= 0)
						client.width = 10;
					if(desiredHeight <= 0)
						desiredHeight = 10;
					dendrogramImage = new Image(getDisplay(), client.width, desiredHeight);
				}				
				
				Rectangle rect = dendrogramImage.getBounds ();
				//hBar.setMaximum (rect.width);
				vBar.setMaximum (rect.height);
				//hBar.setThumb (Math.min (rect.width, client.width));
				vBar.setThumb (Math.min (rect.height, client.height));
				//int hPage = rect.width - client.width;
				int vPage = rect.height - client.height;
				//int hSelection = hBar.getSelection ();
				int vSelection = vBar.getSelection ();
				//if (hSelection >= hPage) {
				//	if (hPage <= 0) hSelection = 0;
				//	origin.x = -hSelection;
				//}
				if (vSelection >= vPage) {
					if (vPage <= 0) vSelection = 0;
					origin.y = -vSelection;
				}
				redraw();
			}
		});
		
		//initial rendering of the dendrogram
		//GC gc = new GC(this);
		GC gc = new GC(dendrogramImage);
		render(gc, parent);
		gc.dispose();
	}
	
	public Font getSelectedFont() {
		return selectedFont;
	}

	public void setSelectedFont(Font selectedFont) {
		this.selectedFont = selectedFont;
	}

	public void clusterDistanceChanged(ClusterDistanceChangeEvent event) {		
		this.setMaxBar(event.newMaxDistance);
		this.setMinBar(event.newMinDistance);
		//System.out.println("saw a change in cluster distance to: " + event);
		getDisplay().asyncExec(new Runnable() {
			public void run() {
				redraw();
			}});		
	}	
	
	public void setMaxBar(double val){
		maxBarDVal = val;
	}
	
	public void setMinBar(double val){
		minBarDVal = val;
	}

	public void setShowClusters(boolean selected){
		altTreeColors = selected;
	}

	public void setShadeClusters(boolean selected){
		shadeBackground = selected;
	}
	
	/**
	 * Select a single schema.  Any previously selected schemas are unselected.
	 * 
	 * @param schemaID
	 */
	public void setSelectedSchema(Integer schemaID) {
		//Unselect any previously selected schemas 
		//TO DO, when this is called, AUTOSCROLL
		unselectAllSchemas();
		
		if(schemaID != null) {
			DendrogramSchemaGUI s =  schemas.get(schemaID);
			if(s != null) {
				s.setSelected(true);
				selectedSchemas.add(schemaID);
			}
		}
	}
	
	/**
	 * Select a group of schemas.  Any previously selected schemas are unselected.
	 * 
	 * @param schemaIDs
	 */
	public void setSelectedSchemas(Collection<Integer> schemaIDs) {
		//Unselect any previusly selected schemas
		unselectAllSchemas();
		
		if(schemaIDs != null) {
			for(Integer schemaID : schemaIDs) {
				DendrogramSchemaGUI s =  schemas.get(schemaID);
				if(s != null) {
					s.setSelected(true);
					selectedSchemas.add(schemaID);
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
		this.unselectAllClusters();		
		this.selectedCluster = cluster;				
	}
	
	/**
	 * Unselect all schemas 
	 */
	public void unselectAllSchemas() {
		for(Integer schemaID : selectedSchemas) {
			DendrogramSchemaGUI s = schemas.get(schemaID);
			if(s != null)
				s.setSelected(false);
		}
		selectedSchemas.clear();
	}
	
	/**
	 * Unselect all clusters
	 */
	public void unselectAllClusters() {
		this.selectedCluster = null;
	}
	
	/**
	 * Unselect all schemas and clusters
	 */
	public void unselectAllSchemasAndClusters() {
		unselectAllSchemas();
		unselectAllClusters();
	}
	
	//this method takes a cluster group and finds the ID's of it's children
	//the ids it finds are the ones specific to this class used in the schemaXLocationsMap etc.
	//this list will always contain exactly two Integers, one for each child in this binary tree
	//the order does not indicate anything
	public ArrayList<Integer> getChildrenIDs(ClusterGroup cg, int numOfOrig){
		ArrayList<Integer> schemaIntegers = new ArrayList<Integer>();
		//int clusA = -1;
		//int clusB = -1;
	/*	
		if(cg == null) {
			System.err.println("Error in DendrogramCanvas.getChildrenIDs(): encountered null ClusterGroup");
			//return null;
		}
		
		
		
		//this line is where the problem occurred
		//System.out.println("size: " + cg.getNumSchemas());
		ClusterGroup temp = null;
		for(int i = numOfOrig-1; i>0; i--){
		//for(Map.Entry<Integer, ClusterGroup> cgEntry : newSchemasToClusterGroups.entrySet()) {
			ClusterGroup currCG = newSchemasToClusterGroups.get(i);
			//ClusterGroup currCG = cgEntry.getValue();
			//System.out.println(i + " is: " + currCG);
			if(cg.contains(currCG)){
				//System.out.println(cg + " contains " + currCG);
				clusA = i;
				//clusA = cgEntry.getKey();
				
				schemaIntegers.add(clusA);
				Collection<Integer> origIDs = cg.getSchemaIDs();
				temp = new ClusterGroup(origIDs);
				temp.removeSchemas(currCG.getSchemaIDs());
				break;
			}
		}
		
		//for(int i = newSchemasToClusterGroups.size(); i>0; i--){
		for(int i = numOfOrig-1; i>0; i--){
		//for(Map.Entry<Integer, ClusterGroup> cgEntry : newSchemasToClusterGroups.entrySet()) {
			ClusterGroup currCG = newSchemasToClusterGroups.get(i);
			//ClusterGroup currCG = cgEntry.getValue();
			//System.out.println(i + " is: " + currCG);
			if(currCG == null){
				System.err.println("null cluster group");
			}else if(temp.contains(currCG)){
				//System.out.println(temp + " contains " + currCG);
				clusB = i;
				//clusB = cgEntry.getKey();
				schemaIntegers.add(clusB);
				break;
			}
		}	
		
		*/
		schemaIntegers.add(firstChild.get(numOfOrig));
		schemaIntegers.add(secondChild.get(numOfOrig));
		return schemaIntegers;
	}
	
	//given a schemaID, draw in a given color all the way down that tree
	public void colorTree(int numOfOrig, GC gcDendrogram, Color color, boolean useHighlightColor){				
		//get children		
		//if there are at least 2 schemas
		//if there is only one then you've hit the bottom of the tree and do nothing
		ClusterGroup testClusGroupSize = newSchemasToClusterGroups.get(numOfOrig);
		testClusGroupSize.setDendroColor(color);
		//double cgVal = testClusGroupSize.getDistance();		
		
		//Change the color to the highlight color if this is the highlighted cluster
		if(testClusGroupSize == selectedCluster)
			useHighlightColor = true;
			//color = highlightColor;
		
		//color children's connector		
		gcDendrogram.setForeground(color);
		gcDendrogram.setLineStyle(SWT.LINE_SOLID);
		gcDendrogram.setLineWidth(3);
		
		Font normalFont = getFont();
		
		int currXVal = schemaXLocationsMap.get(numOfOrig);
		int sizeOrig = testClusGroupSize.getNumSchemas();
			if(sizeOrig>1){
				ArrayList<Integer> children = getChildrenIDs(newSchemasToClusterGroups.get(numOfOrig), numOfOrig);
				Iterator<Integer> iter = children.iterator();
				Integer aID = (Integer)iter.next();
				Integer bID = (Integer)iter.next();
			
				//if portrait then draw the connector between children this way
				Integer aX = schemaXLocationsMap.get(aID);
				Integer aY = schemaYLocationsMap.get(aID);
				Integer bX = schemaXLocationsMap.get(bID);
				Integer bY = schemaYLocationsMap.get(bID);
				
				//if(shadeBackground) {					
				int rectY;
				int rectHeight;

				if(aY<bY){
					rectY = aY;
					rectHeight = bY-aY;
				}else{
					rectY = bY;
					rectHeight = aY-bY;
				}				

				/*if(cgVal <= 0.25){
						gcDendrogram.setBackground(blue5);
					}else if(cgVal <= 0.5){
						gcDendrogram.setBackground(blue4);
					}else if(cgVal <= 0.75){
						gcDendrogram.setBackground(blue3);	
					}else{
						gcDendrogram.setBackground(blue2);
					}*/

				//gcDendrogram.setAlpha(128);
				if(shadeBackground) {
					gcDendrogram.setBackground(lightGray);	
					gcDendrogram.fillRectangle(0, rectY-1, currXVal, rectHeight+2);
					//gcDendrogram.setAlpha(255);
					gcDendrogram.setBackground(white);
				}
				
				//Cache the cluster bounds to enable mouse hit detection
				if(clusterBounds.containsKey(testClusGroupSize)) {
					Rectangle bounds = clusterBounds.get(testClusGroupSize);
					bounds.x = 0; bounds.y = rectY-1;
					bounds.width = currXVal; bounds.height = rectHeight+2;
				}
				else {
					clusterBounds.put(testClusGroupSize, 
							new Rectangle(0, rectY-1, currXVal, rectHeight+2));
				}
				//}
				
				if(useHighlightColor)
					gcDendrogram.setForeground(highlightColor);				
				gcDendrogram.drawLine(aX, aY, currXVal, aY);
				gcDendrogram.drawLine(bX, bY, currXVal, bY);
				gcDendrogram.drawLine(currXVal, aY, currXVal, bY);
				

				//drawing the tick marks for each cluster
				gcDendrogram.setForeground(black);
				gcDendrogram.setLineWidth(1);
				//gcDendrogram.setAlpha(32);
				gcDendrogram.drawLine(currXVal, 0, currXVal, 6);
				gcDendrogram.setForeground(color);
				gcDendrogram.setLineWidth(3);
				gcDendrogram.setAlpha(255);
			
				
				//if the size of the left child isn't 1 then color everything below it
				ClusterGroup aClusGroup = newSchemasToClusterGroups.get(aID);
				int sizeA = aClusGroup.getNumSchemas();
				if(sizeA > 1) {
					colorTree(aID, gcDendrogram, color, useHighlightColor);
				}
				else {
					//Integer actualID = fromNewSchemasToClustertoSchemaID.get(aID);
					//System.out.println("translation: " + fromNewSchemasToClustertoSchemaID);
					//String schemaName = schemas.get(actualID).toString();
					ClusterGroup schemaIDval = newSchemasToClusterGroups.get(aID);
					Iterator<Integer> schemaVal = schemaIDval.iterator();
					Integer clusterId = (Integer)schemaVal.next();
					//System.out.println("D: trying to get aID: " + aID + " from " + clusterId);
					DendrogramSchemaGUI schema = schemas.get(clusterId);
					String schemaName = schema.schema.getName();
					
					int nameLength = schemaName.length();
					int xOffset = 0;
					if(nameLength<maxSchemaNameLength){
						xOffset = maxSchemaNameLength-nameLength;
					}					
					//Render the schema name 
					Integer yName = schemaYLocationsMap.get(aID)-6;
					gcDendrogram.setBackground(lightGray);		
					
					if(schema.isSelected()) {
						//gcDendrogram.setForeground(highlightColor);
						gcDendrogram.setForeground(black);
						gcDendrogram.setFont(selectedFont);
					}
					else {
						gcDendrogram.setForeground(black);
						//gcDendrogram.setFont(normalFont);
					}
					gcDendrogram.drawString(schemaName, xOffset*5, yName, true);
					gcDendrogram.setFont(normalFont);
					
					//Cache the schema name string bounds to enable mouse hit detection
					Point stringExtent = gcDendrogram.stringExtent(schemaName);				
					if(schema.bounds != null) {						
						schema.bounds.x = xOffset*5; schema.bounds.y = yName; 
						schema.bounds.width = stringExtent.x; schema.bounds.height = stringExtent.y;
					}
					else {
						schema.bounds =	new Rectangle(xOffset*5, yName, stringExtent.x, stringExtent.y);
					}
					
					gcDendrogram.setBackground(white);
				}
				//ClusterGroup bClusGroup = newSchemasToClusterGroups.get(bID);
				int sizeB = aClusGroup.getNumSchemas();
				if(sizeB > 1){
					colorTree(bID, gcDendrogram, color, useHighlightColor);
				}else{
					//String schemaName = schemas.get(bID).toString();
					//Integer actualID = fromNewSchemasToClustertoSchemaID.get(bID);
					//String schemaName = schemas.get(actualID).toString();
					
					ClusterGroup schemaIDval = newSchemasToClusterGroups.get(bID);
					Iterator<Integer> schemaVal = schemaIDval.iterator();
					Integer clusterId = (Integer)schemaVal.next();
					//System.out.println("D: trying to get aID: " + aID + " from " + clusterId);
					DendrogramSchemaGUI schema = schemas.get(clusterId);
					String schemaName = schema.schema.getName();
					
					int nameLength = schemaName.length();
					int xOffset = 0;
					if(nameLength<maxSchemaNameLength){
						xOffset = maxSchemaNameLength-nameLength;
					}
					
					Integer yName = schemaYLocationsMap.get(bID)-6;
					gcDendrogram.setBackground(lightGray);
					if(schema.isSelected()) {
						//gcDendrogram.setForeground(highlightColor);
						gcDendrogram.setForeground(black);
						gcDendrogram.setFont(selectedFont);
					}
					else {
						gcDendrogram.setForeground(black);
						gcDendrogram.setFont(normalFont);
					}
					gcDendrogram.drawString(schemaName, xOffset*5, yName, true);
					gcDendrogram.setFont(normalFont);
					
					//Cache the schema name string bounds to enable mouse hit detection
					Point stringExtent = gcDendrogram.stringExtent(schemaName);
					if(schema.bounds != null) {						
						schema.bounds.x = xOffset*5; schema.bounds.y = yName; 
						schema.bounds.width = stringExtent.x; schema.bounds.height = stringExtent.y;
					}
					else {
						schema.bounds = new Rectangle(xOffset*5, yName, stringExtent.x, stringExtent.y);
					}
					
					gcDendrogram.setBackground(white);
				}
			//if the size of the right child isn't 1 then color everything below it
			} else {
				//only one schema, write the name in the color
				//String schemaName = schemas.get(numOfOrig).toString();

				//System.out.println("trying to get the actual schema name for point: " + numOfOrig);
				//System.out.println("which contains cluster: " + newSchemasToClusterGroups.get(numOfOrig));
				
				//String schemaName = schemas.get(fromNewSchemasToClustertoSchemaID.get(numOfOrig)).toString();
				
				ClusterGroup schemaIDval = newSchemasToClusterGroups.get(numOfOrig);
				Iterator<Integer> schemaVal = schemaIDval.iterator();
				Integer clusterId = (Integer)schemaVal.next();
				//System.out.println("D: trying to get aID: " + aID + " from " + clusterId);
				DendrogramSchemaGUI schema = schemas.get(clusterId);
				String schemaName = schema.schema.getName();
				
				//Integer actualID = fromNewSchemasToClustertoSchemaID.get(numOfOrig);
				//String schemaName = schemas.get(actualID).toString();
				
				int nameLength = schemaName.length();
				int xOffset = 0;
				if(nameLength<maxSchemaNameLength){
					xOffset = maxSchemaNameLength-nameLength;
				}
				
				Integer yName = schemaYLocationsMap.get(numOfOrig)-6;
				gcDendrogram.setBackground(lightGray);
				if(schema.isSelected()) {
					//gcDendrogram.setForeground(highlightColor);
					gcDendrogram.setForeground(black);
					gcDendrogram.setFont(selectedFont);
				}
				else {
					gcDendrogram.setForeground(black);
					//gcDendrogram.setFont(normalFont);
				}
				gcDendrogram.drawString(schemaName, xOffset*5, yName, true);
				gcDendrogram.setFont(normalFont);
				
				//Cache the schema name string bounds to enable mouse hit detection
				Point stringExtent = gcDendrogram.stringExtent(schemaName);
				if(schema.bounds != null) {					
					schema.bounds.x = xOffset*5; schema.bounds.y = yName; 
					schema.bounds.width = stringExtent.x; schema.bounds.height = stringExtent.y;
				}
				else {
					schema.bounds =	new Rectangle(xOffset*5, yName, stringExtent.x, stringExtent.y);
				}
				
				gcDendrogram.setBackground(white);
			}		
	}
	
	
	//traverse down the tree
	//when you hit something that should be colored
	//then color everything the appropriate color under that point
	//note that this is meant to take the maxstep
	public void determineColors(int numOfOrig, GC gcDendrogram, int maxVal){	
		int clusterXVal = schemaXLocationsMap.get(numOfOrig);
		
		Font normalFont = getFont();
		
		if(clusterXVal <= maxVal){
			if(!altTreeColors){
				//all will be blue
				colorTree(numOfOrig, gcDendrogram, blue, false);
			}else{
				//if this is true then the dendrogram should be colored
				if(currentColorBlue){
					//gcDendrogram.setForeground(blue);
					currentColorBlue = false;
					if(altTreeColors){
						colorTree(numOfOrig, gcDendrogram, blue, false);
					}else{
						//all will be blue
						colorTree(numOfOrig, gcDendrogram, blue, false);
					}
				}else{
					//gcDendrogram.setForeground(red);
					currentColorBlue = true;
					if(altTreeColors){
						colorTree(numOfOrig, gcDendrogram, red, false);
					}else{
						//all will be blue
						colorTree(numOfOrig, gcDendrogram, blue, false);
					}
				}
			}
		}else{
			//perhaps color the connection between numOfOrig gray
			//only get here if the parent id: numOfOrig was above the maxValue
			//check children to see if they are below bar and should be considered clustered
			ClusterGroup testClusGroupSize = newSchemasToClusterGroups.get(numOfOrig);
			int sizeOrig = testClusGroupSize.getNumSchemas();
				if(sizeOrig>1){
					//split the two cluster into two
					//really we're looking for the two dendro points that make up this point
					ArrayList<Integer> children = getChildrenIDs(newSchemasToClusterGroups.get(numOfOrig), numOfOrig);
					Iterator<Integer> iter = children.iterator();
					Integer aID = (Integer)iter.next();
					Integer bID = (Integer)iter.next();
				
					//if the size of the left child isn't 1 then check to see if it fits
					ClusterGroup aClusGroup = newSchemasToClusterGroups.get(aID);
					int sizeA = aClusGroup.getNumSchemas();
					if(sizeA > 1){
						if(schemaXLocationsMap.get(aID)<=maxVal){
							if(!altTreeColors){
								//all will be blue
								colorTree(aID, gcDendrogram, blue, false);
							}else{
								if(currentColorBlue){
									//gcDendrogram.setForeground(blue);
									currentColorBlue = false;
									colorTree(aID, gcDendrogram, blue, false);
								}else{
									currentColorBlue = true;
									colorTree(aID, gcDendrogram, red, false);
								}
							}
						}else{
							determineColors(aID, gcDendrogram, maxVal);
						}
					}else{
						//hit very bottom without reaching min, draw String in alternate color
						//String schemaName = schemas.get(aID).toString();
						//String schemaName = schemas.get(actualID).toString();
						ClusterGroup schemaIDval = newSchemasToClusterGroups.get(aID);
						Iterator<Integer> schemaVal = schemaIDval.iterator();
						Integer clusterId = (Integer)schemaVal.next();
						//System.out.println("D: trying to get aID: " + aID + " from " + clusterId);
						DendrogramSchemaGUI schema = schemas.get(clusterId);
						String schemaName = schema.schema.getName();						
						
						int nameLength = schemaName.length();
						int xOffset = 0;
						if(nameLength<maxSchemaNameLength){
							xOffset = maxSchemaNameLength-nameLength;
						}
						
						Integer yName = schemaYLocationsMap.get(aID)-6;
						if(schema.isSelected()) {
							//gcDendrogram.setForeground(highlightColor);
							//gcDendrogram.setForeground(gray);
							gcDendrogram.setForeground(black);
							gcDendrogram.setFont(selectedFont);
						}
						else {
							gcDendrogram.setForeground(gray);
							//gcDendrogram.setFont(normalFont);
						}
						gcDendrogram.drawString(schemaName, xOffset*5, yName, true);
						gcDendrogram.setFont(normalFont);
						
						//Cache the schema name string bounds to enable mouse hit detection
						Point stringExtent = gcDendrogram.stringExtent(schemaName);
						if(schema.bounds != null) {							
							schema.bounds.x = xOffset*5; schema.bounds.y = yName; 
							schema.bounds.width = stringExtent.x; schema.bounds.height = stringExtent.y;
						}
						else {
							schema.bounds = new Rectangle(xOffset*5, yName, stringExtent.x, stringExtent.y);
						}
					}
					ClusterGroup bClusGroup = newSchemasToClusterGroups.get(bID);
					int sizeB = bClusGroup.getNumSchemas();
					if(sizeB > 1){
						if(schemaXLocationsMap.get(bID)<=maxVal){
							if(!altTreeColors){
								//all will be blue
								colorTree(bID, gcDendrogram, blue, false);
							}else{
								if(currentColorBlue){
									gcDendrogram.setForeground(blue);
									currentColorBlue = false;
									colorTree(bID, gcDendrogram, blue, false);
								}else{
									gcDendrogram.setForeground(red);
									currentColorBlue = true;
									colorTree(bID, gcDendrogram, red, false);
								}
							}
						}else{
							//System.out.println("at line 315");
							determineColors(bID, gcDendrogram, maxVal);
						}
					}else{
						//String schemaName = schemas.get(bID).toString();
						//String schemaName = schemas.get(actualID).toString();
						ClusterGroup schemaIDval = newSchemasToClusterGroups.get(bID);
						Iterator<Integer> schemaVal = schemaIDval.iterator();
						Integer clusterId = (Integer)schemaVal.next();
						//System.out.println("D: trying to get aID: " + aID + " from " + clusterId);
						DendrogramSchemaGUI schema = schemas.get(clusterId);
						String schemaName = schema.schema.getName();
						
						int nameLength = schemaName.length();
						int xOffset = 0;
						if(nameLength<maxSchemaNameLength){
							xOffset = maxSchemaNameLength-nameLength;
						}
						
						Integer yName = schemaYLocationsMap.get(bID)-6;
											
						if(schema.isSelected()) {
							//gcDendrogram.setForeground(highlightColor);
							//gcDendrogram.setForeground(gray);
							gcDendrogram.setForeground(black);
							gcDendrogram.setFont(selectedFont);
						}
						else {
							gcDendrogram.setForeground(gray);
							//gcDendrogram.setFont(normalFont);
						}
						gcDendrogram.drawString(schemaName, xOffset*5, yName, true);
						gcDendrogram.setFont(normalFont);
						
						//Cache the schema name string bounds to enable mouse hit detection
						Point stringExtent = gcDendrogram.stringExtent(schemaName);
						if(schema.bounds != null) {							
							schema.bounds.x = xOffset*5; schema.bounds.y = yName; 
							schema.bounds.width = stringExtent.x; schema.bounds.height = stringExtent.y;
						}
						else {
							schema.bounds = new Rectangle(xOffset*5, yName, stringExtent.x, stringExtent.y);
						}
						
					}
				//if the size of the right child isn't 1 then color everything below it
				}else{
					//only one schema, write the name in the color
					//String schemaName = schemas.get(numOfOrig).toString();
					//String schemaName = schemas.get(actualID).toString();
					ClusterGroup schemaIDval = newSchemasToClusterGroups.get(numOfOrig);
					Iterator<Integer> schemaVal = schemaIDval.iterator();
					Integer clusterId = (Integer)schemaVal.next();
					//System.out.println("D: trying to get aID: " + aID + " from " + clusterId);
					DendrogramSchemaGUI schema = schemas.get(clusterId);
					String schemaName = schema.schema.getName();
					
					int nameLength = schemaName.length();
					int xOffset = 0;
					if(nameLength<maxSchemaNameLength){
						xOffset = maxSchemaNameLength-nameLength;
					}	
					Integer yName = schemaYLocationsMap.get(numOfOrig)-6;

					if(schema.isSelected()) {
						//gcDendrogram.setForeground(highlightColor);
						//gcDendrogram.setForeground(gray);
						gcDendrogram.setForeground(black);
						gcDendrogram.setFont(selectedFont);
					}
					else {
						gcDendrogram.setForeground(gray);
						//gcDendrogram.setFont(normalFont);
					}
					gcDendrogram.drawString(schemaName, xOffset*5, yName, true);
					gcDendrogram.setFont(normalFont);
					
					//Cache the schema name string bounds to enable mouse hit detection
					Point stringExtent = gcDendrogram.stringExtent(schemaName);
					if(schema.bounds != null) {						
						schema.bounds.x = xOffset*5; schema.bounds.y = yName; 
						schema.bounds.width = stringExtent.x; schema.bounds.height = stringExtent.y;
					}
					else {
						schema.bounds = new Rectangle(xOffset*5, yName, stringExtent.x, stringExtent.y);
					}
				}
		}		
	}	
	
	public void drawDendrogram(GC gcDendrogram){
		//determining the distance between leaves
		//TODO: y distance between schema names is computed here
		int distanceBetween = (canvasHeight-5)/leaves.length;
		int firstDistance = distanceBetween/2;

		Font normalFont = getFont();
		
		//drawing the entire dendrogram in gray first
		gcDendrogram.setLineStyle(SWT.LINE_SOLID);
		gcDendrogram.setForeground(gray);

		schemaXLocationsMap = new HashMap<Integer, Integer>();
		schemaYLocationsMap = new HashMap<Integer, Integer>();
		//this is really dendro "points" to cluster groups
		newSchemasToClusterGroups = new HashMap<Integer, ClusterGroup>();
		firstChild = new HashMap<Integer, Integer>();
		secondChild = new HashMap<Integer, Integer>();
		//only need a mapping from points in the dendrogram to schemaNumbers so we can pull out names
		fromNewSchemasToClustertoSchemaID = new HashMap<Integer, Integer>(); //schemaID to dendroPoint(i.e., newSchemasToClusterGroups)
		Integer yDist;

		//determining the maximum name length to help right align schema names
		int maxNameLength = 0;
		if(leaves != null){
			for(int i=0; i<leaves.length; i++) {
				Integer schemaNum = Integer.parseInt(leaves[i].getLabel());
				//Integer schemaNum = ((SchemaDendrogramNode)leaves[i]).
				String schemaName = schemas.get(schemaNum).schema.toString();
				int schemaNameLength = schemaName.length();
				if(schemaNameLength>maxNameLength){
					maxNameLength = schemaNameLength;
				}
			}
		}

		maxSchemaNameLength = maxNameLength;
		
		//drawing the leaves, marking their locations
		if(leaves != null) {
			for(int i=0; i<leaves.length; i++) {
				Integer schemaNum = Integer.parseInt(leaves[i].getLabel());
				DendrogramSchemaGUI schema = schemas.get(schemaNum);
				String schemaName = schema.schema.getName();
				//just mapping this so we can later get the schemaName, dendrogram only cares about point
				fromNewSchemasToClustertoSchemaID.put(schemaNum, i);
	
				yDist = firstDistance+(i*distanceBetween);			
				//schemaYLocationsMap.put(schemaNum, yDist);
				//schemaXLocationsMap.put(schemaNum, (maxNameLength+5)*5);
				schemaYLocationsMap.put(i, yDist);
				schemaXLocationsMap.put(i, (maxNameLength+5)*5);
				//System.out.println("created new ID:" + i);
				int nameLength = schemaName.length();
				int xOffset = 0;
				if(nameLength<maxNameLength){
					xOffset = maxNameLength-nameLength;
				}
				
				if(schema.isSelected()) {
					//gcDendrogram.setForeground(highlightColor);
					//gcDendrogram.setForeground(gray);
					gcDendrogram.setForeground(black);
					gcDendrogram.setFont(selectedFont);
				}
				else {
					gcDendrogram.setForeground(gray);
					//gcDendrogram.setFont(normalFont);
				}
				gcDendrogram.drawString(schemaName, xOffset*5, yDist-6, true);
				gcDendrogram.setFont(normalFont);
				
				//Cache the schema name string bounds to enable mouse hit detection
				Point stringExtent = gcDendrogram.stringExtent(schemaName);
				if(schema.bounds != null) {					
					schema.bounds.x = xOffset*5; schema.bounds.y = yDist-6; 
					schema.bounds.width = stringExtent.x; schema.bounds.height = stringExtent.y;
				}
				else {
					schema.bounds = new Rectangle(xOffset*5, yDist-6, stringExtent.x, stringExtent.y);
				}
			}
		}

		int startDendroXPoint = (maxNameLength+5)*5;
		int numXpixels = (canvasWidth-20) -  startDendroXPoint;
		
		//scale no smaller than 100 pixels wide
		if(numXpixels < 100){
			numXpixels = 100;
		}
		
		
		int step = 0;
		for(ClusterStep cs : cc){
			for(ClusterGroup cg : cs.getClusterGroups()){
				if(step==0){
					Iterator<Integer> cgIter = cg.iterator();
					Integer clusterId = (Integer)cgIter.next();
					//newSchemasToClusterGroups.put(clusterId, cg);
					Integer translatedID = fromNewSchemasToClustertoSchemaID.get(clusterId);
					newSchemasToClusterGroups.put(translatedID, cg);
					firstChild.put(translatedID, null);
					secondChild.put(translatedID, null);
					//note that we now have a continuously numbered map, where the cg's use the true schema numbers
					//the fake maps we create below have nothing to do with real schemaID numbers
					//all of the real schemaID numbers are put in at this step
				}else{
					//each schema is in it's own cluster at step 0, so we are not interested in that
					//now need to find the two schemas that were combined
					int groupSize = cg.getNumSchemas();
					if(groupSize == 2){
						//connecting two schemas at the bottom level
						Iterator<Integer> cgIter = cg.iterator();
						Integer clusterAid = (Integer)cgIter.next();
						Integer clusterBid = (Integer)cgIter.next();
						
						Integer translatedID_A = fromNewSchemasToClustertoSchemaID.get(clusterAid);
						Integer translatedID_B = fromNewSchemasToClustertoSchemaID.get(clusterBid);
						//translated IDs are really the points in the dendrogram that correspond to that schemaIDs location
						
						//if portrait
						//Integer aX = schemaXLocationsMap.get(clusterAid);
						//Integer aY = schemaYLocationsMap.get(clusterAid);
						//Integer bX = schemaXLocationsMap.get(clusterBid);
						//Integer bY = schemaYLocationsMap.get(clusterBid);
						Integer aX = schemaXLocationsMap.get(translatedID_A);
						Integer aY = schemaYLocationsMap.get(translatedID_A);
						Integer bX = schemaXLocationsMap.get(translatedID_B);
						Integer bY = schemaYLocationsMap.get(translatedID_B);							

						//this will be between 0.0 and 1.0
						double valXNotScaled = cg.getDistance();
						int dendroX = ((int)(numXpixels*valXNotScaled)) + startDendroXPoint;
						
						gcDendrogram.drawLine(aX, aY, dendroX, aY);
						gcDendrogram.drawLine(bX, bY, dendroX, bY);
						gcDendrogram.drawLine(dendroX, aY, dendroX, bY);
					
						//drawing the tick mark at top of canvas
						gcDendrogram.setForeground(black);
						gcDendrogram.setLineWidth(1);
						//gcDendrogram.setAlpha(32);
						gcDendrogram.drawLine(dendroX, 0, dendroX, 6);
						gcDendrogram.setForeground(gray);
						gcDendrogram.setLineWidth(1);
						gcDendrogram.setAlpha(255);

						//what do we put in to mark A and B as a new schema?
						//let's create a new schema ID
						int numSchemas = schemaXLocationsMap.size();
					
						//Note: it does not matter if it does match: cluster group schemaIDs are totally separated from labels for the map
						boolean uniqueIDFound = false;
						Integer stepLabel = numSchemas;
						while(!uniqueIDFound) {
							if(!schemaXLocationsMap.containsKey(stepLabel)){
								uniqueIDFound = true;
							}else{
								stepLabel++;
								System.err.println("should not have had to look for unique id");
							}
						}

						//need new X and Y for clustered group
						schemaXLocationsMap.put(stepLabel, dendroX);
						schemaYLocationsMap.put(stepLabel, (Integer)((aY+bY)/2));

						newSchemasToClusterGroups.put(stepLabel, cg);
						firstChild.put(stepLabel, translatedID_A);
						secondChild.put(stepLabel, translatedID_B);
						
						//System.out.println("created new ID:" + stepLabel + "putting " + cg);
					}else{
						//need to find the cluster groups from the previous step that make up this cluster group
						//note this could be a cluster of size 3 if 2 were merged with one
						
						//go backwards through what was put in since last step should be most recent
						//search for the two steps that make up this cluster group
						int clusA = -1;
						int clusB = -1;
						
						ClusterGroup temp = null;
						for(int i = newSchemasToClusterGroups.size()-1; i>=0; i--){
						//for(ClusterGroup currCG : newSchemasToClusterGroups.values()) {
						//for(Map.Entry<Integer, ClusterGroup> cgEntry : newSchemasToClusterGroups.entrySet()) {
							ClusterGroup currCG = newSchemasToClusterGroups.get(i);
							//ClusterGroup currCG = cgEntry.getValue();							
							if(currCG == null) {
								System.err.println("A Warning in DendrogramCanvas: encountered null ClusterGroup " + i);
							} else if(cg.contains(currCG)){
								//System.out.println(cg + " contains " + currCG);
								//clusA = cgEntry.getKey();
								clusA = i;
								Collection<Integer> origIDs = cg.getSchemaIDs();
								temp = new ClusterGroup(origIDs);
								temp.removeSchemas(currCG.getSchemaIDs());
								break;
							}
						}

						for(int i = newSchemasToClusterGroups.size()-1; i>=0; i--){
						//for(Map.Entry<Integer, ClusterGroup> cgEntry : newSchemasToClusterGroups.entrySet()) {
							ClusterGroup currCG = newSchemasToClusterGroups.get(i);
							//ClusterGroup currCG = cgEntry.getValue();						
							if(temp == null || currCG == null) {
								System.err.println("B Warning in DendrogramCanvas: encountered null ClusterGroup " + i);
							}else if(temp.contains(currCG)) {
								//System.out.println(cg + " contains " + currCG);
								clusB = i;
								//clusB = cgEntry.getKey();
								break;
							}
						}	

							
						//if portrait
						Integer aX = schemaXLocationsMap.get(clusA);
						Integer aY = schemaYLocationsMap.get(clusA);
						Integer bX = schemaXLocationsMap.get(clusB);
						Integer bY = schemaYLocationsMap.get(clusB);						
						
						if(aX == null || aY == null || bX == null || bY == null) {
							System.err.println("C Error in DendgrogramCanvas: Encountered null schemaID");
							return;
						}
	
						double valXNotScaled = cg.getDistance();
						//int dendroX = (int)(numXpixels*valXNotScaled);
						int dendroX = ((int)(numXpixels*valXNotScaled)) + startDendroXPoint;
						
						//System.out.println("drawing connector between group other than 3");						
						//System.out.println("A: " + aX + "," + aY + " B: " + bX + ", " + bY);

						gcDendrogram.drawLine(aX, aY, dendroX, aY);
						gcDendrogram.drawLine(bX, bY, dendroX, bY);
						gcDendrogram.drawLine(dendroX, aY, dendroX, bY);
				
						//drawing the tick mark at top of canvas
						gcDendrogram.setForeground(black);
						gcDendrogram.setLineWidth(1);
						//gcDendrogram.setAlpha(32);
						gcDendrogram.drawLine(dendroX, 0, dendroX, 6);
						gcDendrogram.setForeground(gray);
						gcDendrogram.setLineWidth(1);
						gcDendrogram.setAlpha(255);
						
						//what do we put in to mark A and B as a new schema?
						//let's create a new schema ID						
						int numSchemas = schemaXLocationsMap.size();						
						boolean uniqueIDFound = false;
						Integer stepLabel = numSchemas;
						while(!uniqueIDFound) {
							if(!schemaXLocationsMap.containsKey(stepLabel)){
								uniqueIDFound = true;
							}else{
								stepLabel++;
								System.err.println("should not have had to look for a unique id");
							}
						}	
						//System.out.println("Created new ID: " + stepLabel);
						
						schemaXLocationsMap.put(stepLabel, dendroX);
						schemaYLocationsMap.put(stepLabel, (Integer)((aY+bY)/2));
						
						newSchemasToClusterGroups.put(stepLabel, cg);
						firstChild.put(stepLabel, clusA);
						secondChild.put(stepLabel, clusB);
						//System.out.println("putting " + cg + " at " + stepLabel);
					}	
				}
			}
			step++; 
		}	
				
		//end Dendrogram creation code here		
		
		//when coloring the lines we want to go through the clusters in the reverse order from what they were put in
		//then we can do all clusters below that
		
		currentColorBlue = true;
		
		//this is the X location of the maximum simliarity bar
		int maxdendroBarXloc = ((int)(numXpixels*maxBarDVal)) + startDendroXPoint;
						
		//gcDendrogram.setLineStyle(SWT.LINE_DOT);
		gcDendrogram.setLineWidth(2);
		gcDendrogram.setForeground(black);
		gcDendrogram.setLineStyle(SWT.LINE_DASH);
//		gcDendrogram.setLineStyle(SWT.LINE_DASHDOT);
		gcDendrogram.drawLine(maxdendroBarXloc, 0, maxdendroBarXloc, canvasHeight);
		//System.out.println("drawing max slider bar: " + maxdendroBarXloc + " max y: " + canvasHeight + " min y: " + 0);
		
		
		//draw the location of the minimum similarity bar
		int mindendroBarXloc = ((int)(numXpixels*minBarDVal)) + startDendroXPoint;
		gcDendrogram.setLineWidth(1);
		gcDendrogram.setForeground(gray);
		gcDendrogram.drawLine(mindendroBarXloc, 0, mindendroBarXloc, canvasHeight);
		
		
		//start with the max cluster, the last put in
		//and see if everything is below it
		int maxSchemaID = schemaXLocationsMap.size()-1;
		//System.out.println("maxID" + maxSchemaID);
		//System.out.println(newSchemasToClusterGroups);
		determineColors(maxSchemaID, gcDendrogram, maxdendroBarXloc);	
	
	}
	
	//note that min and max must be between 0.0 and 1.0
	public void render(GC gc, Composite parent) {		
		//Rectangle bounds = getClientArea();
		Rectangle bounds = this.dendrogramImage.getBounds();
		canvasWidth = bounds.width;
		canvasHeight = bounds.height;
		
		setBackground(white);
		setForeground(blue);
		
		drawBackground(gc, bounds.x, bounds.y, bounds.width, bounds.height);
		
		//always start with blue
		//this prevents color from switching on repaint
		//altTreeColors = false;
		currentColorBlue = true;
		//shadeBackground = true;
		drawDendrogram(gc);
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
	
	/*
	public void addAffinityEventListener(AffinityListener listener) {
		this.affinityEventListeners.add(listener);
	}
	
	public boolean removeAffinityEventListener(AffinityListener listener) {
		return this.affinityEventListeners.remove(listener);
	}*/
	
	protected void fireSelectionChangedEvent() {
		if(!selectionChangedListeners.isEmpty()) {
			SelectionChangedEvent event = new SelectionChangedEvent(this);
			if(!selectedSchemas.isEmpty())
				event.selectedSchemas = selectedSchemas;
			if(selectedCluster != null) {
				event.selectedClusters = new LinkedList<ClusterGroup>();
				event.selectedClusters.add(selectedCluster);
			}
			for(SelectionChangedListener listener : selectionChangedListeners) 
				listener.selectionChanged(event);
		}
	}
	
	private static final int leftButtonCtrlStateMask = SWT.BUTTON1 | SWT.CONTROL;
	
	protected void fireSelectionClickedEvent(int button, int stateMask, int clickCount, int x, int y) {
		if(!selectionClickedListeners.isEmpty()) {
			SelectionClickedEvent event = new SelectionClickedEvent(this, button, 
					(stateMask == leftButtonCtrlStateMask) ? true : false, clickCount, x, y);			
			if(!selectedSchemas.isEmpty())
				event.selectedSchemas = selectedSchemas;
			if(selectedCluster != null) {
				event.selectedClusters = new LinkedList<ClusterGroup>();
				event.selectedClusters.add(selectedCluster);
			}
			for(SelectionClickedListener listener : selectionClickedListeners) 
				listener.selectionClicked(event);
		}
	}
	
	/*
	protected void fireSchemaDoubleClickedEvent(Integer schemaID) {
		if(!this.affinityEventListeners.isEmpty()) {
			for(AffinityListener listener : this.affinityEventListeners) {				
				listener.schemaDoubleClicked(schemaID, this);
			}
		}
	}
	
	protected void fireSchemaSelectedEvent(Integer schemaID, boolean selected) {
		if(!this.affinityEventListeners.isEmpty()) {
			for(AffinityListener listener : this.affinityEventListeners) {
				if(selected)
					listener.schemaSelected(schemaID, this);
				else
					listener.schemaUnselected(schemaID, this);
			}
		}
	}
	
	protected void fireClusterDoubleClickedEvent(ClusterGroup cluster) {
		if(!this.affinityEventListeners.isEmpty()) {
			for(AffinityListener listener : this.affinityEventListeners)  {
				listener.clusterDoubleClicked(cluster, this);
			}
		}
	}
	
	protected void fireClusterSelectedEvent(ClusterGroup cluster, boolean selected) {
		if(!this.affinityEventListeners.isEmpty()) {
			for(AffinityListener listener : this.affinityEventListeners) {
				if(selected)
					listener.clusterSelected(cluster, this);
				else
					listener.clusterUnselected(cluster, this);
			}
		}
	}*/
	
	/**
	 * A mouse listener to determine if a schema or cluster was double-clicked.
	 *
	 */
	private class DendrogramMouseListener extends MouseAdapter {
		/*public boolean schemaContainsPoint(Schema schema, int x, int y) {			
			return schemaBounds.get(schema.getId()).contains(x, y);			
		}*/
		
		public ClusterGroup getFirstClusterContainsPoint(int x, int y) {
			ClusterGroup clickedCluster = null;
			//System.out.println("For (" + x + ", " + y + ")");
			for(Map.Entry<ClusterGroup, Rectangle> entry : clusterBounds.entrySet()) {
				//System.out.println("Evaluating cluster: " + entry.getKey());
				if(entry.getValue().contains(x, y)) { 
					//return entry.getKey();
					//System.out.println("Clusters value: " + entry.getValue());
					ClusterGroup cluster = entry.getKey();
					if(clickedCluster == null || cluster.getNumSchemas() < clickedCluster.getNumSchemas())
						clickedCluster = cluster;
				}
			}
			//return null;
			return clickedCluster;
		}
		
		@Override
		//public void mouseDown(MouseEvent e) {}
		public void mouseUp(MouseEvent e) {					
			//First determine if a schema was clicked
			Schema clickedSchema = null;
			for(DendrogramSchemaGUI schema : schemas.values()) {			
				int scrollAdjustedY = e.y + -1*(origin.y);
				
				if(schema.bounds.contains(e.x, scrollAdjustedY)) {
					clickedSchema = schema.schema;					
					if(!schema.selected) {	
						//A single schema was selected
						
						//Unselect any previously selected schemas or clusters
						unselectAllSchemasAndClusters();						
						selectedSchemas.add(schema.schema.getId());
						
						//Mark the schema as selected
						schema.setSelected(true);						
						
						redraw();
						update();
						
						//Notify any SelectionChangedListeners that a single schema is currently selected
						fireSelectionChangedEvent();
					}					
					
					//Notify any SelectionClickedListeners that a single schema was double-clicked					
					if(e.count == 2)
						fireSelectionClickedEvent(e.button, e.stateMask, e.count, e.x, e.y);
						//fireSchemaDoubleClickedEvent(schema.getSchemaID());
					
					break;
				}					
			}							

			//If no schema was clicked, determine if a cluster was clicked
			if(clickedSchema == null) {	
				//note that because the dendrogram can scroll, we need to adjust y based on scrolling
				//System.out.println("about to adjust the y used: " + e.y);
				int scrollAdjustedY = e.y + -1*(origin.y);
				//System.out.println("new y is: " + scrollAdjustedY);
				
				//ClusterGroup cluster = getFirstClusterContainsPoint(e.x, e.y);
				ClusterGroup cluster = getFirstClusterContainsPoint(e.x, scrollAdjustedY);
				if(cluster != null) {
					if(cluster != selectedCluster) {
						//A single cluster was selected		
						
						//Unselect any previously selected schemas or clusters
						unselectAllSchemasAndClusters();										
						
						//Mark the cluster as selected						
						selectedCluster = cluster;
						
						redraw();
						update();
						
						//Notify any SelectionChangedListeners that a single cluster is currently selected
						fireSelectionChangedEvent();
					}					
					
					//Notify any SelectionClickedListeners that a single cluster was double-clicked				
					if(e.count == 2)
						//fireSelectionClickedEvent(e.button, e.stateMask, e.count, e.x, e.y);
						fireSelectionClickedEvent(e.button, e.stateMask, e.count, e.x, scrollAdjustedY);

						//fireClusterDoubleClickedEvent(cluster.cg);
				}
				else {
					//No schema or cluster was clicked, so unhighlight any previously highlighted schema or cluster
					if(!selectedSchemas.isEmpty() || selectedCluster != null) {					 
						unselectAllSchemasAndClusters();
						fireSelectionChangedEvent();
						redraw();
						update();
					}
					/*
					if(highlightedSchema != null)  {
						Schema prevHighlightedSchema = highlightedSchema;
						highlightedSchema = null;
						redraw();
						update();
						
						//Notify any AffinityEventListeners that a schema was unselected						
						fireSchemaSelectedEvent(prevHighlightedSchema.getId(), false);						
					}
					else if(highlightedCluster != null) {
						ClusterGroup prevHighlightedCluster = highlightedCluster;
						setClusterHighlighted(highlightedCluster, false);
						redraw();
						update();
						
						//Notify any AffinityEventListeners that a cluster was unselected
						fireClusterSelectedEvent(prevHighlightedCluster, false);
					}*/
				}
			}
		}

		//@Override			
		//public void mouseDoubleClick(MouseEvent e) {}
	}
}
