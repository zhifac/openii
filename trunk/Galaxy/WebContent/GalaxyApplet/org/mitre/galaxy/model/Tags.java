// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.mitre.galaxy.model.server.SchemaStoreManager;
import org.mitre.schemastore.model.Tag;


/**
 * Class for managing the tags in the schema repository
 * @author CWOLF
 */
public class Tags
{
	/** Caches schema information */
	static private HashMap<Integer,Tag> tags = null;
	
	/** Caches tag schemas */
	static private HashMap<Integer,ArrayList<Integer>> tagSchemaHash = new HashMap<Integer,ArrayList<Integer>>();
	
	/** Caches schema tags */
	static private HashMap<Integer,ArrayList<Integer>> schemaTagHash = new HashMap<Integer,ArrayList<Integer>>();
		
	//-------------
	// Tag Actions
	//-------------
	
	/** Initializes the tags list */
	static private void initTags()
	{
		tags = new HashMap<Integer,Tag>();
		ArrayList<Tag> serverTags = SchemaStoreManager.getTags();
		if(serverTags!=null)
			for(Tag tag : serverTags)
				tags.put(tag.getId(),tag);	
	}
	
	/** Returns a list of base tags */
	static public ArrayList<Tag> getBaseTags()
	{
		if(tags==null) initTags();
		ArrayList<Tag> baseTags = new ArrayList<Tag>();
		for(Tag tag : tags.values())
			if(tag.getParentId()==null)
				baseTags.add(tag);
		return baseTags;
	}
	
	/** Returns a list of sub-categories for the specified tag */
	static public ArrayList<Tag> getSubcategories(Integer tagID)
	{
		if(tags==null) initTags();
		ArrayList<Tag> subcategories = new ArrayList<Tag>();
		for(Tag tag : tags.values())
			if(tag.getParentId()!=null && tag.getParentId().equals(tagID))
				subcategories.add(tag);
		return subcategories;		
	}
	
	/** Returns the specified tag */
	static public Tag getTag(Integer tagID)
	{
		if(tags==null) initTags(); 
		return tags.get(tagID);
	}

	/** Returns a list of schemas associated with the specified tag */
	static public ArrayList<Integer> getTagSchemas(Integer tagID)
	{
		ArrayList<Integer> tagSchemas = tagSchemaHash.get(tagID);
		if(tagSchemas==null)
			tagSchemaHash.put(tagID, tagSchemas=SchemaStoreManager.getTagSchemas(tagID));
		return tagSchemas;
	}
	
	/** Returns the tags associated with the specified schema */
	static public ArrayList<Integer> getSchemaTags(Integer schemaID)
	{
		ArrayList<Integer> schemaTags = schemaTagHash.get(schemaID);
		if(schemaTags==null)
			schemaTagHash.put(schemaID, schemaTags=SchemaStoreManager.getSchemaTags(schemaID));
		return schemaTags;
	}

	/** Returns the number of schemas classified under the specified tag */
	static public Integer getTagSchemaCount(Integer tagID)
	{
		Integer count = getTagSchemas(tagID).size();
		for(Tag subcategory : getSubcategories(tagID))
			count += getTagSchemaCount(subcategory.getId());
		return count;
	}
	
	/** Sorts the list of tag */
	static public ArrayList<Tag> sort(ArrayList<Tag> tags)
	{
		final class TagComparator implements Comparator<Tag>
		{
			public int compare(Tag tag1, Tag tag2)
				{ return tag1.getName().compareTo(tag2.getName()); }
		}
		Collections.sort(tags,new TagComparator());
		return tags;
	}

	/** Sorts the list of tags by ID */
	static public ArrayList<Integer> sortByID(ArrayList<Integer> tags)
	{
		final class TagComparator implements Comparator<Integer>
		{
			public int compare(Integer tag1, Integer tag2)
				{ return getTag(tag1).getName().compareTo(getTag(tag2).getName()); }
		}
		Collections.sort(tags,new TagComparator());
		return tags;
	}
}
