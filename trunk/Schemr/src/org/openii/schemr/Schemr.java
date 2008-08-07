package org.openii.schemr;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemr.viz.SearchResultsUI;

public class Schemr {

	private static final Logger logger = Logger.getLogger(Schemr.class.getName());

	public static void main(String[] args) {

		// get query
		Query q = getQuery();

		// filter for candidate schemas
		ArrayList<Schema> candidateSchemas = getCandidateSchemas(q);

		// process query against candidate schemas
		MatchSummary [] msarray = q.processQuery(candidateSchemas);

		int page = Math.min(30, msarray.length);
		System.out.println("Ranked results");
		MatchSummary [] topResultsArray = new MatchSummary [page];
		for (int i = 0; i < page; i++) {
			topResultsArray[i] = msarray[i];
			System.out.println(i+"\tschema: "+topResultsArray[i].getSchema().getName() + "\t\t\tscore: "+topResultsArray[i].getScore());
		}
		
		SearchResultsUI app;
		app = new SearchResultsUI(topResultsArray);
	}

	public static Query getQuery() {
		Schema querySchema = null;
		ArrayList<SchemaElement> querySchemaElements = null;
		try {

//			querySchema = SchemaUtility.getCLIENT().getSchema(52384);
			querySchema = SchemaUtility.getCLIENT().getSchema(17);
			querySchemaElements = SchemaUtility.getCLIENT().getSchemaElements(querySchema.getId());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	System.out.println("Searching with: "+querySchema.getName());

		HashMap<String,String> queryKeywords = new HashMap<String,String>();
		queryKeywords.put("elephant", "");
		queryKeywords.put("africa", "entity");
		queryKeywords.put("telephone", "attribute");

		// construct new query
		Query q = new Query(querySchema, querySchemaElements, queryKeywords);
		return q;
	}

	public static ArrayList<Schema> getCandidateSchemas(Query query) {
		ArrayList<Schema> candidateSchemas = null;
		try {
//			candidateSchemas = SchemaUtility.getCLIENT().getSchemas();
			candidateSchemas = new ArrayList<Schema>();
			candidateSchemas.add(SchemaUtility.getCLIENT().getSchema(52384));			
			candidateSchemas.add(SchemaUtility.getCLIENT().getSchema(17));			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return candidateSchemas;
	}
	
}
