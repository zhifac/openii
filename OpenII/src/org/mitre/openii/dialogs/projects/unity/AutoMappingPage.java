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
import org.mitre.harmony.matchers.matchers.Matcher;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.matchers.MatchersPane;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;

class AutoMappingPage extends WizardPage implements ModifyListener, SelectionListener {
	private Text authorField = null;
	private Text descriptionField = null;
	private MatchersPane matchersPane = null;
	private ArrayList<Pair<ProjectSchema>> newMappingList = new ArrayList<Pair<ProjectSchema>>();
	private ArrayList<Pair<ProjectSchema>> existingMappingList = new ArrayList<Pair<ProjectSchema>>();
	private HashMap<String, Pair<ProjectSchema>> mappingHash = new HashMap<String, Pair<ProjectSchema>>();

	/** Constructs the auto-mapping pane */
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
		matchersPane = new MatchersPane(pane, this);
		createMappingList(pane);

		// Set default author if none given
		if (authorField.getText().equals(""))
			authorField.setText(System.getProperty("user.name"));
		updatePageCompleteStatus();

		// Displays the control
		setControl(pane);
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

	/**
	 * Not all of the mappings exists in the project yet. This display should
	 * list the mappings that already exist and the ones that will be
	 * automatically generated.
	 */
	private void createMappingList(Composite parent) {
		Project project = ((GenerateVocabularyOldWizard) this.getWizard()).getProject();
		HashMap<Integer, ProjectSchema> schemas = ((GenerateVocabularyOldWizard) this.getWizard()).getSchemas();
		Permuter<ProjectSchema> permuter = new Permuter<ProjectSchema>(new ArrayList<ProjectSchema>(schemas.values()));

		// Display existing schemas
		Group projectSchemaGroup = new Group(parent, SWT.NONE);
		projectSchemaGroup.setText("Project Schemas");
		projectSchemaGroup.setLayout(new GridLayout(1, false));
		for (ProjectSchema schema : schemas.values())
			BasicWidgets.createText(projectSchemaGroup, null).setText(schema.getName());

		// Generate a group for existing mappings
		for (Mapping mapping : OpenIIManager.getMappings(project.getId())) {
			ProjectSchema schema1 = schemas.get(mapping.getSourceId());
			ProjectSchema schema2 = schemas.get(mapping.getTargetId());
			if (schema1 == null || schema2 == null)
				continue;

			Pair<ProjectSchema> exclude = new Pair<ProjectSchema>(schema1, schema2);

			existingMappingList.add(exclude);
		}

		Group existGroup = new Group(parent, SWT.NONE);
		existGroup.setText("Existing Mappings");
		existGroup.setLayout(new GridLayout(1, false));
		existGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		if (existingMappingList.size() == 0)
			new Text(existGroup, SWT.ITALIC).setText("No mappings have been created for the project");
		else
			for (Pair<ProjectSchema> exclude : existingMappingList) {
				Button mapped = new Button(existGroup, SWT.CHECK);
				String pairName = ((ProjectSchema) exclude.getItem1()).getName() + " to " + ((ProjectSchema) exclude.getItem2()).getName();
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
				if (existingMappingList.contains(pair))
					continue;
				newMappingList.add(pair);

				Button unmapped = new Button(newGroup, SWT.CHECK);
				String pairName = ((ProjectSchema) pair.getItem1()).getName() + " to " + ((ProjectSchema) pair.getItem2()).getName();
				unmapped.setText(pairName);
				unmapped.setEnabled(true);
				unmapped.setSelection(true);
				mappingHash.put(pairName, pair);
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
					}
				});
			}
		} else
			new Text(newGroup, SWT.ITALIC).setText("No new mappings will be added.");
	}

	ArrayList<Pair<ProjectSchema>> getNewMappings() {
		return newMappingList;
	}

	ArrayList<Pair<ProjectSchema>> getSelectedMappings() {
		ArrayList<Pair<ProjectSchema>> result = new ArrayList<Pair<ProjectSchema>>();
		result.addAll(existingMappingList);
		result.addAll(newMappingList);
		return result;
	}

	/** Returns the mapping author */
	String getAuthor() {
		return authorField.getText();
	}

	/** Returns the mapping description */
	String getMappingDescription() {
		return descriptionField.getText();
	}

	/** Returns the selected matchers */
	ArrayList<Matcher> getMatchers() {
		return matchersPane.getMatchers();
	}

	/** Rechecks if the page is complete when text is modified */
	public void modifyText(ModifyEvent event) {
		updatePageCompleteStatus();
	}

	/** Rechecks if the page is complete when matchers are selected */
	public void widgetSelected(SelectionEvent arg0) {
		updatePageCompleteStatus();
	}

	/** Updates the page complete status */
	private void updatePageCompleteStatus() {
		boolean notComplete = newMappingList.size() > 0 && matchersPane.getMatchers().size() == 0;
		setPageComplete(!notComplete && getSelectedMappings().size() > 0);
	}

	// Unused event listener
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}
}
