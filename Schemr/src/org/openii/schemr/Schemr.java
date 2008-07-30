package org.openii.schemr;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemr.viz.SearchResultsUI;
import org.openii.schemr.viz.Visualizer;

public class Schemr {

	private static final Logger logger = Logger.getLogger(Schemr.class.getName());

	public static String MATCHER = "NGRAM";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// take a set of schema, keywords, and data
		
		// find a schema with which to query
		ArrayList<Schema> candidateSchemas = null;
		Schema querySchema = null;
		ArrayList<SchemaElement> querySchemaElements = null;
		try {
			candidateSchemas = SchemaUtility.getCLIENT().getSchemas();
//			candidateSchemas = new ArrayList<Schema>();
//			candidateSchemas.add(SchemaUtility.getCLIENT().getSchema(17));
			

			querySchema = SchemaUtility.getCLIENT().getSchema(17);
//			querySchema = SchemaUtility.getCLIENT().getSchema(52453);
			querySchemaElements = SchemaUtility.getCLIENT().getSchemaElements(querySchema.getId());
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	System.out.println("Searching with: "+querySchema.getName());

		HashMap<String,String> queryKeywords = new HashMap<String,String>();
		queryKeywords.put("elephant", "");
		queryKeywords.put("africa", "entity");
		queryKeywords.put("telephone", "attribute");
		
		Query q = new Query(querySchema, querySchemaElements, queryKeywords);
//		System.out.println(q.toString());
		MatchSummary [] msarray = q.processQuery(candidateSchemas);

		int page = 30;
		
		System.out.println(MATCHER+" ranked results");
		MatchSummary [] topResultsArray = new MatchSummary [page];
		for (int i = 0; i < page; i++) {
			topResultsArray[i] = msarray[i];
			System.out.println(i+"\tschema: "+topResultsArray[i].getSchema().getName() + "\t\t\tscore: "+topResultsArray[i].getScore());
		}
		
		SearchResultsUI app;
		app = new SearchResultsUI(topResultsArray);
	}
	
}
