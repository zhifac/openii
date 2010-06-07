package org.mitre.openii.widgets.schemaList;

import java.util.ArrayList;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.model.Schema;

public class SchemaSelectionDialog extends ElementListSelectionDialog {
	/** Constructs the dialog */
	public SchemaSelectionDialog(Shell shell, ArrayList<Schema> schemas, Boolean multipleSelection) {
		super(shell, new LabelProvider());
		setMessage("Select Schemas (* = any string, ? = any char):");
		setMultipleSelection(multipleSelection);
		setElements(WidgetUtilities.sortList(schemas).toArray());
	}

	/** Configures the dialog shell */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Schema.gif"));
		shell.setText("Schema Selection");
	}
}
