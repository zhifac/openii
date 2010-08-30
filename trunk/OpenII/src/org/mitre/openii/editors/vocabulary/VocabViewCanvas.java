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

package org.mitre.openii.editors.vocabulary;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
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
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.Vocabulary;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;


public class VocabViewCanvas extends Canvas {
	private int numSchemas; //num schemas, including common vocab as a schema
	private Integer[] schemaIDs;
	private String[] schemaNames; //schemaNames[0] will always be "Common Vocab"
	private HashMap<Integer, Integer> schemaIDsToColNum;
	private ArrayList<Term> termsArray; 	
	private Table table;
	private Vocabulary vocab;
	
	public VocabViewCanvas(Composite parent, int style, Vocabulary vocabulary) {		
		super(parent, style | SWT.EMBEDDED);

		vocab = vocabulary;
		schemaIDs = vocabulary.getSchemaIDs();
		setSchemaNames(schemaIDs);
		numSchemas = schemaNames.length;

		Term[] terms = vocabulary.getTerms();
		termsArray = new ArrayList<Term>();
		for(int i=0; i<terms.length; i++){
			Term t = terms[i];
			termsArray.add(i, t);
		}
		
		createVocabView();
	}
	
	/** Retrieve the schema names to be used as column headers */
	private void setSchemaNames(Integer[] schemaIDs) {
		schemaNames = new String[schemaIDs.length + 1];
		schemaNames[0] = "Vocabulary";
		schemaIDsToColNum = new HashMap<Integer, Integer>();
		for (int i = 0; i < schemaIDs.length; i++){
			schemaNames[i + 1] = OpenIIManager.getSchema(schemaIDs[i]).getName();
			schemaIDsToColNum.put(schemaIDs[i], i+1);
		}
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
		
		Button buttonMostGroupEs = new Button(buttonsC, SWT.PUSH);
		Image iconImage = OpenIIActivator.getImage("downIcon3.PNG");
		ImageData id = iconImage.getImageData();
		id = id.scaledTo(25, 20);
		Image iconImageScaled = new Image(display, id);	
		buttonMostGroupEs.setImage(iconImageScaled);
		buttonMostGroupEs.setToolTipText("Sorts from most elements in a row to least");
		
		Button buttonLeastGroupEs = new Button(buttonsC, SWT.PUSH);
		Image iconImage2 = OpenIIActivator.getImage("downIcon4.PNG");
		ImageData id2 = iconImage2.getImageData();
		id2 = id2.scaledTo(25, 20);
		Image iconImageScaled2 = new Image(display, id2);
		buttonLeastGroupEs.setImage(iconImageScaled2);
		buttonLeastGroupEs.setToolTipText("Sorts from least elements in a row to most");		 
		
		buttonsC.setLayoutData(new RowData(105, 30));
				
		//creating spreadsheet-evidence split pane
		final SashForm sash = new SashForm(this, SWT.VERTICAL);
		
		//creating table		
		//final Table table = new Table(sash, SWT.BORDER | SWT.FULL_SELECTION);
		table = new Table(sash, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		
		//adding Listener to sort columns alphabetically alphabetically
	    Listener sortAlphabeticallyListener = new Listener() {
	        public void handleEvent(Event e) {
	            TableColumn col = (TableColumn)e.widget;
	            int colNum = 0;
	            for(int i=0; i<table.getColumnCount(); i++){
	            	if(col == table.getColumn(i)){
	            		colNum = i;
	            	}
	            }
	            
            	sortTableItems(3, colNum);
	        }
	    };
	    
		//creating one column for each schema
		TableColumn tempC;
		for(int i=0; i<numSchemas; i++){
			tempC = new TableColumn(table, SWT.NONE);
			tempC.setText(schemaNames[i]);
			tempC.setWidth(150);
			tempC.setMoveable(true);
			tempC.addListener(SWT.Selection, sortAlphabeticallyListener);

			if(i==0){
				tempC.setToolTipText("Double click a cell in the Vocabulary column to edit the Canonical term");
			}
		}
		
		
		//create one row for each synset
		TableItem tempI;
		for(int i=0; i<termsArray.size(); i++){
			tempI = new TableItem(table, SWT.NONE);

			
			tempI.setText(0, termsArray.get(i).getName());
			
						
			AssociatedElement[] elements = termsArray.get(i).getElements();
			for(int j=0; j<elements.length; j++) {
			/*for(int j=0; j<elements.length+1; j++) {
				if(j==elements.length){
					//put in fake element in 2nd col to test multi-term synsets
					int colNum = 2;
					String current = tempI.getText(colNum);
					if(current == null || current == ""){
						tempI.setText(colNum, "fakeEle");
					}else{
						tempI.setText(colNum, current + "\n" + "fakeEle");
					}	

				}else{  */
					AssociatedElement ele = elements[j];
										
					Integer schemaID = elements[j].getSchemaID();
					SchemaInfo si = OpenIIManager.getSchemaInfo(schemaID);
					Integer eleID = elements[j].getElementID();
					String className = si.getElement(eleID).getClass().toString();					
					
					int colNum = schemaIDsToColNum.get(elements[j].getSchemaID());
					String current = tempI.getText(colNum);
					if(current == null || current == ""){
						tempI.setText(colNum, ele.getName());
					}else{
						tempI.setText(colNum, current + ", " + ele.getName());
					}	

					tempI.setImage(colNum, getImageForClass(className));
				//}
			}
						
		}
		
		
		//creating evidence portion of the GUI
		//final Text textControl = new Text(sash, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		final StyledText textControl = new StyledText(sash, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		textControl.setText("<Select a row in the table to see evidence for that synset>");
		
		sash.setWeights(new int[]{75, 25});
		
		//adding listener that allows someone to edit the canonical term if a cell in the Vocabulary column is double clicked
		table.addListener(SWT.MouseDoubleClick, new Listener(){
			public void handleEvent(Event event) {
				//get bounds for the Vocabulary column
				int startXforVocab = 0;
				int[] orderOfCols = table.getColumnOrder();
				for(int i=0; i<orderOfCols.length; i++){
					int inputPos = orderOfCols[i];
					if(inputPos ==0){
						//System.out.println("vocabulary is in col " + i);
						break;
					}else{
						startXforVocab += table.getColumn(inputPos).getWidth();
					}
				}
				
				if(event.getBounds().x > startXforVocab){
					if(event.getBounds().x < startXforVocab + table.getColumn(0).getWidth()){
						//System.out.println("double clicked: " + termsArray.get(table.getSelectionIndex()).getName());
						//want to edit the canonical term
						final TableEditor editor = new TableEditor(table);
						final Text text = new Text(table, SWT.NONE);
						text.setText(termsArray.get(table.getSelectionIndex()).getName());
						//System.out.println("prev name: " + termsArray.get(table.getSelectionIndex()).getName());
						TableItem ti = table.getItem(table.getSelectionIndex());
						editor.setEditor(text, ti, 0); 
						editor.grabHorizontal = true;	
						
						text.addFocusListener(new FocusListener(){
							public void focusGained(FocusEvent e) {
								//do nothing when focus is gained
							}

							public void focusLost(FocusEvent e) {
								String newName = text.getText();
								
								//changing the synset name to the new name in the view
								Term changedTerm = termsArray.get(table.getSelectionIndex());
								changedTerm.setName(newName);
								table.getItem(table.getSelectionIndex()).setText(0, newName);
								
								OpenIIManager.saveVocabulary(vocab);
								
								text.dispose();
								editor.dispose();
							}
						});
						
						text.addKeyListener(new KeyListener(){
							public void keyPressed(KeyEvent e) {
								//do nothing when initially pressed
							}

							public void keyReleased(KeyEvent e) {
								if(e.keyCode == 13){
									//hit enter
									String newName = text.getText();
									
									//changing the synset name to the new name in the view
									Term changedTerm = termsArray.get(table.getSelectionIndex());
									changedTerm.setName(newName);
									
									//OpenIIManager.
									table.getItem(table.getSelectionIndex()).setText(0, newName);
									
									OpenIIManager.saveVocabulary(vocab);

									text.dispose();
									editor.dispose();
								}
							}
						});
					}
				}
			}
		});
		
		
		//adding the Listener to the table so that evidence can be displayed in the bottom pane
		table.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event){
				Integer rowNumSelected = table.getSelectionIndex();
				Term termSelected = termsArray.get(rowNumSelected);
				String synset = termSelected.getName();				
				String evidenceText = "\"" + synset + "\" \n\n";
			
				AssociatedElement[] aes = termSelected.getElements();
				for(int i=0; i<aes.length; i++){
						String elementName = aes[i].getName();
						
						Integer schemaID = aes[i].getSchemaID();
						SchemaInfo si = OpenIIManager.getSchemaInfo(schemaID);

						Integer eleID = aes[i].getElementID();
						String elementDescription = si.getElement(eleID).getDescription();						
						String containingSchemaName = si.getSchema().getName();
						
						evidenceText += elementName + ":\t\t" + elementDescription;
						evidenceText += " (from " + containingSchemaName + ")\n";


				}

				
				StyleRange style1 = new StyleRange();
				style1.start = 0;
				style1.length = synset.length() + 2;
				style1.underline = true;

				textControl.setText(evidenceText);	
				textControl.setStyleRange(style1);
			}
		});


		
		
		//sorting from most to least GroupEs 
		buttonMostGroupEs.addListener(SWT.Selection, new Listener(){
		public void handleEvent(Event event){
			sortTableItems(1, -1);
		}
		});


		//sorting from least to most GroupEs 
		buttonLeastGroupEs.addListener(SWT.Selection, new Listener(){
		public void handleEvent(Event event){
			sortTableItems(2, -1);
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

	//getting the icons that represent the type of entity in a cell
	private Image getImageForClass(String className){		
		if(className.compareTo("class org.mitre.schemastore.model.Containment") ==0){
			return OpenIIActivator.getImage("Containment.jpg");
		}else if(className.compareTo("class org.mitre.schemastore.model.DomainValue") ==0){
			return OpenIIActivator.getImage("DomainValue.jpg");
		}else if(className.compareTo("class org.mitre.schemastore.model.Attribute") ==0){
			return OpenIIActivator.getImage("Attribute.jpg");				
		}else{
			return null;
		}
	}
	
	//if sortBy= 1 it will sort from most to least GroupEs
	//if sortBy= 2 it will sort from least to most GroupEs
	//if sortBy= 3 it will sort from a to z, uses colIndex only for 3
	//basically, it's a helper function that does all the work
	private void sortTableItems(int sortBy, int colIndex){
		TableItem[] allRows = table.getItems();
		
		//for each row in the table, starting with the 2nd
		for(int i=1; i<allRows.length; i++){
			//compare the row to all previously sorted rows
			for(int j=0; j<i; j++){
				boolean move = false;
				if(sortBy==1 || sortBy==2){
					int ithGroupEs = termsArray.get(i).getElements().length;
					int jthGroupEs = termsArray.get(j).getElements().length;
					if(sortBy==1 && ithGroupEs > jthGroupEs){
						move = true;
					}else if(sortBy==2 && ithGroupEs < jthGroupEs){
						move = true;
					}
				}else if(sortBy==3){
					if(allRows[i].getText(colIndex).compareToIgnoreCase(allRows[j].getText(colIndex)) < 0){
							move = true;
					}
				}

				if(move == true){
					//create a temp tableItem that has all info, going to get rid of cur ith row
					String[] tempRow = new String[schemaNames.length];
					Image[] tempImages = new Image[schemaNames.length];	
					
					for(int k=0; k<schemaNames.length; k++){
						tempRow[k] = allRows[i].getText(k);
						tempImages[k] = allRows[i].getImage(k);
					}
					
					//get rid of the previous ith row
					allRows[i].dispose();
					
					TableItem movedItem = new TableItem(table, SWT.NONE, j);
					movedItem.setText(tempRow);
					movedItem.setImage(tempImages);		
					
					//need to reorder the Terms array too
					Term temp = termsArray.get(i);
					termsArray.remove(i);
					termsArray.add(j, temp);
					
					//now work with new table items, and go on to next row
					allRows = table.getItems();
					break;
					}
				}
			}						
	}

	private int getX(){
		return this.getBounds().width;
	}

	private int getY(){
		return this.getBounds().height;
	}
}
