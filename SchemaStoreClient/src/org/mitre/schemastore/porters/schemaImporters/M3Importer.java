// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.schemaImporters;

import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.graph.Graph;
import org.mitre.schemastore.model.xml.ConvertFromXML;
import org.mitre.schemastore.porters.ImporterException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** Importer for copying schemas from other repositories */
public class M3Importer extends SchemaImporter
{
	/** Private class for sorting schema elements */
	class SchemaElementComparator implements Comparator<SchemaElement>
	{
		public int compare(SchemaElement element1, SchemaElement element2)
		{
			// Retrieve the base schemas for the specified elements
			Integer base1 = element1.getBase(); if(base1==null) base1=-1;
			Integer base2 = element2.getBase(); if(base2==null) base2=-1;
			
			// Returns a comparator value for the compared elements
			if(!base1.equals(base2))
				return base1.compareTo(base2);
			if(element1.getClass()!=element2.getClass())
				return element1.getClass().toString().compareTo(element2.getClass().toString());
			return element1.getId().compareTo(element2.getId());
		}
	}
	
	/** Private class for storing a graph that contains parent schema information */ @SuppressWarnings("serial")
	private class ExtendedGraph extends Graph implements Comparable<ExtendedGraph>
	{
		/** Stores the parent schemas */
		private ArrayList<Integer> parentSchemaIDs;
		
		/** Constructs the extended graph */
		private ExtendedGraph(Schema schema, ArrayList<Integer> parentSchemaIDs, ArrayList<SchemaElement> elements)
		{
			super(schema,elements);
			this.parentSchemaIDs = parentSchemaIDs;
		}
		
		/** Returns the parent schema IDs */
		private ArrayList<Integer> getParentSchemaIDs()
			{ return parentSchemaIDs; }
		
		/** Compares two extended graphs based on the schema ID */
		public int compareTo(ExtendedGraph graph)
			{ return getSchema().getId().compareTo(graph.getSchema().getId()); }
	}
	
	/** Stores the list of extended schemas */
	private ArrayList<Integer> extendedSchemaIDs = new ArrayList<Integer>();

	/** Stores the list of schema elements */
	private ArrayList<SchemaElement> schemaElements = null;
	
	/** Returns the importer name */
	public String getName()
		{ return "M3 Importer"; }
	
	/** Returns the importer description */
	public String getDescription()
		{ return "This importer can be used to download a schema in the M3 format"; }
		
