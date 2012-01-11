package org.mitre.affinity.controller.graph;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.mitre.affinity.algorithms.clusterers.CompleteLinker;
import org.mitre.affinity.algorithms.clusterers.HierarchicalClusterer;
import org.mitre.affinity.algorithms.dimensionality_reducers.PrefuseForceDirectedMDS;
import org.mitre.affinity.algorithms.distance_functions.DistanceFunction;
import org.mitre.affinity.algorithms.distance_functions.graph.AhnLinkDistanceFunction;
import org.mitre.affinity.algorithms.distance_functions.graph.NodeDistanceFunction;
import org.mitre.affinity.controller.BasicAffinityController;
import org.mitre.affinity.model.graph.AffinityGraphModel;
import org.mitre.affinity.model.graph.GraphObject;
import org.mitre.affinity.model.graph.GraphClusterObjectManager.ClusterObjectType;
import org.mitre.affinity.view.AffinityPane;
import org.mitre.affinity.view.dialog.MultiTaskLoadProgressDialog;
import org.mitre.affinity.view.dialog.StackTraceDialog;
import org.mitre.affinity.view.menu.graph.AffinityMenu_Graph;

import communityFinder.Edge;
import communityFinder.GraphReader;
import communityFinder.Node;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * @author CBONACETO
 *
 */
public class AffinityGraphController extends BasicAffinityController<String, GraphObject> {
	 
	/** The Affinity Pane */
	protected AffinityPane<String, GraphObject> affinityPane;
	
	/** The Affinity model */
	private AffinityGraphModel affinityModel;
	
	/** Reference to the menu */
	protected AffinityMenu_Graph menu;

	public AffinityGraphController(Composite parentWindow,
			AffinityPane<String, GraphObject> affinityPane,
			AffinityMenu_Graph menu,
			AffinityGraphModel affinityModel) {
		super(parentWindow, affinityPane.getCraigrogramPane(), affinityPane.getDendrogram(), menu);
		this.affinityPane = affinityPane;
		this.affinityModel = affinityModel;
		this.menu = menu;
		menu.setCurrentClusterObjectType(affinityModel.getClusterObjectManager().getClusterObjectType());
	}		

	@Override
	protected boolean processMenuItemSelectedEvent(MenuItem menuItem,
			Integer menuItemID, Integer parentMenuID) {
		if(menuItemID != null) {			
			if(menuItemID == AffinityMenu_Graph.FILE_OPEN_ITEM) {
				//Open a graph file
				try {
					FileDialog fileDlg = new FileDialog(parentWindow.getShell(), SWT.OPEN);
					fileDlg.setText("Open Graph File");
					//fileDlg.setFilterPath("C:/");
					fileDlg.setFilterExtensions(new String[]{"*.gml", "*.csv"});
					String selectedFile = fileDlg.open();
					if(selectedFile != null) {
						openGraph(selectedFile);
					}
				} catch(Exception ex) {
					//Show stack trace dialog
					StackTraceDialog stackTraceDlg = new StackTraceDialog(parentWindow.getShell(), ex);			
					stackTraceDlg.setMessage("Unable to open graph file, details:");
					stackTraceDlg.open();
				}
				return true;
			} else if(menuItemID == AffinityMenu_Graph.GRAPH_CLUSTER_ON_NODES_ITEM) {
				//Cluster on Nodes
				if(affinityModel.getClusterObjectManager().getClusterObjectType() != ClusterObjectType.Nodes) {
					affinityModel.getClusterObjectManager().setClusterObjectType(ClusterObjectType.Nodes);
					this.menu.setCurrentClusterObjectType(ClusterObjectType.Nodes);
					generateClustersAndPositionGrid();
				}
				return true;
			} else if(menuItemID == AffinityMenu_Graph.GRAPH_CLUSTER_ON_LINKS_ITEM) {
				//Cluster on Links
				if(affinityModel.getClusterObjectManager().getClusterObjectType() != ClusterObjectType.Links) {
					affinityModel.getClusterObjectManager().setClusterObjectType(ClusterObjectType.Links);
					this.menu.setCurrentClusterObjectType(ClusterObjectType.Links);
					generateClustersAndPositionGrid();
				}				
				return true;
			} else if(menuItemID == AffinityMenu_Graph.GRAPH_VIEW_OPTIMAL_COMMUNITIES_ITEM) {
				//Filter clusters based on the optimal community size
				if(affinityPane != null) {
					affinityPane.setClusterDistances(0, affinityModel.getBestCommunityClusterDistance());
				}
			}
			else {			
				return super.processMenuItemSelectedEvent(menuItem, menuItemID, parentMenuID);
			}
		}
		return false;
	}
	
	@Override
	protected boolean processMenuShownEvent(Menu menu, Integer menuID, Integer parentMenuID) {
		if(this.menu != null && menuID != null) {
			if(menuID == AffinityMenu_Graph.GRAPH_CLUSTER_OPTIONS_MENU) {
				this.menu.setCurrentClusterObjectType(affinityModel.getClusterObjectManager().getClusterObjectType());
				return true;
			} else {
				return super.processMenuShownEvent(menu, menuID, parentMenuID);
			}			
		}
		return false;
	}	
	
	/**
	 * @param graphFilename
	 */
	public void openGraph(String graphFilename) {
		final MultiTaskLoadProgressDialog progressDlg = new MultiTaskLoadProgressDialog(parentWindow.getShell(), 3);
		progressDlg.setText("Opening Graph");		
		progressDlg.setMinimum(0);
		progressDlg.setMaximum(100);
		progressDlg.open();				
		
		try {
			//Load the graph from the file system
			progressDlg.setCurrentTaskNote("Loading Graph File");
			UndirectedSparseGraph<Node, Edge> g = GraphReader.readGraph(graphFilename);
			affinityModel.getClusterObjectManager().setGraph(g);
			progressDlg.setNumTasksComplete(1);
			
			//System.out.println(g);
			System.out.println("Node Count: " + g.getVertexCount());
			System.out.println("Edge Count: " + g.getEdgeCount());
			
			//Generate clusters and the position grid
			generateClustersAndPositionGrid(progressDlg, 1);			
		} catch(Exception ex) {
			//Show the stack track dialog			
			StackTraceDialog stackTraceDlg = new StackTraceDialog(parentWindow.getShell(), ex);			
			stackTraceDlg.setMessage("Unable to load graph, details:");
			stackTraceDlg.open();
		}
		progressDlg.close();		
	}
	
	protected void generateClustersAndPositionGrid() {
		final MultiTaskLoadProgressDialog progressDlg = new MultiTaskLoadProgressDialog(parentWindow.getShell(), 2);
		progressDlg.setMinimum(0);
		progressDlg.setMaximum(100);
		switch(affinityModel.getClusterObjectManager().getClusterObjectType()) {
		case Links:
			progressDlg.setText("Generating Link Clusters");
			break;
		case Nodes:
			progressDlg.setText("Generating Node Clusters");
			break;
		}
		progressDlg.open();			
		generateClustersAndPositionGrid(progressDlg, 0);
		progressDlg.close();
	}
	
	protected void generateClustersAndPositionGrid(MultiTaskLoadProgressDialog progressDlg, int numPreviousTasks) {
		if(affinityModel.getClusterObjectManager().getGraph() != null) {
			try {
				//Get the nodes or edges
				Collection<GraphObject> graphObjects = affinityModel.getClusterObjectManager().getClusterObjects();
				ArrayList<String> objectIDs = affinityModel.getClusterObjectManager().getClusterObjectIDs();

				//Run the clusterer to generate clusters and the distance grid	
				if(progressDlg != null) {
					progressDlg.setCurrentTaskNote("Computing Clusters");
				}
				DistanceFunction<String, GraphObject> distanceFun = null;
				switch(affinityModel.getClusterObjectManager().getClusterObjectType()) {
				case Links:
					distanceFun = new AhnLinkDistanceFunction();
					break;
				case Nodes:
					distanceFun = new NodeDistanceFunction(1);
					break;
				default:
					distanceFun = new AhnLinkDistanceFunction();
					break;
				}
				affinityModel.generateClusters(objectIDs, 
						distanceFun, new HierarchicalClusterer<String>(new CompleteLinker<String>()), 
						progressDlg);
				progressDlg.setNumTasksComplete(numPreviousTasks + 1);

				//Run the MDS algorithm to generate 2D points
				progressDlg.setCurrentTaskNote("Computing 2D layout");
				PrefuseForceDirectedMDS<String> mds = new PrefuseForceDirectedMDS<String>();			
				affinityModel.getClusters().getDistanceGrid().rescale(0, 1000);
				mds.setObjectIDs(objectIDs);
				affinityModel.generatePositionGrid(objectIDs, mds, progressDlg);		
				progressDlg.setNumTasksComplete(numPreviousTasks + 2);

				System.out.println(affinityModel.getClusters());

				affinityPane.setClusterObjectsAndClusters(objectIDs, graphObjects, 
						affinityModel.getClusters(), affinityModel.getPositionGrid());

				//Add a cluster distance tick mark at the optimal cluster distance community size
				if(affinityModel.getClusterObjectManager().getClusterObjectType() != ClusterObjectType.Nodes) {
					affinityPane.addClusterDistanceTickLocation(affinityModel.getBestCommunityClusterDistance(), true);					
				} 
			} catch(Exception ex) {
				//Show the stack track dialog			
				StackTraceDialog stackTraceDlg = new StackTraceDialog(parentWindow.getShell(), ex);			
				stackTraceDlg.setMessage("Unable to create clusters, details:");
				stackTraceDlg.open();
			}
		}
	}
}