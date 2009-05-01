package org.mitre.openii.views.manager.dialogs.groups;

import java.util.ArrayList;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Schema;

/** Constructs the Edit Group Dialog */
class AddSchemasToGroupDialog extends ElementListSelectionDialog
{
	/** Constructs the dialog */
	AddSchemasToGroupDialog(Shell shell, ArrayList<Integer> selectedSchemaIDs)
	{
		super(shell,new LabelProvider());//DialogComponents.SchemaLabelProvider());
		setMessage("Select Schemas (* = any string, ? = any char):");
		setMultipleSelection(true);
		ArrayList<Schema> schemas = OpenIIManager.getSchemas();
		schemas.removeAll(selectedSchemaIDs);
		setElements(OpenIIManager.sortList(schemas).toArray());
	}	

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Schema.gif"));
		shell.setText("Schema Selection");
	}
}