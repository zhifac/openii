// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.schemaInfo.model;

import java.util.ArrayList;

import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 *  Class for displaying relationship hierarchy
 */
public class TaxonomyModel extends SchemaModel
{
	/** Returns the schema model name */
	public String getName()
		{ return "Taxonomy"; }
	
	/** Returns the root elements in this schema */
	public ArrayList<SchemaElement> getRootElements(HierarchicalSchemaInfo schemaInfo)
	{
		// Start with all Entities.
		ArrayList<SchemaElement> result = schemaInfo.getElements(Entity.class);
		// Remove entities that are sub-classes of some other entity.
		for (SchemaElement schemaElement : schemaInfo.getElements(Subtype.class))
		{
			Subtype subtype = (Subtype)schemaElement;
			result.remove(schemaInfo.getElement(subtype.getChildID()));
		}
		// The entities that remain are the root entities.
		return result;
	}
	
	/** Returns the parent elements of the specified element in this schema */
	public ArrayList<SchemaElement> getParentElements(HierarchicalSchemaInfo schemaInfo, Integer elementID)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		SchemaElement element = schemaInfo.getElement(elementID);
		
		// If an entity, return its super-types as parents.
		if(element instanceof Entity) {
			for (Subtype subtype : schemaInfo.getSubTypes(element.getId())) {
				Integer parentID = subtype.getParentID();
				if (!elementID.equals(parentID)) {
					parentElements.add(schemaInfo.getElement(parentID));
				}
			}
		}
		
		// If element is relationship, return JUST the left id.
		if (element instanceof Relationship)
		{
			parentElements.add(schemaInfo.getElement(((Relationship)element).getLeftID()));
		}
		
		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this schema */
	public ArrayList<SchemaElement> getChildElements(HierarchicalSchemaInfo schemaInfo, Integer elementID)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		SchemaElement element = schemaInfo.getElement(elementID);
		
		// Produce the list of children elements (only entities have children elements)
		if(element instanceof Entity)
		{
			// Retrieve relationships as children, ONLY if the left ID matches.
			for (Relationship rel : schemaInfo.getRelationships(elementID))
			{
				Integer leftID = rel.getLeftID();
				if (elementID.equals(leftID))
					childElements.add(rel);
			}
			
			// Retrieve subtypes as children
			for (Subtype subtype : schemaInfo.getSubTypes(element.getId()))
			{
				Integer childID = subtype.getChildID();
				if (!elementID.equals(childID))
					childElements.add(schemaInfo.getElement(childID));
			}
		}

		return childElements;
	}
	
	/** Returns the domains of the specified element in this schema */
	public Domain getDomainForElement(HierarchicalSchemaInfo schemaInfo, Integer elementID)
	{
		return null;
	}	
	
	/** Returns the elements referenced by the specified domain */
	public ArrayList<SchemaElement> getElementsForDomain(HierarchicalSchemaInfo schemaInfo, Integer domainID)
	{
		return new ArrayList<SchemaElement>();
	}

	/** Returns the type name associated with the specified element (or NULL if element has no name) */
	public SchemaElement getType(HierarchicalSchemaInfo schemaInfo, Integer elementID)
	{
		return null;	
	}
}