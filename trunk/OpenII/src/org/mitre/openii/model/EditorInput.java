package org.mitre.openii.model;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/** Class for providing input to the editor */
public class EditorInput implements IEditorInput
{
	/** Stores the element */
	private Object element = null;
	
	/** Constructs the element id input */
	public EditorInput(Object element)
		{ this.element = element; }

	/** Returns the element */
	public Object getElement()
		{ return element; }
	
	public boolean exists() { return true; }
	public ImageDescriptor getImageDescriptor() { return null; }
	public String getName() { return ""; }
	public IPersistableElement getPersistable() { return null; }
	public String getToolTipText() { return ""; }

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class arg0) { return null; }
}
