// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;

/**
 *  Class for displaying relationship hierarchy
 */
public class RelationalGraphModelWithContainments extends RelationalGraphModel
{
	/** Constructs the relational graph */
	public RelationalGraphModelWithContainments(HierarchicalGraph graph)
		{ super(graph); }

	/** Returns the parent elements of the specified element in this graph */
	// PMORK: Allow an entity to return a list of its parents.
	public ArrayList<SchemaElement> getParentElements(Integer elementID)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// If attribute, return entity as parent
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Attribute)
			parentElements.add(graph.getEntity(elementID));
		
		// If an entity, return its super-types as parents.
		if(element instanceof Entity) {
			for (Subtype subtype : graph.getSubTypes(element.getId())) {
				Integer parentID = subtype.getParentID();
				if (!elementID.equals(parentID)) {
					parentElements.add(graph.getElement(parentID));
				}
			}
		}
		
		// If a containment, return the containing element as a parent.
		if(element instanceof Containment) {
			Containment c = (Containment)element;
			Integer parentID = c.getParentID();
			parentElements.add(graph.getElement(parentID));
		}

		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	// PMORK: Allow an entity to return a list of its children.
	public ArrayList<SchemaElement> getChildElements(Integer elementID)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// If entity, return attributes as children
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Entity)
			for(Attribute value : graph.getAttributes(elementID))
				childElements.add(value);

		// If an entity, return its sub-types as children.
		if(element instanceof Entity) {
			for (Subtype subtype : graph.getSubTypes(element.getId())) {
				Integer childID = subtype.getChildID();
				if (!elementID.equals(childID)) {
					childElements.add(graph.getElement(childID));
				}
			}
		}

		// If an entity, return its containments as children.
		if(element instanceof Entity) {
			for (Containment containment : graph.getContainments(element.getId())) {
				Integer childID = containment.getChildID();
				if (!elementID.equals(childID)) {
					childElements.add(containment);
				}
			}
		}

		return childElements;
	}
	
}