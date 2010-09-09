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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.Vocabulary;
import org.mitre.schemastore.model.mappingInfo.AssociatedElementHash;
import org.mitre.schemastore.model.mappingInfo.MappingInfoExt;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;


public class VocabViewCanvas extends Canvas {
	private int numSchemas; //num schemas, including common vocab as a schema
	private Integer[] schemaIDs;
	private String[] schemaNames; //schemaNames[0] will always be "Common Vocab"
	private HashMap<Integer, Integer> schemaIDsToColNum;
	private ArrayList<Term> termsArray; 	
	private Table table;
	private Vocabulary vocab;
	private Composite viewsParent;
	private ArrayList<Point> highlightedTableItems;
	private ArrayList<Mapping> mappings;
	private boolean confirmation;
	
	public VocabViewCanvas(Composite parent, int style, Vocabulary vocabulary) {		
		super(parent, style | SWT.EMBEDDED);
		viewsParent = parent;
		
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
		
		highlightedTableItems = new ArrayList<Point>();
		
		Integer projID = vocab.getProjectID();
		mappings = OpenIIManager.getMappings(projID);

		confirmation = false;
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
		buttonMostGroupEs.setToolTipText("Sorts from most to least agreement (most elements in a row to least)");
		
		Button buttonLeastGroupEs = new Button(buttonsC, SWT.PUSH);
		Image iconImage2 = OpenIIActivator.getImage("downIcon4.PNG");
		ImageData id2 = iconImage2.getImageData();
		id2 = id2.scaledTo(25, 20);
		Image iconImageScaled2 = new Image(display, id2);
		buttonLeastGroupEs.setImage(iconImageScaled2);
		buttonLeastGroupEs.setToolTipText("Sorts from least to most agreement (least elements in a row to most)");		 
		
		Button mergeButton = new Button(buttonsC, SWT.PUSH);
		mergeButton.setText("Merge");
		mergeButton.setToolTipText("Merges two rows into a single row");
		
		Button searchButton = new Button(buttonsC, SWT.PUSH);
		searchButton.setText("Search");
		searchButton.setToolTipText("Finds all occurences of a term in the spreadsheet (does not search documentation)");
		
		
		buttonsC.setLayoutData(new RowData(155, 30));
				
		//creating spreadsheet-evidence split pane
		final SashForm sash = new SashForm(this, SWT.VERTICAL);
		
		//creating table		
		//final Table table = new Table(sash, SWT.BORDER | SWT.FULL_SELECTION);
		table = new Table(sash, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
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
				tempC.setToolTipText("Double click a cell in the Vocabulary column to edit the vocabulary term");
			}
		}
		

		//Color widghetLightShadow  = this.getDisplay().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
		
