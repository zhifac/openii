// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.database;

import java.util.ArrayList;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;

/** Class for managing the mappings in the schema repository */
public class Mappings
{
	/** Returns a listing of all mappings */
	static public ArrayList<Mapping> getMappings()
		{ return Database.getMappings(); }
	
	/** Retrieve the specified mapping */
	static public Mapping getMapping(Integer mappingID)
		{ return Database.getMapping(mappingID); }
	
	/** Add the specified mapping */
	static public Integer addMapping(Mapping mapping)
		{ return Database.addMapping(mapping); }
	
	/** Update the specified mapping */
	static public Boolean updateMapping(Mapping mapping)
		{ return Database.updateMapping(mapping); }

	/** Delete the specified mapping */
	static public Boolean deleteMapping(Integer mappingID)
		{ return Database.deleteMapping(mappingID); }
	
	/** Get the mapping cells for the specified schema */
	static public ArrayList<MappingCell> getMappingCells(Integer mappingID)
		{ return Database.getMappingCells(mappingID); }

	/** Add the specified mapping cell */
	static public Integer addMappingCell(MappingCell mappingCell)
		{ return Database.addMappingCell(mappingCell); }

	/** Update the specified mapping cell */
	static public Boolean updateMappingCell(MappingCell mappingCell)
		{ return Database.updateMappingCell(mappingCell); }
	
	/** Delete the specified mapping cell */
	static public Boolean deleteMappingCell(Integer mappingCellID)
		{ return Database.deleteMappingCell(mappingCellID); }
}