// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.schemaImporters.ddl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

/**
 *  This class filters out all the crap^H^H^H^H lines that the parser can't handle.  Currently it lets through the
 *  following kinds of statements:<br/>
 *  CREATE TABLE
 *  ALTER TABLE
 */
public class DdlFilteredReader extends BufferedReader
{

    final int EOF = 26;

    boolean inComment = false;
    protected String buffer = "";
    protected int marker = 0;

    public DdlFilteredReader(Reader in)
        throws FileNotFoundException
    {
        super(in);
        // System.out.println( this.getClass().getName() + " ( in constructor ) " );
    }


    public int read() throws IOException
    {

        while( buffer != null && marker >= buffer.length() )
        {
            // System.out.println( "marker/buffer.length(): " + marker + "/" + buffer.length() );
            refillBuffer();
            // System.out.println( "Buffer refilled: " + buffer );
        }

        if( buffer == null )
        {
            return -1;
            //throw new RuntimeException( "Ending the read the hard way:");
        }
        // System.out.print( buffer.charAt( marker ) );
        return buffer.charAt( marker++ );
    }

    /**
     *  overriding readLine to get rid of all line comments
     */
    public String readLine()
        throws IOException
    {
        String line = super.readLine();
        if(line==null)
            return null;

        //get rid of comments (/* ... */ style)
        if( !inComment && line.contains( "/*")  )
        {
            if( line.contains( "*/")  )
            {
                // System.out.println( "Line:" + line + "one line comment" );
                String temp = line.substring( 0, line.indexOf( "/*"));
                // System.out.println( line.substring( line.indexOf( "/*")));
                line = temp + line.substring( line.indexOf( "*/") + 2 );
                return line;
            }
            else
            {
                // System.out.println( "Line:" + line + line );
                // System.out.println( "multi-line comment" );
                inComment = true;
                return line.substring( 0, line.indexOf( "/*")).trim();
            }
        }
        if( inComment )
        {
            if( line.contains( "*/")  )
            {
                line = line.substring( line.indexOf( "*/") + 2);
                inComment=false;

            }
            else return "";
        }

        if( line.indexOf("--") > -1 )
        {
            //truncate comments within the line
            line = line.substring( 0, line.indexOf("--"));
        }

        return line.trim();
    }


    /**
     *  refills the buffer we are taking the chars from.  This will group statements into one line.
     */
    protected void refillBuffer()
        throws IOException
    {
        buffer = readLine();
        marker = 0;

        if( buffer == null )
        {
            return;
        }

        boolean eof = false;
        while( !buffer.endsWith(";") && !eof )
        {
            String nextLine = readLine();
            // System.out.println( this.getClass().getName() + " ( refillBuffer ): nextLine: " + nextLine);
            if( nextLine == null /*|| nextLine.equals( null or eof character ) */)
            {
                eof=true;
                buffer="";
                return;
            }
            // System.out.println( nextLine );
            if( !eof )
                buffer = buffer + " " + nextLine;
        }

        // System.out.println( this.getClass().getName() + " ( refillBuffer ): full buffer" + buffer );

        // now....ignore all the lines we don't want.
        buffer = buffer.trim();
        //System.out.println( "|" + buffer.toLowerCase() + "|" );
        buffer = buffer.replaceAll("VARCHAR2", "VARCHAR");
        buffer = buffer.replaceAll("varchar2", "varchar");

        if( buffer.toLowerCase().startsWith( "create table" )
            //|| buffer.toLowerCase().startsWith( "alter table" )
            //|| buffer.toLowerCase().startsWith( "comment on" )
            )
        {
            System.out.println( "Processing: " + buffer );//.substring(0, 30));
            return;
        }
        else
        {
            System.err.println( "\t\tSkipping: " + buffer.substring(0,25) );
            buffer = "";
        }

    }
}


    // final int SEMICOLON = 59;
    // final int FORMFEED = 12;
    // final int CARRIAGERETURN = 13;
    // final int MINUS = 45;
    // final int LOOKAHEAD = 10000;

    // boolean newline = true;



        // /*
        //  *  get the next token...this will be what is returned if all is well
        //  */
        // int nextToken = super.read();
        // System.out.println( this.getClass().getName() + " ( nextToken ): " + nextToken);
        // /*
        //  *  Now we look ahead to make sure what we are finding is something the lexer can handle
        //  *  We want only CREATE TABLE and ALTER TABLE statements.
        //  */
        // mark( LOOKAHEAD );   // mark the stream so we can come back to it
        // if(newline)
        // {
        //     newline = false;
        //     //now look for --
        //     if( nextToken == MINUS )
        //     {
        //         int lookahead = super.read();
        //         if(lookahead == MINUS)
        //         {
        //             System.out.println( this.getClass().getName() + " ( Start of a line commment throwing it away ): " );
        //             String line = readLine();
        //             mark(LOOKAHEAD);  //remark at the beginning of the next linewline
        //             newline=true;  //and reset newline
        //         }
        //     }
        // }
        // if( nextToken == SEMICOLON )
        // {
        //     System.out.println( this.getClass().getName() + " semicolon! " );
        // }
        // else if(nextToken == CARRIAGERETURN)
        // {
        //     System.out.println( this.getClass().getName() + " ( a new line started! ): " );
        //     newline = true;
        // }
        // // System.out.println( this.getClass().getName() + " in read() " );
        // reset();  //reset back to the last mark
        // return nextToken;
