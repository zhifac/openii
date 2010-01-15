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

package test.org.mitre.schemastore.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.mappingInfo.MappingInfo;

import test.org.mitre.schemastore.model.mapfunctions.TestFixtures;

public class MappingDefinitionTest
{

    Project project = null;
    List<MappingCell> list = null;
    TestFixtures factory= null;
    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        factory = new TestFixtures();
        project = new Project(57, "name", "description", "author", factory.getSchemas() );
        list = new ArrayList<MappingCell>();
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        project = null;
        list = null;
    }


    @Test
    public void testJustRight() throws Exception
    {
        MappingCell mc = MappingCell.createValidatedMappingCell(new Integer(1),new Integer(13),
            Arrays.asList(new Integer(59), new Integer(60)).toArray(new Integer[0]),new Integer(69),"Author",new Date(),"org.mitre.schemastore.model.mapfunctions.NumericAdd","notes");
        list.add( mc );
        MappingInfo fixture = new MappingInfo(project, list, factory.getSourceSchema(), factory.getTargetSchema());
        fixture.addMappingCell(mc);
        fixture.checkMappingCell( mc );
    }


    @Test (expected=IllegalArgumentException.class)
    public void testTooFewInputs() throws Exception
    {
        MappingCell mc = MappingCell.createValidatedMappingCell(new Integer(1),new Integer(13),
            Arrays.asList(new Integer(59)).toArray(new Integer[0]),new Integer(69),"Author",new Date(),"org.mitre.schemastore.model.mapfunctions.NumericAdd","notes");
        list.add( mc );
        MappingInfo fixture = new MappingInfo(project, list, factory.getSourceSchema(), factory.getTargetSchema());
        fixture.addMappingCell(mc);
        fixture.checkMappingCell( mc );
    }


    @Test (expected=IllegalArgumentException.class)
    public void testTooManyInputs() throws Exception
    {
        MappingCell mc = MappingCell.createValidatedMappingCell(new Integer(1),new Integer(13),
            Arrays.asList(new Integer(59), new Integer(60), new Integer(8899)).toArray(new Integer[0]),new Integer(69),"Author",new Date(),"org.mitre.schemastore.model.mapfunctions.NumericAdd","notes");
        list.add( mc );
        MappingInfo fixture = new MappingInfo(project, list, factory.getSourceSchema(), factory.getTargetSchema());
        fixture.addMappingCell(mc);
        fixture.checkMappingCell( mc );
    }

    @Test (expected=IllegalArgumentException.class)
    public void testWrongInputDataType() throws Exception
    {
        MappingCell mc = MappingCell.createValidatedMappingCell(new Integer(1),new Integer(13),
            Arrays.asList(new Integer(59), new Integer(53)).toArray(new Integer[0]),new Integer(53),"Author",new Date(),"org.mitre.schemastore.model.mapfunctions.NumericAdd","notes");
        list.add( mc );
        MappingInfo fixture = new MappingInfo(project, list, factory.getSourceSchema(), factory.getTargetSchema());
        fixture.addMappingCell(mc);
        fixture.checkMappingCell( mc );
    }

    @Test
    public void testIdenityMapping() throws Exception
    {
        MappingCell mc = MappingCell.createValidatedMappingCell(new Integer(1),new Integer(13),
            Arrays.asList(new Integer(51)).toArray(new Integer[0]),new Integer(69),"Author",new Date(),"org.mitre.schemastore.model.mapfunctions.IdentityFunction","notes");
        list.add( mc );
        MappingInfo fixture = new MappingInfo(project, list, factory.getSourceSchema(), factory.getTargetSchema());
        fixture.addMappingCell(mc);
        fixture.checkMappingCell( mc );
    }

}
