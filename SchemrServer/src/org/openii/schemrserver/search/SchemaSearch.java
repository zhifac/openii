package org.openii.schemrserver.search;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.schemaImporters.DDLImporter;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;
import org.mitre.schemastore.porters.schemaImporters.XSDImporter;
import org.openii.schemrserver.indexer.SchemaStoreIndex;
import org.openii.schemrserver.indexer.SchemaUtility;
import org.openii.schemrserver.indexer.SchemaStoreIndex.CandidateSchema;
import org.openii.schemrserver.search.Query;
import org.openii.schemrserver.search.MatchSummary;

public class SchemaSearch {
	private static int  RESULT_PAGE_SIZE = 30;
	public static MatchSummary [] performSearch(String keywordString, File schemaFile) throws RemoteException {
		Query q = null;
		
		HashMap<String,String> queryKeywords = getKeywordMap(keywordString);
		Schema schema = null;
		ArrayList<SchemaElement> schemaElements = null;
		if (schemaFile != null) {
			SchemaImporter importer = null;
			String s = "file:///" + schemaFile.getAbsolutePath().replace("\\", "/").replace(" ", "%20");
			// pick a loader
			if (schemaFile.getName().endsWith("xsd")) {
				importer = new XSDImporter();
			} else if (schemaFile.getName().endsWith("sql")) {
				importer = new DDLImporter();
			} else {
				System.err.println("Not a schema file: "+schemaFile.getName());
			}

			// TODO: set this as preference
			String author = System.getProperty("user.name");
			try {
				schema = importer.generateSchema( new URI(s));
				schemaElements = importer.generateSchemaElements(new URI(s));
			} catch (ImporterException e) {
				System.err.println("Error importing "+s+": "+e.getMessage());
			} catch (URISyntaxException e) {
				System.err.println("Error accessing "+s+": "+e.getMessage());
			}
		}

		if (queryKeywords.size() < 1 && schemaElements == null) {
			System.err.println("The query must have either keywords or a valid schema!");
			return new MatchSummary [0];
		}

		if (schema != null && schemaElements != null) {
			q = new Query(schema, schemaElements, queryKeywords);			
		} else {
			q = new Query(queryKeywords);			
		}

		// filter for candidate schemas
		CandidateSchema [] candidateSchemas = getCandidateSchemas(q);

		int page = Math.min(RESULT_PAGE_SIZE, candidateSchemas.length);
		
		CandidateSchema [] filteredSchemas = new CandidateSchema [page];
		for (int i = 0; i < page; i++) {
			filteredSchemas[i] = candidateSchemas[i];
		}
		
		// process query against candidate schemas
		MatchSummary [] msarray = q.processQuery(filteredSchemas);

		System.out.println("Ranked results");
		MatchSummary [] topResultsArray = new MatchSummary [page];
		for (int i = 0; i < page; i++) {
			topResultsArray[i] = msarray[i];
			System.out.println(i+"\tschema: "+topResultsArray[i].getSchema().getName() + "\t\t\tscore: "+topResultsArray[i].getScore());
		}
		
		return topResultsArray;
	}
	
	
	//HELPERS::
	private static HashMap<String, String> getKeywordMap(String keywordString) {
		HashMap<String,String> queryKeywords = new HashMap<String,String>();
		if (keywordString != null && !keywordString.equals("")) {
			for(String keyword : keywordString.split("\\s+")) {
				String [] ka = keyword.split(":");
				if (ka.length == 1) {
					queryKeywords.put(ka[0], "");
				} else if (ka.length > 1) {
					queryKeywords.put(ka[1], ka[0]);
				} else {
					continue;
				}
			}
		}
		return queryKeywords;
	}
	private static CandidateSchema [] getCandidateSchemas(Query query) {		
		CandidateSchema [] candidateSchemas = SchemaStoreIndex.searchIndex(SchemaUtility.LOCAL_INDEX_DIR, query);			
		System.out.println("Found "+candidateSchemas.length+" candidate schemas");
		return candidateSchemas;
	}
}
