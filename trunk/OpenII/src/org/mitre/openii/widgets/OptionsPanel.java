package org.mitre.openii.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/** Constructs an Options Panel composite */
public class OptionsPanel extends Composite implements SelectionListener
{
	/** Stores option panel listeners */
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	
	/** Enable/disable the control and all children controls */
	private void setEnabled(Control control, boolean enabled)
	{
		control.setEnabled(enabled);
		if(control instanceof Composite)
			for(Control childControl : ((Composite)control).getChildren())
				setEnabled(childControl, enabled);
	}
	
	/** Constructs the Options Panel composite */
	public OptionsPanel(Composite parent)
	{
		super(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1,false);
		layout.marginHeight=4; layout.marginLeft=4; layout.marginRight=0;
		setLayout(layout);
		setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}
	
	/** Adds an option to the options panel */
	public Composite addOption(String option)
	{
		// Create the option button
		Button button = new Button(this, SWT.RADIO);
		button.setText(option);
		button.addSelectionListener(this);
		
		// Create the option pane
		Composite optionPane = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout(2,false);
		layout.marginHeight=0; layout.marginLeft=15; layout.marginRight=0;
		optionPane.setLayout(layout);
		optionPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return optionPane;
	}

	/** Returns the selected option */
	public String getOption()
	{
		for(Control control : getChildren())
			if(control instanceof Button && ((Button)control).getSelection())
				return ((Button)control).getText();
		return "";
	}
	
	/** Handles the selection of an option */
	public void setOption(String option)
	{
		Control controls[] = getChildren();
		for(int i=0; i<controls.length; i++)
			if(controls[i] instanceof Button)
			{
				Button button = (Button)controls[i];
				boolean selected = button.getText().equals(option);
				button.setSelection(selected);
				setEnabled(controls[i+1],selected);
			}
	}
	
	/** Adds an options panel listener */
	public void addListener(ActionListener listener)
		{ listeners.add(listener); }
	
	/** Handles the selection of a specific button */
	public void widgetSelected(SelectionEvent e)
	{
		// Update the panel selection
		Button button = (Button)e.getSource();
		Control controls[] = getChildren();
		for(int i=0; i<controls.length; i++)
			if(controls[i].equals(button))
				setEnabled(controls[i+1], button.getSelection());
		
		// Inform listeners of the change
		for(ActionListener listener : listeners)
			listener.actionPerformed(new ActionEvent(this,0,null));
	}	
	
	// Unused event listeners
	public void widgetDefaultSelected(SelectionEvent arg0) {}
}