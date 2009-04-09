package org.mitre.openii.editors;

import java.awt.Frame;

import javax.swing.JApplet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.awt.SWT_AWT;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.harmony.view.harmonyPane.HarmonyFrame;
import org.mitre.openii.model.OpenIIManager;

/** Constructs the Harmony View */
public class HarmonyView extends OpenIIEditor
{
	/** Displays the Harmony View */
	public void createPartControl(Composite parent)
	{
		// Connects the SchemaStoreClient to Harmony's SchemaStoreManager
		SchemaStoreManager.setConnection(OpenIIManager.getConnection());
		
		// Constructs the AWT frame
		Composite swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		Frame frame = SWT_AWT.new_Frame(swtAwtComponent);

		// Construct the applet pane containing the Harmony frame
		JApplet appletPane = new JApplet();
		HarmonyModel harmonyModel = new HarmonyModel(frame);
		harmonyModel.getMappingManager().loadMapping(elementID);
		appletPane.add(new HarmonyFrame(harmonyModel));
		frame.add(appletPane);
	}

	/** Shuts down the Harmony View
	public void dispose()
	{
		ProjectManager.save();
		super.dispose();
	}*/
}