package org.mitre.openii.widgets.mappingList;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/** Constructs the Add Mappings to List Dialog */
public class MappingSelectionDialog extends Dialog implements ISelectionChangedListener {
	/** Stores the schemas from which to create mappings */
	private ArrayList<Schema> schemas = null;
	
	/** Stores the list of generated mappings */
	private ArrayList<Mapping> mappings = null;

	/** Stores the mapping that we are editing */
	private Mapping mapping = null;
	
	// Stores the various dialog fields
	private ComboViewer sourceList = null;
	private ComboViewer targetList = null;
	private ArrayList<Schema> prohibitedSourceSchemas = null;
	private ArrayList<Schema> prohibitedTargetSchemas = null;

	// Stores the selected source and target schemas */
	private Schema source, target;
	
	/** Constructs the dialog */
	MappingSelectionDialog(Shell shell, ArrayList<Schema> schemas, ArrayList<Mapping> mappings, Mapping mapping) {
		super(shell);
		this.schemas = schemas;
		this.mappings = mappings;
		this.mapping = mapping;

		if (mapping != null) {
			this.source = getSchema(mapping.getSourceId());
			this.target = getSchema(mapping.getTargetId());
		}
	}

	/** Configures the dialog shell */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Mapping.gif"));
		shell.setText("Select Mappings");
	}
	
	/** Creates the contents for the Mapping Dialog */
	protected Control createDialogArea(Composite parent) {
		// Construct the composite pane
		Composite pane = new Composite(parent, SWT.NONE);
		pane.setLayout(new GridLayout(2,false));
		
		// create our source/target select boxes
		BasicWidgets.createLabel(pane, "Source");
		sourceList = new ComboViewer(pane, SWT.NONE);
		BasicWidgets.createLabel(pane, "Target");
		targetList = new ComboViewer(pane, SWT.NONE);

		// populate our schema lists will all their values
		for (Schema schema : schemas) {
			sourceList.add(schema);
			targetList.add(schema);
		}

		// set default values
		if (source != null) { sourceList.setSelection(new StructuredSelection(source)); }
		if (target != null) { targetList.setSelection(new StructuredSelection(target)); }

		// call function to weed out bad entries
		trimSchemaList(sourceList);
		trimSchemaList(targetList);

		// enable listeners
		sourceList.addSelectionChangedListener(this);
		targetList.addSelectionChangedListener(this);

		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Add Mappings Dialog */
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
		return control;
	}

	/** Handles changes to the selected importer */
	public void selectionChanged(SelectionChangedEvent e) {
		// update the schema lists based on what was selected
		if (e.getSource().equals(sourceList)) { trimSchemaList(targetList); }
		if (e.getSource().equals(targetList)) { trimSchemaList(sourceList); }

		// determine if the button should be enabled
		Button ok = getButton(IDialogConstants.OK_ID);
		if (ok != null) {
			// retrieve the selected source and target schemas
			source = getSchema(sourceList);
			target = getSchema(targetList);

			// if the source or target is null, or the source and the target are the same: DISABLED
			boolean disable = source == null || target == null || source.equals(target);

			// only run this loop if the last one turned true, because it is potentially expensive
			// if the source and the target combine to form an already existing mapping: DISABLED
			if (!disable) {
				for (Mapping mapping : mappings) {
					if (mapping.getSourceId().equals(source.getId()) && mapping.getTargetId().equals(target.getId())) {
						disable = true;
						break;
					}
				}
			}

			// enable/disable the button if this is an acceptable mapping
			ok.setEnabled(!disable);
		}
	}

	private void trimSchemaList(ComboViewer schemaList) {
		// determine what is selected on each side
		source = getSchema(sourceList);
		target = getSchema(targetList);

		// construct list of prohibited schemas
		ArrayList<Schema> prohibitedSchemas = new ArrayList<Schema>();

		// make the selected schema in the opposite list be prohibited
		if (schemaList.equals(sourceList) && target != null) { prohibitedSchemas.add(target); }
		if (schemaList.equals(targetList) && source != null) { prohibitedSchemas.add(source); }

		// make schemas that could potentially result in duplicates be prohibited
		for (Mapping mapping : mappings) {
			// if this is the mapping that we are editing, we don't want to prohibit its source/target
			if (this.mapping != null && mapping.getId() == this.mapping.getId()) { continue; }

			// if:
			// - there is a target chosen
			// - and we are currently filtering the source list
			// - and the current mapping we are examining has a target that is the same as our specified target
			// then:
			// - do not add the mapping's source schema to the list
			if (target != null && schemaList.equals(sourceList) && mapping.getTargetId() == target.getId()) {
				prohibitedSchemas.add(getSchema(mapping.getSourceId()));
			}
			// replace "target" with "source" and vice versa from above description
			if (source != null && schemaList.equals(targetList) && mapping.getSourceId() == source.getId()) {
				prohibitedSchemas.add(getSchema(mapping.getTargetId()));
			}
		}

		// if the one being prohibited is the one currently selected, don't prohibit it
		if (schemaList.equals(sourceList) && source != null && prohibitedSchemas.contains(source)) { prohibitedSchemas.remove(source); }
		if (schemaList.equals(targetList) && target != null && prohibitedSchemas.contains(target)) { prohibitedSchemas.remove(target); }

		// take the schema list and remove the ones that are prohibited
		for (Schema schema : prohibitedSchemas) {
			schemaList.remove(schema);
		}

		// store our past prohibited items
		// then take the difference of what we just prohibited and what we've prohibited before
		// add them back to the list
		if (schemaList.equals(sourceList)) {
			if (prohibitedSourceSchemas != null) {
				// remove what we just removed from the previous remove list
				prohibitedSourceSchemas.removeAll(prohibitedSchemas);

				// now add back that difference to the schemaList
				for (Schema item : prohibitedSourceSchemas) { schemaList.add(item); }
			}

			// store our new prohibited list
			prohibitedSourceSchemas = prohibitedSchemas;
		}
		if (schemaList.equals(targetList)) {
			if (prohibitedTargetSchemas != null) {
				// remove what we just removed from the previous remove list
				prohibitedTargetSchemas.removeAll(prohibitedSchemas);

				// now add back that difference to the schemaList
				for (Schema item : prohibitedTargetSchemas) { schemaList.add(item); }
			}

			// store our new prohibited list
			prohibitedTargetSchemas = prohibitedSchemas;
		}
	}

	/** Returns the selected schema for the specified list */
	private Schema getSchema(ComboViewer schemaList) {
		return (Schema)((StructuredSelection)schemaList.getSelection()).getFirstElement();
	}

	/** Returns the selected schema for the specified ID */
	private Schema getSchema(Integer schemaID) {
		for (int i = 0; i < schemas.size(); i++) {
			if (schemas.get(i).getId() == schemaID) { return schemas.get(i); }
		}
		return null;
	}


	/** Returns the selected mapping */
	Mapping getMapping() {
		return new MappingReference(new Mapping(null, null, source.getId(), target.getId()), schemas);
	}
}