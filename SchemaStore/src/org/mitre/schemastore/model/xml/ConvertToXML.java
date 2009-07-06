package org.mitre.schemastore.model.xml;

import java.util.ArrayList;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** Class for converting SchemaStore objects to XML */
public class ConvertToXML
{
	/** Stores a reference to the XML document */
	static private Document document;
	
	/** Adds a new child element to the specified element */
	static private Element addChildElement(Element element, String name, Object object)
	{
		Element childElement = document.createElement(name);
		String text = object==null ? "" : object.toString().replaceAll("[^\\p{ASCII}]","#");
		childElement.appendChild(document.createTextNode(text));
		element.appendChild(childElement);
		return childElement;
	}
	
	/** Generates the XML for the specified schema */
	static public Element generate(Schema schema, ArrayList<Integer> parentIDs, Document documentIn)
	{
		document = documentIn;
		
		// Generate the schema XML element
		Element element = document.createElement("Schema");			
		addChildElement(element,"SchemaId",schema.getId());
		addChildElement(element,"SchemaName", schema.getName());
		addChildElement(element,"SchemaAuthor", schema.getAuthor());
		addChildElement(element,"SchemaSource", schema.getSource());
		addChildElement(element,"SchemaType", schema.getType());
		addChildElement(element,"SchemaDescription", schema.getDescription());
		addChildElement(element,"SchemaLocked", new Boolean(schema.getLocked()));
		
		// Add parent schemas to the schema XML element
		if(parentIDs != null)
		{
			int x = 0;
			for(Integer parentID : parentIDs)
			{
				String name = "SchemaParentId"+new Integer(x++).toString();
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
		addChildElement(element,"ElementId",schemaElement.getId());
		addChildElement(element,"ElementName",schemaElement.getName());
		addChildElement(element,"ElementDescription",schemaElement.getDescription());
		addChildElement(element,"ElementBase",schemaElement.getBase());
		
		// Generate XML for alias schema elements
		if(schemaElement instanceof Alias)
			addChildElement(element,"AliasIdElement",((Alias)schemaElement).getElementID());

		// Generate XML for attribute schema elements
		if(schemaElement instanceof Attribute)
		{
			Attribute attribute = (Attribute)schemaElement;
			addChildElement(element,"AttributeEntityId", attribute.getEntityID());
			addChildElement(element,"AttributeDomainId", attribute.getDomainID());
			addChildElement(element,"AttributeMin", attribute.getMin());
			addChildElement(element,"AttributeMax", attribute.getMax());
			addChildElement(element,"AttributeKey", new Boolean(attribute.isKey()));
		}
		
		// Generate XML for containment schema elements
		if(schemaElement instanceof Containment)
		{
			Containment containment = (Containment)schemaElement;
			addChildElement(element,"ContainmentParentId",containment.getParentID()==null?"NULL":containment.getParentID());
			addChildElement(element,"ContainmentChildId",containment.getChildID());
			addChildElement(element,"ContainmentMin",containment.getMin());
			addChildElement(element,"ContainmentMax",containment.getMax());
		}
		
		// Generate XML for domain value schema elements
		if(schemaElement instanceof DomainValue)
			addChildElement(element,"DomainValueDomainId",((DomainValue)schemaElement).getDomainID());

		// Generate XML for relationship schema elements
		if(schemaElement instanceof Relationship)
		{
			Relationship relationship = (Relationship)schemaElement;
			addChildElement(element,"RelationshipLeftId",relationship.getLeftID());
			addChildElement(element,"RelationshipLeftMin",relationship.getLeftMin());
			addChildElement(element,"RelationshipLeftMax",relationship.getLeftMax());
			addChildElement(element,"RelationshipRightId",relationship.getRightID());
			addChildElement(element,"RelationshipRightMin",relationship.getRightMin());
			addChildElement(element,"RelationshipRightMax",relationship.getRightMax());
		}
		
		// Generate XML for subtype schema elements
		if(schemaElement instanceof Subtype)
		{
			Subtype subtype = (Subtype)schemaElement;
			addChildElement(element,"SubTypeParentId",subtype.getParentID());
			addChildElement(element,"SubTypeChildId",subtype.getChildID());
		}
		
		return element;
	}	
	
	/** Retrieves the element path for the given element ID */
	static private String getElementPath(Integer elementID, ArrayList<HierarchicalGraph> graphs)
	{
		for(HierarchicalGraph graph : graphs)
			if(graph.containsElement(elementID))
			{
				ArrayList<ArrayList<SchemaElement>> paths = graph.getPaths(elementID);
				if(paths!=null && paths.size()>0)
				{
					StringBuffer path = new StringBuffer("/" + graph.getSchema().getName());
					for(SchemaElement element : paths.get(0))
						path.append("/" + graph.getDisplayName(element.getId()));
					return path.toString();
				}
			}
		return null;
	}
	
	/** Generates the XML for the specified mapping */
	static public Element generate(Mapping mapping, ArrayList<MappingCell> mappingCells, ArrayList<HierarchicalGraph> graphs, Document documentIn)
	{
		document = documentIn;
		
		// Generate the mapping property XML elements
		Element element = document.createElement("Mapping");			
		addChildElement(element,"MappingId",mapping.getId());
		addChildElement(element,"MappingName", mapping.getName());
		addChildElement(element,"MappingAuthor", mapping.getAuthor());
		addChildElement(element,"MappingDescription", mapping.getDescription());
		
		// Generate the mapping schema XML elements
		for(MappingSchema mappingSchema : mapping.getSchemas())
		{
			// Retrieve the associated schema
			Schema schema = null;
			for(HierarchicalGraph graph : graphs)
				if(graph.getSchema().getId().equals(mappingSchema.getId()))
					{ schema = graph.getSchema(); break; }
			if(schema==null) continue;
			
			// Stores the mapping schema
			Element schemaElement = addChildElement(element,"MappingSchema",null);
			addChildElement(schemaElement,"SchemaId",mappingSchema.getId());
			addChildElement(schemaElement,"SchemaName",schema.getName());
			addChildElement(schemaElement,"SchemaModel",mappingSchema.getModel());
			addChildElement(schemaElement,"SchemaSide",mappingSchema.getSide());
		}		

		// Generate the mapping cell XML elements
		for(MappingCell mappingCell : mappingCells)
		{
			// Retrieve the element paths
			String element1Path = getElementPath(mappingCell.getElement1(),graphs);
			String element2Path = getElementPath(mappingCell.getElement2(),graphs);
			if(element1Path==null || element2Path==null) continue;
			
			// Stores the mapping cell information
			Element mappingCellElement = addChildElement(element,"MappingCell",null);
			addChildElement(mappingCellElement,"MappingCellId",mappingCell.getId());
			addChildElement(mappingCellElement,"MappingCellAuthor",mappingCell.getAuthor());
			addChildElement(mappingCellElement,"MappingCellDate",mappingCell.getModificationDate());
			addChildElement(mappingCellElement,"MappingCellElement1Id",mappingCell.getElement1());			
			addChildElement(mappingCellElement,"MappingCellElement1Path",element1Path);
			addChildElement(mappingCellElement,"MappingCellElement2Id",mappingCell.getElement2());
			addChildElement(mappingCellElement,"MappingCellElement2Path",element2Path);
			addChildElement(mappingCellElement,"MappingCellScore",mappingCell.getScore());
			addChildElement(mappingCellElement,"MappingCellTransform",mappingCell.getTransform());
			addChildElement(mappingCellElement,"MappingCellNotes",mappingCell.getNotes());
			addChildElement(mappingCellElement,"MappingCellValidated",mappingCell.getValidated());
		}
		
		return element;
	}
}