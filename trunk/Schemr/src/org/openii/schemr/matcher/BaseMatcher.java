package org.openii.schemr.matcher;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mitre.schemastore.graph.GraphBuilder;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemr.MatchSummary;
import org.openii.schemr.QueryFragment;
import org.openii.schemr.SchemaUtility;
import org.openii.schemr.matcher.SimilarityMatrix.ScoreEvidence;


public abstract class BaseMatcher implements Matcher {
	
	private static final Logger logger = Logger.getLogger(BaseMatcher.class.getName());

	Schema candidateSchema; 
	ArrayList<QueryFragment> queryFragments;
	
	HashMap<SchemaElement, TokenSet> colTSMap;
	HashMap<QueryFragment, TokenSet> rowTSMap;
	SimilarityMatrix similarityMatrix;

	public BaseMatcher(Schema candidateSchema, ArrayList<QueryFragment> queryFragments) {
		this.candidateSchema = candidateSchema;
		this.queryFragments = queryFragments;
		
		this.colTSMap = new HashMap<SchemaElement, TokenSet>();
		this.rowTSMap = new HashMap<QueryFragment, TokenSet>();
		
		buildTokenSets();
	}

	public void buildTokenSets() {
		// make column objects -- candidate schema elements
		try {
			int id = this.candidateSchema.getId();
			ArrayList<SchemaElement> schemaElements = SchemaUtility.getCLIENT().getSchemaElements(id);
			schemaElements = GraphBuilder.build(schemaElements, id);
			for (SchemaElement e : schemaElements) {
				if (e != null && !"".equals(e.getName().trim())) {
					TokenSet ts = new TokenSet(e.getName().trim());
					ts.add(new Token(ts.getName()));
					this.colTSMap.put(e, ts);
				} else {
					logger.warning("Ignored empty/null SchemaElement: "+e);
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
				if (q != null && !"".equals(q.getName().trim())) {
					TokenSet ts = new TokenSet(q.getName().trim());
					ts.add(new Token(ts.getName()));
					this.rowTSMap.put(q, ts);
				} else {
					logger.warning("Ignored empty/null QueryFragment: "+q);
				}
			}
		}
	}
	
	public MatchSummary getMatchSummary() {
		if (this.colTSMap == null || this.colTSMap.isEmpty() ||
				this.rowTSMap == null || this.rowTSMap.isEmpty() ||
				this.similarityMatrix == null) {
			throw new IllegalStateException("Matcher is not ready to produce MatchSummary. Did you forget to call a method?");
		}
		
		double score = this.similarityMatrix.getTotalScore();
		return new MatchSummary(this.candidateSchema, this.queryFragments, score, buildEvidenceMap());
	}

	private HashMap<QueryFragment, ScoreEvidence> buildEvidenceMap() {
		
		HashMap<QueryFragment, ScoreEvidence> result = new HashMap<QueryFragment, ScoreEvidence>();
		
		for (QueryFragment qf : this.queryFragments) {
			ArrayList<QueryFragment> list = new ArrayList<QueryFragment>();
			QueryFragment.flatten(qf, list);
			for (QueryFragment q : list) {
				ScoreEvidence maxScoreEvidence = this.similarityMatrix.getMaxScoreEvidence((Object) q);
				result.put(q, maxScoreEvidence);
			}
		}
		return result;
	}


}
