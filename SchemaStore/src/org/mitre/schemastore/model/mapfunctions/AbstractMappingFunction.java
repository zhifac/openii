/*
 * Copyright 2009 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
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


package org.mitre.schemastore.model.mapfunctions;

import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.graph.*;
import java.util.*;

/**
 *  This class provides the base functionality necessary to put a mapping function in all parts of OpenII.  It provides
 *  information to the Harmony GUI, SchemaStore/M3, and RMap.  All mapping functions must inherit from this class.
 *  @author     Jeffrey Hoyt
 *  @version    1.0
 */
abstract public class AbstractMappingFunction
{

    public static final String QUOTE = "\"";
    //configuration variables.  These should be set by subclasses in constructors.  Where possible, NotImplementedException will be thrown where these variables are not overridden.

    /**
     *  The classname - it is recommended that all constructors of implementing classes add a <pre>KEY = getClass().getName()</pre> to their constructor to set this
     */
    protected String KEY = null;

    /**
     *  The minimum number of arguments necessary for this function
     */
    protected int minArgs = -1;

    /**
     *  The maximum number of arguments necessary for this function. Set to the same number as min_args to
     *  specify an exact number of inputs
     */
    protected int maxArgs = -1;

    /**
     *  The function common name -  this is to be used in GUIs and for display purposes.  Examples are Add, Standard
     *  Deviatioon, and Modulus
     */
    protected String displayName = null;

    /**
     *  A user-friendly description of what this mapping function does.
     */
    protected String description = null;

    /**
     *  The version number.  All subclasses should start with 1.0 and add major and minor revisions as necessary.
     *  Subclasses should stick to just two digits, though.  Do not add a third number.
     */
    protected String version = null;

    /**
     *  The function output type.  Example usage -> <pre>outputDomain = OutputType.STRING</pre>
     */
    protected String outputDomain = null;

    /**
     *  This List stores the input typess to the function defined.  The types apply to all inputs.  If necessary, casts *  will have to be employed to convert the actual type into something the fuction can use.
     */
    protected List<String> inputDomains = new ArrayList<String>();

    //methods
    /**
     *  Returns the string to be used for an XQuery statement to perform this mapping.  Throws a
     *  NotImplementedException exception if not overwritten by subclasses.
     */
    public String getXQueryString() throws NotImplementedException,IllegalArgumentException
    {
        throw new NotImplementedException( "getXQueryString() has not been implemented yet for " + KEY );
    }
    /**
     *  The display name to be used to be used in GUIs and for display purposes
     */
    public String getDisplayName() throws NotImplementedException
    {
        if( displayName == null )
        {
            throw new NotImplementedException("The display name of " + KEY + " has not been set");
        }
        return displayName;
    }

    /**
     *  The description detailing what the function does
     */
    public String getDescription() throws NotImplementedException
    {
        if( description == null)
        {
            throw new NotImplementedException("The description of " + KEY + " has not been set");
        }
        return description;
    }

    /**
     *  The minimum number of arguements required
     */
    public int getMinArgs() throws NotImplementedException
    {
        if( minArgs == -1 )
        {
            throw new NotImplementedException("The minimum number of arguments of " + KEY + " has not been set");
        }
        return minArgs;
    }

    /**
     *  The maximum number of arguements required. . -1 means it is unlimited.
     */
    public int getMaxArgs() throws NotImplementedException
    {
        return maxArgs;
    }

    public String getVersion() throws NotImplementedException
    {
        if( version == null)
        {
            throw new NotImplementedException("The version of " + KEY + " has not been set");
        }
        return version;
    }

    /**
     *  Returns the output type
     */
    public String getOutputType() throws NotImplementedException
    {
        if( outputDomain == null )
        {
            throw new NotImplementedException("The output type of " + KEY + " has not been set");
        }
        return String.valueOf( outputDomain );
    }

    /**
     *  Returns all different allowable input types
     */
    public String[] getInputTypes() throws NotImplementedException
    {
        if( inputDomains.size() == 0 )
        {
            throw new NotImplementedException("The input type(s) of " + KEY + " has not been set");
        }
        return inputDomains.toArray(new String[0]);
    }

    /**
     *  Returns the string to be inserted into SQL to perform this mapping
     *  @param colPrefix table alias (possibly with schema) to be prepended to every column name.  No decimal is\
     *                    added..if that's the intent, this parameter should end with a "."
     */
    abstract public String getRelationalString( String[] colPrefix, MappingCell cell, MappingDefinition def )
        throws IllegalArgumentException;


    /**
     *  If possible, performs a cast from the expected datatype to the needed datatype.
     */

     protected String[] processInputStrings(String colPrefix[], MappingCell cell, MappingDefinition def, boolean doCasts)
    {
        String[] ret = new String[cell.getInput().length];
        String name = null;
        for(int i = 0; i < ret.length; i++)
        {
            HierarchicalGraph graph = def.getLeftGraph();
            SchemaElement ele = graph.getElement( cell.getInput()[i] );
            name = colPrefix[i] + QUOTE + ele.getName() + QUOTE;
            if( doCasts )
            {
                if( cell.getInputType() != null )
                {  //can only do anything if it's not null
                    String actual = cell.getInputType()[i];
                    String expected = this.getInputTypes()[0];
                    name = addRelationalCast( expected, actual, name );
                }
            }
            ret[i] = name;
        }
        return ret;
    }


    /**
     *  Adds the SQL casts function where it's necessary and safe ( anything to String and integer to real).
     */
    protected String addRelationalCast( String expected, String actual, String name)
    {
        if( expected.equals(actual) )
            return name;
        if( expected.equals(MappingDefinition.STRING) )
            return "CAST( " + name + " AS VARCHAR )";
        if( expected.equals(MappingDefinition.REAL) && actual.equals(MappingDefinition.INTEGER) )
            return "CAST( " + name + " AS NUMERIC )";
        return name;
    }

}
// Please do not remove the line below - jch
// :wrap=soft:noTabs=true:collapseFolds=1:maxLineLen=120:mode=java:tabSize=4:indentSize=4:noWordSep=_:folding=indent:
