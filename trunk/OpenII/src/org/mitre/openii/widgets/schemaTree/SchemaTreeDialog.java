package org.mitre.openii.widgets.schemaTree;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;

/** Constructs the Schema Tree Dialog */
public class SchemaTreeDialog extends Dialog
{
	/** Stores the schema */
	private SchemaInfo schema = null;
	
	/** Stores the schema model */
	private SchemaModel model = null;
	
	/** Stores reference to the schema tree */
	private SchemaTree tree = null;
	
	/** Constructs the dialog */
	public SchemaTreeDialog(Shell shell, SchemaInfo schema, SchemaModel model)
		{ super(shell); this.schema=schema; this.model=model; }

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Schema.gif"));
		shell.setText("Select Schema Element");
	}
	
	/** Creates the contents for the Edit Tag Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		pane.setLayout(layout);
	
		// Construct the schema tree pane
		tree = new SchemaTree(pane, schema, model);
		
		// Return the generated pane
		return pane;
	}

	/** Returns the schema tree associated with this dialog */
	public SchemaTree getSchemaTree()
		{ return tree; }
}