package org.mitre.openii.dialogs.projects.unity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.wizard.Wizard;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;

public class GenerateVocabularyWizard extends Wizard {

	private Project project;
	private HashMap<Integer, ProjectSchema> schemas;
	private AutoMappingPage autoMappingPage;
	private MatchMakerPage matchMakerPage;

	/** Constructs the generate-vocabulary wizard */
	public GenerateVocabularyWizard(Project project) {
		super();
		setWindowTitle("Generate Vocabulary");
		this.project = project;
		initSchemas();
	}

	/** Get all of the schemas in this project that aren't vocabulary **/
	private void initSchemas() {
		schemas = new HashMap<Integer, ProjectSchema>();
		for (ProjectSchema schema : project.getSchemas())
			schemas.put(schema.getId(), schema);
	}

	public Project getProject() {
		return project;
	}

	/** Adds the pages associated with the import process */
	public void addPages() {
		addPage(autoMappingPage = new AutoMappingPage());
		addPage(matchMakerPage = new MatchMakerPage());
	}

	AutoMappingPage getAutoMappingPropertiesPage() {
		return autoMappingPage;
	}

	MatchMakerPage getMatchMakerPage() {
		return matchMakerPage;
	}

	/** Indicates if the import process can be finished */
	public boolean canFinish() {
		return (autoMappingPage.isPageComplete() && matchMakerPage
				.isPageComplete());
	}

	/** Imports the mapping once the wizard is complete */
	public boolean performFinish() {
		try {
			// This is a temporary constructor that also includes running the mappings
			new MappingProcessor(project, autoMappingPage.getNewMappings(), autoMappingPage.getMatchers());
			
			ArrayList<Mapping> mappings = new ArrayList<Mapping>(); 
			// construct the list of mappings selected
			for (Pair<ProjectSchema> pair : autoMappingPage.getSelectedMappings() ) {
				for ( Mapping m : OpenIIManager.getMappings(project.getId()) ) {
					if ( pair.getItem1().getId().equals(m.getSourceId()) && pair.getItem2().getId().equals(m.getTargetId()) || 
							pair.getItem2().getId().equals(m.getTargetId()) && pair.getItem1().getId().equals(m.getSourceId()) ) 
						mappings.add(m); 
				}
			}
			
			// Create synsets
			Unity unity = new Unity(project);
			unity.DSFUnify( mappings );

			// export the vocabulary
			String exportPath = matchMakerPage.getVocabExportFilePath();
			if (exportPath.length() > 0)
				unity.exportVocabulary( new File(exportPath));
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public HashMap<Integer, ProjectSchema> getSchemas() {
		return schemas;
	}

}
