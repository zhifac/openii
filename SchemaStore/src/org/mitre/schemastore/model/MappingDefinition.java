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
package org.mitre.schemastore.model;

import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.mapfunctions.*;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.graph.*;
import org.mitre.schemastore.model.graph.model.*;
import java.util.*;


/**
 *  This class provides a container for holding, validating, and monitoring a mapping
 *  between two schemas
 *
 *  @author     Jeffrey Hoyt
 *  @version    1.0
 */
public class MappingDefinition {

    //{{{ Member variables
    /**
     * Mapping property.
     */
    protected Mapping mapping = null;


    /**
     * MappingCells property.
     */
    protected List <MappingCell> mappingCells = null;

    /**
     * LeftGraph property.
     */
    protected HierarchicalGraph leftGraph = null;

    /**
     * RightGraph property.
     */
    protected HierarchicalGraph rightGraph = null;

	/**  The different types available */
    public static final String ANY = "Any";
    public static final String BOOLEAN = "Boolean";
    public static final String TIMESTAMP = "Timestamp";
    public static final String STRING = "String";
    public static final String REAL = "Real";
    public static final String INTEGER = "Integer";

    //}}}

    //{{{ Getters and Setters

    /**
     * Get mapping property.
     *
     *@return Mapping property.
     */
    public Mapping getMapping()
    {
        return this.mapping;
    }


    /**
     * Set mapping property.
     *
     *@param mapping New mapping property.
     */
    public void setMapping(Mapping mapping)
    {
        this.mapping = mapping;
    }

    /**
     * Get mappingCells property.
     *
     *@return MappingCells property.
     */
    public List <MappingCell> getMappingCells()
    {
        return this.mappingCells;
    }

    /**
     * Set mappingCells property.
     *
     *@param mappingCells New mappingCells property.
     */
    public void setMappingCells(List <MappingCell> mappingCells)
    {
        this.mappingCells = mappingCells;
    }


    /**
     * Get leftGraph property.
     *
     *@return LeftGraph property.
     */
    public HierarchicalGraph getLeftGraph()
    {
        return this.leftGraph;
    }

    /**
     * Set leftGraph property.
     *
     *@param leftGraph New leftGraph property.
     */
    public void setLeftGraph(HierarchicalGraph leftGraph)
    {
        this.leftGraph = leftGraph;
    }


    /**
     * Get rightGraph property.
     *
     *@return RightGraph property.
     */
    public HierarchicalGraph getRightGraph()
    {
        return this.rightGraph;
    }

    /**
     * Set rightGraph property.
     *
     *@param rightGraph New rightGraph property.
     */
    public void setRightGraph(HierarchicalGraph rightGraph)
    {
        this.rightGraph = rightGraph;
    }
    //}}}

    //{{{ Constructors
    public MappingDefinition()
    {
        mapping = new Mapping();
        mappingCells = new ArrayList <MappingCell>();
    }


    public MappingDefinition(Mapping m, List <MappingCell> cells, HierarchicalGraph left, HierarchicalGraph right)
    {
        mapping = m;
        leftGraph = left;
        rightGraph = right;
        mappingCells =  new ArrayList<MappingCell>();
        for(MappingCell cell : cells)
        {
            addMappingCell(cell);
        }
    }
    //}}}

    // Methods

    public boolean addMappingCell( MappingCell cell )
    {
        setDomains( cell );
        if( checkMappingCell( cell ) )
        {
            mappingCells.add( cell );
            return true;
        }
        return false;
    }

    public boolean removeMappingCell( MappingCell cell )
    {
        return true;
    }

    public boolean checkMappingCell( MappingCell cell )
    {
        AbstractMappingFunction mf = FunctionManager.getFunction( cell.getFunctionClass() );

        /* check input count */
        if( mf.getMaxArgs()==-1 && cell.getInput().length < mf.getMinArgs() )
        {  //checking for unlimited upper bound case
            throw new IllegalArgumentException( mf.getClass().getName() + " must have at least " + mf.getMinArgs() + " input(s)." );
        }
        if( mf.getMinArgs() == mf.getMaxArgs() )
        {  //checking case where exact number of inputs is called for
            if( cell.getInput().length != mf.getMinArgs() )
            {
                throw new IllegalArgumentException( mf.getClass().getName() + " requires exactly " + mf.getMinArgs() + " inputs.  " +
                    cell.getInput().length + " were provided." );
            }
        } //checking case where
        if( mf.getMaxArgs() != -1 && cell.getInput().length > mf.getMaxArgs() || cell.getInput().length < mf.getMinArgs() )
        {
            throw new IllegalArgumentException( mf.getClass().getName() + " requires between " + mf.getMinArgs() + " and " + mf.getMaxArgs() +
                "inputs.  " + cell.getInput().length + " were provided." );
        }

        /* check input domain */
        String[] expectedInput = mf.getInputTypes();
        int i;
        for(i = 0; i < cell.getInputType().length; i++)
        {
            String actual = cell.getInputType()[i];
            boolean match = false;
            for( String expected : expectedInput )
            {
               match = match || checkDataType(expected, actual);
            }
            if(!match) throw new IllegalArgumentException( "Inputs should be one of " + Arrays.toString(expectedInput) + ", but " + cell.getInput()[i] + " is of type " + actual);
        }

        /* check output domain */
        return true;
    }


    protected boolean checkDataType( String expected, String actual )
    {
        //allow mappings of the data types match
        if( expected.equals(actual) ) return true;
        //allow casts from anything to String
        if( expected.equalsIgnoreCase(STRING) ) return true;
        //allow casts from integer to real
        if( expected.equalsIgnoreCase(REAL) && actual.equalsIgnoreCase(INTEGER)) return true;
        //allow if ANY
        if( expected.equalsIgnoreCase(ANY) ) return true;
        return false;
    }

    public void setDomains( MappingCell cell )
    {
        /* set input Domains */
        Integer[] input = cell.getInput();
        String[] types = new String[input.length];
        Domain domain;
        for(int i = 0; i < input.length; i++)
        {
            domain = leftGraph.getDomainForElement(input[i]);
            if( domain != null )
            {
                types[i] = leftGraph.getDomainForElement(input[i]).getName();
            }
            else
            {
                System.out.println(input[i] +"(" +leftGraph.getElement(input[i]) + ") didn't have a domain.");
                types[i] = MappingDefinition.ANY;
            }
        }
        cell.setInputType( types );

        /* set output Domain */
        // Integer output = cell.getOutput();
        // domain = rightGraph.getDomainForElement(output);
        // String type = null;
        // if( domain != null )
        // {
        //     type = rightGraph.getDomainForElement(output).getName();
        // }
        //     type = MappingDefinition.ANY;
    }

}
//please do not remove the line below - JCH
// :wrap=none:noTabs=true:collapseFolds=1:maxLineLen=160:mode=java:tabSize=4:indentSize=4:folding=explicit:
