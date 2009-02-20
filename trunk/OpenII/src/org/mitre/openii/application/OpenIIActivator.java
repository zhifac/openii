package org.mitre.openii.application;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 *  The activator class controls the plug-in life cycle
 */
public class OpenIIActivator extends AbstractUIPlugin
{
	// The plug-in ID
	public static final String PLUGIN_ID = "org.mitre.openii";

	// The shared instance
	private static OpenIIActivator plugin;

	/** Starts up the OpenII plug-in */
	public void start(BundleContext context) throws Exception
		{ super.start(context); plugin = this; }

	/** Closes the OpenII plug-in */
	public void stop(BundleContext context) throws Exception
		{ plugin = null; super.stop(context); }

	/** Returns the shared instance */
	public static OpenIIActivator getDefault()
		{ return plugin; }

	/** Retrieves the bundle file */
	public static File getBundleFile() throws IOException
		{ return FileLocator.getBundleFile(getDefault().getBundle()); }
	
	/** Returns an image descriptor for the image file at the given plug-in relative path */
	public static ImageDescriptor getImageDescriptor(String path)
		{ return imageDescriptorFromPlugin(PLUGIN_ID, path); }

	/** Retrieves the specified image */
	public static Image getImage(String imageName)
		{ return getImageDescriptor("icons/"+imageName).createImage(); }
}
