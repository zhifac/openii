package org.mitre.harmony.model;

import java.awt.Frame;

import org.mitre.harmony.model.filters.FilterManager;
import org.mitre.harmony.model.mapping.MappingCellManager;
import org.mitre.harmony.model.mapping.MappingManager;
import org.mitre.harmony.model.preferences.PreferencesManager;
//import org.mitre.harmony.model.search.SearchManager;
import org.mitre.harmony.model.selectedInfo.SelectedInfoManager;

/** Class for monitoring for changes in the project */
public class HarmonyModel
{
	// Stores the base frame for the particular model
	protected Frame baseFrame = null;
	
	// Stores the managers associated with the currently displayed mapping
	protected SchemaManager schemaManager = new SchemaManager(this);
	protected MappingManager mappingManager = new MappingManager(this);
	protected MappingCellManager mappingCellManager = new MappingCellManager(this);
	
	// Stores the various managers associated with the model
	protected FilterManager filterManager = new FilterManager(this);
	protected PreferencesManager preferencesManager = new PreferencesManager(this);
	protected SelectedInfoManager selectedInfoManager = new SelectedInfoManager(this);
//	protected SearchManager searchManager = new SearchManager(this);

	/** Constructs the Harmony model */
	public HarmonyModel(Frame baseFrame)
	{
		this.baseFrame = baseFrame;

		// Add listeners to the various model objects
		filterManager.addListener(selectedInfoManager);
		selectedInfoManager.addListener(filterManager);
//		selectedInfoManager.addListener(searchManager);
		preferencesManager.addListener(mappingCellManager);
		mappingManager.addListener(mappingCellManager);
		mappingManager.addListener(preferencesManager);
		mappingManager.addListener(selectedInfoManager);
		mappingCellManager.addListener(mappingManager);
		mappingCellManager.addListener(selectedInfoManager);
	}
	
	/** Returns the base frame */
	public Frame getBaseFrame()
		{ return baseFrame; }
	
	/** Returns the filters */
	public FilterManager getFilters()
		{ return filterManager; }

	/** Returns the preferences */
	public PreferencesManager getPreferences()
		{ return preferencesManager; }
	
	/** Returns the selected info manager */
	public SelectedInfoManager getSelectedInfo()
		{ return selectedInfoManager; }

	/** Returns the search manager */
//	public SearchManager getSearchManager()
//		{ return searchManager; }
	
	/** Returns the schema manager */
	public SchemaManager getSchemaManager()
		{ return schemaManager; }

	/** Returns the mapping manager */
	public MappingManager getMappingManager()
		{ return mappingManager; }
	
	/** Returns the mapping cell manager */
	public MappingCellManager getMappingCellManager()
		{ return mappingCellManager; }
}