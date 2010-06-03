package org.mitre.openii.dialogs.mappings.importer;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Schema;

/** Private class for displaying the selection pane */
class SchemaSelectionPane
{
	// Stores the various composite fields
	private Composite schemaPane = null;
	private ComboViewer schemaList = null;
	private Label schemaLabel = null;
	
	/** Stores the project */
	private Project project = null;
	
	/** Stores the project schema */
	private ProjectSchema projectSchema = null;
	
	/** Constructs the selection pane */
	SchemaSelectionPane(Composite parent, Project project, String text)
	{
		// Stores the project 
		this.project = project;
		
		// Display the field label
		BasicWidgets.createLabel(parent, text);
		
		// Displays the schema selection
		schemaPane = new Composite(parent, SWT.NONE);
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		layout.marginLeft = 0;
		layout.marginTop = 0;
		layout.marginBottom = 1;
		layout.center = true;
		schemaPane.setLayout(layout);
		
		// Display the list of schemas which might align with the mapping schema
		schemaList = new ComboViewer(schemaPane, SWT.NONE);
		schemaList.getControl().setLayoutData(new RowData(200,13));
		setProjectFilter(true);
		
		// Display the schema label
		schemaLabel = new Label(schemaPane, SWT.NONE);
		schemaLabel.setLayoutData(new RowData(100,13));
	}

	/** Activates the project filter */
	void setProjectFilter(boolean projectFilter)
	{
		// Identify schemas to place in the schema list
		ArrayList<Schema> schemas = OpenIIManager.getSchemas();
		if(projectFilter)
		{
			ArrayList<Integer> projectSchemaIDs = new ArrayList<Integer>(Arrays.asList(project.getSchemaIDs()));
			for(Schema schema : new ArrayList<Schema>(schemas))
				if(!projectSchemaIDs.contains(schema.getId()))
					schemas.remove(schema);
		}
		
		// Regenerate the schema list
		schemaList.refresh();
		schemaList.add(WidgetUtilities.sortList(schemas).toArray());

		// Update the selected schema
		if(projectSchema!=null) setSchema(projectSchema);
	}
	
	/** Sets the project schema */
	void setSchema(ProjectSchema projectSchema)
	{
		this.projectSchema = projectSchema;
		
		// Update the selection pane label to show the schema name
		schemaLabel.setText(projectSchema==null ? "" : " (" + projectSchema.getName() + ")");
		
		// Tries to select the proper schema if possible
		for(int i=0; i<schemaList.getCombo().getItemCount(); i++)
		{
			Schema schema = (Schema)schemaList.getElementAt(i);
			if(schema.getName().equals(projectSchema.getName()))
				schemaList.getCombo().select(i);
		}
	}
	
	/** Returns the project schema */
	ProjectSchema getSchema()
	{
		Integer index = schemaList.getCombo().getSelectionIndex();
		if(index>=0)
		{
			projectSchema.setId(((Schema)schemaList.getElementAt(index)).getId());
			return projectSchema;
		}
		return null;
	}
	
	/** Adds a listener to the schema combo box */
	void addSelectionChangedListener(ISelectionChangedListener listener)
		{ schemaList.addSelectionChangedListener(listener); }
}