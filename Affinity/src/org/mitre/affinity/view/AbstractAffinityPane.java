package org.mitre.affinity.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.SwingConstants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.mitre.affinity.controller.IAffinityController;
import org.mitre.affinity.model.IClusterObjectManager;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.view.craigrogram.SWTClusterDistanceSliderPane;
import org.mitre.affinity.view.event.ClusterDistanceChangeEvent;
import org.mitre.affinity.view.event.ClusterDistanceChangeListener;
import org.mitre.affinity.view.event.SelectionChangedEvent;
import org.mitre.affinity.view.event.SelectionChangedListener;
import org.mitre.affinity.view.event.SelectionClickedListener;

/** Abstract base class for panes with two linked cluster views (e.g., a Cluster 2D view on the left, and a Dendrogram on the right) 
 *  and a cluster distance slider that manipulates both views.
 * 
 * @author CBONACETO
 *
 * @param <K>
 * @param <V>
 */
public abstract class AbstractAffinityPane<K extends Comparable<K>, V> extends Composite implements SelectionChangedListener<K>, ClusterDistanceChangeListener {
	
	/** Pane with the cluster distance slider */
	protected SWTClusterDistanceSliderPane<K, V> slider;
	
	/** Pane with the left cluster view (e.g., a "Craigrogram") */	
	protected IClusterView<K, V> leftClusterView;
	
	/** Pane with the right cluster view (e.g. a Dendrogram) */	
	protected IClusterView<K, V> rightClusterView;	
	
	/** Pane above the right cluster view that allows user to search for a cluster object **/
	protected SearchToolBar<K, V> searchToolBar;
	
	/** The controller */
	protected IAffinityController<K, V> controller;
	
	/** The cluster object manager */
	protected IClusterObjectManager<K, V> clusterObjectManager;
	
	/** Mapping of cluster object id to a cached cluster object */
	protected Map<K, V> clusterObjectsMap; 
	
	/** Cluster object IDs of the cluster objects to be clustered */
	protected Collection<K> objectIDs;
	
	/** Percent of the screen the left cluster view is using.  Starts out at 50. */
	protected int leftClusterViewPercent = 50;
	
	/**
	 * Create a blank AffinityPane.
	 * 
	 * @param parent
	 * @param style
	 * @param searchHint
	 * @param showingSchemas
	 */
	public AbstractAffinityPane(final Composite parent, int style, IClusterObjectManager<K, V> clusterObjectManager, String searchHint) {
		super(parent, style);
		this.clusterObjectManager = clusterObjectManager;
		createAffinityPane(searchHint);
	}	
	
	/**
	 * Creates an AffinityPane using the given cluster objects, clusters, and position grid.
	 */
	public AbstractAffinityPane(final Composite parent, int style, IClusterObjectManager<K, V> clusterObjectManager,
			ArrayList<K> objectIDs, List<V> clusterObjects, ClustersContainer<K> clusters, 
			PositionGrid<K> pg, String searchHint) {
		super(parent, style);
		this.clusterObjectManager = clusterObjectManager;
		this.objectIDs = objectIDs;
		createAffinityPane(searchHint);	
		setClusterObjectsAndClusters(objectIDs, clusterObjects, clusters, pg);
	}
	
	public void setController(IAffinityController<K, V> controller) {
		this.controller = controller;
		searchToolBar.setController(controller);
	}

	/**
	 * @param objectIDs
	 * @param clusterObjects
	 * @param clusters
	 * @param pg
	 */
	public void setClusterObjectsAndClusters(ArrayList<K> objectIDs, Collection<V> clusterObjects, 
			ClustersContainer<K> clusters, PositionGrid<K> pg) {
		slider.setClusters(clusters);
		setClusterObjectsAndClustersForClusterViews(objectIDs, clusterObjects, clusters, pg);		
	}	
	
	protected abstract void setClusterObjectsAndClustersForClusterViews(ArrayList<K> objectIDs, Collection<V> clusterObjects, 
			ClustersContainer<K> clusters, PositionGrid<K> pg);	
	
	public void setSearchHint(String searchHint) {
		searchToolBar.setSearchHint(searchHint);
	}
	
	public void addClusterDistanceChangeListener(ClusterDistanceChangeListener listener) {
		slider.getClusterDistanceSliderPane().addClusterDistanceChangeListener(listener);
	}
	
	public void removeClusterDistanceChangeListener(ClusterDistanceChangeListener listener) {
		slider.getClusterDistanceSliderPane().removeClusterDistanceChangeListener(listener);
	}
	
