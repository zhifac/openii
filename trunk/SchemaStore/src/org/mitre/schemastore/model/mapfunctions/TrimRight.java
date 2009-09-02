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
import org.mitre.schemastore.model.mappingInfo.MappingInfo;

import java.util.*;


/**
 *  This class provides the for the addition of two fields.
 *  @author     Jeffrey Hoyt
 *  @version    1.0
 */
public class TrimRight extends SingularFunction
{

    public TrimRight( )
    {
        super();
        functionName = "RTRIM";
        displayName = "TrimRight";
        inputDomains=new ArrayList<String>();
        inputDomains.add( MappingInfo.STRING );
        description = "Returns the value with all trailing whitespace removed";
        KEY = String.valueOf( getClass().getName() );
    }

}

