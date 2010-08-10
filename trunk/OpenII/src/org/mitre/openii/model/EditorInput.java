package org.mitre.openii.model;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.mitre.openii.model.EditorManager.EditorType;

/** Class for providing input to the editor */
public class EditorInput implements IEditorInput
{
	/** Stores the editor type */
	private EditorType type;
	
	/** Stores the element */
	private Object element;
	
	/** Constructs the element id input */
	public EditorInput(String editorType, Object element)
	{
		this.type = editorType!=null ? EditorType.valueOf(editorType.substring(1,editorType.length()).toUpperCase()) : null;
		this.element = element;
	}

	/** Returns the editor type */
	public EditorType getType()
		{ return type; }
	
	/** Returns the element */
	public Object getElement()
		{ return element; }
	
	public boolean exists() { return true; }
	public ImageDescriptor getImageDescriptor() { return null; }
	public String getName() { return ""; }
	public IPersistableElement getPersistable() { return null; }
	public String getToolTipText() { return ""; }
	public Object getAdapter(@SuppressWarnings("rawtypes") Class arg0) { return null; }
}
