package org.mitre.harmony.matchers;

import java.util.HashSet;

import org.mitre.schemastore.model.SchemaElement;

/** Class for managing the mapping between types */
public class TypeMappings
{	
	/** Stores the mapping between types */
	private HashSet<String> mapping = new HashSet<String>();
	
	/** Returns the key associated with the class pair */
	private String getKey(Class type1, Class type2)
		{ return type1.getSimpleName() + "_" + type2.getSimpleName(); }
	
	/** Adds the specified pair of types to the mapping */
	public void addMapping(Class<SchemaElement> type1, Class<SchemaElement> type2)
		{ mapping.add(getKey(type1,type2)); }
	
	/** Indicates that all types should be mapped */
	public void mapAll()
		{ mapping=null; }
	
	/** Indicates if there is a mapping between the two specified classes */
	public boolean isMapped(SchemaElement element1, SchemaElement element2)
		{ return mapping==null || mapping.contains(getKey(element1.getClass(),element2.getClass())); }
	
	/** Returns the number of type pairs which have been mapped */
	public Integer size()
		{ return mapping.size(); }
}
