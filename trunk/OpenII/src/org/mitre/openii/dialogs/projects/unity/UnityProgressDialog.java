package org.mitre.openii.dialogs.projects.unity;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.application.OpenIIActivator;

public class UnityProgressDialog extends Dialog implements UnityListener, Runnable {

	private Shell shell;
	private CLabel message;
	protected String processMessage = "Generating vocabulary ......";
	protected String shellTitle = "Progress for vocabulary generation";
	private Composite progressBarComposite;
	protected int processBarStyle = SWT.SMOOTH;
	private ProgressBar progressBar;
	private Label processMessageLabel;
	private Composite cancelComposite;
	private Button cancelButton;
	private boolean mayCancel = true;
	private boolean isClosed = false;

	public UnityProgressDialog(Shell parent) {
		super(parent);
	}

	public void run() {
		createContents(); // create window
		shell.open();
		shell.layout();
	}

	public void killDialog() {
		isClosed = true;
		shell.getParent().dispose();
		shell.close();
		shell.dispose();
	}

	public boolean isClosed() {
		return isClosed;
	}

	synchronized protected void createContents() {
		shell = new Shell(getParent(), SWT.TITLE | SWT.PRIMARY_MODAL );
		shell.setText(shellTitle);
		shell.setImage(OpenIIActivator.getImage("GenerateVocab.gif"));

		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 10;
		shell.setLayout(gridLayout);
		shell.setSize(483, 181);
		shell.setLocation(shell.getParent().getLocation().x, shell.getParent().getLocation().y);

		final Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		composite.setLayout(new GridLayout());

		message = new CLabel(composite, SWT.NONE);
		message.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		message.setText(processMessage);

		progressBarComposite = new Composite(shell, SWT.NONE);
		progressBarComposite.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		progressBarComposite.setLayout(new FillLayout());

		progressBar = new ProgressBar(progressBarComposite, processBarStyle);
		progressBar.setMaximum(100);

		processMessageLabel = new Label(shell, SWT.NONE);
		processMessageLabel.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));

		// Cancel button
		cancelComposite = new Composite(shell, SWT.NONE);
		cancelComposite.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		cancelComposite.setLayout(gridLayout_1);

		cancelButton = new Button(cancelComposite, SWT.NONE);
		cancelButton.setLayoutData(new GridData(78, SWT.DEFAULT));
		cancelButton.setText("Cancel");
		cancelButton.setEnabled(mayCancel);
		cancelButton.addListener(SWT.Selection, new Listener() {
			synchronized public void handleEvent(Event arg0) {
				System.err.println("Cancel key pressed.");
				killDialog();
			}
		});

	}

	synchronized public void updateProgress(Integer percentComplete) {
		progressBar.setSelection(percentComplete);
	}

	synchronized public void updateProgressMessage(String m) {
		processMessageLabel.setText(m);
	}

	synchronized public void updateComplete(boolean completed) {
		// DO Nothing
	}

}
