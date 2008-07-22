package org.openii.schemr.preprocessor;

import java.util.ArrayList;

import org.openii.schemr.matcher.Token;
import org.openii.schemr.matcher.TokenSet;

public abstract class Preprocessor {
	
	public static final Tokenizer TOKENIZER = new Tokenizer();
	public static final Lowercaser LOWERCASER = new Lowercaser();
//	public static final Stemmer STEMMER = new Stemmer();
//	public static final Synonymer SYNONYMER = new Synonymer();
	
    public static void main(String[] args) {
        String s = "abcd-efgh_jklmCamelCaseID abIDcd--efgh__jklmCamelCase \t abcd-EFGHjklmCamelCase\n\n";
        
        System.out.println(s);
        String result = Preprocessor.TOKENIZER.process(s).toString();
        System.out.println(result);
        System.out.println(Preprocessor.LOWERCASER.process(result).toString());

        
        String result2 = Preprocessor.LOWERCASER.process(s).toString();
        System.out.println(result2);
        System.out.println(Preprocessor.TOKENIZER.process(result2).toString());

    }		


	public ArrayList<Token> process(TokenSet ts) {			
		ArrayList<Token> tokens = ts.getTokens();
		ArrayList<Token> newTokens = new ArrayList<Token>();
		if (tokens == null || tokens.isEmpty()) {
			// create new set from name
			newTokens = process(ts.getName());
		} else {
			// modify existing tokens
			for (Token t : tokens) {
				newTokens.addAll(process(t.getToken()));
			}
		}
		return newTokens;
	}
	
	abstract ArrayList<Token> process(String s);

	static class Tokenizer extends Preprocessor {

		@Override
		ArrayList<Token> process(String s) {
			if (s == null) throw new IllegalArgumentException("String s cannot be null");
			
			ArrayList<Token> result = new ArrayList<Token>();
			
			// split greedy on any white space
			for (String split : s.split("\\s+")) {
				// split on _ - or RegularMixCase
				for (String split2 : split.split("[\\_\\-]+")) {

					for (String split3 : unCamelCase(split2)) {
						if (split3 != null && !"".equals(split3)) {
							result.add(new Token(split3));
						}
					}
				}
			}
			return result;
		}

		private static String [] unCamelCase(String inputString) {
			String temp = inputString.replaceAll("([A-Z]+)", " $1").trim();
			return temp.replaceAll("([A-Z][a-z])", " $1").trim().split(" ");
	    }

	}
	
	static class Lowercaser extends Preprocessor {
		
		@Override
		ArrayList<Token> process(String s) {
			if (s == null) throw new IllegalArgumentException("String s cannot be null");
			ArrayList<Token> result = new ArrayList<Token>();
			result.add(new Token(s.toLowerCase()));
			return result;
		}

	}

//	static class Stemmer extends Preprocessor {
//	}
//	static class Synonymer extends Preprocessor {
//	}
}
