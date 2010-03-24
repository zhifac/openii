// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.mappingImporters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.mapfunctions.IdentityFunction;
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
		{ return new ArrayList<String>(Arrays.asList(new String[]{".mdb"})); }

	/** Returns the importer URI type */
	public Integer getURIType()
		{ return FILE; }

	/** Initializes the importer */
	protected void initialize() throws ImporterException
	{
		// Connects to the MS Access database
		String dataSourceName = "mdbTEST";
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String dbURL = "jdbc:odbc:" + dataSourceName;
	        conn = DriverManager.getConnection(dbURL, "","");
	        System.out.println ("Database connection established.");
		}

		catch(Exception e)
		{
		    System.err.println ("Error with database" + dataSourceName);
		    e.printStackTrace();
		}
	}

	/** Returns the source schema from the specified URI */
	public ProjectSchema getSourceSchema()
		{ return new ProjectSchema(null,"HSIP",null); }

	/** Returns the target schema from the specified URI */
	public ProjectSchema getTargetSchema()
		{ return new ProjectSchema(null,"IPT",null); }

	/** Returns the mapping cells from the specified URI */
	public ArrayList<MappingCell> getMappingCells() throws ImporterException
	{
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();

		// Retrieve info for the specified schemas
		HierarchicalSchemaInfo hg1 = null, hg2 = null;
		try {
			hg1 = new HierarchicalSchemaInfo(client.getSchemaInfo(source.getId()), null);
			hg2 = new HierarchicalSchemaInfo(client.getSchemaInfo(target.getId()), null);
		}
		catch (Exception e)
		{
		    System.err.println ("Error getting schema info");
		    e.printStackTrace();
		}

		// Retrieve mapping cells from database
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from HSIPMIPT");
			while(rs.next())
			{
				String notes = rs.getString("MPNotes");
				
				// Path is stored in DB as "#parent#child#child"
				// Chop the first #
				String nodePath1 = rs.getString("HSIP-Path").substring(0);
				String nodePath2 = rs.getString("IPT-Path").substring(1);
				
				ArrayList<String> path1 = new ArrayList<String>(Arrays.asList(nodePath1.split("#")));
				ArrayList<String> path2 = new ArrayList<String>(Arrays.asList(nodePath2.split("#")));

				ArrayList<Integer> pathIds1 = hg1.getPathIDs(path1);
				ArrayList<Integer> pathIds2 = hg2.getPathIDs(path2);
				
				if (pathIds1.size() < 1 || pathIds2.size() < 1) {
					System.out.println("There is no path to " + path1 + " or " + path2);
					System.out.println("Ignoring link.");
				}
				else {
					// path array lists have only one item since paths are unique in our schemas
					int element1 = pathIds1.get(0);
					int element2 = pathIds2.get(0);
			
					MappingCell cell = MappingCell.createValidatedMappingCell(null, null, new Integer[]{element1}, element2, "", Calendar.getInstance().getTime(), IdentityFunction.class.getCanonicalName(), notes);
					mappingCells.add(cell);
				}
			}
			stmt.close();

		}
		catch (SQLException e)
		{
		    System.err.println ("Error reading DB");
		    e.printStackTrace();
		}

		return mappingCells;
	}
}