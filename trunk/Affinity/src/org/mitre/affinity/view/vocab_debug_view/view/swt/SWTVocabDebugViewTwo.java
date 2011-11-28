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

package org.mitre.affinity.view.vocab_debug_view.view.swt;


import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class SWTVocabDebugViewTwo extends Canvas {
	private int numSchemas; //num schemas, including common vocab as a schema
	private int numSynsets;
	private String[] schemaNames; //schemaNames[0] will always be "Common Vocab"
	private ArrayList<String[]> synsets;
	private int coreColNum;
	
	public SWTVocabDebugViewTwo(Composite parent, int style, String[] schemaNamesArray, ArrayList<String[]> synsetsArray) {		
		super(parent, style | SWT.EMBEDDED);
		coreColNum = schemaNamesArray.length - 1;
		setData(schemaNamesArray, synsetsArray);
		createVocabView();
	}
	
	private void setData(String[] schemaNamesArray, ArrayList<String[]> synsetsArray){
		//getting all the column information - schema names
		//numSchemas = 5; //this number includes the core vocab as the first schema
		//schemaNames = new String[numSchemas];
		//schemaNames[0] = "Common Vocab"; //first schema is always called Common Vocab
		//for(int i=1; i<numSchemas; i++){
		//	schemaNames[i] = "schema " + i;
		//}
		numSchemas = schemaNamesArray.length;
		schemaNames = schemaNamesArray;
		
		//getting all the synset information
		numSynsets = synsetsArray.size();
		synsets = synsetsArray;
		
		/*		
		synsets = new ArrayList<String[]>();
		String[] temp;
		for(int i=0; i<numSynsets; i++){
			temp = new String[numSchemas];
			for(int j=0; j<numSchemas; j++){
				temp[j] = "sch" + j + " syn " + i + "term";
			}
			synsets.add(i, temp);
		} 
		*/
	}
	
	private void createVocabView(){
		Display display = getDisplay();
		this.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		RowLayout layout = new RowLayout();
		layout.wrap = false;
		layout.pack = true;
		layout.type = SWT.VERTICAL;
		this.setLayout(layout);
		
		//creating sort buttons
		Composite buttonsC = new Composite(this, SWT.NONE);
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.HORIZONTAL;
		buttonsC.setLayout(fillLayout);
		
		Button button1 = new Button(buttonsC, SWT.PUSH);
		button1.setText("Most");
		/*String filename = "\\icons\\downIcon3.PNG";
		Image iconImage = new Image(display, filename);
		ImageData id = iconImage.getImageData();
		id = id.scaledTo(25, 20);
		Image iconImageScaled = new Image(display, id);	
		button1.setImage(iconImageScaled);
		*/
		Button button2 = new Button(buttonsC, SWT.PUSH);
		button2.setText("Least");
		/*String filename2 = "\\icons\\downIcon4.PNG";
		Image iconImage2 = new Image(display, filename2);
		ImageData id2 = iconImage2.getImageData();
		id2 = id2.scaledTo(25, 20);
		Image iconImageScaled2 = new Image(display, id2);
		button2.setImage(iconImageScaled2);
*/
		
		buttonsC.setLayoutData(new RowData(105, 30));
				
		//creating spreadsheet-evidence split pane
		final SashForm sash = new SashForm(this, SWT.VERTICAL);
		
		//creating table		
		final Table table = new Table(sash, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		//create one column for each schema
		TableColumn tempC;
		for(int i=0; i<numSchemas; i++){
			tempC = new TableColumn(table, SWT.NONE);
			tempC.setText(schemaNames[i]);
			tempC.setWidth(150);
			tempC.setMoveable(true);
		}
		
		//create one row for each synset
		TableItem tempI;
		for(int i=0; i<numSynsets; i++){
			tempI = new TableItem(table, SWT.NONE);
			tempI.setText(synsets.get(i));
		}
		//setting layout for spreadsheet		
		//RowData rd = new RowData();
		//table.setLayoutData(rd);
		
		
		//creating evidence portion of the GUI
		final Text textControl = new Text(sash, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		textControl.setText("<Select a row in the table to see evidence for that synset>");
		//textControl.setSize(750, 200);
		//RowData rd2 = new RowData();	
		//textControl.setLayoutData(rd2);
	
		
		sash.setWeights(new int[]{75, 25});
		
		
		
		//adding the Listener to the table so that evidence can be displayed
		table.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event){
				TableItem selectedItem = (TableItem)event.item;
				//String synset = selectedItem.getText();
				String synset = selectedItem.getText(coreColNum);
				
				String evidenceText = "\n Evidence \n\n\t" +
					"\"" + synset + "\" selected as synset name because... \n\n\t";
				

				for(int i=0; i<numSchemas; i++){
					if(i != coreColNum){
						String elementName = selectedItem.getText(i);
						if(elementName != ""){	
							evidenceText = evidenceText + schemaNames[i] + ": \""
									+ selectedItem.getText(i) + "\" belongs to this synset because... \n\t";
						}
					}
				}
				textControl.setText(evidenceText);
			}
		});
		
		
	
		
		//adding resize capability so fills in space nicely
		this.addListener(SWT.Resize, new Listener(){
			public void handleEvent(Event event){
				int curW = getX();
				int curH = getY() - 35; //subtract 30 because of buttons in top row
				RowData rd = new RowData(curW, curH);
				sash.setLayoutData(rd);			
			}
		});
				
	}
	
	private int getX(){
		return this.getBounds().width;
	}

	private int getY(){
		return this.getBounds().height;
	}
}
