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

import org.mitre.schemastore.model.mapfunctions.*;
import org.mitre.schemastore.model.mappingInfo.MappingInfo;
import org.mitre.schemastore.model.*;
import org.junit.*;
import java.util.*;

import static org.junit.Assert.*;

public class ConcatTest {

    private Concat testee = null;
    private MappingCell cell = null;
    private TestFixtures factory = new TestFixtures();


    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        testee = new Concat();
        cell = factory.getMappingCells().get("concat");
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        testee = null;
        cell = null;
    }

    @Test
    public void testGetRelationalString() {
        assertEquals("\"Diagnosis\" || \"Diagnosis\"", testee.getRelationalString(factory.getPrefixArray("", cell.getInput().length), cell, factory.getMappingDefinition()));
    }

    @Test
    public void testMoreTerms() {
        cell.setInput( Arrays.asList(53,53,53,53).toArray(new Integer[0]));
        assertEquals("\"Diagnosis\" || \"Diagnosis\" || \"Diagnosis\" || \"Diagnosis\"", testee.getRelationalString(factory.getPrefixArray("", cell.getInput().length), cell, factory.getMappingDefinition()));
    }

    @Test (expected=IllegalArgumentException.class)
    public void testOneTerm() {
        cell.setInput( Arrays.asList(53).toArray(new Integer[0]) );
        MappingInfo d = factory.getMappingDefinition();
        d.checkMappingCell(cell);  //this should throw an IllegalArgumentException because there aren't enough inputs
    }

}

