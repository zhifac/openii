package org.mitre.openii.dialogs.projects;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.harmony.matchers.matchers.Matcher;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.dialogs.projects.unity.DisjointSetForest;
import org.mitre.openii.dialogs.projects.unity.DisjointSetForest.ContainerMethod;
import org.mitre.openii.dialogs.projects.unity.MappingList;
import org.mitre.openii.dialogs.projects.unity.MappingProcessor;
import org.mitre.openii.dialogs.projects.unity.Pair;
import org.mitre.openii.dialogs.projects.unity.Permuter;
import org.mitre.openii.dialogs.projects.unity.ProgressBarDialog;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.matchers.MatchersPane;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;

/**
 * Allows the user to run multiple mappings in one shot, with the same matchers
 * (each can be customized)
 * 
 * @author HAOLI
 * 
 */

public class BatchMatchDialog extends TitleAreaDialog implements SelectionListener {

	private static final long serialVersionUID = 5519403988358398471L;

	private Project project;
	private HashMap<String, Pair<ProjectSchema>> mappingHash = new HashMap<String, Pair<ProjectSchema>>();
	private MappingList selectedNewMappingList = new MappingList();
	private MappingList existingMappingList = new MappingList(); 

	private MatchersPane matchersPane;
	private Text authorField;

	private ArrayList<Button> mappingButtons;

	private Group schemaGroupPane;

	private Group mappingPane;
	private Composite projectPane0;
	
	private Group existMappingPane;
	private Composite projectPane;
	
	public BatchMatchDialog(Shell shell, Project project) {
		super(shell);
		setShellStyle(SWT.RESIZE | SWT.TITLE | SWT.CLOSE );
		this.project = project;
	}

