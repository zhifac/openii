// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.schemaImporters;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.ImporterException.ImporterExceptionType;
import org.mitre.schemastore.porters.schemaImporters.ddl.SqlLexer;
import org.mitre.schemastore.porters.schemaImporters.ddl.SqlParser;


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
	public URIType getURIType()
		{ return URIType.FILE; }

	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes()
	{
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".ddl"); fileTypes.add(".sql");
		return fileTypes;
	}

	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> generateSchemaElements() throws ImporterException
	{
		try {
	        SqlLexer lexer = new SqlLexer( new ANTLRFileStream( uri.getPath() ) );
	        CommonTokenStream tokens = new CommonTokenStream( lexer );
	        SqlParser parser = new SqlParser( tokens );

	        try
	        {
	            //parse the entire script
	            //parser.sql_script(  );
	            while(true)
	            {
	                // try
	                // {
	                    parser.sql_stmt();
	                // }
	            //     catch (NoViableAltException e)
	            //     {
                //         System.out.print( e.getClass().getName() + ": " );
	            //         e.printStackTrace();
	            //         return parser.getSchemaObjects();
	            //     }
	            }
	        }
	        catch ( Exception e )
	        {
	            if( e.getMessage()==null || !e.getMessage().contains("<EOF>") )
                {
                    System.out.println( this.getClass().getName() + " ( loadSchem ): " + e.getClass().getName());
                    e.printStackTrace( System.err );
                }
	        }
	        return parser.getSchemaObjects();
		}
		catch(Exception e) { System.out.print( e.getClass().getName() + ": " ); e.printStackTrace();
        throw new ImporterException(ImporterExceptionType.PARSE_FAILURE,e.getMessage()); }
	}


	public static void main(String[] args) throws Exception {
		File ddlFile = new File(args[0]);
		DDLImporter tester = new DDLImporter();
		Repository repository = new Repository(Repository.DERBY,new URI("."),"schemastore","postgres","postgres");
		SchemaStoreClient client = new SchemaStoreClient(repository);
		tester.setClient(client);
		tester.importSchema(ddlFile.getName(), "", "", ddlFile.toURI());
	}
}