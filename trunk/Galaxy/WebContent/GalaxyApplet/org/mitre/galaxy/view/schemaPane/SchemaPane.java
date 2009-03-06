// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.schemaPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashSet;

import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mitre.galaxy.model.SelectedObjects;
import org.mitre.galaxy.model.listeners.SearchListener;
import org.mitre.galaxy.model.listeners.SelectedObjectsListener;
import org.mitre.galaxy.model.search.SearchManager;
import org.mitre.galaxy.view.schemaPane.navigatorPane.NavigatorPane;

import prefuse.Constants;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.layout.graph.NodeLinkTreeLayout;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;

/** Class for displaying the explorer pane of Galaxy */
public class SchemaPane extends JPanel implements ComponentListener, SelectedObjectsListener, SearchListener
{	
	/** Stores the selected schema ID */
	private Integer schemaID;
	
	/** Stores the comparison schema ID */
	private Integer comparisonSchemaID;

	/** Stores the list of matched elements to be shown in the schema pane */
	private HashSet<Integer> matchedElements = new HashSet<Integer>();
	
	/** Keeps track of if the schema graph needs to be updated */
	private boolean updated = false;
	
	/** Stores the visualization */
	public Visualization vis = new Visualization();
	
	/** Stores the display */
	public SchemaPaneDisplay display = new SchemaPaneDisplay(vis);

	/** Stores the schema tree associated with this display */
	private SchemaTree schemaTree = new SchemaTree();
	
	/** Stores the navigator pane */
	private NavigatorPane navigatorPane = null;
	
	/** Updates the visualization graph */
	private void update()
	{
		// Don't update graph while not visible
		updated=false;
		if(!isVisible()) return;
		
		// Display the schema graph if a schema is currently selected
		if(schemaID!=null)
		{
			schemaTree.buildTree(schemaID, comparisonSchemaID);
			vis.run("color");
			vis.run("font");
			vis.run("layout");
			try { Thread.sleep(50); } catch(Exception e) {}
			display.isCentered = false;
			
			// Resets the navigator pane
			navigatorPane.reset();
		}
		
		// Mark that the schema graph is now updated
		updated=true;
	}
	
	/** Returns the layout used with the schema graph */
	private ActionList getLayouts()
	{
		ActionList layouts = new ActionList();
		layouts.add(new NodeLinkTreeLayout("graph",Constants.ORIENT_LEFT_RIGHT,15,15,15));
		layouts.add(new RepaintAction());
		return layouts;
	}
	
	/** Constructs the Editor Pane */
	public SchemaPane(Integer schemaID, Integer comparisonSchemaID)
	{
		// Stores the selected and comparison schema IDs
		this.schemaID = schemaID;
		this.comparisonSchemaID = comparisonSchemaID;
		
		// Initialize the graph display
		display.setBackground(new Color((float)1.0,(float)1.0,(float)0.85));
		display.addControlListener(new DragControl());
		display.addControlListener(new PanControl());
		display.addControlListener(new ZoomControl());
		
		// Constructs the desktop pane
		JDesktopPane pane = new JDesktopPane();
		pane.add(navigatorPane = new NavigatorPane(this),1);
		pane.add(display,2);

		// Construct the pane
		setBorder(new EmptyBorder(1,1,1,1));
		setLayout(new BorderLayout());
		add(pane,BorderLayout.CENTER);
		
		// Initialize the graph visualization
		vis.setRendererFactory(new DefaultRendererFactory(new SchemaLabelRenderer(this),new EdgeRenderer(Constants.EDGE_TYPE_LINE,Constants.EDGE_ARROW_FORWARD)));
		vis.putAction("color", new SchemaColors(this).getColors());
		vis.putAction("font", SchemaFonts.getFonts());
		vis.putAction("layout", getLayouts());
		vis.add("graph", schemaTree);
		
		// Add listeners for various actions which affect this pane
		addComponentListener(this);
	}
	
	/** Gets the schema ID associated with this pane */
	public Integer getSchemaID()
		{ return schemaID; }
	
	/** Gets the comparison schema ID associated with this pane */
	public Integer getComparisonSchemaID()
		{ return comparisonSchemaID; }

	/** Indicates if the search results contains the specified element */
	public boolean containsSearchElement(Integer elementID)
		{ return matchedElements.contains(elementID); }
	
	/** Draws a border around the pane */
	public void paint(Graphics g)
	{
		// Update the schema graph if needed
		if(!updated) update();
		
		// Paint the schema graph and border
		super.paint(g);
		g.setColor(Color.gray);
		g.drawLine(0,0,0,getHeight()-1);
		g.drawLine(0,0,getWidth()-1,0);
		g.drawLine(0,getHeight()-1,getWidth()-1,getHeight()-1);
		g.drawLine(getWidth()-1,0,getWidth()-1,getHeight()-1);	
	}
	
	/** Handles changes to the selected schema */
	public void selectedSchemaChanged()
		{ schemaID=SelectedObjects.getSelectedSchema(); update(); }

	/** Handles changes to the selected comparison schema */
	public void selectedComparisonSchemaChanged()
		{ comparisonSchemaID=SelectedObjects.getSelectedComparisonSchema(); update(); }

	/** Handles the selection of schema objects */
	public void searchResultsChanged()
	{
		matchedElements = SearchManager.getMatchedElements();
		vis.run("color"); repaint();
	}
	
	/** Adjust the size of the various components when this pane is resized */
	public void componentResized(ComponentEvent e)
		{ display.setBounds(0,0,getWidth(),getHeight()); update(); }	
	
	// Unused listener events
	public void componentShown(ComponentEvent e) {}
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void selectedGroupsChanged() {}
}