	public void addSelectionChangedListener(SelectionChangedListener<K> listener) {
		leftClusterView.addSelectionChangedListener(listener);
		rightClusterView.addSelectionChangedListener(listener);
	}
	
	public void removeSelectionChangedListener(SelectionChangedListener<K> listener) {
		leftClusterView.removeSelectionChangedListener(listener);
		rightClusterView.removeSelectionChangedListener(listener);
	}
	
	public void addSelectionClickedListener(SelectionClickedListener<K> listener) {
		leftClusterView.addSelectionClickedListener(listener);
		rightClusterView.addSelectionClickedListener(listener);
	}
	
	public void removeSelectionClickedListener(SelectionClickedListener<K> listener) {
		leftClusterView.removeSelectionClickedListener(listener);
		rightClusterView.removeSelectionClickedListener(listener);
	}	
	
	public Collection<K> getObjectIDs() {
		return objectIDs;
	}
	
	public V getClusterObject(K objectID) {
		return clusterObjectsMap.get(objectID);
	}
	
	public Map<K, V> getClusterObjects() {
		return clusterObjectsMap;
	}
	
	public SWTClusterDistanceSliderPane<K, V> getSlider() {
		return slider;
	}

	public IClusterView<K, V> getLeftClusterView() {
		return leftClusterView;
	}

	public IClusterView<K, V> getRightClusterView() {
		return rightClusterView;
	}

	public void addClusterDistanceTickLocation(double distance, boolean highlighted) {
		if(slider != null) {
			slider.getClusterDistanceSliderPane().addClusterDistanceTickLocation(distance, highlighted);
		}
	}
	
	public void clearAdditionalClusterDistanceTickLocations() {
		if(slider != null) {
			slider.getClusterDistanceSliderPane().clearAdditionalClusterDistanceTickLocations();
		}
	}
	
	public void setClusterDistances(double minDistance, double maxDistance) {
		slider.getClusterDistanceSliderPane().setClusterDistances(minDistance, maxDistance);
		rightClusterView.setMinMaxClusterDistances(minDistance, maxDistance);
		leftClusterView.setMinMaxClusterDistances(minDistance, maxDistance);		
	}
	
	@Override
	public void clusterDistanceChanged(ClusterDistanceChangeEvent event) {
		rightClusterView.setMinMaxClusterDistances(event.newMinDistance, event.newMaxDistance);
		leftClusterView.setMinMaxClusterDistances(event.newMinDistance, event.newMaxDistance);
	}

	/** SelectionChangedListener method to highlight a cluster object/cluster in the right pane when 
	 * highlighted in the left pane and vice-versa. */
	@Override
	public void selectionChanged(SelectionChangedEvent<K> event) {
		if(event.getSource() != leftClusterView) {
			//Update selection in the left cluster view
			leftClusterView.setSelectedClusterObjects(event.selectedClusterObjects);
			if(event.selectedClusters != null && event.selectedClusters.size() == 1) {
				ClusterGroup<K> cluster = event.selectedClusters.iterator().next();
				leftClusterView.setSelectedCluster(cluster);
			}
			else {
				leftClusterView.setSelectedCluster(null);
			}
			leftClusterView.redraw();
		} 		
		if(event.getSource() != rightClusterView) {
			//Update selection in the right cluster view
			rightClusterView.setSelectedClusterObjects(event.selectedClusterObjects);
			if(event.selectedClusters != null && event.selectedClusters.size() == 1) {
				ClusterGroup<K> cluster = event.selectedClusters.iterator().next();
				rightClusterView.setSelectedCluster(cluster);
			}
			else {
				rightClusterView.setSelectedCluster(null);
			}
			rightClusterView.redraw();
		}
	}
	
