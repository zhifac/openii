// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class for storing a subtype relationship
 * @author CWOLF
 */
public class Subtype extends SchemaElement
{
	/** Stores the subtype's parent id */
	private Integer parentID;
	
	/** Stores the subtype's child id */
	private Integer childID;
	
	/** Constructs a default subtype relationship */
	public Subtype() {}

	/** Constructs the subtype relationship */
	public Subtype(Integer id, Integer parentID, Integer childID, Integer base)
		{ super(id,"","",base); this.parentID=parentID; this.childID=childID; }
	
	// Handles all containment getters
	public Integer getParentID() { return parentID; }
	public Integer getChildID() { return childID; }
	
	// Handles all containment setters
	public void setParentID(Integer parentID) { this.parentID = parentID; }
	public void setChildID(Integer childID) { this.childID = childID; }	
	
	/**
	 * Produce an xml element representation of this schemaElement.  Suitable for
	 * sending over the wire or for SchemaStoreArchiveExporter.
	 * @param String nameType - the top level name of the schemaElement (e.g. Alias, Entity) 
	 * @param XML Document (a Factory)
	 * @return XML Element
	 */
	public Element toXML(String nameType, Document dom){
		/* the xml should look like:
		 * <SubTypeElement>
		 * 		<IdElement>1</IdElement>
		 * 		<NameElement>MyName</NameElement>
		 * 		<DescriptionElement>MyDescription</DescriptionElement>
		 * 		<BaseElement>MyBase</BaseElement>
		 * 		<SubTypeParentIdElement>2</SubTypeParentIdElement>
		 * 		<SubTypeChildIdElement>3</SubTypeChildIdElement>
		 * </SubTypeElement>
		 */
		//create top level schema node. Everything but ElementId is created here.
		Element schemaE = super.toXML("SubTypeElement",dom);
		
		//create left id node under that
		Element parentIdE = getNewChildElement("SubTypeParentIdElement", parentID.toString(), dom);
		Element childIdE = getNewChildElement("SubTypeChildIdElement", childID.toString(), dom);
		
		schemaE.appendChild(parentIdE);
		schemaE.appendChild(childIdE);
		
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
		parentID = new Integer(getIntValue(schemaE,"SubTypeParentIdElement"));
		childID = new Integer(getIntValue(schemaE,"SubTypeChildIdElement"));
	}
}
