package org.mitre.openii.widgets;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/** Class for constructing basic widgets*/
public class BasicWidgets
{	
	/** Creates the specified label */
	static public Label createLabel(Composite parent, String text)
	{
		Label label = new Label(parent, SWT.RIGHT);
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
		if(rows!=null)
		{
			GC gc = new GC(field);
			fieldGridData.heightHint = gc.getFontMetrics().getHeight()*rows;
			gc.dispose();
		}
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
	
	/** Creates a combo field */
	static public ComboViewer createComboField(Composite parent, String label, Object items[], ISelectionChangedListener listener)
	{
		createLabel(parent,label);
		ComboViewer list = new ComboViewer(parent, SWT.NONE);
		for(Object item : items) list.add(item);
		list.addSelectionChangedListener(listener);
		return list;
	}
}