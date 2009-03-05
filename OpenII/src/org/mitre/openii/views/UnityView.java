package org.mitre.openii.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.part.ViewPart;
import org.mitre.galaxy.model.Schemas;
import org.mitre.galaxy.model.server.SchemaStoreManager;
import org.mitre.harmony.model.ProjectManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Schema;
import org.mitre.unity.clusters.DistanceGrid;
import org.mitre.unity.clusters.distanceFunctions.RandomDistanceFunction;
import org.mitre.unity.view.swt.ISchemaGUI;
import org.mitre.unity.view.swt.OvalSchemaGUI;
import org.mitre.unity.view.swt.Schema2DPlot;
import org.mitre.unity.dimensionality_reducers.TalismanMDS;
import org.mitre.unity.model.Position;
import org.mitre.unity.model.PositionGrid;
import org.mitre.unity.model.SchemaDocument;

public class UnityView extends ViewPart {

	@Override
	public void createPartControl(final Composite parent) {		
		// have the parent use a fill layout
		parent.setLayout(new FillLayout());
		
		Decorations decoration = new Decorations(parent, SWT.NONE);
		decoration.setLayout(new FillLayout());
		
		
		final Schema2DPlot schema2DPlot = new Schema2DPlot(decoration, SWT.NONE);
		
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
		showTitlesItem.setText("Show Schema Name");
		showTitlesItem.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent event) {
				// TODO Auto-generated method stub
				boolean selected = ((MenuItem)event.getSource()).getSelection();
				//System.err.println("Item Selected: " + selected);
				schema2DPlot.setTitlesVisible(selected);
			}
			
		});
		
		showTitlesItem.setSelection(true);
		
		viewItem.setMenu(viewMenu);
		
		// Initialize the connection to SchemaStore
		SchemaStoreManager.setClient(OpenIIManager.getConnection());

		// Get the schemas
		List<Schema> schemas = Schemas.getSchemas();
		List<SchemaDocument> schemaDocuments = new ArrayList<SchemaDocument>();
		List<Integer> schemaIDs = new ArrayList<Integer>();
		if(schemas != null && !schemas.isEmpty()) {	
			for(Schema schema : schemas) {
				schemaIDs.add(schema.getId());
				schemaDocuments.add(new SchemaDocument(schema.getId(), schema.getName(), schema.getAuthor(),
						schema.getSource(), schema.getType(), schema.getDescription(), false));
				//Integer id, String name, String author, String source, String type, String description, boolean locked
			}
		}
		
		//Compute the distance grid
		RandomDistanceFunction distanceFunction = new RandomDistanceFunction();
		DistanceGrid dg = distanceFunction.generateDistanceGrid((ArrayList<Integer>)schemaIDs);
		final PositionGrid pg =  TalismanMDS.scaleDimensions(dg, true, true, 2, true);
		
		//Rescale the minimum and maximum position values
		//Rectangle bounds = schema2DPlot.getBounds();
		//System.out.println("Bounds: " + bounds);
		pg.rescale(0, 700, 0, 700);
		
		//Create the 2D layout
		//Composite unityComponent = new Composite(parent, SWT.EMBEDDED);
		
		Color lightGray = new Color(parent.getDisplay(), 150, 150, 150);
		//Schema2DPlot schema2DPlot = new Schema2DPlot(unityComponent, SWT.NONE);
		
		final List<ISchemaGUI> schemaGUIs = new ArrayList<ISchemaGUI>();		
		for(SchemaDocument s : schemaDocuments) {
			Position pos = pg.getPosition(s.getId());
			//System.out.println("Position for schema " + s.getName() + ": " + pos);
			//LabelSchemaGUI schemaGUI = new LabelSchemaGUI(schema2DPlot, SWT.HORIZONTAL, s.getId());
			OvalSchemaGUI schemaGUI = new OvalSchemaGUI(parent.getShell(), s.getId(), 30, 30);
			schemaGUI.setLocation((int)pos.getDimensionValue(0), (int)pos.getDimensionValue(1));
			schemaGUI.setLabel(s.getName());
			schemaGUI.setBackground(lightGray);
			schemaGUI.setToolTipText(s.getName());
			//schemaGUI.pack(5,5, true);
			schemaGUIs.add(schemaGUI);			
		}
		schema2DPlot.setSchemata(schemaGUIs);
		schema2DPlot.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		//System.out.println(parent.getSize());		
		
		//Add listener to rescale the plot when the app is resized
		parent.addControlListener(new ControlListener() {
			@Override
			public void controlMoved(ControlEvent event) {}
			
			@Override
			public void controlResized(ControlEvent event) {
				//System.out.println("Rescaling");
				
				Point size = parent.getSize();
				//Point size = schema2DPlot.getSize();
				//System.out.println("new size: " + size);
				pg.rescale(0, size.x - 30, 0, size.y - 80);			
				
				for(ISchemaGUI s : schemaGUIs) {
					Position pos = pg.getPosition(s.getSchemaID());
					s.setLocation((int)pos.getDimensionValue(0), (int)pos.getDimensionValue(1));
					//System.out.println("Position for schema " + s.getSchemaID() + ": " + pos);
				}				
				
				schema2DPlot.redraw();
			}
		});
		
		//Add mouse listener to show schema name when the mouse is over a schema
		schema2DPlot.addMouseMoveListener(new MouseMoveListener() {
			@Override
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
						schema.setMouseOver(true);
					}
					else {
						schema.setMouseOver(false);
					}
				}
			}			
		});

		//Add mouse listener to notifiy when a schema is double clicked
		schema2DPlot.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {				
				List<ISchemaGUI> schemata = schema2DPlot.getSchemata();
				for(ISchemaGUI schema : schemata){					
					if(schema.isMouseOver()){
						schema.doubleClicked();
						break;
					}					
				}				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {}

			@Override
			public void mouseUp(MouseEvent arg0) {}
		});	
	}

	@Override
	public void setFocus() {}
	
	/** Shuts down the Harmony View */
	public void dispose()
	{
		ProjectManager.save();
		super.dispose();
	}

}
