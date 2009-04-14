package org.mitre.openii.views.ygg.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Schema;

/** Constructs the Import Schema Dialog */
public class EditSchemaDialog extends Dialog implements ModifyListener
{
	/** Stores the schema being edited */
	private Schema schema = null;
	
	// Stores the various dialog fields
	private Text nameField = null;
	private Text authorField = null;
	private Text sourceField = null;
	private Text typeField = null;
	private Text descriptionField = null;
	
	/** Constructs the dialog */
	public EditSchemaDialog(Shell parentShell, Schema schema)
	{
		super(parentShell);
		this.schema = schema;
	}	

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Schema.gif"));
		shell.setText("Edit Schema");
	}

	/** Creates the schema info pane */
	private void createInfoPane(Composite parent)
	{
		// Construct the pane for showing the info for the selected importer
		Group group = new Group(parent, SWT.NONE);
		group.setText("Properties");
		group.setLayout(new GridLayout(2,false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		nameField = DialogComponents.createTextField(group,"Name");
		authorField = DialogComponents.createTextField(group,"Author");
		sourceField = DialogComponents.createTextField(group,"Source");
		typeField = DialogComponents.createTextField(group,"Type");
		descriptionField = DialogComponents.createTextField(group,"Description",4);
		
		// Add listeners to the fields to monitor for changes
		nameField.addModifyListener(this);
		authorField.addModifyListener(this);
		descriptionField.addModifyListener(this);
	}
	
	/** Creates the dialog area for the Import Schema Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 400;
		pane.setLayoutData(gridData);
	
		// Generate the pane components
		createInfoPane(pane);
		return pane;
	}

	/** Creates the contents for the Import Schema Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);

		// Set the general information fields
		nameField.setText(schema.getName());
		authorField.setText(schema.getAuthor());
		sourceField.setText(schema.getSource());
		typeField.setText(schema.getType());
		descriptionField.setText(schema.getDescription());

		
		// Lock the name field from being changed
		if(schema.getLocked()) nameField.setEnabled(false);
		
		return control;
	}

	/** Handles modifications to the various text fields */
	public void modifyText(ModifyEvent e)
	{   
		boolean activate = nameField.getText().length()>0 && authorField.getText().length()>0 && descriptionField.getText().length()>0;
		getButton(IDialogConstants.OK_ID).setEnabled(activate);
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{
		// Retrieve new schema information
		schema.setName(nameField.getText());
		schema.setAuthor(authorField.getText());
		schema.setSource(sourceField.getText()==null ? "" : sourceField.getText());
		schema.setType(typeField.getText()==null ? "" : typeField.getText());
		schema.setDescription(descriptionField.getText());

		// Update schema information
		if(!OpenIIManager.updateSchema(schema))
		{
			// Display the error message if failed to update
			MessageBox messageBox = new MessageBox(getShell(),SWT.ERROR);
			messageBox.setText("Schema Generation Error");
			messageBox.setMessage("Unable to update schema '" + schema.getName() + "'");
			messageBox.open();
		}					
		else getShell().dispose();
	}
}