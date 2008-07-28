package org.openii.schemr.matcher;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mitre.schemastore.graph.GraphBuilder;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemr.MatchSummary;
import org.openii.schemr.QueryFragment;
import org.openii.schemr.SchemaUtility;
import org.openii.schemr.matcher.SimilarityMatrix.ScoreEvidence;
import org.openii.schemr.preprocessor.Preprocessor;


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

	/**
	 * TODO: only using Entity and Attribute from corpus schemas
	 */
	public void buildTokenSets() {
		// make column objects -- candidate schema elements
		try {
			int id = this.candidateSchema.getId();
			ArrayList<SchemaElement> schemaElements = SchemaUtility.getCLIENT().getSchemaElements(id);
			schemaElements = new GraphBuilder(schemaElements, id).getSchemaElements();
			for (SchemaElement e : schemaElements) {
				if (e instanceof Entity || e instanceof Attribute) {
					if (e != null && !"".equals(e.getName().trim())) {
						TokenSet ts = new TokenSet(e.getName().trim());
						ts.add(new Token(ts.getName()));
						this.colTSMap.put(e, ts);
					} else {
//						logger.warning("Ignored empty/null SchemaElement: "+e);
					}					
				} else {
//					logger.warning("Ignored SchemaElement: "+e.getClass().getSimpleName());					
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
//					logger.warning("Ignored empty/null QueryFragment: "+q);
				}
			}
		}
	}
	
	// TODO test me!
	public void applyPreprocessor(Preprocessor preprocessor) {
		for (Entry<SchemaElement,TokenSet> e : colTSMap.entrySet()) {
			TokenSet ts = e.getValue();
			TokenSet newTS = new TokenSet(ts.getName());
			newTS.add(preprocessor.process(ts));
			e.setValue(newTS);
		}
		for (Entry<QueryFragment,TokenSet> e : rowTSMap.entrySet()) {
			TokenSet ts = e.getValue();
			TokenSet newTS = new TokenSet(ts.getName());
			newTS.add(preprocessor.process(ts));
			e.setValue(newTS);
		}		
	}
	
	public SimilarityMatrix calculateSimilarityMatrix() {
		this.similarityMatrix = new SimilarityMatrix(
				this.colTSMap.keySet().toArray(new Object[0]), 
				this.rowTSMap.keySet().toArray(new Object[0]));
		return this.similarityMatrix;
	}
	
	public MatchSummary getMatchSummary() {
		if (this.colTSMap == null || this.colTSMap.isEmpty() ||
				this.rowTSMap == null || this.rowTSMap.isEmpty() ||
				this.similarityMatrix == null) {
			logger.warning("This schema has no matchable elements: "+ this.candidateSchema.getName());
			return null;
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
