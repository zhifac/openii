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
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.ClusterStep;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.view.IClusterView;
import org.mitre.affinity.view.dendrogram.model.ClusterObjectDendrogram;
import org.mitre.affinity.view.dendrogram.model.ClusterObjectDendrogramNode;
import org.mitre.affinity.view.event.ClusterDistanceChangeEvent;
import org.mitre.affinity.view.event.ClusterDistanceChangeListener;
import org.mitre.affinity.view.event.SelectionChangedEvent;
import org.mitre.affinity.view.event.SelectionChangedListener;
import org.mitre.affinity.view.event.SelectionClickedEvent;
import org.mitre.affinity.view.event.SelectionClickedListener;
import org.mitre.affinity.view.swt.SWTUtils.TextSize;
import org.eclipse.swt.graphics.Rectangle;

/**
 * Renders a dendrogram from a given set of clusters. 
 * 
 * @author BETHYOST
 *
 */
public class DendrogramCanvas<K extends Comparable<K>, V> extends Canvas implements IClusterView<K, V>, ClusterDistanceChangeListener {
	
	private Composite parent;
	
	/** The clusters */
	private ClustersContainer<K> clusters;	

	/** Maps original cluster object ID to its gui */
	private Map<K, DendrogramClusterObjectGUI<K, V>> clusterObjects;	
	
	private ClusterObjectDendrogramNode<K>[] leaves; //this provides the cluster objects in the order they appear at the bottom of the dendrogram	
	
	/** Maps translated cluster object ID to its x location */
	protected Map<Integer, Integer> clusterObjectXLocationsMap;
	
	/** Maps translated cluster object ID to its y location */
	protected Map<Integer, Integer> clusterObjectYLocationsMap;
	
	/** Maps translated cluster object ID to its cluster group */
	protected Map<Integer, ClusterGroup<K>> newClusterObjectsToClusterGroups;
	
	/** Maps original cluster object IDs to translated object IDs */
	protected Map<K, Integer> fromNewClusterObjectsToClustertoObjectID;
	
	/** Maps translated cluster object IDs to translated object IDs for the first child in a cluster */
	protected Map<Integer, Integer> firstChild;
	
	/** Maps translated cluster object IDs to translated object IDs for the second child in a cluster */
	protected Map<Integer, Integer> secondChild;
	
	private int maxLabelLength;
	
	private boolean currentColorBlue;
	
	private boolean altTreeColors;
	
	private boolean shadeBackground;	
	
	/** We render the dendrogram on an image, and the image is scrolled */
	private Image dendrogramImage;
	
	/** The highlight color */
	private Color highlightColor;
	
	/** Listeners that have registered to received SelectionChangedEvents from this component */
	private List<SelectionChangedListener<K>> selectionChangedListeners;
	
	/** Listeners that have registered to received SelectionClickedEvents from this component */
	private List<SelectionClickedListener<K>> selectionClickedListeners;
	
	/** The currently selected cluster objects */	
	private Collection<K> selectedClusterObjects;
	
	/** The currently selected cluster */
	private ClusterGroup<K> selectedCluster = null;	
	
	/** Cached list of the bounding rectangles for all clusters to enable mouse hit detection  */
	private Map<ClusterGroup<K>, Rectangle> clusterBounds;
	
	/** Font used to render a cluster object name when the cluster object is selected */
	private Font selectedFont = new Font(Display.getDefault(), new FontData("Times", 18, SWT.ITALIC));
	
	/** Vertical scroll bar	 */
	private ScrollBar scrollBar;
	
	private Point origin;
	
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
	
	public DendrogramCanvas(Composite par, int style) {
		this(par, style, null, null);
	}
	
