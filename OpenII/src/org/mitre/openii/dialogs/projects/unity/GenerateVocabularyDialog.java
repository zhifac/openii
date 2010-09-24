package org.mitre.openii.dialogs.projects.unity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.dialogs.projects.unity.DisjointSetForest.ContainerMethod;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.OptionsPanel;
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
	private HashMap<Integer, String> schemaNames;
	private OptionsPanel groupOptions;
	private HashMap<String, Table> mappingGroups = new HashMap<String, Table>();

	public GenerateVocabularyDialog(Shell parentShell, Project project) {
		super(parentShell);
		// setShellStyle(SWT.RESIZE);
		this.project = project;

		// Initialize schema names
		schemaNames = new HashMap<Integer, String>();
		for (Integer i : project.getSchemaIDs()) {
			schemaNames.put(i, OpenIIManager.getSchema(i).getName());
		}

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
		authorField.setText("Set by matchers.");
		return control;
	}

	/** Creates the contents for the Import Schema Dialog */
	protected Control createDialogArea(Composite parent) {
		setTitle("Generating vocabulary... ");
		setMessage("Select a group to generate a vocabulary. Rank the schemas to \n" + "determine vocabulary authoritative source.  ");

		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);

		// Set the pane layout
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 8;
		pane.setLayout(layout);

		// Define the pane width
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 300;
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

			// Add selection to the largest group
			if (schemas.size() > maxGroupSize) {
				maxGroupSize = schemas.size();
				selectedOption = "Group " + (groupIDX + 1) + " schemas";
			}

			Table table = createSchemaRankTable(schemaPane, schemas);

			mappingGroups.put(key, table);
			groupIDX++;
		}
		groupOptions.setOption(selectedOption);
		groupOptions.getOption();
	}

	/**
	 * Create a table with schemas and its ranking of importance. Allow user to
	 * rank
	 **/
	private Table createSchemaRankTable(final Composite schemaPane, final ArrayList<Integer> schemas) {
		final Table table = new Table(schemaPane, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// Add columns
		TableColumn schemaColumn = new TableColumn(table, SWT.NONE, 0);
		schemaColumn.setText("Schema");
		schemaColumn.setWidth(150);

		TableColumn rankColumn = new TableColumn(table, SWT.NONE, 1);
		rankColumn.setText("Rank");
		rankColumn.setWidth(100);

		// Populate each row with schema name and ranking
		final int schemaCol = 0;
		final int rankCol = 1;
		TableItem tableItem;
		for (int i = 0; i < schemas.size(); i++) {
			tableItem = new TableItem(table, SWT.NONE);
			tableItem.setImage(schemaCol, OpenIIActivator.getImage("Schema.gif"));
			tableItem.setText(schemaCol, OpenIIManager.getSchema(schemas.get(i)).getName());
			tableItem.setData("id", schemas.get(i));
			tableItem.setText(rankCol, Integer.toString(i + 1));
		}

		// Add double click and edit text listener to ranking column
		final TableEditor editor = new TableEditor(table);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;

		// change cursor to hand for mouseover ranking editor event
		table.addListener(SWT.MouseHover, new Listener() {
			public void handleEvent(Event event) {
				Point pt = new Point(event.x, event.y);

				for (int tableItemIDX = table.getTopIndex(); tableItemIDX < table.getItemCount(); tableItemIDX++) {
					// check if clicked in ranking column
					final TableItem item = table.getItem(tableItemIDX);
					Rectangle rect = item.getBounds(rankCol);
					if (rect.contains(pt))
						table.setCursor(new Cursor(Display.getCurrent(), SWT.CURSOR_HAND));
				}
			}
		});

		// create combo options that allows the user to rank the schemas
		table.addListener(SWT.MouseDoubleClick, new Listener() {
			public void handleEvent(Event event) {
				// Get event location
				Rectangle clientArea = table.getClientArea();
				Point pt = new Point(event.x, event.y);

				for (int tableItemIDX = table.getTopIndex(); tableItemIDX < table.getItemCount(); tableItemIDX++) {
					// check if clicked in ranking column
					boolean visible = false;
					final TableItem item = table.getItem(tableItemIDX);
					Rectangle rect = item.getBounds(rankCol);
					if (rect.contains(pt)) {
						final CCombo combo = new CCombo(table, SWT.READ_ONLY);
						String[] options = new String[schemas.size()];
						for (int i = 0; i < schemas.size(); i++) {
							options[i] = Integer.toString(i + 1);
							combo.add(options[i]);
						}

						combo.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								// Reorder the rows after a new assignment
								reorderTableRows(item, Integer.parseInt(item.getText(rankCol)) - 1, Integer.parseInt(combo.getText()) - 1);
								combo.dispose();
							}

							private void reorderTableRows(TableItem item, int oldIndex, int newIndex) {
								String updateItemName = item.getText(schemaCol);
								if (oldIndex == newIndex)
									return;

								// Move items backward if the item is moved
								// forward
								else if (oldIndex > newIndex) {
									for (int i = oldIndex; i > newIndex; i--) {
										table.getItem(i).setText(schemaCol, table.getItem(i - 1).getText());
										table.getItem(i).setText(rankCol, Integer.toString(i + 1));
									}
								}

								// Move items forward if the item is moved
								// backward
								else if (oldIndex < newIndex) {
									for (int i = oldIndex; i < newIndex; i++) {
										table.getItem(i).setText(schemaCol, table.getItem(i + 1).getText());
										table.getItem(i).setText(rankCol, Integer.toString(i + 1));
									}
								}

								// Finally update the moved item to the new row
								table.getItem(newIndex).setText(schemaCol, updateItemName);
								table.getItem(newIndex).setText(rankCol, Integer.toString(newIndex + 1));
								table.setSelection(newIndex);
							}
						});

						combo.setFocus();
						combo.select(combo.indexOf(item.getText(rankCol)));
						editor.setEditor(combo, item, rankCol);
						return;
					}
					if (!visible && rect.intersects(clientArea))
						visible = true;

					if (!visible)
						return;
				}
			}
		});

		table.setFocus();

		return table;
	}

	private void createInfoPane(Composite parent) {
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("General Info");
		pane.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		pane.setLayoutData(gridData);

		// Generate the properties to be displayed by the info pane
		authorField = BasicWidgets.createTextField(pane, "Author");
		authorField.setEnabled(false);

		// Add listeners to the fields to monitor for changes
		// vocabName.addModifyListener(this);
		// authorField.addModifyListener(this);
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
		ArrayList<Integer> rankedSchemas = getRankedSchemas();
		ArrayList<Mapping> mappings = OpenIIManager.getMappings(project.getId());
		ArrayList<Mapping> selectedMapping = new ArrayList<Mapping>();

		for (Mapping m : mappings)
			if (rankedSchemas.contains(m.getSourceId()) && rankedSchemas.contains(m.getTargetId()))
				selectedMapping.add(m);

		// Close the generate vocab shell
		getShell().dispose();

		// Start a progress bar to monitor unity process
		ProgressBarDialog progressDialog = new ProgressBarDialog(getParentShell());
		progressDialog.setProcessMessage("Generating vocabulary ..." ); 
		progressDialog.open(); 

		// Start a new thread to generate the vocabulary
		UnityDSF unity = new UnityDSF(project, selectedMapping, rankedSchemas);
		unity.addListener(progressDialog);
		
		// Asynchronously run unity from the display 
		getParentShell().getDisplay().syncExec(unity);

		Vocabulary vocab  = unity.getVocabulary();
		if ( vocab!= null && !progressDialog.isClosed() ) {
			// Save the vocabulary
			progressDialog.updateProgressMessage("Saving the vocabulary...");
			OpenIIManager.saveVocabulary(unity.getVocabulary());
			progressDialog.updateProgressMessage("Vocabulary completed!! ");

			// Sleep a second to allow the message to be shown
			try {Thread.sleep(2000);} catch (InterruptedException e) {} 
			
			// Close progress bar
			progressDialog.killDialog();
		}
		
		// kill the threads
		rankedSchemas = null;
		selectedMapping = null;
		mappings = null;
		schemaNames.clear();
		project = null;
		mappingGroups.clear();
		groupOptions.dispose();
		System.gc();
	}

	/** Get the selected schema group and user ranks **/
	private ArrayList<Integer> getRankedSchemas() {
		ArrayList<Integer> schemas = new ArrayList<Integer>();
		Table schemaTable = mappingGroups.get(groupOptions.getOption());
		System.out.println("okpressed: ");
		for (TableItem item : schemaTable.getItems()) {
			System.out.println((Integer) item.getData("id") + ": " + item.getText(0));
			schemas.add((Integer) item.getData("id"));
		}
		return schemas;
	}
}
