package org.mitre.affinity.view.menu.graph;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.mitre.affinity.model.graph.GraphClusterObjectManager.ClusterObjectType;
import org.mitre.affinity.view.menu.AffinityMenu;

/**
 * @author CBONACETO
 *
 */
public class AffinityMenu_Graph extends AffinityMenu {
	
	public static final int GRAPH_VIEW_MENU = AffinityMenu.NEXT_MENU_ID;
	public static final int CLUSTER_OPTIONS_MENU = AffinityMenu.NEXT_MENU_ID + 1;
	public static final int CLUSTER_ON_MENU =AffinityMenu.NEXT_MENU_ID + 2; 
	
	public static final int FILE_OPEN_ITEM = AffinityMenu.NEXT_MENU_ITEM_ID;
	public static final int CLUSTER_ON_NODES_ITEM = AffinityMenu.NEXT_MENU_ITEM_ID+1;
	public static final int CLUSTER_ON_LINKS_ITEM = AffinityMenu.NEXT_MENU_ITEM_ID+2;
	public static final int VIEW_OPTIMAL_COMMUNITIES_ITEM = AffinityMenu.NEXT_MENU_ITEM_ID+3;	
	
	/** cluster options menu and menu items */
	protected Menu clusterOptionsMenu;
	protected MenuItem clusterOnNodesItem;
	protected MenuItem clusterOnLinksItem;
	protected MenuItem viewOptimalCommunitiesItem;
	/****/
	
	/** Graph View menu and menu items */
	protected Menu graphViewMenu;
	/****/
	
	protected AffinityMenu_Graph(Decorations decorations) {
		super(decorations);
	}
	
	public AffinityMenu_Graph(Decorations decorations, boolean createFileOpenItem,
			boolean createFileExitItem, boolean createAffinityViewMenu, boolean createGraphViewMenu) {
		super(decorations);
		createFileMenu("&File", createFileOpenItem, createFileExitItem);
		if(createAffinityViewMenu)  {
			createAffinityViewMenu("&Cluster View", true, true, true);
		}
		if(createGraphViewMenu) {
			createGraphViewMenu("&Graph View");
		}
	}	
	
	public void setCurrentClusterObjectType(ClusterObjectType clusterObjectType) {
		clusterOnNodesItem.setSelection(clusterObjectType == ClusterObjectType.Nodes);
		clusterOnLinksItem.setSelection(clusterObjectType == ClusterObjectType.Links);
		viewOptimalCommunitiesItem.setEnabled(clusterObjectType != ClusterObjectType.Nodes);
	}
	
	protected void createFileMenu(String menuName, boolean createFileOpenItem, boolean createFileExitItem) {
		super.createFileMenu("&File", false);
		//Add open item to file menu to open a graph file
		if(createFileOpenItem) {
			createMenuItem(fileMenu, "Open Graph File", SWT.NONE,
					FILE_OPEN_ITEM, FILE_MENU, true);
		}
		
		if(createFileExitItem) {
			addExitItemToFileMenu();
		}
	}
	
	protected void createAffinityViewMenu(String menuName, boolean createCraigrogramOptions, 
			boolean createDendrogramOptions, boolean createClusterOptions) {
		super.createViewMenu(menuName, createCraigrogramOptions,
				createDendrogramOptions);
		
		//Create cluster options sub-menu and add it to the Affinity View menu
		clusterOptionsMenu = createSubMenu(viewMenu, "&Cluster Options", SWT.DROP_DOWN, CLUSTER_OPTIONS_MENU, 
				VIEW_MENU, true);
		Menu clusterTypeMenu = createSubMenu(clusterOptionsMenu, "Cluster On", SWT.DROP_DOWN, CLUSTER_ON_MENU, 
				CLUSTER_OPTIONS_MENU, true);			
		clusterOnNodesItem = createMenuItem(clusterTypeMenu, "Nodes", SWT.RADIO, CLUSTER_ON_NODES_ITEM, 
				CLUSTER_ON_MENU, true);
		clusterOnLinksItem = createMenuItem(clusterTypeMenu, "Links", SWT.RADIO, CLUSTER_ON_LINKS_ITEM, 
				CLUSTER_ON_MENU, true);
		
		viewOptimalCommunitiesItem = createMenuItem(clusterOptionsMenu, "View Best Communities", SWT.NONE, 
				VIEW_OPTIMAL_COMMUNITIES_ITEM, 
				CLUSTER_OPTIONS_MENU, true);
	}

	protected void createGraphViewMenu(String menuName) {
		graphViewMenu = createTopLevelMenu(menuName, SWT.DROP_DOWN, GRAPH_VIEW_MENU);
		
	}
}