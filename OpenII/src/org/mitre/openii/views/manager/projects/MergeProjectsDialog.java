package org.mitre.openii.views.manager.projects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;

/** Constructs the Merge Projects Dialog */
public class MergeProjectsDialog extends Dialog implements ModifyListener
{
	/** Private class for making a project checkbox */
	private class ProjectCheckbox implements SelectionListener
	{
		/** Stores the checkbox button */
		private Button button;
		
		/** Stores the project associated with the checkbox */
		private Project project;
		
		/** Constructs the project checkbox */
		private ProjectCheckbox(Composite parent, Project project)
		{
			this.project = project;
			button = new Button(parent, SWT.CHECK);
			button.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			button.setText(project.getName());
			button.addSelectionListener(this);
		}

		/** Indicates if the checkbox is selected */
		private boolean isSelected()
			{ return button.getSelection(); }
		
		/** Returns the project associated with the checkbox */
		private Project getProject()
			{ return project; }

		/** Handles changes to the projects selected in the list */
		public void widgetDefaultSelected(SelectionEvent e)
			{ validateFields(); }

		/** Handles changes to the projects selected in the list */
		public void widgetSelected(SelectionEvent e)
			{ validateFields(); }
	}

	/** Private class for comparing mapping cells */
	private class MappingCellComparator implements Comparator<MappingCell>
	{
		public int compare(MappingCell mappingCell1, MappingCell mappingCell2)
		{
			// First prioritize validated mapping cells over unvalidated mapping cells
			if(mappingCell1.getValidated()!=mappingCell2.getValidated())
				return mappingCell2.getValidated().compareTo(mappingCell1.getValidated());

			// Next prioritize higher scores over lower scores
			return mappingCell2.getScore().compareTo(mappingCell1.getScore());
		}
	}
	
	// Stores the various dialog fields
	private Text nameField = null;
	private Text authorField = null;
	private Text descriptionField = null;
	private ArrayList<ProjectCheckbox> checkboxes = new ArrayList<ProjectCheckbox>();

	/** Retrieves the projects to be displayed arranged by schemas */
	private HashMap<String,ArrayList<Project>> getProjects()
	{
		HashMap<String,ArrayList<Project>> projects = new HashMap<String,ArrayList<Project>>();
		for(Project project : OpenIIManager.getProjects())
		{
			// Get the list of schema names
			ArrayList<String> schemaNames = new ArrayList<String>();
			for(Integer schemaID : project.getSchemaIDs())
				schemaNames.add(OpenIIManager.getSchema(schemaID).getName());
			Collections.sort(schemaNames);

			// Generate the sorted list of schema names
			String schemaList = "";
			for(String schemaName : schemaNames) schemaList += schemaName + ", ";
			if(schemaList.length()>=2) schemaList = schemaList.substring(0, schemaList.length()-2);

			// Place the project into the hash map
			ArrayList<Project> projectList = projects.get(schemaList);
			if(projectList==null) projects.put(schemaList, projectList = new ArrayList<Project>());
			projectList.add(project);
		}
		return projects;
	}

	/** Private class for merging the mapping cells */
	private ArrayList<MappingCell> mergeMappingCells(ArrayList<MappingCell> mappingCells)
	{
		// Group together mapping cells in need of merging
		HashMap<String,ArrayList<MappingCell>> mappingCellHash = new HashMap<String,ArrayList<MappingCell>>();
		for(MappingCell mappingCell : mappingCells)
		{
			// Generate hash key
			Integer element1 = mappingCell.getFirstInput();
			Integer element2 = mappingCell.getOutput();
			String key = element1<element2 ? element1+"-"+element2 : element2+"-"+element1;
			
			// Place mapping cell in hash map
			ArrayList<MappingCell> mappingCellList = mappingCellHash.get(key);
			if(mappingCellList==null) mappingCellHash.put(key, mappingCellList = new ArrayList<MappingCell>());
			mappingCellList.add(mappingCell);
		}
		
		// Merge mapping cells into single list
		ArrayList<MappingCell> mergedMappingCells = new ArrayList<MappingCell>();
		for(ArrayList<MappingCell> mappingCellList : mappingCellHash.values())
		{
			Collections.sort(mappingCellList, new MappingCellComparator());
			mergedMappingCells.add(mappingCellList.get(0));
		}
		return mergedMappingCells;
	}
	
