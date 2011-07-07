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
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.model.terms.Term;


public class TableView  {
	
    private UnityCanvas unityCanvas;
	private Table tableview = null;
    private Text textSearchTable;
    private Combo sortSelector;
	private Combo colorSelectorT;
    private Button colorsettingsT;
    private boolean showTextTableView = true;
    private int[] ordering;

    
	private Color gray;
	private Color lightBlue;


    private Image CheckIcon = OpenIIActivator.getImage("checkicon.png");
    private Image showTextIcon = OpenIIActivator.getImage("showtext.png");
    private Image seperatorIcon = OpenIIActivator.getImage("seperator.png");



	public TableView(UnityCanvas unity) {
		unityCanvas = unity;
		gray =  unityCanvas.getDisplay().getSystemColor(SWT.COLOR_GRAY);
		lightBlue = new Color(unity.getDisplay(), 225, 225, 255);
		
		//set initial ordering equal to default ordering
		Term[] allterms = unityCanvas.getVocabulary().getTerms();
		ordering = new int[allterms.length];
		for(int i = 0; i < allterms.length; i++) {
			ordering[i] = allterms[i].getId();
		}

	}
	
    

	public void createTableView(Composite parent) {
		
		GridLayout tableViewlayout = new GridLayout(1, false);
		tableViewlayout.marginHeight = 0;
		tableViewlayout.marginWidth = 0;
		tableViewlayout.verticalSpacing = 0;
		tableViewlayout.horizontalSpacing = 0;
		tableViewlayout.marginBottom = 0;
		parent.setLayout(tableViewlayout);
		
		Composite tableButtons = new Composite(parent, SWT.NONE);
		RowLayout tableButtonlayout = new RowLayout();
		tableButtonlayout.center = true;
		tableButtons.setLayout(tableButtonlayout);
		tableButtons.setLayoutData(new GridData (SWT.FILL, SWT.TOP, true, false));

		colorSelectorT = new Combo(tableButtons, SWT.READ_ONLY | SWT.DROP_DOWN);
//		colorSelectorT.setForeground(gray);
		colorSelectorT.add("Not Colored");
		colorSelectorT.add("Color by Element Type");
		colorSelectorT.add("Instance Count");
		colorSelectorT.add("Canonical Match");
		colorSelectorT.add("Match Strength (strongest)");
		colorSelectorT.add("Match Strength (weakest)");
		colorSelectorT.setToolTipText("No coloring applied");
		colorSelectorT.select(0);
		colorSelectorT.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorSelectorT.select(0);
			}
		});

		
		colorsettingsT = new Button(tableButtons, SWT.PUSH);
		colorsettingsT.setImage(OpenIIActivator.getImage("color_settings.png"));
		colorsettingsT.setToolTipText("Color Settings");
//		colorsettingsT.setEnabled(false);
		colorsettingsT.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorsettingsT.setSelection(false);
			}
		});

		Button showtext = new Button(tableButtons, SWT.TOGGLE);
		showtext.setImage(showTextIcon);
		showtext.setToolTipText("Hide terms");
		showtext.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				showTextTableView = !showTextTableView;
				tableview.clearAll();
			}
		});				



		Label seperator3 = new Label(tableButtons, SWT.BITMAP);
		seperator3.setImage(seperatorIcon);
		
		sortSelector = new Combo(tableButtons, SWT.READ_ONLY | SWT.DROP_DOWN);
//		sortSelector.setForeground(gray);
		sortSelector.add("Not Sorted");
		sortSelector.add("Sort by Schema Matches");
		sortSelector.add("Sort by Match Strength");
		sortSelector.add("Alphabetical by Vocabulary");
		for(int index = 0; index < unityCanvas.getSchemaIDs().length; index++) {
			sortSelector.add("Alphabetical by " + unityCanvas.getSchemas()[index].getName());
		}
		for(int index = 0; index < unityCanvas.getSchemaIDs().length; index++) {
			sortSelector.add("Structurally by " + unityCanvas.getSchemas()[index].getName());
		}
		sortSelector.setToolTipText("No sorting applied");
		sortSelector.select(0);
		sortSelector.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				sortSelector.select(0);
			}
		});

		
		Label seperator2 = new Label(tableButtons, SWT.BITMAP);
		seperator2.setImage(seperatorIcon);
//		seperator2.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));

		Button tableFilter1 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter1.setImage(OpenIIActivator.getImage("checkedFilter.png"));
		tableFilter1.setToolTipText("Filter out checked synsets");
//		tableFilter1.setEnabled(false);
//		tableFilter1.setVisible(true);
//		tableFilter1.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
//		tableFilter1.pack();

		Button tableFilter2 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter2.setImage(OpenIIActivator.getImage("greenFilter.png"));
		tableFilter2.setToolTipText("Filter out synsets with good matches");
