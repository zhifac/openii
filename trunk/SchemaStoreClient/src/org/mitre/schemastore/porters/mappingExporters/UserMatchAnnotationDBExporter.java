// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.schemastore.porters.mappingExporters;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

public class UserMatchAnnotationDBExporter  extends UserMatchAnnotationExporter
{
	/** Constructs the DataDictionary exporter */
	public UserMatchAnnotationDBExporter()
		{ super(); }
	
	/** Returns the file types associated with this converter */
	public String getFileType()
		{ return ".mdb"; }
	
	/** Indicates if the file is overwritten */
	public boolean isFileOverwritten()
		{ return false; }
	
	/** Returns the exporter name */
	public String getName()
		{ return "User Annotation Match DB Exporter"; }
	
	/** Returns the exporter description */
	public String getDescription()
		{ return "This exporter can be used to export all pairings of terms within the mapping"; }
	
	/** Generates a list of the top 100 matches for this project */
	public void exportMapping(Project project, Mapping mapping, ArrayList<MappingCell> mappingCells, File file) throws IOException
	{	
		String schema1Name = project.getSchemas()[0].getName();
		String schema2Name = project.getSchemas()[1].getName();	
		String unqualifiedFileName = project.getName()+" (" +schema1Name+"-to-"+schema2Name+").mdb";
		String filename = file.getParentFile().getPath()+"\\" + scrubFileName(unqualifiedFileName);
		Connection conn;
		
		// Initializes the db for the specified URI
		try {
			//  connect to MS Access database
			File outputMDB = fileCopy(file, filename);
			
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
						
			String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
            database+= outputMDB.getPath() + ";DriverID=22;READONLY=true}"; // add on to the end 
            // now we can get the connection from the DriverManager
            conn = DriverManager.getConnection( database ,"",""); 
			
	        System.out.println ("Database connection established."); 			    
	        
	        // Delete the old table
//FIXME CHECK IF EXISTS FIRST
	        
	        Statement s = conn.createStatement();
			s.executeUpdate("DROP TABLE T_Schema");
	        
			// Create the table
			s.execute("CREATE TABLE T_Schema (schema_PK INTEGER," +
	        									"schemaName STRING," +
	        									"schemaDescription MEMO, " +	        									
	        									"schemaDateTime DATETIME)");
			s.close();	        
						
			int id1 = project.getSchemaIDs()[0];
			int id2 = project.getSchemaIDs()[1];
			String schema1Des = client.getSchema(id1).getDescription();
			String schema2Des = client.getSchema(id2).getDescription();

			s = conn.createStatement();
			s.execute("insert into T_Schema values("+id1+", '"+schema1Name+"', '"+schema1Des+"', Now())");
			s.execute("insert into T_Schema values("+id2+", '"+schema2Name+"', '"+schema2Des+"', Now())");
			s.close();

			s = conn.createStatement();
			// Create the table
			s.execute("DELETE * FROM tblTimestamps");
			s.close();	        
			
			s = conn.createStatement();
			s.execute("insert into tblTimestamps values(Now())");			  
			s.close();
			
	        s = conn.createStatement();
	        s.executeUpdate("DROP TABLE T_MatchLink");
	        
	        // Create the table
	        s.execute("CREATE TABLE T_MatchLink (linkIdentifier_PK INTEGER," +
	        									"leftSchema_FK INTEGER," +
	        									"leftNodePath MEMO," +
	        									"rightSchema_FK INTEGER," +
	        									"rightNodePath MEMO," +
	        									"linkWeight STRING," +
	        									"linkAuthor STRING," +
	        									"linkDateTime STRING," +
	        									"linkTransform STRING," +
	        									"linkNote MEMO)");
	        s.close();	        	        

	        HierarchicalSchemaInfo source = new HierarchicalSchemaInfo(client.getSchemaInfo(mapping.getSourceId()));
			setSourceInfo(source);
			HierarchicalSchemaInfo target = new HierarchicalSchemaInfo(client.getSchemaInfo(mapping.getTargetId()));
			setTargetInfo(target);	        

			System.out.println("#mapping cells="+mappingCells.size());
			// Get the list of mapping cells
			CompressedList matchList = new CompressedList();
			for(MappingCell mappingCell : mappingCells) {
				matchList.addMappingCell(mappingCell);
			}
	   		List<CompressedMatch> matches = matchList.getMatches();
			Collections.sort(matches);
//			if(matches.size()>count) matches = matches.subList(0, count);
	  		
			int linkIdentifier_PK = 0; // the primary key for the table
			for(CompressedMatch match : matches) { 
				linkIdentifier_PK++;

				int leftSchema_FK = mapping.getSourceId();
				int rightSchema_FK = mapping.getTargetId();
				
				String leftNodePath = match.getPaths1();
				String rightNodePath = match.getPaths2();
				double linkWeight = match.getScore();
				String linkAuthor = match.getAuthor();  if (linkAuthor==null || linkAuthor.equals("null")) { linkAuthor=""; }
				String linkDateTime = match.getDate();  if (linkDateTime==null|| linkDateTime.equals("null")) { linkDateTime=""; }
				String linkTransform = match.getTransform();  if (linkTransform==null|| linkTransform.equals("null")) { linkTransform=""; }
				String linkNote = match.getNotes();  if (linkNote==null|| linkNote.equals("null")) { linkNote=""; }
				
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
			}  // for matches loop		
			conn.close();
		} //end try
		catch(Exception e) { 
		    System.err.println ("Error with database" +filename); 
		    e.printStackTrace(); 
		}
	}
	
	
	public File fileCopy(File inputFile, String filename) {
		try {
			File outputFile = new File(filename);
			
		 	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile), 4096);
	        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile), 4096);
	        int theChar;
	        while ((theChar = bis.read()) != -1) {
	           bos.write(theChar);
	        }
			bis.close();
			bos.close();
			return outputFile;
		}
		catch (IOException e) {	e.printStackTrace(); }
		return null;
	}
	
	public String scrubFileName(String original) {
		String newFileName = original;
		newFileName = newFileName.replaceAll("[^a-zA-Z _\\(\\)0-9\\-.]","");			
		return newFileName;
	}
}
