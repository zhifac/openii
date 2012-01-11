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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mitre.affinity.view.swt.SWTUtils;

public class StackTraceDialog extends Dialog {
	private Shell shell;
	private final Exception ex;
	private String message;
	
	public StackTraceDialog(Shell parent, Exception ex) {
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, ex); 
	}
	
	public StackTraceDialog(Shell parent, int style, Exception ex) {
		super(parent, style);	
		this.ex = ex;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void open() {
		Shell parent = getParent();
		this.shell = new Shell(parent, getStyle());
		shell.setText(getText());
		shell.setLayout(new GridLayout(1, true));
		
		Composite errorPane = new Composite(shell, SWT.NONE);
		errorPane.setLayout(new GridLayout(2, false));
		errorPane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		Label errorLabel = new Label(errorPane, SWT.LEFT);
		//errorLabel.setText("Error");
		errorLabel.setImage(shell.getDisplay().getSystemImage(SWT.ICON_ERROR));
		if(message != null)
			new Label(errorPane, SWT.LEFT).setText(message);
		
		Text stackTraceText = new Text(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		if(this.ex != null) {
			stackTraceText.append(ex.toString() + "\n");
			for(StackTraceElement element : this.ex.getStackTrace()) {
				stackTraceText.append("	at " + element.toString() + "\n");
			}
		}
		Point size = stackTraceText.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		stackTraceText.setLayoutData(new GridData(size. x - 32, size.y / 2));
		
		Button okButton = new Button(shell, SWT.PUSH);
		okButton.setText("Close");
		okButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		okButton.addListener(SWT.Selection, new Listener() {			
			public void handleEvent(Event event) {
				shell.close();
			}			
		});
		
		shell.pack();
		SWTUtils.centerShellOnShell(shell, parent);
		shell.open();
		
		final Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if(display.readAndDispatch()){
				display.sleep();
			}
		}
	}
}