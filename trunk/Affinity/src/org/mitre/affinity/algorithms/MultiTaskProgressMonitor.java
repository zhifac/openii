package org.mitre.affinity.algorithms;

/**
 * @author CBONACETO
 *
 */
public class MultiTaskProgressMonitor implements IProgressMonitor {
	
	protected IProgressMonitor progressMonitor;
	
	protected int numTasks;
	
	protected int numTasksComplete = 0;
	
	public MultiTaskProgressMonitor(IProgressMonitor progressMonitor, int numTasks) {
		this.progressMonitor = progressMonitor;
		this.numTasks = numTasks;
	}

	@Override
	public void setNote(String note) {
		progressMonitor.setNote(note);
	}

	@Override
	public void setErrorNote(String errorNote) {
		progressMonitor.setErrorNote(errorNote);	
	}

	@Override
	public int getMinimum() {
		return progressMonitor.getMinimum();
	}

	@Override
	public void setMinimum(int minimum) {
		progressMonitor.setMinimum(minimum);		
	}

	@Override
	public int getMaximum() {
		return progressMonitor.getMaximum();
	}

	@Override
	public void setMaximum(int maximum) {
		progressMonitor.setMaximum(maximum);
	}
	
	@Override
	public int getProgress() {
		return progressMonitor.getProgress();
	}
	
	public int getNumTasks() {
		return numTasks;
	}

	public int getNumTasksComplete() {
		return numTasksComplete;
	}

	public void setNumTasksComplete(int numTasksComplete) {
		if(numTasksComplete < 0 || numTasksComplete > numTasks) {
			throw new IllegalArgumentException("Error setting numTasksComplete: must be between 0 and numTasks (" + numTasks + ")");
		}
		this.numTasksComplete = numTasksComplete;
		progressMonitor.setProgress((int)computeTaskProgress());
	}
	
	protected float computeTaskProgress() {
		return ((float)numTasksComplete/numTasks * 
				(progressMonitor.getMaximum() - progressMonitor.getMinimum()) + 
				progressMonitor.getMinimum());
	}

	@Override
	public void setProgress(int progress) {
		/*progressMonitor.setProgress((int)
				((float)(progress + progressMonitor.getMinimum())/
				(progressMonitor.getMaximum()-progressMonitor.getMinimum()) * 100f *
				(numTasksComplete+1)/(float)numTasks));*/
		progressMonitor.setProgress((int)
				(computeTaskProgress() +
				((float)(progress + progressMonitor.getMinimum())/
				(progressMonitor.getMaximum()-progressMonitor.getMinimum()) * 100f * 1f/numTasks)));
	}

	@Override
	public boolean isCancelled() {
		return progressMonitor.isCancelled();
	}

	@Override
	public void cancel() {
		progressMonitor.cancel();
	}	
}