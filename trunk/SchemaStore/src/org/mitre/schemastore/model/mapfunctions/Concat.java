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

import org.mitre.schemastore.model.mappingInfo.MappingInfo;
import org.mitre.schemastore.model.schemaInfo.*;
import org.mitre.schemastore.model.*;


/**
 *  This function concatenates 2 or more fields together
 *  @author     Jeffrey Hoyt
 *  @version    1.0
 */
public class Concat extends AbstractMappingFunction
{
    private final String operator = "||";
    private final String space = " ";
    public Concat( )
    {
        setMetaData();
    }

    private void setMetaData()
    {
        KEY = String.valueOf( getClass().getName() );
        minArgs = 2;
        maxArgs = -1;
        inputDomains.add( MappingInfo.REAL );
        inputDomains.add( MappingInfo.INTEGER );
        inputDomains.add( MappingInfo.STRING );
        outputDomain = MappingInfo.STRING;
        displayName = "Concatenate";
        description = "Takes in two or more fields and concatenates them together.";
        version = "1.0";
    }

    public String getRelationalString( String[] colPrefix, MappingCell cell, MappingInfo def ) throws IllegalArgumentException
    {
        String[] processedInputStrings = processInputStrings( colPrefix, cell, def, true );
        StringBuilder ret = new StringBuilder( processedInputStrings[0] );
        for(int i = 1; i < processedInputStrings.length; i++)
        {
            ret.append( space );
            ret.append( operator );
            ret.append( space );
            ret.append( processedInputStrings[i] );
        }
        return ret.toString();
    }
}

// Please do not remove the line below - jch
// :wrap=soft:noTabs=true:collapseFolds=1:maxLineLen=120:mode=java:tabSize=4:indentSize=4:noWordSep=_:folding=indent
