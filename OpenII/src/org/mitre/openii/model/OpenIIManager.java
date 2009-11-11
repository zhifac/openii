package org.mitre.openii.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.eclipse.core.runtime.Preferences;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Tag;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/**
 * Generates the schema store connection for use by all components
 * @author CWOLF
 */
public class OpenIIManager
{
	/** Caches the list of schemas currently available */
	static private HashMap<Integer,Schema> schemas = null;
	
	/** Caches the list of tags currently available */
	static private HashMap<Integer,Tag> tags = null;
	
	/** Stores listeners to the OpenII Manager */
	static private ListenerGroup<OpenIIListener> listeners = new ListenerGroup<OpenIIListener>();
	
	/** Initializes this class */
	static { reset(); }	

	/** Resets the OpenII caches */
	static public void reset()
	{
		// Initialize the list of schemas
		try {
			schemas = new HashMap<Integer,Schema>();
			for(Schema schema : RepositoryManager.getClient().getSchemas())
				schemas.put(schema.getId(),schema);
		} catch(Exception e) {}

		// Initialize the list of tags
		try {
			tags = new HashMap<Integer,Tag>();
			for(Tag tag : RepositoryManager.getClient().getTags())
				tags.put(tag.getId(),tag);
		} catch(Exception e) {}
		
		// Informs listeners that the OpenII repository has been reset
		for(OpenIIListener listener : listeners.get()) listener.repositoryReset();
	}
	
	/** Handles the adding and removal of listeners */
	static public void addListener(OpenIIListener listener) { listeners.add(listener); }
	static public void removeListener(OpenIIListener listener) { listeners.remove(listener); }
	
	//------------ OpenII Settings --------------------

	/** Returns the active directory */
	public static String getActiveDir()
		{ return OpenIIActivator.getDefault().getPluginPreferences().getString("ActiveDirectory"); }
	
	/** Sets the active directory */
	public static void setActiveDir(String activeDir)
	{
		Preferences preferences = OpenIIActivator.getDefault().getPluginPreferences();
		preferences.setValue("ActiveDirectory",activeDir);
		OpenIIActivator.getDefault().savePluginPreferences();
	}
	
	//------------ Schema Functionality -------------
	
	/** Returns the list of schemas */
	public static ArrayList<Schema> getSchemas()
		{ return new ArrayList<Schema>(schemas.values()); }

	/** Returns the specified schema */
	public static Schema getSchema(Integer schemaID)
		{ return schemas.get(schemaID); }

	/** Modifies the schema in the repository */
	public static boolean updateSchema(Schema schema)
	{
		try {
			if(RepositoryManager.getClient().updateSchema(schema))
				{ fireSchemaModified(schema); return true; }
		} catch(Exception e) {}
		return false;
	}
	
	/** Extends the specified schema */
	public static Integer extendSchema(Integer schemaID, String name, String author, String description)
	{
		try {
			Schema schema = RepositoryManager.getClient().extendSchema(schemaID);
			if(schema==null) return null;
			schema.setName(name); schema.setAuthor(author); schema.setDescription(description);
			if(RepositoryManager.getClient().updateSchema(schema)) { fireSchemaAdded(schema); return schema.getId(); }
			else RepositoryManager.getClient().deleteSchema(schemaID);
		} catch(Exception e) {}
		return null;
	}
	
	/** Returns the schema info for the specified schema */
	public static SchemaInfo getSchemaInfo(Integer schemaID)
		{ try { return RepositoryManager.getClient().getSchemaInfo(schemaID); } catch(Exception e) { return null; } }
	
	/** Returns if the schema is deletable */
	public static boolean isDeletable(Integer schemaID)
		{ try { return RepositoryManager.getClient().isDeletable(schemaID); } catch(Exception e) { return false; } }
	
	/** Deletes the specified schema */
	public static boolean deleteSchema(Integer schemaID)
	{
		try {
			if(RepositoryManager.getClient().deleteSchema(schemaID))
				{ fireSchemaDeleted(schemaID); return true; }
		} catch(Exception e) {}
		return false;
	}
	
	/** Inform listeners that schema was added */
	public static void fireSchemaAdded(Schema schema)
	{
		schemas.put(schema.getId(),schema);
		for(OpenIIListener listener : listeners.get()) listener.schemaAdded(schema.getId());
	}

	/** Inform listeners that schema was modified */
	public static void fireSchemaModified(Schema schema)
	{
		schemas.put(schema.getId(),schema);
		for(OpenIIListener listener : listeners.get()) listener.schemaModified(schema.getId());
	}

