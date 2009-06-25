package org.mitre.openii.views.manager.schemas;

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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.URIField;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;
import org.mitre.schemastore.porters.schemaImporters.SchemaProperties;
import org.mitre.schemastore.model.Schema;

/** Constructs the Import Schema Dialog */
public class ImportSchemaDialog extends TitleAreaDialog implements ISelectionChangedListener, ModifyListener
{
	// Stores the various dialog fields
	private ComboViewer importerList = null;
	private Text nameField = null;
	private Text authorField = null;
	private Text descriptionField = null;
	private URIField uriField = null;
	
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
		BasicWidgets.createLabel(pane,"Importer");
		importerList = new ComboViewer(pane, SWT.NONE);
		ArrayList<SchemaImporter> importers = new PorterManager(RepositoryManager.getClient()).getSchemaImporters();
		for(SchemaImporter importer : WidgetUtilities.sortList(importers))
		{
			Integer uriType = importer.getURIType();
			if(uriType==SchemaImporter.FILE || uriType==SchemaImporter.M3MODEL || uriType==SchemaImporter.URI)
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
		nameField = BasicWidgets.createTextField(group,"Name");
		authorField = BasicWidgets.createTextField(group,"Author");
		descriptionField = BasicWidgets.createTextField(group,"Description",4);
		uriField = new URIField(group);
		
		// Add listeners to the fields to monitor for changes
		nameField.addModifyListener(this);
		authorField.addModifyListener(this);
		descriptionField.addModifyListener(this);
		uriField.getTextField().addModifyListener(this);
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
		uriField.getTextField().setFocus();
		
		return control;
	}
	
	/** Handles the updating of the fields based on the selected importer */
	public void updateFields()
	{
		// Retrieve the selected importer
		SchemaImporter importer = (SchemaImporter)((StructuredSelection)importerList.getSelection()).getFirstElement();
		boolean m3Importer = importer.getURIType()==SchemaImporter.M3MODEL;
		boolean uriImporter = importer.getURIType()==SchemaImporter.URI;
		
		// Clear out the fields as needed
		if(m3Importer)
			{ nameField.setText(""); authorField.setText(""); descriptionField.setText(""); }
		else if(authorField.getText().equals(""))
			authorField.setText(System.getProperty("user.name"));
		uriField.getTextField().setText("");
		
		// Disable fields as needed in case of m3 exporter
		nameField.setEnabled(!m3Importer);
		authorField.setEnabled(!m3Importer);
		descriptionField.setEnabled(!m3Importer);
		
		// Generate the list of extensions that are available
		ArrayList<String> extensions = new ArrayList<String>();
		for(String extension : importer.getFileTypes())
			extensions.add("*"+extension);

		// Display file field or URI depending on importer type
		uriField.setMode(uriImporter ? URIField.URI : URIField.FILE);
		uriField.setDialogInfo("Import Schema", importer.getName()+" "+importer.getFileTypes(), extensions);
	}	
	
	/** Handles changes to the selected importer */
	public void selectionChanged(SelectionChangedEvent e)
		{ updateFields(); }

	/** Handles modifications to the various text fields */
	public void modifyText(ModifyEvent e)
	{   
		// Check the validity of the file field
		Text uriText = uriField.getTextField();
		if(e.getSource().equals(uriText))
		{
			setErrorMessage(null);
			if(uriField.getTextField().getText().length()>0)
				setErrorMessage(uriField.isValid() ? null : "A valid file must be selected");
		}
		
		// Update name, author, and description info when file modified
		SchemaImporter importer = (SchemaImporter)((StructuredSelection)importerList.getSelection()).getFirstElement();
		if(e.getSource().equals(uriText))
			try {
				// Retrieve the schema properties
				SchemaProperties properties = null;
				if(uriField.isValid())
					properties = importer.getSchemaProperties(new File(uriText.getText()).toURI());
				
				// Set the fields based on the given schema properties
				if(properties!=null)
				{
					nameField.setText(properties.getName());
					authorField.setText(properties.getAuthor());
					descriptionField.setText(properties.getDescription());
				}
			} catch(Exception e2) {}
		
		// Determine if the OK button should be activated
		boolean activate = nameField.getText().length()>0;
		activate &= authorField.getText().length()>0;
		activate &= descriptionField.getText().length()>0;
		activate &= uriText.getText().length()>0;
		getButton(IDialogConstants.OK_ID).setEnabled(activate && uriField.isValid());
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{
		try {
			SchemaImporter importer = (SchemaImporter)((StructuredSelection)importerList.getSelection()).getFirstElement();
			URI uri = uriField.getURI();
			Integer schemaID = importer.importSchema(nameField.getText(), authorField.getText(), descriptionField.getText(), uri);
			if(schemaID!=null)
			{
				Schema schema = RepositoryManager.getClient().getSchema(schemaID);
				OpenIIManager.fireSchemaAdded(schema); getShell().dispose();
			}
		}
		catch(Exception e)
			{ setErrorMessage("Failed to import schema. " + e.getMessage()); }
	}
}