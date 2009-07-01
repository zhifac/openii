// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.schemastore.porters.mappingExporters;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.filechooser.FileFilter;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;

import java.sql.*;

public class UserMatchAnnotationDBExporter  extends UserMatchAnotationExporter {
	
//	protected URI exportUri = new URI("test.mdb");
	Connection conn = null; 
    
	/** Constructs the DataDictionary exporter */
	public UserMatchAnnotationDBExporter()
		{ 
		super(); 
		}
	
	/** Returns the file types associated with this converter */
	public String getFileType()
		{ return "mdb"; }
	
	/** Returns a file filter for this converter */
	public FileFilter getFileFilter()
	{
		class MdbFilter extends FileFilter
		{
			public boolean accept(File file)
			{
				if(file.isDirectory()) return true;
				if(file.toString().endsWith(".mdb")) return true;
				return false;
			}

			public String getDescription()
				{ return "User Annotation Match Database (.mdb)"; }
		}
		return new MdbFilter();
	}
	
	/** Generates a list of the top 100 matches for this project */
	public void exportMapping(Mapping mapping, ArrayList<MappingCell> mappingCells, File file) throws IOException
	{		
		String dataSourceName = "mdbTEST";
		Connection con;
		
		// Initializes the db for the specified URI
		try {
			//  connect to MS Access database
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String dbURL = "jdbc:odbc:" + dataSourceName;
	        con = DriverManager.getConnection(dbURL, "","");
	        System.out.println ("Database connection established."); 
			     
	        
	        // Delete the old table
//FIXME CHECK IF EXISTS FIRST
	        Statement s = con.createStatement();
	        s.executeUpdate("drop table T_MatchLink");
	        
	        
	        // Create the table
	        s = con.createStatement();
	        s.execute("create table T_MatchLink ( " +
	        		"linkIdentifier_PK integer," +
	        		"leftSchema_FK integer," +
	        		"leftNodePath string," +
	        		"rightSchema_FK integer," +
	        		"rightNodePath string," +
	        		"linkWeight string," +
	        		"linkAuthor string," +
	        		"linkDateTime string," +
	        		"linkTransform string," +
	        		"linkNote string)"); // create a table      
	        s.close();	        	        
	
			// Get the list of mapping cells
			CompressedList matchList = new CompressedList();
			for(MappingCell mappingCell : mappingCells)
				matchList.addMappingCell(mappingCell);
			

			
			
	   	// Outputs the top mapping cells
	    	List<CompressedMatch> matches = matchList.getMatches();
			Collections.sort(matches);
//			if(matches.size()>count) matches = matches.subList(0, count);
	  		
			int linkIdentifier_PK = 0; // the primary key for the table
			for(CompressedMatch match : matches) {
	 
//	  			if ((match.getScore() >= minConfThreshold ) || (match.getScore() < 0.0) ) {
	  				
	  				
	  				
	  				
	  				
//  			        s.execute("insert into T_MatchLink values( " +
//  			        		"1," +
//  			        		"1," +
//  			        		"'nodepathJOEL', " +
//  			        		"'nodepath' )"); // create a table      


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
  					

	  				// create a table      	         	
  	  				
  					s = con.createStatement();

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
//	  		}
		} //end try

		catch(Exception e) { 
		    System.err.println ("Error with databse" + dataSourceName); 
		    e.printStackTrace(); 
		}
	}

	

}
