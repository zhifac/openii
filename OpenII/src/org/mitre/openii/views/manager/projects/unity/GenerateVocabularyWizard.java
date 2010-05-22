package org.mitre.openii.views.manager.projects.unity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.wizard.Wizard;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;

public class GenerateVocabularyWizard extends Wizard {

	private Project project;
	private HashMap<Integer, ProjectSchema> schemas;
	private AutoMappingPage autoMappingPage;
	private MatchMakerPage matchMakerPage;
	private AutoMappingProgressPage autoMappingProgressPage;

	/** Constructs the generate-vocabulary wizard */
	public GenerateVocabularyWizard(Project project) {
		super();
		setWindowTitle("Generate Vocabulary");
		this.project = project;
		initSchemas();
		// Generate a hash of project schemas EXCLUDING schemas with _VOCABULARY_TAG tag
	}

	/** Get all of the schemas in this project that aren't vocabulary **/ 
	private void initSchemas() {
		ArrayList<Integer> vocabIDs = OpenIIManager.getTagSchemas(Vocabulary.getVocabTag().getId());
		schemas = new HashMap<Integer, ProjectSchema>();
		for (ProjectSchema schema : project.getSchemas())
			if (vocabIDs.size() <= 0 || !vocabIDs.contains(schema.getId())) {
				schemas.put(schema.getId(), schema);
			}
	}

	public Project getProject() {
		return project;
	}

	/** Adds the pages associated with the import process */
	public void addPages() {
		addPage(autoMappingPage = new AutoMappingPage());
		// addPage(autoMappingProgressPage = new AutoMappingProgressPage());
		addPage(matchMakerPage = new MatchMakerPage());
	}

	AutoMappingPage getAutoMappingPropertiesPage() {
		return autoMappingPage;
	}

	AutoMappingProgressPage getAutoMappingProgressPage() {
		return autoMappingProgressPage;
	}

	MatchMakerPage getMatchMakerPage() {
		return matchMakerPage;
	}

	/** Indicates if the import process can be finished */
	public boolean canFinish() {
		return (matchMakerPage.isPageComplete()); // && autoMappingProgressPage.isPageComplete());
	}

	/** Imports the mapping once the wizard is complete */
	public boolean performFinish() {
		try {
			Unity unity = new Unity(project);
			unity.generateVocabulary(autoMappingPage.getMatchVoters(), matchMakerPage.getVocabularyName(), matchMakerPage.getAuthor(), matchMakerPage.getDescription());

			String exportPath = matchMakerPage.getVocabExportFilePath();
			if (exportPath.length() > 0) unity.exportVocabulary(new File(exportPath));
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
