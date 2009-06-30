package org.openii.schemrserver.search;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.schemaImporters.DDLImporter;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;
import org.mitre.schemastore.porters.schemaImporters.SchemaProperties;
import org.mitre.schemastore.porters.schemaImporters.XSDImporter;
import org.openii.schemrserver.Consts;
import org.openii.schemrserver.SchemaUtility;
import org.openii.schemrserver.indexer.SchemaStoreIndex;
import org.openii.schemrserver.indexer.SchemaStoreIndex.CandidateSchema;

public class SchemaSearch {

	public static int  RESULT_PAGE_SIZE = 20;
	public static int numResults;
//	public static MatchSummary [] performSearch(String keywordString, File schemaFile) throws RemoteException {
//		return performSearch(keywordString, schemaFile, true);
//	}
	
	public static MatchSummary [] performSearch(String keywordString, File schemaFile, String type, boolean matchersOn) throws RemoteException {
		Query q = null;
		HashMap<String,String> queryKeywords = getKeywordMap(keywordString);
		
		SchemaProperties schemaProps = null;
		ArrayList<SchemaElement> schemaElements = null;
		if (schemaFile != null) {
			SchemaImporter importer = null;
			String s = "file:///" + schemaFile.getAbsolutePath().replace("\\", "/").replace(" ", "%20");
			// pick a loader
			if (type.equals("xsd")) {
				importer = new XSDImporter();
			} else if (type.equals("ddl")) {
				importer = new DDLImporter();
			} else {
				System.err.println("File not a valid type : "+schemaFile.getName());
			}
			try {
				URI searchSchemaURI = new URI(s);
				schemaElements = importer.getSchemaElements(searchSchemaURI);
				schemaProps = importer.getSchemaProperties(searchSchemaURI);
			} catch (ImporterException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}

		if (queryKeywords.size() < 1 && schemaElements == null) {
			System.err.println("The query must have either keywords or a valid schema!");
			return new MatchSummary [0];
		}

		if (schemaElements != null) {
			q = new Query(schemaProps, schemaElements, queryKeywords);			
		} else {
			q = new Query(queryKeywords);			
		}

		// filter for candidate schemas
		CandidateSchema [] candidateSchemas = getCandidateSchemas(q);
		numResults = candidateSchemas.length;
		MatchSummary [] topResultsArray = null;
		
		if (candidateSchemas != null && candidateSchemas.length > 0) {
			int page = Math.min(RESULT_PAGE_SIZE, candidateSchemas.length);
			CandidateSchema [] firstPageSchemas = new CandidateSchema [page];
			for (int i = 0; i < page; i++) {
				firstPageSchemas[i] = candidateSchemas[i];
			}

			topResultsArray = new MatchSummary [page];
			if (matchersOn) { // by default, they are on
				// process query against candidate schemas, result is sorted by score
//				MatchSummary [] msarray = q.processQuery(candidateSchemas);
				MatchSummary [] msarray = q.processQuery(firstPageSchemas);
				for (int i = 0; i < page; i++) {
					topResultsArray[i] = msarray[i];
				}
			} else {
				for (int i = 0; i < page; i++) {
					// just take lucene results from candidateSchemas, no matchers
					// TODO: are candidate schemas ranked?
					topResultsArray[i] = new MatchSummary(
							SchemaUtility.getCLIENT().getSchema(candidateSchemas[i].uid),
							null, 
							null, 
							candidateSchemas[i].score, 
							-1,
							null);
				}				
			}
			
			System.out.println("Ranked results");				
			for (int i = 0; i < page; i++) {
				double trueScore = topResultsArray[i].getScore();
				System.out.println(i+"\tschema: "+topResultsArray[i].getSchema().getName() + "\t\t\tscore: "+trueScore);
			}
		}
		return topResultsArray;
	}

	
	
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
		CandidateSchema [] candidateSchemas = SchemaStoreIndex.searchIndex(Consts.LOCAL_INDEX_DIR, query);
		System.out.println("Found " + (candidateSchemas == null ? 0 : candidateSchemas.length) + " candidate schemas");
		return candidateSchemas;
	}
}
