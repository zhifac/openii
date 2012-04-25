package org.mitre.affinity.view.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.mitre.affinity.AffinityConstants.TextSize;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.view.event.SelectionChangedListener;
import org.mitre.affinity.view.event.SelectionClickedListener;

/**
 * Wraps the GephGraphView JPanel in an SWT Canvas.
 * 
 * @author CBONACETO
 *
 */
public class SWTGephiGraphViewPane extends Canvas implements IGraphClusterView {	

	/** The Gephi graph JPanel */
	protected final GephiGraphView graphView;
	
	public SWTGephiGraphViewPane(Composite parent, int style) {
		super(parent, style | SWT.EMBEDDED);
		
		java.awt.Frame frame = SWT_AWT.new_Frame(this);		
		java.awt.Panel panel = new java.awt.Panel(new java.awt.BorderLayout());
		
		graphView = new GephiGraphView();
		Color background = parent.getBackground();
		graphView.setBackground(new java.awt.Color(background.getRed(), background.getGreen(), background.getBlue()));
		panel.add(graphView);	
		frame.add(panel);		
	}

	public GephiGraphView getGraphView() {
		return graphView;
	}
	
	public void openGraphFile(File file) throws FileNotFoundException {
		graphView.openGraphFile(file);
	}
	
	@Override
	public void addSelectionChangedListener(SelectionChangedListener<String> listener) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void removeSelectionChangedListener(SelectionChangedListener<String> listener) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void addSelectionClickedListener(SelectionClickedListener<String> listener) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void removeSelectionClickedListener(SelectionClickedListener<String> listener) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void setTextSize(TextSize textSize) {
		// TODO Auto-generated method stub		
	}

	@Override
	public TextSize getTextSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShowClusters(boolean showClusters) {
		graphView.setShowClusters(showClusters);
	}

	@Override
	public void setFillClusters(boolean fillClusters) {
		//Does nothing		
	}
	
	@Override
	public void redraw() {
		graphView.redraw();
	}
	
	@Override
	public void resize() {	
		graphView.redraw();
	}

	@Override
	public void setMinClusterDistance(double minClusterDistance) {
		graphView.setMinClusterDistance(minClusterDistance);
	}

	@Override
	public void setMaxClusterDistance(double maxClusterDistance) {
		graphView.setMaxClusterDistance(maxClusterDistance);		
	}

	@Override
	public void setMinMaxClusterDistances(double minClusterDistance, double maxClusterDistance) {
		graphView.setMinMaxClusterDistances(minClusterDistance, maxClusterDistance);		
	}	

	@Override
	public void setSelectedClusterObject(String objectID) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void setSelectedClusterObjects(Collection<String> objectIDs) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void unselectAllClusterObjectsAndClusters() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void setSelectedCluster(ClusterGroup<String> cluster) {
		graphView.setSelectedCluster(cluster);		
	}

	@Override
	public Composite getComposite() {
		return this;
	}
}