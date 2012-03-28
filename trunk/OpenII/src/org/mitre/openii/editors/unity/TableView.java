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
import java.util.Arrays;

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
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;
import org.mitre.schemastore.model.terms.AssociatedElement;
import org.mitre.schemastore.model.terms.Term;



public class TableView  {
	
    private UnityCanvas unityCanvas;
	private Table tableview = null;
    private Text textSearchTable;
    private Combo sortSelector;
	private Combo colorSelectorT;
    private Button colorsettingsT;
    private boolean showTextTableView = true;
    private ArrayList<Integer> ordering = new ArrayList<Integer>();
    private ArrayList<Integer> filteredOrdering = new ArrayList<Integer>();
    private ArrayList<Integer> searchResultsVID = new ArrayList<Integer>();
    private ArrayList<String> searchResultsEID = new ArrayList<String>();
    private ArrayList<Integer> searchResultsVIDd = new ArrayList<Integer>();
    private ArrayList<String> searchResultsEIDd = new ArrayList<String>();
    private int sortColumn = -1;
    private int sortState = -1;
    private boolean sortReverse = false;
    private boolean checkFilter = false;
    private boolean greenFilter = false;
    private boolean yellowFilter = false;
    private boolean singletonFilter = false;
    private Double greenAt = 0.7; //should be defined in color selector
    private Double yellowAt = 0.7; //should be defined in color selector
    
	private Color gray;
	private Color darkgray;
	private Color lightBlue;
	private Color yellow;
	private Color orange;


    private Image CheckIcon = OpenIIActivator.getImage("checkicon.png");
    private Image showTextIcon = OpenIIActivator.getImage("showtext.png");
    private Image seperatorIcon = OpenIIActivator.getImage("seperator.png");



	public TableView(UnityCanvas unity) {
		unityCanvas = unity;
		gray =  unityCanvas.getDisplay().getSystemColor(SWT.COLOR_GRAY);
		darkgray =  unityCanvas.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY);
		lightBlue = new Color(unity.getDisplay(), 225, 225, 255);
		yellow = unityCanvas.getDisplay().getSystemColor(SWT.COLOR_YELLOW); 
		orange = new Color(unity.getDisplay(), 255, 200, 145); 

		
		//set initial ordering equal to default ordering
		Term[] allterms = unityCanvas.getVocabulary().getTerms();
		for(int i = 0; i < allterms.length; i++) {
			ordering.add(allterms[i].getId());
			filteredOrdering.add(allterms[i].getId());
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
		sortSelector.add("Sort by Checked");
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
				int selection = sortSelector.getSelectionIndex();
				if(selection == 0) {
					//nothing to do
				}
				else if(selection == 1) 
				{
					numberOfMatchesSort();
				} else if(selection == 2) 
				{
					matchStrengthSort();
				} else if(selection == 3) 
				{
					checkedSort(false);
				} else if(selection == 4) 
				{
					alphebetize(1, false);
				} else {
					int count = tableview.getColumnCount() - 2;
					if(selection < 5 + count ) {
						alphebetize(selection - 3, false);
					} else {
						structureSort(selection - 3 - count);
					}
				}
			}
		});

		
		Label seperator2 = new Label(tableButtons, SWT.BITMAP);
		seperator2.setImage(seperatorIcon);
//		seperator2.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));

		Button tableFilter1 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter1.setImage(OpenIIActivator.getImage("checkedFilter.png"));
		tableFilter1.setToolTipText("Filter out checked synsets");
//		tableFilter1.setEnabled(false);
		tableFilter1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				checkFilter = !checkFilter;
				filter();
			}
		});				

		Button tableFilter2 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter2.setImage(OpenIIActivator.getImage("greenfilter.png"));
		tableFilter2.setToolTipText("Filter out synsets with good matches");
//		tableFilter2.setEnabled(false);
		tableFilter2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				greenFilter = !greenFilter;
				filter();
			}
		});				

		Button tableFilter3 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter3.setImage(OpenIIActivator.getImage("yellowfilter.png"));
		tableFilter3.setToolTipText("Filter out synsets with average matches");
//		tableFilter3.setEnabled(false);
		tableFilter3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				yellowFilter = !yellowFilter;
				filter();
			}
		});				

		Button tableFilter4 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter4.setImage(OpenIIActivator.getImage("singleton.png"));
		tableFilter4.setToolTipText("Filter out singletons");		
