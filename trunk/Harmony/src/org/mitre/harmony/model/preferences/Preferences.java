// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.harmony.model.ConfigManager;
import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.ListenerGroup;
import org.mitre.harmony.model.MappingListener;
import org.mitre.harmony.model.MappingManager;
import org.mitre.harmony.model.SchemaManager;
import org.mitre.schemastore.model.graph.GraphModel;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 * Tracks preferences used in Harmony
 * @author CWOLF
 */
public class Preferences implements MappingListener
{	
	/** Stores if the various schema elements have been marked as finished */
	static private HashMap<Integer,HashSet<Integer>> finishedElementMap = new HashMap<Integer,HashSet<Integer>>();
	
	/** Stores preference listeners */
	static private ListenerGroup<PreferencesListener> listeners = new ListenerGroup<PreferencesListener>();

	/** Constructor used to monitor changes that might affect the selected info */
	private Preferences()
	{
		// Eliminate graph model preferences for schemas that are no longer in the mapping
		ArrayList<Integer> schemaIDs = MappingManager.getSchemas();
		for(Integer schemaID : ConfigManager.getHashMap("preferences.graphModels").keySet())
			if(!schemaIDs.contains(schemaID))
				setSchemaGraphModel(schemaID, null);
		
		// Only set finished element preferences for schemas that exist in the mapping
		HashMap<Integer,String> map = ConfigManager.getHashMap("preferences.finished");
		for(Integer schemaID : map.keySet())
			if(schemaIDs.contains(schemaID))
			{
				// Format the element list
				String list = map.get(schemaID);
				list = list.substring(1,list.length()-1);

				// Set the listed elements to the finished state
				HashSet<Integer> elementIDs = new HashSet<Integer>();
				for(String element : list.split(","))
					try { elementIDs.add(Integer.valueOf(element.trim())); } catch(Exception e) {}
				setFinished(schemaID, elementIDs, true);
			}
		
		// Add listeners to trigger changes to the preferences
		MappingManager.addListener(this);
	}
	static { new Preferences(); }

	/** Remove the specified graph model when a schema is removed */
	public void schemaRemoved(Integer schemaID)
	{
		// Remove the graph model assigned to the removed schema
		setSchemaGraphModel(schemaID,null);
		
		// Remove all "finished element" preferences associated with the removed schema
		HashSet<Integer> finishedElements = finishedElementMap.get(schemaID);
		if(finishedElements!=null)
			setFinished(schemaID, finishedElements, false);
	}
	
	// Unused action listeners
	public void mappingModified() {}
	public void schemaAdded(Integer schemaID) {}
	
	/** Adds a preference listener */
	static public void addListener(PreferencesListener listener) { listeners.add(listener); }
	
	// ------------- Preference for view to be displayed ------------

	/** Returns the preference for view to be displayed */
	static public Integer getViewToDisplay()
		{ try { return Integer.parseInt(ConfigManager.getParm("preferences.displayedView")); } catch(Exception e) {} return HarmonyConsts.MAPPING_VIEW; }
	
	/** Set preference to view to be displayed */
	static public void setViewToDisplay(Integer view)
	{
		// Only set preference if changed from original
		if(view!=getViewToDisplay())
		{
			ConfigManager.setParm("preferences.displayedView",Integer.toString(view));
			for(PreferencesListener listener : listeners.get())
				listener.displayedViewChanged();
		}
	}
	
	// ------------- Preference for if schema types should be shown ------------

	/** Returns the preference for if schema types should be displayed */
	static public boolean getShowSchemaTypes()
		{ try { return Boolean.parseBoolean(ConfigManager.getParm("preferences.showSchemaTypes")); } catch(Exception e) {} return false; }
	
	/** Set preference to show schema types */
	static public void setShowSchemaTypes(boolean newShowSchemaTypes)
	{
		// Only set preference if changed from original
		if(newShowSchemaTypes!=getShowSchemaTypes())
		{
			ConfigManager.setParm("preferences.showSchemaTypes",Boolean.toString(newShowSchemaTypes));
			for(PreferencesListener listener : listeners.get())
				listener.showSchemaTypesChanged();
		}
	}

