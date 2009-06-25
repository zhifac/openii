package org.mitre.openii.views.manager.mappings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;

/** Constructs the Edit Mapping Dialog */
public class MergeMappingDialog extends Dialog implements ModifyListener
{
	/** Private class for making a mapping checkbox */
	private class MappingCheckbox implements SelectionListener
	{
		/** Stores the checkbox button */
		private Button button;
		
		/** Stores the mapping associated with the checkbox */
		private Mapping mapping;
		
		/** Constructs the mapping checkbox */
		private MappingCheckbox(Composite parent, Mapping mapping)
		{
			this.mapping = mapping;
			button = new Button(parent, SWT.CHECK);
			button.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			button.setText(mapping.getName());
			button.addSelectionListener(this);
		}

		/** Indicates if the checkbox is selected */
		private boolean isSelected()
			{ return button.getSelection(); }
		
		/** Returns the mapping associated with the checkbox */
		private Mapping getMapping()
			{ return mapping; }

		/** Handles changes to the mappings selected in the list */
		public void widgetDefaultSelected(SelectionEvent e)
			{ validateFields(); }

		/** Handles changes to the mappings selected in the list */
		public void widgetSelected(SelectionEvent e)
			{ validateFields(); }
	}

	/** Private class for comparing mapping cells */
	private class MappingCellComparator implements Comparator<MappingCell>
	{
		public int compare(MappingCell mappingCell1, MappingCell mappingCell2)
		{
			// First prioritize validated mapping cells over unvalidated mapping cells
			if(mappingCell1.getValidated()!=mappingCell2.getValidated())
				return mappingCell2.getValidated().compareTo(mappingCell1.getValidated());

			// Next prioritize higher scores over lower scores
			return mappingCell2.getScore().compareTo(mappingCell1.getScore());
		}
	}
	
	// Stores the various dialog fields
	private Text nameField = null;
	private Text authorField = null;
	private Text descriptionField = null;
	private ArrayList<MappingCheckbox> checkboxes = new ArrayList<MappingCheckbox>();

	/** Retrieves the mappings to be displayed arranged by schemas making up the mapping */
	private HashMap<String,ArrayList<Mapping>> getMappings()
	{
		HashMap<String,ArrayList<Mapping>> mappings = new HashMap<String,ArrayList<Mapping>>();
		for(Mapping mapping : OpenIIManager.getMappings())
		{
			// Get the list of schema names
			ArrayList<String> schemaNames = new ArrayList<String>();
			for(Integer schemaID : mapping.getSchemaIDs())
				schemaNames.add(OpenIIManager.getSchema(schemaID).getName());
			Collections.sort(schemaNames);

			// Generate the sorted list of schema names
			String schemaList = "";
			for(String schemaName : schemaNames) schemaList += schemaName + ", ";
			if(schemaList.length()>=2) schemaList = schemaList.substring(0, schemaList.length()-2);

			// Place the mapping into the hash map
			ArrayList<Mapping> mappingList = mappings.get(schemaList);
			if(mappingList==null) mappings.put(schemaList, mappingList = new ArrayList<Mapping>());
			mappingList.add(mapping);
		}
		return mappings;
	}

	/** Private class for merging the mapping cells */
	private ArrayList<MappingCell> mergeMappingCells(ArrayList<MappingCell> mappingCells)
	{
		// Group together mapping cells in need of merging
		HashMap<String,ArrayList<MappingCell>> mappingCellHash = new HashMap<String,ArrayList<MappingCell>>();
		for(MappingCell mappingCell : mappingCells)
		{
			// Generate hash key
			Integer element1 = mappingCell.getElement1();
			Integer element2 = mappingCell.getElement2();
			String key = element1<element2 ? element1+"-"+element2 : element2+"-"+element1;
			
			// Place mapping cell in hash map
			ArrayList<MappingCell> mappingCellList = mappingCellHash.get(key);
			if(mappingCellList==null) mappingCellHash.put(key, mappingCellList = new ArrayList<MappingCell>());
			mappingCellList.add(mappingCell);
		}
		
		// Merge mapping cells into single list
		ArrayList<MappingCell> mergedMappingCells = new ArrayList<MappingCell>();
		for(ArrayList<MappingCell> mappingCellList : mappingCellHash.values())
		{
			Collections.sort(mappingCellList, new MappingCellComparator());
			mergedMappingCells.add(mappingCellList.get(0));
		}
		return mergedMappingCells;
	}
	
