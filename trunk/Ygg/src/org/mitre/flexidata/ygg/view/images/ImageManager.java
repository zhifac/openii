// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.images;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

/** Class for managing all images */
public class ImageManager
{
	/** Returns the specified image */
	static public Image getImage(String name)
	{ 
		Image image = null;
		URL imageLoader = new ImageManager().getClass().getResource(name);
		if(imageLoader != null)
			image = Toolkit.getDefaultToolkit().getImage(imageLoader); 
		return image;
	}
}
