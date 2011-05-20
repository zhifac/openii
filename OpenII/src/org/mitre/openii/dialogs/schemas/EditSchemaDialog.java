package org.mitre.openii.dialogs.schemas;

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
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Thesaurus;

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
		String label = schema.getClass().getName().replaceAll(".*\\.","");		
		shell.setImage(OpenIIActivator.getImage(label+".gif"));
		shell.setText("Edit "+label+" Properties");
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
		nameField = BasicWidgets.createTextField(group,"Name");
		if(!(schema instanceof Thesaurus))
		{
			authorField = BasicWidgets.createTextField(group,"Author");
			sourceField = BasicWidgets.createTextField(group,"Source");
			typeField = BasicWidgets.createTextField(group,"Type");
		}
		descriptionField = BasicWidgets.createTextField(group,"Description",4);
		
		// Add listeners to the fields to monitor for changes
		nameField.addModifyListener(this);
		if(authorField!=null) authorField.addModifyListener(this);
		if(descriptionField!=null) descriptionField.addModifyListener(this);
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
		if(authorField!=null) authorField.setText(schema.getAuthor());
		if(sourceField!=null) sourceField.setText(schema.getSource());
		if(typeField!=null) typeField.setText(schema.getType());
		descriptionField.setText(schema.getDescription());
		
		return control;
	}

	/** Handles modifications to the various text fields */
	public void modifyText(ModifyEvent e)
	{   
		boolean activate = nameField.getText().length()>0 && (authorField==null || authorField.getText().length()>0);
		getButton(IDialogConstants.OK_ID).setEnabled(activate);
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{
		// Retrieve new schema information
		schema.setName(nameField.getText());
		schema.setAuthor(authorField==null ? "" : authorField.getText());
		schema.setSource(sourceField==null || sourceField.getText()==null ? "" : sourceField.getText());
		schema.setType(typeField==null || typeField.getText()==null ? "" : typeField.getText());
		schema.setDescription(descriptionField.getText());

		// Update schema information
		if(!OpenIIManager.updateSchema(schema))
		{
			String label = schema.getClass().getName().replaceAll(".*\\.","");
			
			// Display the error message if failed to update
			MessageBox messageBox = new MessageBox(getShell(),SWT.ERROR);
			messageBox.setText(label+" Generation Error");
			messageBox.setMessage("Unable to update "+label.toLowerCase()+" '" + schema.getName() + "'");
			messageBox.open();
		}					
		else getShell().dispose();
	}
}