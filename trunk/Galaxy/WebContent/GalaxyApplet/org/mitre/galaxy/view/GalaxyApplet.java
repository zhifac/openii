// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import org.mitre.galaxy.model.SelectedObjects;
import org.mitre.galaxy.model.listeners.SearchListener;
import org.mitre.galaxy.model.listeners.SelectedObjectsListener;
import org.mitre.galaxy.model.search.SearchManager;
import org.mitre.galaxy.model.server.ImageManager;
import org.mitre.galaxy.model.server.SchemaStoreManager;
import org.mitre.galaxy.view.explorerPane.ExplorerPane;
import org.mitre.galaxy.view.extensionsPane.ExtensionsPane;
import org.mitre.galaxy.view.extensionsPane.ExtensionsPaneListener;
import org.mitre.galaxy.view.groupPane.GroupPane;
import org.mitre.galaxy.view.schemaPane.SchemaPane;
import org.mitre.galaxy.view.searchPane.SearchPane;
import org.mitre.galaxy.view.searchPane.SearchPaneListener;

/** Class for displaying the Galaxy Applet */
public class GalaxyApplet extends Applet implements SelectedObjectsListener, SearchListener, ExtensionsPaneListener, SearchPaneListener, MouseListener, MouseMotionListener
{
	/** Stores a reference to the applet */
	static public Applet galaxyApplet = null;
	
	/** Stores a constant for the minimum width for the left pane */
	static private int MIN_WIDTH=325;
	
	/** Stores a constant for the maximum width for the left pane */
	static private int MAX_WIDTH=525;
	
	// Stores the panes displayed in the Galaxy Applet
	private JPanel leftPane = new JPanel();
	private JTabbedPane explorerPane = new JTabbedPane();
	private JTabbedPane viewPane = new JTabbedPane();
	private SchemaPane schemaPane = new SchemaPane(null);
	private ExtensionsPane extensionsPane = new ExtensionsPane();
	private SearchPane searchPane = new SearchPane();

	/** Indicates if the left pane is currently being resized */
	private boolean resizeCursor;
	
	/** Tracks when the mouse is being dragged for resizing */
	boolean mouseDragging = false;
	
	/** Private class for displaying the Galaxy title */
	class TitlePane extends JPanel
	{
		/** Adjusts the size of the title pane to fit the logo */
		TitlePane()
			{ setPreferredSize(new Dimension(0,85)); }
		
		/** Paint the title pane */
		public void paint(Graphics g)
		{
			Image image = ImageManager.getImage("GalaxyTitle");
			int x = (getSize().width-image.getWidth(this))/2;
			int y = (getSize().height-image.getHeight(this))/2;
			g.drawImage(image,x,y,this);
			g.setColor(Color.white);
			g.drawRect(x,y,image.getWidth(this)-1,image.getHeight(this)-1);
			g.drawRect(x-1,y-1,image.getWidth(this)+1,image.getHeight(this)+1);
		}
	}
	
	/** Private class used to paint the galaxy pane */
	class GalaxyPane extends JPanel
	{
	  public void paintComponent(Graphics g)
	  {
		  super.paintComponent(g); 
		  Image background = ImageManager.getImage("GalaxyTile");
		  for(int x=0; x<getSize().width; x+=background.getWidth(this))
			  for(int y=0; y<getSize().height; y+=background.getHeight(this))
				  g.drawImage(background,x,y,this);
	  }
	}
	
