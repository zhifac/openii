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

package org.mitre.affinity.application;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.mitre.affinity.algorithms.clusterers.HierarchicalClusterer;
import org.mitre.affinity.algorithms.dimensionality_reducers.MDSTester;
import org.mitre.affinity.algorithms.dimensionality_reducers.PrefuseForceDirectedMDS;
import org.mitre.affinity.algorithms.distance_functions.schemas.JaccardDistanceFunction;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.model.clusters.DistanceGrid;
import org.mitre.affinity.model.schemas.AffinitySchemaModel;
import org.mitre.affinity.model.schemas.AffinitySchemaManager;
import org.mitre.affinity.model.schemas.AffinitySchemaStoreManager;
import org.mitre.affinity.util.SampleDataCreator;
import org.mitre.affinity.view.AffinityPane;
import org.mitre.affinity.view.dialog.LoadProgressDialog;
import org.mitre.affinity.view.dialog.StackTraceDialog;
import org.mitre.affinity.view.drill_down.schemas.SchemaClusterDetailsDlg;
import org.mitre.affinity.view.event.SelectionClickedEvent;
import org.mitre.affinity.view.event.SelectionClickedListener;
import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;

/**
 * Launches the Affinity stand-alone application.
 * 
 * @author CBONACETO
 *
 */
public class AffinityApplication implements SelectionClickedListener<Integer> {
	
	private final Shell shell;
	
	private AffinityPane<Integer, Schema> affinityPane;
	
	private AffinitySchemaModel affinityModel;	
	
	private boolean connectedToRepository = false;
	
	//private SelectionClickedListener<Integer> selectionClickedListener;
	
	private static enum DataSet{REAL_DATA, STUDENT_DATA, DISA_DATA};
	
