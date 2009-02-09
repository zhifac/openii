// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.extensionsPane;

import java.applet.AppletContext;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mitre.galaxy.model.Schemas;
import org.mitre.galaxy.model.SelectedObjects;
import org.mitre.galaxy.model.listeners.SelectedObjectsListener;
import org.mitre.galaxy.view.GalaxyApplet;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Schema;


import prefuse.Constants;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.layout.graph.NodeLinkTreeLayout;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.render.DefaultRendererFactory;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

/** Class for displaying the extensions pane of Galaxy */
public class ExtensionsPane extends JPanel implements ComponentListener, SelectedObjectsListener
{	
	/** Stores the current display */
	protected ExtensionPaneDisplay display = null;
	
	/** Stores the display cache */
	private HashMap<Integer,ExtensionPaneDisplay> cachedDisplays = new HashMap<Integer,ExtensionPaneDisplay>();
	
	/** Private class for handling the action of an item being clicked */
	private class ClickControl extends ControlAdapter
	{
		/** Handles the action of an item being clicked */
		public void itemClicked(VisualItem item, MouseEvent e)
		{
			if(item instanceof NodeItem)
			{
				Object object = ((NodeItem)item).get("NodeObject");
				if(object instanceof Schema)
				{
					Integer schemaID = ((Schema)object).getId();
					if(SelectedObjects.inSelectedGroups(schemaID))
					{
						if(e.getButton()==MouseEvent.BUTTON1 && !e.isMetaDown())
							SelectedObjects.setSelectedSchema(schemaID);
						else if(schemaID!=SelectedObjects.getSelectedSchema())
							SelectedObjects.setSelectedComparisonSchema(schemaID);
					}
				}
				else
				{
				    try
				    {
				      AppletContext a = GalaxyApplet.galaxyApplet.getAppletContext();
				      URL url = new URL(((DataSource)object).getUrl());
				      if(url!=null) a.showDocument(url,"_blank");
				    }
				    catch (MalformedURLException exp){}
				}
			}
		}
	}
	
	/** Set up the layouts to use with this graph */
	private ActionList getLayouts()
	{
		ActionList layouts = new ActionList();
		layouts.add(new NodeLinkTreeLayout("graph",Constants.ORIENT_LEFT_RIGHT,40,20,20));
		return layouts;
	}
	
	/** Constructs the Extensions Pane */
	public ExtensionsPane()
	{
		// Construct the pane
		setBorder(new EmptyBorder(1,1,1,1));
		setLayout(new BorderLayout());
	
		// Add listeners for various actions which affect this pane
		SelectedObjects.addSelectedObjectsListener(this);
		addComponentListener(this);
		
		// Display the selected schema
		selectedSchemaChanged();
	}

	/** Refreshes the Extensions Pane */
	public void refresh()
	{
		if(display!=null)
		{
			display.getVisualization().run("color");
			revalidate();
			repaint();
		}
	}
	
	/** Handles changes to the selected schema */
	public void selectedSchemaChanged()
	{
		Integer schemaID = SelectedObjects.getSelectedSchema();
		if(schemaID!=null)
		{
			// If no cached display found, generate new display
			display = cachedDisplays.get(schemaID);
			if(display==null)
			{
				// Get the list of schemas which make up the display
				Collection<Integer> associatedSchemaIDs = Schemas.getAssociatedSchemas(schemaID);
				
				// Initialize the graph visualization
				Visualization vis = new Visualization();
				vis.setRendererFactory(new DefaultRendererFactory(new ExtensionLabelRenderer(),new ExtensionEdgeRenderer()));
				vis.putAction("color", ExtensionColors.getColors());
				vis.putAction("font", ExtensionFonts.getFonts());
				vis.putAction("layout", getLayouts());

				// Initialize the graph display
				display = new ExtensionPaneDisplay(vis);
				display.setBackground(new Color((float)1.0,(float)1.0,(float)0.85));
				display.addControlListener(new DragControl());
				display.addControlListener(new PanControl());
				display.addControlListener(new ZoomControl());
				display.addControlListener(new ClickControl());
				
				// Run the visualization functions
				vis.add("graph", new ExtensionGraph(associatedSchemaIDs));
				vis.run("font");
				vis.run("layout");
				display.isCentered = false;
				display.setBounds(0,0,getWidth(),getHeight());
				
				// Cache the generated display
				for(Integer associatedSchemaID : associatedSchemaIDs)
					cachedDisplays.put(associatedSchemaID,display);
			}
			removeAll();
			add(display,BorderLayout.CENTER);
		}
		refresh();
	}
	
	/** Refreshes the extensions pane when a new comparison schema is selected */
	public void selectedComparisonSchemaChanged()
		{ refresh(); }

	/** Handles changes to the selected groups */
	public void selectedGroupsChanged()
		{ refresh(); }
	
	/** Centers the display when the component is moved */
	public void componentResized(ComponentEvent e)
	{
		for(ExtensionPaneDisplay display : cachedDisplays.values())
		{
			display.isCentered = false;
			display.setBounds(0,0,getWidth(),getHeight());
		}
		repaint();
	}
	
	/** Unused action listeners */
	public void componentHidden(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
}
