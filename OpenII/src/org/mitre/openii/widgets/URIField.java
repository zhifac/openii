package org.mitre.openii.widgets;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;import org.eclipse.swt.widgets.Composite;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/** Constructs a URI composite */
public class URIField implements ModifyListener
{
	// Constants defining the URI field mode
	static public final Integer FILE = 0;
	static public final Integer URI = 1;
	
	// Stores the various composite fields
	private Label uriLabel = null;
	private Composite uriPane = null;
	private Text uriField = null;
	private Button uriButton = null;
	
	/** Stores if the current file is valid */
	private boolean isValid = false;
	
	/** Store the selection listener for this field */
	private SelectionListener listener = null;
	
	/** Checks the validity of the URI */
	private void checkURIValidity()
	{
		if(uriField.getText().length()==0) isValid = false;
		else if(uriButton==null) isValid = true;
		else isValid = new File(uriField.getText()).exists();
	}
	
	/** Constructs the URI composite */
	public URIField(Composite parent, SelectionListener listener)
	{
		this.listener = listener;
		
		// Create the URI field label 
		uriLabel = BasicWidgets.createLabel(parent, "File");
		
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
	
	/** Indicates if the URI field is valid */
	public boolean isValid()
		{ return isValid; }
	
	/** Sets the URI field mode */
	public void setMode(Integer mode)
	{
		// Update the URI field mode
		uriLabel.setText(mode==URI ? "URI: " : "File: ");
		uriField.setText("");
		checkURIValidity();

		// Eliminate file button when changed to URI mode
		if(mode.equals(URI) && uriButton!=null)
			{ uriButton.dispose(); uriButton = null; }
		
		// Display file button when changed to file mode
		if(mode.equals(FILE) && uriButton==null)
			{ uriButton = BasicWidgets.createButton(uriPane, "Get File...", listener); }

		// Layout the adjusted pane
		((GridLayout)uriPane.getLayout()).numColumns = mode.equals(URI) ? 1 : 2;
		((GridLayout)uriPane.getLayout()).marginHeight = mode.equals(URI) ? 2 : 0;
		uriPane.layout(true);
	}
	
	/** Returns the text field */
	public Text getTextField()
		{ return uriField; }
	
	/** Returns the URI */
	public URI getURI() throws URISyntaxException
 		{ return uriButton==null ? new URI(uriField.getText()) : new File(uriField.getText()).toURI(); }

	/** Handles modifications to the various text fields */
	public void modifyText(ModifyEvent e)
		{ checkURIValidity(); }
}