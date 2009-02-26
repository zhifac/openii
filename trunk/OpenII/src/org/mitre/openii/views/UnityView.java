package org.mitre.openii.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.mitre.galaxy.model.Schemas;
import org.mitre.galaxy.model.server.SchemaStoreManager;
import org.mitre.harmony.model.ProjectManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Schema;
import org.mitre.unity.clusters.DistanceGrid;
import org.mitre.unity.clusters.distanceFunctions.RandomDistanceFunction;
import org.mitre.cg.viz.gui.swt.ISchemaGUI;
import org.mitre.cg.viz.gui.swt.OvalSchemaGUI;
import org.mitre.cg.viz.gui.swt.Schema2DPlot;
import org.mitre.cg.viz.mds.TalismanMDS;
import org.mitre.cg.viz.model.Position;
import org.mitre.cg.viz.model.PositionGrid;
import org.mitre.cg.viz.model.SchemaDocument;

public class UnityView extends ViewPart {

	@Override
	public void createPartControl(Composite parent) {
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
		PositionGrid pg =  TalismanMDS.scaleDimensions(dg, true, true, 2, true);
		//Rescale the minimum and maximum position values
		Rectangle bounds = parent.getBounds();
		pg.rescale(0, 500);		
		
		//Create the 2D layout
		Composite unityComponent = new Composite(parent, SWT.EMBEDDED);
		
		Color lightGray = new Color(parent.getDisplay(), 150, 150, 150);
		Schema2DPlot schema2DPlot = new Schema2DPlot(unityComponent, SWT.NONE);
		List<ISchemaGUI> schemaGUIs = new ArrayList<ISchemaGUI>();		
		for(SchemaDocument s : schemaDocuments) {
			Position pos = pg.getPosition(s.getId());
			//System.out.println("Position for schema " + s.getName() + ": " + pos);
			//LabelSchemaGUI schemaGUI = new LabelSchemaGUI(schema2DPlot, SWT.HORIZONTAL, s.getId());
			OvalSchemaGUI schemaGUI = new OvalSchemaGUI(parent.getShell(), s.getId());
			schemaGUI.setLocation((int)pos.getDimensionValue(0), (int)pos.getDimensionValue(1));
			schemaGUI.setLabel(s.getName());
			schemaGUI.setBackground(lightGray);
			schemaGUI.setToolTipText(s.getName());
			schemaGUI.pack(5,5, true);
			schemaGUIs.add(schemaGUI);			
		}
		schema2DPlot.setSchemata(schemaGUIs);
		schema2DPlot.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		System.out.println(parent.getSize());
		schema2DPlot.setSize(500, 600);
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