	public AffinityApplication(DataSet dataSet) {		
		//Create the display and shell
		Display display = new Display();
		shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText("Affinity");
		
		//Create a dialog to show progress as Affinity starts up
		final LoadProgressDialog progressDlg = new LoadProgressDialog(shell);
		progressDlg.setText("Launching Affinity");		
		progressDlg.open();	
		
		if(dataSet == DataSet.REAL_DATA) {
			try {				
				connectedToRepository = AffinitySchemaStoreManager.setConnection(new SchemaStoreClient
						(new Repository("Ygg",Repository.POSTGRES,new URI("ygg"),"schemastore","postgres","postgres")));
				//AffinitySchemaStoreManager.setConnection(new SchemaStoreClient(
				//		new Repository(Repository.SERVICE, new URI("http://ygg:8080/SchemaStoreForDemo/services/SchemaStore"), "", "", "")));
				//AffinitySchemaStoreManager.setConnection(new SchemaStoreClient(
					//	new Repository(Repository.DERBY, new URI("data"), "openii_schemastore.jar", "", "")));
			} catch (Exception ex) {
				System.err.println("Error connecting to schema store: "); ex.printStackTrace();
			}			
						
			//Just use the real estate / auto / student schemas
			ArrayList<Integer> schemaIDs = new ArrayList<Integer>();			
			//Integer[] ids = {1001, 1170, 1294, 1579, 1676, 1910, 2089, 2179, 2592, 2724, 2837, 3014, 3162, 3301};
			Integer[] ids = {102045, 109239, 116025, 128343, 140409, 142997, 149146, 149526, 98363, 137869, 219217, 219439, 219647, 219849, 384941, 385042};
			for(Integer id : ids)
				schemaIDs.add(id);
			/*
			schemaIDs.add(1001); //autochooser
			schemaIDs.add(1170); //AutoMob			
			schemaIDs.add(1294); //AutoNation
			schemaIDs.add(1579); //Autonet
			schemaIDs.add(1676); //Autonet_ca
			schemaIDs.add(1910); //Autopoint
			schemaIDs.add(2089); //Autoweb
			schemaIDs.add(2179); //buycars
			schemaIDs.add(2592); //newhomebuilders
			schemaIDs.add(2724); //NorthIdahoHomeseekers
			schemaIDs.add(2837); //PlanetProperties
			schemaIDs.add(3014); //RealEstate
			schemaIDs.add(3162); //SpaceForLease
			schemaIDs.add(3301); //YahooRealEstate
			*/			
						
			affinityModel = new AffinitySchemaModel(new AffinitySchemaManager());			
			affinityPane = new AffinityPane<Integer, Schema>(shell, SWT.NONE, affinityModel, 
					schemaIDs, new JaccardDistanceFunction(), new PrefuseForceDirectedMDS<Integer>(), 
					new HierarchicalClusterer<Integer>(), progressDlg, "Schema Name", true);
			/*if(affinityPane.isAffinityPaneCreated()) {
				affinityPane.addSelectionClickedListener(this);
			}*/
			//new AffinityPane(shell);
		}
		else if(dataSet == DataSet.STUDENT_DATA) {
			//Load the student/real estate/auto sample distance grid		
			DistanceGrid<Integer> dg = new DistanceGrid<Integer>();
			try {				
				SampleDataCreator.SchemasAndDistanceGrid data = SampleDataCreator.parseDistanceGridCSVFile("data/realestate_auto_jacard_dist_grid.csv");
				//schemas = data.schemas;
				dg = data.dg;
			} catch(IOException ex) {
				System.err.println(ex);
			}	
			
			//Load the sample 2-d position grid
			List<Schema> schemas = null;
			SampleDataCreator.SchemasAndPositionGrid positionData = null;
			try {			
				positionData = SampleDataCreator.parsePositionGridCSVFile("data/realestate_auto_2d_points.csv");
				schemas = positionData.schemas;
			} catch(IOException ex) {
				System.err.println(ex);
			}	

			ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
			for(Schema s : schemas) {
				//System.out.println("schema id " + s.getId());
				schemaIDs.add(s.getId());
			}
			
			//Debug code to test craigrogram with different permutations of schemata
			schemaIDs = new ArrayList<Integer>();			
			schemaIDs.add(1);
			schemaIDs.add(2);
			schemaIDs.add(3);
			schemaIDs.add(4);
			schemaIDs.add(5);			
			schemaIDs.add(6);
			schemaIDs.add(7);
			schemaIDs.add(8);
			schemaIDs.add(9);
			schemaIDs.add(10);			
			schemaIDs.add(11);
			schemaIDs.add(12);
			schemaIDs.add(13);
			schemaIDs.add(14);
			schemaIDs.add(15);
			schemaIDs.add(16);
			schemaIDs.add(17);
			schemaIDs.add(18);
			schemaIDs.add(19);
			List<Schema> schemas2 = new ArrayList<Schema>();
			DistanceGrid<Integer> dg2 = new DistanceGrid<Integer>();
			for(Integer s1 : schemaIDs) {
				schemas2.add(schemas.get(s1-1));
				for(Integer s2 : schemaIDs) {
					if(s2 > s1)
						dg2.set(s1, s2, dg.get(s1, s2));
				}
			}
			dg = dg2;
			schemas = schemas2;			
			//end debug code;
			
			boolean usePrefuse = true;
			final PositionGrid<Integer> pg;		
			if(usePrefuse) {
				//Compute the 2-d position grid  with Prefuse
				dg.rescale(0, 1000);
				PrefuseForceDirectedMDS<Integer> mds = new PrefuseForceDirectedMDS<Integer>();				
				mds.setObjectIDs(schemaIDs);
				pg =  mds.scaleDimensions(dg, true, true, 2, true);
			}
			else {
				//Use the sample 2-d position grid	
				if(positionData != null)
					pg = positionData.pg;
				else
					pg = null;	
			}		

			try {
				SampleDataCreator.SchemasAndDistanceGrid data = SampleDataCreator.parseDistanceGridCSVFile("data/realestate_auto_jacard_dist_grid.csv");
				//schemas = data.schemas;
				dg = data.dg;
			} catch(IOException ex) {
				System.err.println(ex);
			}
			
			//Rescale the minimum and maximum position values
			//Rectangle bounds = shell.getClientArea();
			//pg.rescale(0, bounds.width-50, 0, bounds.height-80);			
			//pg.rescale(0, bounds.width-50, 0, bounds.width-50);
			
			//Compute the goodness of the 2d plot correlation coefficient
			double corr = MDSTester.compute2DFitCorrelation(schemaIDs, dg, pg);
			System.out.println("Correlation: " + corr);

			//Create clusters
			//dg.rescale(0, 1);
			HierarchicalClusterer<Integer> hc = new HierarchicalClusterer<Integer>();
			ClustersContainer<Integer> cc = hc.generateClusters(schemaIDs, dg);		
			
			//Change some of the student schema names 
			for(Schema s : schemas) {
				if(s.getName().startsWith("Jake"))
					s.setName("Student");
				else if(s.getName().startsWith("Xenia"))
					s.setName("Class");					
				else if(s.getName().startsWith("Ryan"))
					s.setName("GradeReport");
				else if(s.getName().startsWith("yana"))
					s.setName("Schools");
				else if(s.getName().startsWith("igor"))
					s.setName("SchoolFinance");
			}
			
			//System.out.println("Clusters Before Normalizing: ");
			//System.out.println(cc);		
			cc = cc.removeDuplicateClusterGroups(schemaIDs, cc);
			//System.out.println("Clusters After Normalizing: ");
			//System.out.println(cc);
			affinityModel = new AffinitySchemaModel(new AffinitySchemaManager());			
			affinityModel.getSchemaManager().setSchemas(schemas);
			affinityPane = new AffinityPane<Integer, Schema>(shell, SWT.NONE, affinityModel,
					schemaIDs, schemas, cc, pg, "Schema Name", true);			
		} 
		else {
			//Load the DISA sample distance grid		
			DistanceGrid<Integer> dg = new DistanceGrid<Integer>();
			List<Schema> schemas = null;
			try {				
				SampleDataCreator.SchemasAndDistanceGrid data = SampleDataCreator.parseDistanceGridCSVFile("data/DISA_dist_grid.csv");
				schemas = data.schemas;
				dg = data.dg;
			} catch(IOException ex) {
				System.err.println(ex);
			}

			ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
			for(Schema s : schemas) {
				//System.out.println("schema id " + s.getId());
				schemaIDs.add(s.getId());
			}	

			//Compute the 2-d position grid with Prefuse
			dg.rescale(0, 1000);
			PrefuseForceDirectedMDS<Integer> mds = new PrefuseForceDirectedMDS<Integer>();
			mds.setNumIterations(10000);
			mds.setObjectIDs(schemaIDs);
			final PositionGrid<Integer> pg =  mds.scaleDimensions(dg, true, true, 2, true);
			
			try {
				SampleDataCreator.SchemasAndDistanceGrid data = SampleDataCreator.parseDistanceGridCSVFile("data/DISA_dist_grid.csv");
				//schemas = data.schemas;
				dg = data.dg;
			} catch(IOException ex) {
				System.err.println(ex);
			}
			
			//Rescale the minimum and maximum position values
			//Rectangle bounds = shell.getClientArea();
			//pg.rescale(0, bounds.width-50, 0, bounds.height-80);			
			//pg.rescale(0, bounds.width-50, 0, bounds.width-50);
			
			//Compute the goodness of the 2d plot correlation coefficient
			double corr = MDSTester.compute2DFitCorrelation(schemaIDs, dg, pg);
			System.out.println("Correlation: " + corr);

			//Create clusters			
			HierarchicalClusterer<Integer> hc = new HierarchicalClusterer<Integer>();
			ClustersContainer<Integer> cc = hc.generateClusters(schemaIDs, dg);
			
			//System.out.println("Clusters Before Normalizing: ");
			//System.out.println(cc);		
			cc = cc.removeDuplicateClusterGroups(schemaIDs, cc);
			//System.out.println("Clusters After Normalizing: ");
			//System.out.println(cc);
			affinityModel = new AffinitySchemaModel(new AffinitySchemaManager());		
			affinityModel.getSchemaManager().setSchemas(schemas);
			affinityPane = new AffinityPane<Integer, Schema>(shell, SWT.NONE, affinityModel,
					schemaIDs, schemas, cc, pg, "Schema Name", true);	
		}
		
		progressDlg.close();
		if(!affinityPane.isAffinityPaneCreated()) {
			//There was an error launching affinity			
			StackTraceDialog stackTraceDlg = new StackTraceDialog(shell, affinityPane.getException());			
			stackTraceDlg.setMessage("Unable to launch Affinity due to:");
			stackTraceDlg.open();			
			//SWTUtils.centerShellOnShell(errorBox.getParent(), progressDlg.getShell());			
			System.exit(0);
		}
		else {				
			affinityPane.addSelectionClickedListener(this);
			affinityPane.getCraigrogram().debug = false;
			affinityPane.getCraigrogramPane().setLockAspectRatio(false);	
			
			//affinityPane.getDendrogram().setFont(SWTUtils.getFont(SWTUtils.LARGE_FONT));
			//affinityPane.getDendrogram().setSelectedFont(SWTUtils.getFont(SWTUtils.LARGE_BOLD_FONT));

			Rectangle screenSize = Display.getCurrent().getPrimaryMonitor().getBounds();
			//shell.setSize(300, 800);
			shell.setSize(screenSize.width - 200, screenSize.height - 200);
			shell.setLocation((screenSize.width - shell.getSize().x)/2, (screenSize.height - shell.getSize().y)/2);
			shell.open();
			while(!shell.isDisposed()){
				if(!display.readAndDispatch()){
					display.sleep();
				}
			}		

			//System.out.println("exiting");
			if(shell != null && !shell.isDisposed())
				shell.dispose();
			if(display != null && !display.isDisposed())
				display.dispose();	
		}
	}
	
