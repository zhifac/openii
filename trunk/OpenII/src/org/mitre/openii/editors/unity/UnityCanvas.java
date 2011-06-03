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
import java.util.AbstractList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang.WordUtils;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.schemaTree.SchemaMenuManager;
import org.mitre.openii.widgets.schemaTree.SchemaTree;
import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.VocabularyTerms;
import org.mitre.schemastore.model.mappingInfo.AssociatedElementHash;
import org.mitre.schemastore.model.mappingInfo.MappingInfoExt;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;
import org.mitre.schemastore.model.Annotation;

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
	private HashMap<Integer, Mapping> mappings = new HashMap<Integer, Mapping>();
    private HashMap<Integer, Annotation> checkStatus = new HashMap<Integer, Annotation>();
	private boolean confirmation;
//	private Hashtable<Integer, SchemaInfo> schemaInfos = new Hashtable<Integer, SchemaInfo>();
	private boolean coloredTable;

	private Color red =  this.getDisplay().getSystemColor(SWT.COLOR_RED);
	private Color black =  this.getDisplay().getSystemColor(SWT.COLOR_BLACK);
	private Color gray =  this.getDisplay().getSystemColor(SWT.COLOR_GRAY);
	private Color green =  this.getDisplay().getSystemColor(SWT.COLOR_GREEN);
	private Color yellow =  this.getDisplay().getSystemColor(SWT.COLOR_YELLOW);
	private Color white =  this.getDisplay().getSystemColor(SWT.COLOR_WHITE);
	private Color lightYellow = new Color(this.getDisplay(), 255, 255, 128);
	private Color lightGreen = new Color(this.getDisplay(), 128, 225, 128);
	private Color lightRed = new Color(this.getDisplay(), 255, 128, 128);
	private Color lightBlue = new Color(this.getDisplay(), 225, 225, 255);
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
    private Label synsetLabel1;
    private Label synsetLabel2;
    private Label synsetLabel3;
    private Button colorsettingsE;
    private Canvas treeViewCanvas;
    private Canvas tableViewCanvas;
    private Canvas searchViewCanvas;
    private Canvas evidenceCanvas;
    private Canvas closeMatchCanvas;
    private Canvas contextCanvas;
    private Composite EvidenceView;
    private Composite closeMatchView;
    private ScrolledComposite contextView;
    private GridData EvidenceViewGridData;
    private GridLayout EvidenceViewLayout;
    private GridData closeMatchViewGridData;
    private GridLayout closeMatchViewLayout;
    private GridData contextViewGridData;
    private GridLayout contextViewLayout;
    private Integer evidenceSID = new Integer(-1);
    private Integer closeMatchSID = new Integer(-1);
    private Integer contextSID = new Integer(-1);
    private Integer detailSID = new Integer(-1);     
    private Integer detailScID = new Integer(-1);     
    private Integer detailEID = new Integer(-1);     
    private Button newSynset;
    private float minMatchScore = (float)0.20;
    private final Canvas main_sash = new Canvas(this, SWT.NONE);		
    private final Canvas workspace = new Canvas(main_sash, SWT.EMBEDDED);
	private final SashForm bottom_sash = new SashForm(main_sash, SWT.HORIZONTAL);
    private final CTabFolder folder = new CTabFolder(bottom_sash, SWT.TOP);
    private final Composite buttonsC = new Composite(workspace, SWT.NONE);
    private final Canvas tablespace = new Canvas(workspace, SWT.EMBEDDED);
    private final StackLayout stackLayout = new StackLayout();
	private final StyledText tempLabel = new StyledText(tablespace,SWT.WRAP);
    private final Table workspaceTable = new Table(tablespace, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
	private final GC gc = new GC(workspaceTable);
    private final TableEditor editor = new TableEditor(workspaceTable);
    private final Composite buttonsD = new Canvas(workspace,SWT.NONE);
	private final CTabFolder folder2 = new CTabFolder(bottom_sash, SWT.TOP);
    private Integer draggedCol = null;
    private Integer draggedRow = null;
    private Table activeTable = null;
    private int eventDetail = 0;
    private int dragID = 0;
    private int interfaceState = DND.DROP_MOVE;
    private AssociatedElement dragElement;
    private AssociatedElement copiedElement;
    private Integer copiedSchema;
    private TableItem selectedItem;
    private Display display;
    private Image CheckIcon = OpenIIActivator.getImage("checkicon.png");
    private Image saveIcon = OpenIIActivator.getImage("save.png");
    private Image needsaveIcon = OpenIIActivator.getImage("needsave.png");
    private Image addSynsetIcon = OpenIIActivator.getImage("addsynset.png");
    private Image alphaIcon = OpenIIActivator.getImage("alpha.png");
    private Image changeDescIcon = OpenIIActivator.getImage("change_desc.png");
    private Image deleteSynsetIcon = OpenIIActivator.getImage("deletesynset.png");
    private Image evidenceIcon = OpenIIActivator.getImage("footprint1.png");
    private Image closeMatchIcon = OpenIIActivator.getImage("footprint2.png");
    private Image contextIcon = OpenIIActivator.getImage("home.png");
    private Image removeIcon = OpenIIActivator.getImage("remove.png");
    private Image remove2Icon = OpenIIActivator.getImage("removeBigIcon.png");
    private Image deleteIcon = OpenIIActivator.getImage("delete.png");
    private Image pasteIcon = OpenIIActivator.getImage("add.png");
    private Image renameIcon = OpenIIActivator.getImage("rename.png");
    private Image searchIcon = OpenIIActivator.getImage("search.png");
    private Image showTextIcon = OpenIIActivator.getImage("showtext.png");
    private Image tableIcon = OpenIIActivator.getImage("tableicon.png");
    private Image treeViewIcon = OpenIIActivator.getImage("treeview.png");
    private Image insertIcon = OpenIIActivator.getImage("insert.png");
    private Image moveIcon = OpenIIActivator.getImage("move.png");
    private Image copyIcon = OpenIIActivator.getImage("copy.png");
    private Image seperatorIcon = OpenIIActivator.getImage("seperator.png");
    private Image clearIcon = OpenIIActivator.getImage("clear.png");
    private Image alphaSortDownIcon = OpenIIActivator.getImage("alphaDown.png");
    private Image alphaSortUpIcon = OpenIIActivator.getImage("alphaUp.png");
    private Image StructuralSortIcon = OpenIIActivator.getImage("structurally.png");
    private Image checkRemoveIcon = OpenIIActivator.getImage("checkRemove.png");
    private Image checkSortUpIcon = OpenIIActivator.getImage("checkSortUp.png");
    private Image checkSortDownIcon = OpenIIActivator.getImage("checkSortDown.png");
    private Image SchemaImage = OpenIIActivator.getImage("Schema.gif");
    private Image DomainValueImage = OpenIIActivator.getImage("DomainValue.jpg");
    private Image AttributeImage = OpenIIActivator.getImage("Attribute.jpg");
    private Image ContainmentImage = OpenIIActivator.getImage("Containment.jpg");
    private Image RelationshipImage = OpenIIActivator.getImage("Relationship.jpg");
    private Image SchemaElementImage = OpenIIActivator.getImage("SchemaElement.jpg");
    
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

		ArrayList<Mapping> rawmappings = OpenIIManager.getMappings(vocabulary.getProjectID());
		Iterator<Mapping> itr = rawmappings.iterator();
		while(itr.hasNext()) {
			Mapping m = itr.next();
			if(m != null) {
				mappings.put(m.getId(),m);			
			}
		}
		ArrayList<Annotation> allAnnotations = OpenIIManager.getAnnotationsByGroup(vocabulary.getProjectID(), "checked");
System.err.println("retreiving annotations for " + vocabulary.getProjectID());
		Iterator<Annotation> itr2 = allAnnotations.iterator();
		while(itr2.hasNext()) {
			Annotation a = itr2.next();
			if(a != null) {
System.err.println("got annotation for " + a.getElementID());
				checkStatus.put(a.getElementID(), a);			
			}
		}
		
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
		
		GridLayout msLayout = new GridLayout(1, true);
		msLayout.marginWidth = 0;
		msLayout.marginHeight = 0;
		main_sash.setLayout(msLayout);

		
		createWorkspace(main_sash);
		
//		SashForm main_sash = new SashForm(this, SWT.HORIZONTAL);		
	
//		CTabFolder folder = new CTabFolder(main_sash, SWT.TOP);
		folder.setUnselectedCloseVisible(false);
		folder.setSimple(false);
		
		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setImage(treeViewIcon);
		item.setText("Tree");
		item.setToolTipText("Tree View");
		treeViewCanvas = new Canvas(folder, SWT.EMBEDDED);
		item.setControl(treeViewCanvas);
		createTreeView(treeViewCanvas);
		
		item = new CTabItem(folder, SWT.NONE);
		item.setImage(tableIcon);
		item.setText("Table");
		item.setToolTipText("Table View");
		tableViewCanvas = new Canvas(folder, SWT.EMBEDDED);
		item.setControl(tableViewCanvas);
		createTableView(tableViewCanvas);
		
		item = new CTabItem(folder, SWT.NONE);
		item.setImage(searchIcon);
		item.setText("Search");
		item.setToolTipText("Advanced Search");
		searchViewCanvas = new Canvas(folder, SWT.EMBEDDED);
		item.setControl(searchViewCanvas);
		createSearchView(searchViewCanvas);
		
		folder.setSelection(0); 
		folder.setBackground(null); 
		folder.setBorderVisible(true);
		folder.setSelectionBackground(display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT)); 

		GridLayout bottomLayout = new GridLayout(2, false);
		bottomLayout.marginWidth = 0;
		bottomLayout.marginHeight = 0;
		bottom_sash.setLayout(bottomLayout);
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessVerticalSpace = true;
		gridData.grabExcessHorizontalSpace = true;
		bottom_sash.setLayoutData(gridData);

		
		folder2.setUnselectedCloseVisible(false);
		folder2.setSimple(false);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		folder2.setLayoutData(gridData);

		item = new CTabItem(folder2, SWT.NONE);
		item.setImage(evidenceIcon);
		item.setText("Evidence");
		item.setToolTipText("Evidence");
		evidenceCanvas = new Canvas(folder2, SWT.EMBEDDED);
		item.setControl(evidenceCanvas);
		createEvidencePane(evidenceCanvas);
		
		item = new CTabItem(folder2, SWT.NONE);
		item.setImage(closeMatchIcon);
		item.setText("Close Matches");
		item.setToolTipText("Close Matches");
		closeMatchCanvas = new Canvas(folder2, SWT.EMBEDDED);
		item.setControl(closeMatchCanvas);
		createCloseMatchPane(closeMatchCanvas);

		item = new CTabItem(folder2, SWT.NONE);
		item.setImage(contextIcon);
		item.setText("Context");
		item.setToolTipText("Term Context");
		contextCanvas = new Canvas(folder2, SWT.EMBEDDED);
		item.setControl(contextCanvas);
		createContextPane(contextCanvas);

		folder2.setSelection(0); 
		folder2.setBackground(null); 
		folder2.setSelectionBackground(display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT)); 
		folder2.setBorderVisible(true);
		
		addListners();
		
	}

	private void createTreeView(Composite parent) {

		GridLayout treeViewlayout = new GridLayout(1, false);
		treeViewlayout.marginHeight = 0;
		treeViewlayout.marginWidth = 0;
		treeViewlayout.verticalSpacing = 0;
		treeViewlayout.horizontalSpacing = 0;
		treeViewlayout.marginBottom = 0;
		treeViewCanvas.setLayout(treeViewlayout);
		
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
		seperator.setImage(seperatorIcon);
//		seperator.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
		
		treeAlpha = new Button(treeButtons, SWT.TOGGLE);
		treeAlpha.setToolTipText("Alphabetize");
		treeAlpha.setImage(alphaIcon);
		treeAlpha.setVisible(true);
//		treeAlpha.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));

		Label seperator3 = new Label(treeButtons, SWT.BITMAP);
		seperator3.setImage(seperatorIcon);

		Button treeFilter1 = new Button(treeButtons, SWT.TOGGLE);
		treeFilter1.setImage(OpenIIActivator.getImage("checkedFilter.png"));
		treeFilter1.setToolTipText("Filter out checked terms");
		treeFilter1.setVisible(true);
		treeFilter1.setEnabled(false);
//		treeFilter1.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));

		Button treeFilter2 = new Button(treeButtons, SWT.TOGGLE);
		treeFilter2.setImage(OpenIIActivator.getImage("greenFilter.png"));
		treeFilter2.setToolTipText("Filter out terms in good matches");
		treeFilter2.setVisible(true);
		treeFilter2.setEnabled(false);
//		treeFilter2.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));

		Button treeFilter3 = new Button(treeButtons, SWT.TOGGLE);
		treeFilter3.setImage(OpenIIActivator.getImage("yellowFilter.png"));
		treeFilter3.setToolTipText("Filter out terms in average matches");
		treeFilter3.setVisible(true);
		treeFilter3.setEnabled(false);
//		treeFilter3.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
		


		treeview = new SchemaTree(parent,schemaInfos[0],schemaModels[0], true);
		treeview.setMenuManager(new SchemaTreeMenuManager(treeview.getTreeViewer(),this));
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
            	String data2[] = new String[1];
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
					draggedCol = getTreeSchemaID();
					data2[0] = 	"" + items[0].getData("uid");						
					dragElement = new AssociatedElement(draggedCol,(Integer)items[0].getData("uid"),items[0].getText(),(String)items[0].getData("description"));
					for(int i = 0; i < items.length; i++)
					{
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
		colorSelectorT.setForeground(gray);
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
		colorsettingsT.setEnabled(false);
		colorsettingsT.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorsettingsT.setSelection(false);
			}
		});

		Button showtext = new Button(tableButtons, SWT.TOGGLE);
		showtext.setImage(showTextIcon);
		showtext.setToolTipText("Hide terms");

