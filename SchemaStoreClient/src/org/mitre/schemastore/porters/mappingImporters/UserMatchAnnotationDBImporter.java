// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.mappingImporters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.porters.ImporterException;

public class UserMatchAnnotationDBImporter extends MappingImporter
{
	/** Stores the connection to the DB from which the mapping is being transferred */
	Connection conn = null;

	/** Returns the importer name */
	public String getName()
		{ return "User Match Annotation DB Importer"; }

	/** Returns the importer description */
	public String getDescription()
		{ return "Custom mapping importer from DB for HSIP project"; }

	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes()
	{
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".mdb");
		return fileTypes;
	}

	/** Returns the importer URI type */
	public Integer getURIType()
		{ return FILE; }

	/** Initializes the importer */
	protected void initialize() throws ImporterException
	{
		String filename = "";
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			//uri comes in as file:/foo, needs to be foo
			filename = uri.toString().substring(6);
			
			//uris replace spaces with %20s.  Fix that.
			filename = filename.replaceAll("%20"," "); 
			String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
            database+= filename + ";DriverID=22;READONLY=true}"; // add on to the end 
            // now we can get the connection from the DriverManager
            conn = DriverManager.getConnection( database ,"",""); 
            //end file method
			
			System.out.println ("Database connection established."); 
		}
		catch(Exception e) { 
			// If anything goes wrong, alert on the command line.
		    System.err.println ("Cannot connect to database server " + filename + "!"); 
		    e.printStackTrace(); 
			//throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); 
	    }	
	}

	/** Returns the source schema from the specified URI */
	public ProjectSchema getSourceSchema() { 
		try {
			ArrayList<Schema> schemas = client.getSchemas();
		
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from T_Schema");
			int sourceID=-1;  int sourceMatch=0; 
		
			rs.next();
			String sourceName = rs.getString("schemaName");
			for (int i=0; i<schemas.size(); i++) {
				if (sourceName.equals(schemas.get(i).getName())) {
					sourceMatch++;
					sourceID = schemas.get(i).getId();
				}					
			}
			if (sourceID>-1 && sourceMatch==1)  {
				ProjectSchema schema = new ProjectSchema();
				schema.setId(sourceID);
				schema.setName(sourceName);
				schema.setModel("");
				return schema;	
			}
			else if (sourceMatch>1) { 
				throw new ImporterException(ImporterException.PARSE_FAILURE,"Error - more than one schema exists with name '"+sourceName+"'."); 
			}
			else { throw new ImporterException(ImporterException.PARSE_FAILURE,"Error - schema with name '"+sourceName+"' does not exist.");  }
		}
		catch(Exception e) { 
			// If anything goes wrong, alert on the command line.
		    System.err.println ("Cannot connect to database server. "); 
		    e.printStackTrace(); 
			//throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); 
	    }	
		return null;		
	}

	/** Returns the target schema from the specified URI */
	public ProjectSchema getTargetSchema() { 
		try {
			ArrayList<Schema> schemas = client.getSchemas();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from T_Schema");
			int targetID=-1; int targetMatch=0;
			
			rs.next(); rs.next();
			String targetName = rs.getString("schemaName");
			for (int i=0; i<schemas.size(); i++) {
				if (targetName.equals(schemas.get(i).getName())) {
					targetMatch++;
					targetID = schemas.get(i).getId();
				}					
			}
			if (targetID>-1 && targetMatch==1)  { 	
				ProjectSchema schema = new ProjectSchema();
				schema.setId(targetID);
				schema.setName(targetName);
				schema.setModel("");
				return schema;	
			}
			else if (targetMatch>1) { 
				throw new ImporterException(ImporterException.IMPORT_FAILURE,"Error - more than one schema exists with name '"+targetName+"'."); 
			}
			else { throw new ImporterException(ImporterException.IMPORT_FAILURE,"Error - schema with name '"+targetName+"' does not exist.");  }			
		}
		catch(Exception e) { 
			// If anything goes wrong, alert on the command line.
		    System.err.println ("Cannot connect to database server. "); 
		    e.printStackTrace(); 
			//throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); 
	    }			
		return null;
	}

	/** Returns the mapping cells from the specified URI */
	public ArrayList<MappingCell> getMappingCells() throws ImporterException
	{
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();

		// Retrieve mapping cells from database
		try {
			HierarchicalSchemaInfo hsi1 = new HierarchicalSchemaInfo(client.getSchemaInfo(source.getId()), null);
			HierarchicalSchemaInfo hsi2 = new HierarchicalSchemaInfo(client.getSchemaInfo(target.getId()), null);	
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from T_Matchlink");
			
			while(rs.next())
			{
				String notes = rs.getString("linkNote");
				
				// Path is stored in DB as "#parent#child#child"
				// Chop the first #
				String nodePath1 = rs.getString("leftNodePath").substring(1);
				String nodePath2 = rs.getString("rightNodePath").substring(1);
				String author = rs.getString("linkAuthor");
				
				ArrayList<String> path1 = new ArrayList<String>(Arrays.asList(nodePath1.split("#")));
				ArrayList<String> path2 = new ArrayList<String>(Arrays.asList(nodePath2.split("#")));

				ArrayList<Integer> pathIds1 = hsi1.getPathIDs(path1);
				ArrayList<Integer> pathIds2 = hsi2.getPathIDs(path2);
				
				if (pathIds1.size() < 1 || pathIds2.size() < 1) {
					String msg = "There is no path from" + path1 + " to " + path2;
					System.out.println(msg);
				}
				else {
					// path array lists have only one item since paths are unique in our schemas
					int element1 = pathIds1.get(0);
					int element2 = pathIds2.get(0);
			
					MappingCell cell = MappingCell.createIdentityMappingCell(null, null, element1, element2, author, Calendar.getInstance().getTime(), notes);
					cell.setScore(rs.getDouble("linkWeight"));
					mappingCells.add(cell);
				}
			}
			stmt.close();
		}
		catch (Exception e)
		{
		    System.err.println ("Error reading DB");
		    e.printStackTrace();
		}

		return mappingCells;
	}
}