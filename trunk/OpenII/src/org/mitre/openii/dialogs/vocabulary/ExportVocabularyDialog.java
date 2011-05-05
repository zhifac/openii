package org.mitre.openii.dialogs.vocabulary;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.VocabularyInProject;
import org.mitre.openii.widgets.porters.ExporterDialog;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.VocabularyTerms;
import org.mitre.schemastore.porters.PorterType;
import org.mitre.schemastore.porters.vocabularyExporters.VocabularyExporter;

public class ExportVocabularyDialog {

	public static void export(Shell shell, VocabularyInProject vocabularyInProject) {
		VocabularyTerms terms = OpenIIManager.getVocabularyTerms( vocabularyInProject.getProjectID() );
		Project project = OpenIIManager.getProject(terms.getProjectID());
		
		// Create and launch the dialog
		ExporterDialog dialog = new ExporterDialog(shell, project.getName() + " vocabulary", PorterType.VOCABULARY_EXPORTERS);
		String filename = dialog.open();

		// Launch the dialog to retrieve the specified file to save to
		if (filename != null) {
			try {
				// Export the project
				VocabularyExporter exporter = dialog.getExporter();
				exporter.exportVocabulary(terms, new File(filename)); 
				
			} catch (Exception e) {
				MessageBox message = new MessageBox(shell, SWT.ERROR);
				message.setText("Vocabulary Export Error");
				message.setMessage("Failed to export vocabulary. "
						+ e.getMessage());
				message.open();
			}
		}
	}

}
