package org.mitre.openii.dialogs.projects.unity;

import java.util.HashMap;

import org.mitre.schemastore.model.terms.AssociatedElement;

/**
 * This is an extension of an associated element which also tracks other associated elements
 * and the distances between these elements
 * @author HAOLI
 */ @SuppressWarnings("serial")
public class SynsetElement extends AssociatedElement implements Comparable<SynsetElement>
{
	/** Stores distances to various synset elements */
	private HashMap<SynsetElement,Double> distances = new HashMap<SynsetElement,Double>();

	/** Constructs the synset element */
	public SynsetElement(Integer schemaID, Integer elementID, String name, String description)
		{ super(schemaID,elementID,name,description); }

	/** Adds a distance to a specific synset element */
	public void add(SynsetElement element, Double distance)
		{ distances.put(element, distance); }
	
	/** Returns the distance to the specified element */
	public Double getDistance(SynsetElement element)
		{ return distances.get(element); }

	/** Compares two synset elements */
	public int compareTo(SynsetElement element)
		{ return this.toString().compareTo(element.toString()); }
	
	/** Compares two synset elements */
	public boolean equals(SynsetElement element)
		{ return getSchemaID().equals(element.getSchemaID()) && getElementID().equals(element.getElementID()); }

	/** Outputs the synset element as a string */
	public String toString()
		{ return getSchemaID() + getName() + getElementID(); }
}

