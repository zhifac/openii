// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.search;

import java.util.regex.Pattern;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;

/** Class for storing a keyword */
public class Keyword
{
	/** Enumeration for storing keyword types */
	static public Integer SCHEMA = 0;
	static public Integer ENTITY = 1;
	static public Integer ATTRIBUTE = 2;
	static public Integer DOMAIN = 3;
	static public Integer RELATIONSHIP = 4;
	
	/** Stores the keyword */
	private String keyword;
	
	/** Stores the keyword type */
	private Class type;
	
	/** Stores the keyword pattern */
	private Pattern pattern;
	
	/** Generates the search keyword */
	static private String getKeyword(String keyword)
	{
		// First pull off prefixed type labels
		keyword = keyword.replaceAll(".*:","");
		
		// Return the generated keyword
		boolean prefix = keyword.startsWith("*");
		boolean postfix = keyword.endsWith("*");
		return (prefix?"":"\\b") + keyword.replaceAll("\\*","") + (postfix?"":"\\b");
	}
	
	/** Retrieves the type associated with the specified keyword */
	static private Class getType(String keyword)
	{
		if(keyword.matches(".+:.+"))
		{
			if(keyword.startsWith("schema:")) return Schema.class;
			if(keyword.startsWith("entity:")) return Entity.class;
			if(keyword.startsWith("attribute:")) return Attribute.class;
			if(keyword.startsWith("domain:")) return DomainValue.class;
			if(keyword.startsWith("relationship:")) return Relationship.class;
		}
		return null;
	}
	
	/** Constructs a keyword */
	public Keyword(String keyword)
	{
		keyword = keyword.toLowerCase();
		this.keyword = getKeyword(keyword);
		this.type = getType(keyword);
	}
	
	/** Returns the keyword type */
	public Class getType() { return type; }

	/** Returns the keyword pattern */
	public Pattern getPattern()
	{
		if(pattern==null)
			pattern = Pattern.compile(keyword);
		return pattern;
	}
	
	/** Indicates if the keyword is contained within the specified string */
	public boolean isContainedIn(String string)
		{ return string.toLowerCase().matches(".*" + keyword + ".*"); }
}
