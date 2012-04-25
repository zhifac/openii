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

import java.io.File;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.mitre.affinity.controller.graph.AffinityGraphController;
import org.mitre.affinity.model.graph.AffinityGraphModel;
import org.mitre.affinity.model.graph.GraphClusterObjectManager;
import org.mitre.affinity.model.graph.GraphClusterObjectManager.ClusterObjectType;
import org.mitre.affinity.view.dialog.StackTraceDialog;
import org.mitre.affinity.view.graph.AffinityGraphTabbedPane;
import org.mitre.affinity.view.menu.event.AffinityMenuItemListener;
import org.mitre.affinity.view.menu.graph.AffinityMenu_Graph;

import communityFinder.Edge;
import communityFinder.Node;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * Launches the graph-specific Affinity stand-alone application.
 * 
 * @author CBONACETO
 *
 */
public class AffinityApplication {
	
	/** The shell (window) */
	private final Shell shell;
	
	/** The tab folder with the currently open tabs */
	protected CTabFolder tabFolder;
	
	/** The top level menu */
	protected AffinityMenu_Graph menu; 
	
	/** The currently open tabs. Each tab contains a sub-tab with an AffinityPane and a sub-tab with an AffinityGraphPane */
	/** Maps the graph file name to the tab */
	protected HashMap<String, AffinityGraphTab> tabs;		
	
	/** The current tab */
	protected AffinityGraphTab currentTab;
	
	/** The number of tabs without a name */
	protected int numUnnamedTabs = 0;	
	
	/** The current cluster object type */
	protected ClusterObjectType clusterObjectType;

	
	public AffinityApplication() {
		this(null, ClusterObjectType.Nodes);
	}	
	
