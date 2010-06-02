package org.mitre.openii.dialogs.projects.unity;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;

public class AutoMappingProgressPage extends WizardPage {

	ProgressBar progressBar;
	Text statusText;

	public AutoMappingProgressPage() {
		super("AutoMappingProgressPage");
		setTitle("Auto Mapping Progress.. ");
		setDescription("Please wait... ");
	}

	public void createControl(Composite parent) {
		Composite pane = new Composite(parent, SWT.NONE);
		pane.setLayout(new GridLayout(1, false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		progressBar = new ProgressBar(pane, SWT.HORIZONTAL);  
		progressBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		setControl(pane);
	}

	

}
