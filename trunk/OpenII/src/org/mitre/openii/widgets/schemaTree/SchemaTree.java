package org.mitre.openii.widgets.schemaTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.mitre.openii.application.OpenIIApplication;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.editors.help.AboutOpenIIEditor;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;
import org.mitre.schemastore.search.SchemaSearchResult;
import org.mitre.schemastore.search.SearchManager;

/** Constructs a Schema Tree */
public class SchemaTree extends Composite implements SelectionListener, ISelectionChangedListener, KeyListener
{	
	/** Stores the hierarchical schema being displayed */
	private HierarchicalSchemaInfo schema = null;
	private AboutOpenIIEditor faqEditor;

	/** Stores the search results */
	private HashMap<Integer,SchemaSearchResult> results = new HashMap<Integer,SchemaSearchResult>();

	// Stores the various dialog fields
	private SchemaModelSelector modelSelector = null;
	private Text searchField = null;
	private TreeViewer schemaViewer = null;
	private Button alphabetize = null;
	private Button showTypes = null;
	private Button showBaseSchemas = null;
	private Button faqs = null;
	private boolean doalphabetize = false;
	private boolean doshowTypes = false;
	private boolean doshowBaseSchemas = false;
	private boolean doshowCardinality  = false;
	private Button showCardinality = null;

	/** Generate the menu pane */
	private void generateMenuPane(Composite parent)
	{
		// Construct the menu pane
		Composite menuPane = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		menuPane.setLayout(layout);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		menuPane.setLayoutData(data);
		Composite modelPane = new Composite(menuPane, SWT.NONE);
		GridLayout modelLayout = new GridLayout(2,false);
		modelLayout.marginHeight=0;
		modelLayout.marginWidth = 0;
		modelPane.setLayout(layout);
		GridData data1 = new GridData();
		data1.grabExcessHorizontalSpace = true;
		modelPane.setLayoutData(data1);
		// Construct the model pane
		modelSelector = new SchemaModelSelector(modelPane, schema.getModel());
		modelSelector.addSelectionChangedListener(this);

		faqs = new Button(modelPane, SWT.NONE);
		faqs.setToolTipText("View Descriptions");
		faqs.setText("?");
      	faqs.addListener(SWT.Selection, new Listener() {

      		public void handleEvent(Event event) {
      			if (faqEditor == null  || faqEditor.isDisposed())
      			{
      				faqEditor = (AboutOpenIIEditor)EditorManager.launchEditor("AboutOpenIIEditor", null);
      			}
      			faqEditor.goToFaqSection(new Integer(3));
      			faqEditor.getSite().getPage().activate(faqEditor);

      		}
      	});
		// Construct the search pane
		Composite searchPane = new Composite(menuPane, SWT.NONE);
		searchPane.setLayout(new GridLayout(2,false));
		searchPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Construct the search bar
		searchField = BasicWidgets.createTextField(searchPane, "Search");
		searchField.addKeyListener(this);
	}

	/** Generate the tree pane */
	private void generateTreePane(Composite parent)
	{
		// Construct the tree pane
		Composite treePane = new Composite(parent, SWT.BORDER);
		GridLayout layout = new GridLayout(1,false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		treePane.setLayout(layout);
		treePane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Create the tree viewer
		schemaViewer = new TreeViewer(treePane, SWT.SINGLE);
		schemaViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Populate the tree viewer
		schemaViewer.setContentProvider(new SchemaElementContentProvider(this));
		schemaViewer.setLabelProvider(new SchemaElementLabelProvider(this));
		schemaViewer.setSorter(new SchemaElementSorter(this));


		// Add the tree popup menu
		SchemaMenuManager menuManager = new SchemaMenuManager(schemaViewer);
		Menu menu = menuManager.createContextMenu(schemaViewer.getControl());
		schemaViewer.getControl().setMenu(menu);

		// Add tool tips to the tree viewer
		ColumnViewerToolTipSupport.enableFor(schemaViewer);
	}

	/** Generate the option pane */
	private void generateOptionsPane(Composite parent)
	{
		// Construct the option pane
		Composite optionsPane = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3,false);
		layout.marginHeight = 0;
		layout.marginWidth = 5;
		optionsPane.setLayout(layout);
		optionsPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Construct the alphabetize option
		alphabetize = new Button(optionsPane, SWT.CHECK);
		alphabetize.setText("Alphabetize");
		alphabetize.addSelectionListener(this);

		// Construct the show base schemas option
		showTypes = new Button(optionsPane, SWT.CHECK);
		showTypes.setText("Show Types");
		showTypes.addSelectionListener(this);

		// Construct the show base schemas option
		showBaseSchemas = new Button(optionsPane, SWT.CHECK);
		showBaseSchemas.setText("Show Base Schemas");
		showBaseSchemas.addSelectionListener(this);

		//Construct the show cardinality option
		showCardinality = new Button(optionsPane, SWT.CHECK);
		showCardinality.setText("Show Cardinality");
		showCardinality.addSelectionListener(this);
	}

