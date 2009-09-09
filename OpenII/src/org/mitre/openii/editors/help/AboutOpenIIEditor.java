package org.mitre.openii.editors.help;

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

/** Constructs the About OpenII Editor */
public class AboutOpenIIEditor extends EditorPart
{	
	/** Initializes the About OpenII Editor */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException
		{ setSite(site); setInput(input); setPartName("About OpenII"); }

	// Default instantiations of editor functions
	public boolean isDirty() { return false; }
	public boolean isSaveAsAllowed() { return false; }	
	public void doSave(IProgressMonitor arg0) {}
	public void doSaveAs() {}
	public void setFocus() {}
	
	/** Displays the Schema View */
	public void createPartControl(Composite parent)
	{		
		new Browser(parent,SWT.NONE);
		//BasicWidgets.createLabel(parent,"Hello World");
	}
	
	/** Launches the editor */
	static public void launchEditor()
	{
		try {
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			page.openEditor(null,"AboutOpenIIEditor");
		}
		catch(Exception e) { System.err.println(e.getMessage()); e.printStackTrace();}
	}
}