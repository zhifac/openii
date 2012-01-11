package org.mitre.affinity.algorithms;

/**
 * An interface for implementations that track the progress of an algorithm.
 * 
 * @author CBONACETO
 *
 */
public interface IProgressMonitor {
	
	/**
	 * Set the current progress note.
	 * 
	 * @param note the note
	 */
	public void setNote(String note);	
	
	/**
	 * Set an error message note if an error was encountered.
	 * 
	 * @param errorNote
	 */
	public void setErrorNote(String errorNote);
	
	/**
	 * Get the minimum progress value.
	 * 
	 * @return the minimum progress value
	 */
	public int getMinimum();
	
	/**
	 * Set the minimum progress value.
	 * 
	 * @param minimum the minimum progress value
	 */
	public void setMinimum(int minimum);
	
	/**
	 * Get the maximum progress value.
	 * 
	 * @return the maximum progress vale
	 */
	public int getMaximum();
	
	/**
	 * Set the maximum progress value.
	 * 
	 * @param maximum the maximum progress vale
	 */
	public void setMaximum(int maximum);
	
	
	/**
	 * Get the current progress value.
	 * 
	 * @return the current progress value
	 */
	public int getProgress();
	
	/**
	 * Set the current progress value.
	 * 
	 * @param progress the current progress value
	 */
	public void setProgress(int progress);
	
	/**
	 * @return
	 */
	public boolean isCancelled();
	
	/**
	 * 
	 */
	public void cancel();
}