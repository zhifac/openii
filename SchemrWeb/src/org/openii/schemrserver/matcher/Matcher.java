package org.openii.schemrserver.matcher;

import org.openii.schemrserver.search.MatchSummary;
import org.openii.schemrserver.matcher.Preprocessor;

public interface Matcher {

	public SimilarityMatrix calculateSimilarityMatrix();

	public void buildTokenSets();

	public void updateTokenSets();
	
	public MatchSummary getMatchSummary();

	public void applyPreprocessor(Preprocessor preprocessor);


}
