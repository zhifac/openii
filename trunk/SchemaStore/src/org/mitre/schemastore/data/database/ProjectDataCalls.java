// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;

/**
 * Handles project data calls in the database
 * @author CWOLF
 */
public class ProjectDataCalls extends AbstractDataCalls
{	
	/** Constructs the data call class */
	ProjectDataCalls(DatabaseConnection connection) { super(connection); }

	//----------------------------------
	// Handles Projects in the Database
	//----------------------------------

	/** Retrieves the list of projects in the repository */
	public ArrayList<Project> getProjects()
	{
		ArrayList<Project> projects = new ArrayList<Project>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id FROM project");
			while(rs.next())
				projects.add(getProject(rs.getInt("id")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) ProjectDataCalls:getProjects: "+e.getMessage()); }
		return projects;
	}

	/** Retrieves the specified project from the repository */
	public Project getProject(Integer projectID)
	{
		Project project = null;
		try {
			Statement stmt = connection.getStatement();

			// Get the schemas associated with project
			ArrayList<ProjectSchema> schemas = new ArrayList<ProjectSchema>();
			ResultSet rs = stmt.executeQuery("SELECT schema_id,name,model FROM project_schema,\"schema\" WHERE project_id="+projectID+" AND schema_id=id");
			while(rs.next())
				schemas.add(new ProjectSchema(rs.getInt("schema_id"),rs.getString("name"),rs.getString("model")));
			
			// Get the project information
			ResultSet rs2 = stmt.executeQuery("SELECT name,description,author FROM project WHERE id="+projectID);
			if(rs2.next())
				project = new Project(projectID,rs2.getString("name"),rs2.getString("description"),rs2.getString("author"),schemas.toArray(new ProjectSchema[0]));

			stmt.close();
		} catch(SQLException e) { System.out.println("(E) ProjectDataCalls:getProject: "+e.getMessage()); }
		return project;
	}

	/** Retrieves the list of projects associated with the specified schema in the repository */
	public ArrayList<Integer> getSchemaProjectIDs(Integer schemaID)
	{
		ArrayList<Integer> projectIDs = new ArrayList<Integer>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT project_id FROM project_schema WHERE schema_id="+schemaID);
			while(rs.next())
				projectIDs.add(rs.getInt("project_id"));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) ProjectDataCalls:getSchemaProjects: "+e.getMessage()); }
		return projectIDs;
	}

	/** Adds the specified project */
	public Integer addProject(Project project)
	{
		Integer projectID = 0;
		try {
			projectID = getUniversalIDs(1);
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("INSERT INTO project(id,name,description,author) VALUES("+projectID+",'"+scrub(project.getName(),100)+"','"+scrub(project.getDescription(),4096)+"','"+scrub(project.getAuthor(),100)+"')");
			for(ProjectSchema schema : project.getSchemas())
				stmt.executeUpdate("INSERT INTO project_schema(project_id,schema_id,model) VALUES("+projectID+","+schema.getId()+",'"+schema.getModel()+"')");
			stmt.close();
			connection.commit();
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			projectID = 0;
			System.out.println("(E) ProjectDataCalls:addProject: "+e.getMessage());
		}
		return projectID;
	}