	/** Inform listeners that schema was removed */
	public static void fireSchemaDeleted(Integer schemaID)
	{
		schemas.remove(schemaID);
		for(OpenIIListener listener : listeners.get()) listener.schemaDeleted(schemaID);
	}
	
	//------------ Tag Functionality -------------
	
	/** Returns the list of tags */
	public static ArrayList<Tag> getTags()
		{ return new ArrayList<Tag>(tags.values()); }

	/** Returns the specified tag */
	public static Tag getTag(Integer tagID)
		{ return tags.get(tagID); }
	
	/** Returns the list of subcategories for the specified tag */
	public static ArrayList<Tag> getSubcategories(Integer tagID)
	{
		try { return RepositoryManager.getClient().getSubcategories(tagID); }
		catch(Exception e) { return new ArrayList<Tag>(); }
	} 
	
	/** Add tag to the repository */
	public static Integer addTag(Tag tag)
	{
		try {
			Integer tagID = RepositoryManager.getClient().addTag(tag);
			if(tagID!=null)
				{ tag.setId(tagID); fireTagAdded(tag); return tagID; }
		} catch(Exception e) {}
		return null;
	}
	
	/** Modifies the tag in the repository */
	public static boolean updateTag(Tag tag)
	{
		try {
			if(RepositoryManager.getClient().updateTag(tag))
				{ fireTagModified(tag); return true; }
		} catch(Exception e) {}
		return false;
	}
	
	/** Removes tag from the repository */
	public static boolean deleteTag(Integer tagID)
	{
		// First move all tag schemas to parent tag if such a tag exists
		Integer parentID = getTag(tagID).getParentId();
		if(parentID!=null)
		{
			ArrayList<Integer> schemaIDs = getTagSchemas(tagID);
			schemaIDs.addAll(getTagSchemas(parentID));
			setTagSchemas(tagID,new ArrayList<Integer>());
			setTagSchemas(parentID, schemaIDs);
		}
		
		// Delete the tag
		try {
			if(RepositoryManager.getClient().deleteTag(tagID))
				{ fireTagDeleted(tagID); return true; }
		} catch(Exception e) {}
		return false;
	}

	/** Returns the list of schemas associated with the specified tag */
	public static ArrayList<Integer> getTagSchemas(Integer tagID)
		{ try { return RepositoryManager.getClient().getTagSchemas(tagID); } catch(Exception e) { return new ArrayList<Integer>(); } }

	/** Returns the list of schemas associate with child tags */
	public static ArrayList<Integer> getChildTagSchemas(Integer tagID)
	{
		ArrayList<Integer> descendantSchemas = new ArrayList<Integer>();
		for(Tag subcategory : getSubcategories(tagID))
		{
			descendantSchemas.addAll(getTagSchemas(subcategory.getId()));
			descendantSchemas.addAll(getChildTagSchemas(subcategory.getId()));
		}
		return descendantSchemas;
	}
	
	/** Sets the list of schema associated with the specified tag */
	public static boolean setTagSchemas(Integer tagID, ArrayList<Integer> schemaIDs)
	{
		// Only set tag schemas if changes have been made
		ArrayList<Integer> oldSchemaIDs = getTagSchemas(tagID);
		Collections.sort(oldSchemaIDs);
		Collections.sort(schemaIDs);
		if(oldSchemaIDs.equals(schemaIDs)) return true;
		
		// Remove schemas in child agst from list since prohibited from being selected
		schemaIDs.removeAll(getChildTagSchemas(tagID));
		
		// Remove selected schemas from ancestor tags
		Tag parentTag = getTag(tagID);
		while(parentTag.getParentId()!=null)
		{
			parentTag = getTag(parentTag.getParentId());
			ArrayList<Integer> parentSchemaIDs = getTagSchemas(parentTag.getId());
			parentSchemaIDs.removeAll(schemaIDs);
			setTagSchemas(parentTag.getId(), parentSchemaIDs);
		}
		
		// Add and remove schemas from the tag as needed
		try {
			for(Integer oldSchemaID : oldSchemaIDs)
				if(!schemaIDs.contains(oldSchemaID)) RepositoryManager.getClient().removeTagFromSchema(oldSchemaID, tagID);
			for(Integer schemaID : schemaIDs)
				if(!oldSchemaIDs.contains(schemaID)) RepositoryManager.getClient().addTagToSchema(schemaID, tagID);
		} catch(Exception e) { return false; }
		
		// Inform listeners that the tag has been modified
		fireTagModified(getTag(tagID));
		return true;
	}
	
