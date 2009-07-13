// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.harmony.model.AbstractManager;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.model.GraphModel;

/**
 * Class used to manage the current project
 * @author CWOLF
 */
public class MappingManager extends AbstractManager<MappingListener> implements MappingCellListener
{	
	/** Stores the current mapping */
	protected Mapping mapping = new Mapping();	
	
	/** Indicates if the mapping has been modified */
	private boolean modified = false;
	
	/** Constructs the mapping manager */
	public MappingManager(HarmonyModel harmonyModel)
		{ super(harmonyModel); }
	
	/** Indicates if the mapping has been modified */
	public boolean isModified()
		{ return modified; }
	
	/** Sets the mapping as modified */
	public void setModified(boolean modified)
	{
		this.modified = modified;
		for(MappingListener listener : getListeners())
			listener.mappingModified();
	}
	
	/** Gets the mapping info */
	public Mapping getMapping()
		{ return mapping.copy(); }

	/** Gets the mapping schemas */
	public ArrayList<MappingSchema> getSchemas()
	{
		if(mapping.getSchemas()==null) return new ArrayList<MappingSchema>();
		return new ArrayList<MappingSchema>(Arrays.asList(mapping.getSchemas()));
	}
	
	/** Gets the mapping schema IDs */
	public ArrayList<Integer> getSchemaIDs()
		{ return new ArrayList<Integer>(Arrays.asList(mapping.getSchemaIDs())); }

	/** Returns the schemas displayed on the specified side of the mapping */
	public ArrayList<Integer> getSchemaIDs(Integer side)
	{
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		for(MappingSchema schema : getSchemas())
			if(schema.getSide().equals(side)) schemaIDs.add(schema.getId());
		return schemaIDs;
	}
	
