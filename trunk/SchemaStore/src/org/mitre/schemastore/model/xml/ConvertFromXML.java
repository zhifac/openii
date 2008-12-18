package org.mitre.schemastore.model.xml;

import java.util.ArrayList;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/** Class for converting SchemaStore objects from XML */
public class ConvertFromXML
{
	/** Retrieves a value from the element */
	static private String getValue(Element element, String tag)
	{
		NodeList tagList = element.getElementsByTagName(tag);
		try {
			if(tagList!=null && tagList.getLength()>0)
			{
				Element el = (Element)tagList.item(0);
				String value = el.getFirstChild().getNodeValue();
				return value.equals("NULL") ? "" : value;
			}
		} catch(java.lang.NullPointerException e) {}
		return null;
	}
	
	/** Retrieves an integer value from the element */
	static private Integer getIntegerValue(Element element, String tag)
	{
		String value = getValue(element,tag);
		return (value==null || value.equals("")) ? null : Integer.parseInt(value);
	}

	/** Retrieves a boolean value from the element */
	static private Boolean getBooleanValue(Element element, String tag)
	{
		String value = getValue(element,tag);
		return value==null ? null : new Boolean(value);
	}
	
	/** Retrieve the schema from the specified XML */
	static public Schema getSchema(Element element)
	{
		Schema schema = new Schema();
		schema.setId(getIntegerValue(element,"SchemaRootIdElement"));
		schema.setName(getValue(element,"SchemaRootNameElement"));
		schema.setAuthor(getValue(element,"SchemaRootAuthorElement"));
		schema.setSource(getValue(element,"SchemaRootSourceElement"));
		schema.setType(getValue(element,"SchemaRootTypeElement"));
		schema.setDescription(getValue(element,"SchemaRootDescriptionElement"));
		schema.setLocked(getBooleanValue(element,"SchemaRootLockedElement"));
		return schema;
	}
	
	/** Retrieve the parent schemas from the specified XML */
	static public ArrayList<Integer> getParentSchemaIDs(Element element)
	{
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		int x = 0;
		String textValue;
		while((textValue = getValue(element,"SchemaRootParentSchemaElement"+new Integer(x++).toString())) != null)
			schemaIDs.add(Integer.parseInt(textValue));
		return schemaIDs;
	}
	
	/** Retrieves the schema element from the specified XML */
	static public SchemaElement getSchemaElement(Element element)
	{
		// Identify the schema element to be constructed
		SchemaElement schemaElement = null;
		String type = element.getTagName();
		if(type.equals(Alias.class.getName())) schemaElement = new Alias();
		if(type.equals(Attribute.class.getName())) schemaElement = new Attribute();
		if(type.equals(Containment.class.getName())) schemaElement = new Containment();
		if(type.equals(Domain.class.getName())) schemaElement = new Domain();
		if(type.equals(DomainValue.class.getName())) schemaElement = new DomainValue();
		if(type.equals(Entity.class.getName())) schemaElement = new Entity();
		if(type.equals(Relationship.class.getName())) schemaElement = new Relationship();
		if(type.equals(Subtype.class.getName())) schemaElement = new Subtype();
		if(schemaElement==null) return null;
		
		// Populate the general schema element information
		schemaElement.setId(getIntegerValue(element,"IdElement"));
		schemaElement.setName(getValue(element,"NameElement"));
		schemaElement.setDescription(getValue(element,"DescriptionElement"));
		schemaElement.setBase(getIntegerValue(element,"BaseElement"));		
		
		// Populate alias schema elements
		if(schemaElement instanceof Alias)
			((Alias)schemaElement).setElementID(getIntegerValue(element,"AliasElementIdElement"));

		// Populate attribute schema elements
		if(schemaElement instanceof Attribute)
		{
			Attribute attribute = (Attribute)schemaElement;
			attribute.setEntityID(getIntegerValue(element,"AttributeElementEntityIdElement"));
			attribute.setDomainID(getIntegerValue(element,"AttributeElementDomainIdElement"));
			attribute.setMin(getIntegerValue(element,"AttributeMinElement"));
			attribute.setMax(getIntegerValue(element,"AttributeMaxElement"));
			attribute.setKey(getBooleanValue(element,"AttributeKeyElement"));
		}
		
		// Populate containment schema elements
		if(schemaElement instanceof Containment)
		{
			Containment containment = (Containment)schemaElement;
			containment.setParentID(getIntegerValue(element,"ContainmentParentIdElement"));
			containment.setChildID(getIntegerValue(element,"ContainmentChildIdElement"));
			containment.setMin(getIntegerValue(element,"ContainmentMinElement"));
			containment.setMax(getIntegerValue(element,"ContainmentMaxElement"));			
		}
		
		// Populate domain value schema elements
		if(schemaElement instanceof DomainValue)
			((DomainValue)schemaElement).setDomainID(getIntegerValue(element,"DomainValueDomainIdElement"));

		// Populate relationship schema elements
		if(schemaElement instanceof Relationship)
		{
			Relationship relationship = (Relationship)schemaElement;
			relationship.setLeftID(getIntegerValue(element,"RelationshipLeftIdElement"));
			relationship.setLeftMin(getIntegerValue(element,"RelationshipLeftMinElement"));
			relationship.setLeftMax(getIntegerValue(element,"RelationshipLeftMaxElement"));
			relationship.setRightID(getIntegerValue(element,"RelationshipRightIdElement"));
			relationship.setRightMin(getIntegerValue(element,"RelationshipRightMinElement"));
			relationship.setRightMax(getIntegerValue(element,"RelationshipRightMaxElement"));
		}
		
		// Populate subtype schema elements
		if(schemaElement instanceof Subtype)
		{
			Subtype subtype = (Subtype)schemaElement;
			subtype.setParentID(getIntegerValue(element,"SubTypeParentIdElement"));
			subtype.setChildID(getIntegerValue(element,"SubTypeChildIdElement"));
		}

		return schemaElement;
	}
}