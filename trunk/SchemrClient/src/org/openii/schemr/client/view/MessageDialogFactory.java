package org.openii.schemr.client.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageDialogFactory {

	public static void displayError(Shell shell, String title, String msg) {
		MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
		box.setText(title);
		box.setMessage(msg);
		box.open();
	}
}
