package org.mitre.harmony.matchers.voters;

import java.util.*;

import org.mitre.harmony.matchers.VoterScore;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.FilteredGraph;

/**
 * This is an implementation of the Fragment Matcher Idea.
 * 
 * Author: Michael Morse
 *
 * Modified October 31, 2008
 * Modifier: MDMorse
 * Modification: implemented to use 'bag of words' paradigm, instead of
 * 				 thesaurus paradigm.  If thesaurus is added later, can
 * 				 be augmented.
 * 
 * Modified Jan 12, 2009
 * Modifier: MDMorse
 * Modification: clean up/compliance with of SchemaManager interface
 * 				 a few general code clean ups.
 **/

public class FragmentListMatcher implements MatchVoter {
	
	static final double COSINE_THRESHOLD = 0.5;

  //Following 2 lines lifted by mdmorse from StringMatcher.java due to protected conflict.
  static final double MIN_NODE_SIM = 0.001;
  static final double RESOURCE_LITERAL_MATCH_PENALTY = 0.1; // 1.0: no penalty, 0: ignore completely

	/** Constant defining the score ceiling */
	final static double SCORE_CEILING=10;
  
    // implement methods for interface MatchVoter.
    public String getName()
    	{ return "Fragment Lists"; }
    
	/** Returns the score ceiling of the match voter */
	public Double getScoreCeiling()
		{ return SCORE_CEILING; }

    /** Generates scores for the specified elements */
	public VoterScores match(FilteredGraph schema1, FilteredGraph schema2)
	{
		//get a hash of all the words in the document.
		WordConceptManager wcm = new WordConceptManager();
		Hashtable<Integer,SchemaGraphNode> hash = new Hashtable<Integer,SchemaGraphNode>();
		
		//get all of the root elements.
		ArrayList<SchemaElement> sAL = schema1.getFilteredRootElements();
		ArrayList<SchemaElement> tAL = schema2.getFilteredRootElements();
		
		//add the words from all nodes to the wordconceptmanager.
		for(SchemaElement lInt: sAL){
			wcm.walkGraph(schema1, lInt.getId());
		}
		for(SchemaElement rInt: tAL){
			wcm.walkGraph(schema2, rInt.getId());
		}
		
		//store the lists of schema roots.
		ArrayList<SchemaGraphNode> sgnLList = new ArrayList<SchemaGraphNode>();
		ArrayList<SchemaGraphNode> sgnRList = new ArrayList<SchemaGraphNode>();
		
		//******** old comment ***********************
		//right now, to simulate the storage manager functionality,
		//we will do the pre-processing right here.
		//********************************************
		//******** new comment ***********************
		//To create the 'bag of words' fragment matcher, we now need
		//to perform these steps - i.e. loading the child terms into each
		//element.  If some thesaurus technology is added later, this can be changed.
		for(SchemaElement lInt: sAL){
		   SchemaGraphNode sgn = new SchemaGraphNode(lInt.getId());
		   hash = sgn.buildFromRoot(schema1, hash, wcm);
		   sgnLList.add(sgn);
		}
		
		for(SchemaElement rInt: tAL){
		   SchemaGraphNode sgn = new SchemaGraphNode(rInt.getId());
		   hash = sgn.buildFromRoot(schema2, hash, wcm);
		   sgnRList.add(sgn);
		}
		
		//do timing for mergeChildLists within the timing statement.
		Iterator<SchemaGraphNode> liter = sgnLList.iterator();
		while(liter.hasNext()){
			SchemaGraphNode sgn = liter.next();
			sgn.mergeChildLists();
		}
		
		Iterator<SchemaGraphNode> riter = sgnRList.iterator();
		while(riter.hasNext()){
			SchemaGraphNode sgn = riter.next();
			sgn.mergeChildLists();
		}
		
		
		////////////////////////////////////
		
		FLMatcher fmatcher = new FLMatcher();		
		for(int j=0; j < sgnLList.size(); j++)
			for(int k=0; k < sgnRList.size(); k++)
				fmatcher.addRoot(sgnLList.get(j), sgnRList.get(k));
			   
		return fmatcher.match();
	}
}