	/** Returns all elements displayed on the specified side of the mapping */
	public HashSet<SchemaElement> getSchemaElements(Integer side)
	{
		HashSet<SchemaElement> elements = new HashSet<SchemaElement>();
		for(Integer schemaID : getSchemaIDs(side))
			elements.addAll(getModel().getSchemaManager().getGraph(schemaID).getGraphElements());
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

	/** Returns the graph model for the specified schema */
	public GraphModel getGraphModel(Integer schemaID)
	{
		for(MappingSchema schema : getSchemas())
			if(schema.getId().equals(schemaID)) return schema.geetGraphModel();
		return null;
	}
	
	/** Sets the mapping info */
	public void setMappingInfo(String name, String author, String description)
	{
		// Only save information if changes made
		if(!name.equals(mapping.getName()) || !author.equals(mapping.getAuthor()) || !description.equals(mapping.getDescription()))
		{
			// Sets the mapping
			mapping.setName(name);
			mapping.setAuthor(author);
			mapping.setDescription(description);
			
			// Indicates that the mapping has been modified
			for(MappingListener listener : getListeners())
				listener.mappingModified();
		}
	}

	/** Sets the mapping schemas */
	public void setSchemas(ArrayList<MappingSchema> schemas)
	{		
		boolean changesOccured = false;
		
		// Create hash table for the old schema
		HashMap<Integer, MappingSchema> oldSchemas = new HashMap<Integer, MappingSchema>();
		for(MappingSchema schema : getSchemas())
			oldSchemas.put(schema.getId(), schema);

		// Create hash table for the new schema
		HashMap<Integer, MappingSchema> newSchemas = new HashMap<Integer, MappingSchema>();
		for(MappingSchema schema : schemas)
			newSchemas.put(schema.getId(), schema);
		
		// Set the mapping schemas
		mapping.setSchemas(schemas.toArray(new MappingSchema[0]));
		
		// Inform listeners of any schemas that were added
		for(Integer newSchemaID : newSchemas.keySet())
			if(!oldSchemas.containsKey(newSchemaID))
			{
				for(MappingListener listener : getListeners())
					listener.schemaAdded(newSchemaID);
				changesOccured = true;
			}
				
		// Inform listeners of any schemas that were removed
		for(Integer oldSchemaID : oldSchemas.keySet())
			if(!newSchemas.containsKey(oldSchemaID))
			{
				for(MappingListener listener : getListeners())
					listener.schemaRemoved(oldSchemaID);
				changesOccured = true;
			}
				
		// Inform listeners of any schemas that were modified
		for(Integer newSchemaID : newSchemas.keySet())
			if(oldSchemas.containsKey(newSchemaID))
			{
				MappingSchema oldSchema = oldSchemas.get(newSchemaID);
				MappingSchema newSchema = newSchemas.get(newSchemaID);
	
				// Determines if the schema side was modified
				boolean sideModified = !oldSchema.getSide().equals(newSchema.getSide());

				// Determines if the graph model was modified
				boolean graphModelModified = oldSchema.getModel()==null && newSchema.getModel()!=null;
				graphModelModified |= oldSchema.getModel()!=null && !oldSchema.getModel().equals(newSchema.getModel());
				if(graphModelModified)
				{
					GraphModel graphModel = getGraphModel(newSchemaID);
					getModel().getSchemaManager().getGraph(newSchemaID).setModel(graphModel);
				}

				// Informs listeners of modifications to the schema
				if(sideModified || graphModelModified)
				{
					for(MappingListener listener : getListeners())
						listener.schemaModified(newSchemaID);
					changesOccured = true;
				}
			}
		
		// Set the mapping as being modified
		if(changesOccured) setModified(true);
	}
	
	/** Sets the specified schema's graph model */
	public void setGraphModel(Integer schemaID, GraphModel graphModel)
	{
		for(MappingSchema schema : getSchemas())
			if(schema.getId().equals(schemaID))
			{
				schema.seetGraphModel(graphModel);
				getModel().getSchemaManager().getGraph(schemaID).setModel(graphModel);
				for(MappingListener listener : getListeners())
					listener.schemaModified(schemaID);		
				return;
			}
	}
	
	/** Creates a new mapping */
	public void newMapping()
	{
		getModel().getMappingCellManager().deleteMappingCells();
		setSchemas(new ArrayList<MappingSchema>());
		mapping = new Mapping();
		mapping.setAuthor(System.getProperty("user.name"));
		setModified(false);
	}
	
	/** Loads the specified mapping */
	public boolean loadMapping(Integer mappingID)
	{
		try {
			// Retrieve the mapping information
			Mapping newMapping = SchemaStoreManager.getMapping(mappingID);
			ArrayList<MappingCell> newMappingCells = SchemaStoreManager.getMappingCells(mappingID);

			// Sets the new mapping information
			getModel().getMappingCellManager().deleteMappingCells();
			setSchemas(new ArrayList<MappingSchema>(Arrays.asList(newMapping.getSchemas())));
			mapping = newMapping;
			for(MappingCell mappingCell : newMappingCells)
			{
				mappingCell.setId(null);
				getModel().getMappingCellManager().setMappingCell(mappingCell);
			}	
			
			// Inform listeners of modified mapping
			setModified(false);
			for(MappingListener listener : getListeners()) listener.mappingModified();
			return true;
		}
		catch(Exception e) { System.out.println("(E) MappingManager:loadMapping - " + e.getMessage()); return false; }
	}
	
	/** Saves the specified mapping */
	public boolean saveMapping(Mapping mapping)
	{
		try {
			// Update the mapping schema settings
			mapping.setSchemas(this.mapping.getSchemas());
			
			// Save the mapping
			Integer mappingID = SchemaStoreManager.saveMapping(mapping, getModel().getMappingCellManager().getMappingCells());
			if(mappingID==null) throw new Exception("Failed to save mapping");
			mapping.setId(mappingID);
			
			// Indicates that the mapping has been modified
			for(MappingListener listener : getListeners()) listener.mappingModified();
			setModified(false);
			return true;
		}
		catch(Exception e) { System.out.println("(E) MappingManager.saveMapping - " + e.getMessage()); return false; }
	}
	
	/** Deletes the specified mapping */
	public boolean deleteMapping(Integer mappingID)
	{
		// Delete the mapping 
		try {
			SchemaStoreManager.deleteMapping(mappingID);
			return true;
		}
		catch(Exception e) { System.out.println("(E) MappingManager:deleteMapping - " + e.getMessage()); }
		return false;
	}

	// Mark the mapping as modified whenever a mapping cell is modified
	public void mappingCellAdded(MappingCell mappingCell) { setModified(true); }
	public void mappingCellModified(MappingCell oldMappingCell, MappingCell newMappingCell) { setModified(true); }
	public void mappingCellRemoved(MappingCell mappingCell) { setModified(true); }
}