	/** Constructs the dialog */
	public MergeProjectsDialog(Shell shell)
		{ super(shell); }

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Mapping.gif"));
		shell.setText("Merge Projects");
	}
	
	/** Creates the project info pane */
	private void createInfoPane(Composite parent)
	{
		// Construct the pane for showing the info for the selected project
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("General Info");
		pane.setLayout(new GridLayout(2,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		nameField = BasicWidgets.createTextField(pane,"Name");
		authorField = BasicWidgets.createTextField(pane,"Author");
		descriptionField = BasicWidgets.createTextField(pane,"Description",4);
		
		// Add listeners to the fields to monitor for changes
		nameField.addModifyListener(this);
		authorField.addModifyListener(this);
		descriptionField.addModifyListener(this);
	}
	
	/** Creates the project pane */
	private void createProjectPane(Composite parent)
	{
		// Construct the group pane labeling the mapping part of the layout
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("Projects");
		pane.setLayout(new GridLayout(1,false));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 200;
		pane.setLayoutData(gridData);

		// Construct the scrolling pane for showing the mappings available for merging
		ScrolledComposite scrolledPane = new ScrolledComposite(pane, SWT.BORDER | SWT.V_SCROLL);
		scrolledPane.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Creates a project pane
		Composite projectPane = new Composite(scrolledPane, SWT.NONE);
		projectPane.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		projectPane.setLayout(new GridLayout(1,false));
		
		// Display the projects to choose from
		HashMap<String,ArrayList<Project>> projects = getProjects();
		for(String key : WidgetUtilities.sortList(new ArrayList<String>(projects.keySet())))
		{
			ArrayList<Project> projectList = projects.get(key);
			
			// Add spacer if between project groups
			if(projectPane.getChildren().length!=0)
			{
				Label spacer = new Label(projectPane, SWT.NONE);
				spacer.setFont(new Font(Display.getCurrent(),"Arial",2,SWT.NONE));
			}
				
			// Generate the label for the project group
			Label label = new Label(projectPane, SWT.NONE);
			label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			label.setForeground(new Color(Display.getCurrent(),30,30,30));
			label.setFont(new Font(Display.getCurrent(),"Arial",8,SWT.BOLD));
			label.setText("Schemas: " + key);
			
			// Generate the check boxes for the list of projects
			for(Project project : WidgetUtilities.sortList(projectList))
				checkboxes.add(new ProjectCheckbox(projectPane, project));
		}

		// Adjust scroll panes to fit content
		scrolledPane.setContent(projectPane);
		scrolledPane.setExpandVertical(true);
		scrolledPane.setExpandHorizontal(true);
		scrolledPane.setMinSize(projectPane.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	/** Creates the contents for the Import Schema Dialog */
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
		createProjectPane(pane);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Merge Projects Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);

		authorField.setText(System.getProperty("user.name"));
		validateFields();

		return control;
	}
	
	/** Monitors changes to the fields to determine when to activate the OK button */
	public void modifyText(ModifyEvent e)
		{ validateFields(); }
	
	/** Validates the fields in order to activate the OK button */
	private void validateFields()
	{
		boolean activate = false;
		for(ProjectCheckbox checkbox : checkboxes)
			if(checkbox.isSelected()) { activate = true; break; }
		activate &= nameField.getText().length()>0 && authorField.getText().length()>0;
		getButton(IDialogConstants.OK_ID).setEnabled(activate);
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{	
		// Gather the general information
		String name = nameField.getText();
		String author = authorField.getText();
		String description = descriptionField.getText();	
		
		// Gather up the list of schemas and mapping cells in the merged project
		HashSet<ProjectSchema> schemas = new HashSet<ProjectSchema>();
		HashMap<String,ArrayList<MappingCell>> mappings = new HashMap<String,ArrayList<MappingCell>>();
		for(ProjectCheckbox checkbox : checkboxes)
			if(checkbox.isSelected())
			{
				// Add schemas to the schema list
				schemas.addAll(Arrays.asList(checkbox.getProject().getSchemas()));

				// Add mapping cells to the mapping list
				for(Mapping mapping : OpenIIManager.getMappings(checkbox.getProject().getId()))
				{
					String key = mapping.getSourceId() + "_" + mapping.getTargetId();
					ArrayList<MappingCell> mappingCells = mappings.get(key);
					if(mappingCells==null) mappings.put(key, mappingCells = new ArrayList<MappingCell>());
					mappingCells.addAll(OpenIIManager.getMappingCells(mapping.getId()));
				}
			}
		
		// Stores the project and underlying mappings
		Integer projectID = null;
		try {
			// Handles the creation of the project
			Project project = new Project(null,name,description,author,schemas.toArray(new ProjectSchema[0]));
			projectID = OpenIIManager.addProject(project);
			if(projectID==null) throw new Exception();
			
			// Handles the creation of the mappings
			for(String key : mappings.keySet())
			{
				// Stores the mapping
				Integer sourceID = Integer.parseInt(key.replaceAll("_.*",""));
				Integer targetID = Integer.parseInt(key.replaceAll(".*_",""));
				Mapping mapping = new Mapping(null,projectID,sourceID,targetID);
				Integer mappingID = OpenIIManager.addMapping(mapping);
				if(mappingID==null) throw new Exception();
				
				// Stores the mapping cells
				ArrayList<MappingCell> mappingCells = mappings.get(key);
				mappingCells = mergeMappingCells(mappingCells);
				if(!OpenIIManager.saveMappingCells(mappingID, mappingCells))
					throw new Exception();
			}

			// Close the dialog
			getShell().dispose();
		}
		
		// Handles the failure to create the project and mapping cells
		catch(Exception e)
		{
			// Delete the created project
			OpenIIManager.deleteProject(projectID);

			// Generate the message to be displayed
			String message = "Unable to generate project '" + name + "'";
			
			// Display the error message
			MessageBox messageBox = new MessageBox(getShell(),SWT.ERROR);
			messageBox.setText("Mapping Generation Error");
			messageBox.setMessage(message);
			messageBox.open();
		}
	}
}