package org.mitre.openii.dialogs.projects.unity;

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
import org.mitre.harmony.matchers.matchers.Matcher;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;

class AutoMappingPage extends WizardPage implements ModifyListener,
		SelectionListener {

	private Text authorField;
	private Text descriptionField;
	private ArrayList<MatcherCheckBox> matcherCheckBoxes = new ArrayList<MatcherCheckBox>();
	private ArrayList<String> selectedVoters = new ArrayList<String>();
	private ArrayList<Pair<ProjectSchema>> newMappingList = new ArrayList<Pair<ProjectSchema>>();
	private ArrayList<Pair<ProjectSchema>> existingMappingList = new ArrayList<Pair<ProjectSchema>>();
	private HashMap<String, Pair<ProjectSchema>> mappingHash = new HashMap<String, Pair<ProjectSchema>>();

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
		Project project = ((GenerateVocabularyWizard) this.getWizard())
				.getProject();
		HashMap<Integer, ProjectSchema> schemas = ((GenerateVocabularyWizard) this
				.getWizard()).getSchemas();
		Permuter<ProjectSchema> permuter = new Permuter<ProjectSchema>(
				new ArrayList<ProjectSchema>(schemas.values()));

		// Display existing schemas
		Group projectSchemaGroup = new Group(parent, SWT.NONE);
		projectSchemaGroup.setText("Project Schemas");
		projectSchemaGroup.setLayout(new GridLayout(1, false));
		for (ProjectSchema schema : schemas.values())
			BasicWidgets.createText(projectSchemaGroup, null).setText(
					schema.getName());

		// Generate a group for existing mappings
		for (Mapping mapping : OpenIIManager.getMappings(project.getId())) {
			ProjectSchema schema1 = schemas.get(mapping.getSourceId());
			ProjectSchema schema2 = schemas.get(mapping.getTargetId());
			if (schema1 == null || schema2 == null)
				continue;

			Pair<ProjectSchema> exclude = new Pair<ProjectSchema>(schema1,
					schema2);

			permuter.addExcludedPair(exclude);
			existingMappingList.add(exclude);

		}

		Group existGroup = new Group(parent, SWT.NONE);
		existGroup.setText("Existing Mappings");
		existGroup.setLayout(new GridLayout(1, false));
		existGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		if (existingMappingList.size() == 0)
			new Text(existGroup, SWT.ITALIC)
					.setText("No mappings have been created for the project");
		else
			for (Pair<ProjectSchema> exclude : existingMappingList) {
				Button mapped = new Button(existGroup, SWT.CHECK);
				String pairName = ((ProjectSchema) exclude.getItem1())
						.getName()
						+ " to "
						+ ((ProjectSchema) exclude.getItem2()).getName();
				mapped.setText(pairName);
				mapped.setSelection(true);
				mapped.setEnabled(true);
				mappingHash.put(pairName, exclude);
				mapped.addSelectionListener(new SelectionListener() {
					
					public void widgetSelected(SelectionEvent sevent) {
						Button mappingButton = (Button) sevent.widget;
						if (mappingButton.getSelection()) {
							existingMappingList.add(mappingHash.get(mappingButton.getText()));
						} else
							existingMappingList.remove(mappingHash.get(mappingButton.getText()));

						updatePageCompleteStatus();						
					}
					
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
			}

		// Generate group for new mappings to be added
		Group newGroup = new Group(parent, SWT.NONE);
		newGroup.setText("New Mappings To Be Added Automatically");
		newGroup.setLayout(new GridLayout(1, false));
		newGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		if (permuter.size() > 0) {
			while (permuter.hasMoreElements()) {
				Pair<ProjectSchema> pair = permuter.nextElement();
				Button unmapped = new Button(newGroup, SWT.CHECK);
				String pairName=((ProjectSchema) pair.getItem1()).getName()
				+ " to " + ((ProjectSchema) pair.getItem2()).getName();
				unmapped.setText(pairName);
				unmapped.setEnabled(true);
				unmapped.setSelection(true);
				mappingHash.put(pairName, pair ); 
				unmapped.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent sevent) {
						Button mappingButton = (Button) sevent.widget;
						if (mappingButton.getSelection()) {
							newMappingList.add(mappingHash.get(mappingButton.getText()));
						} else
							newMappingList.remove(mappingHash.get(mappingButton.getText()));

						updatePageCompleteStatus();
					}

					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub

					}
				});
			}
		} else
			new Text(newGroup, SWT.ITALIC)
					.setText("No new mappings will be added.");
	}

	ArrayList<Pair<ProjectSchema>> getNewMappings() {
		return newMappingList;
	}

	ArrayList<Pair<ProjectSchema>> getSelectedMappings() {
		ArrayList<Pair<ProjectSchema>> result = existingMappingList;
		result.addAll(newMappingList);
		return result;
	}

	private void createMatcherList(Composite parent) {
		// Construct a group for matchers
		Group group = new Group(parent, SWT.NONE);
		group.setText("Matchers");
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Construct a list of all matchers that can be selected
		for (Matcher matcher : MatcherManager.getVisibleMatchers()) {
			MatcherCheckBox checkBox = new MatcherCheckBox(group, matcher);
			checkBox.addSelectionListener(this);
			matcherCheckBoxes.add(checkBox);
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
		descriptionField = BasicWidgets
				.createTextField(group, "Description", 4);

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
		if (authorField.getText().equals(""))
			authorField.setText(System.getProperty("user.name"));

		// Set voters to a set of optimized choices (hard coded)
		for (MatcherCheckBox checkbox : matcherCheckBoxes)
			for (Matcher matcher : MatcherManager.getDefaultMatchers()) {
				if (checkbox.getName().equals(matcher.getName())) {
					checkbox.checkBox.setSelection(true);
					selectedVoters.add(checkbox.getName());
				}
			}
		updatePageCompleteStatus();
	}

	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	private void updatePageCompleteStatus() {
		setPageComplete(!(newMappingList.size() > 0 && selectedVoters.size() == 0)
				&& getSelectedMappings().size() > 0);
	}

	public void widgetSelected(SelectionEvent event) {
		if (matcherCheckBoxes.contains(event.getSource())) {
			Button voterButton = (Button) event.widget;
			if (voterButton.getSelection())
				selectedVoters.add(voterButton.getText());
			else
				selectedVoters.remove(voterButton.getText());

			updatePageCompleteStatus();
		}
	}

	public boolean canFinish() {
		return false;
	}

	/**
	 * Returns user selected matchers
	 * 
	 * @return
	 */
	ArrayList<Matcher> getMatchers() {
		ArrayList<Matcher> result = new ArrayList<Matcher>();
		for (MatcherCheckBox checkBox : matcherCheckBoxes)
			if (checkBox.checkBox.getSelection())
				result.add(checkBox.matcher);
		return result;
	}

	String getAuthor() {
		return authorField.getText();
	}

	String getMappingDescription() {
		return descriptionField.getText();
	}

	private class MatcherCheckBox {
		// Stores the matcher associated with this checkbox
		private Matcher matcher;
		private Button checkBox;

		/** Initializes the matcher check box */
		MatcherCheckBox(Composite parent, Matcher matcher) {
			checkBox = new Button(parent, SWT.CHECK);
			this.matcher = matcher;
			checkBox.setText(matcher.getName());
		}

		void addSelectionListener(SelectionListener listener) {
			checkBox.addSelectionListener(listener);
		}

		String getName() {
			return matcher.getName();
		}

	}

}