//		Button showicons = new Button(tableButtons, SWT.TOGGLE);
//		showicons.setImage(OpenIIActivator.getImage("showIcons.png"));
//		showicons.setToolTipText("Hide symbols");


		Label seperator3 = new Label(tableButtons, SWT.BITMAP);
		seperator3.setImage(seperatorIcon);
//		seperator3.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
		
		sortSelector = new Combo(tableButtons, SWT.READ_ONLY | SWT.DROP_DOWN);
		sortSelector.setForeground(gray);
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
		sortSelector.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				sortSelector.select(0);
			}
		});

		
		Label seperator2 = new Label(tableButtons, SWT.BITMAP);
		seperator2.setImage(seperatorIcon);
//		seperator2.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));

		Button tableFilter1 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter1.setImage(OpenIIActivator.getImage("checkedFilter.png"));
		tableFilter1.setToolTipText("Filter out checked synsets");
		tableFilter1.setEnabled(false);
//		tableFilter1.setVisible(true);
//		tableFilter1.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
//		tableFilter1.pack();

		Button tableFilter2 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter2.setImage(OpenIIActivator.getImage("greenFilter.png"));
		tableFilter2.setToolTipText("Filter out synsets with good matches");
		tableFilter2.setEnabled(false);
//		tableFilter2.setVisible(true);
//		tableFilter2.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
//		tableFilter2.pack();

		Button tableFilter3 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter3.setImage(OpenIIActivator.getImage("yellowFilter.png"));
		tableFilter3.setToolTipText("Filter out synsets with average matches");
		tableFilter3.setEnabled(false);