	/** Initializes the applet */
	public void init()
	{
		galaxyApplet = this;
		
		// Initializes the database
		SchemaStoreManager.init(this);
		ImageManager.init(this);
		
		// Construct the explorer pane
		explorerPane.setBorder(new EmptyBorder(5,0,0,0));
		explorerPane.addTab("Explore",new ExplorerPane());
		explorerPane.addTab("Groups",new GroupPane());
		explorerPane.addTab("Search",searchPane);
		
		// Constructs the left pane
		leftPane.setBorder(new EmptyBorder(10,10,10,12));
		leftPane.setLayout(new BorderLayout());
		leftPane.setOpaque(false);
		leftPane.setPreferredSize(new Dimension(MIN_WIDTH,0));
		leftPane.add(new TitlePane(),BorderLayout.NORTH);
		leftPane.add(explorerPane,BorderLayout.CENTER);
		
		// Constructs the view pane
		viewPane.setBorder(new EmptyBorder(10,0,10,10));
		viewPane.addTab("Extensions",extensionsPane);
		viewPane.addTab("Schemas",schemaPane);
		
		// Constructs the Galaxy pane
		GalaxyPane galaxyPane = new GalaxyPane();
		galaxyPane.setLayout(new BorderLayout());
		galaxyPane.add(leftPane,BorderLayout.LINE_START);
		galaxyPane.add(viewPane,BorderLayout.CENTER);
		
		// Lays out Galaxy
		setLayout(new BorderLayout());
		add(galaxyPane,BorderLayout.CENTER);
		
		// Disable tabs if no schemas found to display
		if(SelectedObjects.getSelectedSchema()==null)
			{ explorerPane.setEnabled(false); viewPane.setEnabled(false); }
		
		// Add listeners used to update the various components
		extensionsPane.addExtensionsPaneListener(this);
		searchPane.addSearchPaneListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		// Add listeners and initialize the selected schema
		SelectedObjects.addSelectedObjectsListener(this);
		SearchManager.addSearchListener(this);
		selectedSchemaChanged();
	}

    /** Modifies the mouse cursor to allow resizing of left pane */
    public void mouseMoved(MouseEvent e)
	{
    	// If mouse within 3 pixels of left pane border, allow dragging
       	int dist = leftPane.getX()+leftPane.getWidth()-e.getX()-6;
    	if(dist>=-3 && dist<=3) {
    		if(!resizeCursor) setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
    		resizeCursor=true;
    	}
    	
    	// Otherwise, disallow dragging
    	else  {
    		if(resizeCursor) setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			resizeCursor=false;
		}
    }

    /** Starts dragging left pane border if allowed */
    public void mousePressed(MouseEvent e)
		{ if(resizeCursor && e.getButton()==MouseEvent.BUTTON1) mouseDragging=true; }
    
    /** Readjust left pane border as mouse is dragged */
    public void mouseDragged(MouseEvent e)
    {
    	if(mouseDragging)
    	{
    		int newWidth = e.getX()+6;
     		if(newWidth<MIN_WIDTH) newWidth=MIN_WIDTH;
       		if(newWidth>MAX_WIDTH) newWidth=MAX_WIDTH;
    		leftPane.setPreferredSize(new Dimension(newWidth,0));
    		validate(); ((JPanel)leftPane.getParent()).revalidate();
    	}
    }
    
    /** Stop dragging left pane border when mouse is released */
    public void mouseReleased(MouseEvent e)
    	{ if(mouseDragging) { mouseMoved(e); mouseDragging=false; } }
     
    /** Change cursor to default once outside of left pane borders */
	public void mouseExited(MouseEvent e)
		{ if(!mouseDragging) { setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); resizeCursor=false; } }
	
	/** Update panes when the selected schema changes */
	public void selectedSchemaChanged()
	{
		Integer schemaID = SelectedObjects.getSelectedSchema();
		schemaPane.setSchema(schemaID);
		extensionsPane.setSchema(schemaID);
		searchPane.setSchema(schemaID);
	}

	/** Update panes when the selected comparison schema changes */
	public void selectedComparisonSchemaChanged()
	{
		Integer comparisonSchemaID = SelectedObjects.getSelectedComparisonSchema();
		schemaPane.setComparisonSchema(comparisonSchemaID);
		extensionsPane.setComparisonSchema(comparisonSchemaID);
	}
	
	/** Update panes when the selected groups change */
	public void selectedGroupsChanged()
	{
		HashSet<Integer> selectedSchemaGroups = null;
		if(SelectedObjects.getSelectedGroups().size()>0)
			selectedSchemaGroups = SelectedObjects.getSelectedGroupSchemas();
		extensionsPane.setSelectedGroupSchemas(selectedSchemaGroups);
	}

	/** Update panes when the search results change */
	public void searchResultsChanged()
		{ schemaPane.updateSearchResults(SearchManager.getMatchedElements()); }
	
	/** Handles a schema being selected by one of the panes */
	public void schemaSelected(Integer schemaID)
		{ SelectedObjects.setSelectedSchema(schemaID); }
	
	/** Handles a comparison schema being selected by one of the panes */
	public void comparisonSchemaSelected(Integer schemaID)
		{ SelectedObjects.setSelectedComparisonSchema(schemaID); }
	
	// Unused mouse listener events
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
}
