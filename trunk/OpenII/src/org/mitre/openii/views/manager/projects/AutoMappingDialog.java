package org.mitre.openii.views.manager.projects;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.views.manager.projects.matchmaker.MappingProcessor;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.Project;

/**
 * Map all possible pair-wise schemas in a project
 * @author HAOLI
 */
public class AutoMappingDialog extends Dialog
{
	/** Stores the project being mapped */
	private Project project;
	
	// Stores various fields used by the dialog
	private Text nameField;
	private Text authorField;
	private Text descriptionField;
	
	/** Constructs the auto-map dialog */
	public AutoMappingDialog(Shell shell, Project project)
	{ 
		super(shell); 
		this.project = project; 
	}
	
	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Mapping.gif"));
		shell.setText("Auto Mapping");
	}

	/** Creates the mapping info pane */
	private void createInfoPane(Composite parent)
	{
		// Construct the pane for showing the info for the selected mapping
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("General Info");
		pane.setLayout(new GridLayout(2,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		nameField = BasicWidgets.createTextField(pane,"Name");
		authorField = BasicWidgets.createTextField(pane,"Author");
		descriptionField = BasicWidgets.createTextField(pane,"Description",4);
		
		if(project!=null)
		{
			// Set general information fields
			nameField.setText(project.getName());
			authorField.setText(project.getAuthor());
			descriptionField.setText(project.getDescription());
		}
	}
	
	/** Lays out the dialog pane */
	protected Control createDialogArea(Composite parent)
	{			
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 400;
		pane.setLayoutData(gridData);		
		
		// Generate the pane components
		createInfoPane(pane);

		// Return the generated pane
		return pane;
	}
	
	/** Automatically maps the various schemas in the project */
	protected void okPressed()
	{
		try {
			MappingProcessor.run(project);
			getShell().dispose();
		}
		catch(Exception e)
		{
			MessageBox messageBox = new MessageBox(getShell(),SWT.ERROR);
			messageBox.setText("Mapping Generation Error");
			messageBox.setMessage("Failed to automatically generate mappings!  " + e.getMessage());
			messageBox.open();
			return;
		}
	}
}