package org.openii.schemr.client.action;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchWindow;
import org.openii.schemr.SchemaStoreIndex;
import org.openii.schemr.SchemaUtility;

public class BuildIndexAction extends Action {

	private final IWorkbenchWindow window;

	public BuildIndexAction(IWorkbenchWindow window, String label) {
		this.window = window;
		setText(label);
	}

	public void run() {
		if (window != null) {
			try {
				window.getWorkbench().getProgressService().busyCursorWhile(
						new IRunnableWithProgress() {
							public void run(IProgressMonitor pm) throws InvocationTargetException, InterruptedException {

								pm.setTaskName("Building index in "+SchemaUtility.LOCAL_INDEX_DIR+" for "+SchemaUtility.SCHEMA_STORE_URL);
								SchemaStoreIndex.buildIndex(SchemaUtility.getCLIENT(), SchemaUtility.LOCAL_INDEX_DIR);
							}
						});

			} catch (Exception e) {
				MessageDialog.openError(window.getShell(), "Error", "Error building index!" + e.getMessage());
			}
		}
	}

}
