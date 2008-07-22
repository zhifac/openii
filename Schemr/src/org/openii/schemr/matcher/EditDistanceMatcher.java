package org.openii.schemr.matcher;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemr.QueryFragment;
import org.openii.schemr.preprocessor.Preprocessor;

public class EditDistanceMatcher extends BaseMatcher implements Matcher {

	private static final Logger logger = Logger.getLogger(EditDistanceMatcher.class.getName());

	public EditDistanceMatcher(Schema candidateSchema, ArrayList<QueryFragment> queryFragments) {
		super(candidateSchema, queryFragments);
		applyPreprocessor(Preprocessor.LOWERCASER);
		updateTokenSets();
	}

	public SimilarityMatrix calculateSimilarityMatrix() {
		super.calculateSimilarityMatrix();

		// fill it in
		for (SchemaElement se : colTSMap.keySet()) {
			for (QueryFragment qf : this.rowTSMap.keySet()) {
				TokenSet colTokens = colTSMap.get(se);
				TokenSet rowTokens = this.rowTSMap.get(qf);
				if (colTokens.size() != 1 || rowTokens.size() != 1) {
					logger.warning("should only be 1 token in TokenSet for this matcher, instead: "+colTokens.size()+", "+rowTokens.size());
				}
				String c = colTokens.getTokens().get(0).getToken();
				String r = rowTokens.getTokens().get(0).getToken();
				double distanceScore = -1*getLevenshteinDistance(c, r);
//			System.out.println("Edit distance between "+c+", "+r+" :"+distanceScore);
				similarityMatrix.setScore(se, qf, distanceScore);
			}
		}
		return similarityMatrix;
	}

	
	public void updateTokenSets() {
		// nothing to do for now
	}

	public static int getLevenshteinDistance(String s, String t) {
		if (s == null || t == null) {
			throw new IllegalArgumentException("Strings must not be null");
		}

		int n = s.length(); // length of s
		int m = t.length(); // length of t

		if (n == 0) {
			return m;
		} else if (m == 0) {
			return n;
		}

		int p[] = new int[n + 1]; //'previous' cost array, horizontally
		int d[] = new int[n + 1]; // cost array, horizontally
		int _d[]; //placeholder to assist in swapping p and d

		// indexes into strings s and t
		int i; // iterates through s
		int j; // iterates through t

		char t_j; // jth character of t

		int cost; // cost

		for (i = 0; i <= n; i++) {
			p[i] = i;
		}

		for (j = 1; j <= m; j++) {
			t_j = t.charAt(j - 1);
			d[0] = j;

			for (i = 1; i <= n; i++) {
				cost = s.charAt(i - 1) == t_j ? 0 : 1;
				// minimum of cell to the left+1, to the top+1, diagonally left and up +cost				
				d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1]
						+ cost);
			}

			// copy current distance counts to 'previous row' distance counts
			_d = p;
			p = d;
			d = _d;
		}

		// our last action in the above loop was to switch d and p, so p now 
		// actually has the most recent cost counts
		return p[n];
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String c = "asdf";
		String r = "asdf";
		double distanceScore = getLevenshteinDistance(c, r);
		System.out.println("Edit distance between "+c+", "+r+" :"+distanceScore);
		
		c = "asdf";
		r = "sdfg";
		distanceScore = getLevenshteinDistance(c, r);
		System.out.println("Edit distance between "+c+", "+r+" :"+distanceScore);

		c = "asdf";
		r = "asdfasdf";
		distanceScore = getLevenshteinDistance(c, r);
		System.out.println("Edit distance between "+c+", "+r+" :"+distanceScore);

		c = "asdf";
		r = "a s d f";
		distanceScore = getLevenshteinDistance(c, r);
		System.out.println("Edit distance between "+c+", "+r+" :"+distanceScore);

		c = "945jgzd 235-9jga ";
		r = "=0u	234'pjoaerpu062";
		distanceScore = getLevenshteinDistance(c, r);
		System.out.println("Edit distance between "+c+", "+r+" :"+distanceScore);

		c = "asdf";
		r = "a\ns\td  f";
		distanceScore = getLevenshteinDistance(c, r);
		System.out.println("Edit distance between "+c+", "+r+" :"+distanceScore);

		c = "asdf";
		r = "";
		distanceScore = getLevenshteinDistance(c, r);
		System.out.println("Edit distance between "+c+", "+r+" :"+distanceScore);
	}

}
