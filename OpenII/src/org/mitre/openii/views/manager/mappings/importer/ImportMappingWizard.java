package org.mitre.openii.views.manager.mappings.importer;

import java.net.URI;
import java.util.ArrayList;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.porters.mappingImporters.MappingImporter;

/**
 * Class defining a wizard for importing mappings
 */
public class ImportMappingWizard extends Wizard
{
	// Stores the pages used by this wizard
	private MappingPropertiesPage propertiesPage;
	private MappingSchemasPage schemasPage;
	
	/** Constructs the wizard */
	public ImportMappingWizard()
	{
		super();
		setWindowTitle("Import Mapping");
		setDefaultPageImageDescriptor(OpenIIActivator.getImageDescriptor("icons/ImportLarge.png"));
	}
	
	/** Adds the pages associated with the import process */
	public void addPages()
	{
		addPage(propertiesPage = new MappingPropertiesPage());
		addPage(schemasPage = new MappingSchemasPage());
	}

	/** Returns the properties page */
	MappingPropertiesPage getPropertiesPage()
		{ return propertiesPage; }

	/** Returns the schemas page */
	MappingSchemasPage getSchemasPage()
		{ return schemasPage; }
	
	/** Indicates if the import process can be finished */
	public boolean canFinish()
		{ return schemasPage.isPageComplete(); }
	
	/** Imports the mapping once the wizard is complete */
	public boolean performFinish()
	{
		try {
			// Gather up mapping information
			String name = propertiesPage.getMappingName();
			String author = propertiesPage.getMappingAuthor();
			String description = propertiesPage.getMappingDescription();
			ArrayList<Integer> schemaIDs = schemasPage.getSchemaIDs();
			URI uri = propertiesPage.getURI();
			
			// Import mapping
			MappingImporter importer = propertiesPage.getImporter();
			Integer mappingID = importer.importMapping(name, author, description, schemaIDs, uri);
			if(mappingID!=null)
			{
				Mapping mapping = RepositoryManager.getClient().getMapping(mappingID);
				OpenIIManager.fireMappingAdded(mapping); getShell().dispose();
				return true;
			}
		}
		catch(Exception e)
		{
			WizardPage page = (WizardPage)getPage("SchemasPage");
			page.setErrorMessage("Failed to import mapping. " + e.getMessage());
		}
		return false;
	}
}