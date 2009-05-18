package org.mitre.openii.views.manager.dialogs.mappings.importer;

import java.net.URI;
import java.util.ArrayList;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.dialogs.DialogComponents;
import org.mitre.openii.views.manager.dialogs.URIField;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.mappingImporters.MappingImporter;

/**
 * Class for displaying the Mapping Properties page
 */
public class MappingPropertiesPage extends WizardPage implements ISelectionChangedListener, SelectionListener, ModifyListener
{
	// Stores the various dialog fields
	private ComboViewer importerList = null;
	private Text nameField = null;
	private Text authorField = null;
	private Text descriptionField = null;
	private URIField uriField = null;
	
	/** Constructor for the Mapping Properties pane */
	public MappingPropertiesPage()
	{
		super("PropertiesPage");
		setTitle("Import Mapping");
		setDescription("Import a mapping from a file");
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
		for(MappingImporter importer : new PorterManager(OpenIIManager.getConnection()).getMappingImporters())
			importerList.add(importer);
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
		uriField = new URIField(group,this);
		
		// Add listeners to the fields to monitor for changes
		nameField.addModifyListener(this);
		authorField.addModifyListener(this);
		descriptionField.addModifyListener(this);
		uriField.getTextField().addModifyListener(this);
	}
	
	/** Constructs the Mapping Properties page */
	public void createControl(Composite parent)
	{
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.NONE);
		pane.setLayout(new GridLayout(1,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
		// Generate the pane components
		createImporterList(pane);
		createInfoPane(pane);
		
		// Make the default importer selections
		importerList.getCombo().select(0);
		updateFields();
		uriField.getTextField().setFocus();
		
		// Displays the control
		setControl(pane);
	}

	/** Returns the selected importer */
	MappingImporter getImporter()
		{ return (MappingImporter)((StructuredSelection)importerList.getSelection()).getFirstElement(); }
	
	/** Returns the mapping name */
	String getMappingName()
		{ return nameField.getText(); }
	
	/** Returns the mapping author */
	String getMappingAuthor()
		{ return authorField.getText(); }
	
	/** Returns the mapping description */
	String getMappingDescription()
		{ return descriptionField.getText(); }
	
	/** Returns the specified URI */
	URI getURI()
		{ try { return uriField.getURI(); } catch(Exception e) { return null; } }
	
	/** Handles the updating of the fields based on the selected importer */
	public void updateFields()
	{
		// Retrieve the selected importer
		MappingImporter importer = (MappingImporter)((StructuredSelection)importerList.getSelection()).getFirstElement();
		boolean uriImporter = importer.getURIType()==MappingImporter.URI;

		// Update the fields as needed
		if(authorField.getText().equals("")) authorField.setText(System.getProperty("user.name"));
		uriField.setMode(uriImporter ? URIField.URI : URIField.FILE);	}	
	
	/** Handles changes to the selected importer */
	public void selectionChanged(SelectionChangedEvent e)
		{ updateFields(); }

	/** Handles the selection of a file to import from */
	public void widgetSelected(SelectionEvent e)
	{
		// Retrieve the selected importer
		MappingImporter importer = getImporter();
		
		// Generate the list of extensions that are available
		ArrayList<String> extensions = new ArrayList<String>();
		for(String extension : importer.getFileTypes())
			extensions.add("*"+extension);
		
		// Create the dialog
		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
		dialog.setText("Import Mapping");
		dialog.setFilterPath("C:/");
		dialog.setFilterNames(new String[] {importer.getName() + " " + importer.getFileTypes()});
		dialog.setFilterExtensions(extensions.toArray(new String[0]));
		
		// Launch the dialog to retrieve the specified file to load from
        String filename = dialog.open();
        if(filename != null) uriField.getTextField().setText(filename);
	}

	/** Handles modifications to the various text fields */
	public void modifyText(ModifyEvent e)
	{   
		// Check the validity of the file field
		if(e.getSource().equals(uriField.getTextField()))
		{
			setErrorMessage(null);
			if(uriField.getTextField().getText().length()>0)
				setErrorMessage(uriField.isValid() ? null : "A valid file must be selected");
			((ImportMappingWizard)getWizard()).getSchemasPage().setPageComplete(false);
		}
		
		// Determine if the OK button should be activated
		boolean completed = nameField.getText().length()>0 && authorField.getText().length()>0 && descriptionField.getText().length()>0;
		setPageComplete(completed && uriField.isValid());
	}
	
	// Unused listener event
	public void widgetDefaultSelected(SelectionEvent e) {}
}