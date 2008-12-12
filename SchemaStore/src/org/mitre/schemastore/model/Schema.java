// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Class storing a single schema from the repository
 * @author CWOLF
 */
public class Schema implements Serializable
{	
	/** Stores the schema id */
	private Integer id;
	
	/** Stores the schema name */
	private String name;
	
	/** Stores the schema author */
	private String author;
	
	/** Stores the schema source */
	private String source;

	/** Stores the schema type */
	private String type;
	
	/** Stores the schema description */
	private String description;	
	
	/** Indicates if the schema is locked */
	private boolean locked;
	
	/** Stores the schemaIDs that this schema extends. */
	protected ArrayList<Integer> parentSchemaIDs;
	
	/** Constructs a default schema */
	public Schema() {}
	
	/** Constructs a schema */
	public Schema(Integer id, String name, String author, String source, String type, String description, boolean locked)
		{ this.id = id; this.name = name; this.author = author;  this.source = source; this.type = type; this.description = description; this.locked = locked; parentSchemaIDs=null;}
	
	// Handles all schema getters
	public Integer getId() { return id; }
	public String getName() { return name; }
	public String getAuthor() { return author; }
	public String getSource() { return source; }
	public String getType() { return type; }
	public String getDescription() { return description; }
	public boolean getLocked() { return locked; }

	// Handles all schema setters
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setAuthor(String author) { this.author = author; }
	public void setSource(String source) { this.source = source; }
	public void setType(String type) { this.type = type; }
	public void setDescription(String description) { this.description = description; }
	public void setLocked(boolean locked) { this.locked = locked; }
	
	public void setParentSchemaIDs(ArrayList<Integer> pSchemaIDs){
		parentSchemaIDs = pSchemaIDs;
	}
	
	public ArrayList<Integer> getParentSchemaIDs(){
		return parentSchemaIDs;
	}
	
	/** Indicates that two schemas are equals */
	public boolean equals(Object schema)
		{ return schema instanceof Schema && ((Schema)schema).id.equals(id); }
	
	/** String representation of the schema */
	public String toString()
		{ return name; }
	
	/**
	 * Produce an xml element representation of this schemaElement.  Suitable for
	 * sending over the wire or for SchemaStoreArchiveExporter.
	 * @param String nameType - the top level name of the schemaElement (e.g. Alias, Entity) 
	 * @param XML Document (a Factory)
	 * @return XML Element
	 */
	public Element toXML(String nameType, Document dom){
		/* the xml should look like:
		 * <SchemaRoot>
		 * 		<IdElement>1</IdElement>
		 * 		<NameElement>MyName</NameElement>
		 * 		<AuthorElement>MyAuth</AuthorElement>
		 * 		<SourceElement>MySource</SourceElement>
		 * 		<TypeElement>MyType</TypeElement>
		 * 		<DescriptionElement>MyDescription</DescriptionElement>
		 * 		<LockedElement>lock</LockedElement>
		 * </SchemaRoot>
		 */
		if(nameType == null) nameType = "SchemaRoot";
		//create top level schema node.
		Element schemaE = dom.createElement(nameType);
		
		//create id node under that
		Element idE = getNewChildElement("SchemaRootIdElement", id.toString(), dom);
		
		//create name node under that
		Element nameE = getNewChildElement("SchemaRootNameElement", name, dom);
		
		//create author node under that
		Element authE = getNewChildElement("SchemaRootAuthorElement", author, dom);
		
		//create author node under that
		Element sourceE = getNewChildElement("SchemaRootSourceElement", source, dom);
		
		//create author node under that
		Element typeE = getNewChildElement("SchemaRootTypeElement", type, dom);
		
		//create description node under that
		Element dE = getNewChildElement("SchemaRootDescriptionElement", description, dom);
		
		//create base node under that
		Element lockedE = getNewChildElement("SchemaRootLockedElement", new Boolean(locked).toString(), dom);
		
		schemaE.appendChild(idE);
		schemaE.appendChild(nameE);
		schemaE.appendChild(authE);
		schemaE.appendChild(sourceE);
		schemaE.appendChild(typeE);
		schemaE.appendChild(dE);
		schemaE.appendChild(lockedE);
		
		if(parentSchemaIDs != null){
			int x = 0;
			for(Integer pschemaID: parentSchemaIDs){
				Element pschemaElement = getNewChildElement("SchemaRootParentSchemaElement"+new Integer(x++).toString(),pschemaID.toString(), dom);
				schemaE.appendChild(pschemaElement);
			}
		}
		
		return schemaE;
	}
	
	/**
	 * Produce a schemaElement from this XML element.  Suitable for
	 * sending over the wire or for SchemaStoreArchiveExporter.
	 * @param XML Element
	 */
	public void fromXML(Element schemaE){
		//See toXML() for description of what XML should look like.
		id = new Integer(getIntValue(schemaE,"SchemaRootIdElement"));
		name = getTextValue(schemaE,"SchemaRootNameElement");
		author = getTextValue(schemaE,"SchemaRootAuthorElement");
		source = getTextValue(schemaE,"SchemaRootSourceElement");
		type = getTextValue(schemaE,"SchemaRootTypeElement");
		description = getTextValue(schemaE,"SchemaRootDescriptionElement");
		locked = getBoolValue(schemaE,"SchemaRootLockedElement");
		
		int x = 0;
		String textValue;
		parentSchemaIDs = new ArrayList<Integer>();
		while((textValue = getTextValue(schemaE,"SchemaRootParentSchemaElement"+new Integer(x++).toString())) != null){
			parentSchemaIDs.add(Integer.parseInt(textValue));
		}
	}
	
	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content 
	 * i.e for <employee><name>John</name></employee> xml snippet if
	 * the Element points to employee node and tagName is name I will return John  
	 * @param ele
	 * @param tagName
	 * @return
	 */
	protected String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		try{
			if(nl != null && nl.getLength() > 0) {
				Element el = (Element)nl.item(0);
				textVal = el.getFirstChild().getNodeValue();
			}
		} catch(java.lang.NullPointerException e){
			//ok to catch this here.
			textVal = null;
		}

		return textVal;
	}
	
	/**
	 * Calls getTextValue and returns a int value
	 * @param ele
	 * @param tagName
	 * @return
	 */
	protected int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
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
	
	
	/**
	 * Calls getBoolValue and returns a boolean value
	 * @param ele
	 * @param tagName
	 * @return
	 */
	protected boolean getBoolValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return new Boolean(getTextValue(ele,tagName));
	}
}