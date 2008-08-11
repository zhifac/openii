package org.openii.schemr.client.view;

import java.io.File;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;
import org.mitre.schemastore.model.Schema;
import org.openii.schemr.SchemaUtility;
import org.openii.schemr.client.Activator;
import org.openii.schemr.client.action.SearchAction;
import org.openii.schemr.client.model.MatchSummary;

public class SearchBoxView extends ViewPart implements SelectionListener {

	public static final String ID = "org.openii.schemr.client.view.searchBoxView";

	// TODO: set back to ""
	private static final String DEFAULT_FILE_PATH = "";

	private static final int SEARCH_BOX_WIDTH = 235;

	private Composite _parent;
	private Text _textBox;
	private Text _filePathText;
	private Button _uploadButton;
	private Button _clearFileButton;
	private Button _searchButton;

	private Composite _searchResultControl;
	private FormToolkit _toolkit;
	private ScrolledForm _form;
	
	// this is the "model" for now
	private MatchSummary [] _matchSummaries;


	@Override
	public void createPartControl(Composite parent) {
		_parent = parent;
		SashForm sashForm = new SashForm(_parent, SWT.VERTICAL);
//		sashForm.setOrientation(SWT.VERTICAL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		gridData.horizontalSpan = 2;
		sashForm.setLayoutData(gridData);
		buildSearchControl(sashForm);
		buildSearchResult(sashForm);
		sashForm.setWeights(new int[] { 1, 5 });
				
	}
	
	/**
	 * Build the top portion of the panel for entering a query
	 * 
	 */
	private void buildSearchControl(Composite parent) {
		
		Composite searchBoxControl = new Composite(parent, SWT.BORDER);
		GridLayout layout = new GridLayout(3, false);
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		searchBoxControl.setLayout(layout);

		// setup bold font
		Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);

		_textBox = new Text(searchBoxControl, SWT.SINGLE | SWT.BORDER);
		_textBox.setText("Example: human entity:zipcode");
		_textBox.setSelection(0, _textBox.getText().length());
		_textBox.setToolTipText("Enter search keywords, for example: africa attribute:name");
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false);
		data.horizontalSpan = 3;
		data.minimumWidth = SEARCH_BOX_WIDTH;
		_textBox.setLayoutData(data);

		_filePathText = new Text(searchBoxControl, SWT.SINGLE | SWT.BORDER);
		_filePathText.setText(DEFAULT_FILE_PATH);
		_filePathText.setToolTipText("Enter path for schema file");
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false);
		data.horizontalSpan = 2;
		data.minimumWidth = SEARCH_BOX_WIDTH-60;
		_filePathText.setLayoutData(data);

//		_clearFileButton = new Button(searchBoxControl, SWT.PUSH);
//		_clearFileButton.setText("X");
//		_clearFileButton.setEnabled(new File(_filePathText.getText()).isFile());
//		data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING, GridData.VERTICAL_ALIGN_BEGINNING, false, false);
//		_clearFileButton.setLayoutData(data);
//		_clearFileButton.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent event) {				
//				System.out.println("\nClear file button pressed");
//		        _filePathText.setText("");
//		        _clearFileButton.setEnabled(false);
//			}
//		});
				
		_uploadButton = new Button(searchBoxControl, SWT.PUSH);
		_uploadButton.setImage(Activator.getImageDescriptor("/icons/attach16.png").createImage());
		_uploadButton.setToolTipText("Launch file browser");
		data = new GridData(GridData.BEGINNING);
//				, GridData.VERTICAL_ALIGN_BEGINNING, true, false);
		_uploadButton.setLayoutData(data);

		_uploadButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {				
				System.out.println("\nUpload button pressed");
				FileDialog fd = new FileDialog(_parent.getShell(), SWT.OPEN);
		        fd.setText("Load");
		        fd.setFilterPath(System.getProperty("user.home"));
		        String [] filterExt = { "*.xsd", "*.sql", "*.ddl", "*.*" };
		        fd.setFilterExtensions(filterExt);
		        String selected = fd.open();
		        _filePathText.setText(selected);
		        _clearFileButton.setEnabled(true);
		        System.out.println(selected);				
			}
		});
		
		_searchButton = new Button(searchBoxControl, SWT.PUSH);
		_searchButton.setImage(Activator.getImageDescriptor("/icons/xmag16.png").createImage());
		_searchButton.setToolTipText("Search Schemr server at "+SchemaUtility.SCHEMA_STORE_URL);
		data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.horizontalSpan = 3;
		_searchButton.setLayoutData (data);
		_searchButton.addSelectionListener(this);

	}

	@Override
	public void setFocus() {
		// initialize focus in the search box
		_textBox.setFocus();
	}

	// SelectionListener
	public void widgetDefaultSelected(SelectionEvent se) {
		widgetSelected(se);
	}

	// SelectionListener
	public void widgetSelected(SelectionEvent se) {
		if (se.getSource() == _searchButton) {
			File f = null;
			String path = _filePathText.getText();
			if (path.length() > 0 && new File(path).exists()) {
				f = new File(path);
			}
			MatchSummary [] msarray = SearchAction.performSearch(_textBox.getText(), f, _parent.getShell());
			updateResults(msarray);
		} else {
			System.out.println("Unknown selection action by "+se.getSource());
		}
	}

	/**
	 * Build the bottom portion of the panel for displaying search results
	 * 
	 */
	private void buildSearchResult(Composite parent) {
		_searchResultControl = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		_searchResultControl.setLayout(layout);
		
		_matchSummaries = new MatchSummary [0];
		_toolkit = new FormToolkit(_parent.getDisplay());

		updateResults(_matchSummaries);

	}
		
	public void updateResults(MatchSummary [] searchResults) {
		if (_form != null) _form.dispose();

		_form = _toolkit.createScrolledForm(_searchResultControl);
		
		GridLayout layout = new GridLayout();
		_form.getBody().setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_FILL, true, true);
		_form.setLayoutData(data);
		_form.setText("Results:");
		_form.setToolTipText("From Schema Store server "+SchemaUtility.SCHEMA_STORE_URL);
		_form.setMinWidth(SEARCH_BOX_WIDTH-20);
		if (searchResults != null && searchResults.length > 0) {
			
			_matchSummaries = searchResults;
			
			for (MatchSummary ms : searchResults) {
				Schema s = ms.getSchema();
				String name = s.getName().trim();
				String tip = s.getAuthor()+": "+s.getDescription();
				if (tip.length() > 100) tip = tip.substring(0, 100);
				Hyperlink link = _toolkit.createHyperlink(_form.getBody(), name, SWT.WRAP);
				link.setText(name);
				link.setToolTipText(tip);
				link.setData(ms);
				link.addHyperlinkListener(new HyperlinkAdapter() {
					public void linkActivated(HyperlinkEvent e) {						
						Hyperlink link = (Hyperlink) e.getSource();
						MatchSummary ms = (MatchSummary) link.getData();
						
						SearchResultManager.getInstance().resultSelected(this, ms);
					}
				});	
				
			}
			

		} else {
			Label noResultsLabel = new Label(_form.getBody(), SWT.NULL);
			noResultsLabel.setText("No results found");
		}
	
		_searchResultControl.layout();
	}

}
