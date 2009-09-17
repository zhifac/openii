package org.mitre.openii.views.manager.mappings;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.views.manager.mappings.matchmaker.Feeder;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.Mapping;

/**
 * Map all possible pair-wise schemas in a "Mapping" object 
 * @author HAOLI
 *
 */
public class AutoMappingDialog extends Dialog implements ModifyListener, ISelectionChangedListener {
	
	private Mapping mapping;
	private Text nameField;
	private Text authorField;
	private Text descriptionField;
	private CheckboxTableViewer schemaList = null;
	
	public AutoMappingDialog( Shell shell, Mapping mapping ) { 
		super(shell); 
		this.mapping = mapping; 
	}
	
	protected void runFeeder() {
		Feeder feeder;
		
		try {
			// match maker runs n-way matches
			feeder = new Feeder(mapping);
			feeder.startRepeatedMatches(); 
			feeder.mergeMatches(); 
			System.out.println("Map All completed.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Mapping.gif"));
		shell.setText("Auto Mapping");
	}
	
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
//		createSchemaPane(pane);

		// Return the generated pane
		return pane;
	}
	
	/** Creates the mapping schema pane */
	private void createSchemaPane(Composite parent)
	{
		// Construct the pane for showing the schemas for the selected mapping
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("Schemas");
		pane.setLayout(new GridLayout(1,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		schemaList = CheckboxTableViewer.newCheckList(pane,SWT.BORDER | SWT.V_SCROLL);
		schemaList.setContentProvider(new BasicWidgets.SchemaContentProvider());
		schemaList.setLabelProvider(new BasicWidgets.SchemaLabelProvider());
		schemaList.setInput("");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 200;
		schemaList.getControl().setLayoutData(gridData);
		schemaList.addSelectionChangedListener(this);
		
		if(mapping!=null)
		{
			//	 Set all items selected as part of mapping
			for(Integer schemaID : mapping.getSchemaIDs())
				schemaList.setChecked(schemaID, true);
		}
	}
	
	protected void okPressed()
	{		
		runFeeder();
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
		
		if(mapping!=null)
		{
			// Set general information fields
			nameField.setText(mapping.getName());
			authorField.setText(mapping.getAuthor());
			descriptionField.setText(mapping.getDescription());
		}
		
		nameField.addModifyListener(this);
		authorField.addModifyListener(this);
		descriptionField.addModifyListener(this);
	}

	@Override
	public void modifyText(ModifyEvent e) {
		System.out.println( ((Text)e.getSource()).getText() );
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// TODO Auto-generated method stub
		
	}

}
