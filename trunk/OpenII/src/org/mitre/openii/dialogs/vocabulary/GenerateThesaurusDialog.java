package org.mitre.openii.dialogs.vocabulary;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.VocabularyInProject;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Thesaurus;
import org.mitre.schemastore.model.ThesaurusTerms;
import org.mitre.schemastore.model.VocabularyTerms;

/** Constructs the Generate Thesaurus Dialog class */
public class GenerateThesaurusDialog
{
	static public void generate(Shell shell, VocabularyInProject vocabularyInProject)
	{
		// Retrieve the vocabulary to transform
		Integer projectID = vocabularyInProject.getProjectID();
		Project project = OpenIIManager.getProject(projectID);
		VocabularyTerms vocabTerms = OpenIIManager.getVocabularyTerms(projectID);

		// Generate the thesaurus and thesaurus terms
		String projectName = project.getName();
		Thesaurus thesaurus = new Thesaurus(null, projectName, "");
		ThesaurusTerms terms = new ThesaurusTerms(null,vocabTerms.getTerms());
		
		// Save the thesaurus
		boolean success = true;
		Integer thesaurusID = OpenIIManager.addThesaurus(thesaurus);
		if(thesaurusID!=null)
		{
			terms.setThesaurusId(thesaurusID);
			success = OpenIIManager.saveThesaurusTerms(terms);
		}
		if(!success)
		{
			OpenIIManager.deleteSchema(thesaurusID);		
			MessageDialog.openError(shell, "Generation Failure", "The thesaurus failed to be properly generated");
		}
	}
}