	/** Returns the importer URI type */
	public Integer getURIType()
		{ return M3MODEL; }

	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes()
	{
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".m3s");
		return fileTypes;
	}
	
	/** Returns the schema  from the specified URI */
	public SchemaProperties generateSchemaProperties() throws ImporterException
	{
		try {
			ArrayList<ExtendedGraph> graphs = parseDocument();
			return new SchemaProperties(graphs.get(graphs.size()-1).getSchema());
		} catch(Exception e) { throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); }
	}
	
	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> generateExtendedSchemaIDs() throws ImporterException
		{ return extendedSchemaIDs; }
	
	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> generateSchemaElements() throws ImporterException 
		{ return schemaElements; }

	/** Initialize the importer */
	protected void initializeSchemaStructures() throws ImporterException
	{	
		try {
			// Extract out schema graphs from the XML document
			ArrayList<ExtendedGraph> graphs = parseDocument();

			// Create ID translation table between file and repository
			HashMap<Integer,Integer> translationTable = new HashMap<Integer,Integer>();
			
			// Transfer all schemas from which this schema is extended
			ArrayList<Schema> availableSchemas = client.getSchemas();
			for(ExtendedGraph graph : graphs.subList(0, graphs.size()-1))
			{
				// Get the schema and schema ID
				Schema schema = graph.getSchema();
				Integer schemaID = schema.getId();

				// Find if schema already exists in repository
				Integer newSchemaID = getMatchedSchema(availableSchemas, graph);
				
				// Translate all IDs in the graph
				for(Integer key : translationTable.keySet())
					graph.updateElementID(key, translationTable.get(key));
				
				// Create extended schema if does not yet exist
				if(newSchemaID==null)
					newSchemaID = importParentSchema(graph);
				if(newSchemaID==null) throw new Exception("Failure to import schema " + graph.getSchema().getName());
				
				// Update references to the schema ID
				for(ExtendedGraph currGraph : graphs)
				{
					ArrayList<Integer> parentSchemaIDs = currGraph.getParentSchemaIDs();
					if(parentSchemaIDs.contains(schemaID))
						{ parentSchemaIDs.remove(schemaID); parentSchemaIDs.add(newSchemaID); }
				}
				
				// Collect and sort the elements from the two repositories
				ArrayList<SchemaElement> origElements = graph.getBaseElements(null);
				ArrayList<SchemaElement> newElements = client.getGraph(newSchemaID).getBaseElements(null);				
				Collections.sort(origElements,new SchemaElementComparator());
				Collections.sort(newElements,new SchemaElementComparator());

				// Add items to the translation table
				for(int i=0; i<origElements.size(); i++)
				{
					Integer origID = origElements.get(i).getId();
					Integer newID = newElements.get(i).getId();
					translationTable.put(origID, newID);
				}
			}
			
			// Get the schema being imported
			ExtendedGraph graph = graphs.get(graphs.size()-1);

			// Throw an exception if schema already exists in repository
			if(getMatchedSchema(availableSchemas, graph)!=null)
				throw new Exception("Schema already exists in repository");
			
			// Store the information for the schema being imported
			for(Integer key : translationTable.keySet())
				graph.updateElementID(key, translationTable.get(key));
			extendedSchemaIDs = graph.getParentSchemaIDs();
			schemaElements = graph.getBaseElements(null);
		}
		catch(Exception e) { throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); }
	}
	
	/** Parse out schema graphs from XML document */
	private ArrayList<ExtendedGraph> parseDocument() throws Exception
	{	
		// Create a map to temporarily hold schema elements until after the entire file is parsed
		HashMap<Integer,ArrayList<SchemaElement>> elementMap = new HashMap<Integer,ArrayList<SchemaElement>>();
		
		// Open up the XML document from reading
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new File(uri));
		Element element = document.getDocumentElement();
		
		// Gather up all schema information from the XML document in the form of extended graphs
		ArrayList<ExtendedGraph> graphs = new ArrayList<ExtendedGraph>();
		NodeList schemaList = element.getElementsByTagName("SchemaRoot");
		if(schemaList!=null)
			for(int i=0 ; i<schemaList.getLength(); i++)
			{	
				// Get the schema element
				Element schemaXMLElement = (Element)schemaList.item(i);
				
				// Extract schema and parent schema information from the element
				Schema schema = ConvertFromXML.getSchema(schemaXMLElement);
				ArrayList<Integer> parentIDs = ConvertFromXML.getParentSchemaIDs(schemaXMLElement);
				
				// Extract schema element information from the element
				ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();
				NodeList elementNodeList = schemaXMLElement.getChildNodes();
				if(elementNodeList != null)
					for(int j=0; j<elementNodeList.getLength(); j++)
					{
						Node node = elementNodeList.item(j);
						if(node instanceof Element)
						{
							SchemaElement schemaElement = ConvertFromXML.getSchemaElement((Element)node);
							if(schemaElement!=null) schemaElements.add(schemaElement);
						}
					}
						
				// Add the extended graph to the list of extended graphs
				elementMap.put(schema.getId(), schemaElements);
				graphs.add(new ExtendedGraph(schema, parentIDs, new ArrayList<SchemaElement>()));
			}

		// Add schema elements to the generated graphs
		for(ExtendedGraph graph : graphs)
		{
			Integer schemaID = graph.getSchema().getId();
			HashSet<SchemaElement> elements = new HashSet<SchemaElement>(elementMap.get(schemaID));
			for(Integer extendedSchemaID : getExtendedSchemas(schemaID,graphs))
				elements.addAll(elementMap.get(extendedSchemaID));
			boolean success = graph.addElements(new ArrayList<SchemaElement>(elements));
			if(!success) throw new Exception("Failure to generate a schema graph from the archive file");
		}
		
		// Sort graphs by schema ID and then return
		Collections.sort(graphs);
		return graphs;
	}
	
	/** Returns the list of extended schemas for the specified schema ID */
	private ArrayList<Integer> getExtendedSchemas(Integer schemaID, ArrayList<ExtendedGraph> graphs)
	{
		// Get the graph associated with the specified schema ID
		ExtendedGraph graph = null;
		for(ExtendedGraph currGraph : graphs)
			if(currGraph.getSchema().getId().equals(schemaID))
				{ graph=currGraph; break; }
		if(graph==null) return new ArrayList<Integer>();
		
		// Generate the list of extended schemas
		ArrayList<Integer> extendedSchemas = new ArrayList<Integer>(graph.getParentSchemaIDs());
		for(Integer parentSchemaID : graph.getParentSchemaIDs())
			extendedSchemas.addAll(getExtendedSchemas(parentSchemaID, graphs));
		return extendedSchemas;
	}
	
	/** Identifies the matching schema in the repository if one exists */
	private Integer getMatchedSchema(ArrayList<Schema> availableSchemas, Graph graph) throws Exception
	{
		// Search through available schemas to find if one matches
		for(Schema possSchema : availableSchemas)
			if(possSchema.getName().equals(graph.getSchema().getName()))
			{
				// Generate graphs for both schemas
				Graph possGraph = client.getGraph(possSchema.getId());
				
				// Check to see if number of elements matches
				ArrayList<SchemaElement> graphElements = graph.getElements(null);
				ArrayList<SchemaElement> possGraphElements = possGraph.getElements(null);
				if(graphElements.size()==possGraphElements.size())
				{
					// Sort the elements in each list
					Collections.sort(graphElements,new SchemaElementComparator());
					Collections.sort(possGraphElements,new SchemaElementComparator());

					// Validate that all of the elements are indeed the same
					boolean match = true;
					for(int i=0; i<graphElements.size(); i++)
					{
						SchemaElement s1 = graphElements.get(i);
						SchemaElement s2 = possGraphElements.get(i);
						if((s1.getClass().equals(s2.getClass()) && s1.getName().equals(s2.getName()))==false)
							{ match = false; break; }
					}
					if(match) return possSchema.getId();					
				}
		}

		// Indicates that no matched schema was found
		return null;
	}
	
	/** Import the graph to the repository */
	private Integer importParentSchema(ExtendedGraph graph)
	{
		boolean success = false;
		
		// Import the graph
		Integer schemaID = null;
		try {
			schemaID = client.importSchema(graph.getSchema(), graph.getBaseElements(null));
			success = client.setParentSchemas(schemaID, graph.getParentSchemaIDs());
			if(success) client.lockSchema(schemaID);
		} catch(Exception e) {}

		// Delete the imported graph if failure occurred
		if(!success && schemaID!=null)
		{
			try { client.deleteSchema(schemaID); } catch(Exception e) {};
			schemaID=null;
		}
	
		// Return the created schema ID
		return schemaID;
	}
}