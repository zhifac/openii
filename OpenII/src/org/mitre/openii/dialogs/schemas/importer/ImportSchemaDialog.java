package org.mitre.openii.views.manager.schemas.importer;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.URIField;
import org.mitre.openii.widgets.porters.ImporterSelector;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.porters.PorterManager.PorterType;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

/** Constructs the Import Schema Dialog */
public class ImportSchemaDialog extends TitleAreaDialog implements ISelectionChangedListener
{	
	// Stores the various dialog fields
	private ImporterSelector importerSelector = null;
	private SchemaInformationPane schemaInfoPane = null;
	private FileInformationPane fileSelectionPane = null;
	
	/** Constructs the dialog */
	public ImportSchemaDialog(Shell parentShell)
		{ super(parentShell);}	

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setText("Import Schema");
		setTitleImage(OpenIIActivator.getImage("ImportLarge.png"));
	}
	
	/** Creates the importer list */
	private void createImporterList(Composite parent)
	{
		// Construct the composite pane
		Composite pane = new Composite(parent, SWT.NONE);
		pane.setLayout(new GridLayout(2,false));
		
		// Construct a list of all importers that can be selected
		BasicWidgets.createLabel(pane,"Importer");
		importerSelector = new ImporterSelector(pane, PorterType.SCHEMA_IMPORTERS);
	}

	/** Creates the dialog area for the Import Schema Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Set the dialog title and message
		setTitle("Import Schema");
		setMessage("Import a schema from a file");
		
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
		// Generate the pane components
		createImporterList(pane);
		schemaInfoPane = new SchemaInformationPane(pane);
		fileSelectionPane = new FileInformationPane(pane);
		
		// Add listeners to the various panes
		importerSelector.addSelectionChangedListener(this);
		schemaInfoPane.addListener(new SchemaInfoListener());
		schemaInfoPane.addURIListener(new SchemaURIListener());
		fileSelectionPane.addListener(new FileSelectionListener());		
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Import Schema Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);
		schemaInfoPane.initializeSchemaInfo((SchemaImporter)importerSelector.getImporter());		
		return control;
	}
	
	/** Update button */
	private void updateButton()
	{
		boolean valid = schemaInfoPane.isValid();
		valid &= schemaInfoPane.getURIField().getMode().equals(URIField.URI) || fileSelectionPane.isValid();
		getButton(IDialogConstants.OK_ID).setEnabled(valid);		
	}
	
	/** Handles changes to the selected importer */
	public void selectionChanged(SelectionChangedEvent e)
	{
		SchemaImporter importer = importerSelector.getImporter();
		schemaInfoPane.initializeSchemaInfo(importer);
		updateButton();
	}
	
	/** Handles the actual import of the specified file(s) */
	protected void okPressed()
	{
		String errorMessage = null;
		
		// Retrieve the schema importer to be used
		SchemaImporter importer = importerSelector.getImporter();
		
		// Build the list of URIs to be imported
		ArrayList<URI> uris = new ArrayList<URI>();
		URIField uriField = schemaInfoPane.getURIField();
		if(uriField.getMode().equals(URIField.URI))
			try { uris.add(uriField.getURI()); } catch(Exception e) { errorMessage="Invalid URI"; }
		else for(File file : fileSelectionPane.getSelectedFiles())
			uris.add(file.toURI());
		
		// Cycle through all files to be imported
		for(URI uri : uris)
		{
			try {
				String name = schemaInfoPane.getName().equals("") ? new File(uri).getName() : schemaInfoPane.getName();
				Integer schemaID = importer.importSchema(name, schemaInfoPane.getAuthor(), schemaInfoPane.getDescription(), uri);
				if(schemaID!=null)
				{
					Schema schema = RepositoryManager.getClient().getSchema(schemaID);
					OpenIIManager.fireSchemaAdded(schema);
				}
			}
			catch(Exception e) { errorMessage = e.getMessage(); }
		}

		// Issue an error message if needed
		if(errorMessage!=null)
			setErrorMessage("Failed to import schemas. " + errorMessage);
		else getShell().dispose();
	}

	/** Class for listening to the file selection pane */
	private class FileSelectionListener implements ISelectionChangedListener
		{ public void selectionChanged(SelectionChangedEvent e) { updateButton(); } }
	
	/** Class for listening for modifications to the schema uri */
	private class SchemaURIListener implements ModifyListener
	{
		public void modifyText(ModifyEvent e)
		{
			// Retrieve the URI field
			URIField uriField = schemaInfoPane.getURIField();
			String uriString = uriField.getTextField().getText();
			
			// Check to see if a valid URI has been given
			setErrorMessage(null);
			if(uriString.length()>0)
				setErrorMessage(uriField.isValid() ? null : "A valid file must be selected");

			// Update name, author, and description info when file modified
			SchemaImporter importer = importerSelector.getImporter();
			try {
				File file = new File(uriString);
				if(!uriField.getMode().equals(URIField.DIRECTORY))
				{
					// Retrieve information about the schema
					Schema schema = null;
					if(uriField.isValid())
						schema = importer.getSchema(file.toURI());

					// Update schema information
					if(schema!=null)
						schemaInfoPane.setSchemaInfo(schema);
					else if(uriField.getMode().equals(URIField.FILE))
						schemaInfoPane.setName(file.getName().replaceAll("\\.[^\\.]*", ""));
				}
						
				// Update list of files
				fileSelectionPane.initializeFiles(file,importer);
			}
			catch(Exception e2) { setErrorMessage("Failed to retrieve schema info. " + e2.getMessage()); }

			// Updates the dialog button
			updateButton();
		}
	}
	
	/** Class for listening for modifications to the schema information */
	private class SchemaInfoListener implements ModifyListener
		{ public void modifyText(ModifyEvent e) { updateButton(); } }
}