package org.mitre.openii.editors;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.mitre.openii.model.EditorInput;
import org.mitre.openii.model.EditorManager.EditorType;
import org.mitre.openii.model.OpenIIListener;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.SchemaInProject;
import org.mitre.openii.views.manager.SchemaInTag;
import org.mitre.openii.views.manager.VocabularyInProject;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Tag;

/** Constructs the OpenII Editor */
abstract public class OpenIIEditor extends EditorPart implements OpenIIListener
{
	/** Initializes the OpenII Editor */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException
	{
		// Initialize the site and input
		setSite(site);
		setInput(input);

		// Set the title
		Object element = ((EditorInput)input).getElement();
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
	
	/** Returns the type of editor being used */
	protected EditorType getEditorType()
		{ return ((EditorInput)getEditorInput()).getType(); }
	
	/** Returns the element being displayed in the editor */
	protected Object getElement()
		{ return ((EditorInput)getEditorInput()).getElement(); }
	
	/** Returns the element ID of the element being displayed in the editor */
	protected Integer getElementID()
	{
		Object element = getElement();
		if(element instanceof SchemaInTag) return ((SchemaInTag)element).getSchema().getId();
		if(element instanceof SchemaInProject) return ((SchemaInProject)element).getSchema().getId();
		if(element instanceof Schema) return ((Schema)element).getId();
		if(element instanceof Tag) return ((Tag)element).getId();
		if(element instanceof Project) return ((Project)element).getId();
		if(element instanceof Mapping) return ((Mapping)element).getId();
		if(element instanceof VocabularyInProject) return ((VocabularyInProject)element).getProjectID();
		return null;
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
	{
		// Check to see if editor showing the schema
		if(schemaID.equals(getElementID())) closeEditor();

		// Check to see if editor showing group of schemas containing schema
		else if(getElement() instanceof ArrayList)
			for(Object listID : (ArrayList<?>)getElement())
				{ if(schemaID.equals(listID))
					{ closeEditor(); return; } }
		
		// Check to see if editor showing mapping containing schema
		else if(getElement() instanceof Mapping)
		{
			Mapping mapping = (Mapping)getElement();
			if(mapping.getSourceId().equals(schemaID) || mapping.getTargetId().equals(schemaID))
				closeEditor();
		}
	}
	
	/** Dispose of the editor pane if referencing the deleted tag */
	public void tagDeleted(Integer tagID)
		{ if(tagID.equals(getElementID())) closeEditor(); }

	/** Dispose of the editor pane if referencing the deleted project */
	public void projectDeleted(Integer projectID)
	{
		if(projectID.equals(getElementID())) closeEditor();
		else if(getElement() instanceof Mapping)
			if(projectID.equals(((Mapping)getElement()).getProjectId()))
				closeEditor();
	}

	/** Dispose of the editor pane if referencing the deleted mapping */
	public void mappingDeleted(Integer mappingID)
		{ if(mappingID.equals(getElementID())) closeEditor(); }

	/** Dispose of the editor pane if referencing the deleted vocabulary */
	public void vocabularyDeleted(Integer projectID)
		{ if(getEditorType()==EditorType.VOCABULARY && projectID.equals(getElementID())) closeEditor(); }
	
	// Unused event listeners
	public void schemaAdded(Integer schemaID) {}
	public void schemaModified(Integer schemaID) {}
	public void dataSourceAdded(Integer dataSourceID) {}
	public void dataSourceDeleted(Integer dataSourceID) {}
	public void tagAdded(Integer tagID) {}
	public void tagModified(Integer tagID) {}
	public void projectAdded(Integer mappingID) {}
	public void projectModified(Integer mappingID) {}
	public void projectsMerged(ArrayList<Integer> projectIDs, Integer mergedProjectID) {}
	public void mappingAdded(Integer mappingID) {}
	public void mappingModified(Integer mappingID) {}
	public void vocabularyAdded(Integer projectID) {}
	public void vocabularyModified(Integer projectID) {}
}