package org.mitre.openii.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.graph.Graph;

/**
 * Generates the schema store connection for use by all components
 * @author CWOLF
 */
public class OpenIIManager
{
	/** Stores the client used for accessing the database */
	static private SchemaStoreClient client = null;

	/** Caches the list of schemas currently available */
	static private HashMap<Integer,Schema> schemas = null;
	
	/** Caches the list of groups currently available */
	static private HashMap<Integer,Group> groups = null;
	
	/** Initializes this class */
	static
	{
		// Connect to the repository
		try {
			File file = new File(OpenIIActivator.getBundleFile(),"SchemaStore.jar");
			if(!file.exists()) file = new File(OpenIIActivator.getBundleFile(),"lib/SchemaStore.jar");
			client = new SchemaStoreClient(file.getAbsolutePath());
//			client = new SchemaStoreClient("http://ygg:8080/SchemaStoreForDemo/services/SchemaStore");
		}
		catch(Exception e) { System.out.println("(E) SchemaStoreConnection - " + e.getMessage()); }

		// Initialize the list of schemas
		try {
			schemas = new HashMap<Integer,Schema>();
			for(Schema schema : client.getSchemas())
				schemas.put(schema.getId(),schema);
		} catch(Exception e) {}

		// Initialize the list of groups
		try {
			groups = new HashMap<Integer,Group>();
			for(Group group : client.getGroups())
				groups.put(group.getId(),group);
		} catch(Exception e) {}
	}	
	
	/** Stores listeners to the OpenII Manager */
	static private ListenerGroup<OpenIIListener> listeners = new ListenerGroup<OpenIIListener>();
	static public void addListener(OpenIIListener listener) { listeners.add(listener); }
	static public void removeListener(OpenIIListener listener) { listeners.remove(listener); }
	
	/** Returns the schema store connection */
	public static SchemaStoreClient getConnection()
		{ return client; }
	
