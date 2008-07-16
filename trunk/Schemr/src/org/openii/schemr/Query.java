package org.openii.schemr;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mitre.schemastore.graph.GraphBuilder;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

/**
 * 
 */
public class Query {

	private static final Logger logger = Logger.getLogger("org.openii.schemr.Query");
	static {
		logger.setLevel(Level.ALL);
	}
	private Schema m_querySchema = null;
	private ArrayList<SchemaElement> m_querySchemaElements = null;
	private HashMap<String,String> m_queryKeywords = null;
	
	public Query() {
		m_querySchema = new Schema();
		m_querySchemaElements = new ArrayList<SchemaElement>();
		m_queryKeywords = new HashMap<String,String>();
	}
	
	public Query(Schema querySchema, ArrayList<SchemaElement> querySchemaElements, HashMap<String,String> queryKeywords) {
		m_querySchema = querySchema;
		m_queryKeywords = queryKeywords;
		m_querySchemaElements = GraphBuilder.build(querySchemaElements, querySchema.getId());
		
		StringBuffer sb = new StringBuffer();
		sb.append("Schema: " + m_querySchema.getName() 
				+ ", # elements " + m_querySchemaElements.size() 
				+ ", # keywords " + m_queryKeywords.size()
				+ "\n");
//		for (SchemaElement s : querySchemaElements) {
//			sb.append("\t"+SchemaUtility.getType(s)+":"+s.getName()+":"+s.getId());
//			if (s instanceof Attribute) sb.append(":"+(((Attribute) s).getEntityID())+"\n");
//			else sb.append("\n");
//		}
		logger.info(sb.toString());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// take a set of schema, keywords, and data
		
		// find a schema with which to query
		ArrayList<Schema> allSchemas = null;
		Schema querySchema = null;
		ArrayList<SchemaElement> querySchemaElements = null;
		try {
			allSchemas = SchemaUtility.CLIENT.getSchemas();
			querySchema = allSchemas.remove(0);
			querySchemaElements = SchemaUtility.CLIENT.getSchemaElements(querySchema.getId());
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
		
		logger.info("All done.");
	}

	

	/**
	 * QueryParser converts a query into format(s) needed by matchers
	 * Given a matcher, it should convert the query to input required by the matcher
	 */
	public static class QueryParser {

		// QueryObject is a DAG forest of attribute-value pairs
		ArrayList<QueryFragment> m_queryFragments = null;
	
		public QueryParser(Query query) {
			m_queryFragments = new ArrayList<QueryFragment>();			
			buildQueryFragments(query);
		}
		
		private void buildQueryFragments(Query query) {
			Schema s = query.getQuerySchema();			
			ArrayList<SchemaElement> se = query.getQuerySchemaElements();			
			m_queryFragments.add(new QueryFragment(s, se));
			
			HashMap<String,String> k = query.getQueryKeywords();
			for (Iterator<String> iter = k.keySet().iterator(); iter.hasNext(); ) {
				String name = iter.next();
				String type = k.get(name);
				m_queryFragments.add(new QueryFragment(name, type));				
			}			
		}

		public ArrayList<QueryFragment> getQueryFragments() {
			return m_queryFragments;
		}

		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			for (QueryFragment f : m_queryFragments) {
				sb.append(f.toString());
			}
			return sb.toString();
		}

	}

	/**
	 * @return the querySchema
	 */
	public Schema getQuerySchema() {
		return m_querySchema;
	}

	/**
	 * @return the querySchemaElements
	 */
	public ArrayList<SchemaElement> getQuerySchemaElements() {
		return m_querySchemaElements;
	}

	/**
	 * @return the queryKeywords
	 */
	public HashMap<String, String> getQueryKeywords() {
		return m_queryKeywords;
	}

}
