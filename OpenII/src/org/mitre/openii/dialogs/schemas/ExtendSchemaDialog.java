package org.mitre.openii.dialogs.schemas;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.Schema;

/** Constructs the Extend Schema Dialog */
public class ExtendSchemaDialog extends TitleAreaDialog implements ModifyListener
{
	/** Stores the schema being extended */
	private Schema schema = null;
	
	// Stores the various dialog fields
	private Text nameField = null;
	private Text authorField = null;
	private Text descriptionField = null;
	
	/** Constructs the dialog */
	public ExtendSchemaDialog(Shell parentShell, Schema schema)
		{ super(parentShell); this.schema = schema; }

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setText("Extend Schema");
		setTitleImage(OpenIIActivator.getImage("ExtendLarge.png"));
	}
	
	/** Creates the importer info pane */
	private void createInfoPane(Composite parent)
	{
		// Construct the pane for showing the info for the selected importer
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("Properties");
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
	
	/** Creates the dialog area for the Extend Schema Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Set the dialog title and message
		setTitle("Extend Schema");
		setMessage("Extends '" + schema.getName() + "'");
		
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
		// Generate the pane components
		createInfoPane(pane);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Extend Schema Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);
		nameField.setText(schema.getName() + " Extension");
		authorField.setText(System.getProperty("user.name"));
		descriptionField.setText("Extension of " + schema.getName());
		return control;
	}

	/** Handles modifications to the various text fields */
	public void modifyText(ModifyEvent e)
	{   
		boolean activate = nameField.getText().length()>0 && authorField.getText().length()>0 &&
						   descriptionField.getText().length()>0;
		getButton(IDialogConstants.OK_ID).setEnabled(activate);
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{
		try {
			String name = nameField.getText();
			String author = authorField.getText();
			String description = descriptionField.getText();
			Integer schemaID = OpenIIManager.extendSchema(schema.getId(), name, author, description);
			if(schemaID!=null) getShell().dispose();
		}
		catch(Exception e2) {}
		setErrorMessage("Failed to extend schema");
	}
}