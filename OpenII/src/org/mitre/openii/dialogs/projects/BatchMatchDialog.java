package org.mitre.openii.dialogs.projects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.dialogs.projects.unity.Pair;
import org.mitre.openii.dialogs.projects.unity.Permuter;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.matchers.MatchersPane;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;

public class BatchMatchDialog extends Dialog implements ActionListener, ModifyListener, SelectionListener {

	private static final long serialVersionUID = 5519403988358398471L;
	private Project project;
	private MatchersPane matchersPane;
	private Text authorField;
	private Text descriptionField;
	private ArrayList<Pair<ProjectSchema>> selectedNewMappingList = new ArrayList<Pair<ProjectSchema>>();
	private HashMap<String, Pair<ProjectSchema>> mappingHash = new HashMap<String, Pair<ProjectSchema>>();

	public BatchMatchDialog(Shell shell, Project project) {
		super(shell);
		this.project = project;
	}

	/** Configures the dialog shell */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("BatchMatch.gif"));
		shell.setText("Batch Generate Mappings");
	}

	/** Creates the contents for the Import Schema Dialog */
	protected Control createDialogArea(Composite parent) {
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.fill = true;
		pane.setLayout(layout);

		// Generate the pane components
		createInfoPane(pane);
		createMappingPane(pane);
		// matchersPane = new MatchersPane(pane, this);

		// Return the generated pane
		return pane;
	}

	private void createMappingPane(Composite pane) {
		ProjectSchema[] schemas = project.getSchemas();
		HashMap<Integer, ProjectSchema> schemaArray = new HashMap<Integer, ProjectSchema>();
		ArrayList<Pair<ProjectSchema>> existingMappingList = new ArrayList<Pair<ProjectSchema>>();

		for (ProjectSchema ps : schemas)
			schemaArray.put(ps.getId(), ps);

		for (Mapping mapping : OpenIIManager.getMappings(project.getId())) {
			ProjectSchema schema1 = schemaArray.get(mapping.getSourceId());
			ProjectSchema schema2 = schemaArray.get(mapping.getTargetId());
			Pair<ProjectSchema> pair = new Pair<ProjectSchema>(schema1, schema2);
			if (schema1 != null && schema2 != null)
				existingMappingList.add(pair);
		}

		Permuter<ProjectSchema> permuter = new Permuter<ProjectSchema>(new ArrayList<ProjectSchema>(schemaArray.values()));

		Group mappingGroup = new Group(pane, SWT.NONE);
		mappingGroup.setText("Project Mappings");
		mappingGroup.setLayout(new GridLayout(1, false));

		// Create a check box for each mapping pair
		while (permuter.hasMoreElements()) {
			Pair<ProjectSchema> currMappingPair = permuter.nextElement();
			Button mappingButton = new Button(mappingGroup, SWT.CHECK);
			String pairName = ((ProjectSchema) currMappingPair.getItem1()).getName() + " to " + ((ProjectSchema) currMappingPair.getItem2()).getName();
			mappingHash.put(pairName, currMappingPair);
			boolean exist = existingMappingList.contains(currMappingPair);
			mappingButton.setText(pairName);
			mappingButton.setSelection(true);
			mappingButton.setEnabled(!exist);
			if (!exist)
				selectedNewMappingList.add(currMappingPair);

			mappingButton.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent event) {
					Button mappingButton = (Button) event.widget;
					if (mappingButton.getSelection())
						selectedNewMappingList.add(mappingHash.get(mappingButton.getText()));
					else
						selectedNewMappingList.remove(mappingHash.get(mappingButton.getText()));
					updateButton();
				}

				public void widgetDefaultSelected(SelectionEvent event) {
					// Do nothing
				}
			});
		}
	}

	public ArrayList<Pair<ProjectSchema>> getSelectedNewMappings() {
		return selectedNewMappingList;
	}

	private void createInfoPane(Composite parent) {
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("General Info");
		pane.setLayout(new GridLayout(2, false));

		// Generate the properties to be displayed by the info pane
		authorField = BasicWidgets.createTextField(pane, "Author");
		descriptionField = BasicWidgets.createTextField(pane, "Description", 4);

		// Add listeners to the fields to monitor for changes
		authorField.addModifyListener(this);
		descriptionField.addModifyListener(this);
	}

	/** Creates the contents for the Edit Project Dialog */
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		authorField.setText(System.getProperty("user.name"));
		return control;
	}

	// /** Handles the actual import of the specified file */
	protected void okPressed() {
	}

	public void modifyText(ModifyEvent arg0) {
		updateButton();
	}

	/** Updates the page complete status */
	private void updateButton() {
		boolean complete = authorField.getText().length() > 0;
		complete &= selectedNewMappingList.size() > 0;
		getButton(IDialogConstants.OK_ID).setEnabled(complete);
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void widgetSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
	}

}
