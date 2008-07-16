// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.model;

import java.util.ArrayList;

import org.mitre.schemastore.model.Mapping;

/** Manages mapping information */
public class MappingManager
{
	/** Returns the mappings associated with the repository */
	static public ArrayList<Mapping> getMappings()
		{ try { return SchemaStore.getClient().getMappings(); } catch(Exception e) { return new ArrayList<Mapping>(); } }

	/** Deletes the specified mapping */
	static public boolean deleteMapping(Integer mappingID)
		{ try { return SchemaStore.getClient().deleteMapping(mappingID); } catch(Exception e) { return false; } }
}
