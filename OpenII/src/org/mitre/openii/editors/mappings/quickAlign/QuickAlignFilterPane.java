package org.mitre.openii.editors.mappings.quickAlign;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.Mapping;

/** Constructs the QuickAlign filter pane */
public class QuickAlignFilterPane extends Composite
{
//	/** Constructs a schema selection pane */
//	private generateSchemaSelectionPane(Composite parent, Integer schemaID)
//	{
//		// Creates the selection pane
//		Composite selection
//	}
	
	/** Constructs the dialog list */
	QuickAlignFilterPane(Composite parent, Mapping mapping, SelectionListener listener)
	{
		// Constructs the filter pane
		super(parent, SWT.NONE);
		setLayout(new GridLayout(2,false));
		
		// Constructs the selection pane
		Composite selectionPane = new Composite(this, SWT.NONE);
		selectionPane.setLayout(new GridLayout(2,false));
		BasicWidgets.createTextField(selectionPane, "Source", mapping.getSourceId());
		BasicWidgets.createTextField(selectionPane, "Target", mapping.getTargetId());		
		
		// Construct the save button
		Button button = BasicWidgets.createButton(this, "Align", listener);
		button.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));		
	}
}