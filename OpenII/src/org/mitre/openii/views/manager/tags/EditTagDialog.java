package org.mitre.openii.views.manager.tags;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.schemaList.SchemaList;
import org.mitre.schemastore.model.Tag;

/** Constructs the Edit Group Dialog */
public class EditTagDialog extends Dialog implements ActionListener, ModifyListener
{
	/** Stores the tag being edited */
	private Tag tag = null;
	
	/** Stores the parent ID */
	private Integer parentID = null;
	
	/** A list of initial schemas to populate the schema list with */
	private ArrayList<Integer> initialSchemas;
	
	/** Stores the schema list */
	private SchemaList list;
	
	// Stores the various dialog fields
	private Text nameField = null;
	
	/** Constructs the dialog */
	public EditTagDialog(Shell shell, Tag tag, Integer parentID)
		{ this(shell, tag, parentID, null); }	
	
	/** Constructs the dialog */
	public EditTagDialog(Shell shell, Tag tag, Integer parentID, ArrayList<Integer> initialSchemas)
	{
		super(shell);
		this.tag = tag;
		this.parentID = parentID;
		this.initialSchemas = initialSchemas;
	}	

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("SchemaGroup.gif"));
		shell.setText((tag==null ? "Create" : "Edit") + " Tag");
	}
	
	/** Creates the name pane */
	private void createNamePane(Composite parent)
	{
		// Construct the composite pane
		Composite pane = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2,false);
		layout.marginWidth = 8;
		pane.setLayout(layout);
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Constructs the name field
		nameField = BasicWidgets.createTextField(pane,"Tag Name");
		nameField.addModifyListener(this);
	}
	
	/** Creates the contents for the Edit Tag Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		pane.setLayout(layout);
	
		// Generate the pane components
		createNamePane(pane);
		list = new SchemaList(pane,"Schemas in Tag");
		list.addListener(this);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Edit Tag Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);

		// Make default schema list selections
		nameField.setText(tag!=null ? tag.getName() : "");
		if(tag!=null) list.setSchemas(OpenIIManager.getTagSchemas(tag.getId()));
		else if(initialSchemas!=null) list.setSchemas(initialSchemas);
		validateFields();
		
		return control;
	}
	
	/** Monitors changes to the name field to determine when to activate the OK button */
	public void modifyText(ModifyEvent e)
		{ validateFields(); }
	
	/** Monitors changes to the schema list to determine when to activate the OK button */
	public void actionPerformed(ActionEvent e)
		{ validateFields(); }

	/** Validates the fields in order to activate the OK button */
	private void validateFields()
	{
		boolean validated = nameField.getText().length()>0;
		validated &= list.getSchemas().size()>0;
		getButton(IDialogConstants.OK_ID).setEnabled(validated);
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{		
		boolean creationSuccess = true;
		boolean updateSuccess = true;
		boolean schemaAssignmentSuccess = true;
		
		// Handles the creation of the tag
		if(tag==null)
		{
			tag = new Tag(0,nameField.getText(),parentID);
			Integer tagID = OpenIIManager.addTag(tag);
			if(tagID==null) creationSuccess = false;
			else tag.setId(tagID);
		}
		
		// Handles the modification of the tag name
		else if(!tag.getName().equals(nameField.getText()))
		{
			tag.setName(nameField.getText());
			updateSuccess = OpenIIManager.updateTag(tag);
		}
		
		// Handles the assignment of schemas
		if(creationSuccess && updateSuccess)
		{
			ArrayList<Integer> schemaIDs = list.getSchemaIDs();
			schemaAssignmentSuccess = OpenIIManager.setTagSchemas(tag.getId(),schemaIDs);
		}

		// Display error message if needed
		if(!creationSuccess || !updateSuccess || ! schemaAssignmentSuccess)
		{
			// Generate the message to be displayed
			String message = "Unable to ";
			if(!creationSuccess) message += "generate";
			if(!updateSuccess) message += "update the name for";
			if(!schemaAssignmentSuccess) message += "properly assign schema to";
			message += " tag '" + tag.getName() + "'";
			
			// Display the error message
			MessageBox messageBox = new MessageBox(getShell(),SWT.ERROR);
			messageBox.setText("Tag Generation Error");
			messageBox.setMessage(message);
			messageBox.open();
		}					
		else getShell().dispose();
	}
	
	// Unused listener event
	public void widgetDefaultSelected(SelectionEvent e) {}
}