	/** Updates the specified project */
	public boolean updateProject(Project project)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("UPDATE project SET name='"+scrub(project.getName(),100)+"', description='"+scrub(project.getDescription(),4096)+"', author='"+scrub(project.getAuthor(),100)+"' WHERE id="+project.getId());
			stmt.executeUpdate("DELETE FROM project_schema WHERE project_id="+project.getId());
			for(ProjectSchema schema : project.getSchemas())
				stmt.executeUpdate("INSERT INTO project_schema(project_id,schema_id,model) VALUES("+project.getId()+","+schema.getId()+",'"+schema.getModel()+"')");
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) ProjectDataCalls:updateProject: "+e.getMessage());
		}
		return success;
	}

	/** Deletes the specified project */
	public boolean deleteProject(int projectID)
	{
		boolean success = false;
		try {
			// Delete all mappings associated with the project
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id FROM mapping WHERE project_id="+projectID);
			while(rs.next())
				deleteMapping(rs.getInt("id"));
			
			// Delete the project
			stmt.executeUpdate("DELETE FROM project_schema WHERE project_id="+projectID);
			stmt.executeUpdate("DELETE FROM project WHERE id="+projectID);
			stmt.close();

			// Commit the deletion of the project
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) ProjectDataCalls:deleteProject: "+e.getMessage());
		}
		return success;
	}

	//----------------------------------
	// Handles Mappings in the Database
	//----------------------------------

	/** Retrieves the list of mappings for the specified project ID */
	public ArrayList<Mapping> getMappings(Integer projectID, boolean vocabularyMappings)
	{
		ArrayList<Mapping> mappings = new ArrayList<Mapping>();
		try {
			Statement stmt = connection.getStatement();

			// Get the vocabulary ID
			Integer vocabularyID = null;
			ResultSet rs = stmt.executeQuery("SELECT vocabulary_id FROM project WHERE id="+projectID);
			if(rs.next()) vocabularyID = rs.getInt("vocabulary_id");
			if((vocabularyID==null || vocabularyID.equals(0)) && vocabularyMappings) return mappings;
			
			// Construct the mapping query
			String query = "SELECT id,source_id,target_id FROM mapping WHERE project_id="+projectID;
			if(vocabularyID!=null)
				query += " AND target_id" + (vocabularyMappings?"=":"!=") + vocabularyID;

			// Retrieve the mappings
			rs = stmt.executeQuery(query);
			while(rs.next())
				mappings.add(new Mapping(rs.getInt("id"),projectID,rs.getInt("source_id"),rs.getInt("target_id")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) ProjectDataCalls:getMappings: "+e.getMessage()); }
		return mappings;		
	}
	
	/** Retrieves the specified mapping */
	public Mapping getMapping(Integer mappingID)
	{
		Mapping mapping = null;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT project_id,source_id,target_id FROM mapping WHERE id="+mappingID);
			if(rs.next())
				mapping = new Mapping(mappingID,rs.getInt("project_id"),rs.getInt("source_id"),rs.getInt("target_id"));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) ProjectDataCalls:getMapping: "+e.getMessage()); }
		return mapping;
	}
	
	/** Adds the specified mapping */
	public Integer addMapping(Mapping mapping)
	{
		Integer mappingID = 0;
		try {
			mappingID = getUniversalIDs(1);
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("INSERT INTO mapping(id,project_id,source_id,target_id) VALUES("+mappingID+","+mapping.getProjectId()+","+mapping.getSourceId()+","+mapping.getTargetId()+")");
			stmt.close();
			connection.commit();
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			mappingID = 0;
			System.out.println("(E) ProjectDataCalls:addMapping: "+e.getMessage());
		}
		return mappingID;
	}

	/** Deletes the specified mapping */
	public boolean deleteMapping(int mappingID)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("DELETE FROM mapping_cell WHERE mapping_id="+mappingID);
			stmt.executeUpdate("DELETE FROM mapping WHERE id="+mappingID);
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) ProjectDataCalls:deleteMapping: "+e.getMessage());
		}
		return success;
	}	
	
	//---------------------------------------
	// Handles Mapping Cells in the Database
	//---------------------------------------

	/** Retrieves the list of mapping cells in the repository for the specified mapping */
	public ArrayList<MappingCell> getMappingCells(Integer mappingID)
	{
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		try {
            // Retrieve mapping cells
			Statement stmt = connection.getStatement();
            ResultSet rs = stmt.executeQuery("SELECT id,input_ids,output_id,score,function_id,author,modification_date,notes FROM mapping_cell WHERE mapping_id="+mappingID);
            while(rs.next())
            {            	
                // Retrieves the mapping cell info
                int cellID = rs.getInt("id");
                Integer output = rs.getInt("output_id");
                Double score = rs.getDouble("score");
                Integer functionID = rs.getString("function_id")==null?null:rs.getInt("function_id");
                String author = rs.getString("author");
                Date date = rs.getDate("modification_date");
                String notes = rs.getString("notes");
              
                // Get the list of mapping inputs
                ArrayList<Integer> inputs = new ArrayList<Integer>();
                for(String input : rs.getString("input_ids").split(","))
                	inputs.add(Integer.parseInt(input.trim()));
                Integer[] inputArray = inputs.toArray(new Integer[0]);
                
                // Store the mapping cell
                mappingCells.add(new MappingCell(cellID,mappingID,inputArray,output,score,functionID,author,date,notes));
            }
            rs.close();

            // Close the statements
            stmt.close();
		}
		catch(SQLException e) { System.out.println("(E) ProjectDataCalls:getMappingCells: "+e.getMessage());}
		return mappingCells;
	}

	/** Adds the specified mapping cell */
	public Integer addMappingCell(MappingCell mappingCell)
		{ return addMappingCell(mappingCell, true); }

	/** Adds the specified mapping cell */
	private Integer addMappingCell(MappingCell mappingCell, boolean commit)
	{
		// Define various insert statements
        String mappingCellInsert = "INSERT INTO mapping_cell(id, mapping_id, input_ids, output_id, score, function_id, author, modification_date, notes) VALUES (?,?,?,?,?,?,?,?,?)";

        // Insert the mapping cell
        Integer mappingCellID = 0;
        try {
            mappingCellID = getUniversalIDs(1);
            Date date = new Date(mappingCell.getModificationDate().getTime());

            // Generate the input IDs
            String inputIDs = "";
            for(Integer input : mappingCell.getInput())
            	inputIDs += input + ",";
            inputIDs = inputIDs.substring(0, inputIDs.length()-1);

            // Stores the validated mapping cell
            PreparedStatement stmt = connection.getPreparedStatement(mappingCellInsert);
            stmt.setInt(1, mappingCellID);
            stmt.setInt(2, mappingCell.getMappingId());
            stmt.setString(3, inputIDs);
            stmt.setInt(4, mappingCell.getOutput());
            stmt.setDouble(5, mappingCell.getScore());
            if(mappingCell.getFunctionID()==null) stmt.setNull(6, Types.INTEGER);
            else stmt.setInt(6, mappingCell.getFunctionID());
            stmt.setString(7, scrub(mappingCell.getAuthor(),100));
            stmt.setDate(8, date);
            stmt.setString(9, scrub(mappingCell.getNotes(),4096));
            stmt.executeUpdate();
            stmt.close();
 
        	// Commit the mapping cell to the database
        	if(commit) connection.commit();
        }
        catch(SQLException e)
        {
            try { connection.rollback(); } catch(SQLException e2) {}
            mappingCellID = 0;
            System.out.println("(E) ProjectDataCalls:addMappingCell: "+e.getMessage());
        }

		return mappingCellID;
	}

	/** Updates the specified mapping cell */
	public boolean updateMappingCell(MappingCell mappingCell)
	{
		boolean success = true;
		try {
            success = success && deleteMappingCell( mappingCell.getId(), false );
            success = success && (!addMappingCell( mappingCell, false ).equals(Integer.valueOf(0)));
			if (success) connection.commit();
            else { connection.rollback(); return false; }
        }
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) ProjectDataCalls:updateMappingCell: "+e.getMessage());
            return false;
		}
        return true;
	}

	/** Deletes the specified mapping cell */
	public boolean deleteMappingCell(int mappingCellID)
		{ return deleteMappingCell(mappingCellID, true); }

    /** Deletes the specified mapping cell */
	private boolean deleteMappingCell(int mappingCellID, boolean commit)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
            stmt.executeUpdate("DELETE FROM mapping_cell WHERE id="+mappingCellID);
			stmt.close();
            if(commit) connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) ProjectDataCalls:deleteMappingCell: "+e.getMessage());
		}
		return success;
	}
	
	//-------------------------------------------
	// Handles the vocabulary ID in the Database
	//-------------------------------------------
	
	/** Returns the project vocabulary schema id from the repository */
	public Integer getVocabularyID(Integer projectID)
	{
		Integer vocabularyID = null;
		try {			
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT vocabulary_id FROM project WHERE id="+projectID);
			if(rs.next()) vocabularyID = rs.getInt("vocabulary_id");
			if(vocabularyID!=null && vocabularyID.equals(0)) vocabularyID=null;
			stmt.close();
		}
		catch(SQLException e) { System.out.println("(E) VocabularyDataCalls:getVocabularyID: "+e.getMessage()); }
		return vocabularyID;
	}
	
	/** Sets the project vocabulary schema id in the repository */
	public boolean setVocabularyID(Integer projectID, Integer vocabularyID)
	{
		boolean success = false;
		try {			
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("UPDATE project SET vocabulary_id="+vocabularyID+" WHERE id="+projectID);	
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) VocabularyDataCalls:setVocabularyID: "+e.getMessage());
		}
		return success;
	}

	
	/** Deletes the project vocabulary ID from the project in the repository */
	public boolean deleteVocabularyID(Integer projectID)
	{
		boolean success = false;
		try {			
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("UPDATE project SET vocabulary_id=NULL WHERE id="+projectID);	
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) VocabularyDataCalls:deleteVocabularyID: "+e.getMessage());
		}
		return success;
	}
}