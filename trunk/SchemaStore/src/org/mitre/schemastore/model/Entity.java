// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Class for storing an entity
 * @author CWOLF
 */
public class Entity extends SchemaElement
{
	/** Constructs a default entity */
	public Entity() {}
	
	/** Constructs the entity */
	public Entity(Integer id, String name, String description, Integer base)
		{ super(id,name,description,base); }
	
	/**
	 * Produce an xml element representation of this schemaElement.  Suitable for
	 * sending over the wire or for SchemaStoreArchiveExporter.
	 * @param String nameType - the top level name of the schemaElement (e.g. Alias, Entity) 
	 * @param XML Document (a Factory)
	 * @return XML Element
	 */
	public Element toXML(String nameType, Document dom){
		/* the xml should look like:
		 * <DomainElement>
		 * 		<IdElement>1</IdElement>
		 * 		<NameElement>MyName</NameElement>
		 * 		<DescriptionElement>MyDescription</DescriptionElement>
		 * 		<BaseElement>MyBase</BaseElement>
		 * </DomainElement>
		 */
		//create top level schema node. Everything but ElementId is created here.
		Element schemaE = super.toXML("EntityElement",dom);
		
		return schemaE;
	}
}
