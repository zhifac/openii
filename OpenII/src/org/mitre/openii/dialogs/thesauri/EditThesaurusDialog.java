package org.mitre.openii.dialogs.thesauri;

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
import org.mitre.schemastore.model.Thesaurus;

/** Constructs the Import Thesaurus Dialog */
public class EditThesaurusDialog extends Dialog implements ModifyListener
{
	/** Stores the thesaurus being edited */
	private Thesaurus thesaurus = null;
	
	// Stores the various dialog fields
	private Text nameField = null;
	private Text descriptionField = null;
	
	/** Constructs the dialog */
	public EditThesaurusDialog(Shell parentShell, Thesaurus thesaurus)
	{
		super(parentShell);
		if(thesaurus==null) this.thesaurus = thesaurus!=null ? thesaurus : new Thesaurus(null,"","");
	}	

	/** Returns the associated thesaurus */
	public Thesaurus getThesaurus()
		{ return thesaurus; }
	
	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Thesaurus.gif"));
		shell.setText("Edit Thesaurus Properties");
	}

	/** Creates the thesaurus info pane */
	private void createInfoPane(Composite parent)
	{
		// Construct the pane for showing the info for the selected importer
		Group group = new Group(parent, SWT.NONE);
		group.setText("Properties");
		group.setLayout(new GridLayout(2,false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		nameField = BasicWidgets.createTextField(group,"Name");
		descriptionField = BasicWidgets.createTextField(group,"Description",4);
		
		// Add listeners to the fields to monitor for changes
		nameField.addModifyListener(this);
	}
	
	/** Creates the dialog area for the Import Thesaurus Dialog */
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
		nameField.setText(thesaurus.getName());
		descriptionField.setText(thesaurus.getDescription());
		
		return control;
	}

	/** Handles modifications to the various text fields */
	public void modifyText(ModifyEvent e)
	{   
		boolean activate = nameField.getText().length()>0;
		getButton(IDialogConstants.OK_ID).setEnabled(activate);
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{
		// Retrieve new thesaurus information
		thesaurus.setName(nameField.getText());
		thesaurus.setDescription(descriptionField.getText());

		// Create or update the thesaurus information
		boolean success = false;
		if(thesaurus.getId()==null)
		{
			Integer id = OpenIIManager.addThesaurus(thesaurus);
			if(id!=null) { thesaurus.setId(id); success=true; }
		}
		else success = OpenIIManager.updateThesaurus(thesaurus);
		
		// Update schema information
		if(!success)
		{			
			// Display the error message if failed to update
			MessageBox messageBox = new MessageBox(getShell(),SWT.ERROR);
			messageBox.setText("Thesaurus Generation Error");
			messageBox.setMessage("Unable to update thesaurus '" + thesaurus.getName() + "'");
			messageBox.open();
		}					
		else getShell().dispose();
	}
}