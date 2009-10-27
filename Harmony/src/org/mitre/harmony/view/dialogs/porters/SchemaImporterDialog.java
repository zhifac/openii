// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.harmony.view.dialogs.porters;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.ArrayList;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.porters.Importer;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

/** Class for displaying the schema importer dialog */
public class SchemaImporterDialog extends ImporterDialog implements ActionListener, CaretListener
{	
	/** Constructs the importer dialog */
	public SchemaImporterDialog(Component parent, HarmonyModel harmonyModel)
	{
		super(parent, harmonyModel);
		selectionList.addActionListener(this);
		uriField.addListener(this);
		setVisible(true);
	}
	
	/** Returns the type of importer being run */
	protected String getImporterType() { return "Schema"; }

	/** Returns the importers from which the user can select */
	protected ArrayList<Importer> getImporters()
	{
		ArrayList<Importer> importers = new ArrayList<Importer>();
		for(SchemaImporter importer : SchemaStoreManager.getSchemaImporters())
			importers.add(importer);
		return importers;
	}

	/** Imports the currently specified item */
	protected void importItem(String name, String author, String description, URI uri) throws Exception
	{
		SchemaImporter importer = (SchemaImporter)selectionList.getSelectedItem();
		importer.importSchema(name, author, description, uri);
	}
	
	/** Handles the selection of the importer */
	public void actionPerformed(ActionEvent e)
	{
		// Initialize the importer pane
		SchemaImporter importer = (SchemaImporter)getImporter();
		boolean isM3SchemaImporter = importer.getURIType()==SchemaImporter.M3MODEL;
		uriField.setImporter(getImporter());

		// Lock down the name and description fields for archive importers
		nameField.setEditable(!isM3SchemaImporter);
		descriptionField.setEditable(!isM3SchemaImporter);
		descriptionField.setBackground(isM3SchemaImporter ? new Color(0xeeeeee) : Color.white);
	}

	/** Handles changes to the uri */
	public void caretUpdate(CaretEvent e)
	{
		SchemaImporter importer = (SchemaImporter)selectionList.getSelectedItem();
		if(uriField.getValue()!=null)
			try {
				Schema schema = importer.getSchema(uriField.getValue());
				if(schema!=null)
				{
					nameField.setText(schema.getName());
					authorField.setText(schema.getAuthor());
					descriptionField.setText(schema.getDescription());
				}
			} catch(Exception e2) { System.out.println("(E) ImporterDialog.caretUpdate - " + e2.getMessage()); }
	}
}