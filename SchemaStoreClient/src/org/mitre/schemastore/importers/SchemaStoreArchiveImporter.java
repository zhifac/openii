// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.importers;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.graph.Graph;
import org.mitre.schemastore.model.xml.ConvertFromXML;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/** Importer for copying schemas from other repositories */
public class SchemaStoreArchiveImporter extends Importer
{
	/** Private class for sorting schema elements */
	class SchemaComparator implements Comparator<SchemaElement>
	{
		public int compare(SchemaElement element1, SchemaElement element2)
			{ return element1.getId().compareTo(element2.getId()); }
	}

	/** Private class for sorting schema elements */
	class SchemaObjectComparator implements Comparator<Schema>
	{
		public int compare(Schema element1, Schema element2)
			{ return element1.getId().compareTo(element2.getId()); }
	}

	/** Private class for sorting schema elements */
	class GraphComparator implements Comparator<Graph>
	{
		public int compare(Graph element1, Graph element2)
			{ return element1.getSchema().getId().compareTo(element2.getSchema().getId()); }
	}

	/** Private class for defining an extended graph */
	static class ExtendedGraph extends Graph
	{
		/** Stores the parentIDs associated with the extended graph */
		private ArrayList<Integer> parentIDs;
		
		/** Constructs the extended graph */
		ExtendedGraph(Schema schema, ArrayList<Integer> parentIDs, ArrayList<SchemaElement> elements)
		{
			super(schema,elements);
			this.parentIDs = parentIDs;
		}
	}
	
	/** Stores the list of schemas */
	private ArrayList<Integer> extendedSchemaIDs = new ArrayList<Integer>();

	/** Stores the list of schema elements */
	private ArrayList<SchemaElement> schemaElements = null;

	/** Returns the importer name */
	public String getName()
		{ return "Schema Store Archive Importer"; }

	/** Returns the importer description */
	public String getDescription()
		{ return "This class allows the copying of a schema from a schema store archive (file) to a schem store repository"; }

	/** Returns the importer URI type */
	public Integer getURIType()
		{ return FILE; }

	/** Returns the ID of parent schemas */
	public ArrayList<Integer> getParents()
		{ return new ArrayList<Integer>(); }