	/** Sorts the provided array by name */
	public static <T> ArrayList<T> sortList(ArrayList<T> list)
	{
		/** Handles the comparison of list items */
		class ItemComparator implements Comparator<Object>
		{
			public int compare(Object item1, Object item2)
			{
				if(item1.getClass()!=item2.getClass()) return -1;
				return item1.toString().compareTo(item2.toString());
			}
		}
		
		Collections.sort(list, new ItemComparator());
		return list;
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
			if(client.updateSchema(schema))
				{ fireSchemaModified(schema); return true; }
		} catch(Exception e) {}
		return false;
	}
	
	/** Extends the specified schema */
	public static Integer extendSchema(Integer schemaID, String name, String author, String description)
	{
		try {
			Schema schema = client.extendSchema(schemaID);
			if(schema==null) return null;
			schema.setName(name); schema.setAuthor(author); schema.setDescription(description);
			if(client.updateSchema(schema)) { fireSchemaAdded(schema); return schema.getId(); }
			else client.deleteSchema(schemaID);
		} catch(Exception e) {}
		return null;
	}
	
	/** Returns the graph for the specified schema */
	public static Graph getGraph(Integer schemaID)
		{ try { return client.getGraph(schemaID); } catch(Exception e) { return null; } }
	
	/** Returns if the schema is deletable */
	public static boolean isDeletable(Integer schemaID)
		{ try { return client.isDeletable(schemaID); } catch(Exception e) { return false; } }
	
	/** Deletes the specified schema */
	public static boolean deleteSchema(Integer schemaID)
	{
		try {
			if(client.deleteSchema(schemaID))
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
	
	//------------ Group Functionality -------------
	
	/** Returns the list of groups */
	public static ArrayList<Group> getGroups()
		{ return new ArrayList<Group>(groups.values()); }

	/** Returns the specified group */
	public static Group getGroup(Integer groupID)
		{ return groups.get(groupID); }
	
	/** Returns the list of subgroups for the specified group */
	public static ArrayList<Group> getSubgroups(Integer groupID)
	{
		try { return client.getSubgroups(groupID); }
		catch(Exception e) { return new ArrayList<Group>(); }
	} 
	
	/** Add group to the repository */
	public static Integer addGroup(Group group)
	{
		try {
			Integer groupID = client.addGroup(group);
			if(groupID!=null)
				{ group.setId(groupID); fireGroupAdded(group); return groupID; }
		} catch(Exception e) {}
		return null;
	}
	
	/** Modifies the group in the repository */
	public static boolean updateGroup(Group group)
	{
		try {
			if(client.updateGroup(group))
				{ fireGroupModified(group); return true; }
		} catch(Exception e) {}
		return false;
	}
	
	/** Removes group from the repository */
	public static boolean deleteGroup(Integer groupID)
	{
		// First move all group schemas to parent group if such a group exists
		Integer parentID = getGroup(groupID).getParentId();
		if(parentID!=null)
		{
			ArrayList<Integer> schemaIDs = getGroupSchemas(groupID);
			schemaIDs.addAll(getGroupSchemas(parentID));
			setGroupSchemas(groupID,new ArrayList<Integer>());
			setGroupSchemas(parentID, schemaIDs);
		}
		
		// Delete the group
		try {
			if(client.deleteGroup(groupID))
				{ fireGroupDeleted(groupID); return true; }
		} catch(Exception e) {}
		return false;
	}

	/** Returns the list of schemas associated with the specified group */
	public static ArrayList<Integer> getGroupSchemas(Integer groupID)
		{ try { return client.getGroupSchemas(groupID); } catch(Exception e) { return new ArrayList<Integer>(); } }

	/** Returns the list of schemas associate with child groups */
	public static ArrayList<Integer> getChildGroupSchemas(Integer groupID)
	{
		ArrayList<Integer> descendantSchemas = new ArrayList<Integer>();
		for(Group subgroup : getSubgroups(groupID))
		{
			descendantSchemas.addAll(getGroupSchemas(subgroup.getId()));
			descendantSchemas.addAll(getChildGroupSchemas(subgroup.getId()));
		}
		return descendantSchemas;
	}
	
	/** Sets the list of schema associated with the specified group */
	public static boolean setGroupSchemas(Integer groupID, ArrayList<Integer> schemaIDs)
	{
		// Only set group schemas if changes have been made
		ArrayList<Integer> oldSchemaIDs = getGroupSchemas(groupID);
		Collections.sort(oldSchemaIDs);
		Collections.sort(schemaIDs);
		if(oldSchemaIDs.equals(schemaIDs)) return true;
		
		// Remove schemas in child groups from list since prohibited from being selected
		schemaIDs.removeAll(getChildGroupSchemas(groupID));
		
		// Remove selected schemas from ancestor groups
		Group parentGroup = getGroup(groupID);
		while(parentGroup.getParentId()!=null)
		{
			parentGroup = getGroup(parentGroup.getParentId());
			ArrayList<Integer> parentSchemaIDs = getGroupSchemas(parentGroup.getId());
			parentSchemaIDs.removeAll(schemaIDs);
			setGroupSchemas(parentGroup.getId(), parentSchemaIDs);
		}
		
		// Add and remove schemas from the group as needed
		try {
			for(Integer oldSchemaID : oldSchemaIDs)
				if(!schemaIDs.contains(oldSchemaID)) client.removeGroupFromSchema(oldSchemaID, groupID);
			for(Integer schemaID : schemaIDs)
				if(!oldSchemaIDs.contains(schemaID)) client.addGroupToSchema(schemaID, groupID);
		} catch(Exception e) { return false; }
		
		// Inform listeners that the group has been modified
		fireGroupModified(getGroup(groupID));
		return true;
	}
	
	/** Inform listeners that group was added */
	public static void fireGroupAdded(Group group)
	{
		groups.put(group.getId(), group);
		for(OpenIIListener listener : listeners.get()) listener.groupAdded(group.getId());
	}

	/** Inform listeners that group was modified */
	public static void fireGroupModified(Group group)
	{
		groups.put(group.getId(), group);
		for(OpenIIListener listener : listeners.get()) listener.groupModified(group.getId()); }

	/** Inform listeners that group was removed */
	public static void fireGroupDeleted(Integer groupID)
	{
		groups.remove(groupID);
		for(OpenIIListener listener : listeners.get()) listener.groupDeleted(groupID);
	}
	
	//------------ Mapping Functionality -------------
	
	/** Returns the list of mappings */
	public static ArrayList<Mapping> getMappings()
		{ try { return client.getMappings(); } catch(Exception e) { return new ArrayList<Mapping>(); } }

	/** Returns the specified mapping */
	public static Mapping getMapping(Integer mappingID)
		{ try { return client.getMapping(mappingID); } catch(Exception e) { return null; } }
	
	/** Returns the specified mapping cells */
	public static ArrayList<MappingCell> getMappingCells(Integer mappingID)
		{ try { return client.getMappingCells(mappingID); } catch(Exception e) { return new ArrayList<MappingCell>(); } }
	
	/** Add mapping to the repository */
	public static Integer addMapping(Mapping mapping)
	{
		try {
			Integer mappingID = client.addMapping(mapping);
			if(mappingID!=null)
				{ mapping.setId(mappingID); fireMappingAdded(mapping); return mappingID; }
		} catch(Exception e) {}
		return null;
	}

	/** Modifies the mapping in the repository */
	public static boolean updateMapping(Mapping mapping)
	{
		try {
			if(client.updateMapping(mapping))
				{ fireMappingModified(mapping.getId()); return true; }
		} catch(Exception e) {}
		return false;
	}
	
	/** Saves the mapping to the repository */
	public static Integer saveMapping(Mapping mapping, ArrayList<MappingCell> mappingCells)
	{
		try {
			Integer mappingID = client.saveMapping(mapping,mappingCells);
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
			if(client.deleteMapping(mappingID))
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
}