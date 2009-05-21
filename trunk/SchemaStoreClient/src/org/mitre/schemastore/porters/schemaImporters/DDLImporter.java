// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.schemaImporters;

import java.util.*;

import antlr.*;
import java.io.*;

import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.schemaImporters.ddl.DdlFilteredReader;
import org.mitre.schemastore.porters.schemaImporters.ddl.SqlSQL2Lexer;
import org.mitre.schemastore.porters.schemaImporters.ddl.SqlSQL2Parser;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.2 $
 */
public class DDLImporter extends SchemaImporter
{
	/** Returns the importer name */
	public String getName()
		{ return "SQL/DDL Importer"; }

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

	/** Initializes the importer for the specified URI */
	protected void initialize() throws ImporterException {}

	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> getExtendedSchemaIDs() throws ImporterException
		{ return new ArrayList<Integer>(); }

	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> getSchemaElements() throws ImporterException
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
                        System.out.print( e.getClass().getName() + ": " );
	                    e.printStackTrace();
	                    return parser.getSchemaObjects();
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
		catch(Exception e) { System.out.print( e.getClass().getName() + ": " ); e.printStackTrace();
        throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); }
	}

	public static void main(String[] args) throws IOException, ImporterException {
		File ddl = new File(args[0]);
		DDLImporter tester = new DDLImporter();
		tester.setClient(new org.mitre.schemastore.client.SchemaStoreClient(
				"../SchemaStore/SchemaStore.jar"));
		tester.importSchema(ddl.getName(), System.getProperty("user.name"), "Good description of the database goes here", ddl.toURI());
	}
}