// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.schemastore.porters.mappingExporters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 * Class for exporting a list of the top 100 matches from the project
 * @author CWOLF
 */
public class TopMatchExporter extends MappingExporter
{	
	/** Stores a hash map of all schema elements which exist in mapping */
	private HashMap<Integer,SchemaElement> elements = null;
	
	/** Stores an array of all schemas which exist in mapping */
	ArrayList<HierarchicalSchemaInfo> schemaInfoList = new ArrayList<HierarchicalSchemaInfo>();
	
	/** Returns the exporter name */
	public String getName()
		{ return "Top Match Exporter"; }
	
	/** Returns the exporter description */
	public String getDescription()
		{ return "This exporter can be used to export all pairings of terms within the mapping"; }
	
	/** Returns the file types associated with this converter */
	public String getFileType()
		{ return ".csv"; }
	
	/** Generates a list of the top 100 matches for this project */
	public void exportMapping(Mapping mapping, ArrayList<MappingCell> mappingCells, File file) throws IOException
	{		
		// Prepare to export source and target node information
		BufferedWriter out = new BufferedWriter(new FileWriter(file));

		// Initialize the schemas and schema element lists
		elements = getSchemaElements(Arrays.asList(mapping.getSchemaIDs()));
		for(MappingSchema schema : mapping.getSchemas())
			schemaInfoList.add(new HierarchicalSchemaInfo(client.getSchemaInfo(schema.getId())));
			
		// Get the list of mapping cells
		CompressedList matchList = new CompressedList();
		for(MappingCell mappingCell : mappingCells)
			matchList.addMappingCell(mappingCell);
		
    	// Outputs the top mapping cells
		List<CompressedMatch> matches = matchList.getMatches();
		Collections.sort(matches);
		if(matches.size()>100) matches = matches.subList(0, 100);
  		for(CompressedMatch match : matches)
    		out.write(match.toString() + "\n");
    	out.close();
    	
    	// Clear out schema and schema element lists
    	elements = null; schemaInfoList = null;
	}

	/** Gets paths for the specified element */
	private HashSet<String> getPaths(Integer elementID)
	{
		HashSet<String> paths = new HashSet<String>();
		for(HierarchicalSchemaInfo schemaInfo : schemaInfoList)
			for(ArrayList<SchemaElement> pathElements : schemaInfo.getPaths(elementID))
			{
				StringBuffer path = new StringBuffer("");
				for(SchemaElement element : pathElements)
					path.append("/"+element.getName());
				paths.add(path.toString());
			}
		return paths;			
	}	

	/** Class used to store the compressed match */
	class CompressedMatch implements Comparable<CompressedMatch>
	{
		/** Stores the matched elements */
		private SchemaElement element1, element2;
		
		/** Stores the element paths */
		private HashSet<String> paths1, paths2; 
		
		/** Stores the match score */
		private Double score;

		/** Constructs the compressed match */
		private CompressedMatch(SchemaElement element1, SchemaElement element2, Double score)
		{
			// Initialize the compressed match
			this.element1 = element1;
			this.element2 = element2;
			this.score = score;
			this.paths1 = getPaths(element1.getId());
			this.paths2 = getPaths(element2.getId());
		}
		
		/** Merge together compressed matches */
		private void merge(CompressedMatch match)
			{ paths1.addAll(match.paths1); paths2.addAll(match.paths2); }
		
		/** Outputs the paths as a string */
		private String toString(HashSet<String> paths)
		{
			StringBuffer out = new StringBuffer("");
			for(String path : paths)
				out.append(path+"\n");
			return out.substring(0,out.length()-1);
		}
		
		/** Outputs the match as a string */
		public String toString()
		{ 
			// Retrieve the name and description info
			String sName = element1.getName();
			String sDesc = element1.getDescription().replaceAll("\"", "'");
			String tName = element2.getName();
			String tDesc = element2.getDescription().replaceAll("\"", "'");
			return sName+",\""+sDesc+"\",\""+toString(paths1)+"\","+tName+",\""+tDesc+"\",\""+toString(paths2)+"\","+score;			
		}
		
		/** Compares to another compressed match */
		public int compareTo(CompressedMatch match)
			{ return match.score.compareTo(score); }		
	}
	
	/** Class used to store the compress mapping cell list */
	class CompressedList
	{
		/** Stores the list of compressed mapping cells */
		private HashMap<String,CompressedMatch> matches = new HashMap<String,CompressedMatch>();

		/** Generates the hash key */
		private String getKey(CompressedMatch match)
		{
			String element1String = match.element1.getName() + "|" + match.element1.getDescription();
			String element2String = match.element2.getName() + "|" + match.element2.getDescription();
			return element1String + "|" + element2String + "|" + match.score;
		}
		
		/** Adds a mapping cell to the list */
		private void addMappingCell(MappingCell mappingCell)
		{
  			// Construct the compressed match
   			SchemaElement element1 = elements.get(mappingCell.getElement1());
   			SchemaElement element2 = elements.get(mappingCell.getElement2());
   			CompressedMatch newMatch = new CompressedMatch(element1,element2,mappingCell.getScore());

   			// Add match to list of compressed matches
   			String key = getKey(newMatch);
   			CompressedMatch match = matches.get(key);
   			if(match==null) matches.put(key,newMatch);
   			else match.merge(newMatch);
		}
		
		/** Returns the list of compressed mapping cells */
		private ArrayList<CompressedMatch> getMatches()
			{ return new ArrayList<CompressedMatch>(matches.values()); }
	}
}