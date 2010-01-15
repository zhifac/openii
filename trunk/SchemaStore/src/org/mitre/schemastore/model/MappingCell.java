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

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * Class for storing a mapping cell
 * @author CWOLF
 */
public class MappingCell implements Serializable
{
	/** Stores the mapping cell id */
	private Integer id = -1;

	/** Stores the mapping id */
	private Integer mappingId;

	/** Stores the first mapping cell element */
	private Integer[] input;

	/** Stores a string representation of the input types */
	private String[] inputType;

	/** Stores the second mapping cell element */
	private Integer output;

	/** Stores a string representation of the output type */
	private String outputType;

	/** Stores the mapping cell score */
	private Double score;

	/** Stores the mapping cell author */
	private String author;

	/** Stores the modification date */
	private Date modificationDate;

    /**
     * Stores the fully qualified class name of the Java class that represents
     * the transformation that should occur for this mapping
     */
    private String functionClass = "org.mitre.schemastore.model.mapfunctions.IdentityFunction";

	/** Stores notes about the mapping cell */
	private String notes;

	/** Stores if the mapping cell has been validated */
	private Boolean validated;

	/** Constructs a default mapping cell */
	public MappingCell() {}

    /** Constructs a proposed mapping cell */
	private MappingCell(Integer id, Integer mappingId, Integer input, Integer output, Double score, String author, Date modificationDate, String notes)
    	{ this.id=id; this.mappingId=mappingId; this.input=new Integer[1]; this.input[0]=input; this.output=output; this.score=score; this.author=author; this.modificationDate=modificationDate; this.notes=notes; this.validated=false; }

    /** Constructs a validated mapping cell */
    private MappingCell(Integer id, Integer mappingId, Integer[] input, Integer output, String author, Date modificationDate, String functionClass, String notes)
		{ this.id=id; this.mappingId=mappingId; this.input=input; this.output=output; this.author=author; this.modificationDate=modificationDate; this.functionClass=functionClass; this.notes=notes; this.validated=true; this.score=1.0;}

    /**  Factory method to create a proposed mapping cell  */
    public static MappingCell createProposedMappingCell(Integer id, Integer mappingId, Integer input, Integer output, Double score, String author, Date modificationDate, String notes)
    	{ return new MappingCell(id,  mappingId, input, output, score, author, modificationDate, notes); }

    /** Factory method to create a validated mapping cell */
    public static MappingCell createValidatedMappingCell(Integer id, Integer mappingId, Integer[] input, Integer output, String author, Date modificationDate, String functionClass, String notes)
    	{ return new MappingCell( id,  mappingId,  input,  output,  author,  modificationDate,  functionClass,  notes); }

	/** Copies the mapping cell */
	public MappingCell copy()
	{
        if(validated)
            return new MappingCell(getId(),getMappingId(),getInput(),getOutput(),getAuthor(),getModificationDate(),getFunctionClass(),getNotes());
        else
            return new MappingCell(getId(),getMappingId(),getInput()[0],getOutput(),getScore(),getAuthor(),getModificationDate(),getNotes());
    }

	// Handles all mapping cell getters
	public Integer getId() { return id; }
	public Integer getMappingId() { return mappingId; }
    public Integer[] getInput() { return input; }
    public Integer getOutput() { return output; }
    public String[] getInputType() { return inputType; }
    public String getOutputType() { return outputType; }
	public Double getScore() { return score; }
	public String getAuthor() { return author==null ? "" : author; }
	public Date getModificationDate() { return modificationDate; }
    public String getFunctionClass() { return functionClass; }
	public String getNotes() { return notes==null ? "" : notes; }
	public Boolean getValidated() { return validated; }

	// Handles all mapping cell setters
	public void setId(Integer id) { this.id = id; }
	public void setMappingId(Integer mappingId) { this.mappingId = mappingId; }
    public void setInput(Integer[] input) {this.input = input; }
    public void setOutput(Integer output) {this.output = output; }
    public void setInputType(String[] inputType) { this.inputType = inputType; }
    public void setOutputType(String otype) { this.outputType = otype;}
	public void setScore(Double score) { this.score = score; }
	public void setAuthor(String author) { this.author = author==null ? "" : author; }
	public void setModificationDate(Date modificationDate) { this.modificationDate = modificationDate; }
	public void setFunctionClass(String functionClass) { this.functionClass = functionClass==null ? "" : functionClass; }
    public void setNotes(String notes) { this.notes = notes==null ? "" : notes; }

   /** @deprecated This version assumes only one input */
	public Integer getFirstInput() {  if( input != null && input.length>0 ) return input[0]; else return null; }

	/** Returns the string representing the modification date */
	public String getDate()
		{ return modificationDate==null ? "" : DateFormat.getDateInstance(DateFormat.MEDIUM).format(modificationDate); }

	/** Returns the hash code */
	public int hashCode()
		{ return id.hashCode(); }

	/** Indicates that two mapping cells are equals */
	public boolean equals(Object object)
	{
		if(object instanceof Integer) return ((Integer)object).equals(id);
		if(object instanceof MappingCell) return ((MappingCell)object).id.equals(id);
		return false;
	}

	/** String representation of the mapping cell */
	public String toString()
		{ return "("+input[0]+","+output+") -> "+score; }
}