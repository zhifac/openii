package org.mitre.openii.editors.search;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.editors.schemas.schema.SchemaView;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.search.RepositorySearchResult;
import org.mitre.schemastore.search.SearchManager;

/** Constructs the Search Viewer */
public class SearchView extends EditorPart
{
	/** Private class to transfer the search input to the viewer */
	static private class SearchInput implements IEditorInput
	{
		/** Stores the keyword */
		private String keyword = null;

		/** Stores the tag IDs */
		private ArrayList<Integer> tagIDs = null;
		
		/** Constructs the search input */
		private SearchInput(String keyword, ArrayList<Integer> tagIDs)
			{ this.keyword = keyword; this.tagIDs = tagIDs; }
		
		/** Returns the keyword */
		private String getKeyword()
			{ return keyword; }

		/** Returns the tag IDs */
		private ArrayList<Integer> getTagIDs()
			{ return tagIDs; }

		public boolean exists() { return true; }
		public ImageDescriptor getImageDescriptor() { return null; }
		public String getName() { return ""; }
		public IPersistableElement getPersistable() { return null; }
		public String getToolTipText() { return ""; }
		public Object getAdapter(@SuppressWarnings("rawtypes") Class arg0) { return null; }
	}
	
	/** Class for display a matched schema */
	private class ResultPane extends Composite implements MouseListener
	{
		/** Stores the schema being displayed */
		private Integer schemaID = null;
		
		/** Displays the schema label */
		private void displayLabel(String text)
		{			
			// Define the label font style
			FontData fontData = new FontData();
			fontData.setHeight(12);
			fontData.setStyle(SWT.BOLD);
			
			// Generate the label
			CLabel label = new CLabel(this, SWT.NONE);
			label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			label.setFont(new Font(Display.getCurrent(), fontData));
			label.setLeftMargin(6);
			label.setImage(OpenIIActivator.getImage("Schema.gif"));
			label.setText(text);
		}
		
		/** Displays the schema matches */
		private void displayMatches(SchemaInfo schema, RepositorySearchResult result)
		{
			// Construct the scroll pane
			ScrolledComposite scrollPane = new ScrolledComposite(this, SWT.V_SCROLL);
			scrollPane.setExpandHorizontal(true);
			scrollPane.setExpandVertical(true);
			GridLayout layout = new GridLayout(1,false);
			layout.marginHeight = 0; layout.marginWidth = 0;
			scrollPane.setLayout(layout);
			scrollPane.setLayoutData(new GridData(GridData.FILL_BOTH));
			
			// Display the matched schema elements
			TreeViewer resultViewer = new TreeViewer(scrollPane, SWT.SINGLE);
			resultViewer.getTree().setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			resultViewer.setContentProvider(new SearchResultContentProvider(result));
			resultViewer.setLabelProvider(new SearchResultLabelProvider(schema,getKeyword()));
			resultViewer.setInput("");

			// Resize the scroll pane to fit the results
			scrollPane.setContent(resultViewer.getTree());
		}
		
		/** Constructs the Result pane */
		private ResultPane(Composite parent, RepositorySearchResult result)
		{
			// Create the result pane
			super(parent, SWT.BORDER);
			setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			GridLayout layout = new GridLayout(1,false);
			layout.marginHeight = 0; layout.marginWidth = 0;
			layout.horizontalSpacing = 0; layout.verticalSpacing = 0;
			setLayout(layout);
			setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			addMouseListener(this);

			// Display the schema label and match panes
			schemaID = result.getSchemaID();
			SchemaInfo schema = OpenIIManager.getSchemaInfo(schemaID);
			displayLabel(schema.getSchema().getName());
			displayMatches(schema, result);
		}

		/** Launches the schema pane (with keyword search) */
		public void mouseDoubleClick(MouseEvent arg0)
			{ SchemaView.launchEditor(OpenIIManager.getSchema(schemaID),"\\b"+getKeyword()+"\\b"); }

		// Unused event listeners
		public void mouseDown(MouseEvent arg0) {}
		public void mouseUp(MouseEvent arg0) {}
	}
	
	/** Gets the keyword */
	private String getKeyword()
		{ return ((SearchInput)getEditorInput()).getKeyword(); }
	
	/** Initializes the Search Results Viewer */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException
	{
		setSite(site);
		setInput(input);
		setPartName("Search Results");
	}
	
	/** Displays the Search View */
	public void createPartControl(Composite parent)
	{
		// Get the keyword and tag IDs
		SearchInput searchInput = (SearchInput)getEditorInput();		
		String keyword = searchInput.getKeyword();
		ArrayList<Integer> tagIDs = searchInput.getTagIDs();
		
		// Run the search and display results
		try {
			HashMap<Integer, RepositorySearchResult> results = SearchManager.search(keyword, tagIDs, RepositoryManager.getClient());

			// Constructs the main pane
			Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
			pane.setLayout(new GridLayout(1,false));
			pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			// Display label indicating search term
			Composite labelPane = new Composite(pane, SWT.NONE);
			labelPane.setLayout(new GridLayout(1,false));
			Label label = new Label(labelPane, SWT.NONE);
			label.setText("Search Results for: " + keyword);
			
			// Construct the scroll pane
			ScrolledComposite scrollPane = new ScrolledComposite(pane, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
			scrollPane.setExpandHorizontal(true);
			scrollPane.setExpandVertical(true);
			scrollPane.setLayoutData(new GridData(GridData.FILL_BOTH));

			// Construct the results pane
		    Composite resultsPane = new Composite(scrollPane, SWT.NONE);
		    resultsPane.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			GridLayout layout = new GridLayout(1,false);
			layout.marginHeight = 0; layout.marginWidth = 0;
			layout.horizontalSpacing = 1; layout.verticalSpacing = 1;
			resultsPane.setLayout(layout);
			
			// Display the matched schemas
			for(RepositorySearchResult result : results.values())
				new ResultPane(resultsPane,result);

			// Resize the scroll pane to fit the results
			scrollPane.setContent(resultsPane);
		    scrollPane.setMinHeight(resultsPane.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		}
		catch(Exception e) { System.out.println("(E) SearchViewer: Failed to search for schemas by keyword"); }
	}
	
	// Default instantiations of editor functions
	public boolean isDirty() { return false; }
	public boolean isSaveAsAllowed() { return false; }	
	public void doSave(IProgressMonitor arg0) {}
	public void doSaveAs() {}
	public void setFocus() {}
	
	/** Launches the editor */
	static public void launchEditor(String keyword, ArrayList<Integer> tagIDs)
	{		
		try {
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			page.openEditor(new SearchInput(keyword, tagIDs),"SearchView");
		}
		catch(Exception e)
			{ System.err.println(e.getMessage()); e.printStackTrace(); }
	}
}