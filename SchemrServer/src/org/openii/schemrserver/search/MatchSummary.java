package org.openii.schemrserver.search;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.Schema;
import org.openii.schemrserver.matcher.SimilarityMatrix;
import org.openii.schemrserver.matcher.SimilarityMatrix.ScoreEvidence;

public class MatchSummary implements Comparable<MatchSummary> {

	Schema schema;
	ArrayList<QueryFragment> queryFragments;
	SimilarityMatrix similarityMatrix;
	double score;
	HashMap<QueryFragment, ScoreEvidence> queryFragmentToScoreEvidencePairMap;
	
	public MatchSummary(
			Schema schema,
			ArrayList<QueryFragment> queryFragments,
			double score,
			HashMap<QueryFragment, ScoreEvidence> queryFragmentToScoreEvidencePairMap) {
		super();
		this.schema = schema;
		this.queryFragments = queryFragments;
		this.score = score;
		this.queryFragmentToScoreEvidencePairMap = queryFragmentToScoreEvidencePairMap;
	}

	/**
	 * @return the schema
	 */
	public Schema getSchema() {
		return schema;
	}

	/**
	 * @return the queryFragments
	 */
	public ArrayList<QueryFragment> getQueryFragments() {
		return queryFragments;
	}

	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * @return the queryFragmentToScoreEvidencePairMap
	 */
	public HashMap<QueryFragment, ScoreEvidence> getQueryFragmentToScoreEvidencePairMap() {
		return queryFragmentToScoreEvidencePairMap;
	}

	public int compareTo(MatchSummary o) {
		if (o.getScore() == this.getScore()) {
			return 0;
		} else if (o.getScore() > this.getScore()) {
			return 1;
		} else {
			return -1;
		}
	}

}
