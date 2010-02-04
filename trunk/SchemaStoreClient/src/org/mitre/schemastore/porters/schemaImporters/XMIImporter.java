package org.mitre.schemastore.porters.schemaImporters;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

import org.exolab.castor.xml.schema.Annotated;
import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.porters.ImporterException;

/**
 * Importer for XMI models exported from the EA tool.
 * @author DMALLEN
 *
 * THIS IS AN INITIAL COMMIT ONLY.  THIS CODE DOES NOT YET WORK -- STILL NEED TO TALK TO OPENII FOLKS TO FIX
 * A NUMBER OF PROBLEMS. 
 * 
 * Please keep this in CVS for the moment so that John Castleberry and I can collaborate on getting this right.
 */
public class XMIImporter extends SchemaImporter {
	public HashMap<String,SchemaElement> elements = new HashMap<String,SchemaElement>();
	public HashMap<String,String> mappings = new HashMap<String,String>();
	public HashMap<String,Domain> domains = new HashMap<String,Domain>();
	Entity root = null;
	
	public void processDomain(Node n) throws Exception { 
		String name = n.getNodeName();
		String ref = n.getAttributes().getNamedItem("base_Class").getNodeValue(); 
		SchemaElement referent = elements.get(ref); 
		String domainName = null;
		
		if(referent == null) { 
			System.err.println("No referent for domain " + name + " with base_Class " + ref); 
			return;
		}
		
		if(name.contains("thecustomprofile"))  
			domainName = name.substring(name.indexOf(":") + 1);
		else if(name.contains("EAUML:table"))  
			domainName = "table";
		else return;
		
		Domain domain = domains.get(domainName);
		
		if(domain == null) { 
			domain = new Domain(SchemaImporter.nextId(), domainName, "UML Stereotype imported from EA", 0);
			domains.put(domainName, domain);
			elements.put(ref + "-domain", domain);
		}
		
		// TODO I don't know how to create the linkage from an Entity to the Domain that represents its
		// data type.  In XMI/XML, this is the "UML stereotype".
		
		// Here are a couple of approaches that totally fail.
		//Containment c = new Containment(nextId(), "type:" + domainName, domainName, referent.getId(), domain.getId(), 0, 1, 0);
		//Attribute attribute = new Attribute(nextId(), domainName, 
		//		"type:" + domainName, referent.getId(), domain.getId(), null,null,false,0);
		Subtype subtype = new Subtype(nextId(), referent.getId(), domain.getId(), 0);
		
		//elements.put(""+attribute.getId(), attribute); 
		elements.put(""+subtype.getId(), subtype); 
	} // End processDomain
	
	public void processExtensionElement(Node element) throws Exception { 
		NodeList children = element.getChildNodes();
		String xmiID = element.getAttributes().getNamedItem("xmi:idref").getNodeValue();
		SchemaElement se = elements.get(xmiID);
		// System.out.println("Extension element " + xmiID); 
		if(se == null) { 
			System.err.println("No schema element for XMI ID " + xmiID); 
			return;
		}
		
		for(int x=0; x<children.getLength(); x++) { 
			Node n = children.item(x);
			//System.out.println("ExtElement child " + n.getNodeName()); 
			if(n.getNodeName().equals("properties")) { 
				try {
					String docs = n.getAttributes().getNamedItem("documentation").getNodeValue();
					// System.out.println("DOCUMENTATION: " + docs); 
					se.setDescription(docs); 
				} catch(NullPointerException e) { ; } 
			} else if(n.getNodeName().equals("links")) { 
				NodeList aggs = n.getChildNodes();
				for(int y=0; y<aggs.getLength(); y++) { 
					Node agg = aggs.item(y); 
					if(!"Aggregation".equals(agg.getNodeName())) continue;
					String parentID = agg.getAttributes().getNamedItem("end").getNodeValue();
					String childID = agg.getAttributes().getNamedItem("start").getNodeValue(); 
										
					SchemaElement parent = elements.get(parentID);										
					SchemaElement child = elements.get(childID);
					
					//System.out.println("LINK: " + 
					//		           parent.getName() + " => " + 
					//                   " " + child.getName()); 
					
					if(parent == null || child == null) { 
						System.err.println("MISSING SCHEMA ELEMENTS: parent=" + parent + " child=" + child); 
						//throw new Exception();
						return;
					} // End if
					
					// TODO I don't know how to create the linkage from a parent entity to a child entity.
					// In XMI/XML for UML, this is the association links.					
					// BTW, this method totally fails.
					
					Subtype subtype = new Subtype(nextId(), parent.getId(), child.getId(), 0);
					elements.put(""+subtype.getId(), subtype);
				} // End for
			} // end else if
		} // End for
	} // End processExtensionElement
	
	public SchemaElement processClass(Node pe) {  
		SchemaElement result = null;
		//System.out.println("Processing class: " + pe.getAttributes().getNamedItem("name"));
		
		String xmiID = pe.getAttributes().getNamedItem("xmi:id").getNodeValue(); 		
		String name = pe.getAttributes().getNamedItem("name").getNodeValue();
		String documentation = "";
		Integer base = 0; 
			
		NodeList nl = pe.getChildNodes();
		
		if(nl.getLength() == 0) { 
			System.err.println("0 Children for node " + name + " xmiID=" + xmiID + " skipping."); 
			return null;
		}
		
		Entity entity = new Entity(SchemaImporter.nextId(), name, documentation, base);
		//Containment entity = new Containment(nextId(), name, documentation, null, null, 0, 1, 0);
		elements.put(xmiID, entity); 
		
		for(int x=0; x<nl.getLength(); x++) { 
			Node n = nl.item(x); 
			String locname = n.getNodeName();
			if(!"ownedAttribute".equals(locname)) continue; 
			String oaid = n.getAttributes().getNamedItem("xmi:id").getNodeValue();
			// System.out.println("ADDING MAPPING: " + oaid + " => " + xmiID); 
			mappings.put(oaid, xmiID); 
		} // End for
		
		return entity;
	} // End processClass
	
	public SchemaElement processAssociation(Node pe) { 
		SchemaElement result = null;
		// System.out.println("Processing association: " + pe.getAttributes().getNamedItem("xmi:id"));
		
		ArrayList <String> memberEndIDs = new ArrayList <String>();
		
		NodeList nl = pe.getChildNodes();
		for(int x=0; x<nl.getLength(); x++) { 
			Node n = nl.item(x); 
			String name = n.getNodeName();
			if(name.equals("memberEnd")) { 
				String xmiID = n.getAttributes().getNamedItem("xmi:idref").getNodeValue();
				memberEndIDs.add(xmiID); 
			} else if(name.equals("ownedEnd")) { 
				
			} 
		} // End for
		
		// TODO CREATE relationship
		
		return result; 
	} // End processAssociation
	
	public void processPackagedElement(Node pe, SchemaElement parent, String saughtType) throws Exception { 
		SchemaElement here = null;				
		String nodeType = pe.getAttributes().getNamedItem("xmi:type").getNodeValue();				
		if(!nodeType.equals("uml:Package") && !nodeType.equals(saughtType)) return;		

		//System.out.println("Process packaged element: " + pe.getAttributes().getNamedItem("xmi:type") + 
		//		"/" + pe.getAttributes().getNamedItem("name"));
				
		if(nodeType.equals("uml:Class")) here = processClass(pe);
		else if(nodeType.equals("uml:Association")) here = processAssociation(pe); 
		
		NodeList nl = pe.getChildNodes();
		for(int x=0; x<nl.getLength(); x++) { 
			Node child = nl.item(x); 
			if(child.getNodeName().equals("packagedElement")) {
				processPackagedElement(child, here, saughtType); 
			}
		}
	}
	
	public static void main(String [] args) throws Exception {
		Repository repository = new Repository(Repository.DERBY,new URI("file:///c:/schemastore/"),"schemastore","postgres","postgres");
		
		SchemaStoreClient client = new SchemaStoreClient(repository);
		
		XMIImporter xmi = new XMIImporter();
		xmi.setClient(client); 
		
		URI uri = new URI("file:///c:/test.xml"); 
		
		//System.out.println("Importing.");
		Integer schemaID = xmi.importSchema("Schema " + new Date(), "Author", "Description", uri);
		System.out.println("Finished importing."); 		
	} // End main
	
	public Integer getURIType() {
		return FILE;
	}

	public String getDescription() {
		return "This importer can be used to import schemas from an XMI format";
	}
	
	public String getName() {
		return "XMI Importer";
	}
	
	public ArrayList<String> getFileTypes()
	{
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".xmi");
		fileTypes.add(".xml"); 
		return fileTypes;
	}
	
	protected void initialize() throws ImporterException {
		elements = new HashMap<String,SchemaElement> (); 
		
		root = new Entity(SchemaImporter.nextId(), "ROOT", "ROOT ELEMENT", 0); 
		elements.put(root.getId()+"", root); 
		
		try { 
	        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	        Document doc = docBuilder.parse (new File(uri));
	
	        Node root = doc.getChildNodes().item(0); 
	        
	        String modelLabel = "uml:Model";
	        String extLabel = "xmi:Extension";
	        
	        Node model = null;
	        Node ext = null;
	        
	        NodeList nl = root.getChildNodes();
	        
	        for(int x=0; x<nl.getLength(); x++) { 
	        	Node n = nl.item(x);
	        	//System.out.println("Examining child of root: " + n.getNodeName()); 
	        	if(n.getNodeName().equals(modelLabel)) model = n;
	        	if(n.getNodeName().equals(extLabel)) ext = n;        	
	        }
	        
	        NodeList pes = model.getChildNodes();
	        for(int x=0; x<pes.getLength(); x++) { 
	        	Node n = pes.item(x);
	        	if(n.getNodeName().equals("packagedElement")) { 
	        		processPackagedElement(n, null, "uml:Class"); 
	        	} else if(n.getNodeName().contains("thecustomprofile") || n.getNodeName().equals("EAUML:table")) { 
	        		processDomain(n); 
	        	}
	        }
	
	        for(int x=0; x<pes.getLength(); x++) { 
	        	Node n = pes.item(x);
	        	if(n.getNodeName().equals("packagedElement")) { 
	        		processPackagedElement(n, null, "uml:Association"); 
	        	} 
	        }
	 
	        NodeList extE = ext.getChildNodes();
	        for(int x=0; x<extE.getLength(); x++) { 
	        	Node elements = extE.item(x);
	        	if(elements.getNodeName().equals("elements")) {
	        		NodeList sub = elements.getChildNodes();
	        		for(int y=0; y<sub.getLength(); y++) { 
	        			Node s = sub.item(y); 
	        			if(s.getNodeName().equals("element")) { 
	        				processExtensionElement(s);
	        			}
	        		} // End for
	        	} // End if
	        } // End for
		} catch(Exception e) { 
			e.printStackTrace();
			throw new ImporterException(ImporterException.PARSE_FAILURE, e.getMessage()); 
		} // End catch
	} // End initialize
	
	protected ArrayList<SchemaElement> generateSchemaElements() throws ImporterException {
		System.out.println("Generating schema elements for XMI..."); 
		return new ArrayList<SchemaElement>(elements.values());
	} // End generateSchemaElements
} // End XMIIMporter
