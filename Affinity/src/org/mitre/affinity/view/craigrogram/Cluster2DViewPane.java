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

import java.util.Collection;
import java.util.List;

import javax.swing.SwingConstants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import org.mitre.affinity.model.Position;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.view.ClusterDistanceSliderPane;
import org.mitre.affinity.view.IClusterObjectGUI;
import org.mitre.affinity.view.ICluster2DView;
import org.mitre.affinity.view.event.ClusterDistanceChangeEvent;
import org.mitre.affinity.view.event.ClusterDistanceChangeListener;
import org.mitre.affinity.view.event.SelectionChangedListener;
import org.mitre.affinity.view.event.SelectionClickedListener;
import org.mitre.affinity.view.swt.SWTUtils;
import org.mitre.affinity.view.swt.SWTUtils.TextSize;
import org.mitre.affinity.view.swt.event.ZoomPanListener;

/**
 * A pane with a 2D plot with clusters, a slider to set the min/max cluster size, and a tool bar.
 * 
 * @author CBONACETO
 *
 */
public class Cluster2DViewPane<K extends Comparable<K>, V> extends Composite implements ICluster2DView<K, V>, ClusterDistanceChangeListener {
	
	public static enum SliderType {STEP_SIZE, CLUSTER_SIZE, NONE};	
	
	/** The 2D plot with clusters shown */
	private Cluster2DView<K, V> clusterObject2DPlot;
	
	/** Size of the text in the 2D plot */
	private TextSize textSize;
	
	/** Whether or not the 1:1 (width:height) aspect ratio of the plot is maintained after resizing */
	private boolean lockAspectRatio = true;	
	
	/** The position grid that contains the x,y position of each cluster object */
	private PositionGrid<K> positionGrid;
	
	/** We don't rescale if width/height are less than minWidth/minHeight */
	private int minWidth = 200;
	private int minHeight = 300;
	
	/** The tool bar */
	private Cluster2DViewToolBar<K, V> toolBar;
	
	/** A slider bar used to set the min/max cluster step */
	private SWTClusterStepSizeSliderPane<K, V> clusterStepSlider;
	
	/** A slider bar used to set the min/max cluster size */
	private SWTClusterDistanceSliderPane<K, V> clusterDistanceSlider;
	
	public Cluster2DViewPane(Composite parent, int style, boolean showToolBar,
			SliderType sliderType, int minValue, int maxValue) {
		this(parent, style, showToolBar, sliderType, minValue, maxValue, 
				null, null, null);
	}
	
	public Cluster2DViewPane(Composite parent, int style, boolean showToolBar,
			SliderType sliderType, int minValue, int maxValue,
			final List<IClusterObjectGUI<K, V>> clusterObjects, final ClustersContainer<K> clusters,
			final PositionGrid<K> pg) {		
		super(parent, style);
		
		this.positionGrid = pg;		
	
		boolean showSlider = false;
		if(sliderType != SliderType.NONE) 
			showSlider = true;
		
		GridLayout gl = new GridLayout(); 
		gl.makeColumnsEqualWidth = false;
		gl.marginHeight = 0;
		gl.marginTop = 0;
		if(showSlider) 
			gl.numColumns = 2;
		else
			gl.numColumns = 1;
		this.setLayout(gl);
		
		this.toolBar = new Cluster2DViewToolBar<K, V>(this, SWT.NONE, this);
		if(showToolBar) {
			toolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		}
		else {
			toolBar.setLayoutData(new GridData(0, 0));
		}
		
		this.clusterObject2DPlot = new Cluster2DView<K, V>(this, SWT.DOUBLE_BUFFERED | SWT.NO_REDRAW_RESIZE, 
				clusterObjects, clusters);
		this.clusterObject2DPlot.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setTextSize(TextSize.Normal);
		
		//Add listener to update the zoom box in the toolbar when zoom is changed in the craigrogram
		clusterObject2DPlot.addZoomPanListener(new ZoomPanListener() {
			public void widgetPanned(int x, int y) {}
			public void widgetZoomed(float zoomLevel) {	
				toolBar.setZoomSelectionText(Integer.toString((int)(clusterObject2DPlot.getZoom()*100)) + "%");
			}			
		});
		
		if(showSlider) {
			if(sliderType == SliderType.CLUSTER_SIZE) {			
				//this.clusterObject2DPlot.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				this.clusterObject2DPlot.setFilterOnClusterDistance(true);
				this.clusterDistanceSlider = new SWTClusterDistanceSliderPane<K, V>(this, style, clusters, SwingConstants.VERTICAL);			
				this.clusterDistanceSlider.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
				//this.clusterDistanceSlider.getClusterDistanceSliderPane().addClusterDistanceChangeListener(this);
			}	
			else {
				this.clusterStepSlider = new SWTClusterStepSizeSliderPane<K, V>(this, style, this.clusterObject2DPlot, minValue, maxValue);			
				this.clusterStepSlider.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
			}
		}		
	}	
	
