package org.mitre.openii.editors;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;

import org.mitre.affinity.view.application.AffinityPane;
import org.mitre.affinity.view.application.LoadProgressDialog;
import org.mitre.affinity.view.application.StackTraceDialog;
import org.mitre.affinity.view.cluster_details.ClusterDetailsDlg;
import org.mitre.affinity.view.event.SelectionClickedEvent;
import org.mitre.affinity.view.event.SelectionClickedListener;
import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.model.AffinityModel;
import org.mitre.affinity.model.AffinitySchemaStoreManager;
import org.mitre.affinity.model.ClusterManager;
import org.mitre.affinity.model.AffinitySchemaManager;
import org.mitre.affinity.model.ISchemaManager;

import org.mitre.openii.model.EditorInput;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.views.manager.groups.EditGroupDialog;

/**
 * Constructs the Affinity View
 */
public class AffinityView extends OpenIIEditor implements SelectionClickedListener
{	
	/** Stores the Affinity model */
	private AffinityModel affinityModel;
	
	/** All Affinity instances running in OpenII use the same schema and cluster manager */
	private static final ISchemaManager schemaManager = new AffinitySchemaManager();
	private static final ClusterManager clusterManager = new ClusterManager();
	
	/** Stores the Affinity pane */
	private AffinityPane affinity = null;
	
	/**	Menu shown when multiple schemas are selected and right-clicked */
	private Menu multiSchemaMenu;
	
	/**	Menu shown when a cluster is selected and right-clicked */
	private Menu clusterMenu;
	
	/** Currently selected cluster */	
	private ClusterGroup selectedCluster;
	
	/** Currently selected schemas */
	private Collection<Integer> selectedSchemas;
	
	/*static {
		//Initialize Icons
		AffinityConstants.imageRegistry = new ImageRegistry();
		for(String iconName : AffinityConstants.ICON_NAMES) {
			AffinityConstants.imageRegistry.put(iconName, OpenIIActivator.getImage(iconName));
		}
	}*/
	
