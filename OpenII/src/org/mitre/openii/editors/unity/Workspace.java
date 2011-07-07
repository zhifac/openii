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
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.StyledText;
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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.mitre.openii.application.OpenIIActivator;


public class Workspace  {
	
    private UnityCanvas unityCanvas;
    private Canvas workspace = null;
    private Canvas tablespace = null;
    private Composite buttonsC = null;
    private Table workspaceTable = null;
	private StyledText tempLabel = null;
    private Composite buttonsD = null;
    public final StackLayout stackLayout = new StackLayout();
    public boolean showTextWorkspace = true;
    private Button saveVocab;


	private Combo colorSelector;
    private Button colorsettings;
	private Color gray;
	private Color green;
	private Color red;
	private Color lightBlue;

	private Font LargeItalicFont = new Font(Display.getDefault(), new FontData("Arial", 10, SWT.ITALIC));
    private Image needsaveIcon = OpenIIActivator.getImage("needsave.png");
    private Image saveIcon = OpenIIActivator.getImage("save.png");
    private Image addSynsetIcon = OpenIIActivator.getImage("addsynset.png");
    private Image changeDescIcon = OpenIIActivator.getImage("change_desc.png");
    private Image deleteSynsetIcon = OpenIIActivator.getImage("deletesynset.png");
    private Image renameIcon = OpenIIActivator.getImage("rename.png");
    private Image showTextIcon = OpenIIActivator.getImage("showtext.png");
    private Image seperatorIcon = OpenIIActivator.getImage("seperator.png");
    private Image clearIcon = OpenIIActivator.getImage("clear.png");
    private Image CheckIcon = OpenIIActivator.getImage("checkicon.png");
    private Image removeIcon = OpenIIActivator.getImage("remove.png");





	public Workspace(UnityCanvas unity) {
		unityCanvas = unity;
		gray = unityCanvas.getDisplay().getSystemColor(SWT.COLOR_GRAY);
		green = unityCanvas.getDisplay().getSystemColor(SWT.COLOR_GREEN);
		red =  unityCanvas.getDisplay().getSystemColor(SWT.COLOR_RED);
		lightBlue = new Color(unityCanvas.getDisplay(), 225, 225, 255);



	}
	
	public void createWorkspace(Composite parent) {

	    workspace = new Canvas(parent, SWT.EMBEDDED);
	    buttonsC = new Composite(workspace, SWT.NONE);
	    tablespace = new Canvas(workspace, SWT.EMBEDDED);
	    workspaceTable = new Table(tablespace, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
		tempLabel = new StyledText(tablespace,SWT.WRAP);
	    buttonsD = new Canvas(workspace,SWT.NONE);
	    

		workspace.setToolTipText("Drag from the Tree or Table to populate the workspace");

		//Canvas workspace = new Canvas(parent, SWT.EMBEDDED);
		GridLayout wslayout = new GridLayout(2, false);
		wslayout.marginHeight = 0;
		wslayout.marginWidth = 0;
		wslayout.verticalSpacing = 0;
		wslayout.horizontalSpacing = 0;
		wslayout.marginBottom = 0;
		workspace.setLayout(wslayout);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessVerticalSpace = false;
		gridData.grabExcessHorizontalSpace = true;
		workspace.setLayoutData(gridData);

		
//		Composite buttonsC = new Composite(workspace, SWT.NONE);
//		GridLayout gl = new GridLayout(3, false);
//		buttonsC.setLayout(gl);
		RowLayout buttonlayout = new RowLayout();
		buttonlayout.center = true;
		buttonsC.setLayout(buttonlayout);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.grabExcessVerticalSpace = false;
		gridData.grabExcessHorizontalSpace = true;
		buttonsC.setLayoutData(gridData);
		
		colorSelector = new Combo(buttonsC, SWT.READ_ONLY | SWT.DROP_DOWN);
//		colorSelector.setForeground(gray);
		colorSelector.add("Not Colored");
		colorSelector.add("Color by Element Type");
		colorSelector.add("Instance Count");
		colorSelector.add("Canonical Match");
		colorSelector.add("Match Strength (strongest)");
		colorSelector.add("Match Strength (weakest)");		
		colorSelector.setToolTipText("No coloring applied");
		colorSelector.select(0);
		colorSelector.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorSelector.select(0);
			}
		});

		colorsettings = new Button(buttonsC, SWT.PUSH);
		colorsettings.setImage(OpenIIActivator.getImage("color_settings.png"));
		colorsettings.setToolTipText("Color Settings");
