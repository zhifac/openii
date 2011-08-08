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

package org.mitre.affinity.view.cluster_details;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.clusters.ClusterTermVector.ClusterTermStatistics;
import org.mitre.affinity.model.AffinityModel;

/**
 * Displays term frequencies for the schemas in a cluster.
 * 
 * @author CBONACETO
 *
 */
public class ClusterDetailsPane extends Composite {
	
	public ClusterDetailsPane(Composite parent, int style, AffinityModel affinityModel, ClusterGroup cluster) {
		super(parent, style);		
		this.createClusterDetailsPane(affinityModel, cluster);
	}
	
	private void createClusterDetailsPane(final AffinityModel affinityModel, final ClusterGroup cluster) {
		this.setLayout(new GridLayout(1, false));
		
		//Create table with columns for the term name, # of schemas containing the term, and its TF-IDF score
		final Table table = new Table(this, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(data);		
		
		final TableColumn termColumn = new TableColumn(table, SWT.CENTER);
		termColumn.setText("Term");
		final TableColumn frequInClusterColumn = new TableColumn(table, SWT.CENTER);
		frequInClusterColumn.setText("Schemas in Cluster with Term");		 
		final TableColumn frequInGroupColumn= new TableColumn(table, SWT.CENTER);
		frequInGroupColumn.setText("Schemas Shown with Term");
		final TableColumn tfIdfColumn = new TableColumn(table, SWT.CENTER);
		tfIdfColumn.setText("TF-IDF Score");		
			
		if(affinityModel == null) {
			System.err.println("affinity model is null!");
			return;
		}
		
		// Populate the table
		final List<ClusterTermStatistics> clusterTermStats = affinityModel.getClusterTermVector(cluster).getTermsSortedByNumContainingSchemasInCluster();
		final String numSchemasInCluster = Integer.toString(cluster.getNumSchemas());
		final String numSchemas = Integer.toString(affinityModel.getClusters().getSchemaIDs().size());		
		final DecimalFormat decimalFormat = new DecimalFormat();	
		decimalFormat.setMaximumFractionDigits(6);		
		
		for(ClusterTermStatistics termStats : clusterTermStats) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, termStats.getTerm());
			item.setText(1, Integer.toString(termStats.getNumSchemasInClusterContainingTerm()) + "/" + numSchemasInCluster);
			item.setText(2, Integer.toString(termStats.getNumSchemasContainingTerm()) + "/" + numSchemas);
			//String tfidf = Double.toString(termStats.getClusterTFIDFScore());
			StringBuffer tfidf = new StringBuffer();
			tfidf = decimalFormat.format(termStats.getClusterTFIDFScore(), tfidf, new FieldPosition(0));
			item.setText(3, tfidf.toString());		
		}
		for(int i=0; i<table.getColumnCount(); i++) {
			TableColumn column = table.getColumn(i);			
			column.pack();
			column.setWidth(column.getWidth() + 40);			
			//table.getColumn(i).setWidth(50);			
		}
		
		//Add listener to re-populate the table when it is resorted by a column		
		table.addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event e) {
				TableItem item = (TableItem)e.item;
				int index = table.indexOf(item);				
				ClusterTermStatistics termStats = clusterTermStats.get(index);								
				item.setText(0, termStats.getTerm());				
				item.setText(1, Integer.toString(termStats.getNumSchemasInClusterContainingTerm()) + "/" + numSchemasInCluster);
				item.setText(2, Integer.toString(termStats.getNumSchemasContainingTerm()) + "/" + numSchemas);
				StringBuffer tfidf = new StringBuffer();
				tfidf = decimalFormat.format(termStats.getClusterTFIDFScore(), tfidf, new FieldPosition(0));
				item.setText(3, tfidf.toString());				
			}
		});		
		
		//Create listener to sort table by each column
		Listener sortListener = new Listener() {
	        public void handleEvent(Event e) {
	        	// determine new sort column and direction
				TableColumn sortColumn = table.getSortColumn();
				TableColumn column = (TableColumn)e.widget;
				int dir = table.getSortDirection();
				if (sortColumn == column) {
					dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
				} else {
					table.setSortColumn(column);
					if(column == termColumn) dir = SWT.UP;
					else dir = SWT.DOWN;
				}
	        	
				//Sort table based on sort direction and column
				final int direction = dir;
				Comparator<ClusterTermStatistics> comparator = null;
	        	if(column == termColumn) {	
	        		comparator = new Comparator<ClusterTermStatistics>() {
	        			public int compare(ClusterTermStatistics arg0, ClusterTermStatistics arg1) {	        				
	        				if(direction == SWT.UP)
	        					return arg0.getTerm().compareTo(arg1.getTerm());
	        				return arg1.getTerm().compareTo(arg0.getTerm());	        				
	        			}};	        		
	        	}
	        	else if(column == frequInClusterColumn) {	        		
	        		comparator = new Comparator<ClusterTermStatistics>() {
	        			public int compare(ClusterTermStatistics arg0, ClusterTermStatistics arg1) {	        				
	        				if(direction == SWT.UP)
	        					return (arg0.getNumSchemasInClusterContainingTerm() - arg1.getNumSchemasInClusterContainingTerm());
	        				return (arg1.getNumSchemasInClusterContainingTerm() - arg0.getNumSchemasInClusterContainingTerm());	        				
	        			}};	   
	        	}
	        	else if(column == frequInGroupColumn) {	        		
	        		comparator = new Comparator<ClusterTermStatistics>() {
	        			public int compare(ClusterTermStatistics arg0, ClusterTermStatistics arg1) {	        				
	        				if(direction == SWT.UP)
	        					return (arg0.getNumSchemasContainingTerm() - arg1.getNumSchemasContainingTerm());
	        				return (arg1.getNumSchemasContainingTerm() - arg0.getNumSchemasContainingTerm());	        				
	        			}};	   
	        	}
	        	else {       		
	        		comparator = new Comparator<ClusterTermStatistics>() {
	        			public int compare(ClusterTermStatistics arg0, ClusterTermStatistics arg1) {	        				
	        				if(direction == SWT.UP)
	        					return (int)Math.signum(arg0.getClusterTFIDFScore() - arg1.getClusterTFIDFScore());
	        				return (int)Math.signum(arg1.getClusterTFIDFScore() - arg0.getClusterTFIDFScore());	        				
	        			}};	   
	        	}
	        	Collections.sort(clusterTermStats, comparator);
	        	table.setSortDirection(dir);
	        	table.clearAll();
	        }
	    };	    
		termColumn.addListener(SWT.Selection, sortListener);	
		frequInClusterColumn.addListener(SWT.Selection, sortListener);	 		
		frequInGroupColumn.addListener(SWT.Selection, sortListener);		
		tfIdfColumn.addListener(SWT.Selection, sortListener);	    
		
		table.setSortColumn(frequInClusterColumn);
		table.setSortDirection(SWT.DOWN);
	}
}	