//This class 'fakes' the word/concept management at the schema store side.
//basically, its a large hash that gives identifiers to each word/concept.
class WordConceptManager{
	int conceptNumber;
	Hashtable<String, Integer> conceptHash;
	
	public WordConceptManager(){
		conceptHash = new Hashtable<String, Integer>();
		conceptNumber = 0;
	}
	
	//add a word to the concept hash.
	void addWord(String word){
		if(!conceptHash.containsKey(word)){
			conceptHash.put(word, conceptNumber++);
		}
	}
	
	//walk the schema graphs, adding words to the hash.
	void walkGraph(FilteredGraph schemaX, Integer id){
		//add all of the words in this node to a word bag.
		WordBag wb = new WordBag();
		wb.putInMoreWords(schemaX.getElement(id).getName());
		wb.putInMoreWords(schemaX.getElement(id).getDescription());
		//iterate through the words in the word bag.
		ArrayList<String> v = wb.getWords();
		for(int j=0; j < v.size(); j++){
			//get the next word in the word bag.
			String s = v.get(j);
			addWord(s);
		}
		
		// Recurse through child elements
		for(SchemaElement element : schemaX.getChildElements(id))
			walkGraph(schemaX, element.getId());
	}
}

//this class contains 2 ints, used as a compound key for a hashtable.
//the left-right entries are from the left and right relations/xsds/etc
//being matched.
class CompoundKeyHash{
	Integer lID;
	Integer rID;
	
	public CompoundKeyHash(Integer a, Integer b){
		lID=a;
		rID=b;
	}
	
	//implement equals and hashCode methods so that this object may be
	//hashed appropriately.
	public boolean equals(Object other){
		CompoundKeyHash kh = (CompoundKeyHash) other;
		if(kh.lID == lID && kh.rID == rID){
			return true;
		}
		else return false;
	}
	
	public int hashCode(){
		return 31*lID.hashCode()+rID.hashCode();
	}
}

//a schemagraphnode pair, in other words, one schema graph node from the left and
//one from the right that need to be matched appropriately.  To be stored in
//a stack, queue, etc.
class SGNPair{
	SchemaGraphNode lsgnode;
	SchemaGraphNode rsgnode;
	
	SGNPair(SchemaGraphNode left, SchemaGraphNode right){
		lsgnode = left;
		rsgnode = right;
	}

}
//the fragment list matcher.  Once the support objects are constructed, perform
//the matches.
class FLMatcher{
	Hashtable<CompoundKeyHash,CompoundKeyHash> soFarMatches;
	Stack<SGNPair> stackSGN; 
	
	static final double THRESHOLD=0.2;
	
	FLMatcher(){
		soFarMatches = new Hashtable<CompoundKeyHash,CompoundKeyHash>();
		stackSGN = new Stack<SGNPair>();
	}
	
	//add a new root level comparison to the matcher.  This
	//procedure is called before match to prime the data structure.
	void addRoot(SchemaGraphNode left, SchemaGraphNode right){
		stackSGN.push(new SGNPair(left,right));
	}
	
