package org.mitre.openii.editors.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.model.AffinityModel;
import org.mitre.affinity.model.AffinitySchemaManager;
import org.mitre.affinity.model.AffinitySchemaStoreManager;
import org.mitre.affinity.model.ClusterManager;
import org.mitre.affinity.model.ISchemaManager;
import org.mitre.affinity.view.application.AffinityPane;
import org.mitre.affinity.view.application.LoadProgressDialog;
import org.mitre.affinity.view.application.StackTraceDialog;
import org.mitre.affinity.view.cluster_details.ClusterDetailsDlg;
import org.mitre.affinity.view.event.SelectionClickedEvent;
import org.mitre.affinity.view.event.SelectionClickedListener;
import org.mitre.affinity.view.venn_diagram.VennDiagramUtils;
import org.mitre.affinity.view.venn_diagram.model.CachedFilteredSchemaInfo;
import org.mitre.affinity.view.venn_diagram.model.HarmonyMatchScoreComputer;
import org.mitre.affinity.view.venn_diagram.model.IMatchScoreComputer;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSetsMatrix;
import org.mitre.harmony.matchers.mergers.VoteMerger;
import org.mitre.harmony.matchers.voters.DocumentationMatcher;
import org.mitre.harmony.matchers.voters.EditDistanceMatcher;
import org.mitre.harmony.matchers.voters.ExactStructureMatcher;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.EditorInput;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.views.manager.tags.EditTagDialog;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;

/**
 * Constructs the Affinity View
 */
