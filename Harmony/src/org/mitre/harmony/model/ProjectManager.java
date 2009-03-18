package org.mitre.harmony.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;

/** Class for monitoring for changes in the project */
public class ProjectManager implements MappingListener, MappingCellListener {
	/** File used for storing project information */
	static private File projectFile = new File("harmony.project.dat");

	/** Keeps track of if the project has been saved to the repository */
	static private boolean savedToRepository = false;

	/** Tracks the thread used for automatically saving the project */
	static private Thread saveThread = null;

	/** Thread used for automatically saving project */
	static private class SaveThread extends Thread {
		public void run() {
			try {
				Thread.sleep(300000);
				save();
			} catch (Exception e) {}
		}
	}

	/** Instantiates the project manager to listen for any changes to the model */
	public ProjectManager() {
		// Stores project information
		String repository = "";
		Mapping mapping = null;
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();

		// Get current project information from file
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(projectFile));

			// Retrieve repository, mapping, and if saved to repository
			repository = (String) in.readObject();
			mapping = (Mapping) in.readObject();
			savedToRepository = in.readBoolean();

			// Retrieve mapping cells
			int mappingCellCount = in.readInt();
			for (int i = 0; i < mappingCellCount; i++)
				mappingCells.add((MappingCell) in.readObject());
		} catch (Exception e) {}
		if (in != null) try {
			in.close();
		} catch (Exception e) {}

		// Only open mapping if pointing to same repository and repository still
		// contains schemas
		if (repository != null && repository.equals(ConfigManager.getParm("schemastore")) && mapping != null) {
			MappingManager.setMapping(mapping);

			// Set the mapping cells (filtering out all invalid mapping cells)
			for (MappingCell mappingCell : mappingCells) {
				Integer mappingCellID = MappingCellManager.createMappingCell(mappingCell.getElement1(), mappingCell.getElement2());
				MappingCellManager.modifyMappingCell(mappingCellID, mappingCell.getScore(), mappingCell.getAuthor(), mappingCell.getValidated());
			}
		}

		if (mapping == null) MappingManager.setMapping(new Mapping());

		// Add listeners to the project
		MappingManager.addListener(this);
		MappingCellManager.addListener(this);
	}

	/** Saves the project to file */
	static public void save() {
		File tempProjectFile = new File(projectFile.getParentFile(), projectFile.getName() + ".tmp");
		try {
			// Create a temporary file to output to
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(tempProjectFile));

			// Store the repository, mapping, and if saved to repository
			out.writeObject(ConfigManager.getParm("schemastore"));
			out.writeObject(MappingManager.getMapping());
			out.writeBoolean(savedToRepository);

			// Store the mapping cells
			ArrayList<MappingCell> mappingCells = MappingCellManager.getMappingCells();
			out.writeInt(mappingCells.size());
			for (MappingCell mappingCell : mappingCells)
				out.writeObject(mappingCell);
			out.close();

			// Replace the tool file with the new tool file
			projectFile.delete();
			tempProjectFile.renameTo(projectFile);
		} catch (Exception e) {
			System.out.println("(E) ProjectManager.save - " + e.getMessage());
		}

		// Shut down the save thread
		if (saveThread != null) {
			saveThread.interrupt();
			saveThread = null;
		}
	}

	/** Indicates if the project has been saved to repository */
	static public boolean isSavedToRepository() {
		return savedToRepository;
	}

	/** Sets if the project has been saved to repository */
	static public void setSavedToRepository(boolean savedToRepositoryIn) {
		// Fire off the automatic save routine if needed
		if (saveThread == null) {
			saveThread = new SaveThread();
			saveThread.start();
		}

		// Set the savedToRepository flag as needed
		savedToRepository = savedToRepositoryIn;
	}

	// Monitors for any modifications to the model
	public void mappingModified() {
		setSavedToRepository(false);
	}

	public void mappingCellAdded(MappingCell mappingCell) {
		setSavedToRepository(false);
	}

	public void mappingCellModified(MappingCell oldMappingCell, MappingCell newMappingCell) {
		setSavedToRepository(false);
	}

	public void mappingCellRemoved(MappingCell mappingCell) {
		setSavedToRepository(false);
	}

	// Unused listener events
	public void schemaAdded(Integer schemaID) {}

	public void schemaRemoved(Integer schemaID) {}
}
