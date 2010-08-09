package org.mitre.openii.dialogs.projects;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.mitre.schemastore.model.Project;

public class BatchMatchWizard implements IWizard {
	
	public BatchMatchWizard() {
		// TODO Auto-generated constructor stub
	}
	
	public BatchMatchWizard(Project project ) {
		// TODO
	}

	public void addPages() {
		// TODO Auto-generated method stub

	}

	public boolean canFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	public void createPageControls(Composite arg0) {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public IWizardContainer getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	public Image getDefaultPageImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public IDialogSettings getDialogSettings() {
		// TODO Auto-generated method stub
		return null;
	}

	public IWizardPage getNextPage(IWizardPage arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public IWizardPage getPage(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getPageCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public IWizardPage[] getPages() {
		// TODO Auto-generated method stub
		return null;
	}

	public IWizardPage getPreviousPage(IWizardPage arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public IWizardPage getStartingPage() {
		// TODO Auto-generated method stub
		return null;
	}

	public RGB getTitleBarColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getWindowTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isHelpAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean needsPreviousAndNextButtons() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean needsProgressMonitor() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean performCancel() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setContainer(IWizardContainer arg0) {
		// TODO Auto-generated method stub

	}

}
