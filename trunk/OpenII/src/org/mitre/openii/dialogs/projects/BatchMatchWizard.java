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

	@Override
	public void addPages() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPageControls(Composite arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public IWizardContainer getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getDefaultPageImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDialogSettings getDialogSettings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWizardPage getNextPage(IWizardPage arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWizardPage getPage(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPageCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IWizardPage[] getPages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWizardPage getPreviousPage(IWizardPage arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWizardPage getStartingPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RGB getTitleBarColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWindowTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isHelpAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean needsPreviousAndNextButtons() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean needsProgressMonitor() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean performCancel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setContainer(IWizardContainer arg0) {
		// TODO Auto-generated method stub

	}

}