	public void selectionClicked(SelectionClickedEvent<Integer> event) {
		if(event.selectedClusterObjects != null) {
			if(event.selectedClusterObjects.size() == 1) {
				//A single cluster object is currently selected
			} else {
				//Multiple cluster objects are currently selected
				if(event.button == 3 || (event.button == 1 && event.controlDown)) {
					//Multiple cluster objects were right-clicked, display menu
					Menu multiSchemaMenu = new Menu(affinityPane);
					MenuItem item = new MenuItem (multiSchemaMenu, SWT.RADIO);				
					item.setText("View Schema Relatedness");
					item = new MenuItem (multiSchemaMenu, SWT.RADIO);				
					item.setText("View Terms in Schemas");
					item = new MenuItem (multiSchemaMenu, SWT.RADIO);				
					item.setText("Open Schemas In New Affinity Window");				
					item = new MenuItem (multiSchemaMenu, SWT.RADIO);				
					item.setText("Create Group From Schemas");
					multiSchemaMenu.setVisible(true);
				}
			}
		}
		else if(event.selectedClusters != null && event.selectedClusters.size() == 1) {
			//A single cluster is currently selected
			if(event.button == 1 && event.clickCount == 2) {				
				//System.out.println("Cluster double clicked");
				if(connectedToRepository) {
					//A single cluster was double-clicked, display the TF-IDF dialog for the cluster
					ClusterGroup<Integer> cluster = event.selectedClusters.iterator().next();
					SchemaClusterDetailsDlg dlg = new SchemaClusterDetailsDlg(shell, SWT.APPLICATION_MODAL, affinityModel, cluster);				
					dlg.setVisible(true);
				}
			}			
			else if(event.button == 3 || (event.button == 1 && event.controlDown)) {
				//A single cluster was right-clicked, display cluster right click menu
				//System.out.println("cluster right clicked");
				Menu clusterMenu = new Menu(affinityPane);
				MenuItem item = new MenuItem (clusterMenu, SWT.RADIO);				
				item.setText("View Terms in Cluster");
				item = new MenuItem (clusterMenu, SWT.RADIO);				
				item.setText("Open Cluster In New Affinity Window");				
				item = new MenuItem (clusterMenu, SWT.RADIO);				
				item.setText("Tag all Schemas in Cluster");
				clusterMenu.setVisible(true);
			}
		}		
	}
	
	public static void main(String[] args) {
		new AffinityApplication(DataSet.STUDENT_DATA);
		//new AffinityApplication(DataSet.REAL_DATA);
	}	
}