public class AffinityEditor extends OpenIIEditor implements SelectionClickedListener
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
	
	/** Used to compute match scores between two schemas */
	protected static IMatchScoreComputer matchScoreComputer; 
	static {
		ArrayList<MatchVoter> voters = new ArrayList<MatchVoter>();
		//We use all the Harmony voters by default
		//TODO: Determine optimized set of matchers and run in separate thread.  For
		//  now, running all matchers is too time consuming
		voters.add(new EditDistanceMatcher());
		voters.add(new DocumentationMatcher());
		//voters.add(new ThesaurusMatcher()); //ThesaurusMatcher seems to be most time consuming
		voters.add(new ExactStructureMatcher());
		matchScoreComputer = new HarmonyMatchScoreComputer(voters, new VoteMerger());
	}
	
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
				setPartName("*New Tag");
				/*this.addListenerObject(new Listener() {
					public void handleEvent(Event event) {
						System.out.println("listener triggered");
					}					
				});*/
			}
		}
		if(schemaIDs == null) {
			schemaIDs = OpenIIManager.getTagSchemas(elementID);			
			schemaIDs.addAll(OpenIIManager.getChildTagSchemas(elementID));
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
					if(item.getText().startsWith("View relatedness")) {
						//Show a Venn Diagram view with a single venn diagram (for comparing 2 schemas),
						//or with a matrix of diagrams for each pair of schemas (for comparing more than 2 schemas)
						if(selectedSchemas.size() >= 2) {							
							showVennDiagram(selectedSchemas);
						}
					}
					else if(item.getText().startsWith("View terms")) {
						//Create a temporary cluster using the schemas in the tag and display the TF-IDF dialog 
						//for the cluster
						ClusterGroup cluster = new ClusterGroup();
						cluster.addSchemas(selectedSchemas);
						Shell shell = getSite().getWorkbenchWindow().getShell();
						ClusterDetailsDlg dlg = new ClusterDetailsDlg(shell, SWT.APPLICATION_MODAL, affinityModel, cluster);				
						dlg.setVisible(true);						
					}
					else if(item.getText().startsWith("Open")) {
						//Open schemas in a new Affinity Pane
						EditorManager.launchEditor("AffinityEditor", selectedSchemas);
					}
					else {
						//Create a new tag containing the schemas
						Shell shell = getSite().getWorkbenchWindow().getShell();
						EditTagDialog dlg = new EditTagDialog(shell, null, null, new ArrayList<Integer>(selectedSchemas));						
						dlg.open();
					}						
				}
			};
			MenuItem item = new MenuItem (multiSchemaMenu, SWT.NONE);	
			item.setText("View relatedness of schemas");
			item.addSelectionListener(multiSchemaMenuListener);
			item = new MenuItem (multiSchemaMenu, SWT.NONE);				
			item.setText("View terms in schemas");
			item.addSelectionListener(multiSchemaMenuListener);
			item = new MenuItem (multiSchemaMenu, SWT.NONE);				
			item.setText("Open schemas in new Affinity window");
			item.addSelectionListener(multiSchemaMenuListener);
			item = new MenuItem (multiSchemaMenu, SWT.NONE);				
			item.setText("Create tag from schemas");
			item.addSelectionListener(multiSchemaMenuListener);	
			
			// Create cluster right-click menu			
			clusterMenu = new Menu(affinity);
			SelectionListener clusterMenuListener = new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {				
					MenuItem item = (MenuItem)e.widget;
					if(item.getText().startsWith("View relatedness")) {
						//Show a Venn Diagram view with a single venn diagram (for comparing 2 schemas),
						//or with a matrix of diagrams for each pair of schemas (for comparing more than 2 schemas)
						//for the schemas in the cluster
						if(selectedCluster.getNumSchemas() >= 2) {							
							showVennDiagram(selectedCluster.getSchemaIDs());
						}
					}
					else if(item.getText().startsWith("View terms")) {
						//Display the TF-IDF dialog for the cluster
						Shell shell = getSite().getWorkbenchWindow().getShell();
						ClusterDetailsDlg dlg = new ClusterDetailsDlg(shell, SWT.APPLICATION_MODAL, affinityModel, selectedCluster);				
						dlg.setVisible(true);	
					}
					else if(item.getText().startsWith("Open")) {
						//Open cluster in a new Affinity pane						
						EditorManager.launchEditor("AffinityEditor", selectedCluster.getSchemaIDs());								
					}
					else {
						//Create a new tag containing the schemas in the cluster
						Shell shell = getSite().getWorkbenchWindow().getShell();
						EditTagDialog dlg = new EditTagDialog(shell, null, null, selectedCluster.getSchemaIDs());						
						dlg.open();
					}						
				}
			};
			item = new MenuItem (clusterMenu, SWT.NONE);
			item.setText("View relatedness of schemas in cluster");
			item.addSelectionListener(clusterMenuListener);
			item = new MenuItem (clusterMenu, SWT.NONE);				
			item.setText("View terms in cluster");
			item.addSelectionListener(clusterMenuListener);
			item = new MenuItem (clusterMenu, SWT.NONE);				
			item.setText("Open cluster in new Affinity window");
			item.addSelectionListener(clusterMenuListener);
			item = new MenuItem (clusterMenu, SWT.NONE);				
			item.setText("Create group from cluster");
			item.addSelectionListener(clusterMenuListener);			
		}
		else {
			//There was an error creating the Affinity pane, show the error dialog
			StackTraceDialog stackTraceDlg = new StackTraceDialog(parent.getShell(), affinity.getException());			
			stackTraceDlg.setMessage("Unable to launch Affinity due to:");
			stackTraceDlg.open();
		}
	}	
	
	/** Show a Venn Diagram for the given schemas */
	private void showVennDiagram(Collection<Integer> schemaIDs) {
		//Shell shell = getSite().getWorkbenchWindow().getShell();		
		//VennDiagramDlg dlg = null;
		//int preferredWidth = 400;
		Iterator<Integer> iter = schemaIDs.iterator();
		if(schemaIDs.size() == 2) {
			//Open a VennDiagram with 2 schemas
			CachedFilteredSchemaInfo schemaInfo1 = VennDiagramUtils.createCachedFilteredSchemaInfo(iter.next(), schemaManager);
			VennDiagramUtils.sortFilteredElements(schemaInfo1);
			CachedFilteredSchemaInfo schemaInfo2 = VennDiagramUtils.createCachedFilteredSchemaInfo(iter.next(), schemaManager);
			VennDiagramUtils.sortFilteredElements(schemaInfo2);								
			VennDiagramSets sets = new VennDiagramSets(schemaInfo1, schemaInfo2, 0.6, 1.0, matchScoreComputer);
			EditorManager.launchEditor("VennDiagramEditor", sets);
			/*
			//Create a dialog with a VennDiagramPane for 2 schemas			
			CachedFilteredSchemaInfo schemaInfo1 = VennDiagramUtils.createCachedFilteredSchemaInfo(iter.next(), schemaManager);
			VennDiagramUtils.sortFilteredElements(schemaInfo1);
			CachedFilteredSchemaInfo schemaInfo2 = VennDiagramUtils.createCachedFilteredSchemaInfo(iter.next(), schemaManager);
			VennDiagramUtils.sortFilteredElements(schemaInfo2);								
			VennDiagramSets sets = new VennDiagramSets(schemaInfo1, schemaInfo2, 0.4, 1.0, matchScoreComputer);								
			dlg = new VennDiagramDlg(shell, SWT.APPLICATION_MODAL | SWT.RESIZE, sets, true);
			*/								
		}
		else {
			//Open a VennDiagramMatrix with N schemas
			ArrayList<FilteredSchemaInfo> schemaInfos = new ArrayList<FilteredSchemaInfo>();
			while(iter.hasNext()) {
				CachedFilteredSchemaInfo schemaInfo = VennDiagramUtils.createCachedFilteredSchemaInfo(iter.next(), schemaManager);
				VennDiagramUtils.sortFilteredElements(schemaInfo);
				schemaInfos.add(schemaInfo);
			}
			VennDiagramSetsMatrix matrix = new VennDiagramSetsMatrix(schemaInfos, 0.6, 1.0, matchScoreComputer);
			EditorManager.launchEditor("VennDiagramEditor", matrix);			
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
				if(event.button == 3 || (event.button == 1 && event.controlDown)) {
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
			else if(event.button == 3 || (event.button == 1 && event.controlDown)) {
				//A single cluster was right-clicked, display right-click menu for cluster
				//System.out.println("cluster right clicked");				
				clusterMenu.setVisible(true);
			}
		}
	}	
}
