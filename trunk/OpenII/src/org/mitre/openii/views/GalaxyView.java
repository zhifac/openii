package org.mitre.openii.views;

import java.awt.Frame;

import javax.swing.JApplet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.swt.awt.SWT_AWT;
import org.mitre.openii.model.GalaxyManager;

/** Constructs the Galaxy View */
public class GalaxyView extends ViewPart
{
	/** Displays the Galaxy View */
	public void createPartControl(Composite parent)
	{		
		// Construct the applet pane to contain the Harmony frame
		JApplet appletPane = new JApplet();
		appletPane.add(GalaxyManager.getGalaxyPane());
		
		// Embed the applet pane into the Eclipse view
		Composite swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		Frame frame = SWT_AWT.new_Frame( swtAwtComponent );
		frame.add(appletPane);
	}

	// Sets the focus in this view
	public void setFocus() {}
}