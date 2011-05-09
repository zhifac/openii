// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class for storing a vocabulary's terms
 * @author CWOLF
 */
public class VocabularyTerms implements Serializable
{
	/** Stores the project associated with this vocabulary */
	private Integer projectID;
	
	/** Stores the list of terms which make up this vocabulary */
	private Term[] terms = new Term[0];
	
	/** Cache for reverse look up on element IDs **/
	private HashMap<String, Integer[]> ReverseLookupCache = new HashMap<String, Integer[]>();
	
	/** Constructs the default vocabulary */ 
	public VocabularyTerms() {}
	
	/** Constructs the vocabulary */
	public VocabularyTerms(Integer projectID)
		{ this.projectID = projectID; }
	
	/** Constructs the vocabulary */
	public VocabularyTerms(Integer projectID, Term[] terms)
		{ this.projectID = projectID; this.terms = terms; }
	
	/** Copies the vocabulary */
	public VocabularyTerms copy()
	{
		ArrayList<Term> copiedTerms = new ArrayList<Term>();
		for(Term term : terms) copiedTerms.add(term.copy());
		return new VocabularyTerms(getProjectID(),copiedTerms.toArray(new Term[0]));
	}
	
	// Handles all of the vocabulary getters
	public Integer getProjectID() { return projectID; }
	public Term[] getTerms() { return terms; }
	public int getTermIndex(Integer id) { 
		for(int i = 0; i < terms.length ; i++) {
			if(terms[i].getId().equals(id))
			{
				return i;					
			}
		}
		return -1;
	}

	// returns the IDs of all Terms which contain a given element/schema ID.
	public Integer[] reverseLookup(Integer id, Integer schemaID) {
		String key = makeKey(id,schemaID);
		if(!ReverseLookupCache.containsKey(key)) { // build dynamically if it doesn't exist
			ArrayList<Integer> retval = new ArrayList<Integer>();
			for(int i = 0; i < terms.length; i++){
			    AssociatedElement[] emts = terms[i].getElements();
			    for(int j = 0; j < emts.length; j++)
			    {
			    	if(emts[j].getElementID().equals(id) && emts[j].getSchemaID().equals(schemaID))
			    	{
			    		retval.add(terms[i].getId());
			    		break;
			    	}
			    }
			}			    
			//this is stupid,  but java has no way to convert from an arraylist to an array		
			Integer ret[] = new Integer[retval.size()];
			ReverseLookupCache.put(key, retval.toArray(ret));
		}
		return ReverseLookupCache.get(key);
	}
	
	public String makeKey(Integer id, Integer schemaID){
		return "" + id + "_" + schemaID;
	}
	
	public Integer getElementIDfromKey(String key){
		return new Integer(key.substring(0,key.indexOf("_")));
	}
	
	public Integer getSchemaIDfromKey(String key){
		return new Integer(key.substring(key.indexOf("_")+1));
	}
	
	// Handles all of the vocabulary setters
	public void setProjectID(Integer projectID) { this.projectID = projectID; }
	public void setTerms(Term[] terms) { 
		this.terms = terms; 
		ReverseLookupCache.clear();
	}
	
	/** Update a term in the vocabulary */
	public void termUpdated(Integer id)
	{
		termUpdated(id, false);
	}
	
	/** Update a term in the vocabulary 
	 *
	 *  This MUST be called after any TERM update in order to keep the reverselookupcache
	 *  accurate.
	 *  
	 **/
	public void termUpdated(Integer id, boolean deleted)
	{
		AssociatedElement emts[] = terms[getTermIndex(id)].getElements();
		String key;
		ArrayList<Integer> newTerms;
		Iterator itr = ReverseLookupCache.keySet().iterator();
		while(itr.hasNext())
		{
			key = (String)itr.next();
			newTerms = new ArrayList<Integer>();
			Integer termIDs[] = (ReverseLookupCache.get(key));
			boolean updated = false;
			//first remove all instances of this Term from the lookup cache			
			for(int i = 0; i < termIDs.length; i++)
			{
				updated = false;
				if(termIDs[i] != id) {
					newTerms.add(termIDs[i]);
				} else {
					updated = true;
				}				        
			}		
			if(updated){
				Integer newTermsA[] = new Integer[newTerms.size()];
				ReverseLookupCache.put(key, newTerms.toArray(newTermsA));
			}
		}
		//now add Term back into cache
		if(!deleted){
			for(int i = 0; i < emts.length; i++){
				System.out.println("size = " + emts.length );
				System.out.println("schemaID = " + emts[i].getSchemaID());
				System.out.println("elementID+ = " + emts[i].getElementID());
				key = "" + emts[i].getElementID() + "_" + emts[i].getSchemaID();
				if(ReverseLookupCache.containsKey(key)) {
					Integer eterms[] = ReverseLookupCache.get(key);
					newTerms = new ArrayList<Integer>();
					Collections.addAll(newTerms, eterms);
					newTerms.add(id);
					eterms = new Integer[newTerms.size()];
					ReverseLookupCache.put(key, newTerms.toArray(eterms));
				}
			}
		}

	}
	
	/** Adds a term to the vocabulary */
	public void addTerm(Term newTerm)
	{
		ArrayList<Term> terms = new ArrayList<Term>(Arrays.asList(this.terms));
		terms.add(newTerm);
		this.terms = terms.toArray(new Term[0]);
		termUpdated(newTerm.getId());
	}
	
	/** Removes the term from the vocabulary by ID */
	public void removeTerm(Integer id)
	{
		termUpdated(id,true);
		int index = getTermIndex(id);
		ArrayList<Term> terms = new ArrayList<Term>(Arrays.asList(this.terms));
		terms.remove(index);
		this.terms = terms.toArray(new Term[0]);		
	}
	
	/** Removes the term from the vocabulary */
	public void removeTerm(Term oldTerm)
	{
		termUpdated(oldTerm.getId(),true);
		ArrayList<Term> terms = new ArrayList<Term>(Arrays.asList(this.terms));
		terms.remove(oldTerm);
		this.terms = terms.toArray(new Term[0]);		
	}
	
	/** Returns the schemas used in this vocabulary */
	public Integer[] getSchemaIDs()
	{
		HashSet<Integer> schemaIDs = new HashSet<Integer>();
		for(Term term : terms)
			for(AssociatedElement element : term.getElements())
				schemaIDs.add(element.getSchemaID());
		return schemaIDs.toArray(new Integer[0]);
	}
}