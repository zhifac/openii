package org.mitre.openii.editors.mappings.quickAlign;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.matchers.MatchersPane;
import org.mitre.openii.widgets.schemaTree.SchemaTree;
import org.mitre.openii.widgets.schemaTree.SchemaTreeDialog;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;

/** Constructs the QuickAlign filter pane */
public class QuickAlignFilterPane extends Composite implements SelectionListener
{	
	// Stores reference to the various widgets in this pane
	private SchemaFocusPane sourcePane, targetPane;
	private Button calculateButton, saveButton; 
	private MatchersPane matchers = null;
	
	/** Private class for storing a schema's focus */
	private class SchemaFocusPane extends Composite implements MouseListener
	{
		/** Stores the filtered schema as referenced by this focus pane */
		private FilteredSchemaInfo schema;
		
		/** Constructs the schema focus pane */
		private SchemaFocusPane(Composite parent, String label, Integer schemaID, SchemaModel model)
		{
			super(parent, SWT.NONE);
			schema = new FilteredSchemaInfo(new HierarchicalSchemaInfo(OpenIIManager.getSchemaInfo(schemaID),model));
			GridLayout layout = new GridLayout(2,false);
			layout.marginHeight=0; layout.marginLeft=0;
			setLayout(layout);
			
			// Create the label
			new Label(this, SWT.NONE).setText(label + ":");
			
			// Create the displayed schema focus
			Label focus = new Label(this, SWT.NONE);
			focus.setText("<Entire Schema>");
			focus.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
			focus.setCursor(new Cursor(Display.getCurrent(), SWT.CURSOR_HAND));
			focus.addMouseListener(this);
		}
		
		/** Returns the schema */
		FilteredSchemaInfo getSchema()
			{ return schema; }

		/** Allows user to select the schema focus */
		public void mouseUp(MouseEvent e)
		{
			// Prepare the schema tree dialog
			SchemaTreeDialog dialog = new SchemaTreeDialog(getShell(),schema,schema.getModel());
			ArrayList<SchemaElement> rootIDs = schema.getFilteredRootElements();
			dialog.getSchemaTree().setSelectedElement(rootIDs.size()>0 ? rootIDs.get(0).getId() : null);

			// Launch the schema tree dialog
			if(dialog.open()==Window.OK)
			{
				SchemaTree tree = dialog.getSchemaTree();
				schema.setModel(tree.getSchema().getModel());
				schema.setFilteredRoots(new ArrayList<Integer>(Arrays.asList(new Integer[]{tree.getSelectedElement()})));
			}
		}
		
		// Unused event listeners
		public void mouseDoubleClick(MouseEvent e) {}
		public void mouseDown(MouseEvent e) {}
	}
	
	/** Constructs the QuickAlign filter pane */
	QuickAlignFilterPane(Composite parent, Mapping mapping, SelectionListener listener)
	{
		// Constructs the filter pane
		super(parent, SWT.NONE);
		setLayout(new GridLayout(1,false));
		setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Constructs the focus pane
		Composite focusPane = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout(3,false);
		layout.marginHeight=0; layout.marginWidth=0;
		focusPane.setLayout(layout);
		focusPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Creates the focus panes
		Project project = OpenIIManager.getProject(mapping.getProjectId());
		sourcePane = new SchemaFocusPane(focusPane, "Source Focus", mapping.getSourceId(), project.getSchemaModel(mapping.getSourceId()));
		targetPane = new SchemaFocusPane(focusPane, "Target Focus", mapping.getTargetId(), project.getSchemaModel(mapping.getTargetId()));
		
		// Constructs the button pane
		Composite buttonPane = new Composite(focusPane, SWT.NONE);
		layout = new GridLayout(2,false);
		layout.marginHeight=0; layout.marginWidth=0;
		buttonPane.setLayout(layout);
		buttonPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_END));		
		calculateButton = BasicWidgets.createButton(buttonPane, "Calculate Alignment", listener);
		saveButton = BasicWidgets.createButton(buttonPane, "Save Mapping", listener);
		saveButton.setEnabled(false);
		
		// Constructs the matcher pane
		matchers = new MatchersPane(this,this);
	}

	/** Returns the source schema */
	FilteredSchemaInfo getSourceSchema()
		{ return sourcePane.getSchema(); }
	
	/** Returns the target schema */
	FilteredSchemaInfo getTargetSchema()
		{ return targetPane.getSchema(); }
	
	/** Handles changes to the matchers pane */
	public void widgetSelected(SelectionEvent e)
		{ calculateButton.setEnabled(matchers.getMatchers().size()>0); }
	
	// Unused event listeners
	public void widgetDefaultSelected(SelectionEvent e) {}
}