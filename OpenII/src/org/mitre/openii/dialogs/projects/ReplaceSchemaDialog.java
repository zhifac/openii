package org.mitre.openii.dialogs.projects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.views.manager.SchemaInProject;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.mappingExporters.M3MappingExporter;
import org.mitre.schemastore.porters.mappingImporters.M3MappingImporter;
import org.w3c.dom.Document;

/** Constructs the Replace Schema Dialog */
public class ReplaceSchemaDialog extends Dialog implements ISelectionChangedListener
{
	/** Stores the project schema being replaced */
	private SchemaInProject oldSchema = null;

	/** Stores the list of schemas which can replace the project schema */
	private ComboViewer schemaList = null;

	/** Constructs the dialog */
	public ReplaceSchemaDialog(Shell shell, SchemaInProject oldSchema)
		{ super(shell); this.oldSchema = oldSchema; }

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Schema.gif"));
		shell.setText("Replace Schema");
	}

	/** Creates the contents for the Replace Schema Dialog */
	protected Control createDialogArea(Composite parent)
	{
		// Identify possible replacement schemas
		HashSet<Schema> schemas = new HashSet<Schema>(OpenIIManager.getSchemas());
		for(Integer schemaID : OpenIIManager.getProject(oldSchema.getProjectID()).getSchemaIDs())
			for(Schema schema : schemas)
				if(schema.getId().equals(schemaID))
					{ schemas.remove(schema); break; }
		
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		
		// Construct the schema pane
		Composite schemaPane = new Composite(pane, SWT.NONE);
		GridLayout layout = new GridLayout(2,false);
		layout.marginWidth = 8;
		schemaPane.setLayout(layout);
		schemaList = BasicWidgets.createComboField(schemaPane, "Replacement", schemas.toArray(), this);

		// Return the generated pane
		return pane;
	}
	
	/** Creates the contents for the Add Mappings Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
		return control;
	}

	/** Returns the selected schema for the specified list */
	private Schema getSchema()
		{ return (Schema)((StructuredSelection)schemaList.getSelection()).getFirstElement(); }
	
	/** Handles changes to the selected schemas */
	public void selectionChanged(SelectionChangedEvent e)
		{ getButton(IDialogConstants.OK_ID).setEnabled(getSchema()!=null); }
	
	/** Handles the actual replacement of the schema */
	protected void okPressed()
	{
		String errorMessage = null;

		// Get project and schema information
		Project project = OpenIIManager.getProject(oldSchema.getProjectID());
		Schema schema = getSchema();
		
		// Add new schema to project
		ArrayList<ProjectSchema> schemas = new ArrayList<ProjectSchema>(Arrays.asList(project.getSchemas()));
		schemas.add(new ProjectSchema(schema.getId(),schema.getName(),null));
		project.setSchemas(schemas.toArray(new ProjectSchema[0]));
		if(!OpenIIManager.updateProject(project))
			errorMessage = "Unable to add " + schema.getName() + " to the project.'";
		
		// Add new mappings to project
		if(errorMessage==null)
		{
			// Retrieve the M3 Mapping importer and exporter
			PorterManager manager = new PorterManager(RepositoryManager.getClient());
			M3MappingExporter exporter = (M3MappingExporter) manager.getPorter(M3MappingExporter.class);
			M3MappingImporter importer = (M3MappingImporter) manager.getPorter(M3MappingImporter.class);

			// Transfer all mappings
			for(Mapping mapping : OpenIIManager.getMappings(project.getId()))
			{
				boolean isSource = mapping.getSourceId().equals(oldSchema.getSchema().getId());
				boolean isTarget = mapping.getTargetId().equals(oldSchema.getSchema().getId());
				if(isSource || isTarget)
				{
					Integer sourceID = isSource ? schema.getId() : mapping.getSourceId();
					Integer targetID = isTarget ? schema.getId() : mapping.getTargetId();
					try {
						// Transfer the mapping to the new schema
						Document document = exporter.generateXMLDocument(mapping, OpenIIManager.getMappingCells(mapping.getId()));
						importer.initialize(document);
						if(importer.importMapping(project, sourceID, targetID)==null)
							throw new Exception();
					}
					catch(Exception e)
					{
						String sourceName = OpenIIManager.getSchema(sourceID).getName();
						String targetName = OpenIIManager.getSchema(targetID).getName();
						errorMessage = "Unable to add mapping between " + sourceName + " and " + targetName;
					}
				}
			}

			// Remove old mappings (or rollback new mappings in case of failure)
			Integer removalID = errorMessage==null ? oldSchema.getSchema().getId() : schema.getId();
			for(Mapping mapping : OpenIIManager.getMappings(project.getId()))
				if(mapping.getSourceId().equals(removalID) || mapping.getTargetId().equals(removalID))
					OpenIIManager.deleteMapping(mapping.getId());

			// Removes reference to old schema (or rollback new schema in case of failure)
			for(ProjectSchema currSchema : schemas)
				if(currSchema.getId().equals(removalID))
					{ schemas.remove(currSchema); break; }
			project.setSchemas(schemas.toArray(new ProjectSchema[0]));
			OpenIIManager.updateProject(project);
		}		
		
		// Display error message if needed
		if(errorMessage != null)
		{
			MessageBox messageBox = new MessageBox(getShell(), SWT.ERROR);
			messageBox.setText("Error Replacing Schema");
			messageBox.setMessage(errorMessage);
			messageBox.open();
			return;
		}
		else getShell().dispose();
	}
}