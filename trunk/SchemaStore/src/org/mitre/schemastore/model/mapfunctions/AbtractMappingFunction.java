// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.mapfunctions;

import java.util.*;

/**
 *  This class provides the base functionality necessary to put a mapping function in all parts of OpenII.  It provides
 *  information to the Harmony GUI, SchemaStore/M3, and RMap.  All mapping functions must inherit from this class.   
 */
abstract public class AbtractMappingFunction
{
    //configuration variables.  These should be overridden by subclasses.  Where possible, NotImplementedException will be thrown where these variables are not overridden.
    /**
     *  The classname - it is recommended that all constructors of implementing classes add a <pre>KEY = this.class.getName()</pre> to their constructor to set this
     */
    protected static String KEY = null;

    /**
     *  The minimum number of arguments necessary for this function
     */
    protected int min_args = -1;
    
    /**
     *  The maximum number of arguments necessary for this function. The value of min_args is the default (i.e. and 
     *  exact number of argument is expected).
     */
    protected int max_args = min_args;
    
    /**
     *  The function common name -  this is to be used in GUIs and for display purposes.  Examples are Add, Standard 
     *  Deviatioon, and Modulus
     */
    protected String displayName = null;

    /**
     *  The author of this function
     */
    protected String author = null;
    
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
     *  An enumeration of output types
     */
    public enum OutputType { STRING, REAL, INTEGER, DATETIME, BOOLEAN };
    
    /**
     *  The function output type.  Example usage -> <pre>outputType = OutputType.STRING</pre>
     */
    protected OutputType outputType = null;
    
    
    //variables for internal use.  These are available to all functions and typcially represent information used by the OpenII tools.
    /**
     *  This List stores, in order, the inputs to the function defined.  What is stored is the ElementID from the
     *  SchemaStore/M3 repository.  Lookups will have to be performed to get metadata about the attributes.  
     */           
    protected List<Integer> inputs = new ArrayList<Integer>();
    
    //methods
    /**
     *  Returns the string to be inserted into SQL to perform this mapping
     */
    abstract public String getRelationalString();
    
    /**
     *  Returns the string to be used for an XQuery statement to perform this mapping
     */
    abstract public String getXQueryString();
    
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
    
}
// Please do not remove the line below - jch
// :wrap=soft:noTabs=true:collapseFolds=1:maxLineLen=120:mode=java:tabSize=4:indentSize=4:noWordSep=_:folding=indent:
