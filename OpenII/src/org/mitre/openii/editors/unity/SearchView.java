/*
 *  Copyright 2011 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
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
package org.mitre.openii.editors.unity;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class SearchView  {

//    private UnityCanvas unityCanvas;



	public SearchView(UnityCanvas unity) {
//		unityCanvas = unity;
	}
	
	public void createSearchView(Composite parent) {
	
		GridLayout tableViewlayout = new GridLayout(1, false);
		tableViewlayout.marginHeight = 0;
		tableViewlayout.marginWidth = 0;
		tableViewlayout.verticalSpacing = 0;
		tableViewlayout.horizontalSpacing = 0;
		tableViewlayout.marginBottom = 0;
		parent.setLayout(tableViewlayout);


		Label TBD = new Label(parent, SWT.NONE);
		TBD.setText("TBD");
		parent.getParent().layout(true);

				
		addListeners();
	}

	private void addListeners() {
		
	}


}