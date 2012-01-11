/*
 *  Copyright 2010 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mitre.affinity.view.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.mitre.affinity.algorithms.IProgressMonitor;
import org.mitre.affinity.view.swt.SWTUtils;

/**
 * @author CBONACETO
 *
 */
public class LoadProgressDialog extends Dialog implements IProgressMonitor {	
	
	private ProgressBar progressBar;
	
	private Label progressLabel;
	
	private Label errorLabel;
	
	private Shell shell;
	
	private Cursor standardCursor;	
	private Cursor waitCursor;
	
	private boolean cancelled;

	public LoadProgressDialog(Shell parent) {
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL); 
	}

	public LoadProgressDialog(Shell parent, int style) {
		super(parent, style);
	}
	
	@Override
	public void setNote(String note) {
		if(progressLabel != null && !progressLabel.isDisposed()) {
			progressLabel.setText(note);
		}
	}

	@Override
	public void setErrorNote(String errorNote) {
		if(errorLabel != null && !errorLabel.isDisposed()) {
			errorLabel.setText(errorNote);
		}
	}

	@Override
	public int getMinimum() {
		if(progressBar != null && !progressBar.isDisposed()) {
			return progressBar.getMinimum();
		}
		return 0;
	}

	@Override
	public void setMinimum(int minimum) {
		if(progressBar != null && !progressBar.isDisposed()) {
			progressBar.setMinimum(minimum);
		}
	}

	@Override
	public int getMaximum() {
		if(progressBar != null && !progressBar.isDisposed()) {
			return progressBar.getMaximum();
		}
		return 0;
	}

	@Override
	public void setMaximum(int maximum) {
		if(progressBar != null && !progressBar.isDisposed()) {
			progressBar.setMaximum(maximum);
		}
	}

	@Override
	public void setProgress(int progress) {
		if(progressBar != null && !progressBar.isDisposed()) {
			progressBar.setSelection(progress);
			if(progress >= progressBar.getMaximum() && shell.getCursor() != standardCursor) {
				shell.setCursor(standardCursor);
			}
			else if(shell.getCursor() != waitCursor) {
				shell.setCursor(waitCursor);
			}
		}
	}
	
	@Override
	public int getProgress() {
		if(progressBar != null && !progressBar.isDisposed()) {
			return progressBar.getSelection();
		}
		return 0;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void cancel() {
		cancelled = true;
	}	
	
	public Shell getShell() {
		return shell;
	}

	public void open() {
		Shell parent = getParent();
		shell = new Shell(parent, getStyle());
		shell.setText(getText());
		shell.setLayout(new GridLayout(1, true));
		this.standardCursor = shell.getCursor();
		this.waitCursor = shell.getDisplay().getSystemCursor(SWT.CURSOR_APPSTARTING);
		shell.setCursor(waitCursor);
		
		new Label(shell, SWT.LEFT).setText("Please wait...");
		this.progressBar = new ProgressBar(shell, SWT.SMOOTH | SWT.HORIZONTAL);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		this.progressLabel = new Label(shell, SWT.LEFT);
		progressLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.errorLabel = new Label(shell, SWT.LEFT);
		errorLabel.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_RED));
		errorLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		shell.pack();
		shell.setMinimumSize(300, shell.getSize().y);
		SWTUtils.centerShellOnShell(shell, parent);
		shell.open();		
	}
	
	public void close() {
		if(!shell.isDisposed()) {
			shell.close();
		}
	}	
	
	/*public void setProgressText(String text) {	
	if(progressLabel != null && !progressLabel.isDisposed())
		this.progressLabel.setText(text);
}

public void setErrorText(String text) {
	if(errorLabel != null && !errorLabel.isDisposed())
		this.errorLabel.setText(text);
}

public void setPercentComplete(int percent) {
	if(percent < 0 || percent > 100) 
		throw new IllegalArgumentException("Error updating percent complete: must be between 0 and 100");
	if(progressBar != null && !progressBar.isDisposed()) {
		this.progressBar.setSelection(percent);
		if(percent == 100 && shell.getCursor() != standardCursor)
			shell.setCursor(standardCursor);
		else if(shell.getCursor() != waitCursor)
			shell.setCursor(waitCursor);
	}
}*/
}