	protected void createAffinityPane(String searchHint) {
		GridLayout gl= new GridLayout(1, true);
		gl.marginHeight = 1;	
		gl.marginTop = 1;
		gl.marginBottom = 1;
		setLayout(gl);
		
		//Create pane with the cluster distance slider
		Composite sliderPane = new Composite(this, SWT.NONE);
		sliderPane.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		sliderPane.setLayout(new FillLayout());
		this.slider = new SWTClusterDistanceSliderPane<K, V>(sliderPane, SWT.NONE, null, SwingConstants.HORIZONTAL);		
		
		// Create sash that the left and right cluster panes will be attached to		
		final Composite pane = new Composite(this, SWT.NONE);
		pane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		pane.setLayout(new FormLayout());
		final Sash sash = new Sash(pane, SWT.VERTICAL);
		this.leftClusterViewPercent = 50;
		Color white = getDisplay().getSystemColor(SWT.COLOR_WHITE);
		sash.setBackground(white);		
		final FormData sashData = new FormData ();
		sashData.left = new FormAttachment (leftClusterViewPercent, 0);
		sashData.top = new FormAttachment (0, 0);
		sashData.bottom = new FormAttachment (100, 0);
		sash.setLayoutData(sashData);				
		
		//Create the left cluster view
		leftClusterView = createLeftClusterView(pane, sash.getBackground());
		FormData pane1Data = new FormData ();
		pane1Data.left = new FormAttachment (0, 0);
		pane1Data.right = new FormAttachment (sash, 0);
		pane1Data.top = new FormAttachment (0, 0);
		pane1Data.bottom = new FormAttachment (100, 0);
		leftClusterView.getComposite().setLayoutData(pane1Data);
					
		//Create search + right cluster view combo pane
		final Composite searchPlusDendrogramPane = new Composite(pane, SWT.BORDER);	
		FormLayout fl1 = new FormLayout();
		searchPlusDendrogramPane.setLayout(fl1);
		searchToolBar = new SearchToolBar<K, V>(searchPlusDendrogramPane,  SWT.NONE, controller, searchHint);		
		
		//Create the pane for the right cluster view				
		final Composite dendrogramPane = new Composite(searchPlusDendrogramPane, SWT.BORDER);
		dendrogramPane.setLayout(new FillLayout());
		FormData fd1 = new FormData();
		fd1.top = new FormAttachment(searchToolBar, 0);
		fd1.left = new FormAttachment (sash, 0);
		fd1.right = new FormAttachment (100, 0);
		fd1.bottom = new FormAttachment (100, 0);		
		dendrogramPane.setLayoutData(fd1);		
		dendrogramPane.setBackground(white);
		rightClusterView = createRightClusterView(dendrogramPane, dendrogramPane.getBackground());		
		
		//Create right side of sash 
		FormData pane2Data = new FormData ();
		pane2Data.left = new FormAttachment (sash, 0);
		pane2Data.right = new FormAttachment (100, 0);
		pane2Data.top = new FormAttachment (0, 0);
		pane2Data.bottom = new FormAttachment (100, 0);		
		searchPlusDendrogramPane.setLayoutData(pane2Data);
			
		slider.getClusterDistanceSliderPane().addClusterDistanceChangeListener(this);
		//slider.getClusterDistanceSliderPane().addClusterDistanceChangeListener(rightClusterView);
		//slider.getClusterDistanceSliderPane().addClusterDistanceChangeListener(leftClusterView);
		
		// Add the listener to allow resizing of the left and right cluster views
		sash.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				int limit = 20;
				Rectangle sashRect = sash.getBounds ();
				Rectangle shellRect = getClientArea ();
				int right = shellRect.width - sashRect.width - limit;
				e.x = Math.max (Math.min (e.x, right), limit);
				if (e.x != sashRect.x)  {
					sashData.left = new FormAttachment (0, e.x);
					sash.getParent().layout();
				}
				
				//TODO: Resizing introduces error in the position grid, think about maintaining the original aspect ratio or recomputing the position grid
				leftClusterView.resize();
				rightClusterView.resize();
			}
		});	
		
		//Add the resize listeners
		addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent event) {}			
			public void controlResized(ControlEvent event) {
				int leftClusterViewWidth = leftClusterView.getComposite().getSize().x;
				if(leftClusterViewWidth > 0)
					leftClusterViewPercent = (int)((float)leftClusterViewWidth/(leftClusterViewWidth + 
							rightClusterView.getComposite().getSize().x) * 100);
				sashData.left = new FormAttachment (leftClusterViewPercent, 0);				
				layout();				
				leftClusterView.resize();
				rightClusterView.resize();
			}
		});
		
		//Add listener to highlight cluster/schema in the right pane when highlighted in the left pane and vice-versa
		addSelectionChangedListener(this);
	}	
	
	protected abstract IClusterView<K, V> createLeftClusterView(Composite parent, Color background);	
	
	protected abstract IClusterView<K, V> createRightClusterView(Composite parent, Color background);	

	public void resized() {
		leftClusterView.resize();
		rightClusterView.resize();
	}	
}