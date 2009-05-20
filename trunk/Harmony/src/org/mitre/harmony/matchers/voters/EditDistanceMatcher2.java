// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;

import org.mitre.harmony.matchers.VoterScore;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.FilteredGraph;

/**
 * <p>
 * Computes a similarity matrix using a simple edit distance metric.
 * </p>
 * @author Peter Mork
 * @version 1.0
 */
public class EditDistanceMatcher2 implements MatchVoter
{
	// Scoring matrix.
	public static final double INIT_PREFIX_PENALTY = -0.75;

	public static final double CONT_PREFIX_PENALTY = -0.25;

	public static final double BEGIN_SUFFIX_PENALTY = -0.75;

	public static final double CONT_SUFFIX_PENALTY = -0.25;

	public static final double BEGIN_INSERT_PENALTY = -0.75;

	public static final double CONT_INSERT_PENALTY = -0.25;

	public static final double MISMATCH_PENALTY = -1;

	public static final double MAX_PENALTY = Math.min(INIT_PREFIX_PENALTY,
											 Math.min(BEGIN_SUFFIX_PENALTY,
											 Math.min(BEGIN_INSERT_PENALTY,
													  MISMATCH_PENALTY / 2)));

	public static final double MATCH_BONUS = 1;
	
	//max value that this matcher can return.  A scaling factor.
	public static final double SCORE_CEILING = 8.0;

	public static StringBuffer out;
	
	/** Returns the name of the match voter */
	public String getName()
		{ return "Name Similarity 2"; }
	
	/**
	 * Matches all cells passed into the matcher
	 * @param cells Cells passed into the matcher
	 */
	public VoterScores match(FilteredGraph schema1, FilteredGraph schema2)
	{
		VoterScores voterScores = new VoterScores(SCORE_CEILING);
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();
		ArrayList<SchemaElement> targetElements = schema2.getFilteredElements();
		for(SchemaElement sourceElement : sourceElements)
			for(SchemaElement targetElement : targetElements)
				if(voterScores.getScore(sourceElement.getId(), targetElement.getId())==null)
				{
					VoterScore score = matchElements(sourceElement, targetElement);
					voterScores.setScore(sourceElement.getId(), targetElement.getId(), score);
				}
		return voterScores;
	}

	/**
	 * Matches a single pair of nodes.
	 * @param left A node in the source schema.
	 * @param right A node in the target schema.
	 * @return Score of the specified cell
	 */
	public static VoterScore matchElements(SchemaElement sourceElement, SchemaElement targetElement)
	{
		String src = sourceElement.getName();
		String tgt = targetElement.getName();
		return match(src.toCharArray(), tgt.toCharArray());
	}

	/**
	 * Matches a pair of strings represented as char-arrays; no case-correction
	 * is done.
	 * @param source A source string.
	 * @param target A target string.
	 * @return The edit distance between these strings (in the range (-1,+1)).
	 */
	public static VoterScore match(char[] source, char[] target)
	{
		double[][] distance = createDistanceMatrix(source.length, target.length);
		int[][] gap_s = createGap_S(source.length, target.length);
		populateDistanceMatrix(distance, gap_s, source, target);
		double edit = distance[source.length][target.length];
		// Compute the most extreme scores for strings of this length.
		double postive = (Math.min(source.length, target.length) + 1)
				* MATCH_BONUS;
		double negative = -(source.length + target.length + 1) * MAX_PENALTY * 5;
		// Scale the result into the range (-1,+1).
		double scale = (edit > 0) ? postive : negative;
		
		//modify what this procedure returns to return a MatchScore object.
		return new VoterScore(edit, scale);
	}

	// Initialize initial gap/continuation gap matrix
	public static int[][] createGap_S(int s, int t) {
		// Allocate space for the matrix.
		int[][] result = new int[s + 1][];
		for (int i = 0; i <= s; i++) {
			result[i] = new int[t + 1];
		}
		return result;
	}
	/**
	 * Initializes a distance matrix.
	 * @param s The number of letters in the source string.
	 * @param t The number of letters in the target string.
	 * @return A matrix with s+1 rows and t+1 columns.
	 */
	public static double[][] createDistanceMatrix(int s, int t) {
		// Allocate space for the matrix.
		double[][] result = new double[s + 1][];
		for (int i = 0; i <= s; i++) {
			result[i] = new double[t + 1];
		}
		// Initialize the first row and column.
		for (int row = 0; row <= s; row++) {
			result[row][0] = (row-1) * CONT_PREFIX_PENALTY + INIT_PREFIX_PENALTY;
		}
		for (int col = 0; col <= t; col++) {
			result[0][col] = (col-1) * CONT_PREFIX_PENALTY + INIT_PREFIX_PENALTY;
		}
		result[0][0]=0;
		return result;
	}

