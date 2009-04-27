package org.mitre.openii.views.manager.dialogs.mappings;

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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.views.manager.dialogs.DialogComponents;

/** Constructs the Edit Mapping Dialog */
public class MergeMappingDialog extends Dialog implements ModifyListener, ISelectionChangedListener
{
	// Stores the various dialog fields
	private Text nameField = null;
	private Text authorField = null;
	private Text descriptionField = null;
	private CheckboxTableViewer mappingList = null;
	
	/** Constructs the dialog */
	public MergeMappingDialog(Shell shell)
		{ super(shell); }

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Mapping.gif"));
		shell.setText("Merge Mappings");
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
	
	/** Creates the mapping pane */
	private void createMappingPane(Composite parent)
	{
		// Construct the pane for showing the schemas for the selected mapping
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("Mappings");
		pane.setLayout(new GridLayout(1,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		mappingList = CheckboxTableViewer.newCheckList(pane,SWT.BORDER | SWT.V_SCROLL);
		mappingList.setContentProvider(new DialogComponents.MappingContentProvider());
		mappingList.setLabelProvider(new DialogComponents.DialogLabelProvider("Mapping.gif"));
		mappingList.setInput("");
		mappingList.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		mappingList.addSelectionChangedListener(this);
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
		createMappingPane(pane);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Import Schema Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);

		authorField.setText(System.getProperty("user.name"));
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
		getButton(IDialogConstants.OK_ID).setEnabled(activate);
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{		
		getShell().dispose();
	}
}