package org.mitre.openii.editors.mappings;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.mitre.openii.editors.OpenIIEditor;

/** Constructs the Harmony View */
public class HarmonyEditor extends OpenIIEditor
{
//	/** Private class for running the applet */ @SuppressWarnings("serial")
//	private class HarmonyApplet extends JApplet
//	{
//		/** Stores the Harmony model */
//		private HarmonyModel harmonyModel;
//		
//		/** Private variable to indicate if initialization has been completed */
//		private boolean initializationCompleted = false;
//
//		/** Constructs the applet */
//		private HarmonyApplet(Frame frame)
//		{
//			harmonyModel = new HarmonyModel(frame);
//			harmonyModel.getMappingManager().loadMapping(elementID);
//			HarmonyFrame harmonyFrame = new HarmonyFrame(harmonyModel);
//			setContentPane(harmonyFrame);
//		}
//		
//		/** Displays the schema dialog on initialization */
//		public void repaint(long time, int x, int y, int width, int height)
//		{
//			super.repaint(time, x, y, width, height);
//			if(!initializationCompleted)
//			{
//				int displayedSchemas = 0;
//				for(ProjectSchema schema : harmonyModel.getMappingManager().getSchemas())
//					if(!schema.getSide().equals(MappingSchema.NONE)) displayedSchemas++;
//				if(displayedSchemas==0) new SchemaSettingsDialog(harmonyModel);
//				initializationCompleted = true;
//			}
//		}
//	}
	
	/** Displays the Harmony View */
	public void createPartControl(Composite parent)
	{
		closeEditor();
		String message = "The Harmony Editor has been temporarily deactivated while upgrades to " +
						 "Version 7 are being made.  Please contact Christopher Wolf at " +
						 "cwolf@mitre.org or (703-983-4565) for inquiries into this matter.";
		MessageDialog.openInformation(parent.getShell(), "Harmony Disabled", message);
		
//		// Connects the SchemaStoreClient to Harmony's SchemaStoreManager
//		SchemaStoreManager.setConnection(RepositoryManager.getClient());
//		
//		// Constructs the AWT frame
//		Composite swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
//		Frame frame = SWT_AWT.new_Frame(swtAwtComponent);
//		frame.add(new HarmonyApplet(frame));
	}
}