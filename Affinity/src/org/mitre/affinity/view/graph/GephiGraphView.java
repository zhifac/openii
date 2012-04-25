package org.mitre.affinity.view.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.HierarchicalUndirectedGraph;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.io.importer.api.Report;
import org.gephi.io.importer.spi.SpigotImporter;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.ProcessingTarget;
import org.gephi.preview.api.RenderTarget;
import org.gephi.preview.types.DependantColor;
import org.gephi.preview.types.DependantOriginalColor;
import org.gephi.preview.types.EdgeColor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.utils.longtask.spi.LongTask;
import org.gephi.utils.progress.ProgressTicket;
import org.mitre.affinity.AffinityConstants;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.view.swt.SWTUtils;

import org.openide.util.Lookup;

import communityFinder.Edge;
import communityFinder.Node;

import edu.uci.ics.jung.graph.Graph;

import processing.core.PApplet;

/**
 * IGraphClusterView implementation that uses Gephi and Processing to render a graph.
 * 
 * @author CBONACETO
 *
 */
public class GephiGraphView extends JPanel {
	
	private static final long serialVersionUID = -4046213988657162985L;
	
	/** The project controller */
	protected static ProjectController pc;
	
	/** The graph controller */
	protected static GraphController gc;
		
	/** The workspace for this graph panel */
	protected Workspace workspace;
	
	/** The preview controller for this graph panel */
	protected PreviewController previewController;
	
	/** The processing target where the preview is rendered */
	protected ProcessingTarget target;
	
	/** Nodes marked as selected */
	protected Collection<String> userSelectedNodes;
	
	/** Nodes currently rendered as selected. May include both "meta" (cluster) nodes and single nodes. */
	protected List<SelectedNode> selectedNodes;
	
	/** Currently selected edges */
	protected List<org.gephi.graph.api.Edge> selectedEdges;
	
	/** Currently selected node cluster */
	protected ClusterGroup<String> selectedCluster;
	
	/** The cluster type */
	
	/** The clusters */
	protected ClustersContainer<String> clusters;

	/** Maps clusters to their "meta" nodes in the graph */
	protected HashMap<ClusterGroup<String>, ClusterNode> clustersToNodes;
	
	/** Whether to group nodes by clusters and collapse them. If false, we always display all nodes. */
	protected boolean showClusters = true;
	
	/** The nodes for each cluster at each step  */
	//protected List<List<ClusterNode>> clusterNodes;
	
	/** The current min cluster distance being displayed */
	protected double minClusterDistance = 0;
	
	/** The current max cluster distance being displayed */
	protected double maxClusterDistance = 1;
	
	/** Standard node size */
	protected float nodeSize = 4.f;
	
	/** Standard selected node size */
	protected float selectedNodeSize = nodeSize * 1.5f;
	
	/** Node/edge label font */
	protected Font labelFont = AffinityConstants.getFont_AWT(AffinityConstants.LARGE_FONT);	
	
	/** Node/edge label color */
	protected Color labelColor = AffinityConstants.COLOR_CLUSTER_OBJECT_LABEL_AWT;
	
	/** Node border color */
	protected Color nodeBorderColor = AffinityConstants.COLOR_CLUSTER_OBJECT_LINE_AWT;
	
	/** Selected node border color */
	protected Color selectedNodeBorderColor = AffinityConstants.COLOR_CLUSTER_OBJECT_HIGHLIGHT_LINE_AWT;
	
	/** Node fill color */
	protected Color nodeFillColor = AffinityConstants.COLOR_CLUSTER_OBJECT_FILL_AWT;
	
	/** Selected node fill color */
	protected Color selectedNodeFillColor = AffinityConstants.COLOR_CLUSTER_OBJECT_HIGHLIGHT_FILL_AWT;
	
	/** Edge size */
	protected float edgeSize = 1.f;
	
	/** Selected edge size */
	protected float selectedEdgeSize = 2.f;
	
	/** Edge color */
	protected Color edgeColor = Color.LIGHT_GRAY;
	
	/** Selected edge color */
	protected Color selectedEdgeColor= AffinityConstants.COLOR_HIGHLIGHT_AWT;	

