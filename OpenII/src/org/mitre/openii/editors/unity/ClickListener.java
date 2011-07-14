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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.mitre.schemastore.model.Annotation;
import org.mitre.schemastore.model.terms.AssociatedElement;
import org.mitre.schemastore.model.terms.Term;




public class ClickListener implements MouseListener {

	int EDITABLECOLUMN = -1;
    private UnityCanvas unityCanvas;
    private GC gc = null;
	private Font ItalicFont = new Font(Display.getDefault(), new FontData("Arial", 8, SWT.ITALIC));

	public ClickListener(UnityCanvas unity) {
		unityCanvas = unity;
	}
	
			
	public void mouseDoubleClick(MouseEvent e) { }
	
	public void mouseUp(MouseEvent e) {}
	
	public void mouseDown(MouseEvent e)  {
        if((e.stateMask & SWT.CTRL) > 0)
        {
	          System.out.println("ctrl pressed");
	          unityCanvas.interfaceState = DND.DROP_COPY;
        } else {
	          System.out.println("ctrl released");
	          unityCanvas.interfaceState = DND.DROP_MOVE;
        }
        unityCanvas.activeTable = (Table)(e.widget);

	    // Clean up any previous temporary controls
	    Control oldEditor = unityCanvas.getEditor().getEditor();
	    if (oldEditor != null)
	    	oldEditor.dispose();

        //System.out.println("widget = " + e.widget);
        if(e.button == 1 && e.widget instanceof Table && ((Table)(e.widget)).getSelectionCount() > 0){

        	// Identify the selected row
        	unityCanvas.selectedItem = ((Table)(e.widget)).getItem(new Point(e.x,e.y));
	        //System.out.println("selectedItem = " + selectedItem);
		    if (unityCanvas.selectedItem == null)
		    {
		    	return;    	
		    }
		    
			//System.out.println("theTable.getData(\"name\") = " + (String)(e.widget.getData("name")));
			if(((String)(e.widget.getData("name"))).equals("workspaceTable") || (((String)(e.widget.getData("name"))).equals("tableview")))
			{				
				Table theTable = ((Table)(e.widget));		
			    if(gc==null) gc = new GC(theTable);
			    int xoff = e.x;
				Rectangle colrec = null;
				int[] order = theTable.getColumnOrder();
				for(int i = 0; i < theTable.getColumnCount(); i++) {
					colrec = unityCanvas.selectedItem.getBounds(order[i]);
					//System.out.println("colrec x = " + colrec.x + "\n");
					//System.out.println("xoff x = " + xoff + "\n");
					if(colrec.x + colrec.width > xoff){
						EDITABLECOLUMN = order[i];
						break;
					}
				}

				//System.out.println("EDITABLECOLUMN = " + EDITABLECOLUMN);
				
				unityCanvas.draggedRow = (Integer)(unityCanvas.selectedItem.getData("uid"));
				unityCanvas.draggedCol = (Integer)(theTable.getColumn(EDITABLECOLUMN).getData("uid"));
				//System.out.println("draggedRow = " + draggedRow);
				//System.out.println("draggedCol = " + draggedCol + "\n");
				if(EDITABLECOLUMN > 1){
					xoff = xoff - colrec.x;
					unityCanvas.dragElement = null;
					AssociatedElement aElements[] = unityCanvas.getVocabulary().getTerm(unityCanvas.draggedRow).getAssociatedElements(unityCanvas.draggedCol);
					for(int i = 0; i < aElements.length; i++){
						xoff = xoff - gc.textExtent(aElements[i].getName() + ", ").x;
						if(xoff <= 0){
							unityCanvas.dragElement = aElements[i];
							break;
						}
						if(i == aElements.length - 1){
							unityCanvas.dragElement = aElements[i];
							break;									
						}
					}
				}
				if(unityCanvas.dragElement != null) {
					unityCanvas.updateDetailPane(unityCanvas.draggedRow, unityCanvas.draggedCol, unityCanvas.dragElement.getElementID());
				} else {
					unityCanvas.updateDetailPane(unityCanvas.draggedRow, unityCanvas.draggedCol, new Integer(-1));							
				}
		      
					
					
		      //System.err.println("button " + e.button + " was pressed.");
			      // The control that will be the editor must be a child of the
			      // Table
				  if(unityCanvas.draggedCol == -201) {
					  StyledText TableEditor = new StyledText(theTable, SWT.NONE);
					  TableEditor.setText(unityCanvas.selectedItem.getText(EDITABLECOLUMN));
/*						  TableEditor.addModifyListener(new ModifyListener() {
						        public void modifyText(ModifyEvent me) {
								    if (!selectedItem.isDisposed())
								    {
//								      StyledText text = (StyledText) editor.getEditor();
//							          editor.getItem()
//							              .setText(EDITABLECOLUMN, text.getText());
							          editChange = true;
								    }
						        }
						      });*/
					  TableEditor.addFocusListener(new FocusListener() {
					        public void focusGained(FocusEvent fe) {
						        }
					        public void focusLost(FocusEvent fe) {
							    if (!unityCanvas.selectedItem.isDisposed())
							    {
							    	Term theTerm = unityCanvas.getVocabulary().getTerm((Integer)(unityCanvas.selectedItem.getData("uid")));
						        	String text = ((StyledText)unityCanvas.getEditor().getEditor()).getText();
							    	if(!text.equals(theTerm.getName())) {
								    	//System.out.println("writing to " +selectedItem.getData("uid"));
								    	theTerm.setName(text);
								    	unityCanvas.updateTables(new Integer[] {(Integer)unityCanvas.selectedItem.getData("uid")});
							    	}
							    	unityCanvas.getEditor().getEditor().dispose();
						        }
					        }
					      });
					  TableEditor.addKeyListener(new KeyListener() {
					        public void keyPressed(KeyEvent e) {
						        }
					        public void keyReleased(KeyEvent e) {
							    if (e.character == SWT.CR && !unityCanvas.selectedItem.isDisposed())
							    {
							    	Term theTerm = unityCanvas.getVocabulary().getTerm((Integer)(unityCanvas.selectedItem.getData("uid")));
						        	String text = ((StyledText)unityCanvas.getEditor().getEditor()).getText();
						        	int index = ((StyledText)unityCanvas.getEditor().getEditor()).getCaretOffset();
						        	text = text.substring(0,index-2) + text.substring(index,text.length());
						        	((StyledText)unityCanvas.getEditor().getEditor()).setText(text);
							    	if(!text.equals(theTerm.getName())) {
								    	//System.out.println("writing to " +selectedItem.getData("uid"));
								    	theTerm.setName(text);
								    	unityCanvas.updateTables(new Integer[] {(Integer)unityCanvas.selectedItem.getData("uid")});
							    	}
							    	unityCanvas.getEditor().getEditor().dispose();
						        }
					        }
					      });
				      //newEditor.selectAll();
					  TableEditor.setFocus();
					  TableEditor.setFont(ItalicFont);
					  TableEditor.setSelection(TableEditor.getText().length());
					  unityCanvas.getEditor().setEditor(TableEditor, unityCanvas.selectedItem, EDITABLECOLUMN);
					  unityCanvas.getEditor().minimumHeight = unityCanvas.selectedItem.getBounds(EDITABLECOLUMN).height;
					  unityCanvas.getEditor().minimumWidth = unityCanvas.selectedItem.getBounds(EDITABLECOLUMN).width;
				  } else if(unityCanvas.draggedCol == -202) {
						if(unityCanvas.getCheckStatus().containsKey(unityCanvas.draggedRow)) { 
							unityCanvas.getCheckStatus().remove(unityCanvas.draggedRow);
						} else {
							unityCanvas.getCheckStatus().put(unityCanvas.draggedRow,new Annotation(unityCanvas.draggedRow,unityCanvas.getVocabulary().getProjectID(),"checked","true"));									
						}
						
					  //						  if(not checked) {
					  //System.out.println("setting image for " + EDITABLECOLUMN);
//							  	selectedItem.setImage(EDITABLECOLUMN,CheckIcon);						  
//							+ set in vocab
//					  	  } else {
//						  	selectedItem.setImage(EDITABLECOLUMN,null);						  
//							+ set in vocab
//						  }
						unityCanvas.updateTables(new Integer[] {(Integer)unityCanvas.selectedItem.getData("uid")});						  
				  }
		    }
        }
	
	}


}
