package org.mitre.openii.widgets.schemaTree;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;
import org.mitre.schemastore.search.SchemaSearchResult;
import org.mitre.schemastore.search.SearchManager;

/** Constructs a Schema Tree */
public class SchemaTree extends Composite implements ISelectionChangedListener, KeyListener
{	
	/** Stores the hierarchical schema being displayed */
	private HierarchicalSchemaInfo schema = null;
	
	/** Stores the search results */
	private HashMap<Integer,SchemaSearchResult> results = new HashMap<Integer,SchemaSearchResult>();
	
	// Stores the various dialog fields
	private SchemaModelSelector modelSelector = null;
	private Text searchField = null;
	private TreeViewer schemaViewer = null;
	
	/** Generate the menu pane */
	private void generateMenuPane(Composite parent)
	{
		// Construct the menu pane
		Composite menuPane = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2,false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		menuPane.setLayout(layout);
		menuPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
		// Construct the model pane
		modelSelector = new SchemaModelSelector(menuPane, schema.getModel());
		modelSelector.addSelectionChangedListener(this);
		
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
		schemaViewer.setContentProvider(new SchemaElementContentProvider(schema));
		schemaViewer.setLabelProvider(new SchemaElementLabelProvider(this));
		schemaViewer.setInput("");
		
		// Add the tree popup menu
		SchemaMenuManager menuManager = new SchemaMenuManager(schemaViewer);
		Menu menu = menuManager.createContextMenu(schemaViewer.getControl());
		schemaViewer.getControl().setMenu(menu);
		
		// Add tool tips to the tree viewer
		ColumnViewerToolTipSupport.enableFor(schemaViewer);
	}

	/** Constructs the Schema Tree */
	public SchemaTree(Composite parent, SchemaInfo schema)
		{ this(parent, schema, null); }
	
	/** Constructs the Schema Tree */
	public SchemaTree(Composite parent, SchemaInfo schema, SchemaModel model)
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
		generateMenuPane(this);
		generateTreePane(this);

		// Expand out the tree
		schemaViewer.getTree().getItem(0).setExpanded(true);
		schemaViewer.refresh();
	}
	
	/** Returns the schema associated with this view */
	public HierarchicalSchemaInfo getSchema()
		{ return schema; }

	/** Returns the currently selected element */
	public Integer getSelectedElement()
		{ return ((SchemaElement)schemaViewer.getSelection()).getId(); }		
	
	/** Sets the selected element */
	public void setSelectedElement(Integer elementID)
	{
		for(TreePath path : schemaViewer.getExpandedTreePaths())
			System.out.println(path.getLastSegment());
		
		// Expand out to the selected element
		ArrayList<ArrayList<SchemaElement>> paths = schema.getPaths(elementID);
		if(paths.size()>0)
			for(SchemaElement element : paths.get(0))
				schemaViewer.expandToLevel(element,1);
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
		searchField.setText(keyword);
		
		// Highlight all matches in the schema
		results = SearchManager.search(keyword, schema);
		if(results.size()>0) schemaViewer.expandToLevel(2);
		for(Integer elementID : results.keySet())
			for(ArrayList<SchemaElement> path : schema.getPaths(elementID))
				for(SchemaElement element : path)
					schemaViewer.expandToLevel(element,1);					
		schemaViewer.refresh();
	}
	
	/** Handles changes to the schema model */
	public void selectionChanged(SelectionChangedEvent e)
	{
		schema.setModel(modelSelector.getSchemaModel());
		schemaViewer.refresh();
	}

	/** Runs the search query (when ENTER is pressed) */
	public void keyReleased(KeyEvent e)
		{ if(e.character==SWT.CR) searchFor(searchField.getText()); }
	
	// Unused event listeners
	public void keyPressed(KeyEvent e) {}
}