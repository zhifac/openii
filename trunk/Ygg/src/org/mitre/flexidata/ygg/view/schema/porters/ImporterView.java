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
import org.mitre.flexidata.ygg.view.shared.ParameterPane;
import org.mitre.flexidata.ygg.view.shared.RoundedPane;
import org.mitre.flexidata.ygg.view.shared.SelectionPane;
import org.mitre.flexidata.ygg.view.shared.ParameterPane.EditAreaParameter;
import org.mitre.flexidata.ygg.view.shared.ParameterPane.EditFieldParameter;
import org.mitre.flexidata.ygg.view.shared.ParameterPane.FileParameter;
import org.mitre.flexidata.ygg.view.shared.ParameterPane.SchemaParameter;
import org.mitre.flexidata.ygg.view.shared.ParameterPane.RepositoryParameter;

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
	private class InformationPane extends RoundedPane
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
			// Generate the parameter pane
			parameterPane = new ParameterPane();
			parameterPane.setBorder(new EmptyBorder(5,5,5,5));
			parameterPane.addParameter(new EditFieldParameter("Name",""));
			parameterPane.addParameter(new EditFieldParameter("Author",System.getProperty("user.name")));
			parameterPane.addParameter(new EditAreaParameter("Description",""));
			
			// Determine what type of URI needs to be retrieved
			Integer uriType = importer.getURIType();
			if(uriType==Importer.FILE)
				parameterPane.addParameter(new FileParameter("File",importer.getFileFilter()));
			else if(uriType==Importer.SCHEMA)
				parameterPane.addParameter(new SchemaParameter("Schema"));
			else if(uriType==Importer.REPOSITORY)
				parameterPane.addParameter(new RepositoryParameter("Repository"));

			// Reset the view with the new parameter pane
			setView(parameterPane);
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

	/** Handles the running of the importer */
	public void actionPerformed(ActionEvent e)
	{
		// Checks to make sure parameter pane is completed
		ParameterPane parameterPane = informationPane.parameterPane;
		if(parameterPane.isCompleted())
		{
			// Retrieve the parameter
			String name = parameterPane.getParameter(0).getValue();
			String author = parameterPane.getParameter(1).getValue();
			String description = parameterPane.getParameter(2).getValue();
			String uri = parameterPane.getParameter(3)==null ? null : parameterPane.getParameter(3).getValue();

			// Run the importer
			try { selectionPane.getSelection().importSchema(name, author, description, uri); }
			catch(ImporterException e2) { descriptionPane.setText(e2.getMessage(), true); }
		}
		else descriptionPane.setText("All fields must be completed before import!",true);
	}
}