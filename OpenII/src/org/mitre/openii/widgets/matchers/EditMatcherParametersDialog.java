package org.mitre.openii.widgets.matchers;

import java.util.HashMap;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.mitre.harmony.matchers.matchers.Matcher;
import org.mitre.harmony.matchers.parameters.MatcherCheckboxParameter;
import org.mitre.harmony.matchers.parameters.MatcherParameter;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.OptionPane;

/** Constructs the Edit Matcher Parameters Dialog */
public class EditMatcherParametersDialog extends Dialog
{
	/** Stores the matcher being edited */
	private Matcher matcher = null;
	
	// Stores the various matcher parameters
	private HashMap<MatcherParameter,OptionPane> parameters = new HashMap<MatcherParameter,OptionPane>();
	
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
		for(MatcherParameter parameter : matcher.getParameters())
		{
			// Only display check box parameters
			if(parameter instanceof MatcherCheckboxParameter)
			{
				OptionPane optionPane = BasicWidgets.createRadioField(parameterPane, parameter.getText(), new String[]{"true","false"}, null);
				optionPane.setOption(((MatcherCheckboxParameter)parameter).isSelected() ? "true" : "false");
				parameters.put(parameter,optionPane);
			}
		}
		
		// Return the generated pane
		return pane;
	}

	/** Handles the actual import of the specified file */
	protected void okPressed()
	{
		for(MatcherParameter parameter : parameters.keySet())
		{
			OptionPane optionPane = parameters.get(parameter);
			parameter.setValue(optionPane.getOption());
		}
		getShell().dispose();
	}
}