	// ------------- Preferences for storing the import and export directories -------------
	
	/** Returns the import directory */
	static public File getImportDir()
		{ try { return new File(ConfigManager.getParm("preferences.importDir")); } catch(Exception e) {} return new File("."); }

	/** Returns the export directory */
	static public File getExportDir()
		{ try { return new File(ConfigManager.getParm("preferences.exportDir")); } catch(Exception e) {} return new File("."); }

	/** Set current directory used for importing projects */
	static public void setImportDir(File importDir)
	{
		// Only set preference if changed from original
		if(!importDir.equals(getImportDir()))
			ConfigManager.setParm("preferences.importDir",importDir.getPath());
	}
	
	/** Set current directory used for exporting projects */
	static public void setExportDir(File exportDir)
	{
		// Only set preference if changed from original
		if(!exportDir.equals(getExportDir()))
			ConfigManager.setParm("preferences.exportDir",exportDir.getPath());
	}

	// ------------- Preference for storing the schema graph models -------------
	
	/** Returns the graph model for the specified schema */
	static public GraphModel getSchemaGraphModel(Integer schemaID)
	{
		HashMap<Integer,String> hashMap = ConfigManager.getHashMap("preferences.graphModels");
		String model = hashMap.get(schemaID);
		for(GraphModel graphModel : HierarchicalGraph.getGraphModels())
			if(graphModel.getName().equals(model))
				return graphModel;
		return null;
	}
	
	/** Set the graph model for the specified schema */
	static public void setSchemaGraphModel(Integer schemaID, GraphModel model)
	{
		HashMap<Integer,String> hashMap = ConfigManager.getHashMap("preferences.graphModels");
		String currModel = hashMap.get(schemaID);
		if((model==null && currModel!=null) || (model!=null && !model.getName().equals(currModel)))
		{
			if(model!=null)
				hashMap.put(schemaID,model.getName());
			else hashMap.remove(schemaID);
			ConfigManager.setParm("preferences.graphModels",hashMap.toString());
			SchemaManager.getGraph(schemaID).setModel(model);
			for(PreferencesListener listener : listeners.get())
				listener.schemaGraphModelChanged(schemaID);
		}
	}

	// ------------- Preference for tracking which schema elements are marked as finished -------------
	
	/** Returns if the specified schema element is marked as finished */
	static public boolean isFinished(Integer schemaID, Integer elementID)
	{
		HashSet<Integer> finishedElements = finishedElementMap.get(schemaID);
		return finishedElements!=null && finishedElements.contains(elementID);
	}
	
	/** Returns the finished elements for the specified schema */
	static public ArrayList<Integer> getFinishedElements(Integer schemaID)
	{
		HashSet<Integer> finishedElements = finishedElementMap.get(schemaID);
		return finishedElements==null ? new ArrayList<Integer>() : new ArrayList<Integer>(finishedElements);
	}
	
	/** Sets the flag indicating if analysis of the schema element is finished */
	static public void setFinished(Integer schemaID, HashSet<Integer> elementIDs, boolean finished)
	{
		// Retrieve the list of finished elements from the map
		HashSet<Integer> finishedElements = finishedElementMap.get(schemaID);
		if(finishedElements==null) finishedElementMap.put(schemaID, finishedElements = new HashSet<Integer>());

		// Eliminate all elements which aren't really changing state
		for(Integer elementID : new ArrayList<Integer>(elementIDs))
			if(finishedElements.contains(elementID)==finished)
				elementIDs.remove(elementID);
		
		// Update the list of finished items
		if(finished) finishedElements.addAll(elementIDs);
		else finishedElements.removeAll(elementIDs);
		ConfigManager.setParm("preferences.finished",finishedElementMap.toString());
		
		// Inform listeners to the changes made to preferences
		for(PreferencesListener listener : listeners.get())
		{
			if(finished) listener.elementsMarkedAsFinished(schemaID, elementIDs);
			else listener.elementsMarkedAsUnfinished(schemaID, elementIDs);
		}
	}
}