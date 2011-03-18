package utils;

import java.util.ArrayList;
import java.util.Arrays;

import model.CachedData;
import model.MatchedTerms;

import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.Vocabulary;

/** Runs search utilities */
public class SearchUtils
{
	/** Generate the list of matched terms */
	static public MatchedTerms getItems(String keyword)
	{
		// Don't run search if no keyword is provided
		MatchedTerms matchedTerms = new MatchedTerms();
		if(keyword==null || keyword.length()==0) return matchedTerms;
		
		// Generate the list of all terms to search
		ArrayList<Term> terms = new ArrayList<Term>();
		try {
			for(Vocabulary vocabulary : CachedData.getVocabularies())
				terms.addAll(Arrays.asList(vocabulary.getTerms()));
		} catch(Exception e) { System.out.println("(E) SearchUtils.getItems - " + e.getMessage()); }
			
		// First pass only retrieves items whose terms match the specified item
		TERM: for(Term term : terms)
		{
			if(term.getName().equalsIgnoreCase(keyword)) { matchedTerms.add(term); continue; }
			for(AssociatedElement element : term.getElements())
				if(element.getName().equalsIgnoreCase(keyword)) { matchedTerms.add(term); continue TERM; }
		}
	
		// Second pass finds all close matches to the specified item
		if(matchedTerms.size()==0)
		{
			keyword = "(?i).*\\b" + keyword + "\\b.*";
			TERM2: for(Term term : terms)
			{
				if(term.getName().matches(keyword)) { matchedTerms.add(term); continue; }
				for(AssociatedElement element : term.getElements())
					if(element.getName().matches(keyword)) { matchedTerms.add(term); continue TERM2; }
			}
		}
		
		return matchedTerms;
	}
}