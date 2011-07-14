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
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Annotation;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;
import org.mitre.schemastore.model.terms.AssociatedElement;
import org.mitre.schemastore.model.terms.InvertedTermsByElement;
import org.mitre.schemastore.model.terms.Term;
import org.mitre.schemastore.model.terms.Terms;
import org.mitre.schemastore.model.terms.VocabularyTerms;

public class UnityCanvas extends Composite {
	private Integer[] schemaIDs;
	private Schema[] schemas;
	private SchemaInfo[] schemaInfos;
	private SchemaModel[] schemaModels;
	private VocabularyTerms vocab;
	private InvertedTermsByElement invertedVocab;
	private Composite viewsParent;
	private HashMap<Integer, Mapping> mappings = new HashMap<Integer, Mapping>();
    private HashMap<Integer, Annotation> checkStatus = new HashMap<Integer, Annotation>();
	private Font boldFont = new Font(Display.getDefault(), new FontData("Arial", 8, SWT.BOLD));
    private Canvas treeViewCanvas;
    private Canvas tableViewCanvas;
    private Canvas searchViewCanvas;
    private Canvas evidenceCanvas;
    private Canvas closeMatchCanvas;
    private Canvas contextCanvas;
    private TableEditor editor = null;//new TableEditor(workspaceTable);
	private CTabFolder searchFolder;
	private CTabFolder detailFolder;
    private Display display;
    private Image CheckIcon = OpenIIActivator.getImage("checkicon.png");
    private Image evidenceIcon = OpenIIActivator.getImage("footprint1.png");
    private Image closeMatchIcon = OpenIIActivator.getImage("footprint2.png");
    private Image contextIcon = OpenIIActivator.getImage("home.png");
    private Image searchIcon = OpenIIActivator.getImage("search.png");
    private Image tableIcon = OpenIIActivator.getImage("tableicon.png");
    private Image treeViewIcon = OpenIIActivator.getImage("treeview.png");
    private TreeView treeView;
    private TableView tableView;
    private SearchView searchView;
    private Workspace workspace;
    private EvidencePane evidencePane;
    private CloseMatchPane closeMatchPane;
    private ContextPane contextPane;

    
    public Integer maxSafeID = 0;
	public Integer draggedCol = null;
	public Integer draggedRow = null;
	public Table activeTable = null;
    public Integer detailSID = new Integer(-1);     
    public Integer detailScID = new Integer(-1);     
    public Integer detailEID = new Integer(-1);     
    public int eventDetail = 0;
    public int dragID = 0;
    public int interfaceState = DND.DROP_MOVE;
    public AssociatedElement dragElement;
    public AssociatedElement copiedElement;
    public Integer copiedSchema;
    public TableItem selectedItem;
    

    
	public UnityCanvas(Composite parent, int style, VocabularyTerms vocabulary) {
		super(parent, style );
		viewsParent = parent;
		vocab = vocabulary;
		invertedVocab = new InvertedTermsByElement(vocab);

		ArrayList<Mapping> rawmappings = OpenIIManager.getMappings(vocabulary.getProjectID());
		Iterator<Mapping> itr = rawmappings.iterator();
		while(itr.hasNext()) {
			Mapping m = itr.next();
			if(m != null) {
				mappings.put(m.getId(),m);			
			}
		}
		
		ArrayList<Annotation> allAnnotations = OpenIIManager.getAnnotationsByGroup(vocabulary.getProjectID(), "checked");
		Iterator<Annotation> itr2 = allAnnotations.iterator();
		while(itr2.hasNext()) {
			Annotation a = itr2.next();
			if(a != null) {
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
		
	    Canvas main_sash = new Canvas(this, SWT.NONE);		
		
		this.setBackground(null);
		FillLayout layout = new FillLayout();
		layout.type = SWT.VERTICAL;
		this.setLayout(layout);
		
		GridLayout msLayout = new GridLayout(1, true);
		msLayout.marginWidth = 0;
		msLayout.marginHeight = 0;
		main_sash.setLayout(msLayout);

		workspace = new Workspace(this);
		workspace.createWorkspace(main_sash);
		
	    editor = new TableEditor(workspace.getTable());

		SashForm bottom_sash = new SashForm(main_sash, SWT.HORIZONTAL);
		searchFolder = new CTabFolder(bottom_sash, SWT.TOP);
		detailFolder = new CTabFolder(bottom_sash, SWT.TOP);
		bottom_sash.setWeights(new int[] {1, 1});
	      
		
//		SashForm main_sash = new SashForm(this, SWT.HORIZONTAL);		
	
//		CTabFolder folder = new CTabFolder(main_sash, SWT.TOP);
		searchFolder.setUnselectedCloseVisible(false);
		searchFolder.setSimple(false);

		CTabItem item = new CTabItem(searchFolder, SWT.NONE);
		item.setImage(tableIcon);
		item.setText("Table");
		item.setToolTipText("Table View");
		tableViewCanvas = new Canvas(searchFolder, SWT.EMBEDDED);
		item.setControl(tableViewCanvas);
		tableView = new TableView(this);
		tableView.createTableView(tableViewCanvas);
		
		item = new CTabItem(searchFolder, SWT.NONE);
		item.setImage(treeViewIcon);
		item.setText("Tree");
		item.setToolTipText("Tree View");
		treeViewCanvas = new Canvas(searchFolder, SWT.EMBEDDED);
		item.setControl(treeViewCanvas);
		treeView = new TreeView(this);
		treeView.createTreeView(treeViewCanvas);
		
		item = new CTabItem(searchFolder, SWT.NONE);
		item.setImage(searchIcon);
		item.setText("Search");
		item.setToolTipText("Advanced Search");
		searchViewCanvas = new Canvas(searchFolder, SWT.EMBEDDED);
		item.setControl(searchViewCanvas);
		searchView = new SearchView(this);
		searchView.createSearchView(searchViewCanvas);
		
		searchFolder.setSelection(0); 
		searchFolder.setBackground(null); 
		searchFolder.setBorderVisible(true);
		searchFolder.setSelectionBackground(display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT)); 

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

		
		detailFolder.setUnselectedCloseVisible(false);
		detailFolder.setSimple(false);
		gridData = new GridData();
		gridData.widthHint = 500;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		detailFolder.setLayoutData(gridData);

		item = new CTabItem(detailFolder, SWT.NONE);
		item.setImage(evidenceIcon);
		item.setText("Evidence");
		item.setToolTipText("Evidence");
		evidenceCanvas = new Canvas(detailFolder, SWT.EMBEDDED);
		item.setControl(evidenceCanvas);
		evidencePane = new EvidencePane(this);
		evidencePane.createEvidencePane(evidenceCanvas);
		
		item = new CTabItem(detailFolder, SWT.NONE);
		item.setImage(closeMatchIcon);
		item.setText("Close Matches");
		item.setToolTipText("Close Matches");
		closeMatchCanvas = new Canvas(detailFolder, SWT.EMBEDDED);
		item.setControl(closeMatchCanvas);
		closeMatchPane = new CloseMatchPane(this);
		closeMatchPane.createCloseMatchPane(closeMatchCanvas);

		item = new CTabItem(detailFolder, SWT.NONE);
		item.setImage(contextIcon);
		item.setText("Context");
		item.setToolTipText("Term Context");
		contextCanvas = new Canvas(detailFolder, SWT.EMBEDDED);
		item.setControl(contextCanvas);
		contextPane = new ContextPane(this);
		contextPane.createContextPane(contextCanvas);

		detailFolder.setSelection(0); 
		detailFolder.setBackground(null); 
		detailFolder.setSelectionBackground(display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT)); 
		detailFolder.setBorderVisible(true);

		addListners();
		
	}

	private void addListners() {
		
		viewsParent.addListener(SWT.Selection, new DisposeListener(this));
		
	
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
		
						
		detailFolder.addSelectionListener(new SelectionListener () {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				updateDetailPane(detailSID, detailScID, detailEID);
			}

			public void widgetSelected(SelectionEvent arg0) {
				updateDetailPane(detailSID, detailScID, detailEID);
			}
			
		});			
	}
	

	
	/** Add a term to the specified synset */
	public void addTerm(Integer synsetID, AssociatedElement element)
	{
		Term term = vocab.getTerm(synsetID);
		invertedVocab.removeTerm(term);
		term.addAssociatedElement(element);
		invertedVocab.addTerm(term);
		updateTables(new Integer[] {synsetID});
	}

	/** Delete a term from the specified synset */
	public void deleteTerm(Integer synsetID, AssociatedElement element)
	{
		Term term = vocab.getTerm(synsetID);
		invertedVocab.removeTerm(term);
		term.removeAssociatedElement(element);
		invertedVocab.addTerm(term);
		updateTables(new Integer[] {synsetID});
	}

	public void checkAll(boolean check) {
		if(check) { //check all terms
			for (Term term : vocab.getTerms()) {
				if(!getCheckStatus().containsKey(term.getId())) {
					getCheckStatus().put(term.getId(),new Annotation(term.getId(),vocab.getProjectID(),"checked","true"));									
				}
			}		
		} else { //uncheck all terms
			getCheckStatus().clear();
		}
		updateTables();
	}


	public void populateRow(TableItem item, boolean showTerms)
	{
	    Term term = vocab.getTerm((Integer)item.getData("uid"));
		String termName = term.getName();	
		AssociatedElement[] elements = term.getElements();
		if(checkStatus.containsKey(item.getData("uid"))) {
			item.setImage(0, CheckIcon);
		} else {
			item.setImage(0, null);
		}
		for (int j = 0; j < this.getWorkspace().getTable().getColumnCount(); j++) {
			int ID = ((Integer)this.getWorkspace().getTable().getColumn(j).getData("uid")).intValue();
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
		if(detailFolder.getSelectionIndex() == 0 && !evidencePane.evidenceSID.equals(synsetID)) evidencePane.updateEvidence(synsetID, schemaID, elementID);
		else if(detailFolder.getSelectionIndex() == 1 && !closeMatchPane.closeMatchSID.equals(synsetID)) closeMatchPane.updateCloseMatch(synsetID, schemaID, elementID);
		else if(detailFolder.getSelectionIndex() == 2 && !contextPane.contextSID.equals(synsetID)) contextPane.updateContext(synsetID, schemaID, elementID);	
	}	
	


	//add synsets with term matching ID to workspace
	public void addAssociatedSynsets(Integer elementID)
	{
		Integer schemaID = treeView.getTreeSchemaID();
		Terms terms = invertedVocab.getTerms(schemaID,elementID);
		ArrayList<Integer> termIDs = new ArrayList<Integer>();
		for(Term term : terms.getTerms())
			termIDs.add(term.getId());
		this.getWorkspace().addToWorkspace(termIDs);
	}

	public void updateTables () {
		TableItem[] items = getWorkspace().getTable().getItems();
		Integer[] ids = new Integer[items.length];
		for(int i = 0; i < ids.length; i++) {
			ids[i] = (Integer)items[i].getData("uid");
		}
		updateTables(ids);
	}
	
	public void updateTables(Integer[] vocabID) {
		//find in workspace
		for(Integer id : vocabID) {
			TableItem[] workspaceItems = this.getWorkspace().getTable().getItems();
			for(int i = 0; i < workspaceItems.length; i++) {
				if(workspaceItems[i].getData("uid").equals(id)){
					populateRow(workspaceItems[i], this.getWorkspace().showTextWorkspace);
					break;
				}
			}		
		}

		tableView.resetTableView();
	
		//update need save
		needSave(true);

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
	           	
  	 public Integer[] getSchemaIDs() {
 		return schemaIDs;
 	 }

 	 public Schema[] getSchemas() {
 		return schemas;
 	 }
   	 
  	 public SchemaInfo[] getSchemaInfos() {
 		return schemaInfos;
 	 }

 	 public SchemaModel[] getSchemaModels() {
 		return schemaModels;
 	 }
   	 
 	 public TableEditor getEditor() {
 		 return editor;
 	 }
 	 
 	 public VocabularyTerms getVocabulary() {
 		 return vocab;
 	 }
	 
 	 public void setVocabulary(VocabularyTerms vocabIn) {
 		 vocab = vocabIn;
 	 }
	 
 	 public InvertedTermsByElement getInvertedVocab() {
 	 	return invertedVocab;
	}
 	 
  	public TreeView getTreeView() {
		  return treeView;
	}
	
 	public TableView getTableView() {
		  return tableView;
	}
	
 	public Workspace getWorkspace() {
		  return workspace;
	}

 	public EvidencePane getEvidencePane() {
		  return evidencePane;
	}
	
 	public CloseMatchPane getCloseMatchPane() {
		  return closeMatchPane;
	}
	
 	public ContextPane getContextPane() {
		  return contextPane;
	}
	
 	public HashMap<Integer, Annotation> getCheckStatus() {
 	    return checkStatus;
 	}
 	
 	public HashMap<Integer, Mapping> getMappings() {
 	    return mappings;
 	}
 	
 	public CTabFolder getDetailFolder() {
 		return detailFolder;
 	}
 	
 	public CTabFolder getSearchFolder() {
 		return searchFolder;
 	}
 	
 	public void needSave(boolean need) {
 		this.getWorkspace().needSave(need);
 	}

 	public boolean needSave() {
 		return this.getWorkspace().needSave();
 	}
 	
 	public Point getCenter(){
 		return viewsParent.toDisplay(viewsParent.getBounds().x + (viewsParent.getBounds().width/2), viewsParent.getBounds().y + (viewsParent.getBounds().height/2));
 	}
}
