package org.mitre.openii.editors.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.mitre.affinity.algorithms.clusterers.HierarchicalClusterer;
import org.mitre.affinity.algorithms.dimensionality_reducers.PrefuseForceDirectedMDS;
import org.mitre.affinity.algorithms.distance_functions.schemas.JaccardDistanceFunction;
import org.mitre.affinity.controller.schemas.AffinitySchemaController;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.schemas.AffinitySchemaManager;
import org.mitre.affinity.model.schemas.AffinitySchemaModel;
import org.mitre.affinity.model.schemas.AffinitySchemaStoreManager;
import org.mitre.affinity.model.schemas.ISchemaManager;
import org.mitre.affinity.view.AffinityPane;
import org.mitre.affinity.view.dialog.MultiTaskLoadProgressDialog;
import org.mitre.affinity.view.dialog.StackTraceDialog;
import org.mitre.affinity.view.drill_down.schemas.SchemaClusterDetailsDlg;
import org.mitre.affinity.view.event.SelectionClickedEvent;
import org.mitre.affinity.view.event.SelectionClickedListener;
import org.mitre.affinity.view.menu.schemas.AffinityMenu_Schemas;
import org.mitre.affinity.view.venn_diagram.VennDiagramUtils;
import org.mitre.affinity.view.venn_diagram.model.CachedFilteredSchemaInfo;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSetsMatrix;
import org.mitre.harmony.matchers.MatchGenerator;
import org.mitre.harmony.matchers.matchers.EditDistanceMatcher;
import org.mitre.harmony.matchers.matchers.ExactMatcher;
import org.mitre.harmony.matchers.matchers.Matcher;
import org.mitre.harmony.matchers.matchers.documentationMatcher.DocumentationMatcher;
import org.mitre.harmony.matchers.mergers.VoteMerger;
import org.mitre.openii.dialogs.tags.EditTagDialog;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.widgets.matchers.MatchersPane;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;

/**
 * Constructs the Affinity View
 */
public class AffinityEditor extends OpenIIEditor implements SelectionClickedListener<Integer>
{	
	/** Stores the Affinity model */
	private AffinitySchemaModel affinityModel;
	
	/** All Affinity instances running in OpenII use the same schema and cluster manager */
	private static final ISchemaManager schemaManager = new AffinitySchemaManager();
	
	/** Stores the Affinity pane */
	private AffinityPane<Integer, Schema> affinity = null;
	
	/**	Menu shown when the mouse is right clicked on a group of schemas or cluster */
	private Menu affinityMenu;
	
	/** Currently selected cluster */	
	private ClusterGroup<Integer> selectedCluster;
	
	/** Currently selected schemas */
	private Collection<Integer> selectedSchemas;
	
	/** Used to compute match scores between two schemas */
	public static MatchGenerator matchGenerator;
	public static MatchGenerator matchGeneratorOld; 
	static
	{
		//need to get the ArrayList of Matchers by calling matchersPane.getMatchers(); rather than adding
		ArrayList<Matcher> matchers = new ArrayList<Matcher>();
		matchers.add(new EditDistanceMatcher());
		matchers.add(new DocumentationMatcher());
		matchers.add(new ExactMatcher());		
		matchGeneratorOld = new MatchGenerator(matchers, new VoteMerger());
	}

	/** Defines the various types of events */
	private static enum AffinityEventType { CREATE_TAG, DISPLAY_TERMS, LAUNCH_AFFINITY, LAUNCH_PROXIMITY };
	
	/** Event listener for the Affinity menus */
	private class AffinityMenuEventListener extends SelectionAdapter
	{
		/** Defines the affinity event type */
		private AffinityEventType type = null;
		
		/** Constructs the event listener */
		private AffinityMenuEventListener(AffinityEventType type)
			{ this.type = type; }
		
