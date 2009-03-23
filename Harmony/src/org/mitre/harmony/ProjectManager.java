package org.mitre.harmony;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.mitre.harmony.model.ConfigManager;
import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.mapping.MappingListener;
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
	
	/** Loads the specified item */
	private void load(ObjectInputStream in, String item)
	{
		try
		{
			// Loads the mapping
			if(item.equals("Mapping"))
				harmonyModel.getMappingManager().setMapping((Mapping)in.readObject());
		
			// Loads the mapping cells
			if(item.equals("MappingCells"))
			{
				int mappingCellCount = in.readInt();
				for(int i=0; i<mappingCellCount; i++)
				{
					MappingCell mappingCell = (MappingCell)in.readObject();
					mappingCell.setId(null);
					harmonyModel.getMappingCellManager().setMappingCell(mappingCell);
				}
			}
			
			// Loads if the mapping has been modified since saving to the repository			
			if(item.equals("MappingModified"))
				harmonyModel.getMappingManager().setModified(in.readBoolean());

			// Loads the displayed schemas
			if(item.equals("DisplayedSchemas"))
			{
				ArrayList<Integer> leftSchemas = new ArrayList<Integer>();
				ArrayList<Integer> rightSchemas = new ArrayList<Integer>();
				int leftSchemaCount = in.readInt();
				for(int i=0; i<leftSchemaCount; i++) leftSchemas.add(in.readInt());
				int rightSchemaCount = in.readInt();
				for(int i=0; i<rightSchemaCount; i++) rightSchemas.add(in.readInt());
				harmonyModel.getSelectedInfo().setSelectedSchemas(leftSchemas, rightSchemas);
			}
			
		} catch(Exception e) {}
	}
	
	/** Saves the specified item */
	private void save(ObjectOutputStream out, String item)
	{
		try
		{
			// Save the item label
			out.writeObject(item);
		
			// Saves the mapping
			if(item.equals("Mapping"))
				out.writeObject(harmonyModel.getMappingManager().getMapping());

			// Saves the mapping cells
			if(item.equals("MappingCells"))
			{
				ArrayList<MappingCell> mappingCells = harmonyModel.getMappingCellManager().getMappingCells();
				out.writeInt(mappingCells.size());
				for(MappingCell mappingCell : mappingCells) out.writeObject(mappingCell);
			}

			// Saves if the mapping has been modified since saving to the repository
			if(item.equals("MappingModified"))
				out.writeBoolean(harmonyModel.getMappingManager().isModified());

			// Saves the displayed schemas
			if(item.equals("DisplayedSchemas"))
			{
				ArrayList<Integer> leftSchemas = harmonyModel.getSelectedInfo().getSchemas(HarmonyConsts.LEFT);
				out.writeInt(leftSchemas.size());
				for(Integer leftSchema : leftSchemas) out.writeInt(leftSchema);
				ArrayList<Integer> rightSchemas = harmonyModel.getSelectedInfo().getSchemas(HarmonyConsts.RIGHT);
				out.writeInt(rightSchemas.size());
				for(Integer rightSchema : rightSchemas) out.writeInt(rightSchema);
			}
			
		} catch(IOException e) {}
	}
	
	/** Instantiates the project manager to listen for any changes to the model */
	public ProjectManager(HarmonyModel harmonyModel)
	{
		this.harmonyModel = harmonyModel;
		
		// Get current project information from file
		ObjectInputStream in = null;
		try
		{
			in = new ObjectInputStream(new FileInputStream(projectFile));
			String repository = (String)in.readObject();
			if(repository!=null && repository.equals(ConfigManager.getParm("schemastore")))
				try {
					String item = (String)in.readObject();
					while(1==1) { load(in,item); item = (String)in.readObject(); }
				} catch(EOFException e) {}
		}
		catch(Exception e) { System.out.println("(E) ProjectManager - " + e.getMessage()); }
		if(in != null) try { in.close(); } catch(Exception e) {}

		// If no mapping was assigned, set a default mapping
		if(harmonyModel.getMappingManager().getMapping()==null)
			harmonyModel.getMappingManager().setMapping(new Mapping());
		
		// Monitor for changes to the mapping
		harmonyModel.getMappingManager().addListener(this);	
	}
		
	/** Saves the project to file */
	public void save()
	{
		File tempProjectFile = new File(projectFile.getParentFile(),projectFile.getName()+".tmp");
		try
		{
			// Save all project settings
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(tempProjectFile));			
			out.writeObject(ConfigManager.getParm("schemastore"));
			save(out,"Mapping");
			save(out,"MappingCells");
			save(out,"MappingModified");
			save(out,"DisplayedSchemas");
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
