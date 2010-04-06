// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.harmony.view.dialogs.porters;

import java.awt.Component;
import java.net.URI;
import java.util.ArrayList;

import org.mitre.harmony.controllers.ProjectController;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.schemastore.porters.Importer;
import org.mitre.schemastore.porters.projectImporters.ProjectImporter;

/** Class for displaying the project importer dialog */
public class ImportProjectDialog extends AbstractImportDialog
{	
	/** Constructs the importer dialog */
	public ImportProjectDialog(Component parent, HarmonyModel harmonyModel)
		{ super(parent, harmonyModel); setVisible(true); }
	
	/** Returns the type of importer being run */
	protected String getImporterType() { return "Project"; }

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
		// Import the project
		ProjectImporter importer = (ProjectImporter)selectionList.getSelectedItem();
		importer.initialize(uri);
		Integer projectID = importer.importProject(name, author, description);
		ProjectController.loadProject(harmonyModel,projectID);
	}
}