		/** Handles the event */
		public void widgetSelected(SelectionEvent e)
		{
			// Creates a new tag for the selected schemas
			if(type==AffinityEventType.CREATE_TAG)
			{
				Shell shell = getSite().getWorkbenchWindow().getShell();
				EditTagDialog dlg = new EditTagDialog(shell, null, null, new ArrayList<Integer>(selectedSchemas));						
				dlg.open();	
			}
	
			// Display terms associated with the selected schemas
			if(type==AffinityEventType.DISPLAY_TERMS)
			{
				Shell shell = getSite().getWorkbenchWindow().getShell();
				SchemaClusterDetailsDlg dlg = new SchemaClusterDetailsDlg(shell, SWT.APPLICATION_MODAL, affinityModel, selectedCluster);				
				dlg.setVisible(true);								
			}
			
			// Launches Affinity with the selected schemas
			if(type==AffinityEventType.LAUNCH_AFFINITY)
				{ EditorManager.launchEditor("AffinityEditor", selectedSchemas); }
		
			// Launches Proximity with the selected schemas
			if(type==AffinityEventType.LAUNCH_PROXIMITY && e.widget instanceof MenuItem)
			{
				// Generate the list of schemas to launch in Proximity
				final ArrayList<FilteredSchemaInfo> schemas = new ArrayList<FilteredSchemaInfo>();
				Iterator<Integer> iter = selectedCluster.getObjectIDs().iterator();
				while(iter.hasNext())
				{
					CachedFilteredSchemaInfo schemaInfo = VennDiagramUtils.createCachedFilteredSchemaInfo(iter.next(), schemaManager);
					VennDiagramUtils.sortFilteredElements(schemaInfo);
					schemas.add(schemaInfo);
				}
				
				// Launch Proximity



				final String referenceSchema = ((MenuItem)e.widget).getText();				
				
				
				//creating the dialog for selecting matchers to be used in Proximity
				Display display = getSite().getWorkbenchWindow().getShell().getDisplay();
				final Shell chooseMatchersDialog = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				
				chooseMatchersDialog.setText("Select Matchers for Proxmity");
				GridLayout gl = new GridLayout(1, false);
				chooseMatchersDialog.setLayout(gl);

				
				Composite pane = new Composite(chooseMatchersDialog, SWT.NONE);
				GridLayout layout = new GridLayout(1, false);
				layout.marginWidth = 8;
				pane.setLayout(layout);
				GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
				gridData.widthHint = 450;
				pane.setLayoutData(gridData);
				
				final MatchersPane matchersPane = new MatchersPane(pane, this);
				
				Composite okayCancelPane = new Composite(chooseMatchersDialog, SWT.NONE);
				RowLayout rowLayout = new RowLayout(); 
				okayCancelPane.setLayout(rowLayout);
				
				Button okayButton = new Button(okayCancelPane, SWT.PUSH);
				okayButton.setText("Ok");
				okayButton.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						ArrayList<Matcher> matchers = matchersPane.getMatchers();
						chooseMatchersDialog.dispose();
						
						matchGenerator = new MatchGenerator(matchers, new VoteMerger());
						VennDiagramSetsMatrix matrix = new VennDiagramSetsMatrix(referenceSchema, schemas, matchGenerator);	
						EditorManager.launchEditor("ProximityView", matrix);	
					}
				});
				
