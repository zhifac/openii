package org.mitre.schemastore.model.xml;

import java.util.ArrayList;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** Class for converting SchemaStore objects to XML */
public class ConvertToXML
{
	/** Stores a reference to the XML document */
	static private Document document;
	
	/** Adds a new child element to the specified element */
	static private void addChildElement(Element element, String name, Object object)
	{
		Element childElement = document.createElement(name);
		childElement.appendChild(document.createTextNode(object==null ? "" : object.toString()));
		element.appendChild(childElement);
	}
	
	/** Generates the XML for the specified schema */
	static public Element generate(Schema schema, ArrayList<Integer> parentIDs, Document documentIn)
	{
		document = documentIn;
		
		// Generate the schema XML element
		Element element = document.createElement("SchemaRoot");			
		addChildElement(element,"SchemaRootIdElement",schema.getId());
		addChildElement(element,"SchemaRootNameElement", schema.getName());
		addChildElement(element,"SchemaRootAuthorElement", schema.getAuthor());
		addChildElement(element,"SchemaRootSourceElement", schema.getSource());
		addChildElement(element,"SchemaRootTypeElement", schema.getType());
		addChildElement(element,"SchemaRootDescriptionElement", schema.getDescription());
		addChildElement(element,"SchemaRootLockedElement", new Boolean(schema.getLocked()));
		
		// Add parent schemas to the schema XML element
		if(parentIDs != null)
		{
			int x = 0;
			for(Integer parentID : parentIDs)
			{
				String name = "SchemaRootParentSchemaElement"+new Integer(x++).toString();
				addChildElement(element,name,parentID);				
			}
		}
		
		return element;
	}
	
	/** Generate XML for the specified schema element */
	static public Element generate(SchemaElement schemaElement, Document documentIn)
	{
		document = documentIn;

		// Generate XML for the generic schema element information
		Element element = document.createElement(schemaElement.getClass().getName());
		addChildElement(element,"IdElement",schemaElement.getId());
		addChildElement(element,"NameElement",schemaElement.getName());
		addChildElement(element,"DescriptionElement",schemaElement.getDescription());
		addChildElement(element,"BaseElement",schemaElement.getBase());
		
		// Generate XML for alias schema elements
		if(schemaElement instanceof Alias)
			addChildElement(element,"AliasElementIdElement",((Alias)schemaElement).getElementID());

		// Generate XML for attribute schema elements
		if(schemaElement instanceof Attribute)
		{
			Attribute attribute = (Attribute)schemaElement;
			addChildElement(element,"AttributeElementEntityIdElement", attribute.getEntityID());
			addChildElement(element,"AttributeElementDomainIdElement", attribute.getDomainID());
			addChildElement(element,"AttributeMinElement", attribute.getMin());
			addChildElement(element,"AttributeMaxElement", attribute.getMax());
			addChildElement(element,"AttributeKeyElement", new Boolean(attribute.isKey()));
		}
		
		// Generate XML for containment schema elements
		if(schemaElement instanceof Containment)
		{
			Containment containment = (Containment)schemaElement;
			addChildElement(element,"ContainmentParentIdElement",containment.getParentID()==null?"NULL":containment.getParentID());
			addChildElement(element,"ContainmentChildIdElement",containment.getChildID());
			addChildElement(element,"ContainmentMinElement",containment.getMin());
			addChildElement(element,"ContainmentMaxElement",containment.getMax());
		}
		
		// Generate XML for domain value schema elements
		if(schemaElement instanceof DomainValue)
			addChildElement(element,"DomainValueDomainIdElement",((DomainValue)schemaElement).getDomainID());

		// Generate XML for relationship schema elements
		if(schemaElement instanceof Relationship)
		{
			Relationship relationship = (Relationship)schemaElement;
			addChildElement(element,"RelationshipLeftIdElement",relationship.getLeftID());
			addChildElement(element,"RelationshipLeftMinElement",relationship.getLeftMin());
			addChildElement(element,"RelationshipLeftMaxElement",relationship.getLeftMax());
			addChildElement(element,"RelationshipRightIdElement",relationship.getRightID());
			addChildElement(element,"RelationshipRightMinElement",relationship.getRightMin());
			addChildElement(element,"RelationshipRightMaxElement",relationship.getRightMax());
		}
		
		// Generate XML for subtype schema elements
		if(schemaElement instanceof Subtype)
		{
			Subtype subtype = (Subtype)schemaElement;
			addChildElement(element,"SubTypeParentIdElement",subtype.getParentID());
			addChildElement(element,"SubTypeChildIdElement",subtype.getChildID());
		}
		
		return element;
	}	
}