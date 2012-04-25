package org.mitre.affinity.application.graph;

import org.mitre.affinity.controller.graph.AffinityGraphController;
import org.mitre.affinity.model.graph.AffinityGraphModel;
import org.mitre.affinity.view.graph.AffinityGraphTabbedPane;

/**
 * @author CBONACETO
 *
 */
public class AffinityGraphTab {
	
	/** The model */
	 protected final AffinityGraphModel affinityModel;
	
	/** The tabbed pane with the clusters tab and graph tab (view) */
	 protected final AffinityGraphTabbedPane affinityGraphTabbedPane;	 
	 
	/** The controller */
	protected final AffinityGraphController controller;
	 
	public AffinityGraphTab(AffinityGraphModel affinityModel, AffinityGraphTabbedPane affinityGraphTabbedPane,
			AffinityGraphController controller) {
		this.affinityModel = affinityModel;
		this.affinityGraphTabbedPane = affinityGraphTabbedPane;
		this.controller = controller;
	}

	public AffinityGraphModel getAffinityModel() {
		return affinityModel;
	}

	public AffinityGraphTabbedPane getAffinityGraphTabbedPane() {
		return affinityGraphTabbedPane;
	}

	public AffinityGraphController getController() {
		return controller;
	}
}