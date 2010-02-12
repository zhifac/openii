package org.mitre.harmony.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.harmony.model.project.ProjectMapping;
import org.mitre.harmony.view.dialogs.project.ProjectDialog;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;

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

	/** Automatically selects the mappings to display */
	static public void selectMappings(HarmonyModel harmonyModel)
	{
		// Checks to see if all mappings can be displayed simultaneously
		boolean displayAll = true;
		HashSet<Integer> sourceIDs = new HashSet<Integer>();
		HashSet<Integer> targetIDs = new HashSet<Integer>();
		for(ProjectMapping mapping : harmonyModel.getMappingManager().getMappings())
		{
			sourceIDs.add(mapping.getSourceId());
			targetIDs.add(mapping.getTargetId());
			if(sourceIDs.contains(mapping.getTargetId()) || targetIDs.contains(mapping.getSourceId()))
				{ displayAll = false; break; }
		}
		
		// Display all mappings if possible
		if(displayAll)
			for(ProjectMapping mapping : harmonyModel.getMappingManager().getMappings())
				mapping.setVisibility(true);
		else new ProjectDialog(harmonyModel);
	}
	
	/** Saves the specified project */
	static public boolean saveProject(HarmonyModel harmonyModel, Project project)
	{
		try {
			// Set the project schemas
			project.setSchemas(harmonyModel.getProjectManager().getProject().getSchemas());
			
			// Save the project
			if(project.getId()!=null)
			{
				// Collect keys for the current project mappings
				HashSet<String> mappingKeys = new HashSet<String>();
				for(ProjectMapping mapping : harmonyModel.getMappingManager().getMappings())
					mappingKeys.add(mapping.getSourceId() + "_" + mapping.getTargetId());
				
				// Remove mappings which no longer can be supported
				for(Mapping mapping : SchemaStoreManager.getMappings(project.getId()))
					if(!mappingKeys.contains(mapping.getSourceId() + "_" + mapping.getTargetId()))
						if(!SchemaStoreManager.deleteMapping(mapping.getId()))
							throw new Exception("Failed to delete mapping " + mapping.getId());
				
				// Update the project information
				if(!SchemaStoreManager.updateProject(project))
					throw new Exception("Failed to update project");
			}
			else
			{
				Integer projectID = SchemaStoreManager.addProject(project);
				if(project!=null) project.setId(projectID);
				else throw new Exception("Failed to create project");
			}

			// Identify the current project mappings
			HashMap<String,Integer> mappingIDs = new HashMap<String,Integer>();
			for(Mapping mapping : SchemaStoreManager.getMappings(project.getId()))
				mappingIDs.put(mapping.getSourceId() + "_" + mapping.getTargetId(),mapping.getId());
			
			// Save the project mappings
			for(ProjectMapping mapping : harmonyModel.getMappingManager().getMappings())
			{
				// Identify the mapping where to save the 
				Integer mappingID = mappingIDs.get(mapping.getSourceId() + "_" + mapping.getTargetId());
				if(mappingID==null)
				{
					Mapping newMapping = mapping.copy();
					newMapping.setProjectId(project.getId());
					mapping.setId(SchemaStoreManager.addMapping(newMapping));
				}
				else mapping.setId(mappingID);
				if(mapping.getId()==null) throw new Exception("Failed to create mapping");

				// Save the mapping cells to the mapping
				SchemaStoreManager.saveMappingCells(mapping.getId(), mapping.getMappingCells());
			}
			
			// Indicates that the mapping has been modified
			harmonyModel.getProjectManager().setModified(false);
			return true;
		}
		catch(Exception e) { System.out.println("(E) ProjectController.saveProject - " + e.getMessage()); return false; }
	}
}