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

package org.mitre.openii.editors.unity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
//import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout; 
import org.eclipse.swt.custom.StackLayout; 
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
import org.eclipse.swt.dnd.*;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.Vocabulary;
import org.mitre.schemastore.model.VocabularyTerms;
import org.mitre.schemastore.model.mappingInfo.AssociatedElementHash;
import org.mitre.schemastore.model.mappingInfo.MappingInfoExt;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;


import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

import org.mitre.openii.widgets.schemaTree.SchemaTree;

public class UnityCanvas extends Composite {
	private int numSchemas; // num schemas, including common vocab as a schema
	private Integer[] schemaIDs;
	private Schema[] schemas;
	private SchemaInfo[] schemaInfos;
	private SchemaModel[] schemaModels;
	private String[] schemaNames; // schemaNames[0] will always be
									// "Common Vocab"
	private HashMap<Integer, Integer> schemaIDsToColNum;
	private VocabularyTerms vocab;
	private Composite viewsParent;
	private ArrayList<Point> highlightedTableItems;
	private ArrayList<Mapping> mappings;
	private boolean confirmation;
//	private Hashtable<Integer, SchemaInfo> schemaInfos = new Hashtable<Integer, SchemaInfo>();
	private boolean coloredTable;

	private Color red =  this.getDisplay().getSystemColor(SWT.COLOR_RED);
	private Color black =  this.getDisplay().getSystemColor(SWT.COLOR_BLACK);
	private Color green =  this.getDisplay().getSystemColor(SWT.COLOR_GREEN);
	private Color yellow =  this.getDisplay().getSystemColor(SWT.COLOR_YELLOW);
	private Color white =  this.getDisplay().getSystemColor(SWT.COLOR_WHITE);
	private Color lightYellow = new Color(this.getDisplay(), 255, 255, 128);
	private Color lightGreen = new Color(this.getDisplay(), 128, 225, 128);
	private Color lightRed = new Color(this.getDisplay(), 255, 128, 128);
	private Font boldFont = new Font(Display.getDefault(), new FontData("Arial", 8, SWT.BOLD));
	private Font ItalicFont = new Font(Display.getDefault(), new FontData("Arial", 8, SWT.ITALIC));
	private Font LargeItalicFont = new Font(Display.getDefault(), new FontData("Arial", 10, SWT.ITALIC));
	private Font LargeBoldFont = new Font(Display.getDefault(), new FontData("Arial", 14, SWT.NONE));
	private Image[] images = new Image[6];
    private SchemaTree treeview = null;
	private Table tableview = null;
    private Text textSearchTree;
    private Text textSearchTable;
    private Button saveVocab;
    private Button treeAlpha;
    private Button treeTypes;
    private Button treeBase;
    private Combo schemaSelector;  
    private Combo sortSelector;
	private Combo colorSelector;
    private Button colorsettings;
	private Combo colorSelectorT;
    private Button colorsettingsT;
    private Canvas treeViewCanvas;
    private Canvas tableViewCanvas;
    private Canvas searchViewCanvas;
    private Canvas evidenceCanvas;
    private Canvas closeMatchCanvas;
    private Canvas contextCanvas;
    private Button newSynset;
    private final SashForm main_sash = new SashForm(this, SWT.HORIZONTAL);		
    private final CTabFolder folder = new CTabFolder(main_sash, SWT.TOP);
	private final Canvas right_sash = new Canvas(main_sash, SWT.EMBEDDED);
    private final Canvas workspace = new Canvas(right_sash, SWT.EMBEDDED);
    private final Composite buttonsC = new Composite(workspace, SWT.NONE);
    private final Canvas tablespace = new Canvas(workspace, SWT.EMBEDDED);
    private final StackLayout stackLayout = new StackLayout();
	private final StyledText tempLabel = new StyledText(tablespace,SWT.WRAP);
    private final Table workspaceTable = new Table(tablespace, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
	private final GC gc = new GC(workspaceTable);
    private final TableEditor editor = new TableEditor(workspaceTable);
    private final Composite buttonsD = new Canvas(workspace,SWT.NONE);
    private Integer draggedCol = null;
    private Integer draggedRow = null;
    private int eventDetail = 0;
    private int dragID = 0;
    private int interfaceState = DND.DROP_MOVE;
    private AssociatedElement[] dragElements;
    private TableItem selectedItem;
    private Display display;
    private Image CheckIcon = OpenIIActivator.getImage("checkicon.png");
    private Image saveIcon = OpenIIActivator.getImage("save.png");
    private Image needsaveIcon = OpenIIActivator.getImage("needsave.png");
    private Listener mainListener;
    private boolean showTextWorkspace = true;
    private boolean showTextTableView = true;
    private Shell dialog;
    private Text textfield;
    private Label errorLabel;
    private Integer maxSafeID = 0;
    
	public UnityCanvas(Composite parent, int style, VocabularyTerms vocabulary) {
		super(parent, style );
		viewsParent = parent;
		vocab = vocabulary;

		
		schemaIDs = vocabulary.getSchemaIDs();
		schemas = new Schema[schemaIDs.length];
		schemaInfos = new SchemaInfo[schemaIDs.length];
		schemaModels = new SchemaModel[schemaIDs.length];
		for(int index = 0; index < schemaIDs.length; index++) {
			schemas[index] = OpenIIManager.getSchema(schemaIDs[index]);
			schemaInfos[index] = OpenIIManager.getSchemaInfo(schemaIDs[index]);
			schemaModels[index] = OpenIIManager.getProject(vocab.getProjectID()).getSchemaModel(schemaIDs[index]);
////System.out.println("SchemaID " + schemas[index].getId() + " is for " + schemas[index].getName());
		}
		//find a good id to start assigning to new terms
		for(int i = 0; i < vocabulary.getTerms().length; i++){
			if(vocabulary.getTerms()[i].getId() > maxSafeID) {
				maxSafeID = vocabulary.getTerms()[i].getId();
			}
		}
		maxSafeID++;
		createUnityView();		
	}
	
	private void createUnityView() {
		display = this.getDisplay();
		
				
		this.setBackground(null);
		FillLayout layout = new FillLayout();
		layout.type = SWT.VERTICAL;
		this.setLayout(layout);
		
//		SashForm main_sash = new SashForm(this, SWT.HORIZONTAL);		
	
//		CTabFolder folder = new CTabFolder(main_sash, SWT.TOP);
		folder.setUnselectedCloseVisible(false);
		folder.setSimple(false);
		
		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setImage(OpenIIActivator.getImage("treeview.png"));
		item.setText("Tree");
		item.setToolTipText("Tree View");
		treeViewCanvas = new Canvas(folder, SWT.EMBEDDED);
		item.setControl(treeViewCanvas);
		GridLayout treelayout = new GridLayout(1, false);
		treeViewCanvas.setLayout(treelayout);
		GridLayout treeViewlayout = new GridLayout(1, false);
		treeViewlayout.marginHeight = 0;
		treeViewlayout.marginWidth = 0;
		treeViewlayout.verticalSpacing = 0;
		treeViewlayout.horizontalSpacing = 0;
		treeViewlayout.marginBottom = 0;
		treeViewCanvas.setLayout(treeViewlayout);

		createTreeView(treeViewCanvas);
		
		item = new CTabItem(folder, SWT.NONE);
		item.setImage(OpenIIActivator.getImage("tableicon.png"));
		item.setText("Table");
		item.setToolTipText("Table View");
		tableViewCanvas = new Canvas(folder, SWT.EMBEDDED);
		item.setControl(tableViewCanvas);
		GridLayout tablelayout = new GridLayout(1, false);
		tableViewCanvas.setLayout(tablelayout);
		GridLayout tableViewlayout = new GridLayout(1, false);
		tableViewlayout.marginHeight = 0;
		tableViewlayout.marginWidth = 0;
		tableViewlayout.verticalSpacing = 0;
		tableViewlayout.horizontalSpacing = 0;
		tableViewlayout.marginBottom = 0;
		tableViewCanvas.setLayout(tableViewlayout);
		createTableView(tableViewCanvas);
		
		item = new CTabItem(folder, SWT.NONE);
		item.setImage(OpenIIActivator.getImage("search.png"));
		item.setText("Search");
		item.setToolTipText("Advanced Search");
		searchViewCanvas = new Canvas(folder, SWT.EMBEDDED);
		item.setControl(searchViewCanvas);
		createSearchView(searchViewCanvas);
		
		folder.setSelection(0); 
		folder.setBackground(null); 
		folder.setBorderVisible(true);
		folder.setSelectionBackground(display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT)); 

//		SashForm right_sash = new SashForm(main_sash, SWT.VERTICAL);
//		Canvas right_sash = new Canvas(main_sash, SWT.EMBEDDED);
		GridLayout rightLayout = new GridLayout(1, false);
		rightLayout.marginWidth = 0;
		rightLayout.marginHeight = 0;
		right_sash.setLayout(rightLayout);
		
		createWorkspace(right_sash);

		CTabFolder folder2 = new CTabFolder(right_sash, SWT.TOP);
		folder2.setUnselectedCloseVisible(false);
		folder2.setSimple(false);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		folder2.setLayoutData(gridData);

		item = new CTabItem(folder2, SWT.NONE);
		item.setImage(OpenIIActivator.getImage("footprint1.png"));
		item.setText("Evidence");
		item.setToolTipText("Evidence");
		evidenceCanvas = new Canvas(folder2, SWT.EMBEDDED);
		item.setControl(evidenceCanvas);
		createEvidencePane(evidenceCanvas);
		
		item = new CTabItem(folder2, SWT.NONE);
		item.setImage(OpenIIActivator.getImage("footprint2.png"));
		item.setText("Close Matches");
		item.setToolTipText("Close Matches");
		closeMatchCanvas = new Canvas(folder2, SWT.EMBEDDED);
		item.setControl(closeMatchCanvas);
		createCloseMatchPane(closeMatchCanvas);

		item = new CTabItem(folder2, SWT.NONE);
		item.setImage(OpenIIActivator.getImage("home.png"));
		item.setText("Context");
		item.setToolTipText("Term Context");
		contextCanvas = new Canvas(folder2, SWT.EMBEDDED);
		item.setControl(contextCanvas);
		createContextPane(contextCanvas);

		folder2.setSelection(0); 
		folder2.setBackground(null); 
		folder2.setSelectionBackground(display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT)); 
		folder2.setBorderVisible(true);
		
