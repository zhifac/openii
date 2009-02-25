package org.mitre.openii.views.ygg.dialogs;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Schema;

/** Constructs the Import Schema Dialog */
public class EditGroupDialog extends ListSelectionDialog implements ModifyListener, ICheckStateListener, SelectionListener
{
	/** Private class for defining how labels should be displayed in schema tree */
	static private class SchemaLabelProvider implements ILabelProvider
	{
		/** Returns the image associated with the specified element */
		public Image getImage(Object element)
			{ return OpenIIActivator.getImage("Schema.gif"); }

		/** Returns the name associated with the specified element */
		public String getText(Object element)
			{ return element.toString(); }

		/** Indicates that the label is not influenced by an element property */
		public boolean isLabelProperty(Object element, String property) { return false; }
		
		// Unused functions
		public void addListener(ILabelProviderListener listener) {}
		public void dispose() {}
		public void removeListener(ILabelProviderListener listener) {}
	}
	
	/** Private class for defining contents of schema tree */
	static private class SchemaContentProvider implements IStructuredContentProvider
	{
		/** Defines the contents of the schema list */
		public Object[] getElements(Object arg0)
			{ return OpenIIManager.getSchemas().toArray(new Schema[0]); }

		// Unused functions
		public void dispose() {}
		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {}
	}

	/** Stores the group being edited */
	private Group group = null;
	
	/** Stores the parent ID */
	private Integer parentID = null;
	
	// Stores the various dialog fields
	private Text nameField = null;
	
	/** Constructs the dialog */
	public EditGroupDialog(Shell shell, Group group, Integer parentID)
	{
		super(shell,"",new SchemaContentProvider(),new SchemaLabelProvider(),"Select schemas associated with the group");
		this.group = group;
		this.parentID = parentID;
	}	

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Group.gif"));
		shell.setText((group==null ? "Add" : "Edit") + " Group");
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
		nameField = DialogComponents.createTextField(pane,"Group Name");
		nameField.addModifyListener(this);
	}
	
	/** Creates the contents for the Import Schema Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		pane.setLayout(layout);
	
		// Generate the pane components
		createNamePane(pane);
		super.createDialogArea(pane);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Import Schema Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);

		// Make default group dialog selections
		nameField.setText(group!=null ? group.getName() : "");
		if(group!=null)
		{
			// Lock all items selected as part of subgroups
			for(Integer schemaID : OpenIIManager.getDescendantSchemas(group.getId()))
				{ getViewer().setChecked(schemaID, true);  getViewer().setGrayed(schemaID, true); }
			
			// Set all items selected as part of group
			for(Integer schemaID : OpenIIManager.getGroupSchemas(group.getId()))
				getViewer().setChecked(schemaID, true);
		}
		getViewer().addCheckStateListener(this);
		getButton(IDialogConstants.DESELECT_ALL_ID).addSelectionListener(this);
		
		return control;
	}
	
	/** Monitors changes to the name field to determine when to activate the OK button */
	public void modifyText(ModifyEvent e)
		{ getButton(IDialogConstants.OK_ID).setEnabled(nameField.getText().length()>0); }
	
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
			ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
			for(Object schema : getViewer().getCheckedElements())
				if(!getViewer().getGrayed(schema))
					schemaIDs.add(((Schema)schema).getId());
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

	/** Prevent grayed checkboxes from being unchecked */
	public void checkStateChanged(CheckStateChangedEvent e)
	{
		if(getViewer().getGrayed(e.getElement()))
			getViewer().setChecked(e.getElement(), true);
	}
	
	/** Handles the pressing of the "Deselect" button */
	public void widgetSelected(SelectionEvent arg0)
		{ getViewer().setCheckedElements(getViewer().getGrayedElements()); }
	
	// Unused listener event
	public void widgetDefaultSelected(SelectionEvent arg0) {}
}