package org.mitre.openii.views.manager.projects.unity;

/**
 * Creates 
 * 
 * @author HAOLI
 */
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;

class MatchMakerPage extends WizardPage implements ModifyListener, SelectionListener {

	private Text vocabNameField;
	private Text authorField;
	private Text descriptionField;
	private Text fileSelectionPane;

	public MatchMakerPage() {
		super("Match Maker Page");
		setTitle("Vocabulary Information");
		setDescription("Generate vocabulary with Unity.");
		setPageComplete(false); 
	}

	/** Constructs the Mapping Properties page */
	public void createControl(Composite parent) {
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.NONE);
		pane.setLayout(new GridLayout(1, false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// construct a group to hold information for match maker
		Group group = new Group(pane, SWT.NONE);
		group.setText("Properties");
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		vocabNameField = BasicWidgets.createTextField(group, "Vocabulary Name");
		authorField = BasicWidgets.createTextField(group, "authorField"); 
		descriptionField = BasicWidgets.createTextField(group, "Description", 5);

		Group fileGroup = new Group(pane, SWT.NONE); 
		fileGroup.setText("Optional export"); 
		fileGroup.setLayout(new GridLayout(2, false));
		fileGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fileSelectionPane = BasicWidgets.createFileField(fileGroup, "Export to file", this	);   
		
		// default values
		if (authorField.getText().equals("")) authorField.setText(System.getProperty("user.name"));
		if (vocabNameField.getText().equals("")) vocabNameField.setText( ((GenerateVocabularyWizard)(getWizard())).getProject().getName()+"_Vocabulary");
		if (fileSelectionPane.getText().equals("")) fileSelectionPane.setText("C://" + getVocabularyName() + ".xls"); 
		
		vocabNameField.addModifyListener(this); 
		authorField.addModifyListener(this); 
		descriptionField.addModifyListener(this); 
		
		// // Displays the control
		setControl(pane);
	}
	
	
	@Override
	public boolean canFlipToNextPage() { return false; }
	
	public String getVocabularyName(){return vocabNameField.getText(); }
	public String getAuthor() { return authorField.getText(); }
	public String getDescription() { return descriptionField.getText(); }
	public String getVocabExportFilePath() { return fileSelectionPane.getText(); } 

	public void modifyText(ModifyEvent arg0) {
		setPageComplete(getVocabularyName().length() > 0 && getAuthor().length() > 0 && getDescription().length() > 0);
	}

	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void widgetSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
	}	
}
