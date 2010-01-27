package org.mitre.openii.views.manager.projects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.mappingList.MappingList;
import org.mitre.openii.widgets.schemaList.SchemaList;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Schema;

/** Constructs the Edit Project Dialog */
public class EditProjectDialog extends Dialog implements ActionListener, ModifyListener, ISelectionChangedListener
{	
	/** Stores the project being edited */
	private Project project = null;
	
	// Stores the various property fields
	private Text nameField = null;
	private Text authorField = null;
	private Text descriptionField = null;
	
	/** Stores the project schemas */
	private SchemaList schemaList = null;
	
	/** Stores the project mappings */
	private MappingList mappingList = null;
	
	/** Constructs the dialog */
	public EditProjectDialog(Shell shell, Project project)
	{
		super(shell);
		this.project = project;
	}	

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Project.gif"));
		shell.setText((project==null ? "Create" : "Edit") + " Project");
	}
	
	/** Creates the mapping info pane */
	private void createInfoPane(Composite parent)
	{
		// Construct the pane for showing the info for the selected project
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("General Info");
		pane.setLayout(new GridLayout(2,false));
		
		// Generate the properties to be displayed by the info pane
		nameField = BasicWidgets.createTextField(pane,"Name");
		authorField = BasicWidgets.createTextField(pane,"Author");
		descriptionField = BasicWidgets.createTextField(pane,"Description",4);
		
		// Add listeners to the fields to monitor for changes
		nameField.addModifyListener(this);
		authorField.addModifyListener(this);
		descriptionField.addModifyListener(this);
	}
	
	/** Creates the project schema pane */
	private void createSchemaPane(Composite parent)
	{
		// Construct the pane for showing the project schemas
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("Schemas");
		pane.setLayout(new GridLayout(1,false));

		// Generate the schema list
		schemaList = new SchemaList(pane,null);
		schemaList.addListener(this);
	}
	
	/** Creates the mappings pane */
	private void createMappingPane(Composite parent)
	{		
		// Construct the pane for showing the mappings
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("Mappings");
		pane.setLayout(new GridLayout(1,false));
		
		// Construct the list of mappings
		mappingList = new MappingList(pane);
		mappingList.addListener(this);
	}
	
	/** Creates the contents for the Import Schema Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.fill = true;
		pane.setLayout(layout);
	
		// Generate the pane components
		createInfoPane(pane);
		createSchemaPane(pane);
		createMappingPane(pane);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Edit Project Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);

		// Make default mapping dialog selections
		if(project!=null)
		{
			// Set general information fields
			nameField.setText(project.getName());
			authorField.setText(project.getAuthor());
			descriptionField.setText(project.getDescription());
			
			// Set all project schemas
			schemaList.setSchemas(Arrays.asList(project.getSchemaIDs()));
			
			// Set all project mappings
			mappingList.setSchemas(schemaList.getSchemas());
			mappingList.setMappings(OpenIIManager.getMappings(project.getId()));
		}
		else authorField.setText(System.getProperty("user.name"));
		updateButton();
		
		return control;
	}

	/** Monitors changes to the fields to determine when to activate the OK button */
	public void modifyText(ModifyEvent e)
		{ updateButton(); }
	
	/** Handles changes to the schemas selected in the list */
	public void selectionChanged(SelectionChangedEvent e)
		{ updateButton(); }

	/** Informs the mapping list of changes to the schema list */
	public void actionPerformed(ActionEvent e)
	{
		// Handles changes to the schema list
		if(e.getSource().equals(schemaList))
		{
			mappingList.setSchemas(schemaList.getSchemas());
			updateButton();
		}
			
		// Handles changes to the mapping list
		else
		{
			HashSet<Integer> lockedIDs = new HashSet<Integer>();
			for(Mapping mapping : mappingList.getMappings())
				{ lockedIDs.add(mapping.getSourceId()); lockedIDs.add(mapping.getTargetId()); }
			schemaList.setLockedSchemas(lockedIDs);
		}
	}
	
	/** Updates the OK button if all fields are filled out correctly */
	private void updateButton()
	{
		boolean valid = nameField.getText().length()>0 && authorField.getText().length()>0;	
		valid &= schemaList.getSchemas().size()>0;
		getButton(IDialogConstants.OK_ID).setEnabled(valid);
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{
		String errorMessage = null;
		
		// Gather the general information
		String name = nameField.getText();
		String author = authorField.getText();
		String description = descriptionField.getText();	
		
		// Generate the list of schemas which have been selected
		ArrayList<ProjectSchema> schemas = new ArrayList<ProjectSchema>();
		for(Schema schema : schemaList.getSchemas())
			schemas.add(new ProjectSchema(schema.getId(),schema.getName(),null));
		
		// Handles the creation of the project
		if(project==null)
		{
			project = new Project(0,name,description,author,schemas.toArray(new ProjectSchema[0]));
			Integer projectID = OpenIIManager.addProject(project);
			if(projectID==null) errorMessage = "Unable to generate project '" + project.getName() + "'";
			else project.setId(projectID);
		}
		
		// Handles the modification of the project
		else
		{
			project.setName(name); project.setAuthor(author); project.setDescription(description);
			project.setSchemas(schemas.toArray(new ProjectSchema[0]));
			if(!OpenIIManager.updateProject(project))
				errorMessage = "Unable to update project '" + project.getName() + "'";
		}
		
		// Add/remove mappings in project
		if(errorMessage==null)
		{
			// Generate list of old mappings
			HashMap<String,Integer> oldMappings = new HashMap<String,Integer>();
			for(Mapping oldMapping : OpenIIManager.getMappings(project.getId()))
				oldMappings.put(oldMapping.getSourceId()+"_"+oldMapping.getTargetId(), oldMapping.getId());

			// Add new mappings
			for(Mapping newMapping : mappingList.getMappings())
			{
				String key = newMapping.getSourceId()+"_"+newMapping.getTargetId();
				if(!oldMappings.containsKey(key))
				{
					newMapping.setProjectId(project.getId());
					if(OpenIIManager.addMapping(newMapping)<=0)
						errorMessage = "Unable to add project mappings";
				}
				else oldMappings.remove(key);
			}
			
			// Remove old mappings
			for(Integer oldMappingID : oldMappings.values())
				if(!OpenIIManager.deleteMapping(oldMappingID))
					errorMessage = "Unable to delete project mappings";
		}
		
		// Display error message if needed
		if(errorMessage!=null)
		{
			MessageBox messageBox = new MessageBox(getShell(),SWT.ERROR);
			messageBox.setText("Mapping Generation Error");
			messageBox.setMessage(errorMessage);
			messageBox.open();
			return;
		}			
		else getShell().dispose();
	}
}