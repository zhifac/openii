package org.mitre.schemastore.warehouse.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.mitre.schemastore.warehouse.common.InstanceRepository;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;

public class RepositoryXMLParser 
{
	private File xmlFile = null;
	private InputStream xmlStream =null;
	private Document doc = null;
	private ArrayList<InstanceRepository> listOfAvailableRepositories = new ArrayList<InstanceRepository>();
	
	/**	Constructs the object with a File object */
	public RepositoryXMLParser(File xmlFile)
	{
		this.xmlFile = xmlFile;
	}
	
	/**	Constructs the object with a InputStream object	*/
	public RepositoryXMLParser(InputStream xmlStream)
	{
		this.xmlStream = xmlStream;
	}
	
	public ArrayList<InstanceRepository> getAvailableRepositories() throws NoDataFoundException
	{
		// Parse the content of the XML file/stream associated with this parser
		if(!(xmlFile == null))
			parseXmlFile();
		
		if(!(xmlStream == null))
			parseXmlStream();
		
		// Generate the list of all available repositories
		parseDocument();
		
		return listOfAvailableRepositories;
	}
	
	/** Parse the content of the given XML file and create a new DOM Document object */
	private void parseXmlFile()
	{
		// Get the factory
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try
		{
			// Using factory get an instance of document builder
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		
			// Parse using builder to get DOM representation of the XML file
			doc = docBuilder.parse(xmlFile);
		}
		catch(ParserConfigurationException pce)
		{
			System.out.println ("***DocumentBuilder cannot be created that satisfies the configuration requested***");
			pce.printStackTrace();
		} 
		catch (SAXException se) 
		{
			System.out.println ("***Parse error occured***");
			se.printStackTrace();
		} 
		catch (IOException ioe) 
		{
			System.out.println ("***I/O error occured***");
			ioe.printStackTrace();
		}
	}//end of method parseXmlFile
	
	/** Parse the content of the given XML stream and create a new DOM Document object */
	private void parseXmlStream()
	{
		// Get the factory
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try
		{
			// Using factory get an instance of document builder
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		
			// Parse using builder to get DOM representation of the XML file
			doc = docBuilder.parse(xmlStream);
		}
		catch(ParserConfigurationException pce)
		{
			System.out.println ("***DocumentBuilder cannot be created that satisfies the configuration requested***");
			pce.printStackTrace();
		} 
		catch (SAXException se) 
		{
			System.out.println ("***Parse error occured***");
			se.printStackTrace();
		} 
		catch (IOException ioe) 
		{
			System.out.println ("***I/O error occured***");
			ioe.printStackTrace();
		}
	}//end of method parseXmlStream
	
	/** 
	 * Read through the document and parse it to get the list of all available repositories 
	 * @throws NoDataFoundException 
	 * */
	private void parseDocument() throws NoDataFoundException
	{
		//get the root element
		Element rootElement = doc.getDocumentElement();
		System.out.println ("Root element of the xml document = " + rootElement.getNodeName());
		
		// Get the node list of repositories - child elements of the root element
		NodeList nlRepositories = rootElement.getElementsByTagName("repository");

		if(nlRepositories != null && nlRepositories.getLength() > 0)
		{
			for(int i=0; i<nlRepositories.getLength(); i++)
			{
				// Get the repository element
				Node repositoryNode = nlRepositories.item(i);
				if(repositoryNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element repositoryElement = (Element)repositoryNode;
					
					// Get the Repository object
					InstanceRepository repository = getRepository(repositoryElement);
					
					// Add it to the list
					listOfAvailableRepositories.add(repository);
				}
			}
		}
		else
		{
			System.out.println("Repository data not found in the xml file");
			throw new NoDataFoundException("Repository data not found");
		}
	}//end of method parseDocument
	