		addTableListners();
		
	}

	private void createTreeView(Composite parent) {
		Composite treeButtons = new Composite(parent, SWT.NONE);
//		CoolBar treeButtons = new CoolBar(parent, SWT.NONE);
	    
//		GridLayout treeButtonlayout = new GridLayout(9, false);
		RowLayout treeButtonlayout = new RowLayout();
		treeButtonlayout.center = true;
		treeButtons.setLayout(treeButtonlayout);
		treeButtons.setLayoutData(new GridData (SWT.FILL, SWT.TOP, true, false));
		
		schemaSelector = new Combo(treeButtons, SWT.READ_ONLY | SWT.DROP_DOWN);
		for(int index = 0; index < schemaIDs.length; index++) {
			schemaSelector.add(schemas[index].getName());
		}
		schemaSelector.setToolTipText("Displayed Schema is " + schemas[0].getName());
		schemaSelector.select(0);
//		schemaSelector.setLayoutData(new GridData (SWT.RIGHT, SWT.CENTER, true, false));
//		schemaSelector.pack();
		
		treeTypes = new Button(treeButtons, SWT.TOGGLE);
		treeTypes.setToolTipText("Show types");
		treeTypes.setImage(OpenIIActivator.getImage("types.png"));
		treeTypes.setVisible(true);
//		treeTypes.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));

		treeBase = new Button(treeButtons, SWT.TOGGLE);
		treeBase.setToolTipText("Show base schema");
		treeBase.setImage(OpenIIActivator.getImage("baseSchema.png"));
		treeBase.setVisible(true);
//		treeBase.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));


		Label seperator = new Label(treeButtons, SWT.BITMAP);
		seperator.setImage(OpenIIActivator.getImage("seperator.png"));
//		seperator.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
		
		treeAlpha = new Button(treeButtons, SWT.TOGGLE);
		treeAlpha.setToolTipText("Alphabetize");
		treeAlpha.setImage(OpenIIActivator.getImage("alpha.png"));
		treeAlpha.setVisible(true);
//		treeAlpha.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));

		Label seperator3 = new Label(treeButtons, SWT.BITMAP);
		seperator3.setImage(OpenIIActivator.getImage("seperator.png"));

		Button treeFilter1 = new Button(treeButtons, SWT.TOGGLE);
		treeFilter1.setImage(OpenIIActivator.getImage("checkedFilter.png"));
		treeFilter1.setToolTipText("Filter out checked terms");
		treeFilter1.setVisible(true);
//		treeFilter1.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));

		Button treeFilter2 = new Button(treeButtons, SWT.TOGGLE);
		treeFilter2.setImage(OpenIIActivator.getImage("greenFilter.png"));
		treeFilter2.setToolTipText("Filter out terms in good matches");
		treeFilter2.setVisible(true);
//		treeFilter2.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));

		Button treeFilter3 = new Button(treeButtons, SWT.TOGGLE);
		treeFilter3.setImage(OpenIIActivator.getImage("yellowFilter.png"));
		treeFilter3.setToolTipText("Filter out terms in average matches");
		treeFilter3.setVisible(true);
//		treeFilter3.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
		


		treeview = new SchemaTree(parent,schemaInfos[0],schemaModels[0], true);
		//parent.pack();

		Composite treeSearch = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		treeSearch.setLayoutData(gridData);
		GridLayout treeSearchlayout = new GridLayout(3, false);
		treeSearch.setLayout(treeSearchlayout);
		Label tslabel =new Label(treeSearch, SWT.NONE);
		tslabel.setText("Search:");
		textSearchTree = new Text(treeSearch, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		textSearchTree.setLayoutData(gridData);
		
		
		//add listeners
		
		//drag source listener
        DragSource source = new DragSource(treeview.getTreeViewer().getTree(), DND.DROP_LINK | DND.DROP_MOVE | DND.DROP_COPY);
        source.setTransfer(new Transfer [] { FileTransfer.getInstance() });
        source.addDragListener(
        	new DragSourceListener () {
            	String data[];
            	String data2[];
            	public void dragStart (DragSourceEvent event) { 
            		dragID = 1;
            		event.image = null;
  			      // Clean up any previous editor control
  			      Control oldEditor = editor.getEditor();
  			      if (oldEditor != null)
  			        oldEditor.dispose();
////System.out.println("uid = " + treeview.getTreeViewer().getTree().getSelection()[0].getData("uid") + "\n");
					TreeItem items[] = treeview.getTreeViewer().getTree().getSelection();
					ArrayList<String> ids = new ArrayList<String>();
					Integer idssub[];
					data2 = new String[items.length];
					dragElements =  new AssociatedElement[items.length];
					draggedCol = schemas[schemaSelector.getSelectionIndex()].getId();
					for(int i = 0; i < items.length; i++)
					{
						data2[i] = 	"" + items[i].getData("uid");						
						dragElements[i] = new AssociatedElement(draggedCol,(Integer)items[i].getData("uid"),items[i].getText(),(String)items[i].getData("description"));
						idssub = vocab.reverseLookup((Integer)(items[i].getData("uid")), draggedCol);
						////System.out.println("idssub.length = " + idssub.length);
						for(int y = 0; y < idssub.length; y++)
						{
							ids.add("" + idssub[y]);
						}
					}
					String idStrings[] = new String[ids.size()];
					ids.toArray(idStrings);
					data = idStrings;
            		if(data.length == 0) {
            			data = new String[1];
            			data[0] = " ";
            		}
            	} 
            	public void dragFinished (DragSourceEvent event) {
            	} 
        		public void dragSetData (DragSourceEvent event) { 
        			////System.out.println("testing5\n");
        			////System.out.println("event.detail = " + event.detail + "\n");
					if(eventDetail == DND.DROP_LINK) {
						event.data = data;
					} else {
						event.data = data2;
					}
        		} 
        	}
        );		
        
        
		//search listener
		textSearchTree.addListener(SWT.KeyUp, new Listener() {  
		     public void handleEvent(Event e) {  
					if(e.character==SWT.CR) treeview.searchFor(textSearchTree.getText()); }});

		
		// sorting alphabetically
		treeAlpha.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
		    	  boolean alpha = treeview.isAlphabetized();
		    	  treeview.doAlphabetized(!alpha);
 		  		  if(alpha) {
 		  			  //treeAlpha.setImage(OpenIIActivator.getImage("alpha.png"));
   		  			  treeAlpha.setToolTipText("Alphabetize");
 		  		  } else {
 		  			  //treeAlpha.setImage(OpenIIActivator.getImage("alphaOFF.png")); 		  			  
 		  			  treeAlpha.setToolTipText("Stop Alphabetizing");
 		  		  }
			}
		});
		
		// show types
		treeTypes.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
		    	  boolean types = treeview.showTypes();
		    	  treeview.doShowTypes(!types);
 		  		  if(types) {
 		  			//treeTypes.setImage(OpenIIActivator.getImage("types.png"));
 		  			treeTypes.setToolTipText("Show types");
 		  		  } else {
 		  			//treeTypes.setImage(OpenIIActivator.getImage("typesOFF.png")); 		  			  
 		  			treeTypes.setToolTipText("Hide types");
 		  		  }
			}
		});
		
		// show base schema
		treeBase.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
		    	  boolean base = treeview.showBaseSchemas();
		    	  treeview.doShowBaseSchemas(!base);
 		  		  if(base) {
 		  			//treeBase.setImage(OpenIIActivator.getImage("baseSchema.png"));
 		  			treeBase.setToolTipText("Show base schema");
 		  		  } else {
 		  			//treeBase.setImage(OpenIIActivator.getImage("baseSchemaOFF.png")); 		  			  
 		  			treeBase.setToolTipText("Hide base schema");
 		  		  }
			}
		});
		
		// choose schema to view 
		schemaSelector.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int index = schemaSelector.getSelectionIndex();
				//comboLabel.setToolTipText(schemas[index].getName());
				schemaSelector.setToolTipText("Displayed Schema is " + schemas[index].getName());
				treeview.setSchema(schemaInfos[index],schemaModels[index]);

			}
		});
	}

	private void createTableView(Composite parent) {
		Composite tableButtons = new Composite(parent, SWT.NONE);
		RowLayout tableButtonlayout = new RowLayout();
		tableButtonlayout.center = true;
		tableButtons.setLayout(tableButtonlayout);
		tableButtons.setLayoutData(new GridData (SWT.FILL, SWT.TOP, true, false));

		colorSelectorT = new Combo(tableButtons, SWT.READ_ONLY | SWT.DROP_DOWN);
		colorSelectorT.add("Not Colored");
		colorSelectorT.add("Color by Canonical Match");
		colorSelectorT.add("Color by Match Strength");
		colorSelectorT.add("Color by Element Type");
		colorSelectorT.add("Color by Instance Count");
		colorSelectorT.setToolTipText("No coloring applied");
		colorSelectorT.select(0);

		colorsettingsT = new Button(tableButtons, SWT.PUSH);
		colorsettingsT.setImage(OpenIIActivator.getImage("color_settings.png"));
		colorsettingsT.setToolTipText("Color Settings");
		colorsettingsT.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorsettingsT.setSelection(false);
			}
		});

		Button showtext = new Button(tableButtons, SWT.TOGGLE);
		showtext.setImage(OpenIIActivator.getImage("showtext.png"));
		showtext.setToolTipText("Hide terms");

