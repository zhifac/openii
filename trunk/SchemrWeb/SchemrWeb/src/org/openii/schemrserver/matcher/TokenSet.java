package org.openii.schemrserver.matcher;

import java.util.ArrayList;
import java.util.Iterator;

public class TokenSet {

//	private static final Logger logger = Logger.getLogger(TokenSet.class.getName());

	private String name;
	private ArrayList<Token> tokens;

	public TokenSet(String name) {
		if (name == null || "".equals(name)) {
			throw new IllegalArgumentException("TokenSet name cannot be null or empty");
		}
		init(name);
	}

	private void init(String name) {
		this.name = name;
		clear();
	}

	public void clear() {
		this.tokens = new ArrayList<Token>();
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<Token> getTokens() {
		return this.tokens;
	}


	public void add(Token t) {
		this.tokens.add(t);
	}

	public void add(ArrayList<Token> tokens) {
		this.tokens.addAll(tokens);
	}

//	public void add(TokenSet tokenSet) {
//		Iterator it = tokenSet.tokens.iterator();
//		while (it.hasNext()) {
//			Token token = (Token) it.next();
//			add(token);
//		}
//	}
//
//	/**
//	 * Adds the tokens in the given <code>TokenSet tokenSet1</code>
//	 * to the list of tokens, only if that token also appears in the
//	 * <code>TokenSet tokenSet2</code>.
//	 */
//	public void addUnion(TokenSet tokenSet1, TokenSet tokenSet2) {
//		Iterator it = tokenSet1.iterator();
//		while (it.hasNext()) {
//			Token token = (Token) it.next();
//			if (tokenSet2.indexOf(token) != -1)
//				add((Token) token.clone(), true);
//		}
//	}
//
//	/**
//	 * Removes the tokens in the argument <code>TokenSet tokenSet</code>
//	 * from the list of tokens.
//	 */
//	public void remove(TokenSet tokenSet) {
//		Iterator it = tokenSet.tokens.iterator();
//		while (it.hasNext()) {
//			Token token = (Token) it.next();
//			remove(token, true);
//		}
//	}
//
//	/**
//	 * Removes the tokens in the given <code>TokenSet tokenSet1</code>
//	 * from the list of tokens, only if that token also appears in the
//	 * <code>TokenSet tokenSet2</code>.
//	 */
//	public void removeUnion(TokenSet tokenSet1, TokenSet tokenSet2) {
//		Iterator it = tokenSet1.iterator();
//		while (it.hasNext()) {
//			Token token = (Token) it.next();
//			if (tokenSet2.indexOf(token) != -1)
//				remove(token, true);
//		}
//	}

	public int size() {
		return this.tokens.size();
	}

	public int indexOf(Object o) {
		return this.tokens.indexOf(o);
	}

	public Iterator<Token> iterator() {
		return this.tokens.iterator();
	}
	
	@Override
	public String toString() {
		StringBuffer writer = new StringBuffer();
		writer.append("set named: " + name + " | ");
		Iterator<Token> it = this.tokens.iterator();
		while (it.hasNext()) {
			Token token = it.next();
			writer.append(token.toString() + " | ");
		}
		return writer.toString();
	}

}