	//the main procedure for the fragment matcher.
	VoterScores match()
	{
		VoterScores scores = new VoterScores(10.0);
		
		// while there are likely matches still left to find.
		while(!stackSGN.empty())
		{
			//explore all possible matches between the two nodes we pull off the list.
			SGNPair sgnPair = stackSGN.pop();
			SchemaGraphNode left = sgnPair.lsgnode;
			SchemaGraphNode right = sgnPair.rsgnode;
			
			//compare the two sets of children.  If their scores are over the threshold, add them back to the stack.
			for(int j=0; left.children != null && j < left.children.size(); j++)
			{
				//get the jaccard score between left child and right.
				SchemaGraphNode leftN = left.children.get(j);
				double score = leftN.scoreNode(right);
				VoterScore vs = new VoterScore(score);

				//record this score in matchscores.  For Harmony.
				if(score < 1){
					scores.setScore(left.children.get(j).id, right.id, vs);
				}
				else{
					vs = new VoterScore(0.99);
					scores.setScore(left.children.get(j).id, right.id, vs);
					//mscores.setScore(left.children.get(j).id, right.id, score);
				}
				//if this score is above a threshold,
				if(score >= THRESHOLD){
					CompoundKeyHash two = new CompoundKeyHash(left.children.get(j).id,right.id);
					//check the hashtable to see if we have/are considering it already.
					if(!soFarMatches.containsKey(two)){
						//if not, add it to the hashtable,
						soFarMatches.put(two,two);
						//and push it to the stack.
						stackSGN.add(new SGNPair(left.children.get(j),right));
					}
				}
			}
			
			for(int k=0; right.children!=null && k < right.children.size(); k++){
					//get the jaccard score between left and right.
					double score = left.scoreNode(right.children.get(k));
					//record this score in matchscores.  For Harmony.
					VoterScore vs = new VoterScore(score);
					if(score < 1){
						scores.setScore(left.id, right.children.get(k).id, vs);
					}
					else{
						vs = new VoterScore(0.99);
						scores.setScore(left.id, right.children.get(k).id, vs);
					}
					//if this score is above a threshold,
					if(score >= THRESHOLD){
						CompoundKeyHash one = new CompoundKeyHash(left.id,right.children.get(k).id);
						//check the hashtable to see if we have/are considering it already.
						if(!soFarMatches.containsKey(one)){
							//if not, add it to the hashtable,
							soFarMatches.put(one,one);
							//and push it to the stack.
							stackSGN.add(new SGNPair(left,right.children.get(k)));
						}
					}
			}
			
			//now do children with children.
			for(int j=0; left.children != null && j < left.children.size(); j++){
				for(int k=0; right.children!=null && k < right.children.size(); k++){
					//get the jaccard score between left child and right.
					SchemaGraphNode leftN = left.children.get(j);
					double score = leftN.scoreNode(right.children.get(k));
					VoterScore ms = new VoterScore(score);
					//record this score in matchscores.  For Harmony.
					if(score < 1){
						scores.setScore(left.children.get(j).id, right.children.get(k).id, ms);
					}
					else{
						ms = new VoterScore(0.99);
						scores.setScore(left.children.get(j).id, right.children.get(k).id, ms);
						//mscores.setScore(left.children.get(j).id, right.children.get(k).id, score);
					}
					//if this score is above a threshold,
					if(score >= THRESHOLD){
						CompoundKeyHash one = new CompoundKeyHash(left.children.get(j).id,right.children.get(k).id);
						//check the hashtable to see if we have/are considering it already.
						if(!soFarMatches.containsKey(one)){
							//if not, add it to the hashtable,
							soFarMatches.put(one,one);
							//and push it to the stack.
							stackSGN.add(new SGNPair(left.children.get(j),right.children.get(k)));
						}
					}
				}
			}
		}
		return scores;
	}
	
}

//this class represents the <wordconcept, count> pair, corresponding to an offset
//into the big array of possible words, maintained in the schema store and
//the number of times that word/concept appears.
class EntryPair implements Comparable{
	Integer wordConcept; //the integer offset corresponding to a particular word
	Integer count; //the number of times that word appears in a document/node.
	
	EntryPair(Integer wc, Integer c){
		wordConcept = wc;
		count = c;
	}
	
	//implement comparable so that list may be sorted.
	public int compareTo(Object y){
		EntryPair x = (EntryPair) y;
		if(x.wordConcept > this.wordConcept){
			return -1;
		}
		else if(x.wordConcept < this.wordConcept){
			return 1;
		}
		else return 0;	
	}
}

//This is a node in the graph, maintaining which node this corresponds to and the 
//EntryPairs for both this particular node as well as all of the descendant nodes
//of this node.
class LRListNode{
	Integer nodeID;
	ArrayList<EntryPair> myWords; // the words are those contained in the node's documentation and the node tag name.
	ArrayList<EntryPair> myDocument; // the 'document' are all words for the node's children.
	
	LRListNode(){
		myWords = new ArrayList<EntryPair>();
		myDocument = new ArrayList<EntryPair>();
	}
	
