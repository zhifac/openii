// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.SchemaElement;

/** Stores a schema element and its associated alias ID */
public class AliasedSchemaElement implements Comparable<AliasedSchemaElement>
{
	/** Stores the schema element ID */
	private Integer schemaElementID;
	
	/** Stores the schema element alias */
	private Integer aliasID;
	
	/** Constructs the aliased schema element */
	public AliasedSchemaElement(Integer schemaID, Integer schemaElementID)
	{
		this.schemaElementID = schemaElementID;
		Alias alias = Schemas.getGraph(schemaID).getAlias(schemaElementID);
		if(alias!=null) aliasID = alias.getId();
	}
	
	/** Returns the schema element ID */
	public Integer getId()
		{ return schemaElementID; }
	
	/** Returns the schema element alias ID */
	public Integer getAliasId()
		{ return aliasID; }
	
	/** Returns the schema element */
	public SchemaElement getElement()
		{ return Schemas.getSchemaElement(schemaElementID); }
	
	/** Returns the schema element alias */
	public Alias getAlias()
		{ return (Alias)Schemas.getSchemaElement(aliasID); }
	
	/** Returns the schema element name */
	public String getName()
		{ return Schemas.getSchemaElement(aliasID!=null?aliasID:schemaElementID).getName(); }

	/** Compares two aliased schema elements to one another */
	public int compareTo(AliasedSchemaElement aliasedSchemaElement)
		{ return getName().compareTo(aliasedSchemaElement.getName()); }
}
