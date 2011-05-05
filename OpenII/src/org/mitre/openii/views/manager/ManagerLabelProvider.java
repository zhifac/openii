package org.mitre.openii.views.manager;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Tag;
import org.mitre.schemastore.model.Thesaurus;

public class ManagerLabelProvider implements ILabelProvider
{
	/** Returns the image associated with the specified element */
	public Image getImage(Object element)
	{
		String imageName = "";
		if(element instanceof Schema)
		{
			if(((Schema)element).getType().equals(Thesaurus.class.toString())) imageName = "Thesaurus.gif";
			else imageName = "Schema.gif";
		}
		else if(element instanceof SchemaInTag) imageName = "Schema.gif";
		else if(element instanceof SchemasInProject) imageName = "Schemas.gif";
		else if(element instanceof SchemaInProject) imageName = "Schema.gif";
		else if(element instanceof Project) imageName = "Project.gif";
		else if(element instanceof Mapping) imageName = "Mapping.gif";
		else if(element instanceof VocabularyInProject) imageName = "Vocabulary.gif";
		else if(element instanceof Tag) imageName = "SchemaGroup.gif";
		else if(element instanceof DataSource) imageName = "DataSource.gif";
		else if(element==ManagerView.SCHEMAS_HEADER) imageName = "Schemas.gif";
		else if(element==ManagerView.THESAURI_HEADER) imageName = "Thesauri.gif";
		else if(element==ManagerView.ALL_SCHEMAS_HEADER) imageName = "SchemaGroup.gif";
		else if(element==ManagerView.PROJECTS_HEADER) imageName = "Projects.gif";
		return OpenIIActivator.getImage(imageName);
	}

	/** Returns the name associated with the specified element */
	public String getText(Object element)
	{
		if(element instanceof Mapping)
		{
			Mapping mapping = (Mapping)element;
			return OpenIIManager.getSchema(mapping.getSourceId()) + " to " + OpenIIManager.getSchema(mapping.getTargetId());
		}
		return element.toString();
	}

	/** Indicates that the label is not influenced by an element property */
	public boolean isLabelProperty(Object element, String property) { return false; }
	
	// Unused functions
	public void addListener(ILabelProviderListener listener) {}
	public void dispose() {}
	public void removeListener(ILabelProviderListener listener) {}
}
