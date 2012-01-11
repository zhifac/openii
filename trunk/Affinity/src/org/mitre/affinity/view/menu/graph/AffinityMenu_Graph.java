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
	
	public static final int GRAPH_MENU = AffinityMenu.NEXT_MENU_ID;
	public static final int GRAPH_CLUSTER_OPTIONS_MENU = AffinityMenu.NEXT_MENU_ID + 1;
	
	public static final int FILE_OPEN_ITEM = AffinityMenu.NEXT_MENU_ITEM_ID;
	public static final int GRAPH_CLUSTER_ON_NODES_ITEM = AffinityMenu.NEXT_MENU_ITEM_ID+1;
	public static final int GRAPH_CLUSTER_ON_LINKS_ITEM = AffinityMenu.NEXT_MENU_ITEM_ID+2;
	public static final int GRAPH_VIEW_OPTIMAL_COMMUNITIES_ITEM = AffinityMenu.NEXT_MENU_ITEM_ID+3;	
	
	/** Graph menu items */
	protected Menu graphMenu;
	protected MenuItem clusterOnNodesItem;
	protected MenuItem clusterOnLinksItem;
	protected MenuItem viewOptimalCommunitiesItem;
	/****/
	
	protected AffinityMenu_Graph(Decorations decorations) {
		super(decorations);
	}
	
	public AffinityMenu_Graph(Decorations decorations, boolean createFileOpenItem,
			boolean createFileExitItem, boolean createViewMenu, boolean createGraphMenu) {
		super(decorations);
		createFileMenu(createFileOpenItem, createFileExitItem);
		if(createViewMenu)  {
			createViewMenu(true,  true);
		}
		if(createGraphMenu) {
			createGraphMenu();
		}
	}	
	
	public void setCurrentClusterObjectType(ClusterObjectType clusterObjectType) {
		clusterOnNodesItem.setSelection(clusterObjectType == ClusterObjectType.Nodes);
		clusterOnLinksItem.setSelection(clusterObjectType == ClusterObjectType.Links);
		viewOptimalCommunitiesItem.setEnabled(clusterObjectType != ClusterObjectType.Nodes);
	}
	
	protected void createFileMenu(boolean createFileOpenItem, boolean createFileExitItem) {
		super.createFileMenu(false);
		//Add open item to file menu to open a graph file
		if(createFileOpenItem) {
			createMenuItem(fileMenu, "Open Graph File", SWT.NONE,
					FILE_OPEN_ITEM, FILE_MENU, true);
		}
		
		if(createFileExitItem) {
			addExitItemToFileMenu();
		}
	}
	
	protected void createGraphMenu() {
		graphMenu = createTopLevelMenu("&Graph", SWT.DROP_DOWN, GRAPH_MENU);
		
		Menu clusterTypeMenu = createSubMenu(graphMenu, "&Cluster On", SWT.DROP_DOWN, GRAPH_CLUSTER_OPTIONS_MENU, 
				GRAPH_MENU, true);			
		clusterOnNodesItem = createMenuItem(clusterTypeMenu, "Nodes", SWT.RADIO, GRAPH_CLUSTER_ON_NODES_ITEM, 
				GRAPH_CLUSTER_OPTIONS_MENU, true);
		clusterOnLinksItem = createMenuItem(clusterTypeMenu, "Links", SWT.RADIO, GRAPH_CLUSTER_ON_LINKS_ITEM, 
				GRAPH_CLUSTER_OPTIONS_MENU, true);
		
		viewOptimalCommunitiesItem = createMenuItem(graphMenu, "View Best Communities", SWT.NONE, GRAPH_VIEW_OPTIMAL_COMMUNITIES_ITEM, 
				GRAPH_MENU, true);
	}
}