				Button cancelButton = new Button(okayCancelPane, SWT.PUSH);
				cancelButton.setText("Cancel");
				cancelButton.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						chooseMatchersDialog.dispose();
					}
				});
				
				chooseMatchersDialog.pack();
				chooseMatchersDialog.open();
			}
		}
	}
	
	/** Generate a menu item */
	private MenuItem generateMenuItem(Menu menu, String label, AffinityEventType type)
	{
		MenuItem item = new MenuItem (menu, SWT.NONE);				
		item.setText(label);
		item.addSelectionListener(new AffinityMenuEventListener(type));
		return item;
	}
	
	/** Defines the proximity sub menu */
	class ProximitySubMenu implements Listener
	{
		/** Stores the proximity sub menu */
		private Menu proximitySubMenu = null;
		
		/** Constructs the proximity sub menu */
		private ProximitySubMenu(MenuItem proximityMenuItem)
		{
			proximitySubMenu = new Menu(proximityMenuItem);
			proximitySubMenu.addListener(SWT.Show, this);
			proximityMenuItem.setMenu(proximitySubMenu);		
		}
		
		/** Handles an event on the proximity sub menu */
		public void handleEvent(Event e)
		{
			// Clear out old schemas from menu
			MenuItem[] menuItems = proximitySubMenu.getItems();
			for(MenuItem MI : menuItems)
				MI.dispose();

			// Place new schemas into menu
			ArrayList<Integer> schemaIDsForSelection = selectedCluster.getObjectIDs();
			for(int i=0; i<selectedCluster.getNumClusterObjects(); i++)
			{
				Schema schema = schemaManager.getSchema(schemaIDsForSelection.get(i));
				generateMenuItem(proximitySubMenu, schema.getName(), AffinityEventType.LAUNCH_PROXIMITY);
			}
		}		

	}

	/** Generate the menu */
	private Menu generateMenu(Composite parent)
	{
		Menu menu = new Menu(parent);
		
		// Generate basic menu items
		generateMenuItem(menu, "Create tag for schemas", AffinityEventType.CREATE_TAG);
		generateMenuItem(menu, "View terms shared by schemas", AffinityEventType.DISPLAY_TERMS);
		generateMenuItem(menu, "Open schemas in new Affinity window", AffinityEventType.LAUNCH_AFFINITY);
		
		// Generate the proximity menu item
		MenuItem proximityMenuItem = new MenuItem(menu, SWT.CASCADE);
		proximityMenuItem.setText("Open Proximity for schema...");
		new ProximitySubMenu(proximityMenuItem);
				
		return menu;
	}

	/** Displays the Affinity View */ @SuppressWarnings("unchecked")
	public void createPartControl(final Composite parent)
	{
		// Connects the SchemaStoreClient to Affinity's SchemaStoreManager
		AffinitySchemaStoreManager.setConnection(RepositoryManager.getClient());
	
		// Create a dialog to show progress as Affinity loads
		final MultiTaskLoadProgressDialog progressDlg = new MultiTaskLoadProgressDialog(parent.getShell(), 3);
		progressDlg.setText("Opening Affinity");	
		progressDlg.setCurrentTaskNote("Loading Schemas");
		progressDlg.open();		
		
		// Construct the Affinity pane
		parent.setLayout(new FillLayout());
		
		// Generate the list of schemas to be clustered
		ArrayList<Integer> schemaIDs = null;
		if(getElementID()!=null)
		{
			schemaIDs = OpenIIManager.getTagSchemas(getElementID());			
			schemaIDs.addAll(OpenIIManager.getChildTagSchemas(getElementID()));			
		}
		else
		{
			schemaIDs = new ArrayList<Integer>((Collection<Integer>)getElement());
			setPartName(schemaIDs.size()==OpenIIManager.getSchemaIDs().size() ? "All Schemas" : "*New Tag");
		}
		affinityModel =  new AffinitySchemaModel(schemaManager);
		
		//Create the menu
		final Decorations decorations = new Decorations(parent, SWT.NONE);
		decorations.setLayout(new FillLayout());
		AffinityMenu_Schemas menu = new AffinityMenu_Schemas(decorations, true, false);
		
		//Create a blank Affinity pane
		affinity = new AffinityPane<Integer, Schema>(decorations, SWT.NONE, affinityModel.getClusterObjectManager(), "Schema Name");
		
		//Initialize the controller
		AffinitySchemaController controller = new AffinitySchemaController(
				affinity, affinity.getCraigrogramPane(), affinity.getDendrogram(), menu, affinityModel);
		
		//Get the schemas, then generate clusters and the position grid
		Exception exception = null;
		try {
			//Get the schemas
			List<Schema> schemas = new ArrayList<Schema>();
			int i = 1;
			int numSchemas = schemaIDs.size();			
			int percentProgressPerSchema = 100/numSchemas;
			for(Integer schemaID : schemaIDs) {			
				Schema schema = affinityModel.getClusterObjectManager().getClusterObject(schemaID);
				if(schema == null) {
					System.err.println("Warning: schema " + schemaID + " not found");
					progressDlg.setErrorNote("Error: schema " + schemaID + " not found");
				}
				else {
					schemas.add(schema);
				}				
				progressDlg.setNote("Loading Schemas (" + i + "/" + numSchemas + ")");
				progressDlg.setProgress(i * percentProgressPerSchema);
				i++;
			}
			controller.setSchemaIDsAndSchemas(schemaIDs, schemas);
			progressDlg.setNumTasksComplete(1);
			
			//Generate clusters and the distance grid
			progressDlg.setCurrentTaskNote("Computing Clusters");
			affinityModel.generateClusters(schemaIDs, new JaccardDistanceFunction(), 
					new HierarchicalClusterer<Integer>(), progressDlg);
			progressDlg.setNumTasksComplete(2);
			
			//Run the MDS algorithm to generate the position grid
			progressDlg.setCurrentTaskNote("Computing 2D layout");
			PrefuseForceDirectedMDS<Integer> mds = new PrefuseForceDirectedMDS<Integer>();			
			affinityModel.generatePositionGrid(schemaIDs, mds, progressDlg);
			progressDlg.setNumTasksComplete(3);
			
			//Populate the affinity pane	
			affinity.setClusterObjectsAndClusters(schemaIDs, schemas, 
					affinityModel.getClusters(), affinityModel.getPositionGrid());
		} catch(Exception ex) {
			exception = ex;
		}
		
		// Populate the affinity pane		
		/*affinity = new AffinityPane<Integer, Schema>(parent, SWT.NONE, affinityModel, schemaIDs, 
				 new JaccardDistanceFunction(), new PrefuseForceDirectedMDS<Integer>(),
				 new HierarchicalClusterer<Integer>(), progressDlg, "Enter Schema Name", true);*/		
		
		// Indicate that Affinity has launched
		progressDlg.close();
		
		// Finish configuring Affinity
		affinity.getCraigrogram().debug = false;
		affinity.addSelectionClickedListener(this);	
		affinityMenu = generateMenu(affinity);
		if(exception != null) {
			// Show an error dialog if an exception was caught
			StackTraceDialog stackTraceDlg = new StackTraceDialog(parent.getShell(), exception);			
			stackTraceDlg.setMessage("Unable to launch Affinity due to:");
			stackTraceDlg.open();
		}
		/*if(affinity.isAffinityPaneCreated()) 
		{
			affinity.getCraigrogram().debug = false;
			affinity.addSelectionClickedListener(this);	
			affinityMenu = generateMenu(affinity);
		} 
		else 
		{
			// Show an error dialog if an exception was caught
			StackTraceDialog stackTraceDlg = new StackTraceDialog(parent.getShell(), affinity.getException());			
			stackTraceDlg.setMessage("Unable to launch Affinity due to:");
			stackTraceDlg.open();
		}*/
	}	

	/** Handles the selection of a group of schemas or cluster */
	public void selectionClicked(SelectionClickedEvent<Integer> event)
	{
		// Handles the selection of a group of schemas
		if(event.selectedClusterObjects!=null)
		{
			// Generate cluster around selected schemas
			selectedCluster = new ClusterGroup<Integer>();
			selectedCluster.addClusterObjects(selectedSchemas);

			// If only a single schema was double clicked, launch default schema editor
			if(event.selectedClusterObjects.size()==1)
			{
				if(event.button==1 && event.clickCount==2)
					EditorManager.launchDefaultEditor(schemaManager.getSchema(event.selectedClusterObjects.iterator().next()));		
			}
			
			// Otherwise, if a group of schemas was right clicked display the menu
			else if(event.button==3 || (event.button==1 && event.controlDown))
			{
				selectedSchemas = event.selectedClusterObjects;
				affinityMenu.setVisible(true);
			}
		}
		
		// Handles the selection of a cluster
		else if(event.selectedClusters!=null && event.selectedClusters.size()==1)
		{
			// Get the selected cluster (and schemas)
			selectedCluster = event.selectedClusters.iterator().next();
			selectedSchemas = selectedCluster.getObjectIDs();

			// If the cluster was double clicked, show the cluster details
			if(event.button==1 && event.clickCount==2)
			{
				Shell shell = getSite().getWorkbenchWindow().getShell();
				SchemaClusterDetailsDlg dlg = new SchemaClusterDetailsDlg(shell, SWT.APPLICATION_MODAL, affinityModel, selectedCluster);				
				dlg.setVisible(true);		
			}			

			// Otherwise, if the cluster was right clicked display the menu
			else if(event.button==3 || (event.button==1 && event.controlDown))
				affinityMenu.setVisible(true);
		}
	}	
}