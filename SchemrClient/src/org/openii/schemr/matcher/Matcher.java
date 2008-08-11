package org.openii.schemr.matcher;

import org.openii.schemr.client.model.MatchSummary;
import org.openii.schemr.preprocessor.Preprocessor;

public interface Matcher {

	public static int NGRAM = 0;
	public static int EDIT_DISTANCE = 1;
	public static int MATCHER = NGRAM;

	public SimilarityMatrix calculateSimilarityMatrix();

	public void buildTokenSets();

	public void updateTokenSets();
	
	public MatchSummary getMatchSummary();

	public void applyPreprocessor(Preprocessor preprocessor);


}
