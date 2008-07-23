package org.openii.schemr;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mitre.schemastore.graph.GraphBuilder;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemr.matcher.EditDistanceMatcher;
import org.openii.schemr.matcher.Matcher;
import org.openii.schemr.matcher.NGramMatcher;
import org.openii.schemr.matcher.SimilarityMatrix;
import org.openii.schemr.viz.PrefuseVisualizer;
import org.openii.schemr.viz.Visualizer;

/**
 * 
 */
public class Query {

	private static final Logger logger = Logger.getLogger(Query.class.getName());

	private Schema querySchema = null;
	private ArrayList<SchemaElement> querySchemaElements = null;
	private HashMap<String,String> queryKeywords = null;
	
	public Query() {
		this.querySchema = new Schema();
		this.querySchemaElements = new ArrayList<SchemaElement>();
		this.queryKeywords = new HashMap<String,String>();
	}
	
	public Query(Schema querySchema, ArrayList<SchemaElement> querySchemaElements, HashMap<String,String> queryKeywords) {
		this.querySchema = querySchema;
		this.queryKeywords = queryKeywords;
		this.querySchemaElements = GraphBuilder.build(querySchemaElements, querySchema.getId());
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Schema: " + this.querySchema.getName() 
				+ ", # elements " + this.querySchemaElements.size() 
				+ ", # keywords " + this.queryKeywords.size()
				+ "\n");
		for (SchemaElement s : this.querySchemaElements) {
			sb.append("\t"+SchemaUtility.getType(s)+":"+s.getName()+":"+s.getId());
			if (s instanceof Attribute) sb.append(":"+(((Attribute) s).getEntityID())+"\n");
			else sb.append("\n");
		}
		return super.toString();
	}
	
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
//			candidateSchemas = SchemaUtility.getCLIENT().getSchemas();			
			candidateSchemas = new ArrayList<Schema>();
			candidateSchemas.add(SchemaUtility.getCLIENT().getSchema(16));
			
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

		MATCHER = "NGRAM";
		q.processQuery(candidateSchemas);
//		MATCHER = "EDITDISTANCE";
//		q.processQuery(candidateSchemas);

	System.out.println("All done.");		
	}

	public void processQuery(ArrayList<Schema> candidateSchemas) {
		QueryParser qp = new QueryParser(this);				
//	System.out.println(qp.toString());
		
		ArrayList<MatchSummary> matchSummaries = new ArrayList<MatchSummary>();
		
		for (int i = 0; i < candidateSchemas.size(); i++) {
			Schema candidateSchema = candidateSchemas.get(i);
			ArrayList<QueryFragment> queryFragments = qp.getQueryFragments();
			MatchSummary ms = match(candidateSchema, queryFragments);
			if (ms != null) matchSummaries.add(ms);	
		}
		
		// sort according to score
		MatchSummary [] msarray = matchSummaries.toArray(new MatchSummary[0]);
		Arrays.sort(msarray);

		int i = 0;
	System.out.println(MATCHER+" ranked results");
		for (MatchSummary matchSummary : msarray) {
			i++;
	System.out.println(i+"\tschema: "+matchSummary.getSchema().getName() + "\t\t\tscore: "+matchSummary.getScore());
			Visualizer v;
			try {
				v = new PrefuseVisualizer(matchSummary);
				v.show();
			} catch (RemoteException e) {
				logger.log(Level.SEVERE, "Connection to SchemaStore failed.", e);
				break;
			}
		}
	}

	private static String MATCHER = null;

	public MatchSummary match(Schema candidateSchema, ArrayList<QueryFragment> queryFragments) {
		
		Matcher m = null;
		if (MATCHER.equals("NGRAM")) {
			m = new NGramMatcher(candidateSchema, queryFragments);			
		} else if (MATCHER.equals("EDITDISTANCE")) {
			m = new EditDistanceMatcher(candidateSchema, queryFragments);			
		}

		SimilarityMatrix sm = m.calculateSimilarityMatrix();
//	System.out.println(sm.toString());
		// match summary contains schema, queryfragments, score, and evidence
		return m.getMatchSummary();
	}

	

	/**
	 * QueryParser converts a query into format(s) needed by matchers
	 * Given a matcher, it should convert the query to input required by the matcher
	 */
	public static class QueryParser {

		// QueryObject is a DAG forest of attribute-value pairs
		ArrayList<QueryFragment> queryFragments = null;
	
		public QueryParser(Query query) {
			this.queryFragments = new ArrayList<QueryFragment>();			
			buildQueryFragments(query);
		}
		
		private void buildQueryFragments(Query query) {
			Schema s = query.getQuerySchema();			
			ArrayList<SchemaElement> se = query.getQuerySchemaElements();			
			this.queryFragments.add(new QueryFragment(s, se));
			
			HashMap<String,String> k = query.getQueryKeywords();
			for (Iterator<String> iter = k.keySet().iterator(); iter.hasNext(); ) {
				String name = iter.next();
				String type = k.get(name);
				this.queryFragments.add(new QueryFragment(name, type));				
			}			
		}

		public ArrayList<QueryFragment> getQueryFragments() {
			return this.queryFragments;
		}

		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			for (QueryFragment f : this.queryFragments) {
				sb.append(f.toString());
			}
			return sb.toString();
		}

	}

	/**
	 * @return the querySchema
	 */
	public Schema getQuerySchema() {
		return this.querySchema;
	}

	/**
	 * @return the querySchemaElements
	 */
	public ArrayList<SchemaElement> getQuerySchemaElements() {
		return this.querySchemaElements;
	}

	/**
	 * @return the queryKeywords
	 */
	public HashMap<String, String> getQueryKeywords() {
		return this.queryKeywords;
	}

}
