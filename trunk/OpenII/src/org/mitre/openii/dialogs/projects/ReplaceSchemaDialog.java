package org.mitre.openii.dialogs.projects;

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
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.SchemaInProject;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.Schema;

/** Constructs the Replace Schema Dialog */
public class ReplaceSchemaDialog extends Dialog implements ISelectionChangedListener
{
	/** Stores the project schema being replaced */
	private SchemaInProject schema = null;

	/** Stores the list of schemas which can replace the project schema */
	private ComboViewer replacementList = null;

	/** Constructs the dialog */
	public ReplaceSchemaDialog(Shell shell, SchemaInProject schema)
		{ super(shell); this.schema = schema; }

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
		for(Integer schemaID : OpenIIManager.getProject(schema.getProjectID()).getSchemaIDs())
			for(Schema schema : schemas)
				if(schema.getId().equals(schemaID))
					{ schemas.remove(schema); break; }
		
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		pane.setLayout(layout);
		
		// Construct the schema pane
		Composite schemaPane = new Composite(pane, SWT.NONE);
		layout = new GridLayout(2,false);
		layout.marginWidth = 8;
		schemaPane.setLayout(layout);
		replacementList = BasicWidgets.createComboField(schemaPane, "Replacement", schemas.toArray(), this);

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
		{ return (Schema)((StructuredSelection)replacementList.getSelection()).getFirstElement(); }
	
	/** Handles changes to the selected schemas */
	public void selectionChanged(SelectionChangedEvent e)
		{ getButton(IDialogConstants.OK_ID).setEnabled(getSchema()!=null); }
}