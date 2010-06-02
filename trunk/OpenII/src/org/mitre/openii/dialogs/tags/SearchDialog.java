package org.mitre.openii.dialogs.tags;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.galaxy.model.SelectedObjects;
import org.mitre.galaxy.model.search.SearchManager;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.Tag;

/** Constructs the Search Dialog */
public class SearchDialog extends Dialog implements ModifyListener
{
	/** Stores the tag being searched on */
	private Tag tag = null;
	
	/** Stores the keyword field */
	private Text keywordField = null;
	
	/** Constructs the dialog */
	public SearchDialog(Shell shell, Tag tag)
		{ super(shell); this.tag = tag; }	

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Search.gif"));
		shell.setText("Search");
	}
	
	/** Creates the contents for the Edit Tag Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		pane.setLayout(layout);
	
		// Construct the keyword pane
		Composite keywordPane = new Composite(pane, SWT.NONE);
		layout = new GridLayout(2,false);
		layout.marginWidth = 8;
		keywordPane.setLayout(layout);
		keywordPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Constructs the keyword field
		keywordField = BasicWidgets.createTextField(keywordPane,"Keyword");
		keywordField.addModifyListener(this);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Edit Tag Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);
		validateFields();
		return control;
	}
	
	/** Monitors changes to the name field to determine when to activate the OK button */
	public void modifyText(ModifyEvent e)
		{ validateFields(); }

	/** Validates the fields in order to activate the OK button */
	private void validateFields()
		{ getButton(IDialogConstants.OK_ID).setEnabled(keywordField.getText().length()>0); }
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{		
		// Identify the selected tags
		ArrayList<Integer> tags = null;
		if(tag!=null)
		{
			tags = new ArrayList<Integer>();
			tags.add(tag.getId());
			for(int i=0; i<tags.size(); i++)
				for(Tag tag : OpenIIManager.getSubcategories(tags.get(i)))
					if(!tags.contains(tag.getId())) tags.add(tag.getId());
		}
		
		// Run the search
		SelectedObjects.setSelectedTags(tags);
		SearchManager.searchFor(keywordField.getText());
		
		// Display the results
		
		
		// Close the search dialog
		getShell().dispose();
	}
}