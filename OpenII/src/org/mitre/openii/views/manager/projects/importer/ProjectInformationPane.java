package org.mitre.openii.views.manager.projects.importer;

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
import org.mitre.openii.widgets.URIField;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.porters.projectImporters.ProjectImporter;

/** Class for storing project information for the files to be imported */
public class ProjectInformationPane implements SelectionListener
{	
	// Stores the various dialog fields
	private URIField uriField = null;
	private Text nameField = null;
	private Text authorField = null;
	private Text descriptionField = null;
	
	/** Constructs the project information pane */
	ProjectInformationPane(Composite parent)
	{
		// Construct the pane for showing the info for the selected importer
		Group group = new Group(parent, SWT.NONE);
		group.setText("Project Information");
		group.setLayout(new GridLayout(2,false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Generate the properties to be displayed by the info pane
		uriField = new URIField(group);
		nameField = BasicWidgets.createTextField(group,"Name");
		authorField = BasicWidgets.createTextField(group,"Author");
		descriptionField = BasicWidgets.createTextField(group,"Description",4);
		
		// Sets the default mode of the pane
		uriField.setMode(URIField.FILE);
	}
	
	/** Handles the updating of the project info based on the selected importer */
	void initializeProjectInfo(ProjectImporter importer)
	{
		// Clear out the fields as needed
		if(authorField.getText().equals(""))
			authorField.setText(System.getProperty("user.name"));
		uriField.getTextField().setText("");
		
		// Generate the list of extensions that are available
		ArrayList<String> extensions = new ArrayList<String>();
		for(String extension : importer.getFileTypes())
			extensions.add("*"+extension);

		// Display file field or URI depending on importer type
//		uriField.setMode(uriImporter ? URIField.URI : URIField.FILE);
		uriField.setDialogInfo("Import Project", importer.getName()+" "+importer.getFileTypes(), extensions);

		// Set focus on the file field
		uriField.getTextField().setFocus();
	}	
	
	/** Sets the project name */
	void setName(String name)
		{ nameField.setText(name); }
	
	/** Sets the project info based on the passed in project object */
	void setProjectInfo(Project project)
	{
		nameField.setText(project.getName());
		authorField.setText(project.getAuthor());
		if(project.getDescription()!=null) descriptionField.setText(project.getDescription());
	}

	/** Returns the URI field */
	URIField getURIField()
		{ return uriField; }
	
	/** Returns the project name */
	String getName()
		{ return nameField.getText(); }
	
	/** Returns the project author */
	String getAuthor()
		{ return authorField.getText(); }
	
	/** Returns the project description */
	String getDescription()
		{ return descriptionField.getText(); }
	
	/** Indicates if the information provided in this pane is valid */
	boolean isValid()
	{
		boolean valid = uriField.getTextField().getText().length()>0 && uriField.isValid();
		valid &= nameField.getText().length()>0 && authorField.getText().length()>0;
		return valid;		
	}

	/** Handles the toggling of the URI type */
	public void widgetSelected(SelectionEvent e)
		{ nameField.setText(""); }

	// Unused listener event
	public void widgetDefaultSelected(SelectionEvent e) {}
	
	/** Adds a modification listener to the project info pane */
	void addListener(ModifyListener listener)
	{
		nameField.addModifyListener(listener);
		authorField.addModifyListener(listener);
		descriptionField.addModifyListener(listener);
	}
	
	/** Adds a URI listener to the project info pane */
	void addURIListener(ModifyListener listener)
		{ uriField.getTextField().addModifyListener(listener); }
}