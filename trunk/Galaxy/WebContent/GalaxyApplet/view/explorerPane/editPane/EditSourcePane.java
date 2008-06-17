// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane.editPane;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Schema;

import view.sharedComponents.EditPaneInterface;

import model.DataSources;

/** Class for editing a schema source */
class EditSourcePane extends EditPaneInterface
{		
	/** Stores the schema associated with the source */
	private Schema schema;

	/** Stores the source id if one exists */
	private Integer sourceID;
	
	// Stores various pane objects
	private JTextField name = new JTextField();
	private JTextArea url = new JTextArea();

	/** Get the source */
	DataSource getDataSource()
		{ return new DataSource(sourceID,name.getText(),schema.getId(),url.getText()); }
	
	/** Constructs the Source Pane */
	EditSourcePane(Schema schema, DataSource source)
	{
		this.schema = schema;
		
		// Populate the various elements
		if(source!=null)
		{
			sourceID = source.getId();
			name.setText(source.getName());
			url.setText(source.getUrl());
		}
		
		// Constructs the name pane
		NamePane namePane = new NamePane(name);
		namePane.setBorder(new EmptyBorder(2,4,2,2));
		
		// Constructs the description pane
		TextPane urlPane = new TextPane("URL",url);
		urlPane.setBorder(new EmptyBorder(2,14,2,0));
		
		// Construct the Edit Source pane
		setLayout(new BorderLayout());
		setOpaque(false);
		add(namePane,BorderLayout.NORTH);
		add(urlPane,BorderLayout.CENTER);
	}
		
	/** Handles the validating of the data source */
	public boolean validatePane()
	{
		// Checks to make sure that name has been entered
		name.setBackground(Color.white);
		if(name.getText().length()==0)
			{ name.setBackground(Color.yellow); return false; }

		// Don't save if data source url is empty
		url.setBackground(Color.white);
		if(url.getText().length()==0)
			{ url.setBackground(Color.yellow); return false; }
		
		return true;
	}
	
	/** Handles the saving of the data source */
	public void save()
	{
		// Handle the addition of a data source
		DataSource dataSource = getDataSource();
		if(sourceID==null) DataSources.addDataSource(dataSource);
		else DataSources.updateDataSource(dataSource);
	}
	
	/** Handles the deletion of a data source */
	public boolean delete()
		{ return DataSources.deleteDataSource(getDataSource()); }
}