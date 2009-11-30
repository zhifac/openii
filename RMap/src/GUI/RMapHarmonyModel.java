// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package GUI;

import java.awt.Frame;

import org.mitre.harmony.model.HarmonyModel;

/** Class for monitoring for changes in the project */
public class RMapHarmonyModel extends HarmonyModel
{

	// Stores the managers associated with the currently displayed mapping
	private RMapSchemaManager _RMapSchemaManager;
	private RMapMappingManager _RMapMappingManager; 
	private RMapSelectedInfoManager _RMapSelectedInfoManager;
	
	/** Constructs the Harmony model */
	public RMapHarmonyModel(Frame baseFrame){
		super(baseFrame);
	}
	
	/** Returns the schema manager */
	public RMapSchemaManager getSchemaManager(){ 
		if (_RMapSchemaManager == null)
			_RMapSchemaManager = new RMapSchemaManager(this);
		return _RMapSchemaManager; 
	}

	/** Returns the mapping manager */
	public RMapMappingManager getMappingManager()
	{ 
		if (_RMapMappingManager == null)
			_RMapMappingManager = new RMapMappingManager(this);
		return _RMapMappingManager; 
	}

	/** Returns the selected info manager */
	public RMapSelectedInfoManager getSelectedInfo()	
	{ 
		if (_RMapSelectedInfoManager == null)
			_RMapSelectedInfoManager = new RMapSelectedInfoManager(this);
		return _RMapSelectedInfoManager; 
	}
	
}