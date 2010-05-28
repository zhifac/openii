package org.mitre.openii.views.manager.projects.importer;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;
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
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.Importer.URIType;
import org.mitre.schemastore.porters.PorterManager.PorterType;
import org.mitre.schemastore.porters.projectImporters.ProjectImporter;

/** Constructs the Import Schema Dialog */
public class ImportProjectDialog extends TitleAreaDialog
{	
	// Stores the various dialog fields
	private ComboViewer importerList = null;
	private ProjectInformationPane projectInfoPane = null;
	
	/** Constructs the dialog */
	public ImportProjectDialog(Shell parentShell)
		{ super(parentShell);}	

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setText("Import Project");
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
		importerList = new ComboViewer(pane, SWT.NONE);
		ArrayList<ProjectImporter> importers = new PorterManager(RepositoryManager.getClient()).getPorters(PorterType.PROJECT_IMPORTERS);
		for(ProjectImporter importer : WidgetUtilities.sortList(importers))
		{
			URIType uriType = importer.getURIType();
			if(uriType==URIType.FILE || uriType==URIType.M3MODEL || uriType==URIType.URI)
				importerList.add(importer);
		}
	}

	/** Creates the dialog area for the Import Project Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Set the dialog title and message
		setTitle("Import Project");
		setMessage("Import a project from a file");
		
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
		// Generate the pane components
		createImporterList(pane);
		projectInfoPane = new ProjectInformationPane(pane);
		
		// Add listeners to the various panes
		projectInfoPane.addListener(new ProjectInfoListener());
		projectInfoPane.addURIListener(new ProjectURIListener());
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Import Project Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);

		// Make the default importer selections
		importerList.getCombo().select(0);
		ProjectImporter importer = (ProjectImporter)((StructuredSelection)importerList.getSelection()).getFirstElement();
		projectInfoPane.initializeProjectInfo(importer);
		
		return control;
	}
	
	/** Update button */
	private void updateButton()
		{ getButton(IDialogConstants.OK_ID).setEnabled(projectInfoPane.isValid()); }
	
	/** Handles the actual import of the specified file(s) */
	protected void okPressed()
	{
		String errorMessage = null;
		
		// Imports the project
		ProjectImporter importer = (ProjectImporter)((StructuredSelection)importerList.getSelection()).getFirstElement();
		try {
			importer.initialize(projectInfoPane.getURIField().getURI());
			Integer projectID = importer.importProject(projectInfoPane.getName(), projectInfoPane.getAuthor(), projectInfoPane.getDescription());
			if(projectID!=null)
			{
				Project project = RepositoryManager.getClient().getProject(projectID);
				OpenIIManager.fireProjectAdded(project);
			}
		}
		catch(Exception e) { errorMessage = e.getMessage(); }

		// Issue an error message if needed
		if(errorMessage!=null)
			setErrorMessage("Failed to import project. " + errorMessage);
		else getShell().dispose();
	}
	
	/** Class for listening for modifications to the project uri */
	private class ProjectURIListener implements ModifyListener
	{
		public void modifyText(ModifyEvent e)
		{
			// Retrieve the URI field
			URIField uriField = projectInfoPane.getURIField();
			String uriString = uriField.getTextField().getText();
			
			// Check to see if a valid URI has been given
			setErrorMessage(null);
			if(uriString.length()>0)
				setErrorMessage(uriField.isValid() ? null : "A valid file must be selected");

			// Update name to reflect file name
			File file = new File(uriString);
			projectInfoPane.setName(file.getName().replaceAll("\\.[^\\.]*", ""));

			// Updates the dialog button
			updateButton();
		}
	}
	
	/** Class for listening for modifications to the project information */
	private class ProjectInfoListener implements ModifyListener
		{ public void modifyText(ModifyEvent e) { updateButton(); } }
}