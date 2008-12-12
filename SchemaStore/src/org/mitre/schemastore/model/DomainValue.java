// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class for storing a domain value
 * @author CWOLF
 */
public class DomainValue extends SchemaElement
{
	/** Stores the domain value's domain id */
	private Integer domainID;
	
	/** Constructs a default domain value */
	public DomainValue() {}
	
	/** Constructs the domain value */
	public DomainValue(Integer id, String name, String description, Integer domainID, Integer base)
		{ super(id,name,description,base); this.domainID=domainID; }
	
	/** Retrieves the domain value's domain id */
	public Integer getDomainID()
		{ return domainID; }
	
	/** Sets the domain value's domain id */
	public void setDomainID(Integer domainID)
		{ this.domainID = domainID; }
	
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
		 * 		<DomainValueDomainIdElement>2</DomainValueDomainIdElement>
		 * </nameType>
		 */
		//create top level schema node. Everything but DomainID is created here.
		Element schemaE = super.toXML("DomainValueElement",dom);
		
		//create domain id node under that
		Element domainE = getNewChildElement("DomainValueDomainIdElement", domainID.toString(), dom);
		
		schemaE.appendChild(domainE);
		
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
		domainID = new Integer(getIntValue(schemaE,"DomainValueDomainIdElement"));
	}
}
