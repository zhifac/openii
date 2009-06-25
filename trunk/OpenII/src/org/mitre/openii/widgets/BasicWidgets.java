package org.mitre.openii.widgets;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;

/** Class for constructing basic widgets*/
public class BasicWidgets
{
	/** Class for defining how labels should be displayed in schema list */
	static public class SchemaLabelProvider implements ILabelProvider
	{
		/** Returns the image associated with the specified element */
		public Image getImage(Object element)
			{ return OpenIIActivator.getImage("Schema.gif"); }

		/** Returns the name associated with the specified element */
		public String getText(Object element)
			{ return element.toString(); }

		/** Indicates that the label is not influenced by an element property */
		public boolean isLabelProperty(Object element, String property) { return false; }
		
		// Unused functions
		public void addListener(ILabelProviderListener listener) {}
		public void dispose() {}
		public void removeListener(ILabelProviderListener listener) {}
	}
	
	/** Class for defining contents of schema list */
	static public class SchemaContentProvider implements IStructuredContentProvider
	{
		/** Defines the contents of the schema list */
		public Object[] getElements(Object arg0)
			{ return WidgetUtilities.sortList(OpenIIManager.getSchemas()).toArray(); }

		// Unused functions
		public void dispose() {}
		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {}
	}
	
	/** Creates the specified label */
	static public Label createLabel(Composite parent, String text)
	{
		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		label.setText(text+": ");
		return label;
	}

	/** Creates a text component */
	static public Text createText(Composite parent, Integer rows)
	{
		// Display the text
		Text field = new Text(parent, SWT.BORDER | (rows==null ? SWT.NONE : SWT.MULTI | SWT.V_SCROLL | SWT.WRAP));
		GridData fieldGridData = new GridData(GridData.FILL_HORIZONTAL);
		if(rows!=null) fieldGridData.heightHint = new GC(field).getFontMetrics().getHeight()*rows;
		field.setLayoutData(fieldGridData);
		return field;
	}
	
	/** Creates a button component */
	static public Button createButton(Composite parent, String label, SelectionListener listener)
	{
		// Generate the file button component
		Button button = new Button(parent, SWT.NONE);
		button.setText(label);
		button.addSelectionListener(listener);
		return button;
	}
	
	/** Creates a file component */
	static public Text createFile(Composite parent, SelectionListener listener)
	{
		// Construct the composite pane
		Composite pane = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		pane.setLayout(layout);
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the file field component
		Text fileField = new Text(pane, SWT.BORDER);
		fileField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the file button component
		createButton(pane, "Get File...", listener);

		return fileField;
	}
	
	/** Creates the specified text field */
	static public Text createTextField(Composite parent, String label)
	{
		// Display the label
		createLabel(parent,label);

		// Display the text
		Text field = new Text(parent, SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		field.setLayoutData(gridData);
		return field;
	}
	
	/** Creates the specified text field */
	static public Text createTextField(Composite parent, String fieldLabel, Integer rows)
	{
		// Display the label
		Label label = createLabel(parent,fieldLabel);
		GridData labelGridData = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_BEGINNING);
		labelGridData.verticalIndent = 3;
		label.setLayoutData(labelGridData);
		
		// Display the text
		return createText(parent,rows);
	}
	
	/** Creates the specified file field */
	static public Text createFileField(Composite parent, String label, SelectionListener listener)
	{
		createLabel(parent,label);
		return createFile(parent,listener);
	}

	/** Creates a radio field */
	static public OptionPane createRadioField(Composite parent, String label, String options[], SelectionListener listener)
	{
		createLabel(parent,label);
		return new OptionPane(parent, options, listener);
	}
	
	/** Creates a table */
	static public Table createTable(Composite parent, String fields[], ArrayList<Object[]> rows)
	{
		// Create the table
		Table table = new Table(parent, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(data);
		for(String field : fields) new TableColumn(table, SWT.NONE).setText(field);
		
		// Populate the table
		for(Object[] row : rows)
		{
			TableItem item = new TableItem(table, SWT.NONE);
			for(int i=0; i<row.length; i++)
				item.setText(i,row[i]==null ? "" : row[i].toString());
		}
		for(int i=0; i<table.getColumnCount(); i++) table.getColumn(i).pack();
		
		// Returns the create table
		return table;
	}
}