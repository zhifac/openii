package org.mitre.affinity.view.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.mitre.affinity.controller.IAffinityController;
import org.mitre.affinity.model.IClusterObjectManager;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.model.graph.GraphClusterObjectManager;
import org.mitre.affinity.model.graph.GraphObject;
import org.mitre.affinity.view.AffinityPane;


/**
 * Contains a linked AffinityPane and AffinityGraphPane in separate tabs.
 * At present, keeps the cluster distance sliders and Dendrograms in each pane synced.
 * 
 * @author CBONACETO
 *
 */
public class AffinityGraphTabbedPane {
	
	/** The graph file */
	protected File graphFile;
	
	/** The tab folder containing the tabs */
	protected CTabFolder tabFolder;

	/** The AffinityPane (contains a Craigrogram and Dendrogram) */
	protected AffinityPane<String, GraphObject> affinityPane;
	
	/** The AffinityGraphPane (contains a Graph and Dendrogram) */
	protected AffinityGraphPane affinityGraphPane;
	
	public AffinityGraphTabbedPane(final Composite parent, int tabStyle, 
			GraphClusterObjectManager clusterObjectManager, String searchHint) {
		//Create the tab folder 
		tabFolder = new CTabFolder(parent, tabStyle); //SWT.TOP | SWT.MULTI

		//Create the AffinityPane and AffinityGraphPane
		this.affinityPane = new AffinityPane<String, GraphObject>(tabFolder, SWT.NONE,
				clusterObjectManager, searchHint);
		this.affinityGraphPane = new AffinityGraphPane(tabFolder, SWT.NONE,
				clusterObjectManager, searchHint);

		//Create the tabs
		createTabs();
	}		
	
	public AffinityGraphTabbedPane(final Composite parent, int tabStyle, IClusterObjectManager<String, GraphObject> clusterObjectManager,
			ArrayList<String> objectIDs, List<GraphObject> clusterObjects, ClustersContainer<String> clusters, 
			PositionGrid<String> pg, String searchHint) {
		//Create the tab folder 
		tabFolder = new CTabFolder(parent, tabStyle); //SWT.TOP | SWT.MULTI

		//Create the AffinityPane and AffinityGraphPane
		this.affinityPane = new AffinityPane<String, GraphObject>(tabFolder, SWT.NONE,
				clusterObjectManager, objectIDs, clusterObjects, clusters, pg, searchHint);
		this.affinityGraphPane = new AffinityGraphPane(tabFolder, SWT.NONE,
				clusterObjectManager, objectIDs, clusterObjects, clusters, pg, searchHint);

		//Create the tabs
		createTabs();
	}	
	
	/**
	 * @param parent
	 * @param tabStyle
	 * @param affinityPane
	 * @param affinityGraphPane
	 */
	public AffinityGraphTabbedPane(Composite parent, int tabStyle, 
			AffinityPane<String, GraphObject> affinityPane,
			AffinityGraphPane affinityGraphPane) {
		//Create the tab folder 
		tabFolder = new CTabFolder(parent, tabStyle); //SWT.TOP | SWT.MULTI
		
		//Set the AffinityPane and AffinityGraphPane
		this.affinityPane = affinityPane;
		this.affinityGraphPane = affinityGraphPane;
		
		//Create the tabs
		createTabs();
	}
	
	protected void createTabs() {		
		//Create a tab item with the AffinityPane
		CTabItem affinityTabItem = new CTabItem(tabFolder, SWT.NONE, 0);
		affinityTabItem.setText("Cluster View");
		affinityTabItem.setControl(affinityPane);

		//Create a tab item with the AffinityGraphPane
		final CTabItem graphTabItem  = new CTabItem(tabFolder, SWT.NONE, 1);
		graphTabItem.setText("Graph View");
		graphTabItem.setControl(affinityGraphPane);
		//Add listener to refresh the graph pane when the tab is selected
		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(tabFolder.getSelection() == graphTabItem) {
					affinityGraphPane.getGraphView().redraw();
				}
			}
		});
		
		tabFolder.setSelection(affinityTabItem);
	}
	
	public Control getControl() {
		return tabFolder;
	}
	
	public void setController(IAffinityController<String, GraphObject> controller) {
		affinityPane.setController(controller);
		affinityGraphPane.setController(controller);
	}
	
	public void setClusterObjectsAndClusters(ArrayList<String> objectIDs, Collection<GraphObject> clusterObjects, 
			ClustersContainer<String> clusters, PositionGrid<String> pg) {
		affinityPane.setClusterObjectsAndClusters(objectIDs, clusterObjects, clusters, pg);
		affinityGraphPane.setClusterObjectsAndClustersForClusterViews(objectIDs, clusterObjects, clusters, pg);
	}
	
	public void openGraphFile(File file) throws FileNotFoundException {
		this.graphFile = file;		
		affinityGraphPane.openGraphFile(file);
	}	

	public File getGraphFile() {
		return graphFile;
	}

	public AffinityPane<String, GraphObject> getAffinityPane() {
		return affinityPane;
	}

	public AffinityGraphPane getAffinityGraphPane() {
		return affinityGraphPane;
	}		
}