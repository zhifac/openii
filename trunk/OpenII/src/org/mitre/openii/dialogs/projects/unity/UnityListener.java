package org.mitre.openii.dialogs.projects.unity;

public interface UnityListener {

	/** Indicates the current progress of the matching of schema pairs */
	public void updateProgress(Integer percetComplete);
	
	public void updateProgressMessage(String message); 
	
	public void updateComplete(boolean completed); 
	
}
