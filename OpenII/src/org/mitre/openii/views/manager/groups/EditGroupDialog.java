package org.mitre.openii.views.manager.groups;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.ListWithButtonBar;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Schema;

/** Constructs the Edit Group Dialog */
public class EditGroupDialog extends Dialog implements ModifyListener, SelectionListener
{
	/** Stores the group being edited */
	private Group group = null;
	
	/** Stores the parent ID */
	private Integer parentID = null;
	
	// Stores the various dialog fields
	private Text nameField = null;
	private TableViewer list = null;
	private Button addButton = null;
	private Button searchButton = null;
	private Button removeButton = null;
	
	/** Constructs the dialog */
	public EditGroupDialog(Shell shell, Group group, Integer parentID)
	{
		super(shell);
		this.group = group;
		this.parentID = parentID;
	}	

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Group.gif"));
		shell.setText((group==null ? "Create" : "Edit") + " Group");
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
		nameField = BasicWidgets.createTextField(pane,"Group Name");
		nameField.addModifyListener(this);
	}
	
	/** Creates the schema table */
	private void createSchemaTable(Composite parent)
	{
		ListWithButtonBar dialogList = new ListWithButtonBar(parent, "Schemas in Group", "Schema");
		addButton = dialogList.addButton("Add...",this);
		searchButton = dialogList.addButton("Search...",this);
		removeButton = dialogList.addButton("Remove",this);
		list = dialogList.getList();
		list.setLabelProvider(new BasicWidgets.SchemaLabelProvider());
	}
	
	/** Creates the contents for the Edit Group Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		pane.setLayout(layout);
	
		// Generate the pane components
		createNamePane(pane);
		createSchemaTable(pane);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Edit Group Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);

		// Make default group dialog selections
		nameField.setText(group!=null ? group.getName() : "");
		if(group!=null)
		{
			// Set all items selected as part of group
			ArrayList<Schema> groupSchemas = new ArrayList<Schema>();
			for(Integer schemaID : OpenIIManager.getGroupSchemas(group.getId()))
				groupSchemas.add(OpenIIManager.getSchema(schemaID));
			for(Schema schema : WidgetUtilities.sortList(groupSchemas))
				list.add(schema);
		}
		validateFields();
		
		return control;
	}
	
	/** Monitors changes to the name field to determine when to activate the OK button */
	public void modifyText(ModifyEvent e)
		{ validateFields(); }
		
	/** Returns the list of selected schemas */
	private ArrayList<Integer> getSelectedSchemas()
	{
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		for(int i=0; i<list.getTable().getItemCount(); i++)
			schemaIDs.add(((Schema)list.getElementAt(i)).getId());
		return schemaIDs;
	}
	
	/** Validates the fields in order to activate the OK button */
	private void validateFields()
	{
		boolean validated = nameField.getText().length()>0;
		validated &= list.getTable().getItemCount()>0;
		getButton(IDialogConstants.OK_ID).setEnabled(validated);
	}

	/** Handles the pressing of list buttons */
	public void widgetSelected(SelectionEvent e)
	{
		// Handles the addition of schemas
		if(e.getSource().equals(addButton))
		{
			AddSchemasToGroupDialog dialog = new AddSchemasToGroupDialog(getShell(),getSelectedSchemas());
			if(dialog.open() == Window.OK)
				list.add(dialog.getResult());
		}
			
		// Handles the search for schemas
		if(e.getSource().equals(searchButton))
			new QuerySchemasForGroupDialog(getShell()).open();

		// Handles the removal of schemas
		if(e.getSource().equals(removeButton))
			list.getTable().remove(list.getTable().getSelectionIndices());

		// Determine if the "OK" button should be activated
		validateFields();
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{		
		boolean creationSuccess = true;
		boolean updateSuccess = true;
		boolean schemaAssignmentSuccess = true;
		
		// Handles the creation of the group
		if(group==null)
		{
			group = new Group(0,nameField.getText(),parentID);
			Integer groupID = OpenIIManager.addGroup(group);
			if(groupID==null) creationSuccess = false;
			else group.setId(groupID);
		}
		
		// Handles the modification of the group name
		else if(!group.getName().equals(nameField.getText()))
		{
			group.setName(nameField.getText());
			updateSuccess = OpenIIManager.updateGroup(group);
		}
		
		// Handles the assignment of schemas
		if(creationSuccess && updateSuccess)
		{
			ArrayList<Integer> schemaIDs = getSelectedSchemas();
			schemaAssignmentSuccess = OpenIIManager.setGroupSchemas(group.getId(),schemaIDs);
		}

		// Display error message if needed
		if(!creationSuccess || !updateSuccess || ! schemaAssignmentSuccess)
		{
			// Generate the message to be displayed
			String message = "Unable to ";
			if(!creationSuccess) message += "generate";
			if(!updateSuccess) message += "update the name for";
			if(!schemaAssignmentSuccess) message += "properly assign schema to";
			message += " group '" + group.getName() + "'";
			
			// Display the error message
			MessageBox messageBox = new MessageBox(getShell(),SWT.ERROR);
			messageBox.setText("Group Generation Error");
			messageBox.setMessage(message);
			messageBox.open();
		}					
		else getShell().dispose();
	}
	
	// Unused listener event
	public void widgetDefaultSelected(SelectionEvent e) {}
}