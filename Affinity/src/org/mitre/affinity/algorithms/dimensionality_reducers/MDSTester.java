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

package org.mitre.affinity.algorithms.dimensionality_reducers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;

import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.Position;
import org.mitre.affinity.model.clusters.DistanceGrid;
import org.mitre.affinity.util.AffinityUtils;
import org.mitre.affinity.view.IClusterObjectGUI;
import org.mitre.affinity.view.craigrogram.OvalClusterObjectGUI;
import org.mitre.affinity.view.craigrogram.ClusterObject2DView;
import org.mitre.schemastore.model.Schema;


/**
 * Creates a Shephard Plot and 2D layout to verify the results of an MDS algorithm run.
 * 
 * @author CBONACETO
 *
 */
public class MDSTester {
	
	public MDSTester(ArrayList<Integer> schemas, final DistanceGrid<Integer> dg, final PositionGrid<Integer> pg) {
		//Create the display and shell
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("MDS Visual TestZoomPan");			
		//shell.setLayout(new FormLayout());
		shell.setLayout(new FillLayout());
		
		// Create sash that the 2D Layout and Shephard Plot will be attached to
		final Composite sashPane = new Composite(shell, SWT.NONE);		
		sashPane.setLayout(new FormLayout());
		final Sash sash = new Sash(sashPane, SWT.VERTICAL);		
		sash.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));		
		final FormData sashData = new FormData ();
		sashData.left = new FormAttachment (50, 0);
		sashData.top = new FormAttachment (0, 0);
		sashData.bottom = new FormAttachment (100, 0);
		sash.setLayoutData(sashData);		
		
		int schemaDiameter = 10;		
		
		//Create the 2D Layout	
		Color black = shell.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		Color lightGray = new Color(shell.getDisplay(), 150, 150, 150);		
		final List<IClusterObjectGUI<Integer, Schema>> schemaGUIs = new ArrayList<IClusterObjectGUI<Integer, Schema>>();		
		for(Integer sID : schemas) {
			Position pos = pg.getPosition(sID);
			if(pos == null)
				System.err.println("Error: position for schema " + sID + " is null");		
			OvalClusterObjectGUI<Integer, Schema> schemaGUI = new OvalClusterObjectGUI<Integer, Schema>(shell, sID, 
					schemaDiameter, schemaDiameter);			
			schemaGUI.setLocation((int)pos.getDimensionValue(0), (int)pos.getDimensionValue(1));			
			schemaGUI.setLabel(sID.toString());
			schemaGUI.setLabelColor(black);
			schemaGUI.setForeground(lightGray);
			schemaGUI.setBackground(lightGray);
			schemaGUI.setToolTipText(sID.toString());		
			schemaGUIs.add(schemaGUI);			
		}
		final Composite schema2DPlotPane = new Composite(sashPane, SWT.BORDER);
		schema2DPlotPane.setLayout(new FillLayout());
		final ClusterObject2DView<Integer, Schema> schema2DPlot = new ClusterObject2DView<Integer, Schema>(schema2DPlotPane, SWT.NONE, schemaGUIs);
		schema2DPlotPane.setBackground(sash.getBackground());
		schema2DPlot.setBackground(sash.getBackground());
		FormData pane1Data = new FormData ();
		pane1Data.left = new FormAttachment (0, 0);
		pane1Data.right = new FormAttachment (sash, 0);
		pane1Data.top = new FormAttachment (0, 0);
		pane1Data.bottom = new FormAttachment (100, 0);
		schema2DPlotPane.setLayoutData(pane1Data);		
		
		//Create the Shephard Plot
		//Also compute and display the Pearson's correlation coefficient				
		int numSchemas = schemas.size();
		final List<IClusterObjectGUI<Integer, Schema>> widgetDistances = new ArrayList<IClusterObjectGUI<Integer, Schema>>();
		//final PositionGrid pg2 = new PositionGrid(pg.getNumDimensions());
		//Used to compute Pearson's R:		
		double sumX = 0;
		double sumY = 0;
		double sumXSquared = 0;
		double sumYSquared = 0;
		double sumXY = 0;
		int n = 0;
		for(Integer s1=1; s1<=numSchemas; s1++) {
			//pg2.setPosition(s1, new Position(pg.getPosition(s1)));
			for(Integer s2=s1; s2<=numSchemas; s2++) {				
				if(s1 != s2) {					
					double origDistance = (dg.get(s1, s2));// * scaleFactor;
					double newDistance = AffinityUtils.computeDistance(pg.getPosition(s1), pg.getPosition(s2));
					OvalClusterObjectGUI<Integer, Schema> schemaGUI = 
						new OvalClusterObjectGUI<Integer, Schema>(shell, s1, schemaDiameter, schemaDiameter);			
					schemaGUI.setLocation((int)origDistance, (int)newDistance);			
					schemaGUI.setLabel(s1 + "-" + s2);
					schemaGUI.setLabelColor(black);
					schemaGUI.setForeground(lightGray);
					schemaGUI.setBackground(lightGray);
					schemaGUI.setToolTipText(schemaGUI.getLabel());								
					//widgetDistances.add(new SchemaGUI(new SchemaDocument(s1, s1 + "-" + s2, "", false), (int)origDistance, (int)newDistance));
					widgetDistances.add(schemaGUI);
					System.out.println(s1 + "," + s2 + ": Orig Dist=" + origDistance + ", New Dist=" + newDistance);
					sumX += origDistance;
					sumY += newDistance;
					sumXSquared += origDistance * origDistance;
					sumYSquared += newDistance * newDistance;
					sumXY += origDistance * newDistance;
					n++;
				}					
			}
		}		
		double corr = (sumXY - (sumX*sumY)/n) /
			Math.sqrt((sumXSquared -(sumX*sumX)/n) * (sumYSquared -(sumY*sumY)/n));
		System.out.println("Correlation: " + corr);
		//this.shepherdShell.setText(this.appName + " Shepherd Plot: R = " + corr);		
		final Composite shephardPlotPane = new Composite(sashPane, SWT.NONE);	
		shephardPlotPane.setLayout(new FillLayout());				
		final ClusterObject2DView<Integer, Schema> shephardPlot = 
			new ClusterObject2DView<Integer, Schema>(shephardPlotPane, SWT.NONE, widgetDistances);
		shephardPlotPane.setBackground(sash.getBackground());
		shephardPlot.setBackground(sash.getBackground());
		FormData pane2Data = new FormData ();
		pane2Data.left = new FormAttachment (sash, 0);
		pane2Data.right = new FormAttachment (100, 0);
		pane2Data.top = new FormAttachment (0, 0);
		pane2Data.bottom = new FormAttachment (100, 0);		
		shephardPlotPane.setLayoutData(pane2Data);				
		
		// Add the sash resize listener	
		sash.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				int limit = 20;	
				Rectangle sashRect = sash.getBounds ();
				Rectangle shellRect = shell.getClientArea ();
				int right = shellRect.width - sashRect.width - limit;
				e.x = Math.max (Math.min (e.x, right), limit);
				if (e.x != sashRect.x)  {
					sashData.left = new FormAttachment (0, e.x);
					sash.getParent().layout();
				}
			}
		});	
		
		//Add the shell resize listener
		shell.addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent event) {}
			
			public void controlResized(ControlEvent event) {
				//System.out.println("resizing");	
				int schema2DPlotWidth = schema2DPlotPane.getSize().x;	
				if(schema2DPlotWidth > 0) {
					//System.out.println("width: " + schema2DPlotWidth);
					//System.out.println("percent: " + ((float)schema2DPlotWidth/(schema2DPlotWidth + shephardPlotPane.getSize().x)) * 100);
					sashData.left = new FormAttachment ((int)((float)schema2DPlotWidth/(schema2DPlotWidth + shephardPlotPane.getSize().x) * 100), 0);				
					sashPane.layout();
				}
			}
		});
		
		//Add listeners to resize the 2D Layout and Shephard Plot
		schema2DPlot.addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent event) {}			
			public void controlResized(ControlEvent event) {
				Point size = schema2DPlot.getSize();
				pg.rescale(20, size.x - 20, 20, size.y - 20);
				for(IClusterObjectGUI<Integer, Schema> s : schema2DPlot.getClusterObjects()) {
					Position pos = pg.getPosition(s.getObjectID());
					s.setLocation((int)pos.getDimensionValue(0), (int)pos.getDimensionValue(1));					
				}				
				schema2DPlot.redraw();
			}
		});
		
		/*
		shephardPlot.addControlListener(new ControlListener() {
			@Override
			public void controlMoved(ControlEvent event) {}			
			@Override
			public void controlResized(ControlEvent event) {
				Point size = shephardPlot.getSize();
				pg2.rescale(20, size.x - 20, 20, size.y - 20);
				for(ISchemaGUI s : shephardPlot.getSchemata()) {
					Position pos = pg2.getPosition(s.getSchemaID());
					s.setLocation((int)pos.getDimensionValue(0), (int)pos.getDimensionValue(1));					
				}				
				shephardPlot.redraw();
			}
		});
		*/

		Rectangle screenSize = Display.getCurrent().getPrimaryMonitor().getBounds();
		shell.setSize(screenSize.width - 199, screenSize.height - 199);		
		shell.setLocation((screenSize.width - shell.getSize().x)/2, (screenSize.height - shell.getSize().y)/2);
		shell.open();
		shell.setSize(screenSize.width - 200, screenSize.height - 200);
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}	
		if(shell != null && !shell.isDisposed())
			shell.dispose();
		if(display != null && !display.isDisposed())
			display.dispose();	
		
	}	
	
	/**
	 * Given a distance grid and 2D space, compute the correlation between the original distance and the
	 * new distance in the 2D space. 
	 * 
	 * @param schemas
	 * @param dg
	 * @param pg
	 * @return
	 */
	public static double compute2DFitCorrelation(ArrayList<Integer> schemas, DistanceGrid<Integer> dg, PositionGrid<Integer> pg) {
		double sumX = 0;
		double sumY = 0;
		double sumXSquared = 0;
		double sumYSquared = 0;
		double sumXY = 0;
		int n = 0;		
		for(Integer s1 : schemas) {			
			for(Integer s2 : schemas) {				
				if(s1 != s2) {					
					double origDistance = (dg.get(s1, s2));// * scaleFactor;
					double newDistance = AffinityUtils.computeDistance(pg.getPosition(s1), pg.getPosition(s2));					
					//System.out.println(s1 + "," + s2 + ": Orig Dist=" + origDistance + ", New Dist=" + newDistance);
					sumX += origDistance;
					sumY += newDistance;
					sumXSquared += origDistance * origDistance;
					sumYSquared += newDistance * newDistance;
					sumXY += origDistance * newDistance;
					n++;
				}					
			}
		}		
		return (sumXY - (sumX*sumY)/n) /
			Math.sqrt((sumXSquared -(sumX*sumX)/n) * (sumYSquared -(sumY*sumY)/n));		
	}
}
