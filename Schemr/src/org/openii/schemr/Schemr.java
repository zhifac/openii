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
//			candidateSchemas.add(SchemaUtility.getCLIENT().getSchema(400));
			
			querySchema = SchemaUtility.getCLIENT().getSchema(17);
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

		MatchSummary [] msarray = q.processQuery(candidateSchemas);

		
		int i = 0;
		System.out.println(MATCHER+" ranked results");
		for (MatchSummary matchSummary : msarray) {
			i++;
			System.out.println(i+"\tschema: "+matchSummary.getSchema().getName() + "\t\t\tscore: "+matchSummary.getScore());
			Visualizer v;
		}
		
		SearchResultsUI app;
		app = new SearchResultsUI(msarray);
	}
	
}
