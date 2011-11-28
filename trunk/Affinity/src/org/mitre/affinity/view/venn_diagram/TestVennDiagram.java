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

package org.mitre.affinity.view.venn_diagram;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.mitre.affinity.model.schemas.AffinitySchemaManager;
import org.mitre.affinity.model.schemas.AffinitySchemaStoreManager;
import org.mitre.affinity.util.SWTUtils;
import org.mitre.affinity.view.venn_diagram.model.CachedFilteredSchemaInfo;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSetsMatrix;
import org.mitre.affinity.view.venn_diagram.view.swt.SWTVennDiagram;
import org.mitre.affinity.view.venn_diagram.view.swt.SWTVennDiagramMatrix;

import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;

public class TestVennDiagram {	
	public static void main(String[] args) {
		testVennDiagramMatrix();
		//testVennDiagramPane();
	}
	
	//Test VennDiagramMatrixPane
	public static void testVennDiagramMatrix() {
		try {		
			//AffinitySchemaStoreManager.setConnection(new SchemaStoreClient
			//	(new Repository("Ygg",Repository.POSTGRES,new URI("ygg"),"schemastore","postgres","postgres")));
			AffinitySchemaStoreManager.setConnection(new SchemaStoreClient(
					new Repository(Repository.SERVICE, new URI("http://ygg:8080/SchemaStoreForDemo/services/SchemaStore"), "", "", "")));			
		} catch(Exception ex) {
			System.err.println("Error connecting to repository: ");
			ex.printStackTrace();
			return;
		}
		
		AffinitySchemaManager schemaManager =  new AffinitySchemaManager();
		ArrayList<Integer> schemaIDs = schemaManager.getSchemaIDs();
		int i = 0;
		Iterator<Integer> iter = schemaIDs.iterator();
		ArrayList<FilteredSchemaInfo> schemaInfos = new ArrayList<FilteredSchemaInfo>();
		while(iter.hasNext() && i < 5) {
			CachedFilteredSchemaInfo graph = VennDiagramUtils.createCachedFilteredSchemaInfo(iter.next(), schemaManager);
			VennDiagramUtils.sortFilteredElements(graph);
			schemaInfos.add(graph);
			i++;
		}
		VennDiagramSetsMatrix matrix = new VennDiagramSetsMatrix(schemaInfos, 0.4, 1.0, VennDiagramUtils.createDefaultMatchScoreComputer());		
		
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setSize(800, 600);
		shell.setLayout(new FillLayout());		
		new SWTVennDiagramMatrix(shell, SWT.NONE, matrix, true).setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		SWTUtils.centerShellOnMonitor(display.getPrimaryMonitor(), shell);
		shell.open();			
		try {
			while(!shell.isDisposed()) {
				if(!display.readAndDispatch()){
					display.sleep();
				}
			}	
		} catch(Exception ex) {System.err.println("Exception: ");  ex.printStackTrace();}
		shell.dispose();
		display.dispose();
	}
	
	//Test VennDiagramPane
	public static void testVennDiagramPane() {
		try {		
			//AffinitySchemaStoreManager.setConnection(new SchemaStoreClient
			//	(new Repository("Ygg",Repository.POSTGRES,new URI("ygg"),"schemastore","postgres","postgres")));
			AffinitySchemaStoreManager.setConnection(new SchemaStoreClient(
					new Repository(Repository.SERVICE, new URI("http://ygg:8080/SchemaStoreForDemo/services/SchemaStore"), "", "", "")));			
		} catch(Exception ex) {
			System.err.println("Error connecting to repository: ");
			ex.printStackTrace();
		}
		
		AffinitySchemaManager schemaManager =  new AffinitySchemaManager();
		/*System.out.println("Schemas:");
		for(Integer id : schemaManager.getSchemaIDs()) {
			System.out.println("Schema " + id + ": " + schemaManager.getSchema(id).getName());
		}*/		
		Integer s1 = 1579; //Autonet
		Integer s2 = 1294; //AutoNation		
		//s2 = s1;
		
		CachedFilteredSchemaInfo graph1 = VennDiagramUtils.createCachedFilteredSchemaInfo(s1, schemaManager);
		VennDiagramUtils.sortFilteredElements(graph1);
		CachedFilteredSchemaInfo graph2 = VennDiagramUtils.createCachedFilteredSchemaInfo(s2, schemaManager);
		VennDiagramUtils.sortFilteredElements(graph2);
		
		VennDiagramSets sets = new VennDiagramSets(graph1, graph2, 0.4, 1.0, VennDiagramUtils.createDefaultMatchScoreComputer());
		//VennDiagramSets sets = new VennDiagramSets(s1, s2, 0.8, schemaManager, new SimpleMatchScoreComputer()); 		
		
		System.out.println("Schema 1 elements: " + sets.getSchema1AllElements());
		System.out.println("Schema 1 unique elements: " + sets.getSchema1UniqueElements());
		System.out.println("Schema 2 elements: " + sets.getSchema2AllElements());
		System.out.println("Schema 2 unique elements: " + sets.getSchema2UniqueElements());
		System.out.println("Intersecting Elements: " + sets.getIntersectElements());
		System.out.println("Number of intesecting elements: " + sets.getNumIntersectElements());		
		
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setSize(500, 500);
		shell.setLayout(new FillLayout());
		new SWTVennDiagram(shell, SWT.NONE, sets, true).setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		SWTUtils.centerShellOnMonitor(display.getPrimaryMonitor(), shell);
		shell.open();		
		try {
			while(!shell.isDisposed()) {
				if(!display.readAndDispatch()){
					display.sleep();
				}
			}	
		} catch(Exception ex) {System.err.println("Exception: ");  ex.printStackTrace();}
		shell.dispose();
		display.dispose();
	}
}
