package org.openii.schemrserver.matcher;

import java.util.ArrayList;

import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.openii.schemrserver.search.MatchSummary;
import org.openii.schemrserver.search.QueryFragment;
import org.openii.schemrserver.matcher.Preprocessor;

public interface Matcher {

	public SimilarityMatrix calculateSimilarityMatrix();

	public void buildTokenSets();

	public void updateTokenSets();
	
	public MatchSummary getMatchSummary(HierarchicalGraph hg, ArrayList<QueryFragment> queryFragments);

	public void applyPreprocessor(Preprocessor preprocessor);


}
