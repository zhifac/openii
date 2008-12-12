// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.NodeList;


/**
 * Class for storing an alias
 * @author CWOLF
 */
public class Alias extends SchemaElement
{
	/** Stores the element id */
	private Integer elementID;
	
	/** Constructs a default alias */
	public Alias() {}
	
	/** Constructs the alias */
	public Alias(Integer id, String name, Integer elementID, Integer base)
		{ super(id,name,"",base); this.elementID = elementID; }
	
	/** Retrieves the alias' element id */
	public Integer getElementID()
		{ return elementID; }
	
	/** Sets the alias' element id */
	public void setElementID(Integer elementID)
		{ this.elementID = elementID; }
	
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
		 * 		<AliasElementIdElement>2</AliasElementIdElement>
		 * </nameType>
		 */
		//create top level schema node. Everything but ElementId is created here.
		Element schemaE = super.toXML("AliasElement",dom);
		
		//create elementid node under that
		Element elementE = getNewChildElement("AliasElementIdElement", elementID.toString(), dom);
		
		schemaE.appendChild(elementE);
		
		return schemaE;
	}
	
	/**
	 * Produce a schemaElement from this XML element.  Suitable for
	 * sending over the wire or for SchemaStoreArchiveExporter.
	 * @param XML Element
	 */
	public void fromXML(Element schemaE){
		//See toXML() for description of what XML should look like.
		super.fromXML(schemaE);
		elementID = new Integer(getIntValue(schemaE,"AliasElementIdElement"));
	}
}
