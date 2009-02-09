package org.mitre.harmony.matchers;

/** Class for storing a pair of elements */
public class ElementPair
{
	/** Stores element1 */
	private Integer element1;
	
	/** Stores element2 */
	private Integer element2;
	
	/** Constructs the element pair */
	public ElementPair(Integer element1, Integer element2)
	{
		this.element1 = element1<element2 ? element1 : element2;
		this.element2 = element1<element2 ? element2 : element1;
	}
	
	/** Returns element1 */
	public Integer getElement1()
		{ return element1; }
	
	/** Returns element2 */
	public Integer getElement2()
		{ return element2; }

	/** Provides a hash code for the element pair */
	public int hashCode()
		{ return element1*element1 + element2; }
	
	/** Indicates if two voter scores are equivalent */
	public boolean equals(Object object)
	{
		if(!(object instanceof ElementPair)) return false;
		ElementPair pair = (ElementPair)object;
		return element1.equals(pair.element1) && element2.equals(pair.element2);
	}
}