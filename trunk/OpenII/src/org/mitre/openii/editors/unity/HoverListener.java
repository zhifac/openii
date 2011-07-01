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

import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.mitre.schemastore.model.terms.AssociatedElement;
import org.apache.commons.lang.WordUtils;




public class HoverListener implements Listener {

	int EDITABLECOLUMN = -1;
    private UnityCanvas unityCanvas;
    private GC gc = null;

	public HoverListener(UnityCanvas unity) {
		unityCanvas = unity;
	}
	
	public void handleEvent(Event e) {
		unityCanvas.activeTable = (Table)(e.widget);
    	// Identify the selected row
		TableItem item = ((Table)(e.widget)).getItem(new Point(e.x,e.y));
//					System.out.println("item = " + item);
	    if (item == null)
	    {
	    	return;
	    }
//				    System.out.println("item = " + item);
		//System.out.println("theTable.getData(\"name\") = " + (String)(e.widget.getData("name")));
		if(((String)(e.widget.getData("name"))).equals("workspaceTable") || (((String)(e.widget.getData("name"))).equals("tableview")))
		{				
			Table theTable = item.getParent();				
		    if(gc==null) gc = new GC(theTable);
		    int xoff = e.x;
			Rectangle colrec = null;
			int[] order = theTable.getColumnOrder();
			for(int i = 0; i < theTable.getColumnCount(); i++) {
				colrec = item.getBounds(order[i]);
				//System.out.println("colrec x = " + colrec.x + "\n");
				//System.out.println("xoff x = " + xoff + "\n");
				if(colrec.x + colrec.width > xoff){
					EDITABLECOLUMN = order[i];
					break;
				}
			}

			//System.out.println("EDITABLECOLUMN = " + EDITABLECOLUMN);
			
			Integer row = (Integer)(item.getData("uid"));
			Integer col = (Integer)(theTable.getColumn(EDITABLECOLUMN).getData("uid"));
			AssociatedElement element = null;
			//System.out.println("draggedRow = " + draggedRow);
			//System.out.println("draggedCol = " + draggedCol + "\n");
			if(EDITABLECOLUMN > 1){
				xoff = xoff - colrec.x;
				AssociatedElement aElements[] = unityCanvas.getVocabulary().getTerm(row).getAssociatedElements(col);
				for(int i = 0; i < aElements.length; i++){
					xoff = xoff - gc.textExtent(aElements[i].getName() + ", ").x;
					if(xoff <= 0){
						element = aElements[i];
						break;
					}
					if(i == aElements.length - 1){
						element = aElements[i];
						break;									
					}
				}
			}
	     
	      
	      
	
	      
	      // Table tooltip
		  String tooltipText = "";
		  if(col == -201) {
			  tooltipText += unityCanvas.getVocabulary().getTerm(row).getName();
			  tooltipText += "\n";//Description: ";
			  tooltipText +=  WordUtils.wrap(unityCanvas.getVocabulary().getTerm(row).getDescription(),60,"\n",true);
		  } else if(col != -202) {
			  if(element != null){
				  tooltipText += element.getName();
				  tooltipText += "\n";//Description: ";
				  tooltipText +=  WordUtils.wrap(element.getDescription(),60,"\n",true);
			  }					  
		  }
		  theTable.setToolTipText(tooltipText);
		  
	    }

    }
}