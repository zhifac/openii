package org.mitre.harmony.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;

/** Class for various project controls */
public class ProjectController
{
	/** Creates a new project */
	static public void newProject(HarmonyModel harmonyModel)
	{
		// Clear out all old project settings
		harmonyModel.getPreferences().unmarkAllFinished();
		harmonyModel.getMappingManager().removeAllMappings();

		// Set up a new project
		Project project = new Project();
		project.setAuthor(System.getProperty("user.name"));		
		harmonyModel.getProjectManager().setProject(project);
	}
	
	/** Loads the specified project */
	static public boolean loadProject(HarmonyModel harmonyModel, Integer projectID)
	{
		// Retrieve project information from repository
		Project project = null;
		ArrayList<Mapping> mappings = new ArrayList<Mapping>();
		ArrayList<ArrayList<MappingCell>> mappingCells = new ArrayList<ArrayList<MappingCell>>();
		try {
			project = SchemaStoreManager.getProject(projectID);
			for(Mapping mapping : SchemaStoreManager.getMappings(projectID))
			{
				mappings.add(mapping);
				mappingCells.add(SchemaStoreManager.getMappingCells(mapping.getId()));					
				for(MappingCell mappingCell : mappingCells.get(mappingCells.size()-1))
					mappingCell.setId(null);
			}
		}
		catch(Exception e) { System.out.println("(E) ProjectController:loadProject - " + e.getMessage()); return false; }
		
		// Clear out all old project settings
		harmonyModel.getPreferences().unmarkAllFinished();
		harmonyModel.getMappingManager().removeAllMappings();
			
		// Sets the project information
		harmonyModel.getProjectManager().setProject(project);

		// Sets the mapping information
		for(int i=0; i<mappings.size(); i++)
		{
			Mapping mapping = mappings.get(i);
			harmonyModel.getMappingManager().addMapping(mapping);
			harmonyModel.getMappingManager().getMapping(mapping.getId()).setMappingCells(mappingCells.get(i));
		}

		// Indicates that the project was successfully loaded
		return true;
	}
	
	/** Saves the specified project */
	static public boolean saveProject(Project project)
	{
		try {
			// Update the project schema settings
			project.setSchemas(this.project.getSchemas());
			
			// Save the project
			Integer projectID = SchemaStoreManager.saveProject(project, getModel().getMappingManager().getMappingCells());
			if(projectID==null) throw new Exception("Failed to save mapping");
			this.project.setId(projectID);
			
			// Indicates that the mapping has been modified
			setModified(false);
			return true;
		}
		catch(Exception e) { System.out.println("(E) MappingManager.saveMapping - " + e.getMessage()); return false; }
	}
}