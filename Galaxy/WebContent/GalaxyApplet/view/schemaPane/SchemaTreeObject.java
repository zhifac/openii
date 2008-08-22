// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.schemaPane;

import java.util.ArrayList;

import model.Schemas;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

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
	
	/** Stores the object type */
	private Integer type;
	
	/** Constructs the schema tree object */
	public SchemaTreeObject(Integer elementID, HierarchicalGraph sGraph, HierarchicalGraph cGraph)
	{
		boolean hasDomain = false;
		
		// Identify the selected id
		SchemaElement sElement = sGraph.getElement(elementID);
		if(sElement!=null)
		{
			selectedID = elementID;
			hasDomain |= sGraph.getDomainForElement(elementID)!=null;
			Alias alias = sGraph.getAlias(elementID);
			if(alias!=null) selectedAliasID = alias.getId();
		}

		// Identify the compared id
		SchemaElement cElement = cGraph==null ? null : cGraph.getElement(elementID);
		if(cElement!=null)
		{
			comparedID = elementID;
			hasDomain |= sGraph.getDomainForElement(elementID)!=null;
			Alias alias = cGraph.getAlias(elementID);
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
	
	/** Returns if node is in selected graph */
	public Boolean inSelectedGraph()
		{ return selectedID!=null; }
	
	/** Returns if node is in compared graph */
	public Boolean inComparedGraph()
		{ return comparedID!=null; }
	
	/** Returns the node label */
	public String getName()
	{
		// Identify which ID to use in naming
		Integer sID = selectedAliasID!=null ? selectedAliasID : selectedID;
		Integer cID = comparedAliasID!=null ? comparedAliasID : comparedID;
		
		// Handles a merged schema tree object
		if(sID!=null && cID!=null)
		{
			String selectedName = Schemas.getSchemaElement(sID).getName();
			String comparedName = Schemas.getSchemaElement(cID).getName();
			if(sID.equals(cID)) return selectedName;
			else return selectedName + " (" + comparedName + ")";
		}
		
		// Handles an object associated only with the selected or compared schema
		if(sID!=null) return Schemas.getSchemaElement(sID).getName();
		if(cID!=null) return Schemas.getSchemaElement(cID).getName();

		// Handles an unassociated object
		return "";
	}
	
	/** Returns the object type */
	public Integer getType()
		{ return type; }
}