	//We have a number of children, who have lists of words (represented
	//as integers that are mapped to strings).  These lists of words are
	//kept in sorted order for later use.  We want to merge these plus
	//the words kept in this node, keeping the new list in sorted order.
	void combineLists(ArrayList<ArrayList<EntryPair>> childLists){
		//list size is the number of children whose lists of words we are merging.
		int listSize = childLists.size();
		//list positions is the offset into each list that we are currently on when
		//merging the lists.
		//make it bigger by 1 to accomodate this nodes data.
		int listPositions[] = new int[listSize+1];
		//initialize all pointers to 0, the start of each list.
		for(int j=0; j < listSize+1; j++) listPositions[j]=0;
		
		//now, iterate through all of the array lists, merging the EntryPairs based
		//on word/concept number.  When two nodes have the same word/concept pair,
		//merge these also, adding the counts.
	   	while(true){
			int smallestWordConcept = Integer.MAX_VALUE;
			int count = 0;
			for(int k=0; k < listSize; k++){
				//check that we don't yet have all of this nodes' children
				if(childLists.get(k).size() <= listPositions[k]) continue;
				//get the word/concept identifier and the count for that identifier.
				int t_WordConcept=childLists.get(k).get(listPositions[k]).wordConcept.intValue();
				int t_count = childLists.get(k).get(listPositions[k]).count.intValue();
				//if this is the smallest, keep track of it and the count.
				if(smallestWordConcept > t_WordConcept){
					smallestWordConcept = t_WordConcept;
					count = t_count;
				} //if it is equal to the smallest, increment the count.
				else if(smallestWordConcept == t_WordConcept){
					count += t_count;
				}
			}
			//perhaps the smallest word/concept identifier is in this node.
			//check for this possibility, making sure that we have not yet
			//used all of its terms.
			if(myWords.size()> listPositions[listSize] && 
					myWords.get(listPositions[listSize]).wordConcept.intValue() < smallestWordConcept){
				//capture the word/concept number and the count.
				smallestWordConcept = myWords.get(listPositions[listSize]).wordConcept.intValue();
				count = myWords.get(listPositions[listSize]).count.intValue();
				//increment this position in the pointer list.
				listPositions[listSize]++;
			} //see if the best concept is also contained in the node.
			else if(myWords.size() > listPositions[listSize] &&
					myWords.get(listPositions[listSize]).wordConcept.intValue() == smallestWordConcept){
				count += myWords.get(listPositions[listSize]).count.intValue();
				//increment this position in the pointer list.
				listPositions[listSize]++;
				//also, increment the points in the lists that contain this concept.
				for(int c=0; c < listSize; c++){
					//check that we don't yet have all of this nodes' children
					if(childLists.get(c).size() <= listPositions[c]) continue;
					//check if the word/concept is equal
					if(smallestWordConcept == childLists.get(c).get(listPositions[c]).wordConcept.intValue()){
						listPositions[c]++;
					}
				}
			} //otherwise, the best value is contained in one of the children.
			else{
				//increment the points in the lists that contain this concept.
				for(int c=0; c < listSize; c++){
					//check that we don't yet have all of this nodes' children
					if(childLists.get(c).size() <= listPositions[c]) continue;
					//check if the word/concept is equal
					if(smallestWordConcept == childLists.get(c).get(listPositions[c]).wordConcept.intValue()){
						listPositions[c]++;
					}
				}
			}
			//if true, we are all done, so break.
			if(smallestWordConcept == Integer.MAX_VALUE) break;
			//otherwise, add an entry to our list.
			else{
				myDocument.add(new EntryPair(smallestWordConcept, count));
			}
	   	}
	}
	
	//a procedure to put all of the words into a structure.
	void addWords(SchemaElement element, WordConceptManager wcm){
		//put all of the documentation and the node name information into a word bag.
		WordBag wb = new WordBag();
		wb.putInMoreWords(element.getName());
		wb.putInMoreWords(element.getDescription());
		//iterate through the words in the word bag.
		ArrayList<String> v = wb.getWords();
		for(int j=0; j < v.size(); j++){
			//get the next word in the word bag.
			String s = v.get(j);
			//find out how many times it is in the document.
			int WordCount = wb.getWordCount(s);
			//add it to the array list.
			myWords.add(new EntryPair(wcm.conceptHash.get(s),WordCount));
		}
		//sort the array list.
		Collections.sort(myWords);
	}
}

//The schema Graph.  
//each node in the schema contains an LRListNode, which maintains the concept/word
//identifiers and their counts in the document.
class SchemaGraphNode{
	LRListNode lrListNode;
	ArrayList<SchemaGraphNode> children;
	Integer id;
	
