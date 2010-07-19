package org.mitre.openii.editors.schemas.schema;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.schemaTree.SchemaTree;

/** Constructs the Hierarchical View */
public class SchemaView extends OpenIIEditor
{	
	/** Generate the extension pane */
	private void generateExtensionPane(Composite parent)
	{
		// Retrieve the parent and children schemas
		ArrayList<Integer> parentIDs = OpenIIManager.getParentSchemas(getElementID());
		ArrayList<Integer> childIDs = OpenIIManager.getChildrenSchemas(getElementID());
		
		// Only draw pane if there are parent or children IDs to display
		if(parentIDs.size()>0 || childIDs.size()>0)
		{			
			// Construct the extensions pane
			Composite pane = new Composite(parent, SWT.NONE);
			GridLayout layout = new GridLayout(2,true);
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			pane.setLayout(layout);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.heightHint = 150;
			pane.setLayoutData(gridData);
		
			// Construct the "Extends" and "Extensions" pane
			new ExtensionList(pane,"Extended Schemas", parentIDs);
			new ExtensionList(pane,"Extending Schemas", childIDs);
		}
	}

	/** Displays the Schema View */
	public void createPartControl(Composite parent)
	{	
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Layout the menu pane and tree pane
		new SchemaTree(pane, getElementID());
		generateExtensionPane(pane);
	}
}