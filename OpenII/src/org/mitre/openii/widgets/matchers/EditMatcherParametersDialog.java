package org.mitre.openii.widgets.matchers;

import java.util.HashMap;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.mitre.harmony.matchers.MatcherOption;
import org.mitre.harmony.matchers.MatcherOption.OptionType;
import org.mitre.harmony.matchers.matchers.Matcher;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.OptionPane;

/** Constructs the Edit Matcher Parameters Dialog */
public class EditMatcherParametersDialog extends Dialog
{
	/** Stores the matcher being edited */
	private Matcher matcher = null;
	
	// Stores the various dialog fields
	private HashMap<String,OptionPane> parameterFields = new HashMap<String,OptionPane>();
	
	/** Constructs the dialog */
	public EditMatcherParametersDialog(Shell shell, Matcher matcher)
		{ super(shell); this.matcher=matcher; }

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("SchemaGroup.gif"));
		shell.setText("Edit Parameters");
	}
	
	/** Creates the contents for the Edit Tag Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		pane.setLayout(layout);
	
		// Construct the parameter pane
		Composite parameterPane = new Composite(pane, SWT.NONE);
		layout = new GridLayout(2,false);
		layout.marginWidth = 8;
		parameterPane.setLayout(layout);
		parameterPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Constructs the parameter fields
		for(MatcherOption parameter : matcher.getOptions())
		{
			// Only display check box parameters
			if(parameter.getType().equals(OptionType.CHECKBOX))
			{
				OptionPane optionPane = BasicWidgets.createRadioField(parameterPane, parameter.getName(), new String[]{"true","false"}, null);
				optionPane.setOption(parameter.isSelected() ? "true" : "false");
				parameterFields.put(parameter.getName(),optionPane);
			}
		}
		
		// Return the generated pane
		return pane;
	}

	/** Handles the actual import of the specified file */
	protected void okPressed()
	{
		for(String field : parameterFields.keySet())
		{
			OptionPane optionPane = parameterFields.get(field);
			matcher.setOption(field, optionPane.getOption());
		}
		getShell().dispose();
	}
}