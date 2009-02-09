package org.mitre.harmony.matchers.metrics;

import java.io.File;

public class ExtensionFileFilter extends javax.swing.filechooser.FileFilter 
{
	//instance variables
	String description;
	String extensions[];
	
	//constructor
	public ExtensionFileFilter(String description, String extension) 
		{	this(description, new String[] { extension });	}
	
	//constructor
	public ExtensionFileFilter(String description, String extensions[])
	{
	    if (description == null) 
	    	this.description = extensions[0];
	    else 
	    	this.description = description;

	    this.extensions = (String[]) extensions.clone();
	    toLower(this.extensions);
	}

	//method to convert all extensions to lower case
	private void toLower(String array[]) 
	{
	    for (int i = 0, n = array.length; i < n; i++) 
	    	array[i] = array[i].toLowerCase();
	}
	
	//method to get the description
	public String getDescription()
	{	return description;	}
	
	//method to find files of given extensions
	public boolean accept(File file) 
	{
		if (file.isDirectory())
			return true;
		else
		{
			String path = file.getAbsolutePath().toLowerCase();
			for (int i = 0, n = extensions.length; i < n; i++) 
			{
				String extension = extensions[i];
				if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.'))
					return true;
			}
		}
		return false;
	}
}
