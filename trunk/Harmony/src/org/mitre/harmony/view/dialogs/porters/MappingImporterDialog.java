// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.harmony.view.dialogs.porters;

import java.awt.Component;
import java.net.URI;
import java.util.ArrayList;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.schemastore.porters.Importer;
import org.mitre.schemastore.porters.mappingImporters.MappingImporter;

/** Class for displaying the schema importer dialog */
public class MappingImporterDialog extends ImporterDialog
{	
	/** Constructs the importer dialog */
	public MappingImporterDialog(Component parent, HarmonyModel harmonyModel)
		{ super(parent, harmonyModel); setVisible(true); }
	
	/** Returns the type of importer being run */
	protected String getImporterType() { return "Mapping"; }

	/** Returns the importers from which the user can select */
	protected ArrayList<Importer> getImporters()
	{
		ArrayList<Importer> importers = new ArrayList<Importer>();
		importers.add(SchemaStoreManager.getM3ProjectImporter());
		return importers;
	}

	/** Imports the currently specified item */
	protected void importItem(String name, String author, String description, URI uri) throws Exception
	{
		// Import the mapping
		MappingImporter importer = (MappingImporter)selectionList.getSelectedItem();
		importer.initialize(uri);
		Integer mappingID = importer.importMapping(name, author, description, null);
		harmonyModel.getMappingManager().loadMapping(mappingID);
	}
}