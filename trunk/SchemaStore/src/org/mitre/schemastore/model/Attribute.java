// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * Class for storing an attribute
 * @author CWOLF
 */
public class Attribute extends SchemaElement
{
	/** Stores the attribute's entity id */
	private Integer entityID;
	
	/** Stores the attribute's domain id */
	private Integer domainID;
	
	/** Stores the attribute's min cardinality */
	private Integer min;
	
	/** Stores the attribute's max cardinality */
	private Integer max;
	
	/** Stores if the attribute serves as a key for the entity */
	private boolean key;
	
	/** Constructs a default attribute */
	public Attribute() {}
	
	/** Constructs the attribute */
	public Attribute(Integer id, String name, String description, Integer entityID, Integer domainID, Integer min, Integer max, boolean key, Integer base)
		{ super(id,name,description,base); this.entityID=entityID; this.domainID=domainID; this.min=min; this.max=max; this.key=key; }
	
	// Handles all attribute getters
	public Integer getEntityID() { return entityID; }
	public Integer getDomainID() { return domainID; }
	public Integer getMin() { return min; }
	public Integer getMax() { return max; }
	public boolean isKey() { return key; }
	
	// Handles all attribute setters
	public void setEntityID(Integer entityID) { this.entityID = entityID; }
	public void setDomainID(Integer domainID) { this.domainID = domainID; }
	public void setMin(Integer min) { this.min = min; }
	public void setMax(Integer max) { this.max = max; }
	public void setKey(boolean key) { this.key = key; }
	
	/**
	 * Produce an xml element representation of this schemaElement.  Suitable for
	 * sending over the wire or for SchemaStoreArchiveExporter.
	 * @param String nameType - the top level name of the schemaElement (e.g. Alias, Entity) 
	 * @param XML Document (a Factory)
	 * @return XML Element
	 */
	public Element toXML(String nameType, Document dom){
		/* the xml should look like:
		 * <AttributeElement>
		 * 		<IdElement>1</IdElement>
		 * 		<NameElement>MyName</NameElement>
		 * 		<DescriptionElement>MyDescription</DescriptionElement>
		 * 		<BaseElement>MyBase</BaseElement>
		 * 		<EntityIdElement>2</EntityIdElement>
		 * 		<DomainIdElement>3</DomainIdElement>
		 * 		<AttributeMinElement>4</AttributeMinElement>
		 * 		<AttributeMaxElement>5</AttributeMaxElement>
		 * 		<AttributeKeyElement>true</AttributeKeyElement>
		 * </AttributeElement>
		 */
		//create top level schema node. Everything but ElementId is created here.
		Element schemaE = super.toXML("AttributeElement",dom);
		
		//create entity id node under that
		Element entityE = getNewChildElement("AttributeElementEntityIdElement", entityID.toString(), dom);
		
		//create domain id node under that
		Element domainIdE = getNewChildElement("AttributeElementDomainIdElement", domainID.toString(), dom);
		
		//create Min node under that
		Element aliasMinE = getNewChildElement("AttributeMinElement", min.toString(), dom);
		
		//create Max node under that
		Element aliasMaxE = getNewChildElement("AttributeMaxElement", max.toString(), dom);
		
		//create Max node under that
		Element keyE = getNewChildElement("AttributeKeyElement", new Boolean(key).toString(), dom);
		
		schemaE.appendChild(entityE);
		schemaE.appendChild(domainIdE);
		schemaE.appendChild(aliasMinE);
		schemaE.appendChild(aliasMaxE);
		schemaE.appendChild(keyE);
		
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
		entityID = new Integer(getIntValue(schemaE,"AttributeElementEntityIdElement"));
		domainID = new Integer(getIntValue(schemaE,"AttributeElementDomainIdElement"));
		min = new Integer(getIntValue(schemaE,"AttributeMinElement"));
		max = new Integer(getIntValue(schemaE,"AttributeMaxElement"));
		key = getBoolValue(schemaE,"AttributeKeyElement");
		
	}
}
