package org.mitre.openii.editors.help;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.mitre.openii.application.OpenIIActivator;

/** Constructs the About OpenII Editor */
public class AboutOpenIIEditor extends EditorPart {
	/** Initializes the About OpenII Editor */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException	{
		setSite(site);
		setInput(input);
		setPartName("About OpenII");
	}

	// Default instantiations of editor functions
	public boolean isDirty() { return false; }
	public boolean isSaveAsAllowed() { return false; }	
	public void doSave(IProgressMonitor arg0) {}
	public void doSaveAs() {}
	public void setFocus() {}
	
	/** Displays the Schema View */
	public void createPartControl(Composite parent) {
		// create a browser to display in the window
		Browser browser = new Browser(parent, SWT.NONE);
		
		// get the base location of our application
		String homeURL = getClass().getProtectionDomain().getCodeSource().getLocation().toString().replaceAll("\\%20"," ");
		String homePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replaceAll("\\%20"," ");

		// remove any references to the openii jar
		homeURL = homeURL.replaceAll("org\\.mitre\\.openii.*\\.jar$", "");
		homePath = homePath.replaceAll("org\\.mitre\\.openii.*\\.jar$", "");

		// check to see if it is in a location one above us
		// this should match both the IDE and standalone
		File f = new File(homePath + "../OpenII Documentation");
		if (f.exists()) {
			browser.setUrl(homeURL + "../OpenII Documentation/html/index.html");
			return;
		}

		// couldn't find it, show an error
		browser.setText("<html><body>Could not find OpenII Documentation.</body></html>");
	}

	/** Launches the editor */
	static public void launchEditor() {
		try {
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			page.openEditor(null,"AboutOpenIIEditor");
		} catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}