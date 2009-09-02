// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.schemaInfo.model;

import java.util.ArrayList;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 *  Class for displaying domain hierarchy
 */
public class DomainSchemaModel extends SchemaModel
{
	/** Returns the schema model name */
	public String getName()
		{ return "Domain"; }
	
	/** Returns the root elements in this schema */
	public ArrayList<SchemaElement> getRootElements(HierarchicalSchemaInfo schemaInfo)
		{ return schemaInfo.getElements(Domain.class); }
	
	/** Returns the parent elements of the specified element in this schema */
	public ArrayList<SchemaElement> getParentElements(HierarchicalSchemaInfo schemaInfo, Integer elementID)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// If domain value, return domain as parent
		SchemaElement element = schemaInfo.getElement(elementID);
		if(element instanceof DomainValue)
			parentElements.add((Domain)schemaInfo.getElement(((DomainValue)element).getDomainID()));
		
		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this schema */
	public ArrayList<SchemaElement> getChildElements(HierarchicalSchemaInfo schemaInfo, Integer elementID)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// If domain, return domain values as children
		SchemaElement element = schemaInfo.getElement(elementID);
		if(element instanceof Domain)
			for(DomainValue value : schemaInfo.getDomainValuesForDomain(elementID))
				childElements.add(value);

		return childElements;
	}
	
	/** Returns the domains of the specified element in this schema */
	public Domain getDomainForElement(HierarchicalSchemaInfo schemaInfo, Integer elementID)
		{ return null; }
	
	/** Returns the elements referenced by the specified domain */
	public ArrayList<SchemaElement> getElementsForDomain(HierarchicalSchemaInfo schemaInfo, Integer domainID)
		{ return new ArrayList<SchemaElement>(); }
	
	/** Returns the type name associated with the specified element (or NULL if element has no name) */
	public SchemaElement getType(HierarchicalSchemaInfo schemaInfo, Integer elementID)
		{ return null; }
}