//		tableFilter2.setEnabled(false);
//		tableFilter2.setVisible(true);
//		tableFilter2.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
//		tableFilter2.pack();

		Button tableFilter3 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter3.setImage(OpenIIActivator.getImage("yellowFilter.png"));
		tableFilter3.setToolTipText("Filter out synsets with average matches");
//		tableFilter3.setEnabled(false);
//		tableFilter3.setVisible(true);
//		tableFilter3.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
//		tableFilter3.pack();

		Button tableFilter4 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter4.setImage(OpenIIActivator.getImage("singleton.png"));
		tableFilter4.setToolTipText("Filter out singletons");		
//		tableFilter4.setEnabled(false);

		
		
		tableview = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.VIRTUAL);
		tableview.setData("name", "tableview");
		tableview.setHeaderVisible(true);
		tableview.setLinesVisible(true);
		tableview.setToolTipText("");
		GridData gridData = new GridData();
		gridData.horizontalSpan = 0;
		gridData.minimumHeight = 0;
		gridData.verticalSpan = 0;
		gridData.verticalIndent = 0;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		tableview.setLayoutData(gridData);
		
		// creating one column for the status and the vocabulary
		TableColumn tempC;
		tempC = new TableColumn(tableview, SWT.NONE);
		tempC.setWidth(26);
		tempC.setImage(CheckIcon);
		tempC.setMoveable(false);
		tempC.setData("uid",new Integer(-202));
		tempC.setToolTipText("Checked");
		tempC = new TableColumn(tableview, SWT.NONE);
		tempC.setText("Vocabulary");
		tempC.setMoveable(false);
		tempC.setData("uid",new Integer(-201));
		tempC.setWidth(100);
		//tempC.addListener(SWT.Selection, sortAlphabeticallyListener);
		tempC.setToolTipText("Vocabulary");
		// creating one column for each schema
		for (int i = 0; i < unityCanvas.getSchemas().length; i++) {
			tempC = new TableColumn(tableview, SWT.NONE);
			tempC.setText(unityCanvas.getSchemas()[i].getName());
			tempC.setData("uid",new Integer(unityCanvas.getSchemas()[i].getId()));
			tempC.setWidth(100);
			tempC.setMoveable(true);
			//tempC.addListener(SWT.Selection, sortAlphabeticallyListener);
			tempC.setToolTipText(unityCanvas.getSchemas()[i].getName());
		}
		
		Listener dataCallbackListener = new Listener() {

	        public void handleEvent(Event event) {
	            TableItem item = (TableItem)event.item;
	        	int ID = ordering[tableview.indexOf(item)];
				item.setData("uid", ID);
				if(unityCanvas.getCheckStatus().containsKey(ID)) { 
					item.setImage(0,CheckIcon);
				}
				unityCanvas.populateRow(item,showTextTableView);
				item.setBackground(1, lightBlue);
	        }
	    };
	    
	    tableview.setItemCount(unityCanvas.getVocabulary().getTerms().length);
	    tableview.addListener(SWT.SetData, dataCallbackListener);

		
		//tableview.pack();

		Composite tableSearch = new Composite(parent, SWT.NONE);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		tableSearch.setLayoutData(gridData);
		GridLayout tableSearchlayout = new GridLayout(3, false);
		tableSearch.setLayout(tableSearchlayout);
		Label tslabel =new Label(tableSearch, SWT.NONE);
		tslabel.setText("Search:");
		textSearchTable = new Text(tableSearch, SWT.BORDER);
