package org.mitre.openii.editors.projects;

import java.awt.Frame;

import javax.swing.JApplet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.mitre.harmony.controllers.ProjectController;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.harmony.view.harmonyPane.HarmonyFrame;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.EditorInput;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;

/** Constructs the Harmony View */
public class HarmonyEditor extends OpenIIEditor
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

			// Load in the selected project
			Object element = getElement();
			Integer projectID = element instanceof Project ? ((Project)element).getId() : ((Mapping)element).getProjectId();
			ProjectController.loadProject(harmonyModel,projectID);
			
			// Display the harmony frame
			HarmonyFrame harmonyFrame = new HarmonyFrame(harmonyModel);
			setContentPane(harmonyFrame);
		}
		
		/** Displays the schema dialog on initialization */
		public void repaint(long time, int x, int y, int width, int height)
		{
			super.repaint(time, x, y, width, height);
			if(!initializationCompleted)
			{
				// Select the mapping(s) to display
				Object element = getElement();
				if(element instanceof Mapping)
				{
					Mapping mapping = (Mapping)element;
					harmonyModel.getMappingManager().getMapping(mapping.getId()).setVisibility(true);
					harmonyModel.getMappingManager().setMappingLock(true);
				}
				else ProjectController.selectMappings(harmonyModel);
				
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