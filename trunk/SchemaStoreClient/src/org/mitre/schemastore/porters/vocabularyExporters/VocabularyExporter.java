package org.mitre.schemastore.porters.vocabularyExporters;

import java.io.File;
import java.io.IOException;

import org.mitre.schemastore.model.Vocabulary;
import org.mitre.schemastore.porters.Exporter;

public abstract class VocabularyExporter extends Exporter {

	 abstract public void exportVocabulary (Vocabulary vocabulary, File file)  throws IOException; 

}
