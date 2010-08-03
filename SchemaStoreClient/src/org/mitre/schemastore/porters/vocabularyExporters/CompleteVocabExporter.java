package org.mitre.schemastore.porters.vocabularyExporters;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.Vocabulary;

public class CompleteVocabExporter extends VocabularyExporter {

	@Override
	public void exportVocabulary(Vocabulary vocabulary, File file)
			throws IOException {
		PrintStream os = new PrintStream(file); 
		Integer[] schemaIDs = vocabulary.getSchemaIDs();
		
		// Print header 
		os.print( "Vocabulary, Vocabulary Description, " ); 
		for (Integer sid : schemaIDs )
			os.print(client.getSchema(sid).getName() + ", Description, "); 
		os.println("\n");
		
		// Print Term, associated elements, and respective description 
		for ( Term term : vocabulary.getTerms() ) {
			os.print( term.getName() + ", " + term.getDescription() + ",");
			for ( Integer schemaID : vocabulary.getSchemaIDs() ) {
				SchemaElement element = client.getSchemaElement(term.getAssociatedElement(schemaID).getElementID()); 
				os.println( element.getName() + ", " + element.getDescription() + ", " ); 
			}
			os.println("\n");
		}
		os.flush();
		os.close();
	}

	@Override
	public String getFileType() {
		return ".csv"; 
	}

	@Override
	public String getDescription() {
		return "Export the full vocabulary terms and their corresponding terms";
	}

	@Override
	public String getName() {
		return "Complete Vocabulary Exporter"; 
	}

}
