// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data;

import java.util.ArrayList;
import java.util.Arrays;

import org.mitre.schemastore.data.database.ProjectDataCalls;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;

/** Class for managing the projects in the schema repository */
public class ProjectCache extends DataCache
{
	/** Stores reference to the project data calls */
	private ProjectDataCalls dataCalls = null;
	
	/** Constructs the project cache */
	ProjectCache(DataManager manager, ProjectDataCalls dataCalls)
		{ super(manager); this.dataCalls=dataCalls; }

	/** Returns a listing of all projects */
	public ArrayList<Project> getProjects()
		{ return dataCalls.getProjects(); }

	/** Retrieve the specified project */
	public Project getProject(Integer projectID)
		{ return dataCalls.getProject(projectID); }

	/** Retrieves the list of projects associated with the specified schema */
	public ArrayList<Integer> getSchemaProjectIDs(Integer schemaID)
		{ return dataCalls.getSchemaProjectIDs(schemaID); }
	
	/** Add the specified project */
	public Integer addProject(Project project)
		{ return dataCalls.addProject(project); }

	/** Update the specified project */
	public Boolean updateProject(Project project)
	{
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>(Arrays.asList(project.getSchemaIDs()));
		for(Mapping mapping : getMappings(project.getId()))
			if(!schemaIDs.contains(mapping.getSourceId()) || !schemaIDs.contains(mapping.getTargetId())) return false;
		return dataCalls.updateProject(project);
	}

	/** Delete the specified project */
	public Boolean deleteProject(Integer projectID)
		{ return dataCalls.deleteProject(projectID); }

	/** Returns a listing of mappings for the specified project */
	public ArrayList<Mapping> getMappings(Integer projectID)
		{ return dataCalls.getMappings(projectID); }

	/** Retrieve the specified mapping */
	public Mapping getMapping(Integer mappingID)
		{ return dataCalls.getMapping(mappingID); }
	
	/** Add the specified mapping */
	public Integer addMapping(Mapping mapping)
	{
		Integer mappingID = 0;
		try {
			ArrayList<Integer> schemaIDs = new ArrayList<Integer>(Arrays.asList(dataCalls.getProject(mapping.getProjectId()).getSchemaIDs()));
			if(mapping.getSourceId().equals(mapping.getTargetId())) return 0;
			if(!schemaIDs.contains(mapping.getSourceId()) || !schemaIDs.contains(mapping.getTargetId())) return 0;
			mappingID = dataCalls.addMapping(mapping);
		} catch(Exception e) {}
		return mappingID;
	}

	/** Delete the specified mapping */
	public Boolean deleteMapping(Integer mappingID)
		{ return dataCalls.deleteMapping(mappingID); }
	
	/** Get the mapping cells for the specified mapping */
	public ArrayList<MappingCell> getMappingCells(Integer mappingID)
		{ return dataCalls.getMappingCells(mappingID); }

	/** Add the specified mapping cell */
	public Integer addMappingCell(MappingCell mappingCell)
		{ return dataCalls.addMappingCell(mappingCell); }

	/** Update the specified mapping cell */
	public Boolean updateMappingCell(MappingCell mappingCell)
		{ return dataCalls.updateMappingCell(mappingCell); }

	/** Delete the specified mapping cell */
	public Boolean deleteMappingCell(Integer mappingCellID)
		{ return dataCalls.deleteMappingCell(mappingCellID); }
}