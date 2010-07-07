// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import org.mitre.harmony.matchers.MatcherScores;

/** Documentation Matcher Class */
public class DocumentationOnlyMatcher extends DocumentationMatcher
{
	/** Returns the name of the match voter */
	public String getName() { return "Documentation Only Similarity"; }
	
	/** Generates scores for the specified elements */
	public MatcherScores match()
		{ return generateVoteScores(false); }
}