package org.mitre.openii.widgets;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/** Constructs a URI composite */
public class URIField implements ModifyListener, SelectionListener
{
	// Constants defining the URI field mode
	static public final Integer FILE = 0;
	static public final Integer DIRECTORY = 1;
	static public final Integer URI = 2;
	
	// Stores the various composite fields
	private Label uriLabel = null;
	private Composite uriPane = null;
	private Text uriField = null;
	private Button uriButton = null;

	// Stores the dialog information
	private String dialogTitle = null;
	private String dialogFilterName = null;
	private ArrayList<String> dialogFilterExtensions = null;

	/** Stores the current mode */
	private Integer mode = null;
	
	/** Stores if the current file is valid */
	private boolean isValid = false;
	
	/** Checks the validity of the URI */
	private void checkURIValidity()
	{
		if(uriField.getText().length()==0) isValid = false;
		else if(uriButton==null) isValid = true;
		else isValid = new File(uriField.getText().replaceAll("^file:/","")).exists();
	}
	
	/** Constructs the URI composite */
	public URIField(Composite parent)
	{
		// Create the URI field label 
		uriLabel = BasicWidgets.createLabel(parent, "");
		
		// Construct the composite pane
		uriPane = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		uriPane.setLayout(layout);
		uriPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the file field component
		uriField = new Text(uriPane, SWT.BORDER);
		uriField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		uriField.addModifyListener(this);
	}

	/** Sets the dialog information */
	public void setDialogInfo(String dialogTitle, String dialogFilterName, ArrayList<String> dialogFilterExtensions)
	{
		this.dialogTitle=dialogTitle;
		this.dialogFilterName=dialogFilterName;
		this.dialogFilterExtensions=dialogFilterExtensions;
	}
	
	/** Sets the URI field mode */
	public void setMode(Integer mode)
	{
		this.mode=mode;
		
		// Update the URI field mode
		uriLabel.setText(mode==URI ? "URI: " : mode==FILE ? "File: " : "Directory: ");
		uriField.setText("");
		checkURIValidity();

		// Eliminate file button when changed to URI mode
		if(uriButton!=null)
			{ uriButton.dispose(); uriButton = null; }
		
		// Display file button when changed to file mode
		if(mode.equals(FILE))
			uriButton = BasicWidgets.createButton(uriPane, "Get File...", this);

		// Display directory button when changed to directory mode
		if(mode.equals(DIRECTORY))
			uriButton = BasicWidgets.createButton(uriPane, "Get Directory...", this);
		
		// Layout the adjusted pane
		((GridLayout)uriPane.getLayout()).numColumns = mode.equals(URI) ? 1 : 2;
		((GridLayout)uriPane.getLayout()).marginHeight = mode.equals(URI) ? 2 : 0;
		uriPane.getParent().layout(true,true);
	}
	
	/** Returns the text field */
	public Text getTextField()
		{ return uriField; }
	
	/** Returns the URI */
	public URI getURI() throws URISyntaxException
 		{ return uriButton==null ? new URI(uriField.getText()) : new File(uriField.getText()).toURI(); }
	
	/** Indicates if the URI field is valid */
	public boolean isValid()
		{ return isValid; }

	/** Handles modifications to the various text fields */
	public void modifyText(ModifyEvent e)
		{ checkURIValidity(); }

	/** Handles the selection of a file to import from */
	public void widgetSelected(SelectionEvent e)
	{
		String filename = null;
		
		// Retrieves filename from file dialog
		if(mode.equals(FILE))
		{
			FileDialog dialog = new FileDialog(uriPane.getShell(), SWT.OPEN);
			dialog.setText(dialogTitle);
			dialog.setFilterPath("C:/");
			dialog.setFilterNames(new String[] {dialogFilterName});
			dialog.setFilterExtensions(dialogFilterExtensions.toArray(new String[0]));
		    filename = dialog.open();
		}
		   
		// Retrieves directory from directory dialog
		if(mode.equals(DIRECTORY))
		{
			DirectoryDialog dialog = new DirectoryDialog(uriPane.getShell());
			dialog.setText(dialogTitle);
			filename = dialog.open();
		}
		
		// Sets the file text field
		if(filename != null) getTextField().setText(filename);
	}
	
	// Unused listener event
	public void widgetDefaultSelected(SelectionEvent e) {}
}