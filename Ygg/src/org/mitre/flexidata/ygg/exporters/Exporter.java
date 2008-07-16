// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.exporters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/** Abstract Exporter class */
public abstract class Exporter
{
	/** Returns the exporter name */
	abstract public String getName();
	
	/** Returns the exporter description */
	abstract public String getDescription();
	
	/** Returns the exporter file type */
	abstract public String getFileType();

	/** Returns the file filter associated with this importer */
	public FileFilter getFileFilter()
	{
		final class ImporterFileFilter extends FileFilter
		{
			/** Indicates if the file is acceptable */
			public boolean accept(File file)
			{
				if(file.isDirectory()) return true;
				if(file.getName().endsWith(getFileType())) return true;
				return false;
			}
			
			/** Provides a description of what constitutes an acceptable file */
			public String getDescription()
			{
				String description = getName() + "(" + getFileType() + ")";
				return description.replaceAll(",$",")");
			}
		}
		return new ImporterFileFilter();
	}
	
	/** Outputs the exporter name */
	public String toString()
		{ return getName(); }
}
