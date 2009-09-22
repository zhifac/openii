package org.mitre.harmony.matchers;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.SchemaElement;

/** Class for managing the mapping between types */
public class TypeMappings
{
	/** Stores the mapping between types */
	private HashMap<Class<SchemaElement>,ArrayList<Class<SchemaElement>>> mapping = new HashMap<Class<SchemaElement>,ArrayList<Class<SchemaElement>>>();

	/** Adds the specified pair of types to the mapping */
	public void addMapping(Class<SchemaElement> type1, Class<SchemaElement> type2)
	{
		ArrayList<Class<SchemaElement>> mappedTypes = mapping.get(type1);
		if(mappedTypes==null) mapping.put(type1, mappedTypes = new ArrayList<Class<SchemaElement>>());
		mappedTypes.add(type2);
	}
	
	/** Indicates if there is a mapping between the two specified classes */
	public boolean isMapped(SchemaElement element1, SchemaElement element2)
	{
		ArrayList<Class<SchemaElement>> mappedTypes = mapping.get(element1.getClass());
		return mappedTypes!=null && mappedTypes.contains(element2.getClass());
	}
	
	/** Returns the number of type pairs which have been mapped */
	public Integer size()
	{
		Integer size=0;
		for(ArrayList<Class<SchemaElement>> mappedTypes : mapping.values())
			size += mappedTypes.size();
		return size;
	}
}
