// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.schemaPane;

import java.util.ArrayList;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/** Stores a schema tree object */
public class SchemaTreeObject
{
	// Stores the possible object types
	static final Integer ENTITY = 0;
	static final Integer ATTRIBUTE = 1;
	static final Integer DOMAIN_VALUE = 2;
	
	/** Stores the selected schema element id */
	private Integer selectedID, selectedAliasID;
	
	/** Stores the compared schema element id */
	private Integer comparedID, comparedAliasID;
	
	/** Stores the selected and compared names */
	private String selectedName, comparedName;
	
	/** Stores the object type */
	private Integer type;
	
	/** Constructs the schema tree object */
	public SchemaTreeObject(Integer elementID, HierarchicalSchemaInfo sInfo, HierarchicalSchemaInfo cInfo)
	{
		boolean hasDomain = false;
		
		// Identify the selected id
		SchemaElement sElement = sInfo.getElement(elementID);
		if(sElement!=null)
		{
			selectedID = elementID;
			selectedName = sInfo.getDisplayName(elementID);
			hasDomain |= sInfo.getDomainForElement(elementID)!=null;
			Alias alias = sInfo.getAlias(elementID);
			if(alias!=null) selectedAliasID = alias.getId();
		}

		// Identify the compared id
		SchemaElement cElement = cInfo==null ? null : cInfo.getElement(elementID);
		if(cElement!=null)
		{
			comparedID = elementID;
			comparedName = cInfo.getDisplayName(elementID);
			hasDomain |= cInfo.getDomainForElement(elementID)!=null;
			Alias alias = cInfo.getAlias(elementID);
			if(alias!=null) comparedAliasID = alias.getId();
		}

		// Determine the object type
		SchemaElement element = sElement!=null ? sElement : cElement;
		if(element instanceof DomainValue) type = DOMAIN_VALUE;
		else if(hasDomain) type = ATTRIBUTE;
		else type = ENTITY;
	}

	/** Returns the list of associated IDs */
	public ArrayList<Integer> getIDs()
	{
		ArrayList<Integer> ids = new ArrayList<Integer>();
		if(selectedID!=null)
			{ ids.add(selectedID); if(selectedAliasID!=null) ids.add(selectedAliasID); }
		if(comparedID!=null)
			{ if(selectedID!=null) ids.add(comparedID); if(comparedAliasID!=null) ids.add(comparedAliasID); }
		return ids;
	}
	
	/** Returns if node is in selected schema */
	public Boolean inSelectedSchema()
		{ return selectedID!=null; }
	
	/** Returns if node is in compared schema */
	public Boolean inComparedSchema()
		{ return comparedID!=null; }
	
	/** Returns the node label */
	public String getName()
	{
		// Handles a merged schema tree object
		if(selectedName!=null && comparedName!=null)
		{
			if(selectedName.equals(comparedName)) return selectedName;
			else return selectedName + " (" + comparedName + ")";
		}
		
		// Handles an object associated only with the selected or compared schema
		if(selectedName!=null) return selectedName;
		if(comparedName!=null) return comparedName;

		// Handles an unassociated object
		return "";
	}
	
	/** Returns the object type */
	public Integer getType()
		{ return type; }
}