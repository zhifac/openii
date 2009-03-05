package org.mitre.openii.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.mitre.openii.views.ygg.menu.ElementInput;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/** Constructs the Schema Graph */
abstract class OpenIIEditor extends EditorPart
{
	/** Stores the id associated with this editor */
	protected Integer elementID = null;
	
	/** Initializes the Schema Graph */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException
	{
		// Initialize the site and input
		setSite(site); setInput(input);

		// Extract element ID
		Object element = ((ElementInput)input).getElement();
		if(element instanceof Schema) elementID = ((Schema)element).getId();
		if(element instanceof Group) elementID = ((Group)element).getId();
		if(element instanceof Mapping) elementID = ((Mapping)element).getId();
	}

	// Default instantiations of editor functions
	public boolean isDirty() { return false; }
	public boolean isSaveAsAllowed() { return false; }	
	public void doSave(IProgressMonitor arg0) {}
	public void doSaveAs() {}
	public void setFocus() {}

	/** Abstract function used to display OpenII Editor */
	abstract public void createPartControl(Composite parent);
}