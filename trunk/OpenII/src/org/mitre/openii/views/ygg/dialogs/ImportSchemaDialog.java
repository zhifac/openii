package org.mitre.openii.views.ygg.dialogs;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.importers.Importer;
import org.mitre.schemastore.importers.ImporterManager;
import org.mitre.schemastore.model.Schema;

/** Constructs the Import Schema Dialog */
public class ImportSchemaDialog extends TitleAreaDialog implements ISelectionChangedListener, SelectionListener, ModifyListener
{
	// Stores the various dialog fields
	private ComboViewer importerList = null;
	private Text nameField = null;
	private Text authorField = null;
	private Text descriptionField = null;
	private Text fileField = null;
	
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
	private Control createImporterList(Composite parent)
	{
		// Construct the composite pane
		Composite pane = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		pane.setLayout(layout);
		
		// Construct a list of all importers that can be selected
		DialogComponents.createLabel(pane,"Importer");
		importerList = new ComboViewer(pane, SWT.NONE);
		for(Importer importer : new ImporterManager(OpenIIManager.getConnection()).getImporters(null))
			if(importer.getURIType().equals(Importer.FILE) || importer.getURIType().equals(Importer.ARCHIVE))
				importerList.add(importer);
		importerList.addSelectionChangedListener(this);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the importer info pane */
	private Control createInfoPane(Composite parent)
	{
		// Construct the pane for showing the info for the selected importer
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("Properties");
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		pane.setLayout(layout);
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		nameField = DialogComponents.createTextField(pane,"Name");
		authorField = DialogComponents.createTextField(pane,"Author");
		descriptionField = DialogComponents.createTextField(pane,"Description",4);
		fileField = DialogComponents.createFileField(pane,"File",this);
		
		// Add listeners to the fields to monitor for changes
		nameField.addModifyListener(this);
		authorField.addModifyListener(this);
		descriptionField.addModifyListener(this);
		fileField.addModifyListener(this);
		
		// Return the generated pane
		return pane;
	}
	
	/** Creates the contents for the Import Schema Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Set the dialog title and message
		setTitle("Import Schema");
		setMessage("Import a schema from a file");
		
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		pane.setLayout(layout);
	
		// Generate the pane components
		createImporterList(pane);
		createInfoPane(pane);

		// Make a default importer selection
		importerList.getCombo().select(0);
		updateFields();
		fileField.setFocus();
		
		// Return the generated pane
		return pane;
	}

	/** Handles the updating of the fields based on the selected importer */
	public void updateFields()
	{
		// Retrieve the selected importer
		Importer importer = (Importer)((StructuredSelection)importerList.getSelection()).getFirstElement();
		boolean archiveImporter = importer.getURIType().equals(Importer.ARCHIVE);
		
		// Clear out the fields as needed
		if(archiveImporter)
			{ nameField.setText(""); authorField.setText(""); descriptionField.setText(""); }
		else if(authorField.getText().equals(""))
			authorField.setText(System.getProperty("user.name"));
		fileField.setText("");
		
		// Disable fields as needed in case of archive exporter
		nameField.setEnabled(!archiveImporter);
		authorField.setEnabled(!archiveImporter);
		descriptionField.setEnabled(!archiveImporter);
	}	
	
	/** Handles changes to the selected importer */
	public void selectionChanged(SelectionChangedEvent e)
		{ updateFields(); }

	/** Handles the selection of a file to import from */
	public void widgetSelected(SelectionEvent e)
	{
		// Retrieve the selected importer
		Importer importer = (Importer)((StructuredSelection)importerList.getSelection()).getFirstElement();
		
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
		// Update name, author, and description info when archive file modified
		Importer importer = (Importer)((StructuredSelection)importerList.getSelection()).getFirstElement();
		if(e.getSource().equals(fileField) && importer.getURIType().equals(Importer.ARCHIVE))
			try {
				Schema schema = importer.generateSchema(new File(fileField.getText()).toURI());
				nameField.setText(schema.getName());
				authorField.setText(schema.getAuthor());
				descriptionField.setText(schema.getDescription());
			} catch(Exception e2) {}
		
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
	
		// Determine if the OK button should be activated
		boolean activate = nameField.getText().length()>0 && authorField.getText().length()>0 &&
						   descriptionField.getText().length()>0 && fileField.getText().length()>0;
		Button button = getButton(IDialogConstants.OK_ID);
		if(button!=null) button.setEnabled(activate && validFile);
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{
		try {
			Importer importer = (Importer)((StructuredSelection)importerList.getSelection()).getFirstElement();
			Integer schemaID = importer.importSchema(nameField.getText(), authorField.getText(), descriptionField.getText(), new File(fileField.getText()).toURI());
			if(schemaID!=null) getShell().dispose();
		}
		catch(Exception e2) { setErrorMessage("Failed to import schema"); }

	}
	
	// Unused listener event
	public void widgetDefaultSelected(SelectionEvent e) {}
}