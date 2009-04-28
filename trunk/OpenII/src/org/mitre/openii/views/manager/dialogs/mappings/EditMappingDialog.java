package org.mitre.openii.views.manager.dialogs.mappings;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import org.mitre.openii.views.manager.dialogs.DialogComponents;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/** Constructs the Edit Mapping Dialog */
public class EditMappingDialog extends Dialog implements ModifyListener, ISelectionChangedListener
{
	/** Stores the mapping being edited */
	private Mapping mapping = null;
	
	// Stores the various dialog fields
	private Text nameField = null;
	private Text authorField = null;
	private Text descriptionField = null;
	private CheckboxTableViewer schemaList = null;
	
	/** Constructs the dialog */
	public EditMappingDialog(Shell shell, Mapping mapping)
	{
		super(shell);
		this.mapping = mapping;
	}	

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Mapping.gif"));
		shell.setText((mapping==null ? "Create" : "Edit") + " Mapping");
	}
	
	/** Creates the mapping info pane */
	private void createInfoPane(Composite parent)
	{
		// Construct the pane for showing the info for the selected mapping
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("General Info");
		pane.setLayout(new GridLayout(2,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		nameField = DialogComponents.createTextField(pane,"Name");
		authorField = DialogComponents.createTextField(pane,"Author");
		descriptionField = DialogComponents.createTextField(pane,"Description",4);
		
		// Add listeners to the fields to monitor for changes
		nameField.addModifyListener(this);
		authorField.addModifyListener(this);
		descriptionField.addModifyListener(this);
	}
	
	/** Creates the mapping schema pane */
	private void createSchemaPane(Composite parent)
	{
		// Construct the pane for showing the schemas for the selected mapping
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("Schemas");
		pane.setLayout(new GridLayout(1,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		schemaList = CheckboxTableViewer.newCheckList(pane,SWT.BORDER | SWT.V_SCROLL);
		schemaList.setContentProvider(new DialogComponents.SchemaContentProvider());
		schemaList.setLabelProvider(new DialogComponents.SchemaLabelProvider());
		schemaList.setInput("");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 200;
		schemaList.getControl().setLayoutData(gridData);
		schemaList.addSelectionChangedListener(this);
	}
	
	/** Creates the contents for the Import Schema Dialog */
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
		createSchemaPane(pane);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Import Schema Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);

		// Make default mapping dialog selections
		if(mapping!=null)
		{
			// Set general information fields
			nameField.setText(mapping.getName());
			authorField.setText(mapping.getAuthor());
			descriptionField.setText(mapping.getDescription());
			
			// Set all items selected as part of mapping
			for(Integer schemaID : mapping.getSchemas())
				schemaList.setChecked(schemaID, true);
		}
		else authorField.setText(System.getProperty("user.name"));
		validateFields();
		
		return control;
	}
	
	/** Monitors changes to the fields to determine when to activate the OK button */
	public void modifyText(ModifyEvent e)
		{ validateFields(); }
	
	/** Handles changes to the schemas selected in the list */
	public void selectionChanged(SelectionChangedEvent e)
		{ validateFields(); }
	
	/** Validates the fields in order to activate the OK button */
	private void validateFields()
	{
		boolean activate = nameField.getText().length()>0 && authorField.getText().length()>0 &&
		   descriptionField.getText().length()>0;	
		activate &= schemaList.getCheckedElements().length>0;
		getButton(IDialogConstants.OK_ID).setEnabled(activate);
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{		
		boolean creationSuccess = true;
		boolean updateSuccess = true;
		
		// Gather the general information
		String name = nameField.getText();
		String author = authorField.getText();
		String description = descriptionField.getText();	
		
		// Generate the list of schemas which have been selected
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		for(Object element : schemaList.getCheckedElements())
			schemaIDs.add(((Schema)element).getId());
		
		// Handles the creation of the mapping
		if(mapping==null)
		{
			mapping = new Mapping(0,name,description,author,schemaIDs.toArray(new Integer[0]));
			Integer mappingID = OpenIIManager.addMapping(mapping);
			if(mappingID==null) creationSuccess = false;
			else mapping.setId(mappingID);
		}
		
		// Handles the modification of the mapping
		else
		{
			mapping.setName(name); mapping.setAuthor(author); mapping.setDescription(description);
			mapping.setSchemas(schemaIDs.toArray(new Integer[0]));
			updateSuccess = OpenIIManager.updateMapping(mapping);
		}

		// Display error message if needed
		if(!creationSuccess || !updateSuccess)
		{
			// Generate the message to be displayed
			String message = "Unable to ";
			if(!creationSuccess) message += "generate";
			if(!updateSuccess) message += "update";
			message += " mapping '" + mapping.getName() + "'";
			
			// Display the error message
			MessageBox messageBox = new MessageBox(getShell(),SWT.ERROR);
			messageBox.setText("Mapping Generation Error");
			messageBox.setMessage(message);
			messageBox.open();
		}					
		else getShell().dispose();
	}
}