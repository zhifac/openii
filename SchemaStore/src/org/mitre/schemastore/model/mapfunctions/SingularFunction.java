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

import org.mitre.schemastore.model.graph.*;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.SchemaElement;


/**
 *  This class provides a base class for functions with one argument (e.g., FLOOR, ABS, MAX)
 *  @author     Jeffrey Hoyt
 *  @version    1.0
 */
public class SingularFunction extends AbstractMappingFunction
{

    protected String functionName = "ABS";
    protected String space = " ";

    public SingularFunction( )
    {
        setMetaData();
    }

    private void setMetaData()
    {
        KEY = String.valueOf( getClass().getName() );
        minArgs = 1;
        maxArgs = 1;
        inputDomains.add( MappingDefinition.REAL );
        inputDomains.add( MappingDefinition.INTEGER );
        outputDomain = MappingDefinition.REAL;
        displayName = "Function with single input";
        description = "Base class for all functions with only a single input.";
        version = "1.0";
    }

    public String getRelationalString( String[] colPrefix, MappingCell cell, MappingDefinition def ) throws IllegalArgumentException
    {
        String[] processedInputStrings = processInputStrings( colPrefix, cell, def, true );
        return functionName + "(" + processedInputStrings[0] + ")";
    }
}

