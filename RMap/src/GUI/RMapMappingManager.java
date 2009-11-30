// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package GUI;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import org.mitre.harmony.model.mapping.MappingListener;
import org.mitre.harmony.model.mapping.MappingManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.SchemaElement;


public class RMapMappingManager extends MappingManager {
	
	/** Stores the Harmony model */
	private RMapHarmonyModel _harmonyModel;
	

	public RMapMappingManager(RMapHarmonyModel harmonyModel){
		super(null);
		_harmonyModel = harmonyModel;
	}

	/** Returns the Harmony model */
	protected RMapHarmonyModel getModel()
		{ return _harmonyModel; }
	
	/** Returns all elements displayed on the specified side of the mapping */
	public HashSet<SchemaElement> getSchemaElements(Integer side)
	{
		HashSet<SchemaElement> elements = new HashSet<SchemaElement>();
		for(Integer schemaID : getSchemaIDs(side))
			elements.addAll(getModel().getSchemaManager().getSchemaInfo(schemaID).getElements(null));
		return elements;
	}

	/** Returns all element IDs displayed on the specified side of the mapping */
	public HashSet<Integer> getSchemaElementIDs(Integer side)
	{
		HashSet<Integer> elementIDs = new HashSet<Integer>();
		for(SchemaElement element : getSchemaElements(side))
			elementIDs.add(element.getId());
		return elementIDs;
	}

	
	
	/** Loads the specified mapping */
	public boolean loadMapping(Integer mappingID)
	{
		try {
			// Retrieve the mapping information
			Mapping newMapping = _harmonyModel.getSchemaManager().getMapping(mappingID);
			ArrayList<MappingCell> newMappingCells = _harmonyModel.getSchemaManager().getMappingCells(mappingID);
			
			// Sets the new mapping information
			_harmonyModel.getMappingCellManager().deleteAllMappingCells();
			setSchemas(new ArrayList<MappingSchema>(Arrays.asList(newMapping.getSchemas())));
			mapping = newMapping;
			_harmonyModel.getMappingCellManager().setMappingCells(newMappingCells);
			/**
			for(MappingCell mappingCell : newMappingCells){
				mappingCell.setId(null);
				
				_harmonyModel.getMappingCellManager().setMappingCell(mappingCell));
			}	
			*/
			
			// Inform listeners of modified mapping
			setModified(false);
			for(MappingListener listener : getListeners()) listener.mappingModified();
			return true;
		}
		catch(Exception e) { System.out.println("(E) MappingManager:loadMapping - " + e.getMessage()); return false; }
	}
	
}
