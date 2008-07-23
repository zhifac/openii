package org.openii.schemr.viz;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.mitre.schemastore.graph.GraphBuilder;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemr.MatchSummary;
import org.openii.schemr.SchemaUtility;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;

public class PrefuseVisualizer implements Visualizer {

	private static final Logger logger = Logger.getLogger(PrefuseVisualizer.class.getName());



	private static final String VIZ_NAME = "schema";
	private static final String VIZ_EDGES = VIZ_NAME+".edges";
	private static final String VIZ_NODES = VIZ_NAME+".nodes";



	private Visualization vis;
	private Display d;
	private MatchSummary matchSummary;
	
    public PrefuseVisualizer(MatchSummary matchSummary) throws RemoteException {
    	if (matchSummary == null) throw new IllegalArgumentException("match summary must not be null");
    	
    	this.matchSummary = matchSummary;
    	
    	// -- 1. create schema graph for visualization
    	Schema schema = this.matchSummary.getSchema();
		ArrayList<SchemaElement> schemaElements = SchemaUtility.getCLIENT().getSchemaElements(schema.getId());
		schemaElements = GraphBuilder.build(schemaElements, schema.getId());

		Graph graph = new SchemaGraphReader().readGraph(schema, schemaElements);

        this.vis = new Visualization();
        this.vis.add(VIZ_NAME, graph);
        this.vis.setInteractive(VIZ_EDGES, null, false);
        
        // -- 3. the renderers and renderer factory ---------------------------
        
        setupRenderers();
        
        // -- 4. the processing actions ---------------------------------------
        
        setupProcessingActions();
        
        // -- 5. the display and interactive controls -------------------------
        setupDisplay();
        
    }



	private void setupRenderers() {
		// draw the "name" label for NodeItems
        LabelRenderer r = new LabelRenderer(NAME);
        r.setRoundedCorner(8, 8); // round the corners
        
        // create a new default renderer factory
        // return our name label renderer as the default for all non-EdgeItems
        // includes straight line edges for EdgeItems by default
        this.vis.setRendererFactory(new DefaultRendererFactory(r));
	}

	private void setupProcessingActions() {
		// create our nominal color palette
        int[] palette = new int[] {
            ColorLib.rgb(255,180,180), 
            ColorLib.rgb(190,190,255)
        };
        // map nominal data values to colors using our provided palette
        DataColorAction fill = new DataColorAction(VIZ_NODES, TYPE,
                Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
        // use black for node text
        ColorAction text = new ColorAction(VIZ_NODES,
                VisualItem.TEXTCOLOR, ColorLib.gray(0));
        // use light grey for edges
        ColorAction edges = new ColorAction(VIZ_EDGES,
                VisualItem.STROKECOLOR, ColorLib.gray(200));
        
        // create an action list containing all color assignments
        ActionList color = new ActionList();
        color.add(fill);
        color.add(text);
        color.add(edges);
        
        // create an action list with an animated layout
//        ActionList layout = new ActionList(Activity.INFINITY);
        ActionList layout = new ActionList(Activity.DEFAULT_STEP_TIME);
        layout.add(new ForceDirectedLayout(VIZ_NAME));
        layout.add(new RepaintAction());
        
        // add the actions to the visualization
        this.vis.putAction("color", color);
        this.vis.putAction("layout", layout);
	}

	private void setupDisplay() {
		this.d = new Display(this.vis);
        this.d.setSize(720, 500); // set display size
        // drag individual items around
        this.d.addControlListener(new DragControl());
        // pan with left-click drag on background
        this.d.addControlListener(new PanControl()); 
        // zoom with right-click drag
        this.d.addControlListener(new ZoomControl());
	}

	public void show() {
        
        // create a new window to hold the visualization
        JFrame frame = new JFrame("Schemr");
        // ensure application exits when window is closed
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(this.d);
        frame.pack();           // layout components in window
        frame.setVisible(true); // show the window
        
        // assign the colors
        this.vis.run("color");
        // start up the animated layout
        this.vis.run("layout");
		
	}
    
}