	/** Constructs the dialog */
	public MergeMappingDialog(Shell shell)
		{ super(shell); }

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Mapping.gif"));
		shell.setText("Merge Mappings");
	}
	
	/** Creates the mapping info pane */
	private void createInfoPane(Composite parent)
	{
		// Construct the pane for showing the info for the selected mapping
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("General Info");
		pane.setLayout(new GridLayout(2,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		nameField = BasicWidgets.createTextField(pane,"Name");
		authorField = BasicWidgets.createTextField(pane,"Author");
		descriptionField = BasicWidgets.createTextField(pane,"Description",4);
		
		// Add listeners to the fields to monitor for changes
		nameField.addModifyListener(this);
		authorField.addModifyListener(this);
		descriptionField.addModifyListener(this);
	}
	
	/** Creates the mapping pane */
	private void createMappingPane(Composite parent)
	{
		// Construct the group pane labeling the mapping part of the layout
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("Mappings");
		pane.setLayout(new GridLayout(1,false));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 200;
		pane.setLayoutData(gridData);

		// Construct the scrolling pane for showing the mappings available for merging
		ScrolledComposite scrolledPane = new ScrolledComposite(pane, SWT.BORDER | SWT.V_SCROLL);
		scrolledPane.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Creates a mapping pane
		Composite mappingPane = new Composite(scrolledPane, SWT.NONE);
		mappingPane.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		mappingPane.setLayout(new GridLayout(1,false));
		
		// Display the mappings to choose from
		HashMap<String,ArrayList<Mapping>> mappings = getMappings();
		for(String key : mappings.keySet())
		{
			ArrayList<Mapping> mappingList = mappings.get(key);
			
			// Add spacer if between mapping groups
			if(mappingPane.getChildren().length!=0)
			{
				Label spacer = new Label(mappingPane, SWT.NONE);
				spacer.setFont(new Font(Display.getCurrent(),"Arial",2,SWT.NONE));
			}
				
			// Generate the label for the mapping group
			Label label = new Label(mappingPane, SWT.NONE);
			label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			label.setForeground(new Color(Display.getCurrent(),30,30,30));
			label.setFont(new Font(Display.getCurrent(),"Arial",8,SWT.BOLD));
			label.setText("Schemas: " + key);
			
			// Generate the check boxes for the list of mappings
			for(Mapping mapping : WidgetUtilities.sortList(mappingList))
				checkboxes.add(new MappingCheckbox(mappingPane, mapping));
		}

		// Adjust scroll panes to fit content
		scrolledPane.setContent(mappingPane);
		scrolledPane.setExpandVertical(true);
		scrolledPane.setExpandHorizontal(true);
		scrolledPane.setMinSize(mappingPane.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	/** Creates the contents for the Import Schema Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 400;
		pane.setLayoutData(gridData);
	
		// Generate the pane components
		createInfoPane(pane);
		createMappingPane(pane);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Import Schema Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);

		authorField.setText(System.getProperty("user.name"));
		validateFields();

		return control;
	}
	
	/** Monitors changes to the fields to determine when to activate the OK button */
	public void modifyText(ModifyEvent e)
		{ validateFields(); }
	
	/** Validates the fields in order to activate the OK button */
	private void validateFields()
	{
		boolean activate = false;
		for(MappingCheckbox checkbox : checkboxes)
			if(checkbox.isSelected()) { activate = true; break; }
		activate &= nameField.getText().length()>0 && authorField.getText().length()>0 &&
		   descriptionField.getText().length()>0;
		getButton(IDialogConstants.OK_ID).setEnabled(activate);
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{	
		boolean creationSuccess = true;
		
		// Gather the general information
		String name = nameField.getText();
		String author = authorField.getText();
		String description = descriptionField.getText();	
		
		// Generate the list of schemas and mapping cells which have been selected
		HashSet<MappingSchema> schemas = new HashSet<MappingSchema>();
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		for(MappingCheckbox checkbox : checkboxes)
			if(checkbox.isSelected())
			{
				Mapping mapping = checkbox.getMapping();
				schemas.addAll(Arrays.asList(mapping.getSchemas()));
				mappingCells.addAll(OpenIIManager.getMappingCells(mapping.getId()));
			}

		// Handles the creation of the mapping
		Mapping mapping = new Mapping(null,name,description,author,schemas.toArray(new MappingSchema[0]));
		ArrayList<MappingCell> mergedMappingCells = mergeMappingCells(mappingCells);
		creationSuccess = OpenIIManager.saveMapping(mapping,mergedMappingCells)!=null;

		// Display error message if needed
		if(!creationSuccess)
		{
			// Generate the message to be displayed
			String message = "Unable to generate mapping '" + mapping.getName() + "'";
			
			// Display the error message
			MessageBox messageBox = new MessageBox(getShell(),SWT.ERROR);
			messageBox.setText("Mapping Generation Error");
			messageBox.setMessage(message);
			messageBox.open();
		}					
		else getShell().dispose();
	}
}