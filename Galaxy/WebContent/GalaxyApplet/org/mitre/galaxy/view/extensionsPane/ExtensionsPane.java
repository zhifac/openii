// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.extensionsPane;

import java.applet.AppletContext;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mitre.galaxy.model.Schemas;
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
import prefuse.data.tuple.TableNode;
import prefuse.render.DefaultRendererFactory;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

/** Class for displaying the extensions pane of Galaxy */
public class ExtensionsPane extends JPanel implements ComponentListener
{	
	/** Stores the selected schema ID */
	private Integer schemaID;
	
	/** Stores the comparison schema ID */
	private Integer comparisonSchemaID;

	/** Stores the currently selected group schemas */
	private HashSet<Integer> selectedGroupSchemas = null;
	
	/** List of listeners monitoring the extensions pane */
	private ArrayList<ExtensionsPaneListener> listeners = new ArrayList<ExtensionsPaneListener>();
	
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
					if(inSelectedGroups(schemaID))
						for(ExtensionsPaneListener listener : listeners)
							listener.schemaSelected(schemaID, e.getButton());
				}
				else
				{
				    try
				    {
				      AppletContext a = GalaxyApplet.galaxyApplet.getAppletContext();
				      URL url = new URL(((DataSource)object).getUrl());
				      if(url!=null) a.showDocument(url,"_blank");
				    }
				    catch(Exception exp) {}
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
		addComponentListener(this);
	}

	/** Gets the schema ID associated with this pane */
	public Integer getSchemaID()
		{ return schemaID; }
	
	/** Gets the comparison schema ID associated with this pane */
	public Integer getComparisonSchemaID()
		{ return comparisonSchemaID; }

	/** Indicates if the specified schema is contained in selected groups */
	public boolean inSelectedGroups(Integer schemaID)
	{
		if(selectedGroupSchemas==null) return true;
		return selectedGroupSchemas.contains(schemaID);
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
	public void setSchema(Integer schemaID)
	{
		this.schemaID = schemaID;
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
				vis.setRendererFactory(new DefaultRendererFactory(new ExtensionLabelRenderer(this),new ExtensionEdgeRenderer()));
				vis.putAction("color", new ExtensionColors(this).getColors());
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
	
	/** Adds the specified schema to the graph */
	public void addSchema(Integer schemaID)
	{
		Integer parentID = Schemas.getParentSchemas(schemaID).get(0);
		ExtensionPaneDisplay display = cachedDisplays.get(parentID);
		if(display!=null)
		{				
			// Modify the graph to add the new schema
			ExtensionGraph graph = (ExtensionGraph)display.getVisualization().getSourceData("graph");
			graph.addSchema(schemaID);
				
			// Adjust location of added schema
			VisualItem parentItem=null, childItem=null;
			Iterator items = display.getVisualization().items();
			while(items.hasNext())
			{
				VisualItem item = (VisualItem)items.next();
				if(item.getSourceTuple() instanceof TableNode)
				{
					Object object = ((NodeItem)item).get("NodeObject");
					if(object instanceof Schema)
					{
						Integer itemSchemaID = ((Schema)object).getId();
						if(itemSchemaID.equals(schemaID)) childItem=item;
						if(itemSchemaID.equals(parentID)) parentItem=item;
					}
				}
			}
			childItem.setX(parentItem.getBounds().getMinX()+childItem.getBounds().getWidth()/2+20);
			childItem.setY(parentItem.getBounds().getMinY()+childItem.getBounds().getHeight()/2-10);
			childItem.setHover(true);
			refresh();

			// Cache the display for the added schema
			cachedDisplays.put(schemaID,display);
		}
	}
	
	/** Removes the specified schema from the graph */
	public void removeSchema(Integer schemaID)
	{
		ExtensionPaneDisplay display = cachedDisplays.get(schemaID);
		if(display!=null)
		{
			// Modify the graph to remove the new schema
			ExtensionGraph graph = (ExtensionGraph)display.getVisualization().getSourceData("graph");
			graph.removeSchema(schemaID);
			refresh();
			
			// Remove the display for the removed schema
			cachedDisplays.remove(schemaID);
		}		
	}
	
	/** Refreshes the extensions pane when a new comparison schema is selected */
	public void setComparisonSchema(Integer comparisonSchemaID)
		{ this.comparisonSchemaID=comparisonSchemaID; refresh(); }

	/** Handles changes to the selected groups */
	public void setSelectedGroupSchemas(HashSet<Integer> selectedGroupSchemas)
		{ this.selectedGroupSchemas = selectedGroupSchemas; refresh(); }
	
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
	
	/** Adds a listener monitoring the extensions pane */
	public void addExtensionsPaneListener(ExtensionsPaneListener listener)
		{ listeners.add(listener); }
	
	/** Removes a listener monitoring the extensions pane */
	public void removeExtensionsPaneListener(ExtensionsPaneListener listener)
		{ listeners.remove(listener); }
}
