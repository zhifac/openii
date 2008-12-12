// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * Class for storing a containment relationship
 * @author CWOLF
 */
public class Containment extends SchemaElement
{
	/** Stores the containment's parent id */
	private Integer parentID;
	
	/** Stores the containment's child id */
	private Integer childID;

	/** Stores the containment's min value */
	private Integer min;
	
	/** Stores the containment's max value */
	private Integer max;
	
	/** Constructs a default containment relationship */
	public Containment() {}

	/** Constructs the containment relationship */
	public Containment(Integer id, String name, String description, Integer parentID, Integer childID, Integer min, Integer max, Integer base)
		{ super(id,name,description,base); this.parentID=parentID; this.childID=childID; this.min=min; this.max=max; }
	
	// Handles all containment getters
	public Integer getParentID() { return parentID==null || parentID.equals(0) ? null : parentID; }
	public Integer getChildID() { return childID; }
	public Integer getMin() { return min; }
	public Integer getMax() { return max; }
	
	// Handles all containment setters
	public void setParentID(Integer parentID) { this.parentID = parentID==0 ? null : parentID; }
	public void setChildID(Integer childID) { this.childID = childID; }	
	public void setMin(Integer min) { this.min = min; }
	public void setMax(Integer max) { this.max = max; }
	
	/**
	 * Produce an xml element representation of this schemaElement.  Suitable for
	 * sending over the wire or for SchemaStoreArchiveExporter.
	 * @param String nameType - the top level name of the schemaElement (e.g. Alias, Entity) 
	 * @param XML Document (a Factory)
	 * @return XML Element
	 */
	public Element toXML(String nameType, Document dom){
		/* the xml should look like:
		 * <ContainmentElement>
		 * 		<IdElement>1</IdElement>
		 * 		<NameElement>MyName</NameElement>
		 * 		<DescriptionElement>MyDescription</DescriptionElement>
		 * 		<BaseElement>MyBase</BaseElement>
		 * 		<ContainmentParentIdElement>2</ContainmentParentIdElement>
		 * 		<ContainmentChildIdElement>3</ContainmentChildIdElement>
		 * 		<ContainmentMinElement>4</ContainmentMinElement>
		 * 		<ContainmentMaxElement>5</ContainmentMaxElement>
		 * </ContainmentElement>
		 */
		//create top level schema node. Everything but ElementId is created here.
		Element schemaE = super.toXML("ContainmentElement",dom);
		
		Element parentE = null;
		//create entity id node under that
		if(parentID != null){
			parentE = getNewChildElement("ContainmentParentIdElement", parentID.toString(), dom);
		} else{
			parentE = getNewChildElement("ContainmentParentIdElement", "NULL", dom);
		}
		//create domain id node under that
		Element childE = getNewChildElement("ContainmentChildIdElement", childID.toString(), dom);
		
		//create Min node under that
		Element aliasMinE = getNewChildElement("ContainmentMinElement", min.toString(), dom);
		
		//create Max node under that
		Element aliasMaxE = getNewChildElement("ContainmentMaxElement", max.toString(), dom);
		
		schemaE.appendChild(parentE);
		schemaE.appendChild(childE);
		schemaE.appendChild(aliasMinE);
		schemaE.appendChild(aliasMaxE);
		
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
		parentID = getIntValue(schemaE,"ContainmentParentIdElement");
		childID = getIntValue(schemaE,"ContainmentChildIdElement");
		min = getIntValue(schemaE,"ContainmentMinElement");
		max = getIntValue(schemaE,"ContainmentMaxElement");
	}
}
