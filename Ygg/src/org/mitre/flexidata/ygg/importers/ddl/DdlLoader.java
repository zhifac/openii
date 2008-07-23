/*
 *  Copyright (C) 2007 The MITRE Corporation.  All rights reserved.
 */
package org.mitre.flexidata.ygg.importers.ddl;

import java.util.*;

import org.mitre.flexidata.ygg.importers.ddl.*;
import antlr.*;
import antlr.collections.*;
import java.io.*;
import java.net.URI;
import org.mitre.flexidata.ygg.importers.*;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.flexidata.ygg.importers.ImporterUtils;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.6 $
 */
public class DdlLoader implements Loader
{
    /** The Schema object that will be the result of all the parsing. */
    private ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();
    private HashMap<String, Domain> domainList = new HashMap<String, Domain>();
    private Schema schema = null;


    /* @Override */
    public Schema loadSchema( URI schemaLoc ) throws IOException
    {
        schema = new Schema(ImporterUtils.nextId(), "ddl schema", "","","","faulty description", false);
        // loadDomains();
        SqlSQL2Lexer lexer = new SqlSQL2Lexer( new DdlFilteredReader( new FileReader( new File(schemaLoc) ) ) );
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
        schemaElements = parser.getSchemaObjects();

        for(SchemaElement o : schemaElements )
        {
            if( o instanceof Attribute )
            {
                System.out.println( "\tAttribute: " + o.toString() + " attached to entity " + ((Attribute)o).getEntityID() );
            }
            else if( o instanceof Relationship)
            {
                System.out.println( "\tRelationship: " + o.toString() + " attached to entity " + ((Relationship)o).getRightID() );
            }
            else
                System.out.println( "Schema Object: " + o.toString() + " id: " + o.getId() );
        }

        return schema;
    }

    public Schema getSchema() {
    	return schema;
    }

	public ArrayList<SchemaElement> getSchemaElements() {
		return schemaElements;
	}
}

