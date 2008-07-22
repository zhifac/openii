package org.openii.schemr.matcher;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemr.QueryFragment;
import org.openii.schemr.preprocessor.Preprocessor;


public class NGramMatcher extends BaseMatcher implements Matcher {
	
	private static final Logger logger = Logger.getLogger(NGramMatcher.class.getName());

	private static final int NUM_GRAMS = 3;

	public NGramMatcher(Schema candidateSchema, ArrayList<QueryFragment> queryFragments) {
		super(candidateSchema, queryFragments);
		
		applyPreprocessor(Preprocessor.TOKENIZER);
		applyPreprocessor(Preprocessor.LOWERCASER);
		updateTokenSets();
	}
	
	// TODO test me!
	public void updateTokenSets() {		
		NGramQueryConverter converter = new NGramQueryConverter(NUM_GRAMS);

		for (Entry<SchemaElement,TokenSet> e : colTSMap.entrySet()) {
			TokenSet existingTS = e.getValue();
			TokenSet newTS = new TokenSet(existingTS.getName());
			for (Token t : existingTS.getTokens()) {
				ArrayList<Token> list = converter.getTokenSet(t.getToken());
				if (list != null && !list.isEmpty()) {
					newTS.add(list);
				}
			}
			e.setValue(newTS);
		}

		for (Entry<QueryFragment,TokenSet> e : rowTSMap.entrySet()) {
			TokenSet existingTS = e.getValue();
			TokenSet newTS = new TokenSet(existingTS.getName());
			for (Token t : existingTS.getTokens()) {
				ArrayList<Token> list = converter.getTokenSet(t.getToken());
				if (list != null && !list.isEmpty()) {					
					newTS.add(list);
				}
			}
			e.setValue(newTS);
		}		
	}

	public SimilarityMatrix calculateSimilarityMatrix() {
		super.calculateSimilarityMatrix();

		// fill it in
		for (SchemaElement se : colTSMap.keySet()) {
			for (QueryFragment qf : this.rowTSMap.keySet()) {
				TokenSet colTokens = colTSMap.get(se);
				TokenSet rowTokens = this.rowTSMap.get(qf);
				double overlap = countOverlap(colTokens, rowTokens);
				// TODO: alternative approach: use row+col size for symmetric score
				double totalQueryTokens = rowTokens.size();
				double score = overlap / totalQueryTokens;
				similarityMatrix.setScore(se, qf, score);
			}
		}
		return similarityMatrix;
	}

	/**
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
		return result;
	}


}
