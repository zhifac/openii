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
public class NumericSubtract extends AbstractMappingFunction
{

    public NumericSubtract( )
    {
        setMetaData();
    }

    private void setMetaData()
    {
        KEY = String.valueOf( getClass().getName() );
        minArgs = 2;
        maxArgs = 2;
        inputDomains.add( DOUBLE );
        inputDomains.add( DOUBLE );
        outputDomain = DOUBLE;
        displayName = "Numeric Subtract";
        description = "Takes in two REALs and outputs a REAL.  Casts will be necessary to get int values, however some databases will do the cast for you.";
        version = "1.0";
    }

    public String getRelationalString( String colPrefix, Graph graph ) throws IllegalArgumentException
    {
        if( inputs.size() != 2 )
        {
            throw new IllegalArgumentException( KEY + " requires exactly 2 inputs." );
        }
        SchemaElement one, two;
        one = getSchemaElement( inputs.get(0), graph );
        two = getSchemaElement( inputs.get(1), graph);
        //TODO -check the types

        return colPrefix + scrubAttributeName(one.getName()) + " - " + colPrefix + scrubAttributeName(two.getName());
    }
}

// Please do not remove the line below - jch
// :wrap=soft:noTabs=true:collapseFolds=1:maxLineLen=120:mode=java:tabSize=4:indentSize=4:noWordSep=_:folding=indent
