package org.mitre.openii.editors.projects;

import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.JApplet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.mitre.harmony.controllers.ProjectController;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.harmony.model.project.ProjectListener;
import org.mitre.harmony.view.harmonyPane.HarmonyFrame;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.mappingInfo.MappingInfo;

/** Constructs the Harmony View */
public class HarmonyEditor extends OpenIIEditor
{	
	/** Private class for running the applet */ @SuppressWarnings("serial")
	private class HarmonyApplet extends JApplet implements ProjectListener
	{
		/** Stores the Harmony model */
		private HarmonyModel harmonyModel;
		
		/** Private variable to indicate if initialization has been completed */
		private boolean initializationCompleted = false;

		/** Private variable to indicate when the mapping is saved to a project */
		private boolean mappingSaved = false;
		
		/** Constructs the applet */
		private HarmonyApplet(Frame frame)
		{
			harmonyModel = new HarmonyModel(frame);

			// Load in the selected project or mapping
			Object element = getElement();
			if(element instanceof Project)
				ProjectController.loadProject(harmonyModel, ((Project)element).getId());
			else
			{
				Mapping mapping = (Mapping)element;
				if(mapping.getProjectId()==null)
				{
					MappingInfo mappingInfo = new MappingInfo(mapping, new ArrayList<MappingCell>());
					ProjectController.loadMapping(harmonyModel, mappingInfo);					
					harmonyModel.getProjectManager().addListener(this);
				}
				else ProjectController.loadProject(harmonyModel, mapping.getProjectId());
			}
				
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
		
		/** Checks to see if this project needs to be added to the OpenII manager */
		public void projectModified()
		{
			// Checks to see if Harmony has saved the mapping to a project
			Integer projectID = harmonyModel.getProjectManager().getProject().getId();
			if(projectID!=null && !mappingSaved)
			{
				// Inform OpenII of the newly saved mapping
				class MappingSaved implements Runnable
				{
					private Integer projectID = null;
					private MappingSaved(Integer projectID) { this.projectID = projectID; }
					public void run()
					{
						Project project = OpenIIManager.getProject(projectID);
						OpenIIManager.fireProjectAdded(project);
					}
				}
				display.syncExec(new MappingSaved(projectID));
				
				// Update the mapping ID
				Mapping mapping = (Mapping)getElement();
				mapping.setProjectId(projectID);
				mapping.setId(OpenIIManager.getMappings(projectID).get(0).getId());
				
				mappingSaved=true;
			}
		}

		// Unused event listeners
		public void schemaAdded(Integer schemaID) {}
		public void schemaRemoved(Integer schemaID) {}
		public void schemaModelModified(Integer schemaID) {}
	}
	
	/** Stores the display associated with the Harmony Editer */
	private Display display = null;
	
	/** Displays the Harmony View */
	public void createPartControl(Composite parent)
	{
		// Connects the SchemaStoreClient to Harmony's SchemaStoreManager
		SchemaStoreManager.setConnection(RepositoryManager.getClient());

		// Constructs the AWT frame
		Composite swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		Frame frame = SWT_AWT.new_Frame(swtAwtComponent);
		frame.add(new HarmonyApplet(frame));
		
		// Stores the display associated with this part
		display = parent.getDisplay();
	}
}