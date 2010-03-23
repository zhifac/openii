// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.schemastore.porters.mappingExporters;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;

public class UserMatchAnnotationDBExporter  extends UserMatchAnnotationExporter
{
	/** Constructs the DataDictionary exporter */
	public UserMatchAnnotationDBExporter()
		{ super(); }
	
	/** Returns the file types associated with this converter */
	public String getFileType()
		{ return ".mdb"; }
	
	/** Returns the exporter name */
	public String getName()
		{ return "User Annotation Match DB Exporter"; }
	
	/** Returns the exporter description */
	public String getDescription()
		{ return "This exporter can be used to export all pairings of terms within the mapping"; }

	/** Generates a list of the top 100 matches for this project */
	public void exportMapping(Project project, Mapping mapping, ArrayList<MappingCell> mappingCells, File file) throws IOException
	{		
		String dataSourceName = "mdbTEST";
		Connection conn;
		
		// Initializes the db for the specified URI
		try {
			//  connect to MS Access database
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String dbURL = "jdbc:odbc:" + dataSourceName;
	        conn = DriverManager.getConnection(dbURL, "","");
	        System.out.println ("Database connection established."); 
			     
	        
	        // Delete the old table
//FIXME CHECK IF EXISTS FIRST
	        Statement s = conn.createStatement();
	        s.executeUpdate("DROP TABLE T_MatchLink");
	        
	        // Create the table
	        s.execute("CREATE TABLE T_MatchLink (linkIdentifier_PK INTEGER," +
	        									"leftSchema_FK INTEGER," +
	        									"leftNodePath STRING," +
	        									"rightSchema_FK INTEGER," +
	        									"rightNodePath STRING," +
	        									"linkWeight STRING," +
	        									"linkAuthor STRING," +
	        									"linkDateTime STRING," +
	        									"linkTransform STRING," +
	        									"linkNote STRING)");
	        s.close();	        	        

			// Get the list of mapping cells
			CompressedList matchList = new CompressedList();
			for(MappingCell mappingCell : mappingCells)
				matchList.addMappingCell(mappingCell);
			
	   		List<CompressedMatch> matches = matchList.getMatches();
			Collections.sort(matches);
//			if(matches.size()>count) matches = matches.subList(0, count);
	  		
			int linkIdentifier_PK = 0; // the primary key for the table
			for(CompressedMatch match : matches) {
	 

//FIXME: Just faking these for now
				int leftSchema_FK = 1;
				int rightSchema_FK = 1;
				
				String leftNodePath = match.getPaths1();
				String rightNodePath = match.getPaths2();
				double linkWeight = match.getScore();
				String linkAuthor = match.getAuthor();
				String linkDateTime = match.getDate();
				String linkTransform = match.getTransform();
				String linkNote = match.getNotes();
				
				// Escape any single 's by doubling them for the SQL insert query
				// Some of these fields shouldn't ever have 's, but being safe.
				leftNodePath = leftNodePath.replaceAll("'", "''");
				rightNodePath = rightNodePath.replaceAll("'", "''");
				linkAuthor = linkAuthor.replaceAll("'", "''");
				linkDateTime = linkDateTime.replaceAll("'", "''");
				linkTransform = linkTransform.replaceAll("'", "''");
				linkNote = linkNote.replaceAll("'", "''");
				

  				// create a table      	         	
  				
				s = conn.createStatement();

				s.execute("insert into T_MatchLink values( " +
						linkIdentifier_PK + ", " + 
						leftSchema_FK + ", " +
						"'" + leftNodePath + "'" + ", " +
						rightSchema_FK+ ", " +
						"'" + rightNodePath + "'" + ", " +
						linkWeight+ ", " +
						"'" + linkAuthor + "'"+ ", " +
						"'" + linkDateTime + "'"+ ", " +
						"'" + linkTransform + "'"+ ", " +
						"'" + linkNote + "'" + ")");
					  
				s.close();
  			}
  			linkIdentifier_PK++;

		} //end try

		catch(Exception e) { 
		    System.err.println ("Error with database" + dataSourceName); 
		    e.printStackTrace(); 
		}
	}
}
