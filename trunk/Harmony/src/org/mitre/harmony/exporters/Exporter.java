// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.exporters;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileFilter;

/** Export Interface - An exporter enables the exporting of projects */
public interface Exporter
{
	/** Return the file type available for use with this exporter */
	public String getFileType();
	
	/** Return a file filter for accessing files capable of running through this exporter */
	public FileFilter getFileFilter();

	/** Exports the project to the specified file */
	public void exportTo(File f) throws IOException;
}