	/** Inform listeners that tag was added */
	public static void fireTagAdded(Tag tag)
	{
		tags.put(tag.getId(), tag);
		for(OpenIIListener listener : listeners.get()) listener.tagAdded(tag.getId());
	}

	/** Inform listeners that tag was modified */
	public static void fireTagModified(Tag tag)
	{
		tags.put(tag.getId(), tag);
		for(OpenIIListener listener : listeners.get()) listener.tagModified(tag.getId()); }

	/** Inform listeners that tag was removed */
	public static void fireTagDeleted(Integer tagID)
	{
		tags.remove(tagID);
		for(OpenIIListener listener : listeners.get()) listener.tagDeleted(tagID);
	}
	
	//------------ Mapping Functionality -------------
	
	/** Returns the list of mappings */
	public static ArrayList<Mapping> getMappings()
		{ try { return RepositoryManager.getClient().getMappings(); } catch(Exception e) { return new ArrayList<Mapping>(); } }

	/** Returns the specified mapping */
	public static Mapping getMapping(Integer mappingID)
		{ try { return RepositoryManager.getClient().getMapping(mappingID); } catch(Exception e) { return null; } }
	
	/** Returns the specified mapping cells */
	public static ArrayList<MappingCell> getMappingCells(Integer mappingID)
		{ try { return RepositoryManager.getClient().getMappingCells(mappingID); } catch(Exception e) { return new ArrayList<MappingCell>(); } }
	
	/** Add mapping to the repository */
	public static Integer addMapping(Mapping mapping)
	{
		try {
			Integer mappingID = RepositoryManager.getClient().addMapping(mapping);
			if(mappingID!=null)
				{ mapping.setId(mappingID); fireMappingAdded(mapping); return mappingID; }
		} catch(Exception e) {}
		return null;
	}

	/** Modifies the mapping in the repository */
	public static boolean updateMapping(Mapping mapping)
	{
		try {
			if(RepositoryManager.getClient().updateMapping(mapping))
				{ fireMappingModified(mapping.getId()); return true; }
		} catch(Exception e) {}
		return false;
	}
	
	/** Saves the mapping to the repository */
	public static Integer saveMapping(Mapping mapping, ArrayList<MappingCell> mappingCells)
	{
		try {
			Integer mappingID = RepositoryManager.getClient().saveMapping(mapping,mappingCells);
			if(mappingID!=null)
			{
				if(mapping.getId()==null || !mapping.getId().equals(mappingID))
					{ mapping.setId(mappingID); fireMappingAdded(mapping); return mappingID; }
				else { fireMappingModified(mappingID); return mappingID; }
			}
		} catch(Exception e) {}
		return null;
	}
	
	/** Deletes the specified mapping */
	public static boolean deleteMapping(Integer mappingID)
	{
		try {
			if(RepositoryManager.getClient().deleteMapping(mappingID))
				{ fireMappingDeleted(mappingID); return true; }
		} catch(Exception e) {}
		return false;
	}
	
	/** Inform listeners that mapping was added */
	public static void fireMappingAdded(Mapping mapping)
		{ for(OpenIIListener listener : listeners.get()) listener.mappingAdded(mapping.getId()); }

	/** Inform listeners that mapping was modified */
	public static void fireMappingModified(Integer mappingID)
		{ for(OpenIIListener listener : listeners.get()) listener.mappingModified(mappingID); }
	
	/** Inform listeners that mapping was removed */
	public static void fireMappingDeleted(Integer mappingID)
		{ for(OpenIIListener listener : listeners.get()) listener.mappingDeleted(mappingID); }
	
	//------------ Data Source Functionality -------------

	/** Returns the data sources for the specified schema */
	public static ArrayList<DataSource> getDataSources(Integer schemaID)
		{ try { return RepositoryManager.getClient().getDataSources(schemaID); } catch(Exception e) { return new ArrayList<DataSource>(); } }
	
	/** Deletes the specified data source */
	public static boolean deleteDataSource(Integer dataSourceID)
	{
		try {
			if(RepositoryManager.getClient().deleteDataSource(dataSourceID))
				{ fireDataSourceDeleted(dataSourceID); return true; }
		} catch(Exception e) {}
		return false;
	}
	
	/** Inform listeners that data source was added */
	public static void fireDataSourceAdded(Integer dataSourceID)
		{ for(OpenIIListener listener : listeners.get()) listener.dataSourceAdded(dataSourceID); }
	
	/** Inform listeners that data source was removed */
	public static void fireDataSourceDeleted(Integer dataSourceID)
		{ for(OpenIIListener listener : listeners.get()) listener.dataSourceDeleted(dataSourceID); }
}