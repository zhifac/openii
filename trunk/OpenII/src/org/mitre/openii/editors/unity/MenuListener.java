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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.model.terms.AssociatedElement;




public class MenuListener implements Listener {

	int EDITABLECOLUMN = -1;
    private UnityCanvas unityCanvas;
    private GC gc = null;
    
    private Image CheckIcon = OpenIIActivator.getImage("checkicon.png");
    private Image changeDescIcon = OpenIIActivator.getImage("change_desc.png");
    private Image deleteSynsetIcon = OpenIIActivator.getImage("deletesynset.png");
    private Image evidenceIcon = OpenIIActivator.getImage("footprint1.png");
    private Image closeMatchIcon = OpenIIActivator.getImage("footprint2.png");
    private Image contextIcon = OpenIIActivator.getImage("home.png");
    private Image remove2Icon = OpenIIActivator.getImage("removeBigIcon.png");
    private Image deleteIcon = OpenIIActivator.getImage("delete.png");
    private Image pasteIcon = OpenIIActivator.getImage("add.png");
    private Image renameIcon = OpenIIActivator.getImage("rename.png");
    private Image tableIcon = OpenIIActivator.getImage("tableicon.png");
    private Image treeViewIcon = OpenIIActivator.getImage("treeview.png");
    private Image insertIcon = OpenIIActivator.getImage("insert.png");
    private Image moveIcon = OpenIIActivator.getImage("move.png");
    private Image copyIcon = OpenIIActivator.getImage("copy.png");
    private Image alphaSortDownIcon = OpenIIActivator.getImage("alphaDown.png");
    private Image alphaSortUpIcon = OpenIIActivator.getImage("alphaUp.png");
    private Image StructuralSortIcon = OpenIIActivator.getImage("structurally.png");
    private Image checkRemoveIcon = OpenIIActivator.getImage("checkRemove.png");
    private Image checkSortUpIcon = OpenIIActivator.getImage("checkSortUp.png");
    private Image checkSortDownIcon = OpenIIActivator.getImage("checkSortDown.png");

