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

import org.mitre.schemastore.model.graph.*;

import org.mitre.schemastore.model.SchemaElement;


/**
 *  This class provides the for the addition of two fields.
 *  @author     Jeffrey Hoyt
 *  @version    1.0
 */
public class NullFunction extends AbstractMappingFunction
{

    public NullFunction( )
    {
        setMetaData();
    }

    private void setMetaData()
    {
        KEY = String.valueOf( getClass().getName() );
        minArgs = 1;
        maxArgs = 1;
        inputTypes.add( Type.ANY );
        outputType = Type.ANY;
        displayName = "Null Function";
        description = "Passes the value through un-changed";
        version = "1.0";
    }

    public String getRelationalString( String colPrefix, Graph graph ) throws IllegalArgumentException
    {
        if( inputs.size() != 1 )
        {
            throw new IllegalArgumentException( KEY + " requires exactly 1 input." );
        }
        SchemaElement one;
        one = getSchemaElement( inputs.get(0), graph );
        //TODO -check the types
        return colPrefix + scrubAttributeName( one.getName());
    }
}

// Please do not remove the line below - jch
// :wrap=soft:noTabs=true:collapseFolds=1:maxLineLen=120:mode=java:tabSize=4:indentSize=4:noWordSep=_:folding=indent
