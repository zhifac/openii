package org.mitre.openii.editors;

import java.awt.Frame;

import javax.swing.JApplet;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.mitre.openii.model.GalaxyManager;

/** Constructs the Extension graph */
public class ExtensionGraph extends EditorPart
{
	/** Initializes the Extension graph */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException
		{ setSite(site); setInput(input); }
	
	/** Displays the Extension graph */
	public void createPartControl(Composite parent)
	{
		// Construct the applet pane to contain the Harmony frame
		JApplet appletPane = new JApplet();
		appletPane.add(GalaxyManager.getExtensionPane());
		
		// Embed the applet pane into the Eclipse view
		Composite swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		Frame frame = SWT_AWT.new_Frame( swtAwtComponent );
		frame.add(appletPane);
	}

	/** Indicates if editor has been changed */
	public boolean isDirty() { return false; }

	/** Indicates if saving of editor is allowed */
	public boolean isSaveAsAllowed() { return false; }
	
	// Unused editor functions
	public void doSave(IProgressMonitor arg0) {}
	public void doSaveAs() {}
	public void setFocus() {}
}