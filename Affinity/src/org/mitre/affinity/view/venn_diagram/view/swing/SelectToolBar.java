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

package org.mitre.affinity.view.venn_diagram.view.swing;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SelectToolBar extends Composite{
	private Composite toolBar;
	private Text searchField;
	
	public SelectToolBar(Composite parent, int style){
		super(parent, style);		
		//this.dendrogram = dendrogramView;
		//this.craigrogram = craigrogramView;
		//this.schemas = schemas;
		//this.affinityPane = ap;
		this.createToolBar();
	}
	

	private void createToolBar() {
		setLayout(new GridLayout(1, true));
		toolBar = new Composite(this, SWT.NONE);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));		
		GridLayout gl = new GridLayout(12, false);
		gl.marginHeight = 2;
		gl.marginWidth = 2;
		gl.horizontalSpacing = 3;
		toolBar.setLayout(gl);	
		
		Label label = new Label(toolBar, SWT.CENTER);
		label.setText("Search:");		
		this.searchField = new Text(toolBar, SWT.SEARCH);
		searchField.setMessage("Schema Name");


		searchField.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {			
				//System.out.println("find: " + searchField.getText());

				//boolean found = false;
				//Integer schemaID = 0;
				
				/*for(Schema s: schemas){
					if(s.getName().equals(searchingForSchema)){
						//System.out.println("Found a match");
						found = true;
						schemaID = s.getId();
						break;
					}
				}
				*/
				//if(found){
				//	affinityPane.getDendrogram().setSelectedSchema(schemaID);
				//	affinityPane.getCraigrogram().setSelectedSchema(schemaID);
				//	affinityPane.getDendrogram().redraw();
				//	affinityPane.getCraigrogram().redraw();
				//}
			}
		});
		
	}
	
}
