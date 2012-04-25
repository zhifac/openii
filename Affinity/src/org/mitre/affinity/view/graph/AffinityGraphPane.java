package org.mitre.affinity.view.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.mitre.affinity.AffinityConstants;
import org.mitre.affinity.model.IClusterObjectManager;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.model.graph.GraphClusterObjectManager;
import org.mitre.affinity.model.graph.GraphObject;
import org.mitre.affinity.view.AbstractAffinityPane;
import org.mitre.affinity.view.dendrogram.DendrogramCanvas;
import org.mitre.affinity.view.dendrogram.DendrogramClusterObjectGUI;
import org.mitre.affinity.view.event.SelectionChangedEvent;

/**
 * A pane with a linked Graph and Dendrogram and a cluster distance slider.
 * 
 * @author CBONACETO
 *
 */
public class AffinityGraphPane extends AbstractAffinityPane<String, GraphObject> {
	
	/** Pane with the Graph (left cluster view) */	
	protected SWTGephiGraphViewPane graph;
	
	/** Pane with the Dendrogram (right cluster view) */	
	protected DendrogramCanvas<String, GraphObject> dendrogram;	
	
	protected GraphClusterObjectManager graphClusterObjectManager;
	
	/**
	 * Create a blank AffinityGraphPane.
	 * 
	 * @param parent
	 * @param style
	 * @param searchHint
	 * @param showingSchemas
	 */
	public AffinityGraphPane(final Composite parent, int style, GraphClusterObjectManager clusterObjectManager, String searchHint) {
		super(parent, style, clusterObjectManager, searchHint);
		this.graphClusterObjectManager = clusterObjectManager;
	}		
	
	/**
	 * Creates an AffinityGraphPane using the given cluster objects, clusters, and position grid.
	 *
	 * @param parent
	 * @param style
	 * @param clusterObjectManager
	 * @param objectIDs
	 * @param clusterObjects
	 * @param clusters
	 * @param pg
	 * @param searchHint
	 */
	public AffinityGraphPane(final Composite parent, int style, IClusterObjectManager<String, GraphObject> clusterObjectManager,
			ArrayList<String> objectIDs, List<GraphObject> clusterObjects, ClustersContainer<String> clusters, 
			PositionGrid<String> pg, String searchHint) {
		super(parent, style, clusterObjectManager, objectIDs, clusterObjects, clusters, pg, searchHint);
	}	
	
	/** Note: This currently only populates the Dendrogram. The method openGraphFile must be called to populate the graph. */
	@Override
	protected void setClusterObjectsAndClustersForClusterViews(
			ArrayList<String> objectIDs, Collection<GraphObject> clusterObjects,
			ClustersContainer<String> clusters, PositionGrid<String> pg) {
		final List<DendrogramClusterObjectGUI<String, GraphObject>> dendrogramClusterObjectGUIs = new ArrayList<DendrogramClusterObjectGUI<String, GraphObject>>();
		clusterObjectsMap = new HashMap<String, GraphObject>();
		if(clusterObjects != null && !clusterObjects.isEmpty() && objectIDs != null && !objectIDs.isEmpty()) {
			int i = 0;
			for(GraphObject clusterObject : clusterObjects) {
				String objectID = objectIDs.get(i);
				String objectName = clusterObjectManager.getClusterObjectName(objectID);
				clusterObjectsMap.put(objectID, clusterObject);
				DendrogramClusterObjectGUI<String, GraphObject> dendrogramClusterObjectGUI = new DendrogramClusterObjectGUI<String, GraphObject>(objectID, clusterObject);
				dendrogramClusterObjectGUI.setLabel(objectName);
				dendrogramClusterObjectGUIs.add(dendrogramClusterObjectGUI);
				i++;
			}
		}
		dendrogram.setClusterObjectsAndClusters(dendrogramClusterObjectGUIs, clusters);
		dendrogram.redraw();
	}
	
	public void openGraphFile(File file) throws FileNotFoundException {
		graph.openGraphFile(file);
	}	

	public SWTGephiGraphViewPane getGraphPane() {
		return graph;
	}
	
	public GephiGraphView getGraphView() {
		return graph.getGraphView();
	}

	public DendrogramCanvas<String, GraphObject> getDendrogram() {
		return dendrogram;
	}		

	/** Create the Graph */
	@Override
	protected SWTGephiGraphViewPane createLeftClusterView(Composite parent, Color background) {
		graph = new SWTGephiGraphViewPane(parent, SWT.NONE);
		graph.setBackground(background);
		return graph;
	}

	/** Create the Dendrogram */
	@Override
	protected DendrogramCanvas<String, GraphObject> createRightClusterView(Composite parent, Color background) {
		dendrogram = new DendrogramCanvas<String, GraphObject>(parent, SWT.DOUBLE_BUFFERED);		
		dendrogram.setFont(AffinityConstants.getFont(AffinityConstants.NORMAL_FONT));
		dendrogram.setSelectedFont(AffinityConstants.getFont(AffinityConstants.NORMAL_BOLD_FONT));
		dendrogram.setBackground(background);
		return dendrogram;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent<String> event) {
		super.selectionChanged(event);		
		//Update the set of selected nodes and edges in the Graph pane
		/*HashSet<String> selectedObjects = new HashSet<String>();
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
		ClusterObjectType objectType = graphClusterObjectManager.getClusterObjectType();			
		if(objectType == ClusterObjectType.Links) {				
			//Highlight the edges and the nodes connected by the edges
			graph.getGraphView().setSelectedEdges(selectedObjects);
			//TODO: Also highlight the nodes
		} else {
			//Highlight the nodes and the edges connecting the nodes
			graph.getGraphView().setSelectedNodes(selectedObjects);
			//System.out.println(affinityModel.getClusterObjectManager().getEdgesConnectingNodes(selectedObjects));
			graph.getGraphView().setSelectedEdges(graphClusterObjectManager.getEdgesConnectingNodes(selectedObjects));
		}*/
	}
}