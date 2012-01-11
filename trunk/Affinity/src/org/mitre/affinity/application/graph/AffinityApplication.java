/*
 *  Copyright 2010 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mitre.affinity.application.graph;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.mitre.affinity.controller.graph.AffinityGraphController;
import org.mitre.affinity.model.graph.AffinityGraphModel;
import org.mitre.affinity.model.graph.GraphObject;
import org.mitre.affinity.model.graph.GraphClusterObjectManager;
import org.mitre.affinity.model.graph.GraphClusterObjectManager.ClusterObjectType;
import org.mitre.affinity.view.AffinityPane;
import org.mitre.affinity.view.event.SelectionClickedEvent;
import org.mitre.affinity.view.event.SelectionClickedListener;
import org.mitre.affinity.view.menu.graph.AffinityMenu_Graph;

/**
 * Launches the graph-specific Affinity stand-alone application.
 * 
 * @author CBONACETO
 *
 */
public class AffinityApplication implements SelectionClickedListener<String> {
	
	/** The shell (window) */
	private final Shell shell;
	
	/** The Affinity Panel (contains the Craigrogram and Dendrogram) */
	private AffinityPane<String, GraphObject> affinityPane;
	
	/** The Affinity model */
	private AffinityGraphModel affinityModel;
	
	public AffinityApplication() {
		this(null, ClusterObjectType.Links);
	}	
	
	public AffinityApplication(String graphFilename, ClusterObjectType clusterObjectType) {		
		//Create the display and shell
		Display display = new Display();
		shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText("Affinity");	
		
		//Create the Affinity model
		affinityModel = new AffinityGraphModel(new GraphClusterObjectManager());
		affinityModel.getClusterObjectManager().setClusterObjectType(clusterObjectType);

		//Create an empty Affinity panel with a menu
		final Decorations decorations = new Decorations(shell, SWT.NONE);
		decorations.setLayout(new FillLayout());
		AffinityMenu_Graph menu = new AffinityMenu_Graph(decorations, true, true, true, true);
		affinityPane = new AffinityPane<String, GraphObject>(decorations, SWT.NONE, affinityModel.getClusterObjectManager(),
				"Link Name");
		affinityPane.addSelectionClickedListener(this);
		affinityPane.getCraigrogram().debug = false;
		affinityPane.getCraigrogramPane().setLockAspectRatio(false);
		Rectangle screenSize = Display.getCurrent().getPrimaryMonitor().getBounds();
		shell.setSize(screenSize.width - 200, screenSize.height - 200);
		shell.setLocation((screenSize.width - shell.getSize().x)/2, (screenSize.height - shell.getSize().y)/2);
		shell.open();
		
		//Initialize the controller
		AffinityGraphController controller = new AffinityGraphController(
				affinityPane, affinityPane, menu, affinityModel); 
		
		//Open a sample graph
		if(graphFilename != null) {
			controller.openGraph(graphFilename);
		}
		
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}			
		if(shell != null && !shell.isDisposed()) {
			try {
				shell.dispose();
			} catch(Exception ex) {}
		}
		if(display != null && !display.isDisposed()) {
			try {
				display.dispose();
			} catch(Exception ex) {}
		}
	}
	
	public void selectionClicked(SelectionClickedEvent<String> event) {
		
	}
	
	public static void main(String[] args) {		
		String filename = "data/graphs/dolphins.gml";
		//String filename = "data/graphs/hmdmgraph.gml";
		//String filename = "data/graphs/simplegraph.gml";
		new AffinityApplication(filename, ClusterObjectType.Nodes);
		//new AffinityApplication(filename, ClusterObjectType.Links);
	}	
}