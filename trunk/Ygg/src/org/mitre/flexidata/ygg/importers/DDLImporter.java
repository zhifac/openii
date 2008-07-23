// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.importers;

import java.util.*;

import antlr.*;
import java.io.*;

import org.mitre.flexidata.ygg.importers.ddl.DdlFilteredReader;
import org.mitre.flexidata.ygg.importers.ddl.SqlSQL2Lexer;
import org.mitre.flexidata.ygg.importers.ddl.SqlSQL2Parser;
import org.mitre.schemastore.model.SchemaElement;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.2 $
 */
public class DDLImporter extends Importer
{
	/** Returns the importer name */
	public String getName()
		{ return "DDL Importer"; }
	
	/** Returns the importer description */
	public String getDescription()
		{ return "This importer can be used to import schemas from a ddl format"; }
	
	/** Returns the importer URI type */
	public Integer getURIType()
		{ return FILE; }
	
	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes()
	{
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".ddl"); fileTypes.add(".sql");
		return fileTypes;
	}
	
	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> getSchemaElements(Integer schemaID, String uri) throws ImporterException
	{
		try {
	        SqlSQL2Lexer lexer = new SqlSQL2Lexer( new DdlFilteredReader( new FileReader( new File(uri) ) ) );
	        SqlSQL2Parser parser = new SqlSQL2Parser( lexer );
	        try
	        {
	            //parse the entire script
	            //parser.sql_script(  );
	            while(true)
	            {
	                try
	                {
	                    parser.sql_stmt();
	                }
	                catch (NoViableAltException e)
	                {
	                    e.printStackTrace();
	                    break;
	                }
	            }
	        }
	        catch ( Exception e )
	        {
	            System.out.println( this.getClass().getName() + " ( loadSchem ): " + e.getClass().getName());
	            e.printStackTrace( System.err );
	        }
	        return parser.getSchemaObjects();
		}
		catch(Exception e) { throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); }
	}
}