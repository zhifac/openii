package org.mitre.openii.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.implus.IMPLUSListener;
import org.mitre.openii.model.controllers.MappingCellMerger;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Function;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Tag;
import org.mitre.schemastore.model.Vocabulary;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.porters.Porter;
import org.mitre.schemastore.porters.PorterManager.PorterType;

/**
 * Generates the schema store connection for use by all components
 * 
 * @author CWOLF
 */
public class OpenIIManager {
	/**
	 * If true, IM-PLUS will be activated and provenance will be captured on all
	 * actions. If false, that code is disabled.
	 */
	static public boolean CAPTURE_PROVENANCE = false;

	/** Caches the list of schemas currently available */
	static private HashMap<Integer, Schema> schemas = null;

	/** Caches the list of tags currently available */
	static private HashMap<Integer, Tag> tags = null;

	/** Stores listeners to the OpenII Manager */
	static private ListenerGroup<OpenIIListener> listeners = new ListenerGroup<OpenIIListener>();

	/** Initializes this class */
	static {
		if (CAPTURE_PROVENANCE)
			addListener(IMPLUSListener.primary);
		reset();
	} // End static

	/** Resets the OpenII caches */
	static public void reset() {
		// Initialize the list of schemas
		try {
			schemas = new HashMap<Integer, Schema>();
			for (Schema schema : RepositoryManager.getClient().getSchemas())
				schemas.put(schema.getId(), schema);
		} catch (Exception e) {
		}

		// Initialize the list of tags
		try {
			tags = new HashMap<Integer, Tag>();
			for (Tag tag : RepositoryManager.getClient().getTags())
				tags.put(tag.getId(), tag);
		} catch (Exception e) {
		}

		// Informs listeners that the OpenII repository has been reset
		for (OpenIIListener listener : listeners.get())
			listener.repositoryReset();
	}

	/** Handles the adding and removal of listeners */
	static public void addListener(OpenIIListener listener) {
		listeners.add(listener);
	}

	static public void removeListener(OpenIIListener listener) {
		listeners.remove(listener);
	}

	// ------------ OpenII Settings --------------------

	/** Returns the active directory */
	public static String getActiveDir() {
		return OpenIIActivator.getDefault().getPreferenceStore().getString(
				"ActiveDirectory");
	}

	/** Sets the active directory */
	public static void setActiveDir(String activeDir) {
		ScopedPreferenceStore preferences = (ScopedPreferenceStore) OpenIIActivator
				.getDefault().getPreferenceStore();
		preferences.setValue("ActiveDirectory", activeDir);
		try {
			preferences.save();
		} catch (Exception e) {
			System.out
					.println("(E)OpenIIManager.setActiveDir - Failed to save the active directory setting");
		}
	}

	/** Returns the current porter preference */
	public static Class<?> getPorterPreference(PorterType porterType) {
		String porterClass = OpenIIActivator.getDefault().getPreferenceStore()
				.getString(porterType.name());
		try {
			return Class.forName(porterClass);
		} catch (Exception e) {
			return null;
		}
	}

	/** Sets the porter preference */
	public static void setPorterPreference(PorterType porterType, Porter porter) {
		ScopedPreferenceStore preferences = (ScopedPreferenceStore) OpenIIActivator
				.getDefault().getPreferenceStore();
		preferences.setValue(porterType.name(), porter.getClass().getName());
		try {
			preferences.save();
		} catch (Exception e) {
			System.out
					.println("(E)OpenIIManager.setPorterPreference - Failed to save the setting for the selected porter");
		}
	}

	// ------------ Schema Functionality -------------

	/** Returns the list of schemas */
	public static ArrayList<Schema> getSchemas() {
		return new ArrayList<Schema>(schemas.values());
	}

	/** Create a new schema */
	public static Integer addSchema(Schema schema) {
		Integer newId = null;
		try {
			newId = RepositoryManager.getClient().addSchema(schema);
			if (newId != null)
				fireSchemaAdded(schema);
		} catch (Exception e) {
		}
		return newId;
	}

	/** Returns the list of schema IDs */
	public static ArrayList<Integer> getSchemaIDs() {
		return new ArrayList<Integer>(schemas.keySet());
	}

	/** Returns the specified schema */
	public static Schema getSchema(Integer schemaID) {
		return schemas.get(schemaID);
	}