		//create one row for each synset
		TableItem tempI;
		for(int i=0; i<termsArray.size(); i++){
			tempI = new TableItem(table, SWT.NONE);
			//if(i%2 == 0){
				//tempI.setBackground(widghetLightShadow);
			//}
		
			tempI.setText(0, termsArray.get(i).getName());
			Font boldFont = new Font(Display.getDefault(), new FontData("Arial", 8, SWT.BOLD));
			tempI.setFont(0, boldFont);
			
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
						tempI.setText(colNum, current + ", " + "fakeEle");
					}	

				}else{  */
					AssociatedElement ele = elements[j];
										
					Integer schemaID = elements[j].getSchemaID();
					SchemaInfo si = OpenIIManager.getSchemaInfo(schemaID);
					Integer eleID = elements[j].getElementID();
					SchemaElement schemaEle = si.getElement(eleID);
					//String className = schemaEle.getClass().toString();					
					
					int colNum = schemaIDsToColNum.get(elements[j].getSchemaID());
					String current = tempI.getText(colNum);
					if(current == null || current == ""){
						tempI.setText(colNum, ele.getName());
					}else{
						tempI.setText(colNum, current + ", " + ele.getName());
					}	

					Image img = org.mitre.openii.widgets.schemaTree.SchemaElementLabelProvider.getImage(schemaEle);
					tempI.setImage(colNum, img);
				//}
			}
						
		}
		
		
		//creating evidence portion of the GUI
		//final StyledText textControl = new StyledText(sash, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		//textControl.setText("<Select a row in the table to see evidence for that synset>");

		final Table evidenceTable = new Table(sash, SWT.BORDER);
		evidenceTable.setHeaderVisible(true);


				
		//evidenceTable.setLinesVisible(true);
		TableColumn evidence_elementNameCol = new TableColumn(evidenceTable, SWT.NONE);
		evidence_elementNameCol.setText("Name");
		evidence_elementNameCol.setWidth(100);
		//evidence_elementNameCol.addListener(SWT.Selection, sortResultsAlphabeticallyListener);
		

		TableColumn evidence_elementDescCol = new TableColumn(evidenceTable, SWT.NONE);
		evidence_elementDescCol.setText("Description");
		evidence_elementDescCol.setWidth(100);
		//evidence_elementDescCol.addListener(SWT.Selection, sortResultsAlphabeticallyListener);

		TableColumn evidence_elementSrcCol = new TableColumn(evidenceTable, SWT.NONE);
		evidence_elementSrcCol.setText("Source");
		evidence_elementSrcCol.setWidth(100);

		
		TableColumn evidence_matcheScoreCol = new TableColumn(evidenceTable, SWT.NONE);
		evidence_matcheScoreCol.setText("Match Score");
		evidence_matcheScoreCol.setWidth(100);
		
		TableColumn evidence_elementMatchesNameCol = new TableColumn(evidenceTable, SWT.NONE);
		evidence_elementMatchesNameCol.setText("Match Name");
		evidence_elementMatchesNameCol.setWidth(100);
		//evidence_elementNameCol.addListener(SWT.Selection, sortResultsAlphabeticallyListener);
		
		TableColumn evidence_elementMatchesDescCol = new TableColumn(evidenceTable, SWT.NONE);
		evidence_elementMatchesDescCol.setText("Match Description");
		evidence_elementMatchesDescCol.setWidth(100);
		
		TableColumn evidence_elementMatchesSourceCol = new TableColumn(evidenceTable, SWT.NONE);
		evidence_elementMatchesSourceCol.setText("Match Source");
		evidence_elementMatchesSourceCol.setWidth(100);
		
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
				displayEvidence(evidenceTable, termSelected);
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
		
		searchButton.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event) {
				createSearchDialog();
			}
		});

		
		mergeButton.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub		
				//first create dialog warning users
				//boolen confirm = confirmAction();
				System.out.println("selected merge button");
				createWarningDialog();
				if(confirmation){
					int[] rowNumSelected = table.getSelectionIndices();
					mergeRows(rowNumSelected);
				}
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

	private void mergeRows(int[] selectedRows){
		//create a new Term
		Term newMergedTerm = new Term();
		String newTermName = "";
		

		for(int i=0; i<selectedRows.length; i++){
			Term currTerm = termsArray.get(selectedRows[i]);
			if(i==selectedRows.length-1){
				newTermName += currTerm.getName();
			}else{
				newTermName += currTerm.getName() + "-";
			}
			
			//numElementsInNewTerm =+  termsArray.get(selectedRows[i]).getElements().length;
		}
		
		newMergedTerm.setName("NewMergedTermTest");
		
		//AssociatedElement[] newMergedElements = new AssociatedElement[];
		//newMergedTerm.setElements(newMergedElements);
		
		//merge all selected rows	
	}
	
	/** creates the dialog that confirms a user really wanted to split or merge cells **/
	private void createWarningDialog(){
		Display display = viewsParent.getDisplay();
		final Shell warningDialog = new Shell(display, SWT.TITLE| SWT.APPLICATION_MODAL);
		warningDialog.setText("Warning");
		warningDialog.setLayout(new GridLayout(2, false));
		//searchDialog.setSize(400, 500);
		
		Label label = new Label(warningDialog, SWT.NULL);
		label.setText("Are you sure you want to perform this action?");
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);	
		
		Button okButton = new Button(warningDialog, SWT.NONE);
		okButton.setText("Ok");
		okButton.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event){
				confirmation = true;
				warningDialog.dispose();
			}
		});
		
		Button cancelButton = new Button(warningDialog, SWT.NONE);
		cancelButton.setText("Cancel");
		cancelButton.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event){
				confirmation = false;
				warningDialog.dispose();
			}
		});
		
		warningDialog.pack();
		warningDialog.open();
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
					Font boldFont = new Font(Display.getDefault(), new FontData("Arial", 8, SWT.BOLD));
					movedItem.setFont(0, boldFont);

					
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
	

	
	/**creating the dialog that pops up when a user clicks on the search button**/
	private void createSearchDialog(){
		Display display = viewsParent.getDisplay();
		final Shell searchDialog = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		//final Shell searchDialog = new Shell(display);
		searchDialog.setText("Vocabulary Search");
		searchDialog.setLayout(new GridLayout(2, false));
		searchDialog.setSize(400, 500);
		
		Label label = new Label(searchDialog, SWT.NULL);
		label.setText("Search:");
		final Text searchField = new Text(searchDialog, SWT.SEARCH);
		searchField.setLayoutData(new GridData(GridData.FILL));
		
		final Table resultsTable = new Table(searchDialog, SWT.BORDER | SWT.FULL_SELECTION | SWT.RESIZE);
		resultsTable.setHeaderVisible(true);
		resultsTable.setLinesVisible(true);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.heightHint = 250;
		resultsTable.setLayoutData(gd);			

		//adding Listener to sort columns alphabetically alphabetically
	    Listener sortResultsAlphabeticallyListener = new Listener() {
	        public void handleEvent(Event e) {
	            //finding the column number
	        	TableColumn col = (TableColumn)e.widget;
	            int colNum = 0;
	            for(int i=0; i<resultsTable.getColumnCount(); i++){
	            	if(col == resultsTable.getColumn(i)){
	            		colNum = i;
	            	}
	            }
	            
	            //sort items alphabetically base on that column
	        	TableItem[] allRows = resultsTable.getItems();
	    		//for each row in the table, starting with the 2nd
	    		for(int i=1; i<allRows.length; i++){
	    			//compare the row to all previously sorted rows
	    			for(int j=0; j<i; j++){
    					if(allRows[i].getText(colNum).compareToIgnoreCase(allRows[j].getText(colNum)) < 0){
	    							String[] tempRow = new String[3];
	    							Image[] tempImage = new Image[3];
	    							Object tempData = allRows[i].getData();
	    							for(int k=0; k<3; k++){
	    								tempRow[k] = allRows[i].getText(k);
	    								tempImage[k] = allRows[i].getImage(k);
	    							}
	    							
	    							//get rid of the previous ith row
	    							allRows[i].dispose();
	    							
	    							TableItem movedItem = new TableItem(resultsTable, SWT.NONE, j);
	    							movedItem.setText(tempRow);
	    							movedItem.setData(tempData);
	    							movedItem.setImage(tempImage);
	    							
	    							//now work with new table items, and go on to next row
	    							allRows = resultsTable.getItems();
	    							break;
	    				}
	    			}
	    		}
	        }
	    };
	    
		TableColumn synsetNameCol = new TableColumn(resultsTable, SWT.NONE);
		synsetNameCol.setText("Vocabulary Term");
		synsetNameCol.setWidth(100);
		synsetNameCol.addListener(SWT.Selection, sortResultsAlphabeticallyListener);
		
		TableColumn schemaNameCol = new TableColumn(resultsTable, SWT.NONE);
		schemaNameCol.setText("Source");
		schemaNameCol.setWidth(100);
		schemaNameCol.addListener(SWT.Selection, sortResultsAlphabeticallyListener);
		
		TableColumn textCol = new TableColumn(resultsTable, SWT.NONE);
		textCol.setText("Source Term");
		textCol.setWidth(100);
		textCol.addListener(SWT.Selection, sortResultsAlphabeticallyListener);
	
	    
		searchField.addSelectionListener(new SelectionAdapter(){
			public void widgetDefaultSelected(SelectionEvent e){
				String searchTerm = searchField.getText();
				if(searchTerm != null && searchTerm != ""){
					displaySearchResults(resultsTable, searchTerm);
				}
			}
		});
		

		
		resultsTable.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event){
				//un-highlight any previously selected cells
				for(int i=0; i<highlightedTableItems.size(); i++){
					Point location = highlightedTableItems.get(i);
					table.getItem(location.x).setBackground(location.y, table.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
				}
				
				//highlight the selected searchresults cell in the table
				TableItem[] selectedItem = resultsTable.getSelection();
				table.deselectAll();
				for(int i=0; i<selectedItem.length; i++){
					//System.out.println("selected: " + selectedItem[i].getText(0));
					Point location = (Point)selectedItem[i].getData();
					//System.out.println("location: " + location.x + " " + location.y);
					table.setTopIndex(location.x);
					table.getItem(location.x).setBackground(location.y, table.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
					highlightedTableItems.add(location);
				}
			}
		});
		
		searchDialog.addDisposeListener(new DisposeListener(){
			public void widgetDisposed(DisposeEvent e) {
				//un-highlight any previously selected cells
				for(int i=0; i<highlightedTableItems.size(); i++){
					Point location = highlightedTableItems.get(i);
					table.getItem(location.x).setBackground(location.y, table.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
				}
			}
		});
		
		searchDialog.pack();
		searchDialog.open();
	}
	
	
	
	
	/**displays the evidence in the evidenceTable**/
	//0-name, 1-description, 2-source, 3- match score, 4-name of match, 5-match description, 6-match source
	private void displayEvidence(Table evidTable, Term selectedTerm){
		//clear all of the previous table items
		TableItem[] allRows = evidTable.getItems();
		for(int i=0; i<allRows.length; i++){
			allRows[i].dispose();
		}

		
		//add the first row to be the vocab term, and description
		TableItem vocabItem = new TableItem(evidTable, SWT.NONE);
		vocabItem.setText(0, selectedTerm.getName());
		vocabItem.setText(1, selectedTerm.getDescription());
		Font boldFont = new Font(Display.getDefault(), new FontData("Arial", 8, SWT.BOLD));
		//vocabItem.setFont(0, boldFont);
		vocabItem.setFont(0, boldFont);
		
		//add in evidence for each element associated with the vocab term	
		AssociatedElement[] aes = selectedTerm.getElements();
		//System.out.println("saw this many associated elements: " + aes.length);

		Color lightShadow  = this.getDisplay().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
		Color white = this.getDisplay().getSystemColor(SWT.COLOR_WHITE);
		Color currColor = white;
		
		for(int i=0; i<aes.length; i++){
				String elementName = aes[i].getName();
		
				//want to alternate the table background color based on schema
				if(i%2 == 0){
					currColor = lightShadow;
				}else{
					currColor = white;
				}
				
				//get the original element's ID and description
				Integer schemaID = aes[i].getSchemaID();
				SchemaInfo si = OpenIIManager.getSchemaInfo(schemaID);
				Integer eleID = aes[i].getElementID();
				String elementDescription = si.getElement(eleID).getDescription();						
				String containingSchemaName = si.getSchema().getName();
				

				TableItem nextItem = new TableItem(evidTable, SWT.NONE);

				nextItem.setText(0, elementName);
				nextItem.setText(1, elementDescription);
				nextItem.setText(2, containingSchemaName);
				nextItem.setBackground(currColor);
				
				
				//for each mapping, we need to find any cells that map to the current element
				for(Mapping m : mappings){
					ArrayList<MappingCell> mappingCells = OpenIIManager.getMappingCells(m.getId());					
					MappingInfoExt mix = new MappingInfoExt(m, mappingCells);
					AssociatedElementHash aeh = mix.getMappingCells();
					ArrayList<MappingCell> alm = aeh.get(eleID);
					
					Integer otherElementsSchemaID = null;
					if(alm.size()!=0){
						Integer srcSchemaID = m.getSourceId();
						Integer trgSchemaID = m.getTargetId();
						//need to get the schemaID for the element that mapped to the current element
						if(srcSchemaID == trgSchemaID){
							System.out.println("schema was mapped to self");
						}
						if(srcSchemaID == schemaID){
							otherElementsSchemaID = trgSchemaID;
						}else{
							otherElementsSchemaID = srcSchemaID;
						}
					}
					
					//add the name of the element and match score to the Related Elements string
					for(int k=0; k<alm.size();k++){
						MappingCell mc = alm.get(k);
						//System.out.println(mc.toString());
						
						//get the ID of the other element
						Integer[] elementIDs = mc.getElementInputIDs();
						Integer otherElementsID = null;
						for(int n=0; n<elementIDs.length; n++){
							if(elementIDs[n] != eleID){
								otherElementsID = elementIDs[n];
							}
						}
						
						String otherElementsContainingSchemaName = "";
						String otherElementsName = "";
						String score = "";
						String otherElementsDescription = "";
						if(otherElementsSchemaID != null && otherElementsID != null){
							SchemaInfo otherElementsSchemaInfo = OpenIIManager.getSchemaInfo(otherElementsSchemaID);			
							otherElementsContainingSchemaName = otherElementsSchemaInfo.getSchema().getName();
							otherElementsName = otherElementsSchemaInfo.getElement(otherElementsID).getName();
							otherElementsDescription = otherElementsSchemaInfo.getElement(otherElementsID).getDescription();
							score = mc.getScore().toString();
						}
						
						//if it is the first related element found then put it in the row with the initial element
						//otherwise, create a new row for it
						//0-name, 1-description, 2-source, 3- match score, 4-name of match, 5-match description, 6-match source
						if(k==0){
							nextItem.setText(3, score);
							nextItem.setText(4, otherElementsName);
							nextItem.setText(5, otherElementsDescription);
							nextItem.setText(6, otherElementsContainingSchemaName);
						}else{
							TableItem newRelatedItem = new TableItem(evidTable, SWT.NONE);
							newRelatedItem.setBackground(currColor);
							newRelatedItem.setText(3, score);
							newRelatedItem.setText(4, otherElementsName);
							nextItem.setText(5, otherElementsDescription);
							newRelatedItem.setText(6, otherElementsContainingSchemaName);
						}
					}
				} 
		}
	}
	
	
	
	/**displays the search results table in the search vocabulary dialog box**/
	private void displaySearchResults(Table tableForSearchResults, String searchTerm){
		//clear all of the previous table items
		TableItem[] allRows = tableForSearchResults.getItems();
		for(int i=0; i<allRows.length; i++){
			allRows[i].dispose();
		}

		//want to find substrings
		Pattern pattern = Pattern.compile("(?i)" + searchTerm);

		//as items are found in the vocabulary viewer table, put the appropriate information in the search results table		
		TableItem[] allVocabRows = table.getItems();
		for(Integer i=0; i<allVocabRows.length; i++){
			for(int j=0; j<table.getColumnCount(); j++){
				String vocabCellText = allVocabRows[i].getText(j);
				
				//want to find substrings
				Matcher matcher = pattern.matcher(vocabCellText);
				
				
				if(matcher.find()){	
				//if(vocabCellText.compareToIgnoreCase(searchTerm) == 0){
					TableItem tempI = new TableItem(tableForSearchResults, SWT.NONE);
					tempI.setText(0, allVocabRows[i].getText(0));
					tempI.setText(1, table.getColumn(j).getText());
					//tempI.setText(2, searchTerm);
					tempI.setText(2, vocabCellText);
					
					tempI.setImage(2, allVocabRows[i].getImage(j));

					Point location = new Point(i, j);
					tempI.setData(location);
				}
			}
			
		}
		
	}
}

