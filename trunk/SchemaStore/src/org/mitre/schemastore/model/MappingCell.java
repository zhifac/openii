// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;

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
	
	/** Stores the mapping cell scorer */
	private String scorer;
	
	/** Stores if the mapping cell has been validated */
	private Boolean validated;
	
	/** Constructs a default mapping cell */
	public MappingCell() {}
	
	/** Constructs a mapping cell */
	public MappingCell(Integer id, Integer mappingId, Integer element1, Integer element2, Double score, String scorer, Boolean validated)
		{ this.id = id; this.mappingId = mappingId; this.element1 = element1; this.element2 = element2; this.score = score; this.scorer = scorer; this.validated = validated; }
	
	// Handles all mapping cell getters
	public Integer getId() { return id; }
	public Integer getMappingId() { return mappingId; }
	public Integer getElement1() { return element1; }
	public Integer getElement2() { return element2; }
	public Double getScore() { return score; }
	public String getScorer() { return scorer; }
	public Boolean getValidated() { return validated; }

	// Handles all mapping cell setters
	public void setId(Integer id) { this.id = id; }
	public void setMappingId(Integer mappingId) { this.mappingId = mappingId; }
	public void setElement1(Integer element1) { this.element1 = element1; }
	public void setElement2(Integer element2) { this.element2 = element2; }
	public void setScore(Double score) { this.score = score; }
	public void setScorer(String scorer) { this.scorer = scorer; }
	public void setValidated(Boolean validated) { this.validated = validated; }
	
	/** Indicates that two mapping cells are equals */
	public boolean equals(Object mappingCell)
		{ return mappingCell instanceof MappingCell && ((MappingCell)mappingCell).id.equals(id); }
	
	/** String representation of the mapping cell */
	public String toString()
		{ return "("+element1+","+element2+") -> "+score; }
}