//		Button showicons = new Button(tableButtons, SWT.TOGGLE);
//		showicons.setImage(OpenIIActivator.getImage("showIcons.png"));
//		showicons.setToolTipText("Hide symbols");


		Label seperator3 = new Label(tableButtons, SWT.BITMAP);
		seperator3.setImage(OpenIIActivator.getImage("seperator.png"));
//		seperator3.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
		
		sortSelector = new Combo(tableButtons, SWT.READ_ONLY | SWT.DROP_DOWN);
		sortSelector.add("Not Sorted");
		sortSelector.add("Sort by Schema Matches");
		sortSelector.add("Sort by Match Strength");
		sortSelector.add("Alphabetical by Vocabulary");
		for(int index = 0; index < schemaIDs.length; index++) {
			sortSelector.add("Alphabetical by " + schemas[index].getName());
		}
		for(int index = 0; index < schemaIDs.length; index++) {
			sortSelector.add("Structurally by " + schemas[index].getName());
		}
		sortSelector.setToolTipText("No sorting applied");
		sortSelector.select(0);
		
		
		Label seperator2 = new Label(tableButtons, SWT.BITMAP);
		seperator2.setImage(OpenIIActivator.getImage("seperator.png"));
//		seperator2.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));

		Button tableFilter1 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter1.setImage(OpenIIActivator.getImage("checkedFilter.png"));
		tableFilter1.setToolTipText("Filter out checked synsets");
//		tableFilter1.setVisible(true);
//		tableFilter1.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
//		tableFilter1.pack();

		Button tableFilter2 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter2.setImage(OpenIIActivator.getImage("greenFilter.png"));
		tableFilter2.setToolTipText("Filter out synsets with good matches");
//		tableFilter2.setVisible(true);
//		tableFilter2.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
//		tableFilter2.pack();

		Button tableFilter3 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter3.setImage(OpenIIActivator.getImage("yellowFilter.png"));
		tableFilter3.setToolTipText("Filter out synsets with average matches");
