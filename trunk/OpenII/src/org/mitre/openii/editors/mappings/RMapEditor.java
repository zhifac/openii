package org.mitre.openii.editors.mappings;

import java.awt.Frame;

import javax.swing.JApplet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.rmap.model.RMapHarmonyModel;
import org.mitre.rmap.view.RMapFrame;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;

/** Constructs the RMap View */
public class RMapEditor extends OpenIIEditor
{
	/** Private class for running the applet */ @SuppressWarnings("serial")
	private class RMapApplet extends JApplet
	{
		/** Stores the Harmony model */
		private RMapHarmonyModel harmonyModel;
		
		/** Constructs the applet */
		private RMapApplet(Frame frame)
		{
			// create the RMap harmony model, which inherits heavily from Harmony to do its dirty work
			harmonyModel = new RMapHarmonyModel(frame);

			// Load in the selected project
			Object element = getElement();
			Integer projectID = element instanceof Project ? ((Project)element).getId() : ((Mapping)element).getProjectId();

			// Retrieve project information from repository
			Project project = null;
			try {
				project = SchemaStoreManager.getProject(projectID);
			} catch(Exception e) {
				System.out.println("(E) RMapEditor:RMapApplet - " + e.getMessage());
				return;
			}

			// Clear out all old project settings
			harmonyModel.getPreferences().unmarkAllFinished();
			harmonyModel.getMappingManager().removeAllMappings();

			// Sets the project information
			harmonyModel.getProjectManager().setProject(project);

			// Indicates that the project was successfully loaded
			harmonyModel.getProjectManager().setModified(false);

			// Display the harmony frame
			RMapFrame rmapFrame = new RMapFrame(harmonyModel, RepositoryManager.getClient(), getElementID());
			setContentPane(rmapFrame);
		}		
	}

	/** Displays the RMap View */
	public void createPartControl(Composite parent)
	{
		// Connects the SchemaStoreClient to Harmony's SchemaStoreManager
		SchemaStoreManager.setConnection(RepositoryManager.getClient());
		
		// Constructs the AWT frame
		Composite swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		Frame frame = SWT_AWT.new_Frame(swtAwtComponent);
		
		// create the applet and put it into the frame
		RMapApplet applet = new RMapApplet(frame);
		frame.add(applet); 
	}
}