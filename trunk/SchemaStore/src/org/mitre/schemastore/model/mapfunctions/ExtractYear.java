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

import java.util.*;

// import org.mitre.schemastore.model.graph.*;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.mappingInfo.MappingInfo;



/**
 *  This class provides the for the addition of two fields.
 *  @author     Jeffrey Hoyt
 *  @version    1.0
 */
public class ExtractYear extends SingularFunction
{

    public ExtractYear( )
    {
        super();
        inputDomains=new ArrayList<String>();
        inputDomains.add( MappingInfo.TIMESTAMP );
        functionName = "EXTRACT(YEAR FROM ";
        displayName = "ExtractYear";
        description = "Extracts the year from a date field";
        KEY = String.valueOf( getClass().getName() );
    }

    public String getRelationalString( String[] colPrefix, MappingCell cell, MappingInfo def ) throws IllegalArgumentException
    {
        String[] processedInputStrings = processInputStrings( colPrefix, cell, def, true );
        return functionName + processedInputStrings[0] + ")";
    }

}

