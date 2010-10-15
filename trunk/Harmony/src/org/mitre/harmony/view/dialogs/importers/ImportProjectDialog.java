// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.harmony.view.dialogs.importers;

import java.net.URI;
import java.util.ArrayList;

import org.mitre.harmony.controllers.ProjectController;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.porters.PorterType;
import org.mitre.schemastore.porters.projectImporters.ProjectImporter;

/** Class for displaying the project importer dialog */
public class ImportProjectDialog extends AbstractImportDialog
{	
	/** Constructs the importer dialog */
	public ImportProjectDialog(HarmonyModel harmonyModel)
		{ super(harmonyModel.getBaseFrame(), harmonyModel); setVisible(true); }
	
	/** Returns the type of importer being run */
	public PorterType getPorterType() { return PorterType.PROJECT_IMPORTERS; }

	/** Returns the list of used project names */
	protected ArrayList<String> getUsedNames()
	{ 
		ArrayList<String> usedNames = new ArrayList<String>();
		for(Project project : harmonyModel.getProjectManager().getProjects())
			usedNames.add(project.getName());
		return usedNames;
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