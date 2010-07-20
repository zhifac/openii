package org.mitre.openii.widgets.schemaList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.widgets.schemaTree.SchemaModelSelector;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;

/** Constructs the Search Dialog */
public class EditProjectSchemaDialog extends Dialog implements ISelectionChangedListener
{
	/** Stores the originally selected schema model */
	private SchemaModel model = null;
	
	/** Stores the keyword field */
	private SchemaModelSelector selector = null;
	
	/** Constructs the dialog */
	public EditProjectSchemaDialog(Shell shell, SchemaModel model)
		{ super(shell); this.model = model; }	

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Schema.gif"));
		shell.setText("Edit Project Schema");
	}
	
	/** Creates the contents for the Edit Project Schema Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
		// Construct the project model selector and return pane
		selector = new SchemaModelSelector(pane,model);
		selector.addSelectionChangedListener(this);
		return pane;
	}

	/** Returns the selected model */
	protected SchemaModel getSchemaModel()
		{ return model; }

	/** Handles changes to the schema model */
	public void selectionChanged(SelectionChangedEvent e)
		{ this.model = selector.getSchemaModel(); }
}