	SchemaGraphNode(Integer ID){
		id = ID;
		lrListNode = new LRListNode();
		lrListNode.nodeID = ID;
	}

	
	//Assemble the Schema Graph, from the root.
	//note that the root is a rooted subtree, ie not necessarily
	//globally root.
	Hashtable<Integer,SchemaGraphNode> buildFromRoot(FilteredGraph schemaX, Hashtable<Integer,SchemaGraphNode> hash, WordConceptManager wcm)
	{
		children = new ArrayList<SchemaGraphNode>();
		for(SchemaElement element : schemaX.getChildElements(id))
		{
			//get the next child.
			Integer key = element.getId();
			//check to see if we have a node for them already.
			//this is so that graphs will stay graphs and
			//not become trees.
			if(hash.containsKey(key)){
				children.add(hash.get(key));
			}
			else{
				//create a new child node.
				SchemaGraphNode child = new SchemaGraphNode(key);
				child.lrListNode.addWords(schemaX.getElement(key), wcm);
				hash = child.buildFromRoot(schemaX, hash, wcm);
				children.add(child);
			}
		}
		return hash;
	}
	
	//at the point when this procedure is called, all of the data is contained in
	//each individual node.  However, no 'document' is contained at each node
	//for the terms contained in its children.  We want to roll that information
	//up into the parent.
	void mergeChildLists(){
		//check to see if we have children, or are a leaf.
		if(children == null){
			//we are a leaf.  Hence, the document and list
			//of words for the individual are 1 and the same.
			lrListNode.myDocument = lrListNode.myWords;
			return;
		}
		//otherwise, we do have children.
		//for each child, merge their lists.
		for(int j=0; j < children.size();j++){
			children.get(j).mergeChildLists();
		}
		//now, our children have appropriate documents.  
		//what we want to do now is roll this information into the parent.
		ArrayList<ArrayList<EntryPair>> childLists = new ArrayList<ArrayList<EntryPair>>();
		for(int m=0; m < children.size(); m++){
			childLists.add(children.get(m).lrListNode.myDocument);
		}
		//now, combine the data for the childLists together.
		lrListNode.combineLists(childLists);
	}
	
	//produce the score (modified jaccard) between this node and another.
	double scoreNode(SchemaGraphNode other){
		//the two sets of word/concept counts that are going to be compared.
		ArrayList<EntryPair> otherDoc = other.lrListNode.myDocument;
		ArrayList<EntryPair> myDoc = lrListNode.myDocument;
		
		//We are performing a set intersection of sorts - maintain position
		//in each arraylist during intersection ops.
		int otherPos = 0;
		int myPos = 0;
		
		//Jaccard = intersection(other,me)/union(other,me)
		//so, we want to keep track of the total number of terms (ie. the bottom of the formula.
		double totalTerms = 0;
		//we also want the top of the formula.
		double termsInCommon = 0;
		
		//increment the two position pointers otherPos and myPos through the
		//two arrays.
		while(!(otherPos >= otherDoc.size() && myPos >= myDoc.size())){
			//record my word and the other word.
			int myTerm = myPos >= myDoc.size() ? Integer.MAX_VALUE: myDoc.get(myPos).wordConcept;
			int otherTerm = otherPos >= otherDoc.size() ? Integer.MAX_VALUE: otherDoc.get(otherPos).wordConcept;
			//if they are not equal, increment the total term counter
			//and move the appropriate position pointer to the next
			//spot in the array.
			if(myTerm < otherTerm){
				totalTerms+=myDoc.get(myPos++).count.doubleValue();
			}
			else if(otherTerm < myTerm){
				totalTerms+=otherDoc.get(otherPos++).count.doubleValue();
			}
			else{
				//if they are equal, increment the termsInCommon counter also.
				double myCount = myDoc.get(myPos++).count.doubleValue();
				double otherCount = otherDoc.get(otherPos++).count.doubleValue();
				totalTerms+=myCount+otherCount;
				if(myCount < otherCount){
					termsInCommon += myCount*2;
				}
				else{
					termsInCommon += otherCount*2;
				}
			}
		}
		//return the jaccard distance;
		return termsInCommon/totalTerms;
	}
}



