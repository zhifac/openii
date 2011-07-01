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

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.widgets.schemaTree.SchemaTree;
import org.mitre.schemastore.model.terms.AssociatedElement;
import org.mitre.schemastore.model.terms.Term;
import org.mitre.schemastore.model.terms.Terms;

public class TreeView  {

    private Combo schemaSelector;  
    private UnityCanvas unityCanvas;
    private SchemaTree treeview = null;

    private Text textSearchTree;
    private Button treeAlpha;
    private Button treeTypes;
    private Button treeBase;
    private Image alphaIcon = OpenIIActivator.getImage("alpha.png");
    private Image seperatorIcon = OpenIIActivator.getImage("seperator.png");


	public TreeView(UnityCanvas unity) {
		unityCanvas = unity;
	}
	
	public void createTreeView(Composite parent) {
	
		GridLayout treeViewlayout = new GridLayout(1, false);
		treeViewlayout.marginHeight = 0;
		treeViewlayout.marginWidth = 0;
		treeViewlayout.verticalSpacing = 0;
		treeViewlayout.horizontalSpacing = 0;
		treeViewlayout.marginBottom = 0;
		parent.setLayout(treeViewlayout);
		
		Composite treeButtons = new Composite(parent, SWT.NONE);
	//	CoolBar treeButtons = new CoolBar(parent, SWT.NONE);
	    
	//	GridLayout treeButtonlayout = new GridLayout(9, false);
		RowLayout treeButtonlayout = new RowLayout();
		treeButtonlayout.center = true;
		treeButtons.setLayout(treeButtonlayout);
		treeButtons.setLayoutData(new GridData (SWT.FILL, SWT.TOP, true, false));
		
		schemaSelector = new Combo(treeButtons, SWT.READ_ONLY | SWT.DROP_DOWN);
		for(int index = 0; index < unityCanvas.getSchemaIDs().length; index++) {
			schemaSelector.add(unityCanvas.getSchemas()[index].getName());
		}
		schemaSelector.setToolTipText("Displayed Schema is " + unityCanvas.getSchemas()[0].getName());
		schemaSelector.select(0);
	//	schemaSelector.setLayoutData(new GridData (SWT.RIGHT, SWT.CENTER, true, false));
	//	schemaSelector.pack();
		
		treeTypes = new Button(treeButtons, SWT.TOGGLE);
		treeTypes.setToolTipText("Show types");
		treeTypes.setImage(OpenIIActivator.getImage("types.png"));
		treeTypes.setVisible(true);
	//	treeTypes.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
	
		treeBase = new Button(treeButtons, SWT.TOGGLE);
		treeBase.setToolTipText("Show base schema");
		treeBase.setImage(OpenIIActivator.getImage("baseSchema.png"));
		treeBase.setVisible(true);
	//	treeBase.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
	
	
		Label seperator = new Label(treeButtons, SWT.BITMAP);
		seperator.setImage(seperatorIcon);
	//	seperator.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
		
		treeAlpha = new Button(treeButtons, SWT.TOGGLE);
		treeAlpha.setToolTipText("Alphabetize");
		treeAlpha.setImage(alphaIcon);
		treeAlpha.setVisible(true);
	//	treeAlpha.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
	
		Label seperator3 = new Label(treeButtons, SWT.BITMAP);
		seperator3.setImage(seperatorIcon);
	
		Button treeFilter1 = new Button(treeButtons, SWT.TOGGLE);
		treeFilter1.setImage(OpenIIActivator.getImage("checkedFilter.png"));
		treeFilter1.setToolTipText("Filter out checked terms");
		treeFilter1.setVisible(true);
		treeFilter1.setEnabled(false);
	//	treeFilter1.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
	
		Button treeFilter2 = new Button(treeButtons, SWT.TOGGLE);
		treeFilter2.setImage(OpenIIActivator.getImage("greenFilter.png"));
		treeFilter2.setToolTipText("Filter out terms in good matches");
		treeFilter2.setVisible(true);
		treeFilter2.setEnabled(false);
	//	treeFilter2.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
	
		Button treeFilter3 = new Button(treeButtons, SWT.TOGGLE);
		treeFilter3.setImage(OpenIIActivator.getImage("yellowFilter.png"));
		treeFilter3.setToolTipText("Filter out terms in average matches");
		treeFilter3.setVisible(true);
		treeFilter3.setEnabled(false);
	//	treeFilter3.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
		
	
	
		treeview = new SchemaTree(parent,unityCanvas.getSchemaInfos()[0],unityCanvas.getSchemaModels()[0], true);
		treeview.setMenuManager(new SchemaTreeMenuManager(treeview.getTreeViewer(),unityCanvas));
		//parent.pack();
	
		Composite treeSearch = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		treeSearch.setLayoutData(gridData);
		GridLayout treeSearchlayout = new GridLayout(3, false);
		treeSearch.setLayout(treeSearchlayout);
		Label tslabel =new Label(treeSearch, SWT.NONE);
		tslabel.setText("Search:");
		textSearchTree = new Text(treeSearch, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		textSearchTree.setLayoutData(gridData);
				
		addListeners();
	}

	private void addListeners() {
		
		//drag source listener
	    DragSource source = new DragSource(treeview.getTreeViewer().getTree(), DND.DROP_LINK | DND.DROP_MOVE | DND.DROP_COPY);
	    source.setTransfer(new Transfer [] { FileTransfer.getInstance() });
	    source.addDragListener(
	    	new DragSourceListener () {
	        	String data[];
	        	String data2[] = new String[1];
	        	public void dragStart (DragSourceEvent event) { 
	        		unityCanvas.dragID = 1;
	        		event.image = null;
				      // Clean up any previous editor control
				      Control oldEditor = unityCanvas.getEditor().getEditor();
				      if (oldEditor != null)
				        oldEditor.dispose();
	////System.out.println("uid = " + treeview.getTreeViewer().getTree().getSelection()[0].getData("uid") + "\n");
					TreeItem items[] = treeview.getTreeViewer().getTree().getSelection();
					ArrayList<String> ids = new ArrayList<String>();
					unityCanvas.draggedCol = getTreeSchemaID();
					data2[0] = 	"" + items[0].getData("uid");						
					unityCanvas.dragElement = new AssociatedElement(unityCanvas.draggedCol,(Integer)items[0].getData("uid"),items[0].getText(),(String)items[0].getData("description"));
					for(int i = 0; i < items.length; i++)
					{
						Terms terms = unityCanvas.getInvertedVocab().getTerms(unityCanvas.draggedCol,(Integer)(items[i].getData("uid")));
						for(Term term : terms.getTerms())
							ids.add("" + term.getId());
					}
					String idStrings[] = new String[ids.size()];
					ids.toArray(idStrings);
					data = idStrings;
	        		if(data.length == 0) {
	        			data = new String[1];
	        			data[0] = " ";
	        		}
	        	} 
	        	public void dragFinished (DragSourceEvent event) {
	        	} 
	    		public void dragSetData (DragSourceEvent event) { 
	    			////System.out.println("testing5\n");
	    			////System.out.println("event.detail = " + event.detail + "\n");
					if(unityCanvas.eventDetail == DND.DROP_LINK) {
						event.data = data;
					} else {
						event.data = data2;
					}
	    		} 
	    	}
	    );		
	    
	    
		//search listener
		textSearchTree.addListener(SWT.KeyUp, new Listener() {  
		     public void handleEvent(Event e) {  
					if(e.character==SWT.CR) treeview.searchFor(textSearchTree.getText()); }});
	
		
		// sorting alphabetically
		treeAlpha.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
		    	  boolean alpha = treeview.isAlphabetized();
		    	  treeview.doAlphabetized(!alpha);
			  		  if(alpha) {
			  			  //treeAlpha.setImage(OpenIIActivator.getImage("alpha.png"));
			  			  treeAlpha.setToolTipText("Alphabetize");
			  		  } else {
			  			  //treeAlpha.setImage(OpenIIActivator.getImage("alphaOFF.png")); 		  			  
			  			  treeAlpha.setToolTipText("Stop Alphabetizing");
			  		  }
			}
		});
		
		// show types
		treeTypes.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
		    	  boolean types = treeview.showTypes();
		    	  treeview.doShowTypes(!types);
			  		  if(types) {
			  			//treeTypes.setImage(OpenIIActivator.getImage("types.png"));
			  			treeTypes.setToolTipText("Show types");
			  		  } else {
			  			//treeTypes.setImage(OpenIIActivator.getImage("typesOFF.png")); 		  			  
			  			treeTypes.setToolTipText("Hide types");
			  		  }
			}
		});
		
		// show base schema
		treeBase.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
		    	  boolean base = treeview.showBaseSchemas();
		    	  treeview.doShowBaseSchemas(!base);
			  		  if(base) {
			  			//treeBase.setImage(OpenIIActivator.getImage("baseSchema.png"));
			  			treeBase.setToolTipText("Show base schema");
			  		  } else {
			  			//treeBase.setImage(OpenIIActivator.getImage("baseSchemaOFF.png")); 		  			  
			  			treeBase.setToolTipText("Hide base schema");
			  		  }
			}
		});
		
		// choose schema to view 
		schemaSelector.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int index = schemaSelector.getSelectionIndex();
				//comboLabel.setToolTipText(schemas[index].getName());
				schemaSelector.setToolTipText("Displayed Schema is " + unityCanvas.getSchemas()[index].getName());
				treeview.setSchema(unityCanvas.getSchemaInfos()[index],unityCanvas.getSchemaModels()[index]);
	
			}
		});
	}
	
	public void selectSchema(int index) {
		schemaSelector.select(index);
		schemaSelector.setToolTipText("Displayed Schema is " + unityCanvas.getSchemas()[index].getName());
		treeview.setSchema(unityCanvas.getSchemaInfos()[index],unityCanvas.getSchemaModels()[index]);	    
	}
	
	public void searchTreeByID(Integer id) {
		treeview.searchFor(id);
	}
	
	public Integer getTreeSchemaID() {
		return unityCanvas.getSchemas()[schemaSelector.getSelectionIndex()].getId();
	}


}