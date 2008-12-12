// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.NodeList;


/**
 * Class for storing a schema element
 * @author CWOLF
 */
public class SchemaElement implements Serializable
{
	/** Stores the schema element id */
	private Integer id;
	
	/** Stores the schema element name */
	protected String name;
	
	/** Stores the schema element description */
	private String description;

	/** Stores the schema element base ID */
	private Integer base;
	
	/** Constructs a default schema element */
	public SchemaElement() {}
	
	/** Constructs the schema element */
	protected SchemaElement(Integer id, String name, String description, Integer base)
	{
		this.id = id;
		this.name = name==null ? "" : name;
		this.description = description==null ? "" : description;
		this.base = base;
	}
	
	// Handles all schema element getters
	public Integer getId() { return id; }
	public String getName() { return name; }
	public String getDescription() { return description==null ? "" : description; }
	public Integer getBase() { return base; }
	
	// Handles all schema element setters
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name==null ? "" : name; }
	public void setDescription(String description) { this.description = description==null ? "" : description; }
	public void setBase(Integer base) { this.base = base; }
	
	/** Generates a hash code for the match */
	public int hashCode()
		{ return id; }
	
	/** Indicates that two schema elements are equals */
	public final boolean equals(Object schemaElement)
	{
		return schemaElement instanceof SchemaElement && ((SchemaElement)schemaElement).id.equals(id);
	}
	
	/** String representation of the schema element */
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
		 * <nameType>
		 * 		<IdElement>1</IdElement>
		 * 		<NameElement>MyName</NameElement>
		 * 		<DescriptionElement>MyDescription</DescriptionElement>
		 * 		<BaseElement>MyBase</BaseElement>
		 * </nameType>
		 */
		if(nameType == null) nameType = "SchemaElement";
		//create top level schema node.
		Element schemaE = dom.createElement(nameType);
		
		//create id node under that
		Element idE = getNewChildElement("IdElement", id.toString(), dom);
		
		//create name node under that
		Element nameE = getNewChildElement("NameElement", name, dom);
		
		//create description node under that
		Element dE = getNewChildElement("DescriptionElement", description, dom);
		
		//create base node under that
		Element baseE = getNewChildElement("BaseElement", base.toString(), dom);
		
		schemaE.appendChild(idE);
		schemaE.appendChild(nameE);
		schemaE.appendChild(dE);
		schemaE.appendChild(baseE);
		
		return schemaE;
	}
	
	/**
	 * Produce a schemaElement from this XML element.  Suitable for
	 * sending over the wire or for SchemaStoreArchiveExporter.
	 * @param XML Element
	 */
	public void fromXML(Element schemaE){
		//See toXML() for description of what XML should look like.
		id = new Integer(getIntValue(schemaE,"IdElement"));
		name = getTextValue(schemaE,"NameElement");
		description = getTextValue(schemaE,"DescriptionElement");
		base = new Integer(getIntValue(schemaE,"BaseElement"));
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
			if(textVal.equals("NULL")){
				return "";
			}
		} catch( java.lang.NullPointerException e){
			//ok to catch this here.
			textVal = "";
		}
		return textVal;
	}
	
	/**
	 * Calls getTextValue and returns a int value
	 * @param ele
	 * @param tagName
	 * @return
	 */
	protected Integer getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		String s = getTextValue(ele,tagName);
		if(s == null || s.equals("")){
			return null;
		}
		return Integer.parseInt(s);
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
	protected Boolean getBoolValue(Element ele, String tagName) {
		//in production application you would catch the exception
		String s = getTextValue(ele,tagName);
		if(s == null){
			return null;
		}
		return new Boolean(getTextValue(ele,tagName));
	}

}
