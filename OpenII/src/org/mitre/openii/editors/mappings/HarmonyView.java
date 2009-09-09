package org.mitre.openii.editors.mappings;

import java.awt.Frame;

import javax.swing.JApplet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.awt.SWT_AWT;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.harmony.view.dialogs.schemaSettings.SchemaSettingsDialog;
import org.mitre.harmony.view.harmonyPane.HarmonyFrame;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.MappingSchema;

/** Constructs the Harmony View */
public class HarmonyView extends OpenIIEditor
{
	/** Private class for running the applet */ @SuppressWarnings("serial")
	private class HarmonyApplet extends JApplet
	{
		/** Stores the Harmony model */
		private HarmonyModel harmonyModel;
		
		/** Private variable to indicate if initialization has been completed */
		private boolean initializationCompleted = false;

		/** Constructs the applet */
		private HarmonyApplet(Frame frame)
		{
			harmonyModel = new HarmonyModel(frame);
			harmonyModel.getMappingManager().loadMapping(elementID);
			HarmonyFrame harmonyFrame = new HarmonyFrame(harmonyModel);
			setContentPane(harmonyFrame);
		}
		
		/** Displays the schema dialog on initialization */
		public void repaint(long time, int x, int y, int width, int height)
		{
			super.repaint(time, x, y, width, height);
			if(!initializationCompleted)
			{
				int displayedSchemas = 0;
				for(MappingSchema schema : harmonyModel.getMappingManager().getSchemas())
					if(!schema.getSide().equals(MappingSchema.NONE)) displayedSchemas++;
				if(displayedSchemas==0) new SchemaSettingsDialog(harmonyModel);
				initializationCompleted = true;
			}
		}
	}
	
	/** Displays the Harmony View */
	public void createPartControl(Composite parent)
	{
		// Connects the SchemaStoreClient to Harmony's SchemaStoreManager
		SchemaStoreManager.setConnection(RepositoryManager.getClient());
		
		// Constructs the AWT frame
		Composite swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		Frame frame = SWT_AWT.new_Frame(swtAwtComponent);
		frame.add(new HarmonyApplet(frame));
	}
}