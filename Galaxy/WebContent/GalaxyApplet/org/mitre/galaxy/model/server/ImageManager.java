// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.model.server;

import java.applet.Applet;
import java.awt.Image;
import java.awt.MediaTracker;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageManager
{
	/** Listing of the images being managed by this class */
	private static final String[] imageNames = new String[] {"GalaxyTitle.jpg","GalaxyTile.jpg","Schema.jpg",
		"SchemaUnavailable.jpg","SchemaSelected.jpg","SchemaComparison.jpg","SchemaElements.jpg","DependantElement.jpg",
		"Children.jpg","SchemaElement.jpg","Parents.jpg","Document.jpg","Expand.jpg","Collapse.jpg","Tag.gif",
		"Tags.gif","Schemas.jpg","Unassigned.jpg"};
	
	/** Mapping of images available within the applet */
	private static Map<String,Image> images = new HashMap<String,Image>();

	/** This object will allow you to control loading */
    private static MediaTracker mt = null;
    
 	/** Initializes the database for use */
 	public static void init(Applet applet)
 	{
 		try {
 			// Set the applet code base
 			URL codeBase = null;
 			if(applet!=null)
 			{
 				if(applet.getCodeBase().toString().startsWith("file")) codeBase = new URL("http://localhost:8080/Galaxy/GalaxyApplet/");
 				else codeBase = applet.getCodeBase();
 			}
 				
 	 		// Set up the media tracker
 	 		if(applet!=null) mt = new MediaTracker(applet);

 			// Load the images available for use
 	 		int counter=1;
 	 		for(String imageName : imageNames)
 	 			try {
	 	 			// Get the image
	 	 			Image image = null;
	 	 		 	if(applet!=null) image = applet.getImage(codeBase,"org/mitre/galaxy/graphics/"+imageName);
	 	 		 	else image = ImageIO.read(ImageManager.class.getResource("/org/mitre/galaxy/graphics/"+imageName));
	 	 		 	
	 	 		 	// Store the image
	 	 		 	images.put(imageName.replaceFirst("\\..*",""),image);
	 	 	 	    if(mt!=null) mt.addImage(image,counter++);
	 	 		} catch(Exception e) {}
	 		
 	        // Wait until the images load before proceeding
	 	 	if(mt!=null) mt.waitForAll();
		}
 		catch(Exception e) { System.out.println("(E)ImageManager.init - Failed to load images - " + e.getMessage()); } 
	}

 	/** Allows retrieval of images */
 	public static Image getImage(String ref)
 		{ return images.get(ref); }
} 