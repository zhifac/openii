package org.openii.schemr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import org.mitre.schemastore.graph.GraphBuilder;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemr.matcher.EditDistanceMatcher;
import org.openii.schemr.matcher.Matcher;
import org.openii.schemr.matcher.NGramMatcher;
import org.openii.schemr.matcher.SimilarityMatrix;

/**
 * 
 */
public class Query {

	private static final Logger logger = Logger.getLogger(Query.class.getName());

	private Schema querySchema = null;
	private ArrayList<SchemaElement> querySchemaElements = null;
	private HashMap<String,String> queryKeywords = null;
	

	public Query(HashMap<String,String> queryKeywords) {
		this.querySchema = null;
		this.querySchemaElements = new ArrayList<SchemaElement>();
		this.queryKeywords = queryKeywords;
		
	}
	public Query(Schema querySchema, ArrayList<SchemaElement> querySchemaElements, HashMap<String,String> queryKeywords) {
		this.querySchema = querySchema;
		this.queryKeywords = queryKeywords;
		this.querySchemaElements = new GraphBuilder(querySchemaElements, querySchema.getId()).getSchemaElements();
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
			if (s instanceof Attribute) {
				sb.append(":"+(((Attribute) s).getEntityID())+"\n");
//			} else if (s instanceof Containment) {
//				Containment cnt = (Containment) s;
//				SchemaElement p, c;
//				try {
//					p = SchemaUtility.getCLIENT().getSchemaElement(cnt.getParentID());
//					c = SchemaUtility.getCLIENT().getSchemaElement(cnt.getChildID());
//					if (p!=null) sb.append("\tParent: "+p.getName()+ " " + p.getClass().getSimpleName() + " "+p.getId());
//					if (c!=null) sb.append("\tChild: "+c.getName()+" "+c.getClass().getSimpleName()+ " "+c.getId());
//				} catch (RemoteException e) {
//					e.printStackTrace();
//				}
			} else sb.append("\n");
		}
		return sb.toString();
	}

	public MatchSummary [] processQuery(ArrayList<Schema> candidateSchemas) {
		QueryParser qp = new QueryParser(this);				
		
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
		
		return msarray;
	}

	public MatchSummary match(Schema candidateSchema, ArrayList<QueryFragment> queryFragments) {
		
		Matcher m = null;
		if (Matcher.MATCHER == Matcher.NGRAM) {
			m = new NGramMatcher(candidateSchema, queryFragments);			
		} else if (Matcher.MATCHER == Matcher.EDIT_DISTANCE) {
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
