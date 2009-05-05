package org.mitre.openii.editors;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.mitre.affinity.view.application.AffinityPane;
import org.mitre.affinity.view.cluster_details.ClusterDetailsDlg;
import org.mitre.affinity.view.event.AffinityEventListener;
import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.model.SchemaStoreManager;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIManager;

/**
 * Constructs the Affinity View
 */
public class AffinityView extends OpenIIEditor implements AffinityEventListener
{
	/** Stores the Affinity pane */
	private AffinityPane affinity = null;
	
	/** Displays the Affinity View */
	public void createPartControl(final Composite parent)
	{
		// Connects the SchemaStoreClient to Affinity's SchemaStoreManager
		SchemaStoreManager.setConnection(OpenIIManager.getConnection());	
		
		//Construct the Affinity pane
		parent.setLayout(new FillLayout());		
		ArrayList<Integer> schemaIDs = OpenIIManager.getGroupSchemas(elementID);		
		affinity = new AffinityPane(parent, schemaIDs);
		affinity.addAffinityEventListener(this);
	}	

	/** Handles the selection of a cluster within Affinity */
	public void clusterSelected(ClusterGroup cluster)
	{
		Shell shell = getSite().getWorkbenchWindow().getShell();
		ClusterDetailsDlg dlg = new ClusterDetailsDlg(shell, SWT.APPLICATION_MODAL, affinity.getSchemaIDs(), affinity.getSchemas(), null);				
		dlg.setVisible(true);				
	}

	/** Handles the selection of a schema within Affinity */
	public void schemaSelected(Integer schemaID)	
		{ EditorManager.launchDefaultEditor(affinity.getSchema(schemaID)); }
}
