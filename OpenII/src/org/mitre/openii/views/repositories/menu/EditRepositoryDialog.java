package org.mitre.openii.views.repositories.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.OptionsPanel;
import org.mitre.openii.widgets.OptionPane;
import org.mitre.openii.widgets.URIField;
import org.mitre.schemastore.client.Repository;

/** Constructs the Edit Repository Dialog */
public class EditRepositoryDialog extends TitleAreaDialog implements ActionListener, ModifyListener, SelectionListener
{
	// Static constants defining the option panel options
	private static String SERVICE = "Web Service";
	private static String DATABASE = "Database";
	private static String LOCAL = "Local";
	private static String REMOTE = "Remote";
	
	/** Stores the repository being edited */
	private Repository repository = null;
	
	// Stores the various dialog fields
	private Text nameField = null;
	private OptionsPanel optionsPanel = null;
	private Text webURL = null;
	private OptionPane databaseType = null;
	private URIField databaseURI = null;
	private Text databaseName = null;
	private Text databaseUser = null;
	private Text databasePassword = null;
	
	/** Constructs the dialog */
	public EditRepositoryDialog(Shell shell, Repository repository)
		{ super(shell); this.repository = repository; }

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Repository.gif"));
		shell.setText((repository==null ? "Create" : "Edit") + " Repository");
	}
	
	/** Creates the name pane */
	private void createNamePane(Composite parent)
	{
		// Construct the composite pane
		Composite pane = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2,false);
		layout.marginWidth = 8;
		pane.setLayout(layout);
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Constructs the name field
		nameField = BasicWidgets.createTextField(pane,"Repository Name");
		nameField.addModifyListener(this);
	}
	
	/** Creates the connection pane */
	private void createConnectionPane(Composite parent)
	{
		// Construct the pane for showing the info for the selected mapping
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("Connection Info");
		GridLayout layout = new GridLayout(2,false);
		layout.marginHeight = 0; layout.marginWidth = 0;
		pane.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 400;
		pane.setLayoutData(gridData);
		
		// Generate the options panel
		optionsPanel = new OptionsPanel(pane);
		
		// Create the web services option
		Composite webServicePane = optionsPanel.addOption(SERVICE);
		webURL = BasicWidgets.createTextField(webServicePane, "URL");
		
		// Create the database option
		Composite databasePane = optionsPanel.addOption(DATABASE);
		databaseType = BasicWidgets.createRadioField(databasePane, "Type", new String[]{LOCAL,REMOTE}, this);
		databaseURI = new URIField(databasePane); databaseURI.setMode(URIField.DIRECTORY);
		databaseName = BasicWidgets.createTextField(databasePane, "Database");
		databaseUser = BasicWidgets.createTextField(databasePane, "Username");
		databasePassword = BasicWidgets.createTextField(databasePane, "Password");

		// Initializes the database URI field
		databaseURI.setDialogInfo("Database Directory", null, null);
		
		// Listen for changes to the various text fields
		optionsPanel.addListener(this);
		webURL.addModifyListener(this);
		databaseURI.getTextField().addModifyListener(this);
		databaseName.addModifyListener(this);
		databaseUser.addModifyListener(this);
		databasePassword.addModifyListener(this);		
	}
	
	/** Creates the contents for the Edit Repository Dialog */
	protected Control createDialogArea(Composite parent)
	{		
		// Set the dialog title and message
		setTitle("Repository Connection");
		setMessage("Information for connecting to a repository");
		
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		pane.setLayout(layout);
	
		// Generate the pane components
		createNamePane(pane);
		createConnectionPane(pane);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Edit Repository Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);

		// Make default repository dialog selections
		nameField.setText(repository!=null ? repository.getName() : "");
		if(repository!=null)
		{
			nameField.setText(repository.getName());
			if(repository.getType().equals(Repository.SERVICE))
			{
				optionsPanel.setOption(SERVICE);
				webURL.setText(repository.getURI().toString());
			}
			else
			{
				// Display the database options
				optionsPanel.setOption(DATABASE);
				
				// Generate the repository uri
				String uri = repository.getURI().toString();
				if(repository.getType().equals(Repository.DERBY))
					uri = new File(uri).getAbsolutePath();
				
				// Populate the dialog with the repository information
				optionsPanel.setOption(DATABASE);
				databaseType.setOption(repository.getType().equals(Repository.DERBY)?LOCAL:REMOTE);
				databaseURI.getTextField().setText(repository.getURI().toString());
				databaseName.setText(repository.getDatabaseName());
				databaseUser.setText(repository.getDatabaseUser());
				databasePassword.setText(repository.getDatabasePassword());
			}
			optionsPanel.setOption(repository.getType().equals(Repository.SERVICE)?SERVICE:DATABASE);
		}
		else optionsPanel.setOption(SERVICE);
		validateFields();
		
		return control;
	}
	
	/** Monitors changes to the options pane to determine when to activate the OK button */
	public void actionPerformed(ActionEvent e)
		{ validateFields(); }
	
	/** Monitors changes to the text field to determine when to activate the OK button */
	public void modifyText(ModifyEvent e)
		{ validateFields(); }
	
	/** Monitors changes to the radio field to know when to change the connection fields */
	public void widgetSelected(SelectionEvent e)
		{ databaseURI.setMode(databaseType.getOption().equals(LOCAL) ? URIField.DIRECTORY : URIField.URI); }

	/** Validates the fields in order to activate the OK button */
	private void validateFields()
	{
		// Check the validity of the file field
		setErrorMessage(null);
		if(databaseURI.getTextField().getText().length()>0)
			setErrorMessage(databaseURI.isValid() ? null : "A valid file must be selected");
		
		// Determine if the OK button should be activated
		boolean activate = nameField.getText().length()>0;
		if(optionsPanel.getOption().equals(SERVICE))
			activate &= webURL.getText().length()>0;
		else activate &= databaseURI.isValid() && databaseName.getText().length()>0 &&
						 databaseUser.getText().length()>0 && databasePassword.getText().length()>0;
		getButton(IDialogConstants.OK_ID).setEnabled(activate);
	}
	
	/** Handles the creating/updating of the repository */
	protected void okPressed()
	{
		try {
			// Gets the various repository parameters
			String name = nameField.getText();
			Integer type = optionsPanel.getOption().equals(SERVICE) ? Repository.SERVICE : databaseType.getOption().equals(LOCAL) ? Repository.DERBY : Repository.POSTGRES;
			URI uri = type.equals(Repository.SERVICE) ? new URI(webURL.getText()) : databaseURI.getURI();
			String database = databaseName.getText();
			String username = databaseUser.getText();
			String password = databasePassword.getText();
			
			// Adds the repository to the manager
			Repository newRepository = new Repository(name, type, uri, database, username, password);
			if(repository!=null) RepositoryManager.deleteRepository(repository);
			RepositoryManager.addRepository(newRepository);
			getShell().dispose();
		}
		catch(Exception e) { setErrorMessage("Failed to create repository"); }
	}
	
	// Unused event listeners
	public void widgetDefaultSelected(SelectionEvent arg0) {}
}