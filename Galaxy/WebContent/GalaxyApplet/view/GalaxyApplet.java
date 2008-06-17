// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view;

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

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import view.explorerPane.ExplorerPane;
import view.extensionsPane.ExtensionsPane;
import view.groupPane.GroupPane;
import view.schemaPane.SchemaPane;
import view.searchPane.SearchPane;

import model.server.ImageManager;
import model.server.ServletConnection;

/** Class for displaying the Galaxy Applet */
public class GalaxyApplet extends Applet implements MouseListener, MouseMotionListener
{
	/** Stores a reference to the applet */
	static public Applet galaxyApplet = null;
	
	/** Stores a constant for the minimum width for the left pane */
	static private int MIN_WIDTH=325;
	
	/** Stores a constant for the maximum width for the left pane */
	static private int MAX_WIDTH=525;
	
	/** Stores the left pane displayed in the Galaxy Applet */
	private JPanel leftPane = new JPanel();

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
		ServletConnection.init(this);
		ImageManager.init(this);
		
		// Construct the explorer pane
		JTabbedPane explorerPane = new JTabbedPane();
		explorerPane.setBorder(new EmptyBorder(5,0,0,0));
		explorerPane.addTab("Explore",new ExplorerPane());
		explorerPane.addTab("Groups",new GroupPane());
		explorerPane.addTab("Search",new SearchPane());
		
		// Constructs the left pane
		leftPane.setBorder(new EmptyBorder(10,10,10,12));
		leftPane.setLayout(new BorderLayout());
		leftPane.setOpaque(false);
		leftPane.setPreferredSize(new Dimension(MIN_WIDTH,0));
		leftPane.add(new TitlePane(),BorderLayout.NORTH);
		leftPane.add(explorerPane,BorderLayout.CENTER);
		
		// Constructs the view pane
		JTabbedPane viewPane = new JTabbedPane();
		viewPane.setBorder(new EmptyBorder(10,0,10,10));
		viewPane.addTab("Extensions",new ExtensionsPane());
		viewPane.addTab("Schemas",new SchemaPane());
		
		// Constructs the Galaxy pane
		GalaxyPane galaxyPane = new GalaxyPane();
		galaxyPane.setLayout(new BorderLayout());
		galaxyPane.add(leftPane,BorderLayout.LINE_START);
		galaxyPane.add(viewPane,BorderLayout.CENTER);
		
		// Lays out Galaxy
		setLayout(new BorderLayout());
		add(galaxyPane,BorderLayout.CENTER);
		
		// Add mouse listeners used to resize the left pane
		addMouseListener(this);
		addMouseMotionListener(this);
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
    {
    	if(mouseDragging)
    	{
 			mouseMoved(e);
			mouseDragging=false;
		}
    }
     
    /** Change cursor to default once outside of left pane borders */
	public void mouseExited(MouseEvent e)
	{
		if(!mouseDragging) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			resizeCursor=false;
		}
	}
    
	// Unused mouse listener events
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
}
