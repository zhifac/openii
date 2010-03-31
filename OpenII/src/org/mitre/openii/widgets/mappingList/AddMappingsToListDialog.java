package org.mitre.openii.widgets.mappingList;

import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/** Constructs the Add Mappings to List Dialog */
class AddMappingsToListDialog extends Dialog implements ISelectionChangedListener
{
	/** Stores the schemas from which to create mappings */
	private ArrayList<Schema> schemas = null;
	
	/** Stores the list of generated mappings */
	private ArrayList<Mapping> mappings = null;
	
	// Stores the various dialog fields
	private ComboViewer sourceList = null;
	private ComboViewer targetList = null;
	
	// Stores the selected source and target schemas */
	private Schema source, target;
	
	/** Constructs the dialog */
	AddMappingsToListDialog(Shell shell, ArrayList<Schema> schemas, ArrayList<Mapping> mappings)
		{ super(shell); this.schemas = schemas; this.mappings = mappings; }

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Mapping.gif"));
		shell.setText("Add Mapping");
	}
	
	/** Creates a schema list */
	private ComboViewer createSchemaList(Composite parent)
	{
		ComboViewer list = new ComboViewer(parent, SWT.NONE);
		for(Schema schema : schemas) list.add(schema);
		list.addSelectionChangedListener(this);
		return list;
	}
	
	/** Creates the contents for the Add Mapping Dialog */
	protected Control createDialogArea(Composite parent)
	{
		// Construct the composite pane
		Composite pane = new Composite(parent, SWT.NONE);
		pane.setLayout(new GridLayout(2,false));
		
		// Displays the available lists of source/target schemas
		BasicWidgets.createLabel(pane,"Source");
		sourceList = createSchemaList(pane);
		BasicWidgets.createLabel(pane,"Target");
		targetList = createSchemaList(pane);
		
		// Return the generated pane
		return pane;
	}
	
	/** Creates the contents for the Add Mappings Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
		return control;
	}
	
	/** Update the schema lists */
	private void updateSchemas(ComboViewer schemaList)
	{		
		// Get the selected schema
		Schema selectedSchema = getSchema(schemaList);
		
		// Identify the item selected in the other list
		ComboViewer otherList = schemaList==sourceList ? targetList : sourceList;
		Schema otherSchema = getSchema(otherList);
		Integer otherID = otherSchema==null ? null : otherSchema.getId();
		
		// Construct the list of prohibited schemas
		HashSet<Integer> prohibitedSchemas = new HashSet<Integer>();
		if(otherID!=null) prohibitedSchemas.add(otherID);
		for(Mapping mapping : mappings)
		{
			if(schemaList==sourceList && mapping.getTargetId().equals(otherID))
				prohibitedSchemas.add(mapping.getSourceId());
			if(schemaList==targetList && mapping.getSourceId().equals(otherID))
				prohibitedSchemas.add(mapping.getTargetId());			
		}
		
		// Clear out the schema list
		while(schemaList.getCombo().getItemCount()>0)
			schemaList.remove(schemaList.getElementAt(0));

		// Rebuild the schema list while skipping prohibited schemas
		for(Schema schema : schemas)
			if(!prohibitedSchemas.contains(schema.getId()))
				schemaList.add(schema);

		// Reset the selected schema
		if(selectedSchema!=null)
		{
			schemaList.removeSelectionChangedListener(this);
			schemaList.setSelection(new StructuredSelection(selectedSchema));
			schemaList.addSelectionChangedListener(this);
		}
	}
	
	/** Handles changes to the selected importer */
	public void selectionChanged(SelectionChangedEvent e)
	{
		// Updates the schemas
		updateSchemas(e.getSource().equals(sourceList) ? targetList : sourceList);
		
		// Retrieve the selected source and target schemas
		source = getSchema(sourceList);
		target = getSchema(targetList);
		
		// Determine if the button should be enabled
		boolean disable = source==null || target==null || source.equals(target);
		if(!disable)
			for(Mapping mapping : mappings)
				if(mapping.getSourceId().equals(source.getId()) && mapping.getTargetId().equals(target.getId()))
					{ disable = true; break; }

		// Enable/disable the button based on if the mapping exists
		getButton(IDialogConstants.OK_ID).setEnabled(!disable);
	}
	
	/** Returns the selected schema for the specified list */
	private Schema getSchema(ComboViewer schemaList)
		{ return (Schema)((StructuredSelection)schemaList.getSelection()).getFirstElement(); }
	
	/** Returns the selected mapping */
	Mapping getMapping()
		{ return new MappingReference(new Mapping(null,null,source.getId(),target.getId()),schemas); }
}