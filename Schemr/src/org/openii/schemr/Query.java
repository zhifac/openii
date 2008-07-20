package org.openii.schemr;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import org.mitre.schemastore.graph.GraphBuilder;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
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
	
	public Query() {
		this.querySchema = new Schema();
		this.querySchemaElements = new ArrayList<SchemaElement>();
		this.queryKeywords = new HashMap<String,String>();
	}
	
	public Query(Schema querySchema, ArrayList<SchemaElement> querySchemaElements, HashMap<String,String> queryKeywords) {
		this.querySchema = querySchema;
		this.queryKeywords = queryKeywords;
		this.querySchemaElements = GraphBuilder.build(querySchemaElements, querySchema.getId());
		logger.info(this.toString());
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
			candidateSchemas = SchemaUtility.getCLIENT().getSchemas();
			querySchema = candidateSchemas.remove(0);
			querySchemaElements = SchemaUtility.getCLIENT().getSchemaElements(querySchema.getId());
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		HashMap<String,String> queryKeywords = new HashMap<String,String>();
		queryKeywords.put("elephant", "");
		queryKeywords.put("africa", "entity");
		queryKeywords.put("telephone", "attribute");
		
		Query q = new Query(querySchema, querySchemaElements, queryKeywords);
		
		QueryParser qp = new QueryParser(q);				
		logger.info(qp.toString());
		
		
//		candidateSchemas --> text terms
		for (Schema candidateSchema : candidateSchemas) {
			Matcher m = new NGramMatcher(candidateSchema, qp.getQueryFragments());
			SimilarityMatrix sm = m.calculateSimilarityMatrix();
			logger.info(sm.toString());
		}
		
		logger.info("All done.");
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
