// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data;

import java.util.ArrayList;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;

/** Class for managing the mappings in the schema repository */
public class Mappings extends DataCache
{
	/** Constructs the mappings cache */
	Mappings(DataManager manager)
		{ super(manager); }
	
	/** Returns a listing of all mappings */
	public ArrayList<Mapping> getMappings()
		{ return getDatabase().getMappings(); }
	
	/** Retrieve the specified mapping */
	public Mapping getMapping(Integer mappingID)
		{ return getDatabase().getMapping(mappingID); }
	
	/** Add the specified mapping */
	public Integer addMapping(Mapping mapping)
		{ return getDatabase().addMapping(mapping); }
	
	/** Update the specified mapping */
	public Boolean updateMapping(Mapping mapping)
		{ return getDatabase().updateMapping(mapping); }

	/** Delete the specified mapping */
	public Boolean deleteMapping(Integer mappingID)
		{ return getDatabase().deleteMapping(mappingID); }
	
	/** Get the mapping cells for the specified schema */
	public ArrayList<MappingCell> getMappingCells(Integer mappingID)
		{ return getDatabase().getMappingCells(mappingID); }

	/** Add the specified mapping cell */
	public Integer addMappingCell(MappingCell mappingCell)
		{ return getDatabase().addMappingCell(mappingCell); }

	/** Update the specified mapping cell */
	public Boolean updateMappingCell(MappingCell mappingCell)
		{ return getDatabase().updateMappingCell(mappingCell); }
	
	/** Delete the specified mapping cell */
	public Boolean deleteMappingCell(Integer mappingCellID)
		{ return getDatabase().deleteMappingCell(mappingCellID); }
}