//		tableFilter3.setVisible(true);
//		tableFilter3.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));
//		tableFilter3.pack();

		Button tableFilter4 = new Button(tableButtons, SWT.TOGGLE);
		tableFilter4.setImage(OpenIIActivator.getImage("singleton.png"));
		tableFilter4.setToolTipText("Filter out singletons");		
		tableFilter4.setEnabled(false);

		
		
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
	        	int ID = vocab.getTerms()[tableview.indexOf(item)].getId();
				item.setData("uid", ID);
				if(checkStatus.containsKey(ID)) { 
					item.setImage(0,CheckIcon);
				}
				populateRow(item,showTextTableView);
				item.setBackground(1, lightBlue);
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
		textSearchTable.setEnabled(false);
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
							addTerm(vocabID, dragElement);
							if(event.detail == DND.DROP_MOVE){
								deleteTerm(draggedRow, dragElement);
							}
						}
					}						
			} 
		});

	}

	private void createSearchView(Composite parent) {
		GridLayout tableViewlayout = new GridLayout(1, false);
		tableViewlayout.marginHeight = 0;
		tableViewlayout.marginWidth = 0;
		tableViewlayout.verticalSpacing = 0;
		tableViewlayout.horizontalSpacing = 0;
		tableViewlayout.marginBottom = 0;
		parent.setLayout(tableViewlayout);


		Label TBD = new Label(parent, SWT.NONE);
		TBD.setText("TBD");
		parent.getParent().layout(true);
		
	}

	private void createWorkspace(Composite parent) {

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
		colorSelector.setForeground(gray);
		colorSelector.add("Not Colored");
		colorSelector.add("Color by Element Type");
		colorSelector.add("Instance Count");
		colorSelector.add("Canonical Match");
		colorSelector.add("Match Strength (strongest)");
		colorSelector.add("Match Strength (weakest)");		
		colorSelector.setToolTipText("No coloring applied");
		colorSelector.select(0);

		colorsettings = new Button(buttonsC, SWT.PUSH);
		colorsettings.setImage(OpenIIActivator.getImage("color_settings.png"));
		colorsettings.setToolTipText("Color Settings");
		colorsettings.setEnabled(false);

		Button showtext = new Button(buttonsC, SWT.TOGGLE);
		showtext.setImage(showTextIcon);
		showtext.setToolTipText("Hide terms");

//		Button showicons = new Button(buttonsC, SWT.TOGGLE);
//		showicons.setImage(OpenIIActivator.getImage("showIcons.png"));
//		showicons.setToolTipText("Hide symbols");

		Button clearSynsets = new Button(buttonsC, SWT.PUSH);
		clearSynsets.setImage(clearIcon);
		clearSynsets.setToolTipText("Clear Workspace");		
			
		
		Label seperator3 = new Label(buttonsC, SWT.BITMAP);
		seperator3.setImage(seperatorIcon);
//		seperator3.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, false, false));

		newSynset = new Button(buttonsC, SWT.PUSH);
		newSynset.setImage(addSynsetIcon);
		newSynset.setToolTipText("Create a new Synset");		
				
		Button renameSynset = new Button(buttonsC, SWT.PUSH);
		renameSynset.setImage(renameIcon);
		renameSynset.setToolTipText("Rename the selected Synset");		
				
		Button descSynset = new Button(buttonsC, SWT.PUSH);
		descSynset.setImage(changeDescIcon);
		descSynset.setToolTipText("Change the description of the selected Synset");		
				
		Button deleteSynset = new Button(buttonsC, SWT.PUSH);
		deleteSynset.setImage(deleteSynsetIcon);
		deleteSynset.setToolTipText("Permanently delete selected Synset from Vocabulary");		

		Label seperator = new Label(buttonsC, SWT.BITMAP);
		seperator.setImage(seperatorIcon);

		
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
		for (int i = 0; i < schemas.length; i++) {
			tempC = new TableColumn(workspaceTable, SWT.NONE);
			tempC.setText(schemas[i].getName());
			tempC.setData("uid",new Integer(schemas[i].getId()));
			//tempC.setWidth((main_sash.getBounds().x - 120) / schemas.length);
			tempC.setMoveable(true);
			//tempC.addListener(SWT.Selection, sortAlphabeticallyListener);
			tempC.setToolTipText(schemas[i].getName());
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
		
		
		//add listeners
		colorSelector.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorSelector.select(0);
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
				main_sash.layout();									

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
									x1.setImage(removeIcon);
									x1.setData("index", new Integer(workspaceTable.getItemCount()));
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
				renameSynset(workspaceTable);
			}
		});
				
		descSynset.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				descSynset(workspaceTable);
			}
		});
				
		deleteSynset.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				deleteSynset(workspaceTable);
			}
		});

		
		saveVocab.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				VocabularyTerms oldVocab = vocab;
				vocab = OpenIIManager.saveVocabularyTerms(vocab);
				//go through each item and update stuff if ID changed
				Integer oldID = 0;
				Integer newID = 0;
				for(int i = 0; i < vocab.getTerms().length;i++)
				{
					oldID = oldVocab.getTerms()[i].getId();
					newID = vocab.getTerms()[i].getId();
					if(maxSafeID <= newID) maxSafeID = newID+1;
					
					if(newID != oldID) {
						if(checkStatus.containsKey(oldID)) {
							Annotation anno = checkStatus.remove(oldID);
							anno.setElementID(newID);
							checkStatus.put(newID, anno);
						}
						if(draggedRow == oldID)
						{
							draggedRow = newID;
						}
						if(evidenceSID == oldID)
						{
							evidenceSID = newID;
						}
						if(closeMatchSID == oldID)
						{
							closeMatchSID = newID;
						}
						if(contextSID == oldID)
						{
							contextSID = newID;
						}
						if(detailSID == oldID)
						{
							detailSID = newID;
						}
					}
				}
				OpenIIManager.clearAnnotations(vocab.getProjectID(), "checked");
				ArrayList<Annotation> annoList = new ArrayList<Annotation>(checkStatus.values());
				for(int a = 0; a < annoList.size(); a++){
					System.err.println("element = " + annoList.get(a).getElementID());
					System.err.println("project = " + annoList.get(a).getGroupID());
					System.err.println("attribute = " + annoList.get(a).getAttribute());
				}
				OpenIIManager.setAnnotations(annoList);
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

					
					addToWorkspace(data, posIndex);
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
							addTerm(vocabID, dragElement);
							if(event.detail == DND.DROP_MOVE){
								deleteTerm(draggedRow, dragElement);
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
					addToWorkspace(data);
			} 
		});
						
	}
	
	//add a term to a synset
	public void addTerm(Integer synsetID, AssociatedElement elem){
		Term theTerm = vocab.getTerms()[(vocab.getTermIndex(synsetID))];
		theTerm.addAssociatedElement(elem);
		vocab.termUpdated(synsetID);
		updateTables(synsetID);
	}

	//delete a term to a synset
	public void deleteTerm(Integer synsetID, AssociatedElement elem){
		Term fromTerm = vocab.getTerms()[(vocab.getTermIndex(synsetID))];
		fromTerm.removeAssociatedElement(elem.getElementID(),elem.getSchemaID());
		vocab.termUpdated(synsetID);
		updateTables(synsetID);
	}

	//add synsets with term matching ID to workspace
	public void addAssociatedSynsets(Integer id){
		Integer[] idssub;
		Integer schema = getTreeSchemaID();
		idssub = vocab.reverseLookup(id, schema);
		String[] ids = new String[idssub.length];
		//System.out.println("adding " + id);
				for(int y = 0; y < idssub.length; y++)
		{
			ids[y] = "" + idssub[y];
		}
		addToWorkspace(ids);
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
		main_sash.layout();									

	}
	
	public void addToWorkspace(String[] data) {
		addToWorkspace(data, workspaceTable.getItemCount());
	}
	
	public void addToWorkspace(String[] data, int posIndex) {
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
			if(posIndex > workspaceTable.getItemCount())  posIndex = workspaceTable.getItemCount();
			TableItem item = new TableItem(workspaceTable, SWT.NONE, posIndex); 
			item.setBackground(1, lightBlue);
			item.setData("uid", termID);
			populateRow(item, showTextWorkspace);
			if(checkStatus.containsKey(termID)) {
				item.setImage(0, CheckIcon);
			}
				
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
		
		GridLayout viewlayout = new GridLayout(2, false);
		viewlayout.marginHeight = 0;
		viewlayout.marginWidth = 0;
		viewlayout.verticalSpacing = 0;
		viewlayout.horizontalSpacing = 0;
		viewlayout.marginBottom = 0;
		parent.setLayout(viewlayout);
		GridData viewGridData = new GridData();
		viewGridData.horizontalSpan = 2;
		viewGridData.minimumHeight = 0;
		viewGridData.verticalIndent = 0;
		viewGridData.horizontalAlignment = GridData.FILL;
		viewGridData.verticalAlignment = GridData.FILL;
		viewGridData.grabExcessHorizontalSpace = true;
		viewGridData.grabExcessVerticalSpace = true;
		parent.setLayoutData(viewGridData);
		
		Composite synsetLabelC = new Composite(parent, SWT.NONE);
		RowLayout labellayout = new RowLayout();
		//labellayout.center = true;
		synsetLabelC.setLayout(labellayout);
		synsetLabelC.setLayoutData(new GridData (SWT.BEGINNING, SWT.CENTER, true, false));
		
		synsetLabel1 = new Label(synsetLabelC, SWT.NONE);
		synsetLabel1.setText("");
		synsetLabel1.setFont(LargeBoldFont);
		
		Composite buttons = new Composite(parent, SWT.NONE);
		RowLayout tableButtonlayout = new RowLayout();
		//tableButtonlayout.center = true;
		buttons.setLayout(tableButtonlayout);
		buttons.setLayoutData(new GridData (SWT.END, SWT.CENTER, false, false));
		
		colorsettingsE = new Button(buttons, SWT.PUSH);
		colorsettingsE.setImage(OpenIIActivator.getImage("color_settings.png"));
		colorsettingsE.setToolTipText("Color Settings");
		colorsettingsE.setEnabled(false);
		colorsettingsE.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorsettingsE.setSelection(false);
			}
		});

		EvidenceView = new Composite(parent, SWT.NONE);
		EvidenceViewGridData = new GridData();
		EvidenceViewGridData.horizontalSpan = 2;
		EvidenceViewGridData.minimumHeight = 0;
		EvidenceViewGridData.verticalIndent = 0;
		EvidenceViewGridData.horizontalAlignment = GridData.FILL;
		EvidenceViewGridData.verticalAlignment = GridData.BEGINNING;
		EvidenceViewGridData.grabExcessHorizontalSpace = true;
		EvidenceViewGridData.grabExcessVerticalSpace = true;
//		EvidenceView.setLayoutData(gridData);

		EvidenceViewLayout = new GridLayout(1, false);
		EvidenceViewLayout.marginHeight = 0;
		EvidenceViewLayout.marginWidth = 0;
		EvidenceViewLayout.verticalSpacing = 0;
		EvidenceViewLayout.horizontalSpacing = 0;
		EvidenceViewLayout.marginBottom = 0;
//		EvidenceView.setLayout(EVlayout);	

	}

	private void createCloseMatchPane(Composite parent) {
		
		GridLayout viewlayout = new GridLayout(3, false);
		viewlayout.marginHeight = 0;
		viewlayout.marginWidth = 0;
		viewlayout.verticalSpacing = 0;
		viewlayout.horizontalSpacing = 0;
		viewlayout.marginBottom = 0;
		parent.setLayout(viewlayout);
		GridData viewGridData = new GridData();
		viewGridData.horizontalSpan = 3;
		viewGridData.minimumHeight = 0;
		viewGridData.verticalIndent = 0;
		viewGridData.horizontalAlignment = GridData.FILL;
		viewGridData.verticalAlignment = GridData.FILL;
		viewGridData.grabExcessHorizontalSpace = true;
		viewGridData.grabExcessVerticalSpace = true;
		parent.setLayoutData(viewGridData);
		
		Composite synsetLabelC = new Composite(parent, SWT.NONE);
		RowLayout labellayout = new RowLayout();
		//labellayout.center = true;
		synsetLabelC.setLayout(labellayout);
		synsetLabelC.setLayoutData(new GridData (SWT.BEGINNING, SWT.CENTER, false, false));
		
		synsetLabel2 = new Label(synsetLabelC, SWT.NONE);
		synsetLabel2.setText("");
		synsetLabel2.setFont(LargeBoldFont);
		
		Composite matchS = new Composite(parent, SWT.NONE);
		RowLayout matchSlayout = new RowLayout();
		matchSlayout.center = true;
		matchSlayout.fill = true;
		matchSlayout.marginTop = 5;
		//tableButtonlayout.center = true;
		matchS.setLayout(matchSlayout);
		matchS.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, true, false));

		Composite buttons = new Composite(parent, SWT.NONE);
		RowLayout tableButtonlayout = new RowLayout();
		//tableButtonlayout.center = true;
		buttons.setLayout(tableButtonlayout);
		buttons.setLayoutData(new GridData (SWT.END, SWT.CENTER, false, false));
		
		
		Label scoreL = new Label(matchS, SWT.NONE);
		scoreL.setText("Match Score >= ");
		Text scoreT = new Text(matchS, SWT.BORDER);
		scoreT.setText(""+minMatchScore);
		scoreT.addListener(SWT.KeyUp, new Listener() {  
		     public void handleEvent(Event e) {  
					if(e.character==SWT.CR) {
						minMatchScore = Float.parseFloat(((Text)e.widget).getText());
						updateDetailPane(detailSID, detailScID, detailEID);
					}					
		     }});

		colorsettingsE = new Button(buttons, SWT.PUSH);
		colorsettingsE.setImage(OpenIIActivator.getImage("color_settings.png"));
		colorsettingsE.setToolTipText("Color Settings");
		colorsettingsE.setEnabled(false);
		colorsettingsE.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorsettingsE.setSelection(false);
			}
		});

		closeMatchView = new Composite(parent, SWT.NONE);
		closeMatchViewGridData = new GridData();
		closeMatchViewGridData.horizontalSpan = 2;
		closeMatchViewGridData.minimumHeight = 0;
		closeMatchViewGridData.verticalIndent = 0;
		closeMatchViewGridData.horizontalAlignment = GridData.FILL;
		closeMatchViewGridData.verticalAlignment = GridData.BEGINNING;
		closeMatchViewGridData.grabExcessHorizontalSpace = true;
		closeMatchViewGridData.grabExcessVerticalSpace = true;
