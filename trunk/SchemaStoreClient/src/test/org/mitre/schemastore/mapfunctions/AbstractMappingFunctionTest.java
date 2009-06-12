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


package test.org.mitre.schemastore.mapfunctions;

import org.mitre.schemastore.mapfunctions.*;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.client.*;
import java.rmi.RemoteException;
import org.junit.*;
import static org.junit.Assert.*;

public class AbstractMappingFunctionTest {

    private java.util.List emptyList;

    private AbstractMappingFunction testee = null;
    
    /**
     * Sets up the test fixture. 
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        try
        {
            SchemaStoreClient client = new SchemaStoreClient( "/home/jchoyt/devel/openii/SchemaStore/SchemaStore.jar" );
            testee = new AbstractMappingFunction( )
            {
                public String getRelationalString() throws IllegalArgumentException, NotImplementedException 
                {
                    return null;
                }
            };  //empty implementation
            testee.setClient( client );
        }
        catch (RemoteException e)
        {
            System.out.println( "Failed to set up the test SchemaStoreClient" );
        }
    }

    /**
     * Tears down the test fixture. 
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        testee = null;
    }
    
    @Test
    public void testGetSchemaElement() throws java.rmi.RemoteException 
    {
        SchemaElement ele = testee.getSchemaElement( 59 );
        assertEquals( "Ht", ele.getName() );
    }

}

// Please do not remove the line below - jch
// :wrap=soft:noTabs=true:collapseFolds=1:maxLineLen=120:mode=java:tabSize=4:indentSize=4:noWordSep=_:folding=indent:
