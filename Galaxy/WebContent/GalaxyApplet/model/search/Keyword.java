// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.search;

import java.util.regex.Pattern;

/** Class for storing a keyword */
public class Keyword
{	
	// Constants for storing various keyword types
	static public Integer SCHEMA = 0;
	static public Integer ENTITY = 1;
	static public Integer DOMAIN = 2;
	static public Integer RELATIONSHIP = 3;
	
	/** Stores the keyword */
	private String keyword;
	
	/** Stores the keyword type */
	private Integer type;
	
	/** Stores the keyword pattern */
	private Pattern pattern;
	
	/** Retrieves the type associated with the specified keyword */
	static private Integer getType(String keyword)
	{
		if(keyword.matches(".+:.+"))
		{
			if(keyword.startsWith("schema:")) return SCHEMA;
			if(keyword.startsWith("entity:")) return ENTITY;
			if(keyword.startsWith("domain:")) return DOMAIN;
			if(keyword.startsWith("relationship:")) return RELATIONSHIP;
		}
		return null;
	}
	
	/** Constructs a keyword */
	public Keyword(String keyword)
	{
		keyword = keyword.toLowerCase();
		this.keyword = keyword.replaceAll(".*:","");
		this.type = getType(keyword);
	}
	
	/** Returns the keyword */
	public String getKeyword() { return keyword; }
	
	/** Returns the keyword type */
	public Integer getType() { return type; }

	/** Returns the keyword pattern */
	public Pattern getPattern()
	{
		if(pattern==null)
			pattern = Pattern.compile(keyword.replaceAll("\\*",""));
		return pattern;
	}
	
	/** Indicates if the keyword is contained within the specified string */
	public boolean isContainedIn(String string)
	{
		boolean prefix = keyword.startsWith("*");
		boolean postfix = keyword.endsWith("*");
		String regexp = (prefix?"":"\\b") + keyword.replaceAll("\\*","") + (postfix?"":"\\b");
		return string.toLowerCase().matches(".*" + regexp + ".*");
	}
}