package org.mitre.openii.views.manager.mappings.importer;

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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.URIField;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.Importer.URIType;
import org.mitre.schemastore.porters.PorterManager.PorterType;
import org.mitre.schemastore.porters.mappingImporters.MappingImporter;

/**
 * Class defining a wizard for importing mappings
 */
public class ImportMappingDialog extends TitleAreaDialog implements ISelectionChangedListener, ModifyListener, SelectionListener
{
	/** Stores the project to which the mapping is being imported */
	private Project project = null;
	
	// Stores the pages used by this wizard
	private ComboViewer importerList = null;
	private URIField uriField = null;
	private SchemaSelectionPane sourcePane = null;
	private SchemaSelectionPane targetPane = null;
	private Button allSchemasCheckbox = null;
	
	/** Constructs the wizard */
	public ImportMappingDialog(Shell parentShell, Project project)
		{ super(parentShell); this.project = project; }
	
	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setText("Import Mapping");
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
		ArrayList<MappingImporter> importers = new PorterManager(RepositoryManager.getClient()).getPorters(PorterType.MAPPING_IMPORTERS);
		for(MappingImporter importer : WidgetUtilities.sortList(importers))
			importerList.add(importer);
		importerList.addSelectionChangedListener(this);
	}
	
	/** Creates the dialog area for the Import Mapping Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Set the dialog title and message
		setTitle("Import Mapping");
		setMessage("Import a mapping from a file");
		
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
		// Displays the list of importers
		createImporterList(pane);
		
		// Construct the pane for showing the info for the selected importer
		Group group = new Group(pane, SWT.NONE);
		group.setText("Importer Settings");
		group.setLayout(new GridLayout(2,false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		uriField = new URIField(group);
		sourcePane = new SchemaSelectionPane(group, project, "Source");
		targetPane = new SchemaSelectionPane(group, project, "Target");

		// Generate the checkbox indicating if all schemas should be included
		Text blank = new Text(group,SWT.NONE);
		blank.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		allSchemasCheckbox = new Button(group,SWT.CHECK);
		allSchemasCheckbox.setText("Allow selection of non-project schemas");
		allSchemasCheckbox.addSelectionListener(this);
		
		// Add listeners to the fields to monitor for changes
		uriField.getTextField().addModifyListener(this);
		sourcePane.addSelectionChangedListener(this);
		targetPane.addSelectionChangedListener(this);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Import Mapping Dialog */
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
		MappingImporter importer = (MappingImporter)((StructuredSelection)importerList.getSelection()).getFirstElement();
		boolean uriImporter = importer.getURIType()==URIType.URI;

		// Generate the list of extensions that are available
		ArrayList<String> extensions = new ArrayList<String>();
		for(String extension : importer.getFileTypes())
			extensions.add("*"+extension);
		
		// Update the fields as needed
		uriField.setMode(uriImporter ? URIField.URI : URIField.FILE);
		uriField.setDialogInfo("Import Mapping", importer.getName()+" "+importer.getFileTypes(), extensions);
		
		// Updates the button
		updateButton();
	}	

	/** Update button */
	private void updateButton()
	{
		boolean valid = sourcePane.getSchema()!=null && targetPane.getSchema()!=null && uriField.isValid();
		getButton(IDialogConstants.OK_ID).setEnabled(valid);		
	}	
	
	/** Handles changes to the selected importer */
	public void selectionChanged(SelectionChangedEvent e)
	{
		if(e.getSource().equals(importerList)) updateFields();
		else updateButton();
	}
	
	/** Handles modifications to the various text fields */
	public void modifyText(ModifyEvent e)
	{   
		// Check the validity of the file field
		if(e.getSource().equals(uriField.getTextField()))
		{
			// Determine if a valid uri was given
			setErrorMessage(null);
			if(uriField.getTextField().getText().length()>0)
				setErrorMessage(uriField.isValid() ? null : "A valid file must be selected");

			// Update the labeling on the source and schema panes
			try {
				MappingImporter importer = (MappingImporter)((StructuredSelection)importerList.getSelection()).getFirstElement();
				importer.initialize(uriField.getURI());
				sourcePane.setSchema(importer.getSourceSchema());
				targetPane.setSchema(importer.getTargetSchema());
			} catch(Exception e2) {}
		}
		
		// Determine if the OK button should be activated
		updateButton();
	}
	
	/** Handles the marking of the checkbox */
	public void widgetSelected(SelectionEvent e)
	{
		sourcePane.setProjectFilter(!allSchemasCheckbox.getSelection());
		targetPane.setProjectFilter(!allSchemasCheckbox.getSelection());
		updateButton();
	}
	
	/** Handles the actual import of the specified file(s) */
	protected void okPressed()
	{
		try {
			// Gather up URI of the file being imported
			URI uri = uriField.getURI();

			// Gather up schema alignment settings
			Integer sourceID = sourcePane.getSchema().getId();
			Integer targetID = targetPane.getSchema().getId();
			
			// Check to see if mapping already exists in project
			for(Mapping mapping : OpenIIManager.getMappings(project.getId()))
				if(mapping.getSourceId().equals(sourceID) && mapping.getTargetId().equals(targetID))
					throw new Exception("Mapping already exists in project");
			
			// Import mapping
			MappingImporter importer = (MappingImporter)((StructuredSelection)importerList.getSelection()).getFirstElement();
			importer.initialize(uri);
			Integer mappingID = importer.importMapping(project, sourceID, targetID);
			if(mappingID!=null)
			{
				Mapping mapping = RepositoryManager.getClient().getMapping(mappingID);
				OpenIIManager.fireMappingAdded(mapping); getShell().dispose();
				getShell().dispose();
			}
		}
		catch(Exception e) { setErrorMessage("Failed to import mapping. " + e.getMessage()); }
	}
	
	// Unused listener
	public void widgetDefaultSelected(SelectionEvent arg0) {}
}