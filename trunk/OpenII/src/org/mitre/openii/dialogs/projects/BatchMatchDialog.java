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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.harmony.matchers.matchers.Matcher;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.dialogs.projects.unity.DisjointSetForest;
import org.mitre.openii.dialogs.projects.unity.DisjointSetForest.ContainerMethod;
import org.mitre.openii.dialogs.projects.unity.MappingProcessor;
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
	private ArrayList<Pair<ProjectSchema>> checkedMappingList = new ArrayList<Pair<ProjectSchema>>();
	private HashMap<String, Pair<ProjectSchema>> mappingHash = new HashMap<String, Pair<ProjectSchema>>();
	private Group mappingGroupPane;
	private ArrayList<Group> groupPanes;

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

		GridLayout layout = new GridLayout(1, false);
		pane.setLayout(layout);
		layout.marginLeft = 8;
		layout.marginRight = 8;

		// Generate the pane components
		createInfoPane(pane);
		createMappingPane(pane);
		createMappingGroupPane(pane);
		matchersPane = new MatchersPane(pane, this);
		return pane;
	}

	private void createMappingGroupPane(Composite parent) {
		mappingGroupPane = new Group(parent, SWT.NONE);
		mappingGroupPane.setText("Mapping Groups");
		mappingGroupPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		mappingGroupPane.setRedraw(true);
		updateMappingGroupPane();
	}

	void updateMappingGroupPane() {
		// Update the minimum spanning tree for mappings 
		HashMap<Integer, ArrayList<Integer>> schemaGroups = getSchemaGroups(project.getSchemaIDs(), checkedMappingList);
		
		// Dispose old mapping groups
		if (groupPanes != null && groupPanes.size() > 0)
			for ( Group g : groupPanes ) g.dispose(); 
		groupPanes = new ArrayList<Group>();
		
		// Set new layout according to new grouping size
		mappingGroupPane.setLayout(new GridLayout(schemaGroups.keySet().size(), false ));

		// Paint groups with dots indicating schemas 
		for (ArrayList<Integer> group : schemaGroups.values()) {
			Group groupPane = new Group(mappingGroupPane, SWT.NONE);
			groupPanes.add(groupPane); 
			GridLayout layout = new GridLayout(group.size(), true);
			groupPane.setLayout(layout);

			for (Integer schemaID : group) {
				Label slabel = new Label(groupPane, SWT.NONE);
				slabel.setText("*");
				slabel.setToolTipText(OpenIIManager.getSchema(schemaID).getName());
			}
			mappingGroupPane.layout(true, true);
		}
	}

	// Integer[] schemas, ArrayList<Pair<ProjectSchema>> merges
	HashMap<Integer, ArrayList<Integer>> getSchemaGroups(Integer[] schemas, ArrayList<Pair<ProjectSchema>> merges) {
		HashMap<Integer, ArrayList<Integer>> schemaGroups = new HashMap<Integer, ArrayList<Integer>>();
		ContainerMethod<Integer> method = new ContainerMethod<Integer>() {
			public int getContainerFor(Integer v) {
				return v;
			}
		};

		DisjointSetForest<Integer> dsf = new DisjointSetForest<Integer>(schemas, method, schemas.length);
		for (Pair<ProjectSchema> mapping : merges)
			dsf.merge(mapping.getItem1().getId(), mapping.getItem2().getId(), false);

		for (Integer schemaId : schemas) {
			Integer root = new Integer(dsf.find(schemaId));
			if (!schemaGroups.containsKey(root))
				schemaGroups.put(root, new ArrayList<Integer>());
			schemaGroups.get(root).add(schemaId);
		}

		return schemaGroups;

	}

	HashMap<Integer, ArrayList<Integer>> getSchemaGroups(Project project) {
		HashMap<Integer, ArrayList<Integer>> schemaGroups = new HashMap<Integer, ArrayList<Integer>>();
		Integer[] schemas = project.getSchemaIDs();
		ContainerMethod<Integer> method = new ContainerMethod<Integer>() {
			public int getContainerFor(Integer v) {
				return v;
			}
		};

		DisjointSetForest<Integer> dsf = new DisjointSetForest<Integer>(schemas, method, schemas.length);
		for (Mapping mapping : OpenIIManager.getMappings(project.getId())) {
			dsf.merge(mapping.getSourceId(), mapping.getTargetId(), false);
		}

		for (Integer schemaId : schemas) {
			Integer root = new Integer(dsf.find(schemaId));
			if (!schemaGroups.containsKey(root))
				schemaGroups.put(root, new ArrayList<Integer>());
			schemaGroups.get(root).add(schemaId);
		}

		return schemaGroups;
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

		Group mappingPane = new Group(pane, SWT.NONE);
		mappingPane.setText("Selet new mappings to create");
		mappingPane.setLayout(new GridLayout(1, false));
		mappingPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Create a check box for each mapping pair
		while (permuter.hasMoreElements()) {
			Pair<ProjectSchema> currMappingPair = permuter.nextElement();
			Button mappingButton = new Button(mappingPane, SWT.CHECK);
			String pairName = ((ProjectSchema) currMappingPair.getItem1()).getName() + " to " + ((ProjectSchema) currMappingPair.getItem2()).getName();
			mappingHash.put(pairName, currMappingPair);
			boolean exist = existingMappingList.contains(currMappingPair);
			mappingButton.setText(pairName);
			mappingButton.setSelection(true);
			mappingButton.setEnabled(!exist);
			if (!exist)
				selectedNewMappingList.add(currMappingPair);
			checkedMappingList.add(currMappingPair);

			mappingButton.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent event) {
					Button mappingButton = (Button) event.widget;
					if (mappingButton.getSelection()) {
						selectedNewMappingList.add(mappingHash.get(mappingButton.getText()));
						checkedMappingList.add(mappingHash.get(mappingButton.getText()));
					} else {
						selectedNewMappingList.remove(mappingHash.get(mappingButton.getText()));
						checkedMappingList.remove(mappingHash.get(mappingButton.getText()));
					}
					updateMappingGroupPane();
					updateButton();
				}

				public void widgetDefaultSelected(SelectionEvent event) {
					// Do nothing
				}
			});
		}
	}

	ArrayList<Pair<ProjectSchema>> getSelectedNewMappings() {
		return selectedNewMappingList;
	}

	ArrayList<Pair<ProjectSchema>> getSelectedMappings() {
		return checkedMappingList;
	}

	private void createInfoPane(Composite parent) {
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("General Info");
		pane.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 400;
		pane.setLayoutData(gridData);

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

	/** Returns the selected matchers */
	ArrayList<Matcher> getMatchers() {
		return matchersPane.getMatchers();
	}

	// /** Handles the actual import of the specified file */
	protected void okPressed() {
		new MappingProcessor(project, getSelectedNewMappings(), getMatchers());
		getShell().dispose();
	}

	public void modifyText(ModifyEvent arg0) {
		updateButton();
	}

	/** Updates the page complete status */
	private void updateButton() {
		boolean complete = authorField.getText().length() > 0;
		complete &= selectedNewMappingList.size() > 0;
		complete &= getMatchers().size() > 0;
		getButton(IDialogConstants.OK_ID).setEnabled(complete);
	}

	String getAuthor() {
		return authorField.getText();
	}

	String getDescription() {
		return descriptionField.getText();
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void widgetSelected(SelectionEvent arg0) {
		updateButton();
	}

}
