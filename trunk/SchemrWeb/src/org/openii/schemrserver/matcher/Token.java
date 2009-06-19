/**
 * 
 */
package org.openii.schemrserver.matcher;

public class Token implements Comparable<Token> {
	private String token;
//	public int freq;
//	public int docFreq;
//	public double idf;
//	public double prob;
//	public int rank;

	public Token(String token) {
		if (token == null || "".equals(token)) {
			throw new IllegalArgumentException("token cannot be null or empty");
		}
		this.token = token;
//		freq = 1;
//		docFreq = 1;
//		idf = 0.0;
//		prob = 0.0;
//		rank = 0;
	}

	public String getToken() {
		return token;
	}

//	public int getFreq() {
//		return freq;
//	}
//
//	public double getProb() {
//		return prob;
//	}

	public boolean equals(Object o) {
		if (o == null)
			return false;
		else if (o instanceof Token)
			return token.equals(((Token) o).getToken());
		else
			return false;
	}

	/**
	 * Implemented as part of the <code>Comparable Interface</code> to
	 * enable sorting of tokens according to their frequency.
	 */
	public int compareTo(Token o) {
//		return freq - o.freq;
		return this.hashCode() - o.hashCode();
	}

	public String toString() {
		return token;
	}

//	public String toString(char separator) {
//		return '"' + token + '"' + separator + freq + separator + docFreq
//				+ separator + idf + separator + prob;
//	}
}