// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.mappingInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;

/**
 * Class for storing the mapping info
 */
public class MappingInfo implements Serializable
{
	/** Stores the mapping */
	private Mapping mapping;

	/** Stores the mapping cells */
	private HashMap<String,MappingCell> mappingCellHash;

	/** Constructs the hash map key */
	private String getKey(Integer[] inputIDs, Integer outputID)
	{
		StringBuffer key = new StringBuffer();
		for(Integer input : inputIDs)
			key.append(input+"_");
		key.append(outputID);
		return key.toString();
	}

	/** Constructs the hash map key */
	private String getKey(MappingCell mappingCell)
		{ return getKey(mappingCell.getInput(),mappingCell.getOutput()); }
	
	/** Constructs the mapping info */
	public MappingInfo(Mapping mapping, ArrayList<MappingCell> mappingCells)
	{
		this.mapping = mapping;
		for(MappingCell mappingCell : mappingCells)
			mappingCellHash.put(getKey(mappingCell),mappingCell);
	}		

	/** Copy the mapping info */
	public MappingInfo copy()
	{
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		for(MappingCell mappingCell : mappingCellHash.values())
			mappingCells.add(mappingCell);
		return new MappingInfo(mapping.copy(),mappingCells);
	}

	/** Returns the mapping */
	public Mapping getMapping()
		{ return mapping; }

	/** Returns the specified mapping cell */
	public MappingCell getMappingCell(Integer inputID, Integer outputID)
		{ return mappingCellHash.get(getKey(new Integer[]{inputID},outputID)); }

	/** Returns the specified mapping cell */
	public MappingCell getMappingCell(Integer inputIDs[], Integer outputID)
		{ return mappingCellHash.get(getKey(inputIDs,outputID)); }

	/** Indicates if the mapping contains the specified mapping cell */
	public boolean containsMappingCell(Integer inputID, Integer outputID)
		{ return getMappingCell(inputID,outputID)!=null; }

	/** Indicates if the mapping contains the specified mapping cell */
	public boolean containsMappingCell(Integer inputIDs[], Integer outputID)
		{ return getMappingCell(inputIDs,outputID)!=null; }
}