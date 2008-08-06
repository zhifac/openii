// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.graph.*;
import java.util.*;

/** Stores a schema element and its associated alias ID */
public class AliasedSchemaElement extends SchemaElement implements Comparable<AliasedSchemaElement>, GraphSchemaElement
{

	private SchemaElement graphElement;
	
	/** Constructs the aliased schema element */
	public AliasedSchemaElement(SchemaElement ge)
		{ 
			graphElement = ge; 
		}

	public ArrayList<SchemaElement> getChildren()
		{ return ((GraphSchemaElement)graphElement).getChildren();}
	public ArrayList<SchemaElement> getParents()
		{ return ((GraphSchemaElement)graphElement).getParents();}
		
	/** Returns the schema element ID */
	public Integer getId()
		{ return ((SchemaElement)graphElement).getId(); }
	
	/** Returns the schema element alias ID */
	public Integer getAliasId()
		{ return ((GraphSchemaElement)graphElement).getAlias().getId(); }
	
	/** Returns the schema element */
	public SchemaElement getElement()
		{ return (SchemaElement)graphElement; }
	
	/** Returns the schema element alias */
	public GraphAlias getAlias()
		{ return ((GraphSchemaElement)graphElement).getAlias(); }
	
	public void setAlias(GraphAlias a)
		{ }

	
	/** Returns the schema element name */
	public String getName()
	{ 
		if (((GraphSchemaElement)graphElement).getAlias() != null) {
			return ((GraphSchemaElement)graphElement).getAlias().getName();
			
		}
		else {
			if (graphElement instanceof GraphEntity && (((SchemaElement)graphElement).getName() == null || ((SchemaElement)graphElement).getName().length() == 0)) 
				return ((GraphEntity)graphElement).getPath();
			else
				return ((SchemaElement)graphElement).getName(); 
		}
	}
	
	/** Compares two aliased schema elements to one another */
	public int compareTo(AliasedSchemaElement aliasedSchemaElement)
		{ return getName().compareTo(aliasedSchemaElement.getName()); }
}
