/*
 *  Copyright 2008 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 
package org.mitre.schemastore.mapfunctions;

import java.io.InputStream;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.mitre.schemastore.client.SchemaStoreClient;


/** Class for managing the various map functions */
public class FunctionManager
{
	/** Stores listings of the map function display names */
	private List<String> displayNames = null;
    
    /** stores the mapping between displayName and class name  */
    private Properties functionLookup = null;
    
    private SchemaStoreClient client = null;
	
	/** 
     * Constructs the porter manager class
     */
	public FunctionManager(SchemaStoreClient client)
	{
        this.client = client;
        reloadFunctions( );
	}
	
    /**
     *  takes a list of fully qualified class names and populates the displayNames List as
     *  well as the functionLookup Map
     */
    public void reloadFunctions( )
    {
        displayNames = new ArrayList<String>();
        functionLookup = new Properties();
        FunctionListParser parser = new FunctionListParser();
        try
        {
            // parse the mappingfunctions.xml file
            InputStream in = getClass().getResourceAsStream("/mappingfunctions.xml");
            XMLReader xr = XMLReaderFactory.createXMLReader();
            xr.setContentHandler(parser);
            xr.parse(new InputSource(in));
            in.close();
        }
        catch (Exception e)
        {
            System.out.println("(E)FunctionManager - Error loading the mapping functions" + e.getMessage());
            e.printStackTrace();
            return;
        }
        List<String> classnames = parser.getFunctions();
        String displayName;
        for( String name : classnames )
        {
            AbstractMappingFunction f = castFunction( name );
            try
            {
                displayName = f.getDisplayName();
            }
            catch (NotImplementedException e)
            {
                System.out.println("(E)FunctionManager - No display name for " + name + " was set." );
                continue;
            }
            displayNames.add( displayName );
            functionLookup.setProperty( displayName, name );
        }
    }
    
	/** 
     * Returns a list of names of map functions.  To get an instance of the function, use {@link #getFunction() getFunction}.
     */
	public List<String> getAvailableFunctions()
	{ 
        return Collections.unmodifiableList( displayNames );
    }
    
    
    /**
     *  returns the AbstractMappingFunction corresponding to the display name passed in
     */
    public AbstractMappingFunction getFunction(String displayName) throws FunctionNotFoundException
    {
        String className = functionLookup.getProperty(displayName);
        if( className == null )
        {
            throw new FunctionNotFoundException( displayName + " was not found as an available function" );
        }
        AbstractMappingFunction ret = castFunction(className);
        if( ret == null )
        {
            throw new FunctionNotFoundException( displayName + " was not found as an available function" );
        }
        ret.setClient(client);
        return ret;
    }
    

    /**
     *  Based on the passed in class name, uses Reflection to create the new
     *  AbstractMappingFunction objects for inclusion in the MsgObject workingObjects
     *  HashTable.
     *
     *@param  className               Fully qualified class name
     *@return                           AbstractMappingFunction of the class defined by
     *      the input String
     *@exception  MraldParserException  Description of Exception
     */
    protected static AbstractMappingFunction castFunction( String className )
    {
        try
        {
            if ( className == null )
            {
                return null;
            }
            Class classDefinition = Class.forName( className );
            AbstractMappingFunction function = ( AbstractMappingFunction ) classDefinition.newInstance();
            return function;
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }	
}