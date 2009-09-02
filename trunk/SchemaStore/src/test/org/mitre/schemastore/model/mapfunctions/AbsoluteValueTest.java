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
import static org.junit.Assert.*;

public class AbsoluteValueTest {

    private AbsoluteValue add = null;
    private MappingCell cell = null;
    private TestFixtures factory = new TestFixtures();


    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        add = new AbsoluteValue();
        cell = factory.getMappingCells().get("identity");
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        add = null;
        cell = null;
    }

    @Test
    public void testGetRelationalString() {
        assertEquals("ABS(hoorayfortesting.\"Ht\")", add.getRelationalString(factory.getPrefixArray("hoorayfortesting.", cell.getInput().length), cell, factory.getMappingDefinition()));
    }

    @Test (expected=IllegalArgumentException.class)
    public void testTooManyArgs() {
        cell = factory.getMappingCells().get("add");
        cell.setFunctionClass("org.mitre.schemastore.model.mapfunctions.AbsoluteValue");
        MappingInfo d = factory.getMappingDefinition();
        d.addMappingCell(cell);
        d.checkMappingCell(cell);  //this should throw an IllegalArgumentException because there are too many inputs

    }


}

