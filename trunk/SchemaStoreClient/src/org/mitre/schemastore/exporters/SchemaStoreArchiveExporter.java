// Copyright (C) The MITRE Corporation 2008
// ALL RIGHTS RESERVED

package org.mitre.schemastore.exporters;

import java.io.*;
import java.rmi.RemoteException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.graph.Graph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xml.serialize.OutputFormat;


/**
 * Class for moving SchemaStore format between SchemaStore Instances
 * Given schemaID, exporter finds parent types, and other schema dependencies and exports these too.
 *  
 * @author MDMORSE
 */
public class SchemaStoreArchiveExporter extends Exporter
{	
	
	/**
	 * SchemaStoreArchiveExporter(): Constructor. Sets the common domains.
	 *
	 */
	public SchemaStoreArchiveExporter(){	
		//init outputFileName
		outputFileName = "tempSchemaStoreArchive";
		//init StringBuffer;
		xmlStringBuffer = new StringBuffer();
	}

	/** Returns the exporter name */
	public String getName()
		{ return "SchemaStoreArchiveExporter"; }
	
	/** Returns the exporter description */
	public String getDescription()
		{ return "This method exports a schema in SchemaStore Format"; }
	
	/** Returns the exporter file type */
	public String getFileType()
		{ return ".ssa"; } //schemastoreArchive.
	
	/** If writing to a file */
	public String outputFileName; 
	
	/** String Buffer to hold XML Output */
	public StringBuffer xmlStringBuffer;
	
	public void setOutputFileName(String sName){
		outputFileName = sName;
	}
	
	/** override Exporter.exportSchema */
	public StringBuffer exportSchema(Integer schemaID, ArrayList<SchemaElement> schemaElements){
		exportTopLevel(schemaID, client);
		return xmlStringBuffer;
	}

	//top level entry into exporter that looks for all ancestors of schemaID and
	//adds them to the export.
	public void exportTopLevel(Integer schemaID, SchemaStoreClient ssc){
		ArrayList<Integer> schemaIDs = null; 
		try{
			schemaIDs = ssc.getAncestorSchemas(schemaID);
		} catch(Exception e){
			System.out.println(e); e.printStackTrace();
		}
		schemaIDs.add(schemaID);
		//should take care of everything but aliases.
		try{
			Document dom = null;
			
			//get an instance of factory
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {
				//get an instance of builder
				DocumentBuilder db = dbf.newDocumentBuilder();
	
				//create an instance of DOM
				dom = db.newDocument();

			}catch(ParserConfigurationException pce) {
				//dump it
				System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
				pce.printStackTrace();
			}

			//generate the root element
			Element RootE = getNewChildElement("SchemaStoreArchiveExport", "Schemas:", dom);
			//add each schema under the root.
			for(Integer schemaid: schemaIDs){
				try{
					//get the elements of the schema.
					ArrayList<SchemaElement> returnedList = ssc.getGraph(schemaid).getBaseElements(null);
					Schema mySchema = ssc.getGraph(schemaid).getSchema();
					//set the parent schemas
					mySchema.setParentSchemaIDs(ssc.getParentSchemas(schemaid));
					//convert to the xml format
					Element SchemaRootE = mySchema.toXML(null, dom);
					//add the child elements
					for(SchemaElement schElm: returnedList){
						Element Emt = schElm.toXML(null, dom);
						SchemaRootE.appendChild(Emt);
					}
					RootE.appendChild(SchemaRootE);
				} catch(Exception e){
					System.out.println(e); e.printStackTrace();
				}
			}
			printToBuffer(RootE,dom);
			
		} catch(Exception e){
			System.out.println(e); e.printStackTrace();
		}
	}
	
	/**
	 * This method uses writes the xml to a string buffer.
	 * It uses Xerces specific classes.
     */
	private void printToBuffer(Element RootE, Document dom){
		try
		{
			//print
			OutputFormat format = new OutputFormat(dom);
			format.setIndenting(true);

			StringWriter sw = new StringWriter();
			//to generate a file output use fileoutputstream instead of system.out
			XMLSerializer serializer = new XMLSerializer(sw, format);
			dom.appendChild(RootE);

			serializer.serialize(dom);
			xmlStringBuffer = sw.getBuffer();

		} catch(IOException ie) {
		    ie.printStackTrace();
		}
	}
	
	/**
	 * This method prints the XML to a file
     */
	public void printToFile(String fileName){
		try{
			DataOutputStream oos = new DataOutputStream(new FileOutputStream(new File(fileName)));
			oos.writeBytes(xmlStringBuffer.toString());
		} catch(Exception e){
			System.out.println(e); e.printStackTrace();
		}
	}

	public static void main(String[] args) throws RemoteException, IOException {
		Integer schemaId = 195179; // Human Patient
		SchemaStoreClient ss = new SchemaStoreClient(
				"http://brainsrv2:8090/SchemaStore/services/SchemaStore");
				//"C:\\mike\\CVS_ROOT\\COMMONGROUND\\SchemaStore\\SchemaStore.jar");

		if (args != null && args.length > 0)
			schemaId = Integer.parseInt(args[0]);

		if (schemaId == null) {
			System.err.println("Enter a valid schema id from the list below : ");
			// fetch and print all schema with id
			ArrayList<Schema> schemas;
			schemas = ss.getSchemas();
			for (Schema s : schemas) {
				System.out.println(s.getId() + " " + s.getName());
			}
		} else {
			SchemaStoreArchiveExporter exporter = new SchemaStoreArchiveExporter();
			exporter.setClient(ss);
			try {
				exporter.exportSchema(new Integer(schemaId), new ArrayList<SchemaElement>());
				exporter.printToFile("C:\\tempfile2"+exporter.getFileType());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * returns a new Child Element
	 * @param ElementName - what goes between the <>'s in XML
	 * @param textDescription - what goes between the <ElementName></ElementName>.
	 * @return Element
	 */
	protected Element getNewChildElement(String ElementName, String textDescription, Document dom){
		//create description node under that
		Element dE = dom.createElement(ElementName);
		Text dT = dom.createTextNode(textDescription);
		dE.appendChild(dT);
		return dE;
	}
	
} // end class