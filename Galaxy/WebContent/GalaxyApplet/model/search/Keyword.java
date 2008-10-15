// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.search;

import java.util.regex.Pattern;

/** Class for storing a keyword */
public class Keyword
{	
	/** Stores the keyword */
	private String keyword;
	
	/** Stores the keyword type */
	private Integer type;
	
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
	static private Integer getType(String keyword)
	{
		if(keyword.matches(".+:.+"))
		{
			if(keyword.startsWith("schema:")) return SearchManager.SCHEMA;
			if(keyword.startsWith("entity:")) return SearchManager.ENTITY;
			if(keyword.startsWith("domain:")) return SearchManager.DOMAIN;
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
	public Integer getType() { return type; }

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