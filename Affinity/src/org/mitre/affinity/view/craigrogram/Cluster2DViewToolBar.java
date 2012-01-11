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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.mitre.affinity.AffinityConstants;
import org.mitre.affinity.AffinityConstants.IconType;
import org.mitre.affinity.view.ICluster2DView;
import org.mitre.affinity.view.ICluster2DView.Mode;
import org.mitre.affinity.view.swt.SWTUtils.TextSize;

public class Cluster2DViewToolBar<K extends Comparable<K>, V> extends Composite {
	
	/** The schema-cluster view this tool bar is tied to */
	ICluster2DView<K, V> schemaCluster2DView;
	
	//Tool bar widgets
	private Composite toolBar;
	
	private Combo textSizeCombo;
	
	private Combo clusterCombo;
	
	private Combo zoomCombo;
	
	private Button lockAspectRatioButton;

	public Cluster2DViewToolBar(Composite parent, int style, ICluster2DView<K, V> schemaClusterView) {
		super(parent, style);		
		this.schemaCluster2DView = schemaClusterView;
		this.creatToolBar();
	}
	
	public void setLockAspectRatioSelection(boolean lockAspectRatio) {
		if(this.lockAspectRatioButton != null && !this.lockAspectRatioButton.isDisposed())
			this.lockAspectRatioButton.setSelection(lockAspectRatio);
	}
	
	public void setZoomSelectionIndex(int zoomIndex) {
		if(zoomCombo != null && !zoomCombo.isDisposed()) 
			zoomCombo.select(zoomIndex);
	}
	
	public void setZoomSelectionText(String zoomText) {
		if(zoomCombo != null && !zoomCombo.isDisposed()) {
			zoomCombo.setText(zoomText);
			zoomCombo.update();
		}
	}
	
	public void setTextSizeSelection(TextSize textSize) {
		if(textSizeCombo != null && !textSizeCombo.isDisposed())
			textSizeCombo.select(textSize.ordinal());
	}
	