	/**
	 * Populates each cell with the maximum of a) The cell above/left + a match
	 * bonus/penalty, b) The cell left + an insertion/suffix penalty, c) The
	 * cell above + an insertion/suffix penalty.
	 * @param m The matrix to populate.
	 */
	public static void populateDistanceMatrix(double[][] m, int[][] gap_s, char[] s, char[] t) {

		//next 6 lines:
		//become case insensitive for comparisons.
                for(int p = 0; p < s.length; p++){
                   if(s[p]>=65 && s[p]<=90) s[p] = ((char) (((int)s[p])+32));
                }
                for(int q = 0; q < t.length; q++){
                   if(t[q]>=65 && t[q]<=90) t[q] = ((char) (((int)t[q])+32));
                }

		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < t.length; j++) {

				//look at left and up to see who has an open gap/open suffix.
				//we'll use an encoding strategy.
				// bit position.
				// 1st - open/no gap for s (1 is on, 0 is off)
				// 2nd - open/no gap for t (1 is on, 0 is off)
				// 3rd - begun/not begun suffix penalty for s
				// 4th - begun/not begun suffix penalty for t
				// 5th - either gap for s xor gap for t, decide later.
				//
				// here, 1st and 3rd bits and 2nd and 4th bits are mutually exclusive.
				// (an open gap and begun suffix penalty is not possible).

				// left = s; up = t;
				int old_left       = gap_s[i][j+1]; 
				int old_up         = gap_s[i+1][j];
				int new_left_gap   = old_left | (1<<0); // 1st
				int new_up_gap     = old_up   | (1<<1); // 2nd
				int new_left_suf   = old_left | (1<<2); // 3rd
				int new_up_suf     = old_up   | (1<<3); // 4th
				int new_gap_suf;

				//deduce insert/suffix penalties.
				double insert_penalty_left = ((old_left & 17) > 0) ? CONT_INSERT_PENALTY : BEGIN_INSERT_PENALTY;
				double suffix_penalty_left = ((old_left & 4) > 0) ? CONT_SUFFIX_PENALTY : BEGIN_SUFFIX_PENALTY;
				double insert_penalty_up = ((old_up & 18) > 0) ? CONT_INSERT_PENALTY : BEGIN_INSERT_PENALTY;
				double suffix_penalty_up = ((old_up & 8) > 0) ? CONT_SUFFIX_PENALTY : BEGIN_SUFFIX_PENALTY;
				
				double match = (s[i] == t[j]) ? MATCH_BONUS : MISMATCH_PENALTY;
				double a = m[i][j] + match;

				double left = (j < t.length - 1) ? insert_penalty_left : suffix_penalty_left;
				int new_left = (j < t.length - 1) ? new_left_gap : new_left_suf;
				double b = m[i][j + 1] + left;

				double up = (i < s.length - 1) ? insert_penalty_up : suffix_penalty_up;
				int new_up = (j < s.length - 1) ? new_up_gap : new_up_suf;
				double c = m[i + 1][j] + up;

				double d=-10000;
				double e= -10000;
				if(i-1 >= 0){
				    double twoleft = (j < t.length -1) ? BEGIN_INSERT_PENALTY+CONT_INSERT_PENALTY : BEGIN_SUFFIX_PENALTY+CONT_SUFFIX_PENALTY;
				    d = m[i-1][j+1] + twoleft;
				}
				if(j-1 >=0){
				    double twoup = (i < s.length -1) ? BEGIN_INSERT_PENALTY+CONT_INSERT_PENALTY : BEGIN_SUFFIX_PENALTY+CONT_SUFFIX_PENALTY;
				    e = m[i+1][j-1] + twoup;
				}

				m[i + 1][j + 1] = Math.max(a, Math.max(b, Math.max(c,Math.max(d,e))));

				new_gap_suf = 0; //if a (the match/mismatch) is chosen, no gaps/mismatches.
				double new_penalty = a;
				if( b >= a ) {
					new_gap_suf = new_left; 
					new_penalty = b;
					if(c > b){ new_gap_suf = new_up; new_penalty=c;}
					else if(c == b){ new_gap_suf = 1<<4; }
				}
				else if( c >= a) { new_gap_suf = new_up; new_penalty = c;}
				if(d > new_penalty){
                                    new_gap_suf = new_left;
				}
				else if(e> new_penalty){
                                    new_gap_suf = new_up;
				}
				gap_s[i+1][j+1] = new_gap_suf;
			}
		}
	}
}
