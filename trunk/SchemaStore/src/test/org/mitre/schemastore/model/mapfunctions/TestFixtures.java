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

import java.net.*;
import java.io.*;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.data.database.*;
import org.mitre.schemastore.servlet.*;
import org.mitre.schemastore.model.graph.*;
import org.mitre.schemastore.model.graph.model.*;
import java.util.*;


public class TestFixtures
{

    private SchemaStore store = new SchemaStore( DatabaseConnection.DERBY,System.getProperty("java.io.tmpdir"),"schemastore","postgres","postgres");
    private MappingSchema[] schemas = new MappingSchema[2];
    private Mapping map;

    public TestFixtures()
    {
        schemas[0] = new MappingSchema(1025, "left", GraphModel.class.getName(), MappingSchema.LEFT );
        schemas[1] = new MappingSchema(1026, "right", GraphModel.class.getName(), MappingSchema.RIGHT);
        map = new Mapping( new Integer(1024), "name", "description", "author", schemas );
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


    public HierarchicalGraph getSourceGraph()
    {
        ArrayList<SchemaElement> list = new ArrayList<SchemaElement>(Arrays.asList(store.getSchemaElements(10).getSchemaElements()));
        Schema s = store.getSchema(10);
        Graph ret = new Graph( s,list );
        return new HierarchicalGraph(ret, new RelationalGraphModel());
    }


    public HierarchicalGraph getTargetGraph()
    {
        ArrayList<SchemaElement> list = new ArrayList<SchemaElement>(Arrays.asList(store.getSchemaElements(12).getSchemaElements()));
        Schema s = store.getSchema(12);
        Graph ret = new Graph( s,list );
        return new HierarchicalGraph(ret, new RelationalGraphModel());
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
        return ret;
    }


    public MappingSchema[] getMappingSchemas()
    {
        return schemas;
    }

    public Mapping getMapping()
    {
        Mapping mapping = new Mapping(new Integer(333),"","","",getMappingSchemas());
        return mapping;
    }

    public String[] getPrefixArray( String value, int size )
    {
        String[] ret = new String[size];
        Arrays.fill( ret, value );
        return ret;
    }


    public MappingDefinition getMappingDefinition()
    {
        ArrayList<MappingCell> cells = new ArrayList<MappingCell>();
        cells.addAll(getMappingCells().values());
        return new MappingDefinition( getMapping(), cells, getSourceGraph(), getTargetGraph());
    }

}
