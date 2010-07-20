package org.mitre.openii.widgets.schemaTree;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.search.Search;
import org.mitre.schemastore.search.SearchResult;

/** Constructs a Schema Tree */
public class SchemaTree extends Composite implements ISelectionChangedListener, KeyListener
{	
	/** Stores the hierarchical schema being displayed */
	private HierarchicalSchemaInfo schema = null;
	
	/** Stores the search results */
	private HashMap<Integer,SearchResult> searchResults = new HashMap<Integer,SearchResult>();
	
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
		modelSelector = new SchemaModelSelector(menuPane);
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
	public SchemaTree(Composite parent, Integer schemaID)
	{	
		super(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1,false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayout(layout);
		setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// Retrieves the hierarchical schema
		schema = new HierarchicalSchemaInfo(OpenIIManager.getSchemaInfo(schemaID));
		
		// Layout the menu pane and tree pane
		generateMenuPane(this);
		generateTreePane(this);

		// Expand out the tree
		schemaViewer.getTree().getItem(0).setExpanded(true);
		schemaViewer.refresh();
	}

	/** Returns the schema associated with this view */
	HierarchicalSchemaInfo getSchema()
		{ return schema; }
	
	/** Returns the search result for the specified schema element */
	SearchResult getSearchResult(Integer elementID)
		{ return searchResults.get(elementID); }
	
	/** Handles changes to the schema model */
	public void selectionChanged(SelectionChangedEvent e)
	{
		schema.setModel(modelSelector.getSchemaModel());
		schemaViewer.refresh();
	}

	/** Runs the search query (when ENTER is pressed) */
	public void keyReleased(KeyEvent e)
	{
		if(e.character==SWT.CR)
		{
			searchResults = Search.runQuery(searchField.getText(), schema);
			for(Integer elementID : searchResults.keySet())
				for(ArrayList<SchemaElement> path : schema.getPaths(elementID))
					for(SchemaElement element : path)
						schemaViewer.expandToLevel(element,1);					
			schemaViewer.refresh();
		}
	}
	
	// Unused event listeners
	public void keyPressed(KeyEvent e) {}
}