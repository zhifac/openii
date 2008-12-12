// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class for storing a relationship
 * @author CWOLF
 */
public class Relationship extends SchemaElement
{
	/** Stores the relationship's left id */
	private Integer leftID;
	
	/** Stores the relationship's left min value */
	private Integer leftMin;
	
	/** Stores the relationship's left max value */
	private Integer leftMax;
	
	/** Stores the relationship's right id */
	private Integer rightID;
	
	/** Stores the relationship's right min value */
	private Integer rightMin;
	
	/** Stores the relationship's right max value */
	private Integer rightMax;	

	/** Constructs a default relationship */
	public Relationship() {}
	
	/** Constructs the relationship */
	public Relationship(Integer id, String name, Integer leftID, Integer leftMin, Integer leftMax, Integer rightID, Integer rightMin, Integer rightMax, Integer base)
		{ super(id,name,"",base); this.leftID=leftID; this.leftMin=leftMin; this.leftMax=leftMax; this.rightID=rightID; this.rightMin=rightMin; this.rightMax=rightMax; }
	
	// Handles all relationship getters
	public Integer getLeftID() { return leftID; }
	public Integer getLeftMin() { return leftMin; }
	public Integer getLeftMax() { return leftMax; }
	public Integer getRightID() { return rightID; }
	public Integer getRightMin() { return rightMin; }
	public Integer getRightMax() { return rightMax; }

	// Handles all relationship setters
	public void setLeftID(Integer leftID) { this.leftID = leftID; }
	public void setLeftMax(Integer leftMax) { this.leftMax = leftMax; }
	public void setLeftMin(Integer leftMin) { this.leftMin = leftMin; }
	public void setRightID(Integer rightID) { this.rightID = rightID; }
	public void setRightMax(Integer rightMax) { this.rightMax = rightMax; }
	public void setRightMin(Integer rightMin) { this.rightMin = rightMin; }
	
	/**
	 * Produce an xml element representation of this schemaElement.  Suitable for
	 * sending over the wire or for SchemaStoreArchiveExporter.
	 * @param String nameType - the top level name of the schemaElement (e.g. Alias, Entity) 
	 * @param XML Document (a Factory)
	 * @return XML Element
	 */
	public Element toXML(String nameType, Document dom){
		/* the xml should look like:
		 * <RelationshipElement>
		 * 		<IdElement>1</IdElement>
		 * 		<NameElement>MyName</NameElement>
		 * 		<DescriptionElement>MyDescription</DescriptionElement>
		 * 		<BaseElement>MyBase</BaseElement>
		 * 		<RelationshipLeftIdElement>2</RelationshipLeftIdElement>
		 * 		<RelationshipLeftMinElement>3</RelationshipLeftMinElement>
		 * 		<RelationshipLeftMaxElement>4</RelationshipLeftMaxElement>
		 * 		<RelationshipRightIdElement>5</RelationshipRightIdElement>
		 * 		<RelationshipRightMinElement>6</RelationshipRightMinElement>
		 * 		<RelationshipRightMaxElement>7</RelationshipRightMaxElement>
		 * </RelationshipElement>
		 */
		//create top level schema node. Everything but ElementId is created here.
		Element schemaE = super.toXML("RelationshipElement",dom);
		
		//create left id node under that
		Element leftIdE = getNewChildElement("RelationshipLeftIdElement", leftID.toString(), dom);
		Element leftMinE = getNewChildElement("RelationshipLeftMinElement", leftMin.toString(), dom);
		Element leftMaxE = getNewChildElement("RelationshipLeftMaxElement", leftMax.toString(), dom);
		
		//create right id node under that
		Element rightIdE = getNewChildElement("RelationshipRightIdElement", rightID.toString(), dom);
		Element rightMinE = getNewChildElement("RelationshipRightMinElement", rightMin.toString(), dom);
		Element rightMaxE = getNewChildElement("RelationshipRightMaxElement", rightMax.toString(), dom);
		
		schemaE.appendChild(leftIdE);
		schemaE.appendChild(leftMinE);
		schemaE.appendChild(leftMaxE);
		schemaE.appendChild(rightIdE);
		schemaE.appendChild(rightMinE);
		schemaE.appendChild(rightMaxE);
		
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
		leftID = new Integer(getIntValue(schemaE,"RelationshipLeftIdElement"));
		leftMin = new Integer(getIntValue(schemaE,"RelationshipLeftMinElement"));
		leftMax = new Integer(getIntValue(schemaE,"RelationshipLeftMaxElement"));
		rightID = new Integer(getIntValue(schemaE,"RelationshipRightIdElement"));
		rightMin = new Integer(getIntValue(schemaE,"RelationshipRightMinElement"));
		rightMax = new Integer(getIntValue(schemaE,"RelationshipRightMaxElement"));
	}
}
