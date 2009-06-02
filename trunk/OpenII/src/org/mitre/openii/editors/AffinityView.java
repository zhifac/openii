package org.mitre.openii.editors;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import org.mitre.affinity.view.application.AffinityPane;
import org.mitre.affinity.view.cluster_details.ClusterDetailsDlg;
import org.mitre.affinity.view.event.AffinityListener;
import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.model.AffinityModel;
import org.mitre.affinity.model.AffinitySchemaStoreManager;
import org.mitre.affinity.model.ClusterManager;
import org.mitre.affinity.model.AffinitySchemaManager;
import org.mitre.affinity.model.ISchemaManager;

import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIManager;

/**
 * Constructs the Affinity View
 */
public class AffinityView extends OpenIIEditor implements AffinityListener
{	
	/** Stores the Affinity model */
	private AffinityModel affinityModel;
	
	/** All Affinity instances running in OpenII use the same schema and cluster manager */
	private static final ISchemaManager schemaManager = new AffinitySchemaManager();
	private static final ClusterManager clusterManager = new ClusterManager();
	
	/** Stores the Affinity pane */
	private AffinityPane affinity = null;
	
	/** Displays the Affinity View */
	public void createPartControl(final Composite parent)
	{
		// Connects the SchemaStoreClient to Affinity's SchemaStoreManager
		AffinitySchemaStoreManager.setConnection(OpenIIManager.getConnection());	
		
		//Construct the Affinity pane
		parent.setLayout(new FillLayout());		
		ArrayList<Integer> schemaIDs = OpenIIManager.getGroupSchemas(elementID);		
		affinity = new AffinityPane(parent, new AffinityModel(schemaManager, clusterManager), schemaIDs);
		affinity.addAffinityEventListener(this);
	}	
	
	/** Handles a double-click on a cluster within Affinity */
	@Override
	public void clusterDoubleClicked(ClusterGroup cluster, Object source) {
		//Display the TF-IDF dialog for the cluster
		Shell shell = getSite().getWorkbenchWindow().getShell();
		ClusterDetailsDlg dlg = new ClusterDetailsDlg(shell, SWT.APPLICATION_MODAL, affinityModel, cluster);				
		dlg.setVisible(true);			
	}	

	/** Handles a double-click on a schema within Affinity */
	@Override
	public void schemaDoubleClicked(Integer schemaID, Object source) {
		EditorManager.launchDefaultEditor(affinity.getSchema(schemaID)); 
	}

	@Override
	public void clusterSelected(ClusterGroup arg0, Object arg1) {}

	@Override
	public void clusterUnselected(ClusterGroup arg0, Object arg1) {}

	@Override
	public void schemaSelected(Integer arg0, Object arg1) {}

	@Override
	public void schemaUnselected(Integer arg0, Object arg1) {}
}
