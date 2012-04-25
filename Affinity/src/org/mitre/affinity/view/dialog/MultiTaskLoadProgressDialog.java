package org.mitre.affinity.view.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.mitre.affinity.algorithms.BasicProgressMonitor;
import org.mitre.affinity.algorithms.IProgressMonitor;
import org.mitre.affinity.algorithms.MultiTaskProgressMonitor;
import org.mitre.affinity.view.swt.SWTUtils;

/**
 * @author CBONACETO
 *
 */
public class MultiTaskLoadProgressDialog extends Dialog implements IProgressMonitor {

	private ProgressBar overallProgressBar;	
	private ProgressBar currentTaskProgressBar;

	private Label overallProgressLabel;
	private Label currentTaskLabel;	
	private Label currentTaskProgressLabel;	

	private Label errorLabel;

	private Shell shell;

	//private Cursor standardCursor;	
	//private Cursor waitCursor;

	private MultiTaskProgressMonitor progressMonitor;

	public MultiTaskLoadProgressDialog(Shell parent, int numTasks) {
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, numTasks); 
	}

	public MultiTaskLoadProgressDialog(Shell parent, int style, int numTasks) {
		super(parent, style);
		progressMonitor = new MultiTaskProgressMonitor(new BasicProgressMonitor(), numTasks);
		progressMonitor.setMinimum(0);
		progressMonitor.setMaximum(100);
	}

	public boolean isDisposed() {
		if(shell != null) {
			return shell.isDisposed();
		}
		return true;
	}

	public void setCurrentTaskNote(final String note) {
		if(shell != null && !shell.isDisposed()) {
			shell.getDisplay().asyncExec(new Runnable() {
				public void run() {
					if(currentTaskLabel != null && !currentTaskLabel.isDisposed()) {
						currentTaskLabel.setText("Current Task: " + note);		
					}
				}
			});
		}
	}

	@Override
	public void setNote(final String note) {
		if(shell != null && !shell.isDisposed()) {
			shell.getDisplay().asyncExec(new Runnable() {
				public void run() {
					if(currentTaskProgressLabel != null && !currentTaskProgressLabel.isDisposed()) {
						currentTaskProgressLabel.setText(note);
					}
				}
			});		
		}
	}

	@Override
	public void setErrorNote(final String errorNote) {			
		if(shell != null && !shell.isDisposed()) {
			shell.getDisplay().asyncExec(new Runnable() {
				public void run() {
					if(errorLabel != null && !errorLabel.isDisposed()) {
						errorLabel.setText(errorNote);
					}
				}
			});	
		}
	}

	public int getOverallProgressMinimum() {
		return progressMonitor.getMinimum();
	}

	public void setOverallProgressMinimum(final int minimum) {
		progressMonitor.setMinimum(minimum);
		if(shell != null && !shell.isDisposed()) {
			shell.getDisplay().asyncExec(new Runnable() {
				public void run() {
					if(overallProgressBar != null && !overallProgressBar.isDisposed()) {
						overallProgressBar.setMinimum(minimum);
					}
				}
			});
		}
	}

	@Override
	public int getMinimum() {
		if(currentTaskProgressBar != null && !currentTaskProgressBar.isDisposed()) {
			return currentTaskProgressBar.getMinimum();
		}
		return 0;
	}

	@Override
	public void setMinimum(final int minimum) {
		if(shell != null && !shell.isDisposed()) {
			shell.getDisplay().asyncExec(new Runnable() {
				public void run() {
					if(currentTaskProgressBar != null && !currentTaskProgressBar.isDisposed()) {
						currentTaskProgressBar.setMinimum(minimum);
					}
				}
			});
		}
	}

	public int getOverallProgressMaximum() {
		return progressMonitor.getMaximum();
	}

	public void setOverallProgressMaximum(final int maximum) {			
		progressMonitor.setMaximum(maximum);
		if(shell != null && !shell.isDisposed()) {
			shell.getDisplay().asyncExec(new Runnable() {
				public void run() {
					if(overallProgressBar != null && !overallProgressBar.isDisposed()) {
						overallProgressBar.setMaximum(maximum);
					}
				}
			});
		}
	}

	@Override
	public int getMaximum() {
		if(currentTaskProgressBar != null && !currentTaskProgressBar.isDisposed()) {
			return currentTaskProgressBar.getMaximum();
		}
		return 0;
	}

	@Override
	public void setMaximum(final int maximum) {
		if(shell != null && !shell.isDisposed()) {
			shell.getDisplay().asyncExec(new Runnable() {
				public void run() {
					if(currentTaskProgressBar != null && !currentTaskProgressBar.isDisposed()) {
						currentTaskProgressBar.setMaximum(maximum);
					}
				}
			});
		}
	}

	public int getNumTasks() {
		return progressMonitor.getNumTasks();
	}

	public int getNumTasksComplete() {
		return progressMonitor.getNumTasksComplete();
	}

	public void setNumTasksComplete(int numTasksComplete) {
		progressMonitor.setNumTasksComplete(numTasksComplete);
		if(shell != null && !shell.isDisposed()) {
			shell.getDisplay().asyncExec(new Runnable() {
				public void run() {
					//Update overall progress bar
					updateOverallProgress();
				}
			});
		}
	}

	@Override
	public void setProgress(final int progress) {
		progressMonitor.setProgress(progress);
		if(shell != null && !shell.isDisposed()) {
			shell.getDisplay().asyncExec(new Runnable() {
				public void run() {
					//Update current task progress bar
					if(currentTaskProgressBar != null && !currentTaskProgressBar.isDisposed()) {
						currentTaskProgressBar.setSelection(progress);
					}

					//Update overall progress bar
					updateOverallProgress();
				}
			});
		}
	}

	protected void updateOverallProgress() {
		if(overallProgressBar != null && !overallProgressBar.isDisposed()) {
			int progress = progressMonitor.getProgress();
			overallProgressBar.setSelection(progress);
			int progressPercent = 
					(int)(((float)(progress + progressMonitor.getMinimum())/
							(progressMonitor.getMaximum()-progressMonitor.getMinimum()) * 100f));
			//System.out.println(progressPercent);
			overallProgressLabel.setText("Overall Progress: " + progressPercent + "%");
			/*if(progress >= overallProgressBar.getMaximum() && shell.getCursor() != standardCursor) {
				shell.setCursor(standardCursor);
			}
			else if(shell.getCursor() != waitCursor) {
				shell.setCursor(waitCursor);
			}*/
		}	
	}

	public int getOverallProgress() {
		return progressMonitor.getProgress();
	}

	@Override
	public int getProgress() {
		if(currentTaskProgressBar != null && !currentTaskProgressBar.isDisposed()) {
			return currentTaskProgressBar.getSelection();
		}
		return 0;
	}

	@Override
	public boolean isCancelled() {
		return progressMonitor.isCancelled();
	}

	@Override
	public void cancel() {
		close();
	}	

	public Shell getShell() {
		return shell;
	}

	public void open() {
		Shell parent = getParent();
		shell = new Shell(parent, getStyle());
		shell.setText(getText());
		shell.setLayout(new GridLayout(1, true));
		//standardCursor = shell.getCursor();
		//waitCursor = shell.getDisplay().getSystemCursor(SWT.CURSOR_APPSTARTING);
		//shell.setCursor(waitCursor);

		new Label(shell, SWT.LEFT).setText("Please wait...");

		//Create overall progress label and progress bar
		overallProgressLabel = new Label(shell, SWT.LEFT);
		overallProgressLabel.setText("Overall Progress: 0%");
		overallProgressLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		overallProgressBar = new ProgressBar(shell, SWT.SMOOTH | SWT.HORIZONTAL);
		overallProgressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		overallProgressBar.setMinimum(0);
		overallProgressBar.setMaximum(100);		

		//Create current task progress label, progress bar, and label showing the current task progress note
		new Label(shell, SWT.LEFT);
		currentTaskLabel = new Label(shell, SWT.LEFT);
		currentTaskLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		currentTaskProgressBar = new ProgressBar(shell, SWT.SMOOTH | SWT.HORIZONTAL);
		currentTaskProgressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		currentTaskProgressBar.setMinimum(0);
		currentTaskProgressBar.setMaximum(100);
		currentTaskProgressLabel = new Label(shell, SWT.CENTER);
		currentTaskProgressLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		errorLabel = new Label(shell, SWT.LEFT);
		errorLabel.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_RED));
		errorLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		shell.pack();
		shell.setMinimumSize(300, shell.getSize().y);
		SWTUtils.centerShellOnShell(shell, parent);
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				progressMonitor.cancel();
			}
			
		});
		shell.open();		
	}

	public void close() {
		progressMonitor.cancel();		
		if(shell != null && !shell.isDisposed()) {
			shell.getDisplay().asyncExec(new Runnable() {
				public void run() {
					if(!shell.isDisposed()) {
						shell.close();
					}
				}
			});
		}
	}

	/** Test main */
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		//shell.open();

		MultiTaskLoadProgressDialog dlg = new MultiTaskLoadProgressDialog(shell, 2);
		dlg.open();

		for(int i = 0; i < 2; i++) {
			if(i ==0) {
				dlg.setCurrentTaskNote("Computing Clusters");
			}
			else if(i == 1) {
				dlg.setCurrentTaskNote("Computing Layout");
			}
			for(int j=0; j<=100; j++) {
				//System.out.println(j);
				if(j == 0) {
					dlg.setNote("Starting");
				}
				if(j == 50) {
					dlg.setNote("Halfway there!");
				}
				try {
					Thread.sleep(30);
				} catch(Exception ex) {}
				dlg.setProgress(j);
			}
			dlg.setNumTasksComplete(i+1);
			dlg.setNote("");
		}

		while(!dlg.getShell().isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}			
		if(shell != null && !shell.isDisposed()) {
			shell.dispose();
		}
		if(display != null && !display.isDisposed()) {
			display.dispose();	
		}
	}
}