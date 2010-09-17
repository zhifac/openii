package org.mitre.openii.dialogs.projects.unity;

public interface UnityListener {

	/** Indicates the current progress of the matching of schema pairs */
	public void updateProgress(Double percentComplete);
	
	public void updateProgressMessage(String message); 
	
}