	/** Identifies the matching schema in the repository if one exists */
	private Integer getMatchedSchema(ArrayList<Schema> availableSchemas, Graph graph) throws Exception
	{
		Schema schema = graph.getSchema();
		// Search through available schemas to find if one matches
		for(Schema possSchema : availableSchemas)
			if(possSchema.getName().equals(schema.getName()))
			{
				// Generate graphs for both schemas
				Graph possGraph = client.getGraph(possSchema.getId());

				// Check to see if number of elements matches
				ArrayList<SchemaElement> graphElements = graph.getElements(null);
				ArrayList<SchemaElement> possGraphElements = possGraph.getElements(null);
				if(graphElements.size()==possGraphElements.size())
				{
					// Sort the elements in each list
					Collections.sort(graphElements,new SchemaComparator());
					Collections.sort(possGraphElements,new SchemaComparator());

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

	/** Initialize the importer if needed */
	protected void initialize() throws ImporterException
	{
		try {
			//suck in the schema from the ssa file.
			//get the factory
			Document dom = null;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			String fileName = "C:\\tempfile.ssa";

			try {

				//Using factory get an instance of document builder
				DocumentBuilder db = dbf.newDocumentBuilder();

				//parse using builder to get DOM representation of the XML file
				dom = db.parse(fileName);

			}catch(Exception e){
				System.out.println(e);
				e.printStackTrace();
			}

			//get the schemas contained in the ssa
			ArrayList<ExtendedGraph> mySchemaGraphs = parseDocument(dom);
			Collections.sort(mySchemaGraphs, new GraphComparator());

			// Transfer all schemas from which this schema is extended
			ArrayList<Schema> availableSchemas = client.getSchemas();
			int i=0;
			ArrayList<SchemaElement> sElementsV = null;
			Integer oldSchemaID = null;
			for(ExtendedGraph repositoryGraph : mySchemaGraphs)
			{
				Schema repositorySchema = repositoryGraph.getSchema();
				oldSchemaID = repositorySchema.getId();
				// Get the extended schema ID
				Integer schemaID = getMatchedSchema(availableSchemas, repositoryGraph);
				if(schemaID==null)
				{
					//this means the schema isn't in the repository.  Hence, add it.
					schemaID = addNewSchema(repositoryGraph);
				}
				//update the remaining graphs to reflect the new id.
				updateGraphs(schemaID, oldSchemaID, mySchemaGraphs, repositoryGraph, ++i);
				sElementsV = repositoryGraph.getElements(null);
				extendedSchemaIDs = repositoryGraph.parentIDs;
			}

			// Retrieve the base elements to be displayed
			schemaElements = new ArrayList<SchemaElement>();
			for(SchemaElement element : sElementsV)
				if(element.getId()<0 || element.getBase().equals(oldSchemaID))
					schemaElements.add(element);
			Collections.sort(schemaElements,new SchemaComparator());
		}
		catch(Exception e)
			{ System.out.println(e); e.printStackTrace(); }

	}

	/**
	 * internal procedure to update the list of graphs with the new schema id (for a particular
	 * schema) from the old
	 * @param nSchemaID - new schema id
	 * @param oldSchemaID - old schema id
	 * @param myGraphs - the other graphs that need to be updated
	 * @param repositoryGraph - the new graph
	 * @param num
	 */
	private void updateGraphs(Integer nSchemaID, Integer oldSchemaID, ArrayList<ExtendedGraph> myGraphs, Graph repositoryGraph, int num){
		try{
			ExtendedGraph myGraph=null;
			ArrayList<SchemaElement> oldElements = repositoryGraph.getElements(null);
			Collections.sort(oldElements,new SchemaComparator());
			ArrayList<SchemaElement> newElements = client.getGraph(nSchemaID).getElements(null);
			Collections.sort(oldElements,new SchemaComparator());
			//iterate through the graphs and update each with the new id.
			for(int j=num; myGraphs.size() > j; j++)
			{
				myGraph = myGraphs.get(j);

				//check if this schema is a child of changed schema
				ArrayList<Integer> myParents = myGraph.parentIDs;
				if(myParents.contains(oldSchemaID)){
					int place = myParents.indexOf(oldSchemaID);
					myParents.set(place, nSchemaID);
				}

				//Update the graph to reflect altered element IDs
				for(int i=0; i<oldElements.size(); i++)
				{
					Integer origID = oldElements.get(i).getId();
					Integer newID = newElements.get(i).getId();
					if(newID>=0) myGraph.updateElementID(newID,origID);
				}
			}
		}catch(Exception e){
			System.out.println(e); e.printStackTrace();
		}
	}

	/**
	 * A method to add a schema to the repository, if it doesn't exist there.
	 * @param nGraph - the graph to add.
	 * @return the id of the graph from the repository
	 */
	private Integer addNewSchema(ExtendedGraph nGraph){
		//this means the schema isn't in the repository.  Hence, add it.
		Schema schema = nGraph.getSchema();
		boolean success = false;
		Integer schemaID = -1;
		try {
			// Import the schema
			ArrayList<SchemaElement> myElements = nGraph.getElements(null);
			schemaID = client.importSchema(schema, myElements);
			schema.setId(schemaID);
			success = client.setParentSchemas(schema.getId(), nGraph.parentIDs);
		}
		catch(Exception e) {
			System.out.println(e); e.printStackTrace();
		}

		// Delete the schema if import wasn't totally successful
		if(!success)
		{
			try { client.deleteSchema(schema.getId()); } catch(Exception e) {
				System.out.println(e); e.printStackTrace();
			}
		}
		return schemaID;
	}

	/**
	 * Parse the .xml document for the schema format.  Construct the new Graph
	 * @param dom - the xml document.
	 * @return a list of the graphs.
	 */
	private static ArrayList<ExtendedGraph> parseDocument(Document dom){
		//get the root elememt
		Element docEle = dom.getDocumentElement();

		//we're going to build out the tree here.  get all of the schemas
		ArrayList<ExtendedGraph> mySchemas = new ArrayList<ExtendedGraph>();

		//get a nodelist of <SchemaRoot> elements
		NodeList nl = docEle.getElementsByTagName("SchemaRoot");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {

				//get the schema element
				Element schemaElement = (Element)nl.item(i);

				//get the Schema object
				Schema s1 = ConvertFromXML.getSchema(schemaElement);
				ArrayList<Integer> parentIDs = ConvertFromXML.getParentSchemaIDs(schemaElement);
				
				//the schema elements themselves.
				ArrayList<SchemaElement> tSchemaElements = new ArrayList<SchemaElement>();
				tSchemaElements.addAll(getSchemaElementsByName("AliasElement",schemaElement,new Alias()));
				tSchemaElements.addAll(getSchemaElementsByName("AttributeElement",schemaElement,new Attribute()));
				tSchemaElements.addAll(getSchemaElementsByName("ContainmentElement",schemaElement,new Containment()));
				tSchemaElements.addAll(getSchemaElementsByName("DomainElement",schemaElement,new Domain()));
				tSchemaElements.addAll(getSchemaElementsByName("DomainValueElement",schemaElement,new DomainValue()));
				tSchemaElements.addAll(getSchemaElementsByName("EntityElement",schemaElement,new Entity()));
				tSchemaElements.addAll(getSchemaElementsByName("RelationshipElement",schemaElement,new Relationship()));
				tSchemaElements.addAll(getSchemaElementsByName("SchemaElement",schemaElement,new SchemaElement()));
				tSchemaElements.addAll(getSchemaElementsByName("SubTypeElement",schemaElement,new Subtype()));

				ExtendedGraph myGraph = new ExtendedGraph(s1, parentIDs, tSchemaElements);
				//add it to list
				mySchemas.add(myGraph);
			}
		}
		return mySchemas;
	}

	private static ArrayList<SchemaElement> getSchemaElementsByName(String nameType, Element docElement, SchemaElement sE){
		//get a nodelist of <nameType> elements
		ArrayList<SchemaElement> mySchemaElements = new ArrayList<SchemaElement>();
		NodeList nl = docElement.getElementsByTagName(nameType);
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {

				//get the schema element
				Element newSchemaElement = (Element)nl.item(i);
				SchemaElement sNew = ConvertFromXML.getSchemaElement(newSchemaElement);
				mySchemaElements.add(sNew);
			}
		}
		return mySchemaElements;
	}

	public static void main(String[] args) throws RemoteException, IOException {
		SchemaStoreClient ss = new SchemaStoreClient(
				"C:\\mike\\CVS_ROOT\\COMMONGROUND\\SchemaStore\\SchemaStore.jar");

			SchemaStoreArchiveImporter importer = new SchemaStoreArchiveImporter();
			importer.setClient(ss);
			try {
				importer.initialize();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> getExtendedSchemaIDs() throws ImporterException
		{ return extendedSchemaIDs; }

	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> getSchemaElements() throws ImporterException
		{ return schemaElements; }
}