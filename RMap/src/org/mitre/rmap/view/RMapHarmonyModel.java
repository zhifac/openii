// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.rmap.view;

import java.awt.Frame;

import org.mitre.harmony.model.HarmonyModel;

/** Class for monitoring for changes in the project */
public class RMapHarmonyModel extends HarmonyModel
{

	// Stores the managers associated with the currently displayed mapping
	private RMapSchemaManager _RMapSchemaManager;
	private RMapProjectManager _RMapProjectManager; 
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
	public RMapProjectManager getProjectManager()
	{ 
		if (_RMapProjectManager == null)
			_RMapProjectManager = new RMapProjectManager(this);
		return _RMapProjectManager; 
	}

	/** Returns the selected info manager */
	public RMapSelectedInfoManager getSelectedInfo()	
	{ 
		if (_RMapSelectedInfoManager == null)
			_RMapSelectedInfoManager = new RMapSelectedInfoManager(this);
		return _RMapSelectedInfoManager; 
	}
	
}