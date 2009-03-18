// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Class for storing a mapping cell
 * @author CWOLF
 */
public class MappingCell implements Serializable
{		
	/** Stores the mapping cell id */
	private Integer id;
	
	/** Stores the mapping id */
	private Integer mappingId;
	
	/** Stores the first mapping cell element */
	private Integer element1;
	
	/** Stores the second mapping cell element */
	private Integer element2;
	
	/** Stores the mapping cell score */
	private Double score;
	
	/** Stores the mapping cell author */
	private String author;
	
	/** Stores the modification date */
	private Date modificationDate;

	/** Stores the mapping cell transform */
	private String transform;
	
	/** Stores notes about the mapping cell */
	private String notes;
	
	/** Stores if the mapping cell has been validated */
	private Boolean validated;
	
	/** Constructs a default mapping cell */
	public MappingCell() {}
	
	/** Constructs a mapping cell */
	public MappingCell(Integer id, Integer mappingId, Integer element1, Integer element2, Double score, String author, Date modificationDate, String transform, String notes, Boolean validated)
		{ this.id=id; this.mappingId=mappingId; this.element1=element1; this.element2=element2; this.score=score; this.author=author; this.modificationDate=modificationDate; this.transform=transform; this.notes=notes; this.validated=validated; }
	
	/** Copies the mapping cell */
	public MappingCell copy()
		{ return new MappingCell(getId(),getMappingId(),getElement1(),getElement2(),getScore(),getAuthor(),getModificationDate(),getTransform(),getNotes(),getValidated()); }
	
	// Handles all mapping cell getters
	public Integer getId() { return id; }
	public Integer getMappingId() { return mappingId; }
	public Integer getElement1() { return element1; }
	public Integer getElement2() { return element2; }
	public Double getScore() { return score; }
	public String getAuthor() { return author==null ? "" : author; }
	public Date getModificationDate() { return modificationDate; }
	public String getTransform() { return transform==null ? "" : transform; }
	public String getNotes() { return notes==null ? "" : notes; }
	public Boolean getValidated() { return validated; }

	// Handles all mapping cell setters
	public void setId(Integer id) { this.id = id; }
	public void setMappingId(Integer mappingId) { this.mappingId = mappingId; }
	public void setElement1(Integer element1) { this.element1 = element1; }
	public void setElement2(Integer element2) { this.element2 = element2; }
	public void setScore(Double score) { this.score = score; }
	public void setAuthor(String author) { this.author = author==null ? "" : author; }
	public void setModificationDate(Date modificationDate) { this.modificationDate = modificationDate; }
	public void setTransform(String transform) { this.transform = transform==null ? "" : transform; }
	public void setNotes(String notes) { this.notes = notes==null ? "" : notes; }
	public void setValidated(Boolean validated) { this.validated = validated; }

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
		{ return "("+element1+","+element2+") -> "+score; }
}