package org.mitre.openii.editors.schemas.schema;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.schemaTree.SchemaTree;
import org.mitre.schemastore.model.Schema;

/** Constructs the Hierarchical View */
public class SchemaView extends OpenIIEditor
{	
	/** Stores the schema tree displayed in this view */
	private SchemaTree schemaTree = null;
	
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
		schemaTree = new SchemaTree(pane, getElementID());
		generateExtensionPane(pane);
	}
	
	/** Runs a search for the specified keyword */
	private void searchFor(String keyword)
		{ schemaTree.searchFor(keyword); }
	
	/** Launches the editor */
	static public void launchEditor(Schema schema, String keyword)
	{
		SchemaView schemaView = (SchemaView)EditorManager.launchEditor("SchemaView", schema);
		if(schemaView!=null) schemaView.searchFor(keyword);
	}
}