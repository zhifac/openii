package org.mitre.openii.dialogs.schemas;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.URIField;
import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;

import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.warehouse.client.SpreadsheetInstanceDatabaseClient;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;


/** Constructs the Create Data Source Dialog */
public class CreateDataSourceDialog extends TitleAreaDialog implements ModifyListener
{
	/** Stores the schema being edited */
	private Schema schema = null;
	
	/** Stores the various dialog fields */
	private Text nameField = null;
	private URIField uriField = null;
	
	/** Constructs the dialog */
	public CreateDataSourceDialog(Shell parentShell, Schema schema)
	{
		super(parentShell); 
		this.schema = schema;
	}	

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setText("Create Instance Database");
	}
	
	/** Creates the info pane for creating database instance */
	private void createInfoPane(Composite parent)
	{
		// Construct the pane for showing the info for the spreadsheet
		Group group = new Group(parent, SWT.NONE);
		group.setText("Properties");
		group.setLayout(new GridLayout(2,false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		nameField = BasicWidgets.createTextField(group,"Name");
		uriField = new URIField(group);
				
		// Add listeners to the fields to monitor for changes
		nameField.addModifyListener(this);
		uriField.getTextField().addModifyListener(this);
	}
	
	/** Creates the dialog area for the Create Instance Database Dialog */
	protected Control createDialogArea(Composite parent)
	{			
		// Set the dialog title and message
		setTitle("Create Instance Database");
		setMessage("Create an instance database for a schema imported from a spreadsheet");
		
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
		// Generate the pane components
		createInfoPane(pane);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Create Instance Database Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);
		
		// Set the general information fields
		nameField.setEnabled(true);
		nameField.setFocus();
		
		// Generate the list of extensions that are available
		ArrayList<String> extensions = new ArrayList<String>();
		extensions.add("*.xls");
		extensions.add("*.csv");
		
		// Display file field
		uriField.setMode(URIField.FILE);
		
		// Populate the text field with the file from which the schema was originally imported
		String sourceFilename = schema.getSource();
		try
		{
			URI sourceUri = new URI(sourceFilename);
			String sourcePath = sourceUri.getPath();
			File sourceFile = new File(sourcePath);
			String originalPath = sourceFile.getCanonicalPath();
			
			uriField.getTextField().setText(originalPath);
		}
		catch(URISyntaxException e1)
		{
			System.out.println("String could not be parsed as a URI reference");
			System.out.println(e1.getMessage());
		}
		catch(IOException e2)
		{
			System.out.println("Construction of the canonical pathname may require filesystem queries");
			System.out.println(e2.getMessage());
		}		
		
		// Set Dialog Info
		uriField.setDialogInfo("Create Instance Database", "XLS Files (*.xls,*.csv)", extensions);
		
		return control;
	}
	
	/** Handles modifications to the various text fields */
	public void modifyText(ModifyEvent e)
	{   
		// Check the validity of the file field
		Text uriText = uriField.getTextField();
		
		if(e.getSource().equals(uriText))
		{
			setErrorMessage(null);
			if(uriField.getTextField().getText().length()>0)
				setErrorMessage(uriField.isValid() ? null : "A valid file must be selected");
		}
		
		// Determine if the OK button should be activated
		boolean activate = nameField.getText().length()>0;
		activate &= uriText.getText().length()>0;
		getButton(IDialogConstants.OK_ID).setEnabled(activate && uriField.isValid());
	}
	
	/** Handles the actual import of the specified file */
	protected void okPressed()
	{
		try 
		{
			String pathname = uriField.getTextField().getText();
			String userSpecifiedName = nameField.getText().trim();
			
			URI uri = uriField.getURI();
			System.out.println(pathname);
			System.out.println(uri.toString());
			
			System.out.println(nameField.getText());
			
			File clientFile = new File(pathname);
			SchemaStoreClient schemastoreClient = RepositoryManager.getClient();
			Repository selectedRepository = RepositoryManager.getSelectedRepository();
			
			SpreadsheetInstanceDatabaseClient g = new SpreadsheetInstanceDatabaseClient(clientFile, schema, schemastoreClient, userSpecifiedName, selectedRepository) ;
			
			try
			{	g.createInstanceDatabaseTables();	}
			catch(NoDataFoundException e)
			{	System.out.println("----Application Terminated----");
				System.out.println(e.getMessage());	
				throw e;
			}
			
			Integer newDataSourceId = g.getNewDataSourceCreated();
			if(newDataSourceId != null)
				OpenIIManager.fireDataSourceAdded(newDataSourceId);
			
			//SchemaStoreClient client = new SchemaStoreClient("C:\\workspace\\SchemaStore\\SchemaStore.jar");
			System.out.println("SchemaElementCount: " + schemastoreClient.getSchemaElementCount(schema.getId()));
			
			// Get the schema info
			SchemaInfo schemaInfo = null;
			try 
			{
				schemaInfo = schemastoreClient.getSchemaInfo(schema.getId());
			} 
			catch (RemoteException e) 
			{
				System.out.println("Problem occured while getting the SchemaInfo object from the client");
				e.printStackTrace();
			}
			
			// Returns the list of schema elements - Entity
			ArrayList<SchemaElement> elementsEntity = schemaInfo.getElements(Entity.class);
			for(SchemaElement element : elementsEntity)
			{
				Entity entity = (Entity)element;
				Integer id = entity.getId();
				String name = entity.getName();
				String description = entity.getDescription();
				System.out.println("Entity ID= " + id + " Entity name: " + name + " Entity description: " + description);
			}
			
			// Returns the list of schema elements - Attribute
			ArrayList<SchemaElement> elementsAttribute = schemaInfo.getElements(Attribute.class);
			for(SchemaElement element : elementsAttribute)
			{
				Attribute attribute = (Attribute)element;
				Integer id = attribute.getId();
				String name = attribute.getName();
				Integer domainID = attribute.getDomainID();
				System.out.println("Attribute ID= " + id + " Attribute name: " + name + " Attribute domain id: " + domainID);
				SchemaElement domainElement = schemaInfo.getElement(domainID);
				Domain domain = (Domain) domainElement;
				System.out.println("Domain Name= " + domain.getName());
			}
			
			// Returns the list of schema elements - Attribute
			ArrayList<SchemaElement> elementsContainment = schemaInfo.getElements(Containment.class);
			if(elementsContainment.isEmpty())
				System.out.println("No containments found");
			else
			{
				for(SchemaElement element : elementsContainment)
				{
					Containment containment = (Containment)element;
					Integer id = containment.getId();
					String name = containment.getName();
					String description = containment.getDescription();
					System.out.println("Containment ID= " + id + " Containment name: " + name + " Containment description: " + description);
				}
			}
			
			getShell().dispose();
		}
		catch(Exception e)
			{ setErrorMessage("Failed to create instance database. " + e.getMessage()); }
	}
	
}