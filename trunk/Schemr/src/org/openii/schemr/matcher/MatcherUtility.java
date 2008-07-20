package org.openii.schemr.matcher;

import java.util.ArrayList;
import java.util.logging.Logger;

public class MatcherUtility {

	private static final Logger logger = Logger.getLogger(MatcherUtility.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<String> a = getNGrams("abcdefghij", 3);
		logger.info(a.toString());
		
		ArrayList<String> b = getNGrams("a bc def ghij klmno pqrstu", 3);
		logger.info(b.toString());

		ArrayList<String> c = getNGrams("a\tbc  def\n\n ghij kl___mno pqrstu", 3);
		logger.info(c.toString());

	}

	public static ArrayList<String> getNGrams(String str, int n) {
		ArrayList<String> result = new ArrayList<String>();
		String[] tokens = str.split("\\s+");
		for (String s : tokens) {
			int size = s.length();
			if (size < n) {
				result.add(s);
			}
			int numGrams = size - n + 1;
			for (int i = 0; i < numGrams; i++) {
				result.add(s.substring(i, i + n));
			}
		}
		return result;
	}

}
