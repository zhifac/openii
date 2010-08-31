package org.mitre.openii.dialogs.projects.unity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.dialogs.projects.unity.DisjointSetForest.ContainerMethod;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.OptionsPanel;
import org.mitre.openii.widgets.schemaList.RankLabelProvider;
import org.mitre.openii.widgets.schemaList.SchemaLabelProvider;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Vocabulary;

public class GenerateVocabularyDialog extends TitleAreaDialog implements ModifyListener, ActionListener {

	/**
	 * Given a set of mappings and schemas in a project, figure out the
	 * connectedness, or minimum spanning tree in the mappings
	 * 
	 * @param project
	 * @return HashMap<Integer, ArrayList<Integer>> The key is some root id
	 *         which is arbitrary and doesn't matter much. The array that
	 *         follows indicates the group of schema IDs that are connected to
	 *         each other at least with one linkage.
	 */
	static public Collection<ArrayList<Integer>> getMSTMappingGroup(Project project) {
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

		return schemaGroups.values();
	}

	private Project project;
	private Text authorField;
	private Text vocabName;
	private HashMap<Integer, String> schemaNames;
	private OptionsPanel groupOptions;
	private HashMap<String, ArrayList<Integer>> mappingGroups = new HashMap<String, ArrayList<Integer>>();
	private TableViewer list;

	public GenerateVocabularyDialog(Shell parentShell, Project project) {
		super(parentShell);
		// setShellStyle(SWT.RESIZE);
		this.project = project;

		// Initialize schema names
		schemaNames = new HashMap<Integer, String>();
		for (Integer i : project.getSchemaIDs())
			schemaNames.put(i, OpenIIManager.getSchema(i).getName());

	}

	/** Configures the dialog shell */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Generate Vocabulary");
		setTitleImage(OpenIIActivator.getImage("GenerateVocab.gif"));
	}

	/** Creates the contents for the Edit Project Dialog */
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		authorField.setText(System.getProperty("user.name"));
		vocabName.setText("Vocabulary for " + project.getName());
		return control;
	}

	/** Creates the contents for the Import Schema Dialog */
	protected Control createDialogArea(Composite parent) {
		setTitle("Generating vocabulary... ");
		setMessage("Current mappings provide the following groups of schemas. \nSelect a group to generate a vocabulary. ");

		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);

		// Set the pane layout
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 8;
		pane.setLayout(layout);

		// Define the pane width
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 450;
		pane.setLayoutData(gridData);

		// Generate the pane components
		createInfoPane(pane);
		createMappingGroupPane(pane);

		return pane;
	}

	/**
	 * Create mapping group selection panel
	 * 
	 * @param parent
	 */
	private void createMappingGroupPane(Composite parent) {
		// Initialize mapping group
		Collection<ArrayList<Integer>> MSTGroups = GenerateVocabularyDialog.getMSTMappingGroup(project);

		Group mappingGroupPane = new Group(parent, SWT.NONE);
		mappingGroupPane.setText("Mapping Groups");
		mappingGroupPane.setLayout(new GridLayout(1, false));
		mappingGroupPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Create the options panel for mapping groups
		int groupIDX = 0;
		int maxGroupSize = 0;
		String selectedOption = "";
		groupOptions = new OptionsPanel(mappingGroupPane);
		groupOptions.addListener(this);
		for (ArrayList<Integer> schemas : MSTGroups) {
			// Add the radio button for selecting a group.
			String key = "Group " + (groupIDX + 1) + " schemas";
			Composite schemaPane = groupOptions.addOption(key);
			schemaPane.setLayout(new GridLayout(1, false));
			schemaPane.setLayoutData(new GridData(GridData.FILL_VERTICAL));
			mappingGroups.put(key, schemas);

			// Add selection to the largest group
			if (schemas.size() > maxGroupSize) {
				maxGroupSize = schemas.size();
				selectedOption = "Group " + (groupIDX + 1) + " schemas";
			}

			createSchemaTableView(schemaPane, schemas);
			groupIDX++;
		}
		groupOptions.setOption(selectedOption);
	}

	private void createSchemaTableView(Composite schemaPane, ArrayList<Integer> schemas) {
		list = new TableViewer(schemaPane, SWT.BORDER | SWT.FULL_SELECTION);
		Table table = list.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		String[] headers = new String[] { "Schema", "Rank" };
		ArrayList<TableViewerColumn> columns = new ArrayList<TableViewerColumn>();
		// Add the list headers
		for (String header : headers) {
			TableViewerColumn column = new TableViewerColumn(list, SWT.NONE);
			column.getColumn().setText(header);
			column.getColumn().setWidth(200);
			columns.add(column);
		}

		columns.get(0).setLabelProvider(new SchemaLabelProvider());
		HashMap<Integer, Integer> rankings = new HashMap<Integer, Integer>();
		for (int i = 0; i < schemas.size(); i++)
			rankings.put(schemas.get(i), i + 1);
		columns.get(1).setLabelProvider(new RankLabelProvider(rankings));
		columns.get(1).setEditingSupport(new EditingSupport(list){

			protected boolean canEdit(Object arg0) {return true; }

			protected CellEditor getCellEditor(Object arg0) {
				return new TextCellEditor((Table)list.getControl());  }  

			protected Object getValue(Object arg0) {
				return (Integer) arg0; 
			}

			protected void setValue(Object arg0, Object arg1) {
			}
			
		});

		for (int i = 0; i < schemas.size(); i++)
			list.add(OpenIIManager.getSchema(schemas.get(i)));

	}

	private void createInfoPane(Composite parent) {
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("General Info");
		pane.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		pane.setLayoutData(gridData);

		// Generate the properties to be displayed by the info pane
		vocabName = BasicWidgets.createTextField(pane, "Vocabulary");
		authorField = BasicWidgets.createTextField(pane, "Author");

		// Add listeners to the fields to monitor for changes
		vocabName.addModifyListener(this);
		authorField.addModifyListener(this);
	}

	public void modifyText(ModifyEvent e) {
		validateFields();
	}

	public void actionPerformed(ActionEvent e) {

		validateFields();
	}

	private void validateFields() {
		boolean complete = (authorField.getText().length() > 0);
		complete &= (groupOptions.getOption().length() > 0);
		getButton(IDialogConstants.OK_ID).setEnabled(complete);
	}

	// /** Handles the actual import of the specified file */
	protected void okPressed() {
		// Get the group of mappings that include schemas in the selected group
		ArrayList<Integer> schemas = mappingGroups.get(groupOptions.getOption());
		ArrayList<Mapping> mappings = OpenIIManager.getMappings(project.getId());
		ArrayList<Mapping> selectedMapping = new ArrayList<Mapping>();

		for (Mapping m : mappings)
			if (schemas.contains(m.getSourceId()) && schemas.contains(m.getTargetId())) {
				selectedMapping.add(m);
				System.out.println(" Added mappings " + m.getSourceId() + " and " + m.getTargetId());
			} else
				System.out.println(" --- mappings " + m.getSourceId() + " and " + m.getTargetId());

		// Generate and save Vocabulary
		UnityDSF unity = new UnityDSF(project);
		Vocabulary vocab = unity.unify(selectedMapping);
		OpenIIManager.saveVocabulary(vocab);

		getShell().dispose();
	}
}
