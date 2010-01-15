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
package org.mitre.schemastore.model.mappingInfo;

import org.mitre.schemastore.model.mapfunctions.*;
import org.mitre.schemastore.model.schemaInfo.*;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.MappingCell;
import java.util.*;


/**
 *  This class provides a container for holding, validating, and monitoring a mapping
 *  between two schemas
 *
 *  @author     Jeffrey Hoyt
 *  @version    1.0
 */
public class MappingInfo {

    //{{{ Member variables
    /**
     * Mapping property.
     */
    protected Project mapping = null;


    /**
     * MappingCells property.
     */
    protected List <MappingCell> mappingCells = null;

    /**
     * LeftSchema property.
     */
    protected HierarchicalSchemaInfo leftSchema = null;

    /**
     * RightSchema property.
     */
    protected HierarchicalSchemaInfo rightSchema = null;

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
    public Project getMapping()
    {
        return this.mapping;
    }


    /**
     * Set mapping property.
     *
     *@param mapping New mapping property.
     */
    public void setMapping(Project mapping)
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
     * Get leftSchema property.
     *
     *@return LeftSchema property.
     */
    public HierarchicalSchemaInfo getLeftSchema()
    {
        return this.leftSchema;
    }

    /**
     * Set leftSchema property.
     *
     *@param leftSchema New leftSchema property.
     */
    public void setLeftSchema(HierarchicalSchemaInfo leftSchema)
    {
        this.leftSchema = leftSchema;
    }


    /**
     * Get rightSchema property.
     *
     *@return RightSchema property.
     */
    public HierarchicalSchemaInfo getRightSchema()
    {
        return this.rightSchema;
    }

    /**
     * Set rightSchema property.
     *
     *@param rightSchema New rightSchema property.
     */
    public void setRightSchema(HierarchicalSchemaInfo rightSchema)
    {
        this.rightSchema = rightSchema;
    }
    //}}}

    //{{{ Constructors
    public MappingInfo()
    {
        mapping = new Project();
        mappingCells = new ArrayList <MappingCell>();
    }


    public MappingInfo(Project m, List <MappingCell> cells, HierarchicalSchemaInfo left, HierarchicalSchemaInfo right)
    {
        mapping = m;
        leftSchema = left;
        rightSchema = right;
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
            domain = leftSchema.getDomainForElement(input[i]);
            if( domain != null )
            {
                types[i] = leftSchema.getDomainForElement(input[i]).getName();
            }
            else
            {
                System.out.println(input[i] +"(" +leftSchema.getElement(input[i]) + ") didn't have a domain.");
                types[i] = MappingInfo.ANY;
            }
        }
        cell.setInputType( types );

        /* set output Domain */
        // Integer output = cell.getOutput();
        // domain = rightSchema.getDomainForElement(output);
        // String type = null;
        // if( domain != null )
        // {
        //     type = rightSchema.getDomainForElement(output).getName();
        // }
        //     type = MappingDefinition.ANY;
    }

}
//please do not remove the line below - JCH
// :wrap=none:noTabs=true:collapseFolds=1:maxLineLen=160:mode=java:tabSize=4:indentSize=4:folding=explicit:
