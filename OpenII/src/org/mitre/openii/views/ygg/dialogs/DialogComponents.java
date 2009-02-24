package org.mitre.openii.views.ygg.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/** Constructs the Import Schema Dialog */
public class DialogComponents
{
	/** Creates the specified label */
	static Label createLabel(Composite parent, String text)
	{
		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		label.setText(text+": ");
		return label;
	}
	
	/** Creates the specified text field */
	static Text createTextField(Composite parent, String label)
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
	static Text createTextField(Composite parent, String fieldLabel, Integer rows)
	{
		// Display the label
		Label label = createLabel(parent,fieldLabel);
		GridData labelGridData = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_BEGINNING);
		labelGridData.verticalIndent = 3;
		label.setLayoutData(labelGridData);
		
		// Display the text
		Text field = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		GridData fieldGridData = new GridData(GridData.FILL_HORIZONTAL);
		fieldGridData.heightHint = new GC(field).getFontMetrics().getHeight()*rows;
		field.setLayoutData(fieldGridData);
		return field;
	}
	
	/** Creates the specified file field */
	static Text createFileField(Composite parent, String label, SelectionListener listener)
	{
		// Create the label
		createLabel(parent,label);
		
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
		Button button = new Button(pane, SWT.NONE);
		button.setText("Get File...");
		button.addSelectionListener(listener);
		return fileField;
	}
}