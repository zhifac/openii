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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.clusters.distanceFunctions.JaccardDistanceFunction;
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
import org.mitre.openii.dialogs.projects.EditProjectDialog;
import org.mitre.openii.dialogs.tags.EditTagDialog;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.EditorInput;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
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
	
	protected static IMatchScoreComputer entityMatchScoreComputer;
	static{
		ArrayList<MatchVoter> voters = new ArrayList<MatchVoter>();
		voters.add(new DocumentationMatcher());
		entityMatchScoreComputer = new HarmonyMatchScoreComputer(voters, new VoteMerger());
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
		
		// Generate the list of schemas to be clustered
		ArrayList<Integer> schemaIDs = null;
		if(elementID!=null)
		{
			schemaIDs = OpenIIManager.getTagSchemas(elementID);			
			schemaIDs.addAll(OpenIIManager.getChildTagSchemas(elementID));			
		}
		else
		{
			Object object = ((EditorInput)getEditorInput()).getElement();
			schemaIDs = new ArrayList<Integer>((Collection)object);
			setPartName(schemaIDs.size()==OpenIIManager.getSchemaIDs().size() ? "All Schemas" : "*New Tag");
		}
		this.affinityModel =  new AffinityModel(schemaManager, clusterManager);
		JaccardDistanceFunction jdf = new JaccardDistanceFunction();
		affinity = new AffinityPane(parent, SWT.NONE, affinityModel, schemaIDs, progressDlg);
		//affinity = new AffinityPane(parent, SWT.NONE, affinityModel, schemaIDs, jdf, progressDlg);
		//is this the right one to call?
		
		
		progressDlg.close();
		if(affinity.isAffinityPaneCreated()) {
			affinity.getCraigrogram().debug = false;
			affinity.addSelectionClickedListener(this);	
			
			
			// Create multiple schemas right-click menu
			multiSchemaMenu = new Menu(affinity);
			SelectionListener multiSchemaMenuListener = new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {					
					MenuItem item = (MenuItem)e.widget;
					if(item.getText().startsWith("Element")) {
						//Show a Venn Diagram view with a single venn diagram (for comparing 2 schemas),
						//or with a matrix of diagrams for each pair of schemas (for comparing more than 2 schemas)
						if(selectedSchemas.size() >= 2) {							
							showVennDiagram(selectedSchemas);
						}
					}
					else if(item.getText().startsWith("Entity")){
						if(selectedSchemas.size() >=2){
							showVennDiagramWEntities(selectedSchemas);
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
					}else if(item.getText().startsWith("Open")) {
						//Open schemas in a new Affinity Pane
						EditorManager.launchEditor("AffinityEditor", selectedSchemas);
					//}else if(item.getText().startsWith("View vocab debug")){
						//EditorManager.launchEditor("VocabDebugEditor", selectedSchemas);
						//System.out.println("Open up debug view");
					}else if(item.getText().startsWith("View vocab view")){
						//need to send it a project object.. no object exists for projects starting in Affinity
						//temp create one
						EditorManager.launchEditor("VocabEditor", selectedSchemas);
						//System.out.println("Open up debug view");
					}else if(item.getText().startsWith("Jaccard")){
						//change the distance metric from dice to Jaccard
						//if(affinity.getCurrentDistFunction() == "Dice"){
						//	affinity.changeCurrentDistFunction("Jacard");							
						//}
					}else if(item.getText().startsWith("Dice")){
						//change the distance metric from jaccard to dice						
						//if(affinity.getCurrentDistFunction() == "Jacard"){
							//affinity.changeCurrentDistFunction("Dice");
						//}											
					}else {
						//Create a new tag containing the schemas
						Shell shell = getSite().getWorkbenchWindow().getShell();
						EditTagDialog dlg = new EditTagDialog(shell, null, null, new ArrayList<Integer>(selectedSchemas));						
						dlg.open();
					}						
				}
			};

			MenuItem item = new MenuItem(multiSchemaMenu, SWT.NONE);
			item = new MenuItem (multiSchemaMenu, SWT.NONE);				
			item.setText("Create a tag for all schemas in this cluster");
			item.addSelectionListener(multiSchemaMenuListener);	
			

			item = new MenuItem (multiSchemaMenu, SWT.NONE);				
			item.setText("Open schemas in new Affinity window");
			item.addSelectionListener(multiSchemaMenuListener);

			MenuItem kneighbor = new MenuItem(multiSchemaMenu, SWT.POP_UP);
			kneighbor.setText("Venn Diagram K Nearest Neighbors for schema...");
			kneighbor.addSelectionListener(multiSchemaMenuListener);
						
			MenuItem typeOfRelatedness1 = new MenuItem(multiSchemaMenu, SWT.CASCADE);
			typeOfRelatedness1.setText("View amount of overlap(Venn Diagram Matrix)");
			typeOfRelatedness1.addSelectionListener(multiSchemaMenuListener);			
		
			Menu subMenu1 = new Menu(multiSchemaMenu);
			typeOfRelatedness1.setMenu(subMenu1);
			item = new MenuItem (subMenu1, SWT.NONE);					
			item.setText("Entity overlap - faster, less accurate");
			item.addSelectionListener(multiSchemaMenuListener);
			item = new MenuItem (subMenu1, SWT.NONE);
			item.setText("Element overlap - slower, more accurate");
			item.addSelectionListener(multiSchemaMenuListener);
			
			item = new MenuItem (multiSchemaMenu, SWT.NONE);				
			item.setText("View terms shared by these schemas (bag of words approach)");
			item.addSelectionListener(multiSchemaMenuListener);
			
			item = new MenuItem (multiSchemaMenu, SWT.NONE);				
			item.setText("Create a project which includes all schemas in this cluster");
			item.addSelectionListener(multiSchemaMenuListener);	

			item = new MenuItem (multiSchemaMenu, SWT.NONE);
			item.setText("View vocab debug view");
			item.addSelectionListener(multiSchemaMenuListener);
			
			item = new MenuItem (multiSchemaMenu, SWT.NONE);
			item.setText("View vocab view");
			item.addSelectionListener(multiSchemaMenuListener);

			//item = new MenuItem (multiSchemaMenu, SWT.NONE);
			//item.setText("Change distance function to...");
			//item.addSelectionListener(multiSchemaMenuListener);

			MenuItem typeDistFunction = new MenuItem(multiSchemaMenu, SWT.CASCADE);
			typeDistFunction.setText("Change distance function to...");
			typeDistFunction.addSelectionListener(multiSchemaMenuListener);			
		
			Menu subMenu2 = new Menu(multiSchemaMenu);
			typeDistFunction.setMenu(subMenu2);
			item = new MenuItem (subMenu2, SWT.NONE);					
			item.setText("Jaccard");
			item.addSelectionListener(multiSchemaMenuListener);
			item = new MenuItem (subMenu2, SWT.NONE);
			item.setText("Dice");
			item.addSelectionListener(multiSchemaMenuListener);

			
			item = new MenuItem(multiSchemaMenu, SWT.NONE);
			item.setText("Save statistics to...");
			item.addSelectionListener(multiSchemaMenuListener);

			// Create cluster right-click menu			
			clusterMenu = new Menu(affinity);
			final SelectionListener clusterMenuListener = new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {				
					MenuItem item = (MenuItem)e.widget;
					if(item.getText().startsWith("Element")) {
						//Show a Venn Diagram view with a single venn diagram (for comparing 2 schemas),
						//or with a matrix of diagrams for each pair of schemas (for comparing more than 2 schemas)
						//for the schemas in the cluster
						if(selectedCluster.getNumSchemas() >= 2) {							
							showVennDiagram(selectedCluster.getSchemaIDs());
						}
					}else if(item.getText().startsWith("Entity")){
						if(selectedCluster.getNumSchemas() >= 2) {							
							showVennDiagramWEntities(selectedCluster.getSchemaIDs());
						}
					}
					else if(item.getText().startsWith("View terms")) {
						//Display the TF-IDF dialog for the cluster
						Shell shell = getSite().getWorkbenchWindow().getShell();
						ClusterDetailsDlg dlg = new ClusterDetailsDlg(shell, SWT.APPLICATION_MODAL, affinityModel, selectedCluster);				
						dlg.setVisible(true);	
					}else if(item.getText().startsWith("Open schemas")) {
						//Open cluster in a new Affinity pane						
						EditorManager.launchEditor("AffinityEditor", selectedCluster.getSchemaIDs());								
					//}else if(item.getText().startsWith("View vocab debug view")){
						//System.out.println("Open up debug view");
						//EditorManager.launchEditor("VocabDebugEditor", selectedCluster.getSchemaIDs());
					//}else if(item.getText().startsWith("Open Venn Diagram")) {
						//Open cluster in k nearest neighbor view				
						//showVennDiagramKNearest(selectedCluster.getSchemaIDs());	
					}else if(item.getText().startsWith("View vocab view")){
						EditorManager.launchEditor("VocabEditor", selectedCluster.getSchemaIDs());
					}else if(item.getText().startsWith("Create a project")){
						Shell shell = getSite().getWorkbenchWindow().getShell();
						EditProjectDialog dlg = new EditProjectDialog(shell);
						dlg.open();
					}else if(item.getText().startsWith("Jaccard")){
						//change the distance metric from dice to Jaccard
						//if(affinity.getCurrentDistFunction() == "Dice"){
							//affinity.changeCurrentDistFunction("Jaccard");
						//}
					}else if(item.getText().startsWith("Dice")){
						//change the distance metric from jaccard to dice						
						//if(affinity.getCurrentDistFunction() == "Jaccard"){
							//affinity.changeCurrentDistFunction("Dice");
						//}						
					}else if(item.getText().startsWith("Save statistics")){
						Shell shell = getSite().getWorkbenchWindow().getShell();
						//FileDialog fileDlg = new FileDialog(parent.getShell(), SWT.SAVE);
						FileDialog dlg = new FileDialog(shell, SWT.SAVE);
						dlg.setText("Save Stats");
						dlg.setFilterPath("C:/");
						dlg.setFilterExtensions(new String[]{"*.xls"});
						dlg.setFileName("AffinityStatistics.xls");
						dlg.setOverwrite(true);
						String selectedFileName = dlg.open();
						affinity.writeToSpreadsheet(selectedFileName);
					}else if(item.getText().startsWith("Create a tag")){
						//Create a new tag containing the schemas in the cluster
						Shell shell = getSite().getWorkbenchWindow().getShell();
						EditTagDialog dlg = new EditTagDialog(shell, null, null, selectedCluster.getSchemaIDs());						
						dlg.open();
					}else{
						//a schema name to use with K-nearest neighbors was selected
						//System.out.println("selected " + item.getText());
						showVennDiagramKNearest(item.getText(), selectedCluster.getSchemaIDs());
					}
				}
			};
			
			item = new MenuItem (clusterMenu, SWT.NONE);				
			item.setText("View terms shared by these schemas (bag of words approach)");
			item.addSelectionListener(clusterMenuListener);

			

			MenuItem kneighbors = new MenuItem(clusterMenu, SWT.CASCADE);
			kneighbors.setText("Venn Diagram K Nearest Neighbors for schema...");
			
			final Menu subMenuK = new Menu(clusterMenu);
			kneighbors.setMenu(subMenuK);
			
			subMenuK.addListener(SWT.Show, new Listener(){
				public void handleEvent(Event e){
					MenuItem[] menuItems = subMenuK.getItems();
					for(MenuItem MI : menuItems){
						MI.dispose();
					}
					//get schemas in the cluster
					ArrayList<Integer> schemaIDsForSelection = selectedCluster.getSchemaIDs();
					for(int i=0; i<selectedCluster.getNumSchemas(); i++){
						MenuItem menuItem = new MenuItem(subMenuK, SWT.PUSH);
						menuItem.setText(schemaManager.getSchema(schemaIDsForSelection.get(i)).getName());
						menuItem.addSelectionListener(clusterMenuListener);
					}
				}
			});

			
			MenuItem typeRelatedness = new MenuItem(clusterMenu, SWT.CASCADE);
			typeRelatedness.setText("View amount of overlap(Venn Diagram Matrix)");
			
			Menu subMenu = new Menu(clusterMenu);
			typeRelatedness.setMenu(subMenu);

			item = new MenuItem (subMenu, SWT.NONE);					
			item.setText("Entity overlap - faster, less accurate");
			item.addSelectionListener(clusterMenuListener);
			
			item = new MenuItem (subMenu, SWT.NONE);
			item.setText("Element overlap - slower, more accurate");
			item.addSelectionListener(clusterMenuListener);
			
			item = new MenuItem (clusterMenu, SWT.NONE);				
			item.setText("Open schemas in new Affinity window");
			item.addSelectionListener(clusterMenuListener);
			
		
			item = new MenuItem (clusterMenu, SWT.NONE);				
			item.setText("Create a tag for all schemas in this cluster");
			item.addSelectionListener(clusterMenuListener);	
			
			item = new MenuItem (clusterMenu, SWT.NONE);				
			item.setText("Create a project which includes all schemas in this cluster");
			item.addSelectionListener(clusterMenuListener);	
			
			MenuItem distFunctionSelection = new MenuItem(clusterMenu, SWT.CASCADE);
			distFunctionSelection.setText("Change distance metric to...");
			
			Menu subMenu3 = new Menu(clusterMenu);
			distFunctionSelection.setMenu(subMenu2);

			item = new MenuItem (subMenu3, SWT.NONE);					
			item.setText("Jaccard");
			item.addSelectionListener(clusterMenuListener);
			
			item = new MenuItem (subMenu3, SWT.NONE);
			item.setText("Dice");
			item.addSelectionListener(clusterMenuListener);

			//item = new MenuItem (clusterMenu, SWT.NONE);
			//item.setText("View vocab debug view");
			//item.addSelectionListener(clusterMenuListener);
			
		//	item = new MenuItem (clusterMenu, SWT.NONE);
			//item.setText("View vocab view");
			//item.addSelectionListener(clusterMenuListener);
			
			item = new MenuItem (clusterMenu, SWT.NONE);
			item.setText("Save statistics to...");
			item.addSelectionListener(clusterMenuListener);
		
		
		}
		else {
			//There was an error creating the Affinity pane, show the error dialog
			StackTraceDialog stackTraceDlg = new StackTraceDialog(parent.getShell(), affinity.getException());			
			stackTraceDlg.setMessage("Unable to launch Affinity due to:");
			stackTraceDlg.open();
		}
	}	

	
	/** this will launch the K nearest neighbors view **/
	private void showVennDiagramKNearest(String mainSchemaName, Collection<Integer> schemaIDs){
		//NOTE: May still need to handle case with size 2
		//right now this is only for size N
		Iterator<Integer> iter = schemaIDs.iterator();
		ArrayList<FilteredSchemaInfo> schemaInfos = new ArrayList<FilteredSchemaInfo>();
		while(iter.hasNext()) {
			CachedFilteredSchemaInfo schemaInfo = VennDiagramUtils.createCachedFilteredSchemaInfo(iter.next(), schemaManager);
			VennDiagramUtils.sortFilteredElements(schemaInfo);
			schemaInfos.add(schemaInfo);
		}
		
		//VennDiagramSetsMatrix matrix = new VennDiagramSetsMatrix(schemaInfos, matchScoreComputer, 1);		
		VennDiagramSetsMatrix matrix = new VennDiagramSetsMatrix(mainSchemaName, schemaInfos, matchScoreComputer, 1);		
		EditorManager.launchEditor("VennDiagramKNearestEditor", matrix);			
	}
	
	
	private void showVennDiagramWEntities(Collection<Integer> schemaIDs){
		Iterator<Integer> iter = schemaIDs.iterator();
		if(schemaIDs.size() == 2) {
			//Open a VennDiagram with 2 schemas
			CachedFilteredSchemaInfo schemaInfo1 = VennDiagramUtils.createCachedFilteredSchemaInfo(iter.next(), schemaManager);
			VennDiagramUtils.sortFilteredElements(schemaInfo1);
			CachedFilteredSchemaInfo schemaInfo2 = VennDiagramUtils.createCachedFilteredSchemaInfo(iter.next(), schemaManager);
			VennDiagramUtils.sortFilteredElements(schemaInfo2);	
			
			//VennDiagramSets sets = new VennDiagramSets(schemaInfo1, schemaInfo2, 0.6, 1.0, entityMatchScoreComputer, 2);
			VennDiagramSets sets = new VennDiagramSets(schemaInfo1, schemaInfo2, 0.8, 1.0, entityMatchScoreComputer, 2);
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
			
			//VennDiagramSetsMatrix matrix = new VennDiagramSetsMatrix(schemaInfos, 0.8, 1.0, entityMatchScoreComputer, 2);
			VennDiagramSetsMatrix matrix = new VennDiagramSetsMatrix(schemaInfos, entityMatchScoreComputer, 2);
			EditorManager.launchEditor("VennDiagramEditor", matrix);			
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
			VennDiagramSets sets = new VennDiagramSets(schemaInfo1, schemaInfo2, 0.8, 1.0, matchScoreComputer, 1);
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
			
			//VennDiagramSetsMatrix matrix = new VennDiagramSetsMatrix(schemaInfos, 0.8, 1.0, matchScoreComputer, 1);
			VennDiagramSetsMatrix matrix = new VennDiagramSetsMatrix(schemaInfos, matchScoreComputer, 1);
			
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