//		closeMatchView.setLayoutData(gridData);

		closeMatchViewLayout = new GridLayout(1, false);
		closeMatchViewLayout.marginHeight = 0;
		closeMatchViewLayout.marginWidth = 0;
		closeMatchViewLayout.verticalSpacing = 0;
		closeMatchViewLayout.horizontalSpacing = 0;
		closeMatchViewLayout.marginBottom = 0;
//		closeMatchView.setLayout(EVlayout);	

	}

	private void createContextPane(Composite parent) {
		GridLayout viewlayout = new GridLayout(2, false);
		viewlayout.marginHeight = 0;
		viewlayout.marginWidth = 0;
		viewlayout.verticalSpacing = 0;
		viewlayout.horizontalSpacing = 0;
		viewlayout.marginBottom = 0;
		parent.setLayout(viewlayout);
		GridData viewGridData = new GridData();
		viewGridData.horizontalAlignment = GridData.FILL;
		viewGridData.verticalAlignment = GridData.FILL;
		viewGridData.grabExcessHorizontalSpace = true;
		viewGridData.grabExcessVerticalSpace = true;
		parent.setLayoutData(viewGridData);
		
		Composite synsetLabelC = new Composite(parent, SWT.NONE);
		RowLayout labellayout = new RowLayout();
		//labellayout.center = true;
		synsetLabelC.setLayout(labellayout);
		synsetLabelC.setLayoutData(new GridData (SWT.BEGINNING, SWT.CENTER, true, false));
		
		synsetLabel3 = new Label(synsetLabelC, SWT.NONE);
		synsetLabel3.setText("");
		synsetLabel3.setFont(LargeBoldFont);
		
		Composite buttons = new Composite(parent, SWT.NONE);
		RowLayout tableButtonlayout = new RowLayout();
		//tableButtonlayout.center = true;
		buttons.setLayout(tableButtonlayout);
		buttons.setLayoutData(new GridData (SWT.END, SWT.CENTER, false, false));
/*		
		colorsettingsE = new Button(buttons, SWT.PUSH);
		colorsettingsE.setImage(OpenIIActivator.getImage("color_settings.png"));
		colorsettingsE.setToolTipText("Color Settings");
		colorsettingsE.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorsettingsE.setSelection(false);
			}
		});
*/
		contextView = new ScrolledComposite(parent, SWT.V_SCROLL);
		contextViewGridData = new GridData();
		contextViewGridData.horizontalSpan = 2;
		contextViewGridData.minimumHeight = 0;
		contextViewGridData.verticalIndent = 0;
		contextViewGridData.horizontalAlignment = GridData.FILL;
		contextViewGridData.verticalAlignment = GridData.BEGINNING;
		contextViewGridData.grabExcessHorizontalSpace = true;
		contextViewGridData.grabExcessVerticalSpace = true;
		contextView.setLayoutData(contextViewGridData);

		contextViewLayout = new GridLayout(2, false);
		contextViewLayout.marginHeight = 4;
		contextViewLayout.marginWidth = 4;
		contextView.setLayout(contextViewLayout);	
		new Composite(contextView, SWT.NONE); //dummy component
		contextView.setExpandVertical(true);
		contextView.setExpandHorizontal(false);

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
	
	public void updateDetailPane(Integer synsetID, Integer schemaID, Integer elementID){
		detailSID = synsetID;
		detailScID = schemaID;
		detailEID = elementID;
		if(folder2.getSelectionIndex() == 0 && !evidenceSID.equals(synsetID)) updateEvidence(synsetID, schemaID, elementID);
		else if(folder2.getSelectionIndex() == 1 && !closeMatchSID.equals(synsetID)) updateCloseMatch(synsetID, schemaID, elementID);
		else if(folder2.getSelectionIndex() == 2 && !contextSID.equals(synsetID)) updateContext(synsetID, schemaID, elementID);	
	}	
	
	public void updateEvidence(Integer synsetID, Integer schemaID, Integer elementID){
//		System.err.println("updating detail pane");
		EvidenceView.dispose();
		EvidenceView = new Composite(evidenceCanvas, SWT.NONE);
		EvidenceView.setLayoutData(EvidenceViewGridData);
		EvidenceView.setLayout(EvidenceViewLayout);	

		evidenceSID = synsetID;
		Term term = vocab.getTerms()[vocab.getTermIndex(synsetID)];
		synsetLabel1.setText(term.getName());

		AssociatedElement[][] allElements = new AssociatedElement[schemaIDs.length][];
		ArrayList<AssociatedElement> assElements = new ArrayList<AssociatedElement>();
		int elementCount = 0;
		TableItem showMeR = null;
		TableColumn showMeC = null;
		//int first = -1;
		                  
		//loop through once to get all terms
		for(int j = 0; j < schemaIDs.length; j++){			
			allElements[j] = term.getAssociatedElements(schemas[j].getId()); 
			for(int i = 0; i < allElements[j].length; i++) {
				assElements.add(allElements[j][i]);				
			}
			elementCount += allElements[j].length;
		//	if(allElements[j].length > 0 && first < 0) { first = j;}
		}
		//if(elementCount < 2) {return;}

		Table EvidenceTable = new Table(EvidenceView, SWT.BORDER | SWT.HIDE_SELECTION | SWT.NO_FOCUS | SWT.NONE);
		EvidenceTable.setData("name", "evidenceTable");
		EvidenceTable.setHeaderVisible(true);
		EvidenceTable.setLinesVisible(true);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 0;
		gridData.minimumHeight = 0;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		EvidenceTable.setLayoutData(gridData);
		
		// creating one column for the status and the vocabulary
		TableColumn tempC;
		tempC = new TableColumn(EvidenceTable, SWT.NONE);
//		tempC.setText("Term");
		tempC.setMoveable(false);		
		//tempC.setWidth(gc.textExtent(allElements[first][0].getName()).x + 20);
		tempC.setData("uid",new Integer(-204));
		//tempC.addListener(SWT.Selection, sortAlphabeticallyListener);
		tempC.setToolTipText("Matched Schema Term");
		// creating one column for each element
		for (int i = 0; i < allElements.length; i++) {
			for(int j = 0; j < allElements[i].length; j++) {				
				tempC = new TableColumn(EvidenceTable, SWT.NONE);
				tempC.setText(allElements[i][j].getName());
				tempC.setData("sid",allElements[i][j].getSchemaID());
				tempC.setData("eid",allElements[i][j].getElementID());
				tempC.setWidth(50);
				//tempC.setWidth((folder2.getBounds().width - (gc.textExtent(allElements[first][0].getName()).x + 20))/elementCount);
				tempC.setMoveable(true);
				String schema = "";
				for(int k = 0; k < schemas.length; k++){
					if(schemas[k].getId().equals(allElements[i][j].getSchemaID())){
						schema = schemas[k].getName();
					}
				}
			      // tooltip
				  String tooltipText = allElements[i][j].getName();
				  tooltipText += "\nSchema - " + schema;
				  tooltipText += "\n";//Description: ";
				  tooltipText +=  WordUtils.wrap(allElements[i][j].getDescription(),60,"\n",true);
				tempC.setToolTipText(tooltipText);
				if(showMeC == null || (allElements[i][j].getSchemaID().equals(schemaID) && allElements[i][j].getElementID().equals(elementID))) {
					showMeC = tempC;
				}
			}
		}		
		
		
		ArrayList<MappingCell> mappingCellList = OpenIIManager.getAssociatedMappingCells(vocab.getProjectID(),assElements);
		//for each schema
		for(int j = 0; j < schemaIDs.length; j++){
			
//			Label schemaLabel = new Label(EvidenceView, SWT.NONE);
//			schemaLabel.setText(schemas[j].getName());
//			schemaLabel.setToolTipText(schemas[j].getDescription());
			
					
			for(int i = 0; i < allElements[j].length; i++) {			
			//for each term
				TableItem newItem = new TableItem(EvidenceTable, SWT.NONE, EvidenceTable.getItemCount());
				newItem.setText(0, allElements[j][i].getName());
				if(showMeR == null || (allElements[j][i].getSchemaID().equals(schemaID) && allElements[j][i].getElementID().equals(elementID))) {
					showMeR = newItem;
				}

				
				
				//add matching info here
				// for each mapping, we need to find any cells that map to the
				// current element
/*				ArrayList<MappingCell> mappingCellList = new ArrayList<MappingCell>();
				for (Mapping m : mappings.values()) {
					MappingInfoExt mix = new MappingInfoExt(m, OpenIIManager.getMappingCells(m.getId()));
					AssociatedElementHash aeh = mix.getMappingCells();
					for ( MappingCell mc : aeh.get(allElements[j][i].getElementID()) )
						if ( !mappingCellList.contains(mc))
							mappingCellList.add(mc); 
				}	*/
				int count = 0;
				for (int l = 0; l < allElements.length; l++) {
					for (int n = 0; n < allElements[l].length; n++) {
						count++;
						for (int k = 0; k < mappingCellList.size(); k++) {
							MappingCell mc = mappingCellList.get(k); 
							Mapping m = mappings.get(mc.getMappingId());
							Integer outputId = mc.getOutput();
							Integer otherSchemaId = (allElements[j][i].getSchemaID().equals(m.getSourceId())) ? m.getTargetId() : m.getSourceId();
							if(otherSchemaId.equals(allElements[j][i].getSchemaID())){
								break;
							}
							int otherSIndex = 0;
							for(; otherSIndex < schemaIDs.length; otherSIndex++){
								if(schemaIDs[otherSIndex].equals(otherSchemaId)) {
									break;
								}
							}
	
							for (Integer inputId : mc.getElementInputIDs()) {
								Integer otherElementId = (outputId.equals(allElements[j][i].getElementID())) ? inputId : outputId;
								if(otherElementId.equals(EvidenceTable.getColumn(count).getData("eid")) && otherSchemaId.equals(EvidenceTable.getColumn(count).getData("sid")) ) {
	//System.out.println("otherElementId = " + otherElementId + "otherSchemaId = " + otherSchemaId + " score = " + mc.getScore().toString());
									//								SchemaElement otherElement = schemaInfos[otherSIndex].getElement(otherElementId);
									newItem.setText(count, mc.getScore().toString());
									if(mc.getScore() >= .85) {
										newItem.setBackground(count,green);
									} else if(mc.getScore() >= .7) {
										newItem.setBackground(count,green);
									} else if(mc.getScore() >= .4) {
										newItem.setBackground(count,yellow);
									} else if(mc.getScore() >= .2) {
										newItem.setBackground(count,red);
									} else {
										newItem.setBackground(count,red);
									}
								}
								
							}
						
						}
					}
				}
				
				
				
			    // tooltip
			    String tooltipText = allElements[j][i].getName();
			    tooltipText += "\nSchema - " + schemas[j].getName();
			    tooltipText += "\n";//Description: ";
			    tooltipText +=  WordUtils.wrap(allElements[j][i].getDescription(),60,"\n",true);
				newItem.setData("popup",tooltipText);
			}
		}
				
		if(showMeR != null) EvidenceTable.showItem(showMeR);
		if(showMeC != null) EvidenceTable.showColumn(showMeC);
		if(showMeR != null) EvidenceTable.getColumn(0).setWidth(gc.textExtent(showMeR.getText()).x + 20);
		
		EvidenceTable.addListener(SWT.MouseHover, new Listener() {
			public void handleEvent(Event e) {
					activeTable = (Table)(e.widget);
		        	// Identify the selected row
					TableItem item = ((Table)(e.widget)).getItem(new Point(e.x,e.y));
				    if (activeTable.getColumn(0).getWidth() < e.x || item == null)
				    {
				    	activeTable.setToolTipText(null);
				    } else if(item.getData("popup") != null) {
				    	activeTable.setToolTipText((String)item.getData("popup"));
				    }
		        }

		});
		
		EvidenceTable.addListener(SWT.Resize, new Listener() {	
			public void handleEvent(Event e) {
				Table table = ((Table)(e.widget));
				if(table.getColumnCount() > 1) {
					int size = 1+(table.getBounds().width - table.getColumn(0).getWidth())/(table.getColumnCount()-1);
					for(int i = 1; i < table.getColumnCount(); i++)
					{
						table.getColumn(i).setWidth(size);
					}
				}
			}
		});

		
/* These workarounds don't work
		EvidenceTable.addListener(SWT.EraseItem, new Listener() {
			public void handleEvent(Event event) {
				event.detail &= ~SWT.HOT;	
				if((event.detail & SWT.SELECTED) != 0) {
					event.detail &= ~SWT.SELECTED;					
				}						
				int value = 0;
				Table theTable = (Table)(event.widget);
				theTable.deselectAll();
				for(int i = 1; i < theTable.getColumnCount(); i++)
				{
					value = Integer.parseInt(theTable.getSelection()[0].getText(i));
					if(value >= .85) {
						theTable.getSelection()[0].setBackground(i,green);
					} else if(value >= .7) {
						theTable.getSelection()[0].setBackground(i,green);
					} else if(value >= .4) {
						theTable.getSelection()[0].setBackground(i,yellow);
					} else if(value >= .2) {
						theTable.getSelection()[0].setBackground(i,red);
					} else {
						theTable.getSelection()[0].setBackground(i,red);
					}
				}
			}
		});		

		EvidenceTable.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent event) {
				int value = 0;
				Table theTable = (Table)(event.widget);
				theTable.deselectAll();
				for(int i = 1; i < theTable.getColumnCount(); i++)
				{
					value = Integer.parseInt(theTable.getSelection()[0].getText(i));
					if(value >= .85) {
						theTable.getSelection()[0].setBackground(i,green);
					} else if(value >= .7) {
						theTable.getSelection()[0].setBackground(i,green);
					} else if(value >= .4) {
						theTable.getSelection()[0].setBackground(i,yellow);
					} else if(value >= .2) {
						theTable.getSelection()[0].setBackground(i,red);
					} else {
						theTable.getSelection()[0].setBackground(i,red);
					}
				}
            }

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				int value = 0;
				Table theTable = (Table)(event.widget);
				theTable.deselectAll();
				for(int i = 1; i < theTable.getColumnCount(); i++)
				{
					value = Integer.parseInt(theTable.getSelection()[0].getText(i));
					if(value >= .85) {
						theTable.getSelection()[0].setBackground(i,green);
					} else if(value >= .7) {
						theTable.getSelection()[0].setBackground(i,green);
					} else if(value >= .4) {
						theTable.getSelection()[0].setBackground(i,yellow);
					} else if(value >= .2) {
						theTable.getSelection()[0].setBackground(i,red);
					} else {
						theTable.getSelection()[0].setBackground(i,red);
					}
				}
			}

		});
	*/	
		
		evidenceCanvas.layout(true);
	
	}
	
	public void updateCloseMatch(Integer synsetID, Integer schemaID, Integer elementID){
//		System.err.println("updating detail pane");
		closeMatchView.dispose();
		closeMatchView = new Composite(closeMatchCanvas, SWT.NONE);
		closeMatchView.setLayoutData(closeMatchViewGridData);
		closeMatchView.setLayout(closeMatchViewLayout);	

		Label TBD = new Label(closeMatchView, SWT.NONE);
		TBD.setText("TBD");

		
		Term term = vocab.getTerms()[vocab.getTermIndex(synsetID)];
		synsetLabel2.setText(term.getName());
		closeMatchSID = synsetID;

		AssociatedElement[][] allElements = new AssociatedElement[schemaIDs.length][];
		ArrayList<AssociatedElement> assElements = new ArrayList<AssociatedElement>();
		int elementCount = 0;
		TableItem showMeR = null;
		TableColumn showMeC = null;
		//int first = -1;
		                  
		//loop through once to get all terms
		for(int j = 0; j < schemaIDs.length; j++){			
			allElements[j] = term.getAssociatedElements(schemas[j].getId()); 
			for(int i = 0; i < allElements[j].length; i++) {
				assElements.add(allElements[j][i]);				
			}
			elementCount += allElements[j].length;
		//	if(allElements[j].length > 0 && first < 0) { first = j;}
		}
		//if(elementCount < 2) {return;}

		Table EvidenceTable = new Table(EvidenceView, SWT.BORDER | SWT.HIDE_SELECTION | SWT.NO_FOCUS | SWT.NONE);
		EvidenceTable.setData("name", "evidenceTable");
		EvidenceTable.setHeaderVisible(true);
		EvidenceTable.setLinesVisible(true);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 0;
		gridData.minimumHeight = 0;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		EvidenceTable.setLayoutData(gridData);
		
		// creating one column for the status and the vocabulary
		TableColumn tempC;
		tempC = new TableColumn(EvidenceTable, SWT.NONE);
//		tempC.setText("Term");
		tempC.setMoveable(false);		
		//tempC.setWidth(gc.textExtent(allElements[first][0].getName()).x + 20);
		tempC.setData("uid",new Integer(-204));
		//tempC.addListener(SWT.Selection, sortAlphabeticallyListener);
		tempC.setToolTipText("Matched Schema Term");
		// creating one column for each element
		for (int i = 0; i < allElements.length; i++) {
			for(int j = 0; j < allElements[i].length; j++) {				
				tempC = new TableColumn(EvidenceTable, SWT.NONE);
				tempC.setText(allElements[i][j].getName());
				tempC.setData("sid",allElements[i][j].getSchemaID());
				tempC.setData("eid",allElements[i][j].getElementID());
				tempC.setWidth(50);
				//tempC.setWidth((folder2.getBounds().width - (gc.textExtent(allElements[first][0].getName()).x + 20))/elementCount);
				tempC.setMoveable(true);
				String schema = "";
				for(int k = 0; k < schemas.length; k++){
					if(schemas[k].getId().equals(allElements[i][j].getSchemaID())){
						schema = schemas[k].getName();
					}
				}
			      // tooltip
				  String tooltipText = allElements[i][j].getName();
				  tooltipText += "\nSchema - " + schema;
				  tooltipText += "\n";//Description: ";
				  tooltipText +=  WordUtils.wrap(allElements[i][j].getDescription(),60,"\n",true);
				tempC.setToolTipText(tooltipText);
				if(showMeC == null || (allElements[i][j].getSchemaID().equals(schemaID) && allElements[i][j].getElementID().equals(elementID))) {
					showMeC = tempC;
				}
			}
		}		
		
		
		/*
		ArrayList<MappingCell> mappingCellList = OpenIIManager.getAssociatedMappingCells(vocab.getProjectID(),assElements);
		ArrayList<String> drawnRows = new ArrayList<String>();
		//add terms  already in
		for(int i = 0; i < assElements.size(); i++){
			drawnRows.add(assElements.get(i).getElementID() + "_" + assElements.get(i).getSchemaID());
			
		}
		for(int j = 0; j < mappingCellList.size(); j++){
			MappingCell mc = mappingCellList.get(j);
			AssociatedElement workElement = null;
			Integer outputId = mc.getOutput();
			Integer otherElementId = outputId;
			for (Integer inputId : mc.getElementInputIDs()) {
				otherElementId = (drawnRows.contains(outputId) ? inputId : outputId);			
			}
			
			if(!drawnRows.contains(otherElementId + "_" + )){

			}
			drawnRows.add(workElement);				
		}
	
							for (Integer inputId : mc.getElementInputIDs()) {
								Integer otherElementId = (outputId.equals(allElements[j][i].getElementID())) ? inputId : outputId;
								if(otherElementId.equals(EvidenceTable.getColumn(count).getData("eid")) && otherSchemaId.equals(EvidenceTable.getColumn(count).getData("sid")) ) {
	//System.out.println("otherElementId = " + otherElementId + "otherSchemaId = " + otherSchemaId + " score = " + mc.getScore().toString());
									//								SchemaElement otherElement = schemaInfos[otherSIndex].getElement(otherElementId);
									newItem.setText(count, mc.getScore().toString());
									if(mc.getScore() >= .85) {
										newItem.setBackground(count,green);
									} else if(mc.getScore() >= .7) {
										newItem.setBackground(count,green);
									} else if(mc.getScore() >= .4) {
										newItem.setBackground(count,yellow);
									} else if(mc.getScore() >= .2) {
										newItem.setBackground(count,red);
									} else {
										newItem.setBackground(count,red);
									}
								}
								
							}
						
						}
					}
				}
				
				
				
			    // tooltip
			    String tooltipText = allElements[j][i].getName();
			    tooltipText += "\nSchema - " + schemas[j].getName();
			    tooltipText += "\n";//Description: ";
			    tooltipText +=  WordUtils.wrap(allElements[j][i].getDescription(),60,"\n",true);
				newItem.setData("popup",tooltipText);
			}
		}
				
		EvidenceTable.showItem(showMeR);
		EvidenceTable.showColumn(showMeC);
		EvidenceTable.getColumn(0).setWidth(gc.textExtent(showMeR.getText()).x + 20);
		
		EvidenceTable.addListener(SWT.MouseHover, new Listener() {
			public void handleEvent(Event e) {
					activeTable = (Table)(e.widget);
		        	// Identify the selected row
					TableItem item = ((Table)(e.widget)).getItem(new Point(e.x,e.y));
				    if (activeTable.getColumn(0).getWidth() < e.x || item == null)
				    {
				    	activeTable.setToolTipText(null);
				    } else if(item.getData("popup") != null) {
				    	activeTable.setToolTipText((String)item.getData("popup"));
				    }
		        }

		});
		
		EvidenceTable.addListener(SWT.Resize, new Listener() {	
			public void handleEvent(Event e) {
				Table table = ((Table)(e.widget));
				int size = 1+(table.getBounds().width - table.getColumn(0).getWidth())/(table.getColumnCount()-1);
				for(int i = 1; i < table.getColumnCount(); i++)
				{
					table.getColumn(i).setWidth(size);
				}
			}
		});

		*/
		
		closeMatchCanvas.layout(true);
		
	}
	
	public void updateContext(Integer synsetID, Integer schemaID, Integer elementID){
//		System.err.println("updating detail pane");
		Composite tempComp = (Composite)contextView.getChildren()[0];
		if(tempComp != null && !tempComp.isDisposed())
		{
			tempComp.dispose();
		}
		//contextView.setLayoutData(contextViewGridData);
		
		//contextView.setLayout(contextViewLayout);	
//		contextView.setLayout(new GridLayout(1, false));
//		System.err.println("minwidth = " + contextCanvas.getBounds().width);
//		System.err.println("minheight = " + contextCanvas.getBounds().height);
//		if(!contextView.isDisposed() && !contextCanvas.isDisposed()){
//			contextView.setMinHeight(contextCanvas.getBounds().height);}
//		contextView.setMinWidth(contextCanvas.getBounds().width);
		
		tempComp = new Composite(contextView, SWT.NONE);
		contextView.setContent(tempComp);
		tempComp.setSize(contextCanvas.getBounds().height*3, contextCanvas.getBounds().width);

		tempComp.setLayout(new GridLayout(1, false));
		tempComp.setLayoutData(contextViewGridData);

		Term term = vocab.getTerms()[vocab.getTermIndex(synsetID)];
		synsetLabel3.setText(term.getName());
		contextSID = synsetID;

		AssociatedElement[][] allElements = new AssociatedElement[schemaIDs.length][];
		int elementCount = 0;
		Composite showMeR = null;
		                  
		//loop through once to get all terms
		for(int j = 0; j < schemaIDs.length; j++){			
			allElements[j] = term.getAssociatedElements(schemas[j].getId()); 
			elementCount += allElements[j].length;
		}

		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;

		ArrayList<SchemaElement> parents = null;
		//for each schema
		for(int j = 0; j < schemaIDs.length; j++){
		
					
			for(int i = 0; i < allElements[j].length; i++) {			
			//for each term
				Composite termDetails = new Composite(tempComp, SWT.NONE);
				termDetails.setLayoutData(gridData);
				termDetails.setLayout(new GridLayout(1,true));
				termDetails.setBackground(white);
				HierarchicalSchemaInfo hsi = new HierarchicalSchemaInfo(schemaInfos[j], schemaModels[j]);
				Label name = new Label(termDetails, SWT.NONE);
				name.setText("  " + allElements[j][i].getName());
				name.setBackground(white);

				Label schemaname = new Label(termDetails, SWT.NONE);
				schemaname.setText("  Schema - " + schemas[j].getName());
				schemaname.setBackground(white);

				Composite contextC = new Composite(termDetails,SWT.NONE);
				contextC.setBackground(white);
				contextC.setLayout(new GridLayout(3,false));
				Label context = new Label(contextC, SWT.NONE);
				context.setText("Context - ");
				context.setBackground(white);
				
				parents = hsi.getAncestorElements(allElements[j][i].getElementID());
				int k = 0;
				String text = "";
				for(; k < parents.size(); k++) {
					text = "";
					for(int l = 0; l < k; l++) {
						text += "     ";
					}
					if(k > 0) {
						text += "                  ";
						contextC = new Composite(termDetails,SWT.NONE);
						contextC.setBackground(white);
						contextC.setLayout(new GridLayout(3,false));
						Label spacerC = new Label(contextC, SWT.NONE);
						spacerC.setBackground(white);
						spacerC.setText(text);
					}
					Label iLabel = new Label(contextC, SWT.NONE);
					Label pLabel = new Label(contextC, SWT.NONE);
					pLabel.setBackground(white);
					SchemaElement parentElement = parents.get(k);
System.err.println("name = " + parentElement.getName());
					Image img =  SchemaElementImage;
					if(parentElement instanceof DomainValue) img =  DomainValueImage;
					else if(parentElement instanceof Attribute) img =  AttributeImage;
					else if(parentElement instanceof Containment) img =  ContainmentImage;
					else if(parentElement instanceof Relationship) img =  RelationshipImage;
					iLabel.setImage(img);
					pLabel.setText(parentElement.getName());
				}
				text = "";
				for(int l = 0; l < k; l++) {
					text += "     ";
				}
				if(k > 0) {
					text += "                  ";
					contextC = new Composite(termDetails,SWT.NONE);
					contextC.setBackground(white);
					contextC.setLayout(new GridLayout(3,false));
					Label spacerC = new Label(contextC, SWT.NONE);
					spacerC.setText(text);
					spacerC.setBackground(white);
				}
				Label iLabel = new Label(contextC, SWT.NONE);
				Label pLabel = new Label(contextC, SWT.NONE);
				SchemaElement selfElement = hsi.getType(hsi, allElements[j][i].getElementID());
				pLabel.setText(allElements[j][i].getName());
				pLabel.setAlignment(SWT.BEGINNING);
				pLabel.setBackground(white);
				Image img =  SchemaElementImage;
				if(selfElement instanceof DomainValue) img =  DomainValueImage;
				else if(selfElement instanceof Attribute) img =  AttributeImage;
				else if(selfElement instanceof Containment) img =  ContainmentImage;
				else if(selfElement instanceof Relationship) img =  RelationshipImage;
				iLabel.setImage(img);
				
				Label descLabel = new Label(termDetails, SWT.NONE);
				descLabel.setText("  Description - " + allElements[j][i].getDescription());
				descLabel.setBackground(white);

				if(showMeR == null || (allElements[j][i].getSchemaID().equals(schemaID) && allElements[j][i].getElementID().equals(elementID))) {
					showMeR = termDetails;
				}
			}
		}

		//show item
//		contextView.layout(true);
		contextView.setSize(contextCanvas.getBounds().height, contextCanvas.getBounds().width);
		contextCanvas.layout(true);
		
	}
	
	public void updateTables(Integer vocabID) {
		//find in workspace
		TableItem[] workspaceItems = workspaceTable.getItems();
		for(int i = 0; i < workspaceItems.length; i++) {
			if(workspaceItems[i].getData("uid").equals(vocabID)){
				populateRow(workspaceItems[i], showTextWorkspace);
				if(checkStatus.containsKey(vocabID)) {
					workspaceItems[i].setImage(0, CheckIcon);
				}
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
	
	public void renameSynset(Table theTable) {
		activeTable = theTable;
		if(activeTable.getSelectionCount() >= 1){
			Term theTerm = vocab.getTerms()[(vocab.getTermIndex((Integer)(activeTable.getSelection()[0].getData("uid"))))];
			
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
					if(activeTable.getSelectionCount() == 1){
						Term theTerm = vocab.getTerms()[(vocab.getTermIndex((Integer)(activeTable.getSelection()[0].getData("uid"))))];
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
						if(activeTable.getSelectionCount() == 1){
							Term theTerm = vocab.getTerms()[(vocab.getTermIndex((Integer)(activeTable.getSelection()[0].getData("uid"))))];
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
	public void descSynset (Table theTable) {
		activeTable = theTable;
		if(activeTable.getSelectionCount() == 1){
			Term theTerm = vocab.getTerms()[(vocab.getTermIndex((Integer)(activeTable.getSelection()[0].getData("uid"))))];
				

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
					if(activeTable.getSelectionCount() == 1){
						Term theTerm = vocab.getTerms()[(vocab.getTermIndex((Integer)(activeTable.getSelection()[0].getData("uid"))))];
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
						if(activeTable.getSelectionCount() == 1){
							Term theTerm = vocab.getTerms()[(vocab.getTermIndex((Integer)(activeTable.getSelection()[0].getData("uid"))))];
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
	
	public void deleteSynset(Table theTable) {
		activeTable = theTable;
		if(theTable.getSelectionCount() >= 1){
			TableItem[] rows = theTable.getSelection();
			String name = "selected Synsets";
			if(theTable.getSelectionCount() == 1) {
				for(int i = 0; i< theTable.getColumnCount(); i++){
					if(theTable.getColumn(i).getData("uid").equals(new Integer(-201))){
						name = "\"" + rows[0].getText(i) + "\"";
					}
				}
			}
			dialog = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			GridLayout dialogLayout = new GridLayout(2, true);
			dialog.setLayout(dialogLayout);
			dialog.setText("Delete Synset");
			dialog.setSize(200, 120);
			dialog.setLocation(newSynset.toDisplay(10, 10));
			Label nameLabel = new Label(dialog, SWT.NONE);
			nameLabel.setText("This will permanently delete " + name + " from the Vocabulary.");
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
					TableItem items[] = activeTable.getSelection();
					for(int i = 0; i < items.length; i++){
						Integer id = (Integer)items[i].getData("uid");
						removeFromWorkspace(id);									
						vocab.removeTerm(id);
					}
					tableview.setItemCount(tableview.getItemCount()-items.length);
					tableview.clearAll(); 
					saveVocab.setBackground(red);
					saveVocab.setImage(needsaveIcon);									
					dialog.close();
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

	
	
	
	private void addListners() {
		
		
		viewsParent.addListener(SWT.Dispose, new Listener() {
			public void handleEvent(Event event) {
				//event.doit = false; SWT bugged
		  		if(saveVocab.getBackground().equals(red)){  			
					dialog = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
					GridLayout dialogLayout = new GridLayout(2, true);
					dialog.setLayout(dialogLayout);
					dialog.setText("Exiting Unity");
					dialog.setSize(200, 120);
					dialog.setLocation(viewsParent.toDisplay(viewsParent.getBounds().x + (viewsParent.getBounds().width/2), viewsParent.getBounds().y + (viewsParent.getBounds().height/2)));
					Label nameLabel = new Label(dialog, SWT.NONE);
					nameLabel.setText("Vocabulary has been changed since last save");
					GridData gridData = new GridData();
					gridData.horizontalSpan = 2;
					gridData.horizontalAlignment = GridData.FILL;
					gridData.verticalAlignment = GridData.FILL;
					gridData.grabExcessHorizontalSpace = true;
					gridData.grabExcessVerticalSpace = false;
					nameLabel.setLayoutData(gridData);
					Label nameLabel2 = new Label(dialog, SWT.NONE);
					nameLabel2.setText("Would you like to save?");
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
							VocabularyTerms oldVocab = vocab;
							vocab = OpenIIManager.saveVocabularyTerms(vocab);
							//go through each item and update stuff if ID changed
							Integer oldID = 0;
							Integer newID = 0;
							for(int i = 0; i < vocab.getTerms().length;i++)
							{
								oldID = oldVocab.getTerms()[i].getId();
								newID = vocab.getTerms()[i].getId();
								if(maxSafeID <= newID) maxSafeID = newID+1;

								if(newID != oldID) {
									if(checkStatus.containsKey(oldID)) {
										Annotation anno = checkStatus.remove(oldID);
										anno.setElementID(newID);
										checkStatus.put(newID, anno);
									}
									if(draggedRow == oldID)
									{
										draggedRow = newID;
									}
									if(evidenceSID == oldID)
									{
										evidenceSID = newID;
									}
									if(closeMatchSID == oldID)
									{
										closeMatchSID = newID;
									}
									if(contextSID == oldID)
									{
										contextSID = newID;
									}
									if(detailSID == oldID)
									{
										detailSID = newID;
									}
								}
							}
							OpenIIManager.clearAnnotations(vocab.getProjectID(), "checked");
							ArrayList<Annotation> annoList = new ArrayList<Annotation>(checkStatus.values());
							OpenIIManager.setAnnotations(annoList);
							dialog.close();
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
		
		MouseListener clickListener  = new MouseListener() {
			int EDITABLECOLUMN = -1;
			public void mouseDoubleClick(MouseEvent e) { }
			public void mouseUp(MouseEvent e) {
			}
			public void mouseDown(MouseEvent e)  {
		        if((e.stateMask & SWT.CTRL) > 0)
		        {
			          System.out.println("ctrl pressed");
		      	  interfaceState = DND.DROP_COPY;
		        } else {
			          System.out.println("ctrl released");
		        	interfaceState = DND.DROP_MOVE;
		        }
				activeTable = (Table)(e.widget);

			    // Clean up any previous temporary controls
			    Control oldEditor = editor.getEditor();
			    if (oldEditor != null)
			    	oldEditor.dispose();

		        //System.out.println("widget = " + e.widget);
		        if(e.button == 1 && e.widget instanceof Table && ((Table)(e.widget)).getSelectionCount() > 0){

		        	// Identify the selected row
					selectedItem = ((Table)(e.widget)).getItem(new Point(e.x,e.y));
			        //System.out.println("selectedItem = " + selectedItem);
				    if (selectedItem == null)
				    	return;
				    
					//System.out.println("theTable.getData(\"name\") = " + (String)(e.widget.getData("name")));
					if(((String)(e.widget.getData("name"))).equals("workspaceTable") || (((String)(e.widget.getData("name"))).equals("tableview")))
					{				
						Table theTable = ((Table)(e.widget));				
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
							dragElement = null;
							AssociatedElement aElements[] = vocab.getTerms()[vocab.getTermIndex(draggedRow)].getAssociatedElements(draggedCol);
							for(int i = 0; i < aElements.length; i++){
								xoff = xoff - gc.textExtent(aElements[i].getName() + ", ").x;
								if(xoff <= 0){
									dragElement = aElements[i];
									break;
								}
								if(i == aElements.length - 1){
									dragElement = aElements[i];
									break;									
								}
							}
						}
						if(dragElement != null) {
							updateDetailPane(draggedRow, draggedCol, dragElement.getElementID());
						} else {
							updateDetailPane(draggedRow, draggedCol, new Integer(-1));							
						}
				      
				      //System.err.println("button " + e.button + " was pressed.");
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
								if(checkStatus.containsKey(draggedRow)) { 
									checkStatus.remove(draggedRow);
								} else {
									checkStatus.put(draggedRow,new Annotation(draggedRow,vocab.getProjectID(),"checked","true"));									
								}
								
							  //						  if(not checked) {
							  //System.out.println("setting image for " + EDITABLECOLUMN);
//							  	selectedItem.setImage(EDITABLECOLUMN,CheckIcon);						  
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
		
		Listener menuListener = new Listener() {
			int EDITABLECOLUMN = -1;
			public void handleEvent(Event e) {
//System.out.println("in menulistener");
			    Menu oldmenu = workspaceTable.getMenu();
			    if (oldmenu != null)
			    	oldmenu.dispose();
			    oldmenu = tableview.getMenu();
			    if (oldmenu != null)
			    	oldmenu.dispose();
			    
				Table theTable = ((Table)e.widget);
				activeTable = (Table)(e.widget);

				Point pt = display.map(null, theTable, new Point(e.x, e.y));

				// Identify the selected row
				selectedItem = theTable.getItem(pt);
				//System.out.println("selectedItem = " + selectedItem);
//				if (selectedItem == null) //fake to get column widths
//					selectedItem = theTable.get



				
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
				
				draggedCol = (Integer)(theTable.getColumn(EDITABLECOLUMN).getData("uid"));								
				Menu popupMenu = new Menu(theTable);				
				
				if(pt.y < theTable.getHeaderHeight())
				{
					if(draggedCol == -202) {
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

 						
 						if (draggedCol != -201) {

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
					
					draggedRow = (Integer)(selectedItem.getData("uid"));

					if(draggedCol == -202) {
					    MenuItem checkItem = new MenuItem(popupMenu, SWT.CASCADE);
					    checkItem.setImage(CheckIcon);
					    checkItem.setText("Check");
					    MenuItem uncheckItem = new MenuItem(popupMenu, SWT.NONE);
					    uncheckItem.setImage(checkRemoveIcon);
					    uncheckItem.setText("Uncheck");
						
					} else if (draggedCol == -201) {
						if(activeTable.getSelectionCount() < 2) {
						    MenuItem RenameItem = new MenuItem(popupMenu, SWT.CASCADE);
						    RenameItem.setImage(renameIcon);
						    RenameItem.setText("Rename Synset");
						    RenameItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									renameSynset(activeTable);
								}
							});								    							    
						    
						    MenuItem changeDescItem = new MenuItem(popupMenu, SWT.NONE);
						    changeDescItem.setImage(changeDescIcon);
						    changeDescItem.setText("Edit Description");
						    changeDescItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									descSynset(activeTable);
								}
							});								    
						}
					    
					    if(theTable.getData("name").equals("workspaceTable")) {
					    	MenuItem removeItem = new MenuItem(popupMenu, SWT.NONE);
					    	removeItem.setImage(remove2Icon);
					    	removeItem.setText("Remove from Workspace");
					    	removeItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									removeFromWorkspace(workspaceTable.getSelectionIndex()+1);
								} 
							});				
					    
					    } else {
					    	
					    	
					    	MenuItem addItem = new MenuItem(popupMenu, SWT.NONE);
					    	addItem.setImage(insertIcon);
					    	addItem.setText("Add to Workspace");
					    	addItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									TableItem[] items = activeTable.getSelection();
									String[] ids = new String[items.length];
									for(int i = 0; i< items.length; i++)
									{
										ids[i] = "" + (Integer)items[i].getData("uid");
									}
									addToWorkspace(ids);
								}
							});				
					    }
					    MenuItem deleteItem = new MenuItem(popupMenu, SWT.NONE);
					    deleteItem.setImage(deleteSynsetIcon);
					    deleteItem.setText("Permanently Delete Synset");
				    	deleteItem.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {
								deleteSynset(activeTable);
							}
						});	

					    
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
								folder2.setSelection(0);
								updateDetailPane(draggedRow, draggedCol, null);
							}
						});	
					    
					    MenuItem closeMatchItem = new MenuItem(popupMenu, SWT.NONE);
					    closeMatchItem.setImage(closeMatchIcon);
					    closeMatchItem.setText("Show Close Matches");
					    closeMatchItem.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {								
								folder2.setSelection(1);
								updateDetailPane(draggedRow, draggedCol, null);
							}
						});	
					    
					    MenuItem contextItem = new MenuItem(popupMenu, SWT.NONE);
					    contextItem.setImage(contextIcon);
					    contextItem.setText("Show Context");
					    contextItem.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {								
								folder2.setSelection(2);
								updateDetailPane(draggedRow, draggedCol, null);
							}
						});	
					    
					} else {
												
						System.err.println("xoff = " + xoff);
						System.err.println("pt.x = " + pt.x);
						dragElement = null;
						AssociatedElement aElements[] = vocab.getTerms()[vocab.getTermIndex(draggedRow)].getAssociatedElements(draggedCol);
						for(int i = 0; i < aElements.length; i++){
							xoff = xoff + gc.textExtent(aElements[i].getName() + ", ").x;
							if(xoff >= pt.x){
								dragElement = aElements[i];
								break;
							}
							if(i == aElements.length - 1){
								dragElement = aElements[i];
								break;									
							}
						}
						System.err.println("xoff after = " + xoff);

						if(dragElement != null) {
							MenuItem cutItem = new MenuItem(popupMenu, SWT.CASCADE);
							cutItem.setImage(moveIcon);
							cutItem.setText("Cut " + dragElement.getName());
							cutItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									copyElementToClipboard(draggedCol,dragElement);
									deleteTerm(draggedRow, dragElement);
								}
							});	
						    MenuItem copyItem = new MenuItem(popupMenu, SWT.NONE);
						    copyItem.setImage(copyIcon);
						    copyItem.setText("Copy " + dragElement.getName());
						    copyItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									copyElementToClipboard(draggedCol,dragElement);
								}
							});	
						}
						if(draggedCol.equals(copiedSchema) && copiedElement != null) {
							MenuItem pasteItem = new MenuItem(popupMenu, SWT.CASCADE);
							pasteItem.setImage(pasteIcon);
							pasteItem.setText("Paste " + copiedElement.getName());
							pasteItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									addTerm(draggedRow, copiedElement);
								}
							});	
						}
						if(dragElement != null) {
						    MenuItem deleteItem = new MenuItem(popupMenu, SWT.NONE);
						    deleteItem.setImage(deleteIcon);
						    deleteItem.setText("Delete " + dragElement.getName());
						    deleteItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									deleteTerm(draggedRow, dragElement);
								}
							});	
						
						    new MenuItem(popupMenu, SWT.SEPARATOR); 
						    MenuItem treeItem = new MenuItem(popupMenu, SWT.NONE);
						    treeItem.setImage(treeViewIcon);
						    treeItem.setText("Show in Tree");
						    treeItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									schemaSelector.select(EDITABLECOLUMN-2);
									schemaSelector.setToolTipText("Displayed Schema is " + schemas[EDITABLECOLUMN-2].getName());
									treeview.setSchema(schemaInfos[EDITABLECOLUMN-2],schemaModels[EDITABLECOLUMN-2]);
									folder.setSelection(0);
								    treeview.searchFor(dragElement.getElementID());
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
									folder2.setSelection(0);
									updateDetailPane(draggedRow, draggedCol, dragElement.getElementID());
								}
							});	
						    
						    MenuItem closeMatchItem = new MenuItem(popupMenu, SWT.NONE);
						    closeMatchItem.setImage(closeMatchIcon);
						    closeMatchItem.setText("Show Close Matches");
						    closeMatchItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {								
									folder2.setSelection(1);
									updateDetailPane(draggedRow, draggedCol, dragElement.getElementID());
								}
							});	
						    
						    MenuItem contextItem = new MenuItem(popupMenu, SWT.NONE);
						    contextItem.setImage(contextIcon);
						    contextItem.setText("Show Context");
						    contextItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {								
									folder2.setSelection(2);
									updateDetailPane(draggedRow, draggedCol, dragElement.getElementID());
								}
							});	
						}
					}
				}
			    theTable.setMenu(popupMenu);
			    theTable.getMenu().setVisible(true);																		
			}
		};
		
		Listener hoverListener  = new Listener() {
			int EDITABLECOLUMN = -1;
			public void handleEvent(Event e) {
					activeTable = (Table)(e.widget);
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
							AssociatedElement aElements[] = vocab.getTerms()[vocab.getTermIndex(row)].getAssociatedElements(col);
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
						  tooltipText += vocab.getTerms()[vocab.getTermIndex(row)].getName();
						  tooltipText += "\n";//Description: ";
						  tooltipText +=  WordUtils.wrap(vocab.getTerms()[vocab.getTermIndex(row)].getDescription(),60,"\n",true);
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

		};

			Listener scrollListener = new Listener() {
				public void handleEvent(Event e) {
					activeTable = (Table)(e.widget);
					//addlock panes
					//editor.getEditor().dispose();  doesn't do anything?  need to fix this bug,  will be masked by pane freezing
					}
		      };


		
		//		display.addFilter(SWT.MouseDown,mainListener);
				workspaceTable.addMouseListener(clickListener);
				workspaceTable.addListener(SWT.MenuDetect, menuListener);
				workspaceTable.addListener(SWT.MouseHover,hoverListener);
			if(workspaceTable.getVerticalBar() != null)
				workspaceTable.getVerticalBar().addListener(SWT.FocusIn, scrollListener);
			if(workspaceTable.getHorizontalBar() != null)
				workspaceTable.getHorizontalBar().addListener(SWT.FocusIn, scrollListener);
			
			
			
//			tableview.addListener(SWT.MouseDown,clickListener);
			tableview.addMouseListener(clickListener);
			tableview.addListener(SWT.MenuDetect, menuListener);
			tableview.addListener(SWT.MouseHover,hoverListener);			
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
			
			workspaceTable.addListener(SWT.Resize, new Listener() {	
				public void handleEvent(Event e) {
					int size = 1+(((Table)(e.widget)).getBounds().width - workspaceTable.getColumn(0).getWidth()- workspaceTable.getColumn(1).getWidth())/(workspaceTable.getColumnCount()-2);
					for(int i = 2; i < workspaceTable.getColumnCount(); i++)
					{
						workspaceTable.getColumn(i).setWidth(size);
					}
				}
			});
						
			folder2.addSelectionListener(new SelectionListener () {

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					updateDetailPane(detailSID, detailScID, detailEID);
				}

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					updateDetailPane(detailSID, detailScID, detailEID);
				}
				
			});			
	}
	
	public Integer getTreeSchemaID() {
		return schemas[schemaSelector.getSelectionIndex()].getId();
	}
	
	public void copyElementToClipboard(Integer schemaID, Integer ID, String name, String desc){
		copyElementToClipboard(schemaID, new AssociatedElement(schemaID,ID,name,desc));
	}
	
	public void copyElementToClipboard(Integer schemaID, AssociatedElement elem){
		copiedSchema = schemaID;
		copiedElement = elem;
	}
	
  	 protected void finalize()  {
//  		display.removeFilter(SWT.MouseDown,mainListener);
	 }
		
}
