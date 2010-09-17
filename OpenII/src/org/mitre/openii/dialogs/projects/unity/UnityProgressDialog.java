package org.mitre.openii.dialogs.projects.unity;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Vocabulary;

public class UnityProgressDialog extends Dialog implements UnityListener  {

	private Shell shell;
	private CLabel message;
	protected String processMessage = "process......";
	protected String shellTitle = "Progress..."; //
	private Composite progressBarComposite;
	protected int processBarStyle = SWT.SMOOTH;
	private ProgressBar progressBar;
	private Label processMessageLabel;
	private Composite cancelComposite;
	private Button cancelButton;
	protected volatile boolean isClosed = false;// closed state
	private boolean mayCancel = true;
	private Project project;
	private ArrayList<Integer> rankedSchemas;
	private ArrayList<Mapping> mappings;
	private UnityDSF unity;

	public UnityProgressDialog(Shell parent, Project project, ArrayList<Integer> schemaSequence, ArrayList<Mapping> selectedMapping) {
		super(parent);
		this.project = project; 
		this.rankedSchemas = schemaSequence; 
		this.mappings = selectedMapping; 
	}

	public Object open() {
		createContents(); // create window
		shell.open();
		shell.layout();
		Vocabulary vocab = null;
		unity = new UnityDSF(project, mappings, rankedSchemas);
		

		// Start a new thread to generate the vocabuary 
		unity.addListener(this); 
		unity.start(); 
		vocab = unity.getVocabulary(); 
		OpenIIManager.saveVocabulary(vocab);
		
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()  ) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		
		return vocab;
	}

	protected void createContents() {
		shell = new Shell(getParent(), SWT.TITLE | SWT.PRIMARY_MODAL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 10;
		shell.setLayout(gridLayout);
		shell.setSize(483, 181);

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

		cancelComposite = new Composite(shell, SWT.NONE);
		cancelComposite.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		cancelComposite.setLayout(gridLayout_1);

		cancelButton = new Button(cancelComposite, SWT.NONE);
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				isClosed = true;
				unity.stopThread(); 
				shell.dispose(); 
			}
		});
		cancelButton.setLayoutData(new GridData(78, SWT.DEFAULT));
		cancelButton.setText("cancel");
		cancelButton.setEnabled(mayCancel);
	}

	public void updateProgress(Double percentComplete) {
		this.progressBar.setSelection( percentComplete.intValue());
	}


	public void updateProgressMessage(String message) {
		processMessageLabel.setText(message); 
	}
	
	 

}