//		tableFilter3.setVisible(true);
//		tableFilter3.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
//		tableFilter3.pack();

		Button tableFilter4 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter4.setImage(OpenIIActivator.getImage("singleton.png"));
		tableFilter4.setToolTipText("Filter out singletons");		

		
		
		tableview = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.VIRTUAL);
		tableview.setData("name", "tableview");
		tableview.setHeaderVisible(true);
		tableview.setLinesVisible(true);
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
		for (int i = 0; i < schemas.length; i++) {
			tempC = new TableColumn(tableview, SWT.NONE);
			tempC.setText(schemas[i].getName());
			tempC.setData("uid",new Integer(schemas[i].getId()));
			tempC.setWidth(100);
			tempC.setMoveable(true);
			//tempC.addListener(SWT.Selection, sortAlphabeticallyListener);
			tempC.setToolTipText(schemas[i].getName());
		}
		
		Listener dataCallbackListener = new Listener() {

	        public void handleEvent(Event event) {
	            TableItem item = (TableItem)event.item;
				item.setData("uid", vocab.getTerms()[tableview.indexOf(item)].getId());
				populateRow(item,showTextTableView);
	        }
	    };
	    
	    tableview.setItemCount(vocab.getTerms().length);
	    tableview.addListener(SWT.SetData, dataCallbackListener);

		
		//tableview.pack();

		Composite tableSearch = new Composite(parent, SWT.NONE);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		tableSearch.setLayoutData(gridData);
		GridLayout treeSearchlayout = new GridLayout(3, false);
		tableSearch.setLayout(treeSearchlayout);
		Label tslabel =new Label(tableSearch, SWT.NONE);
		tslabel.setText("Search:");
		textSearchTable = new Text(tableSearch, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		textSearchTable.setLayoutData(gridData);
		
		
		//add listeners
		
		showtext.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				showTextTableView = !showTextTableView;
				tableview.clearAll();
			}
		});				

		
		
		
        DragSource source = new DragSource(tableview, DND.DROP_LINK | DND.DROP_MOVE | DND.DROP_COPY);
        source.setTransfer(new Transfer [] { FileTransfer.getInstance() });
        source.addDragListener(
        	new DragSourceListener () {
    			String[] data = new String[1];
            	String data2[] = new String[1];
    			public void dragStart (DragSourceEvent event) { 
    				event.image = null;
    			      // Clean up any previous editor control
    			      Control oldEditor = editor.getEditor();
    			      if (oldEditor != null)
    			        oldEditor.dispose();
    				dragID = 2;
        			TableItem selected[] = tableview.getSelection();
        			String ids[] = new String[selected.length];
        			for(int i = 0; i < selected.length; i++)
        			{
        				ids[i] = "" + selected[i].getData("uid");
        			}
    				data = ids;
    				if(draggedCol > -1){
    					//event.data = workspaceTable.getItem(index).getText(index2);
    					data2[0] = "" + vocab.getTerms()[vocab.getTermIndex((Integer)(selectedItem.getData("uid")))].getElements()[0].getElementID();
    					//trim image
    				}
    			} 
    			public void dragFinished (DragSourceEvent event) { 
    			} 
    			public void dragSetData (DragSourceEvent event) { 
    				if(eventDetail == DND.DROP_LINK) {
    					event.data = data;
    				} else {
    					event.data = data2;
    				}
    			}
        	}
        );
        
		DropTarget target = new DropTarget (tableview, DND.DROP_COPY | DND.DROP_MOVE); 
		target.setTransfer(new Transfer [] { FileTransfer.getInstance() });
		target.addDropListener(new DropTargetListener () { 
			public void dragEnter(DropTargetEvent event) { 
				event.detail = DND.DROP_COPY;					
				eventDetail = DND.DROP_COPY;
			} 
			public void dragOver(DropTargetEvent event) { 
				event.detail = DND.DROP_NONE;
				eventDetail = DND.DROP_NONE;
				
				if(event.item instanceof TableItem){
						Rectangle colrec = null;
						int[] order = tableview.getColumnOrder();
						for(int i = 0; i < tableview.getColumnCount(); i++) {
							colrec = ((TableItem)event.item).getBounds(order[i]);
							if(colrec.x + colrec.width > tableview.getDisplay().map(null, tableview, event.x, event.y).x ){
//								////System.out.println("col = " + order[i] + "\n");
								if(order[i] > 1){
//									//System.out.println(workspaceTable.getColumn(order[i]).getData("uid") + " != " + draggedCol + "?\n");
									if(tableview.getColumn(order[i]).getData("uid").equals(draggedCol)){
										event.detail = DND.DROP_COPY;										
										eventDetail = DND.DROP_COPY;
										if(dragID == 2){
											event.detail = interfaceState;										
											eventDetail = interfaceState;											
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
				
					Object target = event.item;
					if(target instanceof TableItem){
						Integer vocabID = (Integer)(((TableItem) target).getData("uid"));
//System.out.println("vocabID = " + vocabID + " and draggedRow is " + draggedRow);
						if(!vocabID.equals(draggedRow)) {
							Term theTerm = vocab.getTerms()[(vocab.getTermIndex(vocabID))];
							for(int i = 0; i < ((String[])event.data).length; i++){
								theTerm.addAssociatedElement(dragElements[i]);
							}
							if(event.detail == DND.DROP_MOVE){
								Term fromTerm = vocab.getTerms()[(vocab.getTermIndex(draggedRow))];
								fromTerm.removeAssociatedElement(dragElements[0].getElementID(),dragElements[0].getSchemaID());
								vocab.termUpdated(draggedRow);
								updateTables(draggedRow);
							}
							vocab.termUpdated(vocabID);
							updateTables(vocabID);
						}
					}						
			} 
		});

	}

	private void createSearchView(Composite parent) {

	}

	private void createWorkspace(Composite parent) {

		workspace.setToolTipText("Drag Terms from the Tree or Table");

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
		colorSelector.add("Not Colored");
		colorSelector.add("Color by Canonical Match");
		colorSelector.add("Color by Match Strength");
		colorSelector.add("Color by Element Type");
		colorSelector.add("Color by Instance Count");
		colorSelector.setToolTipText("No coloring applied");
		colorSelector.select(0);

		colorsettings = new Button(buttonsC, SWT.PUSH);
		colorsettings.setImage(OpenIIActivator.getImage("color_settings.png"));
		colorsettings.setToolTipText("Color Settings");
		colorsettings.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorsettings.setSelection(false);
			}
		});

		Button showtext = new Button(buttonsC, SWT.TOGGLE);
		showtext.setImage(OpenIIActivator.getImage("showtext.png"));
		showtext.setToolTipText("Hide terms");

//		Button showicons = new Button(buttonsC, SWT.TOGGLE);
//		showicons.setImage(OpenIIActivator.getImage("showIcons.png"));
//		showicons.setToolTipText("Hide symbols");

		Button clearSynsets = new Button(buttonsC, SWT.PUSH);
		clearSynsets.setImage(OpenIIActivator.getImage("clear.png"));
		clearSynsets.setToolTipText("Clear Workspace");		
			
		
		Label seperator3 = new Label(buttonsC, SWT.BITMAP);
		seperator3.setImage(OpenIIActivator.getImage("seperator.png"));
//		seperator3.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));

		newSynset = new Button(buttonsC, SWT.PUSH);
		newSynset.setImage(OpenIIActivator.getImage("addsynset.png"));
		newSynset.setToolTipText("Create a new Synset");		
				
		Button renameSynset = new Button(buttonsC, SWT.PUSH);
		renameSynset.setImage(OpenIIActivator.getImage("rename.png"));
		renameSynset.setToolTipText("Rename the selected Synset");		
				
		Button descSynset = new Button(buttonsC, SWT.PUSH);
		descSynset.setImage(OpenIIActivator.getImage("change_desc.png"));
		descSynset.setToolTipText("Change the description of the selected Synset");		
				
		Button deleteSynset = new Button(buttonsC, SWT.PUSH);
		deleteSynset.setImage(OpenIIActivator.getImage("deletesynset.png"));
		deleteSynset.setToolTipText("Permenately delete selected Synset from Vocabulary");		

		Label seperator = new Label(buttonsC, SWT.BITMAP);
		seperator.setImage(OpenIIActivator.getImage("seperator.png"));

		
		saveVocab = new Button(buttonsC, SWT.PUSH);
		saveVocab.setBackground(green);
		saveVocab.setImage(saveIcon);
		saveVocab.setToolTipText("Save the current Vocabulary");	

				

		
//		Label spacerB = new Label(buttonsC, SWT.BITMAP);
//		spacerB.setImage(OpenIIActivator.getImage("empty.png"));
//		gridData = new GridData();
//		gridData.heightHint = 25;
//		gridData.widthHint = 10;
//		spacerB.setLayoutData(gridData);


		
//		workspaceTable = new Table(workspace, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI );
		workspaceTable.setHeaderVisible(true);
		workspaceTable.setLinesVisible(true);
		workspaceTable.setData("name", "workspaceTable");
		
		
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
		TableColumn tempC;
		tempC = new TableColumn(workspaceTable, SWT.NONE);
		tempC.setWidth(26);
		tempC.setImage(CheckIcon);
		tempC.setMoveable(false);
		tempC.setData("uid",new Integer(-202));
		tempC.setToolTipText("Checked");
		tempC = new TableColumn(workspaceTable, SWT.NONE);
		tempC.setText("Vocabulary");
		tempC.setMoveable(false);
		tempC.setData("uid",new Integer(-201));
		tempC.setWidth(100);
		//tempC.addListener(SWT.Selection, sortAlphabeticallyListener);
		tempC.setToolTipText("Vocabulary");
		// creating one column for each schema
		for (int i = 0; i < schemas.length; i++) {
			tempC = new TableColumn(workspaceTable, SWT.NONE);
			tempC.setText(schemas[i].getName());
			tempC.setData("uid",new Integer(schemas[i].getId()));
			tempC.setWidth(100);
			tempC.setMoveable(true);
			//tempC.addListener(SWT.Selection, sortAlphabeticallyListener);
			tempC.setToolTipText(schemas[i].getName());
		}
        
		//temp label
		tempLabel.setText("\nDrag Terms from the Tree or Table");
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
		
		
		//add listeners
		colorSelector.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
			}
		});

		colorsettings.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorsettings.setSelection(false);
			}
		});

		showtext.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				showTextWorkspace = !showTextWorkspace;
				TableItem[] workspaceItems = workspaceTable.getItems();
				workspaceTable.clearAll();
				for(int i = 0; i < workspaceItems.length; i++) {
					populateRow(workspaceItems[i],showTextWorkspace);
				}
			}
		});				
		
