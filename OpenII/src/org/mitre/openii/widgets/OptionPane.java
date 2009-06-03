package org.mitre.openii.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/** Constructs an Option Pane */
public class OptionPane extends Composite
{
	/** Constructs the Option Pane */
	public OptionPane(Composite parent, String options[], SelectionListener listener)
	{
		super(parent, SWT.NONE);
		GridLayout layout = new GridLayout(options.length,true);
		layout.marginHeight = 0; layout.marginLeft = 0;
		setLayout(layout);

		// Generate the various options
		for(String option : options)
		{
			Button button = new Button(this, SWT.RADIO);
			button.setText(option);
			button.addSelectionListener(listener);
		}
		
		// Select the first option
		if(getChildren().length>0)
			((Button)getChildren()[0]).setSelection(true);
	}
	
	/** Returns the currently selected radio button */
	public String getOption()
	{
		for(Control control : getChildren())
			if(((Button)control).getSelection()) return ((Button)control).getText();
		return null;
	}
	
	/** Handles the selection of an option */
	public void setOption(String option)
	{
		Control controls[] = getChildren();
		for(int i=0; i<controls.length; i++)
		{
			Button button = (Button)controls[i];
			if(button.getText().equals(option)) button.setSelection(true);
		}
	}
}