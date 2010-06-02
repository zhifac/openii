package org.mitre.openii.editors.schemas.schema;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;

/** Constructs the Hierarchical View */
public class SchemaView extends OpenIIEditor implements ISelectionChangedListener
{	
	/** Stores the hierarchical schema being displayed */
	private HierarchicalSchemaInfo schema = null;
	
	// Stores the various dialog fields
	private ComboViewer modelList = null;
	private Text searchField = null;
	private TreeViewer schemaView = null;
	
	/** Generate the menu pane */
	private void generateMenuPane(Composite parent)
	{
		// Construct the menu pane
		Composite menuPane = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2,false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		menuPane.setLayout(layout);
		menuPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
		// Construct the model pane
		Composite modelPane = new Composite(menuPane, SWT.NONE);
		modelPane.setLayout(new GridLayout(2,false));
		modelPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Displays the models available for selection
		BasicWidgets.createLabel(modelPane,"Model");
		modelList = new ComboViewer(modelPane);
		for(SchemaModel model : HierarchicalSchemaInfo.getSchemaModels())
		{
			modelList.add(model);
			if(model.getClass().equals(schema.getModel().getClass()))
				modelList.setSelection(new StructuredSelection(model));
		}
		modelList.addSelectionChangedListener(this);
		
		// Construct the search pane
		Composite searchPane = new Composite(menuPane, SWT.NONE);
		searchPane.setLayout(new GridLayout(2,false));
		searchPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Construct the search bar
		searchField = BasicWidgets.createTextField(searchPane, "Search");
	}
	
	/** Generate the tree pane */
	private void generateTreePane(Composite parent)
	{
		// Construct the tree pane
		Composite treePane = new Composite(parent, SWT.BORDER);
		GridLayout layout = new GridLayout(1,false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		treePane.setLayout(layout);
		treePane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		// Create the tree viewer
		schemaView = new TreeViewer(treePane, SWT.SINGLE);
		schemaView.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		// Populate the tree viewer
		schemaView.setContentProvider(new SchemaContentProvider(schema));
		schemaView.setLabelProvider(new SchemaLabelProvider(schema));
		schemaView.setInput("");
	}
	
	/** Displays the Schema View */
	public void createPartControl(Composite parent)
	{	
		// Retrieves the hierarchical schema
		schema = new HierarchicalSchemaInfo(OpenIIManager.getSchemaInfo(elementID));
		
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Layout the menu pane and tree pane
		generateMenuPane(pane);
		generateTreePane(pane);
	}

	/** Handles changes to the schema model */
	public void selectionChanged(SelectionChangedEvent arg0)
	{
		SchemaModel model = (SchemaModel)((StructuredSelection)modelList.getSelection()).getFirstElement();
		schema.setModel(model);
		schemaView.refresh();
	}
}