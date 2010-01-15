// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data;

import java.util.ArrayList;
import java.util.Arrays;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;

/** Class for managing the projects in the schema repository */
public class Projects extends DataCache
{
	/** Constructs the project cache */
	Projects(DataManager manager)
		{ super(manager); }

	/** Returns a listing of all projects */
	public ArrayList<Project> getProjects()
		{ return getDatabase().getProjects(); }

	/** Retrieve the specified project */
	public Project getProject(Integer projectID)
		{ return getDatabase().getProject(projectID); }

	/** Add the specified project */
	public Integer addProject(Project project)
		{ return getDatabase().addProject(project); }

	/** Update the specified project */
	public Boolean updateProject(Project project)
	{
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>(Arrays.asList(project.getSchemaIDs()));
		for(Mapping mapping : getMappings(project.getId()))
			if(!schemaIDs.contains(mapping.getSourceId()) || !schemaIDs.contains(mapping.getTargetId())) return false;
		return getDatabase().updateProject(project);
	}

	/** Delete the specified project */
	public Boolean deleteProject(Integer projectID)
		{ return getDatabase().deleteProject(projectID); }

	/** Returns a listing of mappings for the specified project */
	public ArrayList<Mapping> getMappings(Integer projectID)
		{ return getDatabase().getMappings(projectID); }

	/** Retrieve the specified mapping */
	public Mapping getMapping(Integer mappingID)
		{ return getDatabase().getMapping(mappingID); }
	
	/** Add the specified mapping */
	public Integer addMapping(Mapping mapping)
	{
		Integer mappingID = 0;
		try {
			ArrayList<Integer> schemaIDs = new ArrayList<Integer>(Arrays.asList(getDatabase().getProject(mapping.getId()).getSchemaIDs()));
			if(mapping.getSourceId().equals(mapping.getTargetId())) return 0;
			if(!schemaIDs.contains(mapping.getSourceId()) || !schemaIDs.contains(mapping.getTargetId())) return 0;
			mappingID = getDatabase().addMapping(mapping);
		} catch(Exception e) {}
		return mappingID;
	}

	/** Delete the specified mapping */
	public Boolean deleteMapping(Integer mappingID)
		{ return getDatabase().deleteMapping(mappingID); }
	
	/** Get the mapping cells for the specified mapping */
	public ArrayList<MappingCell> getMappingCells(Integer mappingID)
		{ return getDatabase().getMappingCells(mappingID); }

	/** Add the specified mapping cell */
	public Integer addMappingCell(MappingCell mappingCell)
		{ return getDatabase().addMappingCell(mappingCell); }

	/** Update the specified mapping cell */
	public Boolean updateMappingCell(MappingCell mappingCell)
		{ return getDatabase().updateMappingCell(mappingCell); }

	/** Delete the specified mapping cell */
	public Boolean deleteMappingCell(Integer mappingCellID)
		{ return getDatabase().deleteMappingCell(mappingCellID); }
}