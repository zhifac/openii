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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;

/** Constructs the Replace Schema Dialog */
public class ReplaceSchemaDialog extends Dialog implements ISelectionChangedListener
{
	/** Stores the project in which a schema needs to be replaced */
	private Project project = null;

	// Stores the various dialog fields
	private ComboViewer oldSchemaList = null;
	private ComboViewer newSchemaList = null;

	/** Constructs the dialog */
	public ReplaceSchemaDialog(Shell shell, Project project)
		{ super(shell); this.project = project; }

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Project.gif"));
		shell.setText("Replace Schema");
	}

	/** Creates a schema list */
	private ComboViewer createSchemaList(Composite parent, ArrayList<Schema> schemas)
	{
		ComboViewer list = new ComboViewer(parent, SWT.NONE);
try {
		list.getCCombo().setLayout(new GridLayout());
		list.getCCombo().setLayoutData(new GridData(GridData.FILL_VERTICAL));
} catch(Exception e) { e.printStackTrace(); }
		for(Schema schema : schemas) list.add(schema);
		list.addSelectionChangedListener(this);
		return list;
	}

	/** Creates the contents for the Replace Schema Dialog */
	protected Control createDialogArea(Composite parent)
	{
		// Construct the composite pane
		Composite pane = new Composite(parent, SWT.NONE);
		pane.setLayout(new GridLayout(2,false));
		
		// Gather up schemas (in and outside of project)
		ArrayList<Schema> projectSchemas = new ArrayList<Schema>();
		ArrayList<Schema> nonProjectSchemas = new ArrayList<Schema>();
		HashSet<Integer> projectSchemaIDs = new HashSet<Integer>(Arrays.asList(project.getSchemaIDs()));
		for(Schema schema : OpenIIManager.getSchemas())
		{
			if(projectSchemaIDs.contains(schema.getId())) projectSchemas.add(schema);
			else nonProjectSchemas.add(schema);
		}
		
		// Displays the available lists of source/target schemas
		BasicWidgets.createLabel(pane,"Source");
		oldSchemaList = createSchemaList(pane,projectSchemas);
		BasicWidgets.createLabel(pane,"Target");
		newSchemaList = createSchemaList(pane,nonProjectSchemas);

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

	/** Handles changes to the selected schemas */
	public void selectionChanged(SelectionChangedEvent e)
	{
		// Verifies that an old and new schema have been selected
		Schema oldSchema = getSchema(oldSchemaList);
		Schema newSchema = getSchema(newSchemaList);
		getButton(IDialogConstants.OK_ID).setEnabled(oldSchema!=null && newSchema!=null);
	}

	/** Returns the selected schema for the specified list */
	private Schema getSchema(ComboViewer schemaList)
		{ return (Schema)((StructuredSelection)schemaList.getSelection()).getFirstElement(); }
}