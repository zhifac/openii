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

import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.clusters.ClustersContainer;
import org.mitre.affinity.model.Position;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.util.SWTUtils;
import org.mitre.affinity.util.SWTUtils.TextSize;
import org.mitre.affinity.view.ClusterDistanceSliderPane;
import org.mitre.affinity.view.ISchemaCluster2DView;
import org.mitre.affinity.view.event.ClusterDistanceChangeEvent;
import org.mitre.affinity.view.event.ClusterDistanceChangeListener;
import org.mitre.affinity.view.event.SelectionChangedListener;
import org.mitre.affinity.view.event.SelectionClickedListener;
import org.mitre.affinity.view.swt.event.ZoomPanListener;

/**
 * A pane with a 2D plot with clusters, a slider to set the min/max cluster size, and a tool bar.
 * 
 * @author CBONACETO
 *
 */
public class SchemaCluster2DViewPane extends Composite implements ISchemaCluster2DView, ClusterDistanceChangeListener {
	public static enum SliderType {STEP_SIZE, CLUSTER_SIZE, NONE};	
	
	/** The 2D plot with clusters shown */
	private SchemaCluster2DView schema2DPlot;
	
	/** Size of the text in the 2D plot */
	private TextSize textSize;
	
	/** Whether or not the 1:1 (width:height) aspect ratio of the plot is maintained after resizing */
	private boolean lockAspectRatio = true;	
	
	/** The position grid that contains the x,y position of each schema */
	private PositionGrid positionGrid;
	
	/** We don't rescale if width/height are less than minWidth/minHeight */
	private int minWidth = 200;
	private int minHeight = 300;
	
	/** The tool bar */
	private SchemaCluster2DViewToolBar toolBar;
	
	/** A slider bar used to set the min/max cluster step */
	private SWTClusterStepSizeSliderPane clusterStepSlider;
	
	/** A slider bar used to set the min/max cluster size */
	private SWTClusterDistanceSliderPane clusterDistanceSlider;
	
