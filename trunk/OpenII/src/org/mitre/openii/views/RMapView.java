package org.mitre.openii.views;

import java.awt.Frame;

import javax.swing.JApplet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.swt.awt.SWT_AWT;
import org.mitre.harmony.model.ProjectManager;

/** Constructs the Harmony View */
public class RMapView extends ViewPart
{
	/** Displays the Harmony View */
	public void createPartControl(Composite parent)
	{
		// Connects the SchemaStoreClient to Harmony's SchemaStoreManager
		//SchemaStoreManager.setConnection(SchemaStoreConnection.getConnection());
		
		// Constructs the AWT frame
		Composite swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		Frame frame = SWT_AWT.new_Frame(swtAwtComponent);

		// Construct the applet pane containing the Harmony frame
		JApplet appletPane = new JApplet();
	//	appletPane.add(new SQLGeneratorGUI(SchemaStoreConnection.getConnection()));		
		frame.add(appletPane);
	}
	
	/** Sets the focus in this view */
	public void setFocus() {}

	/** Shuts down the Harmony View */
	public void dispose()
	{
		ProjectManager.save();
		super.dispose();
	}
}