	private void creatToolBar() {
		this.setLayout(new GridLayout(1, true));
		//this.toolBar = new ToolBar(this, SWT.HORIZONTAL | SWT.BORDER);	
		this.toolBar = new Composite(this, SWT.NONE);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));		
		GridLayout gl = new GridLayout(12, false);
		gl.marginHeight = 2;
		gl.marginWidth = 2;
		gl.horizontalSpacing = 3;
		toolBar.setLayout(gl);	
		
		ToolBar buttonBar = new ToolBar(toolBar, SWT.HORIZONTAL);
		ToolItem panItem = new ToolItem(buttonBar, SWT.PUSH);
		panItem.setToolTipText("Pan and Select Schemas and Clusters");
		panItem.setImage(AffinityConstants.getAffinityIcon(IconType.PAN_ICON));	
		panItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				schemaCluster2DView.setMode(Mode.PAN_AND_SELECT);
			}
		});
		ToolItem selectItem = new ToolItem(buttonBar, SWT.PUSH);
		selectItem.setImage(AffinityConstants.getAffinityIcon(IconType.CURSOR_ICON));
		selectItem.setToolTipText("Select Multiple Schemas");
		selectItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				schemaCluster2DView.setMode(Mode.SELECT_MULTIPLE_CLUSTER_OBJECTS);
			}
		});
		
		new Label(toolBar, SWT.NONE).setLayoutData(new GridData(10, 1));		
		
		Label label = new Label(toolBar, SWT.CENTER);
		label.setText("Zoom:");		
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
					if(zoomText.endsWith("%")) {
						zoom = Integer.parseInt(zoomText.substring(0, zoomText.length() - 1));
					} else {						
						zoom = Integer.parseInt(zoomText);
					}
				} catch(Exception ex) {}	
				if(zoom > 0) {
					if(!zoomText.endsWith("%")) {
						zoomCombo.setText(zoomText + "%");
						//zoomCombo.setItem(0, zoomText + "%");
					}									
					//zoomLevels[0] = zoom;
					schemaCluster2DView.setZoom(zoom/100.f);
					schemaCluster2DView.redraw();
				}				
			}
			public void widgetSelected(SelectionEvent e) {
				//System.out.println("setting zoom: " + zoomLevels[zoomCombo.getSelectionIndex()]);
				int selection = zoomCombo.getSelectionIndex();
				if(selection >= 0) {
					if(selection == zoomLevels.length) {
						//Fit in window was selected
						schemaCluster2DView.fitInWindow();
					}
					else {
						// Zoom to selected zoom level
						schemaCluster2DView.setZoom(zoomLevels[selection]/100.f);
						schemaCluster2DView.redraw();
					}
				}
			}
		});
	
		Button plus = new Button(toolBar, SWT.PUSH | SWT.CENTER);
		//plus.setLayoutData(new RowData(22, 22));
		plus.setLayoutData(new GridData(22,22));
		//plus.setText("+");
		plus.setImage(AffinityConstants.getAffinityIcon(IconType.ZOOM_IN_ICON));
		plus.addListener(SWT.Selection,  new Listener() {
			public void handleEvent(Event event) {
				schemaCluster2DView.zoomIn(1);
				zoomCombo.setText(Integer.toString((int)(schemaCluster2DView.getZoom()*100)) + "%");				
				schemaCluster2DView.redraw();
			}
		});
		Button minus = new Button(toolBar, SWT.PUSH | SWT.CENTER);
		//minus.setLayoutData(new RowData(22, 22));
		minus.setLayoutData(new GridData(22,22));
		minus.setImage(AffinityConstants.getAffinityIcon(IconType.ZOOM_OUT_ICON));
		//minus.setText("-");		
		//minus.setToolTipText("Zoom Out, Also use \"-\" key or sroll wheel");
		minus.addListener(SWT.Selection,  new Listener() {
			public void handleEvent(Event event) {
				schemaCluster2DView.zoomOut(1);
				zoomCombo.setText(Integer.toString((int)(schemaCluster2DView.getZoom()*100)) + "%");
				//zoomCombo.setItem(0, Integer.toString((int)(schema2DPlot.getZoom()*100)) + "%");
				//zoomCombo.select(0);
				schemaCluster2DView.redraw();
			}
		});		
		
		new Label(toolBar, SWT.NONE).setLayoutData(new GridData(10, 1));
		
		new Label(toolBar, SWT.NONE).setText("Text Size:");
		this.textSizeCombo = new Combo(toolBar, SWT.DROP_DOWN);
		textSizeCombo.add("Small");
		textSizeCombo.add("Normal");
		textSizeCombo.add("Large");
		textSizeCombo.add("Hide");
		textSizeCombo.select(1);
		textSizeCombo.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent e) {				
				if(textSizeCombo.getSelectionIndex() != -1) {
					//TextSize textSize = TextSize.Normal;
					switch(textSizeCombo.getSelectionIndex()) {
					case 0: 
						schemaCluster2DView.setClusterObjectNamesVisible(true);
						schemaCluster2DView.setTextSize(TextSize.Small);
						break;		
					case 1: 
						schemaCluster2DView.setClusterObjectNamesVisible(true);
						schemaCluster2DView.setTextSize(TextSize.Normal);
						break;
					case 2: 
						schemaCluster2DView.setClusterObjectNamesVisible(true);
						schemaCluster2DView.setTextSize(TextSize.Large);
						break;
					case 3: 
						schemaCluster2DView.setClusterObjectNamesVisible(false);
						schemaCluster2DView.redraw();
						break;
					}
				}
			}
		});
		
		new Label(toolBar, SWT.NONE).setLayoutData(new GridData(10, 1));
		
		/*this.lockAspectRatioButton = new Button(toolBar, SWT.CHECK);
		lockAspectRatioButton.setText("Lock Aspect Ratio");
		lockAspectRatioButton.setSelection(schemaCluster2DView.isLockAspectRatio());
		lockAspectRatioButton.addListener(SWT.Selection,  new Listener() {
			public void handleEvent(Event event) {
				schemaCluster2DView.setLockAspectRatio(lockAspectRatioButton.getSelection());				
			}
		});
		*/
		new Label(toolBar, SWT.NONE).setText("Clusters:");
		this.clusterCombo = new Combo(toolBar, SWT.DROP_DOWN);
		clusterCombo.add("None");
		clusterCombo.add("Outline");
		clusterCombo.add("Fill");
		clusterCombo.select(2);
		clusterCombo.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent e) {				
				if(clusterCombo.getSelectionIndex() != -1) {
					switch(clusterCombo.getSelectionIndex()) {
					case 0: 
						schemaCluster2DView.setShowClusters(false);
						break;		
					case 1: 
						schemaCluster2DView.setShowClusters(true);
						schemaCluster2DView.setFillClusters(false);
						break;
					case 2: 
						schemaCluster2DView.setShowClusters(true);
						schemaCluster2DView.setFillClusters(true);
						break;
					}
					schemaCluster2DView.redraw();
				}
			}
		});
	}
}