	//a DendroCanvas expects a ClustersContainer that has duplicate clusters removed
	public DendrogramCanvas(Composite par, int style, ClustersContainer<K> clusters, 
			List<DendrogramClusterObjectGUI<K, V>> clusterObjects) {   //ClusterObjectDendrogramNode<K>[] leafs) {
		super(par, style | SWT.V_SCROLL);	
		this.parent = par;
		this.selectedClusterObjects = new LinkedList<K>();		
		this.selectionChangedListeners = new LinkedList<SelectionChangedListener<K>>();
		this.selectionClickedListeners = new LinkedList<SelectionClickedListener<K>>();	
		this.clusterBounds = new HashMap<ClusterGroup<K>, Rectangle>();
		this.clusterObjects = new HashMap<K, DendrogramClusterObjectGUI<K, V>>();
		
		//starting value is set by default to 1.0 and 0.0
		maxBarDVal = 1.00;
		minBarDVal = 0.00;
		
		//Default is true for showing alternating colors and background fill
		altTreeColors = true;
		shadeBackground = true;
		
		//Initialize colors
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
		
		this.dendrogramImage = new Image(getDisplay(), 200, 300);
		this.origin = new Point(0, 0); //Scrolling origin
		
		//Create vertical scrollbar
		scrollBar = getVerticalBar();
		scrollBar.addListener(SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				int vSelection = scrollBar.getSelection();
				int destY = -vSelection - origin.y;
				Rectangle rect = getBounds();
				scroll(0, destY, 0, 0, rect.width, rect.height, false);
				origin.y = -vSelection;
			}
		});
		
		//Add mouse listener
		addMouseListener(new DendrogramMouseListener());
		
		//Add paint listener
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
		
		//Add resize listener
		addListener(SWT.Resize,  new Listener () {
			public void handleEvent (Event e) {
				resizeDendrogramImage();
			}
		});
		
		setClusterObjectsAndClusters(clusterObjects, clusters);
	}
	
	public ClustersContainer<K> getClusters() {
		return clusters;
	}
	
	public void setClusterObjectsAndClusters(List<DendrogramClusterObjectGUI<K, V>> clusterObjects,
			ClustersContainer<K> clusters) {
		this.clusters = clusters;
		this.clusterObjects.clear();
		List<V> objects = new LinkedList<V>();
		if(clusterObjects != null) {
			for(DendrogramClusterObjectGUI<K, V> clusterObject : clusterObjects) { 
				this.clusterObjects.put(clusterObject.getObjectID(), clusterObject);
				objects.add(clusterObject.getClusterObject());
			}
		}
		if(clusters != null && clusterObjects != null) {
			//dendrogram takes the leaves in order, uses IVC code to get these leaves - this would be easy to replace if need be
			Collection<K> objectIDs = new LinkedList<K>();
			objectIDs.addAll(this.clusterObjects.keySet());		
			this.leaves = new ClusterObjectDendrogram<K, V>(objects, objectIDs, clusters).getLeaves();
		}		
		resizeDendrogramImage();		
		
		//initial rendering of the dendrogram	
		GC gc = new GC(dendrogramImage);
		render(gc, parent);
		gc.dispose();
	}
	
	/** Called when the clusters and cluster objects are set or when the canvas size changes */
	protected void resizeDendrogramImage() {
		Rectangle client = getClientArea();
		
		//int fontHeight = getFont().getFontData()[0].getHeight();
		GC imageGC = new GC(dendrogramImage);
		int fontHeight = imageGC.stringExtent("X").y;
		imageGC.dispose();
		
		int desiredHeight = client.height;				
		if(leaves != null && leaves.length > 0) {
			int distanceBetween = (client.height-5)/leaves.length;
			int minDistanceBetween = fontHeight + 4;					
			if(distanceBetween < minDistanceBetween) {
				desiredHeight = minDistanceBetween * leaves.length + 5;
			}
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
		scrollBar.setMaximum (rect.height);
		//hBar.setThumb (Math.min (rect.width, client.width));
		scrollBar.setThumb (Math.min (rect.height, client.height));
		//int hPage = rect.width - client.width;
		int vPage = rect.height - client.height;
		//int hSelection = hBar.getSelection ();
		int vSelection = scrollBar.getSelection ();
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
	
	public boolean isShowClusters() {
		return altTreeColors;
	}

	public void setShowClusters(boolean showClusters){
		altTreeColors = showClusters;
	}
	
	public boolean isFillClusters() {
		return shadeBackground;
	}
	
	@Override
	public void setFillClusters(boolean fillClusters) {
		shadeBackground = fillClusters;
	}	
	
	@Override
	public void setTextSize(TextSize textSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TextSize getTextSize() {
		// TODO Auto-generated method stub
		return null;
	}	
	
	/**
	 * Select a single cluster object.  Any previously selected cluster objects are unselected.
	 * 
	 * @param objectID
	 */	
	public void setSelectedClusterObject(K objectID) {
		//Unselect any previously selected cluster objects
		//TODO: when this is called, AUTOSCROLL
		unselectAllClusterObjects();
		
		if(objectID != null) {
			DendrogramClusterObjectGUI<K, V> clusterObject =  clusterObjects.get(objectID);
			if(clusterObject != null) {
				clusterObject.setSelected(true);
				selectedClusterObjects.add(objectID);
			}
		}
	}
	
	/**
	 * Select a group of cluster objects.  Any previously selected cluster objects are unselected.
	 * 
	 * @param objectIDs
	 */
	public void setSelectedClusterObjects(Collection<K> objectIDs) {
		//Unselect any previously selected cluster objects
		unselectAllClusterObjects();		
		if(objectIDs != null && clusterObjects != null) {
			for(K objectID : objectIDs) {
				DendrogramClusterObjectGUI<K, V> clusterObject =  clusterObjects.get(objectID);
				if(clusterObject != null) {
					clusterObject.setSelected(true);
					selectedClusterObjects.add(objectID);
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
		this.unselectAllClusters();		
		this.selectedCluster = cluster;				
	}
	
	/**
	 * Unselect all cluster objects. 
	 */
	public void unselectAllClusterObjects() {
		for(K objectID : selectedClusterObjects) {
			DendrogramClusterObjectGUI<K, V> clusterObject = clusterObjects.get(objectID);
			if(clusterObject != null)
				clusterObject.setSelected(false);
		}
		selectedClusterObjects.clear();
	}
	
	/**
	 * Unselect all clusters.
	 */
	public void unselectAllClusters() {
		this.selectedCluster = null;
	}
	
	/**
	 * Unselect all cluster objects and clusters.
	 */
	public void unselectAllClusterObjectsAndClusters() {
		unselectAllClusterObjects();
		unselectAllClusters();
	}
	
	//This method takes a cluster group and finds the ID's of it's children
	//The ids it finds are the ones specific to this class used in the clusterObjectXLocationsMap etc.
	//This list will always contain exactly two Integers, one for each child in this binary tree
	//The order does not indicate anything
	public ArrayList<Integer> getChildrenIDs(ClusterGroup<K> cg, Integer objectID) {
		if(firstChild != null && secondChild != null) {
			ArrayList<Integer> objectIDs = new ArrayList<Integer>();
			objectIDs.add(firstChild.get(objectID));
			objectIDs.add(secondChild.get(objectID));
			return objectIDs;
		}
		return null;
	}
	
	//given a translated cluster object ID, draw in a given color all the way down that tree
	protected void colorTree(Integer objectID, GC gcDendrogram, Color color, boolean useHighlightColor) {
		if(clusterObjects == null || clusterObjects.isEmpty()) {
			return;
		}
		
		//get children		
		//if there are at least 2 cluster objects
		//if there is only one then you've hit the bottom of the tree and do nothing
		ClusterGroup<K> testClusGroupSize = newClusterObjectsToClusterGroups.get(objectID);
		testClusGroupSize.setDendroColor(color);		
		
		//Change the color to the highlight color if this is the highlighted cluster
		if(testClusGroupSize == selectedCluster) {
			useHighlightColor = true;
		}
		
		//color children's connector		
		gcDendrogram.setForeground(color);
		gcDendrogram.setLineStyle(SWT.LINE_SOLID);
		gcDendrogram.setLineWidth(3);
		
		Font normalFont = getFont();
		
		int currXVal = clusterObjectXLocationsMap.get(objectID);
		int sizeOrig = testClusGroupSize.getNumClusterObjects();
			if(sizeOrig > 1) {
				ArrayList<Integer> children = getChildrenIDs(newClusterObjectsToClusterGroups.get(objectID), objectID);
				Iterator<Integer> iter = children.iterator();
				Integer aID = iter.next();
				Integer bID = iter.next();
			
				//if portrait then draw the connector between children this way
				Integer aX = clusterObjectXLocationsMap.get(aID);
				Integer aY = clusterObjectYLocationsMap.get(aID);
				Integer bX = clusterObjectXLocationsMap.get(bID);
				Integer bY = clusterObjectYLocationsMap.get(bID);
				
				int rectY;
				int rectHeight;

				if(aY < bY) {
					rectY = aY;
					rectHeight = bY-aY;
				} else {
					rectY = bY;
					rectHeight = aY-bY;
				}
				
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
				
				if(useHighlightColor) {
					gcDendrogram.setForeground(highlightColor);
				}
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
				ClusterGroup<K> aClusGroup = newClusterObjectsToClusterGroups.get(aID);
				int sizeA = aClusGroup.getNumClusterObjects();
				if(sizeA > 1) {
					colorTree(aID, gcDendrogram, color, useHighlightColor);
				}
				else {
					ClusterGroup<K> clusterObjectIDval = newClusterObjectsToClusterGroups.get(aID);
					Iterator<K> clusterObjectVal = clusterObjectIDval.iterator();
					K clusterId = clusterObjectVal.next();
					DendrogramClusterObjectGUI<K, V> clusterObject = clusterObjects.get(clusterId);
					String name = clusterObject.getLabel();
					
					int nameLength = name.length();
					int xOffset = 0;
					if(nameLength<maxLabelLength){
						xOffset = maxLabelLength - nameLength;
					}					
					
					//Render the cluster object name label 
					Integer yName = clusterObjectYLocationsMap.get(aID) - 6;
					gcDendrogram.setBackground(lightGray);		
					
					if(clusterObject.isSelected()) {
						//gcDendrogram.setForeground(highlightColor);
						gcDendrogram.setForeground(black);
						gcDendrogram.setFont(selectedFont);
					}
					else {
						gcDendrogram.setForeground(black);
						//gcDendrogram.setFont(normalFont);
					}
					gcDendrogram.drawString(name, xOffset*5, yName, true);
					gcDendrogram.setFont(normalFont);
					
					//Cache the label name string bounds to enable mouse hit detection
					Point stringExtent = gcDendrogram.stringExtent(name);				
					if(clusterObject.bounds != null) {						
						clusterObject.bounds.x = xOffset*5; clusterObject.bounds.y = yName; 
						clusterObject.bounds.width = stringExtent.x; clusterObject.bounds.height = stringExtent.y;
					}
					else {
						clusterObject.bounds =	new Rectangle(xOffset*5, yName, stringExtent.x, stringExtent.y);
					}
					
					gcDendrogram.setBackground(white);
				}
				
				int sizeB = aClusGroup.getNumClusterObjects();
				if(sizeB > 1) {
					colorTree(bID, gcDendrogram, color, useHighlightColor);
				} else {					
					ClusterGroup<K> clusterObjectIDval = newClusterObjectsToClusterGroups.get(bID);
					Iterator<K> clusterObjectVal = clusterObjectIDval.iterator();
					K clusterId = clusterObjectVal.next();
					//System.out.println("D: trying to get aID: " + aID + " from " + clusterId);
					DendrogramClusterObjectGUI<K, V> clusterObject = clusterObjects.get(clusterId);
					String name = clusterObject.getLabel();
					
					int nameLength = name.length();
					int xOffset = 0;
					if(nameLength < maxLabelLength){
						xOffset = maxLabelLength - nameLength;
					}
					
					Integer yName = clusterObjectYLocationsMap.get(bID)-6;
					gcDendrogram.setBackground(lightGray);
					if(clusterObject.isSelected()) {
						//gcDendrogram.setForeground(highlightColor);
						gcDendrogram.setForeground(black);
						gcDendrogram.setFont(selectedFont);
					}
					else {
						gcDendrogram.setForeground(black);
						gcDendrogram.setFont(normalFont);
					}
					gcDendrogram.drawString(name, xOffset*5, yName, true);
					gcDendrogram.setFont(normalFont);
					
					//Cache the cluster object label name string bounds to enable mouse hit detection
					Point stringExtent = gcDendrogram.stringExtent(name);
					if(clusterObject.bounds != null) {						
						clusterObject.bounds.x = xOffset*5; clusterObject.bounds.y = yName; 
						clusterObject.bounds.width = stringExtent.x; clusterObject.bounds.height = stringExtent.y;
					}
					else {
						clusterObject.bounds = new Rectangle(xOffset*5, yName, stringExtent.x, stringExtent.y);
					}
					
					gcDendrogram.setBackground(white);
				}
			//if the size of the right child isn't 1 then color everything below it
			} else {
				//only one cluster object, write the name in the color				
				ClusterGroup<K> clusterObjectIDval = newClusterObjectsToClusterGroups.get(objectID);
				Iterator<K> clusterObjectVal = clusterObjectIDval.iterator();
				K clusterId = clusterObjectVal.next();
				DendrogramClusterObjectGUI<K, V> clusterObject = clusterObjects.get(clusterId);
				String name = clusterObject.getLabel();				
				
				int nameLength = name.length();
				int xOffset = 0;
				if(nameLength < maxLabelLength){
					xOffset = maxLabelLength - nameLength;
				}
				
				Integer yName = clusterObjectYLocationsMap.get(objectID)-6;
				gcDendrogram.setBackground(lightGray);
				if(clusterObject.isSelected()) {
					gcDendrogram.setForeground(black);
					gcDendrogram.setFont(selectedFont);
				}
				else {
					gcDendrogram.setForeground(black);
				}
				gcDendrogram.drawString(name, xOffset*5, yName, true);
				gcDendrogram.setFont(normalFont);
				
				//Cache the cluster object label name string bounds to enable mouse hit detection
				Point stringExtent = gcDendrogram.stringExtent(name);
				if(clusterObject.bounds != null) {					
					clusterObject.bounds.x = xOffset*5; clusterObject.bounds.y = yName; 
					clusterObject.bounds.width = stringExtent.x; clusterObject.bounds.height = stringExtent.y;
				}
				else {
					clusterObject.bounds =	new Rectangle(xOffset*5, yName, stringExtent.x, stringExtent.y);
				}
				
				gcDendrogram.setBackground(white);
			}		
	}
	
	
	//traverse down the tree
	//when you hit something that should be colored
	//then color everything the appropriate color under that point
	//note that this is meant to take the maxstep
	protected void determineColors(Integer objectID, GC gcDendrogram, int maxVal) {
		if(clusterObjects == null || clusterObjects.isEmpty()) {
			return;
		}
		
		int clusterXVal = clusterObjectXLocationsMap.get(objectID);
		
		Font normalFont = getFont();
		
		if(clusterXVal <= maxVal){
			if(!altTreeColors) {
				//all will be blue
				colorTree(objectID, gcDendrogram, blue, false);
			} else {
				//if this is true then the dendrogram should be colored
				if(currentColorBlue) {
					currentColorBlue = false;
					if(altTreeColors) {
						colorTree(objectID, gcDendrogram, blue, false);
					} else {
						//all will be blue
						colorTree(objectID, gcDendrogram, blue, false);
					}
				} else {
					currentColorBlue = true;
					if(altTreeColors){
						colorTree(objectID, gcDendrogram, red, false);
					}else{
						//all will be blue
						colorTree(objectID, gcDendrogram, blue, false);
					}
				}
			}
		} else {
			//perhaps color the connection between numOfOrig gray
			//only get here if the parent id: numOfOrig was above the maxValue
			//check children to see if they are below bar and should be considered clustered
			ClusterGroup<K> testClusGroupSize = newClusterObjectsToClusterGroups.get(objectID);
			int sizeOrig = testClusGroupSize.getNumClusterObjects();
				if(sizeOrig > 1) {
					//split the two cluster into two
					//really we're looking for the two dendro points that make up this point
					ArrayList<Integer> children = getChildrenIDs(newClusterObjectsToClusterGroups.get(objectID), objectID);
					Iterator<Integer> iter = children.iterator();
					Integer aID = iter.next();
					Integer bID = iter.next();
				
					//if the size of the left child isn't 1 then check to see if it fits
					ClusterGroup<K> aClusGroup = newClusterObjectsToClusterGroups.get(aID);
					int sizeA = aClusGroup.getNumClusterObjects();
					if(sizeA > 1) {
						if(clusterObjectXLocationsMap.get(aID)<=maxVal) {
							if(!altTreeColors) {
								//all will be blue
								colorTree(aID, gcDendrogram, blue, false);
							} else {
								if(currentColorBlue) {
									currentColorBlue = false;
									colorTree(aID, gcDendrogram, blue, false);
								} else {
									currentColorBlue = true;
									colorTree(aID, gcDendrogram, red, false);
								}
							}
						} else {
							determineColors(aID, gcDendrogram, maxVal);
						}
					} else {
						//hit very bottom without reaching min, draw String in alternate color
						ClusterGroup<K> clusterObjectIDval = newClusterObjectsToClusterGroups.get(aID);
						Iterator<K> clusterObjectVal = clusterObjectIDval.iterator();
						K clusterId = clusterObjectVal.next();
						DendrogramClusterObjectGUI<K, V> clusterObject = clusterObjects.get(clusterId);
						String name = clusterObject.getLabel();						
						
						int nameLength = name.length();
						int xOffset = 0;
						if(nameLength < maxLabelLength){
							xOffset = maxLabelLength - nameLength;
						}
						
						Integer yName = clusterObjectYLocationsMap.get(aID)-6;
						if(clusterObject.isSelected()) {
							gcDendrogram.setForeground(black);
							gcDendrogram.setFont(selectedFont);
						}
						else {
							gcDendrogram.setForeground(gray);
						}
						gcDendrogram.drawString(name, xOffset*5, yName, true);
						gcDendrogram.setFont(normalFont);
						
						//Cache the cluster object label name string bounds to enable mouse hit detection
						Point stringExtent = gcDendrogram.stringExtent(name);
						if(clusterObject.bounds != null) {							
							clusterObject.bounds.x = xOffset*5; clusterObject.bounds.y = yName; 
							clusterObject.bounds.width = stringExtent.x; clusterObject.bounds.height = stringExtent.y;
						}
						else {
							clusterObject.bounds = new Rectangle(xOffset*5, yName, stringExtent.x, stringExtent.y);
						}
					}
					ClusterGroup<K> bClusGroup = newClusterObjectsToClusterGroups.get(bID);
					int sizeB = bClusGroup.getNumClusterObjects();
					if(sizeB > 1) {
						if(clusterObjectXLocationsMap.get(bID) <= maxVal) {
							if(!altTreeColors) {
								//all will be blue
								colorTree(bID, gcDendrogram, blue, false);
							} else {
								if(currentColorBlue) {
									gcDendrogram.setForeground(blue);
									currentColorBlue = false;
									colorTree(bID, gcDendrogram, blue, false);
								} else {
									gcDendrogram.setForeground(red);
									currentColorBlue = true;
									colorTree(bID, gcDendrogram, red, false);
								}
							}
						} else {
							determineColors(bID, gcDendrogram, maxVal);
						}
					} else {
						ClusterGroup<K> clusterObjectIDval = newClusterObjectsToClusterGroups.get(bID);
						Iterator<K> clusterObjectVal = clusterObjectIDval.iterator();
						K clusterId = clusterObjectVal.next();
						DendrogramClusterObjectGUI<K, V> clusterObject = clusterObjects.get(clusterId);
						String name = clusterObject.getLabel();
						
						int nameLength = name.length();
						int xOffset = 0;
						if(nameLength < maxLabelLength) {
							xOffset = maxLabelLength - nameLength;
						}
						
						Integer yName = clusterObjectYLocationsMap.get(bID)-6;
											
						if(clusterObject.isSelected()) {
							gcDendrogram.setForeground(black);
							gcDendrogram.setFont(selectedFont);
						}
						else {
							gcDendrogram.setForeground(gray);
						}
						gcDendrogram.drawString(name, xOffset*5, yName, true);
						gcDendrogram.setFont(normalFont);
						
						//Cache the cluster object label name string bounds to enable mouse hit detection
						Point stringExtent = gcDendrogram.stringExtent(name);
						if(clusterObject.bounds != null) {							
							clusterObject.bounds.x = xOffset*5; clusterObject.bounds.y = yName; 
							clusterObject.bounds.width = stringExtent.x; clusterObject.bounds.height = stringExtent.y;
						}
						else {
							clusterObject.bounds = new Rectangle(xOffset*5, yName, stringExtent.x, stringExtent.y);
						}
						
					}
				//if the size of the right child isn't 1 then color everything below it
				} else {
					//only one cluster object, write the name in the color
					ClusterGroup<K> clusterObjectIDval = newClusterObjectsToClusterGroups.get(objectID);
					Iterator<K> clusterObjectVal = clusterObjectIDval.iterator();
					K clusterId = clusterObjectVal.next();
					DendrogramClusterObjectGUI<K, V> clusterObject = clusterObjects.get(clusterId);
					String name = clusterObject.getLabel();
					
					int nameLength = name.length();
					int xOffset = 0;
					if(nameLength<maxLabelLength){
						xOffset = maxLabelLength-nameLength;
					}	
					Integer yName = clusterObjectYLocationsMap.get(objectID)-6;

					if(clusterObject.isSelected()) {
						gcDendrogram.setForeground(black);
						gcDendrogram.setFont(selectedFont);
					}
					else {
						gcDendrogram.setForeground(gray);
					}
					gcDendrogram.drawString(name, xOffset*5, yName, true);
					gcDendrogram.setFont(normalFont);
					
					//Cache the cluster object label name string bounds to enable mouse hit detection
					Point stringExtent = gcDendrogram.stringExtent(name);
					if(clusterObject.bounds != null) {						
						clusterObject.bounds.x = xOffset*5; clusterObject.bounds.y = yName; 
						clusterObject.bounds.width = stringExtent.x; clusterObject.bounds.height = stringExtent.y;
					}
					else {
						clusterObject.bounds = new Rectangle(xOffset*5, yName, stringExtent.x, stringExtent.y);
					}
				}
		}		
	}	
	
	public void drawDendrogram(GC gcDendrogram) {
		if(clusterObjects == null || clusterObjects.isEmpty()) {
			return;
		}
		
		clusterObjectXLocationsMap = new HashMap<Integer, Integer>();
		clusterObjectYLocationsMap = new HashMap<Integer, Integer>();
		//this is really dendro "points" to cluster groups
		newClusterObjectsToClusterGroups = new HashMap<Integer, ClusterGroup<K>>();
		firstChild = new HashMap<Integer, Integer>();
		secondChild = new HashMap<Integer, Integer>();
		//only need a mapping from points in the dendrogram to cluster object IDs so we can pull out names
		fromNewClusterObjectsToClustertoObjectID = new HashMap<K, Integer>(); //cluster object ID to dendroPoint(i.e., newClusterObjectsToClusterGroups)
		Integer yDist;
		Font normalFont = getFont();		
		
		//drawing the entire dendrogram in gray first
		gcDendrogram.setLineStyle(SWT.LINE_SOLID);
		gcDendrogram.setForeground(gray);		
		
		//drawing the leaves, marking their locations
		if(leaves != null && leaves.length > 0) {
			//determining the maximum name length to help right align cluster object labels
			maxLabelLength = 0;
			for(int i=0; i<leaves.length; i++) {
				K objectID = leaves[i].getObjectID();
				String name = clusterObjects.get(objectID).getLabel();
				int nameLength = name.length();
				if(nameLength > maxLabelLength) {
					maxLabelLength = nameLength;
				}
			}
			
			//determining the distance between leaves
			//y distance between cluster object names is computed here
			int distanceBetween = (canvasHeight-5)/leaves.length;
			int firstDistance = distanceBetween/2;		
			
			for(int i=0; i<leaves.length; i++) {
				K objectID = leaves[i].getObjectID();
				DendrogramClusterObjectGUI<K, V> clusterObject = clusterObjects.get(objectID);
				String name = clusterObject.getLabel();
				//just mapping this so we can later get the cluster object name, dendrogram only cares about point
				fromNewClusterObjectsToClustertoObjectID.put(objectID, i);
	
				yDist = firstDistance+(i*distanceBetween);			
				//schemaYLocationsMap.put(schemaNum, yDist);
				//schemaXLocationsMap.put(schemaNum, (maxNameLength+5)*5);
				clusterObjectYLocationsMap.put(i, yDist);
				clusterObjectXLocationsMap.put(i, (maxLabelLength+5)*5);
				//System.out.println("created new ID:" + i);
				int nameLength = name.length();
				int xOffset = 0;
				if(nameLength < maxLabelLength){
					xOffset = maxLabelLength - nameLength;
				}
				
				if(clusterObject.isSelected()) {
					gcDendrogram.setForeground(black);
					gcDendrogram.setFont(selectedFont);
				}
				else {
					gcDendrogram.setForeground(gray);
				}
				gcDendrogram.drawString(name, xOffset*5, yDist-6, true);
				gcDendrogram.setFont(normalFont);
				
				//Cache the cluster object name string bounds to enable mouse hit detection
				Point stringExtent = gcDendrogram.stringExtent(name);
				if(clusterObject.bounds != null) {					
					clusterObject.bounds.x = xOffset*5; clusterObject.bounds.y = yDist-6; 
					clusterObject.bounds.width = stringExtent.x; clusterObject.bounds.height = stringExtent.y;
				}
				else {
					clusterObject.bounds = new Rectangle(xOffset*5, yDist-6, stringExtent.x, stringExtent.y);
				}
			}
		}

		int startDendroXPoint = (maxLabelLength+5)*5;
		int numXpixels = (canvasWidth-20) -  startDendroXPoint;
		
		//scale no smaller than 100 pixels wide
		if(numXpixels < 100){
			numXpixels = 100;
		}
		
		if(clusters != null) {
			int step = 0;
			for(ClusterStep<K> cs : clusters){
				for(ClusterGroup<K> cg : cs.getClusterGroups()){
					if(step == 0) {
						Iterator<K> cgIter = cg.iterator();
						K clusterId = cgIter.next();
						Integer translatedID = fromNewClusterObjectsToClustertoObjectID.get(clusterId);
						newClusterObjectsToClusterGroups.put(translatedID, cg);
						firstChild.put(translatedID, null);
						secondChild.put(translatedID, null);
						//note that we now have a continuously numbered map, where the cg's use the true cluster objectIDs.
						//the fake maps we create below have nothing to do with real cluster objectIDs.
						//all of the real cluster objectIDs are put in at this step
					} else {
						//each cluster object is in it's own cluster at step 0, so we are not interested in that
						//now need to find the two cluster objects that were combined
						int groupSize = cg.getNumClusterObjects();
						if(groupSize == 2){
							//connecting two cluster objects at the bottom level
							Iterator<K> cgIter = cg.iterator();
							K clusterAid = cgIter.next();
							K clusterBid = cgIter.next();

							//translated IDs are really the points in the dendrogram that correspond to that cluster object IDs location
							Integer translatedID_A = fromNewClusterObjectsToClustertoObjectID.get(clusterAid);
							Integer translatedID_B = fromNewClusterObjectsToClustertoObjectID.get(clusterBid);

							Integer aX = clusterObjectXLocationsMap.get(translatedID_A);
							Integer aY = clusterObjectYLocationsMap.get(translatedID_A);
							Integer bX = clusterObjectXLocationsMap.get(translatedID_B);
							Integer bY = clusterObjectYLocationsMap.get(translatedID_B);							

							//this will be between 0.0 and 1.0
							double valXNotScaled = cg.getDistance();
							int dendroX = ((int)(numXpixels*valXNotScaled)) + startDendroXPoint;

							gcDendrogram.drawLine(aX, aY, dendroX, aY);
							gcDendrogram.drawLine(bX, bY, dendroX, bY);
							gcDendrogram.drawLine(dendroX, aY, dendroX, bY);

							//drawing the tick mark at top of canvas
							gcDendrogram.setForeground(black);
							gcDendrogram.setLineWidth(1);
							gcDendrogram.drawLine(dendroX, 0, dendroX, 6);
							gcDendrogram.setForeground(gray);
							gcDendrogram.setLineWidth(1);
							gcDendrogram.setAlpha(255);

							//what do we put in to mark A and B as a new cluster object?
							//let's create a new cluster object ID
							int numClusterObjects = clusterObjectXLocationsMap.size();

							//Note: it does not matter if it does match: cluster group objectIDs are totally separated from labels for the map
							boolean uniqueIDFound = false;
							Integer stepLabel = numClusterObjects;
							while(!uniqueIDFound) {
								if(!clusterObjectXLocationsMap.containsKey(stepLabel)) {
									uniqueIDFound = true;
								} else {
									stepLabel++;
								}
							}

							//need new X and Y for clustered group
							clusterObjectXLocationsMap.put(stepLabel, dendroX);
							clusterObjectYLocationsMap.put(stepLabel, (Integer)((aY+bY)/2));

							newClusterObjectsToClusterGroups.put(stepLabel, cg);
							firstChild.put(stepLabel, translatedID_A);
							secondChild.put(stepLabel, translatedID_B);
						} else {
							//need to find the cluster groups from the previous step that make up this cluster group
							//note this could be a cluster of size 3 if 2 were merged with one

							//go backwards through what was put in since last step should be most recent
							//search for the two steps that make up this cluster group
							int clusA = -1;
							int clusB = -1;

							ClusterGroup<K> temp = null;
							for(int i = newClusterObjectsToClusterGroups.size()-1; i>=0; i--) {
								ClusterGroup<K> currCG = newClusterObjectsToClusterGroups.get(i);	
								if(currCG == null) {
									System.err.println("A Warning in DendrogramCanvas: encountered null ClusterGroup " + i);
								} else if(cg.contains(currCG)) {
									clusA = i;
									Collection<K> origIDs = cg.getObjectIDs();
									temp = new ClusterGroup<K>(origIDs);
									temp.removeClusterObjects(currCG.getObjectIDs());
									break;
								}
							}

							for(int i = newClusterObjectsToClusterGroups.size()-1; i>=0; i--) {
								ClusterGroup<K> currCG = newClusterObjectsToClusterGroups.get(i);
								if(temp == null || currCG == null) {
									System.err.println("B Warning in DendrogramCanvas: encountered null ClusterGroup " + i);
								} else if(temp.contains(currCG)) {
									clusB = i;
									break;
								}
							}

							Integer aX = clusterObjectXLocationsMap.get(clusA);
							Integer aY = clusterObjectYLocationsMap.get(clusA);
							Integer bX = clusterObjectXLocationsMap.get(clusB);
							Integer bY = clusterObjectYLocationsMap.get(clusB);						

							if(aX == null || aY == null || bX == null || bY == null) {
								System.err.println("Error in DendgrogramCanvas: Encountered null objectID");
								return;
							}

							double valXNotScaled = cg.getDistance();
							int dendroX = ((int)(numXpixels*valXNotScaled)) + startDendroXPoint;						

							gcDendrogram.drawLine(aX, aY, dendroX, aY);
							gcDendrogram.drawLine(bX, bY, dendroX, bY);
							gcDendrogram.drawLine(dendroX, aY, dendroX, bY);

							//drawing the tick mark at top of canvas
							gcDendrogram.setForeground(black);
							gcDendrogram.setLineWidth(1);
							gcDendrogram.drawLine(dendroX, 0, dendroX, 6);
							gcDendrogram.setForeground(gray);
							gcDendrogram.setLineWidth(1);
							gcDendrogram.setAlpha(255);

							//what do we put in to mark A and B as a new cluster object?
							//let's create a new cluster object ID
							int numClusterObjects = clusterObjectXLocationsMap.size();						
							boolean uniqueIDFound = false;
							Integer stepLabel = numClusterObjects;
							while(!uniqueIDFound) {
								if(!clusterObjectXLocationsMap.containsKey(stepLabel)) {
									uniqueIDFound = true;
								} else {
									stepLabel++;
								}
							}	

							clusterObjectXLocationsMap.put(stepLabel, dendroX);
							clusterObjectYLocationsMap.put(stepLabel, (Integer)((aY+bY)/2));

							newClusterObjectsToClusterGroups.put(stepLabel, cg);
							firstChild.put(stepLabel, clusA);
							secondChild.put(stepLabel, clusB);
						}	
					}
				}
				step++; 
			}				
		}
		//end Dendrogram creation code here		
		
		//when coloring the lines we want to go through the clusters in the reverse order from what they were put in
		//then we can do all clusters below that		
		currentColorBlue = true;
		
		//this is the X location of the maximum simliarity bar
		int maxdendroBarXloc = ((int)(numXpixels*maxBarDVal)) + startDendroXPoint;
		
		gcDendrogram.setLineWidth(2);
		gcDendrogram.setForeground(black);
		gcDendrogram.setLineStyle(SWT.LINE_DASH);
		gcDendrogram.drawLine(maxdendroBarXloc, 0, maxdendroBarXloc, canvasHeight);		
		
		//draw the location of the minimum similarity bar
		int mindendroBarXloc = ((int)(numXpixels*minBarDVal)) + startDendroXPoint;
		gcDendrogram.setLineWidth(1);
		gcDendrogram.setForeground(gray);
		gcDendrogram.drawLine(mindendroBarXloc, 0, mindendroBarXloc, canvasHeight);		
		
		//start with the max cluster, the last put in
		//and see if everything is below it
		int maxClusterObjectID = clusterObjectXLocationsMap.size() - 1;
		determineColors(maxClusterObjectID, gcDendrogram, maxdendroBarXloc);	
	}
	
	//note that min and max must be between 0.0 and 1.0
	public void render(GC gc, Composite parent) {
		//Draw the background
		Rectangle bounds = this.dendrogramImage.getBounds();
		canvasWidth = bounds.width;
		canvasHeight = bounds.height;
		setBackground(white);
		drawBackground(gc, bounds.x, bounds.y, bounds.width, bounds.height);		
		
		//always start with blue
		//this prevents color from switching on repaint
		setForeground(blue);
		currentColorBlue = true;;
		drawDendrogram(gc);
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
			Collection<K> selectedClusterObjects = null;
			if(!this.selectedClusterObjects.isEmpty()) {
				selectedClusterObjects = this.selectedClusterObjects;
			}
			List<ClusterGroup<K>> selectedClusters = null;
			if(selectedCluster != null) {				
				selectedClusters = new LinkedList<ClusterGroup<K>>();
				selectedClusters.add(selectedCluster);
			}
			SelectionChangedEvent<K> event = new SelectionChangedEvent<K>(this, selectedClusterObjects, selectedClusters);
			for(SelectionChangedListener<K> listener : selectionChangedListeners) {
				listener.selectionChanged(event);
			}
		}
	}
	
	private static final int leftButtonCtrlStateMask = SWT.BUTTON1 | SWT.CONTROL;
	
	protected void fireSelectionClickedEvent(int button, int stateMask, int clickCount, int x, int y) {
		if(!selectionClickedListeners.isEmpty()) {
			Collection<K> selectedClusterObjects = null;
			if(!this.selectedClusterObjects.isEmpty()) {
				selectedClusterObjects = this.selectedClusterObjects;
			}
			List<ClusterGroup<K>> selectedClusters = null;
			if(selectedCluster != null) {				
				selectedClusters = new LinkedList<ClusterGroup<K>>();
				selectedClusters.add(selectedCluster);
			}
			SelectionClickedEvent<K> event = new SelectionClickedEvent<K>(this, 
					selectedClusterObjects, selectedClusters, button, 
					(stateMask == leftButtonCtrlStateMask) ? true : false, clickCount, x, y);			
			for(SelectionClickedListener<K> listener : selectionClickedListeners) { 
				listener.selectionClicked(event);
			}
		}
	}	
	
	/**
	 * A mouse listener to determine if a cluster object or cluster was double-clicked.
	 *
	 */
	private class DendrogramMouseListener extends MouseAdapter {		
		public ClusterGroup<K> getFirstClusterContainsPoint(int x, int y) {
			if(clusterBounds == null || clusterBounds.isEmpty()) {
				return null;
			}
			ClusterGroup<K> clickedCluster = null;
			for(Map.Entry<ClusterGroup<K>, Rectangle> entry : clusterBounds.entrySet()) {
				if(entry.getValue().contains(x, y)) {
					ClusterGroup<K> cluster = entry.getKey();
					if(clickedCluster == null || cluster.getNumClusterObjects() < clickedCluster.getNumClusterObjects())
						clickedCluster = cluster;
				}
			}
			return clickedCluster;
		}
		
		@Override
		public void mouseUp(MouseEvent e) {					
			//First determine if a cluster object was clicked
			if(clusterObjects == null || clusterObjects.isEmpty()) {
				return;
			}
			V clickedClusterObject = null;
			for(DendrogramClusterObjectGUI<K, V> clusterObject : clusterObjects.values()) {			
				int scrollAdjustedY = e.y + -1*(origin.y);
				
				if(clusterObject.bounds.contains(e.x, scrollAdjustedY)) {
					clickedClusterObject = clusterObject.getClusterObject();				
					if(!clusterObject.isSelected()) {	
						//A single cluster object was selected
						
						//Unselect any previously selected cluster objects or clusters
						unselectAllClusterObjectsAndClusters();						
						selectedClusterObjects.add(clusterObject.getObjectID());
						
						//Mark the cluster object as selected
						clusterObject.setSelected(true);						
						
						redraw();
						update();
						
						//Notify any SelectionChangedListeners that a single cluster object is currently selected
						fireSelectionChangedEvent();
					}					
					
					//Notify any SelectionClickedListeners that a single cluster object was double-clicked					
					if(e.count == 2) {
						fireSelectionClickedEvent(e.button, e.stateMask, e.count, e.x, e.y);
					}
					
					break;
				}					
			}							

			//If no cluster object was clicked, determine if a cluster was clicked
			if(clickedClusterObject == null) {	
				//Note that because the dendrogram can scroll, we need to adjust y based on scrolling
				int scrollAdjustedY = e.y + -1*(origin.y);
				
				ClusterGroup<K> cluster = getFirstClusterContainsPoint(e.x, scrollAdjustedY);
				if(cluster != null) {
					if(cluster != selectedCluster) {
						//A single cluster was selected		
						
						//Unselect any previously selected cluster objects or clusters
						unselectAllClusterObjectsAndClusters();										
						
						//Mark the cluster as selected						
						selectedCluster = cluster;
						
						redraw();
						update();
						
						//Notify any SelectionChangedListeners that a single cluster is currently selected
						fireSelectionChangedEvent();
					}					
					
					//Notify any SelectionClickedListeners that a single cluster was double-clicked				
					if(e.count == 2) {
						fireSelectionClickedEvent(e.button, e.stateMask, e.count, e.x, scrollAdjustedY);
					}
				}
				else {
					//No cluster object or cluster was clicked, so unhighlight any previously highlighted cluster object or cluster
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
}