	/** Take a repository element and read the values in, 
	 *  create a Repository object and return it
	 */
	private InstanceRepository getRepository(Element repositoryElement) throws NoDataFoundException 
	{
		Integer type = null;
		String host = null;
		String databaseName = null;
		String databaseUser = null;
		String databasePassword = null;
		
		/* For each <repository> element, */
		
		// Get the "type" attribute
		String typeAttribute = repositoryElement.getAttribute("type");
		try
		{	type = Integer.valueOf(typeAttribute);	}
		catch(NumberFormatException nfe)
		{	System.out.println("***Repository type cannot be parsed as an integer***");
			nfe.printStackTrace();
			throw new NoDataFoundException("Valid Repository \"type\" not found - should be an integer");
		}
		
		// Get text or int values of
		// <host>, <databaseName>, <databaseUser>, <databasePassword>
		host = getTextValue(repositoryElement, "host");
		databaseName = getTextValue(repositoryElement, "databaseName");
		databaseUser = getTextValue(repositoryElement, "databaseUser");
		databasePassword = getTextValue(repositoryElement, "databasePassword");
		
		//Create a new InstanceRepository with the value read from the xml nodes
		InstanceRepository repository = new InstanceRepository(type, host, databaseName, databaseUser, databasePassword);

		return repository;
	}//end of method getRepository
	
	/**
	 * Called when text value is expected
	 * Take a xml element and the tag name, look for the tag and get the text content
	 * i.e for <repository><uri>XXXX</uri></repository> xml snippet if
	 * the Element points to this particular repository node and tagName is 'uri',
	 * then 'XXXX' is returned.
	 */
	private String getTextValue(Element element, String tagName) 
	{
		String textVal = null;
		NodeList nl = element.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) 
		{
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}
		return textVal;
	}//end of method getTextValue
	
	/**
	 * Called when int value is expected
	 * @throws NoDataFoundException 
	 */
	private Integer getIntValue(Element element, String tagName) throws NoDataFoundException 
	{
		Integer intVal = null;
		String intValAsString = getTextValue(element,tagName);
		
		try
		{	intVal = Integer.valueOf(intValAsString);	}
		catch(NumberFormatException nfe)
		{	System.out.println("***Value " + intValAsString + "cannot be parsed as an integer***");
			nfe.printStackTrace();
			throw new NoDataFoundException("Valid data not found for the repository");
		}
		return intVal;
	}//end of method getIntValue
	
	
	/** Iterate and print the data 
	 * @throws NoDataFoundException */
	private void printData() throws NoDataFoundException
	{
		int totalRepositories = listOfAvailableRepositories.size();
		if(totalRepositories == 0)
		{
			// Parse the content of the XML file/stream associated with this parser
			if(!(xmlFile == null))
				parseXmlFile();
			
			if(!(xmlStream == null))
				parseXmlStream();
			
			// Generate the list of all available repositories
			parseDocument();
			
			// Get the new count
			totalRepositories = listOfAvailableRepositories.size();
		}

		System.out.println("Total no of repositories = " + totalRepositories);
			
		for(int s=0; s<totalRepositories ; s++)
		{
			InstanceRepository r = listOfAvailableRepositories.get(s);
			System.out.println("\nPrinting details of Repository " + (s+1));
			System.out.println("------------------------------------");
			System.out.println("Type = " + r.getType());
			System.out.println("Host = " + r.getHost());
			System.out.println("DatabaseName = " + r.getDatabaseName());
			System.out.println("DatabaseUser = " + r.getUsername());
			System.out.println("DatabasePassword = " + r.getPassword());
		}
	}//end of method printData
	
	
	public static void main(String[] args)
	{
		//RepositoryXMLParser rp = new RepositoryXMLParser(new File("\\repository.xml"));
		InputStream configStream = null;
		try 
		{
			configStream = new FileInputStream(new File("\\repository.xml"));
		}
		catch (FileNotFoundException e1) 
		{	e1.printStackTrace();	}
		RepositoryXMLParser rp = new RepositoryXMLParser(configStream);
		
		try
		{	rp.getAvailableRepositories();	}
		catch(NoDataFoundException e)
		{	e.printStackTrace();	}
		
		try
		{	rp.printData();	}
		catch(NoDataFoundException e)
		{	e.printStackTrace();	}

	}
}//end of class
