package org.mitre.harmony;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.mitre.harmony.model.ConfigManager;
import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.mapping.MappingCellManager;
import org.mitre.harmony.model.mapping.MappingListener;
import org.mitre.harmony.model.mapping.MappingManager;
import org.mitre.harmony.model.selectedInfo.SelectedInfoManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;

/** Class for monitoring for changes in the project */
public class ProjectManager implements MappingListener
{
	/** File used for storing project information */
	private File projectFile = new File("harmony.project.dat");
	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Tracks the thread used for automatically saving the project */
	private Thread saveThread = null;
	
	/** Thread used for automatically saving project */
	private class SaveThread extends Thread
	{
		public void run()
		{
			try {
				Thread.sleep(300000);
				save();
			 } catch(Exception e) {}
		}
	}
	
	/** Instantiates the project manager to listen for any changes to the model */
	public ProjectManager(HarmonyModel harmonyModel)
	{
		this.harmonyModel = harmonyModel;
		
		// Get the mapping model and mapping cell manager
		MappingManager mappingManager = harmonyModel.getMappingManager();
		MappingCellManager mappingCellManager = harmonyModel.getMappingCellManager();
		
		// Stores project information
		String repository = "";
		Mapping mapping = null;
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		ArrayList<Integer> leftSchemas = new ArrayList<Integer>();
		ArrayList<Integer> rightSchemas = new ArrayList<Integer>();
		
		// Get current project information from file
		ObjectInputStream in = null;
		try
		{
			in = new ObjectInputStream(new FileInputStream(projectFile));

			// Retrieve repository, mapping, and if saved to repository
			repository = (String)in.readObject();
			mapping = (Mapping)in.readObject();
			mappingManager.setModified(in.readBoolean());
			
			// Retrieve mapping cells
			int mappingCellCount = in.readInt();
			for(int i=0; i<mappingCellCount; i++)
				mappingCells.add((MappingCell)in.readObject());

			// Retrieves the displayed schemas
			int leftSchemaCount = in.readInt();
			for(int i=0; i<leftSchemaCount; i++) leftSchemas.add(in.readInt());
			int rightSchemaCount = in.readInt();
			for(int i=0; i<rightSchemaCount; i++) rightSchemas.add(in.readInt());
		}
		catch(Exception e) {}
		if(in != null) try { in.close(); } catch(Exception e) {}
		
		// Only open mapping if pointing to same repository and repository still contains schemas
		if(repository!=null && repository.equals(ConfigManager.getParm("schemastore")))
		{
			mappingManager.setMapping(mapping);

			// Set the mapping cells (filtering out all invalid mapping cells)
			for(MappingCell mappingCell : mappingCells)
			{
				mappingCell.setId(null);
				mappingCellManager.setMappingCell(mappingCell);
			}
			
			// Set the displayed schemas
			harmonyModel.getSelectedInfo().setSelectedSchemas(leftSchemas, rightSchemas);
		}
		if(mapping==null) mappingManager.setMapping(new Mapping());
		
		// Monitor for changes to the mapping
		mappingManager.addListener(this);	
	}
	
	/** Saves the project to file */
	public void save()
	{
		File tempProjectFile = new File(projectFile.getParentFile(),projectFile.getName()+".tmp");
		try
		{
			// Create a temporary file to output to
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(tempProjectFile));
			
			// Store the repository, mapping, and if saved to repository
			out.writeObject(ConfigManager.getParm("schemastore"));
			out.writeObject(harmonyModel.getMappingManager().getMapping());
			out.writeBoolean(harmonyModel.getMappingManager().isModified());
			
			// Store the mapping cells
			ArrayList<MappingCell> mappingCells = harmonyModel.getMappingCellManager().getMappingCells();
			out.writeInt(mappingCells.size());
			for(MappingCell mappingCell : mappingCells)
				out.writeObject(mappingCell);
			
			// Store the schemas displayed on the left
			ArrayList<Integer> leftSchemas = harmonyModel.getSelectedInfo().getSchemas(HarmonyConsts.LEFT);
			out.writeInt(leftSchemas.size());
			for(Integer leftSchema : leftSchemas) out.writeInt(leftSchema);

			// Stores the schemas displayed on the right
			ArrayList<Integer> rightSchemas = harmonyModel.getSelectedInfo().getSchemas(HarmonyConsts.RIGHT);
			out.writeInt(rightSchemas.size());
			for(Integer rightSchema : rightSchemas) out.writeInt(rightSchema);
			out.close();			
			
			// Replace the tool file with the new tool file
			projectFile.delete();
			tempProjectFile.renameTo(projectFile);			
		}
		catch(Exception e) { System.out.println("(E) ProjectManager.save - " + e.getMessage()); }
		
		// Shut down the save thread
		if (saveThread != null)
		{
			saveThread.interrupt();
			saveThread = null;
		}
	}
	
	// Saves the mapping whenever modifications are made (for fast recall)
	public void mappingModified()
	{
		if(saveThread==null)
		{
			saveThread = new SaveThread();
			saveThread.start();
		}
	}

	// Unused listener events
	public void schemaAdded(Integer schemaID) {}
	public void schemaRemoved(Integer schemaID) {}
}
