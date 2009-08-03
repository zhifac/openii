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

package test.org.mitre.schemastore.model.mapfunctions;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.Graph;
import org.mitre.schemastore.model.mapfunctions.AbstractMappingFunction;
import org.mitre.schemastore.model.mapfunctions.NotImplementedException;


public class AbstractMappingFunctionTest {

    private AbstractMappingFunction testee = null;
    private AbstractMappingFunction testee2 = null;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        testee = new AbstractMappingFunction( )
        {
            public String getDescription()
            {
                //hijacking this method to set the properties
                KEY = "testee";
                minArgs = 2;
                maxArgs = 2;
                inputDomains.add( DOUBLE );
                inputDomains.add( STRING );
                outputDomain = DOUBLE;
                displayName = "testee";
                description = "Nonesense implementation to test with";
                version = "1.0";
                return description;
            }


            public String getRelationalString(String prefix, Graph graph) throws IllegalArgumentException, NotImplementedException
            {
                return "";
            }
        };
        testee.getDescription();

        testee2 = new AbstractMappingFunction( )
        {
            public String getDescription()
            {
                //hijacking this method to set the properties
                KEY = "testee2";
                minArgs = 1;
                maxArgs = -1;
                inputDomains.add( STRING );
                outputDomain = DOUBLE;
                displayName = "testee";
                description = "Nonesense implementation to test with";
                version = "1.0";
                return description;
            }


            public String getRelationalString(String prefix, Graph graph) throws IllegalArgumentException, NotImplementedException
            {
                return "";
            }
        };
        testee2.getDescription();


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
        SchemaElement ele = testee.getSchemaElement( 9, TestFixtures.getSourceGraph() );
        assertEquals( "personID", ele.getName() );
    }

    // @Test (expected=IllegalArgumentException.class)
    // public void testAddWrongDataType() throws Exception
    // {
    //     Graph graph = TestFixtures.getSourceGraph();

    // }


    @Test (expected=IllegalArgumentException.class)
    public void testTooFewInputs() throws Exception
    {
        testee.addInput(1);
        testee.checkInputCount();
    }


    @Test (expected=IllegalArgumentException.class)
    public void testTooManyInputs() throws Exception
    {
        testee.addInput(1);
        testee.addInput(1);
        testee.addInput(1);
        testee.checkInputCount();
    }



}

// Please do not remove the line below - jch
// :wrap=soft:noTabs=true:collapseFolds=1:maxLineLen=120:mode=java:tabSize=4:indentSize=4:noWordSep=_:folding=indent:
