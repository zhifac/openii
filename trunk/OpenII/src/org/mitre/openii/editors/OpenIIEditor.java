package org.mitre.openii.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.mitre.openii.model.EditorInput;
import org.mitre.openii.model.OpenIIListener;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Tag;

/** Constructs the OpenII Editor */
abstract public class OpenIIEditor extends EditorPart implements OpenIIListener
{
	/** Stores the id associated with this editor */
	protected Integer elementID = null;
	
	/** Initializes the OpenII Editor */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException
	{
		// Initialize the site and input
		setSite(site); setInput(input);
		
		// Extract element ID
		Object element = ((EditorInput)input).getElement();
		if(element instanceof Schema) elementID = ((Schema)element).getId();
		if(element instanceof Tag) elementID = ((Tag)element).getId();
		if(element instanceof Project) elementID = ((Project)element).getId();
		if(element instanceof Mapping) elementID = ((Mapping)element).getId();

		// Set the title
		if(element instanceof Mapping)
		{
			Mapping mapping = (Mapping)element;
			Schema source = OpenIIManager.getSchema(mapping.getSourceId());
			Schema target = OpenIIManager.getSchema(mapping.getTargetId());
			setPartName(source.getName() + " to " + target.getName());
		}
		else setPartName(element.toString());
		
		// Listen for modifications to the repository and OpenII model
		OpenIIManager.addListener(this);
	}

	// Default instantiations of editor functions
	public boolean isDirty() { return false; }
	public boolean isSaveAsAllowed() { return false; }	
	public void doSave(IProgressMonitor arg0) {}
	public void doSaveAs() {}
	public void setFocus() {}
	
	/** Closes the editor */
	public void closeEditor()
	{
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		window.getActivePage().closeEditor(this, true);
	}
	
	/** Abstract function used to display OpenII Editor */
	abstract public void createPartControl(Composite parent);

	/** Dispose of the editor pane if the repository has been changed */
	public void repositoryReset() { closeEditor(); }
	
	/** Dispose of the editor pane if referencing the deleted schema */
	public void schemaDeleted(Integer schemaID)
		{ if(elementID.equals(schemaID)) closeEditor(); }
	
	/** Dispose of the editor pane if referencing the deleted tag */
	public void tagDeleted(Integer tagID)
		{ if(elementID.equals(tagID)) closeEditor(); }

	/** Dispose of the editor pane if referencing the deleted project */
	public void projectDeleted(Integer projectID)
		{ if(elementID.equals(projectID)) closeEditor(); }

	/** Dispose of the editor pane if referencing the deleted mapping */
	public void mappingDeleted(Integer mappingID)
		{ if(elementID.equals(mappingID)) closeEditor(); }

	// Unused event listeners
	public void schemaAdded(Integer schemaID) {}
	public void schemaModified(Integer schemaID) {}
	public void dataSourceAdded(Integer dataSourceID) {}
	public void dataSourceDeleted(Integer dataSourceID) {}
	public void tagAdded(Integer tagID) {}
	public void tagModified(Integer tagID) {}
	public void projectAdded(Integer mappingID) {}
	public void projectModified(Integer mappingID) {}
	public void mappingAdded(Integer mappingID) {}
	public void mappingModified(Integer mappingID) {}
}