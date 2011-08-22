package org.mitre.openii.editors.vocabulary;

import java.util.Comparator;
import java.util.HashSet;

import org.mitre.schemastore.model.terms.AssociatedElement;
import org.mitre.schemastore.model.terms.Term;

/** Sorts the passed in vocabulary */
public class VocabularySort
{
	/** Function used to sort terms by number of aligned schemas */
	static public class AlignedSchemasSorter implements Comparator<Term>
	{
		/** Indicates if items should be in ascending or descending order */
		private boolean ascending;
		
		/** Constructs the sorter */
		public AlignedSchemasSorter(boolean ascending)
			{ this.ascending = ascending; }

		/** Returns the number of aligned schemas for the specified term */
		private HashSet<Integer> schemaIDs = new HashSet<Integer>();
		private int getAlignedSchemas(Term term)
		{
			schemaIDs.clear();
			for(AssociatedElement element : term.getElements())
				schemaIDs.add(element.getSchemaID());
			return schemaIDs.size();
		}
		
		/** Indicates how two terms align */
		public int compare(Term term1, Term term2)
			{ return (ascending?1:-1)*(getAlignedSchemas(term2) - getAlignedSchemas(term1)); }
	}
	
	/** Function used to sort terms by the specified schema */
	static public class SpecifiedSchemaSorter implements Comparator<Term>
	{
		/** Indicates the schema on which the terms should be sorted */
		private Integer schemaID;
		
		/** Constructs the sorter */
		public SpecifiedSchemaSorter(Integer schemaID)
			{ this.schemaID = schemaID; }

		/** Returns the string associated with the schema associated with the specified term */
		private String getSchemaString(Term term)
		{
			String word = "";
			if(schemaID==null) word = term.getName();
			else for(AssociatedElement element : term.getAssociatedElements(schemaID))
				word += element.getName()+" ";
			if(word.length()==0) word = "zzzzz";
			return word;
		}
		
		/** Indicates how two terms align */
		public int compare(Term term1, Term term2)
			{ return getSchemaString(term1).compareToIgnoreCase(getSchemaString(term2)); }
	}
}