//		textSearchTable.setEnabled(false);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		textSearchTable.setLayoutData(gridData);
		
		
		//add listeners
		addListeners();
	}
	
	private void addListeners() {
		
		
        DragSource source = new DragSource(tableview, DND.DROP_LINK | DND.DROP_MOVE | DND.DROP_COPY);
        source.setTransfer(new Transfer [] { FileTransfer.getInstance() });
        source.addDragListener(
        	new DragSourceListener () {
    			String[] data = new String[1];
            	String data2[] = new String[1];
    			public void dragStart (DragSourceEvent event) { 
    				event.image = null;
    			      // Clean up any previous editor control
    			      Control oldEditor = unityCanvas.getEditor().getEditor();
    			      if (oldEditor != null)
    			        oldEditor.dispose();
    			    unityCanvas.dragID = 2;
        			TableItem selected[] = tableview.getSelection();
        			String ids[] = new String[selected.length];
        			for(int i = 0; i < selected.length; i++)
        			{
        				ids[i] = "" + selected[i].getData("uid");
        			}
    				data = ids;
    				if(unityCanvas.draggedCol > -1)
    					data2[0] = "" + unityCanvas.getVocabulary().getTerm((Integer)unityCanvas.selectedItem.getData("uid")).getElements()[0].getElementID();
    			} 
    			public void dragFinished (DragSourceEvent event) { 
    			} 
    			public void dragSetData (DragSourceEvent event) { 
    				if(unityCanvas.eventDetail == DND.DROP_LINK) {
    					event.data = data;
    				} else {
    					event.data = data2;
    				}
    			}
        	}
        );
        
		DropTarget target = new DropTarget (tableview, DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK); 
		target.setTransfer(new Transfer [] { FileTransfer.getInstance() });
		target.addDropListener(new DropTargetListener () { 
			public void dragEnter(DropTargetEvent event) { 
				event.detail = DND.DROP_COPY;					
				unityCanvas.eventDetail = DND.DROP_COPY;
			} 
			public void dragOver(DropTargetEvent event) { 
				if(FileTransfer.getInstance().isSupportedType(event.currentDataType)){
					event.detail = DND.DROP_LINK;					
					unityCanvas.eventDetail = DND.DROP_LINK;
				} else {
					event.detail = DND.DROP_NONE;
					unityCanvas.eventDetail = DND.DROP_NONE;
				}
				
				if(event.item instanceof TableItem){
						Rectangle colrec = null;
						int[] order = tableview.getColumnOrder();
						for(int i = 0; i < tableview.getColumnCount(); i++) {
							colrec = ((TableItem)event.item).getBounds(order[i]);
							if(colrec.x + colrec.width > tableview.getDisplay().map(null, tableview, event.x, event.y).x ){
//								////System.out.println("col = " + order[i] + "\n");
								if(order[i] > 1){
//									//System.out.println(workspaceTable.getColumn(order[i]).getData("uid") + " != " + draggedCol + "?\n");
									if(tableview.getColumn(order[i]).getData("uid").equals(unityCanvas.draggedCol)){
										event.detail = DND.DROP_COPY;										
										unityCanvas.eventDetail = DND.DROP_COPY;
										if(unityCanvas.dragID == 2){
											event.detail = unityCanvas.interfaceState;										
											unityCanvas.eventDetail = unityCanvas.interfaceState;											
										}
									} 
								}
								break;
							}
						}
					}
			}
			public void dragOperationChanged(DropTargetEvent event) { 				
				//System.out.println("change");
				
			}
			public void dragLeave(DropTargetEvent event) { 
				//System.out.println("leave");
		    } 
			public void dropAccept(DropTargetEvent event) { 
			} 
			public void drop(DropTargetEvent event) { 
				if(event.detail == DND.DROP_LINK){
					Table ttarget = (Table)((DropTarget)event.widget).getControl();
					int posIndex = ttarget.getItemCount();
					if(ttarget.getItemCount() > 0) {
						TableItem dropTarget = (TableItem)event.item;
						if(dropTarget != null) { posIndex = ttarget.indexOf(dropTarget); }
						else if(ttarget.getDisplay().map(null, ttarget, event.x, event.y).y  < ttarget.getHeaderHeight()){posIndex = 0;}
					}
					String[] data = ((String[])event.data);
					if(data.length == 1 && data[0].equals(" ")) return;
					ArrayList<Integer> termIDs = new ArrayList<Integer>();
					for(String item : data) termIDs.add(Integer.parseInt(item));
					changeOrder(termIDs, posIndex);
				} else if(event.detail == DND.DROP_COPY || event.detail == DND.DROP_MOVE){
					Object target = event.item;
					if(target instanceof TableItem){
						Integer vocabID = (Integer)(((TableItem) target).getData("uid"));
						if(!vocabID.equals(unityCanvas.draggedRow)) {
							unityCanvas.addTerm(vocabID, unityCanvas.dragElement);
							if(event.detail == DND.DROP_MOVE){
								unityCanvas.deleteTerm(unityCanvas.draggedRow, unityCanvas.dragElement);
							}
						}
					}		
				} else {
					
				}
			} 
		});
		
		tableview.addMouseListener(new ClickListener(unityCanvas));
		tableview.addListener(SWT.MenuDetect, new MenuListener(unityCanvas));
		tableview.addListener(SWT.MouseHover, new HoverListener(unityCanvas));			

	}
	
	public void changeOrder(ArrayList<Integer> ids, int position) {
		int offset = 0;
		int newordering[] = new int[ordering.length];
		int index = 0;
		
		for(; index + offset < position && index < ordering.length; index++) {
			if(ids.contains(new Integer(ordering[index]))) {
				offset--;
			} else {
				newordering[index+offset] = ordering[index];
			}
		}
		for(Integer id : ids) {
			newordering[index+offset] = id;
			offset++;
		}
		for(; index < ordering.length; index++)
		{
			if(ids.contains(new Integer(ordering[index]))) {
				offset--;
			} else {
				newordering[index+offset] = ordering[index];
			}			
		}
		ordering = newordering;
		sortSelector.select(0);
		resetTableView();
	}
	
	public void adjustTableSize(int increment) {
		tableview.setItemCount(tableview.getItemCount() + increment);
	}
	
	public void resetTableView() {
		tableview.clearAll();
	}
	
	public Menu getMenu() {
		return tableview.getMenu();
	}
}