	public GephiGraphView() {
		super(new BorderLayout());
		this.selectedNodes = new LinkedList<SelectedNode>();
		this.selectedEdges = new LinkedList<org.gephi.graph.api.Edge>();
		this.clustersToNodes = new HashMap<ClusterGroup<String>, ClusterNode>();
		
		if(pc == null) {
			//Initialize project controller
			pc = Lookup.getDefault().lookup(ProjectController.class);
			pc.newProject();
			gc = Lookup.getDefault().lookup(GraphController.class);
		}
		//pc.newProject();
		
		//Create the workspace
		workspace = pc.newWorkspace(pc.getCurrentProject());		
		pc.openWorkspace(workspace);
		//workspace = projectController.getCurrentWorkspace();
		
		//Configure the preview controller
		previewController = Lookup.getDefault().lookup(PreviewController.class); 
		//previewController = new PreviewControllerImpl();
		//workspace.add(previewController);		
		//System.out.println("preview controller: " + previewController);
		PreviewModel previewModel = previewController.getModel(workspace);
		previewModel.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
		previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_COLOR, new DependantOriginalColor(labelColor));	
		previewModel.getProperties().putValue(PreviewProperty.NODE_BORDER_COLOR, new DependantColor(nodeBorderColor));
		previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT, labelFont);
		previewModel.getProperties().putValue(PreviewProperty.NODE_OPACITY, 85);
		//previewModel.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(edgeColor));
		previewModel.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(EdgeColor.Mode.ORIGINAL));
		previewModel.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.FALSE);
		previewModel.getProperties().putValue(PreviewProperty.EDGE_OPACITY, 85);
		previewModel.getProperties().putValue(PreviewProperty.EDGE_RADIUS, 0f); //previously 5
		previewModel.getProperties().putValue(PreviewProperty.BACKGROUND_COLOR, Color.WHITE);
		previewController.refreshPreview(workspace);
		
		//Create the Processing target and add the PApplet to the panel
		target = (ProcessingTarget)previewController.getRenderTarget(RenderTarget.PROCESSING_TARGET, workspace);
		PApplet applet = target.getApplet();
		applet.init();
		previewController.render(target, workspace);
		target.refresh();
		target.resetZoom();
		add(applet);
		
		//Add a resize listener
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				redraw();
			}
			@Override
			public void componentShown(ComponentEvent e) {
				redraw();
			}
		});
	}	
	
	/**
	 * Make the workspace for this graph the current workspace.
	 */
	public void makeCurrent() {
		if(workspace != null) {
			pc.openWorkspace(workspace);
		}
	}
	
	/**
	 * @return
	 */
	public Workspace getWorkspace() {
		return workspace;
	}	
	
	/**
	 * @param file
	 * @throws FileNotFoundException
	 */
	public void openGraphFile(File file) throws FileNotFoundException {
		//Clear workspace
		selectedNodes.clear();
		selectedEdges.clear();
		clusters = null;
		clustersToNodes.clear();
		pc.cleanWorkspace(workspace);		
		gc.getModel(workspace).clear();
		
		//Open graph file
		ImportController importController = Lookup.getDefault().lookup(ImportController.class);
		Container container = importController.importFile(file);
		container.getLoader().setEdgeDefault(EdgeDefault.UNDIRECTED); //Make graph undirected

		//Append imported data to GraphAPI
		importController.process(container, new DefaultProcessor(), workspace);		
		
		//Set the node and edge colors and re-render the graph
		workspaceChanged();
	}	
	
	/** Call this if the graph in the workspace has been modified */
	public void workspaceChanged() {
		org.gephi.graph.api.Graph graph = gc.getModel(workspace).getGraph();
		
		//Set node colors
		if(graph.getNodeCount() > 0) {
			//nodes = new HashMap<String, org.gephi.graph.api.Node>(graph.getNodeCount());		
			nodeSize = 0.f;
			for(org.gephi.graph.api.Node node : graph.getNodes()) {
				//nodes.put(node.getNodeData().getId(), node);
				node.getNodeData().setColor(nodeFillColor.getRed()/255.f, 
						nodeFillColor.getGreen()/255.f, nodeFillColor.getBlue()/255.f);
				if(nodeSize == 0.f) {
					nodeSize = node.getNodeData().getSize();
					selectedNodeSize = nodeSize * 1.5f;
				}
			}
		}

		//Set edge colors
		if(graph.getEdgeCount() > 0) {
			//this.edges = new HashMap<String, org.gephi.graph.api.Edge>(graph.getEdgeCount());
			edgeSize = 0.f;
			org.gephi.graph.api.Edge[] edges = graph.getEdges().toArray();
			if(edges != null && edges.length > 0) {
				for(org.gephi.graph.api.Edge edge : edges) {
					//this.edges.put(edge.getEdgeData().getId(), edge);
					edge.getEdgeData().setColor(edgeColor.getRed()/255.f, 
							edgeColor.getGreen()/255.f, edgeColor.getBlue()/255.f);
					if(edgeSize == 0.f) {				
						edgeSize = edge.getEdgeData().getSize();
						selectedEdgeSize = edgeSize * 2f;
					}	
				}
			}
		}

		//Re-render the graph
		refreshGraph();
	}
	
	/** Call this to redraw the graph if the workspace has been modified */
	protected void refreshGraph() {
		previewController.refreshPreview(workspace);
		previewController.render(target, workspace);
		target.refresh();
		target.resetZoom();
		target.refresh();
	}

	/**
	 * @param graph
	 */
	public void setGraph(Graph<Node, Edge> graph) {
		//Clear workspace
		selectedNodes.clear();
		selectedEdges.clear();
		selectedCluster = null;
		clusters = null;
		clustersToNodes.clear();
		pc.cleanWorkspace(workspace);
		GraphModel graphModel = gc.getModel(workspace);
		graphModel.clear();
		
		//Import the Jung graph
		ImportController importController = Lookup.getDefault().lookup(ImportController.class);
		JungGraphSpigot importer = new JungGraphSpigot(graph);
		Container container = importController.importSpigot(importer);
		container.getLoader().setEdgeDefault(EdgeDefault.UNDIRECTED); //Make graph undirected

		//Append imported data to GraphAPI
		importController.process(container, new DefaultProcessor(), workspace);		
		
		//Set the node and edge colors and re-render the graph
		workspaceChanged();
	}

	/**
	 * Set the node clusters.
	 * 
	 * @param clusters the clusters
	 */
	public void setClusters(ClustersContainer<String> clusters) {
		this.clusters = clusters;
		clustersChanged();
	}	
	

	public boolean isShowClusters() {
		return showClusters;
	}

	public void setShowClusters(boolean showClusters) {
		if(showClusters != this.showClusters) {
			this.showClusters = showClusters;
			setMinMaxClusterDistances(minClusterDistance, maxClusterDistance);
		}
	}

	/**
	 * @param minClusterDistance
	 */
	public void setMinClusterDistance(double minClusterDistance) {
		//Does nothing yet		
		this.minClusterDistance = minClusterDistance;
	}
	
	/**
	 * @param maxClusterDistance
	 */
	public void setMaxClusterDistance(double maxClusterDistance) {
		setMinMaxClusterDistances(minClusterDistance, maxClusterDistance);		
	}
	
	/**
	 * @param minClusterDistance
	 * @param maxClusterDistance
	 */
	public void setMinMaxClusterDistances(double minClusterDistance, double maxClusterDistance) {
		this.minClusterDistance = minClusterDistance;
		this.maxClusterDistance = maxClusterDistance;
		GraphModel graphModel = gc.getModel(workspace);
		org.gephi.graph.api.HierarchicalUndirectedGraph graph = graphModel.getHierarchicalUndirectedGraph();
		if(!clustersToNodes.isEmpty()) {
			for(ClusterNode clusterNode : clustersToNodes.values()) {
				graph.ungroupNodes(clusterNode.clusterNode);
			}
		}
		clustersToNodes.clear();
		if(maxClusterDistance == 0 || !showClusters) {
			//Show all nodes
			graph.resetViewToLeaves();
		} else {
			//Get the "top level" clusters for the current max cluster distance (top level clusters are clusters that 
			//are not nested inside another cluster).
			List<ClusterGroup<String>> topLevelClusters = getTopLevelClusters(minClusterDistance, maxClusterDistance);			
			//System.out.println("Top Level Clusters: " + topLevelClusters.size());
			//Create collapsed cluster nodes for the top level clusters
			if(topLevelClusters != null && !topLevelClusters.isEmpty()) {
				for(ClusterGroup<String> cluster : topLevelClusters) {
					org.gephi.graph.api.Node node = createClusterNode(cluster, graph);
					ClusterNode clusterNode = new ClusterNode(cluster, node, node.getNodeData().getSize());
					clustersToNodes.put(cluster, clusterNode);
				}
			}
		}
		//Update the selected nodes to render (if any)
		updateSelectedNodes(false);
		redraw();
		
		//Find the cluster step with a cluster distance between min - max cluster distance and collapse the 
		//graph at that cluster step
		/*GraphModel graphModel = gc.getModel(workspace);
		org.gephi.graph.api.HierarchicalUndirectedGraph graph = graphModel.getHierarchicalUndirectedGraph();
		//System.out.println("num levels in graph: " + graph.getHeight());
		if(clusterNodes != null) {			
			for(int clusterStep = 0; clusterStep < clusters.getNumClusterSteps(); clusterStep++) {
				for(ClusterNode node : clusterNodes.get(clusterStep)) {
					if(node.cluster.getDistance() > maxClusterDistance) {						
						setClusterStep(graph.getLevel(node.clusterNode));
						//setClusterStep((int)Math.round(maxClusterDistance * graph.getHeight()));
						return;
					}
				}
			}			
			setClusterStep(0);
		}*/
	}	
	
	/**
	 * Set the current cluster step.
	 * When the cluster step is 0, we display all individual nodes (each node is its own cluster).
	 * 
	 * @param clusterStep the cluster step
	 */
	/*public void setClusterStep(int clusterStep) {
		//System.out.println("Setting cluster step: " + clusterStep);
		if(clusterStep != this.clusterStep) {			
			this.clusterStep = clusterStep;
			GraphModel graphModel = gc.getModel(workspace);
			org.gephi.graph.api.HierarchicalUndirectedGraph graph = graphModel.getHierarchicalUndirectedGraph();
			//if(clusterStep == 0) {
				//Display all nodes
				//graph.resetViewToLeaves();
			//} else {
				//Roll up nodes by cluster at the current step
				if(clusters != null) {
					graph.resetViewToLevel(clusterStep);
				}
			//}
			redraw();
		}
	}*/
	
	/**
	 * @param minClusterDistance
	 * @param maxClusterDistance
	 * @return
	 */
	protected List<ClusterGroup<String>> getTopLevelClusters(double minClusterDistance, double maxClusterDistance) {
		if(clusters != null) {
			List<ClusterGroup<String>> filteredClusters = new LinkedList<ClusterGroup<String>>();
			for(int step = clusters.getNumClusterSteps()-1; step >=0; step--) {
				for(ClusterGroup<String> cg : clusters.getClusterStep(step)) {
					if(cg.getNumClusterObjects() > 1 && cg.getDistance() <= maxClusterDistance) {
						boolean nestedCluster = false;
						for(ClusterGroup<String> filteredCluster : filteredClusters) {
							if(filteredCluster.contains(cg)) {
								nestedCluster = true;
								break;
							}
						}
						if(!nestedCluster) {
							filteredClusters.add(cg);
						}
					}
				}
			}
			return filteredClusters;
		}
		return null;
	}
	
	/**
	 * Called when the clusters are modified.
	 */
	protected void clustersChanged() {
		GraphModel graphModel = gc.getModel(workspace);
		org.gephi.graph.api.HierarchicalUndirectedGraph graph = graphModel.getHierarchicalUndirectedGraph();
		if(!clustersToNodes.isEmpty()) {
			for(ClusterNode clusterNode : clustersToNodes.values()) {
				graph.ungroupNodes(clusterNode.clusterNode);
			}
		}
		clustersToNodes.clear();
		/*clusterNodes = null;
		 * if(clusters != null) {
			clusterNodes = new ArrayList<List<ClusterNode>>(clusters.getNumClusterSteps());
			int step = 0;
			for(ClusterStep<String> clusterStep : clusters) {				
				ArrayList<ClusterNode> nodes = new ArrayList<ClusterNode>(clusterStep.getSize());
				clusterNodes.add(nodes);
				//Create a cluster node for each cluster in the cluster step
				for(ClusterGroup<String> cg : clusterStep.getClusterGroups()) {
					//createClusterNode(cluster, graph);
					if(step == 0) {
						//At step 0, each node is its own cluster
						//We may be able to eliminate this step
						if(cg.getObjectIDs() == null || cg.getObjectIDs().size() != 1) {
							throw new IllegalArgumentException("Each cluster group at step 0 must contain exactly 1 cluster object.");
						}
						org.gephi.graph.api.Node node = graph.getNode(cg.getObjectIDs().get(0));
						if(node != null) {
							ClusterNode clusterNode = new ClusterNode(cg, createClusterNode(Collections.singleton(node), graph));
							nodes.add(clusterNode);
							clustersToNodes.put(cg, clusterNode);
						}
					}
					else if (step == 1) {
						if(cg.getObjectIDs() == null || cg.getObjectIDs().size() != 2) {
							throw new IllegalArgumentException("Each cluster group at step 1 must contain exactly 2 cluster objects.");
						}
						//Find the two cluster nodes at step 0 that were merged to create this cluster
						ArrayList<org.gephi.graph.api.Node> mergedNodes = new ArrayList<org.gephi.graph.api.Node>(2);
						for(ClusterNode currNode : clusterNodes.get(0)) {
							if(cg.contains(currNode.cluster)) {
								mergedNodes.add(currNode.clusterNode);
								if(mergedNodes.size() == 2) {
									break;
								}
							}
						}		
						if(mergedNodes.size() == 2) {
							ClusterNode clusterNode = new ClusterNode(cg, createClusterNode(mergedNodes, graph));
							nodes.add(clusterNode);
							clustersToNodes.put(cg, clusterNode);				
						} else {
							throw new IllegalArgumentException("Could not connect cluster group at step" + step + " to children");
						}
						//steps[step][cgIndex].setLabel(createLabel(cg));						
					}
					else {					
						//Find the two cluster nodes in a previous step that were merged to create this cluster
						//A cluster node at a previous step was merged to create this cluster iff it is a subset of this cluster
						int searchStep = step - 1;
						ArrayList<org.gephi.graph.api.Node> mergedNodes = new ArrayList<org.gephi.graph.api.Node>(2);
						System.out.print("At step " + step + ", Merged nodes from steps: ");
						while(mergedNodes.size() < 2 && searchStep >= 0) {
							//For each node at the previous step, determine if its children are contained by this cluster group
							for(ClusterNode currNode : clusterNodes.get(searchStep)) {
								if(graph.getParent(currNode.clusterNode) == null && cg.contains(currNode.cluster)) {
									System.out.print(searchStep + ", ");
									mergedNodes.add(currNode.clusterNode);
									if(mergedNodes.size() == 2) {
										break;
									}
								}
							}
							searchStep--;
						}
						if(mergedNodes.size() == 2) {							
							ClusterNode clusterNode = new ClusterNode(cg, createClusterNode(mergedNodes, graph));
							nodes.add(clusterNode);
							clustersToNodes.put(cg, clusterNode);	
							System.out.println("Node Level: " + graph.getLevel(clusterNode.clusterNode) + ", Distance: " + cg.getDistance());
						} else {
							throw new IllegalArgumentException("Could not connect cluster group at step" + step + " to children");
						}
					}
				}
				step++;					
			}
		}*/
		//Reset view based on current min and max cluster distances
		setMinMaxClusterDistances(minClusterDistance, maxClusterDistance);
		//graph.resetViewToLeaves();
		//redraw();
	}	
	
	/**
	 * Create a new cluster node from a cluster.
	 * 
	 * @param cluster
	 * @param graph
	 * @return
	 */
	protected org.gephi.graph.api.Node createClusterNode(ClusterGroup<String> cluster, 
			HierarchicalUndirectedGraph graph) {
		if(cluster != null && cluster.getNumClusterObjects() > 0) {
			org.gephi.graph.api.Node[] nodes = new org.gephi.graph.api.Node[cluster.getNumClusterObjects()];
			int i = 0;
			float xAvg = 0;
			float yAvg = 0;
			for(String nodeId : cluster) {
				org.gephi.graph.api.Node node = graph.getNode(nodeId);
				if(node != null) {
					xAvg += node.getNodeData().x();
					yAvg += node.getNodeData().y();
					nodes[i] = node;
					i++;
				}
			}
			//System.out.println("creating cluster node: " + nodes.length);
			return createClusterNode(nodes, graph, SWTUtils.getAwtColor(cluster.getDendroColor()), 
					xAvg/i, yAvg/i);
		}
		return null;
	}	
	
	/**
	 * Create a new cluster node from the group of nodes.
	 * 
	 * @param nodes
	 * @param graph
	 * @return
	 */
	protected org.gephi.graph.api.Node createClusterNode(Collection<org.gephi.graph.api.Node> nodes, 
			HierarchicalUndirectedGraph graph) {
		if(nodes != null && nodes.size() > 0) {
			int i = 0;
			float xAvg = 0;
			float yAvg = 0;
			for(org.gephi.graph.api.Node node : nodes) {
				if(node != null) {
					xAvg += node.getNodeData().x();
					yAvg += node.getNodeData().y();
					i++;
				}
			}
			return createClusterNode(nodes.toArray(new org.gephi.graph.api.Node[nodes.size()]), graph,
					null, xAvg/i, yAvg/i);
		}
		return null;
	}
	
	/**
	 * @param nodes
	 * @param graph
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	private org.gephi.graph.api.Node createClusterNode(org.gephi.graph.api.Node[] nodes, 
			HierarchicalUndirectedGraph graph, Color nodeColor, float xPos, float yPos) {
		org.gephi.graph.api.Node clusterNode = graph.groupNodes(nodes);
		clusterNode.getNodeData().setX(xPos);
		clusterNode.getNodeData().setY(yPos);
		//Size the node based on the number of nodes it contains
		Float size = nodeSize * ((nodes.length == 1) ? 1 : (float)Math.log(nodes.length)*2);
		clusterNode.getNodeData().setSize(size);
		//Label the node based on the number of nodes it contains
		clusterNode.getNodeData().setLabel(Integer.toString(nodes.length));
		//clusterNode.getAttributes().setValue("_size", size);
		if(nodeColor != null) {
			clusterNode.getNodeData().setColor(nodeColor.getRed()/255.f, 
					nodeColor.getGreen()/255.f, nodeColor.getBlue()/255.f);
			//clusterNode.getAttributes().setValue("_color", nodeColor);
			//System.out.println("setting cluster node color: " + nodeColor);
		}
		//Set the edge colors
		/*System.out.println("created cluster node: " + nodes.length);
		for(org.gephi.graph.api.Edge edge : graph.getEdgesAndMetaEdges(clusterNode)) {
			System.out.println(edge.getWeight());
		}*/		
		return clusterNode;
	}

	/**
	 * Redraws the graph
	 */
	public void redraw() {
		previewController.refreshPreview(workspace);
		target.refresh();
	}
	
	/**
	 * Select a "meta" node corresponding to a cluster. Any previously selected nodes are unselected.
	 * Any previously selected nodes or clusters or edges are unselected.
	 * 
	 * @param cluster
	 */
	public void setSelectedCluster(ClusterGroup<String> cluster) {
		//Unselect any previously selected edges
		unselectAllEdges();
		//unselectAllNodes();
		userSelectedNodes = null;
		selectedCluster = cluster;
		//Update the selected nodes to render
		updateSelectedNodes(true);
	}
	
	/** */
	protected void updateSelectedNodes(boolean redraw) {
		//Render any previously selected nodes as unselected
		renderNodeSelections(false, false);
		
		//Determine which cluster nodes and/or individual nodes are currently selected and render them as selected
		selectedNodes.clear();
		Set<String> remainingNodes = new HashSet<String>();
		org.gephi.graph.api.Graph graph = gc.getModel(workspace).getGraph();
		if(selectedCluster != null && selectedCluster.getNumClusterObjects() > 0) {
			//A cluster is selected
			boolean clusterNodeOrNestedClusterNode = false;
			if(!clustersToNodes.isEmpty()) {
				if(clustersToNodes.containsKey(selectedCluster)) {
					//Cluster matches a cluster node
					ClusterNode clusterNode = clustersToNodes.get(selectedCluster);
					selectedNodes.add(new SelectedNode(clusterNode.clusterNode, clusterNode.getClusterColor(),
							clusterNode.getNodeSize()));
					clusterNodeOrNestedClusterNode = true;
				} /*else {
					for(ClusterNode clusterNode : clustersToNodes.values()) {
						if(clusterNode.cluster.contains(selectedCluster)) {
							//Cluster is nested within a cluster node, so we currently do nothing
							clusterNodeOrNestedClusterNode = true;
							break;
						}
					}
				}*/
			}		
			if(!clusterNodeOrNestedClusterNode) {
				//Select the individual nodes
				remainingNodes.addAll(selectedCluster.getObjectIDs());
				/*for(String nodeId : selectedCluster) {
					org.gephi.graph.api.Node node = graph.getNode(nodeId);
					if(node != null) {
						selectedNodes.add(node);
					}
				}*/
			}
		} else if(userSelectedNodes != null && !userSelectedNodes.isEmpty()) {
			//A set of individual nodes is selected
			remainingNodes.addAll(userSelectedNodes);
		}		
		if(!remainingNodes.isEmpty()) {
			if(!clustersToNodes.isEmpty()) {
				//Determine if the nodes or subsets of the nodes are contained in any clusters
				for(ClusterNode clusterNode : clustersToNodes.values()) {
					if(clusterNode.cluster.containedBy(remainingNodes)) {
						selectedNodes.add(new SelectedNode(clusterNode.clusterNode, clusterNode.getClusterColor(),
								clusterNode.getNodeSize()));
						remainingNodes.removeAll(clusterNode.cluster.getObjectIDs());
					}
				}
			}
			for(String nodeId : remainingNodes) {
				org.gephi.graph.api.Node node = graph.getNode(nodeId);
				if(node != null) {
					selectedNodes.add(new SelectedNode(node));
				}
			}
		}
		
		//Render any selected nodes as selected
		renderNodeSelections(true, false);
		if(redraw) {
			redraw();
		}
	}
	
	/**
	 * Select a single node.  Any previously selected nodes are unselected.
	 * 
	 * @param schemaID
	 */
	public void setSelectedNode(String nodeId) {
		setSelectedNodes(Collections.singleton(nodeId));
	}
	
	/**
	 * Select a group of nodes.  Any previously selected nodes or clusters are unselected.
	 * 
	 * @param objectIDs
	 */
	public void setSelectedNodes(Collection<String> nodeIds) {		
		//Unselect any previously selected nodes or clusters
		//unselectAllNodes();
		userSelectedNodes = nodeIds;
		selectedCluster = null;
		//Update the selected nodes to render
		updateSelectedNodes(true);		
		/*unselectAllNodes();		
		org.gephi.graph.api.Graph graph = gc.getModel(workspace).getGraph();
		if(graph != null && nodeIds != null && !nodeIds.isEmpty()) {
			for(String nodeId : nodeIds) {
				org.gephi.graph.api.Node node = graph.getNode(nodeId);
				//org.gephi.graph.api.Node node = nodes.get(nodeId);
				//node.getNodeData().getModel().setSelected(true);
				//System.out.println("Fetched node: " + nodeId + ", " + node);
				if(node != null) {
					selectedNodes.add(node);
					node.getNodeData().setColor(selectedNodeFillColor.getRed()/255.f, 
							selectedNodeFillColor.getGreen()/255.f, selectedNodeFillColor.getBlue()/255.f);
					node.getNodeData().setSize(selectedNodeSize);
				}
			}
			redraw();
		}*/		
	}
	
	/**
	 * Unselect all nodes.
	 */
	public void unselectAllNodes() {
		renderNodeSelections(false, true);
		selectedNodes.clear();
	}
	
	/** Renders any currently selected nodes as either selected or unselected. */
	protected void renderNodeSelections(boolean renderAsSelected, boolean redraw) {
		if(!selectedNodes.isEmpty()) {
			for(SelectedNode node : selectedNodes) {
				//System.out.println("unselecting node: " + nodeId + ", " + node);
				if(node != null) {
					if(renderAsSelected) {
						//node.getNodeData().getModel().setSelected(true);
						node.node.getNodeData().setColor(selectedNodeFillColor.getRed()/255.f, 
								selectedNodeFillColor.getGreen()/255.f, selectedNodeFillColor.getBlue()/255.f);
						if(node.origSize != null) {
							node.node.getNodeData().setSize(node.origSize * 1.5f);	
						} else {
							node.node.getNodeData().setSize(selectedNodeSize);
						}
					} else {
						//node.getNodeData().getModel().setSelected(false);
						Color color = nodeFillColor;
						if(node.origColor != null) {
							color = node.origColor;
							//System.out.println("restored orig color: " + color);
						}
						node.node.getNodeData().setColor(color.getRed()/255.f, 
								color.getGreen()/255.f, color.getBlue()/255.f);
						if(node.origSize != null) {
							node.node.getNodeData().setSize(node.origSize);	
						} else {
							node.node.getNodeData().setSize(nodeSize);
						}
					}
				}
			}
			if(redraw) {
				redraw();	
			}
		}
	}
	
	/**
	 * Select a single edge.  Any previously selected edges are unselected.
	 * 
	 * @param schemaID
	 */
	public void setSelectedEdge(String edgeId) {
		setSelectedEdges(Collections.singleton(edgeId));
	}	
	
	/**
	 * Select a group of edges.  Any previously selected edges are unselected.
	 * 
	 * @param objectIDs
	 */
	public void setSelectedEdges(Collection<String> edgeIds) {
		//Unselect any previously selected edges
		unselectAllEdges();
		org.gephi.graph.api.Graph graph = gc.getModel(workspace).getGraph();
		if(graph != null && edgeIds != null && !edgeIds.isEmpty()) {
			for(String edgeId : edgeIds) {
				org.gephi.graph.api.Edge edge = graph.getEdge(edgeId);
				if(edge != null) {
					//System.out.println("selecting edge: " + edge);
					selectedEdges.add(edge);
					edge.getEdgeData().setColor(selectedEdgeColor.getRed()/255.f, 
							selectedEdgeColor.getGreen()/255.f, selectedEdgeColor.getBlue()/255.f);
					edge.getEdgeData().setSize(selectedEdgeSize);
				}
			}
			redraw();	
		}
	}	
	
	/**
	 * Unselect all edges.
	 */
	public void unselectAllEdges() {
		if(!selectedEdges.isEmpty()) {
			for(org.gephi.graph.api.Edge edge : selectedEdges) {
				//edge.getEdgeData().getModel().setSelected(false);
				edge.getEdgeData().setColor(edgeColor.getRed()/255.f, 
						edgeColor.getGreen()/255.f, edgeColor.getBlue()/255.f);
				edge.getEdgeData().setSize(edgeSize);
			}
			redraw();
		}
		selectedEdges.clear();
	}
	
	/**
	 * Unselect all nodes and edges.
	 */
	public void unselectAllNodesAndEdges() {
		unselectAllNodes();
		unselectAllEdges();
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("finalizing");
		//Remove the worskspace for this panel
		if(workspace != null && pc != null) {
			pc.deleteWorkspace(workspace);
		}
	}
	
	public void test() {
		//Init a project - and therefore a workspace
		ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
		pc.newProject();		
		Workspace workspace = pc.getCurrentWorkspace();

		//Import file
		ImportController importController = Lookup.getDefault().lookup(ImportController.class);
		Container container;
		try {
			//System.out.println(new File("data/graphs/voting.gexf").isFile());
			//File file = new File(getClass().getResource("/org/gephi/toolkit/demos/resources/Java.gexf").toURI());
			File file = new File("data/graphs/voting.gexf");
			container = importController.importFile(file);
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		//Append imported data to GraphAPI
		importController.process(container, new DefaultProcessor(), workspace);

		//Preview configuration
		PreviewController previewController = Lookup.getDefault().lookup(PreviewController.class);
		PreviewModel previewModel = previewController.getModel();
		previewModel.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
		previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_COLOR, new DependantOriginalColor(Color.WHITE));
		previewModel.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.FALSE);
		previewModel.getProperties().putValue(PreviewProperty.EDGE_OPACITY, 50);
		previewModel.getProperties().putValue(PreviewProperty.EDGE_RADIUS, 10f);
		previewModel.getProperties().putValue(PreviewProperty.BACKGROUND_COLOR, Color.BLACK);
		previewController.refreshPreview();

		//New Processing target, get the PApplet
		ProcessingTarget target = (ProcessingTarget)previewController.getRenderTarget(RenderTarget.PROCESSING_TARGET);
		PApplet applet = target.getApplet();
		applet.init();

		//Refresh the preview and reset the zoom
		previewController.render(target);
		target.refresh();
		target.resetZoom();

		//Add the applet to a JFrame and display
		JFrame frame = new JFrame("Test Preview");
		frame.setLayout(new BorderLayout());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(applet, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
		target.refresh();
	}

	/** Test main */
	public static void main(String[] args) {
		GephiGraphView graphPanel1 = new GephiGraphView();
		try {
			graphPanel1.openGraphFile(new File("data/graphs/voting.gexf"));
		} catch(Exception ex) {
			ex.printStackTrace();
		}		
		JFrame frame1 = new JFrame("Test Preview 1");
		frame1.setLayout(new BorderLayout());
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.add(graphPanel1);
		frame1.pack();
		frame1.setVisible(true);
		graphPanel1.setSelectedNodes(Arrays.asList("300042", "300077", "300100"));		
		graphPanel1.setSelectedEdges(Arrays.asList("1", "2", "3"));
		
		/*try {
			graphPanel1.openGraphFile(new File("data/graphs/lesmis.gml"));
		} catch(Exception ex) {
			ex.printStackTrace();
		}*/
		
		/*GephiGraphView graphPanel2 = new GephiGraphView();
		try {
			graphPanel2.openGraphFile(new File("data/graphs/karate.gml"));
		} catch(Exception ex) {
			ex.printStackTrace();
		}		
		JFrame frame2 = new JFrame("Test Preview 2");
		frame2.setLayout(new BorderLayout());
		//frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.add(graphPanel2);
		frame2.pack();
		frame2.setVisible(true);*/
	}
	
	/**
	 * Contains the cluster and "meta" node in a graph for a cluster.
	 * 
	 * @author CBONACETO
	 *
	 */
	protected static class ClusterNode {
		/** The cluster this cluster node comprises */
		protected final ClusterGroup<String> cluster;
		
		/** The "meta" node in the graph for this cluster */
		protected final org.gephi.graph.api.Node clusterNode;
		
		/** The cluster node size */
		protected final Float nodeSize;
		
		public ClusterNode(ClusterGroup<String> cluster, org.gephi.graph.api.Node clusterNode,
				Float nodeSize) {
			this.cluster = cluster;
			this.clusterNode = clusterNode;
			this.nodeSize = nodeSize;
		}

		public ClusterGroup<String> getCluster() {
			return cluster;
		}

		public org.gephi.graph.api.Node getClusterNode() {
			return clusterNode;
		}
		
		public Color getClusterColor() {
			return SWTUtils.getAwtColor(cluster.getDendroColor());
		}

		public Float getNodeSize() {
			return nodeSize;
		}		
	}
	
	protected static class SelectedNode {
		/** The node */
		protected org.gephi.graph.api.Node node;
		
		/** The original (unselected) color */
		protected Color origColor;
		
		/** The original (unselected) size */
		protected Float origSize;
		
		public SelectedNode(org.gephi.graph.api.Node node) {
			this.node = node;
		}
		
		public SelectedNode(org.gephi.graph.api.Node node,
				Color origColor, Float origSize) {
			this.node = node;
			this.origColor = origColor;
			this.origSize = origSize;
		}
	}
	
	/**
	 * Spigot importer for importing a Jung graph.
	 * 
	 * @author CBONACETO
	 *
	 */
	public static class JungGraphSpigot implements SpigotImporter, LongTask {
		 
	    private ContainerLoader container;
	    
	    private Report report;
	    
	    private ProgressTicket progressTicket;
	    
	    private boolean cancel = false;
	    
	    private Graph<Node, Edge> graph;
	    
	    //private ImportContainerImpl importContainer;	
	    
	    public JungGraphSpigot() {}
	    
	    public JungGraphSpigot(Graph<Node, Edge> graph) {
	    	this.graph = graph;
	    }
	    
	    @Override
	    public boolean execute(ContainerLoader loader) {
	    	if(graph == null) {
	    		return false;
	    	}
	    	
	    	/*if(loader instanceof ImportContainerImpl) {
	    		importContainer = (ImportContainerImpl)loader; 
	    	} else {
	    		importContainer = new ImportContainerImpl();
	    	}*/
	    	
	    	if(progressTicket != null) {
	    		progressTicket.start(graph.getVertexCount() + graph.getEdgeCount());
	    		progressTicket.progress("Importing Graph");
	    	}
	    	
	        this.container = loader;
	        this.report = new Report();
	        
	        HashMap<String, NodeDraft> nodes = new HashMap<String, NodeDraft>();
			HashMap<String, EdgeDraft> edges = new HashMap<String, EdgeDraft>();
			
			if(graph != null) {
				if(graph.getVertexCount() > 0) {
					for(Node node : graph.getVertices()) {
						NodeDraft n = container.factory().newNodeDraft(); //new NodeDraftImpl(importContainer, node.getId());
						n.setId(node.getId());
						n.setLabel(node.getValue());
						nodes.put(node.getId(), n);
					}
				}
				if(graph.getEdgeCount() > 0) {
					for(Edge edge : graph.getEdges()) {
						NodeDraft source = nodes.get(edge.getSourceNode().getId());
						NodeDraft dest = nodes.get(edge.getDestNode().getId());
						if(source != null && dest != null) {
							EdgeDraft e = container.factory().newEdgeDraft(); //new EdgeDraftImpl(importContainer, edge.getId());
							e.setId(edge.getId());
							e.setSource(source);
							e.setTarget(dest);
							edges.put(edge.getId(), e);
						}
					}
				}
			}
			
			for (NodeDraft n : nodes.values()){
				//System.out.println("Adding node: " + n.getNodeData().getLabel());
				container.addNode(n);
			}
			for (EdgeDraft e : edges.values()) {
				//System.out.println("Adding edge: " + e.getEdgeData().getId());
				container.addEdge(e);
			}
	        
	        return !cancel;
	    }
	 
	    @Override
	    public ContainerLoader getContainer() {
	        return container;
	    }
	 
	    @Override
	    public Report getReport() {
	        return report;
	    }
	 
	    @Override
	    public boolean cancel() {
	        cancel = true;
	        return true;
	    }
	 
	    @Override
	    public void setProgressTicket(ProgressTicket progressTicket) {
	        this.progressTicket = progressTicket;
	    }
	    
	    public void setGraph(Graph<Node, Edge> graph) {
	    	this.graph = graph;
	    }
	}
}