	/** Configures the dialog shell */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		setTitleImage(OpenIIActivator.getImage("BatchMatch.gif"));
		shell.setText("Batch Generate Mappings");
	}

	/** Creates the contents for the Edit Project Dialog */
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		authorField.setText("(Auto set by matchers)");
		authorField.setText(MappingProcessor.matchersToString(getMatchers())); 
		authorField.setEnabled(false); 
		return control;
	}

	/** Creates the contents for the Import Schema Dialog */
	protected Control createDialogArea(Composite parent) {
		setTitle("Batch Generate Mappings");
		setMessage("Generate multiple mappings with customize matchers. Disabled mappings already exist. ");

		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM );

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
		createMappingPane(pane);
		createMappingGroupPane(pane);
		matchersPane = new MatchersPane(pane, this);

		return pane;
	}

	private void createInfoPane(Composite parent) {
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("General Info");
		pane.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		pane.setLayoutData(gridData);

		// Generate the properties to be displayed by the info pane
		authorField = BasicWidgets.createTextField(pane, "Author",2);
	}

	private void createMappingGroupPane(Composite parent) {
		schemaGroupPane = new Group(parent, SWT.NONE);
		schemaGroupPane.setText("Mapping Groups");
		schemaGroupPane.setToolTipText("Each group demonstrates connected schemas");
		schemaGroupPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		schemaGroupPane.setRedraw(true);
		updateMappingGroupPane();
	}

	private void createMappingPane(Composite pane) {
		ProjectSchema[] schemas = project.getSchemas();
		HashMap<Integer, ProjectSchema> schemaArray = new HashMap<Integer, ProjectSchema>();

		for (ProjectSchema ps : schemas)
			schemaArray.put(ps.getId(), ps);

		for (Mapping mapping : OpenIIManager.getMappings(project.getId())) {
			ProjectSchema schema1 = schemaArray.get(mapping.getSourceId());
			ProjectSchema schema2 = schemaArray.get(mapping.getTargetId());
			if (schema1 != null && schema2 != null)
				existingMappingList.add(new Pair<ProjectSchema>(schema1, schema2));
		}

		Permuter<ProjectSchema> permuter = new Permuter<ProjectSchema>(new ArrayList<ProjectSchema>(schemaArray.values()));

		// New mapping pane
		mappingPane = new Group(pane, SWT.NONE);
		mappingPane.setText("Select new mappings to create");
		mappingPane.setLayout(new GridLayout(1, false));
		GridData gd0 = new GridData(GridData.FILL_HORIZONTAL);
		gd0.heightHint = 100;
		mappingPane.setLayoutData(gd0);

		ScrolledComposite scrolledPane0 = new ScrolledComposite(mappingPane, SWT.BORDER | SWT.V_SCROLL);
		scrolledPane0.setLayoutData(new GridData(GridData.FILL_BOTH));
		projectPane0 = new Composite(scrolledPane0, SWT.NONE);
		projectPane0.setLayout(new GridLayout(1,false));

		
		
		// exist-mapping pane
		existMappingPane = new Group(pane, SWT.NONE);
		existMappingPane.setText("Existing mappings will be replaced if checked");
		existMappingPane.setLayout(new GridLayout(1,false));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 100;
		existMappingPane.setLayoutData(gridData);

		// Construct the scrolling pane for showing the mappings available for merging
		ScrolledComposite scrolledPane = new ScrolledComposite(existMappingPane, SWT.BORDER | SWT.V_SCROLL);
		scrolledPane.setLayoutData(new GridData(GridData.FILL_BOTH));
		// Creates a project pane
		projectPane = new Composite(scrolledPane, SWT.NONE);
		projectPane.setLayout(new GridLayout(1,false));
		
		
		
		// Create a check box for each mapping pair
		while (permuter.hasMoreElements()) {
			Pair<ProjectSchema> currMappingPair = permuter.nextElement();
			boolean exist = existingMappingList.contains(currMappingPair);
			String pairName = getMappingHashKey(currMappingPair);

			mappingHash.put(pairName, currMappingPair);
			Button mappingButton;
			//Button mappingButtonTest;
			if (!exist) {
				//mappingButton = new Button(mappingPane, SWT.CHECK);
				mappingButton = new Button(projectPane0, SWT.CHECK);
				mappingButton.setSelection(true);				
				selectedNewMappingList.add(currMappingPair);
			} else {
				//mappingButton = new Button(existMappingPane, SWT.CHECK);
				mappingButton = new Button(projectPane, SWT.CHECK);
				mappingButton.setSelection(false);
			}
			mappingButton.setData(currMappingPair);
			mappingButton.setText(pairName);


			mappingButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					// Do nothing
				}

				public void widgetSelected(SelectionEvent event) {
					Button mappingButton = (Button) event.widget;
					if (mappingButton.getSelection())
						selectedNewMappingList.add(mappingHash.get(mappingButton.getText()));
					else
						selectedNewMappingList.remove(mappingHash.get(mappingButton.getText()));
					updateMappingGroupPane();
					updateButton();
				}
			});
			
		}
		
		// Adjust scroll panes to fit content
		scrolledPane0.setContent(projectPane0);
		scrolledPane0.setExpandVertical(true);
		scrolledPane0.setExpandHorizontal(true);
		scrolledPane0.setMinSize(projectPane0.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		scrolledPane.setContent(projectPane);
		scrolledPane.setExpandVertical(true);
		scrolledPane.setExpandHorizontal(true);
		scrolledPane.setMinSize(projectPane.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		

	}

	private String getMappingHashKey(Pair<ProjectSchema> currMappingPair) {
		return ((ProjectSchema) currMappingPair.getItem1()).getName() + " to " + ((ProjectSchema) currMappingPair.getItem2()).getName();
	}

	String getAuthor() {
		return authorField.getText();
	}

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
	HashMap<Integer, ArrayList<Integer>> getMappingMST(Integer[] schemas, MappingList mapped) {
		HashMap<Integer, ArrayList<Integer>> schemaGroups = new HashMap<Integer, ArrayList<Integer>>();
		ContainerMethod<Integer> method = new ContainerMethod<Integer>() {
			public int getContainerFor(Integer v) {
				return v;
			}
		};

		DisjointSetForest<Integer> dsf = new DisjointSetForest<Integer>(schemas, method, schemas.length);
		for (Pair<ProjectSchema> mapping : mapped.getList())
			dsf.merge(mapping.getItem1().getId(), mapping.getItem2().getId(), false);

		for (Integer schemaId : schemas) {
			Integer root = new Integer(dsf.find(schemaId));
			if (!schemaGroups.containsKey(root))
				schemaGroups.put(root, new ArrayList<Integer>());
			schemaGroups.get(root).add(schemaId);
		}

		return schemaGroups;
	}

	/** Returns the selected matchers */
	ArrayList<Matcher> getMatchers() {
		return matchersPane.getMatchers();
	}

	MappingList getSelectedNewMappings() {
		return selectedNewMappingList;
	}


	// /** Handles the actual import of the specified file */
	protected void okPressed() {
		ArrayList<Mapping> mappings = OpenIIManager.getMappings(project.getId());

		// delete old mappings that are to be replaced
		Integer item1, item2, source, target;
		for (Pair<ProjectSchema> pair : selectedNewMappingList.getList()) {
			if (existingMappingList.contains(pair)) {
				item1 = pair.getItem1().getId();
				item2 = pair.getItem2().getId();
				for (Mapping mapping : mappings) {
					source = mapping.getSourceId();
					target = mapping.getTargetId();
					if ((item1.equals(source) && item2.equals(target)) || (item1.equals(target) && item2.equals(source)))
						OpenIIManager.deleteMapping(mapping.getId());
				}
			}
		}

		
		// create new mappings
		MappingProcessor mappingProcess = new MappingProcessor(project, getMatchers(), getSelectedNewMappings());

		// Close the generate vocab shell
		getShell().dispose();
		
		// create a new progress bar 
		ProgressBarDialog progressBar = new ProgressBarDialog(getParentShell());
		progressBar.setProcessMessage("Generating batch matches..."); 
		progressBar.open(); 
		
		mappingProcess.addProgressListener(progressBar);
		getParentShell().getDisplay().syncExec(mappingProcess); 
		progressBar.updateProgressMessage("Finished!"); 
		
		// Sleep a second to allow the message to be shown
		try {Thread.sleep(2000);} catch (InterruptedException e) {} 
		progressBar.killDialog();
		
		mappingProcess.run(); 
		
		// clean up
		this.mappingHash.clear();
		this.mappingButtons.clear();
		System.gc();
		getShell().dispose();
	}

	/** Updates the page complete status */
	private void updateButton() {
		boolean complete = authorField.getText().length() > 0;
		complete &= selectedNewMappingList.size() > 0;
		complete &= getMatchers().size() > 0;
		getButton(IDialogConstants.OK_ID).setEnabled(complete);
	}

	/** updates the mapping group pane with the latest minimal spanning tree **/ 
	private void updateMappingGroupPane() {
		
		MappingList mstMappings = new MappingList();
		mstMappings.addAll(existingMappingList);
		for (Pair<ProjectSchema> newMapping : selectedNewMappingList.getList())
			if (!mstMappings.contains(newMapping))
				mstMappings.add(newMapping);

		// Update the minimum spanning tree for mappings
		HashMap<Integer, ArrayList<Integer>> schemaGroups = getMappingMST(project.getSchemaIDs(), mstMappings);

		// Dispose old mapping groups
		if (mappingButtons != null && mappingButtons.size() > 0)
			for (Button b : mappingButtons)
				b.dispose();
		mappingButtons = new ArrayList<Button>();

		// Set new layout according to new grouping size
		schemaGroupPane.setLayout(new GridLayout(schemaGroups.keySet().size(), false));

		// Toggles the highlighted mapping group buttons
		Listener groupSelectionListener = new Listener() {
			public void handleEvent(Event e) {
				Button button = (Button) e.widget;
				ArrayList<Integer> schemaGroup = (ArrayList<Integer>) button.getData();
				Control[] children = schemaGroupPane.getChildren();

				// reset all of the selections to be off if the button was to
				// turn off
				if (!button.getSelection()) {
					button.setSelection(false);
					//for (Control mappingButton : mappingPane.getChildren())
					for(Control mappingButton : projectPane0.getChildren())
						((Button) mappingButton).setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

					//for (Control mappingButton : existMappingPane.getChildren())
					for (Control mappingButton : projectPane.getChildren())
						((Button) mappingButton).setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

					return;

				}

				// Otherwise activate the button
				for (int i = 0; i < children.length; i++)
					if (e.widget != children[i] && children[i] instanceof Button && (children[i].getStyle() & SWT.TOGGLE) != 0)
						((Button) children[i]).setSelection(false);
				button.setSelection(true);

				// Highlight the mapping cells
				for(Control mappingButton : projectPane0.getChildren()){
					Pair<ProjectSchema> pair = (Pair<ProjectSchema>) ((Button) mappingButton).getData();
					if (schemaGroup.contains(pair.getItem1().getId()) || schemaGroup.contains(pair.getItem2().getId()))
						((Button) mappingButton).setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
					else
						((Button) mappingButton).setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
				}

				for (Control mappingButton : projectPane.getChildren()) {
					Pair<ProjectSchema> pair = (Pair<ProjectSchema>) ((Button) mappingButton).getData();
					if (schemaGroup.contains(pair.getItem1().getId()) || schemaGroup.contains(pair.getItem2().getId()))
						((Button) mappingButton).setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
					else
						((Button) mappingButton).setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
				}
			}
		};

		// Paint groups with stars indicating schemas
		for (ArrayList<Integer> group : schemaGroups.values()) {
			String stars = "";
			for (int i = 0; i < group.size(); i++)
				stars += "*";

			// Create a button that contains the schema group
			Button schemaGroupButton = new Button(schemaGroupPane, SWT.TOGGLE);
			schemaGroupButton.setText(stars);
			schemaGroupButton.setData(group);
			schemaGroupButton.addListener(SWT.Selection, groupSelectionListener);

			mappingButtons.add(schemaGroupButton);
			new MappingGroupToolTip(schemaGroupButton, group);
		}

		schemaGroupPane.layout(true, true);
	}

	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void widgetSelected(SelectionEvent e) {
		authorField.setText(getAutoAuthorString()); 
		updateButton();
	}
	
	/** Returns the author as auto generated by matcher names **/ 
	private String getAutoAuthorString() {
		return MappingProcessor.matchersToString(getMatchers());
	}

	/** Displays the matcher parameters */
	public class MappingGroupToolTip extends ToolTip {
		/** Stores the matcher who's parameters are being displayed */
		private ArrayList<Integer> schemaIDs;

		/** Constructs the parameters display */
		public MappingGroupToolTip(Control parent, ArrayList<Integer> mappingGroupIDs) {
			super(parent);
			this.schemaIDs = mappingGroupIDs;
			setShift(new Point(10, 10));
		}

		/** Displays the parameters */
		protected Composite createToolTipContentArea(Event e, Composite parent) {
			Composite pane = new Composite(parent, SWT.NONE);
			pane.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			pane.setLayout(new GridLayout(1, false));

			// Display the matcher parameters
			for (Integer schemaID : schemaIDs) {
				Label parameter = new Label(pane, SWT.NONE);
				parameter.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				parameter.setText(OpenIIManager.getSchema(schemaID).getName());
			}

			return pane;
		}
	}

}