//		colorsettings.setEnabled(false);
		colorsettings.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorsettings.setSelection(false);
			}
		});
		
		
		Button showtext = new Button(buttonsC, SWT.TOGGLE);
		showtext.setImage(showTextIcon);
		showtext.setToolTipText("Hide terms");
		showtext.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				showTextWorkspace = !showTextWorkspace;
				TableItem[] workspaceItems = workspaceTable.getItems();
				workspaceTable.clearAll();
				for(int i = 0; i < workspaceItems.length; i++) {
					unityCanvas.populateRow(workspaceItems[i],showTextWorkspace);
				}
			}
		});				


		Button clearSynsets = new Button(buttonsC, SWT.PUSH);
		clearSynsets.setImage(clearIcon);
		clearSynsets.setToolTipText("Clear Workspace");		
		clearSynsets.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				workspaceTable.removeAll();
				Control[] buttons = buttonsD.getChildren();
				for(int i = 1; i < buttons.length; i++)
				{
					buttons[i].dispose();
				}
				stackLayout.topControl = tempLabel;										
				workspace.getParent().layout();									

			}
		});		
		
		Label seperator3 = new Label(buttonsC, SWT.BITMAP);
		seperator3.setImage(seperatorIcon);
//		seperator3.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));

		Button newSynset = new Button(buttonsC, SWT.PUSH);
		newSynset.setImage(addSynsetIcon);
		newSynset.setToolTipText("Create a new Synset");		
		newSynset.addListener(SWT.Selection, new NewSynsetListener(unityCanvas,newSynset));
		
		Button renameSynset = new Button(buttonsC, SWT.PUSH);
		renameSynset.setImage(renameIcon);
		renameSynset.setToolTipText("Rename the selected Synset");		
		renameSynset.addListener(SWT.Selection, new RenameSynsetListener(unityCanvas,renameSynset,workspaceTable));
				
		Button descSynset = new Button(buttonsC, SWT.PUSH);
		descSynset.setImage(changeDescIcon);
		descSynset.setToolTipText("Change the description of the selected Synset");		
		descSynset.addListener(SWT.Selection, new DescSynsetListener(unityCanvas,descSynset,workspaceTable));
				
		Button deleteSynset = new Button(buttonsC, SWT.PUSH);
		deleteSynset.setImage(deleteSynsetIcon);
		deleteSynset.setToolTipText("Permanently delete selected Synset from Vocabulary");		
		deleteSynset.addListener(SWT.Selection, new DeleteSynsetListener(unityCanvas,deleteSynset,workspaceTable));

		Label seperator = new Label(buttonsC, SWT.BITMAP);
		seperator.setImage(seperatorIcon);

		
		saveVocab = new Button(buttonsC, SWT.PUSH);
		saveVocab.setToolTipText("Save the current Vocabulary");	
		needSave(false);
		saveVocab.addListener(SWT.Selection, new SaveListener(unityCanvas));

					
//		workspaceTable = new Table(workspace, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI );
		workspaceTable.setHeaderVisible(true);
		workspaceTable.setLinesVisible(true);
		workspaceTable.setData("name", "workspaceTable");
		workspaceTable.setToolTipText("");

		
		gridData = new GridData();
		gridData.horizontalSpan = 0;
		gridData.minimumHeight = 0;
		gridData.verticalSpan = 0;
		gridData.verticalIndent = 0;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		workspaceTable.setLayoutData(gridData);
		tablespace.setLayoutData(gridData);
		tablespace.setLayout(stackLayout);
		
		// creating one column for the status and the vocabulary
		TableColumn tempC1;
		tempC1 = new TableColumn(workspaceTable, SWT.NONE);
		tempC1.setWidth(26);
		tempC1.setImage(CheckIcon);
		tempC1.setMoveable(false);
		tempC1.setData("uid",new Integer(-202));
		tempC1.setToolTipText("Checked");
		
		TableColumn tempC;		
		tempC = new TableColumn(workspaceTable, SWT.NONE);
		tempC.setText("Vocabulary");
		tempC.setMoveable(false);
		tempC.setData("uid",new Integer(-201));
		tempC.setWidth(100);
		//tempC.addListener(SWT.Selection, sortAlphabeticallyListener);
		tempC.setToolTipText("Vocabulary");
		// creating one column for each schema
		for (int i = 0; i < unityCanvas.getSchemas().length; i++) {
			tempC = new TableColumn(workspaceTable, SWT.NONE);
			tempC.setText(unityCanvas.getSchemas()[i].getName());
			tempC.setData("uid",new Integer(unityCanvas.getSchemas()[i].getId()));
			//tempC.setWidth((main_sash.getBounds().x - 120) / schemas.length);
			tempC.setMoveable(true);
			//tempC.addListener(SWT.Selection, sortAlphabeticallyListener);
			tempC.setToolTipText(unityCanvas.getSchemas()[i].getName());
		}
        
		//temp label
		tempLabel.setText("\nDrag from the Tree or Table to populate the workspace");
		tempLabel.setAlignment(SWT.CENTER);
		tempLabel.setFont(LargeItalicFont);
		tempLabel.setEditable(false);
		tempLabel.setCaret(null);
		stackLayout.topControl = tempLabel;
		
		//buttonsD = new Canvas(workspace, SWT.NONE);
		GridLayout buttonsDLayout = new GridLayout(1, false);
		buttonsDLayout.marginHeight = 0;
		buttonsDLayout.marginWidth = 0;
		buttonsDLayout.verticalSpacing = 0;
		buttonsDLayout.horizontalSpacing = 0;
		buttonsD.setLayout(buttonsDLayout);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.grabExcessVerticalSpace = true;
		buttonsD.setLayoutData(gridData);

		Label spacer = new Label(buttonsD, SWT.BITMAP);
		spacer.setImage(OpenIIActivator.getImage("empty.png"));
		gridData = new GridData();
		gridData.heightHint = 25;
		gridData.widthHint = 20;
		spacer.setLayoutData(gridData);
		spacer.setData("index", new Integer(-1));
		
		
		addListeners();
	}
		
	private void addListeners() {

		workspaceTable.addMouseListener(new ClickListener(unityCanvas));
		workspaceTable.addListener(SWT.MenuDetect, new MenuListener(unityCanvas));
		workspaceTable.addListener(SWT.MouseHover, new HoverListener(unityCanvas));			
		
		workspaceTable.addListener(SWT.Resize, new Listener() {	
			public void handleEvent(Event e) {
				int size = 1+(((Table)(e.widget)).getBounds().width - workspaceTable.getColumn(0).getWidth()- workspaceTable.getColumn(1).getWidth())/(workspaceTable.getColumnCount()-2);
				for(int i = 2; i < workspaceTable.getColumnCount(); i++)
				{
					workspaceTable.getColumn(i).setWidth(size);
				}
			}
		});

		
		//add drag and drop sources
		
		DragSource source = new DragSource(workspaceTable, DND.DROP_LINK | DND.DROP_MOVE | DND.DROP_COPY);
		source.setTransfer(new Transfer [] { FileTransfer.getInstance() });
		source.addDragListener(new DragSourceListener () {
			String[] data = new String[1];
        	String data2[] = new String[1];
			public void dragStart (DragSourceEvent event) { 
				event.image = null;
			      // Clean up any previous editor control
			      Control oldEditor = unityCanvas.getEditor().getEditor();
			      if (oldEditor != null)
			        oldEditor.dispose();
			      unityCanvas.dragID = 3;
    			data[0] = "" + unityCanvas.draggedRow;
				if(unityCanvas.draggedCol > -1){
					//event.data = workspaceTable.getItem(index).getText(index2);
					data2[0] = "" + unityCanvas.getVocabulary().getTerm((Integer)(unityCanvas.selectedItem.getData("uid"))).getElements()[0].getElementID();
					//trim image
				}
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
		});							



		DropTarget target = new DropTarget (workspaceTable, DND.DROP_COPY | DND.DROP_DEFAULT | DND.DROP_MOVE | DND.DROP_LINK); 
		target.setTransfer(new Transfer [] { FileTransfer.getInstance() });
		target.addDropListener(new DropTargetListener () { 
			public void dragEnter(DropTargetEvent event) { 
				event.detail = DND.DROP_COPY;					
				unityCanvas.eventDetail = DND.DROP_COPY;
			} 
			public void dragOver(DropTargetEvent event) { 
//				//System.out.println("dragover");
				if(FileTransfer.getInstance().isSupportedType(event.currentDataType)){
//					//System.out.println("link");
					event.detail = DND.DROP_LINK;					
					unityCanvas.eventDetail = DND.DROP_LINK;
				} else {
//				//System.out.println("none");
				event.detail = DND.DROP_NONE;
				unityCanvas.eventDetail = DND.DROP_NONE;
				}
				
				if(event.item instanceof TableItem){
						Rectangle colrec = null;
						int[] order = workspaceTable.getColumnOrder();
						for(int i = 0; i < workspaceTable.getColumnCount(); i++) {
							colrec = ((TableItem)event.item).getBounds(order[i]);
							if(colrec.x + colrec.width > workspaceTable.getDisplay().map(null, workspaceTable, event.x, event.y).x ){
//								//System.out.println("col = " + order[i] + "\n");
								if(order[i] > 1){
//									//System.out.println(workspaceTable.getColumn(order[i]).getData("uid") + " != " + draggedCol + "?\n");
									if(workspaceTable.getColumn(order[i]).getData("uid").equals(unityCanvas.draggedCol)){
										event.detail = DND.DROP_COPY;										
										unityCanvas.eventDetail = DND.DROP_COPY;
										if(unityCanvas.dragID == 3){
											event.detail = unityCanvas.interfaceState;										
											unityCanvas.eventDetail = unityCanvas.interfaceState;											
										}
									} else {
										event.detail = DND.DROP_NONE;
										unityCanvas.eventDetail = DND.DROP_NONE;
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
//				if(FileTransfer.getInstance().isSupportedType(event.currentDataType)){
				//System.out.println("drop");
				if(event.detail == DND.DROP_LINK){
					//adding row
//System.out.println("detail = " + event.detail + "\n");
					Table ttarget = (Table)((DropTarget)event.widget).getControl();
					int posIndex = ttarget.getItemCount();
					if(ttarget.getItemCount() > 0) {
						TableItem dropTarget = (TableItem)event.item;
						if(dropTarget != null) { posIndex = ttarget.indexOf(dropTarget); }
						else if(ttarget.getDisplay().map(null, ttarget, event.x, event.y).y  < ttarget.getHeaderHeight()){posIndex = 0;}

					}
					//System.out.println("posIndex = " + posIndex + "\n");
					String[] data = ((String[])event.data);
					if(data.length == 1 && data[0].equals(" ")) return;
					ArrayList<Integer> termIDs = new ArrayList<Integer>();
					for(String item : data) termIDs.add(Integer.parseInt(item));
					addToWorkspace(termIDs, posIndex);
				} else if(event.detail == DND.DROP_COPY || event.detail == DND.DROP_MOVE){
//System.out.println("got = " + event.data + "\n");
/*					if(draggedCol > -1){
						Object target = event.item;
						if(target instanceof TableItem){
							String newtext = ((TableItem)target).getText(draggedCol);
							if(newtext != "") newtext += ", ";
							newtext = newtext + ((String)(event.data));
							((TableItem) target).setText(draggedCol,  newtext );							
							((Table)((DropTarget)event.widget).getControl()).getItem(draggedRow).setText(draggedCol,"");
							//System.out.println("metric = " + gc.textExtent(newtext));	
						}
					}*/
					Object target = event.item;
					if(target instanceof TableItem){
						Integer vocabID = (Integer)(((TableItem) target).getData("uid"));
//System.out.println("vocabID = " + vocabID + " and draggedRow is " + draggedRow);
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
						
		DropTarget target2 = new DropTarget (tempLabel, DND.DROP_LINK); 
		target2.setTransfer(new Transfer [] { FileTransfer.getInstance() });
		target2.addDropListener(new DropTargetListener () { 
			public void dragEnter(DropTargetEvent event) { 
				event.detail = DND.DROP_LINK;					
				unityCanvas.eventDetail = DND.DROP_LINK;
			} 
			public void dragOver(DropTargetEvent event) { 
			}
			public void dragOperationChanged(DropTargetEvent event) { 				
				//System.out.println("change");
				
			}
			public void dragLeave(DropTargetEvent event) { 
				//System.out.println("leave");
		    } 
			public void dropAccept(DropTargetEvent event) { 
			} 
			public void drop(DropTargetEvent event)
			{
				String[] data = ((String[])event.data);
				if(data.length == 1 && data[0].equals(" ")) return;
				stackLayout.topControl = tempLabel;
				ArrayList<Integer> termIDs = new ArrayList<Integer>();
				for(String item : data) termIDs.add(Integer.parseInt(item));
				addToWorkspace(termIDs);
			} 
		});
						
	}	
	
	
	public void removeFromWorkspace(Integer id) {
		for(int i = 0; i < workspaceTable.getItemCount(); i++) {
			if(workspaceTable.getItem(i).getData("uid").equals(id)) {
				removeFromWorkspace(i+1);
				break;
			}
		}

	}
	
	public void removeFromWorkspace(int index) {
		workspaceTable.remove(index-1);
		Control[] buttons = buttonsD.getChildren();
		buttons[buttons.length-1].dispose();
		if(buttons.length == 2) {
			stackLayout.topControl = tempLabel;										
		}
		workspace.getParent().layout();									

	}
	
	public void addToWorkspace(ArrayList<Integer> termIDs)
		{ addToWorkspace(termIDs, workspaceTable.getItemCount()); }
	
	public void addToWorkspace(ArrayList<Integer> termIDs, int posIndex)
	{
		for(Integer termID : termIDs)
		{
			boolean inTable = false;
			for(int i = 0; i < workspaceTable.getItemCount(); i++)
			{							
				if(((Integer)workspaceTable.getItem(i).getData("uid")).equals(termID)) {
					inTable = true;
					workspaceTable.remove(i);
					break;
				}
			}
			if(posIndex > workspaceTable.getItemCount())  posIndex = workspaceTable.getItemCount();
			TableItem item = new TableItem(workspaceTable, SWT.NONE, posIndex); 
			item.setBackground(1, lightBlue);
			item.setData("uid", termID);
			unityCanvas.populateRow(item, showTextWorkspace);
				
			if(!inTable){
				//add a button for each new entry
				Button x1;
				x1 = new Button(buttonsD, SWT.PUSH);
				x1.setImage(removeIcon);
				x1.setToolTipText("Remove from Workspace");
				x1.setData("index", workspaceTable.getItemCount());
				GridData gridData = new GridData();
				gridData.heightHint = 19;
				gridData.widthHint = 19;
				x1.setLayoutData(gridData);						
				x1.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						int index = ((Integer)(e.widget.getData("index"))).intValue();
						removeFromWorkspace(index);
					}
				});
			}
			stackLayout.topControl = workspaceTable;
			workspaceTable.getParent().getParent().getParent().layout(true);
		}
				
	}


 	public Table getTable() {
		  return workspaceTable;
	}
	
 	public void createRemoveButton() {
		Button x1;
		x1 = new Button(buttonsD, SWT.PUSH);
		x1.setToolTipText("Remove from Workspace");									
		x1.setImage(removeIcon);
		x1.setData("index", new Integer(workspaceTable.getItemCount()));
		GridData gridData = new GridData();
		gridData.heightHint = 19;
		gridData.widthHint = 19;
		x1.setLayoutData(gridData);						
		x1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int index = ((Integer)(e.widget.getData("index"))).intValue();
				removeFromWorkspace(index);									}
		});

 	}
 	
 	public void needSave(boolean need) {
 		if(need) {
			saveVocab.setBackground(red);
			saveVocab.setImage(needsaveIcon);									
 		} else {
 			saveVocab.setBackground(green);
 			saveVocab.setImage(saveIcon);
 		}
 	}

 	public boolean needSave() {
 		return saveVocab.getBackground().equals(red);
 	}
}
	