	public MenuListener(UnityCanvas unity) {
		unityCanvas = unity;
	}
	
			
	public void handleEvent(Event e) {
	    Menu oldmenu = unityCanvas.getWorkspace().getTable().getMenu();
	    if (oldmenu != null)
	    	oldmenu.dispose();
	    oldmenu = unityCanvas.getTableView().getMenu();
	    if (oldmenu != null)
	    	oldmenu.dispose();
	    
		Table theTable = ((Table)e.widget);
	    if(gc==null) gc = new GC(theTable);

		unityCanvas.activeTable = theTable;

		Point pt = unityCanvas.getDisplay().map(null, theTable, new Point(e.x, e.y));

		// Identify the selected row
		unityCanvas.selectedItem = theTable.getItem(pt);
		//System.out.println("selectedItem = " + selectedItem);
//			if (selectedItem == null) //fake to get column widths
//				selectedItem = theTable.get



		
	    int xoff = 0;
		int[] order = theTable.getColumnOrder();
		for(int i = 0; i < theTable.getColumnCount(); i++) {					
			xoff += theTable.getColumn(order[i]).getWidth();
			//System.out.println("colrec x = " + colrec.x + "\n");
			//System.out.println("xoff x = " + xoff + "\n");
			if(xoff > pt.x){
				xoff -= theTable.getColumn(order[i]).getWidth();
				EDITABLECOLUMN = order[i];
				break;
			}
		}
		
		unityCanvas.draggedCol = (Integer)(theTable.getColumn(EDITABLECOLUMN).getData("uid"));								
		Menu popupMenu = new Menu(theTable);				
		
		if(pt.y < theTable.getHeaderHeight())
		{
			if(unityCanvas.draggedCol == -202) {
			    MenuItem checkItem = new MenuItem(popupMenu, SWT.CASCADE);
			    checkItem.setImage(CheckIcon);
			    checkItem.setText("Check All");
			    checkItem.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
					}
				});	
			    MenuItem uncheckItem = new MenuItem(popupMenu, SWT.NONE);
			    uncheckItem.setImage(checkRemoveIcon);
			    uncheckItem.setText("Uncheck All");
			    uncheckItem.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
					}
				});	
			    new MenuItem(popupMenu, SWT.SEPARATOR); 
			    MenuItem sortUp = new MenuItem(popupMenu, SWT.NONE);
			    sortUp.setImage(checkSortUpIcon);
			    sortUp.setText("Ascending by Checked");
			    sortUp.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
					}
				});	
			    MenuItem sortDown = new MenuItem(popupMenu, SWT.NONE);
			    sortDown.setImage(checkSortDownIcon);
			    sortDown.setText("Descending by Checked");
			    sortDown.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
					}
				});	
				} else { 

			    MenuItem sortUp = new MenuItem(popupMenu, SWT.NONE);
			    sortUp.setImage(alphaSortUpIcon);
			    sortUp.setText("Ascending Alphabetical");
			    sortUp.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
					}
				});	
			    MenuItem sortDown = new MenuItem(popupMenu, SWT.NONE);
			    sortDown.setImage(alphaSortDownIcon);
			    sortDown.setText("Descending Alphabetical");
			    sortDown.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
					}
				});	

					
					if (unityCanvas.draggedCol != -201) {

					    MenuItem structSort = new MenuItem(popupMenu, SWT.NONE);
					    structSort.setImage(StructuralSortIcon);
					    structSort.setText("Structurally");
					    structSort.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {
							}
						});	 							
					
					}			
					
				}

		} else {
			
			unityCanvas.draggedRow = (Integer)(unityCanvas.selectedItem.getData("uid"));

			if(unityCanvas.draggedCol == -202) {
			    MenuItem checkItem = new MenuItem(popupMenu, SWT.CASCADE);
			    checkItem.setImage(CheckIcon);
			    checkItem.setText("Check");
			    MenuItem uncheckItem = new MenuItem(popupMenu, SWT.NONE);
			    uncheckItem.setImage(checkRemoveIcon);
			    uncheckItem.setText("Uncheck");
				
			} else if (unityCanvas.draggedCol == -201) {
				if(unityCanvas.activeTable.getSelectionCount() < 2) {
				    MenuItem RenameItem = new MenuItem(popupMenu, SWT.CASCADE);
				    RenameItem.setImage(renameIcon);
				    RenameItem.setText("Rename Synset");
					RenameItem.addListener(SWT.Selection, new RenameSynsetListener(unityCanvas,theTable,theTable));				    
				    
				   				    
				    MenuItem changeDescItem = new MenuItem(popupMenu, SWT.NONE);
				    changeDescItem.setImage(changeDescIcon);
				    changeDescItem.setText("Edit Description");
				    changeDescItem.addListener(SWT.Selection, new DescSynsetListener(unityCanvas,theTable,theTable));

				}
			    
			    if(theTable.getData("name").equals("workspaceTable")) {
			    	MenuItem removeItem = new MenuItem(popupMenu, SWT.NONE);
			    	removeItem.setImage(remove2Icon);
			    	removeItem.setText("Remove from Workspace");
			    	removeItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							unityCanvas.getWorkspace().removeFromWorkspace(unityCanvas.getWorkspace().getTable().getSelectionIndex()+1);
						} 
					});				
			    
			    } else {
			    	
			    	
			    	MenuItem addItem = new MenuItem(popupMenu, SWT.NONE);
			    	addItem.setImage(insertIcon);
			    	addItem.setText("Add to Workspace");
			    	addItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							TableItem[] items = unityCanvas.activeTable.getSelection();
							ArrayList<Integer> termIDs = new ArrayList<Integer>();
							for(TableItem item : items)
								termIDs.add((Integer)item.getData("uid"));
							unityCanvas.getWorkspace().addToWorkspace(termIDs);
						}
					});				
			    }
			    MenuItem deleteItem = new MenuItem(popupMenu, SWT.NONE);
			    deleteItem.setImage(deleteSynsetIcon);
			    deleteItem.setText("Permanently Delete Synset");
		    	deleteItem.addListener(SWT.Selection, new DeleteSynsetListener(unityCanvas,theTable,theTable));

			    
				new MenuItem(popupMenu, SWT.SEPARATOR);								
			    if((e.widget.getData("name")).equals("workspaceTable")) {
				    MenuItem tableItem = new MenuItem(popupMenu, SWT.NONE);
				    tableItem.setImage(tableIcon);
				    tableItem.setText("Show in Table");
				    tableItem.setEnabled(false);
			    }
			    MenuItem evidenceItem = new MenuItem(popupMenu, SWT.NONE);
			    evidenceItem.setImage(evidenceIcon);
			    evidenceItem.setText("Show Evidence");
			    evidenceItem.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {								
						unityCanvas.getDetailFolder().setSelection(0);
						unityCanvas.updateDetailPane(unityCanvas.draggedRow, unityCanvas.draggedCol, null);
					}
				});	
			    
			    MenuItem closeMatchItem = new MenuItem(popupMenu, SWT.NONE);
			    closeMatchItem.setImage(closeMatchIcon);
			    closeMatchItem.setText("Show Close Matches");
			    closeMatchItem.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {								
						unityCanvas.getDetailFolder().setSelection(1);
						unityCanvas.updateDetailPane(unityCanvas.draggedRow, unityCanvas.draggedCol, null);
					}
				});	
			    
			    MenuItem contextItem = new MenuItem(popupMenu, SWT.NONE);
			    contextItem.setImage(contextIcon);
			    contextItem.setText("Show Context");
			    contextItem.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {								
						unityCanvas.getDetailFolder().setSelection(2);
						unityCanvas.updateDetailPane(unityCanvas.draggedRow, unityCanvas.draggedCol, null);
					}
				});	
			    
			} else {
										
				unityCanvas.dragElement = null;
				AssociatedElement aElements[] = unityCanvas.getVocabulary().getTerm(unityCanvas.draggedRow).getAssociatedElements(unityCanvas.draggedCol);
				for(int i = 0; i < aElements.length; i++){
					xoff = xoff + gc.textExtent(aElements[i].getName() + ", ").x;
					if(xoff >= pt.x){
						unityCanvas.dragElement = aElements[i];
						break;
					}
					if(i == aElements.length - 1){
						unityCanvas.dragElement = aElements[i];
						break;									
					}
				}
				//System.err.println("xoff after = " + xoff);

				if(unityCanvas.dragElement != null) {
					MenuItem cutItem = new MenuItem(popupMenu, SWT.CASCADE);
					cutItem.setImage(moveIcon);
					cutItem.setText("Cut " + unityCanvas.dragElement.getName());
					cutItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							unityCanvas.copyElementToClipboard(unityCanvas.draggedCol,unityCanvas.dragElement);
							unityCanvas.deleteTerm(unityCanvas.draggedRow, unityCanvas.dragElement);
						}
					});	
				    MenuItem copyItem = new MenuItem(popupMenu, SWT.NONE);
				    copyItem.setImage(copyIcon);
				    copyItem.setText("Copy " + unityCanvas.dragElement.getName());
				    copyItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							unityCanvas.copyElementToClipboard(unityCanvas.draggedCol,unityCanvas.dragElement);
						}
					});	
				}
				if(unityCanvas.draggedCol.equals(unityCanvas.copiedSchema) && unityCanvas.copiedElement != null) {
					MenuItem pasteItem = new MenuItem(popupMenu, SWT.CASCADE);
					pasteItem.setImage(pasteIcon);
					pasteItem.setText("Paste " + unityCanvas.copiedElement.getName());
					pasteItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							unityCanvas.addTerm(unityCanvas.draggedRow, unityCanvas.copiedElement);
						}
					});	
				}
				if(unityCanvas.dragElement != null) {
				    MenuItem deleteItem = new MenuItem(popupMenu, SWT.NONE);
				    deleteItem.setImage(deleteIcon);
				    deleteItem.setText("Delete " + unityCanvas.dragElement.getName());
				    deleteItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							unityCanvas.deleteTerm(unityCanvas.draggedRow, unityCanvas.dragElement);
						}
					});	
				
				    new MenuItem(popupMenu, SWT.SEPARATOR); 
				    MenuItem treeItem = new MenuItem(popupMenu, SWT.NONE);
				    treeItem.setImage(treeViewIcon);
				    treeItem.setText("Show in Tree");
				    treeItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							unityCanvas.getTreeView().selectSchema(EDITABLECOLUMN-2);
							unityCanvas.getSearchFolder().setSelection(0);
							unityCanvas.getTreeView().searchTreeByID(unityCanvas.dragElement.getElementID());
						
						}
					});	
				    
				    
				    if((e.widget.getData("name")).equals("workspaceTable")) {
					    MenuItem tableItem = new MenuItem(popupMenu, SWT.NONE);
					    tableItem.setImage(tableIcon);
					    tableItem.setText("Show in Table");
					    tableItem.setEnabled(false);
				    }
				    MenuItem evidenceItem = new MenuItem(popupMenu, SWT.NONE);
				    evidenceItem.setImage(evidenceIcon);
				    evidenceItem.setText("Show Evidence");
				    evidenceItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {								
							unityCanvas.getDetailFolder().setSelection(0);
							unityCanvas.updateDetailPane(unityCanvas.draggedRow, unityCanvas.draggedCol, unityCanvas.dragElement.getElementID());
						}
					});	
				    
				    MenuItem closeMatchItem = new MenuItem(popupMenu, SWT.NONE);
				    closeMatchItem.setImage(closeMatchIcon);
				    closeMatchItem.setText("Show Close Matches");
				    closeMatchItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {								
							unityCanvas.getDetailFolder().setSelection(1);
							unityCanvas.updateDetailPane(unityCanvas.draggedRow, unityCanvas.draggedCol, unityCanvas.dragElement.getElementID());
						}
					});	
				    
				    MenuItem contextItem = new MenuItem(popupMenu, SWT.NONE);
				    contextItem.setImage(contextIcon);
				    contextItem.setText("Show Context");
				    contextItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {								
							unityCanvas.getDetailFolder().setSelection(2);
							unityCanvas.updateDetailPane(unityCanvas.draggedRow, unityCanvas.draggedCol, unityCanvas.dragElement.getElementID());
						}
					});	
				}
			}
		}
	    theTable.setMenu(popupMenu);
	    theTable.getMenu().setVisible(true);																		
	}


}
