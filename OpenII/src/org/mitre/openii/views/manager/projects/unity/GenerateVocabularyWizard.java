package org.mitre.openii.views.manager.projects.unity;

import org.eclipse.jface.wizard.Wizard;
import org.mitre.schemastore.model.Project;


public class GenerateVocabularyWizard extends Wizard {

	private Project project;
	private AutoMappingPage autoMappingPage;
	private MatchMakerPage matchMakerPage;
	private AutoMappingProgressPage autoMappingProgressPage;
	
	

	/** Constructs the generate-vocabulary wizard */
	public GenerateVocabularyWizard(Project project) {
		super();
		setWindowTitle("Generate Vocabulary");
		this.project = project;
	}
	
	public Project getProject() {
		return project; 
	}

	/** Adds the pages associated with the import process */
	public void addPages() {
		addPage(autoMappingPage = new AutoMappingPage());
//		addPage(autoMappingProgressPage = new AutoMappingProgressPage()); 
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
		return ( matchMakerPage.isPageComplete() && autoMappingProgressPage.isPageComplete() ); 
	}
	

	/** Imports the mapping once the wizard is complete */
	public boolean performFinish() {
		
		 return true;
	}

}
