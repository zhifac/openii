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

 
package org.mitre.schemastore.mapfunctions;

import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.client.SchemaStoreClient;
import java.util.*;

/**
 *  This class provides the base functionality necessary to put a mapping function in all parts of OpenII.  It provides
 *  information to the Harmony GUI, SchemaStore/M3, and RMap.  All mapping functions must inherit from this class.   
 *  @author     Jeffrey Hoyt
 *  @version    1.0
 */
abstract public class AbstractMappingFunction
{
    /**
     *  An enumeration of output types
     */
    public static enum Type { STRING, REAL, INTEGER, DATETIME, BOOLEAN };

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
     *  The function output type.  Example usage -> <pre>outputType = OutputType.STRING</pre>
     */
    protected Type outputType = null;
    
    /**
     *  This List stores, in order, the input typess to the function defined.  If necessary, casts will have to
     *  be employed to convert the actual type into something the fuction can use.
     */
    protected List<Type> inputTypes = new ArrayList<Type>();
    
    //variables for internal use.  These are available to all functions and typcially represent information used by the OpenII tools.
    /**
     *  SchemaStoreClient to be used to access the data types and other metadata
     */
    protected SchemaStoreClient client = null;
    
    /**
     *  This List stores, in order, the inputs to the function defined.  What is stored is the ElementID (or a 
     *  MappingID)  from the SchemaStore/M3 repository.  Lookups will have to be performed to get metadata about 
     *  the attributes.  
     */           
    protected List<Integer> inputs = new ArrayList<Integer>();
    
    /**
     *  The ID of the output (this could be the input to another mapping or an element ID
     */
    protected int output = -1;
    
    //methods
    /**
     *  Sets the SchemaStoreClient to use
     */
    public void setClient( SchemaStoreClient client)
    {
        this.client = client;
    }
    
    
    /**
     *  Returns the string to be inserted into SQL to perform this mapping
     */
    abstract public String getRelationalString() throws IllegalArgumentException, NotImplementedException ;
    
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
     *  The maximum number of arguements required
     */
    public int getMaxArgs() throws NotImplementedException
    {
        if( maxArgs == -1 )
        {
            throw new NotImplementedException("The maximum number of arguments of " + KEY + " has not been set");
        }
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
     *  Returns the output type.  Unless it's extended, this will be one of STRING, REAL, INTEGER, DATETIME, or BOOLEAN
     */
    public String getOutputType() throws NotImplementedException
    {
        if( outputType == null )
        {
            throw new NotImplementedException("The output type of " + KEY + " has not been set");
        }
        return String.valueOf( outputType );
    }
    
    /**
     *  Returns, in order, the types of the expected inputs
     */
    public String[] getInputTypes() throws NotImplementedException
    {
        if( inputTypes.size() == 0 )
        {
            throw new NotImplementedException("The input type(s) of " + KEY + " has not been set");
        }
        return (String[]) inputTypes.toArray();
    }
    
    /**
     *  Adds a new SchemaElement ID to the list of inputs
     *  TODO: type checking and casting here?
     */
     public void addInput(Integer i)
    {
        inputs.add(i);
    }
    
    /**
     *  Returns the SchemaElement with the ID provided
     */
    public SchemaElement getSchemaElement(Integer i) throws java.rmi.RemoteException
    {
        return client.getSchemaElement( i );
    }
}
// Please do not remove the line below - jch
// :wrap=soft:noTabs=true:collapseFolds=1:maxLineLen=120:mode=java:tabSize=4:indentSize=4:noWordSep=_:folding=indent:
