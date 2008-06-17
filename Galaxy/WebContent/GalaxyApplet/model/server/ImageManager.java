// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.server;

import java.awt.*;
import java.applet.*;
// These classes are for Url's.
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class ImageManager
{
	/** Mapping of images available within the applet */
	private static Map<String,Image> images = new HashMap<String,Image>();

	/** This object will allow you to control loading */
    private static MediaTracker mt;
 	
    /** Adds a single image into the Media Tracker */
    private static void addImage(int id, String name, Applet applet, URL codeBase)
    {
	 	Image image = applet.getImage(codeBase,"Images/"+name);
 	 	images.put(name.replaceFirst("\\..*",""),image);
 	    mt.addImage(image,id);
    }
    
 	/** Initializes the database for use */
 	public static void init(Applet applet)
 	{
 		try {
 			// Set the applet code base
 			URL codeBase = null;
 			if(applet.getCodeBase().toString().startsWith("file")) codeBase = new URL("http://localhost:8080/Galaxy/GalaxyApplet/");
 			else codeBase = applet.getCodeBase();

 	 		// Set up the media tracker
 	 		mt = new MediaTracker(applet);

 			// Load the images available for use
 	 		addImage(1,"GalaxyTitle.jpg",applet,codeBase);
 	 		addImage(2,"GalaxyTile.jpg",applet,codeBase);
 	 		addImage(3,"Schema.jpg",applet,codeBase);
 	 		addImage(4,"SchemaUnavailable.jpg",applet,codeBase);
 	 		addImage(5,"SchemaSelected.jpg",applet,codeBase);
 	 		addImage(6,"SchemaComparison.jpg",applet,codeBase);
 	 		addImage(7,"SchemaElements.jpg",applet,codeBase);
 	 		addImage(8,"Attribute.jpg",applet,codeBase);
 	 		addImage(9,"Children.jpg",applet,codeBase);
 	 		addImage(10,"SchemaElement.jpg",applet,codeBase);
 	 		addImage(11,"Parents.jpg",applet,codeBase);
 	 		addImage(12,"Document.jpg",applet,codeBase);
 	 		addImage(13,"Expand.jpg",applet,codeBase);
 	 		addImage(14,"Collapse.jpg",applet,codeBase);
 	 		addImage(15,"Group.gif",applet,codeBase);
 	 		addImage(16,"Groups.gif",applet,codeBase);
 	 		addImage(17,"Schemas.jpg",applet,codeBase);
 	 		addImage(18,"Unassigned.jpg",applet,codeBase);
	 		
 	        // Wait until the images load before proceeding
 	        mt.waitForAll();
		}
 		catch(MalformedURLException e) {} 
 		catch(InterruptedException e) {}
	}

 	/** Allows retrieval of images */
 	public static Image getImage(String ref)
 		{ return images.get(ref); }
} 