	public SchemaCluster2DViewPane(Composite parent, int style, 
			final List<ISchemaGUI> schemata, final ClustersContainer clusters,
			final PositionGrid pg, boolean showToolBar,
			SliderType sliderType, int minValue, int maxValue) {		
		super(parent, style);
		
		this.positionGrid = pg;
		//this.clusters = clusters;		
	
		boolean showSlider = false;
		if(sliderType != SliderType.NONE) 
			showSlider = true;
		
		GridLayout gl = new GridLayout(); 
		gl.makeColumnsEqualWidth = false;
		gl.marginHeight = 0;
		gl.marginTop = 0;
		//gl.marginBottom = 1;
		if(showSlider) 
			gl.numColumns = 2;
		else
			gl.numColumns = 1;
			//this.setLayout(new FillLayout());
		this.setLayout(gl);
		
		//if(showToolbar) {
		this.toolBar = new SchemaCluster2DViewToolBar(this, SWT.NONE, this);
		//createToolBar();		
		if(showToolBar) {
			toolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		}
		else {
			toolBar.setLayoutData(new GridData(0, 0));
		}
		
		this.schema2DPlot = new SchemaCluster2DView(this, SWT.DOUBLE_BUFFERED | SWT.NO_REDRAW_RESIZE , 
				schemata, clusters);
		this.schema2DPlot.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setTextSize(TextSize.Normal);
		
		//Add listener to update the zoom box in the toolbar when zoom is changed in the craigrogram
		schema2DPlot.addZoomPanListener(new ZoomPanListener() {
			public void widgetPanned(int x, int y) {}
			public void widgetZoomed(float zoomLevel) {	
				toolBar.setZoomSelectionText(Integer.toString((int)(schema2DPlot.getZoom()*100)) + "%");
			}			
		});
		
		if(showSlider) {
			if(sliderType == SliderType.CLUSTER_SIZE) {			
				//this.schema2DPlot.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				this.schema2DPlot.setFilterOnClusterDistance(true);
				this.clusterDistanceSlider = new SWTClusterDistanceSliderPane(this, style, clusters, SwingConstants.VERTICAL);			
				this.clusterDistanceSlider.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
				//this.clusterDistanceSlider.getClusterDistanceSliderPane().addClusterDistanceChangeListener(this);
			}	
			else {
				this.clusterStepSlider = new SWTClusterStepSizeSliderPane(this, style, this.schema2DPlot, minValue, maxValue);			
				this.clusterStepSlider.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
			}
		}
		
		//this.resize();
		
		//Add listener to rescale the plot when the app is resized	
		/*
		this.schema2DPlot.addControlListener(new ControlListener() {
			@Override
			public void controlMoved(ControlEvent event) {}			
			@Override
			public void controlResized(ControlEvent event) {
				resize();
			}
		});
		*/
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
	
	/*
	private void createToolBar() {
		toolBar = new Composite(this, SWT.NONE);
		GridLayout gl = new GridLayout(8, false);
		gl.marginHeight = 2;
		toolBar.setLayout(gl);
		//CoolBar toolbar = new CoolBar(this, SWT.NONE);		
		
		new Label(toolBar, SWT.NONE).setText("Text Size:");
		this.textSizeCombo = new Combo(toolBar, SWT.DROP_DOWN);	
		textSizeCombo.add("Small");
		textSizeCombo.add("Normal");
		textSizeCombo.add("Large");
		textSizeCombo.select(1);
		textSizeCombo.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent e) {				
				if(textSizeCombo.getSelectionIndex() != -1) {
					TextSize textSize = TextSize.Normal;
					switch(textSizeCombo.getSelectionIndex()) {
					case 0: textSize = TextSize.Small; break;				
					case 2: textSize = TextSize.Large; break;
					}
					setTextSize(textSize);
				}
			}
		});
		
		Label label = new Label(toolBar, SWT.CENTER);
		label.setText("  Zoom:");		
		this.zoomCombo = new Combo(toolBar, SWT.DROP_DOWN);
		final int[] zoomLevels = AffinityConstants.ZOOM_LEVELS;
		for(int zoomLevel : zoomLevels) {
			zoomCombo.add(Integer.toString(zoomLevel) + "%");
		}		
		zoomCombo.add("Fit In Window");
		zoomCombo.setVisibleItemCount(zoomLevels.length + 1);
		zoomCombo.select(4);
		zoomCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				//Called after user enters custom zoom and hits return
				//System.out.println("hello");				
				int zoom = -1;
				String zoomText = zoomCombo.getText();			
				try {							
					if(zoomText.endsWith("%"))
						zoom = Integer.parseInt(zoomText.substring(0, zoomText.length() - 1));
					else						
						zoom = Integer.parseInt(zoomText);
				} catch(Exception ex) {}	
				if(zoom > 0) {
					if(!zoomText.endsWith("%")) {
						zoomCombo.setText(zoomText + "%");
						//zoomCombo.setItem(0, zoomText + "%");
					}									
					//zoomLevels[0] = zoom;
					schema2DPlot.setZoom(zoom/100.f);
					schema2DPlot.redraw();
				}				
			}
			public void widgetSelected(SelectionEvent e) {
				//System.out.println("setting zoom: " + zoomLevels[zoomCombo.getSelectionIndex()]);
				int selection = zoomCombo.getSelectionIndex();
				if(selection >= 0) {
					if(selection == zoomLevels.length) {
						//Fit in window was selected
						fitInWindow();
					}
					else {
						// Zoom to selected zoom level
						schema2DPlot.setZoom(zoomLevels[selection]/100.f);
						schema2DPlot.redraw();
					}
				}
			}
		});
	
		Button plus = new Button(toolBar, SWT.PUSH | SWT.CENTER);
		//plus.setLayoutData(new RowData(22, 22));
		plus.setLayoutData(new GridData(22,22));
		plus.setText("+");		
		plus.addListener(SWT.Selection,  new Listener() {
			public void handleEvent(Event event) {
				schema2DPlot.zoomIn(1);
				zoomCombo.setText(Integer.toString((int)(schema2DPlot.getZoom()*100)) + "%");
				//zoomCombo.setItem(0, Integer.toString((int)(schema2DPlot.getZoom()*100)) + "%");
				//zoomCombo.select(0);
				schema2DPlot.redraw();
			}
		});
		Button minus = new Button(toolBar, SWT.PUSH | SWT.CENTER);
		//minus.setLayoutData(new RowData(22, 22));
		minus.setLayoutData(new GridData(22,22));
		minus.setText("-");
		//minus.setToolTipText("Zoom Out, Also use \"-\" key or sroll wheel");
		minus.addListener(SWT.Selection,  new Listener() {
			public void handleEvent(Event event) {
				schema2DPlot.zoomOut(1);
				zoomCombo.setText(Integer.toString((int)(schema2DPlot.getZoom()*100)) + "%");
				//zoomCombo.setItem(0, Integer.toString((int)(schema2DPlot.getZoom()*100)) + "%");
				//zoomCombo.select(0);
				schema2DPlot.redraw();
			}
		});		
		
		new Label(toolBar, SWT.NONE).setText("  ");
		this.lockAspectRatioButton = new Button(toolBar, SWT.CHECK);
		lockAspectRatioButton.setText("Lock Aspect Ratio");
		lockAspectRatioButton.setSelection(this.lockAspectRatio);
		lockAspectRatioButton.addListener(SWT.Selection,  new Listener() {
			public void handleEvent(Event event) {
				lockAspectRatio = lockAspectRatioButton.getSelection();
				resize();
			}
		});
	}*/
	
	public void fitInWindow() {
		toolBar.setZoomSelectionIndex(4);		
		schema2DPlot.reset();
		schema2DPlot.redraw();
	}
	
	public float getZoomPercent() {
		return schema2DPlot.getZoom() * 100.f;
	}
	
	public void setZoomPercent(int percent) {
		setZoom(percent/100.f);		
		
	}
	
	public float getZoom() {
		return this.schema2DPlot.getZoom();
	}
	
	public void setZoom(float zoom) {
		toolBar.setZoomSelectionText(Integer.toString((int)(zoom*100.f)) + "%");
		/*
		if(zoomCombo != null && !zoomCombo.isDisposed()) {
			zoomCombo.setText(Integer.toString((int)(zoom*100.f)) + "%");
			zoomCombo.update();
		}
		*/
		this.schema2DPlot.setZoom(zoom);
		schema2DPlot.redraw();
	}
	
	public void zoomIn(int numLevels) {
		this.schema2DPlot.zoomIn(numLevels);
	}

	public void zoomOut(int numLevels) {
		this.schema2DPlot.zoomOut(numLevels);
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
		schema2DPlot.setFonts(font, selectedFont);
		schema2DPlot.redraw();
	}
	
	/*
	@Override
	public void addAffinityListener(AffinityListener listener) {
		this.schema2DPlot.addAffinityListener(listener);
	}
	@Override
	public void removeAffinityListener(AffinityListener listener) {
		this.schema2DPlot.removeAffinityListener(listener);
	}*/
	
	public void addSelectionChangedListener(SelectionChangedListener listener) {
		this.schema2DPlot.addSelectionChangedListener(listener);
	}
	
	public void removeSelectionChangedListener(SelectionChangedListener listener) {
		this.schema2DPlot.removeSelectionChangedListener(listener);
	}
	
	public void addSelectionClickedListener(SelectionClickedListener listener) {
		this.schema2DPlot.addSelectionClickedListener(listener);
	}
	
	public void removeSelectionClickedListener(SelectionClickedListener listener) {
		this.schema2DPlot.removeSelectionClickedListener(listener);
	}	
	
	public void setSelectedSchema(Integer schemaID) {
		this.schema2DPlot.setSelectedSchema(schemaID);
	}
	
	public void setSelectedSchemas(Collection<Integer> schemaIDs) {
		this.schema2DPlot.setSelectedSchemas(schemaIDs);	
	}
	
	public void unselectAllSchemasAndClusters() {
		this.schema2DPlot.unselectAllSchemasAndClusters();
	}
	
	public void setSelectedCluster(ClusterGroup cluster) {
		this.schema2DPlot.setSelectedCluster(cluster);
	}

	public void setFillClusters(boolean fillClusters) {
		this.schema2DPlot.setFillClusters(fillClusters);
	}
	
	public void setShowClusters(boolean showClusters) {
		this.schema2DPlot.setShowClusters(showClusters);
	}
	
	public void setMode(Mode mode) {
		this.schema2DPlot.setMode(mode);
	}	

	public void resize() {	
		Point size = schema2DPlot.getSize();
		//System.out.println("new size: " + size);
		
		//Maintain minimum width and height
		if(size.x < minWidth)
			size.x = minWidth;
		if(size.y < minHeight)
			size.y = minHeight;
		
		//if(size.x >= minWidth && size.y >= minHeight) {
		int borderSize = schema2DPlot.getStartRadius() + (schema2DPlot.getRadiusIncrement() * this.schema2DPlot.getMaxClusterNestingLevel()) + 10;									
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
		//positionGrid.rescale(borderSize, width - borderSize, borderSize, height - borderSize);
		//positionGrid.rescale(borderSize/2, width - borderSize*2, borderSize/2, height - borderSize*2);
		positionGrid.rescale(0, width - borderSize*2, 0, height - borderSize*2);

		//Center the plot			
		//positionGrid.translate(10, 20);
		positionGrid.center(0, 0, size.x, size.y);
		//positionGrid.center(borderSize/2, borderSize/2, width - borderSize*2, height - borderSize*2);

		for(ISchemaGUI s : schema2DPlot.getSchemata()) {
			Position pos = positionGrid.getPosition(s.getSchemaID());
			s.setLocation((int)pos.getDimensionValue(0), (int)pos.getDimensionValue(1));
			//System.out.println("Position for schema " + s.getSchemaID() + ": " + pos);
		}

		schema2DPlot.schemaLocationsChanged();
		schema2DPlot.redraw();
		//}
	}

	public SchemaCluster2DView getSchema2DPlot() {
		return schema2DPlot;
	}
	
	public PositionGrid getPositionGrid() {
		return positionGrid;
	}

	public void setPositionGrid(PositionGrid positionGrid) {
		this.positionGrid = positionGrid;
	}

	public ClusterDistanceSliderPane getClusterDistanceSlider() {
		return clusterDistanceSlider.getClusterDistanceSliderPane();
	}	
	
	public void redraw() {
		this.schema2DPlot.redraw();
	}

	public void clusterDistanceChanged(ClusterDistanceChangeEvent event) {
		//System.out.println("new min: " + event.newMinDistance + ", new max: " + event.newMaxDistance);
		this.schema2DPlot.setMinClusterDistance(event.newMinDistance);
		this.schema2DPlot.setMaxClusterDistance(event.newMaxDistance);
		this.schema2DPlot.getDisplay().asyncExec(new Runnable() {
			public void run() {
				schema2DPlot.redraw();
			}});
	}

	public void setSchemaNamesVisible(boolean b) {
		this.schema2DPlot.setSchemaNamesVisible(b);		
	}
}
