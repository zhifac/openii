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

package org.mitre.affinity.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.mitre.affinity.controller.IAffinityController;

/**
 * 
 * @author cbonaceto
 *
 */
public class SearchToolBar<K extends Comparable<K>, V> extends Composite {
	
	private Composite toolBar;
	
	private Text searchField;
	
	private IAffinityController<K, V> controller;	
	
	public SearchToolBar(Composite parent, int style,
			IAffinityController<K, V> controller, String searchHint) {
		super(parent, style);
		this.controller = controller;
		this.createToolBar(searchHint);
	}
	
	protected void createToolBar(String searchHint) {
		setLayout(new GridLayout(1, true));
		toolBar = new Composite(this, SWT.NONE);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));		
		GridLayout gl = new GridLayout(12, false);
		//gl.marginHeight = 2;
		
		gl.marginHeight = 0;
		gl.marginTop = 2;
		gl.marginBottom = 5;
		
		gl.marginWidth = 2;
		gl.horizontalSpacing = 3;
		toolBar.setLayout(gl);	
		
		Label label = new Label(toolBar, SWT.CENTER);
		label.setText("Search:");		
		this.searchField = new Text(toolBar, SWT.SEARCH);
		searchField.setMessage(searchHint);

		searchField.addSelectionListener(new SelectionAdapter(){
			public void widgetDefaultSelected(SelectionEvent e){
				String searchingForClusterObject = searchField.getText();
				if(controller != null) {
					controller.findAndSelectClusterObject(searchingForClusterObject);
				}				
				/*K objectID = null;				
				if(clusterObjectManager != null) {
					objectID = clusterObjectManager.findClusterObject(searchingForClusterObject);
				}				
				if(objectID != null) {
					affinityPane.getDendrogram().setSelectedClusterObject(objectID);
					affinityPane.getCraigrogram().setSelectedClusterObject(objectID);
					affinityPane.getDendrogram().redraw();
					affinityPane.getCraigrogram().redraw();
				}*/
			}
		});		
	}
	
	public void setController(IAffinityController<K, V> controller) {
		this.controller = controller;
	}

	public void setSearchHint(String searchHint) {
		searchField.setMessage(searchHint);
	}
	
	public interface ClusterObjectComparator<V> {
		public int compare(V clusterObject, String identifier);
	}
}