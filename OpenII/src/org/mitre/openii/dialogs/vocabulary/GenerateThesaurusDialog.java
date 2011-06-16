package org.mitre.openii.dialogs.vocabulary;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.dialogs.thesauri.EditThesaurusDialog;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.VocabularyInProject;
import org.mitre.schemastore.model.Thesaurus;
import org.mitre.schemastore.model.terms.ThesaurusTerms;
import org.mitre.schemastore.model.terms.VocabularyTerms;

/** Constructs the Generate Thesaurus Dialog class */
public class GenerateThesaurusDialog
{
	static public void generate(Shell shell, VocabularyInProject vocabularyInProject)
	{
		// Retrieve the vocabulary to transform
		Integer projectID = vocabularyInProject.getProjectID();
		VocabularyTerms vocabTerms = OpenIIManager.getVocabularyTerms(projectID);
		
		// Generate the thesaurus
		EditThesaurusDialog dialog = new EditThesaurusDialog(shell, null);
		dialog.open();
		if(dialog.getReturnCode() == IDialogConstants.CANCEL_ID) return;
		Thesaurus thesaurus = dialog.getThesaurus();

		// Generate the thesaurus and thesaurus terms
		ThesaurusTerms terms = new ThesaurusTerms(null,vocabTerms.getTerms());		

		// Save the thesaurus
		terms.setThesaurusId(thesaurus.getId());
		if(!OpenIIManager.saveThesaurusTerms(terms))
		{
			OpenIIManager.deleteThesaurus(thesaurus.getId());
			MessageDialog.openError(shell, "Generation Failure", "The thesaurus failed to be properly generated");
		}
	}
}