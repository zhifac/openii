package org.mitre.openii.views.manager.projects.unity;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.projects.matchmaker.Pair;
import org.mitre.openii.views.manager.projects.matchmaker.Permuter;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;

class AutoMappingPage extends WizardPage implements ModifyListener, SelectionListener {

	private Text authorField;
	private Text descriptionField;
	private ArrayList<MatchVoterCheckBox> voterCheckBoxes = new ArrayList<MatchVoterCheckBox>();
	private ArrayList<String> selectedVoters = new ArrayList<String>();
	private ArrayList<Pair<ProjectSchema>> newMappings = new ArrayList<Pair<ProjectSchema>>();

	public AutoMappingPage() {
		super("AutoMappingPage");
		setTitle("Auto Mapping");
		setDescription("Ensure that all pair-wise mappings exists for all of the schemas in selected project.");
	}

	/** Constructs the Mapping Properties page */
	public void createControl(Composite parent) {
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.NONE);
		pane.setLayout(new GridLayout(1, false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Generate the pane components
		createInfoPane(pane);
		createMatcherList(pane);
		createMappingList(pane);

		// Make the default importer selections
		setDefaultFields();

		// // Displays the control
		setControl(pane);
	}

	/**
	 * Not all of the mappings exists in the project yet. This display should
	 * list the mappings that already exist and the ones that will be
	 * automatically generated.
	 * 
	 * @param pane
	 */
	private void createMappingList(Composite parent) {
		Project project = ((GenerateVocabularyWizard) this.getWizard()).getProject();
		// Generate a hash of project schemas
		HashMap<Integer, ProjectSchema> schemas = new HashMap<Integer, ProjectSchema>();
		for (ProjectSchema schema : project.getSchemas())
			schemas.put(schema.getId(), schema);
		Permuter<ProjectSchema> permuter = new Permuter<ProjectSchema>(new ArrayList<ProjectSchema>(schemas.values()));
		ArrayList<Pair<ProjectSchema>> excludeList = new ArrayList<Pair<ProjectSchema>>();

		// Generate a group for existing mappings
		for (Mapping mapping : OpenIIManager.getMappings(project.getId())) {
			ProjectSchema schema1 = schemas.get(mapping.getSourceId());
			ProjectSchema schema2 = schemas.get(mapping.getTargetId());
			Pair<ProjectSchema> exclude = new Pair<ProjectSchema>(schema1, schema2);
			permuter.addExcludedPair(exclude);
			excludeList.add(exclude);
		}

		Group existGroup = new Group(parent, SWT.NONE);
		existGroup.setText("Existing Mappings");
		existGroup.setLayout(new GridLayout(1, false));
		existGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		if (excludeList.size() == 0) {
			Text msg = new Text(existGroup, SWT.ITALIC);
			msg.setText("No mappings have been created for the project");
		} else for (Pair<ProjectSchema> exclude : excludeList) {
			Button mapped = new Button(existGroup, SWT.CHECK);
			mapped.setText(((ProjectSchema) exclude.getItem1()).getName() + " to " + ((ProjectSchema) exclude.getItem2()).getName());
			mapped.setSelection(true);
			mapped.setEnabled(false);
		}

		// Generate group for new mappings to be added
		if (permuter.size() > excludeList.size()) {
			Group newGroup = new Group(parent, SWT.NONE);
			newGroup.setText("New Mappings To Be Added Automatically");
			newGroup.setLayout(new GridLayout(1, false));
			newGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			while (permuter.hasMoreElements()) {
				Pair<ProjectSchema> pair = permuter.nextElement();
				Button unmapped = new Button(newGroup, SWT.CHECK);
				unmapped.setText(((ProjectSchema) pair.getItem1()).getName() + " to " + ((ProjectSchema) pair.getItem2()).getName());
				unmapped.setEnabled(false);
				unmapped.setSelection(true);
				newMappings.add(pair);
			}
		}
	}

	ArrayList<Pair<ProjectSchema>> getNewMappings() {
		return newMappings;
	}

	private void createMatcherList(Composite parent) {
		// Construct a group for matchers
		Group group = new Group(parent, SWT.NONE);
		group.setText("Matchers");
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Construct a list of all voters that can be selected
		for (MatchVoter voter : MatcherManager.getVisibleVoters()) {
			MatchVoterCheckBox checkBox = new MatchVoterCheckBox(group, voter);
			checkBox.addSelectionListener(this);
			voterCheckBoxes.add(checkBox);
		}
	}

	/** Creates the importer info pane */
	private void createInfoPane(Composite parent) {
		// Construct the pane for showing the info for the selected importer
		Group group = new Group(parent, SWT.NONE);
		group.setText("Properties");
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Generate the properties to be displayed by the info pane
		authorField = BasicWidgets.createTextField(group, "Author");
		descriptionField = BasicWidgets.createTextField(group, "Description", 4);

		// Add listeners to the fields to monitor for changes
		authorField.addModifyListener(this);
		descriptionField.addModifyListener(this);
	}

	public void modifyText(ModifyEvent event) {
		updatePageCompleteStatus();
	}

	// Select default author and selected matchers
	public void setDefaultFields() {
		// Set author to system user name by default
		if (authorField.getText().equals("")) authorField.setText(System.getProperty("user.name"));

		// Set voters to a set of optimized choices (hard coded)
		for (MatchVoterCheckBox checkbox : voterCheckBoxes)
			if (checkbox.getName().equals("Documentation Similarity") || checkbox.getName().equals("Documentation + Synonyms") || checkbox.getName().equals("Name Similarity") || checkbox.getName().equals("Exact Structure")) {
				checkbox.checkBox.setSelection(true);
				selectedVoters.add(checkbox.getName());
			}
	}

	public void widgetDefaultSelected(SelectionEvent arg0) {}

	private void updatePageCompleteStatus() {
		setPageComplete(selectedVoters.size() > 0 && authorField.getText().trim().length() > 0 && descriptionField.getText().trim().length() > 0);
	}

	public void widgetSelected(SelectionEvent event) {
		Button voterButton = (Button) event.widget;
		if (voterButton.getSelection()) selectedVoters.add(voterButton.getText());
		else selectedVoters.remove(voterButton.getText());

		updatePageCompleteStatus();
	}

	/**
	 * Returns user selected match voters
	 * 
	 * @return
	 */
	ArrayList<MatchVoter> getMatchVoters() {
		ArrayList<MatchVoter> result = new ArrayList<MatchVoter>();
		for (MatchVoterCheckBox checkBox : voterCheckBoxes)
			if (checkBox.checkBox.getSelection()) result.add(checkBox.voter);
		return result;
	}

	String getAuthor() {
		return authorField.getText();
	}

	String getMappingDescription() {
		return descriptionField.getText();
	}

	private class MatchVoterCheckBox {
		// Stores the voter associated with this checkbox
		private MatchVoter voter;
		private Button checkBox;

		/** Initializes the match voter check box */
		MatchVoterCheckBox(Composite parent, MatchVoter voter) {
			checkBox = new Button(parent, SWT.CHECK);
			this.voter = voter;
			checkBox.setText(voter.getName());
		}

		void addSelectionListener(SelectionListener listener) {
			checkBox.addSelectionListener(listener);
		}

		String getName() {
			return voter.getName();
		}

	}

}