//		showicons.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//			}
//		});				
		
		clearSynsets.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				workspaceTable.removeAll();
				Control[] buttons = buttonsD.getChildren();
				for(int i = 1; i < buttons.length; i++)
				{
					buttons[i].dispose();
				}
				stackLayout.topControl = tempLabel;										
				right_sash.layout();									

			}
		});				
		
		newSynset.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				dialog = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				GridLayout dialogLayout = new GridLayout(2, true);
				dialog.setLayout(dialogLayout);
				dialog.setText("Create a new Synset");
				dialog.setSize(200, 120);
				dialog.setLocation(newSynset.toDisplay(10, 10));
				Label nameLabel = new Label(dialog, SWT.NONE);
				nameLabel.setText("Synset Name:");
				textfield = new Text(dialog, SWT.NONE);
				GridData gridData = new GridData();
				gridData.horizontalSpan = 2;
				gridData.horizontalAlignment = GridData.FILL;
				gridData.verticalAlignment = GridData.FILL;
				gridData.grabExcessHorizontalSpace = true;
				gridData.grabExcessVerticalSpace = false;
				textfield.setLayoutData(gridData);
				textfield.setToolTipText("Enter a unique Synset name");
				errorLabel = new Label(dialog, SWT.NONE);
				errorLabel.setText("");
				gridData = new GridData();
				gridData.horizontalSpan = 2;
				gridData.horizontalAlignment = GridData.CENTER;
				gridData.verticalAlignment = GridData.FILL;
				gridData.grabExcessHorizontalSpace = true;
				gridData.grabExcessVerticalSpace = false;
				errorLabel.setLayoutData(gridData);
				errorLabel.setForeground(red);
				Button ok = new Button(dialog, SWT.PUSH);
				ok.setText(" Ok ");
				ok.setSize(50, 20);
				gridData = new GridData();
				gridData.horizontalAlignment = GridData.END;
				gridData.verticalAlignment = GridData.FILL;
				gridData.grabExcessHorizontalSpace = false;
				gridData.grabExcessVerticalSpace = false;
				ok.setLayoutData(gridData);
				Button cancel = new Button(dialog, SWT.PUSH);
				cancel.setText("Cancel");
				cancel.setSize(50, 20);
				gridData = new GridData();
				gridData.horizontalAlignment = GridData.BEGINNING;
				gridData.verticalAlignment = GridData.FILL;
				gridData.grabExcessHorizontalSpace = false;
				gridData.grabExcessVerticalSpace = false;
				cancel.setLayoutData(gridData);

				ok.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						if(doesNameExist(textfield.getText())){
							errorLabel.setText(textfield.getText() + " already exists in vocabulary");
							dialog.pack(true);
							dialog.layout(true);
						} else {
							Term newTerm = new Term(maxSafeID++,textfield.getText(),"");
							vocab.addTerm(newTerm);
							tableview.setItemCount(tableview.getItemCount()+1);
							TableItem item = new TableItem(workspaceTable, SWT.NONE, workspaceTable.getItemCount()); 
							item.setData("uid", maxSafeID-1);
							populateRow(item, showTextWorkspace);
								//add button
								Button x1;
								x1 = new Button(buttonsD, SWT.PUSH);
								x1.setToolTipText("Remove from Workspace");									
								x1.setImage(OpenIIActivator.getImage("remove.png"));
								x1.setData("index", new Integer(workspaceTable.getItemCount()));
								GridData gridData = new GridData();
								gridData.heightHint = 19;
								gridData.widthHint = 19;
								x1.setLayoutData(gridData);						
								x1.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent e) {
										int index = ((Integer)(e.widget.getData("index"))).intValue();
										workspaceTable.remove(index-1);
										Control[] buttons = buttonsD.getChildren();
										buttons[buttons.length-1].dispose();
										if(buttons.length == 2) {
											stackLayout.topControl = tempLabel;										
										}
										right_sash.layout();									
									}
								});

							updateTables(maxSafeID-1);
							stackLayout.topControl = workspaceTable;
							workspaceTable.getParent().getParent().getParent().layout(true);

							dialog.close();

						}
					}
				});				
				cancel.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						dialog.close();
					}
				});				
				
				textfield.addKeyListener(new KeyListener() {
			        public void keyPressed(KeyEvent e) {
				        }
			        public void keyReleased(KeyEvent e) {
					    if (e.character == SWT.CR)
					    {
							if(doesNameExist(textfield.getText())){
								errorLabel.setText(textfield.getText() + " already exists in vocabulary");
								dialog.pack(true);
								dialog.layout(true);
							} else {
								Term newTerm = new Term(maxSafeID++,textfield.getText(),"");
								vocab.addTerm(newTerm);
								tableview.setItemCount(tableview.getItemCount()+1);
								TableItem item = new TableItem(workspaceTable, SWT.NONE, workspaceTable.getItemCount()); 
								item.setData("uid", maxSafeID-1);
								populateRow(item, showTextWorkspace);
									//add button
									Button x1;
									x1 = new Button(buttonsD, SWT.PUSH);
									x1.setToolTipText("Remove from Workspace");									
									x1.setImage(OpenIIActivator.getImage("remove.png"));
									x1.setData("index", new Integer(workspaceTable.getItemCount()));
									GridData gridData = new GridData();
									gridData.heightHint = 19;
									gridData.widthHint = 19;
									x1.setLayoutData(gridData);						
									x1.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(SelectionEvent e) {
											int index = ((Integer)(e.widget.getData("index"))).intValue();
											workspaceTable.remove(index-1);
											Control[] buttons = buttonsD.getChildren();
											buttons[buttons.length-1].dispose();
											if(buttons.length == 2) {
												stackLayout.topControl = tempLabel;										
											}
											right_sash.layout();									
										}
									});

								updateTables(maxSafeID-1);
								stackLayout.topControl = workspaceTable;
								workspaceTable.getParent().getParent().getParent().layout(true);

								dialog.close();
							}
				        }
			        }
			      });

				
				dialog.open();
				
				
			}
		});
				
		renameSynset.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if(workspaceTable.getSelectionCount() == 1){
					Term theTerm = vocab.getTerms()[(vocab.getTermIndex((Integer)(workspaceTable.getSelection()[0].getData("uid"))))];
					
					dialog = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
					GridLayout dialogLayout = new GridLayout(2, true);
					dialog.setLayout(dialogLayout);
					dialog.setText("Rename Synset");
					dialog.setSize(200, 120);
					dialog.setLocation(newSynset.toDisplay(10, 10));
					Label nameLabel = new Label(dialog, SWT.NONE);
					nameLabel.setText("Synset Name:");
					textfield = new Text(dialog, SWT.NONE);
					GridData gridData = new GridData();
					gridData.horizontalSpan = 2;
					gridData.horizontalAlignment = GridData.FILL;
					gridData.verticalAlignment = GridData.FILL;
					gridData.grabExcessHorizontalSpace = true;
					gridData.grabExcessVerticalSpace = false;
					textfield.setLayoutData(gridData);
					textfield.setText(theTerm.getName());
					textfield.setToolTipText("Enter a unique Synset name");
					errorLabel = new Label(dialog, SWT.NONE);
					errorLabel.setText("");
					gridData = new GridData();
					gridData.horizontalSpan = 2;
					gridData.horizontalAlignment = GridData.CENTER;
					gridData.verticalAlignment = GridData.FILL;
					gridData.grabExcessHorizontalSpace = true;
					gridData.grabExcessVerticalSpace = false;
					errorLabel.setLayoutData(gridData);
					errorLabel.setForeground(red);
					Button ok = new Button(dialog, SWT.PUSH);
					ok.setText(" Ok ");
					ok.setSize(50, 20);
					gridData = new GridData();
					gridData.horizontalAlignment = GridData.END;
					gridData.verticalAlignment = GridData.FILL;
					gridData.grabExcessHorizontalSpace = false;
					gridData.grabExcessVerticalSpace = false;
					ok.setLayoutData(gridData);
					Button cancel = new Button(dialog, SWT.PUSH);
					cancel.setText("Cancel");
					cancel.setSize(50, 20);
					gridData = new GridData();
					gridData.horizontalAlignment = GridData.BEGINNING;
					gridData.verticalAlignment = GridData.FILL;
					gridData.grabExcessHorizontalSpace = false;
					gridData.grabExcessVerticalSpace = false;
					cancel.setLayoutData(gridData);

					ok.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							if(workspaceTable.getSelectionCount() == 1){
								Term theTerm = vocab.getTerms()[(vocab.getTermIndex((Integer)(workspaceTable.getSelection()[0].getData("uid"))))];
								if(!textfield.getText().equals(theTerm.getName())){
							    	if(doesNameExist(textfield.getText())){
										errorLabel.setText(textfield.getText() + " already exists in vocabulary");
										dialog.pack(true);
										dialog.layout(true);
									} else {
								    	theTerm.setName(textfield.getText());
							        	updateTables((Integer)selectedItem.getData("uid"));
									}
								}
								dialog.close();

							}
						}
					});				
					cancel.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							dialog.close();
						}
					});				
					
					textfield.addKeyListener(new KeyListener() {
				        public void keyPressed(KeyEvent e) {
					        }
				        public void keyReleased(KeyEvent e) {
						    if (e.character == SWT.CR)
						    {
								if(workspaceTable.getSelectionCount() == 1){
									Term theTerm = vocab.getTerms()[(vocab.getTermIndex((Integer)(workspaceTable.getSelection()[0].getData("uid"))))];
									if(!textfield.getText().equals(theTerm.getName())){
										if(doesNameExist(textfield.getText())){
											errorLabel.setText(textfield.getText() + " already exists in vocabulary");
											dialog.pack(true);
											dialog.layout(true);
										} else {
									    	theTerm.setName(textfield.getText());
								        	updateTables((Integer)selectedItem.getData("uid"));
										}
									}
									dialog.close();

								}
						    }

				        }
				      });

					
					dialog.open();			
				}
			}
		});
				
		descSynset.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if(workspaceTable.getSelectionCount() == 1){
					Term theTerm = vocab.getTerms()[(vocab.getTermIndex((Integer)(workspaceTable.getSelection()[0].getData("uid"))))];
						

					dialog = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
					GridLayout dialogLayout = new GridLayout(2, true);
					dialog.setLayout(dialogLayout);
					dialog.setText("Change Description");
					dialog.setSize(200, 200);
					dialog.setLocation(newSynset.toDisplay(10, 10));
					Label nameLabel = new Label(dialog, SWT.NONE);
					nameLabel.setText("Description for "+theTerm.getName()+":");
					textfield = new Text(dialog, SWT.NONE);
					GridData gridData = new GridData();
					gridData.horizontalSpan = 2;
					gridData.horizontalAlignment = GridData.FILL;
					gridData.verticalAlignment = GridData.FILL;
					gridData.grabExcessHorizontalSpace = true;
					gridData.grabExcessVerticalSpace = true;
					textfield.setLayoutData(gridData);
					textfield.setText(theTerm.getDescription());
					textfield.setToolTipText("Enter a unique Synset name");
					Button ok = new Button(dialog, SWT.PUSH);
					ok.setText(" Ok ");
					ok.setSize(50, 20);
					gridData = new GridData();
					gridData.horizontalAlignment = GridData.END;
					gridData.verticalAlignment = GridData.FILL;
					gridData.grabExcessHorizontalSpace = false;
					gridData.grabExcessVerticalSpace = false;
					ok.setLayoutData(gridData);
					Button cancel = new Button(dialog, SWT.PUSH);
					cancel.setText("Cancel");
					cancel.setSize(50, 20);
					gridData = new GridData();
					gridData.horizontalAlignment = GridData.BEGINNING;
					gridData.verticalAlignment = GridData.FILL;
					gridData.grabExcessHorizontalSpace = false;
					gridData.grabExcessVerticalSpace = false;
					cancel.setLayoutData(gridData);

					ok.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							if(workspaceTable.getSelectionCount() == 1){
								Term theTerm = vocab.getTerms()[(vocab.getTermIndex((Integer)(workspaceTable.getSelection()[0].getData("uid"))))];
								if(!textfield.getText().equals(theTerm.getDescription())){
							    	theTerm.setDescription(textfield.getText());
						        	updateTables((Integer)selectedItem.getData("uid"));
								}
								dialog.close();

							}
						}
					});				
					cancel.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							dialog.close();
						}
					});				
					
					textfield.addKeyListener(new KeyListener() {
				        public void keyPressed(KeyEvent e) {
					        }
				        public void keyReleased(KeyEvent e) {
						    if (e.character == SWT.CR)
						    {
								if(workspaceTable.getSelectionCount() == 1){
									Term theTerm = vocab.getTerms()[(vocab.getTermIndex((Integer)(workspaceTable.getSelection()[0].getData("uid"))))];
									if(!textfield.getText().equals(theTerm.getDescription())){
								    	theTerm.setDescription(textfield.getText());
							        	updateTables((Integer)selectedItem.getData("uid"));
									}
									dialog.close();

								}
						    }

				        }
				      });

					
					dialog.open();			
				}
			}
		});
				
		deleteSynset.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if(workspaceTable.getSelectionCount() == 1){
					TableItem row = workspaceTable.getSelection()[0];
					String name = "";
					for(int i = 0; i< workspaceTable.getColumnCount(); i++){
						if(workspaceTable.getColumn(i).getData("uid").equals(new Integer(-201))){
							name = row.getText(i);
						}
					}
					dialog = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
					GridLayout dialogLayout = new GridLayout(2, true);
					dialog.setLayout(dialogLayout);
					dialog.setText("Delete Synset");
					dialog.setSize(200, 120);
					dialog.setLocation(newSynset.toDisplay(10, 10));
					Label nameLabel = new Label(dialog, SWT.NONE);
					nameLabel.setText("This will perminately remove \"" + name + "\" from the Vocabulary.");
					GridData gridData = new GridData();
					gridData.horizontalSpan = 2;
					gridData.horizontalAlignment = GridData.FILL;
					gridData.verticalAlignment = GridData.FILL;
					gridData.grabExcessHorizontalSpace = true;
					gridData.grabExcessVerticalSpace = false;
					nameLabel.setLayoutData(gridData);
					Label nameLabel2 = new Label(dialog, SWT.NONE);
					nameLabel2.setText("Are you sure you want to do this?");
					gridData = new GridData();
					gridData.horizontalSpan = 2;
					gridData.horizontalAlignment = GridData.FILL;
					gridData.verticalAlignment = GridData.FILL;
					gridData.grabExcessHorizontalSpace = true;
					gridData.grabExcessVerticalSpace = false;
					nameLabel2.setLayoutData(gridData);
					Button ok = new Button(dialog, SWT.PUSH);
					ok.setText(" Yes ");
					ok.setSize(50, 20);
					gridData = new GridData();
					gridData.horizontalAlignment = GridData.END;
					gridData.verticalAlignment = GridData.FILL;
					gridData.grabExcessHorizontalSpace = false;
					gridData.grabExcessVerticalSpace = false;
					ok.setLayoutData(gridData);
					Button cancel = new Button(dialog, SWT.PUSH);
					cancel.setText(" No ");
					cancel.setSize(50, 20);
					gridData = new GridData();
					gridData.horizontalAlignment = GridData.BEGINNING;
					gridData.verticalAlignment = GridData.FILL;
					gridData.grabExcessHorizontalSpace = false;
					gridData.grabExcessVerticalSpace = false;
					cancel.setLayoutData(gridData);
	
					ok.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							if(workspaceTable.getSelectionCount() == 1){
								int index = workspaceTable.getSelectionIndex();
								Integer id = (Integer)workspaceTable.getItem(index).getData("uid");
								workspaceTable.remove(index);
								vocab.removeTerm(id);
								tableview.setItemCount(tableview.getItemCount()-1);
								tableview.clearAll(); 
								saveVocab.setBackground(red);
								saveVocab.setImage(needsaveIcon);									
								Control[] buttons = buttonsD.getChildren();
								buttons[buttons.length-1].dispose();
								if(buttons.length == 2) {
									stackLayout.topControl = tempLabel;										
								}
								right_sash.layout();									
								workspaceTable.getParent().getParent().getParent().layout(true);
	
								dialog.close();
	
							}
						}
					});				
					cancel.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							dialog.close();
						}
					});				
					
	
					dialog.pack(true);
					dialog.layout(true);
					dialog.open();
					
					
				}
			}

		});

		
		saveVocab.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				OpenIIManager.saveVocabularyTerms(vocab);
				((Button)e.widget).setBackground(green);
				((Button)e.widget).setImage(saveIcon);
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
			      Control oldEditor = editor.getEditor();
			      if (oldEditor != null)
			        oldEditor.dispose();
				dragID = 3;
    			data[0] = "" + draggedRow;
				if(draggedCol > -1){
					//event.data = workspaceTable.getItem(index).getText(index2);
					data2[0] = "" + vocab.getTerms()[vocab.getTermIndex((Integer)(selectedItem.getData("uid")))].getElements()[0].getElementID();
					//trim image
				}
			} 
			public void dragFinished (DragSourceEvent event) { 
			} 
			public void dragSetData (DragSourceEvent event) { 
				if(eventDetail == DND.DROP_LINK) {
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
				eventDetail = DND.DROP_COPY;
			} 
			public void dragOver(DropTargetEvent event) { 
//				//System.out.println("dragover");
				if(FileTransfer.getInstance().isSupportedType(event.currentDataType)){
//					//System.out.println("link");
					event.detail = DND.DROP_LINK;					
					eventDetail = DND.DROP_LINK;
				} else {
//				//System.out.println("none");
				event.detail = DND.DROP_NONE;
				eventDetail = DND.DROP_NONE;
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
									if(workspaceTable.getColumn(order[i]).getData("uid").equals(draggedCol)){
										event.detail = DND.DROP_COPY;										
										eventDetail = DND.DROP_COPY;
										if(dragID == 3){
											event.detail = interfaceState;										
											eventDetail = interfaceState;											
										}
									} else {
										event.detail = DND.DROP_NONE;
										eventDetail = DND.DROP_NONE;
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
					}
					//System.out.println("posIndex = " + posIndex + "\n");
					String [] data = ((String[])event.data);
					if(data.length == 1 && data[0].equals(" "))
					{
						//there is no data,  just break
						return;
					}

					for(int index = data.length -1; index > -1; index--)
					{
						//System.out.println("id = "+ data[index]);
						Integer termID = new Integer(data[index]);
						boolean inTable = false;
						//check if its already in the table
						for(int i = 0; i < ttarget.getItemCount(); i++)
						{							
							if(((Integer)ttarget.getItem(i).getData("uid")).equals(termID)) {
								inTable = true;
								ttarget.remove(i);
								break;
							}
						}
						if(posIndex > ttarget.getItemCount())  posIndex = ttarget.getItemCount();
						TableItem item = new TableItem(ttarget, SWT.NONE, posIndex); 
						item.setData("uid", termID);
						populateRow(item, showTextWorkspace);
							
						if(!inTable){
							//add a button for each new entry
							Button x1;
							x1 = new Button(buttonsD, SWT.PUSH);
							x1.setImage(OpenIIActivator.getImage("remove.png"));
							x1.setToolTipText("Remove from Workspace");
							x1.setData("index", workspaceTable.getItemCount());
							GridData gridData = new GridData();
							gridData.heightHint = 19;
							gridData.widthHint = 19;
							x1.setLayoutData(gridData);						
							x1.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									int index = ((Integer)(e.widget.getData("index"))).intValue();
									workspaceTable.remove(index-1);
									Control[] buttons = buttonsD.getChildren();
									buttons[buttons.length-1].dispose();
									if(buttons.length == 2) {
										stackLayout.topControl = tempLabel;										
									}
									right_sash.layout();									
								}
							});
						}
						stackLayout.topControl = workspaceTable;
						workspaceTable.getParent().getParent().getParent().layout(true);
					}
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
						if(!vocabID.equals(draggedRow)) {
							Term theTerm = vocab.getTerms()[(vocab.getTermIndex(vocabID))];
							for(int i = 0; i < ((String[])event.data).length; i++){
								theTerm.addAssociatedElement(dragElements[i]);
							}
							if(event.detail == DND.DROP_MOVE){
								Term fromTerm = vocab.getTerms()[(vocab.getTermIndex(draggedRow))];
								fromTerm.removeAssociatedElement(dragElements[0].getElementID(),dragElements[0].getSchemaID());
								vocab.termUpdated(draggedRow);
								updateTables(draggedRow);
							}
							vocab.termUpdated(vocabID);
							updateTables(vocabID);
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
				eventDetail = DND.DROP_LINK;
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
			public void drop(DropTargetEvent event) { 
				//System.out.println("drop");
					//adding row
	//System.out.println("detail = " + event.detail + "\n");
					String [] data = ((String[])event.data);
					if(data.length == 1 && data[0].equals(" "))
					{
						//there is no data,  just break
						return;
					}
					stackLayout.topControl = tempLabel;
					for(int index = data.length -1; index > -1; index--)
					{
						//System.out.println("id = "+ data[index]);
						Integer termID = new Integer(data[index]);
						boolean inTable = false;
						
						//check if its already in the table
						for(int i = 0; i < workspaceTable.getItemCount(); i++)
						{							
							if(((Integer)workspaceTable.getItem(i).getData("uid")).equals(termID)) {
								inTable = true;
								workspaceTable.remove(i);
								break;
							}
						}
						TableItem item = new TableItem(workspaceTable, SWT.NONE, 0); 
						item.setData("uid", termID);
						populateRow(item, showTextWorkspace);
							
						if(!inTable){
							//add a button for each new entry
							Button x1;
							x1 = new Button(buttonsD, SWT.PUSH);
							x1.setToolTipText("Remove from Workspace");									
							x1.setImage(OpenIIActivator.getImage("remove.png"));
							x1.setData("index", new Integer(workspaceTable.getItemCount()));
							GridData gridData = new GridData();
							gridData.heightHint = 19;
							gridData.widthHint = 19;
							x1.setLayoutData(gridData);						
							x1.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									int index = ((Integer)(e.widget.getData("index"))).intValue();
									workspaceTable.remove(index-1);
									Control[] buttons = buttonsD.getChildren();
									buttons[buttons.length-1].dispose();
									if(buttons.length == 2) {
										stackLayout.topControl = tempLabel;										
									}
									right_sash.layout();									
								}
							});

						}
					}
					stackLayout.topControl = workspaceTable;
					workspaceTable.getParent().getParent().getParent().layout(true);
			} 
		});
						
	}

	private void populateRow(TableItem item, boolean showTerms) {
	    int tableIndex = vocab.getTermIndex((Integer)item.getData("uid"));
		String termName = vocab.getTerms()[tableIndex].getName();	
		AssociatedElement[] elements = vocab.getTerms()[tableIndex].getElements();
		for (int j = 0; j < workspaceTable.getColumnCount(); j++) {
			int ID = ((Integer)workspaceTable.getColumn(j).getData("uid")).intValue();
			if(ID == -202) {
			} else if(ID == -201) {
				item.setText(j, termName);
				item.setFont(j, boldFont);			
			} else if (showTerms){
				String terms = "";
				for(int i = 0; i < elements.length; i++) {
					if(elements[i].getSchemaID() == ID) {
						if(terms == "") {
							terms = elements[i].getName();
						} else {
							terms = terms + ", " + elements[i].getName();									
						}
					}
				}
				item.setText(j, terms);	
			}
		}
	}
	
	private void createEvidencePane(Composite parent) {

	}

	private void createCloseMatchPane(Composite parent) {

	}

	private void createContextPane(Composite parent) {

	}

	public boolean doesNameExist(String name) {
		Term[] theTerms = vocab.getTerms();
		for(int i = 0; i < theTerms.length; i++){
			if(theTerms[i].getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public void updateTables(Integer vocabID) {
		//find in workspace
		TableItem[] workspaceItems = workspaceTable.getItems();
		for(int i = 0; i < workspaceItems.length; i++) {
			if(workspaceItems[i].getData("uid").equals(vocabID)){
				populateRow(workspaceItems[i], showTextWorkspace);
				break;
			}
		}		

		//find in TableView
		tableview.clearAll(); //does this perform better?
/*		TableItem[] tableItems = tableview.getItems();
		for(int i = 0; i < tableItems.length; i++) {
			if(tableItems[i].getData("uid").equals(vocabID)){
				populateRow(tableItems[i]);
				break;
			}
		}*/
	
		//update need save
		saveVocab.setBackground(red);
		saveVocab.setImage(needsaveIcon);

	}

	private void addTableListners() {
		
		mainListener = new Listener() {
			int EDITABLECOLUMN = -1;
			public void handleEvent(Event e) {
		        if((e.stateMask & SWT.CTRL) > 0)
		        {
			          System.out.println("ctrl pressed");
		      	  interfaceState = DND.DROP_COPY;
		        } else {
			          System.out.println("ctrl released");
		        	interfaceState = DND.DROP_MOVE;
		        }

			    // Clean up any previous editor control
			    Control oldEditor = editor.getEditor();
			    if (oldEditor != null)
			    	oldEditor.dispose();

		        
		        if(e.widget instanceof Table && ((Table)(e.widget)).getSelectionCount() > 0){

		        	// Identify the selected row
					selectedItem = ((Table)(e.widget)).getItem(new Point(e.x,e.y));
				    if (selectedItem == null)
				    	return;
					//System.out.println("theTable.getData(\"name\") = " + (String)(e.widget.getData("name")));
					if(((String)(e.widget.getData("name"))).equals("workspaceTable") || (((String)(e.widget.getData("name"))).equals("tableview")))
					{				
						Table theTable = selectedItem.getParent();				
					    int xoff = e.x;
						Rectangle colrec = null;
						int[] order = theTable.getColumnOrder();
						for(int i = 0; i < theTable.getColumnCount(); i++) {
							colrec = selectedItem.getBounds(order[i]);
							//System.out.println("colrec x = " + colrec.x + "\n");
							//System.out.println("xoff x = " + xoff + "\n");
							if(colrec.x + colrec.width > xoff){
								EDITABLECOLUMN = order[i];
								break;
							}
						}

						//System.out.println("EDITABLECOLUMN = " + EDITABLECOLUMN);
						
						draggedRow = (Integer)(selectedItem.getData("uid"));
						draggedCol = (Integer)(theTable.getColumn(EDITABLECOLUMN).getData("uid"));
						//System.out.println("draggedRow = " + draggedRow);
						//System.out.println("draggedCol = " + draggedCol + "\n");
						if(EDITABLECOLUMN > 1){
							xoff = xoff - colrec.x;
							dragElements = new AssociatedElement[1];
							AssociatedElement aElements[] = vocab.getTerms()[vocab.getTermIndex(draggedRow)].getAssociatedElements(draggedCol);
							for(int i = 0; i < aElements.length; i++){
								xoff = xoff - gc.textExtent(aElements[i].getName() + ",").x;
								if(xoff <= 0){
									dragElements[0] = aElements[i];
									break;
								}
								xoff = xoff - gc.textExtent(" ").x;
								if(i == aElements.length - 1){
									dragElements[0] = aElements[i];
									break;									
								}
							}
						}
				     
				      
				      
				
				      // The control that will be the editor must be a child of the
				      // Table
					  if(draggedCol == -201) {
						  StyledText TableEditor = new StyledText(theTable, SWT.NONE);
						  TableEditor.setText(selectedItem.getText(EDITABLECOLUMN));
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
								    if (!selectedItem.isDisposed())
								    {
								    	Term theTerm = vocab.getTerms()[(vocab.getTermIndex((Integer)(selectedItem.getData("uid"))))];
							        	String text = ((StyledText)editor.getEditor()).getText();
								    	if(!text.equals(theTerm.getName())) {
									    	//System.out.println("writing to " +selectedItem.getData("uid"));
									    	theTerm.setName(text);
								        	updateTables((Integer)selectedItem.getData("uid"));
								    	}
								        editor.getEditor().dispose();
							        }
						        }
						      });
						  TableEditor.addKeyListener(new KeyListener() {
						        public void keyPressed(KeyEvent e) {
							        }
						        public void keyReleased(KeyEvent e) {
								    if (e.character == SWT.CR && !selectedItem.isDisposed())
								    {
								    	Term theTerm = vocab.getTerms()[(vocab.getTermIndex((Integer)(selectedItem.getData("uid"))))];
							        	String text = ((StyledText)editor.getEditor()).getText();
							        	int index = ((StyledText)editor.getEditor()).getCaretOffset();
							        	text = text.substring(0,index-2) + text.substring(index,text.length());
							        	((StyledText)editor.getEditor()).setText(text);
								    	if(!text.equals(theTerm.getName())) {
									    	//System.out.println("writing to " +selectedItem.getData("uid"));
									    	theTerm.setName(text);
								        	updateTables((Integer)selectedItem.getData("uid"));
								    	}
								        editor.getEditor().dispose();
							        }
						        }
						      });
					      //newEditor.selectAll();
						  TableEditor.setFocus();
						  TableEditor.setFont(ItalicFont);
						  TableEditor.setSelection(TableEditor.getText().length());
					      editor.setEditor(TableEditor, selectedItem, EDITABLECOLUMN);
					      editor.minimumHeight = selectedItem.getBounds(EDITABLECOLUMN).height;
					      editor.minimumWidth = selectedItem.getBounds(EDITABLECOLUMN).width;
					  } else if(draggedCol == -202) {
//						  if(not checked) {
						  //System.out.println("setting image for " + EDITABLECOLUMN);
						  	selectedItem.setImage(EDITABLECOLUMN,CheckIcon);						  
//							+ set in vocab
//					  	  } else {
//						  	selectedItem.setImage(EDITABLECOLUMN,null);						  
//							+ set in vocab
//						  }
			        	  updateTables((Integer)selectedItem.getData("uid"));						  
					  }
				    }

		        }
			
			}

		};

			Listener scrollListener = new Listener() {
				public void handleEvent(Event e) {
					//addlock panes
					//editor.getEditor().dispose();  doesn't do anything?  need to fix this bug
					}
		      };

		
		//		display.addFilter(SWT.MouseDown,mainListener);
			workspaceTable.addListener(SWT.MouseDown,mainListener);
			if(workspaceTable.getVerticalBar() != null)
				workspaceTable.getVerticalBar().addListener(SWT.FocusIn, scrollListener);
			if(workspaceTable.getHorizontalBar() != null)
				workspaceTable.getHorizontalBar().addListener(SWT.FocusIn, scrollListener);
			
			tableview.addListener(SWT.MouseDown,mainListener);
			if(tableview.getVerticalBar() != null)
				tableview.getVerticalBar().addListener(SWT.FocusIn, scrollListener);
			if(tableview.getHorizontalBar() != null)
				tableview.getHorizontalBar().addListener(SWT.FocusIn, scrollListener);

	
			display.addFilter(SWT.MouseDown, new Listener() {	
				public void handleEvent(Event e) {
				    // Clean up any previous editor control
					if(editor != null){
					    Control oldEditor = editor.getEditor();
					    if (oldEditor != null){
					    	oldEditor.dispose();	
					    }
					}
				}
			});
	}
	
  	 protected void finalize()  {
//  		display.removeFilter(SWT.MouseDown,mainListener);
	 }
		
}
