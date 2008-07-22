package org.openii.schemr.matcher;

import org.openii.schemr.MatchSummary;
import org.openii.schemr.preprocessor.Preprocessor;

public interface Matcher {

	public SimilarityMatrix calculateSimilarityMatrix();

	public void buildTokenSets();

	public void updateTokenSets();
	
	public MatchSummary getMatchSummary();

	public void applyPreprocessor(Preprocessor preprocessor);


}
