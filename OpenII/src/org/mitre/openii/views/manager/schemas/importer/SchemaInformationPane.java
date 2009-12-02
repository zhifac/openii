package org.mitre.openii.views.manager.schemas.importer;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.OptionPane;
import org.mitre.openii.widgets.URIField;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

/** Class for storing schema information for the files to be imported */
public class SchemaInformationPane implements SelectionListener
{	
	// Stores the various dialog fields
	private OptionPane uriType = null;
	private URIField uriField = null;
	private Text nameField = null;
	private Text authorField = null;
	private Text descriptionField = null;
	
	/** Constructs the schema information pane */
	SchemaInformationPane(Composite parent)
	{
		// Construct the pane for showing the info for the selected importer
		Group group = new Group(parent, SWT.NONE);
		group.setText("Schema Information");
		group.setLayout(new GridLayout(2,false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		uriType = BasicWidgets.createRadioField(group,"Import Type",new String[]{"File","Directory"},this);
		uriField = new URIField(group);
		nameField = BasicWidgets.createTextField(group,"Name");
		authorField = BasicWidgets.createTextField(group,"Author");
		descriptionField = BasicWidgets.createTextField(group,"Description",4);
		
		// Sets the default mode of the pane
		uriField.setMode(URIField.FILE);
	}
	
	/** Handles the updating of the schema info based on the selected importer */
	void initializeSchemaInfo(SchemaImporter importer)
	{
		// Identify the importer type
		boolean m3Importer = importer.getURIType()==SchemaImporter.M3MODEL;
		boolean uriImporter = importer.getURIType()==SchemaImporter.URI;
		
		// Clear out the fields as needed
		if(m3Importer)
			{ nameField.setText(""); authorField.setText(""); descriptionField.setText(""); }
		else if(authorField.getText().equals(""))
			authorField.setText(System.getProperty("user.name"));
		uriField.getTextField().setText("");
		
		// Disable fields as needed in case of m3 exporter
		nameField.setEnabled(!m3Importer);
		authorField.setEnabled(!m3Importer);
		descriptionField.setEnabled(!m3Importer);
		
		// Generate the list of extensions that are available
		ArrayList<String> extensions = new ArrayList<String>();
		for(String extension : importer.getFileTypes())
			extensions.add("*"+extension);

		// Display file field or URI depending on importer type
		uriType.setOption("File");
		uriField.setMode(uriImporter ? URIField.URI : URIField.FILE);
		uriField.setDialogInfo("Import Schema", importer.getName()+" "+importer.getFileTypes(), extensions);

		// Set focus on the file field
		uriField.getTextField().setFocus();
	}	
	
	/** Sets the schema name */
	void setName(String name)
		{ nameField.setText(name); }
	
	/** Sets the schema info based on the passed in schema object */
	void setSchemaInfo(Schema schema)
	{
		nameField.setText(schema.getName());
		authorField.setText(schema.getAuthor());
		if(schema.getDescription()!=null) descriptionField.setText(schema.getDescription());
	}

	/** Returns the URI field */
	URIField getURIField()
		{ return uriField; }
	
	/** Returns the schema name */
	String getName()
		{ return nameField.getText(); }
	
	/** Returns the schema author */
	String getAuthor()
		{ return authorField.getText(); }
	
	/** Returns the schema description */
	String getDescription()
		{ return descriptionField.getText(); }
	
	/** Indicates if the information provided in this pane is valid */
	boolean isValid()
	{
		boolean valid = uriField.getTextField().getText().length()>0 && uriField.isValid();
		valid &= uriType.getOption().equals("Directory") || nameField.getText().length()>0;
		valid &= authorField.getText().length()>0;
		return valid;		
	}

	/** Handles the toggling of the URI type */
	public void widgetSelected(SelectionEvent e)
	{
		Integer mode = uriType.getOption().equals("File") ? URIField.FILE : URIField.DIRECTORY;
		uriField.setMode(mode);
		nameField.setText("");
		nameField.setEnabled(mode.equals(URIField.FILE));
	}

	// Unused listener event
	public void widgetDefaultSelected(SelectionEvent e) {}
	
	/** Adds a modification listener to the schema info pane */
	void addListener(ModifyListener listener)
	{
		nameField.addModifyListener(listener);
		authorField.addModifyListener(listener);
		descriptionField.addModifyListener(listener);
	}
	
	/** Adds a URI listener to the schema info pane */
	void addURIListener(ModifyListener listener)
		{ uriField.getTextField().addModifyListener(listener); }
}