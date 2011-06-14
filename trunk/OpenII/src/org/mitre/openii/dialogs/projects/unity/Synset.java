package org.mitre.openii.dialogs.projects.unity;

import java.util.ArrayList;

/**
 * A Synset is an arraylist of terms.
 * 
 * A Synset is just an arraylist of Nodes, that can do a few tricks. This object
 * came from Michael Morse's clustering code, with lots of modifications by
 * David and Maya. The way we actually use it in our code, a Synset is a
 * cluster. The hierarchical clustering code's result is a list of synsets,
 * which are "chains" of pairwise matches that all logically group together.
 * 
 * @author MDMORSE
 * 
 */
public class Synset implements Comparable<Synset>
{
	/** Stores the list of synset elements */
	private ArrayList<SynsetElement> elements = new ArrayList<SynsetElement>();
	private SynsetElement leastNode = null;

	/** Constructs the synset */
	public Synset(SynsetElement element)
		{ add(element); }

	/** Adds a term to the synset */
	public void add(SynsetElement element)
	{
		if(!elements.contains(element)) elements.add(element);
		if(leastNode == null || leastNode.compareTo(element)>0) leastNode = element;
	}

	/** Calculates out the complete linkage between this synset and the specified synset */
	double completeLinkage(Synset synset)
	{
		double maxDistance = 0;
		for(SynsetElement element1 : elements)
			for(SynsetElement element2 : synset.elements)
			{
				Double distance = element1.getDistance(element2);
				if(distance!=null && distance>maxDistance)
					maxDistance = distance;
			}
		return maxDistance;
	}
	
	/** Returns the synset elements that belong to this synset */
	public ArrayList<SynsetElement> getElements()
		{ return elements; }

	/** Compares two synsets to one another */
	public int compareTo(Synset synset)
		{ return this.leastNode.compareTo(synset.leastNode); }

	/** Returns the synset element that belongs to the specified schema */
	public SynsetElement getElementBySchemaID(Integer schemaID)
	{
		for(SynsetElement element : elements)
			if(element.getSchemaID().equals(schemaID)) return element;
		return null;
	}
}