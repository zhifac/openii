package org.mitre.openii.views.manager.dialogs.groups;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.views.manager.dialogs.DialogComponents;

/** Constructs the Query Schemas For Group Dialog */
public class QuerySchemasForGroupDialog extends Dialog implements SelectionListener
{
	// Stores the various dialog fields
	private Text queryField = null;
	private Button queryButton = null;
	
	/** Constructs the dialog */
	public QuerySchemasForGroupDialog(Shell shell)
		{ super(shell); }

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Schema.gif"));
		shell.setText("Query Schemas");
	}
	
	/** Creates the contents for the Import Schema Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 300;
		pane.setLayoutData(gridData);
	
		// Constructs the query label
		DialogComponents.createLabel(pane, "Search Schemas:");
		
		// Construct the query pane
		Composite queryPane = new Composite(pane, SWT.NONE);
		queryPane.setLayout(new GridLayout(2,false));
		queryPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		queryField = DialogComponents.createText(queryPane,null);
		queryButton = DialogComponents.createButton(queryPane,"Search",this);
		
		// Constructs the results pane
		Composite resultsPane = new Composite(pane, SWT.BORDER | SWT.V_SCROLL);
		resultsPane.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		resultsPane.setLayout(new GridLayout(1,false));
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 300;
		resultsPane.setLayoutData(gridData);
		
		// Return the generated pane
		return pane;
	}
	
	/** Validates the fields in order to activate the OK button */
	private void validateFields()
	{
		boolean activate = false;
		getButton(IDialogConstants.OK_ID).setEnabled(activate);
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{	
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{ }

	@Override
	public void widgetSelected(SelectionEvent arg0) {}
}