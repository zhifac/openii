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

import java.io.*;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.data.database.*;
import org.mitre.schemastore.servlet.*;
import org.mitre.schemastore.model.mappingInfo.MappingInfo;
import org.mitre.schemastore.model.schemaInfo.*;
import org.mitre.schemastore.model.schemaInfo.model.*;

import java.util.*;


public class TestFixtures
{

    private SchemaStore store = new SchemaStore( DatabaseConnection.DERBY,System.getProperty("java.io.tmpdir"),"schemastore","postgres","postgres");
    private ProjectSchema[] schemas = new ProjectSchema[2];
    private Mapping[] mappings = new Mapping[1];

    public TestFixtures()
    {
        schemas[0] = new ProjectSchema(1025, "left", SchemaModel.class.getName());
        schemas[1] = new ProjectSchema(1026, "right", SchemaModel.class.getName());
        mappings[0] = new Mapping(1027,1024,1025,1026);
    }

    public boolean reset()
    {
        return deleteDir( new File(System.getProperty("java.io.tmpdir"), "schemastore") );
    }

    /**
     * Deletes all files and subdirectories under dir.
     * Returns true if all deletions were successful.
     * If a deletion fails, the method stops attempting to delete and returns false.
     */
    protected boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }


    public HierarchicalSchemaInfo getSourceSchema()
    {
        Schema s = store.getSchema(10);
        ArrayList<Integer> parentSchemaIDs = new ArrayList<Integer>();
        for(int parentSchemaID : store.getParentSchemas(10))
        	parentSchemaIDs.add(parentSchemaID);
        ArrayList<SchemaElement> list = new ArrayList<SchemaElement>(Arrays.asList(store.getSchemaElements(10).geetSchemaElements()));
        SchemaInfo ret = new SchemaInfo( s,parentSchemaIDs,list );
        return new HierarchicalSchemaInfo(ret, new RelationalSchemaModel());
    }


    public HierarchicalSchemaInfo getTargetSchema()
    {
        Schema s = store.getSchema(12);
        ArrayList<Integer> parentSchemaIDs = new ArrayList<Integer>();
        for(int parentSchemaID : store.getParentSchemas(10))
        	parentSchemaIDs.add(parentSchemaID);
        ArrayList<SchemaElement> list = new ArrayList<SchemaElement>(Arrays.asList(store.getSchemaElements(12).geetSchemaElements()));
        SchemaInfo ret = new SchemaInfo( s,parentSchemaIDs,list );
        return new HierarchicalSchemaInfo(ret, new RelationalSchemaModel());
    }


    public Map<String, MappingCell> getMappingCells()
    {
        HashMap<String,MappingCell> ret = new HashMap<String,MappingCell>();
        // "add" maps HT + WT to hdl
        ret.put("add",  MappingCell.createValidatedMappingCell(1027,new Integer(-1),Arrays.asList(59,60).toArray(new Integer[0]),67,"Author Q. Tester",new Date(),"org.mitre.schemastore.model.mapfunctions.NumericAdd","notes"));
        // "Identity maps HT to hdl
        ret.put( "identity", MappingCell.createValidatedMappingCell(1028,new Integer(-1),Arrays.asList(59).toArray(new Integer[0]),67,"Author Q. Tester",new Date(),"org.mitre.schemastore.model.mapfunctions.IdentityFunction","notes"));
        // "concat" maps Diagnosis and Diagnosis to funding
        ret.put( "concat", MappingCell.createValidatedMappingCell(1028,new Integer(-1),Arrays.asList(53,53).toArray(new Integer[0]),53,"Author Q. Tester",new Date(),"org.mitre.schemastore.model.mapfunctions.Concat","notes"));
        // "extract" maps DOB to hdl
        ret.put( "extract", MappingCell.createValidatedMappingCell(1028,new Integer(-1),Arrays.asList(48).toArray(new Integer[0]),67,"Author Q. Tester",new Date(),"org.mitre.schemastore.model.mapfunctions.ExtractYear","notes"));
        return ret;
    }


    public ProjectSchema[] getSchemas()
    {
        return schemas;
    }

    public Mapping[] getMappings()
    {
        return mappings;
    }

    public Project getProject()
    {
        return new Project(new Integer(333),"","","",getSchemas());
    }

    public String[] getPrefixArray( String value, int size )
    {
        String[] ret = new String[size];
        Arrays.fill( ret, value );
        return ret;
    }


    public MappingInfo getMappingDefinition()
    {
        ArrayList<MappingCell> cells = new ArrayList<MappingCell>();
        cells.addAll(getMappingCells().values());
        return new MappingInfo( getProject(), cells, getSourceSchema(), getTargetSchema());
    }

}
