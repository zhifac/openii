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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.editors.vocabulary.VocabularySort.AlignedSchemasSorter;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.mappingInfo.AssociatedElementHash;
import org.mitre.schemastore.model.mappingInfo.MappingInfoExt;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;
import org.mitre.schemastore.model.terms.AssociatedElement;
import org.mitre.schemastore.model.terms.Term;
import org.mitre.schemastore.model.terms.VocabularyTerms;

public class VocabViewCanvas extends Canvas
{
	/** Class used to cache information from the SchemaStore repository */
	private class SchemaStoreCache
	{
		/** Stores the various schemas */
		private Hashtable<Integer, SchemaInfo> schemas = new Hashtable<Integer, SchemaInfo>();
		
		/** Returns the requested schema */	
		private SchemaInfo getSchemaInfo(Integer schemaID)
		{
			if(!schemas.containsKey(schemaID))
				schemas.put(schemaID, OpenIIManager.getSchemaInfo(schemaID));
			return schemas.get(schemaID);
		}
		
		/** Returns the requested schema element */
		private SchemaElement getSchemaElement(Integer schemaId, Integer elementId)
			{ return getSchemaInfo(schemaId).getElement(elementId); }
	}	
	
	/** Stores the cache used to reference SchemaStore */
	private SchemaStoreCache cache = new SchemaStoreCache();
	private HashMap<ImageDescriptor, Image> imageCache = new HashMap<ImageDescriptor, Image>();
	private int numSchemas; // num schemas, including common vocab as a schema
	private Integer[] schemaIDs;
	private String[] schemaNames; // schemaNames[0] will always be
									// "Common Vocab"
	private HashMap<Integer, Integer> schemaIDsToColNum;
	private ArrayList<Term> termsArray;
	private Table table;
	private VocabularyTerms vocabTerms;
	private Composite viewsParent;
	private ArrayList<Point> highlightedTableItems;
	private ArrayList<Mapping> mappings;
	private boolean coloredTable;

	private Color white =  this.getDisplay().getSystemColor(SWT.COLOR_WHITE);
	private Color lightYellow = new Color(this.getDisplay(), 255, 255, 128);
	private Color lightGreen = new Color(this.getDisplay(), 128, 225, 128);
	private Color lightRed = new Color(this.getDisplay(), 255, 128, 128);
	
	public VocabViewCanvas(Composite parent, int style, VocabularyTerms vocabTerms) {
		super(parent, style | SWT.EMBEDDED);
		viewsParent = parent;

		this.vocabTerms = vocabTerms;
		schemaIDs = vocabTerms.getSchemaIDs();
		setSchemaNames(schemaIDs);
		numSchemas = schemaNames.length;

		Term[] terms = vocabTerms.getTerms();
		termsArray = new ArrayList<Term>();
		for (int i = 0; i < terms.length; i++) {
			Term t = terms[i];
			termsArray.add(i, t);
		}

		highlightedTableItems = new ArrayList<Point>();

		Integer projID = vocabTerms.getProjectID();
		mappings = OpenIIManager.getMappings(projID);

		createVocabView();
	}

	/** Retrieve the schema names to be used as column headers */
	private void setSchemaNames(Integer[] schemaIDs) {
		schemaNames = new String[schemaIDs.length + 1];
		schemaNames[0] = "Vocabulary";
		schemaIDsToColNum = new HashMap<Integer, Integer>();
		for (int i = 0; i < schemaIDs.length; i++) {
			schemaNames[i + 1] = OpenIIManager.getSchema(schemaIDs[i]).getName();
			schemaIDsToColNum.put(schemaIDs[i], i + 1);
		}
	}

	private void createVocabView() {
		Display display = getDisplay();
		this.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		RowLayout layout = new RowLayout();
		layout.wrap = false;
		layout.pack = true;
		layout.type = SWT.VERTICAL;
		this.setLayout(layout);

		// creating sort buttons
		Composite buttonsC = new Composite(this, SWT.NONE);
		//FillLayout fillLayout = new FillLayout();
		//fillLayout.type = SWT.HORIZONTAL;
		//buttonsC.setLayout(fillLayout);
		GridLayout gl = new GridLayout(5, false);
		buttonsC.setLayout(gl);
		
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
		
		
		//Button mergeButton = new Button(buttonsC, SWT.PUSH);
		//mergeButton.setText("Merge");
		//mergeButton.setToolTipText("Merges two rows into a single row");

		Label colorToggle = new Label(buttonsC, SWT.NONE);
		colorToggle.setText("Color by Exact Name Match:");
		final Combo colorCombo = new Combo(buttonsC, SWT.DROP_DOWN);	
		colorCombo.add("On");
		colorCombo.add("Off");
		colorCombo.select(1);
		coloredTable = false;
		colorCombo.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent e) {				
				if(colorCombo.getSelectionIndex() != -1) {
					if(colorCombo.getSelectionIndex() == 0){
						//System.out.println("color the table");
						if(!coloredTable){
							coloredTable=true;
							colorTheTable();
						}
					}else{
						//System.out.println("do not color the table");
						if(coloredTable){
							coloredTable=false;
							colorTheTable();
						}
					}
				}
			}
		});

		Button searchButton = new Button(buttonsC, SWT.PUSH);
		searchButton.setText("Search");
		searchButton.setToolTipText("Finds all occurences of a term in the spreadsheet (does not search documentation)");
		

		// creating spreadsheet-evidence split pane
		final SashForm sash = new SashForm(this, SWT.VERTICAL);

		// creating table
		// final Table table = new Table(sash, SWT.BORDER | SWT.FULL_SELECTION);
		table = new Table(sash, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		/** Sorts the terms alphabetically by the selected column */
		Listener sortAlphabeticallyListener = new Listener() {
			public void handleEvent(Event e)
			{
				// Identify the selected column
				TableColumn col = (TableColumn) e.widget;
				int colNum = 0;
				for (int i = 0; i < table.getColumnCount(); i++)
					if (col == table.getColumn(i)) colNum = i;

				// Sort the table
				Integer schemaID = null;
				if(colNum>0) schemaID = vocabTerms.getSchemaIDs()[colNum-1];
				sortTableItems(new VocabularySort.SpecifiedSchemaSorter(schemaID));
			}
		};

		// creating one column for each schema
		TableColumn tempC;
		for (int i = 0; i < numSchemas; i++) {
			tempC = new TableColumn(table, SWT.NONE);
			tempC.setText(schemaNames[i]);
			tempC.setWidth(150);
			tempC.setMoveable(true);
			tempC.addListener(SWT.Selection, sortAlphabeticallyListener);

			if (i == 0) {
				tempC.setToolTipText("Double click a cell in the Vocabulary column to edit the vocabulary term");
			}
		}

		// Populate the table with term information
		populateTable();

		// creating evidence portion of the GUI
		// final StyledText textControl = new StyledText(sash, SWT.MULTI |
		// SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		// textControl.setText("<Select a row in the table to see evidence for that synset>");

		final Table evidenceTable = new Table(sash, SWT.BORDER | SWT.SINGLE);
		evidenceTable.setHeaderVisible(true);

		TableColumn evidence_elementSrcCol = new TableColumn(evidenceTable, SWT.NONE);
		evidence_elementSrcCol.setText("Schema");
		evidence_elementSrcCol.setWidth(100);

		// evidenceTable.setLinesVisible(true);
		TableColumn evidence_elementNameCol = new TableColumn(evidenceTable, SWT.NONE);
		evidence_elementNameCol.setText("Term");
		evidence_elementNameCol.setWidth(100);
		// evidence_elementNameCol.addListener(SWT.Selection,
		// sortResultsAlphabeticallyListener);

		TableColumn evidence_elementDescCol = new TableColumn(evidenceTable, SWT.NONE);
		evidence_elementDescCol.setText("Description");
		evidence_elementDescCol.setWidth(100);
		// evidence_elementDescCol.addListener(SWT.Selection,
		// sortResultsAlphabeticallyListener);

		TableColumn evidence_matcheScoreCol = new TableColumn(evidenceTable, SWT.NONE);
		evidence_matcheScoreCol.setText("Match Score");
		evidence_matcheScoreCol.setWidth(100);

		TableColumn evidence_elementMatchesSourceCol = new TableColumn(evidenceTable, SWT.NONE);
		evidence_elementMatchesSourceCol.setText("(Matched Term) Schema");
		evidence_elementMatchesSourceCol.setWidth(100);

		TableColumn evidence_elementMatchesNameCol = new TableColumn(evidenceTable, SWT.NONE);
		evidence_elementMatchesNameCol.setText("(Matched) Term");
		evidence_elementMatchesNameCol.setWidth(100);
		// evidence_elementNameCol.addListener(SWT.Selection,
		// sortResultsAlphabeticallyListener);

		TableColumn evidence_elementMatchesDescCol = new TableColumn(evidenceTable, SWT.NONE);
		evidence_elementMatchesDescCol.setText("(Matched Term) Description");
		evidence_elementMatchesDescCol.setWidth(100);


		sash.setWeights(new int[] { 75, 25 });

		// adding the Listener to the table so that evidence can be displayed in
		// the bottom pane
		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				Integer rowNumSelected = table.getSelectionIndex();
				Term termSelected = termsArray.get(rowNumSelected);
				displayEvidence(evidenceTable, termSelected);
			}
		});
		
		table.addMouseListener(new MouseListener(){
			public void mouseDoubleClick(MouseEvent e) {	
			}

			public void mouseDown(MouseEvent e) {
				if(e.button == 3){
					//System.out.println("right clicked on a table item");
				    Menu menu = new Menu(table.getShell(), SWT.POP_UP);
				    MenuItem addNewRowItem = new MenuItem(menu, SWT.PUSH);
				    addNewRowItem.setText("Create row");
				    addNewRowItem.addSelectionListener(new SelectionListener(){
						public void widgetDefaultSelected(SelectionEvent e) {
						}

						public void widgetSelected(SelectionEvent e) {
							//System.out.println("Create a row");
							createRowCreationDialog();
						}				    	
				    });
				    
				    MenuItem editRowItem = new MenuItem(menu, SWT.PUSH);
				    editRowItem.setText("Edit row");
				    editRowItem.addSelectionListener(new SelectionListener(){
						public void widgetDefaultSelected(SelectionEvent e) {}

						public void widgetSelected(SelectionEvent e) {
							//System.out.println("Edit a row");
							createRowEditingDialog();
						}				    	
				    });

				    MenuItem delRowItem = new MenuItem(menu, SWT.PUSH);
				    delRowItem.setText("Delete row");
				    delRowItem.addSelectionListener(new SelectionListener(){
						public void widgetDefaultSelected(SelectionEvent e) {}

						public void widgetSelected(SelectionEvent e) {
							createWarningDialog();
						}				    	
				    });

				    Point pt = new Point(e.x, e.y);
				    pt = table.toDisplay(pt);
				    menu.setLocation(pt.x, pt.y);
				    menu.setVisible(true);
				}
			}

			public void mouseUp(MouseEvent e) {				
			}			
		});

		/** Sort terms such that the most aligned schemas are on top */
		buttonMostGroupEs.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event)
				{ sortTableItems(new AlignedSchemasSorter(true)); }
		});

		/** Sort terms such that the least aligned schemas are on top */
		buttonLeastGroupEs.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event)
				{ sortTableItems(new AlignedSchemasSorter(false)); }
		});

		searchButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event)
			{
				createSearchDialog();
			}
		});

		// adding resize capability so fills in space nicely
		this.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				int curW = getX();
				int curH = getY() - 35; // subtract 30 because of buttons in top
										// row
				RowData rd = new RowData(curW, curH);
				sash.setLayoutData(rd);
			}
		});
	}

	/** deletes the selectedRow number from the table and the vocabulary **/
	private void deleteRow(int selectedRow){
			//remove the deleted row from the table
			TableItem[] allRows = table.getItems();
			allRows[selectedRow].dispose();

			//saving the deletion to the database
			Term deletedTerm = termsArray.get(selectedRow);
			vocabTerms.removeTerm(deletedTerm);
			OpenIIManager.saveVocabularyTerms(vocabTerms);			

			//removed the deleted row from the terms array
			termsArray.remove(selectedRow);
	}
	
	/** adds a row with the name directly below the selected row **/
	private void addRow(String newName){
		//create new term
		Term newTerm = new Term();
		newTerm.setName(newName);
		
		//add new term to the database
		vocabTerms.addTerm(newTerm);
		OpenIIManager.saveVocabularyTerms(vocabTerms);
	
		//add the new term to the terms array
		termsArray.add(table.getSelectionIndex(), newTerm);
			
		//add the new term to the table
		TableItem newItem = new TableItem(table, SWT.NONE, table.getSelectionIndex());
		newItem.setText(0, newName);
		Font boldFont = new Font(Display.getDefault(), new FontData("Arial", 8, SWT.BOLD));
		newItem.setFont(0, boldFont);
		colorTheTable();
	}

	
	/**creates the dialog that allows a user to add a new term to a row when editing**/
	private void createAddElementToTermDialog(Display display, final Table currElementsTable){
		Term term = termsArray.get(table.getSelectionIndex());
		//Display display = viewsParent.getDisplay();
		final Shell addDialog = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		addDialog.setText("Add Element to " + term.getName());
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		addDialog.setLayout(layout);
		

		Label label = new Label(addDialog, SWT.RIGHT);
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		label.setText("From Schema: ");

		final Combo schemaCombo = new Combo(addDialog, SWT.DROP_DOWN | SWT.READ_ONLY);		
		for(int i=1; i<numSchemas; i++){
			schemaCombo.add(schemaNames[i]);
		}
		//schemaCombo.select(0);

		
		Label label2 = new Label(addDialog, SWT.RIGHT);
		label2.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		label2.setText("Add Term: ");
		final Combo elementCombo = new Combo(addDialog, SWT.DROP_DOWN | SWT.READ_ONLY);		

		Composite okayCancelPane = new Composite(addDialog, SWT.NONE);
		RowLayout rowLayout = new RowLayout(); 
		okayCancelPane.setLayout(rowLayout);
		
	
		Button okButton = new Button(okayCancelPane, SWT.NONE);
		okButton.setText("Ok");
		Button cancelButton = new Button(okayCancelPane, SWT.NONE);

		cancelButton.setText("Cancel");
		cancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				addDialog.dispose();
			}
		});
		
		okButton.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event) {
				if(schemaCombo.getSelectionIndex() != -1){
					if(elementCombo.getSelectionIndex() != -1){
						Integer schemaID = schemaIDs[schemaCombo.getSelectionIndex()];
						
						SchemaModel model = OpenIIManager.getProject(vocabTerms.getProjectID()).getSchemaModel(schemaID);
						SchemaInfo si = cache.getSchemaInfo(schemaID);
						HierarchicalSchemaInfo schema = new HierarchicalSchemaInfo(si, model);
						
							
						//ArrayList<SchemaElement> elements = si.getElements(null);
						ArrayList<SchemaElement> elements = schema.getHierarchicalElements();
						
						SchemaElement se = elements.get(elementCombo.getSelectionIndex());
						Integer elementID = se.getId();
						AssociatedElement ae = new AssociatedElement(schemaID, elementID, se.getName(), se.getDescription());						
						Term selectedTerm = termsArray.get(table.getSelectionIndex());
						selectedTerm.addAssociatedElement(ae);
						
						TableItem ti = new TableItem(currElementsTable, SWT.NONE);
						ti.setText(0, si.getSchema().getName());	
						ti.setText(1, se.getName());			
						ti.setData(ae);					
					}
				}
				addDialog.dispose();
			}
		});
		
		
		schemaCombo.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent e) {				
				if(schemaCombo.getSelectionIndex() != -1) {
					elementCombo.removeAll();
							
					Integer schemaID = schemaIDs[schemaCombo.getSelectionIndex()];
					SchemaInfo si = cache.getSchemaInfo(schemaID);
					SchemaModel model = OpenIIManager.getProject(vocabTerms.getProjectID()).getSchemaModel(schemaID);
					HierarchicalSchemaInfo schema = new HierarchicalSchemaInfo(si, model);
										
					ArrayList<SchemaElement> elements = schema.getHierarchicalElements();
					
					for(SchemaElement ele : elements){
						elementCombo.add(ele.getName());
					}
					elementCombo.select(0);
				}
			}
		});

		addDialog.pack();
		addDialog.open();
		
	}


	/**creates the dialog that allows a user to edit a row **/
	private void createRowEditingDialog(){
		//TO DO: create a row that allows a user to add or remove terms from any synsets
		Display display = viewsParent.getDisplay();
		final Shell editRowDialog = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		editRowDialog.setText("Edit");
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		editRowDialog.setLayout(layout);
		editRowDialog.setSize(100, 50);
		
		//adding the Vocab Name field
		Composite pane = new Composite(editRowDialog, SWT.NONE);
		GridLayout paneLayout = new GridLayout(2,false);
		paneLayout.marginWidth = 8;
		pane.setLayout(paneLayout);
		
		Label label = new Label(pane, SWT.RIGHT);
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		label.setText("Vocabularly Term Name: ");
		
		final Text text = new Text(pane, SWT.BORDER);
		GridData gd0 = new GridData(GridData.FILL_HORIZONTAL);
		text.setLayoutData(gd0);
		text.setText(termsArray.get(table.getSelectionIndex()).getName());
		
		Composite elementsLabelPane = new Composite(editRowDialog, SWT.NONE);
		GridLayout elementsLabelPaneLayout = new GridLayout(1,false);
		elementsLabelPane.setLayout(elementsLabelPaneLayout);
		Label schemasLable = new Label(elementsLabelPane, SWT.NONE);
		schemasLable.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		schemasLable.setText("Terms Mapped to Vocabularly Term: ");
		
		//creating the list of elements currently in the vocabulary object
		Composite elementsPane = new Composite(editRowDialog, SWT.NONE);
		GridLayout elementsPaneLayout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		elementsPane.setLayout(elementsPaneLayout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint=150;
		elementsPane.setLayoutData(gd);
		
		final Table currElements = new Table(elementsPane, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		currElements.setHeaderVisible(true);
		currElements.setLinesVisible(true);
		currElements.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		
		TableColumn schema = new TableColumn(currElements, SWT.NONE);
		schema.setWidth(100);
		schema.setText("Schema");
		TableColumn elements = new TableColumn(currElements, SWT.NONE);
		elements.setWidth(100);
		elements.setText("Term");
		
		//now need to add the data to the columns
		//final Term editedTerm = termsArray.get(table.getSelectionIndex());
		final AssociatedElement[] uneditedTermElements = termsArray.get(table.getSelectionIndex()).getElements();
		for(int i=0; i<uneditedTermElements.length; i++){
			AssociatedElement ele = uneditedTermElements[i];
			
			TableItem ti = new TableItem(currElements, SWT.NONE);
			//get the name of the schema associated with the element
			Integer schemaID = ele.getSchemaID();
			SchemaInfo si = cache.getSchemaInfo(schemaID);
			ti.setText(0, si.getSchema().getName());
			
			Integer eleID = ele.getElementID();
			SchemaElement schemaEle = si.getElement(eleID);
			ti.setText(1, schemaEle.getName());			
			
			ti.setData(ele);
		}
		
		
		Composite buttonPane = new Composite(elementsPane, SWT.NONE);
		layout = new GridLayout(1, true);
		layout.marginHeight=0;
		layout.marginWidth=0;
		buttonPane.setLayout(layout);
		buttonPane.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		Button addButton = new Button(buttonPane, SWT.NONE);
		addButton.setText("Add...");
		Button removeButton = new Button(buttonPane, SWT.NONE);
		removeButton.setText("Remove");
		removeButton.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event) {
				Term term = termsArray.get(table.getSelectionIndex());
				AssociatedElement ae = (AssociatedElement) currElements.getItem(currElements.getSelectionIndex()).getData();

				//TO DO: Something is not working in this line
				//System.out.println("TO DO: fix term.removeAssociatedElement(ae) because it hangs");
				//term.removeAssociatedElement(ae);

				//temp fix
				AssociatedElement[] prevList = term.getElements();
				AssociatedElement[] newList = new AssociatedElement[prevList.length-1];
				int currPos=0;
				for(int i=0; i<prevList.length; i++){
					if(prevList[i] != ae){
						newList[currPos] = prevList[i];
						currPos++;
					}
				}
				
				termsArray.get(table.getSelectionIndex()).setElements(newList);
				
				//remove selected element from the table
				currElements.getItem(currElements.getSelectionIndex()).dispose();
				
			}
		});
		
		addButton.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event) {
				createAddElementToTermDialog(editRowDialog.getDisplay(), currElements);	
			}
		});
		
		Composite okayRemovePane = new Composite(elementsPane, SWT.NONE);
		RowLayout rowLayout = new RowLayout(); 
		okayRemovePane.setLayout(rowLayout);

	
		Button okButton = new Button(okayRemovePane, SWT.NONE);
		okButton.setText("Ok");
		okButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				//get the term that was edited
				Term changedTerm = termsArray.get(table.getSelectionIndex());
				
				//change the name of the synset in the actual Term and save changes to the repository
				String newName = text.getText();
				changedTerm.setName(newName);
				//changedTerm.setDescription("");

				/*System.out.println("about to save changes to vocab, revised term should have: ");
				AssociatedElement[] aea = changedTerm.getElements();
				for(int i=0; i<aea.length; i++){
					System.out.println(aea[i].getName());
				}
				
				Term[] tempTermsTest = new Term[termsArray.size()];
				int start=0;
				for(Term t : termsArray){
					tempTermsTest[start] = t;
					System.out.println("terms include: " + t.getName());
					start++;
				}
				vocab.setTerms(tempTermsTest);*/
				OpenIIManager.saveVocabularyTerms(vocabTerms);

				//update the vocab viewer table to reflect the name change 
				//update the row in the table
				int rownum = table.getSelectionIndex();
				table.getItem(rownum).dispose();
				
				TableItem tempI = new TableItem(table, SWT.NONE, rownum);
				tempI.setText(0, newName);
				Font boldFont = new Font(Display.getDefault(), new FontData("Arial", 8, SWT.BOLD));
				tempI.setFont(0, boldFont);

				AssociatedElement[] elements = changedTerm.getElements();
				for (int j = 0; j < elements.length; j++) {
					AssociatedElement ele = elements[j];
					Integer schemaID = elements[j].getSchemaID();
					SchemaInfo si = cache.getSchemaInfo(schemaID);
					Integer eleID = elements[j].getElementID();
					SchemaElement schemaEle = si.getElement(eleID);

					int colNum = schemaIDsToColNum.get(elements[j].getSchemaID());
					String current = tempI.getText(colNum);
					if (current == null || current == "") {
						tempI.setText(colNum, ele.getName());
					} else {
						tempI.setText(colNum, current + ", " + ele.getName());
					}
					
					Image img = getImage(schemaEle);
					tempI.setImage(colNum, img);
				}
						
				colorTheTable();
				editRowDialog.dispose();
			}
		});

		Button cancelButton = new Button(okayRemovePane, SWT.NONE);
		cancelButton.setText("Cancel");
		cancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				//do not want to save any of the changes that may have been made in terms of adding or removing elements
				//returning the associated elements to the initial state
				termsArray.get(table.getSelectionIndex()).setElements(uneditedTermElements);
				editRowDialog.dispose();
			}
		});

		editRowDialog.pack();
		editRowDialog.open();			
	}
	/** Returns the image associated with the specified element */
	private Image getImage(Object element)
	{
		String imageName = "";
		if(element instanceof Schema) imageName = "Schema.gif";
		else if(element instanceof DomainValue) imageName = "DomainValue.jpg";
			else if(element instanceof Attribute) imageName = "Attribute.jpg";
			else if(element instanceof Containment) imageName = "Containment.jpg";
			else if(element instanceof Relationship) imageName = "Relationship.jpg";
			else imageName = "SchemaElement.jpg";	
		ImageDescriptor descriptor = OpenIIActivator.getImageDescriptorForIcons(imageName);
		Image image = imageCache.get(descriptor);
		if (image == null) {
			image = descriptor.createImage();
			imageCache.put(descriptor, image);
		}
		return image;
	}
	public void dispose() {
		for (Image image : imageCache.values()) {
			image.dispose();
		}
		imageCache.clear();
		super.dispose();
	}
	/** creates the dialog that allows a user to add a row to the table **/
	private void createRowCreationDialog(){
		Display display = viewsParent.getDisplay();
		final Shell rowCreationDialog = new Shell(display, SWT.TITLE | SWT.APPLICATION_MODAL);
		rowCreationDialog.setText("Create");
		rowCreationDialog.setLayout(new GridLayout(2, false));

		Label label = new Label(rowCreationDialog, SWT.RIGHT);
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		label.setText("Vocabularly Term Name: ");

		final Text text = new Text(rowCreationDialog, SWT.BORDER);		
		GridData gd0 = new GridData(GridData.FILL_HORIZONTAL);
		text.setLayoutData(gd0);
		text.setText("<name>");
		
		
		Composite okayRemovePane = new Composite(rowCreationDialog, SWT.NONE);
		RowLayout rowLayout = new RowLayout(); 
		okayRemovePane.setLayout(rowLayout);
		
		Button okButton = new Button(okayRemovePane, SWT.NONE);
		okButton.setText("Ok");
		okButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				//confirmed addition, go ahead and add the new row
				addRow(text.getText());
				rowCreationDialog.dispose();
			}
		});

		Button cancelButton = new Button(okayRemovePane, SWT.NONE);
		cancelButton.setText("Cancel");
		cancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				rowCreationDialog.dispose();
			}
		});

		rowCreationDialog.pack();
		rowCreationDialog.open();
		
	}

	
	/**
	 * creates the dialog that confirms a user really wanted to delete a row
	 * if answer is yes, then the row will be permanently deleted by calling deleteRow()
	 **/
	private void createWarningDialog() {
		Display display = viewsParent.getDisplay();
		final Shell warningDialog = new Shell(display, SWT.TITLE | SWT.APPLICATION_MODAL);
		warningDialog.setText("Warning");
		warningDialog.setLayout(new GridLayout(2, false));

		Label label = new Label(warningDialog, SWT.NULL);
		label.setText("Are you sure you want to delete this row?");
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);

		Button okButton = new Button(warningDialog, SWT.NONE);
		okButton.setText("Ok");
		okButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				//confirmed the deletion, go ahead and delete the row
				deleteRow(table.getSelectionIndex());
				warningDialog.dispose();
			}
		});

		Button cancelButton = new Button(warningDialog, SWT.NONE);
		cancelButton.setText("Cancel");
		cancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				warningDialog.dispose();
			}
		});

		warningDialog.pack();
		warningDialog.open();
	}

	/** Populates the table with the provided terms */
	private void populateTable()
	{
		// Define the font to use in displaying the terms
		Font font = new Font(Display.getDefault(), new FontData("Arial", 8, SWT.BOLD));
		
		// Clear out the table
		table.clearAll();
		for(TableItem item : table.getItems())
			item.dispose();
		
		// Add the terms to the table
		for(Term term : termsArray)
		{	
			// Create the table item
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, term.getName());
			item.setFont(0, font);

			// Traverse through all associated elements
			for(AssociatedElement element : term.getElements())
			{
				// Get the column associated with this element
				Integer schemaID = element.getSchemaID();
				int column = schemaIDsToColNum.get(schemaID);

				// Add the element to the column
				String currentText = item.getText(column);
				if(currentText == null || currentText == "")
					item.setText(column, element.getName());
				else item.setText(column, currentText + ", " + element.getName());
			
				// Display the image associated with the specific type of element
	//			SchemaInfo schemaInfo = cache.getSchemaInfo(schemaID);
	//			SchemaElement schemaElement = schemaInfo.getElement(element.getElementID());
	//			Image image = org.mitre.openii.widgets.schemaTree.SchemaElementLabelProvider.getImage(schemaElement);
	//			item.setImage(column, image);
			}
		}
	}
	
	/**if coloredTabled is true then it will color table cells based on string match with vocab term **/
	/**if coloredTable is false then it will color all table cells white **/
	private void colorTheTable(){
		TableItem[] allRows = table.getItems();
		int numCols = table.getColumnCount();
		
		// for each row in the table, starting with the 2nd
		for (int i = 0; i < allRows.length; i++) {
			// compare the row to all previously sorted rows
			String vocabTerm = allRows[i].getText(0);
			for (int j = 1; j < numCols; j++) {
				String currTerm = allRows[i].getText(j);
				if(coloredTable){
					if(currTerm.equalsIgnoreCase(vocabTerm)){
						allRows[i].setBackground(j, lightGreen);
					}else if(currTerm.equals("") == true){
						allRows[i].setBackground(j, lightRed);
					}else{
						allRows[i].setBackground(j, lightYellow);
					}
				}else{
					allRows[i].setBackground(j, white);
				}
			}
		}
		
	}
	
	/** Sorts the table items as specified */
	private void sortTableItems(Comparator<Term> sorter)
	{
		Collections.sort(termsArray, sorter);
		populateTable();
		colorTheTable();
	}

	private int getX() {
		return this.getBounds().width;
	}

	private int getY() {
		return this.getBounds().height;
	}

	
	
	/** creating the dialog that pops up when a user clicks on the search button **/
	private void createSearchDialog() {
		Display display = viewsParent.getDisplay();
		final Shell searchDialog = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		// final Shell searchDialog = new Shell(display);
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

		// adding Listener to sort columns alphabetically alphabetically
		Listener sortResultsAlphabeticallyListener = new Listener() {
			public void handleEvent(Event e) {
				// finding the column number
				TableColumn col = (TableColumn) e.widget;
				int colNum = 0;
				for (int i = 0; i < resultsTable.getColumnCount(); i++) {
					if (col == resultsTable.getColumn(i)) {
						colNum = i;
					}
				}

				// sort items alphabetically base on that column
				TableItem[] allRows = resultsTable.getItems();
				// for each row in the table, starting with the 2nd
				for (int i = 1; i < allRows.length; i++) {
					// compare the row to all previously sorted rows
					for (int j = 0; j < i; j++) {
						if (allRows[i].getText(colNum).compareToIgnoreCase(allRows[j].getText(colNum)) < 0) {
							String[] tempRow = new String[3];
							Image[] tempImage = new Image[3];
							Object tempData = allRows[i].getData();
							for (int k = 0; k < 3; k++) {
								tempRow[k] = allRows[i].getText(k);
								tempImage[k] = allRows[i].getImage(k);
							}

							// get rid of the previous ith row
							allRows[i].dispose();

							TableItem movedItem = new TableItem(resultsTable, SWT.NONE, j);
							movedItem.setText(tempRow);
							movedItem.setData(tempData);
							movedItem.setImage(tempImage);

							// now work with new table items, and go on to next
							// row
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
		schemaNameCol.setText("Schema");
		schemaNameCol.setWidth(100);
		schemaNameCol.addListener(SWT.Selection, sortResultsAlphabeticallyListener);

		TableColumn textCol = new TableColumn(resultsTable, SWT.NONE);
		textCol.setText("Schema Term");
		textCol.setWidth(100);
		textCol.addListener(SWT.Selection, sortResultsAlphabeticallyListener);

		searchField.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				String searchTerm = searchField.getText();
				if (searchTerm != null && searchTerm != "") {
					displaySearchResults(resultsTable, searchTerm);
				}
			}
		});

		resultsTable.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// un-highlight any previously selected cells
				for (int i = 0; i < highlightedTableItems.size(); i++) {
					Point location = highlightedTableItems.get(i);
					
					//make background white if it's in vocab column or the appropriate color otherwise
					if(location.y == 0 || coloredTable==false){
						table.getItem(location.x).setBackground(location.y, table.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
					}else{
						//need to find appropriate background color
						String vocabTerm = table.getItem(location.x).getText(0);
						String currTerm = table.getItem(location.x).getText(location.y);
						if(currTerm.equalsIgnoreCase(vocabTerm)){
							table.getItem(location.x).setBackground(location.y, lightGreen);
						}else if(currTerm.equals("") == true){
							table.getItem(location.x).setBackground(location.y, lightRed);
						}else{
							table.getItem(location.x).setBackground(location.y, lightYellow);
						}							
					}

				}

				// highlight the selected searchresults cell in the table
				TableItem[] selectedItem = resultsTable.getSelection();
				table.deselectAll();
				for (int i = 0; i < selectedItem.length; i++) {
					// System.out.println("selected: " +
					// selectedItem[i].getText(0));
					Point location = (Point) selectedItem[i].getData();
					// System.out.println("location: " + location.x + " " +
					// location.y);
					table.setTopIndex(location.x);
					table.getItem(location.x).setBackground(location.y, table.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
					highlightedTableItems.add(location);
				}
			}
		});

		searchDialog.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				// un-highlight any previously selected cells
				for (int i = 0; i < highlightedTableItems.size(); i++) {
					Point location = highlightedTableItems.get(i);
					
					//make background white if it's in vocab column or the appropriate color otherwise
					if(location.y == 0 || coloredTable==false){
						table.getItem(location.x).setBackground(location.y, table.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
					}else{
						//need to find appropriate background color
						String vocabTerm = table.getItem(location.x).getText(0);
						String currTerm = table.getItem(location.x).getText(location.y);
						if(currTerm.equalsIgnoreCase(vocabTerm)){
							table.getItem(location.x).setBackground(location.y, lightGreen);
						}else if(currTerm.equals("") == true){
							table.getItem(location.x).setBackground(location.y, lightRed);
						}else{
							table.getItem(location.x).setBackground(location.y, lightYellow);
						}							
					}
				}
			}
		});

		searchDialog.pack();
		searchDialog.open();
	}

	
	/** displays the evidence in the evidenceTable **/
	//0-schema, 1-term, 2-description, 3-score, 4-match schema, 5-match term, 6-match description
	private void displayEvidence(Table evidTable, Term selectedTerm) {
		// clear all of the previous table items
		
		TableItem[] allRows = evidTable.getItems();
		for (int i = 0; i < allRows.length; i++) {
			allRows[i].dispose();
		}

		
		// add the first row to be the vocab term, and description
		TableItem vocabItem = new TableItem(evidTable, SWT.NONE);
		vocabItem.setText(0, "Vocabulary");
		vocabItem.setText(1, selectedTerm.getName());
		
		String forDescription = null;
		if(selectedTerm.getDescription() != null){
			forDescription = selectedTerm.getDescription();
		}else{
			forDescription = "";
		}
			
		vocabItem.setText(2, forDescription);
		Font boldFont = new Font(Display.getDefault(), new FontData("Arial", 8, SWT.BOLD));
		vocabItem.setFont(0, boldFont);
		vocabItem.setFont(1, boldFont);

		// add in evidence for each element associated with the vocab term
		AssociatedElement[] aes = selectedTerm.getElements();

		//alternating background colors
		Color white = this.getDisplay().getSystemColor(SWT.COLOR_WHITE);
		Color lightShadow = this.getDisplay().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);

		//alternating
		//Color highlightOnGray = new Color(this.getDisplay(), 255, 255, 0);
		//Color highlightOnWhite = new Color(this.getDisplay(), 255, 255, 128);
		//Font highlightFont = new Font(Display.getDefault(), new FontData("Arial", 8, SWT.ITALIC));
		
		Color currColor = white;

		for (int i = 0; i < aes.length; i++) {
			String elementName = aes[i].getName();

			// want to alternate the table background color based on schema
			if (i % 2 == 0) {
				currColor = lightShadow;
			} else {
				currColor = white;
			}

			// get the original element's ID and description
			Integer schemaID = aes[i].getSchemaID();
			Integer eleID = aes[i].getElementID();
			SchemaElement currElement = cache.getSchemaElement(schemaID, eleID);
			String elementDescription = currElement.getDescription();
			String containingSchemaName = cache.getSchemaInfo(schemaID).getSchema().getName();

			TableItem nextItem = new TableItem(evidTable, SWT.NONE);
			nextItem.setText(1, elementName);
			nextItem.setText(2, elementDescription);
			nextItem.setText(0, containingSchemaName);
			nextItem.setBackground(currColor);
			nextItem.setForeground(this.getDisplay().getSystemColor(SWT.COLOR_BLACK));
			
			// for each mapping, we need to find any cells that map to the
			// current element
			ArrayList<MappingCell> mappingCellList = new ArrayList<MappingCell>();
			for (Mapping m : mappings) {
				MappingInfoExt mix = new MappingInfoExt(m, OpenIIManager.getMappingCells(m.getId()));
				AssociatedElementHash aeh = mix.getMappingCells();
				for ( MappingCell mc : aeh.get(eleID) )
					if ( !mappingCellList.contains(mc))
						mappingCellList.add(mc); 
			}

			Collections.sort(mappingCellList, new Comparator<MappingCell>() {
				public int compare(MappingCell cell1, MappingCell cell2) {
					return -(cell1.getScore().compareTo(cell2.getScore()));
				}
			});


			// add the name of the element and match score to the Related
			// Elements string
			for (int k = 0; k < mappingCellList.size(); k++) {
				MappingCell mc = mappingCellList.get(k); 
				Mapping m = OpenIIManager.getMapping(mc.getMappingId());
				Integer outputId = mc.getOutput();
				Integer otherSchemaId = (schemaID.equals(m.getSourceId())) ? m.getTargetId() : m.getSourceId();

				for (Integer inputId : mc.getElementInputIDs()) {
					Integer otherElementId = (outputId.equals(eleID)) ? inputId : outputId;
					SchemaElement otherElement = cache.getSchemaElement(otherSchemaId, otherElementId);

					//TableItem currItem = (k == 0) ? nextItem : new TableItem(evidTable, SWT.NONE);
					TableItem currItem = new TableItem(evidTable, SWT.NONE);
					// set background color
					currItem.setBackground(currColor);

					// highlight chosen element mapping cells
					boolean found = false;
					for (AssociatedElement ae : aes){
						if(ae.getElementID().equals(otherElementId)){
							found = true;
						}
					}
					
					if(!found){
							for (int c = 3; c <= 6; c++){
								//currItem.setBackground(c, highlightColor);
								currItem.setForeground(c, this.getDisplay().getSystemColor(SWT.COLOR_DARK_RED));
							}
					}else{
						currItem.setForeground(this.getDisplay().getSystemColor(SWT.COLOR_BLACK));
					}
				
					//0-schema, 1-term, 2-description, 3-score, 4-match schema, 5-match term, 6-match description
					currItem.setText(3, mc.getScore().toString());
					currItem.setText(5, otherElement.getName());
					currItem.setText(6, otherElement.getDescription());
					currItem.setText(4, OpenIIManager.getSchema(otherSchemaId).getName());
				}

			}
		}
	}
	
	/** displays the search results table in the search vocabulary dialog box **/
	private void displaySearchResults(Table tableForSearchResults, String searchTerm) {
		// clear all of the previous table items
		TableItem[] allRows = tableForSearchResults.getItems();
		for (int i = 0; i < allRows.length; i++) {
			allRows[i].dispose();
		}

		// want to find substrings
		Pattern pattern = Pattern.compile("(?i)" + searchTerm);

		// as items are found in the vocabulary viewer table, put the
		// appropriate information in the search results table
		TableItem[] allVocabRows = table.getItems();
		for (Integer i = 0; i < allVocabRows.length; i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				String vocabCellText = allVocabRows[i].getText(j);

				// want to find substrings
				Matcher matcher = pattern.matcher(vocabCellText);

				if (matcher.find()) {
					// if(vocabCellText.compareToIgnoreCase(searchTerm) == 0){
					TableItem tempI = new TableItem(tableForSearchResults, SWT.NONE);
					tempI.setText(0, allVocabRows[i].getText(0));
					tempI.setText(1, table.getColumn(j).getText());
					// tempI.setText(2, searchTerm);
					tempI.setText(2, vocabCellText);

					tempI.setImage(2, allVocabRows[i].getImage(j));

					Point location = new Point(i, j);
					tempI.setData(location);
				}
			}

		}

	}
}