	public AffinityApplication(String graphFilename, ClusterObjectType clusterObjectType) {
		this.clusterObjectType = clusterObjectType;
		tabs = new HashMap<String, AffinityGraphTab>();
		
		//Create the display and shell
		Display display = new Display();
		shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText("Affinity");			
		
		//Create the top-level menu bar and listen for menu item events
		final Decorations decorations = new Decorations(shell, SWT.NONE);
		decorations.setLayout(new FillLayout());
		menu = new AffinityMenu_Graph(decorations, true, true, true, true);	
		menu.addMenuItemListener(new MenuHandler());
		
		//Create an empty tab folder where tabs will be opened
		tabFolder = new CTabFolder(decorations, SWT.TOP | SWT.MULTI);
		tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			@Override
			public void close(CTabFolderEvent event) {
				//Close the tab
				closeTab(((CTabItem)event.item).getText());
			}
		});
		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				changeCurrentTab(((CTabItem)event.item).getText());
			}			
		});
		
		//Size the shell based on the screen dimensions and open it
		Rectangle screenSize = Display.getCurrent().getPrimaryMonitor().getBounds();
		shell.setSize(screenSize.width - 200, screenSize.height - 200);
		shell.setLocation((screenSize.width - shell.getSize().x)/2, (screenSize.height - shell.getSize().y)/2);
		shell.open();		
		
		//Open a sample graph
		if(graphFilename != null) {
			createNewTab(graphFilename);
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
		System.exit(0);
	}
	
	/** Create a new tab and make it the current tab */
	public void createNewTab(String graphFilename) {
		String tabName = (graphFilename != null && !graphFilename.isEmpty()) ? getFileName(graphFilename) : 
			"*New Graph " + Integer.toString(++numUnnamedTabs);
		
		if(tabs.containsKey(tabName)) {
			//TODO: Show error message that file with that name is already open
			return;
		}
		
		//Create the tab and add the tab to the list of tabs and make it the current tab
		AffinityGraphTab tab = createTab(tabName);
		
		//Open the graph
		if(graphFilename != null && !graphFilename.isEmpty()) {
			tab.controller.openGraph(graphFilename);
		}		
	}
	
	public void createNewTab(UndirectedSparseGraph<Node, Edge> graph) {
		String tabName = "*New Graph " + Integer.toString(++numUnnamedTabs);
		
		//Create the tab and add the tab to the list of tabs and make it the current tab
		AffinityGraphTab tab = createTab(tabName);
		
		//Load the graph
		if(graph != null) {
			tab.controller.setGraph(graph);
		}
	}
	
	protected AffinityGraphTab createTab(String tabName) {
		//Create the Affinity model
		AffinityGraphModel affinityModel = new AffinityGraphModel(new GraphClusterObjectManager());
		affinityModel.getClusterObjectManager().setClusterObjectType(clusterObjectType);

		//Create tabs with an empty Affinity panel and Graph panel		
		AffinityGraphTabbedPane tabbedPane = new AffinityGraphTabbedPane(tabFolder, SWT.TOP | SWT.MULTI,
				affinityModel.getClusterObjectManager(),
				clusterObjectType == ClusterObjectType.Links ? "Link Name" : "Node Name");		

		//Create a tab item with the tabs
		final CTabItem tabItem  = new CTabItem(tabFolder, SWT.NONE, tabs.size());
		tabItem.setShowClose(true);
		tabItem.setText(tabName);
		tabItem.setControl(tabbedPane.getControl());

		//Create the controller
		AffinityGraphController controller = new AffinityGraphController(
				this, tabbedPane.getAffinityPane(), tabbedPane.getAffinityGraphPane(), 
				menu, affinityModel, true);
		tabbedPane.setController(controller);
		
		//Add the tab to the list of tabs and make it the current tab
		AffinityGraphTab tab = new AffinityGraphTab(affinityModel, tabbedPane, controller);
		tabs.put(tabName, tab);

		if(currentTab != null) {
			currentTab.getController().setControllerActive(false);
		}
		currentTab = tab;
		tabFolder.setSelection(tabItem);
		
		return tab;
	}
	
	/** Called when a tab is closed */
	public void closeTab(String tabName) {
		AffinityGraphTab tab = tabs.get(tabName);
		if(tab != null) {
			//System.out.println("Closing tab " + tabName);
			tab.controller.setControllerActive(false);
			tabs.remove(tabName);
		}
	}
	
	protected void changeCurrentTab(String tabName) {
		AffinityGraphTab newActiveTab = tabs.get(tabName);
		if(newActiveTab != null) {
			//System.out.println("new active tab: " + tabName);
			if(currentTab != null) {
				currentTab.getController().setControllerActive(false);
			}
			currentTab = newActiveTab;
			currentTab.getAffinityGraphTabbedPane().getAffinityGraphPane().getGraphView().makeCurrent();
		}
	}
	
	public Shell getShell() {
		return shell;
	}

	protected String getFileName(String path) {
		File file = new File(path);
		return file.getName();
	}

	public static void main(String[] args) {		
		//String filename = "data/graphs/dolphins.gml";
		//String filename = "data/graphs/hmdmgraph.gml";
		//String filename = "data/graphs/simplegraph.gml";
		String filename = "data/graphs/congressional_voting.gml";
		new AffinityApplication(filename, ClusterObjectType.Nodes);
		//new AffinityApplication(filename, ClusterObjectType.Links);
	}	
	
	/** This class handles menu interactions */
	protected class MenuHandler implements AffinityMenuItemListener {
		@Override
		public void menuItemSelected(MenuItem menuItem, Integer menuItemID,
				Integer parentMenuID) {
			if(menuItemID != null) {			
				if(menuItemID == AffinityMenu_Graph.FILE_OPEN_ITEM) {
					//Open a graph file
					try {
						FileDialog fileDlg = new FileDialog(shell, SWT.OPEN);
						fileDlg.setText("Open Graph File");
						//fileDlg.setFilterPath("C:/");
						fileDlg.setFilterExtensions(new String[]{"*.gml", "*.csv"});
						String selectedFile = fileDlg.open();
						if(selectedFile != null) {
							createNewTab(selectedFile);
						}
					} catch(Exception ex) {
						//Show stack trace dialog
						StackTraceDialog stackTraceDlg = new StackTraceDialog(shell, ex);			
						stackTraceDlg.setMessage("Unable to open graph file, details:");
						stackTraceDlg.open();
					}
				} 		
			}			
		}
	}
}