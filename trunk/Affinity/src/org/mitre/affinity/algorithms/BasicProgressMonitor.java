package org.mitre.affinity.algorithms;

/**
 * @author CBONACETO
 *
 */
public class BasicProgressMonitor implements IProgressMonitor {
	
	protected int minimum;
	
	protected int maximum;
	
	protected int progress;
	
	protected boolean cancelled;

	@Override
	public void setNote(String note) {}

	@Override
	public void setErrorNote(String errorNote) {}

	@Override
	public int getMinimum() {
		return minimum;
	}

	@Override
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	@Override
	public int getMaximum() {
		return maximum;
	}

	@Override
	public void setMaximum(int maximum) {
		this.maximum = maximum;	
	}

	@Override
	public int getProgress() {
		return progress;
	}

	@Override
	public void setProgress(int progress) {
		this.progress = progress;	
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void cancel() {
		cancelled = true;
	}
}
