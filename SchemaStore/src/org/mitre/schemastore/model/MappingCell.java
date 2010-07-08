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
	/** Defines the identify function */
	private static final Integer IDENTIFY_FUNCTION = 450;
	
	/** Stores the mapping cell id */
	private Integer id = -1;

	/** Stores the mapping id */
	private Integer mappingId;

	/** Stores the first mapping cell element */
	private Integer[] input;

	/** Stores the second mapping cell element */
	private Integer output;

	/** Stores the mapping cell score */
	private Double score;

	/** Stores the reference to the function used by this mapping cell */
    private Integer functionID;

	/** Stores the mapping cell author */
	private String author;

	/** Stores the modification date */
	private Date modificationDate;

	/** Stores notes about the mapping cell */
	private String notes;

	/** Constructs a default mapping cell */
	public MappingCell() {}

    /** Constructs a score-based mapping cell */
    static public MappingCell createProposedMappingCell(Integer id, Integer mappingId, Integer input, Integer output, Double score, String author, Date modificationDate, String notes)
    	{ return new MappingCell(id, mappingId, new Integer[]{input}, output, score, null, author, modificationDate, notes); }
    
    /** Constructs an identity mapping cell */
    static public MappingCell createIdentityMappingCell(Integer id, Integer mappingId, Integer input, Integer output, String author, Date modificationDate, String notes)
    	{ return new MappingCell(id, mappingId, new Integer[]{input}, output, 1.0, IDENTIFY_FUNCTION, author, modificationDate, notes); }
    
    /** Constructs a function mapping cell */
    static public MappingCell createFunctionMappingCell(Integer id, Integer mappingId, Integer inputs[], Integer output, Integer functionID, String author, Date modificationDate, String notes)
    	{ return new MappingCell(id, mappingId, inputs, output, 1.0, functionID, author, modificationDate, notes); }
   
    /** Constructs a mapping cell */
    public MappingCell(Integer id, Integer mappingId, Integer[] input, Integer output, Double score, Integer functionID, String author, Date modificationDate, String notes)
		{ this.id=id; this.mappingId=mappingId; this.input=input; this.output=output; this.score=score; this.functionID=functionID; this.author=author; this.modificationDate=modificationDate; this.notes=notes; }
    
	/** Copies the mapping cell */
	public MappingCell copy()
		{ return new MappingCell(getId(),getMappingId(),getInput(),getOutput(),getScore(),getFunctionID(),getAuthor(),getModificationDate(),getNotes()); }

	// Handles all mapping cell getters
	public Integer getId() { return id; }
	public Integer getMappingId() { return mappingId; }
    public Integer[] getInput() { return input; }
    public Integer getOutput() { return output; }
	public Double getScore() { return score; }
    public Integer getFunctionID() { return functionID; }
	public String getAuthor() { return author==null ? "" : author; }
	public Date getModificationDate() { return modificationDate; }
	public String getNotes() { return notes==null ? "" : notes; }
	
	// Handles all mapping cell setters
	public void setId(Integer id) { this.id = id; }
	public void setMappingId(Integer mappingId) { this.mappingId = mappingId; }
    public void setInput(Integer[] input) {this.input = input; }
    public void setOutput(Integer output) {this.output = output; }
	public void setScore(Double score) { this.score = score; }
	public void setFunctionID(Integer functionID) { this.functionID=functionID; }
	public void setAuthor(String author) { this.author = author==null ? "" : author; }
	public void setModificationDate(Date modificationDate) { this.modificationDate = modificationDate; }
    public void setNotes(String notes) { this.notes = notes==null ? "" : notes; }
    
   /** @deprecated This version assumes only one input */
	public Integer getFirstInput() {  if( input != null && input.length>0 ) return input[0]; else return null; }
	
	/** Returns the string representing the modification date */
	public String getDate()
		{ return modificationDate==null ? "" : DateFormat.getDateInstance(DateFormat.MEDIUM).format(modificationDate); }

	/** Indicates if the mapping cell has been validated */
	public Boolean isValidated()
		{ return functionID!=null; }
	
	/** Indicates if the identity function is being used */
	public Boolean isIdentityFunction()
		{ return functionID==null || functionID.equals(IDENTIFY_FUNCTION); }
	
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