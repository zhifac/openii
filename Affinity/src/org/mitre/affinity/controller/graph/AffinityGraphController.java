package org.mitre.affinity.controller.graph;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingWorker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.mitre.affinity.algorithms.clusterers.CompleteLinker;
import org.mitre.affinity.algorithms.clusterers.HierarchicalClusterer;
import org.mitre.affinity.algorithms.dimensionality_reducers.PrefuseForceDirectedMDS;
import org.mitre.affinity.algorithms.distance_functions.DistanceFunction;
import org.mitre.affinity.algorithms.distance_functions.graph.AhnLinkDistanceFunction;
import org.mitre.affinity.algorithms.distance_functions.graph.NodeDistanceFunction;
import org.mitre.affinity.application.graph.AffinityApplication;
import org.mitre.affinity.controller.BasicAffinityController;
import org.mitre.affinity.io.GraphReader;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.graph.AffinityGraphModel;
import org.mitre.affinity.model.graph.GraphClusterObjectManager;
import org.mitre.affinity.model.graph.GraphObject;
import org.mitre.affinity.model.graph.GraphClusterObjectManager.ClusterObjectType;
import org.mitre.affinity.view.AffinityPane;
import org.mitre.affinity.view.dialog.MultiTaskLoadProgressDialog;
import org.mitre.affinity.view.dialog.StackTraceDialog;
import org.mitre.affinity.view.event.ClusterDistanceChangeEvent;
import org.mitre.affinity.view.event.ClusterDistanceChangeListener;
import org.mitre.affinity.view.event.SelectionChangedEvent;
import org.mitre.affinity.view.event.SelectionChangedListener;
import org.mitre.affinity.view.event.SelectionClickedEvent;
import org.mitre.affinity.view.event.SelectionClickedListener;
import org.mitre.affinity.view.graph.AffinityGraphPane;
import org.mitre.affinity.view.menu.graph.AffinityMenu_Graph;

import communityFinder.Edge;
import communityFinder.Node;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * Controller for the graph-based version of Affinity.
 * 
 * @author CBONACETO
 *
 */
public class AffinityGraphController extends BasicAffinityController<String, GraphObject> 
	implements SelectionClickedListener<String>, SelectionChangedListener<String>, ClusterDistanceChangeListener {	
	
	/** Reference to the application */
	protected AffinityApplication app;
	 
	/** Reference to the Affinity Pane */
	protected AffinityPane<String, GraphObject> affinityPane;
	
	/** Reference to the Graph Pane */
	protected AffinityGraphPane graphPane;
	
	/** Reference to the Affinity model */
	private AffinityGraphModel affinityModel;
	
	/** The Gephi format of the graph for display in the graph pane */
	protected org.gephi.graph.api.Graph gephiGraph;
	
	/** Reference to the menu */
	protected AffinityMenu_Graph menu;
	
	/**	Menu shown when the mouse is right clicked on a group of cluster objects or a cluster */
	protected Menu rightClickMenu;
	
	/** Currently selected cluster */	
	protected ClusterGroup<String> selectedCluster;
	
	/** Currently selected cluster objects */
	protected Collection<String> selectedClusterObjects;
	
	/** Whether this controller is active (e.g., listening and responding to events) */
	protected boolean active = false;
	
	protected boolean processFileOpenEvents = false;

	public AffinityGraphController(AffinityApplication app,
			AffinityPane<String, GraphObject> affinityPane,
			AffinityGraphPane graphPane,
			AffinityMenu_Graph menu,
			AffinityGraphModel affinityModel,
			boolean active) {
		super(app.getShell(), affinityPane.getCraigrogramPane(), affinityPane.getDendrogram(), null);
		this.app = app;
		this.affinityPane = affinityPane;
		this.graphPane = graphPane;
		this.affinityModel = affinityModel;
		this.menu = menu;		
		this.rightClickMenu = createRightClickMenu();
		
		//Register to receive selection clicked and changed events from the Affinity pane so we can keep the 
		//Affinity pane and Graph pane synched up
		affinityPane.addSelectionClickedListener(this);
		affinityPane.addSelectionChangedListener(this);		
		graphPane.addSelectionClickedListener(this);
		graphPane.addSelectionChangedListener(this);
		
		//Register to receive cluster distance changed events so we can keep the Affinity pane and Graph pane synched up
		affinityPane.addClusterDistanceChangeListener(this);
		graphPane.addClusterDistanceChangeListener(this);
		
		setControllerActive(active);
	}	
	
	protected Menu createRightClickMenu() {
		Menu menu = new Menu(app.getShell());
		
		// Generate basic menu items
		MenuItem item = new MenuItem (menu, SWT.NONE);				
		item.setText("Open items in new tab");
		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//TODO: Open selected cluster or cluster objects in a new tab
				if(selectedClusterObjects != null && !selectedClusterObjects.isEmpty() && 
						selectedClusterObjects.size() > 1) {
					app.createNewTab(createSubGraph(selectedClusterObjects));
				}
			}
		});
		
		return menu;
	}
	
	/** Create a new sub-graph from the given group of nodes or edges */
	protected UndirectedSparseGraph<Node, Edge> createSubGraph(Collection<String> objectIds) {
		UndirectedSparseGraph<Node, Edge> subGraph = new UndirectedSparseGraph<Node, Edge>();		
		GraphClusterObjectManager clusterObjectManager = affinityModel.getClusterObjectManager();
		ClusterObjectType objectType = clusterObjectManager.getClusterObjectType();
		ArrayList<Node> nodes = null;
		if(objectType == ClusterObjectType.Nodes) {
			nodes = new ArrayList<Node>(objectIds.size());
		}
		
		for(String objectId : objectIds) {
			GraphObject object = clusterObjectManager.getClusterObject(objectId);
			if(object != null) {
				if(objectType == ClusterObjectType.Nodes) {
					//Add node
					Node node = object.getNode();
					nodes.add(node);
					subGraph.addVertex(node);
				} else {
					//Add edge and nodes edge connects
					Edge edge = object.getEdge();
					subGraph.addVertex(edge.getSourceNode());
					subGraph.addVertex(edge.getDestNode());
					subGraph.addEdge(edge, edge.getSourceNode(), edge.getDestNode());
				}			
			}
		}	
		
		if(objectType == ClusterObjectType.Nodes) {
			//Add the common neighbor nodes
			Set<Node> allNodes = new HashSet<Node>(nodes); 
			NodeDistanceFunction distanceFun = new NodeDistanceFunction(1);
			Set<Node> commonNeighbors = distanceFun.getCommonNeighbors(nodes, clusterObjectManager.getGraph());
			if(commonNeighbors != null && !commonNeighbors.isEmpty()) {				
				for(Node node : commonNeighbors) {
					subGraph.addVertex(node);
					allNodes.add(node);
					//allNodes.add(node.getId());
				}
			}
			
			//Add edges connecting nodes and common neighbor nodes
			Collection<String> edges = clusterObjectManager.getEdgesConnectingNodes(allNodes);
			if(edges != null && !edges.isEmpty()) {
				for(String edgeId : edges) {
					Edge edge = clusterObjectManager.getEdge(edgeId);
					if(edge != null) {
						subGraph.addEdge(edge, edge.getSourceNode(), edge.getDestNode());
					}
				}
			}
		}
		
		return subGraph;
	}
	
	public boolean isControllerActive() {
		return active;
	}
	
	public void setControllerActive(boolean active) {
		if(active !=  this.active) {
			this.active = active;
			if(menu != null) {
				if(active) {
					menu.addMenuItemListener(this);
					menu.addMenuListener(this);
				} else {
					menu.removeMenuItemListener(this);
					menu.removeMenuListener(this);
				}
			}
		}
	}

	@Override
	public void findAndSelectClusterObject(String identifier) {
		String objectID = null;				
		if(affinityModel.getClusterObjectManager() != null) {
			objectID = affinityModel.getClusterObjectManager().findClusterObject(identifier);
		}		
		if(objectID != null) {			
			/*dendrogram.setSelectedClusterObject(objectID);
			craigrogram.setSelectedClusterObject(objectID);
			dendrogram.redraw();
			craigrogram.redraw();*/
			selectionChanged(new SelectionChangedEvent<String>(null, Collections.singleton(objectID), 
					null));
		}
	}

	@Override
	protected boolean processMenuItemSelectedEvent(MenuItem menuItem,
			Integer menuItemID, Integer parentMenuID) {
		if(menuItemID != null) {			
			if(menuItemID == AffinityMenu_Graph.FILE_OPEN_ITEM) {
				if(processFileOpenEvents) {
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
				}
				return true;
			} else if(menuItemID == AffinityMenu_Graph.CLUSTER_ON_NODES_ITEM) {
				//Cluster on Nodes
				if(affinityModel.getClusterObjectManager().getClusterObjectType() != ClusterObjectType.Nodes) {
					affinityPane.setSearchHint("Node Name");
					affinityModel.getClusterObjectManager().setClusterObjectType(ClusterObjectType.Nodes);
					this.menu.setCurrentClusterObjectType(ClusterObjectType.Nodes);
					generateClustersAndPositionGrid();
				}
				return true;
			} else if(menuItemID == AffinityMenu_Graph.CLUSTER_ON_LINKS_ITEM) {
				//Cluster on Links
				if(affinityModel.getClusterObjectManager().getClusterObjectType() != ClusterObjectType.Links) {
					affinityPane.setSearchHint("Link Name");
					affinityModel.getClusterObjectManager().setClusterObjectType(ClusterObjectType.Links);
					this.menu.setCurrentClusterObjectType(ClusterObjectType.Links);
					generateClustersAndPositionGrid();
				}				
				return true;
			} else if(menuItemID == AffinityMenu_Graph.VIEW_OPTIMAL_COMMUNITIES_ITEM) {
				//Filter clusters based on the optimal community size
				if(affinityPane != null) {
					affinityPane.setClusterDistances(0, affinityModel.getBestCommunityClusterDistance());
				}
			} else {			
				return super.processMenuItemSelectedEvent(menuItem, menuItemID, parentMenuID);
			}
		}
		return false;
	}
	
	@Override
	protected boolean processMenuShownEvent(Menu menu, Integer menuID, Integer parentMenuID) {
		if(this.menu != null && menuID != null) {
			if(menuID == AffinityMenu_Graph.CLUSTER_ON_MENU) {
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
		progressDlg.open();
		progressDlg.setMinimum(0);
		progressDlg.setMaximum(100);						
		
		try {
			//Load the graph from the file system
			progressDlg.setCurrentTaskNote("Loading Graph File");
			if(graphPane != null) {
				gephiGraph = GraphReader.openGraphFile(new File(graphFilename), 
						graphPane.getGraphView().getWorkspace(), progressDlg);
			} else {
				gephiGraph = GraphReader.openGraphFile(new File(graphFilename), progressDlg);
			}			
			UndirectedSparseGraph<Node, Edge> g = GraphReader.convertGephiGraph(gephiGraph);
			//UndirectedSparseGraph<Node, Edge> g = GraphReader.readGraph(graphFilename);			
			affinityModel.getClusterObjectManager().setGraph(g);
			progressDlg.setNumTasksComplete(1);
			
			//System.out.println(g);
			System.out.println("Node Count: " + g.getVertexCount());
			System.out.println("Edge Count: " + g.getEdgeCount());
			
			//Generate clusters and the position grid
			if(!progressDlg.isCancelled()) {
				generateClustersAndPositionGrid(progressDlg, 1);
			} else {
				//TODO: Show message dialog
			}
			
			//Refresh the graph in the graph pane
			/*Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if(graphPane != null) {
						if(progressDlg.isCancelled()) {
							//TODO: Restore original graph if cancelled
						} 
						graphPane.getGraphView().workspaceChanged();
						if(affinityModel.getClusterObjectManager().getClusterObjectType() == ClusterObjectType.Nodes) {
							graphPane.getGraphView().setClusters(affinityModel.getClusters());
						}
					}}
				});*/
		} catch(Exception ex) {
			//Show the stack track dialog
			if(!progressDlg.isDisposed()) {
				progressDlg.close();
			}
			StackTraceDialog stackTraceDlg = new StackTraceDialog(parentWindow.getShell(), ex);			
			stackTraceDlg.setMessage("Unable to load graph, details:");
			stackTraceDlg.open();
		}
	}
	
	public void setGraph(UndirectedSparseGraph<Node, Edge> graph) {
		final MultiTaskLoadProgressDialog progressDlg = new MultiTaskLoadProgressDialog(parentWindow.getShell(), 2);
		progressDlg.setText("Loading Graph");		
		progressDlg.open();
		progressDlg.setMinimum(0);
		progressDlg.setMaximum(100);
		
		graphPane.getGraphView().setGraph(graph);
		affinityModel.getClusterObjectManager().setGraph(graph);
		
		//System.out.println(g);
		System.out.println("Node Count: " + graph.getVertexCount());
		System.out.println("Edge Count: " + graph.getEdgeCount());
		
		//Generate clusters and the position grid
		if(!progressDlg.isCancelled()) {
			generateClustersAndPositionGrid(progressDlg, 0);
		} else {
			//TODO: Show message dialog
		}
		
		//Refresh the graph in the graph pane
		/*Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if(graphPane != null) {
					if(progressDlg.isCancelled()) {
						//TODO: Restore original graph if cancelled
					} 
					graphPane.getGraphView().workspaceChanged();
					if(affinityModel.getClusterObjectManager().getClusterObjectType() == ClusterObjectType.Nodes) {
						graphPane.getGraphView().setClusters(affinityModel.getClusters());
					}
				}}
			});*/
	}
	
	protected void generateClustersAndPositionGrid() {
		final MultiTaskLoadProgressDialog progressDlg = new MultiTaskLoadProgressDialog(parentWindow.getShell(), 2);
		progressDlg.open();			
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
		generateClustersAndPositionGrid(progressDlg, 0);
	}

	protected void generateClustersAndPositionGrid(final MultiTaskLoadProgressDialog progressDlg, final int numPreviousTasks) {		
		if(affinityModel.getClusterObjectManager().getGraph() != null) {
			SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {
				@Override
				protected Object doInBackground() throws Exception {										
					try {
						try {
							//Get the nodes or edges
							final Collection<GraphObject> graphObjects = affinityModel.getClusterObjectManager().getClusterObjects();
							final ArrayList<String> objectIDs = affinityModel.getClusterObjectManager().getClusterObjectIDs();

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
							if(progressDlg == null || !progressDlg.isCancelled()) {
								affinityModel.generateClusters(objectIDs, 
										distanceFun, new HierarchicalClusterer<String>(new CompleteLinker<String>()), 
										progressDlg);
								progressDlg.setNumTasksComplete(numPreviousTasks + 1);
							}

							//Run the MDS algorithm to generate 2D points
							if(progressDlg == null || !progressDlg.isCancelled()) {
								progressDlg.setCurrentTaskNote("Computing 2D layout");
								PrefuseForceDirectedMDS<String> mds = new PrefuseForceDirectedMDS<String>();			
								affinityModel.getClusters().getDistanceGrid().rescale(0, 1000);
								mds.setObjectIDs(objectIDs);
								affinityModel.generatePositionGrid(objectIDs, mds, progressDlg);		
								progressDlg.setNumTasksComplete(numPreviousTasks + 2);
								System.out.println(affinityModel.getClusters());
							}

							if(progressDlg == null || !progressDlg.isCancelled()) {
								Display.getDefault().asyncExec(new Runnable() {
									public void run() {
										affinityPane.setClusterObjectsAndClusters(objectIDs, graphObjects, 
												affinityModel.getClusters(), affinityModel.getPositionGrid());
										graphPane.setClusterObjectsAndClusters(objectIDs, graphObjects, 
												affinityModel.getClusters(), affinityModel.getPositionGrid());

										//Add a cluster distance tick mark at the optimal cluster distance community size
										if(affinityModel.getClusterObjectManager().getClusterObjectType() != ClusterObjectType.Nodes) {
											affinityPane.addClusterDistanceTickLocation(affinityModel.getBestCommunityClusterDistance(), true);
											graphPane.addClusterDistanceTickLocation(affinityModel.getBestCommunityClusterDistance(), true);
										}
										
										//Refresh the graph pane
										if(graphPane != null) {
											graphPane.getGraphView().workspaceChanged();
											if(affinityModel.getClusterObjectManager().getClusterObjectType() == ClusterObjectType.Nodes) {
												graphPane.getGraphView().setClusters(affinityModel.getClusters());
											}
										}										

										if(!progressDlg.isDisposed()) {
											progressDlg.close();
										}
									}
								});								
							} else {
								//TODO: Show cancelled message dialog, restore original graph if cancelled

							}
						} catch(final Exception ex) {
							//Show the stack track dialog	
							Display.getDefault().asyncExec(new Runnable() {
								public void run() {
									if(!progressDlg.isDisposed()) {
										progressDlg.close();
									}
									StackTraceDialog stackTraceDlg = new StackTraceDialog(parentWindow.getShell(), ex);			
									stackTraceDlg.setMessage("Unable to create clusters, details:");
									stackTraceDlg.open();		
								}
							});
						}
					} catch(Exception ex) {ex.printStackTrace();}
					return null;
				}				
			};
			worker.execute();			
		}
	}

	@Override
	public void selectionClicked(SelectionClickedEvent<String> event) {
		//Show a right-click menu to open sub-graph in a new tab
		if(event.getButton() == SelectionClickedEvent.RIGHT_BUTTON || 
				 (event.button==SelectionClickedEvent.LEFT_BUTTON && event.controlDown)) {
			selectedClusterObjects = getSelectedClusterObjects(event);
			//System.out.println("selection right-clicked");			
			if(selectedClusterObjects != null && selectedClusterObjects.size() > 1) { // Handles the selection of a group of cluster objects
				// Generate cluster around selected cluster objects
				//selectedClusterObjects = event.selectedClusterObjects;
				//selectedCluster = new ClusterGroup<String>();
				//selectedCluster.addClusterObjects(selectedClusterObjects);

				//Display the menu				
				rightClickMenu.setVisible(true);
			}
			//} else if(event.selectedClusters != null && event.selectedClusters.size() == 1) { // Handles the selection of a cluster
			//	// Get the selected cluster (and cluster objects)
			//	selectedCluster = event.selectedClusters.iterator().next();
			//	selectedClusterObjects = selectedCluster.getObjectIDs();

			//	//Display the menu
			//	rightClickMenu.setVisible(true);
			//}
		}
	}

	@Override
	public void selectionChanged(SelectionChangedEvent<String> event) {
		if(event.getSource() == null) {
			//Selection changed, update the Affinity Pane and Graph Pane
			graphPane.selectionChanged(event);
			if(graphPane != null) {
				//Update the set of selected nodes and edges in the graph pane
				updateGraphSelection(event);
			}
			affinityPane.selectionChanged(event);
		} else {
			if(event.getSource() == affinityPane.getCraigrogram() ||
					event.getSource() == affinityPane.getDendrogram()) {
				//Selection changed in the in the Affinity Pane, update the Graph Pane
				graphPane.selectionChanged(event);	
				if(graphPane != null) {
					//Update the set of selected nodes and edges in the graph pane
					updateGraphSelection(event);
				}
			}		
			else {
				//Selection changed in the Graph Pane, update the Affinity Pane
				affinityPane.selectionChanged(event);
			}		
		}
	}
	
	protected Set<String> getSelectedClusterObjects(org.mitre.affinity.view.event.SelectionEvent<String> event) {
		HashSet<String> selectedObjects = new HashSet<String>();
		if(event.getSelectedClusterObjects() != null) {
			for(String objectId : event.getSelectedClusterObjects()) {
				selectedObjects.add(objectId);
			}
		}
		if(event.getSelectedClusters() != null) {
			for(ClusterGroup<String> cluster : event.getSelectedClusters()) {
				selectedObjects.addAll(cluster.getObjectIDs());
			}
		}
		return selectedObjects;
	}
	
	protected void updateGraphSelection(org.mitre.affinity.view.event.SelectionEvent<String> event) {
		if(event.getSelectedClusterObjects() != null) {
			ClusterObjectType objectType = affinityModel.getClusterObjectManager().getClusterObjectType();
			Set<String> selectedObjects = getSelectedClusterObjects(event);
			if(selectedObjects != null && !selectedObjects.isEmpty()) {
				ArrayList<Node> nodes = null;
				if(objectType == ClusterObjectType.Nodes) {
					nodes = new ArrayList<Node>(selectedObjects.size());
					for(String objectId : selectedObjects) {
						Node node = affinityModel.getClusterObjectManager().getNode(objectId);
						if(node != null) {
							nodes.add(node);
						}	
					}
				}
				if(objectType == ClusterObjectType.Links) {				
					//Highlight the edges and the nodes connected by the edges
					graphPane.getGraphView().setSelectedEdges(selectedObjects);
					//TODO: Also highlight the nodes
				} else {
					//Highlight the nodes and the edges connecting the nodes and the edges connecting the nodes to common neighbor nodes
					graphPane.getGraphView().setSelectedNodes(selectedObjects);

					//System.out.println(affinityModel.getClusterObjectManager().getEdgesConnectingNodes(selectedObjects));
					HashSet<Node> nodesAndCommonNeighbors = new HashSet<Node>(nodes);
					NodeDistanceFunction distanceFun = new NodeDistanceFunction(1);
					nodesAndCommonNeighbors.addAll(distanceFun.getCommonNeighbors(nodes, 
							affinityModel.getClusterObjectManager().getGraph()));

					//System.out.println(affinityModel.getClusterObjectManager().getEdgesConnectingNodes(nodesAndCommonNeighbors));
					graphPane.getGraphView().setSelectedEdges(
							affinityModel.getClusterObjectManager().getEdgesConnectingNodes(nodesAndCommonNeighbors));
				}	
			} else {
				//Unselect everything in the graph
				graphPane.getGraphView().unselectAllNodesAndEdges();
			}
		}
	}

	@Override
	public void clusterDistanceChanged(ClusterDistanceChangeEvent event) {
		if(event.getSource() == null) {
			//Cluster distances changed, update the Affinity Pane and Graph Pane
			graphPane.setClusterDistances(event.getNewMinDistance(), event.getNewMaxDistance());
			affinityPane.setClusterDistances(event.getNewMinDistance(), event.getNewMaxDistance());
		} else {
			if(event.getSource() == affinityPane.getSlider().getClusterDistanceSliderPane()) {
				//Cluster distances changed in the in the Affinity Pane, update the Graph Pane
				graphPane.setClusterDistances(event.getNewMinDistance(), event.getNewMaxDistance());
			}		
			else {
				//Cluster distances changed in the Graph Pane, update the Affinity Pane
				affinityPane.setClusterDistances(event.getNewMinDistance(), event.getNewMaxDistance());
			}		
		}
	}
}