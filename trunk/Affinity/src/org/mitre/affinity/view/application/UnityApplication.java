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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.mitre.affinity.clusters.DistanceGrid;
import org.mitre.affinity.dimensionality_reducers.TalismanMDS;
import org.mitre.affinity.model.Position;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.SchemaDocument;
import org.mitre.affinity.util.AffinityUtils;
import org.mitre.affinity.view.craigrogram.ISchemaGUI;
import org.mitre.affinity.view.craigrogram.OvalSchemaGUI;
import org.mitre.affinity.view.craigrogram.Schema2DView;

public class UnityApplication {
	public static void main(String[] args) {
		new UnityApplication();
	}
	
	public UnityApplication() {		
		this("Unity TestZoomPan Application", null, null);
	}
		
	public UnityApplication(String appName, List<SchemaDocument> schemas, DistanceGrid dg) {
		if(dg == null) {
			//Create sample distances matrix
			//int numSchemas = 5;
			//DistanceGrid dg = createSampleDistancesGrid2();		
			dg = AffinityUtils.createSampleDistancesGrid();	
		}		

		//Create the schemas 
		if(schemas == null) {
			schemas = new ArrayList<SchemaDocument>();
			for(int i=1; i<=9; i++) {
				SchemaDocument s = new SchemaDocument();
				s.setId(i);
				s.setName("D" + Integer.toString(i));
				schemas.add(s);
			}
		}
		
		this.create2DPlot(appName, schemas, dg);
	}
	
	private void create2DPlot(String appName, List<SchemaDocument> schemas, DistanceGrid dg) {
		//Create the display and shell
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText(appName);
		shell.setLayout(new FillLayout());
		
		Decorations decoration = new Decorations(shell, SWT.NONE);
		decoration.setLayout(new FillLayout());		
		
		final Schema2DView schema2DPlot = new Schema2DView(decoration, SWT.NONE);
		
		// add menu		
		Menu menuBar = new Menu(decoration, SWT.BAR);
		decoration.setMenuBar(menuBar);		
		
		// add view menu
		MenuItem viewItem = new MenuItem(menuBar, SWT.CASCADE);
		viewItem.setText("View");
		
		Menu viewMenu = new Menu(decoration, SWT.DROP_DOWN);
		
		//MenuItem currentViewItem = new MenuItem(viewMenu, SWT.RADIO);
		//currentViewItem.setText("Current View");
		
		MenuItem showTitlesItem = new MenuItem(viewMenu, SWT.CHECK);
		showTitlesItem.setText("Show Schema Names");
		showTitlesItem.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {				
			}

			public void widgetSelected(SelectionEvent event) {			
				boolean selected = ((MenuItem)event.getSource()).getSelection();				
				schema2DPlot.setSchemaNamesVisible(selected);
			}
		});
		
		showTitlesItem.setSelection(true);
		
		viewItem.setMenu(viewMenu);			
		
		//Compute a 2-d position grid and scale it so the maximum value is 500
		final PositionGrid pg =  TalismanMDS.getTalismanMDSInstance().scaleDimensions(dg, true, true, 2, true);
		
		//Rescale the minimum and maximum position values
		Point bounds = shell.getSize();
		//pg.rescale(0, bounds.width);
		pg.rescale(0, bounds.x, 0, bounds.y);
		System.out.println("Size: " + bounds);
		
		//Plot the schemas in 2-d
		Color lightGray = new Color(display, 150, 150, 150);		
		final List<ISchemaGUI> schemaGUIs = new ArrayList<ISchemaGUI>();		
		for(SchemaDocument s : schemas) {
			Position pos = pg.getPosition(s.getId());
			System.out.println("Position for schema " + s.getName() + ": " + pos);
			//LabelSchemaGUI schemaGUI = new LabelSchemaGUI(schema2DPlot, SWT.HORIZONTAL, s.getId());
			OvalSchemaGUI schemaGUI = new OvalSchemaGUI(shell, s.getId(), 30, 30);
			schemaGUI.setLocation((int)pos.getDimensionValue(0), (int)pos.getDimensionValue(1));
			schemaGUI.setLabel(s.getName());
			schemaGUI.setBackground(lightGray);
			schemaGUI.setToolTipText(s.getName());
			//schemaGUI.pack(5,5, true);
			schemaGUIs.add(schemaGUI);			
		}
		schema2DPlot.setSchemata(schemaGUIs);
		schema2DPlot.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		/*
		this.mdsShell.addListener(SWT.Paint, new PaintListener(this.mdsShell, viewMode, schemaPane));
		//shell.addListener(SWT.MouseMove, new ToolTipListener(shell, schemaPane.getSchemaList()));
		this.mdsShell.addMouseMoveListener(new ToolTipListener(this.mdsShell, schemaPane.getSchemaList()));
		this.mdsShell.addMouseListener(new ClickListener(this.mdsShell, schemaPane.getSchemaList()));
		*/		
		Rectangle screenResolution = Display.getCurrent().getPrimaryMonitor().getBounds();				
		
		shell.setSize(screenResolution.width - 300, screenResolution.height - 200);
		//schema2DPlot.setSize(shell.getSize());
		shell.setLocation(new Point(150, 50));		
		
		//Add listener to rescale the plot when the app is resized
		shell.addControlListener(new ControlListener(){
			public void controlMoved(ControlEvent event) {				
				
			}
			public void controlResized(ControlEvent event) {
				System.out.println("Rescaling");
				
				Point size = shell.getSize();
				//Point size = schema2DPlot.getSize();
				//System.out.println("new size: " + size);
				pg.rescale(0, size.x - 30, 0, size.y - 80);			
				
				for(ISchemaGUI s : schemaGUIs) {
					Position pos = pg.getPosition(s.getSchemaID());
					s.setLocation((int)pos.getDimensionValue(0), (int)pos.getDimensionValue(1));
					System.out.println("Position for schema " + s.getSchemaID() + ": " + pos);
				}				
				
				schema2DPlot.redraw();
			}
		});
		
		//Add mouse listener to show schema name when the mouse is over a schema
		schema2DPlot.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent event) {			
				int mouseX = event.x;
				int mouseY = event.y;
				//System.err.println("x: " + event.x + ", y: " + event.y);
				List<ISchemaGUI> schemata = schema2DPlot.getSchemata();

				// very cpu intensive..
				for(ISchemaGUI schema : schemata){
					// get the rectangular bounds of the schema
					Point midpoint = schema.getLocation();
					Point size = schema.getSize();
					int width = size.x;
					int height = size.y;

					int x = midpoint.x - (width / 2); 
					int y = midpoint.y - (height / 2);

					if((mouseX >= x && mouseX <= x + width) &&
							(mouseY >= y && mouseY <= y + height)){
						//System.err.println("Mouse Is Over schema: " + schema.getLabel());
						schema.setMouseOver(true);
					}
					else {
						schema.setMouseOver(false);
					}
				}
			}			
		});

		shell.open();		
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}		
		display.dispose();
		shell.dispose();
		schema2DPlot.dispose();
		menuBar.dispose();
		lightGray.dispose();		
	}
}
