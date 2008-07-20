package org.openii.schemr.matcher;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mitre.schemastore.graph.GraphBuilder;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemr.QueryFragment;
import org.openii.schemr.SchemaUtility;


public class NGramMatcher implements Matcher {
	
	private static final Logger logger = Logger.getLogger(NGramMatcher.class.getName());

	private static final int NUM_GRAMS = 3;

	Schema candidateSchema = null; 
	ArrayList<QueryFragment> queryFragments = null;
	
	public NGramMatcher(Schema candidateSchema, ArrayList<QueryFragment> queryFragments) {
		this.candidateSchema = candidateSchema;
		this.queryFragments = queryFragments;
		
	}

	public static void main(String[] args) {
	}

	public SimilarityMatrix calculateSimilarityMatrix() {
		
		NGramQueryConverter converter = new NGramQueryConverter(NUM_GRAMS);
		
		HashMap<SchemaElement, TokenSet> colTSMap = new HashMap<SchemaElement, TokenSet>();
		HashMap<QueryFragment, TokenSet> rowTSMap = new HashMap<QueryFragment, TokenSet>();
		
		// make column objects -- candidate schema elements
		try {
			int id = this.candidateSchema.getId();
			ArrayList<SchemaElement> schemaElements = SchemaUtility.getCLIENT().getSchemaElements(id);
			schemaElements = GraphBuilder.build(schemaElements, id);
			for (SchemaElement e : schemaElements) {
				TokenSet ts = converter.getTokenSet(e);
				if (ts != null && ts.size() > 0) {
					colTSMap.put(e, ts);					
				} else {
					logger.warning("Ignored SchemaElement produced empty TokenSet: "+e);
				}
			}
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Failed to get schema elements", e);
		}
				
		// make row objects -- query fragments			
		for (QueryFragment qf : this.queryFragments) {
			ArrayList<QueryFragment> list = new ArrayList<QueryFragment>();
			QueryFragment.flatten(qf, list);
			for (QueryFragment q : list) {
				TokenSet ts = converter.getTokenSet(q);
				if (ts != null && ts.size() > 0) {
					rowTSMap.put(q, ts);
				} else {
					logger.warning("Ignored SchemaElement produced empty TokenSet: "+q);
				}
			}
		}

		SimilarityMatrix sm = new SimilarityMatrix(
				colTSMap.keySet().toArray(new Object[0]), 
				rowTSMap.keySet().toArray(new Object[0]));

		// fill it in
		for (SchemaElement se : colTSMap.keySet()) {
			for (QueryFragment qf : rowTSMap.keySet()) {
				TokenSet colTokens = colTSMap.get(se);
				TokenSet rowTokens = rowTSMap.get(qf);
				double overlap = countOverlap(colTokens, rowTokens);
				double totalQueryTokens = rowTokens.size();
				double score = overlap / totalQueryTokens;
				if (score > 0) logger.info("\t\t\t\tscore: "+ overlap + "/" + totalQueryTokens + " = " + score);
				sm.setScore(se, qf, score);
			}
		}
		
		return sm;
	}

	/*
	 * count overlap returns the # of overlapping tokens 
	 * (a, b, c) vs (a, a, d) = 1 overlapping token, not 2
	 */
	private int countOverlap(TokenSet tokenSet1, TokenSet tokenSet2) {
		int result = 0;
		for (Token t1 : tokenSet1.getTokens()) {
			for (Token t2 : tokenSet2.getTokens()) {
				if (t1.equals(t2)) result += 1;
			}			
		}
		if (result > 0) logger.info("\t\t\t"+tokenSet1.toString() + " | " + tokenSet2);

		return result;
	}

}