	/** Constructs the Schema Tree */
	public SchemaTree(Composite parent, SchemaInfo schema)
	{ this(parent, schema, null, false); }

	/** Constructs the Schema Tree */
	public SchemaTree(Composite parent, SchemaInfo schema, SchemaModel model)
	{ this(parent, schema, model, false); }

	/** Constructs the Schema Tree */
	public SchemaTree(Composite parent, SchemaInfo schema, SchemaModel model, boolean treeonly)
	{	

		super(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1,false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayout(layout);
		setLayoutData(new GridData(GridData.FILL_BOTH));

		// Retrieves the hierarchical schema
		this.schema = new HierarchicalSchemaInfo(schema,model);

		// Layout the menu pane and tree pane
		if(!treeonly) generateMenuPane(this);
		generateTreePane(this);
		if(!treeonly) generateOptionsPane(this);

		// Expand out the tree
		schemaViewer.setInput("");
		schemaViewer.getTree().getItem(0).setExpanded(true);
		schemaViewer.refresh();
	}

	/** Returns the schema associated with this view */
	public HierarchicalSchemaInfo getSchema()
	{ return schema; }

	/** Sets the schema associated with this view */
	public void setSchema(SchemaInfo schema, SchemaModel model)
	{ 
		this.schema = new HierarchicalSchemaInfo(schema,model);
		//schemaViewer.getTree().getItem(0).setExpanded(true);
		schemaViewer.refresh();
		schemaViewer.expandToLevel(2);
	}

	/** Sets the menu manager */
	public void setMenuManager(MenuManager manager)
	{ 
		Menu menu = manager.createContextMenu(schemaViewer.getControl());
		schemaViewer.getControl().setMenu(menu);
	}

	/** Indicates if the schema should be alphabetized */
	public boolean isAlphabetized()
	{ return doalphabetize; }

	/** Indicates if the schema should show types */
	public boolean showTypes()
	{ return doshowTypes; }

	/** Indicates if the schema should show base schemas */
	public boolean showBaseSchemas()
	{ return doshowBaseSchemas; }

	/** Indicates if the schema should show cardinality */
	public boolean showCardinality()
	{ return doshowCardinality; }


	/** Indicates if the schema should be alphabetized */
	public void doAlphabetized(boolean bool)
	{ doalphabetize = bool;
	schemaViewer.refresh(); 
	}


	/** Indicates if the schema should show types */
	public void doShowTypes(boolean bool)
	{ doshowTypes = bool;
	schemaViewer.refresh(); 
	}


	/** Indicates if the schema should show base schemas */
	public void doShowBaseSchemas(boolean bool)
	{ doshowBaseSchemas = bool; 
	schemaViewer.refresh(); 
	}

	/** Indicates if the schema should show base schemas */
	public void doShowCardinality(boolean bool)
	{ 
		if (doshowCardinality != bool){
			doshowCardinality = bool; 
			schemaViewer.refresh(); 
		}
	}

	/** Returns the currently selected element */

	public Integer getSelectedElement()
	{ 
		Object obj = schemaViewer.getSelection();
		return ((SchemaElementWrapper)schemaViewer.getSelection()).getSchemaElement().getId(); }		

	/** Sets the selected element */

	public void setSelectedElement(Integer elementID)
	{
		for(TreePath path : schemaViewer.getExpandedTreePaths())
			System.out.println(path.getLastSegment());

		// Expand out to the selected element

		TreeItem item = schemaViewer.getTree().getTopItem();

		for(ArrayList<SchemaElement> path : schema.getPaths(elementID)) {
			TreeItem[] currentLevel = item.getItems();

			for(SchemaElement element : path){
				for (TreeItem node : currentLevel) {
					SchemaElementWrapper wrap = (SchemaElementWrapper)node.getData();
					if (wrap!= null && wrap.getSchemaElement().getId().equals(element.getId())) {

						schemaViewer.expandToLevel(wrap,1);
						currentLevel = node.getItems();
						break;
					}
				}
			}
		}

	}

	/** Adds a double click listener to the schema tree */
	public void addDoubleClickListener(IDoubleClickListener listener)
	{ schemaViewer.addDoubleClickListener(listener); }

	/** Locks the schema model */
	public void lockModel()
	{ modelSelector.setEnabled(false); }

	/** Returns the search result for the specified schema element */
	SchemaSearchResult getSearchResult(Integer elementID)
	{ return results.get(elementID); }

	/** Searches for the specified keyword */
	public void searchFor(String keyword)
	{
		// Update the search field keyword
		if(searchField != null)searchField.setText(keyword);

		// Highlight all matches in the schema
		results = SearchManager.search(keyword, schema);
		if(results.size()>0) schemaViewer.expandToLevel(1);
		TreeItem item = schemaViewer.getTree().getTopItem();
		for(Integer elementID : results.keySet()){
			List<ArrayList<SchemaElement>> paths = schema.getPaths(elementID);
			if (!schema.shouldExpandAll() && paths.size()>10) {
				paths = paths.subList(0, 10);
			}
			for(ArrayList<SchemaElement> path : paths) {
				TreeItem[] currentLevel = item.getItems();

				for(SchemaElement element : path){
					for (TreeItem node : currentLevel) {

						SchemaElementWrapper wrap = (SchemaElementWrapper)node.getData();
						if (wrap!= null && wrap.getSchemaElement()!= null && element != null && wrap.getSchemaElement().getId().equals(element.getId())) {

							schemaViewer.expandToLevel(wrap,1);
							currentLevel = node.getItems();
							break;
						}
					}
				}
			}
		}
		schemaViewer.refresh();
	}

	/** Searches for the specified id */
	public void searchFor(Integer id)
	{
		// Highlight all matches in the schema
		results = SearchManager.search(id, schema);
		if(results.size()>0) schemaViewer.expandToLevel(1);
		TreeItem item = schemaViewer.getTree().getTopItem();
		for(Integer elementID : results.keySet()){
			for(ArrayList<SchemaElement> path : schema.getPaths(elementID)) {
				TreeItem[] currentLevel = item.getItems();

				for(SchemaElement element : path){
					for (TreeItem node : currentLevel) {
						
						SchemaElementWrapper wrap = (SchemaElementWrapper)node.getData();
						if (wrap!= null && wrap.getSchemaElement().getId().equals(element.getId())) {

							schemaViewer.expandToLevel(wrap,1);
							currentLevel = node.getItems();
							break;
						}
					}
				}
			}
		}
		schemaViewer.refresh();
	}


	/** Handles changes to the selected options */
	public void widgetSelected(SelectionEvent e)
	{ 
		doalphabetize = alphabetize.getSelection();
		doshowTypes = showTypes.getSelection();
		doshowBaseSchemas = showBaseSchemas.getSelection();
		doshowCardinality = showCardinality.getSelection();


		schemaViewer.refresh(); 
	}

	public TreeViewer getTreeViewer(){
		return schemaViewer;
	}


	/** Handles changes to the schema model */
	public void selectionChanged(SelectionChangedEvent e)
	{
		schema.setModel(modelSelector.getSchemaModel());
		if (!schema.shouldExpandAll()) {
			MessageDialog.openInformation(this.getShell(), "Large Schema and Complex View", "Due to the size of the schema and the number of levels and links with this view, \"Expand All\" has been disabled and search will not be exhaustive to reduce performance issues.");
		}
		schemaViewer.refresh();
	}

	/** Runs the search query (when ENTER is pressed) */
	public void keyReleased(KeyEvent e)
	{ if(e.character==SWT.CR) searchFor(searchField.getText()); }

	// Unused event listeners
	public void keyPressed(KeyEvent e) {}
	public void widgetDefaultSelected(SelectionEvent arg0) {}
}