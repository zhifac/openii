// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.extensionsPane;

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
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import model.DataSources;
import model.Schemas;
import model.SelectedObjects;
import model.UniversalObjects;
import model.listeners.DataSourceListener;
import model.listeners.SchemasListener;
import model.listeners.SelectedObjectsListener;

import prefuse.Constants;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.layout.graph.NodeLinkTreeLayout;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Node;
import prefuse.data.tuple.TableNode;
import prefuse.render.DefaultRendererFactory;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;
import view.GalaxyApplet;

/** Class for displaying the extensions pane of Galaxy */
public class ExtensionsPane extends JPanel implements ComponentListener, DataSourceListener, SchemasListener, SelectedObjectsListener
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
				Integer nodeID = (Integer)((NodeItem)item).get("NodeObject");
				if(UniversalObjects.isSchema(nodeID))
				{
					if(SelectedObjects.inSelectedGroups(nodeID))
					{
						if(e.getButton()==MouseEvent.BUTTON1 && !e.isMetaDown())
							SelectedObjects.setSelectedSchema(nodeID);
						else if(nodeID!=SelectedObjects.getSelectedSchema())
							SelectedObjects.setSelectedComparisonSchema(nodeID);
					}
				}
				else
				{
				    try
				    {
				      AppletContext a = GalaxyApplet.galaxyApplet.getAppletContext();
				      URL url = new URL((DataSources.getDataSource(nodeID)).getUrl());
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
		Schemas.addSchemasListener(this);
		DataSources.addDataSourceListener(this);
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
	
	/** Handles the addition of a schema */
	public void schemaAdded(Schema schema)
	{
		Integer parentID = Schemas.getParentSchemas(schema.getId()).get(0);
		ExtensionPaneDisplay display = cachedDisplays.get(parentID);
		if(display!=null)
		{				
			// Modify the graph to add the new schema
			ExtensionGraph graph = (ExtensionGraph)display.getVisualization().getSourceData("graph");
			graph.schemaAdded(schema);
				
			// Adjust location of added schema
			VisualItem parentItem=null, childItem=null;
			Iterator items = display.getVisualization().items();
			while(items.hasNext())
			{
				VisualItem visualItem = (VisualItem)items.next();
				if(visualItem.getSourceTuple() instanceof TableNode)
				{
					Integer nodeID = (Integer)((Node)visualItem.getSourceTuple()).get("NodeObject");
					if(UniversalObjects.isSchema(nodeID))
					{
						if(nodeID.equals(schema.getId())) childItem=visualItem;
						if(nodeID.equals(parentID)) parentItem=visualItem;
					}
				}
			}
			childItem.setX(parentItem.getBounds().getMinX()+childItem.getBounds().getWidth()/2+20);
			childItem.setY(parentItem.getBounds().getMinY()+childItem.getBounds().getHeight()/2-10);
			childItem.setHover(true);
			display.getVisualization().run("font");

			// Cache the display for the added schema
			cachedDisplays.put(schema.getId(),display);
		}
	}
	
	/** Handles the removal of a schema */
	public void schemaRemoved(Schema schema)
	{
		ExtensionPaneDisplay display = cachedDisplays.get(schema.getId());
		if(display!=null)
		{
			// Modify the graph to remove the new schema
			ExtensionGraph graph = (ExtensionGraph)display.getVisualization().getSourceData("graph");
			graph.schemaRemoved(schema);
			
			// Remove the display for the removed schema
			cachedDisplays.remove(schema);
		}		
	}
	
	/** Handles the update of schema parents */
	public void schemaParentsUpdated(Schema schema)
	{
		ExtensionPaneDisplay display = cachedDisplays.get(schema.getId());
		if(display!=null)
		{
			// Modify the graph to remove the schema
			ExtensionGraph graph = (ExtensionGraph)display.getVisualization().getSourceData("graph");
			graph.schemaParentsUpdated(schema);
		}
		display.getVisualization().run("color");
		display.getVisualization().repaint();
	}
	
	/** Handles the addition of a data source */
	public void dataSourceAdded(DataSource dataSource)
	{
		Schema schema = Schemas.getSchema(dataSource.getSchemaID());
		ExtensionPaneDisplay display = cachedDisplays.get(schema.getId());
		if(display!=null)
		{				
			// Modify the graph to add the new data source
			ExtensionGraph graph = (ExtensionGraph)display.getVisualization().getSourceData("graph");
			graph.dataSourceAdded(dataSource);
				
			// Adjust location of added data source
			VisualItem parentItem=null, childItem=null;
			Iterator items = display.getVisualization().items();
			while(items.hasNext())
			{
				VisualItem visualItem = (VisualItem)items.next();
				if(visualItem.getSourceTuple() instanceof TableNode)
				{
					Integer nodeID = (Integer)((Node)visualItem.getSourceTuple()).get("NodeObject");
					if(nodeID.equals(dataSource.getId())) childItem=visualItem;
					if(nodeID.equals(schema.getId())) parentItem=visualItem;
				}
			}
			childItem.setX(parentItem.getBounds().getMaxX()+30);
			childItem.setY(parentItem.getBounds().getCenterY());
			childItem.setHover(true);
			display.getVisualization().run("font");
			revalidate(); validate(); refresh();
		}
	}

	/** Handles the update of a data source */
	public void dataSourceUpdated(DataSource dataSource)
	{
		display.getVisualization().run("font");
		revalidate(); validate(); refresh();
	}
	
	/** Handles the removal of a data source */
	public void dataSourceRemoved(DataSource dataSource)
	{
		ExtensionPaneDisplay display = cachedDisplays.get(dataSource.getSchemaID());
		if(display!=null)
		{
			// Modify the graph to remove the data source
			ExtensionGraph graph = (ExtensionGraph)display.getVisualization().getSourceData("graph");
			graph.dataSourceRemoved(dataSource);
		}
		refresh();
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
	
	/** Refreshes the extensions pane when a schema name is updated */
	public void schemaUpdated(Schema schema)
		{ refresh(); }
	
	/** Refreshes the extensions pane when a schema has been locked */
	public void schemaLocked(Schema schema)
		{ refresh(); }
	
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
	
	/** Update the extension similarity percentage  when a schema element is added */
	public void schemaElementAdded(SchemaElement schemaElement)
	{
		ExtensionGraph graph = (ExtensionGraph)display.getVisualization().getSourceData("graph");
		graph.schemaParentsUpdated(Schemas.getSchema(schemaElement.getBase()));
		display.getVisualization().run("color");
		display.getVisualization().repaint();
	}
	
	/** Update the extension similarity percentage  when a schema element is removed */
	public void schemaElementRemoved(SchemaElement schemaElement)
	{
		ExtensionGraph graph = (ExtensionGraph)display.getVisualization().getSourceData("graph");
		graph.schemaParentsUpdated(Schemas.getSchema(schemaElement.getBase()));
		display.getVisualization().run("color");
		display.getVisualization().repaint();
	}
	
	/** Unused action listeners */
	public void componentHidden(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
}