	/** Modifies the schema in the repository */
	public static boolean updateSchema(Schema schema) {
		try {
			if (RepositoryManager.getClient().updateSchema(schema)) {
				fireSchemaModified(schema);
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/** Extends the specified schema */
	public static Integer extendSchema(Integer schemaID, String name,
			String author, String description) {
		try {
			Schema schema = RepositoryManager.getClient()
					.extendSchema(schemaID);
			if (schema == null)
				return null;
			schema.setName(name);
			schema.setAuthor(author);
			schema.setDescription(description);
			if (RepositoryManager.getClient().updateSchema(schema)) {
				fireSchemaAdded(schema);
				return schema.getId();
			} else
				RepositoryManager.getClient().deleteSchema(schemaID);
		} catch (Exception e) {
		}
		return null;
	}

	/** Returns the schema info for the specified schema */
	public static SchemaInfo getSchemaInfo(Integer schemaID) {
		try {
			return RepositoryManager.getClient().getSchemaInfo(schemaID);
		} catch (Exception e) {
			return null;
		}
	}

	/** Returns the schema parents */
	public static ArrayList<Integer> getParentSchemas(Integer schemaID) {
		try {
			return RepositoryManager.getClient().getParentSchemas(schemaID);
		} catch (Exception e) {
			return new ArrayList<Integer>();
		}
	}

	/** Returns the schema children */
	public static ArrayList<Integer> getChildrenSchemas(Integer schemaID) {
		try {
			return RepositoryManager.getClient().getChildSchemas(schemaID);
		} catch (Exception e) {
			return new ArrayList<Integer>();
		}
	}

	/** Returns the schema ancestors */
	public static ArrayList<Integer> getAncestorSchemas(Integer schemaID) {
		try {
			return RepositoryManager.getClient().getAncestorSchemas(schemaID);
		} catch (Exception e) {
			return new ArrayList<Integer>();
		}
	}

	/** Returns if the schema is deletable */
	public static boolean isDeletable(Integer schemaID) {
		try {
			return RepositoryManager.getClient().isDeletable(schemaID);
		} catch (Exception e) {
			return false;
		}
	}

	/** Deletes the specified schema */
	public static boolean deleteSchema(Integer schemaID) {
		try {
			if (RepositoryManager.getClient().deleteSchema(schemaID)) {
				fireSchemaDeleted(schemaID);
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/** Inform listeners that schema was added */
	public static void fireSchemaAdded(Schema schema) {
		schemas.put(schema.getId(), schema);
		for (OpenIIListener listener : listeners.get())
			listener.schemaAdded(schema.getId());
	}

	/** Inform listeners that schema was modified */
	public static void fireSchemaModified(Schema schema) {
		schemas.put(schema.getId(), schema);
		for (OpenIIListener listener : listeners.get())
			listener.schemaModified(schema.getId());
	}

	/** Inform listeners that schema was removed */
	public static void fireSchemaDeleted(Integer schemaID) {
		schemas.remove(schemaID);
		for (OpenIIListener listener : listeners.get())
			listener.schemaDeleted(schemaID);
	}

	// ------------ Tag Functionality -------------

	/** Returns the list of tags */
	public static ArrayList<Tag> getTags() {
		return new ArrayList<Tag>(tags.values());
	}

	/** Returns the specified tag */
	public static Tag getTag(Integer tagID) {
		return tags.get(tagID);
	}

	/** Returns the list of subcategories for the specified tag */
	public static ArrayList<Tag> getSubcategories(Integer tagID) {
		try {
			return RepositoryManager.getClient().getSubcategories(tagID);
		} catch (Exception e) {
			return new ArrayList<Tag>();
		}
	}

	/** Add tag to the repository */
	public static Integer addTag(Tag tag) {
		try {
			Integer tagID = RepositoryManager.getClient().addTag(tag);
			if (tagID != null) {
				tag.setId(tagID);
				fireTagAdded(tag);
				return tagID;
			}
		} catch (Exception e) {
		}
		return null;
	}

	/** Modifies the tag in the repository */
	public static boolean updateTag(Tag tag) {
		try {
			if (RepositoryManager.getClient().updateTag(tag)) {
				fireTagModified(tag);
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/** Removes tag from the repository */
	public static boolean deleteTag(Integer tagID) {
		// First move all tag schemas to parent tag if such a tag exists
		Integer parentID = getTag(tagID).getParentId();
		if (parentID != null) {
			ArrayList<Integer> schemaIDs = getTagSchemas(tagID);
			schemaIDs.addAll(getTagSchemas(parentID));
			setTagSchemas(tagID, new ArrayList<Integer>());
			setTagSchemas(parentID, schemaIDs);
		}

		// Delete the tag
		try {
			if (RepositoryManager.getClient().deleteTag(tagID)) {
				fireTagDeleted(tagID);
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/** Returns the list of schemas associated with the specified tag */
	public static ArrayList<Integer> getTagSchemas(Integer tagID) {
		try {
			return RepositoryManager.getClient().getTagSchemas(tagID);
		} catch (Exception e) {
			return new ArrayList<Integer>();
		}
	}

	/** Returns the list of schemas associate with child tags */
	public static ArrayList<Integer> getChildTagSchemas(Integer tagID) {
		ArrayList<Integer> descendantSchemas = new ArrayList<Integer>();
		for (Tag subcategory : getSubcategories(tagID)) {
			descendantSchemas.addAll(getTagSchemas(subcategory.getId()));
			descendantSchemas.addAll(getChildTagSchemas(subcategory.getId()));
		}
		return descendantSchemas;
	}

	/** Sets the list of schema associated with the specified tag */
	public static boolean setTagSchemas(Integer tagID,
			ArrayList<Integer> schemaIDs) {
		// Only set tag schemas if changes have been made
		ArrayList<Integer> oldSchemaIDs = getTagSchemas(tagID);
		Collections.sort(oldSchemaIDs);
		Collections.sort(schemaIDs);
		if (oldSchemaIDs.equals(schemaIDs))
			return true;

		// Remove schemas in child agst from list since prohibited from being
		// selected
		schemaIDs.removeAll(getChildTagSchemas(tagID));

		// Remove selected schemas from ancestor tags
		Tag parentTag = getTag(tagID);
		while (parentTag.getParentId() != null) {
			parentTag = getTag(parentTag.getParentId());
			ArrayList<Integer> parentSchemaIDs = getTagSchemas(parentTag
					.getId());
			parentSchemaIDs.removeAll(schemaIDs);
			setTagSchemas(parentTag.getId(), parentSchemaIDs);
		}

		// Add and remove schemas from the tag as needed
		try {
			for (Integer oldSchemaID : oldSchemaIDs)
				if (!schemaIDs.contains(oldSchemaID))
					RepositoryManager.getClient().removeTagFromSchema(
							oldSchemaID, tagID);
			for (Integer schemaID : schemaIDs)
				if (!oldSchemaIDs.contains(schemaID))
					RepositoryManager.getClient().addTagToSchema(schemaID,
							tagID);
		} catch (Exception e) {
			return false;
		}

		// Inform listeners that the tag has been modified
		fireTagModified(getTag(tagID));
		return true;
	}

	/** Inform listeners that tag was added */
	public static void fireTagAdded(Tag tag) {
		tags.put(tag.getId(), tag);
		for (OpenIIListener listener : listeners.get())
			listener.tagAdded(tag.getId());
	}

	/** Inform listeners that tag was modified */
	public static void fireTagModified(Tag tag) {
		tags.put(tag.getId(), tag);
		for (OpenIIListener listener : listeners.get())
			listener.tagModified(tag.getId());
	}

	/** Inform listeners that tag was removed */
	public static void fireTagDeleted(Integer tagID) {
		tags.remove(tagID);
		for (OpenIIListener listener : listeners.get())
			listener.tagDeleted(tagID);
	}

	// ------------ Project Functionality -------------

	/** Returns the list of projects */
	public static ArrayList<Project> getProjects() {
		try {
			return RepositoryManager.getClient().getProjects();
		} catch (Exception e) {
			return new ArrayList<Project>();
		}
	}

	/** Returns the specified project */
	public static Project getProject(Integer projectID) {
		try {
			return RepositoryManager.getClient().getProject(projectID);
		} catch (Exception e) {
			return null;
		}
	}

	/** Add project to the repository */
	public static Integer addProject(Project project) {
		try {
			Integer projectID = RepositoryManager.getClient().addProject(
					project);
			if (projectID != null) {
				project.setId(projectID);
				fireProjectAdded(project);
				return projectID;
			}
		} catch (Exception e) {
		}
		return null;
	}

	/** Modifies the project in the repository */
	public static boolean updateProject(Project project) {
		try {
			if (RepositoryManager.getClient().updateProject(project)) {
				fireProjectModified(project.getId());
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/** Deletes the specified project */
	public static boolean deleteProject(Integer projectID) {
		try {
			if (RepositoryManager.getClient().deleteProject(projectID)) {
				fireProjectDeleted(projectID);
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/** Merges the specified projects */
	public static Integer mergeProjects(String name, String description,
			String author, ArrayList<Project> projects) {
		Integer mergedProjectID = null;
		try {
			// Gather up the list of schemas and mapping cells in the merged
			// project
			HashSet<ProjectSchema> schemas = new HashSet<ProjectSchema>();
			HashMap<String, ArrayList<MappingCell>> mappings = new HashMap<String, ArrayList<MappingCell>>();
			for (Project project : projects) {
				// Add schemas to the schema list
				schemas.addAll(Arrays.asList(project.getSchemas()));

				// Add mapping cells to the mapping list
				for (Mapping mapping : OpenIIManager.getMappings(project
						.getId())) {
					String key = mapping.getSourceId() + "_"
							+ mapping.getTargetId();
					ArrayList<MappingCell> mappingCells = mappings.get(key);
					if (mappingCells == null)
						mappings.put(key,
								mappingCells = new ArrayList<MappingCell>());
					mappingCells.addAll(OpenIIManager.getMappingCells(mapping
							.getId()));
				}
			}

			// Handles the creation of the project
			Project mergedProject = new Project(null, name, description,
					author, schemas.toArray(new ProjectSchema[0]));
			mergedProjectID = RepositoryManager.getClient().addProject(
					mergedProject);
			if (mergedProjectID == null)
				throw new Exception();

			// Handles the creation of the mappings
			for (String key : mappings.keySet()) {
				// Stores the mapping
				Integer sourceID = Integer.parseInt(key.replaceAll("_.*", ""));
				Integer targetID = Integer.parseInt(key.replaceAll(".*_", ""));
				Mapping mapping = new Mapping(null, mergedProjectID, sourceID,
						targetID);
				Integer mappingID = RepositoryManager.getClient().addMapping(
						mapping);
				if (mappingID == null)
					throw new Exception();

				// Stores the mapping cells
				ArrayList<MappingCell> mappingCells = mappings.get(key);
				mappingCells = MappingCellMerger.merge(mappingCells);
				if (!OpenIIManager.saveMappingCells(mappingID, mappingCells))
					throw new Exception();
			}

			// Inform others that the projects have been merged
			ArrayList<Integer> projectIDs = new ArrayList<Integer>();
			for (Project project : projects)
				projectIDs.add(project.getId());
			fireProjectsMerged(projectIDs, mergedProjectID);
			return mergedProjectID;

		} catch (Exception e) {
			if (mergedProjectID != null)
				try {
					RepositoryManager.getClient()
							.deleteProject(mergedProjectID);
				} catch (Exception e2) {
				}
		}
		return null;
	}

	/** Inform listeners that project was added */
	public static void fireProjectAdded(Project project) {
		for (OpenIIListener listener : listeners.get())
			listener.projectAdded(project.getId());
	}

	/** Inform listeners that project was modified */
	public static void fireProjectModified(Integer projectID) {
		for (OpenIIListener listener : listeners.get())
			listener.projectModified(projectID);
	}

	/** Inform listeners that project was removed */
	public static void fireProjectDeleted(Integer projectID) {
		for (OpenIIListener listener : listeners.get())
			listener.projectDeleted(projectID);
	}

	/** Inform listeners that projects were merged */
	public static void fireProjectsMerged(ArrayList<Integer> projectIDs,
			Integer mergedProjectID) {
		for (OpenIIListener listener : listeners.get())
			listener.projectsMerged(projectIDs, mergedProjectID);
	}

	// ------------ Mapping Functionality -------------

	/** Returns the list of mappings */
	public static ArrayList<Mapping> getMappings(Integer projectID) {
		try {
			return RepositoryManager.getClient().getMappings(projectID);
		} catch (Exception e) {
			return new ArrayList<Mapping>();
		}
	}

	/** Returns the specified mapping */
	public static Mapping getMapping(Integer mappingID) {
		try {
			return RepositoryManager.getClient().getMapping(mappingID);
		} catch (Exception e) {
			return null;
		}
	}

	/** Add mapping to the repository */
	public static Integer addMapping(Mapping mapping) {
		try {
			Integer mappingID = RepositoryManager.getClient().addMapping(
					mapping);
			if (mappingID != null) {
				mapping.setId(mappingID);
				fireMappingAdded(mapping);
				return mappingID;
			}
		} catch (Exception e) {
		}
		return null;
	}

	/** Deletes the specified mapping */
	public static boolean deleteMapping(Integer mappingID) {
		try {
			if (RepositoryManager.getClient().deleteMapping(mappingID)) {
				fireMappingDeleted(mappingID);
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/** Returns the specified mapping cells */
	public static ArrayList<MappingCell> getMappingCells(Integer mappingID) {
		try {
			return RepositoryManager.getClient().getMappingCells(mappingID);
		} catch (Exception e) {
			return new ArrayList<MappingCell>();
		}
	}

	/** Saves mapping cells to the repository */
	public static boolean saveMappingCells(Integer mappingID,
			ArrayList<MappingCell> mappingCells) {
		try {
			if (RepositoryManager.getClient().saveMappingCells(mappingID,
					mappingCells)) {
				fireMappingModified(mappingID);
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/** Inform listeners that mapping was added */
	public static void fireMappingAdded(Mapping mapping) {
		for (OpenIIListener listener : listeners.get())
			listener.mappingAdded(mapping.getId());
	}

	/** Inform listeners that mapping was modified */
	public static void fireMappingModified(Integer mappingID) {
		for (OpenIIListener listener : listeners.get())
			listener.mappingModified(mappingID);
	}

	/** Inform listeners that mapping was removed */
	public static void fireMappingDeleted(Integer mappingID) {
		for (OpenIIListener listener : listeners.get())
			listener.mappingDeleted(mappingID);
	}

	// ------------ Vocabulary Functionality -------------

	/** Indicates if the project has a vocabulary */
	public static boolean hasVocabulary(Integer projectID) {
		try {
			return RepositoryManager.getClient().hasVocabulary(projectID);
		} catch (Exception e) {
			return false;
		}
	}

	/** Retrieves the vocabulary for the specified project */
	public static Vocabulary getVocabulary(Integer projectID) {
		try {
			return RepositoryManager.getClient().getVocabulary(projectID);
		} catch (Exception e) {
			return null;
		}
	}

	/** Deletes the vocabulary for the specified project */
	public static boolean deleteVocabulary(Integer projectID) {
		try {
			if (RepositoryManager.getClient().deleteVocabulary(projectID)) {
				fireMappingDeleted(projectID);
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/** Save the  specified vocabulary */
	public static boolean saveVocabulary(Vocabulary vocabulary) {
		try {
			RepositoryManager.getClient().saveVocabulary(vocabulary);
			fireVocabularyModified(vocabulary.getProjectID()); 
			return true;
		} catch (RemoteException e) {	
		}
		return false;
	}

	/** Inform listeners that the vocabulary was saved */
	public static void fireVocabularyModified(Integer projectID) {
		for (OpenIIListener listener : listeners.get())
			listener.vocabularyModified(projectID);
	}

	/** Inform listeners that the vocabulary was removed */
	public static void fireVocabularyDeleted(Integer projectID) {
		for (OpenIIListener listener : listeners.get())
			listener.vocabularyDeleted(projectID);
	}

	// ------------ Function Functionality -------------

	public static Function getFunction(Integer functionID) {
		try {
			return RepositoryManager.getClient().getFunction(functionID);
		} catch (Exception e) {
			return null;
		}
	}

	// ------------ Data Source Functionality -------------

	/** Returns the data sources for the specified schema */
	public static ArrayList<DataSource> getDataSources(Integer schemaID) {
		try {
			return RepositoryManager.getClient().getDataSources(schemaID);
		} catch (Exception e) {
			return new ArrayList<DataSource>();
		}
	}

	/** Deletes the specified data source */
	public static boolean deleteDataSource(Integer dataSourceID) {
		try {
			if (RepositoryManager.getClient().deleteDataSource(dataSourceID)) {
				fireDataSourceDeleted(dataSourceID);
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/** Inform listeners that data source was added */
	public static void fireDataSourceAdded(Integer dataSourceID) {
		for (OpenIIListener listener : listeners.get())
			listener.dataSourceAdded(dataSourceID);
	}

	/** Inform listeners that data source was removed */
	public static void fireDataSourceDeleted(Integer dataSourceID) {
		for (OpenIIListener listener : listeners.get())
			listener.dataSourceDeleted(dataSourceID);
	}
}