//		tableFilter4.setEnabled(false);
		tableFilter4.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				singletonFilter = !singletonFilter;
				filter();
			}
		});				
		
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
		tempC.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				checkSort();
			}
		});				

		tempC.setToolTipText("Checked");
		tempC = new TableColumn(tableview, SWT.NONE);
		tempC.setText("Vocabulary");
		tempC.setMoveable(false);
		tempC.setData("uid",new Integer(-201));
		tempC.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				clickSort(-201);
			}
		});				
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
			tempC.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					clickSort((Integer)e.widget.getData("uid"));
				}
			});				
		}
		
		Listener dataCallbackListener = new Listener() {

	        public void handleEvent(Event event) {
	            TableItem item = (TableItem)event.item;
	        	int ID = filteredOrdering.get(tableview.indexOf(item));
				item.setData("uid", ID);
				if(unityCanvas.getCheckStatus().containsKey(ID)) { 
					item.setImage(0,CheckIcon);
				}
				unityCanvas.populateRow(item,showTextTableView);
				item.setBackground(1, lightBlue);
				if(searchResultsVID.contains(new Integer(ID))) {
					item.setBackground(1, yellow);					
				} else if (searchResultsVIDd.contains(new Integer(ID))) {
					item.setBackground(1, orange);					
				}

				for(TableItem wItem : unityCanvas.getWorkspace().getTable().getItems())
				{
					if(wItem.getData("uid").equals(ID)) {
						item.setForeground(darkgray);
					}
				}
				
				//search results on elements (may need to remove if performance is bad)
				for(int j = 2; j < tableview.getColumnCount(); j++) {
					Integer schemaID = ((Integer)tableview.getColumn(j).getData("uid")).intValue();
					AssociatedElement[] elements = unityCanvas.getVocabulary().getTerm((Integer)item.getData("uid")).getElements();
					for(int i = 0; i < elements.length; i++) {
						if( (!item.getBackground(j).equals(yellow)) && (elements[i].getSchemaID()).equals(schemaID)) {
							if(searchResultsEID.contains(elements[i].getSchemaID() + "#" + elements[i].getElementID())) {
								item.setBackground(j, yellow);					
							} else if (searchResultsEIDd.contains(elements[i].getSchemaID() + "#" + elements[i].getElementID())) {
								item.setBackground(j, orange);					
							}												
						}						
					}
						
				}
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
		textSearchTable.addListener(SWT.KeyUp, new Listener() {  
		     public void handleEvent(Event e) {  
					if(e.character==SWT.CR) search(textSearchTable.getText()); }});
	

		
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
										if(unityCanvas.dragID == 2 || unityCanvas.dragID == 3){
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
					int posIndex = ttarget.getItemCount() - 1;
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
						if(!vocabID.equals(unityCanvas.draggedRow) && unityCanvas.dragElement != null) {
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

	public void changeOrder(ArrayList<Integer> ids, int positionIn) {

		Integer id;	
		int position = -1;
		//find position in big list
		for(int i = 0; position == -1 && i < ordering.size(); i++) {
			if(ordering.get(i).equals(filteredOrdering.get(positionIn))) {
				position = i;
			}
		}
		
		int curPosition = position;		
		
		for(int i = ordering.size(); i  > curPosition+1; i--) {
			if(ids.contains(ordering.get(i-1))) {				
				id = ordering.remove(i-1);
				ordering.add(position,id);
				curPosition++;
				i++;
			}
		}
		curPosition = position;
		for(int i = 0; i < curPosition; i++) {
			if(ids.contains(ordering.get(i))) {				
				id = ordering.remove(i);
				ordering.add(position,id);
				curPosition--;
				i--;
			}
		}

		ArrayList<Integer> newFiltered = new ArrayList<Integer>();
		for(int i = 0; i < ordering.size(); i++) {
			id = ordering.get(i);
			if(filteredOrdering.contains(id)) {
				newFiltered.add(id);
			}
		}
		filteredOrdering = newFiltered;

		
		sortSelector.select(0);
		sortColumn = -1;
		sortReverse = false;
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
	
	public void alphebetize(int column, boolean reverse) {
				
		boolean placed = false;
		ArrayList<Integer> newOrder = new ArrayList<Integer>();
		ArrayList<String> newOrderValues = new ArrayList<String>();
		Integer schemaID = (Integer)tableview.getColumn(column).getData("uid");
		Integer id = ordering.get(0);
		AssociatedElement[] elements = unityCanvas.getVocabulary().getTerm(id).getAssociatedElements(schemaID);
		String text = "";
		for(int i = 0; i < elements.length; i++)
		{
			text += elements[i].getName();
		}
		newOrder.add(id);
		newOrderValues.add(text);
		
		for(int i = 1; i < ordering.size(); i++) {
			placed = false;
			id = ordering.get(i);
			elements = unityCanvas.getVocabulary().getTerm(id).getAssociatedElements(schemaID);
			text = "";
			for(int j = 0; j < elements.length; j++)
			{
				text += elements[j].getName();
			}
			
			for(int j = 0; !placed && j < newOrder.size(); j++) {
				if(reverse) {
					sortState = 1;
					if(newOrderValues.get(j).equals("") || (!text.equals("") && text.compareToIgnoreCase(newOrderValues.get(j)) > 0)) {
						newOrder.add(j,id);
						newOrderValues.add(j,text);
						placed = true;
					}						
				} else {
					sortState = 0;
					if(newOrderValues.get(j).equals("") || (!text.equals("") && text.compareToIgnoreCase(newOrderValues.get(j)) < 0)) {
						newOrder.add(j,id);
						newOrderValues.add(j,text);
						placed = true;
					}						
				}								
			}

			if(!placed) {
				newOrder.add(id);
				newOrderValues.add(text);				
			}			
		}
		
		if(newOrder.size() == ordering.size()) {
			ordering = newOrder;
			
			ArrayList<Integer> newFiltered = new ArrayList<Integer>();
			for(int i = 0; i < ordering.size(); i++) {
				id = ordering.get(i);
				if(filteredOrdering.contains(id)) {
					newFiltered.add(id);
				}
			}
			filteredOrdering = newFiltered;

		}		
		sortSelector.select(column + 3);
		sortColumn = column;
		sortReverse = reverse;
		resetTableView();
	}

	public void checkSort() {
		checkedSort(sortReverse && sortColumn == 0);
	}
	
	public void checkedSort(boolean reverse) {
	
		Integer id;
		ArrayList<Integer> checked = new ArrayList<Integer>();
		ArrayList<Integer> notChecked = new ArrayList<Integer>();
		
		for(int i = 0; i < ordering.size(); i++) {
			id = ordering.get(i);
			if(unityCanvas.getCheckStatus().containsKey(id)) {
				checked.add(id);
			} else {
				notChecked.add(id);
			}
		}

		ordering.clear();
		if(reverse) {
			ordering.addAll(notChecked);
			ordering.addAll(checked);
		} else {
			ordering.addAll(checked);
			ordering.addAll(notChecked);
		}

		ArrayList<Integer> newFiltered = new ArrayList<Integer>();
		for(int i = 0; i < ordering.size(); i++) {
			id = ordering.get(i);
			if(filteredOrdering.contains(id)) {
				newFiltered.add(id);
			}
		}
		filteredOrdering = newFiltered;
			
		sortReverse = !reverse;
		sortSelector.select(3);
		sortColumn = 0;
		resetTableView();
	}

	public void matchStrengthSort() {

		boolean placed = false;
		ArrayList<Integer> newOrder = new ArrayList<Integer>();
		ArrayList<Double> newOrderValues = new ArrayList<Double>();
		ArrayList<AssociatedElement> assElements = new ArrayList<AssociatedElement>();

		Integer id;
		double strength;
		ArrayList<MappingCell> mappingCellList;
		
		for(int i = 0; i < ordering.size(); i++) {
			placed = false;
			id = ordering.get(i);
			strength = 0;
			assElements = new ArrayList<AssociatedElement> (Arrays.asList((unityCanvas.getVocabulary().getTerm(id).getElements())));
			mappingCellList = OpenIIManager.getAssociatedMappingCells(unityCanvas.getVocabulary().getProjectID(),assElements);
			for (int k = 0; k < mappingCellList.size(); k++) {
				strength += mappingCellList.get(k).getScore();
			}
			if(mappingCellList.size() != 0) {
				strength = strength / (double)mappingCellList.size();
			}
			for(int j = 0; !placed && j < newOrder.size(); j++) {
				if(i == 0 || strength > newOrderValues.get(j).doubleValue()) {
					placed = true;
					newOrder.add(j,id);
					newOrderValues.add(j,strength);
				}
			}

			if(!placed) {
				newOrder.add(id);
				newOrderValues.add(strength);				
			}			
		}
		
		if(newOrder.size() == ordering.size()) {
			ordering = newOrder;

			ArrayList<Integer> newFiltered = new ArrayList<Integer>();
			for(int i = 0; i < ordering.size(); i++) {
				id = ordering.get(i);
				if(filteredOrdering.contains(id)) {
					newFiltered.add(id);
				}
			}
			filteredOrdering = newFiltered;

		}		
		sortSelector.select(2);
		sortColumn = -1;
		sortReverse = false;
		resetTableView();

	}
	
	public void numberOfMatchesSort() {

		boolean placed = false;
		ArrayList<Integer> newOrder = new ArrayList<Integer>();
		ArrayList<Integer> newOrderValues = new ArrayList<Integer>();
		Integer id = ordering.get(0);
		int size = unityCanvas.getVocabulary().getTerm(id).getElements().length;
		newOrder.add(id);
		newOrderValues.add(size);
		
		for(int i = 1; i < ordering.size(); i++) {
			placed = false;
			id = ordering.get(i);
			size = unityCanvas.getVocabulary().getTerm(id).getElements().length;

			for(int j = 0; !placed && j < newOrder.size(); j++) {
				if(size > newOrderValues.get(j)) {
					placed = true;
					newOrder.add(j,id);
					newOrderValues.add(j,size);
				}
			}

			if(!placed) {
				newOrder.add(id);
				newOrderValues.add(size);				
			}			
		}
		
		if(newOrder.size() == ordering.size()) {
			ordering = newOrder;
			
			ArrayList<Integer> newFiltered = new ArrayList<Integer>();
			for(int i = 0; i < ordering.size(); i++) {
				id = ordering.get(i);
				if(filteredOrdering.contains(id)) {
					newFiltered.add(id);
				}
			}
			filteredOrdering = newFiltered;

		}		
		sortSelector.select(1);
		sortColumn = -1;
		sortReverse = false;
		resetTableView();

	}
	
	public void structureSort(int column) {

		ArrayList<Integer> newOrder = new ArrayList<Integer>();
		HierarchicalSchemaInfo schema = new HierarchicalSchemaInfo(unityCanvas.getSchemaInfos()[column-2],unityCanvas.getSchemaModels()[column-2]);
		for(SchemaElement element : schema.getRootElements())
		{				
			childrenSorter(element,schema,newOrder);			
		}
		//now add in all other terms
		newOrder.addAll(ordering);
		ordering = newOrder;
		
		Integer id;
		ArrayList<Integer> newFiltered = new ArrayList<Integer>();
		for(int i = 0; i < ordering.size(); i++) {
			id = ordering.get(i);
			if(filteredOrdering.contains(id)) {
				newFiltered.add(id);
			}
		}
		filteredOrdering = newFiltered;

		
		sortSelector.select(column + 1 + tableview.getColumnCount());
		sortState = 2;
		sortColumn = column;
		sortReverse = false;
		resetTableView();

	}
	
	private void childrenSorter(SchemaElement element, HierarchicalSchemaInfo schema, ArrayList<Integer> newOrder)
	{
		int id;
		for(int i = 0; i < ordering.size(); i++) {
			id = ordering.get(i);
			AssociatedElement[] elements = unityCanvas.getVocabulary().getTerm(id).getAssociatedElements(schema.getSchema().getId());
			for(int j = 0; j < elements.length; j++) {
				if(elements[j].getElementID().equals(element.getId())) {
					newOrder.add(id);
					ordering.remove(i);
				}
			}
		}
		//now recursively sort children
		for(SchemaElement childElement : schema.getChildElements(((SchemaElement)element).getId())) {
			childrenSorter(childElement,schema,newOrder);
		}
	}
	
	public void clickSort(int columnID) {
		int column = -1;
		
		//find column
		TableColumn[] cols = tableview.getColumns();
		for(int i = 0; i < cols.length; i++) {
			if(cols[i].getData("uid").equals(columnID))
			{
				column = i;
			}
		}
		if(column == -1) return; //column not found 
		
		//check for toggle
		if(column == sortColumn) {
			sortState++;
			if(sortState > 2) sortState = 0;
			if(sortState > 1 && column == 1) sortState = 0;
		} else {
			sortState = 0;
		}
		sortColumn = column;
		
		//sort
		if(sortState == 0) alphebetize(column, false);
		else if(sortState == 1) alphebetize(column, true);
		else if(sortState == 2) structureSort(column);
		sortReverse = (sortState == 1);

	}
	
	public void filter()
	{
		int id;
		ArrayList<AssociatedElement> assElements;
		ArrayList<MappingCell> mappingCellList;
		boolean isYellow = false;
		boolean isGreen = false;
		filteredOrdering.clear();
		for(int i = 0; i < ordering.size(); i++) {
			isYellow = false;
			isGreen = false;
			id = ordering.get(i);
			if(checkFilter && unityCanvas.getCheckStatus().containsKey(id)) {
//				System.err.println(unityCanvas.getVocabulary().getTerm(id).getName() + " is checked");
			} 
			else {
				assElements = new ArrayList<AssociatedElement> (Arrays.asList(unityCanvas.getVocabulary().getTerm(id).getElements())); 
				if(singletonFilter && assElements.size() < 2) {}
				else {
					mappingCellList = OpenIIManager.getAssociatedMappingCells(unityCanvas.getVocabulary().getProjectID(),assElements);
					for (int k = 0; k < mappingCellList.size() && !isYellow && !isGreen; k++) {
						if(mappingCellList.get(k).getScore() >= yellowAt)
						{
							isYellow = true;
						}
						if(mappingCellList.get(k).getScore() >= greenAt)
						{
							isGreen = true;
						}
					}
					if(yellowFilter && isYellow) {}
					else if (greenFilter && isGreen) {} 
					else {
						filteredOrdering.add(id);
					}
				}
			}
		}
		
		tableview.setItemCount(filteredOrdering.size());
		resetTableView();
		/*
		int selection = sortSelector.getSelectionIndex();
		if(selection == 0) {
			resetTableView();
		}
		else if(selection == 1) 
		{
			numberOfMatchesSort();
		} else if(selection == 2) 
		{
			matchStrengthSort();
		} else if(selection == 3) 
		{
			checkedSort(!sortReverse);
		} else if(selection == 4) 
		{
			resetTableView();
			alphebetize(1, sortReverse);
		} else {
			int count = tableview.getColumnCount() - 2;
			if(selection < 5 + count ) {
				resetTableView();
				alphebetize(selection - 3, sortReverse);
			} else {
				structureSort(selection - 3 - count);
			}
		}*/
		
	}
	
	/** Search for elements that match the keyword */
	public void search(String keyword)
	{		
		int moveIndex = filteredOrdering.size();
		int moveID = -1; 
		
		searchResultsVID.clear();
		searchResultsEID.clear();
		searchResultsVIDd.clear();
		searchResultsEIDd.clear();
				
		// Generate the keyword
		if(keyword.equals("")) return;
		keyword = ".*" + keyword + ".*";
		if(keyword.toLowerCase().equals(keyword)) keyword = "(?i)" + keyword;

		// Determine what vocabulary terms match search criteria
		int vocabLength = unityCanvas.getVocabulary().getTerms().length;
		for(int i = vocabLength - 1; i >= 0; i--)
		{	
			Term term = unityCanvas.getVocabulary().getTerm(ordering.get(i));
			// Check to see if element name or description matches search criteria
			if(term.getName().matches(keyword)){
				searchResultsVID.add(term.getId());
				if(filteredOrdering.contains(term.getId())) {
					moveID = term.getId();
				}
			}
			else if(term.getDescription().matches(keyword)){
				searchResultsVIDd.add(term.getId());	
				if(filteredOrdering.contains(term.getId())) {
					moveID = term.getId();
				}
			}
		}

		// Determine what elements match search criteria		
		SchemaInfo[] schemasInfos = unityCanvas.getSchemaInfos();
		SchemaModel[] schemaModels = unityCanvas.getSchemaModels();
		for(int j = 0; j < schemasInfos.length; j++)
		{
			HierarchicalSchemaInfo schema = new HierarchicalSchemaInfo(schemasInfos[j],schemaModels[j]);
			for(SchemaElement element : schema.getHierarchicalElements())
			{				
				// Check to see if element name or description matches search criteria
				if(element.getName().matches(keyword)){
					searchResultsEID.add(schema.getSchema().getId() + "#" + element.getId());
				}
				else if(element.getDescription().matches(keyword)){
					searchResultsEIDd.add(schema.getSchema().getId() + "#" + element.getId());				
				}
			}
		}
		
		
		
		//determine index of first element
		if(moveID > -1) {
			for(int j = 0; j < moveIndex; j++) {
				if(filteredOrdering.get(j).equals(moveID)) {
					moveIndex = j;
				}
			}
		}
		
		//find first instance of match if element occurs before term
		for(int j = 0; j < moveIndex; j++)
		{	
			AssociatedElement[] elements = unityCanvas.getVocabulary().getTerm(filteredOrdering.get(j)).getElements();
			for(int i = 0; i < elements.length; i++) {
				if(searchResultsEID.contains(elements[i].getSchemaID() + "#" + elements[i].getElementID()) || searchResultsEIDd.contains(elements[i].getSchemaID() + "#" + elements[i].getElementID())) {	
					moveIndex = j;
				}
			}
		}		
				
		resetTableView();
		if(moveIndex < vocabLength) {
			TableItem itemToShow = tableview.getItem(moveIndex);
			tableview.showItem(itemToShow);
		}
	}

	public void addTerm(int id) {
		ordering.add(id);
		filter();
	}

	public void removeTerm(int id) {
		ordering.remove(new Integer(id));
		filter();
	}
}
