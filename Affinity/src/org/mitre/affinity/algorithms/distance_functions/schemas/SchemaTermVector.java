/*
 *  Copyright 2010 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mitre.affinity.algorithms.distance_functions.schemas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.porters.Porter;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.PorterType;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

/**
 * Create a term vector for a schema.  The vector contains a list
 * of the terms contained in a document, along with the number of times that
 * word occurs in the schema.  For example, this class might store
 * {book, store, cd},{3, 1, 2} if a schema contains 3 occurrences of book, 1 of
 * store, and 2 of cd.
 * @author MDMORSE
 * Created Feb 26, 2009
 */
public class SchemaTermVector
{
	/** Stores the bag of words */
	private HashMap<String,Integer> termHash = new HashMap<String,Integer>();;
	
	/** Stores the total number of words in the schema */
	private int wordCount;	
	
	/** Constructs the schema term vector */
	private SchemaTermVector() {}
	
	/** Constructs the schema term vector from the schema */
	public SchemaTermVector(SchemaInfo schema)
	{	
		ArrayList<Porter> importers = new PorterManager((SchemaStoreClient)null).getPorters(PorterType.SCHEMA_IMPORTERS);
		String importerName = schema.getSchema().getType();
		boolean done = false;
		ArrayList<String> baseDomainNames = null;
		for (int i = 0; i < importers.size() && !done; i++ ){
			SchemaImporter importer = (SchemaImporter)importers.get(i);
			if (importer.getName().equals(importerName)){
				baseDomainNames = importer.getBaseDomainNames();
				done = true;
			}
		}

		for(SchemaElement schemaElement: schema.getElements(null))
			if(!(schemaElement instanceof org.mitre.schemastore.model.DomainValue) && 
					(!done ||!(schemaElement instanceof org.mitre.schemastore.model.Domain)
					|| !baseDomainNames.contains(schemaElement.getName())))
				updateCount(schemaElement,true);
	}
	
	/** Copies the schema term vector */ @SuppressWarnings("unchecked")
	public SchemaTermVector copy()
	{
		SchemaTermVector copy = new SchemaTermVector();
		copy.termHash = (HashMap<String,Integer>)termHash.clone();
		copy.wordCount = wordCount;
		return copy;
	}
	
	/** Returns the list of terms */
	public Set<String> getTerms()
		{ return termHash.keySet(); }
	
	/** Returns the number of occurrences of the specified term */
	public int getWordCount(String term)
	{
		Integer count = termHash.get(term);
		return count==null ? 0 : count;
	}
	
	/** Returns the total number of words in the schema */
	public int getWordCount()
		{ return wordCount; }
	
	/** Returns the number of terms in the schema */
	public int getTermCount()
		{ return termHash.size(); }
	
	/** Returns the frequency of the specified word */
	public float getTermFrequency(String term)
		{ return wordCount==0 ? 0 : (float)getWordCount(term)/wordCount; }
	
	/** Indicates if the the specified term is contained */
	public boolean containsTerm(String term)
		{ return termHash.containsKey(term); }
	
	/** Removes the specified schema element */
	public void remove(SchemaElement schemaElement)
		{ updateCount(schemaElement,false); }
	
	/** Update the term counts based on the schema element (either adds or removes term) */
	private void updateCount(SchemaElement schemaElement, boolean increment)
	{
		WordBag wb = new WordBag(schemaElement);
		for(String word : wb.getWords())
		{
			// Gets the word count
			int count = wb.getWordCount(word) * (increment ? 1 : -1);

			// Update the term count			
			Integer currCount = termHash.get(word);
			termHash.put(word, (currCount==null ? 0 : currCount) + count);
			
			// Update the total number of terms
			wordCount += count;
		}
	}

	/** Combines a list of term vectors */
	public static ArrayList<String> getCommonWords(ArrayList<SchemaTermVector> termVectors)
	{
		HashSet<String> terms = new HashSet<String>();
		for(SchemaTermVector termVector: termVectors)
			terms.addAll(termVector.getTerms());
		return new ArrayList<String>(terms);
	}
}