// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;

/**
 *  Class for displaying relationship hierarchy
 */
public class RelationalGraph extends HierarchicalGraph
{
	/** Constructs the relational graph */
	public RelationalGraph(Graph graph)
		{ super(graph); }

	/** Returns the root elements in this graph */
	// PMORK: Do not include any entities that have a parent entity.
	public ArrayList<SchemaElement> getRootElements()
	{
		ArrayList<SchemaElement> result = getElements(Entity.class);
		for (SchemaElement schemaElement : getElements(Subtype.class)) {
			Subtype subtype = (Subtype)schemaElement;
			result.remove(subtype.getChildID());
		}
		return result;
//		return getSchemaElements(Entity.class); // Returns all entities, not just the root entities!
	}
	
	/** Returns the parent elements of the specified element in this graph */
	// PMORK: Allow an entity to return a list of its parents.
	public ArrayList<SchemaElement> getParentElements(SchemaElement element)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// If attribute, return entity as parent
		if(element instanceof Attribute)
			parentElements.add(getEntity(element.getId()));
		
		// If an entity, return its super-types as parents.
		if(element instanceof Entity) {
			for (Subtype subtype : getSubTypes(element.getId())) {
				Integer parentID = subtype.getParentID();
				if (!element.equals(parentID)) {
					parentElements.add(getElement(parentID));
				}
			}
		}
		
		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	// PMORK: Allow an entity to return a list of its children.
	public ArrayList<SchemaElement> getChildElements(SchemaElement element)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// If entity, return attributes as children
		if(element instanceof Entity)
			for(Attribute value : getAttributes(element.getId()))
				childElements.add(value);

		// If an entity, return its super-types as parents.
		if(element instanceof Entity) {
			for (Subtype subtype : getSubTypes(element.getId())) {
				Integer childID = subtype.getChildID();
				if (!element.equals(childID)) {
					childElements.add(getElement(childID));
				}
			}
		}
		return childElements;
	}
	
	/** Returns the domains of the specified element in this graph */
	public Domain getDomain(SchemaElement element)
	{
		if(element instanceof Attribute)
			return getDomain(element);
		return null;
	}
}