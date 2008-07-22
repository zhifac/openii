package org.openii.schemr.matcher;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemr.QueryFragment;

public class NGramQueryConverter implements QueryConverter {

	private static final Logger logger = Logger.getLogger(NGramQueryConverter.class.getName());

	private final int numGrams;

	public NGramQueryConverter(int numGrams) {
		this.numGrams = numGrams;
	}

	public TokenSet getTokenSet(QueryFragment q) {
		return new TokenSet(q.getName());
	}

	public TokenSet getTokenSet(SchemaElement e) {
		return new TokenSet(e.getName());
	}

	public ArrayList<Token> getTokenSet(String s) {
		if (s == null)
			throw new IllegalArgumentException("Unexpected null input");
		ArrayList<Token> list = null;
		if (s != null && !"".equals(s.trim())) {
			list = new ArrayList<Token>();
			ArrayList<String> ngrams = MatcherUtility.getNGrams(s, this.numGrams);
			for (String ngram : ngrams) {
				if (ngram == null || "".equals(ngram)) {
					logger.warning("Skipped null or empty ngram");
					continue;
				}
				list.add(new Token(ngram));
			}
		}
		return list;
	}
}
