// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.importers.ddl;

import java.io.*;

/**
 *  This class filters out everything except CREATE TABLE and ALTER TABLE statements
 */
public class DdlFilteredReader extends BufferedReader
{
    final int EOF = 26;

    protected String buffer = "";
    protected int marker = 0;

    /** Constructs the DDL Filtered Reader */
    public DdlFilteredReader(Reader in) throws FileNotFoundException
    	{ super(in); }

    /** Handles the reading of a character from the buffer */
    public int read() throws IOException
    {
        while( buffer != null && marker >= buffer.length() ) refillBuffer();
        return buffer==null ? -1 : buffer.charAt( marker++ );
    }

    /** Handles the reading of a line from the buffer (excludes comments) */
    public String readLine() throws IOException
    {
        String line = super.readLine();
        if(line==null) return null;
        if( line.indexOf("--") > -1 )
            line = line.substring( 0, line.indexOf("--"));
        return line.trim();
    }

    /** Refills the buffers where characters are being taken */
    protected void refillBuffer() throws IOException
    {
        buffer = readLine();
        marker = 0;

        if( buffer == null ) return;

        boolean eof = false;
        while( !buffer.endsWith(";") && !eof )
        {
            String nextLine = readLine();
            if(nextLine == null)
            	{ eof=true; buffer=null; return; }
            if( !eof )
                buffer = buffer + " " + nextLine;
        }

        // now....ignore all the lines we don't want.
        buffer = buffer.trim();
        buffer = buffer.replaceAll("VARCHAR2", "VARCHAR");
        buffer = buffer.replaceAll("varchar2", "varchar");
        if( buffer.toLowerCase().startsWith( "create table" ) || buffer.toLowerCase().startsWith( "alter table" ) )
            return;
        else buffer = "";
    }
}