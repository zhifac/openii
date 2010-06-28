package org.mitre.openii.dialogs.projects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.mappingList.MappingList;
import org.mitre.openii.widgets.mappingList.MappingReference;
import org.mitre.openii.widgets.schemaList.SchemaList;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Tag;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.mappingExporters.M3MappingExporter;
import org.mitre.schemastore.porters.mappingImporters.M3MappingImporter;
import org.w3c.dom.Document;

/** Constructs the Edit Project Dialog */
public class EditProjectDialog extends Dialog implements ActionListener, ModifyListener, ISelectionChangedListener {
	/** Stores the tag from which the project is being created */
	private Tag tag = null;

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

	/** Constructs the dialog for a new project */
	public EditProjectDialog(Shell shell) {
		super(shell);
	}

	/** Constructs the dialog for a project based on the specified tag */
	public EditProjectDialog(Shell shell, Tag tag) {
		super(shell);
		this.tag = tag;
	}

	/** Constructs the dialog for the specified project */
	public EditProjectDialog(Shell shell, Project project) {
		super(shell);
		this.project = project;
	}

	/** Configures the dialog shell */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Project.gif"));
		shell.setText((project == null ? "Create" : "Edit") + " Project");
	}

	/** Creates the mapping info pane */
	private void createInfoPane(Composite parent) {
		// Construct the pane for showing the info for the selected project
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("General Info");
		pane.setLayout(new GridLayout(2, false));

		// Generate the properties to be displayed by the info pane
		nameField = BasicWidgets.createTextField(pane, "Name");
		authorField = BasicWidgets.createTextField(pane, "Author");
		descriptionField = BasicWidgets.createTextField(pane, "Description", 4);

		// Add listeners to the fields to monitor for changes
		nameField.addModifyListener(this);
		authorField.addModifyListener(this);
		descriptionField.addModifyListener(this);
	}

	/** Creates the project schema pane */
	private void createSchemaPane(Composite parent) {
		// Construct the pane for showing the project schemas
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("Schemas");
		pane.setLayout(new GridLayout(1, false));

		// Generate the schema list
		schemaList = new SchemaList(pane, null);
		schemaList.setWidth(300);
		schemaList.addListener(this);
	}

	/** Creates the mappings pane */
	private void createMappingPane(Composite parent) {
		// Construct the pane for showing the mappings
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("Mappings");
		pane.setLayout(new GridLayout(1, false));
		
		// Construct the list of mappings
		mappingList = new MappingList(pane, null);
		mappingList.setWidth(300);
		mappingList.addListener(this);
	}

	/** Creates the contents for the Import Schema Dialog */
	protected Control createDialogArea(Composite parent) {
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
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);

		// Make default mapping dialog selections if a tag was given
		if (tag != null) {
			// Set the name field
			nameField.setText(tag.getName());

			// Set the selected schema
			ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
			schemaIDs.addAll(OpenIIManager.getTagSchemas(tag.getId()));
			schemaIDs.addAll(OpenIIManager.getChildTagSchemas(tag.getId()));
			schemaList.setSchemas(schemaIDs);
		}

		// Make default mapping dialog selections if a project was given
		if (project != null) {
			// Set general information fields
			nameField.setText(project.getName());
			authorField.setText(project.getAuthor());
			descriptionField.setText(project.getDescription());

			// Set all project schemas
			schemaList.setSchemas(Arrays.asList(project.getSchemaIDs()));

			// Set all project mappings
			mappingList.setSchemas(schemaList.getSchemas());
			mappingList.setMappings(OpenIIManager.getMappings(project.getId()));
		} else {
			authorField.setText(System.getProperty("user.name"));
		}

		return control;
	}

	/**
	 * Monitors changes to the fields to determine when to activate the OK
	 * button
	 */
	public void modifyText(ModifyEvent e) {
		updateButton();
	}

	/** Handles changes to the schemas selected in the list */
	public void selectionChanged(SelectionChangedEvent e) {
		updateButton();
	}

	/** Informs the mapping list of changes to the schema list */
	public void actionPerformed(ActionEvent e) {
		// Handles changes to the schema list
		if (e.getSource().equals(schemaList)) {
			mappingList.setSchemas(schemaList.getSchemas());
			updateButton();
		}

		// Handles changes to the mapping list
		else {
			HashSet<Integer> lockedIDs = new HashSet<Integer>();
			for (Mapping mapping : mappingList.getMappings()) {
				lockedIDs.add(mapping.getSourceId());
				lockedIDs.add(mapping.getTargetId());
			}
			schemaList.setLockedSchemas(lockedIDs);
		}
	}

	/** Updates the OK button if all fields are filled out correctly */
	private void updateButton() {
		boolean valid = nameField.getText().length() > 0 && authorField.getText().length() > 0;
		valid &= schemaList.getSchemas().size() > 0;
		getButton(IDialogConstants.OK_ID).setEnabled(valid);
	}

	/** Handles the actual import of the specified file */
	protected void okPressed() {
		// Gather the general information
		String name = nameField.getText();
		String author = authorField.getText();
		String description = descriptionField.getText();

		try {
			if (project == null) {
				// this is a new project so we just create the project with whatever schemas
				// and mappings are there and don't worry about mapping cells

				// generate the list of schemas which have been selected
				ArrayList<ProjectSchema> schemas = new ArrayList<ProjectSchema>();
				for (Schema schema : schemaList.getSchemas()) {
					schemas.add(new ProjectSchema(schema.getId(), schema.getName(), null));
				}

				// create a new project and put it into the database
				project = new Project(0, name, description, author, schemas.toArray(new ProjectSchema[0]));
				Integer projectID = OpenIIManager.addProject(project);
				if (projectID == null) {
					throw new Exception("Unable to create project '" + project.getName() + "'.");
				} else {
					project.setId(projectID);
				}

				// add the new mappings to our project
				// there are no old mappings, so we don't have to delete anything
				for (MappingReference newMappingReference : mappingList.getMappings()) {
					Mapping newMapping = new Mapping(newMappingReference.getId(), project.getId(), newMappingReference.getSourceId(), newMappingReference.getTargetId());

					Integer mappingID = OpenIIManager.addMapping(newMapping);
					if (mappingID == null) {
						throw new Exception("Unable to set project mappings.");
					}
				}
			} else {
				// this is an existing project, so we need to update the project information,
				// save all mappings and mapping cells into an export, and then delete any removed mappings

				// update basic project details
				project.setName(name);
				project.setAuthor(author);
				project.setDescription(description);
				if (!OpenIIManager.updateProject(project)) {
					throw new Exception("Unable to update project '" + project.getName() + "'.");
				}

				// get list of schemas and mappings that are in our project now
				ArrayList<ProjectSchema> oldSchemas = new ArrayList(Arrays.asList(project.getSchemas()));
				ArrayList<ProjectSchema> newSchemas = new ArrayList<ProjectSchema>();
				for (Schema newSchema : schemaList.getSchemas()) {
					// see if this schema is already in our project and if it is then add that to our list
					boolean foundOldSchema = false;
					for (ProjectSchema oldSchema : oldSchemas) {
						if (oldSchema.getId() == newSchema.getId()) {
							newSchemas.add(oldSchema);
							foundOldSchema = true;
						}
					}

					// otherwise make a new ProjectSchema and use that instead
					// this preserves the "Model" attribute of the ProjectSchema
					if (!foundOldSchema) { 
						newSchemas.add(new ProjectSchema(newSchema.getId(), newSchema.getName(), null));
					}
				}
				ArrayList<MappingReference> newMappingReferences = mappingList.getMappings();

				// export each mapping
				try {
					// create the exporter/importer manager
					PorterManager manager = new PorterManager(RepositoryManager.getClient());

					// create an exporter and export the mapping into a portable format
					M3MappingExporter exporter = (M3MappingExporter)manager.getPorter(M3MappingExporter.class);

					// go through all of our mappings and save them
					for (MappingReference mapping : newMappingReferences) {
						Integer oldMappingId = mapping.isModified() ? mapping.getOldMappingId() : mapping.getId();
						if (oldMappingId != null) {
							// our old mapping id will be null if this is a new mapping in an existing project
							mapping.setExportDocument(exporter.generateXMLDocument(project, OpenIIManager.getMapping(oldMappingId), OpenIIManager.getMappingCells(oldMappingId)));
						}
					}
				} catch (Exception exception) {
					System.err.println(exception.getMessage());
					exception.printStackTrace();
					throw new Exception("Unable to modify project mappings. (export)");
				}

				// replace all the schemas in this project
				project.setSchemas(newSchemas.toArray(new ProjectSchema[0]));
				if (!OpenIIManager.updateProject(project)) {
					throw new Exception("Unable to update project '" + project.getName() + "'.");
				}

				// re-import mappings with new schemas
				ArrayList<Integer> newMappingIds = new ArrayList<Integer>();
				try {
					// create the exporter/importer manager
					PorterManager manager = new PorterManager(RepositoryManager.getClient());

					// create an importer and import the mapping from the portable format using the new target/source
					M3MappingImporter importer = (M3MappingImporter)manager.getPorter(M3MappingImporter.class);

					// go through all of our mappings and import them
					for (MappingReference mapping : newMappingReferences) {
						Integer mappingID;
						Document document = mapping.getExportDocument();

						if (document == null) {
							// our document will be null if this is a new mapping in an existing project
							Mapping newMapping = new Mapping(null, project.getId(), mapping.getSourceId(), mapping.getTargetId());
							mappingID = OpenIIManager.addMapping(newMapping);
						} else {
							importer.initialize(mapping.getExportDocument());
							mappingID = importer.importMapping(project, mapping.getSourceId(), mapping.getTargetId());
						}

						// this mapping is now on our list of mappings to keep
						newMappingIds.add(mappingID);
					}
				} catch (Exception exception) {
					System.err.println(exception.getMessage());
					exception.printStackTrace();
					throw new Exception("Unable to modify project mappings. (import)");
				}

				// delete mappings that are no longer in existence
				for (Mapping mapping : OpenIIManager.getMappings(project.getId())) {
					if (!newMappingIds.contains(mapping.getId())) {
						if (!OpenIIManager.deleteMapping(mapping.getId())) {
							throw new Exception("Unable to modify project mappings. (delete)");
						}
					}
				}
			}

			// close our window
			getShell().dispose();
		} catch (Exception errorMessage) {
			MessageBox messageBox = new MessageBox(getShell(), SWT.ERROR);
			messageBox.setText("Error Saving Project");
			messageBox.setMessage(errorMessage.getMessage());
			messageBox.open();
		}
	}
}