	public void setShowToolBar(boolean showToolBar) {		
		if(showToolBar) {
			toolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		}
		else {
			toolBar.setLayoutData(new GridData(0, 0));
		}	
		this.layout();
	}
	
	public boolean isLockAspectRatio() {
		return lockAspectRatio;
	}

	public void setLockAspectRatio(boolean lockAspectRatio) {
		if(lockAspectRatio != this.lockAspectRatio) {			
			this.lockAspectRatio = lockAspectRatio;			
			toolBar.setLockAspectRatioSelection(lockAspectRatio);
			this.resize();
		}		
	}

	public boolean isShowToolBar() {
		return toolBar.getSize().y > 1;
	}	
	
	public void fitInWindow() {		
		toolBar.setZoomSelectionIndex(4);		
		clusterObject2DPlot.reset();
		clusterObject2DPlot.redraw();
	}
	
	public float getZoomPercent() {
		return clusterObject2DPlot.getZoom() * 100.f;
	}
	
	public void setZoomPercent(int percent) {
		setZoom(percent/100.f);
	}
	
	public float getZoom() {
		return this.clusterObject2DPlot.getZoom();
	}
	
	public void setZoom(float zoom) {
		toolBar.setZoomSelectionText(Integer.toString((int)(zoom*100.f)) + "%");
		clusterObject2DPlot.setZoom(zoom);
		clusterObject2DPlot.redraw();
	}
	
	public void zoomIn(int numLevels) {
		this.clusterObject2DPlot.zoomIn(numLevels);
	}

	public void zoomOut(int numLevels) {
		this.clusterObject2DPlot.zoomOut(numLevels);
	}
	
	public TextSize getTextSize() {
		return this.textSize;
	}
	
	public void setTextSize(TextSize textSize) {
		this.textSize = textSize;
		Font font = null;
		Font selectedFont = null;
		switch(textSize) {
		case Small: 
			font = SWTUtils.getFont(SWTUtils.SMALL_FONT);
			selectedFont = SWTUtils.getFont(SWTUtils.SMALL_BOLD_FONT);
			break;
		case Normal: 
			font = SWTUtils.getFont(SWTUtils.NORMAL_FONT);
			selectedFont = SWTUtils.getFont(SWTUtils.NORMAL_BOLD_FONT);
			break;
		case Large: 
			font = SWTUtils.getFont(SWTUtils.LARGE_FONT);
			selectedFont = SWTUtils.getFont(SWTUtils.LARGE_BOLD_FONT);
			break;
		}
		toolBar.setTextSizeSelection(textSize);
		clusterObject2DPlot.setFonts(font, selectedFont);
		clusterObject2DPlot.redraw();
	}
	
	public void addSelectionChangedListener(SelectionChangedListener<K> listener) {
		this.clusterObject2DPlot.addSelectionChangedListener(listener);
	}
	
	public void removeSelectionChangedListener(SelectionChangedListener<K> listener) {
		this.clusterObject2DPlot.removeSelectionChangedListener(listener);
	}
	
	public void addSelectionClickedListener(SelectionClickedListener<K> listener) {
		this.clusterObject2DPlot.addSelectionClickedListener(listener);
	}
	
	public void removeSelectionClickedListener(SelectionClickedListener<K> listener) {
		this.clusterObject2DPlot.removeSelectionClickedListener(listener);
	}	
	
	public void setSelectedClusterObject(K objectID) {
		this.clusterObject2DPlot.setSelectedClusterObject(objectID);
	}
	
