// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.graph.*;
import java.util.*;

/** Stores a schema element and its associated alias ID */
public class AliasedSchemaElement extends SchemaElement implements Comparable<AliasedSchemaElement>, GraphSchemaElement
{

	private GraphSchemaElement graphElement;
	
	/** Constructs the aliased schema element */
	public AliasedSchemaElement(GraphSchemaElement ge)
		{ graphElement = ge; }

	public ArrayList<SchemaElement> getChildren()
		{ return graphElement.getChildren();}
	public ArrayList<SchemaElement> getParents()
		{ return graphElement.getParents();}
		
	/** Returns the schema element ID */
	public Integer getId()
		{ return ((SchemaElement)graphElement).getId(); }
	
	/** Returns the schema element alias ID */
	public Integer getAliasId()
		{ return graphElement.getAlias().getId(); }
	
	/** Returns the schema element */
	public SchemaElement getElement()
		{ return (SchemaElement)graphElement; }
	
	/** Returns the schema element alias */
	public GraphAlias getAlias()
		{ return graphElement.getAlias(); }
	
	public void setAlias(GraphAlias a)
		{ }

	
	/** Returns the schema element name */
	public String getName()
	{ 
		if (graphElement.getAlias() != null) return graphElement.getAlias().getName();
		else return ((SchemaElement)graphElement).getName(); 
	}
	
	/** Compares two aliased schema elements to one another */
	public int compareTo(AliasedSchemaElement aliasedSchemaElement)
		{ return getName().compareTo(aliasedSchemaElement.getName()); }
}