	/** Displays the Affinity View */
	@SuppressWarnings("unchecked")
	public void createPartControl(final Composite parent)
	{
		// Connects the SchemaStoreClient to Affinity's SchemaStoreManager
		AffinitySchemaStoreManager.setConnection(RepositoryManager.getClient());
		
		//Create a dialog to show progress as Affinity loads
		final LoadProgressDialog progressDlg = new LoadProgressDialog(parent.getShell());
		progressDlg.setText("Opening Affinity");		
		progressDlg.open();		
		
		//Construct the Affinity pane
		parent.setLayout(new FillLayout());
		
		ArrayList<Integer> schemaIDs = null;
		IEditorInput editorInput = getEditorInput();
		if(editorInput != null && editorInput instanceof EditorInput) {
			Object element = ((EditorInput)editorInput).getElement();
			if(element != null && element instanceof Collection) {
				schemaIDs = new ArrayList<Integer>((Collection)element);
				setPartName("*New Group");
				/*this.addListenerObject(new Listener() {
					public void handleEvent(Event event) {
						System.out.println("listener triggered");
					}					
				});*/
			}
		}
		if(schemaIDs == null) {
			schemaIDs = OpenIIManager.getGroupSchemas(elementID);			
			schemaIDs.addAll(OpenIIManager.getChildGroupSchemas(elementID));
		}
		this.affinityModel =  new AffinityModel(schemaManager, clusterManager);
		
		//System.out.println("Element id: " + elementID);
		//System.out.println("SchemaIDs: " + schemaIDs);		
		
		affinity = new AffinityPane(parent, SWT.NONE, affinityModel, schemaIDs, progressDlg);
		progressDlg.close();
		if(affinity.isAffinityPaneCreated()) {
			affinity.getCraigrogram().debug = false;
			affinity.addSelectionClickedListener(this);	
			
			// Create multiple schemas right-click menu
			multiSchemaMenu = new Menu(affinity);
			SelectionListener multiSchemaMenuListener = new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {					
					MenuItem item = (MenuItem)e.widget;					
					if(item.getText().startsWith("View")) {
						//Create a temporary cluster using the schemas in the group and display the TF-IDF dialog 
						//for the cluster
						ClusterGroup cluster = new ClusterGroup();
						cluster.addSchemas(selectedSchemas);
						Shell shell = getSite().getWorkbenchWindow().getShell();
						ClusterDetailsDlg dlg = new ClusterDetailsDlg(shell, SWT.APPLICATION_MODAL, affinityModel, cluster);				
						dlg.setVisible(true);						
					}
					else if(item.getText().startsWith("Open")) {
						//Open schemas in a new Affinity Pane
						EditorManager.launchEditor("org.mitre.openii.editors.AffinityView", selectedSchemas);
					}
					else {
						//Create a new group containing the schemas
						Shell shell = getSite().getWorkbenchWindow().getShell();
						EditGroupDialog dlg = new EditGroupDialog(shell, null, null, new ArrayList<Integer>(selectedSchemas));						
						dlg.open();
					}						
				}
			};
			MenuItem item = new MenuItem (multiSchemaMenu, SWT.NONE);				
			item.setText("View Terms in Schemas");
			item.addSelectionListener(multiSchemaMenuListener);
			item = new MenuItem (multiSchemaMenu, SWT.NONE);				
			item.setText("Open Schemas In New Affinity Window");
			item.addSelectionListener(multiSchemaMenuListener);
			item = new MenuItem (multiSchemaMenu, SWT.NONE);				
			item.setText("Create Group From Schemas");
			item.addSelectionListener(multiSchemaMenuListener);	
			
			// Create cluster right-click menu			
			clusterMenu = new Menu(affinity);
			SelectionListener clusterMenuListener = new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {				
					MenuItem item = (MenuItem)e.widget;
					if(item.getText().startsWith("View")) {
						//Display the TF-IDF dialog for the cluster
						Shell shell = getSite().getWorkbenchWindow().getShell();
						ClusterDetailsDlg dlg = new ClusterDetailsDlg(shell, SWT.APPLICATION_MODAL, affinityModel, selectedCluster);				
						dlg.setVisible(true);	
					}
					else if(item.getText().startsWith("Open")) {
						//Open cluster in a new Affinity pane						
						EditorManager.launchEditor("org.mitre.openii.editors.AffinityView", selectedCluster.getSchemaIDs());								
					}
					else {
						//Create a new group containing the schemas in the cluster
						Shell shell = getSite().getWorkbenchWindow().getShell();
						EditGroupDialog dlg = new EditGroupDialog(shell, null, null, selectedCluster.getSchemaIDs());						
						dlg.open();
						/*
						Group group = new Group();
						Integer groupID = OpenIIManager.addGroup(group);
						OpenIIManager.setGroupSchemas(groupID, selectedCluster.getSchemaIDs());
						*/
					}						
				}
			};
			item = new MenuItem (clusterMenu, SWT.NONE);				
			item.setText("View Terms in Cluster");
			item.addSelectionListener(clusterMenuListener);
			item = new MenuItem (clusterMenu, SWT.NONE);				
			item.setText("Open Cluster In New Affinity Window");
			item.addSelectionListener(clusterMenuListener);
			item = new MenuItem (clusterMenu, SWT.NONE);				
			item.setText("Create Group From Cluster");
			item.addSelectionListener(clusterMenuListener);			
		}
		else {
			//There was an error creating the Affinity pane, show the error dialog
			StackTraceDialog stackTraceDlg = new StackTraceDialog(parent.getShell(), affinity.getException());			
			stackTraceDlg.setMessage("Unable to launch Affinity due to:");
			stackTraceDlg.open();
		}
	}	
	
	//SelectionClickedListener method	
	/* (non-Javadoc)
	 * @see org.mitre.affinity.view.event.SelectionClickedListener#selectionClicked(org.mitre.affinity.view.event.SelectionClickedEvent)
	 */
	public void selectionClicked(SelectionClickedEvent event) {
		if(event.selectedSchemas != null) {
			if(event.selectedSchemas.size() == 1) {
				//A single schema is currently selected
				if(event.button == 1 && event.clickCount == 2) {
					//Schema was double-clicked, display the default editor for
					//schemas in a new tab
					EditorManager.launchDefaultEditor(schemaManager.getSchema(event.selectedSchemas.iterator().next()));		
				}
			}
			else {
				//Multiple schemas are currently selected
				selectedSchemas = event.selectedSchemas;
				if(event.button == 3) {
					//Multiple schemas were right-clicked, display right-click menu for multiple schemas					
					multiSchemaMenu.setVisible(true);
				}
			}
		}
		else if(event.selectedClusters != null && event.selectedClusters.size() == 1) {
			selectedCluster = event.selectedClusters.iterator().next();
			//A single cluster is currently selected
			if(event.button == 1 && event.clickCount == 2) {
				//A single cluster was double-clicked, display the TF-IDF dialog for the cluster
				Shell shell = getSite().getWorkbenchWindow().getShell();
				ClusterDetailsDlg dlg = new ClusterDetailsDlg(shell, SWT.APPLICATION_MODAL, affinityModel, selectedCluster);				
				dlg.setVisible(true);		
			}			
			else if(event.button == 3) {
				//A single cluster was right-clicked, display right-click menu for cluster
				//System.out.println("cluster right clicked");				
				clusterMenu.setVisible(true);
			}
		}
	}	
}
