package org.mitre.openii.widgets.schemaTree;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;

/** Constructs a Schema Tree */
public class SchemaModelSelector extends Composite
{	
	// Stores the various dialog fields
	private ComboViewer modelList = null;


	/** Constructs the Schema Tree */
	public SchemaModelSelector(Composite parent)
		{ this(parent,null, null, false); }
	public SchemaModelSelector(Composite parent, SchemaModel selectedModel, HierarchicalSchemaInfo schemaInfo, boolean forProjectUse) {
		super(parent, SWT.NONE);
		setLayout(new GridLayout(2,false));
		setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Displays the models available for selection
		BasicWidgets.createLabel(this,"View");
		modelList = new ComboViewer(this);

		// Display the default schema model
		String defaultModel = "<default>";
		modelList.add("<default>");
		if(selectedModel==null) modelList.setSelection(new StructuredSelection(defaultModel));
		boolean limited = false;
		// Display the various schema models
		for(SchemaModel model : HierarchicalSchemaInfo.getSchemaModels())
		{
			if (!forProjectUse || schemaInfo.shouldExpandAll(model)){
			modelList.add(model);
			if(selectedModel!=null && model.getClass().equals(selectedModel.getClass()))
				modelList.setSelection(new StructuredSelection(model));
			}else {
				limited = true;
			}
		}
		if (limited) {
			MessageDialog.openInformation(parent.getShell(), "Large Schema", "Due to the size of this schema, the choices for models have been limited in Harmony to avoid performance issues.");
		}
	}
	/** Constructs the Schema Tree */
	public SchemaModelSelector(Composite parent, SchemaModel selectedModel)
	{	
		this(parent, selectedModel, null, false);
	}
	
	/** Returns the selected schema model */
	public SchemaModel getSchemaModel()
	{
		Object model = ((StructuredSelection)modelList.getSelection()).getFirstElement();
		return model instanceof SchemaModel ? (SchemaModel)model : null; 
	}
	
	/** Handles the enabling of this component */
	public void setEnabled(boolean enabled)
		{ modelList.getCombo().setEnabled(enabled); }
	
	/** Adds a listener to the model selector */
	public void addSelectionChangedListener(ISelectionChangedListener listener)
		{ modelList.addSelectionChangedListener(listener); }
}