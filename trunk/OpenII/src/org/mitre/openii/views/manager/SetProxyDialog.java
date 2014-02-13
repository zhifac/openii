package org.mitre.openii.views.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.util.HashMap;

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
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.OptionsPanel;
import org.mitre.openii.widgets.OptionPane;
import org.mitre.openii.widgets.URIField;
import org.mitre.schemastore.client.Repository;

/** Constructs the Edit Repository Dialog */
public class SetProxyDialog extends TitleAreaDialog implements ActionListener, ModifyListener, SelectionListener
{

	
	
	/** Stores the repository being edited */

	
	// Stores the various dialog fields
	private String httpHost;
	private String httpPort;
	private String httpsHost;
	private String httpsPort;
	private String nonProxyHosts;
	private boolean isManual;
	private HashMap<String, String> proxyProperties;
	private OptionPane proxyType = null;

	private Text httpHostField = null;
	private Text httpPortField = null;
	private Text httpsHostField = null;
	private Text httpsPortField = null;
	private Text nonProxyHostField = null;
	
	/** Constructs the dialog */
	public SetProxyDialog(Shell shell)
		{ super(shell); 
			proxyProperties = OpenIIManager.getProxyFromSavedPreferences();
			httpHost = proxyProperties.get(OpenIIManager.HTTP_PROXY);
			httpPort =proxyProperties.get(OpenIIManager.HTTP_PROXY_PORT);
			httpsHost = proxyProperties.get(OpenIIManager.HTTPS_PROXY);
			httpsPort =proxyProperties.get(OpenIIManager.HTTPS_PROXY_PORT);
			nonProxyHosts = proxyProperties.get(OpenIIManager.HTTP_NON_PROXY_HOSTS);
			isManual = Boolean.parseBoolean(proxyProperties.get(OpenIIManager.MANUAL_PROXY));
			
		
		}

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		
		shell.setText("Set proxy");
	}
	

	
	/** Creates the proxy pane */
	private void createProxyPane(Composite parent)
	{
		// Construct the pane for showing the info for the selected mapping
		Group pane = new Group(parent, SWT.NONE);
		pane.setText("Proxy Info");
		GridLayout layout = new GridLayout(2,false);
		layout.marginHeight = 0; layout.marginWidth = 0;
		pane.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 400;
		pane.setLayoutData(gridData);
		

		
		
		proxyType = BasicWidgets.createRadioField(pane, "Type", new String[]{"Direct","Manual"}, this);
		httpHostField = BasicWidgets.createTextField(pane, "HTTP Proxy host");
		httpPortField = BasicWidgets.createTextField(pane, "HTTP Proxy port");
		nonProxyHostField = BasicWidgets.createTextField(pane, "Non proxy hosts");
		httpsHostField = BasicWidgets.createTextField(pane, "HTTPS Proxy host");
		httpsPortField = BasicWidgets.createTextField(pane, "HTTPS Proxy port");

		
		
		httpHostField.addModifyListener(this);
		httpPortField.addModifyListener(this);
		nonProxyHostField.addModifyListener(this);
		httpsHostField.addModifyListener(this);
		httpsPortField.addModifyListener(this);
	}
	
	/** Creates the contents for the Edit Repository Dialog */
	protected Control createDialogArea(Composite parent)
	{		
		// Set the dialog title and message
		setTitle("Set proxy");
		setMessage("Information for connecting through a proxy");
		
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		pane.setLayout(layout);
	

		createProxyPane(pane);
		
		// Return the generated pane
		return pane;
	}

	/** Creates the contents for the Edit Repository Dialog */
	protected Control createContents(Composite parent)
	{
		Control control = super.createContents(parent);

		// Make default  dialog selections
		httpHostField.setText(httpHost==null?"":httpHost);
		httpPortField.setText(httpPort==null?"":httpPort);
		this.nonProxyHostField.setText(nonProxyHosts==null?"":nonProxyHosts);
		httpsHostField.setText(httpsHost==null?"":httpsHost);
		httpsPortField.setText(httpsPort==null?"":httpsPort);
		if (!isManual) {
			proxyType.setOption("Direct");
			setEnabledProxyFields(false);
			
		}else {
			proxyType.setOption("Manual");
			setEnabledProxyFields(true);
		}
		validateFields();
		
		return control;
	}
	private void setEnabledProxyFields(boolean enabled) {
		httpHostField.setEnabled(enabled);
		httpsHostField.setEnabled(enabled);
		httpPortField.setEnabled(enabled);
		httpsPortField.setEnabled(enabled);
		nonProxyHostField.setEnabled(enabled);
	}
	
	/** Monitors changes to the options pane to determine when to activate the OK button */
	public void actionPerformed(ActionEvent e)
		{ validateFields(); }
	
	/** Monitors changes to the text field to determine when to activate the OK button */
	public void modifyText(ModifyEvent e)
		{ validateFields(); }
	
	/** Monitors changes to the radio field to know when to change the connection fields */
	public void widgetSelected(SelectionEvent e)
		{ setEnabledProxyFields(proxyType.getOption().equals("Manual")); }

	/** Validates the fields in order to activate the OK button */
	private void validateFields()
	{
		setErrorMessage(null);
		boolean activate = true;
		if (proxyType.getOption().equals("Manual")){
			// Check the validity of the  fields
			boolean hasError = false;
			if(!httpsHostField.getText().trim().isEmpty()){
				if (httpsPortField.getText().trim().isEmpty()) {
					setErrorMessage("Must specify a https port number");
					hasError = true;
				}
				else {
					try {
						int portNumber = Integer.parseInt(httpsPortField.getText());
						if (portNumber <1 || portNumber >65536) {
							setErrorMessage("Port must be a number between 1 and 65536");
							hasError = true;
						}

					}catch (NumberFormatException numE) {
						setErrorMessage("Port must be a number");
						hasError = true;
					}
				}
			}
			else if (!httpsPortField.getText().isEmpty()) {
				setErrorMessage("Must specify a https host");
				hasError = true;
			}
			if(!httpHostField.getText().isEmpty()){
				if (httpPortField.getText().isEmpty()){
					setErrorMessage("Must specify a http port number");
					hasError = true;
				}			else {
					try {
						int portNumber = Integer.parseInt(httpPortField.getText());
						if (portNumber <1 || portNumber >65536) {
							setErrorMessage("Port must be a number between 1 and 65536");
							hasError = true;
						}
					}catch (NumberFormatException numE) {
						setErrorMessage("Port must be a number");
						hasError = true;
					}
				}
			}
			else if (!httpPortField.getText().isEmpty()) {
				setErrorMessage("Must specify a http host");
				hasError = true;
			}
			if (httpHostField.getText().length()==0 && httpsHostField.getText().length()==0) {
				setErrorMessage("Must specify a proxy for manual mode");
				hasError = true;
			}
			activate = !hasError;
		}


			getButton(IDialogConstants.OK_ID).setEnabled(activate);
	}
	
	/** Handles the creating/updating of the repository */
	protected void okPressed()
	{

			
			proxyProperties.put(OpenIIManager.MANUAL_PROXY, Boolean.toString(proxyType.getOption().equals("Manual")));
			proxyProperties.put(OpenIIManager.HTTP_PROXY, httpHostField.getText().trim());
			proxyProperties.put(OpenIIManager.HTTPS_PROXY, httpsHostField.getText().trim());
			proxyProperties.put(OpenIIManager.HTTP_PROXY_PORT, httpPortField.getText().trim());
			proxyProperties.put(OpenIIManager.HTTPS_PROXY_PORT, httpsPortField.getText().trim());
			nonProxyHosts = nonProxyHostField.getText().trim().replaceAll(" ", "|").replaceAll(",", "|");
			while (nonProxyHosts.contains("||")) {
				nonProxyHosts = nonProxyHosts.replace("||", "|");
			}
			proxyProperties.put(OpenIIManager.HTTP_NON_PROXY_HOSTS, nonProxyHosts);
			
			OpenIIManager.setProxySettings(proxyProperties);
			
			
			getShell().dispose();

	}
	
	private void setSystemProperty(String propertyName, String propertyValue) {
		if (propertyValue == null || propertyValue.isEmpty()){
			System.setProperty(propertyName, null);
		}
		else {
			System.setProperty(propertyName, propertyValue);
		}
	}
	
	// Unused event listeners
	public void widgetDefaultSelected(SelectionEvent arg0) {}
}