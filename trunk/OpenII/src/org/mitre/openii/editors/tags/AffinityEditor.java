package org.mitre.openii.editors.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.model.AffinityModel;
import org.mitre.affinity.model.AffinitySchemaManager;
import org.mitre.affinity.model.AffinitySchemaStoreManager;
import org.mitre.affinity.model.ISchemaManager;
import org.mitre.affinity.view.application.AffinityPane;
import org.mitre.affinity.view.application.LoadProgressDialog;
import org.mitre.affinity.view.application.StackTraceDialog;
import org.mitre.affinity.view.cluster_details.ClusterDetailsDlg;
import org.mitre.affinity.view.cluster_details.ClusterDetailsPane;
import org.mitre.affinity.view.event.SelectionClickedEvent;
import org.mitre.affinity.view.event.SelectionClickedListener;
import org.mitre.affinity.view.venn_diagram.VennDiagramUtils;
import org.mitre.affinity.view.venn_diagram.model.CachedFilteredSchemaInfo;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSetsMatrix;
import org.mitre.harmony.matchers.MatchGenerator;
import org.mitre.harmony.matchers.matchers.DocumentationMatcher;
import org.mitre.harmony.matchers.matchers.EditDistanceMatcher;
import org.mitre.harmony.matchers.matchers.ExactMatcher;
import org.mitre.harmony.matchers.matchers.Matcher;
import org.mitre.harmony.matchers.mergers.VoteMerger;
import org.mitre.openii.dialogs.tags.EditTagDialog;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.openii.widgets.matchers.MatchersPane;

/**
 * Constructs the Affinity View
 */
public class AffinityEditor extends OpenIIEditor implements SelectionClickedListener
{	
	/** Stores the Affinity model */
	private AffinityModel affinityModel;
	
	/** All Affinity instances running in OpenII use the same schema and cluster manager */
	private static final ISchemaManager schemaManager = new AffinitySchemaManager();
	
	/** Stores the Affinity pane */
	private AffinityPane affinity = null;
	
	/**	Menu shown when the mouse is right clicked on a group of schemas or cluster */
	private Menu affinityMenu;
	
	/** Currently selected cluster */	
	private ClusterGroup selectedCluster;
	
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
				ClusterDetailsDlg dlg = new ClusterDetailsDlg(shell, SWT.APPLICATION_MODAL, affinityModel, selectedCluster);				
				dlg.setVisible(true);								
			}
			
			// Launches Affinity with the selected schemas
			if(type==AffinityEventType.LAUNCH_AFFINITY)
				{ EditorManager.launchEditor("AffinityEditor", selectedSchemas); }
		
			// Launches Proximity with the selected schemas
			if(type==AffinityEventType.LAUNCH_PROXIMITY)
			{
				// Generate the list of schemas to launch in Proximity
				final ArrayList<FilteredSchemaInfo> schemas = new ArrayList<FilteredSchemaInfo>();
				Iterator<Integer> iter = selectedCluster.getSchemaIDs().iterator();
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
				chooseMatchersDialog.setLayout(new GridLayout(1, false));
	
				final MatchersPane matchersPane = new MatchersPane(chooseMatchersDialog, this);
					
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
			ArrayList<Integer> schemaIDsForSelection = selectedCluster.getSchemaIDs();
			for(int i=0; i<selectedCluster.getNumSchemas(); i++)
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

	/** Displays the Affinity View */
	public void createPartControl(final Composite parent)
	{
		// Connects the SchemaStoreClient to Affinity's SchemaStoreManager
		AffinitySchemaStoreManager.setConnection(RepositoryManager.getClient());
	
		// Create a dialog to show progress as Affinity loads
		final LoadProgressDialog progressDlg = new LoadProgressDialog(parent.getShell());
		progressDlg.setText("Opening Affinity");		
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
		
		// Generate the affinity pane
		this.affinityModel =  new AffinityModel(schemaManager);
		affinity = new AffinityPane(parent, SWT.NONE, affinityModel, schemaIDs, progressDlg);		
		
		// Indicate that Affinity has launched
		progressDlg.close();
		
		// Finish configuring Affinity
		if(affinity.isAffinityPaneCreated())
		{
			affinity.getCraigrogram().debug = false;
			affinity.addSelectionClickedListener(this);	
			affinityMenu = generateMenu(affinity);
		}
		
		// Show error dialog if Affinity pane failed to launch
		else
		{
			StackTraceDialog stackTraceDlg = new StackTraceDialog(parent.getShell(), affinity.getException());			
			stackTraceDlg.setMessage("Unable to launch Affinity due to:");
			stackTraceDlg.open();
		}
	}	

	/** Handles the selection of a group of schemas or cluster */
	public void selectionClicked(SelectionClickedEvent event)
	{
		// Handles the selection of a group of schemas
		if(event.selectedSchemas!=null)
		{
			// Generate cluster around selected schemas
			selectedCluster = new ClusterGroup();
			selectedCluster.addSchemas(selectedSchemas);

			// If only a single schema was double clicked, launch default schema editor
			if(event.selectedSchemas.size()==1)
			{
				if(event.button==1 && event.clickCount==2)
					EditorManager.launchDefaultEditor(schemaManager.getSchema(event.selectedSchemas.iterator().next()));		
			}
			
			// Otherwise, if a group of schemas was right clicked display the menu
			else if(event.button==3 || (event.button==1 && event.controlDown))
			{
				selectedSchemas = event.selectedSchemas;
				affinityMenu.setVisible(true);
			}
		}
		
		// Handles the selection of a cluster
		else if(event.selectedClusters!=null && event.selectedClusters.size()==1)
		{
			// Get the selected cluster (and schemas)
			selectedCluster = event.selectedClusters.iterator().next();
			selectedSchemas = selectedCluster.getSchemaIDs();

			// If the cluster was double clicked, show the cluster details
			if(event.button==1 && event.clickCount==2)
			{
				Shell shell = getSite().getWorkbenchWindow().getShell();
				ClusterDetailsDlg dlg = new ClusterDetailsDlg(shell, SWT.APPLICATION_MODAL, affinityModel, selectedCluster);				
				dlg.setVisible(true);		
			}			

			// Otherwise, if the cluster was right clicked display the menu
			else if(event.button==3 || (event.button==1 && event.controlDown))
				affinityMenu.setVisible(true);
		}
	}	
}
