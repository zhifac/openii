// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.schema.porters;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import org.mitre.flexidata.ygg.importers.Importer;
import org.mitre.flexidata.ygg.importers.ImporterException;
import org.mitre.flexidata.ygg.model.ConfigManager;
import org.mitre.flexidata.ygg.view.Consts;
import org.mitre.flexidata.ygg.view.GenericView;
import org.mitre.flexidata.ygg.view.shared.ColoredButton;
import org.mitre.flexidata.ygg.view.shared.DescriptionPane;
import org.mitre.flexidata.ygg.view.shared.RoundedPane;
import org.mitre.flexidata.ygg.view.shared.SelectionPane;
import org.mitre.flexidata.ygg.view.shared.parameters.AbstractParameter;
import org.mitre.flexidata.ygg.view.shared.parameters.EditAreaParameter;
import org.mitre.flexidata.ygg.view.shared.parameters.EditFieldParameter;
import org.mitre.flexidata.ygg.view.shared.parameters.FileParameter;
import org.mitre.flexidata.ygg.view.shared.parameters.ParameterPane;
import org.mitre.flexidata.ygg.view.shared.parameters.RepositoryParameter;
import org.mitre.flexidata.ygg.view.shared.parameters.SchemaParameter;
import org.mitre.schemastore.model.Schema;

/** Class for displaying the importer view */
public class ImporterView extends GenericView implements ActionListener
{
	// Stores various objects used by the schema view
	private ImporterSelectionPane selectionPane;
	private InformationPane informationPane;
	private DescriptionPane descriptionPane;
	
	/** Stores the submit button used to run the importer */
	private ColoredButton submitButton = new ColoredButton("Submit");
	
	/** Class for displaying the importer selection pane */
	private class ImporterSelectionPane extends SelectionPane<Importer> implements ActionListener
	{
		/** Constructs the importer selection pane */
		private ImporterSelectionPane()
		{
			super("Method",ConfigManager.getImporters());
			addActionListener(this);
		}
			
		/** Handles the selection of an importer */
		public void actionPerformed(ActionEvent e)
		{
			Importer importer = getSelection();
			informationPane.displayImporter(importer);
			descriptionPane.setText(importer.getDescription(),false);
		}
	}
	
	/** Class for displaying the information pane */
	private class InformationPane extends RoundedPane implements ActionListener
	{
		/** Stores the parameter pane */
		private ParameterPane parameterPane = null;
		
		/** Constructs the information pane */
		private InformationPane()
		{			
			setBorder(new EmptyBorder(10,10,0,10));
			setBackground(Consts.LIGHT_TAN);
			displayImporter(selectionPane.getSelection());
		}
		
		/** Sets the type of URI needed for the currently selected importer */
		private void displayImporter(Importer importer)
		{
			// Determines if the name, author, and description should be editable
			Integer uriType = importer.getURIType();
			boolean editable = uriType!=Importer.REPOSITORY;
			
			// Generate the parameter pane
			parameterPane = new ParameterPane();
			parameterPane.setBorder(new EmptyBorder(5,5,5,5));
			parameterPane.addParameter(new EditFieldParameter("Name","",editable));
			parameterPane.addParameter(new EditFieldParameter("Author",editable?System.getProperty("user.name"):"",editable));
			parameterPane.addParameter(new EditAreaParameter("Description","",editable));
			
			// Determine what type of URI needs to be retrieved
			if(uriType==Importer.FILE)
				parameterPane.addParameter(new FileParameter("File",importer.getFileTypes()));
			else if(uriType==Importer.SCHEMA)
				parameterPane.addParameter(new SchemaParameter("Schema"));
			else if(uriType==Importer.REPOSITORY)
			{
				RepositoryParameter repositoryParameter = new RepositoryParameter("Repository");
				repositoryParameter.addActionListener(this);
				parameterPane.addParameter(repositoryParameter);
			}
			
			// Reset the view with the new parameter pane
			setView(parameterPane);
		}

		/** Handles the changing of the selected schema */
		public void actionPerformed(ActionEvent e)
		{
			Schema schema = ((RepositoryParameter)parameterPane.getParameter("Repository")).getSchema();
			parameterPane.getParameter("Name").setValue(schema.getName());
			parameterPane.getParameter("Author").setValue(schema.getAuthor());
			parameterPane.getParameter("Description").setValue(schema.getDescription());
		}
	}
	
	/** Constructs the importer view */
	public ImporterView()
	{
		// Generate the schema view
		setOpaque(false);
		setLayout(new BorderLayout());
		add(selectionPane = new ImporterSelectionPane(),BorderLayout.NORTH);
		add(informationPane = new InformationPane(),BorderLayout.CENTER);
		add(descriptionPane = new DescriptionPane(selectionPane.getSelection().getDescription()),BorderLayout.SOUTH);
		
		// Add button listener
		submitButton.addActionListener(this);
	}
	
	/** Return the title */
	public String getTitle()
		{ return "Add Schema"; }
	
	/** Return the buttons used by this view */
	public ArrayList<JButton> getButtons()
	{
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		buttons.add(submitButton);
		return buttons;
	}

	/** Determines if the parameter has been completed */
	public boolean isCompleted(AbstractParameter parameter)
	{
		boolean parameterCompleted = parameter.getValue()!=null;
		parameter.setHighlight(!parameterCompleted);
		return parameterCompleted;
	}
	
	/** Handles the running of the importer */
	public void actionPerformed(ActionEvent e)
	{
		// Get the current importer being used
		Importer importer = selectionPane.getSelection();
		
		// Retrieve the name, author, and description parameters
		ParameterPane parameterPane = informationPane.parameterPane;
		AbstractParameter nameParm = parameterPane.getParameter("Name");
		AbstractParameter authorParm = parameterPane.getParameter("Author");
		AbstractParameter descriptionParm = parameterPane.getParameter("Description");
		AbstractParameter uriParm = parameterPane.getParameter(importer.getURIType()==Importer.FILE ? "File" : importer.getURIType()==Importer.SCHEMA ? "Schema" : "Repository");

		boolean completed = true;
		if(importer.getURIType()==Importer.REPOSITORY)
			completed = isCompleted(uriParm);
		else completed = isCompleted(nameParm) && isCompleted(authorParm) && isCompleted(descriptionParm) && isCompleted(uriParm);

		// If completed, run importer
		if(completed)
		{
			// Run the importer
			String uri = uriParm==null ? null : uriParm.getValue();
			try { importer.importSchema(nameParm.getValue(), authorParm.getValue(), descriptionParm.getValue(), uri); }
			catch(ImporterException e2) { descriptionPane.setText(e2.getMessage(), true); }
		}
		else descriptionPane.setText("All fields must be completed before import!",true);
	}
}