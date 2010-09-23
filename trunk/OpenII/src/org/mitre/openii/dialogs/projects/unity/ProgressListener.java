package org.mitre.openii.dialogs.projects.unity;

public interface ProgressListener {

	/** Indicates the current progress of the matching of schema pairs */
	public void updateProgress(Integer percetComplete);
	
	public void updateProgressMessage(String message); 
}
