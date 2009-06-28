package org.openii.schemrserver.search;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.Graph;
import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.mitre.schemastore.porters.schemaImporters.SchemaProperties;
import org.openii.schemrserver.SchemaUtility;
import org.openii.schemrserver.indexer.SchemaStoreIndex;
import org.openii.schemrserver.indexer.SchemaStoreIndex.CandidateSchema;
import org.openii.schemrserver.matcher.EditDistanceMatcher;
import org.openii.schemrserver.matcher.Matcher;
import org.openii.schemrserver.matcher.NGramMatcher;

/**
 * 
 */
public class Query {

//	private static final Logger logger = Logger.getLogger(Query.class.getName());

	private SchemaProperties querySchema = null;
	private ArrayList<SchemaElement> querySchemaElements = null;
	private HashMap<String,String> queryKeywords = null;
	

	public Query(HashMap<String,String> queryKeywords) {
		this.querySchema = null;
		this.querySchemaElements = new ArrayList<SchemaElement>();
		this.queryKeywords = queryKeywords;
		
	}
	
	public Query(SchemaProperties schemaProps, ArrayList<SchemaElement> querySchemaElements, HashMap<String,String> queryKeywords) {
		this.querySchema = schemaProps;
		this.querySchemaElements = querySchemaElements;
		this.queryKeywords = queryKeywords;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Schema: " + this.querySchema 
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
	
	public String toKeywordSearchString() {
		StringBuffer sb = new StringBuffer();
		
		for (Entry<String, String> e : queryKeywords.entrySet()) {
			String key = e.getKey();
			String val = e.getValue();
			
			if (!key.equals("")) {
				if (!val.equals("")) {
					sb.append(val+":");
				}
				sb.append(key+" ");				
			}
		}
		
		if (this.querySchemaElements != null) {
			// the query schema could be null
			if (this.querySchema != null) {
				String schemaName = this.querySchema.getName();
				if (schemaName != null && !schemaName.equals("")) {
					sb.append("title:" + schemaName);
				}
				String schemaDesc = this.querySchema.getDescription();
				if (schemaDesc != null && !schemaDesc.equals("")) {
					sb.append(" " + schemaDesc);
				}
			}

			for (SchemaElement s : this.querySchemaElements) {
				String type = SchemaUtility.getType(s);
				String name = s.getName();
				if (SchemaStoreIndex.TYPES_SET.contains(type) 
						&& name != null && !name.equals("")) {
					sb.append(" "+type+":"+s.getName());					
				}				
			}			
		}
		System.out.println("Query flattened: "+sb.toString());
		return sb.toString();		
	}

	public MatchSummary [] processQuery(CandidateSchema [] candidateSchemas) throws RemoteException {
		QueryParser qp = new QueryParser(this);
		
		ArrayList<MatchSummary> matchSummaries = new ArrayList<MatchSummary>();
		
		for (int i = 0; i < candidateSchemas.length; i++) {
			CandidateSchema candidateSchema = candidateSchemas[i];
			Schema s = SchemaUtility.getCLIENT().getSchema(candidateSchema.uid);
			ArrayList<QueryFragment> queryFragments = qp.getQueryFragments();
			MatchSummary ms = match(s, queryFragments);
			if (ms != null) matchSummaries.add(ms);	
		}
		
		// sort according to score
		MatchSummary [] msarray = matchSummaries.toArray(new MatchSummary[0]);
		Arrays.sort(msarray);
		
		return msarray;
	}

	public MatchSummary match(Schema candidateSchema, ArrayList<QueryFragment> queryFragments) {
		Matcher nGram, editDist = null;
		nGram = new NGramMatcher(candidateSchema, queryFragments);
		editDist = new EditDistanceMatcher(candidateSchema, queryFragments);
		nGram.calculateSimilarityMatrix();
		editDist.calculateSimilarityMatrix();
		MatchSummary out = nGram.getMatchSummary(); 
		
		// FIXME what is 8!?
		
		out.score = out.score * 0.5 + ((editDist.getMatchSummary().score + 8.0) / 16);
		
		
		return out;
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
			if (query == null) {
				throw new IllegalArgumentException("ERROR: query must not be null");
			}
			// Generate the schema
			String schemaName = ""; String author = ""; String description = "";
			SchemaProperties schemaProps = query.getQuerySchemaProps();
			if (schemaProps != null) {
				schemaName = schemaProps.getName(); author = schemaProps.getAuthor(); description = schemaProps.getDescription();
			}
			String uri = ""; String type = "";			
			Schema schema = new Schema(Integer.MIN_VALUE, schemaName, author, uri, type, description, false);			

			// generate a hierarchical model 
			ArrayList<SchemaElement> se = query.getQuerySchemaElements();
			if (se != null) {
				Graph g = new Graph(schema,se);
				HierarchicalGraph hg = new HierarchicalGraph(g, null);

				QueryFragment root = new QueryFragment(schemaName, se, hg);
				this.queryFragments.add(root);
			}
			
			HashMap<String,String> k = query.getQueryKeywords();
			for (Iterator<String> iter = k.keySet().iterator(); iter.hasNext(); ) {
				String name = iter.next();
				String kind = k.get(name);
				this.queryFragments.add(new QueryFragment(name, kind));				
			}			
		}

		public ArrayList<QueryFragment> getQueryFragments() {
			return this.queryFragments;
		}

	}

	/**
	 * @return the querySchema
	 */
	public SchemaProperties getQuerySchemaProps() {
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
