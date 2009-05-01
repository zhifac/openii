package org.mitre.openii.editors;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.PlatformUI;
import org.mitre.affinity.view.application.AffinityPane;
import org.mitre.affinity.view.cluster_details.ClusterDetailsDlg;
import org.mitre.affinity.view.event.AffinityEventListener;
import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.clusters.distanceFunctions.TermVector;
import org.mitre.affinity.model.SchemaStoreManager;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Schema;

/**
 * Constructs the Affinity View
 * 
 * @author CBONACETO
 *
 */
public class AffinityView extends OpenIIEditor {	
	//public void createPartControl(Composite parent) {
	public void createPartControl(final Composite parent) {
		// Connects the SchemaStoreClient to Affinity's SchemaStoreManager
		SchemaStoreManager.setConnection(OpenIIManager.getConnection());	
		
		//Construct the Affinity pane
		parent.setLayout(new FillLayout());		
		final AffinityPane affinity = new AffinityPane(parent);
		
		//Add a listener to show a cluster details when a cluster is clicked and a Galaxy view when a schema is clicked
		affinity.addAffinityEventListener(new AffinityEventListener() {
			public void clusterSelected(ClusterGroup cluster) {
				//(Composite parent, int style, 
				 //Map<Integer, Schema> schemas, Map<Integer, TermVector> schemaTermVectors)		
				System.out.println("cluster selected");
				//Shell shell = new Shell(parent.getShell(), SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
				//shell.setSize(300, 400);
				//shell.open();
				ClusterDetailsDlg dlg = new ClusterDetailsDlg(parent.getShell(), SWT.APPLICATION_MODAL,
						affinity.getSchemaIDs(), affinity.getSchemas(), null);				
				dlg.setVisible(true);				
				//Unhighlight the cluster when the dialog is closed
				//affinity.get
			}
			public void schemaSelected(Integer schemaID) {				
				Object element = affinity.getSchema(schemaID);
				IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
				IEditorDescriptor editor = registry.getDefaultEditor(EditorManager.getEditorType(element));
				if(editor!=null) EditorManager.launchEditor(editor.getId(), element);				
			}			
		});
		
		//parent.pack();
		//parent.update();
	}

	@Override
	public void setFocus() {}
}
