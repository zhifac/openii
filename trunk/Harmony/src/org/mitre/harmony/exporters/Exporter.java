// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.exporters;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileFilter;

import org.mitre.harmony.model.HarmonyModel;

/** Export Class - An exporter enables the exporting of projects */
public abstract class Exporter
{
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Constructs the exporter */
	public Exporter(HarmonyModel harmonyModel)
		{ this.harmonyModel = harmonyModel; }
	
	/** Returns the Harmony model */
	protected HarmonyModel getModel()
		{ return harmonyModel; }
	
	/** Return the file type available for use with this exporter */
	abstract public String getFileType();
	
	/** Return a file filter for accessing files capable of running through this exporter */
	abstract public FileFilter getFileFilter();

	/** Exports the project to the specified file */
	abstract public void exportTo(File f) throws IOException;
}