package org.mitre.openii.views.manager.dialogs.schemas;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.dialogs.DialogComponents;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;
import org.mitre.schemastore.model.Schema;

/** Constructs the Import Schema Dialog */
public class ImportSchemaDialog extends TitleAreaDialog implements ISelectionChangedListener, SelectionListener, ModifyListener
{
	// Stores the various dialog fields
	private ComboViewer importerList = null;
	private Text nameField = null;
	private Text authorField = null;
	private Text descriptionField = null;
	private Composite uriPane = null;
	private StackLayout uriLayout = null;
	private Label uriLabel = null;
	private Text fileField = null;
	private Text uriField = null;
	
	/** Keeps track of if the file is valid */
	private boolean validFile = false;
	
	/** Constructs the dialog */
	public ImportSchemaDialog(Shell parentShell)
		{ super(parentShell); }	

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
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		pane.setLayout(layout);
		
		// Construct a list of all importers that can be selected
		DialogComponents.createLabel(pane,"Importer");
		importerList = new ComboViewer(pane, SWT.NONE);
		for(SchemaImporter importer : new PorterManager(OpenIIManager.getConnection()).getSchemaImporters())
		{
			Integer uriType = importer.getURIType();
			if(uriType==SchemaImporter.FILE || uriType==SchemaImporter.ARCHIVE || uriType==SchemaImporter.URI)
				importerList.add(importer);
		}
		importerList.addSelectionChangedListener(this);
	}

	/** Creates the importer info pane */
	private void createInfoPane(Composite parent)
	{
		// Construct the pane for showing the info for the selected importer
		Group group = new Group(parent, SWT.NONE);
		group.setText("Properties");
		group.setLayout(new GridLayout(2,false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		nameField = DialogComponents.createTextField(group,"Name");
		authorField = DialogComponents.createTextField(group,"Author");
		descriptionField = DialogComponents.createTextField(group,"Description",4);

		// Generate the uri pane
		uriLabel = DialogComponents.createLabel(group, "File");
		uriPane = new Composite(group, SWT.NONE);
		uriPane.setLayout(uriLayout = new StackLayout());
		uriPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Create file field
		fileField = DialogComponents.createFile(uriPane,this);

		// Create uri field
		Composite uriFieldPane = new Composite(uriPane, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		uriFieldPane.setLayout(layout);
		uriField = DialogComponents.createText(uriFieldPane,null);
		
		// Add listeners to the fields to monitor for changes
		nameField.addModifyListener(this);
		authorField.addModifyListener(this);
		descriptionField.addModifyListener(this);
		fileField.addModifyListener(this);
		uriField.addModifyListener(this);
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
		createInfoPane(pane);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Import Schema Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);

		// Make the default importer selections
		importerList.getCombo().select(0);
		updateFields();
		fileField.setFocus();
		
		return control;
	}
	
	/** Handles the updating of the fields based on the selected importer */
	public void updateFields()
	{
		// Retrieve the selected importer
		SchemaImporter importer = (SchemaImporter)((StructuredSelection)importerList.getSelection()).getFirstElement();
		boolean archiveImporter = importer.getURIType()==SchemaImporter.ARCHIVE;
		boolean uriImporter = importer.getURIType()==SchemaImporter.URI;
		
		// Clear out the fields as needed
		if(archiveImporter)
			{ nameField.setText(""); authorField.setText(""); descriptionField.setText(""); }
		else if(authorField.getText().equals(""))
			authorField.setText(System.getProperty("user.name"));
		fileField.setText(""); uriField.setText("");
		
		// Disable fields as needed in case of archive exporter
		nameField.setEnabled(!archiveImporter);
		authorField.setEnabled(!archiveImporter);
		descriptionField.setEnabled(!archiveImporter);
		
		// Display file field or uri depending on importer type
		uriLabel.setText(uriImporter ? "URI: " : "File: ");
		uriLayout.topControl = uriPane.getChildren()[uriImporter?1:0];
		uriPane.layout();
		validFile=uriImporter;
	}	
	
	/** Handles changes to the selected importer */
	public void selectionChanged(SelectionChangedEvent e)
		{ updateFields(); }

	/** Handles the selection of a file to import from */
	public void widgetSelected(SelectionEvent e)
	{
		// Retrieve the selected importer
		SchemaImporter importer = (SchemaImporter)((StructuredSelection)importerList.getSelection()).getFirstElement();
		
		// Generate the list of extensions that are available
		ArrayList<String> extensions = new ArrayList<String>();
		for(String extension : importer.getFileTypes())
			extensions.add("*"+extension);
		
		// Create the dialog
		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
		dialog.setText("Import Schema");
		dialog.setFilterPath("C:/");
		dialog.setFilterNames(new String[] {importer.getName() + " " + importer.getFileTypes()});
		dialog.setFilterExtensions(extensions.toArray(new String[0]));
		
		// Launch the dialog to retrieve the specified file to load from
        String filename = dialog.open();
        if(filename != null) fileField.setText(filename);
	}

	/** Handles modifications to the various text fields */
	public void modifyText(ModifyEvent e)
	{   
		// Check the validity of the file field
		if(e.getSource().equals(fileField))
		{
			setErrorMessage(null);
			if(fileField.getText().length()>0)
			{
				validFile = new File(fileField.getText()).exists();
				setErrorMessage(validFile ? null : "A valid file must be selected");
			}
		}
		
		// Update name, author, and description info when archive file modified
		SchemaImporter importer = (SchemaImporter)((StructuredSelection)importerList.getSelection()).getFirstElement();
		if(e.getSource().equals(fileField) && importer.getURIType()==SchemaImporter.ARCHIVE)
			try {
				if(validFile)
				{
					Schema schema = importer.generateSchema(new File(fileField.getText()).toURI());
					nameField.setText(schema.getName());
					authorField.setText(schema.getAuthor());
					descriptionField.setText(schema.getDescription());
				}
				else { nameField.setText(""); authorField.setText(""); descriptionField.setText(""); }
			} catch(Exception e2) {}
		
		// Determine if the OK button should be activated
		boolean activate = nameField.getText().length()>0 && authorField.getText().length()>0 && descriptionField.getText().length()>0;
		activate &= importer.getURIType()==SchemaImporter.URI ? uriField.getText().length()>0 : fileField.getText().length()>0;
		getButton(IDialogConstants.OK_ID).setEnabled(activate && validFile);
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{
		try {
			SchemaImporter importer = (SchemaImporter)((StructuredSelection)importerList.getSelection()).getFirstElement();
			URI uri = importer.getURIType()==SchemaImporter.URI ? new URI(uriField.getText()) : new File(fileField.getText()).toURI();
			Integer schemaID = importer.importSchema(nameField.getText(), authorField.getText(), descriptionField.getText(), uri);
			if(schemaID!=null)
			{
				Schema schema = OpenIIManager.getConnection().getSchema(schemaID);
				OpenIIManager.fireSchemaAdded(schema); getShell().dispose();
			}
		}
		catch(Exception e2) {}
		setErrorMessage("Failed to import schema");
	}
	
	// Unused listener event
	public void widgetDefaultSelected(SelectionEvent e) {}
}