	public void setSelectedClusterObjects(Collection<K> objectIDs) {
		this.clusterObject2DPlot.setSelectedClusterObjects(objectIDs);	
	}
	
	public void unselectAllClusterObjectsAndClusters() {
		this.clusterObject2DPlot.unselectAllClusterObjectsAndClusters();
	}
	
	public void setSelectedCluster(ClusterGroup<K> cluster) {
		this.clusterObject2DPlot.setSelectedCluster(cluster);
	}

	public void setFillClusters(boolean fillClusters) {
		this.clusterObject2DPlot.setFillClusters(fillClusters);
	}
	
	public void setShowClusters(boolean showClusters) {
		this.clusterObject2DPlot.setShowClusters(showClusters);
	}
	
	public void setMode(Mode mode) {
		this.clusterObject2DPlot.setMode(mode);
	}	

	public void resize() {	
		Point size = clusterObject2DPlot.getSize();
		
		//Maintain minimum width and height
		if(size.x < minWidth)
			size.x = minWidth;
		if(size.y < minHeight)
			size.y = minHeight;
		
		//if(size.x >= minWidth && size.y >= minHeight) {
		int borderSize = clusterObject2DPlot.getStartRadius() + (clusterObject2DPlot.getRadiusIncrement() * this.clusterObject2DPlot.getMaxClusterNestingLevel()) + 10;									
		int width = size.x;
		int height = size.y;

		//TODO: Determine if maintaining aspect ratio is necessary,
		if(lockAspectRatio) {
			//Maintain 1:1 (width:height) aspect ratio
			if(width < height)
				height = width;
			else
				width = height;
		}
		
		if(positionGrid != null) {
			//positionGrid.rescale(borderSize, width - borderSize, borderSize, height - borderSize);
			//positionGrid.rescale(borderSize/2, width - borderSize*2, borderSize/2, height - borderSize*2);
			positionGrid.rescale(0, width - borderSize*2, 0, height - borderSize*2);

			//Center the plot			
			//positionGrid.translate(10, 20);
			positionGrid.center(0, 0, size.x, size.y);
			//positionGrid.center(borderSize/2, borderSize/2, width - borderSize*2, height - borderSize*2);
		}

		if(clusterObject2DPlot.getClusterObjects() != null) {
			for(IClusterObjectGUI<K, V> c : clusterObject2DPlot.getClusterObjects()) {
				Position pos = positionGrid.getPosition(c.getObjectID());
				c.setLocation((int)pos.getDimensionValue(0), (int)pos.getDimensionValue(1));
			}
			clusterObject2DPlot.clusterObjectLocationsChanged();
			clusterObject2DPlot.redraw();
		}
	}

	public Cluster2DView<K, V> getClusterObject2DPlot() {
		return clusterObject2DPlot;
	}
	
	public void setClusterObjectsAndClusters(List<IClusterObjectGUI<K, V>> clusterObjects, 
			ClustersContainer<K> clusters, PositionGrid<K> pg) {
		clusterObject2DPlot.setClusterObjectsAndClusters(clusterObjects, clusters);
		if(clusterDistanceSlider != null) {
			clusterDistanceSlider.setClusters(clusters);
		}
		this.positionGrid = pg;
		resize();
	}
	
	/*public PositionGrid<K> getPositionGrid() {
		return positionGrid;
	}*/

	public void setPositionGrid(PositionGrid<K> positionGrid) {
		this.positionGrid = positionGrid;
	}

	public ClusterDistanceSliderPane<K, V> getClusterDistanceSlider() {
		return clusterDistanceSlider.getClusterDistanceSliderPane();
	}	
	
	public void redraw() {
		this.clusterObject2DPlot.redraw();
	}

	public void clusterDistanceChanged(ClusterDistanceChangeEvent event) {
		this.clusterObject2DPlot.setMinClusterDistance(event.newMinDistance);
		this.clusterObject2DPlot.setMaxClusterDistance(event.newMaxDistance);
		this.clusterObject2DPlot.getDisplay().asyncExec(new Runnable() {
			public void run() {
				clusterObject2DPlot.redraw();
			}});
	}

	public void setClusterObjectNamesVisible(boolean b) {
		this.clusterObject2DPlot.setClusterObjectNamesVisible(b);		
	}
}